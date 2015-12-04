package wrapper;

import entidades.Cliente;
import entidades.Distancia;
import entidades.Geo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fernando Spanhol
 */
public class GeoWrapper extends Wrapper<Geo> {

	private final StringProperty placeId = new SimpleStringProperty();
	private final DoubleProperty lat = new SimpleDoubleProperty();
	private final DoubleProperty lng = new SimpleDoubleProperty();
	private final DoubleProperty northeastlat = new SimpleDoubleProperty();
	private final DoubleProperty northeastlng = new SimpleDoubleProperty();
	private final DoubleProperty southwestlat = new SimpleDoubleProperty();
	private final DoubleProperty southwestlng = new SimpleDoubleProperty();
	private final IntegerProperty cep = new SimpleIntegerProperty();
	private final StringProperty cidade = new SimpleStringProperty();
	private final StringProperty estado = new SimpleStringProperty();
	private final StringProperty bairro = new SimpleStringProperty();
	private final StringProperty rua = new SimpleStringProperty();
	private final StringProperty numero = new SimpleStringProperty();
	private final ObjectProperty<Cliente> cliente = new SimpleObjectProperty<>();
	private final StringProperty locationType = new SimpleStringProperty();
	private final ObjectProperty<Distancia> distancia = new SimpleObjectProperty<>();

	private void init(Geo elemento) {
		this.elemento = elemento;
		if (elemento == null) {
			return;
		}
		placeId.setValue(elemento.getPlaceId());
		lat.setValue(elemento.getLat());
		lng.setValue(elemento.getLng());
		northeastlat.setValue(elemento.getNortheastlat());
		northeastlng.setValue(elemento.getNortheastlng());
		southwestlat.setValue(elemento.getSouthwestlat());
		southwestlng.setValue(elemento.getSouthwestlng());
		cep.setValue(elemento.getCep());
		cidade.setValue(elemento.getCidade());
		estado.setValue(elemento.getEstado());
		bairro.setValue(elemento.getBairro());
		rua.setValue(elemento.getRua());
		numero.setValue(elemento.getNumero());
		cliente.setValue(elemento.getCodigoCli());
	}

	public GeoWrapper() {
		Geo e = new Geo();
		init(e);
	}

	public GeoWrapper(Geo e) {
		init(e);
	}

	public String getPlaceId() {
		return placeId.get();
	}

	public void setPlaceId(String value) {
		elemento.setPlaceId(value);
		placeId.set(value);
	}

	public StringProperty placeIdProperty() {
		return placeId;
	}

	public double getLat() {
		return lat.get();
	}

	public void setLat(double value) {
		elemento.setLat(value);
		lat.set(value);
	}

	public DoubleProperty latProperty() {
		return lat;
	}

	public double getLng() {
		return lng.get();
	}

	public void setLng(double value) {
		elemento.setLng(value);
		lng.set(value);
	}

	public DoubleProperty lngProperty() {
		return lng;
	}

	public Double getNortheastlat() {
		return northeastlat.get();
	}

	public void setNortheastlat(Double value) {
		elemento.setNortheastlat(value);
		northeastlat.set(value);
	}

	public DoubleProperty northeastlatProperty() {
		return northeastlat;
	}

	public double getNortheastlng() {
		return northeastlng.get();
	}

	public void setNortheastlng(double value) {

		elemento.setNortheastlng(value);
		northeastlng.set(value);
	}

	public DoubleProperty northeastlngProperty() {
		return northeastlng;
	}

	public Double getSouthwestlat() {
		return southwestlat.get();
	}

	public void setSouthwestlat(Double value) {
		elemento.setSouthwestlat(value);
		southwestlat.set(value);
	}

	public DoubleProperty SouthwestlatProperty() {
		return southwestlat;
	}

	public double getSouthwestlng() {
		return southwestlng.get();
	}

	public void setSouthwestlng(double value) {
		elemento.setSouthwestlng(value);
		southwestlng.set(value);
	}

	public DoubleProperty SouthwestlngProperty() {
		return southwestlng;
	}

	public int getCep() {
		return cep.get();
	}

	public void setCep(int value) {
		elemento.setCep(value);
		cep.set(value);
	}

	public IntegerProperty cepProperty() {
		return cep;
	}

	public String getCidade() {
		return cidade.get();
	}

	public void setCidade(String value) {

		elemento.setCidade(value);
		cidade.set(value);
	}

	public StringProperty cidadeProperty() {
		return cidade;
	}

	public String getEstado() {
		return estado.get();
	}

	public void setEstado(String value) {
		elemento.setEstado(value);
		estado.set(value);
	}

	public StringProperty estadoProperty() {
		return estado;
	}

	public String getBairro() {
		return bairro.get();
	}

	public void setBairro(String value) {
		elemento.setBairro(value);
		bairro.set(value);
	}

	public StringProperty bairroProperty() {
		return bairro;
	}

	public String getRua() {
		return rua.get();
	}

	public void setRua(String value) {
		elemento.setRua(value);
		rua.set(value);
	}

	public StringProperty ruaProperty() {
		return rua;
	}

	public void setLocationType(String value) {
		elemento.setLocationType(value);
		locationType.set(value);
	}

	public StringProperty locationTypeProperty() {
		return locationType;
	}

	public void setNumero(String value) {
		elemento.setNumero(value);
		numero.set(value);
	}

	public StringProperty numeroProperty() {
		return numero;
	}

	public void setCliente(Cliente value) {
		elemento.setCodigoCli(value);
		cliente.set(value);
	}

	public ObjectProperty clienteProperty() {
		return cliente;
	}

	public Distancia getDistancia() {
		return distancia.get();
	}

	public void setDistancia(Distancia value) {
		distancia.set(value);
	}

	public ObjectProperty distanciaProperty() {
		return distancia;
	}

	/*
	 &markers=color:blue%7Clabel:S%7C-25.4279452,-49.24829219999999
	 */
	public String getMarker() {
		return "&markers=color:red%7C" + toLatLng();
	}

	public String getMarker(String color) {
		return "&markers=color:" + color + "%7C" + toLatLng();
	}

	@Override
	public String toString() {
		return "geo{" + "lat=" + lat.get() + ", lng=" + lng.get() + ", cidade=" + cidade.get() + '}';
	}

	public String toLatLng() {
		return lat.get() + "," + lng.get();
	}

	public String toPlaceId() {
		return "place_id:" + placeId.get();
	}
}
