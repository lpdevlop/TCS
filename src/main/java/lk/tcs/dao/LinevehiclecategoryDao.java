package lk.tcs.dao;

import lk.tcs.entity.Item;
import lk.tcs.entity.Line;
import lk.tcs.entity.Linevehiclecategory;
import lk.tcs.entity.Vehiclemodel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinevehiclecategoryDao extends JpaRepository<Linevehiclecategory,Integer> {


    @Query("SELECT l FROM Linevehiclecategory l ORDER BY l.id DESC")
    Page<Linevehiclecategory> findAll(Pageable of);


    @Query(value = "SELECT l FROM Linevehiclecategory l")
    List<Linevehiclecategory> list();

    //where l in(SELECT l.lineId from Linevehiclecategory l

    /*@Query("select l from Linevehiclecategory l where l.vehiclecategoryId.id =:vehiclecaid")
    List<Linevehiclecategory>Linevehivlecategory(@Param("vehiclecaid") Integer vehiclecaid);*/

    @Query(value="SELECT  new Linevehiclecategory(l.id) FROM Linevehiclecategory l WHERE l IN (SELECT l.vehiclecategoryId FROM Linevehiclecategory l WHERE l.vehiclecategoryId.id=:vehiclecaid)")
    List<Linevehiclecategory> Linevehiclecategory(@Param("vehiclecaid")Integer vehiclecaid);



}
