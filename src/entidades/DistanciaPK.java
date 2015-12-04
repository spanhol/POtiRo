package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Fernando Spanhol
 */
@Embeddable
public class DistanciaPK implements Serializable {
	@Basic(optional = false)
    @Column(name = "placea")
	private String placea;
	@Basic(optional = false)
    @Column(name = "placeb")
	private String placeb;

	public DistanciaPK() {
	}

	public DistanciaPK(String placea, String placeb) {
		this.placea = placea;
		this.placeb = placeb;
	}

	public String getPlacea() {
		return placea;
	}

	public void setPlacea(String placea) {
		this.placea = placea;
	}

	public String getPlaceb() {
		return placeb;
	}

	public void setPlaceb(String placeb) {
		this.placeb = placeb;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (placea != null ? placea.hashCode() : 0);
		hash += (placeb != null ? placeb.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof DistanciaPK)) {
			return false;
		}
		DistanciaPK other = (DistanciaPK) object;
		if ((this.placea == null && other.placea != null) || (this.placea != null && !this.placea.equals(other.placea))) {
			return false;
		}
		if ((this.placeb == null && other.placeb != null) || (this.placeb != null && !this.placeb.equals(other.placeb))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.DistanciaPK[ placea=" + placea + ", placeb=" + placeb + " ]";
	}
	
}
