package lk.tcs.controller;


import lk.tcs.dao.OrderStatusDao;
import lk.tcs.entity.Orderstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderStatusController {

    @Autowired
    private OrderStatusDao orderstatusdao;

    @RequestMapping(value = "/orderstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Orderstatus> orderstatus() {
        return orderstatusdao.list();
    }




}
