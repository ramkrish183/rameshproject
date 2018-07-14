package com.ge.trans.rmd.services.alert.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertMultiUsersResponseType", propOrder = {
    "userId",
    "firstName",
    "lastName",
    "userEmailId",
    "userPhoneNumber",
    "userCountryCode"
})
@XmlRootElement
public class AlertMultiUsersResponseType 
{

	protected String userId;
	protected String firstName;
	protected String lastName;
	protected String userEmailId;
	protected String userPhoneNumber;
	protected String userCountryCode;
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserCountryCode() {
		return userCountryCode;
	}
	public void setUserCountryCode(String userCountryCode) {
		this.userCountryCode = userCountryCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
	
}
