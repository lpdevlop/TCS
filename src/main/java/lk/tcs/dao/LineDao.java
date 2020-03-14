package lk.tcs.dao;

import lk.tcs.entity.Brand;
import lk.tcs.entity.Line;
import lk.tcs.entity.Sell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineDao  extends JpaRepository<Line,Integer> {

    @Query("SELECT l FROM Line l ORDER BY l.id DESC")
    Page<Line> findAll(Pageable of);

    @Query(value = "SELECT concat('LN',lpad(substring(max(l.code),3,8)+1,8,'0')) FROM Line l",nativeQuery = true)
    String nextLineno();


    @Query("SELECT l FROM Line l WHERE l.code=:code")
    Line findbyCode(@Param("code") String code);

    @Query(value = "SELECT l FROM Line l")
    List<Line> list();

    //find line using vehicle category
    @Query(value = "SELECT * FROM servicecenter.line, servicecenter.linevehiclecategory where vehiclecategory_id=:vehiclecaid",nativeQuery = true)
    List<Line>Linevehivlecategory(@Param("vehiclecaid") Integer vehiclecaid);





}
