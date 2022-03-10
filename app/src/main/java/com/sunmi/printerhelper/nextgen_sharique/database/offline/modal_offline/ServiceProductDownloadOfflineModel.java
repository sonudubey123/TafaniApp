package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableName_service_product")
public class ServiceProductDownloadOfflineModel {

    @PrimaryKey(autoGenerate = true)
     private int id;

    String  productcode="";
    String  vendorcode="";
    String  denomination="";

    public ServiceProductDownloadOfflineModel(String productcode, String vendorcode, String denomination) {
        this.productcode = productcode;
        this.vendorcode = vendorcode;
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

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
