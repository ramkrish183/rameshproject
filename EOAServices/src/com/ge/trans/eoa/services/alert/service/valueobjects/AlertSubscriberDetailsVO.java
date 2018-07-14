package com.ge.trans.eoa.services.alert.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/**
 * @author Igate
 */
@SuppressWarnings("serial")
public class AlertSubscriberDetailsVO extends BaseVO {

 
	//Outputs
    private String sso;
    private String notificationType;
    private String emailId;
    private String phoneNumber;
    private String measurementSystem;
    private String userTimezone;
    
    //Inputs 
    private String vehicleHeader;
    private String vehicleNumber;
    private String latitude;
    private String longitude; 
    private String region;
    private String subRegion;
    private String alertId; 
    private String alertType; 
    
    
     
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSubRegion() {
		return subRegion;
	}
	public void setSubRegion(String subRegion) {
		this.subRegion = subRegion;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	/**
	 * @return the vehicleHeader
	 */
	public String getVehicleHeader() {
		return vehicleHeader;
	}
	/**
	 * @param vehicleHeader the vehicleHeader to set
	 */
	public void setVehicleHeader(String vehicleHeader) {
		this.vehicleHeader = vehicleHeader;
	}
	/**
	 * @return the vehicleNumber
	 */
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	/**
	 * @param vehicleNumber the vehicleNumber to set
	 */
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the alertId
	 */
	public String getAlertId() {
		return alertId;
	}
	/**
	 * @param alertId the alertId to set
	 */
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	/**
	 * @return the sso
	 */
	public String getSso() {
		return sso;
	}
	/**
	 * @param sso the sso to set
	 */
	public void setSso(String sso) {
		this.sso = sso;
	}
	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}
	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the measurementSystem
	 */
	public String getMeasurementSystem() {
		return measurementSystem;
	}
	/**
	 * @param measurementSystem the measurementSystem to set
	 */
	public void setMeasurementSystem(String measurementSystem) {
		this.measurementSystem = measurementSystem;
	}
	/**
	 * @return the userTimezone
	 */
	public String getUserTimezone() {
		return userTimezone;
	}
	/**
	 * @param userTimezone the userTimezone to set
	 */
	public void setUserTimezone(String userTimezone) {
		this.userTimezone = userTimezone;
	}
	

   
}