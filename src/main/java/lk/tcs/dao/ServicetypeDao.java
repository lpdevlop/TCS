package lk.tcs.dao;

import lk.tcs.entity.Sellstatus;
import lk.tcs.entity.Servicetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicetypeDao extends JpaRepository<Servicetype,Integer> {



    @Query(value = "SELECT new Servicetype(s.id,s.name) FROM Servicetype s")
    List<Servicetype> list();



}
