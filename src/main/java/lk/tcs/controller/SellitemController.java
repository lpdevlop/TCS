package lk.tcs.controller;

import lk.tcs.dao.SellitemDao;
import lk.tcs.entity.Sellitem;
import lk.tcs.entity.Servicerequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SellitemController {

    @Autowired
    private SellitemDao selitemdao;


    @RequestMapping(value = "/sellitem/listbysupplier",method = RequestMethod.GET,produces = "application/json")
    public List<Sellitem> Reorderitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        if(AuthProvider.isUser(username,password)) {
            return selitemdao.findsupplierqty();
        }
        return null;
    }







}
