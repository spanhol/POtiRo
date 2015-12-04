package algoritmos;

import modelo.Vertice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import modelo.Ponto;
import wrapper.GeoWrapper;

public class Christofides {

	boolean VERBOSE = false;

	private Christofides() {
	}

	public static final List<Vertice> run(List<GeoWrapper> entregas) {
		Christofides c = new Christofides();
		return c.eulerCircuit(c.match(c.prim(entregas)));
//		return c.eulerCircuit(c.duplicacao(c.prim(entregas)));
	}

	private List<Vertice> prim(List<GeoWrapper> cidades) {
		List<Ponto> waypoints = new ArrayList<>();
		cidades.stream().forEach((entrega) -> {
			waypoints.add(new Ponto(entrega));
		});
		if (waypoints.isEmpty()) {
			return null;
		}
		ArrayList<Vertice> verti = new ArrayList<>();
		ArrayList<Ponto> ListaAtual = new ArrayList<>();
		ListaAtual.add(waypoints.get(0));
		waypoints.get(0).setVisitado(true);
		do {
			Ponto proximo = null;
			Ponto proxy = null;
			Double distancia = Double.MAX_VALUE;
			for (Ponto atual : ListaAtual) {
				for (Ponto e : waypoints) {
					if (!e.isVisitado()) {
						Double dist = e.distanciaAte(atual);
						if (dist < distancia) {
							distancia = dist;
							proximo = e;
							proxy = atual;
						}
					}
				}
			}
			if (proximo != null) {
				ListaAtual.add(proximo);
				verti.add(new Vertice(proxy, proximo));
				proximo.setVisitado(true);
			}
		} while (ListaAtual.size() != waypoints.size());
		if (VERBOSE) {
			System.out.println("----------------prims----------------");
			System.out.println(verti);
		}
		return verti;
	}

	private List<Vertice> match(List<Vertice> vertices) {
		List<Ponto> odd = new ArrayList<>();
		/**
		 * enquanto houverem pontos com vertices impares procura melhor match e
		 * adiciona a lista de vertices.
		 */
		do {
			odd = getOdds(vertices);
			//reset vertices
			for (Ponto o : odd) {
				o.getVerticesEntrando().clear();
				o.getVerticesSaindo().clear();
			}
			Vertice best = getBestMatch(odd);

			if (best != null) {
				vertices.add(best);
			}
			if (VERBOSE) {
				System.out.println("mais perto = " + best);
				System.out.println("vertices");
				System.out.println(vertices);
			}
		} while (odd.size() > 0);

		return vertices;
	}

	public List<Ponto> getOdds(List<Vertice> vertices) {
		HashMap<Ponto, Integer> pares = new HashMap<>();
		List<Ponto> odd = new ArrayList<>();
		for (Vertice v : vertices) {
			if (pares.get(v.getA()) != null) {
				pares.put(v.getA(), pares.get(v.getA()) + 1);
			} else {
				pares.put(v.getA(), 1);
			}
			if (pares.get(v.getB()) != null) {
				pares.put(v.getB(), pares.get(v.getB()) + 1);
			} else {
				pares.put(v.getB(), 1);
			}
		}
		//adicionas ocorencias impares a lista
		pares.keySet().stream().filter((key) -> (pares.get(key) % 2 > 0)).forEach((key) -> {
			odd.add(key);
		});
		if (VERBOSE) {
			System.out.println("----odd");
			System.out.println(odd);
		}
		return odd;
	}

	public Vertice getBestMatch(List<Ponto> odd) {
		List<Vertice> match = new LinkedList<>();
		//enconta os pares de pontos mais proximo na lista dos impares
		for (Ponto o : odd) {
			o.getVerticesEntrando().clear();
			o.getVerticesSaindo().clear();
		}
		for (Ponto o : odd) {
			Double distanciaMenor = Double.MAX_VALUE;
			Ponto proximo = null;
			Ponto proxy = null;
			for (Ponto o2 : odd) {
				if (!o.equals(o2)) {
					Double distancia = o.distanciaAte(o2);
					if (distancia < distanciaMenor) {
						distanciaMenor = distancia;
						proximo = o2;
						proxy = o;
					}
				}
			}
			Vertice v = new Vertice(proxy, proximo);
//			proximo.getVerticesEntrando().add(new Vertice(proxy, proximo));
//			o.getVerticesSaindo().add(new Vertice(proxy, proximo));
			if (VERBOSE) {
				System.out.println("close = " + v);
			}
		}

		/**
		 * para cada ponto.<br>
		 * se destino mais proximo de somente um ponto entao é o melhor
		 * match.<br>
		 * se destino mais proximo de mais de um ponto entao seleciona o mais
		 * distante deles como o melhor match.
		 *
		 */
		for (Ponto o : odd) {
			if (VERBOSE) {
				System.out.println("--------------");
				System.out.println(o);
				System.out.println("in = " + o.getVerticesEntrando().size() + " => " + o.getVerticesEntrando());
				System.out.println("out = " + o.getVerticesSaindo().size() + " => " + o.getVerticesSaindo());
			}
			if (o.getVerticesSaindo().size() == 1) {
				if (o.getVerticesEntrando().isEmpty()) {
					match.add(o.getVerticesSaindo().get(0));
					if (VERBOSE) {
						System.out.println("match");
					}
				} else if (o.getVerticesEntrando().size() == 1) {
					if (o.getVerticesEntrando().size() == 1 && o.getVerticesSaindo().size() == 1) {
						Ponto b = o.getVerticesEntrando().get(0).getA();
						if (b.getVerticesEntrando().size() == 1 && b.getVerticesSaindo().size() == 1) {
							if (o.getVerticesEntrando().get(0).reverso(o.getVerticesSaindo().get(0))) {
								match.add(o.getVerticesEntrando().get(0));
								if (VERBOSE) {
									System.out.println("<->");
									System.out.println("match");
								}
							}
						}
					}
				}
//				else {
//					boolean retorno = false;
//					for (Vertice ret : o.getVerticesEntrando().get(0).getA().getVerticesEntrando()) {
//						if (ret.getA().equals(o)) {
//							retorno = true;
//							break;
//						}
//					}
//					System.out.println("retorno = " + retorno);
//					if (!retorno) {
//						match.add(o.getVerticesEntrando().get(0));
//						System.out.println("match");
//					}
//				}
			}
//			else if (o.getVertices().size() > 1) {
//				System.out.println("> 1 " + o.getVertices());
//				System.out.println("--");
//				Double dist = 0d;
//				Vertice maisLonge = null;
//				for (Vertice v : o.getVertices()) {
//					Double d = v.getA().distanciaAte(v.getB());
//					if (d > dist) {
//						dist = d;
//						maisLonge = v;
//					}
//				}
//				if (maisLonge == null) {
//					System.out.println("ERRO-------");
//				} else {
//					System.out.println("mais longe = " + maisLonge);
//					match.add(maisLonge);
//				}
//			}
		}
		Double dist = Double.MAX_VALUE;
		Vertice maisPerto = null;
		for (Vertice v : match) {
			Double d = v.getA().distanciaAte(v.getB());
			if (d < dist) {
				dist = d;
				maisPerto = v;
			}
		}
		if (VERBOSE) {
			System.out.println("mid match");
			System.out.println(match);
		}
		return maisPerto;
	}

