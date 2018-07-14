package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/**
 * ============================================================
 * File : TesterVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.tester.service.valueobjects
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
import java.io.Serializable;
import java.util.Date;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This Class consists of the value objects like
 *              RuleId,TrackingId,
 *              ruleType,ruleTitle,createdBy,status,fromDate,dtTo
 * @History :
 ******************************************************************************/
public class TesterVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ruleId;
    private String trackingId;
    private String ruleType;
    private String ruleTitle;
    private String createdBy;
    private String status;
    private Date fromDate;
    private Date dtTo;

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
