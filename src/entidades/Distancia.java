/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "distancia")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Distancia.findAll", query = "SELECT d FROM Distancia d"),
	@NamedQuery(name = "Distancia.findByPlacea", query = "SELECT d FROM Distancia d WHERE d.distanciaPK.placea = :placea"),
	@NamedQuery(name = "Distancia.findByPlaceb", query = "SELECT d FROM Distancia d WHERE d.distanciaPK.placeb = :placeb"),
	@NamedQuery(name = "Distancia.findByDistancia", query = "SELECT d FROM Distancia d WHERE d.distancia = :distancia")})
public class Distancia implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected DistanciaPK distanciaPK;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "distancia")
	private Double distancia;

	public Distancia() {
	}

	public Distancia(DistanciaPK distanciaPK) {
		this.distanciaPK = distanciaPK;
	}

	public Distancia(String placea, String placeb) {
		this.distanciaPK = new DistanciaPK(placea, placeb);
	}

	public DistanciaPK getDistanciaPK() {
		return distanciaPK;
	}

	public void setDistanciaPK(DistanciaPK distanciaPK) {
		this.distanciaPK = distanciaPK;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (distanciaPK != null ? distanciaPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Distancia)) {
			return false;
		}
		Distancia other = (Distancia) object;
		return !((this.distanciaPK == null && other.distanciaPK != null) || (this.distanciaPK != null && !this.distanciaPK.equals(other.distanciaPK)));
	}

	@Override
	public String toString() {
		return "entidades.Distancia[ distanciaPK=" + distanciaPK + " ]";
	}

}
