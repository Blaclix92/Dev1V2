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

/**
 *
 * @author Donovan
 */
@Entity
@Table(name = "school", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "School.findAll", query = "SELECT s FROM School s"),
    @NamedQuery(name = "School.findBySchoolid", query = "SELECT s FROM School s WHERE s.schoolid = :schoolid"),
    @NamedQuery(name = "School.findBySchoolname", query = "SELECT s FROM School s WHERE s.schoolname = :schoolname"),
    @NamedQuery(name = "School.findBySchoollevel", query = "SELECT s FROM School s WHERE s.schoollevel = :schoollevel")})
public class School implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "School_id", nullable = false)
    private Integer schoolid;
    @Basic(optional = false)
    @Column(name = "School_name", nullable = false, length = 255)
    private String schoolname;
    @Basic(optional = false)
    @Column(name = "School_level", nullable = false, length = 255)
    private String schoollevel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private Collection<Degree> degreeCollection;

    public School() {
    }

    public School(Integer schoolid) {
        this.schoolid = schoolid;
    }

    public School(Integer schoolid, String schoolname, String schoollevel) {
        this.schoolid = schoolid;
        this.schoolname = schoolname;
        this.schoollevel = schoollevel;
    }

    public Integer getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Integer schoolid) {
        this.schoolid = schoolid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSchoollevel() {
        return schoollevel;
    }

    public void setSchoollevel(String schoollevel) {
        this.schoollevel = schoollevel;
    }

    public Collection<Degree> getDegreeCollection() {
        return degreeCollection;
    }

    public void setDegreeCollection(Collection<Degree> degreeCollection) {
        this.degreeCollection = degreeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolid != null ? schoolid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        if ((this.schoolid == null && other.schoolid != null) || (this.schoolid != null && !this.schoolid.equals(other.schoolid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.School[ schoolid=" + schoolid + " ]";
    }
    
}
