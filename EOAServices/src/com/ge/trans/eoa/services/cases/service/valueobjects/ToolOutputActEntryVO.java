package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class ToolOutputActEntryVO {

    private String parentCaseID;
    private String parentCaseObjId;
    private List<String> manualcaseID = new ArrayList<String>();
    private List<String> caseTitle = new ArrayList<String>();
    private String userName;
    private List<String> arlCaseObjid = new ArrayList<String>();

    public String getParentCaseObjId() {
        return parentCaseObjId;
    }

    public void setParentCaseObjId(String parentCaseObjId) {
        this.parentCaseObjId = parentCaseObjId;
    }

    public String getParentCaseID() {
        return parentCaseID;
    }

    public void setParentCaseID(String parentCaseID) {
        this.parentCaseID = parentCaseID;
    }

    public List<String> getManualcaseID() {
        return manualcaseID;
    }

    public void setManualcaseID(List<String> manualcaseID) {
        this.manualcaseID = manualcaseID;
    }

    public List<String> getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(List<String> caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getArlCaseObjid() {
        return arlCaseObjid;
    }

    public void setArlCaseObjid(List<String> arlCaseObjid) {
        this.arlCaseObjid = arlCaseObjid;
    }

}
