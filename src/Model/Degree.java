/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "degree", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Degree.findAll", query = "SELECT d FROM Degree d"),
    @NamedQuery(name = "Degree.findByDegreeid", query = "SELECT d FROM Degree d WHERE d.degreePK.degreeid = :degreeid"),
    @NamedQuery(name = "Degree.findByBsn", query = "SELECT d FROM Degree d WHERE d.degreePK.bsn = :bsn"),
    @NamedQuery(name = "Degree.findBySchoolid", query = "SELECT d FROM Degree d WHERE d.degreePK.schoolid = :schoolid"),
    @NamedQuery(name = "Degree.findByCoursename", query = "SELECT d FROM Degree d WHERE d.coursename = :coursename")})
public class Degree implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DegreePK degreePK;
    @Basic(optional = false)
    @Column(name = "Course_name", nullable = false, length = 255)
    private String coursename;
    @JoinColumn(name = "School_id", referencedColumnName = "School_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private School school;
    @JoinColumn(name = "Bsn", referencedColumnName = "Bsn", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public Degree() {
    }

    public Degree(DegreePK degreePK) {
        this.degreePK = degreePK;
    }

    public Degree(DegreePK degreePK, String coursename) {
        this.degreePK = degreePK;
        this.coursename = coursename;
    }

    public Degree(int degreeid, int bsn, int schoolid) {
        this.degreePK = new DegreePK(degreeid, bsn, schoolid);
    }

    public DegreePK getDegreePK() {
        return degreePK;
    }

    public void setDegreePK(DegreePK degreePK) {
        this.degreePK = degreePK;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
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
        hash += (degreePK != null ? degreePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Degree)) {
            return false;
        }
        Degree other = (Degree) object;
        if ((this.degreePK == null && other.degreePK != null) || (this.degreePK != null && !this.degreePK.equals(other.degreePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Degree[ degreePK=" + degreePK + " ]";
    }
    
}
