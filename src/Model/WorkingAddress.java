/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "working_address", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "WorkingAddress.findAll", query = "SELECT w FROM WorkingAddress w"),
    @NamedQuery(name = "WorkingAddress.findByBsn", query = "SELECT w FROM WorkingAddress w WHERE w.workingAddressPK.bsn = :bsn"),
    @NamedQuery(name = "WorkingAddress.findByCountry", query = "SELECT w FROM WorkingAddress w WHERE w.workingAddressPK.country = :country"),
    @NamedQuery(name = "WorkingAddress.findByPostalcode", query = "SELECT w FROM WorkingAddress w WHERE w.workingAddressPK.postalcode = :postalcode"),
    @NamedQuery(name = "WorkingAddress.findByStartdate", query = "SELECT w FROM WorkingAddress w WHERE w.startdate = :startdate"),
    @NamedQuery(name = "WorkingAddress.findByEnddate", query = "SELECT w FROM WorkingAddress w WHERE w.enddate = :enddate")})
public class WorkingAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WorkingAddressPK workingAddressPK;
    @Column(name = "Start_date")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "End_date")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @JoinColumns({
        @JoinColumn(name = "Country", referencedColumnName = "Country", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "Postalcode", referencedColumnName = "Postalcode", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Address address;
    @JoinColumn(name = "Bsn", referencedColumnName = "Bsn", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public WorkingAddress() {
    }

    public WorkingAddress(WorkingAddressPK workingAddressPK) {
        this.workingAddressPK = workingAddressPK;
    }

    public WorkingAddress(int bsn, String country, String postalcode) {
        this.workingAddressPK = new WorkingAddressPK(bsn, country, postalcode);
    }

    public WorkingAddressPK getWorkingAddressPK() {
        return workingAddressPK;
    }

    public void setWorkingAddressPK(WorkingAddressPK workingAddressPK) {
        this.workingAddressPK = workingAddressPK;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workingAddressPK != null ? workingAddressPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkingAddress)) {
            return false;
        }
        WorkingAddress other = (WorkingAddress) object;
        if ((this.workingAddressPK == null && other.workingAddressPK != null) || (this.workingAddressPK != null && !this.workingAddressPK.equals(other.workingAddressPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.WorkingAddress[ workingAddressPK=" + workingAddressPK + " ]";
    }
    
}
