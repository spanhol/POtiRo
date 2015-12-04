package algoritmos;

import modelo.Vertice;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import modelo.Ponto;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Greedy {

	public static final List<Vertice> run(LinkedList<GeoWrapper> entregas) {

		LinkedList<Ponto> waypoints = new LinkedList<>();
		//adiciona destino aos waypoints para que faça parte das buscas
		entregas.stream().forEach((entrega) -> {
			waypoints.add(new Ponto(entrega));
		});
		Greedy c = new Greedy();
		return c.executar(waypoints);
	}

	private Greedy() {
	}

	public List<Vertice> executar(List<Ponto> inativos) {
		List<Vertice> resposta = new ArrayList<>();
		List<Ponto> ativos = new ArrayList<>();
		ativos.add(inativos.get(0));
		Vertice near;

		//init
		Vertice loop = new Vertice(inativos.get(0), inativos.get(0));
		resposta.add(loop);
		while (!inativos.isEmpty()) {
			//calcula a distancia da origem para todos os outros pontos
			near = maisProximo(ativos, inativos);
			System.out.println(near);
			if (near != null) {
				for (Vertice out : near.getA().getVerticesSaindo()) {
					if (!out.equals(near)) {
						System.out.println("out.A = " + out.getA());
						System.out.println("near.B = " + near.getB());
						out.setA(near.getB());
					}
				}
				ativos.add(near.getB());
				resposta.add(near);
			}
			inativos.remove(near.getB());
		}
		return resposta;
	}

	public Vertice maisProximo(List<Ponto> ativos, List<Ponto> inativos) {
		Ponto proximo = null;
		Ponto proxy = null;
		Double distancia = Double.MAX_VALUE;
		for (Ponto ativ : ativos) {
			for (Ponto inat : inativos) {
				Double distanciaAtual = ativ.distanciaAte(inat);
				if (distanciaAtual < distancia) {
					distancia = distanciaAtual;
					proxy = ativ;
					proximo = inat;
				}
			}
		}
		return new Vertice(proxy, proximo);
	}
}
