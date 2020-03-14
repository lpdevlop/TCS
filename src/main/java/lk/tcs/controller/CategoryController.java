package lk.tcs.controller;

import lk.tcs.dao.CategoryDao;
import lk.tcs.dao.TitleDao;
import lk.tcs.entity.Category;
import lk.tcs.entity.Title;
import lk.tcs.entity.Category;
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
public class CategoryController {

    @Autowired
    CategoryDao dao;




    @RequestMapping(value="category/list", method= RequestMethod.GET, produces = "application/json")
    public List<Category> category(){return dao.list();}



    @RequestMapping(value = "/category", params = {"page", "size","name"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Category> findAll(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {

        // System.out.println(name);


        if(AuthProvider.isAuthorized(username,password, ModuleList.CATEGORY,AuthProvider.SELECT)) {

            List<Category> category = dao.findAll(Sort.by(Sort.Direction.DESC, "id"));
            Stream<Category> categoryStream = category.stream();

            categoryStream = categoryStream.filter(c -> c.getName().startsWith(name));

            List<Category> category2 = categoryStream.collect(Collectors.toList());

            int start = page * size;
            int end = start + size < category2.size() ? start + size : category2.size();
            Page<Category> despage = new PageImpl<>(category2.subList(start, end), PageRequest.of(page, size), category2.size());

            return despage;
        }

        return null;

    }


    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String add(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Category category) {

        if(AuthProvider.isAuthorized(username,password,ModuleList.CATEGORY,AuthProvider.INSERT)) {
            Category desname = dao.findByName(category.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(category);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT)
    public String update(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @Validated @RequestBody Category category) {

        if(AuthProvider.isAuthorized(username,password, ModuleList.CATEGORY,AuthProvider.INSERT)) {
            Category desname = dao.findByName(category.getName());
            if (desname != null)
                return "Error-Validation : Designation Exists";
            else
                try {
                    dao.save(category);
                    return "0";
                } catch (Exception e) {
                    return "Error-Saving : " + e.getMessage();
                }
        }
        return "Error-Saving : You have no Permission";

    }


    @RequestMapping(value = "/category", method = RequestMethod.DELETE)
    public String delete(@CookieValue(value="username", required=false) String username, @CookieValue(value="password", required=false) String password, @RequestBody Category category ) {
        if(AuthProvider.isAuthorized(username,password, ModuleList.CATEGORY,AuthProvider.DELETE)) {
            try {
                dao.delete(dao.getOne(category.getId()));
                return "0";
            }
            catch(Exception e) {
                return "Error-Deleting : "+e.getMessage();
            }
        }
        return "Error-Deleting : You have no Permission";

    }
}



