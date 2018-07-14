/**
 * ============================================================
 * Classification: GE Confidential
 * File : UsersVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.security.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.service.valueobjects;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Contains user details
 * @History :
 * 
 ******************************************************************************/
public class UserServiceVO extends BaseVO {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6827314315603973412L;
	/** getUsrUsersSeqId of Type Long **/
	private Long getUsrUsersSeqId;
	/** getUsrContactSedId of Type Long **/
	private Long getUsrContactSedId;
	/** status of Type Long **/
	private Long status; 
	private Long usrRoleId;
	
	/** userId of Type String **/
	private String userId;
	/** homePage of Type String **/
	private String homePage;
	/** strPassword of Type String **/
	private String strPassword;
	/** strFirstName of Type String **/
	private String strFirstName;
	/** strMiddleName of Type String **/
	private String strLastName;
	/** strTimeZone of Type String **/
	private String strTimeZone;
	/** strEmail of Type String **/
	private String strEmail;
	/** strRole of Type String **/
	private String strRole;
	/** passwordChgDate of Type Date **/
	private Date passwordChgDate;
	/** lastLoginDate of Type Date **/
	private Date lastLoginDate;
	
	/** locale of Type Locale **/
	private Locale locale;
	
	private String customerId;
	private String defaultCustomer;
	private List roleList;
	
	private String rolesChangeFlag;
	
	private List<RolesVO> roles;
	
	private List<String> listCustomer;
	private String oldUserId;
	private String newUserIdEntered;
	private String userType;
    private String uom;
	/* Added for MDSC Admin changes */
	private String eoaCMFlag;
	private String eoaMLFlag;
	private String eoaAlias;
	private String eoaMLVal;
	private String updateEOAUser;
	private String omdCMFlag;
	private String omdMLFlag;
	private String omdCmMlPrevRemoved;
	private boolean eoaTableUpdated;
	private String omdMlAloneRemoved;
	/*ENd of MDSC Admin*/
	//newly added code
	private String lastUpdatedBy;
	//Added by Murali Medicherla for Rally Id : US226051
	private String mobileAccess;
	private String emetricsAccess;
	// Added by Sriram.B(212601214) for SMS feature
	private String userPhoneNo;
	private String userPhoneCountryCode;
	private String endUserScoring;
	private String eoaOnsiteUser;
	
