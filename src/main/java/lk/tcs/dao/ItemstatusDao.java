package lk.tcs.dao;

import lk.tcs.entity.Itemstatus;
import lk.tcs.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemstatusDao extends JpaRepository<Unit,Integer> {

    @Query(value = "SELECT new Itemstatus(i.id,i.name) FROM Itemstatus i")
    List<Itemstatus>list();



}
