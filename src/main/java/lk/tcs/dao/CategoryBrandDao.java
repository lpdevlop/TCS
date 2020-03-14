package lk.tcs.dao;

import lk.tcs.entity.Categorybrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryBrandDao extends JpaRepository<Categorybrand,Integer> {



    @Query(value="SELECT c.brandId FROM Categorybrand c  WHERE c.categoryId.id =:categoryid")
    List<Categorybrand> listBARND(@Param("categoryid")Integer categoryid);




}
