package lk.tcs.dao;

import lk.tcs.entity.Designation;
import lk.tcs.entity.Subcategory;
import lk.tcs.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubcategoryDao extends JpaRepository<Subcategory,Integer> {

    @Query(value = "SELECT new Subcategory(s.id,s.name) FROM Subcategory s")
    List<Subcategory>list();
    @Query("SELECT s FROM Subcategory s WHERE s.name= :name")
    Subcategory findByName(@Param("name")String name);

    @Query(value = "SELECT new Subcategory (s.id,s.name) FROM Subcategory s  where s.categoryId.id =:categoryid")
    List<Subcategory>listbysubcategory(@Param("categoryid")Integer categoryid);



}
