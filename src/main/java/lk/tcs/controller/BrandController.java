package lk.tcs.controller;

import lk.tcs.dao.BrandDao;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Brand;
import lk.tcs.entity.Title;
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
public class BrandController {

    @Autowired
    BrandDao dao;





    @RequestMapping(value="brand/list", method= RequestMethod.GET, produces = "application/json")
    public List<Brand> brand(){return dao.list();}

    
    @RequestMapping(value = "/brand", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Brand> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.BRAND,AuthProvider.SELECT)) {

            List<Brand> brand = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Brand> brandStream = brand.stream();

            brandStream = brandStream.filter(s -> s.getName().startsWith(name));

            List<Brand> brand2 = brandStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < brand2.size() ? start + size : brand2.size();
            Page<Brand> despage = new PageImpl<>(brand2.subList(start, end), PageRequest.of(page, size), brand2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Brand brand) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.BRAND,AuthProvider.INSERT)) {
            Brand desname = dao.findByName(brand.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(brand);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/brand", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Brand brand) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.BRAND,AuthProvider.INSERT)) {
            Brand desname = dao.findByName(brand.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(brand);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/brand", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Brand brand ) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.BRAND,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(brand.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }

}
