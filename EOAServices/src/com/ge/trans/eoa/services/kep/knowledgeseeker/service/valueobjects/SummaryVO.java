package com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects;

/*******************************************************************************
 * @Author : Igate Patni Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 24, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class SummaryVO extends BaseVO {

    private int segmentWindow;
    private int classificationWindow;
    private int classificationStepSize;
    private int progExclude;
    private int totAssetsAnalysed;

    public int getTotAssetsAnalysed() {
        return totAssetsAnalysed;
    }

    public void setTotAssetsAnalysed(int totAssetsAnalysed) {
        this.totAssetsAnalysed = totAssetsAnalysed;
    }

    private String normalCondition;
    private String supportLevel;
    // private int intDateRange;
    private String dateRange;
    private int analysisFailureCases;
    private int analysisNormalCases;
    private int dataNormalCases;
    private int dataFailureCases;
    private String filterFlag;
    private String failureType;
    private String dataFilter;
    // new fields
    private int daysAfterEvent;
    private String algorithmSelection;
    private int coverageAnalysis;
    private String classification;
    private String rootCause;
    private String symptoms;
    private int feAssets;
    private int seAssets;
    private int normalSegments;
    private int failureSegments;
    private String ruleMiningStartDate;
    private String ruleMiningEndDate;
    private String faultCode;
    private String rxDesc;

    public int getAnalysisFailureCases() {
        return analysisFailureCases;
    }

    public void setAnalysisFailureCases(final int analysisFailureCases) {
        this.analysisFailureCases = analysisFailureCases;
    }

    public int getAnalysisNormalCases() {
        return analysisNormalCases;
    }

    public void setAnalysisNormalCases(final int analysisNormalCases) {
        this.analysisNormalCases = analysisNormalCases;
    }

    public int getDataNormalCases() {
        return dataNormalCases;
    }

    public void setDataNormalCases(final int dataNormalCases) {
        this.dataNormalCases = dataNormalCases;
    }

    public int getDataFailureCases() {
        return dataFailureCases;
    }

    public void setDataFailureCases(final int dataFailureCases) {
        this.dataFailureCases = dataFailureCases;
    }

    public String getNormalCondition() {
        return normalCondition;
    }

    public int getSegmentWindow() {
        return segmentWindow;
    }

    public void setSegmentWindow(final int segmentWindow) {
        this.segmentWindow = segmentWindow;
    }

    public int getClassificationWindow() {
        return classificationWindow;
    }

    public void setClassificationWindow(final int classificationWindow) {
        this.classificationWindow = classificationWindow;
    }

    public int getClassificationStepSize() {
        return classificationStepSize;
    }

    public void setClassificationStepSize(final int classificationStepSize) {
        this.classificationStepSize = classificationStepSize;
    }

    public int getProgExclude() {
        return progExclude;
    }

    public void setProgExclude(final int progExclude) {
        this.progExclude = progExclude;
    }

    public void setNormalCondition(final String normalCondition) {
        this.normalCondition = normalCondition;
    }

    public String getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(final String supportLevel) {
        this.supportLevel = supportLevel;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(final String dateRange) {
        this.dateRange = dateRange;
    }

    public String getFilterFlag() {
        return filterFlag;
    }

    public void setFilterFlag(final String filterFlag) {
        this.filterFlag = filterFlag;
    }

    public String getFailureType() {
        return failureType;
    }

    public void setFailureType(final String failureType) {
        this.failureType = failureType;
    }

    public String getDataFilter() {
        return dataFilter;
    }

    public void setDataFilter(final String dataFilter) {
        this.dataFilter = dataFilter;
    }

    public int getDaysAfterEvent() {
        return daysAfterEvent;
    }

    public void setDaysAfterEvent(final int daysAfterEvent) {
        this.daysAfterEvent = daysAfterEvent;
    }

    public String getAlgorithmSelection() {
        return algorithmSelection;
    }

    public void setAlgorithmSelection(final String algorithmSelection) {
        this.algorithmSelection = algorithmSelection;
    }

    public int getCoverageAnalysis() {
        return coverageAnalysis;
    }

    public void setCoverageAnalysis(final int coverageAnalysis) {
        this.coverageAnalysis = coverageAnalysis;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(final String classification) {
        this.classification = classification;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(final String rootCause) {
        this.rootCause = rootCause;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(final String symptoms) {
        this.symptoms = symptoms;
    }

    public int getFeAssets() {
        return feAssets;
    }

    public void setFeAssets(final int feAssets) {
        this.feAssets = feAssets;
    }

    public int getSeAssets() {
        return seAssets;
    }

    public void setSeAssets(final int seAssets) {
        this.seAssets = seAssets;
    }

    public int getNormalSegments() {
        return normalSegments;
    }

    public void setNormalSegments(final int normalSegments) {
        this.normalSegments = normalSegments;
    }

    public int getFailureSegments() {
        return failureSegments;
    }

    public void setFailureSegments(final int failureSegments) {
        this.failureSegments = failureSegments;
    }

    public String getRuleMiningStartDate() {
        return ruleMiningStartDate;
    }

    public void setRuleMiningStartDate(final String ruleMiningStartDate) {
        this.ruleMiningStartDate = ruleMiningStartDate;
    }

    public String getRuleMiningEndDate() {
        return ruleMiningEndDate;
    }

    public void setRuleMiningEndDate(final String ruleMiningEndDate) {
        this.ruleMiningEndDate = ruleMiningEndDate;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    /**
     * @return the rxDesc
     */
    public String getRxDesc() {
        return rxDesc;
    }

    /**
     * @param rxDesc
     *            the rxDesc to set
     */
    public void setRxDesc(String rxDesc) {
        this.rxDesc = rxDesc;
    }
}
