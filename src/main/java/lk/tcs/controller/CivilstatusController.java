package lk.tcs.controller;

import lk.tcs.dao.CivilstatusDao;
import lk.tcs.entity.Civilstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CivilstatusController {

    @Autowired
    private CivilstatusDao dao;

    @RequestMapping(value = "/civilstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Civilstatus> civilstatuses() {
        return dao.list();
    }


}