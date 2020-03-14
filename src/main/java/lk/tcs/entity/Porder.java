/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "porder")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Porder.findAll", query = "SELECT p FROM Porder p")})
public class Porder implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "porderId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Goodrecive> goodreciveList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "orderdate")
   // @Temporal(TemporalType.DATE)
    private LocalDate orderdate;
    @Column(name = "requiredate")
    //@Temporal(TemporalType.DATE)
    private LocalDate requiredate;
    @Column(name = "code")
    private String code;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "porderId", fetch = FetchType.LAZY,orphanRemoval = true)
    //@Fetch(value = FetchMode.SUBSELECT)
    private List<Porderitem> porderitemList;

    @JoinColumn(name = "Supplier_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Supplier supplierid;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "orderstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Orderstatus orderstatusId;

    public Porder(Integer id,String code) {
        this.id=id;
        this.code = code;
    }

    public Porder() {
    }

    public Porder(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public LocalDate getRequiredate() {
        return requiredate;
    }

    public void setRequiredate(LocalDate requiredate) {
        this.requiredate = requiredate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlTransient
    public List<Porderitem> getPorderitemList() {
        return porderitemList;
    }

    public void setPorderitemList(List<Porderitem> porderitemList) {
        this.porderitemList = porderitemList;
    }

    public Supplier getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Supplier supplierid) {
        this.supplierid = supplierid;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Orderstatus getOrderstatusId() {
        return orderstatusId;
    }

    public void setOrderstatusId(Orderstatus orderstatusId) {
        this.orderstatusId = orderstatusId;
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
        if (!(object instanceof Porder)) {
            return false;
        }
        Porder other = (Porder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Porder[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Goodrecive> getGoodreciveList() {
        return goodreciveList;
    }

    public void setGoodreciveList(List<Goodrecive> goodreciveList) {
        this.goodreciveList = goodreciveList;
    }
    
}