	private List<Vertice> duplicacao(List<Vertice> vertices) {
		List<Vertice> duplicados = new ArrayList<>();
		Vertice reverse;
		for (Vertice v : vertices) {
			reverse = new Vertice(v.getB(), v.getA());
			if (!duplicados.contains(reverse)) {
				duplicados.add(reverse);
			}
		}
		duplicados.addAll(vertices);
		if (VERBOSE) {
			System.out.println("--------duplicacao--------------");
			System.out.println(duplicados);
		}
		return duplicados;
	}

	private List<Vertice> eulerCircuit(List<Vertice> vertices) {
		if (VERBOSE) {
			System.out.println("---------------Todos os vertices----------------");
			System.out.println(vertices);
		}
		for (Vertice v : vertices) {
			v.getA().setVisitado(false);
			v.getB().setVisitado(false);
		}
		if (VERBOSE) {
			System.out.println("\n---------------------------Euler-----------------------------");
		}
		List<Vertice> circuit = new ArrayList<>();

		//origem aleatoria
		Ponto origem = vertices.get(0).getA();
		Vertice atual = vertices.get(0);
		if (VERBOSE) {
			System.out.println("origem = " + origem);
			System.out.println("atual = " + atual);
		}
		//remove vertice
		vertices.remove(atual);

		Ponto a = origem;
		Ponto b = null;
		Ponto de = origem;
		origem.setVisitado(true);

		while (atual != null) {
			if (a == null) {
				//reverte vertice
				a = b;
				b = atual.getA();
			} else { //if b == null
				b = atual.getB();
			}

			//se Ponto B ainda nao foi visitado, salva aresta
			if (!b.isVisitado()) {
				circuit.add(new Vertice(de, b));
				if (VERBOSE) {
					System.out.println("add " + new Vertice(de, b) + "\n");
				}
				//nao precisamos mais conciderar este ponto
				b.setVisitado(true);
				//B é o inicio do proximo vertice
				de = b;
			} else {
				if (VERBOSE) {
					System.out.println("ja VISITADO " + b);
				}
			}
			//procurar proxima aresta para seguir
			atual = null;
			//vamos encontrar um vertice que contenha B
			if (VERBOSE) {
				System.out.println("procurando por: " + b);
			}
			for (Vertice v : vertices) {
				//se o vertice comeca no Ponto B
				if (!v.getA().isVisitado() && v.getB().equals(b)) {
					if (VERBOSE) {
						System.out.println("vertice " + v);
					}
					//marca para remocao
					//atualiza atual
					atual = v;
					a = null;	//sinaliza para reverter vertice
					b = v.getB();
					break;
				} else if (!v.getB().isVisitado() && v.getA().equals(b)) {
					if (VERBOSE) {
						System.out.println("vertice " + v);
					}
					//marca para remocao
					//atualiza atual
					atual = v;
					b = null;
					a = v.getA();
					break;
				}
			}
			if (atual == null) {
				for (Vertice v : vertices) {
					if (v.getB().equals(b)) {
						if (VERBOSE) {
							if (!v.getB().isVisitado()) {
								System.out.println("B not visitado");
							} else {
								System.out.println("B visitado " + v);
							}
						}
						//marca para remocao
						//atualiza atual
						atual = v;
						a = null;	//sinaliza para reverter vertice
						b = v.getB();
						break;
					} else if (v.getA().equals(b)) {
						if (VERBOSE) {
							if (!v.getA().isVisitado()) {
								System.out.println("A not visitado");
							} else {
								System.out.println("A visitado " + v);
							}
						}
						//marca para remocao
						//atualiza atual
						atual = v;
						b = null;
						a = v.getA();
						break;
					}
				}
			}
			if (atual != null) {
				vertices.remove(atual);
			} else {
				if (VERBOSE) {
					System.out.println(vertices);
				}
				if (b.equals(origem)) {
					circuit.add(new Vertice(de, origem));
				}
			}

			if (VERBOSE) {
				System.out.println("faltam " + vertices.size() + " vertices");
			}
		}
		return circuit;
	}
}
