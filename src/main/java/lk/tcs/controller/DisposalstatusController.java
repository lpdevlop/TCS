package lk.tcs.controller;

import lk.tcs.dao.DisposalStatusDao;
import lk.tcs.entity.Disposalstatus;
import lk.tcs.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisposalstatusController {


   @Autowired
    private DisposalStatusDao disposalStatusDao;


    @RequestMapping(value="disposalstatus/list", method= RequestMethod.GET, produces = "application/json")
    public List<Disposalstatus> title(){return disposalStatusDao.list();}


}


