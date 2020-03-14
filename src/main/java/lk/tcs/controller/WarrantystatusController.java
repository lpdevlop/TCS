package lk.tcs.controller;

import lk.tcs.dao.SupplierStatusDao;
import lk.tcs.dao.WarrantyStatusDao;
import lk.tcs.entity.Supplierstatus;
import lk.tcs.entity.Title;
import lk.tcs.entity.Warrantyservicestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarrantystatusController {



    @Autowired
    WarrantyStatusDao warDao;


    @RequestMapping(value="warrantystatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Warrantyservicestatus> warrantystatus(){return warDao.list();}






}