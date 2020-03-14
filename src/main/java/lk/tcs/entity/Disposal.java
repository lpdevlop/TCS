/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "disposal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disposal.findAll", query = "SELECT d FROM Disposal d")})
public class Disposal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "reson")
    private String reson;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    @Column(name = "disposaldate")
    //@Temporal(TemporalType.DATE)
    private LocalDate disposaldate;
    @Column(name = "code")
    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disposalId", fetch = FetchType.EAGER)
    private List<Disposalitem> disposalitemList;

    @JoinColumn(name = "disposalstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Disposalstatus disposalstatusId;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    public Disposal() {
    }

    public Disposal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDisposaldate() {
        return disposaldate;
    }

    public void setDisposaldate(LocalDate disposaldate) {
        this.disposaldate = disposaldate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public List<Disposalitem> getDisposalitemList() {
        return disposalitemList;
    }

    public void setDisposalitemList(List<Disposalitem> disposalitemList) {
        this.disposalitemList = disposalitemList;
    }

    public Disposalstatus getDisposalstatusId() {
        return disposalstatusId;
    }

    public void setDisposalstatusId(Disposalstatus disposalstatusId) {
        this.disposalstatusId = disposalstatusId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
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
        if (!(object instanceof Disposal)) {
            return false;
        }
        Disposal other = (Disposal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Disposal[ id=" + id + " ]";
    }
    
}
