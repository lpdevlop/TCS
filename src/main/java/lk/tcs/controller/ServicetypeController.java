package lk.tcs.controller;


import lk.tcs.dao.ServicetypeDao;
import lk.tcs.entity.Sellstatus;
import lk.tcs.entity.Servicetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicetypeController {

    @Autowired
    private ServicetypeDao stypdao;


    @RequestMapping(value="servicestatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Servicetype> list(){return stypdao.list();}



}

