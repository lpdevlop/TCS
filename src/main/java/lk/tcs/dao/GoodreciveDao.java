package lk.tcs.dao;

import lk.tcs.entity.Goodrecive;
import lk.tcs.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodreciveDao extends JpaRepository<Goodrecive,Integer> {

    @Query("SELECT g FROM Goodrecive g ORDER BY g.id DESC")
    Page<Goodrecive> findAll(Pageable of);

    @Query(value = "SELECT concat('GR',lpad(substring(max(g.code),3,8)+1,8,'0')) FROM Goodrecive g",nativeQuery = true)
    String nextGoodReciveno();

    @Query("SELECT g FROM Goodrecive g WHERE g.code=:code")
    Goodrecive findbyCode(@Param("code") String code);



}
