package com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin;

public class ProductModelOrderPin {


    String  productcode="";
    String  vendorcode="";
    String  denomination="";

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getVendorcode() {
        return vendorcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    public void setOperatorCode(String productcode) {
        this.productcode = productcode;
    }

    public ProductModelOrderPin(String operatorCode,String vendorcode,String denomination) {
        this.productcode = operatorCode;
        this.vendorcode = vendorcode;
        this.denomination = denomination;
    }


}
