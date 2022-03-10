package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableName_posstock_s")
public class PostockSDownloadOfflineModel {

    @PrimaryKey(autoGenerate = true)
     private int id;


    public  String seriolNumber="";

    public PostockSDownloadOfflineModel(String seriolNumber, String kNumber) {
        this.seriolNumber = seriolNumber;
        this.kNumber = kNumber;
    }

    public  String kNumber="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeriolNumber() {
        return seriolNumber;
    }

    public void setSeriolNumber(String seriolNumber) {
        this.seriolNumber = seriolNumber;
    }

    public String getkNumber() {
        return kNumber;
    }

    public void setkNumber(String kNumber) {
        this.kNumber = kNumber;
    }
}
