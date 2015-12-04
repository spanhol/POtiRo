package wrapper;

import entidades.Caminhao;
import entidades.Motorista;
import entidades.Regiao;
import entidades.Viagem;
import java.util.Calendar;
import java.util.Collection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import modelo.CustomDate;

/**
 *
 * @author Fernando Spanhol
 */
public class ViagemWrapper extends Wrapper<Viagem> {


	private final IntegerProperty id;
	private final IntegerProperty numcontrole;
	private final IntegerProperty numcarga;
	private final ObjectProperty<CustomDate> datacarregamento;
	private final StringProperty regiao;
	private final ObjectProperty<Motorista> motorista;
	private final ObjectProperty<Caminhao> caminhao;
	private final DoubleProperty adiantamento;
	private final DoubleProperty adiantamentochapa;
	private final DoubleProperty despdiarias;
	private final DoubleProperty comisretorno;
	private final DoubleProperty despoutras;
	private final DoubleProperty credito;
	private final DoubleProperty debito;
	private final DoubleProperty saldo;
	private final StringProperty obs;
	private final DoubleProperty frete;
	private final DoubleProperty litrosCombustivel;
	private final DoubleProperty valorCombustivel;
	private final IntegerProperty kmInicial;
	private final IntegerProperty kmFinal;
	private final DoubleProperty comissao;
	private final DoubleProperty retorno;
	private final DoubleProperty difchapa;
	private final BooleanProperty firme;
	private final ObjectProperty<CustomDate> ini;
	private final ObjectProperty<CustomDate> fim;

	public ViagemWrapper(Viagem e) {
		this.elemento = e;
		id = new SimpleIntegerProperty(e.getId());
		numcontrole = new SimpleIntegerProperty(e.getNumcontrole());
		numcarga = new SimpleIntegerProperty(e.getNumcarga());
		datacarregamento = new SimpleObjectProperty<>(new CustomDate(e.getDatacarregamento().getTime()));
		ini = new SimpleObjectProperty<>(e.getInicio() != null ? (new CustomDate(e.getInicio().getTime())) : null);
		fim = new SimpleObjectProperty<>(e.getFim() != null ? (new CustomDate(e.getFim().getTime())) : null);
		regiao = new SimpleStringProperty(getRegioes());
		motorista = new SimpleObjectProperty<>(e.getIdmotorista());
		caminhao = new SimpleObjectProperty<>(e.getIdcaminhao());
		adiantamento = new SimpleDoubleProperty(e.getAdiantamento());
		adiantamentochapa = new SimpleDoubleProperty(e.getAdiantamentochapa());
		despdiarias = new SimpleDoubleProperty(e.getDespdiarias());
		comisretorno = new SimpleDoubleProperty(e.getComisretorno());
		despoutras = new SimpleDoubleProperty(e.getDespoutras());
		obs = new SimpleStringProperty(e.getObs());
		frete = new SimpleDoubleProperty(e.getFrete());
		litrosCombustivel = new SimpleDoubleProperty(e.getLitrosCombustivel());
		valorCombustivel = new SimpleDoubleProperty(e.getValorCombustivel());
		kmInicial = new SimpleIntegerProperty(e.getKmInicial());
		kmFinal = new SimpleIntegerProperty(e.getKmFinal());
		comissao = new SimpleDoubleProperty(e.getComissao());
		retorno = new SimpleDoubleProperty(e.getRetorno());
		difchapa = new SimpleDoubleProperty(e.getDifchapa());
		firme = new SimpleBooleanProperty(e.getFirme());
		credito = new SimpleDoubleProperty(credito());
		debito = new SimpleDoubleProperty(debito());
		saldo = new SimpleDoubleProperty(saldo());
	}

