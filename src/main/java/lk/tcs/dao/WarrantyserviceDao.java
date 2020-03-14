package lk.tcs.dao;

import lk.tcs.entity.Warrantyservice;
import lk.tcs.entity.Warrantyservice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarrantyserviceDao extends JpaRepository<Warrantyservice,Integer> {



    @Query("SELECT w FROM Warrantyservice w ORDER BY w.id DESC")
    Page<Warrantyservice> findAll(Pageable of);


    @Query(value = "SELECT concat('WS',lpad(substring(max(w.code),3,8)+1,8,'0')) FROM Warrantyservice w",nativeQuery = true)
    String nextWrrantyno();


    @Query("SELECT w FROM Warrantyservice w WHERE w.code=:code")
    Warrantyservice findbyCode(@Param("code") String code);







}
