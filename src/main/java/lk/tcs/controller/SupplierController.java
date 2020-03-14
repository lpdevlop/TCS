package lk.tcs.controller;

import lk.tcs.dao.SupplierDao;
import lk.tcs.dao.SupplierStatusDao;
import lk.tcs.entity.*;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
public class SupplierController {

    @Autowired
    private SupplierDao supdao;

    @Autowired
    private SupplierStatusDao supstatdao;

    /*@RequestMapping(value = "/category/list/bysupplier", params = "supplierid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Supplier> suppliercategoryList(@RequestParam("supplierid") Integer supplierid){
        return supdao.suppliercategoryList(supplierid);
    }
*/
   /* @RequestMapping(value = "/items/listbysupplier", params = "supplierid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Supplier> suppliercategoryList(@RequestParam("supplierid") Integer supplierid){
        return supdao.suppliercategoryList(supplierid);
    }

*/


    @RequestMapping(value = "/supplier/nextcusno",method = RequestMethod.GET, produces = "application/json")
    public String nextSupNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password ){
        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.SELECT)) {
            String supcode = supdao.nextSupno();
            return "{\"no\":\"" +supcode+ "\"}";
        }
        return null;
    }





    @RequestMapping(value = "/suppliers", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Supplier> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.SELECT)) {
            return supdao.findAll(PageRequest.of(page, size));
        }
        return null;
    }

    @RequestMapping(value = "/suppliers/list", method = RequestMethod.GET, produces = "application/json")
    public List<Supplier> list(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isUser(username, password)) {
            return supdao.list();
        }
        return null;
    }


    @RequestMapping(value = "/suppliers", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Supplier supplier) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.INSERT)) {
            Supplier supnic = supdao.findByContact1(supplier.getContact1());
         /*   if (supnic != null)
                return "Error-Validation : Contact Exists";
            else*/
                try {

                    for(Suppliercategory su : supplier.getSuppliercategoryList()) {

                        su.setSupplierid(supplier);
                        System.out.println("supplier"+supplier.getSuppliercategoryList());

                    }
                    supdao.save(supplier);

                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Saving : " + e.getMessage();

                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/suppliers", method = RequestMethod.PUT)
    public String update(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Supplier supplier) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.UPDATE)) {
     //       Supplier supnic = supdao.findByContact1(supplier.getContact1());
           // if (supnic== null || supnic.getId() == supplier.getId()) {
                try {


                    Supplier supplierrompersistent = supdao.findByUsername(supplier.getName());
                    supplierrompersistent.setName(supplier.getName());
                    supplierrompersistent.getSuppliercategoryList().clear();
                    supplierrompersistent.setContact1(supplier.getContact1());
                    supplierrompersistent.setContact2(supplier.getContact2());
                    supplierrompersistent.setFax(supplier.getFax());
                    supplierrompersistent.setEmail(supplier.getEmail());
                    supplierrompersistent.setSupplierstatusId(supplier.getSupplierstatusId());
                    supplierrompersistent.setAddress(supplier.getAddress());
                    supplierrompersistent.setDate(supplier.getDate());
                    supplierrompersistent.setDescription(supplier.getDescription());

                    for(Suppliercategory su : supplier.getSuppliercategoryList()){

                        supplierrompersistent.getSuppliercategoryList().add(su);
                        su.setSupplierid(supplierrompersistent);

                    }
                    supdao.save(supplierrompersistent);
                    return "0";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error-Updating : " + e.getMessage();

                }
            } else {
                return "Error-Updating : Contact Exists";
            }
        }
      //  return "Error-Updating : You have no Permission";
    //}


    @RequestMapping(value = "/suppliers", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @RequestBody Supplier supplier) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.DELETE)) {
            try {
                supdao.delete(supdao.getOne(supplier.getId()));
                return "0";
            } catch (Exception e) {
                return "Error-Deleting : " + e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }


    @RequestMapping(value = "/suppliers", params = {"page", "size", "name", "address", "supplierstatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Supplier> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("supplierstatusId") Integer supplierstatusId) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SUPPLIER, AuthProvider.SELECT)) {

            List<Supplier> suppliers = supdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Supplier> supplierStream = suppliers.stream();

            if (supplierstatusId != null)
                supplierStream = supplierStream.filter(s -> s.getSupplierstatusId().equals(supstatdao.getOne(supplierstatusId)));
            supplierStream = supplierStream.filter(s -> s.getName().contains(name));
            supplierStream = supplierStream.filter(s -> s.getAddress().contains(address));


            List<Supplier> supplier2 = supplierStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < supplier2.size() ? start + size : supplier2.size();
            Page<Supplier> suppage = new PageImpl<>(supplier2.subList(start, end), PageRequest.of(page, size), supplier2.size());

            return suppage;
        }

        return null;


    }
}

