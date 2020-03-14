package lk.tcs.controller;


import lk.tcs.dao.VehicleCategoryDao;
import lk.tcs.entity.Vehiclecategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehiclecategoryController {


@Autowired
    private VehicleCategoryDao vcdao;


    @RequestMapping(value="vehcilecategory/list", method= RequestMethod.GET, produces = "application/json")
    public List<Vehiclecategory> vehiclecategories(){return vcdao.list();}




}
