package lk.tcs.controller;

import lk.tcs.dao.SubcategoryDao;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Subcategory;
import lk.tcs.entity.Supplier;
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
public class SubcategoryController {

    @Autowired
    SubcategoryDao dao;

    @RequestMapping(value = "/category/listbysubcategory", params = "categoryid" ,method = RequestMethod.GET ,produces = "application/json")
    public List<Subcategory> itemslistbysupplier(@RequestParam("categoryid") Integer categoryid){
        return dao.listbysubcategory(categoryid);
    }





    @RequestMapping(value="subcategory/list", method= RequestMethod.GET, produces = "application/json")
    public List<Subcategory> subcategory(){return dao.list();}



    @RequestMapping(value = "/subcategory", params = {"page", "size","name","categoryId"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Subcategory> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("categoryId") Integer categoryId ) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.SUBCATEGORY,AuthProvider.SELECT)) {

            List<Subcategory> subcategory = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Subcategory> subcategoryStream = subcategory.stream();

            subcategoryStream = subcategoryStream.filter(s -> s.getName().startsWith(name));

            List<Subcategory> subcategory2 = subcategoryStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < subcategory2.size() ? start + size : subcategory2.size();
            Page<Subcategory> despage = new PageImpl<>(subcategory2.subList(start, end), PageRequest.of(page, size), subcategory2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/subcategory", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Subcategory subcategory) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.SUBCATEGORY,AuthProvider.INSERT)) {
            Subcategory desname = dao.findByName(subcategory.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(subcategory);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/subcategory", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Subcategory subcategory) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.SUBCATEGORY,AuthProvider.INSERT)) {
            Subcategory desname = dao.findByName(subcategory.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(subcategory);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/subcategory", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Subcategory subcategory ) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.SUBCATEGORY,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(subcategory.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}




