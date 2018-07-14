/**
 * ============================================================
 * File : ReportBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.reports.service.valueobjects
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
package com.ge.trans.eoa.services.reports.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 9, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleMeasSearResServiceVO extends BaseVO {
    static final long serialVersionUID = 6192873L;
    private String strRuleDef;
    private String strRuleTitle;
    private String strFamily;
    private String strActive;
    private String strAccurate;
    private String strRxAttach;
    private Date strActivationDt;
    private String strFiredTimes;
    private String strAcurateTimes;
    private String strMissTimes;
    private Long strRuleId;

    public RuleMeasSearResServiceVO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the strRuleDef
     */
    public String getStrRuleDef() {
        return strRuleDef;
    }

    /**
     * @param strRuleDef
     *            the strRuleDef to set
     */
    public void setStrRuleDef(String strRuleDef) {
        this.strRuleDef = strRuleDef;
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
    public void setStrRuleTitle(String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
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
    public void setStrFamily(String strFamily) {
        this.strFamily = strFamily;
    }

    /**
     * @return the strActive
     */
    public String getStrActive() {
        return strActive;
    }

    /**
     * @param strActive
     *            the strActive to set
     */
    public void setStrActive(String strActive) {
        this.strActive = strActive;
    }

    /**
     * @return the strAccurate
     */
    public String getStrAccurate() {
        return strAccurate;
    }

    /**
     * @param strAccurate
     *            the strAccurate to set
     */
    public void setStrAccurate(String strAccurate) {
        this.strAccurate = strAccurate;
    }

    /**
     * @return the strRxAttach
     */
    public String getStrRxAttach() {
        return strRxAttach;
    }

    /**
     * @param strRxAttach
     *            the strRxAttach to set
     */
    public void setStrRxAttach(String strRxAttach) {
        this.strRxAttach = strRxAttach;
    }

    /**
     * @return the strRuleId
     */
    public Long getStrRuleId() {
        return strRuleId;
    }

    /**
     * @param strRuleId
     *            the strRuleId to set
     */
    public void setStrRuleId(Long strRuleId) {
        this.strRuleId = strRuleId;
    }

    /**
     * @return the strActivationDt
     */
    public Date getStrActivationDt() {
        return strActivationDt;
    }

    /**
     * @param strActivationDt
     *            the strActivationDt to set
     */
    public void setStrActivationDt(Date strActivationDt) {
        this.strActivationDt = strActivationDt;
    }

    /**
     * @return the strFiredTimes
     */
    public String getStrFiredTimes() {
        return strFiredTimes;
    }

    /**
     * @param strFiredTimes
     *            the strFiredTimes to set
     */
    public void setStrFiredTimes(String strFiredTimes) {
        this.strFiredTimes = strFiredTimes;
    }

    /**
     * @return the strAcurateTimes
     */
    public String getStrAcurateTimes() {
        return strAcurateTimes;
    }

    /**
     * @param strAcurateTimes
     *            the strAcurateTimes to set
     */
    public void setStrAcurateTimes(String strAcurateTimes) {
        this.strAcurateTimes = strAcurateTimes;
    }

    /**
     * @return the strMissTimes
     */
    public String getStrMissTimes() {
        return strMissTimes;
    }

    /**
     * @param strMissTimes
     *            the strMissTimes to set
     */
    public void setStrMissTimes(String strMissTimes) {
        this.strMissTimes = strMissTimes;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRuleDef", strRuleDef)
                .append("strRuleTitle", strRuleTitle).append("strFamily", strFamily).append("strActive", strActive)
                .append("strAccurate", strAccurate).append("strRxAttach", strRxAttach)
                .append("strActivationDt", strActivationDt).append("strFiredTimes", strFiredTimes)
                .append("strAcurateTimes", strAcurateTimes).append("strMissTimes", strMissTimes)
                .append("strRuleId", strRuleId).toString();
    }

}
