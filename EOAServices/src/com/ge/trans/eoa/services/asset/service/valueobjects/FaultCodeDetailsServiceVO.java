package com.ge.trans.eoa.services.asset.service.valueobjects;

public class FaultCodeDetailsServiceVO {

    private String controllerConfig;
    private String faultCode;
    private String faultDesc;
    private String faultDetail;

    public String getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(String controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultDetail() {
        return faultDetail;
    }

    public void setFaultDetail(String faultDetail) {
        this.faultDetail = faultDetail;
    }

}
