package lk.tcs.dao;

import lk.tcs.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {

    @Query(value = "SELECT new Category(c.id,c.name) FROM Category c")
    List<Category>list();

    @Query("SELECT c FROM Category c WHERE c.name= :name")
    Category findByName(@Param("name")String name);





}
