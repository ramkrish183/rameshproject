/**
 * ============================================================
 * File : RuleHistoryServiceVO.java
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

import java.util.Date;

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
public class RuleHistoryServiceVO extends BaseVO {
    static final long serialVersionUID = 29546853L;
    private String strVersionNumber;
	private String strDateCreated;
    private String strCreatedBy;
    private String strRevisionHistory;
    private String strRuleID;
    private String strOriginalID;
    private String active;
    private String status;

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(final String active) {
        this.active = active;
    }

    /**
     * @param strCreatedBy
     * @param strDateCreated
     * @param strOriginalID
     * @param strRevisionHistory
     * @param strRuleID
     * @param strVersionNumber
     */
	public RuleHistoryServiceVO(final String strCreatedBy, final String strDateCreated,
			final String strOriginalID,final  String strRevisionHistory, final String strRuleID,
			final String strVersionNumber) {
        super();
        this.strVersionNumber = strVersionNumber;
        this.strCreatedBy = strCreatedBy;
        this.strDateCreated = strDateCreated;
        this.strOriginalID = strOriginalID;
        this.strRevisionHistory = strRevisionHistory;
        this.strRuleID = strRuleID;
    }

    /**
     *
     */
    public RuleHistoryServiceVO() {
    }

    /**
     * @return the strVersionNumber
     */
    public String getStrVersionNumber() {
        return strVersionNumber;
    }

    /**
     * @param strVersionNumber
     *            the strVersionNumber to set
     */
    public void setStrVersionNumber(final String strVersionNumber) {
        this.strVersionNumber = strVersionNumber;
    }

    /**
     * @return the strDateCreated
     */
	public String getStrDateCreated() {
        return strDateCreated;
    }

    /**
     * @param strDateCreated
     *            the strDateCreated to set
     */
	public void setStrDateCreated(final String strDateCreated) {
        this.strDateCreated = strDateCreated;
    }

    /**
     * @return the strCreatedBy
     */
    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    /**
     * @param strCreatedBy
     *            the strCreatedBy to set
     */
    public void setStrCreatedBy(final String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    /**
     * @return the strRevisionHistory
     */
    public String getStrRevisionHistory() {
        return strRevisionHistory;
    }

    /**
     * @param strRevisionHistory
     *            the strRevisionHistory to set
     */
    public void setStrRevisionHistory(final String strRevisionHistory) {
        this.strRevisionHistory = strRevisionHistory;
    }

    /**
     * @return the strRuleID
     */
    public String getStrRuleID() {
        return strRuleID;
    }

    /**
     * @param strRuleID
     *            the strRuleID to set
     */
    public void setStrRuleID(final String strRuleID) {
        this.strRuleID = strRuleID;
    }

    /**
     * @return the strOriginalID
     */
    public String getStrOriginalID() {
        return strOriginalID;
    }

    /**
     * @param strOriginalID
     *            the strOriginalID to set
     */
    public void setStrOriginalID(final String strOriginalID) {
        this.strOriginalID = strOriginalID;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strVersionNumber", strVersionNumber)
                .append("strDateCreated", strDateCreated).append("strCreatedBy", strCreatedBy)
                .append("strRevisionHistory", strRevisionHistory).append("strRuleID", strRuleID)
                .append("strOriginalID", strOriginalID).append("active", active).toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
