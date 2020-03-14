package lk.tcs.dao;

import lk.tcs.entity.Disposal;
import lk.tcs.entity.Disposalitem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisposalDao extends JpaRepository<Disposal,Integer> {

    @Query("SELECT d FROM Disposal d ORDER BY d.id DESC")
    Page<Disposal> findAll(Pageable of);


    @Query(value = "SELECT concat('DI',lpad(substring(max(d.code),3,8)+1,8,'0')) FROM Disposal d",nativeQuery = true)
    String nextDpno();


    @Query("SELECT d FROM Disposal d WHERE d.code=:code")
    Disposal findbyCode(@Param("code") String code);




}
