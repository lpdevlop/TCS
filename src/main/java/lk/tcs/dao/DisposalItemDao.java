package lk.tcs.dao;

import lk.tcs.entity.Disposalitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisposalItemDao extends JpaRepository<Disposalitem, Integer> {

    @Query("SELECT d FROM Disposalitem d where disposal_id=:id")
    List<Disposalitem> collectionItemList(@Param("id") Integer id);


}
