package com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin;

public class OperatorModalOrderPin {
    public String name;
    public String code;

    public OperatorModalOrderPin(String name, String code) {
        this.name = name;
        this.code = code;
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
