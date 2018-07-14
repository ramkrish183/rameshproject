/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 502166888
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CONFIG_INCIDENT_DATA")
public class RCIMessageBodyVO {
	
	@XmlElement(name="FAULT_NUMBER")
	private String faultNumber;
	@XmlElement(name="TEMPLATE_NUMBER")
	private String templateNumber;
	@XmlElement(name="TEMPLATE_VERSION")
	private String templateVersion;
	@XmlElement(name="TEMPLATE_ACTION")
	private String templateAction;
	/**
	 * @return the faultNumber
	 */
	public final String getFaultNumber() {
		return faultNumber;
	}
	/**
	 * @param faultNumber the faultNumber to set
	 */
	public final void setFaultNumber(String faultNumber) {
		this.faultNumber = faultNumber;
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
	 * @return the templateAction
	 */
	public final String getTemplateAction() {
		return templateAction;
	}
	/**
	 * @param templateAction the templateAction to set
	 */
	public final void setTemplateAction(String templateAction) {
		this.templateAction = templateAction;
	}
	
	

}
