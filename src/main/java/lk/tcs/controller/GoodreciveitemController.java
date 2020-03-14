package lk.tcs.controller;

import lk.tcs.dao.GoodreciveDao;
import lk.tcs.dao.GoodreciveitemDao;
import lk.tcs.dao.ItemDao;
import lk.tcs.dao.PorderDao;
import lk.tcs.entity.Goodrecive;
import lk.tcs.entity.Goodreciveitem;
import lk.tcs.entity.Item;
import lk.tcs.entity.Porder;
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
public class GoodreciveitemController {

    @Autowired
    private GoodreciveDao gooddao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private GoodreciveitemDao goodreciveitem;







    @RequestMapping(value = "/goodrecive", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Goodrecive> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.GOODRECIVE, AuthProvider.SELECT)) {
            return gooddao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/good/nextsgoodno", method = RequestMethod.GET, produces = "application/json")
    public String nextGoodReciveno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.GOODRECIVE, AuthProvider.SELECT)) {
            String goodno = gooddao.nextGoodReciveno();
            return "{\"no\":\"" + goodno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Goodrecive good) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.GOODRECIVE, AuthProvider.INSERT)) {
            Goodrecive go = gooddao.findbyCode(good.getCode());
            if (go != null) {
                return "Error validation" + good.getCode();
            }
            try {

           //     System.out.println("item"+Goodrecive good);

                for (Goodreciveitem goitem : good.getGoodreciveitemList()) {
                    goitem.setGoodreciveId(good);

                }
                qtyremove(goodreciveitem.collectionItemList(good.getId()));

                gooddao.save(good);

                return "0";
            } catch (Exception s) {

                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/goods", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Goodrecive good){

        if(AuthProvider.isAuthorized(username,password,ModuleList.GOODRECIVE,AuthProvider.UPDATE)) {
            try {


                for (Goodreciveitem goitem : good.getGoodreciveitemList()) {
                    goitem.setGoodreciveId(good);
                }



                /*if(good.getGooodrecivestatusId().getId()==1)  {
                    qtyremove(goodreciveitem.collectionItemList(good.getId()));
                    gooddao.save(good);
                    System.out.println(good.getGooodrecivestatusId().getId());


                } else if (good.getGooodrecivestatusId().getId()==2){
                    qtyAdd(goodreciveitem.collectionItemList(good.getId()));
                    gooddao.save(good);
                    System.out.println(good.getGooodrecivestatusId().getId());

                }*/
                gooddao.save(good);

                return "0";
            } catch (Exception e) {
                e.printStackTrace();
               // return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/goods", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Goodrecive good){

        if(AuthProvider.isAuthorized(username,password,ModuleList.GOODRECIVE,AuthProvider.DELETE)) {
            try {

                Goodrecive go =  gooddao.getOne(good.getId());
                go.getGoodreciveitemList().remove(good);
                qtyAdd(goodreciveitem.collectionItemList(good.getId()));

                gooddao.delete(good);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/good", params = {"page", "size","code","recivedate"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Goodrecive> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("recivedate") String recivedate) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.GOODRECIVE,AuthProvider.SELECT)) {
            List<Goodrecive> goods = gooddao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Goodrecive> goodStream = goods.stream();

            if(recivedate !=null)
                goodStream  = goodStream.filter(g -> g.getRecivedate().toString().contains(recivedate));

            goodStream = goodStream.filter(g -> g.getCode().contains(code));


            List<Goodrecive> goodList = goodStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < goodList.size() ? start + size : goodList.size();
            return new PageImpl<>(goodList.subList(start, end),
                    PageRequest.of(page, size),
                    goodList.size());
        }
        return null;
    }

    private void qtyremove(List<Goodreciveitem> goodreciveitems) {
            for(Goodreciveitem goodreciveitem : goodreciveitems) {
            Item item = itemDao.getOne(goodreciveitem.getItemId().getId());
            item.setQty(item.getQty()-goodreciveitem.getQty());
            itemDao.save(item);
        }

    }

    private void qtyAdd(List<Goodreciveitem> disposalitems) {
        for (Goodreciveitem disposalitem : disposalitems) {
            Item item = itemDao.getOne(disposalitem.getItemId().getId());
            item.setQty(item.getQty() + disposalitem.getQty());
            itemDao.save(item);


        }


    }
















}
