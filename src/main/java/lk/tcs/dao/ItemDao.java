package lk.tcs.dao;

import lk.tcs.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemDao extends JpaRepository<Item,Integer> {


    @Query(value="SELECT new Item(i.id,i.name) FROM Item i")
    List<Item>list();


    @Query(value = "SELECT concat('IT',lpad(substring(max(i.code),3,8)+1,8,'0')) FROM Item i",nativeQuery = true)
    String nextItemno();

    @Query(value="SELECT new Item(i.id,i.name) FROM Item i WHERE i not in (Select u.employeeId from User u)")
    List<Item> listWithoutUsers();

    @Query(value="SELECT new Item(i.id,i.name) FROM Item i WHERE i in (Select u.employeeId from User u)")
    List<Item> listWithUseraccount();


    @Query("SELECT i FROM Item i ORDER BY i.id DESC")
    Page<Item> findAll(Pageable of);


    @Query("SELECT new Item(i.id,id.name,id.price,id.saleprice) FROM Item i ")
    List<Item> listbyitem();


    @Query("select new Item(i.id,i.name,i.price,id.saleprice) from Item i where i.categoryId.id =:categoryid")
    List<Item>listbysupplier(@Param("categoryid") Integer categoryid);

    @Query(value = "SELECT * FROM Item i where qty < rop",nativeQuery = true)
    List<Item> reorerfind();

    @Query("select new Item(i.id,i.name,id.saleprice) from Item i where itemstatusId.id=1 and  subcategoryId.id =:subcategoryid")
    List<Item>listbyitemusesubcategory(@Param("subcategoryid") Integer subcategoryid);



/*
    @Query(value="SELECT new Item(i.id,i.name) FROM Item i WHERE i not in (Select i.titleId from Item i)")
    List<Item> listWithoutUsers();

    @Query(value="SELECT new Item(i.id,i.name) FROM Item i WHERE i in (Select i.titleId from Item i)")
    List<Item> listWithUseraccount();*/

/*
    @Query(value="SELECT new Item(i.id,i.name) FROM Item i WHERE i not in (Select i.titleId from Customer c)")
    List<Customer> listWithoutUsers();

    @Query(value="SELECT new Customer(c.id,c.name) FROM Customer c WHERE c in (Select c.titleId from Customer c)")
    List<Customer> listWithUseraccount();

    @Query("SELECT c FROM Customer c ORDER BY c.id DESC")
    Page<Customer> findAll(Pageable of);

    @Query("SELECT c FROM Customer c WHERE c.nic= :nic")
    Customer findByNIC(@Param("nic") String nic);*/

  /*  @Query("SELECT c FROM Customer c WHERE c in (SELECT c.id=c.code ")
    Customer findByCode(@Param("code")String code);*/

    /* @Query(value = "SELECT concat('CU',lpad(substring(max(c.code),3,8)+1,8,'0')) FROM Customer c",nativeQuery = true)
     String nextCusrno();*/



}
