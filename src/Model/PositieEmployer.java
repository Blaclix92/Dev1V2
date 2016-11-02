/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
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
@Table(name = "positie_employer", catalog = "dev2", schema = "")
@NamedQueries({
    @NamedQuery(name = "PositieEmployer.findAll", query = "SELECT p FROM PositieEmployer p"),
    @NamedQuery(name = "PositieEmployer.findByPositieid", query = "SELECT p FROM PositieEmployer p WHERE p.positieEmployerPK.positieid = :positieid"),
    @NamedQuery(name = "PositieEmployer.findByBsn", query = "SELECT p FROM PositieEmployer p WHERE p.positieEmployerPK.bsn = :bsn")})
public class PositieEmployer implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PositieEmployerPK positieEmployerPK;
    @JoinColumn(name = "Headquarter_id", referencedColumnName = "Headquarter_id", nullable = false)
    @ManyToOne(optional = false)
    private HeadquarterInfo headquarterid;
    @JoinColumn(name = "Project_id", referencedColumnName = "Project_id")
    @ManyToOne
    private Project projectid;
    @JoinColumn(name = "Positie_id", referencedColumnName = "Positie_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PositieDescription positieDescription;
    @JoinColumn(name = "Bsn", referencedColumnName = "Bsn", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    public PositieEmployer() {
    }

    public PositieEmployer(PositieEmployerPK positieEmployerPK) {
        this.positieEmployerPK = positieEmployerPK;
    }

    public PositieEmployer(int positieid, int bsn) {
        this.positieEmployerPK = new PositieEmployerPK(positieid, bsn);
    }

    public PositieEmployerPK getPositieEmployerPK() {
        return positieEmployerPK;
    }

    public void setPositieEmployerPK(PositieEmployerPK positieEmployerPK) {
        this.positieEmployerPK = positieEmployerPK;
    }

    public HeadquarterInfo getHeadquarterid() {
        return headquarterid;
    }

    public void setHeadquarterid(HeadquarterInfo headquarterid) {
        this.headquarterid = headquarterid;
    }

    public Project getProjectid() {
        return projectid;
    }

    public void setProjectid(Project projectid) {
        this.projectid = projectid;
    }

    public PositieDescription getPositieDescription() {
        return positieDescription;
    }

    public void setPositieDescription(PositieDescription positieDescription) {
        this.positieDescription = positieDescription;
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
        hash += (positieEmployerPK != null ? positieEmployerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PositieEmployer)) {
            return false;
        }
        PositieEmployer other = (PositieEmployer) object;
        if ((this.positieEmployerPK == null && other.positieEmployerPK != null) || (this.positieEmployerPK != null && !this.positieEmployerPK.equals(other.positieEmployerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Models.PositieEmployer[ positieEmployerPK=" + positieEmployerPK + " ]";
    }
    
}
