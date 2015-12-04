/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fernando Spanhol
 */
@Entity
@Table(name = "geo")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Geo.findAll", query = "SELECT g FROM Geo g"),
	@NamedQuery(name = "Geo.findByPlaceId", query = "SELECT g FROM Geo g WHERE g.placeId = :placeId"),
	@NamedQuery(name = "Geo.findByLat", query = "SELECT g FROM Geo g WHERE g.lat = :lat"),
	@NamedQuery(name = "Geo.findByLng", query = "SELECT g FROM Geo g WHERE g.lng = :lng"),
	@NamedQuery(name = "Geo.findByNortheastlat", query = "SELECT g FROM Geo g WHERE g.northeastlat = :northeastlat"),
	@NamedQuery(name = "Geo.findByNortheastlng", query = "SELECT g FROM Geo g WHERE g.northeastlng = :northeastlng"),
	@NamedQuery(name = "Geo.findBySouthwestlat", query = "SELECT g FROM Geo g WHERE g.southwestlat = :southwestlat"),
	@NamedQuery(name = "Geo.findBySouthwestlng", query = "SELECT g FROM Geo g WHERE g.southwestlng = :southwestlng"),
	@NamedQuery(name = "Geo.findByCep", query = "SELECT g FROM Geo g WHERE g.cep = :cep"),
	@NamedQuery(name = "Geo.findByCidade", query = "SELECT g FROM Geo g WHERE g.cidade = :cidade"),
	@NamedQuery(name = "Geo.findByEstado", query = "SELECT g FROM Geo g WHERE g.estado = :estado"),
	@NamedQuery(name = "Geo.findByBairro", query = "SELECT g FROM Geo g WHERE g.bairro = :bairro"),
	@NamedQuery(name = "Geo.findByRua", query = "SELECT g FROM Geo g WHERE g.rua = :rua"),
	@NamedQuery(name = "Geo.findByNumero", query = "SELECT g FROM Geo g WHERE g.numero = :numero"),
	@NamedQuery(name = "Geo.findByLocationType", query = "SELECT g FROM Geo g WHERE g.locationType = :locationType")})
public class Geo implements Serializable {
	@Column(name = "numero")
	private String numero;
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "place_id")
	private String placeId;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "lat")
	private Double lat;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "northeastlat")
	private Double northeastlat;
	@Column(name = "northeastlng")
	private Double northeastlng;
	@Column(name = "southwestlat")
	private Double southwestlat;
	@Column(name = "southwestlng")
	private Double southwestlng;
	@Column(name = "cep")
	private Integer cep;
	@Column(name = "cidade")
	private String cidade;
	@Column(name = "estado")
	private String estado;
	@Column(name = "bairro")
	private String bairro;
	@Column(name = "rua")
	private String rua;
	@Column(name = "location_type")
	private String locationType;
	@JoinColumn(name = "codigo_cli", referencedColumnName = "codigo")
    @ManyToOne
	private Cliente codigoCli;

	public Geo() {
	}

	public Geo(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getNortheastlat() {
		return northeastlat;
	}

	public void setNortheastlat(Double northeastlat) {
		this.northeastlat = northeastlat;
	}

	public Double getNortheastlng() {
		return northeastlng;
	}

	public void setNortheastlng(Double northeastlng) {
		this.northeastlng = northeastlng;
	}

	public Double getSouthwestlat() {
		return southwestlat;
	}

	public void setSouthwestlat(Double southwestlat) {
		this.southwestlat = southwestlat;
	}

	public Double getSouthwestlng() {
		return southwestlng;
	}

	public void setSouthwestlng(Double southwestlng) {
		this.southwestlng = southwestlng;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}


	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Cliente getCodigoCli() {
		return codigoCli;
	}

	public void setCodigoCli(Cliente codigoCli) {
		this.codigoCli = codigoCli;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (placeId != null ? placeId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Geo)) {
			return false;
		}
		Geo other = (Geo) object;
		if ((this.placeId == null && other.placeId != null) || (this.placeId != null && !this.placeId.equals(other.placeId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.Geo[ placeId=" + placeId + " ]";
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}
