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
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")})
public class Item implements Serializable {

    @Column(name = "date")
    private LocalDate date;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Serviceitem> serviceitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Returnitemitem> returnitemitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Disposalitem> disposalitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Goodreciveitem> goodreciveitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Porderitem> porderitemList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sellitem> sellitemList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message="Invalid Description")
    private String description;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Za-z0-9]{2,}([\\s][A-Za-z0-9]{2,})*)$" ,message="Invalid Name")
    private String name;
    @Column(name = "price")
    private Long price;
    @Column(name = "saleprice")
    private Long saleprice;
    @Column(name = "qty")
    private Integer qty;
    @Column(name = "rop")
    private Integer rop;
    @Column(name = "code")
    private String code;
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Brand brandId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Category categoryId;
    @JoinColumn(name = "itemstatus_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Itemstatus itemstatusId;
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Subcategory subcategoryId;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Unit unitId;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employee employeeId;

    public Item() {
    }

    public Item( Integer id,String name, Long saleprice) {
        this.name = name;
        this.saleprice = saleprice;
        this.id=id;
    }

    public Item(Integer id, String name) {
        this.name = name;
        this.id=id;
    }

    public Item(Integer id, String name, Long price, Long saleprice) {
        this.name = name;
        this.id=id;
        this.price = price;
        this.saleprice = saleprice;
    }

    public Item(Integer id,Long price, Long saleprice) {
        this.id=id;
        this.price = price;
        this.saleprice = saleprice;
    }


    public Item(Integer id) {
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    public Long getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Long saleprice) {
        this.saleprice = saleprice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getRop() {
        return rop;
    }

    public void setRop(Integer rop) {
        this.rop = rop;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Itemstatus getItemstatusId() {
        return itemstatusId;
    }

    public void setItemstatusId(Itemstatus itemstatusId) {
        this.itemstatusId = itemstatusId;
    }

    public Subcategory getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Subcategory subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Item[ id=" + id + " ]";
    }


    @XmlTransient
    public List<Sellitem> getSellitemList() {
        return sellitemList;
    }

    public void setSellitemList(List<Sellitem> sellitemList) {
        this.sellitemList = sellitemList;
    }


    @XmlTransient
    public List<Porderitem> getPorderitemList() {
        return porderitemList;
    }

    public void setPorderitemList(List<Porderitem> porderitemList) {
        this.porderitemList = porderitemList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @XmlTransient
    public List<Goodreciveitem> getGoodreciveitemList() {
        return goodreciveitemList;
    }

    public void setGoodreciveitemList(List<Goodreciveitem> goodreciveitemList) {
        this.goodreciveitemList = goodreciveitemList;
    }
    @XmlTransient
    public List<Disposalitem> getDisposalitemList() {
        return disposalitemList;
    }

    public void setDisposalitemList(List<Disposalitem> disposalitemList) {
        this.disposalitemList = disposalitemList;
    }


    @XmlTransient
    public List<Returnitemitem> getReturnitemitemList() {
        return returnitemitemList;
    }

    public void setReturnitemitemList(List<Returnitemitem> returnitemitemList) {
        this.returnitemitemList = returnitemitemList;
    }

    @XmlTransient
    public List<Serviceitem> getServiceitemList() {
        return serviceitemList;
    }

    public void setServiceitemList(List<Serviceitem> serviceitemList) {
        this.serviceitemList = serviceitemList;
    }






}
