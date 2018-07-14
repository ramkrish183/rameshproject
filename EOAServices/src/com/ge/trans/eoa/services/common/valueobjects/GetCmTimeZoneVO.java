/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetCmTimeZoneHVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.util.Set;

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
@SuppressWarnings("unchecked")
public class GetCmTimeZoneVO extends BaseVO {

    static final long serialVersionUID = 635654132L;

    private Long getCmTimeZoneSeqId;
    private String name;
    private Long gmtDiff;
    private Integer rowVersion;
    private Set getCmContactInfos;
    private String timeZoneId;
    private String fullName;
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public GetCmTimeZoneVO() {
    }

    /**
     * @return the timeZoneId
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * @param timeZoneId
     *            the timeZoneId to set
     */
    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getGetCmTimeZoneSeqId() {
        return this.getCmTimeZoneSeqId;
    }

    public void setGetCmTimeZoneSeqId(Long getCmTimeZoneSeqId) {
        this.getCmTimeZoneSeqId = getCmTimeZoneSeqId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGmtDiff() {
        return this.gmtDiff;
    }

    public void setGmtDiff(Long gmtDiff) {
        this.gmtDiff = gmtDiff;
    }

    @Override
    public Integer getRowVersion() {
        return this.rowVersion;
    }

    @Override
    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Set getGetCmContactInfos() {
        return this.getCmContactInfos;
    }

    public void setGetCmContactInfos(Set getCmContactInfos) {
        this.getCmContactInfos = getCmContactInfos;
    }

}