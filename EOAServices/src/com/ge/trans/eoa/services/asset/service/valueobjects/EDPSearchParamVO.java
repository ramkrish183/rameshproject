package com.ge.trans.eoa.services.asset.service.valueobjects;

public class EDPSearchParamVO {
    private String searchBy;
    private String condition;
    private String searchVal;
    private String ctrlCfgObjId;
    private String cfgFileName;

    public String getSearchBy() {
        return this.searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSearchVal() {
        return this.searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public String getCtrlCfgObjId() {
        return this.ctrlCfgObjId;
    }

    public void setCtrlCfgObjId(String ctrlCfgObjId) {
        this.ctrlCfgObjId = ctrlCfgObjId;
    }

    public String getCfgFileName() {
        return this.cfgFileName;
    }

    public void setCfgFileName(String cfgFileName) {
        this.cfgFileName = cfgFileName;
    }
}