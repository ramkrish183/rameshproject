package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userEOADetailsResponseType", propOrder = {
		"userId",
		"firstName",
		"lastName",
		"aliasName",
		"emailId",
		"eoaMLVal",
		"omdCMPrevFlag",
		"omdMLPrevFlag",
		"eoaCMPrevFlag",
		"eoaMLPrevFlag",
		"blnOMDCmMlPreRemoved",
		"blnOMDMlAloneRemoved",
		"eoaEmetricsVal",
		"eoaEmetricsPrevFlag",
	    "blnOMDEmetricsAloneRemoved",
		"omdEmetricsPrevFlag",
		"blnOMDCmMlEmetricsPreRemoved",
		"errorMsg",
		"endUserScoring"
})

@XmlRootElement
public class UserEOADetailsResponseType {

	
	
	@XmlElement(required = true)
	protected String userId;
	@XmlElement(required = true)
	protected String firstName;
	@XmlElement(required = true)
	protected String lastName;
	@XmlElement(required = true)
	protected String aliasName;
	@XmlElement(required = true)
	protected String emailId;
	@XmlElement(required = true)
	private String eoaMLVal;
	@XmlElement(required = true)
	private boolean omdCMPrevFlag;
	@XmlElement(required = true)
	private boolean omdMLPrevFlag;
	@XmlElement(required = true)
	private boolean eoaCMPrevFlag;
	@XmlElement(required = true)
	private boolean eoaMLPrevFlag;
	@XmlElement(required = true)
	private boolean blnOMDCmMlPreRemoved;
	@XmlElement(required = true)
	private boolean blnOMDMlAloneRemoved;
	@XmlElement(required = true)
	private String eoaEmetricsVal;
	@XmlElement(required = true)
	private boolean eoaEmetricsPrevFlag;
	@XmlElement(required = true)
	private boolean blnOMDEmetricsAloneRemoved;
	@XmlElement(required = true)
	private boolean omdEmetricsPrevFlag;
	@XmlElement(required = true)
	private boolean blnOMDCmMlEmetricsPreRemoved;
	@XmlElement(required = true)
	private String errorMsg;
	@XmlElement(required = true)
	private String endUserScoring;
	

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isBlnOMDCmMlEmetricsPreRemoved() {
		return blnOMDCmMlEmetricsPreRemoved;
	}
	public void setBlnOMDCmMlEmetricsPreRemoved(boolean blnOMDCmMlEmetricsPreRemoved) {
		this.blnOMDCmMlEmetricsPreRemoved = blnOMDCmMlEmetricsPreRemoved;
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
	public boolean isBlnOMDEmetricsAloneRemoved() {
		return blnOMDEmetricsAloneRemoved;
	}
	public void setBlnOMDEmetricsAloneRemoved(boolean blnOMDEmetricsAloneRemoved) {
		this.blnOMDEmetricsAloneRemoved = blnOMDEmetricsAloneRemoved;
	}
	public boolean isOmdEmetricsPrevFlag() {
		return omdEmetricsPrevFlag;
	}
	public void setOmdEmetricsPrevFlag(boolean omdEmetricsPrevFlag) {
		this.omdEmetricsPrevFlag = omdEmetricsPrevFlag;
	}
	public String getEndUserScoring() {
		return endUserScoring;
	}
	public void setEndUserScoring(String endUserScoring) {
		this.endUserScoring = endUserScoring;
	}
	
	
	
}
