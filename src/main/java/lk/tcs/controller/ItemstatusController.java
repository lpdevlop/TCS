package lk.tcs.controller;

import lk.tcs.dao.ItemstatusDao;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Itemstatus;
import lk.tcs.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemstatusController {

    @Autowired
    ItemstatusDao dao;

    @RequestMapping(value="itemstatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Itemstatus> itemstatus(){return dao.list();}




}