	public String getUserPhoneNo() {
		return userPhoneNo;
	}

	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}

	public String getUserPhoneCountryCode() {
		return userPhoneCountryCode;
	}

	public void setUserPhoneCountryCode(String userPhoneCountryCode) {
		this.userPhoneCountryCode = userPhoneCountryCode;
	}
  
    public String getEmetricsAccess() {
        return emetricsAccess;
    }

    public void setEmetricsAccess(String emetricsAccess) {
        this.emetricsAccess = emetricsAccess;
    }
    
	
	public String getEoaEmetricsFlag() {
		return eoaEmetricsFlag;
	}

	public void setEoaEmetricsFlag(String eoaEmetricsFlag) {
		this.eoaEmetricsFlag = eoaEmetricsFlag;
	}

	public String getEoaEmetricsVal() {
		return eoaEmetricsVal;
	}

	public void setEoaEmetricsVal(String eoaEmetricsVal) {
		this.eoaEmetricsVal = eoaEmetricsVal;
	}

	public String getOmdEmetricsFlag() {
		return omdEmetricsFlag;
	}

	public void setOmdEmetricsFlag(String omdEmetricsFlag) {
		this.omdEmetricsFlag = omdEmetricsFlag;
	}

	public String getOmdEmetricsAloneRemoved() {
		return omdEmetricsAloneRemoved;
	}

	public void setOmdEmetricsAloneRemoved(String omdEmetricsAloneRemoved) {
		this.omdEmetricsAloneRemoved = omdEmetricsAloneRemoved;
	}

	private String lastUpdatedTime;
	//newly added code

	private String eoaEmetricsFlag;
	private String eoaEmetricsVal;
	private String omdEmetricsFlag;
	private String omdEmetricsAloneRemoved;
	private String errormsg;
	
	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	
	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUserType() {
		return userType;
	}

	public String getEoaOnsiteUser() {
		return eoaOnsiteUser;
	}

	public void setEoaOnsiteUser(String eoaOnsiteUser) {
		this.eoaOnsiteUser = eoaOnsiteUser;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNewUserIdEntered() {
		return newUserIdEntered;
	}

	public void setNewUserIdEntered(String newUserIdEntered) {
		this.newUserIdEntered = newUserIdEntered;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	
	public List<RolesVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesVO> roles) {
		this.roles = roles;
	}
	
	public String getRolesChangeFlag() {
		return rolesChangeFlag;
	}

	public void setRolesChangeFlag(String rolesChangeFlag) {
		this.rolesChangeFlag = rolesChangeFlag;
	}
	
	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public Date getPasswordChgDate() {
		return passwordChgDate;
	}
	
	/**
	 * @Author: 
	 * @param passwordChgDate
	 * @Description:
	*/
	public void setPasswordChgDate(Date passwordChgDate) {
		this.passwordChgDate = passwordChgDate;
	}
	
	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	/**
	 * @Author: 
	 * @param lastLoginDate
	 * @Description:
	*/
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * @Author: 
	 * @param locale
	 * @Description:
	*/
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public String getPassword() {
		return strPassword;
	}

	/**
	 * @Author: 
	 * @param strPassword
	 * @Description:
	*/
	public void setPassword(final String strPassword) {
		this.strPassword = strPassword;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public String getStrFirstName() {
		return strFirstName;
	}

	/**
	 * @Author: 
	 * @param strFirstName
	 * @Description:
	*/
	public void setStrFirstName(final String strFirstName) {
		this.strFirstName = strFirstName;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public String getStrLastName() {
		return strLastName;
	}

	/**
	 * @Author: 
	 * @param strLastName
	 * @Description:
	*/
	public void setStrLastName(final String strLastName) {
		this.strLastName = strLastName;
	}

	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public String getStrTimeZone() {
		return strTimeZone;
	}

	/**
	 * @Author: 
	 * @param strTimeZone
	 * @Description:
	*/
	public void setStrTimeZone(final String strTimeZone) {
		this.strTimeZone = strTimeZone;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, getToStringStyleObject()).append(
				"strUserID", userId).append("strPassword", strPassword)
				.append("strFirstName", strFirstName).append("strLastName", strLastName)
				.append("strTimeZone", strTimeZone).toString();
	}
	
	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public Long getGetUsrUsersSeqId() {
		return getUsrUsersSeqId;
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
	public Long getStatus() {
		return status;
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
	public String getStrEmail() {
		return strEmail;
	}
	
	/**
	 * @Author: 
	 * @param strEmail
	 * @Description:
	*/
	public void setStrEmail(final String strEmail) {
		this.strEmail = strEmail;
	}
	
	/**
	 * @Author: 
	 * @return
	 * @Description:
	*/
	public String getUserId() {
		return userId;
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
	public Long getGetUsrContactSedId() {
		return getUsrContactSedId;
	}

	/**
	 * @Author: 
	 * @param getUsrContactSedId
	 * @Description:
	*/
	public void setGetUsrContactSedId(final Long getUsrContactSedId) {
		this.getUsrContactSedId = getUsrContactSedId;
	}

	/**
	 * @return the strRole
	 */
	public String getStrRole() {
		return strRole;
	}

	/**
	 * @param strRole the strRole to set
	 */
	public void setStrRole(final String strRole) {
		this.strRole = strRole;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getUsrRoleId() {
		return usrRoleId;
	}

	public void setUsrRoleId(Long usrRoleId) {
		this.usrRoleId = usrRoleId;
	}

	public List<String> getListCustomer() {
		return listCustomer;
	}

	public void setListCustomer(List<String> listCustomer) {
		this.listCustomer = listCustomer;
	}

	public String getDefaultCustomer() {
		return defaultCustomer;
	}

	public void setDefaultCustomer(String defaultCustomer) {
		this.defaultCustomer = defaultCustomer;
	}

	public String getEoaCMFlag() {
		return eoaCMFlag;
	}

	public void setEoaCMFlag(String eoaCMFlag) {
		this.eoaCMFlag = eoaCMFlag;
	}

	public String getEoaMLFlag() {
		return eoaMLFlag;
	}

	public void setEoaMLFlag(String eoaMLFlag) {
		this.eoaMLFlag = eoaMLFlag;
	}

	public String getEoaAlias() {
		return eoaAlias;
	}

	public void setEoaAlias(String eoaAlias) {
		this.eoaAlias = eoaAlias;
	}

	public String getEoaMLVal() {
		return eoaMLVal;
	}

	public void setEoaMLVal(String eoaMLVal) {
		this.eoaMLVal = eoaMLVal;
	}

	public String getUpdateEOAUser() {
		return updateEOAUser;
	}

	public void setUpdateEOAUser(String updateEOAUser) {
		this.updateEOAUser = updateEOAUser;
	}

	public String getOmdCMFlag() {
		return omdCMFlag;
	}

	public void setOmdCMFlag(String omdCMFlag) {
		this.omdCMFlag = omdCMFlag;
	}

	public String getOmdMLFlag() {
		return omdMLFlag;
	}

	public void setOmdMLFlag(String omdMLFlag) {
		this.omdMLFlag = omdMLFlag;
	}

	public String getOmdCmMlPrevRemoved() {
		return omdCmMlPrevRemoved;
	}

	public void setOmdCmMlPrevRemoved(String omdCmMlPrevRemoved) {
		this.omdCmMlPrevRemoved = omdCmMlPrevRemoved;
	}

	public boolean isEoaTableUpdated() {
		return eoaTableUpdated;
	}

	public void setEoaTableUpdated(boolean eoaTableUpdated) {
		this.eoaTableUpdated = eoaTableUpdated;
	}

	public String getOmdMlAloneRemoved() {
		return omdMlAloneRemoved;
	}

	public void setOmdMlAloneRemoved(String omdMlAloneRemoved) {
		this.omdMlAloneRemoved = omdMlAloneRemoved;
	}

	public String getMobileAccess() {
		return mobileAccess;
	}

	public void setMobileAccess(String mobileAccess) {
		this.mobileAccess = mobileAccess;
	}

	public String getEndUserScoring() {
		return endUserScoring;
	}

	public void setEndUserScoring(String endUserScoring) {
		this.endUserScoring = endUserScoring;
	}

	
	
	
}