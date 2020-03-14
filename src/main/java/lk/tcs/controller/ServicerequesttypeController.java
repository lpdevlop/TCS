package lk.tcs.controller;

import lk.tcs.dao.ServicerequesttypeDao;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Servicerequeststatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServicerequesttypeController {

    @Autowired
    private ServicerequesttypeDao srtpdao;

    @RequestMapping(value="servicerequeststatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Servicerequeststatus> Servicerequeststatus(){return srtpdao.list();}







}
