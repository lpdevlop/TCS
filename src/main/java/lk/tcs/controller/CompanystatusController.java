package lk.tcs.controller;

import lk.tcs.dao.CompanyStatusDao;
import lk.tcs.entity.Companystatus;
import lk.tcs.entity.Customertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanystatusController {

    @Autowired
    private CompanyStatusDao companyStatusDao;


    @RequestMapping(value = "/companytype/list",method = RequestMethod.GET, produces = "application/json")
    public List<Companystatus> companystatuses(){return companyStatusDao.list();}

}