	public ViagemWrapper() {
		this.elemento = new Viagem(-1);
		id = new SimpleIntegerProperty();
		numcontrole = new SimpleIntegerProperty();
		numcarga = new SimpleIntegerProperty();
		datacarregamento = new SimpleObjectProperty<>();
		ini = new SimpleObjectProperty<>();
		fim = new SimpleObjectProperty<>();
		regiao = new SimpleStringProperty();
		motorista = new SimpleObjectProperty<>();
		caminhao = new SimpleObjectProperty<>();
		adiantamento = new SimpleDoubleProperty();
		adiantamentochapa = new SimpleDoubleProperty();
		despdiarias = new SimpleDoubleProperty();
		comisretorno = new SimpleDoubleProperty();
		despoutras = new SimpleDoubleProperty();
		credito = new SimpleDoubleProperty();
		debito = new SimpleDoubleProperty();
		saldo = new SimpleDoubleProperty();
		obs = new SimpleStringProperty();
		frete = new SimpleDoubleProperty();
		litrosCombustivel = new SimpleDoubleProperty();
		valorCombustivel = new SimpleDoubleProperty();
		kmInicial = new SimpleIntegerProperty();
		kmFinal = new SimpleIntegerProperty();
		comissao = new SimpleDoubleProperty();
		retorno = new SimpleDoubleProperty();
		difchapa = new SimpleDoubleProperty();
		firme = new SimpleBooleanProperty();
		setNumcontrole(0);
		setNumcarga(0);
		setDatacarregamento(new CustomDate(Calendar.getInstance().getTimeInMillis()));
		setRegiaoCollection(FXCollections.observableArrayList());
		setIdmotorista(null);
		setIdcaminhao(null);
		setAdiantamento(0d);
		setAdiantamentochapa(0d);
		setDespdiarias(0d);
		setDespoutras(0d);
		setComisretorno(0d);
		setObs("");
		setFrete(0d);
		setLitrosCombustivel(0d);
		setValorCombustivel(0d);
		setKmInicial(0);
		setKmFinal(0);
		setComissao(0d);
		setRetorno(0d);
		setDifchapa(0d);
		setFirme(false);
		credito.set(credito());
		debito.set(debito());
		setSaldo();
	}

	@Override
	public Viagem get() {
		return elemento;
	}

	public void set(Viagem e) {
		this.elemento = e;
	}

	final public double debito() {
		double res
			= elemento.getAdiantamentochapa()
			+ elemento.getValorCombustivel()
			+ elemento.getDespdiarias()
			+ elemento.getDespoutras()
			+ elemento.getDifchapa()
			+ elemento.getComisretorno();
		return res;
	}

	final public double credito() {
		double res
			= elemento.getRetorno()
			+ elemento.getFrete();
		return res;
	}

	final public double saldo() {
		return credito() - debito();
	}

	@Override
	public String toString() {
		return elemento.getNumcontrole().toString();
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
		elemento.setId(id);
	}

	public Integer getNumcontrole() {
		return numcontrole.get();
	}

	final public void setNumcontrole(Integer numcontrole) {
		this.numcontrole.set(numcontrole);
		elemento.setNumcontrole(numcontrole);
	}

	public Integer getNumcarga() {
		return numcarga.get();
	}

	final public void setNumcarga(Integer numcarga) {
		this.numcarga.set(numcarga);
		elemento.setNumcarga(numcarga);
	}

	public CustomDate getDatacarregamento() {
		return datacarregamento.get();
	}

	final public void setDatacarregamento(CustomDate datacarregamento) {
		this.datacarregamento.set(datacarregamento);
		elemento.setDatacarregamento(datacarregamento);
	}

	public CustomDate getInicio() {
		return ini.get();
	}

	final public void setInicio(CustomDate inicio) {
		this.ini.set(inicio);
		elemento.setInicio(inicio);
	}

	public CustomDate getFim() {
		return fim.get();
	}

	final public void setFim(CustomDate fim) {
		this.fim.set(fim);
		elemento.setInicio(fim);
	}

	public Double getAdiantamento() {
		return adiantamento.get();
	}

	final public void setAdiantamento(Double adiantamento) {
		this.adiantamento.set(adiantamento);
		elemento.setAdiantamento(adiantamento);
	}

	public Double getAdiantamentochapa() {
		return adiantamentochapa.get();
	}

	final public void setAdiantamentochapa(Double adiantamentochapa) {
		this.adiantamentochapa.set(adiantamentochapa);
		elemento.setAdiantamentochapa(adiantamentochapa);
	}

	public Double getDespdiarias() {
		return despdiarias.get();
	}

	final public void setDespdiarias(Double despdiarias) {
		this.despdiarias.set(despdiarias);
		elemento.setDespdiarias(despdiarias);
	}

	public Double getComisretorno() {
		return comisretorno.get();
	}

	final public void setComisretorno(Double comisretorno) {
		this.comisretorno.set(comisretorno);
		elemento.setComisretorno(comisretorno);
	}

	public Double getDespoutras() {
		return despoutras.get();
	}

	final public void setDespoutras(Double despoutras) {
		this.despoutras.set(despoutras);
		elemento.setDespoutras(despoutras);
	}

	public DoubleProperty creditoProperty() {
		credito.set(credito());
		return credito;
	}

	final public DoubleProperty debitoProperty() {
		debito.set(debito());
		return debito;
	}

	public String getObs() {
		return obs.get();
	}

	final public void setObs(String obs) {
		this.obs.set(obs);
		elemento.setObs(obs);
	}

	public Double getFrete() {
		return frete.get();
	}

	final public void setFrete(Double frete) {
		this.frete.set(frete);
		elemento.setFrete(frete);
	}

	public Double getLitrosCombustivel() {
		return litrosCombustivel.get();
	}

