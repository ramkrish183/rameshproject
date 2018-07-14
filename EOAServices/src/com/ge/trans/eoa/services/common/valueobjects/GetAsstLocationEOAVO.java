/**
 * ============================================================
 * File : GetAsstLocationVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification : GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.usertype.UserType;

import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.common.valueobjects.RMDAuditInfoVO;
import com.ge.trans.rmd.utilities.RMDAuditableIntf;

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
public class GetAsstLocationEOAVO extends RMDAuditInfoVO implements RMDAuditableIntf, UserType, Serializable {

    private static final long serialVersionUID = -461506425164591974L;
    private RMDAuditInfoVO auditInfo = new RMDAuditInfoVO();
    private Long getAsstLocationSeqId;
    private GetCmCustomerEOAVO getCmCustomer;
    private GetSysLookupVO getSysLookup;
    private GetCmContactInfoEOAVO getCmContactInfo;
    private String locationId;
    private String name;
    private Long status;
    private Integer rowVersion;
    private Set getAsstAssets = new HashSet(0);

    public Long getGetAsstLocationSeqId() {
        return this.getAsstLocationSeqId;
    }

    public void setGetAsstLocationSeqId(Long getAsstLocationSeqId) {
        this.getAsstLocationSeqId = getAsstLocationSeqId;
    }

    public GetCmCustomerEOAVO getGetCmCustomer() {
        return this.getCmCustomer;
    }

    public void setGetCmCustomer(GetCmCustomerEOAVO getCmCustomer) {
        this.getCmCustomer = getCmCustomer;
    }

    public GetSysLookupVO getGetSysLookup() {
        return this.getSysLookup;
    }

    public void setGetSysLookup(GetSysLookupVO getSysLookup) {
        this.getSysLookup = getSysLookup;
    }

    public GetCmContactInfoEOAVO getGetCmContactInfo() {
        return this.getCmContactInfo;
    }

    public void setGetCmContactInfo(GetCmContactInfoEOAVO getCmContactInfo) {
        this.getCmContactInfo = getCmContactInfo;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public Integer getRowVersion() {
        return this.rowVersion;
    }

    @Override
    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Set getGetAsstAssets() {
        return this.getAsstAssets;
    }

    public void setGetAsstAssets(Set getAsstAssets) {
        this.getAsstAssets = getAsstAssets;
    }

    /**
     * @return the auditInfo
     */
    @Override
    public RMDAuditInfoVO getAuditInfo() {
        return auditInfo;
    }

    /**
     * @param auditInfo
     *            the auditInfo to set
     */
    public void setAuditInfo(RMDAuditInfoVO auditInfo) {
        this.auditInfo = auditInfo;
    }
}
