package lk.tcs.controller;

import lk.tcs.dao.CustomertypeDao;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Customer;
import lk.tcs.dao.CustomerDao;
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
public class CustomerController {

    @Autowired
    private CustomerDao cusdao;

    @Autowired
    private CustomertypeDao daoCustomertype;

    @Autowired
    private TitleDao daoTitle;

   //customer mobile number find
    @RequestMapping(value = "/customers/findmobilenumlist", params = {"cuid"},method = RequestMethod.GET, produces = "application/json")
    public List<Customer> findByMobilenum(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("cuid") Integer cuid) {
        if (AuthProvider.isUser(username, password))
            return cusdao.findByMobilenum(cuid);
        else return null;
    }
    @RequestMapping(value = "/customer/nextcusno",method = RequestMethod.GET, produces = "application/json")
    public String nextCusNo(@CookieValue(value = "username") String username, @CookieValue(value = "password")
            String password ){
        if (AuthProvider.isAuthorized(username, password, ModuleList.CUSTOMER, AuthProvider.SELECT)) {
            String cusno = cusdao.nextCusrno();
            return "{\"no\":\"" +cusno+ "\"}";
        }
        return null;
    }
@RequestMapping(value = "/customers/list", method = RequestMethod.GET, produces = "application/json")
public List<Customer> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
    if(AuthProvider.isUser(username,password)) {
        return cusdao.list();
    }
    return null;
}



   /* @RequestMapping(value = "/customers/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Customer> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return cusdao.listWithoutUsers();
        }
        return null;
    }

    @RequestMapping(value = "/customers/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Customer> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return cusdao.listWithUseraccount();
        }
        return null;
    }*/

    @RequestMapping(value = "/customers", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Customer> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.CUSTOMER, AuthProvider.SELECT)) {
            return cusdao.findAll(PageRequest.of(page, size));
       }
       return null;
    }

    @RequestMapping(value ="/customers", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Customer customer ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CUSTOMER,AuthProvider.DELETE)) {
            try {
                cusdao.delete(cusdao.getOne(customer.getId()));

                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }



    @RequestMapping(value = "/customers", params = {"page", "size","name","nic","customertypeId","titleId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Customer> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("nic") String nic, @RequestParam("customertypeId") Integer customertypeId, @RequestParam("titleId") Integer titleId) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CUSTOMER,AuthProvider.SELECT)) {

            List<Customer> customers = cusdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Customer> customerstream = customers.stream();
          //  customerstream = customerstream.filter(c -> !(c.getName().equals("Admin")));

            if (customertypeId != null)
                customerstream = customerstream.filter(c -> c.getCustomertypeId().equals(daoCustomertype.getOne(customertypeId)));
            if (titleId != null)
                customerstream = customerstream.filter(c -> c.getTitleId().equals(daoTitle.getOne(titleId)));

            customerstream = customerstream.filter(c -> c.getNic().startsWith(nic));
            customerstream = customerstream.filter(c -> c.getName().contains(name.trim()));

            List<Customer> customers2 = customerstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < customers2.size() ? start + size : customers2.size();
            Page<Customer> emppage = new PageImpl<>(customers2.subList(start, end), PageRequest.of(page, size), customers2.size());

            return emppage;
        }

        return null;

    }
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Customer customer) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CUSTOMER,AuthProvider.INSERT)) {


            Customer cusnic = cusdao.findByNIC(customer.getNic());
          //  Customer cusnumber = dao.findByCode(customer.getCode());
            if (cusnic != null)
                return "Error-Validation : NIC Exists";
          //  else if (cusnumber != null)
         //       return "Error-Validation : Number Exists";
           else
                try {
                    cusdao.save(customer);
                    return "0";

                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();

                }
        }
        return "Error-Saving : You have no Permission";

    }
    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Customer customer) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CUSTOMER,AuthProvider.UPDATE)) {
            Customer emp = cusdao.findByNIC(customer.getNic());
            if(emp==null || emp.getId()==customer.getId()) {
                try {
                    cusdao.save(customer);
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





}





