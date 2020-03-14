package lk.tcs.dao;

import lk.tcs.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PorderDao extends JpaRepository<Porder,Integer> {


    @Query("SELECT p FROM Porder p ORDER BY p.id DESC")
    Page<Porder> findAll(Pageable of);

    @Query(value = "SELECT concat('PO',lpad(substring(max(p.code),3,8)+1,8,'0')) FROM Porder p",nativeQuery = true)
    String nextPorderno();


    @Query("SELECT p FROM Porder p WHERE p.code=:code")
    Porder findbyCode(@Param("code") String code);

    @Query("SELECT porderitemList FROM Porder p where p.code=:code")
    List<Porder>listbyorder(@Param("code") String code);



    @Query(value="SELECT new Porder(i.id,i.code) FROM Porder i")
    List<Porder>list();


    @Query(value = "SELECT count(*) FROM Porder p WHERE p.orderstatus_id=1",nativeQuery = true)
    String countporder();

/*

    @Query("select new Porder(p.id,p.name,p.qty,p.total,p.subtotal,p.price) from Porder p where p in(SELECT p.code from Porder P where s.supplierid.id =:supplierid)")
    List<Porder>listbyorder(@Param("orderid") Integer orderid);

*/




}
