/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "address", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findByCountry", query = "SELECT a FROM Address a WHERE a.addressPK.country = :country"),
    @NamedQuery(name = "Address.findByPostalcode", query = "SELECT a FROM Address a WHERE a.addressPK.postalcode = :postalcode"),
    @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
    @NamedQuery(name = "Address.findByStreetname", query = "SELECT a FROM Address a WHERE a.streetname = :streetname"),
    @NamedQuery(name = "Address.findByBuildingnumber", query = "SELECT a FROM Address a WHERE a.buildingnumber = :buildingnumber"),
    @NamedQuery(name = "Address.findByBuildingletter", query = "SELECT a FROM Address a WHERE a.buildingletter = :buildingletter")})
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AddressPK addressPK;
    @Column(name = "City", length = 255)
    private String city;
    @Column(name = "Street_name", length = 255)
    private String streetname;
    @Column(name = "Building_number")
    private Integer buildingnumber;
    @Column(name = "Building_letter")
    private Character buildingletter;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
    private Collection<HeadquarterInfo> headquarterInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
    private Collection<WorkingAddress> workingAddressCollection;

    public Address() {
    }

    public Address(AddressPK addressPK) {
        this.addressPK = addressPK;
    }

    public Address(String country, String postalcode) {
        this.addressPK = new AddressPK(country, postalcode);
    }

    public AddressPK getAddressPK() {
        return addressPK;
    }

    public void setAddressPK(AddressPK addressPK) {
        this.addressPK = addressPK;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public Integer getBuildingnumber() {
        return buildingnumber;
    }

    public void setBuildingnumber(Integer buildingnumber) {
        this.buildingnumber = buildingnumber;
    }

    public Character getBuildingletter() {
        return buildingletter;
    }

    public void setBuildingletter(Character buildingletter) {
        this.buildingletter = buildingletter;
    }

    public Collection<HeadquarterInfo> getHeadquarterInfoCollection() {
        return headquarterInfoCollection;
    }

    public void setHeadquarterInfoCollection(Collection<HeadquarterInfo> headquarterInfoCollection) {
        this.headquarterInfoCollection = headquarterInfoCollection;
    }

    public Collection<WorkingAddress> getWorkingAddressCollection() {
        return workingAddressCollection;
    }

    public void setWorkingAddressCollection(Collection<WorkingAddress> workingAddressCollection) {
        this.workingAddressCollection = workingAddressCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressPK != null ? addressPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressPK == null && other.addressPK != null) || (this.addressPK != null && !this.addressPK.equals(other.addressPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.Address[ addressPK=" + addressPK + " ]";
    }
    
}
