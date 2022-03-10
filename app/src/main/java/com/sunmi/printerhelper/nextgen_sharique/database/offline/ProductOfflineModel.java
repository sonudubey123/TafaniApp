package com.sunmi.printerhelper.nextgen_sharique.database.offline;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_product")
public class ProductOfflineModel {
    @PrimaryKey(autoGenerate = true)
    //variable for our id.
     private int id;
     public String responsects;
     public String agentcode;
     public String agentname;
     //public String vendorcode;
     public String transid;
     public String resultcode;
     public String resultdescription;
     public String clienttype;
     public String recordcount;
     public String qty;
     public String parentname;
     public String parentcode;
     public String terminalid;
     public String operatorcount;
    public String servicecode;
    public String servicedesc;
    public String vendortypecode;
    public String vendortypedesc;
    public String vendorcode;
    public String vendordesc;
    public String productcode;
    public String productdesc;
    public String denomination;

    public ProductOfflineModel(String servicecode, String servicedesc, String vendortypecode, String vendortypedesc, String vendorcode, String vendordesc, String productcode, String productdesc, String denomination) {
        this.servicecode = servicecode;
        this.servicedesc = servicedesc;
        this.vendortypecode = vendortypecode;
        this.vendortypedesc = vendortypedesc;
        this.vendorcode = vendorcode;
        this.vendordesc = vendordesc;
        this.productcode = productcode;
        this.productdesc = productdesc;
        this.denomination = denomination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponsects() {
        return responsects;
    }

    public void setResponsects(String responsects) {
        this.responsects = responsects;
    }

    public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultdescription() {
        return resultdescription;
    }

    public void setResultdescription(String resultdescription) {
        this.resultdescription = resultdescription;
    }

    public String getClienttype() {
        return clienttype;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getParentcode() {
        return parentcode;
    }

    public void setParentcode(String parentcode) {
        this.parentcode = parentcode;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getOperatorcount() {
        return operatorcount;
    }

    public void setOperatorcount(String operatorcount) {
        this.operatorcount = operatorcount;
    }

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getServicedesc() {
        return servicedesc;
    }

    public void setServicedesc(String servicedesc) {
        this.servicedesc = servicedesc;
    }

    public String getVendortypecode() {
        return vendortypecode;
    }

    public void setVendortypecode(String vendortypecode) {
        this.vendortypecode = vendortypecode;
    }

    public String getVendortypedesc() {
        return vendortypedesc;
    }

    public void setVendortypedesc(String vendortypedesc) {
        this.vendortypedesc = vendortypedesc;
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

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }
}
