package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.List;

public class RepairCodeEoaDetailsVO {
    private String taskDesc;
    private String repairCodeId;
    private String repairCode;
    private String repairCodeDesc;
    private String model;
    private String searchBy;
    private String searchValue;
    private String recomId;
    private String caseId;
    private String taskId;
    private List<String> repairCodeList;
    private String noOfCases;
    private String status;
    private String models;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getModels() {
        return models;
    }
    public void setModels(String models) {
        this.models = models;
    }
    public String getNoOfCases() {
        return noOfCases;
    }
    public void setNoOfCases(String noOfCases) {
        this.noOfCases = noOfCases;
    }
    public String getTaskDesc() {
        return taskDesc;
    }
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
    public String getRepairCodeId() {
        return repairCodeId;
    }
    public void setRepairCodeId(String repairCodeId) {
        this.repairCodeId = repairCodeId;
    }
    public String getRepairCode() {
        return repairCode;
    }
    public void setRepairCode(String repairCode) {
        this.repairCode = repairCode;
    }
    public String getRepairCodeDesc() {
        return repairCodeDesc;
    }
    public void setRepairCodeDesc(String repaidCodeDesc) {
        this.repairCodeDesc = repaidCodeDesc;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getSearchBy() {
        return searchBy;
    }
    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public String getRecomId() {
        return recomId;
    }
    public void setRecomId(String recomId) {
        this.recomId = recomId;
    }
    public String getCaseId() {
        return caseId;
    }
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public List<String> getRepairCodeList() {
        return repairCodeList;
    }
    public void setRepairCodeList(List<String> repairCodeList) {
        this.repairCodeList = repairCodeList;
    }
}
