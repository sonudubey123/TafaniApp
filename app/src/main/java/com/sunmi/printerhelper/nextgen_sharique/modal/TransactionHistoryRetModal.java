package com.sunmi.printerhelper.nextgen_sharique.modal;

public class TransactionHistoryRetModal {


  String transid="";
  String responsects="";
  String transtype="";
  String walletbalance="";
  String amount="";
    String operator="";
    String product="";

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getResponsects() {
        return responsects;
    }

    public void setResponsects(String responsects) {
        this.responsects = responsects;
    }

    public String getTranstype() {
        return transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    public String getWalletbalance() {
        return walletbalance;
    }

    public void setWalletbalance(String walletbalance) {
        this.walletbalance = walletbalance;
    }
}
