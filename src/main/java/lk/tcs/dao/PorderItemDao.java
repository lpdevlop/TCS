package lk.tcs.dao;

import lk.tcs.entity.Item;
import lk.tcs.entity.Porderitem;
import lk.tcs.entity.Porder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PorderItemDao extends JpaRepository<Porderitem,Integer> {




}
