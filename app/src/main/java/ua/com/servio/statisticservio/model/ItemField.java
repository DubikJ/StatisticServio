package ua.com.servio.statisticservio.model;


public class ItemField {

    private String title;
    private String paymentTypeName;
    private String paymentFiscalTypeName;
    private Double param1;
    private Double param2;
    private Double param3;
    private Double param4;
    private Double param5;

    private Double param1Total;
    private Double param2Total;
    private Double param3Total;
    private Double param4Total;
    private Double param5Total;

    private Boolean showTitle;
    private Boolean showTotal;

    public ItemField(String title, String paymentTypeName, String paymentFiscalTypeName,
                     Double param1, Double param2, Double param3, Double param1Total,
                     Double param2Total, Double param3Total, Boolean showTitle, Boolean showTotal) {
        this.title = title;
        this.paymentTypeName = paymentTypeName;
        this.paymentFiscalTypeName = paymentFiscalTypeName;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param1Total = param1Total;
        this.param2Total = param2Total;
        this.param3Total = param3Total;
        this.showTitle = showTitle;
        this.showTotal = showTotal;
    }

    public ItemField(String title, String paymentTypeName, String paymentFiscalTypeName,
                     Double param1, Double param2, Double param3, Double param4, Double param1Total,
                     Double param2Total, Double param3Total, Double param4Total,
                     Boolean showTitle, Boolean showTotal) {
        this.title = title;
        this.paymentTypeName = paymentTypeName;
        this.paymentFiscalTypeName = paymentFiscalTypeName;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param1Total = param1Total;
        this.param2Total = param2Total;
        this.param3Total = param3Total;
        this.param4Total = param4Total;
        this.showTitle = showTitle;
        this.showTotal = showTotal;
    }


    public ItemField(String title, String paymentTypeName, String paymentFiscalTypeName,
                     Double param1, Double param2, Double param3, Double param4, Double param5,
                     Double param1Total, Double param2Total, Double param3Total,
                     Double param4Total, Double param5Total, Boolean showTitle, Boolean showTotal) {
        this.title = title;
        this.paymentTypeName = paymentTypeName;
        this.paymentFiscalTypeName = paymentFiscalTypeName;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param1Total = param1Total;
        this.param2Total = param2Total;
        this.param3Total = param3Total;
        this.param4Total = param4Total;
        this.param5Total = param5Total;
        this.showTitle = showTitle;
        this.showTotal = showTotal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName;
    }

    public String getPaymentFiscalTypeName() {
        return paymentFiscalTypeName;
    }

    public void setPaymentFiscalTypeName(String paymentFiscalTypeName) {
        this.paymentFiscalTypeName = paymentFiscalTypeName;
    }

    public Double getParam1() {
        return param1;
    }

    public void setParam1(Double param1) {
        this.param1 = param1;
    }

    public Double getParam2() {
        return param2;
    }

    public void setParam2(Double param2) {
        this.param2 = param2;
    }

    public Double getParam3() {
        return param3;
    }

    public void setParam3(Double param3) {
        this.param3 = param3;
    }

    public Double getParam4() {
        return param4;
    }

    public void setParam4(Double param4) {
        this.param4 = param4;
    }

    public Double getParam5() {
        return param5;
    }

    public void setParam5(Double param5) {
        this.param5 = param5;
    }

    public Double getParam1Total() {
        return param1Total;
    }

    public void setParam1Total(Double param1Total) {
        this.param1Total = param1Total;
    }

    public Double getParam2Total() {
        return param2Total;
    }

    public void setParam2Total(Double param2Total) {
        this.param2Total = param2Total;
    }

    public Double getParam3Total() {
        return param3Total;
    }

    public void setParam3Total(Double param3Total) {
        this.param3Total = param3Total;
    }

    public Double getParam4Total() {
        return param4Total;
    }

    public void setParam4Total(Double param4Total) {
        this.param4Total = param4Total;
    }

    public Double getParam5Total() {
        return param5Total;
    }

    public void setParam5Total(Double param5Total) {
        this.param5Total = param5Total;
    }

    public Boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Boolean showTitle) {
        this.showTitle = showTitle;
    }

    public Boolean getShowTotal() {
        return showTotal;
    }

    public void setShowTotal(Boolean showTotal) {
        this.showTotal = showTotal;
    }
}

