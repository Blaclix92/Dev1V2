/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Benny
 */
@Embeddable
public class WorkingAddressPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Bsn", nullable = false)
    private int bsn;
    @Basic(optional = false)
    @Column(name = "Country", nullable = false, length = 255)
    private String country;
    @Basic(optional = false)
    @Column(name = "Postalcode", nullable = false, length = 255)
    private String postalcode;

    public WorkingAddressPK() {
    }

    public WorkingAddressPK(int bsn, String country, String postalcode) {
        this.bsn = bsn;
        this.country = country;
        this.postalcode = postalcode;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) bsn;
        hash += (country != null ? country.hashCode() : 0);
        hash += (postalcode != null ? postalcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkingAddressPK)) {
            return false;
        }
        WorkingAddressPK other = (WorkingAddressPK) object;
        if (this.bsn != other.bsn) {
            return false;
        }
        if ((this.country == null && other.country != null) || (this.country != null && !this.country.equals(other.country))) {
            return false;
        }
        if ((this.postalcode == null && other.postalcode != null) || (this.postalcode != null && !this.postalcode.equals(other.postalcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.WorkingAddressPK[ bsn=" + bsn + ", country=" + country + ", postalcode=" + postalcode + " ]";
    }
    
}
