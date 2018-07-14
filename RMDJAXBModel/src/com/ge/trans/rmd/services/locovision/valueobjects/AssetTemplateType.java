/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MSHIRAJUDDIN
 *
 */
@XmlRootElement
public class AssetTemplateType {
	
	private String obsoleteFlag; 
	private String templateType;
	private String templateNumber;
	private String templateVersion;
	private String installationDate;
	private String offboardStatus;
	private String offboardStatusDate;
	private String templateRemove;
	private String templateReapply;
	private String strTemplateID;
	private String strTemplateKey;
	private String title;
	private String isActive;
	
	
	/**
	 * @return the obsoleteFlag
	 */
	public String getObsoleteFlag() {
		return obsoleteFlag;
	}
	/**
	 * @param obsoleteFlag the obsoleteFlag to set
	 */
	public void setObsoleteFlag(String obsoleteFlag) {
		this.obsoleteFlag = obsoleteFlag;
	}
	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}
	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	/**
	 * @return the templateNumber
	 */
	public String getTemplateNumber() {
		return templateNumber;
	}
	/**
	 * @param templateNumber the templateNumber to set
	 */
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	/**
	 * @return the templateVersion
	 */
	public String getTemplateVersion() {
		return templateVersion;
	}
	/**
	 * @param templateVersion the templateVersion to set
	 */
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	/**
	 * @return the installationDate
	 */
	public String getInstallationDate() {
		return installationDate;
	}
	/**
	 * @param installationDate the installationDate to set
	 */
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	/**
	 * @return the offboardStatus
	 */
	public String getOffboardStatus() {
		return offboardStatus;
	}
	/**
	 * @param offboardStatus the offboardStatus to set
	 */
	public void setOffboardStatus(String offboardStatus) {
		this.offboardStatus = offboardStatus;
	}
	/**
	 * @return the offboardStatusDate
	 */
	public String getOffboardStatusDate() {
		return offboardStatusDate;
	}
	/**
	 * @param offboardStatusDate the offboardStatusDate to set
	 */
	public void setOffboardStatusDate(String offboardStatusDate) {
		this.offboardStatusDate = offboardStatusDate;
	}
	/**
	 * @return the templateRemove
	 */
	public String getTemplateRemove() {
		return templateRemove;
	}
	/**
	 * @param templateRemove the templateRemove to set
	 */
	public void setTemplateRemove(String templateRemove) {
		this.templateRemove = templateRemove;
	}
	/**
	 * @return the templateReapply
	 */
	public String getTemplateReapply() {
		return templateReapply;
	}
	/**
	 * @param templateReapply the templateReapply to set
	 */
	public void setTemplateReapply(String templateReapply) {
		this.templateReapply = templateReapply;
	}
	/**
	 * @return the strTemplateID
	 */
	public String getStrTemplateID() {
		return strTemplateID;
	}
	/**
	 * @param strTemplateID the strTemplateID to set
	 */
	public void setStrTemplateID(String strTemplateID) {
		this.strTemplateID = strTemplateID;
	}
	/**
	 * @return the strTemplateKey
	 */
	public String getStrTemplateKey() {
		return strTemplateKey;
	}
	/**
	 * @param strTemplateKey the strTemplateKey to set
	 */
	public void setStrTemplateKey(String strTemplateKey) {
		this.strTemplateKey = strTemplateKey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
