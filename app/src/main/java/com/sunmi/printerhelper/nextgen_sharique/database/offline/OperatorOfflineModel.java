package com.sunmi.printerhelper.nextgen_sharique.database.offline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "service_operator")
public class OperatorOfflineModel {
    @PrimaryKey(autoGenerate = true)
    //variable for our id.
    private int id;
    public String responsects;
    public String agentcode;
    public String agentname;
    public String vendorcode;
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
    public String name;
    public String code;

    public OperatorOfflineModel(String name, String code) {
        this.name = name;
        this.code = code;
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

    public String getVendorcode() {
        return vendorcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
