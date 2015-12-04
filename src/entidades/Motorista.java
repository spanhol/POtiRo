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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "motorista")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Motorista.findAll", query = "SELECT m FROM Motorista m"),
	@NamedQuery(name = "Motorista.findById", query = "SELECT m FROM Motorista m WHERE m.id = :id"),
	@NamedQuery(name = "Motorista.findByNome", query = "SELECT m FROM Motorista m WHERE m.nome = :nome"),
	@NamedQuery(name = "Motorista.findByCtps", query = "SELECT m FROM Motorista m WHERE m.ctps = :ctps"),
	@NamedQuery(name = "Motorista.findByCnh", query = "SELECT m FROM Motorista m WHERE m.cnh = :cnh"),
	@NamedQuery(name = "Motorista.findByCpf", query = "SELECT m FROM Motorista m WHERE m.cpf = :cpf"),
	@NamedQuery(name = "Motorista.findByRg", query = "SELECT m FROM Motorista m WHERE m.rg = :rg"),
	@NamedQuery(name = "Motorista.findByAdmissao", query = "SELECT m FROM Motorista m WHERE m.admissao = :admissao"),
	@NamedQuery(name = "Motorista.findByDesativado", query = "SELECT m FROM Motorista m WHERE m.desativado = :desativado"),
	@NamedQuery(name = "Motorista.findByAtivo", query = "SELECT m FROM Motorista m WHERE m.ativo = :ativo")})
public class Motorista implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private Integer id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "ctps")
	private String ctps;
	@Column(name = "cnh")
	private String cnh;
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "rg")
	private String rg;
	@Column(name = "admissao")
    @Temporal(TemporalType.DATE)
	private Date admissao;
	@Column(name = "desativado")
    @Temporal(TemporalType.DATE)
	private Date desativado;
	@Column(name = "ativo")
	private Boolean ativo;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idmotorista")
	private Collection<Viagem> viagemCollection;
	@JoinColumn(name = "favcaminhaoid", referencedColumnName = "id")
    @ManyToOne
	private Caminhao favcaminhaoid;

	public Motorista() {
	}

	public Motorista(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getAdmissao() {
		return admissao;
	}

	public void setAdmissao(Date admissao) {
		this.admissao = admissao;
	}

	public Date getDesativado() {
		return desativado;
	}

	public void setDesativado(Date desativado) {
		this.desativado = desativado;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@XmlTransient
	public Collection<Viagem> getViagemCollection() {
		return viagemCollection;
	}

	public void setViagemCollection(Collection<Viagem> viagemCollection) {
		this.viagemCollection = viagemCollection;
	}

	public Caminhao getFavcaminhaoid() {
		return favcaminhaoid;
	}

	public void setFavcaminhaoid(Caminhao favcaminhaoid) {
		this.favcaminhaoid = favcaminhaoid;
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
		if (!(object instanceof Motorista)) {
			return false;
		}
		Motorista other = (Motorista) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Motorista[ id=" + id + " ]";
	}
	
}
