package lk.tcs.controller;


import lk.tcs.dao.CategoryBrandDao;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Categorybrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CateogryBandController {

@Autowired
private CategoryBrandDao categoryBrandDao;


    @RequestMapping(value = "/category/list/bybrand", params = {"categoryid"},method = RequestMethod.GET, produces = "application/json")
    public List<Categorybrand> brands(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("categoryid") Integer categoryid) {
        if (AuthProvider.isUser(username, password))
            return categoryBrandDao.listBARND(categoryid);
        else return null;
    }




}
