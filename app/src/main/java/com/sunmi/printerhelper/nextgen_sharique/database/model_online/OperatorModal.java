package com.sunmi.printerhelper.nextgen_sharique.database.model_online;

public class OperatorModal {

    public String vendorcode;
    public String vendordesc;

    public OperatorModal(String vendorcode, String vendordesc) {
        this.vendorcode = vendorcode;
        this.vendordesc = vendordesc;
    }

    public String getVendorcode() {
        return vendorcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    public String getVendordesc() {
        return vendordesc;
    }

    public void setVendordesc(String vendordesc) {
        this.vendordesc = vendordesc;
    }
}
