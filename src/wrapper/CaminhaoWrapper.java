package wrapper;

import entidades.Caminhao;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fernando Spanhol
 */
public class CaminhaoWrapper extends  Wrapper<Caminhao> {

	private final IntegerProperty id;
	private final StringProperty placa;
	private final DoubleProperty comissao;
	private final IntegerProperty centrocusto;
	private final IntegerProperty titFinanciamento;
	private final StringProperty modelo;
	private final BooleanProperty ativo;

	public CaminhaoWrapper(Caminhao elemento) {
		this.elemento = elemento != null ? elemento : new Caminhao();
		this.id = new SimpleIntegerProperty(elemento.getId() != null ? elemento.getId() : 0);
		this.placa = new SimpleStringProperty(elemento.getPlaca() != null ? elemento.getPlaca() : "");
		this.comissao = new SimpleDoubleProperty(elemento.getComissao() != null ? elemento.getComissao() : 0);
		this.centrocusto = new SimpleIntegerProperty(elemento.getCentrocusto() != null ? elemento.getCentrocusto() : 0);
		this.titFinanciamento = new SimpleIntegerProperty(elemento.getTitFinanciamento() != null ? elemento.getTitFinanciamento() : 0);
		this.modelo = new SimpleStringProperty(elemento.getModelo() != null ? elemento.getModelo() : "");
		this.ativo = new SimpleBooleanProperty(elemento.getAtivo() != null ? elemento.getAtivo() : false);
	}

	@Override
	public Caminhao get() {
		return elemento;
	}

	@Override
	public String toString() {
		return elemento.getPlaca();
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
		elemento.setId(id);
	}

	public String getPlaca() {
		return placa.get();
	}

	public void setPlaca(String placa) {
		this.placa.set(placa);
		elemento.setPlaca(placa);
	}

	public Double getComissao() {
		return comissao.get();
	}

	public void setComissao(Double comissao) {
		this.comissao.set(comissao);
		elemento.setComissao(comissao);
	}

	public Integer getCentrocusto() {
		return centrocusto.get();
	}

	public void setCentrocusto(Integer centrocusto) {
		this.centrocusto.set(centrocusto);
		elemento.setCentrocusto(centrocusto);
	}

	public Integer getTitFinanciamento() {
		return titFinanciamento.get();
	}

	public void setTitFinanciamento(Integer titFinanciamento) {
		this.titFinanciamento.set(titFinanciamento);
		elemento.setTitFinanciamento(titFinanciamento);
	}

	public String getModelo() {
		return modelo.get();
	}

	public void setModelo(String modelo) {
		this.modelo.set(modelo);
		elemento.setModelo(modelo);
	}

	public Boolean getAtivo() {
		return ativo.get();
	}

	public void setAtivo(Boolean ativo) {
		this.ativo.set(ativo);
		elemento.setAtivo(ativo);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty placaProperty() {
		return placa;
	}

	public StringProperty modeloProperty() {
		return modelo;
	}

	public DoubleProperty comissaoProperty() {
		return comissao;
	}

	public IntegerProperty centrocustoProperty() {
		return centrocusto;
	}

	public IntegerProperty titFinanciamentoProperty() {
		return titFinanciamento;
	}

	public BooleanProperty ativoProperty() {
		return ativo;
	}
}
