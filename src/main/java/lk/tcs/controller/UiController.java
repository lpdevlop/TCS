package lk.tcs.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui")
public class UiController {



    @RequestMapping("/config")
    public ModelAndView config(){
        ModelAndView model = new ModelAndView();
        model.setViewName("config.html");
        return model;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("login.html");
        return model;
    }


    @RequestMapping("/mainwindow")
    public ModelAndView mainwindow(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("mainwindow.html",username,password);
    }

    @RequestMapping("/index")
    public ModelAndView index(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("index.html",username,password);
    }

    @RequestMapping("/myui")
    public ModelAndView myui(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("myui.html",username,password);
    }

    @RequestMapping("/home")
    public ModelAndView home(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("home.html",username,password);
    }


    @RequestMapping("/employee")
    public ModelAndView employee(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("employee.html",username,password);
    }
    @RequestMapping("/sms")
    public ModelAndView sms(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("sms.html",username,password);
    }


    @RequestMapping("/user")
    public ModelAndView user(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("user.html",username,password);
    }

    @RequestMapping("/previlage")
    public ModelAndView previlage(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("previlage.html",username,password);

    }


    @RequestMapping("/changepassword")
    public ModelAndView changepassword(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("changepassword.html",username,password);
    }


    @RequestMapping("/designation")
    public ModelAndView designation(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("designation.html",username,password);
    }


    @RequestMapping("/customer")
    public ModelAndView customer(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("customer.html",username,password);
    }


    @RequestMapping("/item")
    public ModelAndView item(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("item.html",username,password);
    }

    @RequestMapping("/supplier")
    public ModelAndView supplier(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("supplier.html",username,password);
    }

    @RequestMapping("/sell")
    public ModelAndView sell(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("sell.html",username,password);
    }

    @RequestMapping("/order")
    public ModelAndView order(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("order.html",username,password);
    }


    @RequestMapping("/goodrecive")
    public ModelAndView goodrecive(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("goodrecive.html",username,password);
    }

    @RequestMapping("/disposal")
    public ModelAndView disposal(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("disposal.html",username,password);
    }

    @RequestMapping("/returnitem")
    public ModelAndView returnitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("returnitem.html",username,password);
    }

    @RequestMapping("/serviceline")
    public ModelAndView serviceline(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("serviceline.html",username,password);
    }

    @RequestMapping("/servicerequest")
    public ModelAndView servicerequest(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("servicerequest.html",username,password);
    }
    @RequestMapping("/service")
    public ModelAndView service(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("service.html",username,password);
    }

    @RequestMapping("/company")
    public ModelAndView company(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("company.html",username,password);
    }
    @RequestMapping("/warrantyservice")
    public ModelAndView warrantyservice(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("warrantyservice.html",username,password);
    }

    @RequestMapping("/employeestatus")
    public ModelAndView employeestatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("employeestatus.html",username,password);
    }

    @RequestMapping("/userstatus")
    public ModelAndView userstatus(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("userstatus.html",username,password);
    }
    @RequestMapping("/category")
    public ModelAndView category(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("category.html",username,password);
    }
    @RequestMapping("/subcategory")
    public ModelAndView subcategory(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("subcategory.html",username,password);
    }

    @RequestMapping("/webui")
    public ModelAndView webui(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("WebUi.html",username,password);
    }

    @RequestMapping("/unit")
    public ModelAndView unit(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("unit.html",username,password);
    }
    @RequestMapping("/brand")
    public ModelAndView brand(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("brand.html",username,password);
    }
    @RequestMapping("/email")
    public ModelAndView email(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("email.html",username,password);
    }

    @RequestMapping("/Dashboard")
    public ModelAndView Dasboard(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("Dashboard.html",username,password);
    }

    @RequestMapping("/HomePage")
    public ModelAndView HomePage(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("HomePage.html",username,password);
    }


    public ModelAndView getModelView(String page,String username, String password){

        ModelAndView model = new ModelAndView();

        if(AuthProvider.isUser(username,password)) {

            model.setViewName(page);
        }
        else {
            model.setViewName("noprivilage.html");

        }
        return model;

    }



}