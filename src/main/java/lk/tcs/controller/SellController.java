package lk.tcs.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lk.tcs.dao.ItemDao;
import lk.tcs.dao.SellDao;
import lk.tcs.dao.SellStatusDao;
import lk.tcs.dao.SellitemDao;
import lk.tcs.entity.*;
import lk.tcs.util.ModuleList;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
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
public class SellController {

    @Autowired
    private SellDao selldao;

    @Autowired
    private ItemDao itemdao;

    @Autowired
    private SellitemDao selitemdao;

    @Autowired
    private SellStatusDao sellStatusDao;

    @RequestMapping(value = "/sell/listsum",method = RequestMethod.GET,produces = "application/json")
    public String Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return selldao.totalsellsum();
        }
        return null;
    }




    @RequestMapping(value = "/sells", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Sell> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SELL, AuthProvider.SELECT)) {
            return selldao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/sell/nextsellno", method = RequestMethod.GET, produces = "application/json")
    public String nextCusNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SELL, AuthProvider.SELECT)) {
            String sellno = selldao.nextSellno();
            return "{\"no\":\"" + sellno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/sells", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Sell sell) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SELL, AuthProvider.INSERT)) {
            Sell se = selldao.findbyCode(sell.getCode());
            if (se != null) {
                return "Error validation" + sell.getCode();
            }
            try {
                for (Sellitem seitem : sell.getSellitemList()) {
                    seitem.setSellId(sell);
                }
                System.out.println("item"+sell.getSellitemList());
                selldao.save(sell);
                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/sells", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Sell sell){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SELL,AuthProvider.UPDATE)) {
            try {
                for (Sellitem seitem : sell.getSellitemList()) {
                    seitem.setSellId(sell);
                }

                selldao.save(sell);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/sells", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Sell sell){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SELL,AuthProvider.DELETE)) {
            try {

                Sell sel =  selldao.getOne(sell.getId());
                sel.getSellitemList().remove(sel);

                selldao.delete(sell);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/sells", params = {"page", "size","code","employeeId","sellstatusId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Sell> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("code") String code,@RequestParam("employeeId") Integer employeeId, @RequestParam("sellstatusId") Integer sellstatusId) {


            if (AuthProvider.isAuthorized(username, password, ModuleList.SELL,AuthProvider.SELECT)) {
                List<Sell> sells = selldao.findAll(Sort.by(Sort.Direction.DESC, "id"));
                Stream<Sell> sellStream = sells.stream();

                if(employeeId !=null)
                sellStream = sellStream.filter(s -> s.getEmployeeId().equals(selldao.getOne(employeeId)));
                if (sellstatusId != null)
                sellStream = sellStream.filter(s -> s.getSellstatusId().equals(sellStatusDao.getOne(sellstatusId)));

                sellStream = sellStream.filter(s -> s.getCode().contains(code));


                List<Sell> sellList = sellStream.collect(Collectors.toList());
                int start = page * size;
                int end = start + size < sellList.size() ? start + size : sellList.size();
                return new PageImpl<>(sellList.subList(start, end),
                        PageRequest.of(page, size),
                        sellList.size());
            }
            return null;
        }

/*
    private void qtyupdate(List<Sellitem> sellitems) {
        for(Sellitem sellitem : sellitems) {
            Item item = itemdao.getOne(sellitem.getItemId().getId());
            item.setQty(item.getQty()-serviceitem.getQty());
            itemdao.save(item);
        }

    }

    private void qtyAdd(List<Sellitem> sellitems) {
        for (Sellitem sellitem : sellitems) {
            Item item = itemdao.getOne(sellitem.getItemId().getId());
            item.setQty(item.getQty() + sellitem.getQty());
            itemdao.save(item);

}
        }*/


    }









    //    sellStream = sellStream.filter(s -> !(s.getName().equals("Admin")));

     /*       if (customerId != null)
            //          sellStream = sellStream.filter(c -> c.getCustomertypeId().equals(daoCustomertype.getOne(customertypeId)));
            if (sellstatusId != null)
    sellStream = sellStream.filter(c -> c.getTitleId().equals(daoTitle.getOne(titleId)));
    customerstream = customerstream.filter(c -> c.getNic().startsWith(nic));
    customerstream = customerstream.filter(c -> c.getName().contains(name));

*/








