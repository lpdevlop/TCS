package lk.tcs.dao;

import lk.tcs.entity.Returnitemitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReturnitemitemDao extends JpaRepository<Returnitemitem,Integer> {


    @Query("SELECT r FROM Returnitemitem r where returnitem_id=:id")
    List<Returnitemitem> collectionItemList(@Param("id") Integer id);







}
