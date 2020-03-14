package lk.tcs.controller;

import lk.tcs.dao.VehicleBrandDao;
import lk.tcs.entity.Vehiclebrand;
import lk.tcs.entity.Vehiclemodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehiclebrandController {

    @Autowired
    private VehicleBrandDao vehbrandao;

      @RequestMapping(value = "vehiclebrand/list",method = RequestMethod.GET,produces = "application/json")
      List<Vehiclebrand>vehiclebrands(){return vehbrandao.list();}


    @RequestMapping(value = "/list/brandassign", params = {"vehiclemodelid"}, method = RequestMethod.GET, produces = "application/json")
    public List<Vehiclebrand> listmodelassigned(Integer vehiclemodelid) {
        return vehbrandao.listmodelassigned(vehiclemodelid);

    }







}
