package wrapper;

import entidades.Caminhao;
import entidades.Custocaminhao;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.CustomDate;

/**
 *
 * @author Fernando Spanhol
 */
public class CustoCaminhaoWrapper extends Wrapper<Custocaminhao> {


	private final SimpleBooleanProperty selected;

	private final IntegerProperty id;
	private final ObjectProperty<CustomDate> data;
	private final ObjectProperty<Caminhao> caminhao;
	private final DoubleProperty rastreador;
	private final DoubleProperty seguro;
	private final DoubleProperty ipva;
	private final DoubleProperty salario;
	private final DoubleProperty depreciacao;
	private final DoubleProperty multa;
	private final DoubleProperty oficina;
	private final DoubleProperty pedagio;
	private final DoubleProperty financiamento;
	private final DoubleProperty total;
	private final DoubleProperty medicina;
	private final BooleanProperty firme;

	public CustoCaminhaoWrapper(Custocaminhao elemento) {
		if (elemento == null) {
			this.elemento = new Custocaminhao(-1);
			this.elemento.setData(Calendar.getInstance().getTime());
			this.elemento.setDepreciacao(0d);
			this.elemento.setFinanciamento(0d);
			this.elemento.setFirme(false);
			this.elemento.setId(-1);
			this.elemento.setIpva(0d);
			this.elemento.setMulta(0d);
			this.elemento.setOficina(0d);
			this.elemento.setPedagio(0d);
			this.elemento.setRastreador(0d);
			this.elemento.setSalario(0d);
			this.elemento.setSeguro(0d);
			this.elemento.setMedicina(0d);
		} else {
			this.elemento = elemento;
		}

		selected = new SimpleBooleanProperty(false);
		id = new SimpleIntegerProperty(this.elemento.getId());
		data = new SimpleObjectProperty<>(new CustomDate(this.elemento.getData().getTime()));
		caminhao = new SimpleObjectProperty<>(this.elemento.getIdcaminhao());
		rastreador = new SimpleDoubleProperty(this.elemento.getRastreador());
		seguro = new SimpleDoubleProperty(this.elemento.getSeguro());
		ipva = new SimpleDoubleProperty(this.elemento.getIpva());
		salario = new SimpleDoubleProperty(this.elemento.getSalario());
		depreciacao = new SimpleDoubleProperty(this.elemento.getDepreciacao());
		multa = new SimpleDoubleProperty(this.elemento.getMulta());
		oficina = new SimpleDoubleProperty(this.elemento.getOficina());
		pedagio = new SimpleDoubleProperty(this.elemento.getPedagio());
		financiamento = new SimpleDoubleProperty(this.elemento.getFinanciamento());
		firme = new SimpleBooleanProperty(this.elemento.getFirme());
		medicina = new SimpleDoubleProperty(this.elemento.getMedicina());

		total = new SimpleDoubleProperty(getTotal());
		total.bind(rastreador.add(seguro).add(ipva).add(salario).add(depreciacao).add(multa).add(oficina).add(pedagio).add(financiamento).add(medicina));
	}

	private Double getTotal() {
		double sum = rastreador.get() + seguro.get() + ipva.get() + salario.get()
			+ depreciacao.get() + multa.get() + oficina.get() + pedagio.get()
			+ financiamento.get() + medicina.get();
		return sum;
	}

	public SimpleBooleanProperty selectedProperty() {
		return selected;
	}

	public boolean getSelected() {
		return selected.get();
	}

	public void setSelected(Boolean selected) {
		this.selected.set(selected);
	}

	@Override
	public String toString() {
		return (new SimpleDateFormat("dd/MM/yyyy").format(elemento.getData())) + " " + elemento.getIdcaminhao().getPlaca();
	}

	@Override
	public Custocaminhao get() {
		return elemento;
	}

	public void set(Custocaminhao elemento) {
		this.elemento = elemento;
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
		elemento.setId(id);
	}

	public ObjectProperty<CustomDate> dataProperty() {
		return data;
	}

	public CustomDate getData() {
		return data.get();
	}

	public void setData(CustomDate data) {
		this.data.set(data);
		elemento.setData(data.getData());
	}

	public ObjectProperty<Caminhao> caminhaoProperty() {
		return caminhao;
	}

	public Caminhao getIdcaminhao() {
		return caminhao.get();
	}

	public void setIdcaminhao(Caminhao caminhao) {
		this.caminhao.set(caminhao);
		elemento.setIdcaminhao(caminhao);
	}

	public DoubleProperty rastreadorProperty() {
		return rastreador;
	}

	public Double getRastreador() {
		return rastreador.get();
	}

	public void setRastreador(Double rastreador) {
		this.rastreador.set(rastreador);
		elemento.setRastreador(rastreador);
	}

	public DoubleProperty seguroProperty() {
		return seguro;
	}

	public Double getSeguro() {
		return seguro.get();
	}

	public void setSeguro(Double seguro) {
		this.seguro.set(seguro);
		elemento.setSeguro(seguro);
	}

	public DoubleProperty ipvaProperty() {
		return ipva;
	}

	public Double getIpva() {
		return ipva.get();
	}

	public void setIpva(Double ipva) {
		this.ipva.set(ipva);
		elemento.setIpva(ipva);
	}

	public DoubleProperty salarioProperty() {
		return salario;
	}

	public Double getSalario() {
		return salario.get();
	}

	public void setSalario(Double salario) {
		this.salario.set(salario);
		elemento.setSalario(salario);
	}

	public DoubleProperty depreciacaoProperty() {
		return depreciacao;
	}

	public Double getDepreciacao() {
		return depreciacao.get();
	}

	public void setDepreciacao(Double depreciacao) {
		this.depreciacao.set(depreciacao);
		elemento.setDepreciacao(depreciacao);
	}

	public DoubleProperty multaProperty() {
		return multa;
	}

	public Double getMulta() {
		return multa.get();
	}

	public void setMulta(Double multa) {
		this.multa.set(multa);
		elemento.setMulta(multa);
	}

	public DoubleProperty oficinaProperty() {
		return oficina;
	}

	public Double getOficina() {
		return oficina.get();
	}

	public void setOficina(Double oficina) {
		this.oficina.set(oficina);
		elemento.setOficina(oficina);
	}

	public DoubleProperty pedagioProperty() {
		return pedagio;
	}

	public Double getPedagio() {
		return pedagio.get();
	}

	public void setPedagio(Double pedagio) {
		this.pedagio.set(pedagio);
		elemento.setPedagio(pedagio);
	}

	public DoubleProperty financiamentoProperty() {
		return financiamento;
	}

	public DoubleProperty totalProperty() {
		return total;
	}

	public Double getFinanciamento() {
		return financiamento.get();
	}

	public void setFinanciamento(Double financiamento) {
		this.financiamento.set(financiamento);
		elemento.setFinanciamento(financiamento);
	}

	public DoubleProperty medicinaProperty() {
		return medicina;
	}

	public Double getMedicina() {
		return medicina.get();
	}

	public void setMedicina(Double medicina) {
		this.medicina.set(medicina);
		elemento.setMedicina(medicina);
	}

	public BooleanProperty firmeProperty() {
		return firme;
	}

	public Boolean getFirme() {
		return firme.get();
	}

	public void setFirme(Boolean firme) {
		this.firme.set(firme);
		elemento.setFirme(firme);
	}
}
