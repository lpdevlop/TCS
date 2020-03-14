package lk.tcs.dao;


import lk.tcs.entity.Civilstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CivilstatusDao extends JpaRepository<Civilstatus, Integer>
{

    @Query(value="SELECT new Civilstatus(c.id,c.name) FROM Civilstatus c")
    List<Civilstatus> list();


}