package algoritmos;

import java.util.LinkedList;
import modelo.Ponto;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Blob {

	LinkedList<Ponto> waypoints;
	LinkedList<Ponto> u;
	LinkedList<Ponto> d;
	Ponto origem;
	Ponto distante;

	public Blob(GeoWrapper origem, LinkedList<GeoWrapper> entregas) {
		waypoints = new LinkedList<>();
		u = new LinkedList<>();
		d = new LinkedList<>();

		this.origem = new Ponto(origem);
		this.origem.setOrigem(true);
//		this.origem.setCaminho(this.origem);
		entregas.stream().forEach((entrega) -> {
			waypoints.add(new Ponto(entrega));
		});
	}

	public Ponto run() {
		Ponto atual = origem;
		//ponto mais proximo
		//calcula a distancia da origem para todos os outros pontos
		for (Ponto way : waypoints) {
//			way.setCaminho(atual);
			way.setDistancia(atual.distanciaAte(way));
			atual = way;
		}

		distante = null;
		waypoints.stream().filter((w) -> (distante == null || distante.getDistancia() < w.getDistancia())).forEach((w) -> {
			distante = w;
		});
		if (distante == null) {
			return null;
		} else {
			System.out.println("distante");
			System.out.println(distante);
			for (Ponto way : waypoints) {
				if (estaAcima(way)) {
					u.add(way);
				} else {
					d.add(way);
				}
			}
			System.out.println("u");
			System.out.println(u);
			System.out.println("d");
			System.out.println(d);
			//remove distante das listas
			//roda a* de origem até distante na lista u
			//roda a* de origem até distante na lista d

//			return visit(atual);
			return null;
		}
	}

//	public Ponto visit(Ponto atual) {
//
//		return visit(null);
//	}
	/**
	 * interpolacao entre origem e ponto distante para saber em que lado o ponto
	 * P se encontra
	 *
	 * @param p
	 * @return
	 */
	public boolean estaAcima(Ponto p) {
		//se negativo \. se positivo /.
		double coeficienteAngular = (p.getLng() - origem.getLng()) / (distante.getLat() - origem.getLat());
		double latIterpolada;
		double lngIterpolada;

		lngIterpolada = distante.getLng() + (p.getLat() - origem.getLat()) / (distante.getLat() - origem.getLat()) * (distante.getLng() - origem.getLng());
		latIterpolada = distante.getLat() + (p.getLng() - origem.getLng()) / (distante.getLng() - origem.getLng()) * (distante.getLat() - origem.getLat());
		if (coeficienteAngular > 0) {
			if (latIterpolada < p.getLat()) {
				if (lngIterpolada > p.getLng()) {
					return true;
				}
			}
		} else {
			if (latIterpolada > p.getLat()) {
				if (lngIterpolada > p.getLng()) {
					return true;
				}
			}
		}
		return false;
	}
}
