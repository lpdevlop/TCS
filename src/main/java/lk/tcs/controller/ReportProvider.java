package lk.tcs.controller;



import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ReportProvider {

    public static List<ReportEntitySystemAccessAnalysis> getSystemAccessAnalysisReport(LocalDateTime startdate, LocalDateTime enddate) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT d.name as name, count(*) as attempt FROM servicecenter.sessionlog as s, servicecenter.user as u, servicecenter.employee as e, servicecenter.designation as d where s.user_id=u.id and u.employee_id=e.id and e.designation_id=d.id and s.logintime between '"+startdate+"' and '"+enddate+"' group by d.id ;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySystemAccessAnalysis> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySystemAccessAnalysis report = new ReportEntitySystemAccessAnalysis(rs.getString("name"), rs.getInt("attempt"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<ReportEntitySupplier> getsupplierdetails() {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT supplier.name,sum(porderitem.total) AS total FROM servicecenter.supplier inner join porder on supplier.id=porder.Supplier_id inner join porderitem on porderitem.porder_id=porder.id GROUP BY supplier.name;";

            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySupplier> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySupplier report = new ReportEntitySupplier(rs.getString("name"), rs.getString("total"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }


    public static List<ReportEntitySalesItem> getSalesitem(LocalDateTime startdate, LocalDateTime enddate) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT item.code,item.name ,sum(sellitem.qty) AS quantity FROM servicecenter.item,servicecenter.sell,servicecenter.sellitem where item.id=sellitem.item_id and sell.id=sellitem.id  and sell.date  between '"+startdate+"' and '"+enddate+"'  group by item.name;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySalesItem> list = new ArrayList<>();

            while (rs.next()) {
               ReportEntitySalesItem report = new ReportEntitySalesItem(rs.getString("code"),rs.getString("name"),rs.getInt("quantity"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }































/*








    public static List<ReportEntitySupplier> getSupplierReport() {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT name, regno, address, contactname, contactnumber, totalpayable FROM servicecenter.supplier;";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntitySupplier> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntitySupplier report = new ReportEntitySupplier(rs.getString("name"), rs.getString("regno"), rs.getString("address"),rs.getString("contactname"), rs.getString("contactnumber"),rs.getBigDecimal("totalpayable"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<ReportEntityInvoice> getInvoiceReport(Integer customerid) {


        try {
            AuthProvider.setConnection();
            Statement st = AuthProvider.connection.createStatement();
            String query = "SELECT c.fname, inv.no, ip.name, coi.qty, inv.grandtotal, inv.date FROM servicecenter.invoice inv, servicecenter.customerorder co,servicecenter.customerorderitempackage coi, servicecenter.itempackage ip, servicecenter.customer c where inv.customerorder_id=co.id and coi.customerorder_id=co.id and coi.itempackage_id=ip.id and co.customer_id=c.id and c.id = '"+customerid+"';";
            ResultSet rs = st.executeQuery(query);

            List<ReportEntityInvoice> list = new ArrayList<>();

            while (rs.next()) {
                ReportEntityInvoice report = new ReportEntityInvoice(rs.getString("fname"), rs.getString("no"), rs.getString("name"), rs.getInt("qty"),rs.getBigDecimal("grandtotal"), rs.getDate("date"));
                list.add(report);
            }
            return list;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }*/
}



