package algoritmos;

import java.util.LinkedList;
import modelo.Ponto;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Ant {

	LinkedList<Ponto> waypoints;
	Ponto origem;
	Ponto destino;

	/**
	 *
	 * @param origem ponto de origem
	 * @param destino ponto de destino
	 * @param entregas pontos entre a origem e o destino
	 */
	public Ant(GeoWrapper origem, GeoWrapper destino, LinkedList<GeoWrapper> entregas) {
		waypoints = new LinkedList<>();

		this.origem = new Ponto(origem);
		this.origem.setOrigem(true);
//		this.origem.setCaminho(this.origem);
		this.destino = new Ponto(destino);
		this.destino.setDestino(true);
		//adiciona destino aos waypoints para que faça parte das buscas
		waypoints.add(this.destino);
		entregas.stream().forEach((entrega) -> {
			waypoints.add(new Ponto(entrega));
		});
	}

	public Ponto menorDistancia() {
		Ponto near = null;
		Double distancia = Double.MAX_VALUE;
		for (Ponto w : waypoints) {
			if (w.getDistancia() < distancia) {
				near = w;
			}
		}
		return near;
	}
}
