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
import java.math.BigDecimal;
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
@Table(name = "goodrecive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goodrecive.findAll", query = "SELECT g FROM Goodrecive g")})
public class Goodrecive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    @Column(name = "recivedate")
    //@Temporal(TemporalType.DATE)
    private LocalDate recivedate;
    @Column(name = "total")
     private BigDecimal total;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodreciveId", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Goodreciveitem> goodreciveitemList;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "gooodrecivestatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Gooodrecivestatus gooodrecivestatusId;
    @JoinColumn(name = "porder_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Porder porderId;

    public Goodrecive() {
    }

    public Goodrecive(Integer id) {
        this.id = id;
    }

    public Goodrecive(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

      public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getRecivedate() {
        return recivedate;
    }

    public void setRecivedate(LocalDate recivedate) {
        this.recivedate = recivedate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public List<Goodreciveitem> getGoodreciveitemList() {
        return goodreciveitemList;
    }

    public void setGoodreciveitemList(List<Goodreciveitem> goodreciveitemList) {
        this.goodreciveitemList = goodreciveitemList;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Gooodrecivestatus getGooodrecivestatusId() {
        return gooodrecivestatusId;
    }

    public void setGooodrecivestatusId(Gooodrecivestatus gooodrecivestatusId) {
        this.gooodrecivestatusId = gooodrecivestatusId;
    }

    public Porder getPorderId() {
        return porderId;
    }

    public void setPorderId(Porder porderId) {
        this.porderId = porderId;
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
        if (!(object instanceof Goodrecive)) {
            return false;
        }
        Goodrecive other = (Goodrecive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Goodrecive[ id=" + id + " ]";
    }
    
}
