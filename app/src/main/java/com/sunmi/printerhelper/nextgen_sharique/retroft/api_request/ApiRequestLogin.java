package com.sunmi.printerhelper.nextgen_sharique.retroft.api_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiRequestLogin {

    @SerializedName("requesttype")
    @Expose
    private String requesttype;


    @SerializedName("agentcode")
    @Expose
    private String agentcode;


    @SerializedName("vendorcode")
    @Expose
    private String vendorcode;


    @SerializedName("clienttype")
    @Expose
    private String clienttype;

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    public void setClienttype(String clienttype) {
        this.clienttype = clienttype;
    }
}
