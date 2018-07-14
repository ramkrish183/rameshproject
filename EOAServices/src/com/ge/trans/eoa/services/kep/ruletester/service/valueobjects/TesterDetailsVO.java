package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/**
 * ============================================================
 * File : TesterDetailsVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.tester.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This Class consists of the value objects for TesterDetails
 * @History :
 ******************************************************************************/
public class TesterDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String customer;
    private String fleet;
    private String model;
    private String config;
    private String assetNumber;
    private String dateRange;
    private String options;
    private String ruleId;
    private String coverage;
    private String coveragePercent;
    private String falseAlarms;
    private String falseAlarmPercent;
    private String segmentWindow;
    private String classificationWindow;
    private String classifyStepSize;
    private String progExclude;
    private String failureType;
    private String rootCause;
    private String symptom;
    private String classification;
    private String ruleTesterStartDate;
    private String ruleTesterEndDate;
    private List<TesterResultVO> testerResult = new ArrayList<TesterResultVO>();
    private List<DataFilterDetailsVO> dataFilterDetails = new ArrayList<DataFilterDetailsVO>();
    private List<TesterSummaryVO> testerSummary = new ArrayList<TesterSummaryVO>();
    private boolean hasClassification;
    private String rxDesc;
    private String roadNumber;

    public String getFailureType() {
        return failureType;
    }

    public void setFailureType(final String failureType) {
        this.failureType = failureType;
    }

    public List<TesterResultVO> getTesterResult() {
        return testerResult;
    }

    public void setTesterResult(final List<TesterResultVO> testerResult) {
        this.testerResult = testerResult;
    }

    public List<DataFilterDetailsVO> getDataFilterDetails() {
        return dataFilterDetails;
    }

    public void setDataFilterDetails(final List<DataFilterDetailsVO> dataFilterDetails) {
        this.dataFilterDetails = dataFilterDetails;
    }

    public List<TesterSummaryVO> getTesterSummary() {
        return testerSummary;
    }

    public void setTesterSummary(final List<TesterSummaryVO> testerSummary) {
        this.testerSummary = testerSummary;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(final String customer) {
        this.customer = customer;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(final String fleet) {
        this.fleet = fleet;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(final String config) {
        this.config = config;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(final String dateRange) {
        this.dateRange = dateRange;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(final String options) {
        this.options = options;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(final String ruleId) {
        this.ruleId = ruleId;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(final String coverage) {
        this.coverage = coverage;
    }

    public String getCoveragePercent() {
        return coveragePercent;
    }

    public void setCoveragePercent(final String coveragePercent) {
        this.coveragePercent = coveragePercent;
    }

    public String getFalseAlarms() {
        return falseAlarms;
    }

    public void setFalseAlarms(final String falseAlarms) {
        this.falseAlarms = falseAlarms;
    }

    public String getFalseAlarmPercent() {
        return falseAlarmPercent;
    }

    public void setFalseAlarmPercent(final String falseAlarmPercent) {
        this.falseAlarmPercent = falseAlarmPercent;
    }

    public String getSegmentWindow() {
        return segmentWindow;
    }

    public void setSegmentWindow(final String segmentWindow) {
        this.segmentWindow = segmentWindow;
    }

    public String getClassificationWindow() {
        return classificationWindow;
    }

    public void setClassificationWindow(final String classificationWindow) {
        this.classificationWindow = classificationWindow;
    }

    public String getClassifyStepSize() {
        return classifyStepSize;
    }

    public void setClassifyStepSize(final String classifyStepSize) {
        this.classifyStepSize = classifyStepSize;
    }

    public String getProgExclude() {
        return progExclude;
    }

    public void setProgExclude(final String progExclude) {
        this.progExclude = progExclude;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(final String rootCause) {
        this.rootCause = rootCause;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(final String symptom) {
        this.symptom = symptom;
    }

    public String getRxDesc() {
        return rxDesc;
    }

    public void setRxDesc(String rxDesc) {
        this.rxDesc = rxDesc;
    }

    public boolean isHasClassification() {
        return hasClassification;
    }

    public void setHasClassification(final boolean hasClassification) {
        this.hasClassification = hasClassification;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getRuleTesterStartDate() {
        return ruleTesterStartDate;
    }

    public void setRuleTesterStartDate(final String ruleTesterStartDate) {
        this.ruleTesterStartDate = ruleTesterStartDate;
    }

    public String getRuleTesterEndDate() {
        return ruleTesterEndDate;
    }

    public void setRuleTesterEndDate(final String ruleTesterEndDate) {
        this.ruleTesterEndDate = ruleTesterEndDate;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }
}
