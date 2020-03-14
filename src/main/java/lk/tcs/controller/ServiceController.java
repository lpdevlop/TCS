package lk.tcs.controller;

import lk.tcs.dao.ItemDao;
import lk.tcs.dao.ServiceDao;
import lk.tcs.dao.ServiceitemDao;
import lk.tcs.entity.*;
import lk.tcs.entity.Service;
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
public class ServiceController {

@Autowired
private ServiceDao servicedao;

@Autowired
private ServiceitemDao serviceitemDao;

@Autowired
private ItemDao itemDao;

    @RequestMapping(value="serviceslist/list", method= RequestMethod.GET, produces = "application/json")
    public List<Service> servicelistwarranty(){return servicedao.list();}




    @RequestMapping(value = "/services", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Service> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICE, AuthProvider.SELECT)) {
            return servicedao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/service/nextserviceno", method = RequestMethod.GET, produces = "application/json")
    public String nextServiceNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICE, AuthProvider.SELECT)) {
            String serviceno = servicedao.nextServiceno();
            return "{\"no\":\"" + serviceno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Service service) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICE ,AuthProvider.INSERT)) {
            Service ser = servicedao.findbyCode(service.getCode());
            if (ser != null) {
                return "Error validation" + service.getCode();
            }
            try {
                System.out.println("item"+service.getServiceitemList());
                for (Serviceitem serviceitem : service.getServiceitemList()) {
                    serviceitem.setServiceId(service);
                }
                qtyupdate(serviceitemDao.collectionItemList(service.getId()));

                servicedao.save(service);

                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/services", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Service service){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICE,AuthProvider.UPDATE)) {
            try {
                for (Serviceitem serviceitem : service.getServiceitemList()) {
                    serviceitem.setServiceId(service);
                }
                qtyAdd(serviceitemDao.collectionItemList(service.getId()));
                servicedao.save(service);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/services", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Service service){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SELL,AuthProvider.DELETE)) {
            try {

                Service ser =  servicedao.getOne(service.getId());
                ser.getServiceitemList().remove(service);
                qtyAdd(serviceitemDao.collectionItemList(service.getId()));
                servicedao.delete(service);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/services", params = {"page", "size","code","employeeId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Service> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("code") String code,@RequestParam("employeeId") Integer employeeId) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICE,AuthProvider.SELECT)) {
            List<Service> services = servicedao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Service> serviceStream = services.stream();

            if(employeeId !=null)
                serviceStream = serviceStream.filter(s -> s.getEmployeeId().equals(servicedao.getOne(employeeId)));
            if (code != null)
                serviceStream = serviceStream.filter(s-> s.getCode().contains(code));



            List<Service> serviceList = serviceStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < serviceList.size() ? start + size : serviceList.size();
            return new PageImpl<>(serviceList.subList(start, end),
                    PageRequest.of(page, size),
                    serviceList.size());
        }
        return null;
    }

    private void qtyupdate(List<Serviceitem> serviceitems) {
        for(Serviceitem serviceitem : serviceitems) {
            Item item = itemDao.getOne(serviceitem.getItemId().getId());
            item.setQty(item.getQty()-serviceitem.getQty());
            itemDao.save(item);
        }

    }

    private void qtyAdd(List<Serviceitem> serviceitems) {
        for (Serviceitem serviceitem : serviceitems) {
            Item item = itemDao.getOne(serviceitem.getItemId().getId());
            item.setQty(item.getQty() + serviceitem.getQty());
            itemDao.save(item);


        }


    }















}
















