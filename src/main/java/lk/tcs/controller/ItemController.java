package lk.tcs.controller;

import lk.tcs.dao.*;
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

@RestController
public class ItemController {

    @Autowired
    private BrandDao dao;

    @Autowired
    private UnitDao daoUnit;

    @Autowired
    private CategoryDao daoCategory;

    @Autowired
    private ItemstatusDao daoItem;

    @Autowired
    private SubcategoryDao daoSubcategory;

    @Autowired
    private ItemDao itemdao;




    @RequestMapping(value = "/items/listbyitemusesubcategory", params = "subcategoryid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Item> listbyitemusesubcategory(@RequestParam("subcategoryid") Integer subcategoryid){
        return itemdao.listbyitemusesubcategory(subcategoryid);
    }




    @RequestMapping(value = "/items/catselectlistbyitem", params = "categoryid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Item> itemslistbysupplier(@RequestParam("categoryid") Integer categoryid){
        return itemdao.listbysupplier(categoryid);
    }


    @RequestMapping(value = "/items/reorderlist",method = RequestMethod.GET,produces = "application/json")
    public List<Item> Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return itemdao.reorerfind();
        }
        return null;
    }


    @RequestMapping(value = "/items/listbyitem",method = RequestMethod.GET,produces = "application/json")
    public List<Item> listbyitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return itemdao.listbyitem();
        }
        return null;
    }


   @RequestMapping(value = "/item/nextcusno",method = RequestMethod.GET, produces = "application/json")
    public String nextItemno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password ){
        if (AuthProvider.isAuthorized(username, password, ModuleList.ITEM, AuthProvider.SELECT)) {
            String itemno = itemdao.nextItemno();
            return "{\"no\":\"" +itemno+ "\"}";
        }
        return null;
    }

@RequestMapping(value = "/items/list", method = RequestMethod.GET, produces = "application/json")
public List<Item> list(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
    if(AuthProvider.isUser(username,password)) {
        return itemdao.list();
    }
    return null;
}

    @RequestMapping(value = "/items/list/withoutusers",  method = RequestMethod.GET, produces = "application/json")
    public List<Item> listwithoutusers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return itemdao.listWithoutUsers();
        }
        return null;
    }

    @RequestMapping(value = "/items/list/withuseraccount",  method = RequestMethod.GET, produces = "application/json")
    public List<Item> listwithuseraccount(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if(AuthProvider.isUser(username,password)) {
            return itemdao.listWithUseraccount();
        }
        return null;
    }



    @RequestMapping(value ="/items", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Item> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if(AuthProvider.isAuthorized(username,password,ModuleList.ITEM, AuthProvider.SELECT)) {

            return itemdao.findAll(PageRequest.of(page, size));

       }
       return null;
    }


    @RequestMapping(value = "/items", params = {"page", "size","name","price","categoryid","subcategoryid"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Item> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("name") String name, @RequestParam("price") Long price, @RequestParam("categoryid") Integer categoryId, @RequestParam("subcategoryid") Integer subcategoryId) {



        if(AuthProvider.isAuthorized(username,password, ModuleList.ITEM,AuthProvider.SELECT)) {

            List<Item> items = itemdao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Item> itemstream = items.stream();
        //    itemstream = itemstream.filter(i -> !(i.getName().equals("Admin")));

            if (categoryId != null)
                itemstream = itemstream.filter(i -> i.getCategoryId().equals(daoCategory.getOne(categoryId)));
            if (subcategoryId != null)
                itemstream = itemstream.filter(i -> i.getSubcategoryId().equals(daoSubcategory.getOne(subcategoryId)));
           // itemstream = itemstream.filter(i ->i.getPrice().equals(price));
            itemstream = itemstream.filter(i -> i.getName().contains(name.trim()));

            List<Item> items2 = itemstream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < items2.size() ? start + size : items2.size();
            Page<Item> itmpage = new PageImpl<>(items2.subList(start, end), PageRequest.of(page, size), items2.size());

            return itmpage;
        }

        return null;

    }


    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Item item) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ITEM,AuthProvider.INSERT)) {


           // Item itmnic = dao.findByNIC(customer.getNic());
          //  Customer cusnumber = dao.findByCode(customer.getCode());
        //    if (cusnic != null)
          //      return "Error-Validation : NIC Exists";
          //  else if (cusnumber != null)
         //       return "Error-Validation : Number Exists";
       //    else
                try {
                    itemdao.save(item);
                    return "0";

                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();

                }


        }

        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/items", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Item item) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ITEM,AuthProvider.UPDATE)) {
        //Item itm = dao.findByNIC(.getNic());
         // if(itm==null || itm.getId()==item.getId())
       //     {
              try {
                    itemdao.save(item);


                    return "0";
                }
                catch(Exception e) {
                    return "Error-Updating : "+e.getMessage();
                }
       //     }
     //       else {  return "Error-Updating : NIC Exists"; }
        }
        return "Error-Updating : You have no Permission";
    }


    @RequestMapping(value ="/items", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@RequestBody Item item ) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.ITEM,AuthProvider.DELETE)) {
            try {
                itemdao.delete(itemdao.getOne(item.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }





}





