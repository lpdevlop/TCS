package lk.tcs.dao;

import lk.tcs.entity.Employee;
import lk.tcs.entity.Item;
import lk.tcs.entity.Supplier;
import lk.tcs.entity.Suppliercategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier,Integer> {


    @Query(value="SELECT new Supplier(s.id,s.name) FROM Supplier s")
    List<Supplier> list();


    @Query("SELECT s FROM Supplier s WHERE s.contact1=:contact1")
    Supplier findByContact1(@Param("contact1")String contact1);

    @Query("SELECT s FROM Supplier s ORDER BY s.id DESC")
    Page<Supplier> findAll(Pageable of);

    @Query(value = "SELECT concat('SU',lpad(substring(max(s.code),3,8)+1,8,'0')) FROM Supplier s",nativeQuery = true)
    String nextSupno();

   /* @Query("SELECT s.suppliercategoryList FROM Supplier s  where s.supplierid.id =:supplierid")
    List<Supplier>suppliercategoryList(@Param("supplierid") Integer supplierid);*/


    @Query("SELECT s FROM Supplier s WHERE s.name= :name")
    Supplier findByUsername(@Param("name")String name);






}
