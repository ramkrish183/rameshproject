package com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects;

public class KSRequestVO {
    private String customers;
    private String fleets;
    private String models;
    private String configs;
    private String unitNumbers;
    private String roadNumber;
    private String runname;

    private String userName;
    private String daysAfterEvent;
    private String classification;
    private String filterFlag;
    private String progWindow;
    private String algorithm;
    private String classificationWindow;
    private String symptom;
    private String rootCause;
    private String coverage;
    private String normalData;
    private String fromdate;
    private String todate;
    private String faultCode;
    private String rx;

    /**
     * @return the rx
     */
    public String getRx() {
        return rx;
    }

    /**
     * @param rx
     *            the rx to set
     */
    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(String filterFlag) {
        this.filterFlag = filterFlag;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(final String customers) {
        this.customers = customers;
    }

    public String getFleets() {
        return fleets;
    }

    public void setFleets(final String fleets) {
        this.fleets = fleets;
    }

    public String getModels() {
        return models;
    }

    public void setModels(final String models) {
        this.models = models;
    }

    public String getConfigs() {
        return configs;
    }

    public void setConfigs(final String configs) {
        this.configs = configs;
    }

    public String getUnitNumbers() {
        return unitNumbers;
    }

    public void setUnitNumbers(final String unitNumbers) {
        this.unitNumbers = unitNumbers;
    }

    public String getRunname() {
        return runname;
    }

    public void setRunname(final String runname) {
        this.runname = runname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getDaysAfterEvent() {
        return daysAfterEvent;
    }

    public void setDaysAfterEvent(final String daysAfterEvent) {
        this.daysAfterEvent = daysAfterEvent;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(final String classification) {
        this.classification = classification;
    }

    public String getProgWindow() {
        return progWindow;
    }

    public void setProgWindow(final String progWindow) {
        this.progWindow = progWindow;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }

    public String getClassificationWindow() {
        return classificationWindow;
    }

    public void setClassificationWindow(final String classificationWindow) {
        this.classificationWindow = classificationWindow;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(final String symptom) {
        this.symptom = symptom;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(final String rootCause) {
        this.rootCause = rootCause;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(final String coverage) {
        this.coverage = coverage;
    }

    public String getNormalData() {
        return normalData;
    }

    public void setNormalData(final String normalData) {
        this.normalData = normalData;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(final String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(final String todate) {
        this.todate = todate;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(final String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

}
