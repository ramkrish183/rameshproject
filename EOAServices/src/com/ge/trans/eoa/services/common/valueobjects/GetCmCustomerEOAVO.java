/**
 * ============================================================
 * File : GetCmCustomerHVO.java
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
 * Classification : GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.usertype.UserType;

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
public class GetCmCustomerEOAVO extends RMDAuditInfoVO implements RMDAuditableIntf, UserType, Serializable {

    private static final long serialVersionUID = -3017256384296400636L;
    private RMDAuditInfoVO auditInfo = new RMDAuditInfoVO();
    private Long getCmCustomerSeqId;
    private GetCmContactInfoEOAVO getCmContactInfo;
    private String customerId;
    private String name;
    private String type;
    private Long status;
    private Integer rowVersion;
    private Set getKmDpdCusts = new HashSet(0);
    private Set getCmGenericNotesLogs = new HashSet(0);
    private Set getAsstFleets = new HashSet(0);
    private Set getAsstAssets = new HashSet(0);
    private Set getAsstLocations = new HashSet(0);
    private Set getAsstGroups = new HashSet(0);

    public Long getGetCmCustomerSeqId() {
        return this.getCmCustomerSeqId;
    }

    public void setGetCmCustomerSeqId(Long getCmCustomerSeqId) {
        this.getCmCustomerSeqId = getCmCustomerSeqId;
    }

    public GetCmContactInfoEOAVO getGetCmContactInfo() {
        return this.getCmContactInfo;
    }

    public void setGetCmContactInfo(GetCmContactInfoEOAVO getCmContactInfo) {
        this.getCmContactInfo = getCmContactInfo;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Set getGetKmDpdCusts() {
        return this.getKmDpdCusts;
    }

    public void setGetKmDpdCusts(Set getKmDpdCusts) {
        this.getKmDpdCusts = getKmDpdCusts;
    }

    public Set getGetCmGenericNotesLogs() {
        return this.getCmGenericNotesLogs;
    }

    public void setGetCmGenericNotesLogs(Set getCmGenericNotesLogs) {
        this.getCmGenericNotesLogs = getCmGenericNotesLogs;
    }

    public Set getGetAsstFleets() {
        return this.getAsstFleets;
    }

    public void setGetAsstFleets(Set getAsstFleets) {
        this.getAsstFleets = getAsstFleets;
    }

    public Set getGetAsstAssets() {
        return this.getAsstAssets;
    }

    public void setGetAsstAssets(Set getAsstAssets) {
        this.getAsstAssets = getAsstAssets;
    }

    public Set getGetAsstLocations() {
        return this.getAsstLocations;
    }

    public void setGetAsstLocations(Set getAsstLocations) {
        this.getAsstLocations = getAsstLocations;
    }

    public Set getGetAsstGroups() {
        return this.getAsstGroups;
    }

    public void setGetAsstGroups(Set getAsstGroups) {
        this.getAsstGroups = getAsstGroups;
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
