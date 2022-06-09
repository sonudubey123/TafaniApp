package com.sunmi.tafani_printerhelper.nextgen_sharique.report;

public class ModalPosStockReport {

    String productCode="";
    int quent;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuent() {
        return quent;
    }

    public void setQuent(int quent) {
        this.quent = quent;
    }

    public ModalPosStockReport(String productCode, int quent) {
        this.productCode = productCode;
        this.quent = quent;
    }

    @Override
    public String toString() {
        return "ModalTemoDemo{" +
                "productCode='" + productCode + '\'' +
                ", quent=" + quent +
                '}';
    }
}
