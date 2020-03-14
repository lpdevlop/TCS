package lk.tcs.controller;

import lk.tcs.dao.LinevehiclecategoryDao;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Linevehiclecategory;
import lk.tcs.util.RegexPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LinevehiclecategoryController {

    @Autowired
    private LinevehiclecategoryDao lvda;

   /* @RequestMapping(value = "/linevehiclecategory/listbyline", params = "vehiclecategoryid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Linevehiclecategory> LinevehiclecategoryList(@RequestParam("vehiclecategoryid") Integer vehiclecategoryid){
        return lvda.Linevehivlecategory(vehiclecategoryid);
    }
*/
    @RequestMapping(value="linevehcilecategory/list", method=RequestMethod.GET,produces = "application/json")
    public List<Linevehiclecategory>Linevehivlecategory(){return lvda.list();}

}
