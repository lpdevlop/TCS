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
import java.time.LocalTime;
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
@Table(name = "service")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "labourcost")
    private BigDecimal labourcost;
    @Column(name = "stime")
    //@Temporal(TemporalType.TIME)
    private LocalTime stime;
    @Column(name = "etime")
    //@Temporal(TemporalType.TIME)
    private LocalTime etime;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "tax")
    private BigDecimal tax;
    @Column(name = "total")
    private BigDecimal total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceId", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Serviceitem> serviceitemList;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customer customerId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "servicerequest_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Servicerequest servicerequestId;
    @JoinColumn(name = "servicestype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Servicestype servicestypeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Warrantyservice> warrantyserviceList;

    public Service() {
    }

    public Service(Integer id) {
        this.id = id;
    }



    public Service(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public BigDecimal getLabourcost() {
        return labourcost;
    }

    public void setLabourcost(BigDecimal labourcost) {
        this.labourcost = labourcost;
    }

    public LocalTime getStime() {
        return stime;
    }

    public void setStime(LocalTime stime) {
        this.stime = stime;
    }

    public LocalTime getEtime() {
        return etime;
    }

    public void setEtime(LocalTime etime) {
        this.etime = etime;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @XmlTransient
    public List<Serviceitem> getServiceitemList() {
        return serviceitemList;
    }

    public void setServiceitemList(List<Serviceitem> serviceitemList) {
        this.serviceitemList = serviceitemList;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Servicerequest getServicerequestId() {
        return servicerequestId;
    }

    public void setServicerequestId(Servicerequest servicerequestId) {
        this.servicerequestId = servicerequestId;
    }

    public Servicestype getServicestypeId() {
        return servicestypeId;
    }

    public void setServicestypeId(Servicestype servicestypeId) {
        this.servicestypeId = servicestypeId;
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
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Service[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Warrantyservice> getWarrantyserviceList() {
        return warrantyserviceList;
    }

    public void setWarrantyserviceList(List<Warrantyservice> warrantyserviceList) {
        this.warrantyserviceList = warrantyserviceList;
    }





}
