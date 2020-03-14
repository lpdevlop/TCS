package lk.tcs.dao;

import lk.tcs.entity.Orderstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderStatusDao extends JpaRepository<Orderstatus,Integer>
{

    @Query(value="SELECT new Orderstatus(o.id,o.name) FROM Orderstatus o")
    List<Orderstatus> list();


}









