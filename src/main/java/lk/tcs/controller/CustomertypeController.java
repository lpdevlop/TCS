package lk.tcs.controller;


import lk.tcs.dao.CustomertypeDao;
import lk.tcs.entity.Customertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomertypeController {


    @Autowired
    private CustomertypeDao dao;

    @RequestMapping(value = "/customertype/list",method = RequestMethod.GET, produces = "application/json")
     public List<Customertype>customertypes(){return dao.list();}









}
