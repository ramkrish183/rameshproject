package com.ge.trans.eoa.services.cases.service.valueobjects;

public class FindCasesVO {
    private String customerName;
    private String roadNumberHeader;
    private String roadNumber;
    private String searchOption;
    private String filterOption;
    private String fieldValue;
    private String caseType;
    private String startdate;
    private String endDate;
    private String roadNumberFilterSelected;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoadNumberHeader() {
        return roadNumberHeader;
    }

    public void setRoadNumberHeader(String roadNumberHeader) {
        this.roadNumberHeader = roadNumberHeader;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

    public String getFilterOption() {
        return filterOption;
    }

    public void setFilterOption(String filterOption) {
        this.filterOption = filterOption;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRoadNumberFilterSelected() {
        return roadNumberFilterSelected;
    }

    public void setRoadNumberFilterSelected(String roadNumberFilterSelected) {
        this.roadNumberFilterSelected = roadNumberFilterSelected;
    }
}
