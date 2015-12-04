package modelo;

import dao.DistanciaDao;
import entidades.Distancia;
import entidades.DistanciaPK;
import java.util.Objects;
import wrapper.DistanciaWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class Vertice {

	Ponto a;
	Ponto b;

	public Vertice(Ponto a, Ponto b) {
		this.a = a;
		this.b = b;
		if (!a.getVerticesSaindo().contains(this)) {
			this.a.getVerticesSaindo().add(this);
		}
		if (!b.getVerticesEntrando().contains(this)) {
			this.b.getVerticesEntrando().add(this);
		}
	}

	public Ponto getA() {
		return a;
	}

	public void setA(Ponto a) {
		this.a.getVerticesSaindo().remove(this);
		this.a = a;
		if (!a.getVerticesSaindo().contains(this)) {
			this.a.getVerticesSaindo().add(this);
		}
	}

	public Ponto getB() {
		return b;
	}

	public void setB(Ponto b) {
		this.b.getVerticesEntrando().remove(this);
		this.b = b;
		if (!b.getVerticesEntrando().contains(this)) {
			this.b.getVerticesEntrando().add(this);
		}
	}

	public Double getTamanho() {
		return a.distanciaAte(b);
	}

	@Override
	public String toString() {
		return a.getGeo().getCidade() + " => " + b.getGeo().getCidade() + "\n";
	}

	@Override
	public int hashCode() {
		int hash = 3;
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
		final Vertice other = (Vertice) obj;
		if (!Objects.equals(this.a, other.a)) {
			return false;
		}
		if (!Objects.equals(this.b, other.b)) {
			return false;
		}
		return true;
	}

	public boolean reverso(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Vertice other = (Vertice) obj;
		if (!Objects.equals(this.a, other.b)) {
			return false;
		}
		if (!Objects.equals(this.b, other.a)) {
			return false;
		}
		return true;
	}

	public static Double getDistancia(Ponto a, Ponto b) {
		DistanciaDao dao = new DistanciaDao();
		DistanciaWrapper dw = dao.getById(new DistanciaPK(a.getGeo().getPlaceId(), b.getGeo().getPlaceId()));
		if (dw == null) {
			//TODO
			//se nao encontrar pedir direcoes ao google e salvar no banco

			if (true) {
				return null;
			} else {
				return null;
			}
		} else {
			return dw.getDistancia();
		}

	}

}
