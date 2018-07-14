package com.ge.trans.eoa.services.cases.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class CaseMergeServiceVO extends BaseVO {
    private static final long serialVersionUID = 45406664L;
    private String caseId;
    private String toCaseId;
    private String rxId;
    private String mergedTo;
    private String userId;
    private String rxDelv;
    private int skipCount;
    private String ruleDefId;
    private String toolId;
    private String customerId;
    private String assetGrpName;
    private String assetNumber;
    private String toolObjId;

    public String getMergedTo() {
        return mergedTo;
    }

    public void setMergedTo(String mergedTo) {
        this.mergedTo = mergedTo;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getRxId() {
        return rxId;
    }

    public void setRxId(String rxId) {
        this.rxId = rxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRxDelv() {
        return rxDelv;
    }

    public void setRxDelv(String rxDelv) {
        this.rxDelv = rxDelv;
    }

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public String getRuleDefId() {
        return ruleDefId;
    }

    public void setRuleDefId(String ruleDefId) {
        this.ruleDefId = ruleDefId;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getToolObjId() {
        return toolObjId;
    }

    public void setToolObjId(String toolObjId) {
        this.toolObjId = toolObjId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the toCaseId
     */
    public String getToCaseId() {
        return toCaseId;
    }

    /**
     * @param toCaseId
     *            the toCaseId to set
     */
    public void setToCaseId(String toCaseId) {
        this.toCaseId = toCaseId;
    }

}
