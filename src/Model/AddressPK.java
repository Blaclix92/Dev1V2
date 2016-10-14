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
 * @author Donovan
 */
@Embeddable
public class AddressPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Country", nullable = false, length = 255)
    private String country;
    @Basic(optional = false)
    @Column(name = "Postalcode", nullable = false, length = 255)
    private String postalcode;

    public AddressPK() {
    }

    public AddressPK(String country, String postalcode) {
        this.country = country;
        this.postalcode = postalcode;
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
        hash += (country != null ? country.hashCode() : 0);
        hash += (postalcode != null ? postalcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AddressPK)) {
            return false;
        }
        AddressPK other = (AddressPK) object;
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
        return "Model.AddressPK[ country=" + country + ", postalcode=" + postalcode + " ]";
    }
    
}
