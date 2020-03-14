package lk.tcs.dao;

import lk.tcs.entity.Returnstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnstatusDao extends JpaRepository<Returnstatus,Integer> {



    @Query(value = "SELECT new Returnstatus(r.id,r.name) FROM Returnstatus r")
    List<Returnstatus> list();







}


