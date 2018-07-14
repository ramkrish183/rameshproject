package com.ge.trans.eoa.services.asset.service.valueobjects;

public class AddEditEDPDetailsVO {
    private String tempObjId;
    private String ctrlCfgObjId;
    private String ctrlCfgName;
    private String cfgFileName;
    private String paramObjId;
    private String addedParamObjId;
    private String removedParamObjId;
    private String templateNo;
    private String versionNo;
    private String status;
    private String title;
    private String whatNew;
    private String userName;

    public String getTempObjId() {
        return this.tempObjId;
    }

    public void setTempObjId(String tempObjId) {
        this.tempObjId = tempObjId;
    }

    public String getCtrlCfgObjId() {
        return this.ctrlCfgObjId;
    }

    public void setCtrlCfgObjId(String ctrlCfgObjId) {
        this.ctrlCfgObjId = ctrlCfgObjId;
    }

    public String getCtrlCfgName() {
        return this.ctrlCfgName;
    }

    public void setCtrlCfgName(String ctrlCfgName) {
        this.ctrlCfgName = ctrlCfgName;
    }

    public String getCfgFileName() {
        return this.cfgFileName;
    }

    public void setCfgFileName(String cfgFileName) {
        this.cfgFileName = cfgFileName;
    }

    public String getParamObjId() {
        return this.paramObjId;
    }

    public void setParamObjId(String paramObjId) {
        this.paramObjId = paramObjId;
    }

    public String getAddedParamObjId() {
        return addedParamObjId;
    }

    public void setAddedParamObjId(String addedParamObjId) {
        this.addedParamObjId = addedParamObjId;
    }

    public String getRemovedParamObjId() {
        return removedParamObjId;
    }

    public void setRemovedParamObjId(String removedParamObjId) {
        this.removedParamObjId = removedParamObjId;
    }

    public String getTemplateNo() {
        return this.templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhatNew() {
        return this.whatNew;
    }

    public void setWhatNew(String whatNew) {
        this.whatNew = whatNew;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}