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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "supplier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s")})
public class Supplier implements Serializable {

    @Column(name = "date")
   // @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierid", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Suppliercategory> suppliercategoryList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierid", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Supplierbrand> supplierbrandList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierid", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Porder> porderList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Za-z]{2,}([\\s][A-Za-z]{2,})*)$",message="Invalid Name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    private String address;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid Description")
    private String description;
    @Column(name = "contact1")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobile phone Number")
    private String contact1;
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Mobile phone Number")
    @Column(name = "contact2")
    private String contact2;
    @Column(name = "fax")
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid Fax phone Number")
    private String fax;
    @Column(name = "email")
    @Pattern(regexp="^([a-zA-Z0-9]+@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)*)$", message = "Invalid email")
    private String email;
    @JoinTable(name = "suppliercategory", joinColumns = {
        @JoinColumn(name = "Supplier_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "category_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categoryList;
    @JoinTable(name = "supplierbrand", joinColumns = {
        @JoinColumn(name = "Supplier_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "brand_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Brand> brandList;

    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;
    @JoinColumn(name = "supplierstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Supplierstatus supplierstatusId;

    public Supplier() {
    }

    public Supplier(Integer id,String name) {
        this.name = name;
        this.id=id;
    }

    public Supplier(Integer id) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @XmlTransient
    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Supplierstatus getSupplierstatusId() {
        return supplierstatusId;
    }

    public void setSupplierstatusId(Supplierstatus supplierstatusId) {
        this.supplierstatusId = supplierstatusId;
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
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Supplier[ id=" + id + " ]";
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @XmlTransient
    public List<Porder> getPorderList() {
        return porderList;
    }

    public void setPorderList(List<Porder> porderList) {
        this.porderList = porderList;
    }


    @XmlTransient
    public List<Suppliercategory> getSuppliercategoryList() {
        return suppliercategoryList;
    }

    public void setSuppliercategoryList(List<Suppliercategory> suppliercategoryList) {
        this.suppliercategoryList = suppliercategoryList;
    }

    @XmlTransient
    public List<Supplierbrand> getSupplierbrandList() {
        return supplierbrandList;
    }

    public void setSupplierbrandList(List<Supplierbrand> supplierbrandList) {
        this.supplierbrandList = supplierbrandList;
    }









}
