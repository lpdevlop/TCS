package lk.tcs.dao;

import lk.tcs.entity.Vehiclemodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import lk.tcs.entity.Vehiclebrand;

import java.util.List;

public interface VehiclemodelDao extends JpaRepository<Vehiclemodel,Integer> {

    @Query(value = "SELECT new Vehiclemodel(v.id,v.name) FROM Vehiclemodel v")
    List<Vehiclemodel> list();



   /* @Query(value="SELECT new Vehiclebrand(v.name,v.id) FROM Vehiclebrand v WHERE v IN (SELECT v.name.id FROM Vehiclemodel v WHERE v.name.id= :vehiclemodelid)")
    List<Vehiclemodel> listmodelassigned(@Param("vehiclemodelid")Integer vehiclemodelid);*/


}



