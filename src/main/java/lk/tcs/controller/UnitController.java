package lk.tcs.controller;

import lk.tcs.dao.TitleDao;
import lk.tcs.dao.UnitDao;
import lk.tcs.entity.Title;
import lk.tcs.entity.Unit;
import lk.tcs.util.ModuleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UnitController {

    @Autowired
    UnitDao dao;

    @RequestMapping(value="unit/list", method= RequestMethod.GET, produces = "application/json")
    public List<Unit> unit(){return dao.list();}



    @RequestMapping(value = "/unit", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Unit> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.UNIT,AuthProvider.SELECT)) {

            List<Unit> unit = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Unit> unitStream = unit.stream();

            unitStream = unitStream.filter(c -> c.getName().startsWith(name));

            List<Unit> unit2 = unitStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < unit2.size() ? start + size : unit2.size();
            Page<Unit> despage = new PageImpl<>(unit2.subList(start, end), PageRequest.of(page, size), unit2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/unit", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Unit unit) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.UNIT,AuthProvider.INSERT)) {
            Unit desname = dao.findByName(unit.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(unit);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/unit", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Unit unit) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.UNIT,AuthProvider.INSERT)) {
            Unit desname = dao.findByName(unit.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(unit);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/unit", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Unit unit ) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.UNIT,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(unit.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}


