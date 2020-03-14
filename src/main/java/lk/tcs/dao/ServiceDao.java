package lk.tcs.dao;

import lk.tcs.entity.Sell;
import lk.tcs.entity.Service;
import lk.tcs.entity.Warrantyservice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceDao extends JpaRepository<Service,Integer> {


    @Query("SELECT s FROM Service s ORDER BY s.id DESC")
    Page<Service> findAll(Pageable of);

    @Query(value = "SELECT concat('SI',lpad(substring(max(s.code),3,8)+1,8,'0')) FROM Service s",nativeQuery = true)
    String nextServiceno();


    @Query("SELECT s FROM Service s WHERE s.code=:code")
    Service findbyCode(@Param("code") String code);



    @Query(value = "SELECT new Service (s.id,s.code) FROM Service s")
    List<Service>list();








}
