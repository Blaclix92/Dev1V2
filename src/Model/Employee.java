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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByBsn", query = "SELECT e FROM Employee e WHERE e.bsn = :bsn"),
    @NamedQuery(name = "Employee.findByEmployeename", query = "SELECT e FROM Employee e WHERE e.employeename = :employeename"),
    @NamedQuery(name = "Employee.findBySurname", query = "SELECT e FROM Employee e WHERE e.surname = :surname")})
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Bsn")
    private Integer bsn;
    @Basic(optional = false)
    @Column(name = "Employee_name")
    private String employeename;
    @Basic(optional = false)
    @Column(name = "Surname")
    private String surname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<Degree> degreeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<PositieEmployer> positieEmployerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private Collection<WorkingAddress> workingAddressCollection;

    public Employee() {
    }

    public Employee(Integer bsn) {
        this.bsn = bsn;
    }

    public Employee(Integer bsn, String employeename, String surname) {
        this.bsn = bsn;
        this.employeename = employeename;
        this.surname = surname;
    }

    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlTransient
    public Collection<Degree> getDegreeCollection() {
        return degreeCollection;
    }

    public void setDegreeCollection(Collection<Degree> degreeCollection) {
        this.degreeCollection = degreeCollection;
    }

    @XmlTransient
    public Collection<PositieEmployer> getPositieEmployerCollection() {
        return positieEmployerCollection;
    }

    public void setPositieEmployerCollection(Collection<PositieEmployer> positieEmployerCollection) {
        this.positieEmployerCollection = positieEmployerCollection;
    }

    @XmlTransient
    public Collection<WorkingAddress> getWorkingAddressCollection() {
        return workingAddressCollection;
    }

    public void setWorkingAddressCollection(Collection<WorkingAddress> workingAddressCollection) {
        this.workingAddressCollection = workingAddressCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bsn != null ? bsn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.bsn == null && other.bsn != null) || (this.bsn != null && !this.bsn.equals(other.bsn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Employee[ bsn=" + bsn + " ]";
    }
    
}
