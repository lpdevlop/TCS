package lk.tcs.dao;

import lk.tcs.entity.Companystatus;
import lk.tcs.entity.Itemstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyStatusDao extends JpaRepository<Companystatus,Integer> {


    @Query(value = "SELECT new Companystatus(c.id,c.name) FROM Companystatus c")
    List<Companystatus> list();









}
