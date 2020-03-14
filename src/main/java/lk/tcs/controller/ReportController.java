package lk.tcs.controller;




import lk.tcs.util.ModuleList;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class ReportController {



    @RequestMapping(value = "/reports/systemaccessanalysis", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {

        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
    LocalDateTime startdate = LocalDateTime.now().minusDays(35);
    LocalDateTime enddate = LocalDateTime.now().plusDays(1);
    return ReportProvider.getSystemAccessAnalysisReport(startdate, enddate);
}
else return  null;


    }

    @RequestMapping(value = "/reports/systemaccessanalysis", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySystemAccessAnalysis> systemaccessanalysis2(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.EMPLOYEE,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getSystemAccessAnalysisReport(stdate,endate);
        }
        else return  null;
    }

    @RequestMapping(value = "/reports/supplierdetails", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySupplier> suppliers(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.SUPPLIER,AuthProvider.SELECT)) {
            return ReportProvider.getsupplierdetails();
        }
        else return  null;


    }


    @RequestMapping(value = "/reports/salesitem", method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySalesItem> Salesitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password) {

        if (AuthProvider.isAuthorized(username,password, ModuleList.ITEM,AuthProvider.SELECT)) {
            LocalDateTime startdate = LocalDateTime.now().minusDays(35);
            LocalDateTime enddate = LocalDateTime.now().plusDays(1);
            return ReportProvider.getSalesitem(startdate, enddate);
        }
        else return  null;


    }

    @RequestMapping(value = "/reports/salesitem", params = {"startdate", "enddate"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntitySalesItem> Salesitem(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.ITEM,AuthProvider.SELECT)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime stdate=LocalDateTime.parse(startdate+" 00:00",formatter );
            LocalDateTime endate=LocalDateTime.parse(enddate +" 00:00",formatter);
            return ReportProvider.getSalesitem(stdate,endate);
        }
        else return  null;
    }













/*
    @RequestMapping(value = "/reports/invoices", params = {"customerid"}, method = RequestMethod.GET, produces = "application/json")
    public List<ReportEntityInvoice> invoices(@CookieValue(value="username") String username, @CookieValue(value="password") String password,@RequestParam("customerid") Integer customerid) {
        if (AuthProvider.isAuthorized(username,password, ModuleList.INVOICE,AuthProvider.SELECT)) {

            return ReportProvider.getInvoiceReport(customerid);
        }
        else return  null;
    }*/

}
