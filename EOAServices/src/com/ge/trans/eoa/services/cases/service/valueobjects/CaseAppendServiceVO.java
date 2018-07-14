/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseInfoServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 21, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CaseAppendServiceVO extends BaseVO {

    private static final long serialVersionUID = 13406664L;
    private String caseId;

    private String caseType;

    private String rxId;

    private String userId;

    private String rxDelv;
    private int skipCount;

    public int getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(int skipCount) {
        this.skipCount = skipCount;
    }

    public String getRxDelv() {
        return rxDelv;
    }

    public void setRxDelv(String rxDelv) {
        this.rxDelv = rxDelv;
    }

    private List<String> rxIdList;

    private String toCaseId;

    private String toolId;

    private String vehicleId;
    private String customerId;
    private String assetGrpName;
    private String assetNumber;
    private String toolObjId;

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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getRuleDefId() {
        return ruleDefId;
    }

    public void setRuleDefId(String ruleDefId) {
        this.ruleDefId = ruleDefId;
    }

    private String ruleDefId;

    public String getToCaseId() {
        return toCaseId;
    }

    public void setToCaseId(String toCaseId) {
        this.toCaseId = toCaseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getRxId() {
        return rxId;
    }

    public void setRxId(String rxId) {
        this.rxId = rxId;
    }

    public List<String> getRxIds() {
        return rxIdList;
    }

    public void setRxIds(List<String> rxIdList) {
        this.rxIdList = rxIdList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the toolObjId
     */
    public String getToolObjId() {
        return toolObjId;
    }

    /**
     * @param toolObjId
     *            the toolObjId to set
     */
    public void setToolObjId(String toolObjId) {
        this.toolObjId = toolObjId;
    }

}