/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetUsrUsersHVO.java
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

import java.util.Date;
import java.util.List;
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
public class GetUsrUsersEOAVO extends BaseVO {

    /** serialVersionUID of Type long **/
    static final long serialVersionUID = 635654132L;

    /** getUsrUsersSeqId of Type Long **/
    private Long getUsrUsersSeqId;
    /** getCmContactInfo of Type GetCmContactInfoVO **/
    private GetCmContactInfoEOAVO getCmContactInfo;
    /** userId of Type String **/
    private String userId;
    /** password of Type String **/
    private String password;
    /** homePage of Type String **/
    private String homePage;
    /** status of Type Long **/
    private Long status;
    /** passwordChgDate of Type Date **/
    private Date passwordChgDate;
    /** lastLoginDate of Type Date **/
    private Date lastLoginDate;
    /** rowVersion of Type Integer **/
    private Integer rowVersion;
    /** getCmCasesForLinkUsers of Type Set **/
    private Set getCmCasesForLinkUsers;
    /** getCmCasesForLinkCreatorUsers of Type Set **/
    private Set getCmCasesForLinkCreatorUsers;
    /** getUsrUserRoles of Type Set **/
    private List<RolesEOAVO> getUsrUserRoles;
    /** customerId of type string **/
    private String customerId;
    /** customerName of type string **/
    private String customerName;

    /**
     * @Author:
     * @param getCustomerName
     * @Description:
     */

    public String getCustomerName() {
        return customerName;
    }

    /**
     * @Author:
     * @param setCustomerName
     * @Description:
     */

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    /**
     * @Author:
     * @param getCustomerId
     * @Description:
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @Author:
     * @param setCustomerId
     * @Description:
     */
    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Long getGetUsrUsersSeqId() {
        return this.getUsrUsersSeqId;
    }

    /**
     * @Author:
     * @param getUsrUsersSeqId
     * @Description:
     */
    public void setGetUsrUsersSeqId(final Long getUsrUsersSeqId) {
        this.getUsrUsersSeqId = getUsrUsersSeqId;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public GetCmContactInfoEOAVO getGetCmContactInfo() {
        return this.getCmContactInfo;
    }

    /**
     * @Author:
     * @param getCmContactInfo
     * @Description:
     */
    public void setGetCmContactInfo(final GetCmContactInfoEOAVO getCmContactInfo) {
        this.getCmContactInfo = getCmContactInfo;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @Author:
     * @param userId
     * @Description:
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @Author:
     * @param password
     * @Description:
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Long getStatus() {
        return this.status;
    }

    /**
     * @Author:
     * @param status
     * @Description:
     */
    public void setStatus(final Long status) {
        this.status = status;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getHomePage() {
        return homePage;
    }

    /**
     * @Author:
     * @param homePage
     * @Description:
     */
    public void setHomePage(final String homePage) {
        this.homePage = homePage;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Date getPasswordChgDate() {
        return this.passwordChgDate;
    }

    /**
     * @Author:
     * @param passwordChgDate
     * @Description:
     */
    public void setPasswordChgDate(final Date passwordChgDate) {
        this.passwordChgDate = passwordChgDate;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    /**
     * @Author:
     * @param lastLoginDate
     * @Description:
     */
    public void setLastLoginDate(final Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.common.valueobjects.BaseVO#getRowVersion()
     */
    @Override
    public Integer getRowVersion() {
        return this.rowVersion;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.common.valueobjects.BaseVO#setRowVersion(java.lang.
     * Integer )
     */
    @Override
    public void setRowVersion(final Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Set getGetCmCasesForLinkUsers() {
        return this.getCmCasesForLinkUsers;
    }

    /**
     * @Author:
     * @param getCmCasesForLinkUsers
     * @Description:
     */
    public void setGetCmCasesForLinkUsers(Set getCmCasesForLinkUsers) {
        this.getCmCasesForLinkUsers = getCmCasesForLinkUsers;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Set getGetCmCasesForLinkCreatorUsers() {
        return this.getCmCasesForLinkCreatorUsers;
    }

    /**
     * @Author:
     * @param getCmCasesForLinkCreatorUsers
     * @Description:
     */
    public void setGetCmCasesForLinkCreatorUsers(Set getCmCasesForLinkCreatorUsers) {
        this.getCmCasesForLinkCreatorUsers = getCmCasesForLinkCreatorUsers;
    }

    public List<RolesEOAVO> getGetUsrUserRoles() {
        return getUsrUserRoles;
    }

    public void setGetUsrUserRoles(List<RolesEOAVO> getUsrUserRoles) {
        this.getUsrUserRoles = getUsrUserRoles;
    }

}