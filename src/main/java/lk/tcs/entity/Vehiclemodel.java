/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "vehiclemodel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehiclemodel.findAll", query = "SELECT v FROM Vehiclemodel v")})
public class Vehiclemodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiclemodelId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Servicerequest> servicerequestList;
    @JoinColumn(name = "vehiclebrand_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Vehiclebrand vehiclebrandId;

    public Vehiclemodel(String name ,Vehiclebrand vehiclebrandId) {
        this.vehiclebrandId=vehiclebrandId;
        this.name=name;
    }

    public Vehiclemodel(Integer id,String name) {
        this.name = name;
        this.id=id;
    }

    public Vehiclemodel() {
    }

    public Vehiclemodel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Servicerequest> getServicerequestList() {
        return servicerequestList;
    }

    public void setServicerequestList(List<Servicerequest> servicerequestList) {
        this.servicerequestList = servicerequestList;
    }

    public Vehiclebrand getVehiclebrandId() {
        return vehiclebrandId;
    }

    public void setVehiclebrandId(Vehiclebrand vehiclebrandId) {
        this.vehiclebrandId = vehiclebrandId;
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
        if (!(object instanceof Vehiclemodel)) {
            return false;
        }
        Vehiclemodel other = (Vehiclemodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Vehiclemodel[ id=" + id + " ]";
    }
    
}
