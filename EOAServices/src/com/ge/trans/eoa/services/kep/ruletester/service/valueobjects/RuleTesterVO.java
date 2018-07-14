package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/**
 * ============================================================
 * File : RuleTesterVO.java
 * Description : 
 * 
 * Package :com.ge.trans.rmd.services.kep.ruletester.service.valueobjects
 * Author : iGATE Patni
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.util.Date;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the value objects for Rule tester VO
 * @History :
 ******************************************************************************/
public class RuleTesterVO {
    private static final long serialVersionUID = 1L;
    private String ruleId;
    private String trackingId;
    private String ruleType;
    private String ruleTitle;
    private String createdBy;
    private String status;
    private Date fromDate;
    private Date dtTo;
    private boolean blnDefaultLoad;

    public boolean isBlnDefaultLoad() {
        return blnDefaultLoad;
    }

    public void setBlnDefaultLoad(boolean blnDefaultLoad) {
        this.blnDefaultLoad = blnDefaultLoad;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(final String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(final String ruleType) {
        this.ruleType = ruleType;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(final String trackingId) {
        this.trackingId = trackingId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(final String ruleId) {
        this.ruleId = ruleId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getDtTo() {
        return dtTo;
    }

    public void setDtTo(final Date dtTo) {
        this.dtTo = dtTo;
    }
}
