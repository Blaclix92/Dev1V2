/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Benny
 */
@Entity
@Table(name = "project", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByProjectid", query = "SELECT p FROM Project p WHERE p.projectid = :projectid"),
    @NamedQuery(name = "Project.findByProjectname", query = "SELECT p FROM Project p WHERE p.projectname = :projectname"),
    @NamedQuery(name = "Project.findByBudget", query = "SELECT p FROM Project p WHERE p.budget = :budget"),
    @NamedQuery(name = "Project.findByAllocatedhour", query = "SELECT p FROM Project p WHERE p.allocatedhour = :allocatedhour"),
    @NamedQuery(name = "Project.findByCompanyname", query = "SELECT p FROM Project p WHERE p.companyname = :companyname")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Project_id", nullable = false)
    private Integer projectid;
    @Column(name = "Project_name", length = 255)
    private String projectname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Budget", precision = 22)
    private Double budget;
    @Column(name = "Allocated_hour")
    private Integer allocatedhour;
    @Column(name = "Company_name", length = 255)
    private String companyname;
    @JoinColumn(name = "Headquarter_id", referencedColumnName = "Headquarter_id")
    @ManyToOne
    private HeadquarterInfo headquarterid;
    @OneToMany(mappedBy = "projectid")
    private Collection<PositieEmployer> positieEmployerCollection;

    public Project() {
    }

    public Project(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getAllocatedhour() {
        return allocatedhour;
    }

    public void setAllocatedhour(Integer allocatedhour) {
        this.allocatedhour = allocatedhour;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public HeadquarterInfo getHeadquarterid() {
        return headquarterid;
    }

    public void setHeadquarterid(HeadquarterInfo headquarterid) {
        this.headquarterid = headquarterid;
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
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Project[ projectid=" + projectid + " ]";
    }
    
}
