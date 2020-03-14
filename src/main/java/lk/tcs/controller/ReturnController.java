package lk.tcs.controller;


import lk.tcs.dao.ItemDao;
import lk.tcs.dao.ReturnDao;
import lk.tcs.dao.ReturnitemitemDao;
import lk.tcs.dao.ReturnstatusDao;
import lk.tcs.entity.*;
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
public class ReturnController {

@Autowired
private ReturnDao returndao;

@Autowired
private ItemDao itemDao;

@Autowired
private ReturnitemitemDao returnitemitemDao;

@Autowired
private ReturnstatusDao returnstatusDao;

    @RequestMapping(value = "/return/listcount",method = RequestMethod.GET,produces = "application/json")
    public String Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return returndao.countreturn();
        }
        return null;
    }


    @RequestMapping(value = "/returns", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Returnitem> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.RETURNITEM, AuthProvider.SELECT)) {
            return returndao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/return/nextreturnno", method = RequestMethod.GET, produces = "application/json")
    public String nextReturnNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.RETURNITEM, AuthProvider.SELECT)) {
            String returnno = returndao.nextReturnno();
            return "{\"no\":\"" + returnno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/returns", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Returnitem returnitem) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.RETURNITEM, AuthProvider.INSERT)) {
            Returnitem re = returndao.findbyCode(returnitem.getCode());
            if (re != null) {
                return "Error validation" + returnitem.getCode();
            }
            try {
                System.out.println("items"+returnitem.getReturnitemitemList());
                for (Returnitemitem reitem : returnitem.getReturnitemitemList()) {
                    reitem.setReturnitemId(returnitem);
                }
              System.out.println("item"+returnitem.getReturnitemitemList());
                returndao.save(returnitem);
                qtyremove(returnitemitemDao.collectionItemList(returnitem.getId()));

                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/returns", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Returnitem returnitem){

        if(AuthProvider.isAuthorized(username,password,ModuleList.RETURNITEM,AuthProvider.UPDATE)) {
            try {
                for (Returnitemitem reitem : returnitem.getReturnitemitemList()) {
                    reitem.setReturnitemId(returnitem);
                }
                if(returnitem.getReturnstatusId().getId()==1)  {
                    qtyremove(returnitemitemDao.collectionItemList(returnitem.getId()));
                    returndao.save(returnitem);

                } else if (returnitem.getReturnstatusId().getId()==2){
                    qtyAdd(returnitemitemDao.collectionItemList(returnitem.getId()));
                    returndao.save(returnitem);

                }
             //   returndao.save(returnitem);
             //   qtyAdd(returnitemitemDao.collectionItemList(returnitem.getId()));

                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/returns", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Returnitem returnitem){

        if(AuthProvider.isAuthorized(username,password,ModuleList.RETURNITEM,AuthProvider.DELETE)) {
            try {

                Returnitem rel =  returndao.getOne(returnitem.getId());
                rel.getReturnitemitemList().remove(rel);
                qtyAdd(returnitemitemDao.collectionItemList(returnitem.getId()));

                returndao.delete(returnitem);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/returns", params = {"page", "size","code","employeeId","rawdate"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Returnitem> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("employeeId") Integer employeeId, @RequestParam("rawdate") String rawdate) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.RETURNITEM,AuthProvider.SELECT)) {
            List<Returnitem> returnitems = returndao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Returnitem> returnitemStream = returnitems.stream();

/*

            if (returnstatusId != null)
                returnitemStream = returnitemStream.filter(r -> r.getReturnstatusId().equals(returnstatusDao.getOne(returnstatusId)));
            if(employeeId !=null)
                returnitemStream = returnitemStream.filter(r -> r.getEmployeeId().equals(returndao.getOne(employeeId)));
*/
            if(rawdate !=null)
                returnitemStream  = returnitemStream.filter(r -> r.getRawdate().toString().contains(rawdate));


            returnitemStream = returnitemStream.filter(r -> r.getCode().contains(code));


            List<Returnitem> returnitemList = returnitemStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < returnitemList.size() ? start + size : returnitemList.size();
            return new PageImpl<>(returnitemList.subList(start, end), PageRequest.of(page, size), returnitemList.size());

        }
        return null;
    }




    private void qtyremove(List<Returnitemitem> returnitems) {
        for (Returnitemitem returnitem : returnitems) {
            Item item = itemDao.getOne(returnitem.getItemId().getId());
            item.setQty(item.getQty()-returnitem.getQty());
            itemDao.save(item);
        }

    }

    private void qtyAdd(List<Returnitemitem> returnitems){
        for(Returnitemitem returnitem:returnitems){
            Item item =itemDao.getOne(returnitem.getItemId().getId());
            item.setQty(item.getQty()+returnitem.getQty());
            itemDao.save(item);




        }




    }



}









