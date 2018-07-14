package com.ge.trans.rmd.services.assets.configmaintenance.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.assets.valueobjects.AssetsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultCodeDetailsResponseType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "templateReportRequestType", propOrder = { "templateName", "templateDescription", "templateVersion",
        "templateNumber", "templateStatus", "configType","controllerName","configObjid","controllerObjid","templateFileName","templateFileContent","faultCode","userName" })

@XmlRootElement
public class TemplateRequestType extends AssetsRequestType{
	
	
	@XmlElement(required = true)
	protected String templateName;
	@XmlElement(required = true)
	protected String templateDescription;
	@XmlElement(required = true)
	protected String templateVersion;
	@XmlElement(required = true)
	protected String templateNumber;
	@XmlElement(required = true)
	protected String templateStatus;
	@XmlElement(required = true)
	protected String configType;
	@XmlElement(required = true)
	protected String controllerName;
	@XmlElement
	protected String configObjid;
	@XmlElement
	protected String controllerObjid;	
	@XmlElement
	protected String templateFileName;
	@XmlElement
	protected String templateFileContent;
	@XmlElement
	protected String faultCode;
	@XmlElement
    protected String userName;
	
	
	/**
	 * @return the templateName
	 */
	public final String getTemplateName() {
		return templateName;
	}
	/**
	 * @param templateName the templateName to set
	 */
	public final void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * @return the templateDescription
	 */
	public final String getTemplateDescription() {
		return templateDescription;
	}
	/**
	 * @param templateDescription the templateDescription to set
	 */
	public final void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	/**
	 * @return the templateVersion
	 */
	public final String getTemplateVersion() {
		return templateVersion;
	}
	/**
	 * @param templateVersion the templateVersion to set
	 */
	public final void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	/**
	 * @return the templateNumber
	 */
	public final String getTemplateNumber() {
		return templateNumber;
	}
	/**
	 * @param templateNumber the templateNumber to set
	 */
	public final void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	/**
	 * @return the templateStatus
	 */
	public final String getTemplateStatus() {
		return templateStatus;
	}
	/**
	 * @param templateStatus the templateStatus to set
	 */
	public final void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}
	
	/**
	 * @return the configType
	 */
	public final String getConfigType() {
		return configType;
	}
	/**
	 * @param configType the configType to set
	 */
	public final void setConfigType(String configType) {
		this.configType = configType;
	}
	/**
	 * @return the controllerName
	 */
	public final String getControllerName() {
		return controllerName;
	}
	/**
	 * @param controllerName the controllerName to set
	 */
	public final void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	/**
	 * @return the configObjid
	 */
	public final String getConfigObjid() {
		return configObjid;
	}
	/**
	 * @param configObjid the configObjid to set
	 */
	public final void setConfigObjid(String configObjid) {
		this.configObjid = configObjid;
	}
	/**
	 * @return the controllerObjid
	 */
	public final String getControllerObjid() {
		return controllerObjid;
	}
	/**
	 * @param controllerObjid the controllerObjid to set
	 */
	public final void setControllerObjid(String controllerObjid) {
		this.controllerObjid = controllerObjid;
	}
	/**
	 * @return the templateFileName
	 */
	public String getTemplateFileName() {
		return templateFileName;
	}
	/**
	 * @param templateFileName the templateFileName to set
	 */
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
	/**
	 * @return the templateFileContent
	 */
	public String getTemplateFileContent() {
		return templateFileContent;
	}
	/**
	 * @param templateFileContent the templateFileContent to set
	 */
	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}
    public String getFaultCode() {
        return faultCode;
    }
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
	
	
	
}
