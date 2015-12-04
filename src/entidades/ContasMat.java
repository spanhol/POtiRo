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
@Table(name = "contas_mat")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "ContasMat.findAll", query = "SELECT c FROM ContasMat c"),
	@NamedQuery(name = "ContasMat.findById", query = "SELECT c FROM ContasMat c WHERE c.id = :id"),
	@NamedQuery(name = "ContasMat.findByData", query = "SELECT c FROM ContasMat c WHERE c.data = :data"),
	@NamedQuery(name = "ContasMat.findByDescricao", query = "SELECT c FROM ContasMat c WHERE c.descricao = :descricao"),
	@NamedQuery(name = "ContasMat.findByValor", query = "SELECT c FROM ContasMat c WHERE c.valor = :valor"),
	@NamedQuery(name = "ContasMat.findByCcdeb", query = "SELECT c FROM ContasMat c WHERE c.ccdeb = :ccdeb"),
	@NamedQuery(name = "ContasMat.findByCccred", query = "SELECT c FROM ContasMat c WHERE c.cccred = :cccred"),
	@NamedQuery(name = "ContasMat.findByCtadeb", query = "SELECT c FROM ContasMat c WHERE c.ctadeb = :ctadeb"),
	@NamedQuery(name = "ContasMat.findByCtacred", query = "SELECT c FROM ContasMat c WHERE c.ctacred = :ctacred")})
public class ContasMat implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "data")
    @Temporal(TemporalType.DATE)
	private Date data;
	@Column(name = "descricao")
	private String descricao;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "valor")
	private Double valor;
	@Column(name = "ccdeb")
	private Integer ccdeb;
	@Column(name = "cccred")
	private Integer cccred;
	@Column(name = "ctadeb")
	private Integer ctadeb;
	@Column(name = "ctacred")
	private Integer ctacred;

	public ContasMat() {
	}

	public ContasMat(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getCcdeb() {
		return ccdeb;
	}

	public void setCcdeb(Integer ccdeb) {
		this.ccdeb = ccdeb;
	}

	public Integer getCccred() {
		return cccred;
	}

	public void setCccred(Integer cccred) {
		this.cccred = cccred;
	}

	public Integer getCtadeb() {
		return ctadeb;
	}

	public void setCtadeb(Integer ctadeb) {
		this.ctadeb = ctadeb;
	}

	public Integer getCtacred() {
		return ctacred;
	}

	public void setCtacred(Integer ctacred) {
		this.ctacred = ctacred;
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
		if (!(object instanceof ContasMat)) {
			return false;
		}
		ContasMat other = (ContasMat) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.ContasMat[ id=" + id + " ]";
	}
	
}
