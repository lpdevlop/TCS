package lk.tcs.controller;

import lk.tcs.dao.UserstatusDao;
import lk.tcs.entity.Userstatus;
import lk.tcs.entity.Userstatus;
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
public class UserstatusController {

    @Autowired
    private UserstatusDao dao;

    @RequestMapping(value = "/userstatuses/list", method = RequestMethod.GET, produces = "application/json")
    public List<Userstatus> gender() {
        return dao.list();
    }


    @RequestMapping(value = "/userstatuses", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Userstatus> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.USERSTATUS,AuthProvider.SELECT)) {

            List<Userstatus> userstatuses = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Userstatus> userstatusStream = userstatuses.stream();

            userstatusStream = userstatusStream.filter(e -> e.getName().startsWith(name));

            List<Userstatus> userstatus2 = userstatusStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < userstatus2.size() ? start + size : userstatus2.size();
            Page<Userstatus> despage = new PageImpl<>(userstatus2.subList(start, end), PageRequest.of(page, size), userstatus2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/userstatuses", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Userstatus userstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.USERSTATUS,AuthProvider.INSERT)) {
            Userstatus desname = dao.findByName(userstatus.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(userstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/userstatuses", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Userstatus userstatus) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.USERSTATUS,AuthProvider.INSERT)) {
            Userstatus desname = dao.findByName(userstatus.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(userstatus);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/userstatuses", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Userstatus userstatus ) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.USERSTATUS,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(userstatus.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}
