package lk.tcs.dao;

import lk.tcs.entity.Vehiclecategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleCategoryDao extends JpaRepository<Vehiclecategory,Integer > {



    @Query(value = "SELECT v FROM Vehiclecategory v")
    List<Vehiclecategory> list();






}
