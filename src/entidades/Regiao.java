/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "regiao")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Regiao.findAll", query = "SELECT r FROM Regiao r"),
	@NamedQuery(name = "Regiao.findById", query = "SELECT r FROM Regiao r WHERE r.id = :id"),
	@NamedQuery(name = "Regiao.findByAbreviacao", query = "SELECT r FROM Regiao r WHERE r.abreviacao = :abreviacao"),
	@NamedQuery(name = "Regiao.findByNome", query = "SELECT r FROM Regiao r WHERE r.nome = :nome")})
public class Regiao implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "abreviacao")
	private String abreviacao;
	@Column(name = "nome")
	private String nome;
	@JoinTable(name = "viagemregiao", joinColumns = {
    	@JoinColumn(name = "idregiao", referencedColumnName = "id")}, inverseJoinColumns = {
    	@JoinColumn(name = "idviagem", referencedColumnName = "id")})
    @ManyToMany
	private Collection<Viagem> viagemCollection;

	public Regiao() {
	}

	public Regiao(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlTransient
	public Collection<Viagem> getViagemCollection() {
		return viagemCollection;
	}

	public void setViagemCollection(Collection<Viagem> viagemCollection) {
		this.viagemCollection = viagemCollection;
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
		if (!(object instanceof Regiao)) {
			return false;
		}
		Regiao other = (Regiao) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Regiao[ id=" + id + " ]";
	}
	
}
