package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableName_bulkdownload")
public class BulkDownloadOfflineModel {

    @PrimaryKey(autoGenerate = true)
     private int id;
     public String vendorcode;
     public String productcode;
     public String denomination;
     public String recordcount;

    public String pinNo_encript;
    public String serialNumber;
    public String key;
    public String status_check;

    public String sell_dateTime;  // date time create constructer

    public BulkDownloadOfflineModel(String vendorcode, String productcode, String denomination, String recordcount, String pinNo_encript, String serialNumber, String key, String status_check, String sell_dateTime) {
        this.vendorcode = vendorcode;
        this.productcode = productcode;
        this.denomination = denomination;
        this.recordcount = recordcount;
        this.pinNo_encript = pinNo_encript;
        this.serialNumber = serialNumber;
        this.key = key;
        this.status_check = status_check;
        this.sell_dateTime = sell_dateTime;
    }

    public String getSell_dateTime() {
        return sell_dateTime;
    }

    public void setSell_dateTime(String sell_dateTime) {
        this.sell_dateTime = sell_dateTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getStatus_check() {
        return status_check;
    }

    public void setStatus_check(String status_check) {
        this.status_check = status_check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorcode() {
        return vendorcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getPinNo_encript() {
        return pinNo_encript;
    }

    public void setPinNo_encript(String pinNo_encript) {
        this.pinNo_encript = pinNo_encript;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "BulkDownloadOfflineModel{" +
                "id=" + id +
                ", vendorcode='" + vendorcode + '\'' +
                ", productcode='" + productcode + '\'' +
                ", denomination='" + denomination + '\'' +
                ", recordcount='" + recordcount + '\'' +
                ", pinNo_encript='" + pinNo_encript + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", key='" + key + '\'' +
                ", status_check='" + status_check + '\'' +
                '}';
    }
}
