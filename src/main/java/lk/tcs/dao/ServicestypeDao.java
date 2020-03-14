package lk.tcs.dao;

import lk.tcs.entity.Servicestype;
import lk.tcs.entity.Servicetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicestypeDao extends JpaRepository<Servicestype,Integer> {


    @Query(value = "SELECT new Servicestype(s.id,s.name) FROM Servicestype s")
    List<Servicestype> list();










}
