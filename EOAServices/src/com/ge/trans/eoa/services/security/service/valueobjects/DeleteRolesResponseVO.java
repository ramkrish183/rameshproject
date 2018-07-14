package com.ge.trans.eoa.services.security.service.valueobjects;

import java.util.List;

public class DeleteRolesResponseVO {
	
	private String userId;
	private String firstName;
	private String lastName;
	private List<String> userCustomers;
	private String userSeqId;
	
	public String getUserSeqId() {
		return userSeqId;
	}
	public void setUserSeqId(String userSeqId) {
		this.userSeqId = userSeqId;
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
	public List<String> getUserCustomers() {
		return userCustomers;
	}
	public void setUserCustomers(List<String> userCustomers) {
		this.userCustomers = userCustomers;
	}
	


}
