package wrapper;

import dao.DistanciaDao;
import entidades.Distancia;
import entidades.DistanciaPK;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.Vertice;

/**
 *
 * @author Fernando Spanhol
 */
public class DistanciaWrapper extends Wrapper<Distancia> {

	private final StringProperty placeA = new SimpleStringProperty();
	private final StringProperty placeB = new SimpleStringProperty();
	private final DoubleProperty distancia = new SimpleDoubleProperty();

	private void init(Distancia elemento) {
		this.elemento = elemento;
		if (elemento == null) {
			return;
		}
		placeA.set(elemento.getDistanciaPK().getPlacea());
		placeB.set(elemento.getDistanciaPK().getPlaceb());
		distancia.set(elemento.getDistancia());
	}

	public DistanciaWrapper() {
		Distancia e = new Distancia();
		init(e);
	}

	public DistanciaWrapper(Distancia e) {
		init(e);
	}

	public DistanciaWrapper(Vertice v) {
		DistanciaDao dao = new DistanciaDao();
		DistanciaPK pk = new DistanciaPK(v.getA().getGeo().getPlaceId(), v.getB().getGeo().getPlaceId());
		Distancia e = new Distancia(pk);
		e = dao.getById(e.getDistanciaPK()).get();
		init(e);
	}

	public String getPlaceA() {
		return placeA.get();
	}

	public void setPlaceA(String value) {
		placeA.set(value);
	}

	public StringProperty placeAProperty() {
		return placeA;
	}

	public String getPlaceB() {
		return placeB.get();
	}

	public void setPlaceB(String value) {
		placeB.set(value);
	}

	public StringProperty placeBProperty() {
		return placeB;
	}

	public double getDistancia() {
		return distancia.get();
	}

	public void setDistancia(double value) {
		distancia.set(value);
	}

	public DoubleProperty distanciaProperty() {
		return distancia;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DistanciaWrapper other = (DistanciaWrapper) obj;
		if (!this.placeA.equals(other.placeA)) {
			return false;
		}
		if (!this.placeB.equals(other.placeB)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + Objects.hashCode(this.placeA);
		hash = 71 * hash + Objects.hashCode(this.placeB);
		return hash;
	}

	@Override
	public String toString() {
		return "{" + placeA + " - " + placeB + " = " + distancia + '}';
	}
}
