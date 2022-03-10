package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableName_posstock_o")
public class PosstockODownloadOfflineModel {

    public PosstockODownloadOfflineModel(String productcode, String denomination, String qty) {
        this.productcode = productcode;
        this.denomination = denomination;
        this.qty = qty;
    }

    @PrimaryKey(autoGenerate = true)
     private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String action="";
    public String recordcount="";
    public String productcode="";
    public String denomination="";
    public String qty="";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }




}
