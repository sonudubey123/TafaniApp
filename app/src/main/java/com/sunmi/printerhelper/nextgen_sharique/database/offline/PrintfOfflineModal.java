package com.sunmi.printerhelper.nextgen_sharique.database.offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "printf_records")
public class PrintfOfflineModal
{
    @PrimaryKey(autoGenerate = true)
     private int id;
     public String productcode="";
     public String denomination="";
    public String reqqty="";
    public String resqty="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getReqqty() {
        return reqqty;
    }

    public void setReqqty(String reqqty) {
        this.reqqty = reqqty;
    }

    public String getResqty() {
        return resqty;
    }

    public void setResqty(String resqty) {
        this.resqty = resqty;
    }

    public PrintfOfflineModal(String productcode, String denomination, String reqqty, String resqty) {
        this.productcode = productcode;
        this.denomination = denomination;
        this.reqqty = reqqty;
        this.resqty = resqty;

    }

}
