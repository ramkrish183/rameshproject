package com.ge.trans.eoa.services.asset.service.valueobjects;

public class FaultValueVO {

    private String parameterTitle;
    private String preValue;
    private String postValue;
    private String biasValue;
    private String edpTemplate;
    private String faultSource;
    private String objId;
    private String cfgVersion;
    private String cfgDesc;
    private String cfgDetailDesc;
    private String status;

    public String getParameterTitle() {
        return parameterTitle;
    }

    public void setParameterTitle(String parameterTitle) {
        this.parameterTitle = parameterTitle;
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getPostValue() {
        return postValue;
    }

    public void setPostValue(String postValue) {
        this.postValue = postValue;
    }

    public String getBiasValue() {
        return biasValue;
    }

    public void setBiasValue(String biasValue) {
        this.biasValue = biasValue;
    }

    public String getEdpTemplate() {
        return edpTemplate;
    }

    public void setEdpTemplate(String edpTemplate) {
        this.edpTemplate = edpTemplate;
    }

    public String getFaultSource() {
        return faultSource;
    }

    public void setFaultSource(String faultSource) {
        this.faultSource = faultSource;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getCfgVersion() {
        return cfgVersion;
    }

    public void setCfgVersion(String cfgVersion) {
        this.cfgVersion = cfgVersion;
    }

    public String getCfgDesc() {
        return cfgDesc;
    }

    public void setCfgDesc(String cfgDesc) {
        this.cfgDesc = cfgDesc;
    }

    public String getCfgDetailDesc() {
        return cfgDetailDesc;
    }

    public void setCfgDetailDesc(String cfgDetailDesc) {
        this.cfgDetailDesc = cfgDetailDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
