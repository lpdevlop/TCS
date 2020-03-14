/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "servicerequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicerequest.findAll", query = "SELECT s FROM Servicerequest s")})
public class Servicerequest implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicerequestId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Service> serviceList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Column(name = "indx")
    private Integer indx;
    @Column(name = "vechiclenumber")
    private String vechiclenumber;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "date")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;
    @Column(name = "requestdate")
   // @Temporal(TemporalType.DATE)
    private LocalDate requestdate;
    @Column(name = "appointmentime")
   // @Temporal(TemporalType.TIME)
    private LocalTime appointmentime;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Customer customerId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Line lineId;
    @JoinColumn(name = "servicerequeststatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Servicerequeststatus servicerequeststatusId;
    @JoinColumn(name = "servicetype_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Servicetype servicetypeId;
    @JoinColumn(name = "vehiclecategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehiclecategory vehiclecategoryId;
    @JoinColumn(name = "vehiclemodel_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Vehiclemodel vehiclemodelId;

    public Servicerequest() {
    }

    public String getVechiclenumber() {
        return vechiclenumber;
    }

    public void setVechiclenumber(String vechiclenumber) {
        this.vechiclenumber = vechiclenumber;
    }

    public Servicerequest(Integer id) {
        this.id = id;
    }

    public Servicerequest(Integer id, String code) {
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

    public Integer getIndx() {
        return indx;
    }

    public void setIndx(Integer indx) {
        this.indx = indx;
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

    public LocalDate getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(LocalDate requestdate) {
        this.requestdate = requestdate;
    }

    public LocalTime getAppointmentime() {
        return appointmentime;
    }

    public void setAppointmentime(LocalTime appointmentime) {
        this.appointmentime = appointmentime;
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

    public Line getLineId() {
        return lineId;
    }

    public void setLineId(Line lineId) {
        this.lineId = lineId;
    }

    public Servicerequeststatus getServicerequeststatusId() {
        return servicerequeststatusId;
    }

    public void setServicerequeststatusId(Servicerequeststatus servicerequeststatusId) {
        this.servicerequeststatusId = servicerequeststatusId;
    }

    public Servicetype getServicetypeId() {
        return servicetypeId;
    }

    public void setServicetypeId(Servicetype servicetypeId) {
        this.servicetypeId = servicetypeId;
    }

    public Vehiclecategory getVehiclecategoryId() {
        return vehiclecategoryId;
    }

    public void setVehiclecategoryId(Vehiclecategory vehiclecategoryId) {
        this.vehiclecategoryId = vehiclecategoryId;
    }

    public Vehiclemodel getVehiclemodelId() {
        return vehiclemodelId;
    }

    public void setVehiclemodelId(Vehiclemodel vehiclemodelId) {
        this.vehiclemodelId = vehiclemodelId;
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
        if (!(object instanceof Servicerequest)) {
            return false;
        }
        Servicerequest other = (Servicerequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Servicerequest[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
