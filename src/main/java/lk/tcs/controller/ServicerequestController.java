package lk.tcs.controller;

import lk.tcs.dao.ServicerequestDao;
import lk.tcs.entity.Servicerequest;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicerequestController {


    @Autowired
    private ServicerequestDao servicerequestdao;



    @RequestMapping(value = "/servicerequset/countrequest",method = RequestMethod.GET,produces = "application/json")
    public String totalrequest(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return servicerequestdao.totalrequest();
        }
        return null;
    }






    @RequestMapping(value = "/servicerequset/bookinglist",method = RequestMethod.GET,produces = "application/json")
    public List<Servicerequest> Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return servicerequestdao.bookingcus();
        }
        return null;
    }




    @RequestMapping(value="servicerequest/list", method= RequestMethod.GET, produces = "application/json")
    public List<Servicerequest> Servicerequeststatus(){return servicerequestdao.list();}




    @RequestMapping(value = "/servicerequest/index", method = RequestMethod.GET, produces = "application/json")
    public String nextIndex(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SELL, AuthProvider.SELECT)) {
            String indexno = servicerequestdao.nextIndex();
            return "{\"no\":\"" + indexno + "\"}";
        }
        return null;
    }




    @RequestMapping(value = "/servicerequest/nextservice", method = RequestMethod.GET, produces = "application/json")
    public String nextSRNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SELL, AuthProvider.SELECT)) {
            String serreno = servicerequestdao.nextSRNo();
            return "{\"no\":\"" + serreno + "\"}";
        }
        return null;
    }




    @RequestMapping(value = "/servicerequesturl", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Servicerequest> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.SERVICEREQUEST,AuthProvider.SELECT)) {
            return servicerequestdao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/servicerequests", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Servicerequest servicerequest) {
        //System.out.print("Added Successfully.....!");

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICEREQUEST,AuthProvider.INSERT)) {

            Servicerequest srq = servicerequestdao.findByServno(servicerequest.getCode());
            if (srq != null)
                return "Error-Validation : Supplier Already Exists";
            else
                try {
                   /* for(Supply su : supplier.getSupplyList())
                        su.setSupplierId(supplier);
*/
                    servicerequestdao.save(servicerequest);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/servicerequest", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Servicerequest servicerequest){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICEREQUEST,AuthProvider.UPDATE)) {
            try {
               /* for(Supply su : supplier.getSupplyList())
                    su.setSupplierId(supplier);*/

                servicerequestdao.save(servicerequest);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/servicerequest", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Servicerequest servicerequest){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICEREQUEST,AuthProvider.DELETE)) {
            try {
/*
                Servicerequest sre =  servicerequestdao.getOne(servicerequest.getId());
                sre.getSupplyList().remove(sre);*/

                servicerequestdao.delete(servicerequest);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }



    @RequestMapping(value = "/servicerequesturl", params = {"page", "size","indx","date","requestdate"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Servicerequest> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("indx") String indx, @RequestParam("date") String date, @RequestParam("requestdate") String requestdate) {


        if(AuthProvider.isAuthorized(username,password, ModuleList.SERVICEREQUEST,AuthProvider.SELECT)) {

            //list from DB
            List<Servicerequest> servicerequest = servicerequestdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            //list to Stream
            Stream<Servicerequest> servicerequestStream = servicerequest.stream();
            servicerequestStream = servicerequestStream.filter(s -> s.getIndx().toString().startsWith(indx));
            servicerequestStream = servicerequestStream.filter(s -> s.getRequestdate().toString().contains(requestdate));
            servicerequestStream = servicerequestStream.filter(s -> s.getDate().toString().contains(date));
            //Stream to List
            List<Servicerequest> servicerequests2 = servicerequestStream.collect(Collectors.toList());
            //List to Page
            int start = page * size;
            int end = start + size < servicerequests2.size() ? start + size : servicerequests2.size();
            Page<Servicerequest> suppage = new PageImpl<>(servicerequests2.subList(start, end), PageRequest.of(page, size), servicerequests2.size());
            return suppage;

        }

        return null;


    }













}
