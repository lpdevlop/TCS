package lk.tcs.controller;

public class ReportEntitySalesItem {
    String code;
    String name;
    int Quantity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }



    public ReportEntitySalesItem(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        Quantity = quantity;
    }
}
