package com.ge.trans.rmd.common.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userRequestType", propOrder = {
    "userId",
    "userPhoneNo",
    "userPhoneCountryCode",
    "arrUserRequestType"
})

@XmlRootElement
public class UserRequestType implements Serializable {

	@XmlElement(required = true)
	private String userId;
	@XmlElement(required = true)
	private String userPhoneNo;
	@XmlElement(required = true)
	private String userPhoneCountryCode;
	
	protected List<UserRequestType> arrUserRequestType;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public List<UserRequestType> getarrUserRequestType() {
        if (arrUserRequestType == null) {
        	arrUserRequestType = new ArrayList<UserRequestType>();
        }
        return this.arrUserRequestType;
    }
	
}
