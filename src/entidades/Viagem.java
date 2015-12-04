/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "viagem")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Viagem.findAll", query = "SELECT v FROM Viagem v"),
	@NamedQuery(name = "Viagem.findById", query = "SELECT v FROM Viagem v WHERE v.id = :id"),
	@NamedQuery(name = "Viagem.findByNumcontrole", query = "SELECT v FROM Viagem v WHERE v.numcontrole = :numcontrole"),
	@NamedQuery(name = "Viagem.findByNumcarga", query = "SELECT v FROM Viagem v WHERE v.numcarga = :numcarga"),
	@NamedQuery(name = "Viagem.findByDatacarregamento", query = "SELECT v FROM Viagem v WHERE v.datacarregamento = :datacarregamento"),
	@NamedQuery(name = "Viagem.findByAdiantamento", query = "SELECT v FROM Viagem v WHERE v.adiantamento = :adiantamento"),
	@NamedQuery(name = "Viagem.findByAdiantamentochapa", query = "SELECT v FROM Viagem v WHERE v.adiantamentochapa = :adiantamentochapa"),
	@NamedQuery(name = "Viagem.findByDespdiarias", query = "SELECT v FROM Viagem v WHERE v.despdiarias = :despdiarias"),
	@NamedQuery(name = "Viagem.findByComisretorno", query = "SELECT v FROM Viagem v WHERE v.comisretorno = :comisretorno"),
	@NamedQuery(name = "Viagem.findByDespoutras", query = "SELECT v FROM Viagem v WHERE v.despoutras = :despoutras"),
	@NamedQuery(name = "Viagem.findBySaldo", query = "SELECT v FROM Viagem v WHERE v.saldo = :saldo"),
	@NamedQuery(name = "Viagem.findByObs", query = "SELECT v FROM Viagem v WHERE v.obs = :obs"),
	@NamedQuery(name = "Viagem.findByFrete", query = "SELECT v FROM Viagem v WHERE v.frete = :frete"),
	@NamedQuery(name = "Viagem.findByLitrosCombustivel", query = "SELECT v FROM Viagem v WHERE v.litrosCombustivel = :litrosCombustivel"),
	@NamedQuery(name = "Viagem.findByValorCombustivel", query = "SELECT v FROM Viagem v WHERE v.valorCombustivel = :valorCombustivel"),
	@NamedQuery(name = "Viagem.findByKmInicial", query = "SELECT v FROM Viagem v WHERE v.kmInicial = :kmInicial"),
	@NamedQuery(name = "Viagem.findByKmFinal", query = "SELECT v FROM Viagem v WHERE v.kmFinal = :kmFinal"),
	@NamedQuery(name = "Viagem.findByComissao", query = "SELECT v FROM Viagem v WHERE v.comissao = :comissao"),
	@NamedQuery(name = "Viagem.findByRetorno", query = "SELECT v FROM Viagem v WHERE v.retorno = :retorno"),
	@NamedQuery(name = "Viagem.findByDifchapa", query = "SELECT v FROM Viagem v WHERE v.difchapa = :difchapa"),
	@NamedQuery(name = "Viagem.findByFirme", query = "SELECT v FROM Viagem v WHERE v.firme = :firme"),
	@NamedQuery(name = "Viagem.findByInicio", query = "SELECT v FROM Viagem v WHERE v.inicio = :inicio"),
	@NamedQuery(name = "Viagem.findByFim", query = "SELECT v FROM Viagem v WHERE v.fim = :fim")})
