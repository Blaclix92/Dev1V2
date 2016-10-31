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
@Table(name = "positie_description")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PositieDescription.findAll", query = "SELECT p FROM PositieDescription p"),
    @NamedQuery(name = "PositieDescription.findByPositieid", query = "SELECT p FROM PositieDescription p WHERE p.positieid = :positieid"),
    @NamedQuery(name = "PositieDescription.findByPositiename", query = "SELECT p FROM PositieDescription p WHERE p.positiename = :positiename"),
    @NamedQuery(name = "PositieDescription.findByPositiedescription", query = "SELECT p FROM PositieDescription p WHERE p.positiedescription = :positiedescription"),
    @NamedQuery(name = "PositieDescription.findByHourfee", query = "SELECT p FROM PositieDescription p WHERE p.hourfee = :hourfee"),
    @NamedQuery(name = "PositieDescription.findByAmountshours", query = "SELECT p FROM PositieDescription p WHERE p.amountshours = :amountshours")})
public class PositieDescription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Positie_id")
    private Integer positieid;
    @Basic(optional = false)
    @Column(name = "Positie_name")
    private String positiename;
    @Column(name = "Positie_description")
    private String positiedescription;
    @Column(name = "Hour_fee")
    private Integer hourfee;
    @Column(name = "Amounts_hours")
    private Integer amountshours;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "positieDescription")
    private Collection<PositieEmployer> positieEmployerCollection;

    public PositieDescription() {
    }

    public PositieDescription(Integer positieid) {
        this.positieid = positieid;
    }

    public PositieDescription(Integer positieid, String positiename) {
        this.positieid = positieid;
        this.positiename = positiename;
    }

    public Integer getPositieid() {
        return positieid;
    }

    public void setPositieid(Integer positieid) {
        this.positieid = positieid;
    }

    public String getPositiename() {
        return positiename;
    }

    public void setPositiename(String positiename) {
        this.positiename = positiename;
    }

    public String getPositiedescription() {
        return positiedescription;
    }

    public void setPositiedescription(String positiedescription) {
        this.positiedescription = positiedescription;
    }

    public Integer getHourfee() {
        return hourfee;
    }

    public void setHourfee(Integer hourfee) {
        this.hourfee = hourfee;
    }

    public Integer getAmountshours() {
        return amountshours;
    }

    public void setAmountshours(Integer amountshours) {
        this.amountshours = amountshours;
    }

    @XmlTransient
    public Collection<PositieEmployer> getPositieEmployerCollection() {
        return positieEmployerCollection;
    }

    public void setPositieEmployerCollection(Collection<PositieEmployer> positieEmployerCollection) {
        this.positieEmployerCollection = positieEmployerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (positieid != null ? positieid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PositieDescription)) {
            return false;
        }
        PositieDescription other = (PositieDescription) object;
        if ((this.positieid == null && other.positieid != null) || (this.positieid != null && !this.positieid.equals(other.positieid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.PositieDescription[ positieid=" + positieid + " ]";
    }
    
}
