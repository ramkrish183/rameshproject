package com.ge.trans.eoa.services.alert.service.valueobjects;

public class AlertMultiUsersVO {
    private String userId;
    private String firstName;
    private String lastName;
    private String userEmailId;
    private String userPhoneNumber;
    private String userCountryCode;
    
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