public class Viagem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "numcontrole")
	private Integer numcontrole;
	@Column(name = "numcarga")
	private Integer numcarga;
	@Column(name = "datacarregamento")
    @Temporal(TemporalType.DATE)
	private Date datacarregamento;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "adiantamento")
	private Double adiantamento;
	@Column(name = "adiantamentochapa")
	private Double adiantamentochapa;
	@Column(name = "despdiarias")
	private Double despdiarias;
	@Column(name = "comisretorno")
	private Double comisretorno;
	@Column(name = "despoutras")
	private Double despoutras;
	@Column(name = "saldo")
	private Double saldo;
	@Column(name = "obs")
	private String obs;
	@Column(name = "frete")
	private Double frete;
	@Column(name = "litros_combustivel")
	private Double litrosCombustivel;
	@Column(name = "valor_combustivel")
	private Double valorCombustivel;
	@Column(name = "km_inicial")
	private Integer kmInicial;
	@Column(name = "km_final")
	private Integer kmFinal;
	@Column(name = "comissao")
	private Double comissao;
	@Column(name = "retorno")
	private Double retorno;
	@Column(name = "difchapa")
	private Double difchapa;
	@Basic(optional = false)
    @Column(name = "firme")
	private boolean firme;
	@Column(name = "inicio")
    @Temporal(TemporalType.DATE)
	private Date inicio;
	@Column(name = "fim")
    @Temporal(TemporalType.DATE)
	private Date fim;
	@ManyToMany(mappedBy = "viagemCollection")
	private Collection<Regiao> regiaoCollection;
	@JoinColumn(name = "idcaminhao", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Caminhao idcaminhao;
	@JoinColumn(name = "idmotorista", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Motorista idmotorista;

	public Viagem() {
	}

	public Viagem(Integer id) {
		this.id = id;
	}

	public Viagem(Integer id, boolean firme) {
		this.id = id;
		this.firme = firme;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumcontrole() {
		return numcontrole;
	}

	public void setNumcontrole(Integer numcontrole) {
		this.numcontrole = numcontrole;
	}

	public Integer getNumcarga() {
		return numcarga;
	}

	public void setNumcarga(Integer numcarga) {
		this.numcarga = numcarga;
	}

	public Date getDatacarregamento() {
		return datacarregamento;
	}

	public void setDatacarregamento(Date datacarregamento) {
		this.datacarregamento = datacarregamento;
	}

	public Double getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Double adiantamento) {
		this.adiantamento = adiantamento;
	}

	public Double getAdiantamentochapa() {
		return adiantamentochapa;
	}

	public void setAdiantamentochapa(Double adiantamentochapa) {
		this.adiantamentochapa = adiantamentochapa;
	}

	public Double getDespdiarias() {
		return despdiarias;
	}

	public void setDespdiarias(Double despdiarias) {
		this.despdiarias = despdiarias;
	}

	public Double getComisretorno() {
		return comisretorno;
	}

	public void setComisretorno(Double comisretorno) {
		this.comisretorno = comisretorno;
	}

	public Double getDespoutras() {
		return despoutras;
	}

	public void setDespoutras(Double despoutras) {
		this.despoutras = despoutras;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Double getFrete() {
		return frete;
	}

	public void setFrete(Double frete) {
		this.frete = frete;
	}

	public Double getLitrosCombustivel() {
		return litrosCombustivel;
	}

	public void setLitrosCombustivel(Double litrosCombustivel) {
		this.litrosCombustivel = litrosCombustivel;
	}

	public Double getValorCombustivel() {
		return valorCombustivel;
	}

	public void setValorCombustivel(Double valorCombustivel) {
		this.valorCombustivel = valorCombustivel;
	}

	public Integer getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(Integer kmInicial) {
		this.kmInicial = kmInicial;
	}

	public Integer getKmFinal() {
		return kmFinal;
	}

	public void setKmFinal(Integer kmFinal) {
		this.kmFinal = kmFinal;
	}

	public Double getComissao() {
		return comissao;
	}

	public void setComissao(Double comissao) {
		this.comissao = comissao;
	}

	public Double getRetorno() {
		return retorno;
	}

	public void setRetorno(Double retorno) {
		this.retorno = retorno;
	}

	public Double getDifchapa() {
		return difchapa;
	}

	public void setDifchapa(Double difchapa) {
		this.difchapa = difchapa;
	}

	public boolean getFirme() {
		return firme;
	}

	public void setFirme(boolean firme) {
		this.firme = firme;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@XmlTransient
	public Collection<Regiao> getRegiaoCollection() {
		return regiaoCollection;
	}

	public void setRegiaoCollection(Collection<Regiao> regiaoCollection) {
		this.regiaoCollection = regiaoCollection;
	}

	public Caminhao getIdcaminhao() {
		return idcaminhao;
	}

	public void setIdcaminhao(Caminhao idcaminhao) {
		this.idcaminhao = idcaminhao;
	}

	public Motorista getIdmotorista() {
		return idmotorista;
	}

	public void setIdmotorista(Motorista idmotorista) {
		this.idmotorista = idmotorista;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Viagem)) {
			return false;
		}
		Viagem other = (Viagem) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Viagem[ id=" + id + " ]";
	}
	
}
