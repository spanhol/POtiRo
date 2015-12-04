package util;

import dao.ClienteDao;
import dao.GeoDao;
import entidades.Cliente;
import entidades.Geo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class GeoService {

//	private static final String chave = "AIzaSyB-FVuO1s67b0AtI4gvUdYQKJNRebDnM_c";
	private static final String chave = "&key=AIzaSyB-FVuO1s67b0AtI4gvUdYQKJNRebDnM_c";

	/**
	 *
	 * @param origem
	 * @param destino
	 * @param waypoints
	 * @return
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String getDirecoes(GeoWrapper origem, GeoWrapper destino, List<GeoWrapper> waypoints) throws UnsupportedEncodingException, MalformedURLException, IOException {
		String link = "https://maps.googleapis.com/maps/api/directions/json?"
			+ "origin="
			+ URLEncoder.encode(origem.toPlaceId(), "UTF-8");
		if (waypoints != null && waypoints.size() > 0) {
			link += "&waypoints=";
//			link += "optimize:true|";
		}
		for (GeoWrapper w : waypoints) {
			link += URLEncoder.encode(w.toPlaceId(), "UTF-8") + "|";
		}
		link = link.substring(0, link.length() - 1);
		link += "&destination="
			+ URLEncoder.encode(destino.toPlaceId(), "UTF-8");
		link += chave;
		System.out.println(link);

//		URL url = new URL(link);
//		URLConnection yc = url.openConnection();
//		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
		return link;
	}

	public static GeoWrapper getGeo(String endereco) throws MalformedURLException, IOException {
		String link = "http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(endereco, "UTF-8") + "&language=pt-br";
		System.out.println(link);
		GeoWrapper gw = new GeoWrapper();
		URL url = new URL(link);
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));

		boolean acceptTypes = true;
		String placeId = null;
		String longName = "";
		String tipo;

		String numero = "";
		String rua = "";
		String bairro = "";
		String cidade = "";
		String estado = "";
		String pais = "";
		String cep = "";
		String locationType = "";

		boolean acceptLat = false;
		boolean acceptLng = false;
		String lat = "";
		String lng = "";

		String linha;
		while ((linha = in.readLine()) != null) {
			System.out.println(linha);
			if (linha.trim().equals("\"status\" : \"OVER_QUERY_LIMIT\"")) {
				break;
			}
			if (acceptTypes && linha.contains("\"formatted_address\" : ")) {
				acceptTypes = false;
			} else if (acceptTypes && linha.contains("\"long_name\" : \"")) {
				longName = linha.replace("\"long_name\" : \"", "").replace(",", "").replace("\"", "").trim();
			} else if (acceptTypes && linha.contains("\"types\" : [")) {
				tipo = linha.replace("\"types\" : [", "").replace("]", "").replace("\"", "").trim();
				ArrayList<String> tipos = new ArrayList<>(Arrays.asList(tipo.split(",")));
				for (String t : tipos) {
					t = t.trim();
				}
				if (tipos.contains("street_number")) {
					numero = longName;
				} else if (tipos.contains("route")) {
					rua = longName;
				} else if (tipos.contains("sublocality_level_1")) {
					bairro = longName;
				} else if (tipos.contains("administrative_area_level_2")) {
					cidade = longName;
				} else if (tipos.contains("administrative_area_level_1")) {
					estado = longName;
				} else if (tipos.contains("country")) {
					pais = longName;
				} else if (tipos.contains("postal_code_prefix")) {
					cep = longName + "000";
				} else if (tipos.contains("postal_code")) {
					cep = longName;
				}
			} else if (linha.contains("\"location\" : {")) {
				acceptLat = true;
				acceptLng = true;
			} else if (linha.contains("\"lat\" : ")) {
				if (acceptLat) {
					lat = linha.replace("\"lat\" : ", "").replace(",", "").replace("\"", "").trim();
					acceptLat = false;
				}
			} else if (linha.contains("\"lng\" : ")) {
				if (acceptLng) {
					lng = linha.replace("\"lng\" : ", "").replace(",", "").replace("\"", "").trim();
					acceptLng = false;
				}
			} else if (linha.contains("\"location_type\" : \"")) {
				locationType = linha.replace("\"location_type\" : \"", "").replace(",", "").replace("\"", "").trim();
			} else if (linha.contains("\"place_id\" : \"")) {
				placeId = linha.replace("\"place_id\" : \"", "").replace(",", "").replace("\"", "").trim();
			}
		}
		in.close();

		if (cep.equals("") || pais.equals("Brasil")) {
			return null;
		}

		if (placeId != null) {
			gw.setPlaceId(placeId);
			gw.setBairro(bairro);
			if (!cep.equals("")) {
				gw.setCep(Integer.valueOf(cep.replace("-", "")));
			}
			gw.setCidade(cidade);
			gw.setEstado(estado);
			gw.setLat(Double.valueOf(lat));
			gw.setLng(Double.valueOf(lng));
			gw.setLocationType(locationType);
			gw.setNumero(numero);
			gw.setPlaceId(placeId);
			gw.setRua(rua);
		}
		if (placeId != null) {
			return gw;
		} else {
			return null;
		}
	}

	public static void batchGeo(boolean update) {
		ClienteDao dao = new ClienteDao();
		GeoDao geoDao = new GeoDao();
		ObservableList<Cliente> clientes = FXCollections.observableArrayList();
		clientes.addAll(dao.listar());
		for (Cliente c : clientes) {
			boolean existe = false;
			if (!update) {
				if (!c.getGeoCollection().isEmpty()) {
					for (Geo g : c.getGeoCollection()) {
						if (g.getNumero() == null) {
							existe = false;
							System.out.println("geo cliente: " + c.getCodigo() + " sem rua.");
						} else {
							existe = true;
							System.out.println("geo cliente: " + c.getCodigo() + " existe.");
						}
					}
				}
			}
			if (update || !existe) {
				System.out.println(c);
				String end = "";
				if (c.getEstado() != null) {
					end += c.getEstado() + "+";
				}
				if (c.getCidade() != null) {
					end += c.getCidade() + "+";
				}
				if (c.getBairro() != null) {
					end += c.getBairro() + "+";
				}
				if (c.getCep() != null) {
					end += c.getCep() + "+";
				}
				if (c.getRua() != null) {
					end += c.getRua() + "+";
				}
				if (c.getNumero() != null) {
					end += c.getNumero();
				}
				System.out.println(end);
				GeoWrapper gw = null;
				try {
					gw = getGeo(end);
					if (gw == null) {
						gw = getGeo(c.getCep());
						if (gw == null) {
							gw = getGeo(c.getCep().substring(0, c.getCep().length() - 3));
						}
					}
				} catch (IOException ex) {
					Logger.getLogger(GeoService.class.getName()).log(Level.SEVERE, null, ex);
				}
				if (gw != null) {
					gw.setCliente(c);
					System.out.println(gw.toString());
					geoDao.upsert(gw);
				} else {
					System.out.println("NULL");
				}
			}
		}
	}

	public static void printMarkers() {
		GeoDao geoDao = new GeoDao();
		ObservableList<GeoWrapper> geo = FXCollections.observableArrayList();
		geo.addAll(geoDao.listarWrapper());
		geo.stream().forEach((g) -> {
			System.out.println(g.getMarker());
		});
	}
}
