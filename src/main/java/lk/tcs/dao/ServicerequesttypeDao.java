package lk.tcs.dao;

import lk.tcs.entity.Brand;
import lk.tcs.entity.Servicerequeststatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicerequesttypeDao extends JpaRepository<Servicerequeststatus,Integer> {

    @Query(value = "SELECT new Servicerequeststatus(s.id,s.name) FROM Servicerequeststatus s")
    List<Servicerequeststatus> list();









}
