package lk.tcs.dao;

import lk.tcs.entity.Disposalstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DisposalStatusDao extends JpaRepository<Disposalstatus,Integer> {


    @Query(value="SELECT new Disposalstatus(d.id,d.name) FROM Disposalstatus d")
    List<Disposalstatus> list();



}
