package lk.tcs.dao;

import lk.tcs.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Integer> {


    @Query(value="SELECT new Customer(c.id,c.name,c.mobile) FROM Customer c ")
    List<Customer>list();

    /*@Query(value="SELECT new Customer(c.id,c.name) FROM Customer c WHERE c not in (Select c.titleId from Customer c)")
    List<Customer> listWithoutUsers();

    @Query(value="SELECT new Customer(c.id,c.name) FROM Customer c WHERE c in (Select c.titleId from Customer c)")
    List<Customer> listWithUseraccount();*/

    @Query("SELECT c FROM Customer c ORDER BY c.id DESC")
    Page<Customer> findAll(Pageable of);

    @Query("SELECT c FROM Customer c WHERE c.nic= :nic")
    Customer findByNIC(@Param("nic")String nic);

  /*  @Query("SELECT c FROM Customer c WHERE c in (SELECT c.id=c.code ")
    Customer findByCode(@Param("code")String code);*/

     @Query(value = "SELECT concat('CU',lpad(substring(max(c.code),3,8)+1,8,'0')) FROM Customer c",nativeQuery = true)
     String nextCusrno();

    @Query("SELECT c.mobile FROM Customer c WHERE c.id= :cuid")
    List<Customer> findByMobilenum(@Param("cuid")Integer cuid);

}
