package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableName_service_operator")
public class ServiceOperatorDownloadOfflineModel {

    @PrimaryKey(autoGenerate = true)
     private int id;
    public String operatorName;
    public String operatorCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public ServiceOperatorDownloadOfflineModel(String operatorName, String operatorCode) {

        this.operatorName = operatorName;
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
}
