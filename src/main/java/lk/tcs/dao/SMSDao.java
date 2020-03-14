package lk.tcs.dao;

import lk.tcs.entity.Company;
import lk.tcs.entity.Item;
import lk.tcs.entity.Messages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SMSDao extends JpaRepository<Messages, Integer> {


    @Query("SELECT m FROM Messages m WHERE m.code= :code")
    Messages findByCode(@Param("code")String code);

    @Query(value = "SELECT concat('SM',lpad(substring(max(m.code),3,8)+1,8,'0')) FROM Messages m",nativeQuery = true)
    String nextSMSNo();


    @Query("SELECT m FROM Messages m ORDER BY m.id DESC")
    Page<Messages> findAll(Pageable of);

    @Query("SELECT m FROM Messages m WHERE m.number= :number")
    Messages findByNumber(@Param("number")String number);


    @Query(value="SELECT new Messages (m.id,m.message) FROM Messages m")
    List<Messages> list();





}





