package lk.tcs.controller;

import lk.tcs.dao.LineDao;
import lk.tcs.dao.LinevehiclecategoryDao;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Line;
import lk.tcs.entity.Linevehiclecategory;
import lk.tcs.entity.Vehiclemodel;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class LineController {

@Autowired
    private LineDao ldao;
@Autowired
private LinevehiclecategoryDao lvcdao;

//FIND LIN ID USING VEHICLE CATEGORY
    @RequestMapping(value = "/lines/list/lineassigned", params = {"vehiclecaid"}, method = RequestMethod.GET, produces = "application/json")
    public List<Line> listmodelassigned(Integer vehiclecaid) {
        return ldao.Linevehivlecategory(vehiclecaid);

    }



    @RequestMapping(value="lines/list", method= RequestMethod.GET, produces = "application/json")
    public List<Line> lines(){return ldao.list();}



    @RequestMapping(value = "/lines", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Line> findAll(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICELINE, AuthProvider.SELECT)) {
            return ldao.findAll(PageRequest.of(page, size));
        }
        return null;
    }


    @RequestMapping(value = "/line/nextlineno", method = RequestMethod.GET, produces = "application/json")
    public String nextLineno(@CookieValue(value = "username") String username, @CookieValue(value = "password") String password) {
        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICELINE, AuthProvider.SELECT)) {
            String lineno = ldao.nextLineno();
            return "{\"no\":\"" + lineno + "\"}";
        }
        return null;
    }

    @RequestMapping(value = "/lines", method = RequestMethod.POST)
    public String add(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password, @Validated @RequestBody Line line) {

        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICELINE, AuthProvider.INSERT)) {
            Line lin = ldao.findbyCode(line.getCode());
            if (lin != null) {
                return "Error validation" + line.getCode();
            }
            try {

                for (Linevehiclecategory liveca : line.getLinevehiclecategoryList()) {
                    liveca.setLineId(line);
                }
                ldao.save(line);
                return "0";
            } catch (Exception s) {
                s.printStackTrace();
                return "Error-Saving : " + s.getMessage();


            }
        }
        return "Error-Saving : You have no Permission";

    }



    @RequestMapping(value = "/lines", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Line line){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICELINE,AuthProvider.UPDATE)) {
            try {
                for (Linevehiclecategory liveca : line.getLinevehiclecategoryList()) {
                    liveca.setLineId(line);
                }

                ldao.save(line);
                return "0";
            } catch (Exception e) {
                return "Error-Update : " + e.getMessage();
            }
        } return "Error-Updating : You have no Permission";
    }



    @RequestMapping(value = "/lines", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password,@Validated @RequestBody Line line){

        if(AuthProvider.isAuthorized(username,password,ModuleList.SERVICELINE,AuthProvider.DELETE)) {
            try {

                Line lind =  ldao.getOne(line.getId());
                lind.getLinevehiclecategoryList().remove(line);

                ldao.delete(line);

                return "0";
            } catch (Exception e) {
                return "Error-Delete : " + e.getMessage();
            }
        } return "Error-Deleting : You have no Permission";
    }


    @RequestMapping(value = "/lines", params = {"page", "size","code","employeeId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Line> findAll(@CookieValue(value="username") String username, @CookieValue(value="password") String password, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("code") String code, @RequestParam("employeeId") Integer employeeId) {


        if (AuthProvider.isAuthorized(username, password, ModuleList.SERVICELINE,AuthProvider.SELECT)) {
            List<Line> lines = ldao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Line> lineStream = lines.stream();

            if(code !=null)
               lineStream = lineStream.filter(l -> l.getCode().contains(code));
            if(employeeId !=null)
                lineStream = lineStream.filter(l -> l.getEmployeeId().equals(ldao.getOne(employeeId)));
        /*    if (date != null)
                lineStream = lineStream.filter(l -> l.getDate().*/



            List<Line> lineList = lineStream.collect(Collectors.toList());
            int start = page * size;
            int end = start + size < lineList.size() ? start + size : lineList.size();
            return new PageImpl<>(lineList.subList(start, end),
                    PageRequest.of(page, size),
                    lineList.size());
        }
        return null;
    }

}

















