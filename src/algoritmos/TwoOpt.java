package algoritmos;

import java.util.LinkedList;
import modelo.Ponto;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class TwoOpt {

	LinkedList<Ponto> waypoints;
	Ponto origem;

	/**
	 *
	 * @param origem ponto de origem
	 * @param entregas pontos entre a origem e o destino
	 */
	public TwoOpt(GeoWrapper origem, LinkedList<GeoWrapper> entregas) {
		waypoints = new LinkedList<>();

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
			way.setCaminho(atual);
			atual = way;
		}
		return atual;
	}
}
