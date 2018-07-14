package com.ge.trans.eoa.services.asset.service.valueobjects;

public class EFIDetailsVO {
    private String tempObjId;
    private String templateNo;
    private String versionNo;
    private String isActive;

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

    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}