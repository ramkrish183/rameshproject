package com.ge.trans.eoa.services.asset.service.valueobjects;

public class ConfigTemplateDetailsVO {
    private String tempObjId;
    private String templateNo;
    private String versionNo;
    private String title;
    private String status;

    public String getTempObjId() {
        return this.tempObjId;
    }

    public void setTempObjId(String tempObjId) {
        this.tempObjId = tempObjId;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}