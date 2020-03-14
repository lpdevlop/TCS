package lk.tcs.controller;

public class ReportEntitySupplier {
    String name;
    String total;

    public ReportEntitySupplier(String name, String total) {
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String code) {
        this.total = code;
    }
}
