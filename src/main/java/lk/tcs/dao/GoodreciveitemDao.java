package lk.tcs.dao;

import lk.tcs.entity.Goodreciveitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodreciveitemDao extends JpaRepository<Goodreciveitem,Integer> {


    @Query("SELECT r FROM Goodreciveitem r where goodrecive_id=:id")
    List<Goodreciveitem> collectionItemList(@Param("id") Integer id);








}
