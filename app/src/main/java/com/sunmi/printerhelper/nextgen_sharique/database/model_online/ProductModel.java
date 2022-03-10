package com.sunmi.printerhelper.nextgen_sharique.database.model_online;

public class ProductModel {


    String  productCode="";

    public ProductModel(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
