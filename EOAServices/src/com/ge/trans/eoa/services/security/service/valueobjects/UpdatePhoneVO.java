package com.ge.trans.eoa.services.security.service.valueobjects;

public class UpdatePhoneVO {

	public String userId;
	public String userPhoneNo;
	public String userPhoneCountryCode;

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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
		
}

