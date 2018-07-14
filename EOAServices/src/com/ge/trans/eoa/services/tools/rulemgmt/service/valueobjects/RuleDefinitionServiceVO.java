/**
 * ============================================================
 * File : RuleDefinitionServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleDefinitionServiceVO extends BaseVO {

    static final long serialVersionUID = 115415664L;
    private String strFinalRuleID;
    private String strRuleDefID;
    private ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomer;
    private ArrayList<RuleDefModelServiceVO> arlRuleDefModel;
    private ArrayList<RuleDefConfigServiceVO> arlRuleDefConfig;
    private ArrayList<RuleDefFleetServiceVO> arlRuleDefFleet;
    private RuleDefModelServiceVO ruleDefModelService;
    private String strMessage;
    private String strRuleType;
    private String strRecommendation;
    private String strServiceType;
    private String strExclude;
    private String healthFactor;
    private String perfHealthCheckID;

    /**
     * @param arlRuleDefConfig
     * @param arlRuleDefCustomer
     * @param arlRuleDefModel
     * @param strFinalRuleID
     * @param strMessage
     * @param strRecommendation
     * @param strRuleDefID
     * @param strRuleType
     * @param strServiceType
     */
    public RuleDefinitionServiceVO(ArrayList<RuleDefConfigServiceVO> arlRuleDefConfig,
            ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomer, ArrayList<RuleDefModelServiceVO> arlRuleDefModel,
            String strFinalRuleID, String strMessage, String strRecommendation, String strRuleDefID, String strRuleType,
            String strServiceType) {
        super();
        this.arlRuleDefConfig = arlRuleDefConfig;
        this.arlRuleDefCustomer = arlRuleDefCustomer;
        this.arlRuleDefModel = arlRuleDefModel;
        this.strFinalRuleID = strFinalRuleID;
        this.strMessage = strMessage;
        this.strRecommendation = strRecommendation;
        this.strRuleDefID = strRuleDefID;
        this.strRuleType = strRuleType;
        this.strServiceType = strServiceType;
    }

    /**
     *
     */
    public RuleDefinitionServiceVO() {
    }

    /**
     * @return the strFinalRuleID
     */
    public String getStrFinalRuleID() {
        return strFinalRuleID;
    }

    /**
     * @param strFinalRuleID
     *            the strFinalRuleID to set
     */
    public void setStrFinalRuleID(final String strFinalRuleID) {
        this.strFinalRuleID = strFinalRuleID;
    }

    /**
     * @return the strRuleDefID
     */
    public String getStrRuleDefID() {
        return strRuleDefID;
    }

    /**
     * @param strRuleDefID
     *            the strRuleDefID to set
     */
    public void setStrRuleDefID(final String strRuleDefID) {
        this.strRuleDefID = strRuleDefID;
    }

    /**
     * @return the arlRuleDefCustomer
     */
    public ArrayList<RuleDefCustomerServiceVO> getArlRuleDefCustomer() {
        return arlRuleDefCustomer;
    }

    /**
     * @param arlRuleDefCustomer
     *            the arlRuleDefCustomer to set
     */
    public void setArlRuleDefCustomer(ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomer) {
        this.arlRuleDefCustomer = arlRuleDefCustomer;
    }

    /**
     * @return the arlRuleDefModel
     */
    public ArrayList<RuleDefModelServiceVO> getArlRuleDefModel() {
        return arlRuleDefModel;
    }

    /**
     * @param arlRuleDefModel
     *            the arlRuleDefModel to set
     */
    public void setArlRuleDefModel(ArrayList<RuleDefModelServiceVO> arlRuleDefModel) {
        this.arlRuleDefModel = arlRuleDefModel;
    }

    /**
     * @return the arlRuleDefConfig
     */
    public ArrayList<RuleDefConfigServiceVO> getArlRuleDefConfig() {
        return arlRuleDefConfig;
    }

    /**
     * @param arlRuleDefConfig
     *            the arlRuleDefConfig to set
     */
    public void setArlRuleDefConfig(ArrayList<RuleDefConfigServiceVO> arlRuleDefConfig) {
        this.arlRuleDefConfig = arlRuleDefConfig;
    }

    /**
     * @return the strMessage
     */
    public String getStrMessage() {
        return strMessage;
    }

    /**
     * @param strMessage
     *            the strMessage to set
     */
    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    /**
     * @return the strRuleType
     */
    public String getStrRuleType() {
        return strRuleType;
    }

    /**
     * @param strRuleType
     *            the strRuleType to set
     */
    public void setStrRuleType(String strRuleType) {
        this.strRuleType = strRuleType;
    }

    /**
     * @return the strRecommendation
     */
    public String getStrRecommendation() {
        return strRecommendation;
    }

    /**
     * @param strRecommendation
     *            the strRecommendation to set
     */
    public void setStrRecommendation(String strRecommendation) {
        this.strRecommendation = strRecommendation;
    }

    /**
     * @return the strServiceType
     */
    public String getStrServiceType() {
        return strServiceType;
    }

    /**
     * @param strServiceType
     *            the strServiceType to set
     */
    public void setStrServiceType(String strServiceType) {
        this.strServiceType = strServiceType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFinalRuleID", strFinalRuleID)
                .append("strRuleDefID", strRuleDefID).append("arlRuleDefCustomer", arlRuleDefCustomer)
                .append("arlRuleDefModel", arlRuleDefModel).append("arlRuleDefConfig", arlRuleDefConfig)
                .append("strRuleType", strRuleType).append("strRecommendation", strRecommendation)
                .append("strServiceType", strServiceType).toString();
    }

    public ArrayList<RuleDefFleetServiceVO> getArlRuleDefFleet() {
        return arlRuleDefFleet;
    }

    public void setArlRuleDefFleet(ArrayList<RuleDefFleetServiceVO> arlRuleDefFleet) {
        this.arlRuleDefFleet = arlRuleDefFleet;
    }

    public RuleDefModelServiceVO getRuleDefModelService() {
        return ruleDefModelService;
    }

    public void setRuleDefModelService(RuleDefModelServiceVO ruleDefModelService) {
        this.ruleDefModelService = ruleDefModelService;
    }

    public String getStrExclude() {
        return strExclude;
    }

    public void setStrExclude(String strExclude) {
        this.strExclude = strExclude;
    }

    public String getHealthFactor() {
        return healthFactor;
    }

    public void setHealthFactor(String healthFactor) {
        this.healthFactor = healthFactor;
    }

    public String getPerfHealthCheckID() {
        return perfHealthCheckID;
    }

    public void setPerfHealthCheckID(String perfHealthCheckID) {
        this.perfHealthCheckID = perfHealthCheckID;
    }

}
