package lk.tcs.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/ui/report")
public class ReportUiController {

    @RequestMapping("/systemaccessanalysis")
    public ModelAndView login(){
        ModelAndView model = new ModelAndView();
        model.setViewName("systemaccessanalysis.html");
        return model;
    }

      @RequestMapping("/supplierdetails")
    public ModelAndView supplier(){
        ModelAndView model = new ModelAndView();
        model.setViewName("supplierdetails.html");
        return model;
    }

    @RequestMapping("/salesitem")
    public ModelAndView salesitem(){
        ModelAndView model = new ModelAndView();
        model.setViewName("salesitem.html");
        return model;
    }
    @RequestMapping("/suppliersaleitem")
    public ModelAndView suppliersaleitem(){
        ModelAndView model = new ModelAndView();
        model.setViewName("suppliersaleitem.html");
        return model;
    }







/*
    @RequestMapping("/invoices")
    public ModelAndView invoices(){
        ModelAndView model = new ModelAndView();
        model.setViewName("reportinvoices.html");
        return model;
    }

    @RequestMapping("/meterials")
    public ModelAndView meterials(){
        ModelAndView model = new ModelAndView();
        model.setViewName("reportmeterials.html");
        return model;
    }

    @RequestMapping("/purchasorders")
    public ModelAndView purchasorders(){
        ModelAndView model = new ModelAndView();
        model.setViewName("reportpurchasorders.html");
        return model;
    }

    @RequestMapping("/grns")
    public ModelAndView grns(){
        ModelAndView model = new ModelAndView();
        model.setViewName("reportgrns.html");
        return model;
    }
*/
}