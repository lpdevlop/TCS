package lk.tcs.dao;

import lk.tcs.entity.Suppliercategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuppliercategoryDao extends JpaRepository<Suppliercategory, Integer> {


  /* @Query("SELECT new Suppliercategory(s.categoryId) FROM Suppliercategory s WHERE s.supplierid.id =:supplierid ")
    List<Suppliercategory>suppliercategoryList(@Param("supplierid") Integer supplierid)*/;

    //in(SELECT s.categoryId from Suppliercategory s where

    @Query(value="SELECT s.categoryId FROM Suppliercategory s  WHERE s.supplierid.id =:supplierid")
    List<Suppliercategory> suppliercategoryList(@Param("supplierid")Integer supplierid);




}
