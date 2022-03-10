package com.sunmi.printerhelper.nextgen_sharique.retroft.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseLogin {

    @SerializedName("resultcode")
    @Expose
    private String resultcode;

    @SerializedName("resultdescription")
    @Expose
    private String resultdescription;


    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("agenttype")
    @Expose
    private String agenttype;

    @SerializedName("phoneno")
    @Expose
    private String phoneno;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("parentname")
    @Expose
    private String parentname;



    @SerializedName("agentname")
    @Expose
    private String agentname;


    public String getResultcode() {
        return resultcode;
    }

    public String getResultdescription() {
        return resultdescription;
    }

    public String getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getSource() {
        return source;
    }

    public String getAgenttype() {
        return agenttype;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getLastname() {
        return lastname;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getParentname() {
        return parentname;
    }

    public String getAgentname() {
        return agentname;
    }
}