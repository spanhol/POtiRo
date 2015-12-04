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
import javax.persistence.Id;
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
@Table(name = "titulos_contas_pagar")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "TitulosContasPagar.findAll", query = "SELECT t FROM TitulosContasPagar t"),
	@NamedQuery(name = "TitulosContasPagar.findById", query = "SELECT t FROM TitulosContasPagar t WHERE t.id = :id"),
	@NamedQuery(name = "TitulosContasPagar.findByNumTit", query = "SELECT t FROM TitulosContasPagar t WHERE t.numTit = :numTit"),
	@NamedQuery(name = "TitulosContasPagar.findByParc", query = "SELECT t FROM TitulosContasPagar t WHERE t.parc = :parc"),
	@NamedQuery(name = "TitulosContasPagar.findByDtVenc", query = "SELECT t FROM TitulosContasPagar t WHERE t.dtVenc = :dtVenc"),
	@NamedQuery(name = "TitulosContasPagar.findByAb", query = "SELECT t FROM TitulosContasPagar t WHERE t.ab = :ab"),
	@NamedQuery(name = "TitulosContasPagar.findByValor", query = "SELECT t FROM TitulosContasPagar t WHERE t.valor = :valor"),
	@NamedQuery(name = "TitulosContasPagar.findByJuros", query = "SELECT t FROM TitulosContasPagar t WHERE t.juros = :juros"),
	@NamedQuery(name = "TitulosContasPagar.findByPago", query = "SELECT t FROM TitulosContasPagar t WHERE t.pago = :pago")})
public class TitulosContasPagar implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "num_tit")
	private Double numTit;
	@Column(name = "parc")
	private String parc;
	@Column(name = "dt_venc")
    @Temporal(TemporalType.DATE)
	private Date dtVenc;
	@Column(name = "ab")
	private Integer ab;
	@Column(name = "valor")
	private Double valor;
	@Column(name = "juros")
	private Double juros;
	@Column(name = "pago")
	private Double pago;

	public TitulosContasPagar() {
	}

	public TitulosContasPagar(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getNumTit() {
		return numTit;
	}

	public void setNumTit(Double numTit) {
		this.numTit = numTit;
	}

	public String getParc() {
		return parc;
	}

	public void setParc(String parc) {
		this.parc = parc;
	}

	public Date getDtVenc() {
		return dtVenc;
	}

	public void setDtVenc(Date dtVenc) {
		this.dtVenc = dtVenc;
	}

	public Integer getAb() {
		return ab;
	}

	public void setAb(Integer ab) {
		this.ab = ab;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getJuros() {
		return juros;
	}

	public void setJuros(Double juros) {
		this.juros = juros;
	}

	public Double getPago() {
		return pago;
	}

	public void setPago(Double pago) {
		this.pago = pago;
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
		if (!(object instanceof TitulosContasPagar)) {
			return false;
		}
		TitulosContasPagar other = (TitulosContasPagar) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.TitulosContasPagar[ id=" + id + " ]";
	}
	
}
