package lk.tcs.controller;

import lk.tcs.dao.DisposalDao;
import lk.tcs.dao.DisposalItemDao;
import lk.tcs.dao.ItemDao;
import lk.tcs.entity.Disposal;
import lk.tcs.entity.Disposalitem;
import lk.tcs.entity.Disposalstatus;
import lk.tcs.entity.Item;
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
import java.util.Scanner;

@RestController
public class DisposalController {

    @Autowired
    private DisposalDao disposaldao;

    @Autowired
    private ItemDao itemdao;

    @Autowired
    private DisposalItemDao disposalItemDao;

    @RequestMapping(value = "/disposals", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Disposal> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.SELECT)) {
            return disposaldao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/disposal/nextdisposalno", method = RequestMethod.GET, produces = "application/json")
    public String nextDpNo(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.SELECT)) {
            String dpno = disposaldao.nextDpno();
            return "{\"no\":\"" + dpno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/disposals", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Disposal disposal) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.INSERT)) {
            Disposal dp = disposaldao.findbyCode(disposal.getCode());
            if (dp != null) {
                return "Error validation" + dp.getCode();
            }
            try {
                for (Disposalitem dpitem : disposal.getDisposalitemList()) {
                    dpitem.setDisposalId(disposal);
                }

                disposaldao.save(disposal);
                qtyremove(disposalItemDao.collectionItemList(disposal.getId()));

                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                System.out.println("item" + disposal.getDisposalitemList());

                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/disposals", method = RequestMethod.PUT)
    public String update(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Disposal disposal) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.UPDATE)) {
            try {
                for (Disposalitem dpitem : disposal.getDisposalitemList()) {
                    dpitem.setDisposalId(disposal);
                }

                if(disposal.getDisposalstatusId().getId()==1)  {
                    qtyremove(disposalItemDao.collectionItemList(disposal.getId()));
                    disposaldao.save(disposal);
                    System.out.println(disposal.getDisposalstatusId().getId());


                } else if (disposal.getDisposalstatusId().getId()==2){
                    qtyAdd(disposalItemDao.collectionItemList(disposal.getId()));
                    disposaldao.save(disposal);
                    System.out.println(disposal.getDisposalstatusId().getId());

                }

              /*  else if (disposal.getDisposalstatusId().equals(1)) {
                    qtyremove(disposalItemDao.collectionItemList(disposal.getId()));
                    disposaldao.save(disposal);

                }*/
            //    System.out.println(disposal.getDisposalstatusId().getId());
             //   qtyAdd(disposalItemDao.collectionItemList(disposal.getId()));
              //  disposaldao.save(disposal);

                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value = "/disposals", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Disposal disposal) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.DELETE)) {
            try {

                Disposal dp = disposaldao.getOne(disposal.getId());
                dp.getDisposalitemList().remove(dp);
                qtyAdd(disposalItemDao.collectionItemList(disposal.getId()));

                disposaldao.delete(disposal);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/disposals", params = {"page", "size", "code", "employeeid", "disposaldate"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Disposal> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("employeeid") Integer employeeId, @RequestParam("disposaldate") String disposaldate) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.DISPOSAL, AuthProvider.SELECT)) {
            List<Disposal> disposals = disposaldao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Disposal> disposalStream = disposals.stream();


            if (employeeId != null)
                disposalStream = disposalStream.filter(d -> d.getEmployeeId().equals(disposaldao.getOne(employeeId)));
            if (disposaldate != null)
                disposalStream = disposalStream.filter(d -> d.getDisposaldate().toString().contains(disposaldate));

            disposalStream = disposalStream.filter(d -> d.getCode().contains(code));


            List<Disposal> disposalsList = disposalStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < disposalsList.size() ? start + size : disposalsList.size();
            return new PageImpl<>(disposalsList.subList(start, end),
                    PageRequest.of(page, size),
                    disposalsList.size());
        }
        return null;
    }

    //activate satatus
    private void qtyremove(List<Disposalitem> disposalitems) {
        for (Disposalitem disposalitem : disposalitems) {
            Item item = itemdao.getOne(disposalitem.getItemId().getId());
            item.setQty(item.getQty() - disposalitem.getQty());
            itemdao.save(item);
        }

    }

    //deactivate satatus
    private void qtyAdd(List<Disposalitem> disposalitems) {
        for (Disposalitem disposalitem : disposalitems) {
            Item item = itemdao.getOne(disposalitem.getItemId().getId());
            item.setQty(item.getQty() + disposalitem.getQty());
            itemdao.save(item);


        }


    }


   
}
              /*  else if (disposal.getDisposalstatusId().equals(1)) {
                    qtyremove(disposalItemDao.collectionItemList(disposal.getId()));
                    disposaldao.save(disposal);


    }














}

    //    sellStream = sellStream.filter(s -> !(s.getName().equals("Admin")));

     /*       if (customerId != null)
            //          sellStream = sellStream.filter(c -> c.getCustomertypeId().equals(daoCustomertype.getOne(customertypeId)));
            if (sellstatusId != null)
    sellStream = sellStream.filter(c -> c.getTitleId().equals(daoTitle.getOne(titleId)));
    customerstream = customerstream.filter(c -> c.getNic().startsWith(nic));
    customerstream = customerstream.filter(c -> c.getName().contains(name));

*/


    