package com.ge.trans.rmd.services.alert.valueobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertSubscriptionRequestType", propOrder = {
    "alertConfSeqId",
    "service",
    "customerId",
    "alertSubscribed",
    "regionid",
    "region",
    "fleetid",
    "fleet",
    "vehicleObjId",
    "asset",
    "emailFormat",
    "subscriptionModel",
    "status",
    "alertType",
    "userid",
    "emailId",
    "userSeqId",
    "customerAlertObjId", 
    "notifyAlertObjId",
    "subDivisionid",
    "subDivision",
    "shopid",
    "shop",
    "rxSubscribedAlerts",
    "rxAlertValue",
    "rxAlertId",
    "modelId",
    "model",
    "flag",
    "isModelPrivilege",
    "multiUsers",
    "configAlertModelVal",
    "isConfigAlertPrivilege",
    "isMultiSubscriptionPrivilege",
    "isAllCustomer",
    "userType",
    "customerList",
    "notificationType",
    "phoneNumber",
    "rxType"
})

@XmlRootElement
public class AlertSubscriptionRequestType implements Serializable {

	private String alertConfSeqId;
	private String service;
	private String customerId;
	private String alertSubscribed;
	private String regionid;
	private String region;
	private String fleetid;
	private String fleet;
	private String vehicleObjId;
	private String asset;
	private String emailFormat;
	private String subscriptionModel;
	private String status;
	private String alertType;
	private String userid;
	private String emailId;
	private String userSeqId;
	private String customerAlertObjId;
	private String notifyAlertObjId;
	private String shopid;
	private String shop;
	private String subDivisionid;
	private String subDivision;
	private String rxAlertValue;
	private String rxSubscribedAlerts;
	private String rxAlertId;
	private String modelId;
	private String model;
	private String flag;
	private boolean isModelPrivilege;
	private String multiUsers;
	private String configAlertModelVal;
	private boolean isConfigAlertPrivilege;
	private boolean isMultiSubscriptionPrivilege;
	private boolean isAllCustomer;
	private String userType;
	private String customerList;
	private String notificationType;
	private String phoneNumber;
	private String rxType;
	
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRxAlertId() {
		return rxAlertId;
	}
	public void setRxAlertId(String rxAlertId) {
		this.rxAlertId = rxAlertId;
	}
	public String getUserSeqId() {
		return userSeqId;
	}
	public void setUserSeqId(String userSeqId) {
		this.userSeqId = userSeqId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public String getFleetid() {
		return fleetid;
	}
	public void setFleetid(String fleetid) {
		this.fleetid = fleetid;
	}
	public String getVehicleObjId() {
		return vehicleObjId;
	}
	public void setVehicleObjId(String vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}
	public String getAlertConfSeqId() {
		return alertConfSeqId;
	}
	public String getRxType() {
		return rxType;
	}
	public void setRxType(String rxType) {
		this.rxType = rxType;
	}
	public void setAlertConfSeqId(String alertConfSeqId) {
		this.alertConfSeqId = alertConfSeqId;
	}
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAlertSubscribed() {
		return alertSubscribed;
	}
	public void setAlertSubscribed(String alertSubscribed) {
		this.alertSubscribed = alertSubscribed;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFleet() {
		return fleet;
	}
	public void setFleet(String fleet) {
		this.fleet = fleet;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public String getEmailFormat() {
		return emailFormat;
	}
	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}
	public String getSubscriptionModel() {
		return subscriptionModel;
	}
	public void setSubscriptionModel(String subscriptionModel) {
		this.subscriptionModel = subscriptionModel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomerAlertObjId() {
		return customerAlertObjId;
	}
	public void setCustomerAlertObjId(String customerAlertObjId) {
		this.customerAlertObjId = customerAlertObjId;
	}
	public String getNotifyAlertObjId() {
		return notifyAlertObjId;
	}
	public void setNotifyAlertObjId(String notifyAlertObjId) {
		this.notifyAlertObjId = notifyAlertObjId;
	}
	public String getShopid() {
		return shopid;
	}
	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getSubDivisionid() {
		return subDivisionid;
	}
	public void setSubDivisionid(String subDivisionid) {
		this.subDivisionid = subDivisionid;
	}
	public String getSubDivision() {
		return subDivision;
	}
	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}
	public String getRxAlertValue() {
		return rxAlertValue;
	}
	public void setRxAlertValue(String rxAlertValue) {
		this.rxAlertValue = rxAlertValue;
	}
	public String getRxSubscribedAlerts() {
		return rxSubscribedAlerts;
	}
	public void setRxSubscribedAlerts(String rxSubscribedAlerts) {
		this.rxSubscribedAlerts = rxSubscribedAlerts;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public boolean isModelPrivilege() {
		return isModelPrivilege;
	}
	public void setModelPrivilege(boolean isModelPrivilege) {
		this.isModelPrivilege = isModelPrivilege;
	}
	public String getMultiUsers() {
		return multiUsers;
	}
	public void setMultiUsers(String multiUsers) {
		this.multiUsers = multiUsers;
	}
	public String getConfigAlertModelVal() {
		return configAlertModelVal;
	}
	public void setConfigAlertModelVal(String configAlertModelVal) {
		this.configAlertModelVal = configAlertModelVal;
	}
	public boolean isConfigAlertPrivilege() {
		return isConfigAlertPrivilege;
	}
	public void setConfigAlertPrivilege(boolean isConfigAlertPrivilege) {
		this.isConfigAlertPrivilege = isConfigAlertPrivilege;
	}
	public boolean isMultiSubscriptionPrivilege() {
		return isMultiSubscriptionPrivilege;
	}
	public void setMultiSubscriptionPrivilege(boolean isMultiSubscriptionPrivilege) {
		this.isMultiSubscriptionPrivilege = isMultiSubscriptionPrivilege;
	}
	public boolean isAllCustomer() {
		return isAllCustomer;
	}
	public void setAllCustomer(boolean isAllCustomer) {
		this.isAllCustomer = isAllCustomer;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCustomerList() {
		return customerList;
	}
	public void setCustomerList(String customerList) {
		this.customerList = customerList;
	}

}
