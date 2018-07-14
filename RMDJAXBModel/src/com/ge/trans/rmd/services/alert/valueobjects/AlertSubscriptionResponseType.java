package com.ge.trans.rmd.services.alert.valueobjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertSubscriptionResponseType", propOrder = {
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
    "shopid",
    "shop",
    "subDivisionid",
    "subDivision",
    "Value",
    "rxSubscribedAlerts",
    "rxAlertId",
    "modelId",
    "model",
    "ruleTitle",
    "active",
    "originalId",
    "ruleModel",
    "firstName",
    "lastName",
    "userEmail",
    "notificationType",
    "phoneNumber",
    "rxType"
})
@XmlRootElement
public class AlertSubscriptionResponseType implements Serializable {
	
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
	private String Value;
	private String rxSubscribedAlerts;
	private String rxAlertId;
	private String modelId;
	private String model;
	private String ruleTitle;
	private String active;
	private String originalId;
	private String ruleModel;
	private String firstName;
	private String lastName;
	private String userEmail;
	private String notificationType;
	private String phoneNumber;
	private String rxType;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
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
	public String getRxType() {
		return rxType;
	}
	public void setRxType(String rxType) {
		this.rxType = rxType;
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
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
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
	public String getRuleTitle() {
		return ruleTitle;
	}
	public void setRuleTitle(String ruleTitle) {
		this.ruleTitle = ruleTitle;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
	public String getRuleModel() {
		return ruleModel;
	}
	public void setRuleModel(String ruleModel) {
		this.ruleModel = ruleModel;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
