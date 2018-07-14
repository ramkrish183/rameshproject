package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "templateResponseType", propOrder = { "ldvrTemplates"})*/

@XmlRootElement
public class LDVRSaveTemplateResponseType {
	
	private String status;	
	private String templateNumber;
	private String templateVersion;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String templateObjid;
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	/**
	 * @return the lastUpdatedDate
	 */
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getTemplateObjid() {
		return templateObjid;
	}
	public void setTemplateObjid(String templateObjid) {
		this.templateObjid = templateObjid;
	}

	
	
}
