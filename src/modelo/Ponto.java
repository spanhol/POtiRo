package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Ponto {

	Double lat;
	Double lng;
	Double distanciaGoogle;
	Double distancia = Double.MAX_VALUE;
	Double distanciaDestino = Double.MAX_VALUE;
	Double valor = 0d;
	GeoWrapper geo;
	Ponto caminho;
	boolean origem = false;
	boolean destino = false;
	boolean visitado = false;
	List<Vertice> verticesEntrando;
	List<Vertice> verticesSaindo;

	public Ponto() {
		verticesEntrando = new ArrayList<>();
		verticesSaindo = new ArrayList<>();
	}

	public Ponto(GeoWrapper geo) {
		verticesEntrando = new ArrayList<>();
		verticesSaindo = new ArrayList<>();
		this.geo = geo;
		lat = geo.getLat();
		lng = geo.getLng();
	}

	/**
	 * distancia entre dois pontos <BR>
	 *
	 * distancia = raiz do quadrado das diferencas entre as coordenadas
	 *
	 * @param p
	 * @return
	 */
	public Double distanciaAte(Ponto p) {
		distanciaGoogle = Vertice.getDistancia(this, p);
		if (distanciaGoogle != null){
			return distanciaGoogle;
		}
		return Math.sqrt((Math.pow((lat - p.getLat()), 2) + Math.pow((lng - p.getLng()), 2)));
	}

	public boolean isOrigem() {
		return origem;
	}

	public void setOrigem(boolean origem) {
		this.origem = origem;
	}

	public boolean isDestino() {
		return destino;
	}

	public void setDestino(boolean destino) {
		this.destino = destino;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Ponto getCaminho() {
		return caminho;
	}

	public void setCaminho(Ponto caminho) {
		this.caminho = caminho;
	}

	public GeoWrapper getGeo() {
		return geo;
	}

	public void setGeo(GeoWrapper geo) {
		this.geo = geo;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Double getDistanciaDestino() {
		return distanciaDestino;
	}

	public void setDistanciaDestino(Double distanciaDestino) {
		this.distanciaDestino = distanciaDestino;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<Vertice> getVerticesEntrando() {
		return verticesEntrando;
	}

	public void setVerticesEntrando(List<Vertice> vertices) {
		this.verticesEntrando = vertices;
	}

	public List<Vertice> getVerticesSaindo() {
		return verticesSaindo;
	}

	public void setVerticesSaindo(List<Vertice> verticesSaindo) {
		this.verticesSaindo = verticesSaindo;
	}

//	@Override
//	public String toString() {
//		return "Ponto{geo=" + geo + ", \ncaminho=" + caminho + '}';
//	}
	@Override
	public String toString() {
		return geo.toString();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.lat);
		hash = 23 * hash + Objects.hashCode(this.lng);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Ponto other = (Ponto) obj;
		if (!Objects.equals(this.lat, other.lat)) {
			return false;
		}
		if (!Objects.equals(this.lng, other.lng)) {
			return false;
		}
		return true;
	}
}
