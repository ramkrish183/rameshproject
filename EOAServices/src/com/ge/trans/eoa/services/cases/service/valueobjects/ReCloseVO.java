package com.ge.trans.eoa.services.cases.service.valueobjects;

public class ReCloseVO {

    private String caseId;
    private String userId;
    private String reCloseAction;
    private String appendCaseId;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReCloseAction() {
        return reCloseAction;
    }

    public void setReCloseAction(String reCloseAction) {
        this.reCloseAction = reCloseAction;
    }

    public String getAppendCaseId() {
        return appendCaseId;
    }

    public void setAppendCaseId(String appendCaseId) {
        this.appendCaseId = appendCaseId;
    }

}
