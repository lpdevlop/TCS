
package lk.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lasith
 */
@Entity
@Table(name = "brand")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Brand.findAll", query = "SELECT b FROM Brand b")})
public class Brand implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Categorybrand> categorybrandList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId", fetch = FetchType.LAZY)
    private List<Supplierbrand> supplierbrandList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Brand")
    private String name;
    @JoinTable(name = "categorybrand", joinColumns = {
        @JoinColumn(name = "brand_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "category_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Category> categoryList;
    @ManyToMany(mappedBy = "brandList", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Supplier> supplierList;

    public Brand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand() {
    }

    public Brand(Integer id) {
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
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @XmlTransient
    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
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
        if (!(object instanceof Brand)) {
            return false;
        }
        Brand other = (Brand) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lk.tcs.entity.Brand[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Supplierbrand> getSupplierbrandList() {
        return supplierbrandList;
    }

    public void setSupplierbrandList(List<Supplierbrand> supplierbrandList) {
        this.supplierbrandList = supplierbrandList;
    }

    @XmlTransient
    public List<Categorybrand> getCategorybrandList() {
        return categorybrandList;
    }

    public void setCategorybrandList(List<Categorybrand> categorybrandList) {
        this.categorybrandList = categorybrandList;
    }












}
