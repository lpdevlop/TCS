/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "linevehiclecategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Linevehiclecategory.findAll", query = "SELECT l FROM Linevehiclecategory l")})
public class Linevehiclecategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "avgduration")
    private String avgduration;
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Line lineId;
    @JoinColumn(name = "vehiclecategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehiclecategory vehiclecategoryId;
    @JoinColumn(name = "servicetype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Servicetype servicetypeId;

    public Linevehiclecategory(Line lineId) {
        this.lineId = lineId;
    }

    public Linevehiclecategory() {
    }

    public Linevehiclecategory(Vehiclecategory vehiclecategoryId) {
        this.vehiclecategoryId = vehiclecategoryId;
    }

    public Linevehiclecategory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvgduration() {
        return avgduration;
    }

    public void setAvgduration(String avgduration) {
        this.avgduration = avgduration;
    }

    public Line getLineId() {
        return lineId;
    }

    public void setLineId(Line lineId) {
        this.lineId = lineId;
    }

    public Vehiclecategory getVehiclecategoryId() {
        return vehiclecategoryId;
    }

    public void setVehiclecategoryId(Vehiclecategory vehiclecategoryId) {
        this.vehiclecategoryId = vehiclecategoryId;
    }

    public Servicetype getServicetypeId() {
        return servicetypeId;
    }

    public void setServicetypeId(Servicetype servicetypeId) {
        this.servicetypeId = servicetypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Linevehiclecategory)) {
            return false;
        }
        Linevehiclecategory other = (Linevehiclecategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Linevehiclecategory[ id=" + id + " ]";
    }
    
}
