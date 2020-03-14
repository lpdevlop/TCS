package lk.tcs.controller;

import lk.tcs.dao.SupplierStatusDao;
import lk.tcs.entity.Supplierstatus;
import lk.tcs.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupplierStatusController {

@Autowired
SupplierStatusDao supsdao;


    @RequestMapping(value="supplierstatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Supplierstatus> supplierstatus(){return supsdao.list();}





}
