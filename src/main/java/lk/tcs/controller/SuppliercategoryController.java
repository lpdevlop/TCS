package lk.tcs.controller;

import lk.tcs.dao.SuppliercategoryDao;
import lk.tcs.entity.Suppliercategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuppliercategoryController {

@Autowired
    SuppliercategoryDao sucadao;

    @RequestMapping(value = "/category/list/bysupplier", params = "supplierid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Suppliercategory> itemslistbysupplier(@RequestParam("supplierid") Integer supplierid){
        return sucadao.suppliercategoryList(supplierid);
    }

    /*@RequestMapping(value = "/items/listbysupplier", params = "supplierid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Suppliercategory> suppliercategoryList(@RequestParam("supplierid") Integer supplierid){
        return sucadao.suppliercategoryList(supplierid);
    }*/






}
