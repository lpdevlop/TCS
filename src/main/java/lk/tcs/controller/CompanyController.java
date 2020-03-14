package lk.tcs.controller;

import lk.tcs.dao.CompanyDao;
import lk.tcs.dao.CompanyStatusDao;
import lk.tcs.entity.Company;
import lk.tcs.entity.Employee;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class CompanyController {

    @Autowired
    private CompanyDao comapnydao;


    @Autowired
    private CompanyStatusDao companyStatusDao;


    @RequestMapping(value = "/company/nextcompany",method = RequestMethod.GET, produces = "application/json")
    public String nextCompanyno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password ){
        if (AuthProvider.isAuthorized(username, password, ModuleList.COMPANY, AuthProvider.SELECT)) {
            String companyno = comapnydao.nextCompanyno();
            return "{\"no\":\"" +companyno+ "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/companys/list", method = RequestMethod.GET, produces = "application/json")
    public List<Company> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return comapnydao.list();
        }
        return null;
    }


   /* @RequestMapping(value = "/companys/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Company> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return comapnydao.listWithoutUsers();
        }
        return null;
    }

    @RequestMapping(value = "/companys/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Company> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return comapnydao.listWithUseraccount();
        }
        return null;
    }

*/


    @RequestMapping(value = "/companys", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Company> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.SELECT)) {
            return comapnydao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/companys", params = {"page", "size","name","companystatusId","employeeId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Company> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name,  @RequestParam("employeeId") Integer employeeId, @RequestParam("companystatusId") Integer companystatusId) {

        // System.out.println(name+"-"+nic+"-"+designationid+"-"+employeestatusid);


        if(AuthProvider.isAuthorized(username,password, ModuleList.COMPANY,AuthProvider.SELECT)) {

            List<Company> companys = comapnydao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Company> companyStream = companys.stream();

            companyStream = companyStream.filter(c ->c.getName().contains(name));

            if (companystatusId != null)
                companyStream = companyStream.filter(c -> c.getCompanystatusId().equals(companyStatusDao.getOne(companystatusId)));
            if (employeeId != null)
                companyStream= companyStream.filter(c -> c.getEmployeeId().equals(comapnydao.getOne(employeeId)));
           /* companyStream = companyStream.filter(e -> e.getNic().startsWith(nic));
            employeestream = employeestream.filter(e -> e.getFullname().contains(name));*/

            List<Company> companies2 = companyStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < companies2.size() ? start + size : companies2.size();
            Page<Company> emppage = new PageImpl<>(companies2.subList(start, end), PageRequest.of(page, size), companies2.size());

            return emppage;
        }

        return null;

    }


    @RequestMapping(value = "/companys", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Company company) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.INSERT)) {
            Company comcode = comapnydao.findByContact1(company.getContact1());
            Company comemail = comapnydao.findbyEmail(company.getEmail());
            if (comcode != null)
               return "Error-Validation : This Company Number"+company.getContact1()+" Exists Database";
               else if (comemail != null)
                return "Error-Validation : This Email Exists in Database";
           // else
                comapnydao.save(company);
/*
            try {
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " ;
                }*/
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/companys", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Company company) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.COMPANY,AuthProvider.UPDATE)) {
        /*    Employee emp = dao.findByNIC(employee.getNic());
            if(emp==null || emp.getId()==employee.getId()) {*/
                try {
                    comapnydao.save(company);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
           else {  return "Error-Updating : NIC Exists"; }
       // }
        //return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value ="/companys", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Company company ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.COMPANY,AuthProvider.DELETE)) {
            try {
                comapnydao.delete(comapnydao.getOne(company.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }











}
