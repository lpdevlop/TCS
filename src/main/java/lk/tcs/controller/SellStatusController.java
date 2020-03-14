package lk.tcs.controller;


import lk.tcs.dao.SellStatusDao;
import lk.tcs.entity.Sellstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SellStatusController {

@Autowired
    SellStatusDao setaDao;


    @RequestMapping(value="sellstatuses/list", method= RequestMethod.GET, produces = "application/json")
    public List<Sellstatus> sellstatuses(){return setaDao.list();}






}


