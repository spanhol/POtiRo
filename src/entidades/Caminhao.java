/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "caminhao")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Caminhao.findAll", query = "SELECT c FROM Caminhao c"),
	@NamedQuery(name = "Caminhao.findById", query = "SELECT c FROM Caminhao c WHERE c.id = :id"),
	@NamedQuery(name = "Caminhao.findByPlaca", query = "SELECT c FROM Caminhao c WHERE c.placa = :placa"),
	@NamedQuery(name = "Caminhao.findByComissao", query = "SELECT c FROM Caminhao c WHERE c.comissao = :comissao"),
	@NamedQuery(name = "Caminhao.findByCentrocusto", query = "SELECT c FROM Caminhao c WHERE c.centrocusto = :centrocusto"),
	@NamedQuery(name = "Caminhao.findByAtivo", query = "SELECT c FROM Caminhao c WHERE c.ativo = :ativo"),
	@NamedQuery(name = "Caminhao.findByTitFinanciamento", query = "SELECT c FROM Caminhao c WHERE c.titFinanciamento = :titFinanciamento"),
	@NamedQuery(name = "Caminhao.findByModelo", query = "SELECT c FROM Caminhao c WHERE c.modelo = :modelo")})
public class Caminhao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "placa")
	private String placa;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "comissao")
	private Double comissao;
	@Column(name = "centrocusto")
	private Integer centrocusto;
	@Column(name = "ativo")
	private Boolean ativo;
	@Column(name = "tit_financiamento")
	private Integer titFinanciamento;
	@Column(name = "modelo")
	private String modelo;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idcaminhao")
	private Collection<Viagem> viagemCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idcaminhao")
	private Collection<Custocaminhao> custocaminhaoCollection;
	@OneToMany(mappedBy = "favcaminhaoid")
	private Collection<Motorista> motoristaCollection;

	public Caminhao() {
	}

	public Caminhao(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Double getComissao() {
		return comissao;
	}

	public void setComissao(Double comissao) {
		this.comissao = comissao;
	}

	public Integer getCentrocusto() {
		return centrocusto;
	}

	public void setCentrocusto(Integer centrocusto) {
		this.centrocusto = centrocusto;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Integer getTitFinanciamento() {
		return titFinanciamento;
	}

	public void setTitFinanciamento(Integer titFinanciamento) {
		this.titFinanciamento = titFinanciamento;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@XmlTransient
	public Collection<Viagem> getViagemCollection() {
		return viagemCollection;
	}

	public void setViagemCollection(Collection<Viagem> viagemCollection) {
		this.viagemCollection = viagemCollection;
	}

	@XmlTransient
	public Collection<Custocaminhao> getCustocaminhaoCollection() {
		return custocaminhaoCollection;
	}

	public void setCustocaminhaoCollection(Collection<Custocaminhao> custocaminhaoCollection) {
		this.custocaminhaoCollection = custocaminhaoCollection;
	}

	@XmlTransient
	public Collection<Motorista> getMotoristaCollection() {
		return motoristaCollection;
	}

	public void setMotoristaCollection(Collection<Motorista> motoristaCollection) {
		this.motoristaCollection = motoristaCollection;
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
		if (!(object instanceof Caminhao)) {
			return false;
		}
		Caminhao other = (Caminhao) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Caminhao[ id=" + id + " ]";
	}
	
}
