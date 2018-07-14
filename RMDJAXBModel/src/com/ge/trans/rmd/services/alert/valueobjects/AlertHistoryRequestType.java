package com.ge.trans.rmd.services.alert.valueobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertHistoryRequestType", propOrder = {
    "strCustomer",
    "strType",
    "strAlert",
    "strRxFilter",
    "strRule",
	"strRegion",
	"strSubDivision",
	"strFleet",
	"strAsset",
	"strShop",
	"strModel",
	"strNumberOfDays",
	"strMultiUsers",
	"strFromDate",
	"strToDate",
	"strSearchValue",
	"strAlertDate",
	"strPdfUrl",
	"strMailDate",
	"strGPSLog",
	"strGPSLat",
	"strAlertTitle",
	"strRuleLogic",
	"strDtTrigPoint",
	"isMultiSubscriptionPrivilege",
	"userType",
	"userId",
	"pageNo",
	"recordsPerPage",
	"startRow",
	"strMethodFlag"
})

@XmlRootElement
public class AlertHistoryRequestType implements Serializable
{
	private static final long serialVersionUID = 5159288206283860205L;
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
	private String strAlertDate;
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
	private String pageNo;
	private String recordsPerPage;
	private String startRow;
	private String strMethodFlag;
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
	public String getStrAlertDate() {
		return strAlertDate;
	}
	public void setStrAlertDate(String strAlertDate) {
		this.strAlertDate = strAlertDate;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(String recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getStartRow() {
		return startRow;
	}
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}
	public String getStrMethodFlag() {
		return strMethodFlag;
	}
	public void setStrMethodFlag(String strMethodFlag) {
		this.strMethodFlag = strMethodFlag;
	}
	
	
}
