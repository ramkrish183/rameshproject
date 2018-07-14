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
public class ClearingLogicRuleServiceVO extends BaseVO {

    static final long serialVersionUID = -2943619455635410586L;
    private String strClearingLogicRuleID;
    private String strOldClearingLogicRuleID;
    private String strFinalRuleID;
    private ArrayList<SimpleRuleServiceVO> arlSimpleRule;
    private ArrayList<ComplexRuleServiceVO> arlComplexRule;
    private String strTopLevelRuleID;
    private String strPixels;

    /**
     * @param strFinalRuleID
     */
    public ClearingLogicRuleServiceVO(final String strFinalRuleID) {
        super();
        this.strFinalRuleID = strFinalRuleID;
    }

    public ClearingLogicRuleServiceVO() {
    }

    /**
     * @return the strFinalRuleID
     */
    public String getStrClearingLogicRuleID() {
        return strClearingLogicRuleID;
    }

    /**
     * @param strFinalRuleID
     *            the strFinalRuleID to set
     */
    public void setStrClearingLogicRuleID(final String strClearingLogicRuleID) {
        this.strClearingLogicRuleID = strClearingLogicRuleID;
    }

    /**
     * @return the strOldClearingLogicRuleID
     */
    public String getStrOldClearingLogicRuleID() {
        return strOldClearingLogicRuleID;
    }

    /**
     * @param strOldClearingLogicRuleID
     *            the strOldClearingLogicRuleID to set
     */
    public void setStrOldClearingLogicRuleID(String strOldClearingLogicRuleID) {
        this.strOldClearingLogicRuleID = strOldClearingLogicRuleID;
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
     * @return the topLevelRuleID
     */
    public String getStrTopLevelRuleID() {
        return strTopLevelRuleID;
    }

    /**
     * @param topLevelRuleID
     *            the topLevelRuleID to set
     */
    public void setStrTopLevelRuleID(String strTopLevelRuleID) {
        this.strTopLevelRuleID = strTopLevelRuleID;
    }

    public String getStrPixels() {
        return strPixels;
    }

    public void setStrPixels(String strPixels) {
        this.strPixels = strPixels;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFinalRuleID", strFinalRuleID)
                .append("arlSimpleRule", arlSimpleRule).append("arlComplexRule", arlComplexRule).toString();
    }

}