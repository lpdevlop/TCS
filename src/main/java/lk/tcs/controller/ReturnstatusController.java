package lk.tcs.controller;


import lk.tcs.dao.ReturnstatusDao;
import lk.tcs.entity.Returnstatus;
import lk.tcs.entity.Sellstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReturnstatusController {

@Autowired
private ReturnstatusDao rsdao;


    @RequestMapping(value="returnstatuses/list", method= RequestMethod.GET, produces = "application/json")
    public List<Returnstatus> returnstatuses(){return rsdao.list();}
}