	final public void setLitrosCombustivel(Double litrosCombustivel) {
		this.litrosCombustivel.set(litrosCombustivel);
		elemento.setLitrosCombustivel(litrosCombustivel);
	}

	public Double getValorCombustivel() {
		return valorCombustivel.get();
	}

	final public void setValorCombustivel(Double valorCombustivel) {
		this.valorCombustivel.set(valorCombustivel);
		elemento.setValorCombustivel(valorCombustivel);
	}

	public Integer getKmInicial() {
		return kmInicial.get();
	}

	final public void setKmInicial(Integer kmInicial) {
		this.kmInicial.set(kmInicial);
		elemento.setKmInicial(kmInicial);
	}

	public Integer getKmFinal() {
		return kmFinal.get();
	}

	final public void setKmFinal(Integer kmFinal) {
		this.kmFinal.set(kmFinal);
		elemento.setKmFinal(kmFinal);
	}

	public Double getComissao() {
		return comissao.get();
	}

	final public void setComissao(Double comissao) {
		this.comissao.set(comissao);
		elemento.setComissao(comissao);
	}

	public Double getRetorno() {
		return retorno.get();
	}

	final public void setRetorno(Double retorno) {
		this.retorno.set(retorno);
		elemento.setRetorno(retorno);
	}

	public Double getDifchapa() {
		return difchapa.get();
	}

	final public void setDifchapa(Double difchapa) {
		this.difchapa.set(difchapa);
		elemento.setDifchapa(difchapa);
	}

	public Boolean getFirme() {
		return firme.get();
	}

	final public void setFirme(Boolean firme) {
		this.firme.set(firme);
		elemento.setFirme(firme);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	final public IntegerProperty numcontroleProperty() {
		return numcontrole;
	}

	public IntegerProperty numcargaProperty() {
		return numcarga;
	}

	final public ObjectProperty<CustomDate> datacarregamentoProperty() {
		return datacarregamento;
	}

	final public ObjectProperty<CustomDate> inicioProperty() {
		return ini;
	}

	final public ObjectProperty<CustomDate> fimProperty() {
		return fim;
	}

	public DoubleProperty adiantamentoProperty() {
		return adiantamento;
	}

	final public DoubleProperty adiantamentochapaProperty() {
		return adiantamentochapa;
	}

	public DoubleProperty despdiariasProperty() {
		return despdiarias;
	}

	final public DoubleProperty comisretornoProperty() {
		return comisretorno;
	}

	public DoubleProperty despoutrasProperty() {
		return despoutras;
	}

	final public DoubleProperty saldoProperty() {
		saldo.set(saldo());
		return saldo;
	}

	public Double getSaldo() {
		return elemento.getSaldo();
	}

	final public void setSaldo() {
		elemento.setSaldo(saldo());
		saldo.set(saldo());
	}

	public StringProperty obsProperty() {
		return obs;
	}

	final public DoubleProperty freteProperty() {
		return frete;
	}

	public DoubleProperty litrosCombustivelProperty() {
		return litrosCombustivel;
	}

	final public DoubleProperty valorCombustivelProperty() {
		return valorCombustivel;
	}

	public IntegerProperty kmInicialProperty() {
		return kmInicial;
	}

	final public IntegerProperty kmFinalProperty() {
		return kmFinal;
	}

	public DoubleProperty comissaoProperty() {
		return comissao;
	}

	final public DoubleProperty retornoProperty() {
		return retorno;
	}

	public DoubleProperty difchapaProperty() {
		return difchapa;
	}

	final public BooleanProperty firmeProperty() {
		return firme;
	}

	public StringProperty regiaoProperty() {
		return regiao;
	}

	final public Collection<Regiao> getRegiaoCollection() {
		return elemento.getRegiaoCollection();
	}

	final public String getRegioes() {
		String str = "";
		for (Regiao r : elemento.getRegiaoCollection()) {
			str = str + r.getNome() + ", ";
		}
		if (elemento.getRegiaoCollection().size() > 0) {
			str = str.substring(0, str.length() - 2);
		}
		return str;
	}

	final public void setRegiaoCollection(Collection<Regiao> regiao) {
		this.elemento.setRegiaoCollection(regiao);
		this.regiao.set(getRegioes());
	}

	public ObjectProperty<Motorista> motoristaProperty() {
		return motorista;
	}

	public Motorista getIdmotorista() {
		return motorista.get();
	}

	final public void setIdmotorista(Motorista motorista) {
		this.motorista.set(motorista);
		elemento.setIdmotorista(motorista);
	}

	public ObjectProperty<Caminhao> caminhaoProperty() {
		return caminhao;
	}

	public Caminhao getIdcaminhao() {
		return elemento.getIdcaminhao();
	}

	final public void setIdcaminhao(Caminhao caminhao) {
		this.caminhao.set(caminhao);
		elemento.setIdcaminhao(caminhao);
	}

}
