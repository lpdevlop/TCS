package lk.tcs.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TitleController {

    @Autowired
    TitleDao dao;

    @RequestMapping(value="title/list", method= RequestMethod.GET, produces = "application/json")
    public List<Title> title(){return dao.list();}




}
