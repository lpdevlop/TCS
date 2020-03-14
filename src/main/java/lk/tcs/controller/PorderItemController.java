package lk.tcs.controller;

import lk.tcs.dao.PorderItemDao;
import lk.tcs.entity.Item;
import lk.tcs.entity.Itemstatus;
import lk.tcs.entity.Porderitem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class PorderItemController {

    @Autowired
    private PorderItemDao poitdao;








/*

    @RequestMapping(value = "/items/listbypoit", params = "code" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Porderitem> itemslistbysupplier(@RequestParam("code") Character code){
        return poitdao.listbypoditem(code);
    }
*/



}
