/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetCmContactInfoHVO.java
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

import java.io.Serializable;

import org.hibernate.usertype.UserType;

//import com.ge.trans.rmd.caseapi.services.valueobjects.GetAsstLocationVO;
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
public class GetCmContactInfoEOAVO extends RMDAuditInfoVO implements RMDAuditableIntf, UserType, Serializable {

    static final long serialVersionUID = 635654132L;
    private RMDAuditInfoVO auditInfo = new RMDAuditInfoVO();
    private GetAsstLocationEOAVO getAsstLocation;
    private Long getCmContactInfoSeqId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String title;
    private String phone;
    private String eMail;
    private String dialComm;
    private String cellPhone;
    private String homePhone;
    private String language;
    private Integer rowVersion;
    private Long status;

    @Override
    public RMDAuditInfoVO getAuditInfo() {
        return auditInfo;
    }

    /*
     * public GetAsstLocationVO getGetAsstLocation() { return getAsstLocation; }
     * public void setGetAsstLocation(final GetAsstLocationVO getAsstLocation) {
     * this.getAsstLocation = getAsstLocation; }
     */

    public Long getGetCmContactInfoSeqId() {
        return getCmContactInfoSeqId;
    }

    public void setGetCmContactInfoSeqId(final Long getCmContactInfoSeqId) {
        this.getCmContactInfoSeqId = getCmContactInfoSeqId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(final String mail) {
        eMail = mail;
    }

    public String getDialComm() {
        return dialComm;
    }

    public void setDialComm(final String dialComm) {
        this.dialComm = dialComm;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(final String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(final String homePhone) {
        this.homePhone = homePhone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public Integer getRowVersion() {
        return rowVersion;
    }

    @Override
    public void setRowVersion(final Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    public void setAuditInfo(RMDAuditInfoVO auditInfo) {
        this.auditInfo = auditInfo;
    }

    public GetAsstLocationEOAVO getGetAsstLocation() {
        return getAsstLocation;
    }

    public void setGetAsstLocation(GetAsstLocationEOAVO getAsstLocation) {
        this.getAsstLocation = getAsstLocation;
    }
}