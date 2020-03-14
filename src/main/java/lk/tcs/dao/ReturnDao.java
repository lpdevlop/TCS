package lk.tcs.dao;

import lk.tcs.entity.Returnitem;
import lk.tcs.entity.Sell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReturnDao extends JpaRepository<Returnitem,Integer> {



    @Query("SELECT r FROM Returnitem r ORDER BY r.id DESC")
    Page<Returnitem> findAll(Pageable of);

    @Query(value = "SELECT concat('RI',lpad(substring(max(r.code),3,8)+1,8,'0')) FROM Returnitem r",nativeQuery = true)
    String nextReturnno();


    @Query("SELECT r FROM Returnitem r WHERE r.code=:code")
    Returnitem findbyCode(@Param("code") String code);


    @Query(value = "SELECT count(*) FROM Returnitem r WHERE r.returnstatus_id=1",nativeQuery = true)
    String countreturn();



}
