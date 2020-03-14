package lk.tcs.controller;

import lk.tcs.dao.VehicleBrandDao;
import lk.tcs.dao.VehiclemodelDao;
import lk.tcs.entity.Unit;
import lk.tcs.entity.Vehiclemodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehiclemodelController {

    @Autowired
    private VehiclemodelDao vehimodeldao;



    @RequestMapping(value = "vehiclemodel/list",method = RequestMethod.GET,produces = "application/json")
    public List<Vehiclemodel>Vehiclemodel(){return  vehimodeldao.list();}


}
