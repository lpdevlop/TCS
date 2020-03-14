package lk.tcs.dao;

import lk.tcs.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleDao extends JpaRepository<Title,Integer> {

    @Query(value = "SELECT new Title(t.id,t.name) FROM Title t")
    List<Title>list();


}
