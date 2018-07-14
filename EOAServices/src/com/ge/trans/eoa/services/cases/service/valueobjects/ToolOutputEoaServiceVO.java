/**
 * ============================================================
 * Classification: GE Confidential
 * File : ToolOutputServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 2, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ToolOutputEoaServiceVO extends BaseVO {

    static final long serialVersionUID = 96587423L;
    private String strRecomId;
    private String strRecomTitle;
    private String strRecomProb;
    private String strRecomStatus;
    private String strRecomDelv;
    private String strUrgency;
    private String strEstRepairTime;
    private String strUrgRepairValue;
    private String strEstRepairTimeValue;
    private String strRecomStatusValue;
    private String strRuleDefId;
    private String strTitle;
    private String strRuleId;
    private Date dtLastRunDt;
    private Date dtRxDelvDate;
    private String strFalseAlarmPct;
    private String strToolCovgPct;
    private String strMdscPerfPct;
    private String strFalseAlarmColor;
    private String strToolCovgColor;
    private String strMdscPerfColor;
    private String strFalseAlarmInd;
    private String strToolCovgInd;
    private String strMdscPerfInd;
    private String strToolId;
    private boolean isFCFault = true;
    private String strToolIdDes;
    private String rxRevision;
    private String faultCode;
    private String toolObjId;
    private String finRuleId;
	private String strViz;
	private boolean isPlotVizPresent=false;
	
	public boolean isPlotVizPresent() {
		return isPlotVizPresent;
	}

	public void setPlotVizPresent(boolean isPlotVizPresent) {
		this.isPlotVizPresent = isPlotVizPresent;
	}

	public String getStrViz() {
		return strViz;
	}

	public void setStrViz(String strViz) {
		this.strViz = strViz;
	}
    public String getRxRevision() {
        return rxRevision;
    }

    public void setRxRevision(String rxRevision) {
        this.rxRevision = rxRevision;
    }

    /**
     * @return the strRecomId
     */
    public String getStrRecomId() {
        return strRecomId;
    }

    /**
     * @param strRecomId
     *            the strRecomId to set
     */
    public void setStrRecomId(String strRecomId) {
        this.strRecomId = strRecomId;
    }

    /**
     * @return the strRecomTitle
     */
    public String getStrRecomTitle() {
        return strRecomTitle;
    }

    /**
     * @param strRecomTitle
     *            the strRecomTitle to set
     */
    public void setStrRecomTitle(String strRecomTitle) {
        this.strRecomTitle = strRecomTitle;
    }

    /**
     * @return the strRecomProb
     */
    public String getStrRecomProb() {
        return strRecomProb;
    }

    /**
     * @param strRecomProb
     *            the strRecomProb to set
     */
    public void setStrRecomProb(String strRecomProb) {
        this.strRecomProb = strRecomProb;
    }

    /**
     * @return the strRecomStatus
     */
    public String getStrRecomStatus() {
        return strRecomStatus;
    }

    /**
     * @param strRecomStatus
     *            the strRecomStatus to set
     */
    public void setStrRecomStatus(String strRecomStatus) {
        this.strRecomStatus = strRecomStatus;
    }

    /**
     * @return the strRecomDelv
     */
    public String getStrRecomDelv() {
        return strRecomDelv;
    }

    /**
     * @param strRecomDelv
     *            the strRecomDelv to set
     */
    public void setStrRecomDelv(String strRecomDelv) {
        this.strRecomDelv = strRecomDelv;
    }

    /**
     * @return the strUrgency
     */
    public String getStrUrgency() {
        return strUrgency;
    }

    /**
     * @param strUrgency
     *            the strUrgency to set
     */
    public void setStrUrgency(String strUrgency) {
        this.strUrgency = strUrgency;
    }

    /**
     * @return the strEstRepairTime
     */
    public String getStrEstRepairTime() {
        return strEstRepairTime;
    }

    /**
     * @param strEstRepairTime
     *            the strEstRepairTime to set
     */
    public void setStrEstRepairTime(String strEstRepairTime) {
        this.strEstRepairTime = strEstRepairTime;
    }

    /**
     * @return the strRuleDefId
     */
    public String getStrRuleDefId() {
        return strRuleDefId;
    }

    /**
     * @param strRuleDefId
     *            the strRuleDefId to set
     */
    public void setStrRuleDefId(String strRuleDefId) {
        this.strRuleDefId = strRuleDefId;
    }

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle
     *            the strTitle to set
     */
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * @return the strRuleId
     */
    public String getStrRuleId() {
        return strRuleId;
    }

    /**
     * @param strRuleId
     *            the strRuleId to set
     */
    public void setStrRuleId(String strRuleId) {
        this.strRuleId = strRuleId;
    }

    /**
     * @return the strUrgRepairValue
     */
    public String getStrUrgRepairValue() {
        return strUrgRepairValue;
    }

    /**
     * @param strUrgRepairValue
     *            the strUrgRepairValue to set
     */
    public void setStrUrgRepairValue(String strUrgRepairValue) {
        this.strUrgRepairValue = strUrgRepairValue;
    }

    /**
     * @return the strEstRepairTimeValue
     */
    public String getStrEstRepairTimeValue() {
        return strEstRepairTimeValue;
    }

    /**
     * @param strEstRepairTimeValue
     *            the strEstRepairTimeValue to set
     */
    public void setStrEstRepairTimeValue(String strEstRepairTimeValue) {
        this.strEstRepairTimeValue = strEstRepairTimeValue;
    }

    /**
     * @return the strRecomStatusValue
     */
    public String getStrRecomStatusValue() {
        return strRecomStatusValue;
    }

    /**
     * @param strRecomStatusValue
     *            the strRecomStatusValue to set
     */
    public void setStrRecomStatusValue(String strRecomStatusValue) {
        this.strRecomStatusValue = strRecomStatusValue;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRecomId", strRecomId)
                .append("strRecomTitle", strRecomTitle).append("strRecomProb", strRecomProb)
                .append("strRecomStatus", strRecomStatus).append("strRecomDelv", strRecomDelv)
                .append("strUrgency", strUrgency).append("strEstRepairTime", strEstRepairTime)
                .append("strRuleDefId", strRuleDefId).append("strTitle", strTitle).append("strRuleId", strRuleId)
                .toString();
    }

    /**
     * @return the dtLastRunDt
     */
    public Date getDtLastRunDt() {
        return dtLastRunDt;
    }

    /**
     * @param dtLastRunDt
     *            the dtLastRunDt to set
     */
    public void setDtLastRunDt(Date dtLastRunDt) {
        this.dtLastRunDt = dtLastRunDt;
    }

    /**
     * @return the dtRxDelvDate
     */
    public Date getDtRxDelvDate() {
        return dtRxDelvDate;
    }

    /**
     * @param dtRxDelvDate
     *            the dtRxDelvDate to set
     */
    public void setDtRxDelvDate(Date dtRxDelvDate) {
        this.dtRxDelvDate = dtRxDelvDate;
    }

    public String getStrFalseAlarmPct() {
        return strFalseAlarmPct;
    }

    public void setStrFalseAlarmPct(String strFalseAlarmPct) {
        this.strFalseAlarmPct = strFalseAlarmPct;
    }

    public String getStrToolCovgPct() {
        return strToolCovgPct;
    }

    public void setStrToolCovgPct(String strToolCovgPct) {
        this.strToolCovgPct = strToolCovgPct;
    }

    public String getStrMdscPerfPct() {
        return strMdscPerfPct;
    }

    public void setStrMdscPerfPct(String strMdscPerfPct) {
        this.strMdscPerfPct = strMdscPerfPct;
    }

    /**
     * @return the strFalseAlarmColor
     */
    public String getStrFalseAlarmColor() {
        return strFalseAlarmColor;
    }

    /**
     * @param strFalseAlarmColor
     *            the strFalseAlarmColor to set
     */
    public void setStrFalseAlarmColor(String strFalseAlarmColor) {
        this.strFalseAlarmColor = strFalseAlarmColor;
    }

    /**
     * @return the strToolCovgColor
     */
    public String getStrToolCovgColor() {
        return strToolCovgColor;
    }

    /**
     * @param strToolCovgColor
     *            the strToolCovgColor to set
     */
    public void setStrToolCovgColor(String strToolCovgColor) {
        this.strToolCovgColor = strToolCovgColor;
    }

    /**
     * @return the strMdscPerfColor
     */
    public String getStrMdscPerfColor() {
        return strMdscPerfColor;
    }

    /**
     * @param strMdscPerfColor
     *            the strMdscPerfColor to set
     */
    public void setStrMdscPerfColor(String strMdscPerfColor) {
        this.strMdscPerfColor = strMdscPerfColor;
    }

    public String getStrFalseAlarmInd() {
        return strFalseAlarmInd;
    }

    public void setStrFalseAlarmInd(String strFalseAlarmInd) {
        this.strFalseAlarmInd = strFalseAlarmInd;
    }

    public String getStrToolCovgInd() {
        return strToolCovgInd;
    }

    public void setStrToolCovgInd(String strToolCovgInd) {
        this.strToolCovgInd = strToolCovgInd;
    }

    public String getStrMdscPerfInd() {
        return strMdscPerfInd;
    }

    public void setStrMdscPerfInd(String strMdscPerfInd) {
        this.strMdscPerfInd = strMdscPerfInd;
    }

    /**
     * @return the strToolId
     */
    public String getStrToolId() {
        return strToolId;
    }

    /**
     * @param strToolId
     *            the strToolId to set
     */
    public void setStrToolId(String strToolId) {
        this.strToolId = strToolId;
    }

    /**
     * @return the isFCFault
     */
    public boolean isFCFault() {
        return isFCFault;
    }

    /**
     * @param isFCFault
     *            the isFCFault to set
     */
    public void setFCFault(boolean isFCFault) {
        this.isFCFault = isFCFault;
    }

    /**
     * @return the toolIdDes
     */
    public String getStrToolIdDes() {
        return strToolIdDes;
    }

    /**
     * @param toolIdDes
     *            the toolIdDes to set
     */
    public void setStrToolIdDes(String strToolIdDes) {
        this.strToolIdDes = strToolIdDes;
    }

    /**
     * @return the faultCode
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * @param faultCode
     *            the faultCode to set
     */
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
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

    public String getFinRuleId() {
        return finRuleId;
    }

    public void setFinRuleId(String finRuleId) {
        this.finRuleId = finRuleId;
    }

}
