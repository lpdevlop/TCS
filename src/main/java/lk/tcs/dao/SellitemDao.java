package lk.tcs.dao;

import lk.tcs.entity.Role;
import lk.tcs.entity.Sellitem;
import lk.tcs.entity.Servicerequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellitemDao extends JpaRepository<Sellitem,Integer> {



    @Query(value="SELECT new Role(r.id,r.name) FROM Role r")
    List<Role> list();

    @Query(value = "SELECT l.qty,e.name FROM servicecenter.sellitem l inner join servicecenter.item  a ON l.item_id=a.id inner join servicecenter.category s ON s.id=a.id inner join servicecenter.suppliercategory p ON p.category_id=s.id inner join servicecenter.supplier e ON e.id=p.id where Supplier_id=:1;",nativeQuery = true)
    List<Sellitem>findsupplierqty();






}
