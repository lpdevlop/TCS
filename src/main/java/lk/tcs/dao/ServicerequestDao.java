package lk.tcs.dao;

import lk.tcs.entity.Servicerequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicerequestDao extends JpaRepository<Servicerequest,Integer>{


    @Query("SELECT s FROM Servicerequest s ORDER BY s.id DESC")
    Page<Servicerequest> findAll(Pageable of);

    @Query("SELECT s FROM Servicerequest s WHERE s.code= :code")
    Servicerequest findByServno(@Param("code") String code);

    @Query(value = "SELECT concat('SR',lpad(substring(max(s.code),3,8)+1,8,'0')) FROM Servicerequest s",nativeQuery = true)
    String nextSRNo();

    @Query(value = "SELECT concat(max(s.indx)+1) FROM Servicerequest s",nativeQuery = true)
    String nextIndex();

    @Query(value = "SELECT new Servicerequest(s.id,s.code) FROM Servicerequest s")
    List<Servicerequest> list();

     @Query(value = "SELECT * From Servicerequest s where requestdate=current_date and servicerequeststatus_id=1",nativeQuery = true)
    List<Servicerequest>bookingcus();

     @Query(value = "SELECT count(code)  FROM Servicerequest s where requestdate=current_date",nativeQuery = true)
    String totalrequest();






}
