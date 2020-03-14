package lk.tcs.controller;


import lk.tcs.dao.PorderDao;
import lk.tcs.entity.Item;
import lk.tcs.entity.Porder;
import lk.tcs.entity.Porderitem;
import lk.tcs.util.ModuleList;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PorderController {

    @Autowired
    private PorderDao porderdao;


    @RequestMapping(value = "/porder/listcount",method = RequestMethod.GET,produces = "application/json")
    public String Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return porderdao.countporder();
        }
        return null;
    }



    @RequestMapping(value = "/porder/list", method = RequestMethod.GET, produces = "application/json")
    public List<Porder> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return porderdao.list();
        }
        return null;
    }


    @RequestMapping(value = "/grnitems/listbyitem", params = "code", method = RequestMethod.GET, produces = "application/json")
    public List<Porder> itemslistpoitem(@RequestParam("code") String code) {

        return porderdao.listbyorder(code);



    }




    @RequestMapping(value = "/porders", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Porder> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.SELECT)) {
            return porderdao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/porder/nextporderno", method = RequestMethod.GET, produces = "application/json")
    public String nextPorderno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.SELECT)) {
            String porderno = porderdao.nextPorderno();
            return "{\"no\":\"" + porderno + "\"}";
        }
        return null;
    }


    @RequestMapping(value = "/porders", params = {"page", "size", "code", "orderdate", "requiredate"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Porder> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("orderdate") String orderdate, @RequestParam("requiredate") String requiredate) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.SELECT)) {
            List<Porder> porders = porderdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Porder> porderStream = porders.stream();

            porderStream = porderStream.filter(p -> p.getCode().contains(code));

            if (orderdate != null)
                porderStream = porderStream.filter(p -> p.getOrderdate().toString().contains(orderdate));
            if (requiredate != null)
                porderStream = porderStream.filter(p -> p.getRequiredate().toString().contains(requiredate));


            List<Porder> porderList = porderStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < porderList.size() ? start + size : porderList.size();
            return new PageImpl<>(porderList.subList(start, end),
                    PageRequest.of(page, size),
                    porderList.size());
        }
        return null;
    }


    @RequestMapping(value = "/porders", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Porder porder) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.INSERT)) {
            Porder po = porderdao.findbyCode(porder.getCode());
            if (po != null) {
                return "Error validation" + porder.getCode();
            }
            try {

                for (Porderitem pditem : porder.getPorderitemList()) {
                    pditem.setPorderId(porder);
                }

                porderdao.save(porder);
                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/porders", method = RequestMethod.PUT)
    public String update(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Porder porder) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.UPDATE)) {
            try {
                for (Porderitem poitem : porder.getPorderitemList()) {
                    poitem.setPorderId(porder);
                }

                porderdao.save(porder);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/porders", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Porder porder) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.PORDER, AuthProvider.DELETE)) {
            try {

                Porder po = porderdao.getOne(porder.getId());
                po.getPorderitemList().remove(po);

                porderdao.delete(porder);

                return "0";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error-Delete : " + e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";
    }



}



























