/**
 * ============================================================
 * File : ComplexRuleServiceVO.java
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
 * @Date Created: Nov 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ComplexRuleServiceVO extends BaseVO {

    static final long serialVersionUID = 733369998L;
    private Long csequenceID;
    private String strTimeWindow;
    private String strFrequency;
    private String strFunction;
    private String strRule1;
    private String strRule1Type;
    private String strRule2;
    private String strRule2Type;
    private String strHR;
    private String strPT;
    private String strMIN;
    private String strTimeFlag;
    private boolean bHR;
    private boolean bPT;
    private boolean bMIN;
    private String strComplexRuleId;
    private ArrayList<ComplexRuleLinkServiceVO> arlComplexRuleLinkVO;
    private String strFinalRule1Flag;
    private String strFinalRule2Flag;
    private String strPixels;
    private String strRule1Pixels;
    private String strRule2Pixels;
    private String strSubTimeWindow;
    private String strSubWindowGoal;
    private String notes;

    public String getStrSubTimeWindow() {
        return strSubTimeWindow;
    }

    public void setStrSubTimeWindow(String strSubTimeWindow) {
        this.strSubTimeWindow = strSubTimeWindow;
    }

    public String getStrSubWindowGoal() {
        return strSubWindowGoal;
    }

    public void setStrSubWindowGoal(String strSubWindowGoal) {
        this.strSubWindowGoal = strSubWindowGoal;
    }

    /**
     *
     */
    public ComplexRuleServiceVO() {
        super();
    }

    /**
     * @return the csequenceID
     */
    public Long getCsequenceID() {
        return csequenceID;
    }

    /**
     * @param csequenceID
     *            the csequenceID to set
     */
    public void setCsequenceID(final Long csequenceID) {
        this.csequenceID = csequenceID;
    }

    /**
     * @return the strTimeWindow
     */
    public String getStrTimeWindow() {
        return strTimeWindow;
    }

    /**
     * @param strTimeWindow
     *            the strTimeWindow to set
     */
    public void setStrTimeWindow(final String strTimeWindow) {
        this.strTimeWindow = strTimeWindow;
    }

    /**
     * @return the strFrequency
     */
    public String getStrFrequency() {
        return strFrequency;
    }

    /**
     * @param strFrequency
     *            the strFrequency to set
     */
    public void setStrFrequency(final String strFrequency) {
        this.strFrequency = strFrequency;
    }

    /**
     * @return the strFunction
     */
    public String getStrFunction() {
        return strFunction;
    }

    /**
     * @param strFunction
     *            the strFunction to set
     */
    public void setStrFunction(final String strFunction) {
        this.strFunction = strFunction;
    }

    /**
     * @return the strRule1
     */
    public String getStrRule1() {
        return strRule1;
    }

    /**
     * @param strRule1
     *            the strRule1 to set
     */
    public void setStrRule1(final String strRule1) {
        this.strRule1 = strRule1;
    }

    /**
     * @return the strRule1Type
     */
    public String getStrRule1Type() {
        return strRule1Type;
    }

    /**
     * @param strRule1Type
     *            the strRule1Type to set
     */
    public void setStrRule1Type(final String strRule1Type) {
        this.strRule1Type = strRule1Type;
    }

    /**
     * @return the strRule2
     */
    public String getStrRule2() {
        return strRule2;
    }

    /**
     * @param strRule2
     *            the strRule2 to set
     */
    public void setStrRule2(final String strRule2) {
        this.strRule2 = strRule2;
    }

    /**
     * @return the strRule2Type
     */
    public String getStrRule2Type() {
        return strRule2Type;
    }

    /**
     * @param strRule2Type
     *            the strRule2Type to set
     */
    public void setStrRule2Type(final String strRule2Type) {
        this.strRule2Type = strRule2Type;
    }

    /**
     * @return the strComplexRuleId
     */
    public String getStrComplexRuleId() {
        return strComplexRuleId;
    }

    /**
     * @param strComplexRuleId
     *            the strComplexRuleId to set
     */
    public void setStrComplexRuleId(final String strComplexRuleId) {
        this.strComplexRuleId = strComplexRuleId;
    }

    /**
     * @return the arlComplexRuleLinkVO
     */
    public ArrayList<ComplexRuleLinkServiceVO> getArlComplexRuleLinkVO() {
        return arlComplexRuleLinkVO;
    }

    /**
     * @param arlComplexRuleLinkVO
     *            the arlComplexRuleLinkVO to set
     */
    public void setArlComplexRuleLinkVO(ArrayList<ComplexRuleLinkServiceVO> arlComplexRuleLinkVO) {
        this.arlComplexRuleLinkVO = arlComplexRuleLinkVO;
    }

    /**
     * @return the bHR
     */
    public boolean isBHR() {
        return bHR;
    }

    /**
     * @param bhr
     *            the bHR to set
     */
    public void setBHR(final boolean bhr) {
        bHR = bhr;
    }

    /**
     * @return the bPT
     */
    public boolean isBPT() {
        return bPT;
    }

    /**
     * @param bpt
     *            the bPT to set
     */
    public void setBPT(final boolean bpt) {
        bPT = bpt;
    }

    /**
     * @return the bMIN
     */
    public boolean isBMIN() {
        return bMIN;
    }

    /**
     * @param bmin
     *            the bMIN to set
     */
    public void setBMIN(final boolean bmin) {
        bMIN = bmin;
    }

    /**
     * @return the strHR
     */
    public String getStrHR() {
        return strHR;
    }

    /**
     * @param strHR
     *            the strHR to set
     */
    public void setStrHR(final String strHR) {
        this.strHR = strHR;
    }

    /**
     * @return the strPT
     */
    public String getStrPT() {
        return strPT;
    }

    /**
     * @param strPT
     *            the strPT to set
     */
    public void setStrPT(final String strPT) {
        this.strPT = strPT;
    }

    /**
     * @return the strMIN
     */
    public String getStrMIN() {
        return strMIN;
    }

    /**
     * @param strMIN
     *            the strMIN to set
     */
    public void setStrMIN(final String strMIN) {
        this.strMIN = strMIN;
    }

    /**
     * @return the strTimeFlag
     */
    public String getStrTimeFlag() {
        return strTimeFlag;
    }

    /**
     * @param strTimeFlag
     *            the strTimeFlag to set
     */
    public void setStrTimeFlag(final String strTimeFlag) {
        this.strTimeFlag = strTimeFlag;
    }

    /**
     * @return the strFinalRule1Flag
     */
    public String getStrFinalRule1Flag() {
        return strFinalRule1Flag;
    }

    /**
     * @param strFinalRule1Flag
     *            the strFinalRule1Flag to set
     */
    public void setStrFinalRule1Flag(final String strFinalRule1Flag) {
        this.strFinalRule1Flag = strFinalRule1Flag;
    }

    /**
     * @return the strFinalRule2Flag
     */
    public String getStrFinalRule2Flag() {
        return strFinalRule2Flag;
    }

    /**
     * @param strFinalRule2Flag
     *            the strFinalRule2Flag to set
     */
    public void setStrFinalRule2Flag(final String strFinalRule2Flag) {
        this.strFinalRule2Flag = strFinalRule2Flag;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("csequenceID", csequenceID)
                .append("strTimeWindow", strTimeWindow).append("strFrequency", strFrequency)
                .append("strFunction", strFunction).append("strRule1", strRule1).append("strRule1Type", strRule1Type)
                .append("strRule2", strRule2).append("strRule2Type", strRule2Type).append("strHR", strHR)
                .append("strPT", strPT).append("strMIN", strMIN).append("strTimeFlag", strTimeFlag).append("bHR", bHR)
                .append("bPT", bPT).append("bMIN", bMIN).append("strComplexRuleId", strComplexRuleId)
                .append("arlComplexRuleLinkVO", arlComplexRuleLinkVO).append("strFinalRule1Flag", strFinalRule1Flag)
                .append("strFinalRule2Flag", strFinalRule2Flag).toString();
    }

    public String getStrPixels() {
        return strPixels;
    }

    public void setStrPixels(String strPixels) {
        this.strPixels = strPixels;
    }

    public String getStrRule1Pixels() {
        return strRule1Pixels;
    }

    public void setStrRule1Pixels(String strRule1Pixels) {
        this.strRule1Pixels = strRule1Pixels;
    }

    public String getStrRule2Pixels() {
        return strRule2Pixels;
    }

    public void setStrRule2Pixels(String strRule2Pixels) {
        this.strRule2Pixels = strRule2Pixels;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
