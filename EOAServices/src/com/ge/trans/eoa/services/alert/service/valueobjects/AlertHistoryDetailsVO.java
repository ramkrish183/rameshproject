package com.ge.trans.eoa.services.alert.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AlertHistoryDetailsVO extends BaseVO
{
	private static final long serialVersionUID = -6822925024022429265L;
	private String strCustomer;
	private String strType;
	private String strAlert;
	private String strRxFilter;
	private String strRule;
	private String strRegion;
	private String strSubDivision;
	private String strFleet;
	private String strAsset;
	private String strShop;
	private String strModel;
	private String strNumberOfDays;
	private String strMultiUsers;
	private String strFromDate;
	private String strToDate;
	private String strSearchValue;
	private Date strAlertDate;
	private String strPdfUrl;
	private String strMailDate;
	private String strGPSLog;
	private String strGPSLat;
	private String strAlertTitle;
	private String strRuleLogic;
	private String strDtTrigPoint;
	private boolean isMultiSubscriptionPrivilege;
	private String userType;
	private String userId;
	private String emailText;
	private String firingId;
	private String firedOn;
	private int pageNo;
	private int recordsPerPage;
	private int startRow;
	private String strMethodFlag;
	private String mobileNo;
	private String smsText;
	private String notification;
	private String isSmsFlag;
	private String isEmailFlag;
	public String getStrCustomer() {
		return strCustomer;
	}
	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}
	public String getStrAlert() {
		return strAlert;
	}
	public void setStrAlert(String strAlert) {
		this.strAlert = strAlert;
	}
	public String getStrRxFilter() {
		return strRxFilter;
	}
	public void setStrRxFilter(String strRxFilter) {
		this.strRxFilter = strRxFilter;
	}
	public String getStrRule() {
		return strRule;
	}
	public void setStrRule(String strRule) {
		this.strRule = strRule;
	}
	public String getStrRegion() {
		return strRegion;
	}
	public void setStrRegion(String strRegion) {
		this.strRegion = strRegion;
	}
	public String getStrSubDivision() {
		return strSubDivision;
	}
	public void setStrSubDivision(String strSubDivision) {
		this.strSubDivision = strSubDivision;
	}
	public String getStrFleet() {
		return strFleet;
	}
	public void setStrFleet(String strFleet) {
		this.strFleet = strFleet;
	}
	public String getStrAsset() {
		return strAsset;
	}
	public void setStrAsset(String strAsset) {
		this.strAsset = strAsset;
	}
	public String getStrShop() {
		return strShop;
	}
	public void setStrShop(String strShop) {
		this.strShop = strShop;
	}
	public String getStrModel() {
		return strModel;
	}
	public void setStrModel(String strModel) {
		this.strModel = strModel;
	}
	public String getStrNumberOfDays() {
		return strNumberOfDays;
	}
	public void setStrNumberOfDays(String strNumberOfDays) {
		this.strNumberOfDays = strNumberOfDays;
	}
	public String getStrMultiUsers() {
		return strMultiUsers;
	}
	public void setStrMultiUsers(String strMultiUsers) {
		this.strMultiUsers = strMultiUsers;
	}
	public String getStrFromDate() {
		return strFromDate;
	}
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}
	public String getStrToDate() {
		return strToDate;
	}
	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}
	public String getStrSearchValue() {
		return strSearchValue;
	}
	public void setStrSearchValue(String strSearchValue) {
		this.strSearchValue = strSearchValue;
	}
	public String getStrPdfUrl() {
		return strPdfUrl;
	}
	public void setStrPdfUrl(String strPdfUrl) {
		this.strPdfUrl = strPdfUrl;
	}
	public String getStrMailDate() {
		return strMailDate;
	}
	public void setStrMailDate(String strMailDate) {
		this.strMailDate = strMailDate;
	}
	public String getStrGPSLog() {
		return strGPSLog;
	}
	public void setStrGPSLog(String strGPSLog) {
		this.strGPSLog = strGPSLog;
	}
	public String getStrGPSLat() {
		return strGPSLat;
	}
	public void setStrGPSLat(String strGPSLat) {
		this.strGPSLat = strGPSLat;
	}
	public String getStrAlertTitle() {
		return strAlertTitle;
	}
	public void setStrAlertTitle(String strAlertTitle) {
		this.strAlertTitle = strAlertTitle;
	}
	public String getStrRuleLogic() {
		return strRuleLogic;
	}
	public void setStrRuleLogic(String strRuleLogic) {
		this.strRuleLogic = strRuleLogic;
	}
	public String getStrDtTrigPoint() {
		return strDtTrigPoint;
	}
	public void setStrDtTrigPoint(String strDtTrigPoint) {
		this.strDtTrigPoint = strDtTrigPoint;
	}
	public boolean isMultiSubscriptionPrivilege() {
		return isMultiSubscriptionPrivilege;
	}
	public void setMultiSubscriptionPrivilege(boolean isMultiSubscriptionPrivilege) {
		this.isMultiSubscriptionPrivilege = isMultiSubscriptionPrivilege;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getStrAlertDate() {
		return strAlertDate;
	}
	public void setStrAlertDate(Date strAlertDate) {
		this.strAlertDate = strAlertDate;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFiringId() {
		return firingId;
	}
	public void setFiringId(String firingId) {
		this.firingId = firingId;
	}
	public String getFiredOn() {
		return firedOn;
	}
	public void setFiredOn(String firedOn) {
		this.firedOn = firedOn;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public String getStrMethodFlag() {
		return strMethodFlag;
	}
	public void setStrMethodFlag(String strMethodFlag) {
		this.strMethodFlag = strMethodFlag;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getIsSmsFlag() {
		return isSmsFlag;
	}
	public void setIsSmsFlag(String isSmsFlag) {
		this.isSmsFlag = isSmsFlag;
	}
	public String getIsEmailFlag() {
		return isEmailFlag;
	}
	public void setIsEmailFlag(String isEmailFlag) {
		this.isEmailFlag = isEmailFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
