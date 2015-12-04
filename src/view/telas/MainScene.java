package view.telas;

import algoritmos.Blob;
import algoritmos.Christofides;
import algoritmos.Greedy;
import modelo.Vertice;
import algoritmos.aEstrela;
import com.google.common.io.Files;
import dao.GeoDao;
import entidades.Geo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import modelo.Ponto;
import util.GeoService;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class MainScene extends Scene {

	Pane root;

	public final List<Vertice> greedy(LinkedList<GeoWrapper> entregas) {
		System.out.println("-----------------------------greedy----------------------------");
		List<Vertice> resultado = Greedy.run(entregas);
		return resultado;
	}

	public final Ponto blob(GeoWrapper origem, LinkedList<GeoWrapper> entregas) {
		System.out.println("-----------------------------blob----------------------------");
		Blob b = new Blob(origem, entregas);
		return b.run();
	}

	public final Ponto cristofides(GeoWrapper origem, LinkedList<GeoWrapper> entregas) {
		System.out.println("-----------------------------Cristofides----------------------------");
		Blob b = new Blob(origem, entregas);
		return b.run();
	}

	public final Ponto aestrela(GeoWrapper origem, GeoWrapper destino, LinkedList<GeoWrapper> entregas) {
		System.out.println("-----------------------------aestrela----------------------------");
		aEstrela a = new aEstrela(origem, destino, entregas);
		return a.run();
	}

	public MainScene(Pane main) {
		super(main);
		root = main;
		int x = 1350;
		int y = 650;
		root.setMinSize(x, y);
		HBox hbox = new HBox();
		hbox.setMinSize(x, y);
		WebView webView = new WebView();
		webView.setMinSize(x, y);
		WebEngine pagina = webView.getEngine();

		GeoService.batchGeo(false);

//		GeoDao dao = new GeoDao();
//		GeoWrapper origem = dao.getInstance(new GeoWrapper(new Geo("ChIJ-cKmDq328JQRXYzuA52tmxk")));
//		Ampére - -25.9167035,-53.47472990000001
//		LinkedList<GeoWrapper> entregas = new LinkedList<>();
//		entregas.add(origem);
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJj-o7RpBH8JQR0kf8cBFK16E"))));
//
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJ04NtfX66pQRkZnRVFGbOdA"))));
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJasuwWmp4pQRYSW-TLbVbYQ"))));
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjb_Vp9Vy8JQR5Utnb_w2_Fo"))));
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjcbKbiFa7JQRIEJL3yegMKE"))));
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJjRyJIg3V55QRV9I3IrosErM"))));
////Terra Roxa - -24.2463363,-54.041223
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6zyHl4Gg9JQRfPU92M6dUWk"))));
////Saudades - -26.9252164,-53.0049449
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6bDLfCsi-5QRSQ3lXBHb4qY"))));
////Concórdia - -27.2040882,-51.9791849
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJJRzkf51_45QRVxiWQXnSSoY"))));
////Machadinho - -27.6008599,-51.6623517
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ79zqVCXI45QRg2nZd86s3A4"))));
////Osório - -29.88809359999999,-50.2656749
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6Zx2gAdmGJURWB4MikdlzLI"))));
////Vidal Ramos - -27.3699127,-49.34501119999999
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ73BOuPh335QR4eh5gI_jXFk"))));
////Jaraguá do Sul - -26.4896863,-49.0315087
//		entregas.add(dao.getInstance(new GeoWrapper(new Geo("ChIJ6Qn8Y-W_3pQRPFgs5Oj5zpI"))));
//		Ponto res = aestrela(origem, dao.getInstance(new GeoWrapper(new Geo("ChIJ79zqVCXI45QRg2nZd86s3A4"))), entregas);
//		Ponto res = blob(origem, entregas);
//		Ponto res = cristofides(origem, entregas);
//		System.out.println("--------------------caminho-----------------");
//		Ponto atual = res;
//		System.out.println(atual);
//		LinkedList<GeoWrapper> ways = new LinkedList<>();
//		while (atual != null) {
//			ways.add(atual.getGeo());
//			atual = atual.getCaminho();
//		}
//		entregas.add(origem);
//		System.out.println(origem.getCidade() + " - " + origem.toLatLng());
//		entregas.stream().forEach((e) -> {
//			System.out.println(e.getCidade() + " - " + e.toLatLng());
//		});
//		List<Vertice> greedy = Greedy.run(entregas);
//		System.out.println("--------------------greedy-----------------");
//		System.out.println(greedy);
//		entregas.add(origem);
//		LinkedList<GeoWrapper> ways = new LinkedList<>();
//		Ponto ga = greedy.get(0).getA();
//		Ponto gat = greedy.get(0).getB();
//		ways.add(ga.getGeo());
//		for (int i = 0; i < greedy.size(); i++) {
//			ways.add(gat.getGeo());
//			gat = gat.getVerticesSaindo().get(0).getB();
//		}
//		List<Vertice> christofides = Christofides.run(entregas);
//		System.out.println("--------------------christofides-----------------");
//		System.out.println(christofides);
//
//		List<GeoWrapper> ways = new LinkedList<>();
//		christofides.stream().forEach((c) -> {
//			ways.add(c.getA().getGeo());
//		});
//		String htm = setWaypoints(origem, ways, true);
//		System.out.println("---------------------ways------------------");
//		System.out.println(ways);
//		String htm = setMultiRotas(ways);
//		pagina.loadContent(htm);
//		hbox.getChildren().add(webView);
		main.getChildren().add(hbox);

//		try {
//			GeoService.getDirecoes(origem, origem, ways);
//		} catch (UnsupportedEncodingException ex) {
//			Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (IOException ex) {
//			Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}

	private String setWaypoints(GeoWrapper origem, LinkedList<GeoWrapper> ways, boolean otimizar) {
		String htm = "";
		String ww = "";
		try {
			htm = Files.toString(new File("src/paginas/dir.html"), Charset.defaultCharset());
			htm = htm.replace("@@origem", "\"" + origem.toLatLng() + "\"");

			if (ways.size() > 0) {
				ww = "waypoints: [";
				for (GeoWrapper w : ways) {
					ww += "{location: '" + w.toLatLng() + "'},";
				}
				ww = ww.substring(0, ww.length() - 1);
				ww += "],";
				if (otimizar) {
					ww += "\n\t\t\t\toptimizeWaypoints: true,";
				}
			}
			htm = htm.replace("@@waypoints", ww);

			// waypoints: [{location: 'Rodoviária, Campinas'}, {location: 'Taquaral, Campinas'}],
			htm = htm.replace("@@destino", "\"" + origem.toLatLng() + "\"");

			System.out.println(htm);
		} catch (IOException ex) {
			Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
		}
		return htm;
	}

	private String setMultiRotas(List<GeoWrapper> ways) {
		String htm;
		try {
			htm = Files.toString(new File("src/paginas/multidir.html"), Charset.defaultCharset());
		} catch (IOException ex) {
			Logger.getLogger(MainScene.class.getName()).log(Level.SEVERE, null, ex);
			return "";
		}
		String ww = "";
		int i = 0;
		int contagem = 0;
		Iterator<GeoWrapper> it = ways.iterator();
		String rota = "";

		GeoWrapper origem = ways.get(0);
		GeoWrapper ultimo = null;
		GeoWrapper atual = null;
		while (it.hasNext()) {
			ultimo = atual;
			atual = it.next();
			if (contagem % 7 == 0 && contagem != 0) {
				contagem = 0;
				rota = rota.substring(0, rota.length() - 1) + "],\n";
				ww += rota;
				i++;
			}
			if (contagem == 0) {
				//rota vazia
				rota = "\"r" + i + "\": [";
				//se nao for a primeira rota e ainda existirem cidades pra passar
				if (ultimo != null) {
					//comeca pelo ultimo da ultima volta
					rota += "\'" + ultimo.toLatLng() + "\',";
					ultimo = null;
				}
			}
			rota += "\'" + atual.toLatLng() + "\',";
			contagem++;
		}
		rota += "\'" + origem.toLatLng() + "\'";
		ww += rota + "]\n";
		htm = htm.replace("@@rotas", ww);
		System.out.println(htm);
		return htm;
	}
}
