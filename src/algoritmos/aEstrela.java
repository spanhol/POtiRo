package algoritmos;

import java.util.LinkedList;
import modelo.Ponto;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class aEstrela {

	LinkedList<Ponto> waypoints;
	LinkedList<Ponto> visitados;
	Ponto origem;
	Ponto destino;

	/**
	 *
	 * @param origem ponto de origem
	 * @param destino ponto de destino
	 * @param entregas pontos entre a origem e o destino
	 */
	public aEstrela(GeoWrapper origem, GeoWrapper destino, LinkedList<GeoWrapper> entregas) {
		visitados = new LinkedList<>();
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

	/**
	 * set origem encontra o Ponto mais proximo para comecar a busca, encontra a
	 * distancia de cada ponto até o destino
	 *
	 * @return
	 */
	public Ponto run() {
		Ponto atual = origem;
		//ponto mais proximo
		Ponto nearest = new Ponto();
		nearest.setDistancia(Double.MAX_VALUE);

		//calcula a distancia da origem para todos os outros pontos
		for (Ponto way : waypoints) {
			Double distanciaAtual = atual.distanciaAte(way);
			if (distanciaAtual < way.getDistancia()) {
				way.setDistancia(distanciaAtual);
				way.setCaminho(atual);
			}
			if (distanciaAtual < nearest.getDistancia()) {
				nearest = way;
				nearest.setCaminho(atual);
			}
			way.setDistanciaDestino(way.distanciaAte(destino));
		}
		nearest.setVisitado(true);
		if (nearest.isDestino()) {
			return nearest;
		} else {
			return traverse(nearest);
		}
	}

	public Ponto traverse(Ponto atual) {
//		System.out.println(atual);
		//ponto mais proximo
		Ponto nearest = new Ponto();
		nearest.setDistancia(Double.MAX_VALUE);

		for (Ponto way : waypoints) {
			if (!way.isVisitado()) {
				Double distanciaAtual = atual.distanciaAte(way);
				//se a distancia for o menor caminho até aquele ponto, atualiza
				if (distanciaAtual < way.getDistancia()) {
					way.setDistancia(distanciaAtual);
					way.setCaminho(atual);
				}
				//armazena o ponto com a menor distancia
				if (distanciaAtual < nearest.getDistancia()) {
					nearest = way;
				}
			}
		}

		nearest.setVisitado(true);
		if (nearest.isDestino()) {
			return nearest;
		} else {
			return traverse(nearest);
		}
	}
}
