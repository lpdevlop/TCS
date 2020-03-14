package lk.tcs.controller;

import lk.tcs.entity.*;
import lk.tcs.util.RegexPattern;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

@RestController
@RequestMapping("/regexes")
public class RegexController {

    @RequestMapping(value = "/employee", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employee() {
        return getRegex(new Employee());
    }

    @RequestMapping(value = "/user", produces = "application/json")
    public HashMap<String, HashMap<String, String>> user() {
        return getRegex(new User());
    }

    @RequestMapping(value = "/designation", produces = "application/json")
    public HashMap<String, HashMap<String, String>> designation() {
        return getRegex(new Designation());
    }

    @RequestMapping(value = "/customer", produces = "application/json")
    public HashMap<String, HashMap<String, String>> customer() {
        return getRegex(new Customer());
    }

    @RequestMapping(value = "/item", produces = "application/json")
    public HashMap<String, HashMap<String, String>> item() {
        return getRegex(new Item());
    }

    @RequestMapping(value = "/supplier", produces = "application/json")
    public HashMap<String, HashMap<String, String>> supplier() {
        return getRegex(new Supplier());
    }

    @RequestMapping(value = "/sell", produces = "application/json")
    public HashMap<String, HashMap<String, String>> sell() {
        return getRegex(new Sell());
    }

    @RequestMapping(value = "/employeestatuses", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employeestatuses() {
        return getRegex(new Employeestatus());
    }

    @RequestMapping(value = "/userstatuses", produces = "application/json")
    public HashMap<String, HashMap<String, String>> userstatuses() {
        return getRegex(new Userstatus());
    }

    @RequestMapping(value = "/category", produces = "application/json")
    public HashMap<String, HashMap<String, String>> category() {
        return getRegex(new Category());
    }

    @RequestMapping(value = "/brand", produces = "application/json")
    public HashMap<String, HashMap<String, String>> brand() {
        return getRegex(new Brand());
    }

    @RequestMapping(value = "/subcategory", produces = "application/json")
    public HashMap<String, HashMap<String, String>> subcategory() {
        return getRegex(new Subcategory());
    }

    @RequestMapping(value = "/company", produces = "application/json")
    public HashMap<String, HashMap<String, String>> company() {
        return getRegex(new Company());
    }

    @RequestMapping(value = "/warrantyservice", produces = "application/json")
    public HashMap<String, HashMap<String, String>> warrantyservice() {
        return getRegex(new Warrantyservice());
    }




    public static <T> HashMap<String, HashMap<String, String>> getRegex(T t) {
        try {
            Class<? extends Object> aClass = t.getClass();
            HashMap<String, HashMap<String, String>> regex = new HashMap<>();

            for (Field field : aClass.getDeclaredFields()) {

                Annotation[] annotations = field.getDeclaredAnnotations();

                for (Annotation annotation : annotations) {

                    if (annotation instanceof Pattern) {
                        Pattern myAnnotation = (Pattern) annotation;
                        HashMap<String, String> map = new HashMap<>();
                        map.put("regex", myAnnotation.regexp());
                        map.put("message", myAnnotation.message());
                        regex.put(field.getName(), map);
                    }

                    if (annotation instanceof RegexPattern) {
                        RegexPattern myAnnotation2 = (RegexPattern) annotation;
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("regex", myAnnotation2.regexp());
                        map2.put("message", myAnnotation2.message());
                        regex.put(field.getName(), map2);
                    }
                }
            }
            return regex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
