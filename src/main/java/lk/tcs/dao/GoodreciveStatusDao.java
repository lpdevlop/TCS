package lk.tcs.dao;

import lk.tcs.entity.Gooodrecivestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodreciveStatusDao extends JpaRepository<Gooodrecivestatus,Integer> {


    @Query(value = "SELECT new Gooodrecivestatus(g.id,g.name) FROM Gooodrecivestatus g")
    List<Gooodrecivestatus> list();




}
