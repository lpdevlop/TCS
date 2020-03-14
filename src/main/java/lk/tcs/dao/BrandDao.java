package lk.tcs.dao;

import lk.tcs.entity.Brand;
import lk.tcs.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrandDao extends JpaRepository<Brand,Integer> {

    @Query(value = "SELECT new Brand(b.id,b.name) FROM Brand b")
    List<Brand>list();


    @Query("SELECT b FROM Brand b WHERE b.name= :name")
    Brand findByName(@Param("name")String name);

/*    @Query(value="SELECT * FROM Brand  WHERE in (SELECT brand_id FROM categorybrand WHERE category_id = :categoryid)",nativeQuery = true)
    List<Brand> list(@Param("categoryid")Integer categoryid);*/
/*
    @Query(value="SELECT * FROM categorybrand c  WHERE category_id=:categoryid",nativeQuery = true)
    List<Brand> list(@Param("categoryid")Integer categoryid);
*/



}
