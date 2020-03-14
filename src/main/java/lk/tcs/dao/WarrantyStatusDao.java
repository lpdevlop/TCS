

package lk.tcs.dao;
import lk.tcs.entity.Warrantyservicestatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarrantyStatusDao extends JpaRepository<Warrantyservicestatus,Integer> {



    @Query(value = "SELECT new Warrantyservicestatus(w.id,w.name) FROM Warrantyservicestatus w")
    List<Warrantyservicestatus> list();









}















