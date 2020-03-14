/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "returnitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Returnitem.findAll", query = "SELECT r FROM Returnitem r")})
public class Returnitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "descriptin")
    private String descriptin;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    @Column(name = "rawdate")
    private LocalDate rawdate;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private BigDecimal total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "returnitemId", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Returnitemitem> returnitemitemList;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    @JoinColumn(name = "returnstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Returnstatus returnstatusId;

    public Returnitem() {
    }

    public Returnitem(Integer id) {
        this.id = id;
    }

    public Returnitem(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public LocalDate getRawdate() {
        return rawdate;
    }

    public void setRawdate(LocalDate rawdate) {
        this.rawdate = rawdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescriptin() {
        return descriptin;
    }

    public void setDescriptin(String descriptin) {
        this.descriptin = descriptin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @XmlTransient
    public List<Returnitemitem> getReturnitemitemList() {
        return returnitemitemList;
    }

    public void setReturnitemitemList(List<Returnitemitem> returnitemitemList) {
        this.returnitemitemList = returnitemitemList;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Returnstatus getReturnstatusId() {
        return returnstatusId;
    }

    public void setReturnstatusId(Returnstatus returnstatusId) {
        this.returnstatusId = returnstatusId;
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
        if (!(object instanceof Returnitem)) {
            return false;
        }
        Returnitem other = (Returnitem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Returnitem[ id=" + id + " ]";
    }
    
}
