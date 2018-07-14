package com.ge.trans.rmd.services.assets.valueobjects;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configRequestType", propOrder = {
		"objId",
		"templateId",
		"versionId",
		"configId",
		"configValue",
		"arlConfigRequestType"
})

@XmlRootElement
public class ConfigRequestType {
	
	@XmlElement(required = true)
	protected String objId;
	@XmlElement(required = true)
	protected String templateId;
	@XmlElement(required = true)
	protected String versionId;
	@XmlElement(required = true)
	protected String configId;
	@XmlElement(required = true)
	protected String configValue;
	@XmlElement(required = true)
	protected List<ConfigRequestType> arlConfigRequestType;
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public List<ConfigRequestType> getArlConfigRequestType() {
		return arlConfigRequestType;
	}
	public void setArlConfigRequestType(List<ConfigRequestType> arlConfigRequestType) {
		this.arlConfigRequestType = arlConfigRequestType;
	}
	
	
	
	
}
