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
public class DegreePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Degree_id")
    private int degreeid;
    @Basic(optional = false)
    @Column(name = "Bsn")
    private int bsn;
    @Basic(optional = false)
    @Column(name = "School_id")
    private int schoolid;

    public DegreePK() {
    }

    public DegreePK(int degreeid, int bsn, int schoolid) {
        this.degreeid = degreeid;
        this.bsn = bsn;
        this.schoolid = schoolid;
    }

    public int getDegreeid() {
        return degreeid;
    }

    public void setDegreeid(int degreeid) {
        this.degreeid = degreeid;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    public int getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(int schoolid) {
        this.schoolid = schoolid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) degreeid;
        hash += (int) bsn;
        hash += (int) schoolid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DegreePK)) {
            return false;
        }
        DegreePK other = (DegreePK) object;
        if (this.degreeid != other.degreeid) {
            return false;
        }
        if (this.bsn != other.bsn) {
            return false;
        }
        if (this.schoolid != other.schoolid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.DegreePK[ degreeid=" + degreeid + ", bsn=" + bsn + ", schoolid=" + schoolid + " ]";
    }
    
}
