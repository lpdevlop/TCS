package lk.tcs.controller;


import lk.tcs.dao.ServicesDao;
import lk.tcs.dao.ServicestypeDao;
import lk.tcs.entity.Servicestype;
import lk.tcs.entity.Servicetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicestypeController {

@Autowired
private ServicestypeDao servicestypeDao;

    @RequestMapping(value="servicetype/list", method= RequestMethod.GET, produces = "application/json")
    public List<Servicestype> servicestatus(){return servicestypeDao.list();}







}
