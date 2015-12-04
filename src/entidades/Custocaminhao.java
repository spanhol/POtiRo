/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "custocaminhao")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Custocaminhao.findAll", query = "SELECT c FROM Custocaminhao c"),
	@NamedQuery(name = "Custocaminhao.findByData", query = "SELECT c FROM Custocaminhao c WHERE c.data = :data"),
	@NamedQuery(name = "Custocaminhao.findByRastreador", query = "SELECT c FROM Custocaminhao c WHERE c.rastreador = :rastreador"),
	@NamedQuery(name = "Custocaminhao.findBySeguro", query = "SELECT c FROM Custocaminhao c WHERE c.seguro = :seguro"),
	@NamedQuery(name = "Custocaminhao.findByIpva", query = "SELECT c FROM Custocaminhao c WHERE c.ipva = :ipva"),
	@NamedQuery(name = "Custocaminhao.findBySalario", query = "SELECT c FROM Custocaminhao c WHERE c.salario = :salario"),
	@NamedQuery(name = "Custocaminhao.findByDepreciacao", query = "SELECT c FROM Custocaminhao c WHERE c.depreciacao = :depreciacao"),
	@NamedQuery(name = "Custocaminhao.findById", query = "SELECT c FROM Custocaminhao c WHERE c.id = :id"),
	@NamedQuery(name = "Custocaminhao.findByMulta", query = "SELECT c FROM Custocaminhao c WHERE c.multa = :multa"),
	@NamedQuery(name = "Custocaminhao.findByOficina", query = "SELECT c FROM Custocaminhao c WHERE c.oficina = :oficina"),
	@NamedQuery(name = "Custocaminhao.findByPedagio", query = "SELECT c FROM Custocaminhao c WHERE c.pedagio = :pedagio"),
	@NamedQuery(name = "Custocaminhao.findByFinanciamento", query = "SELECT c FROM Custocaminhao c WHERE c.financiamento = :financiamento"),
	@NamedQuery(name = "Custocaminhao.findByFirme", query = "SELECT c FROM Custocaminhao c WHERE c.firme = :firme")})
public class Custocaminhao implements Serializable {
	@Column(name = "medicina")
	private Double medicina;
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
	private Date data;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "rastreador")
	private Double rastreador;
	@Column(name = "seguro")
	private Double seguro;
	@Column(name = "ipva")
	private Double ipva;
	@Column(name = "salario")
	private Double salario;
	@Column(name = "depreciacao")
	private Double depreciacao;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "multa")
	private Double multa;
	@Column(name = "oficina")
	private Double oficina;
	@Column(name = "pedagio")
	private Double pedagio;
	@Column(name = "financiamento")
	private Double financiamento;
	@Basic(optional = false)
    @Column(name = "firme")
	private boolean firme;
	@JoinColumn(name = "idcaminhao", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Caminhao idcaminhao;

	public Custocaminhao() {
	}

	public Custocaminhao(Integer id) {
		this.id = id;
	}

	public Custocaminhao(Integer id, Date data, boolean firme) {
		this.id = id;
		this.data = data;
		this.firme = firme;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getRastreador() {
		return rastreador;
	}

	public void setRastreador(Double rastreador) {
		this.rastreador = rastreador;
	}

	public Double getSeguro() {
		return seguro;
	}

	public void setSeguro(Double seguro) {
		this.seguro = seguro;
	}

	public Double getIpva() {
		return ipva;
	}

	public void setIpva(Double ipva) {
		this.ipva = ipva;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Double getDepreciacao() {
		return depreciacao;
	}

	public void setDepreciacao(Double depreciacao) {
		this.depreciacao = depreciacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getMulta() {
		return multa;
	}

	public void setMulta(Double multa) {
		this.multa = multa;
	}

	public Double getOficina() {
		return oficina;
	}

	public void setOficina(Double oficina) {
		this.oficina = oficina;
	}

	public Double getPedagio() {
		return pedagio;
	}

	public void setPedagio(Double pedagio) {
		this.pedagio = pedagio;
	}

	public Double getFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(Double financiamento) {
		this.financiamento = financiamento;
	}

	public boolean getFirme() {
		return firme;
	}

	public void setFirme(boolean firme) {
		this.firme = firme;
	}

	public Caminhao getIdcaminhao() {
		return idcaminhao;
	}

	public void setIdcaminhao(Caminhao idcaminhao) {
		this.idcaminhao = idcaminhao;
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
		if (!(object instanceof Custocaminhao)) {
			return false;
		}
		Custocaminhao other = (Custocaminhao) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Custocaminhao[ id=" + id + " ]";
	}

	public Double getMedicina() {
		return medicina;
	}

	public void setMedicina(Double medicina) {
		this.medicina = medicina;
	}
	
}
