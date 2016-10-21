/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "headquarter_info", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "HeadquarterInfo.findAll", query = "SELECT h FROM HeadquarterInfo h"),
    @NamedQuery(name = "HeadquarterInfo.findByHeadquarterid", query = "SELECT h FROM HeadquarterInfo h WHERE h.headquarterid = :headquarterid"),
    @NamedQuery(name = "HeadquarterInfo.findByBuildingname", query = "SELECT h FROM HeadquarterInfo h WHERE h.buildingname = :buildingname"),
    @NamedQuery(name = "HeadquarterInfo.findByRoomamount", query = "SELECT h FROM HeadquarterInfo h WHERE h.roomamount = :roomamount"),
    @NamedQuery(name = "HeadquarterInfo.findByMonthrent", query = "SELECT h FROM HeadquarterInfo h WHERE h.monthrent = :monthrent")})
public class HeadquarterInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Headquarter_id", nullable = false)
    private Integer headquarterid;
    @Basic(optional = false)
    @Column(name = "Building_name", nullable = false, length = 255)
    private String buildingname;
    @Column(name = "Room_amount")
    private Integer roomamount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Month_rent", precision = 22)
    private Double monthrent;
    @JoinTable(name = "headquarter_location", joinColumns = {
        @JoinColumn(name = "Headquarter_id", referencedColumnName = "Headquarter_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "Country", referencedColumnName = "Country", nullable = false),
        @JoinColumn(name = "Postalcode", referencedColumnName = "Postalcode", nullable = false)})
    @ManyToMany
    private Collection<Address> addressCollection;
    @OneToMany(mappedBy = "headquarterid")
    private Collection<Project> projectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "headquarterid")
    private Collection<PositieEmployer> positieEmployerCollection;

    public HeadquarterInfo() {
    }

    public HeadquarterInfo(Integer headquarterid) {
        this.headquarterid = headquarterid;
    }

    public HeadquarterInfo(Integer headquarterid, String buildingname) {
        this.headquarterid = headquarterid;
        this.buildingname = buildingname;
    }

    public Integer getHeadquarterid() {
        return headquarterid;
    }

    public void setHeadquarterid(Integer headquarterid) {
        this.headquarterid = headquarterid;
    }

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    public Integer getRoomamount() {
        return roomamount;
    }

    public void setRoomamount(Integer roomamount) {
        this.roomamount = roomamount;
    }

    public Double getMonthrent() {
        return monthrent;
    }

    public void setMonthrent(Double monthrent) {
        this.monthrent = monthrent;
    }

    public Collection<Address> getAddressCollection() {
        return addressCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
    }

    public Collection<Project> getProjectCollection() {
        return projectCollection;
    }

    public void setProjectCollection(Collection<Project> projectCollection) {
        this.projectCollection = projectCollection;
    }

    public Collection<PositieEmployer> getPositieEmployerCollection() {
        return positieEmployerCollection;
    }

    public void setPositieEmployerCollection(Collection<PositieEmployer> positieEmployerCollection) {
        this.positieEmployerCollection = positieEmployerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (headquarterid != null ? headquarterid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HeadquarterInfo)) {
            return false;
        }
        HeadquarterInfo other = (HeadquarterInfo) object;
        if ((this.headquarterid == null && other.headquarterid != null) || (this.headquarterid != null && !this.headquarterid.equals(other.headquarterid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.HeadquarterInfo[ headquarterid=" + headquarterid + " ]";
    }
    
}
