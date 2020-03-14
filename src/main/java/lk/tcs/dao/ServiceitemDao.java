package lk.tcs.dao;

import lk.tcs.entity.Serviceitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceitemDao extends JpaRepository<Serviceitem,Integer> {

    @Query("SELECT r FROM Serviceitem r where service_id=:id")
    List<Serviceitem> collectionItemList(@Param("id") Integer id);








}
