package lk.tcs.dao;

import lk.tcs.entity.Company;
import lk.tcs.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyDao extends JpaRepository<Company,Integer> {


    @Query(value="SELECT new Company(c.id,c.name) FROM Company c where companystatusId=1")
    List<Company> list();

/*    @Query(value="SELECT new Company(c.id,c.name) FROM Company c WHERE c not in (Select u.employeeId from User u)")
    List<Company> listWithoutUsers();

    @Query(value="SELECT new Company(c.id,c.name) FROM Company c WHERE c in (Select u.employeeId from User u)")
    List<Company> listWithUseraccount();*/

    @Query("SELECT c FROM Company c WHERE c.contact1=:contact1")
    Company findByContact1(@Param("contact1")String contact1);

    @Query("SELECT c FROM Company c ORDER BY c.id DESC")
    Page<Company> findAll(Pageable of);

    @Query("SELECT c FROM Company c WHERE c.code= :code")
    Company findByCode(@Param("code")String code);

    @Query("SELECT c FROM Company c WHERE c.email= :email")
    Company findbyEmail(@Param("email")String email);

   /* @Query("SELECT c FROM Company c WHERE c.number= :number")
    Company findByNumber(@Param("number")String number);
*/

    @Query(value = "SELECT concat('CO',lpad(substring(max(c.code),3,8)+1,8,'0')) FROM Company c",nativeQuery = true)
    String nextCompanyno();












}
