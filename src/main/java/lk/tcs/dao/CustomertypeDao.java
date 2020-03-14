package lk.tcs.dao;

import lk.tcs.entity.Customer;
import lk.tcs.entity.Customertype;
import lk.tcs.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomertypeDao extends JpaRepository<Customertype,Integer> {

  @Query(value="SELECT new Customertype(c.id,c.name) FROM Customertype c")
    List<Customertype>list();


}
