package com.ge.trans.eoa.services.admin.service.valueobjects;

public class UserEOADetailsVO {

	private String userId;
	private String firstName;
	private String lastName;
	private String aliasName;
	private String emailId;
	private String eoaMLVal;
	private boolean omdCMPrevFlag;
	private boolean omdMLPrevFlag;
	private boolean eoaCMPrevFlag;
	private boolean eoaMLPrevFlag;
	private boolean blnOMDCmMlPreRemoved;
	private boolean blnOMDMlAloneRemoved;
	private String eoaEmetricsVal;
	private boolean eoaEmetricsPrevFlag;
	private boolean blnOMDEmetricsAloneRemoved;
	private boolean omdEmetricsPrevFlag;
	private boolean blnOMDCmMlEmetricsPreRemoved;
	private String errorMsg;
	private String endUserScoring;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isOmdEmetricsPrevFlag() {
		return omdEmetricsPrevFlag;
	}
	public void setOmdEmetricsPrevFlag(boolean omdEmetricsPrevFlag) {
		this.omdEmetricsPrevFlag = omdEmetricsPrevFlag;
	}
	
	public boolean isBlnOMDCmMlEmetricsPreRemoved() {
		return blnOMDCmMlEmetricsPreRemoved;
	}
	public void setBlnOMDCmMlEmetricsPreRemoved(boolean blnOMDCmMlEmetricsPreRemoved) {
		this.blnOMDCmMlEmetricsPreRemoved = blnOMDCmMlEmetricsPreRemoved;
	}
	public boolean isBlnOMDEmetricsAloneRemoved() {
		return blnOMDEmetricsAloneRemoved;
	}
	public void setBlnOMDEmetricsAloneRemoved(boolean blnOMDEmetricsAloneRemoved) {
		this.blnOMDEmetricsAloneRemoved = blnOMDEmetricsAloneRemoved;
	}
	public String getEoaEmetricsVal() {
		return eoaEmetricsVal;
	}
	public void setEoaEmetricsVal(String eoaEmetricsVal) {
		this.eoaEmetricsVal = eoaEmetricsVal;
	}
	public boolean isEoaEmetricsPrevFlag() {
		return eoaEmetricsPrevFlag;
	}
	public void setEoaEmetricsPrevFlag(boolean eoaEmetricsPrevFlag) {
		this.eoaEmetricsPrevFlag = eoaEmetricsPrevFlag;
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
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEoaMLVal() {
		return eoaMLVal;
	}
	public void setEoaMLVal(String eoaMLVal) {
		this.eoaMLVal = eoaMLVal;
	}
	public boolean isOmdCMPrevFlag() {
		return omdCMPrevFlag;
	}
	public void setOmdCMPrevFlag(boolean omdCMPrevFlag) {
		this.omdCMPrevFlag = omdCMPrevFlag;
	}
	public boolean isOmdMLPrevFlag() {
		return omdMLPrevFlag;
	}
	public void setOmdMLPrevFlag(boolean omdMLPrevFlag) {
		this.omdMLPrevFlag = omdMLPrevFlag;
	}
	public boolean isEoaCMPrevFlag() {
		return eoaCMPrevFlag;
	}
	public void setEoaCMPrevFlag(boolean eoaCMPrevFlag) {
		this.eoaCMPrevFlag = eoaCMPrevFlag;
	}
	public boolean isEoaMLPrevFlag() {
		return eoaMLPrevFlag;
	}
	public void setEoaMLPrevFlag(boolean eoaMLPrevFlag) {
		this.eoaMLPrevFlag = eoaMLPrevFlag;
	}
	public boolean isBlnOMDCmMlPreRemoved() {
		return blnOMDCmMlPreRemoved;
	}
	public void setBlnOMDCmMlPreRemoved(boolean blnOMDCmMlPreRemoved) {
		this.blnOMDCmMlPreRemoved = blnOMDCmMlPreRemoved;
	}
	public boolean isBlnOMDMlAloneRemoved() {
		return blnOMDMlAloneRemoved;
	}
	public void setBlnOMDMlAloneRemoved(boolean blnOMDMlAloneRemoved) {
		this.blnOMDMlAloneRemoved = blnOMDMlAloneRemoved;
	}
	public String getEndUserScoring() {
		return endUserScoring;
	}
	public void setEndUserScoring(String endUserScoring) {
		this.endUserScoring = endUserScoring;
	}
	
	
}
