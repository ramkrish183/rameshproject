package com.ge.trans.eoa.services.cbr.service.valueobjects;

public class CBRAdminVO {
    private String rxId;
    private String caseId;
    private String status;
    private String rxTitle;
    private String comments;
    private String strLanguage;
    private String simonObjid;

    public String getSimonObjid() {
        return simonObjid;
    }

    public void setSimonObjid(String simonObjid) {
        this.simonObjid = simonObjid;
    }

    public String getStrLanguage() {
        return strLanguage;
    }

    public void setStrLanguage(String strLanguage) {
        this.strLanguage = strLanguage;
    }

    public String getRxId() {
        return rxId;
    }

    public void setRxId(String rxId) {
        this.rxId = rxId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRxTitle() {
        return rxTitle;
    }

    public void setRxTitle(String rxTitle) {
        this.rxTitle = rxTitle;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
