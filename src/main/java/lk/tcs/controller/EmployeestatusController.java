package lk.tcs.controller;

import lk.tcs.entity.Designation;
import lk.tcs.entity.Employeestatus;
import lk.tcs.dao.EmployeestatusDao;
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
public class EmployeestatusController {

    @Autowired
    private EmployeestatusDao dao;

    @RequestMapping(value = "/employeestatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Employeestatus> employeestatuses() {
        return dao.list();
    }


    @RequestMapping(value = "/employeestatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Employeestatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEESTATUS,AuthProvider.SELECT)) {

            List<Employeestatus> employeestatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Employeestatus> employeestatusStream = employeestatuses.stream();

            employeestatusStream = employeestatusStream.filter(e -> e.getName().startsWith(name));

            List<Employeestatus> employeestatuses2 = employeestatusStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < employeestatuses2.size() ? start + size : employeestatuses2.size();
            Page<Employeestatus> despage = new PageImpl<>(employeestatuses2.subList(start, end), PageRequest.of(page, size), employeestatuses2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/employeestatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Employeestatus employeestatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEESTATUS,AuthProvider.INSERT)) {
            Employeestatus desname = dao.findByName(employeestatus.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(employeestatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/employeestatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Employeestatus employeestatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEESTATUS,AuthProvider.INSERT)) {
            Employeestatus desname = dao.findByName(employeestatus.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(employeestatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/employeestatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Employeestatus employeestatus ) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.EMPLOYEESTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(employeestatus.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


/*

    @RequestMapping(value = "/employees", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Designation employee) {

        if(AuthProvider.isAuthorized(username,password,"Designation",AuthProvider.UPDATE)) {
            Designation emp = dao.findByNIC(employee.getNic());
            if(emp==null || emp.getId()==employee.getId()) {
                try {
                    dao.save(employee);
                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
            }
            else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Designation employee ) {
        if(AuthProvider.isAuthorized(username,password,"Designation",AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(employee.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

*/

}


