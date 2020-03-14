package lk.tcs.dao;

import lk.tcs.entity.Vehiclebrand;
import lk.tcs.entity.Vehiclemodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleBrandDao extends JpaRepository<Vehiclebrand,Integer> {

    @Query(value = "SELECT v FROM Vehiclebrand v")
    List<Vehiclebrand> list();


    @Query(value="SELECT new Vehiclebrand(v.id,v.name) FROM Vehiclebrand v WHERE v IN (SELECT v.name.id FROM Vehiclemodel v WHERE v.name.id= :vehiclemodelid)")
    List<Vehiclebrand> listmodelassigned(@Param("vehiclemodelid")Integer vehiclemodelid);




}
