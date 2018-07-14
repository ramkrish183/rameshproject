/**
 * ============================================================
 * File : FinalRuleServiceVO.java
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
public class FinalRuleServiceVO extends BaseVO {

    static final long serialVersionUID = -2943619455635410586L;
    private String strFinalRuleID;
    private String strSubsystem;
    private String strRuleDescription;
    private String strLookBack;
    private String strFamily;
    private String strRuleTitle;
    private String strCompleteStatus;
    private String strStatus;
    private String strRuleType;
    private String strTopLevelRuleId;
    private String strOriginalId;
    // Added the new variable to hold the rule iD which we are going to edit.
    private String strOldFinalRuleID;
    // Added the new variable to hold the revision notes.
    private String strRevisionNotes;
    private ArrayList<RuleHistoryServiceVO> arlRuleHistoryVO;
    private ArrayList<SimpleRuleServiceVO> arlSimpleRule;
    private ArrayList<ComplexRuleServiceVO> arlComplexRule;
    private ClearingLogicRuleServiceVO clearingLogicRule;
    private ArrayList<RuleDefinitionServiceVO> arlRuleDefinition;
    private RuleDefinitionServiceVO ruleDefinition;
    private String strPixels;
	private String defaultUOM;	
	
	public String getDefaultUOM() {
		return defaultUOM;
	}

	public void setDefaultUOM(String defaultUOM) {
		this.defaultUOM = defaultUOM;
	}
    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrOriginalId() {
        return strOriginalId;
    }

    public void setStrOriginalId(final String strOriginalId) {
        this.strOriginalId = strOriginalId;
    }

    public String getStrRuleType() {
        return strRuleType;
    }

    public void setStrRuleType(final String strRuleType) {
        this.strRuleType = strRuleType;
    }

    /**
     * @param arlRuleHistoryVO
     * @param strFinalRuleID
     * @param strLookBack
     * @param strRuleDescription
     * @param strRuleTitle
     * @param strSubsystem
     */
    public FinalRuleServiceVO(final ArrayList<RuleHistoryServiceVO> arlRuleHistoryVO, final String strFinalRuleID,
            final String strLookBack, final String strRuleDescription, final String strRuleTitle,
            final String strSubsystem) {
        super();
        this.arlRuleHistoryVO = arlRuleHistoryVO;
        this.strFinalRuleID = strFinalRuleID;
        this.strLookBack = strLookBack;
        this.strRuleDescription = strRuleDescription;
        this.strRuleTitle = strRuleTitle;
        this.strSubsystem = strSubsystem;
    }

    public FinalRuleServiceVO() {
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
     * @return the arlSimpleRule
     */
    public ArrayList<SimpleRuleServiceVO> getArlSimpleRule() {
        return arlSimpleRule;
    }

    /**
     * @param arlSimpleRule
     *            the arlSimpleRule to set
     */
    public void setArlSimpleRule(final ArrayList<SimpleRuleServiceVO> arlSimpleRule) {
        this.arlSimpleRule = arlSimpleRule;
    }

    /**
     * @return the arlComplexRule
     */
    public ArrayList<ComplexRuleServiceVO> getArlComplexRule() {
        return arlComplexRule;
    }

    /**
     * @param arlComplexRule
     *            the arlComplexRule to set
     */
    public void setArlComplexRule(final ArrayList<ComplexRuleServiceVO> arlComplexRule) {
        this.arlComplexRule = arlComplexRule;
    }

    /**
     * @param arlComplexRule
     *            the arlComplexRule to set
     */
    public void setClearingLogicRule(final ClearingLogicRuleServiceVO clearingLogicRule) {
        this.clearingLogicRule = clearingLogicRule;
    }

    /**
     * @return the arlComplexRule
     */
    public ClearingLogicRuleServiceVO getClearingLogicRule() {
        return clearingLogicRule;
    }

    /**
     * @return the arlRuleDefinition
     */
    public ArrayList<RuleDefinitionServiceVO> getArlRuleDefinition() {
        return arlRuleDefinition;
    }

    /**
     * @param arlRuleDefinition
     *            the arlRuleDefinition to set
     */
    public void setArlRuleDefinition(final ArrayList<RuleDefinitionServiceVO> arlRuleDefinition) {
        this.arlRuleDefinition = arlRuleDefinition;
    }

    /**
     * @return the strSubsystem
     */
    public String getStrSubsystem() {
        return strSubsystem;
    }

    /**
     * @param strSubsystem
     *            the strSubsystem to set
     */
    public void setStrSubsystem(final String strSubsystem) {
        this.strSubsystem = strSubsystem;
    }

    /**
     * @return the strRuleDescription
     */
    public String getStrRuleDescription() {
        return strRuleDescription;
    }

    /**
     * @param strRuleDescription
     *            the strRuleDescription to set
     */
    public void setStrRuleDescription(final String strRuleDescription) {
        this.strRuleDescription = strRuleDescription;
    }

    /**
     * @return the strLookBack
     */
    public String getStrLookBack() {
        return strLookBack;
    }

    /**
     * @param strLookBack
     *            the strLookBack to set
     */
    public void setStrLookBack(final String strLookBack) {
        this.strLookBack = strLookBack;
    }

    /**
     * @return the strRuleTitle
     */
    public String getStrRuleTitle() {
        return strRuleTitle;
    }

    /**
     * @param strRuleTitle
     *            the strRuleTitle to set
     */
    public void setStrRuleTitle(final String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
    }

    /**
     * @return the arlRuleHistoryVO
     */
    public ArrayList<RuleHistoryServiceVO> getArlRuleHistoryVO() {
        return arlRuleHistoryVO;
    }

    /**
     * @param arlRuleHistoryVO
     *            the arlRuleHistoryVO to set
     */
    public void setArlRuleHistoryVO(final ArrayList<RuleHistoryServiceVO> arlRuleHistoryVO) {
        this.arlRuleHistoryVO = arlRuleHistoryVO;
    }

    public String getStrTopLevelRuleId() {
        return strTopLevelRuleId;
    }

    public void setStrTopLevelRuleId(final String strTopLevelRuleId) {
        this.strTopLevelRuleId = strTopLevelRuleId;
    }

    /**
     * @return the strFamily
     */
    public String getStrFamily() {
        return strFamily;
    }

    /**
     * @param strFamily
     *            the strFamily to set
     */
    public void setStrFamily(final String strFamily) {
        this.strFamily = strFamily;
    }

    public RuleDefinitionServiceVO getRuleDefinition() {
        return ruleDefinition;
    }

    public void setRuleDefinition(final RuleDefinitionServiceVO ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }

    /**
     * @return the strFamily
     */
    /*
     * public String getStrFamily() { return strFamily; }
     *//**
       * @param strFamily
       *            the strFamily to set
       */
    /*
     * public void setStrFamily(String strFamily) { this.strFamily = strFamily;
     * }
     */
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFinalRuleID", strFinalRuleID)
                .append("strSubsystem", strSubsystem).append("strRuleDescription", strRuleDescription)
                .append("strLookBack", strLookBack).append("strFamily", strFamily).append("strRuleTitle", strRuleTitle)
                .append("strTopLevelRuleId", strTopLevelRuleId).append("arlRuleHistoryVO", arlRuleHistoryVO)
                .append("arlSimpleRule", arlSimpleRule).append("arlComplexRule", arlComplexRule)
                .append("arlClearingLogicRule", clearingLogicRule).append("arlRuleDefinition", arlRuleDefinition)
                .toString();
    }

    public String getStrCompleteStatus() {
        return strCompleteStatus;
    }

    public void setStrCompleteStatus(final String strCompleteStatus) {
        this.strCompleteStatus = strCompleteStatus;
    }

    public String getStrOldFinalRuleID() {
        return strOldFinalRuleID;
    }

    public void setStrOldFinalRuleID(String strOldFinalRuleID) {
        this.strOldFinalRuleID = strOldFinalRuleID;
    }

    public String getStrRevisionNotes() {
        return strRevisionNotes;
    }

    public void setStrRevisionNotes(final String strRevisionNotes) {
        this.strRevisionNotes = strRevisionNotes;
    }

    public String getStrPixels() {
        return strPixels;
    }

    public void setStrPixels(String strPixels) {
        this.strPixels = strPixels;
    }

}