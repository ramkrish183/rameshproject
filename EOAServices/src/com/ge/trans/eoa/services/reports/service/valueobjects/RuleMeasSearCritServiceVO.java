/**
 * ============================================================
 * File : RuleMeasSearCritServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.reports.service.valueobjectss
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
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleMeasSearCritServiceVO extends BaseVO {

    static final long serialVersionUID = 99441188L;
    private Date dtStartDate;
    private Date dtEndDate;
    private boolean bRuleFired;
    private boolean bActiveRules;
    private String strRules;
    private String strRuleId;
    private String strRuleDefnId;

    public RuleMeasSearCritServiceVO() {
        super();
    }

    /**
     * @return the dtStartDate
     */
    public Date getDtStartDate() {
        return dtStartDate;
    }

    /**
     * @param dtStartDate
     *            the dtStartDate to set
     */
    public void setDtStartDate(Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }

    /**
     * @return the dtEndDate
     */
    public Date getDtEndDate() {
        return dtEndDate;
    }

    /**
     * @param dtEndDate
     *            the dtEndDate to set
     */
    public void setDtEndDate(Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }

    /**
     * @return the bRuleFired
     */
    public boolean isBRuleFired() {
        return bRuleFired;
    }

    /**
     * @param ruleFired
     *            the bRuleFired to set
     */
    public void setBRuleFired(boolean ruleFired) {
        bRuleFired = ruleFired;
    }

    /**
     * @return the bActiveRules
     */
    public boolean isBActiveRules() {
        return bActiveRules;
    }

    /**
     * @param activeRules
     *            the bActiveRules to set
     */
    public void setBActiveRules(boolean activeRules) {
        bActiveRules = activeRules;
    }

    /**
     * @return the strRules
     */
    public String getStrRules() {
        return strRules;
    }

    /**
     * @param strRules
     *            the strRules to set
     */
    public void setStrRules(String strRules) {
        this.strRules = strRules;
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
     * @return the strRuleDefnId
     */
    public String getStrRuleDefnId() {
        return strRuleDefnId;
    }

    /**
     * @param strRuleDefnId
     *            the strRuleDefnId to set
     */
    public void setStrRuleDefnId(String strRuleDefnId) {
        this.strRuleDefnId = strRuleDefnId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("dtStartDate", dtStartDate)
                .append("dtEndDate", dtEndDate).append("strRuleFired", bRuleFired)
                .append("strActiveRules", bActiveRules).append("strRules", strRules).append("strRuleId", strRuleId)
                .append("strRuleDefnId", strRuleDefnId).toString();
    }

}