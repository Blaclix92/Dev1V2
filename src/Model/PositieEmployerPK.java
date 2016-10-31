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
public class PositieEmployerPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Positie_id")
    private int positieid;
    @Basic(optional = false)
    @Column(name = "Bsn")
    private int bsn;

    public PositieEmployerPK() {
    }

    public PositieEmployerPK(int positieid, int bsn) {
        this.positieid = positieid;
        this.bsn = bsn;
    }

    public int getPositieid() {
        return positieid;
    }

    public void setPositieid(int positieid) {
        this.positieid = positieid;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) positieid;
        hash += (int) bsn;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PositieEmployerPK)) {
            return false;
        }
        PositieEmployerPK other = (PositieEmployerPK) object;
        if (this.positieid != other.positieid) {
            return false;
        }
        if (this.bsn != other.bsn) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.PositieEmployerPK[ positieid=" + positieid + ", bsn=" + bsn + " ]";
    }
    
}
