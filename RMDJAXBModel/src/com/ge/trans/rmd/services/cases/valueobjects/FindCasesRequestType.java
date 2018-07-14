package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findCasesRequestType", propOrder = { "customerName", "roadNumberHeader", "roadNumber", "searchOption",
        "filterOption", "fieldValue", "caseType", "startdate", "endDate", "roadNumberFilterSelected" })
@XmlRootElement
public class FindCasesRequestType {
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected String roadNumberHeader;
    @XmlElement(required = true)
    protected String roadNumber;
    @XmlElement(required = true)
    protected String searchOption;
    @XmlElement(required = true)
    protected String filterOption;
    @XmlElement(required = true)
    protected String fieldValue;
    @XmlElement(required = true)
    protected String caseType;
    @XmlElement(required = true)
    protected String startdate;
    @XmlElement(required = true)
    protected String endDate;
    @XmlElement(required = true)
    protected String roadNumberFilterSelected;

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
