package lk.tcs.dao;

import lk.tcs.entity.Brand;
import lk.tcs.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitDao extends JpaRepository<Unit,Integer> {

    @Query(value = "SELECT new Unit(u.id,u.name) FROM Unit u")
    List<Unit>list();


    @Query("SELECT u FROM Unit u WHERE u.name= :name")
    Unit findByName(@Param("name")String name);


}
