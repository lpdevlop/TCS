package lk.tcs.dao;

import lk.tcs.entity.Sell;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface SellDao extends JpaRepository<Sell,Integer> {

    @Query("SELECT s FROM Sell s ORDER BY s.id DESC")
    Page<Sell> findAll(Pageable of);

    @Query(value = "SELECT concat('SE',lpad(substring(max(s.code),3,8)+1,8,'0')) FROM Sell s",nativeQuery = true)
    String nextSellno();


    @Query("SELECT s FROM Sell s WHERE s.code=:code")
    Sell findbyCode(@Param("code") String code);


    @Query(value = "SELECT sum(total) FROM Sell  s where date(date)=curdate()",nativeQuery = true)
    String totalsellsum();




}
