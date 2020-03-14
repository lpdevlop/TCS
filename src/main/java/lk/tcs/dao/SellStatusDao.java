package lk.tcs.dao;

import lk.tcs.entity.Sellstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellStatusDao extends JpaRepository<Sellstatus,Integer> {



    @Query(value = "SELECT new Sellstatus(s.id,s.name) FROM Sellstatus s")
    List<Sellstatus> list();





}
