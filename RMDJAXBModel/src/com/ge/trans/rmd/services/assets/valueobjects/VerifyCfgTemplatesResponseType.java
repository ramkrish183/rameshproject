package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyCfgTemplatesResponseType", propOrder = {
		"objid",
		"cfgFile",
		"template",
		"version",
		"title",
		"cfgType",
		"status",
		"customer",
		"faultCode",
		"fileName",
		"fileContent"
})

@XmlRootElement
public class VerifyCfgTemplatesResponseType {
	
	@XmlElement(required = true)
	protected String objid;
	@XmlElement(required = true)
	protected String template;
	@XmlElement(required = true)
	protected String version;
	@XmlElement(required = true)
	protected String title;
	@XmlElement(required = true)
	protected String cfgType;
	@XmlElement(required = true)
	protected String customer;

	@XmlElement(required = true)
	protected String cfgFile;
	@XmlElement(required = true)
    protected String faultCode;
	@XmlElement(required = true)
    protected String fileName;
    @XmlElement(required = true)
    protected String fileContent;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@XmlElement(required = true)
	protected String status;
	
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCfgType() {
		return cfgType;
	}
	public void setCfgType(String cfgType) {
		this.cfgType = cfgType;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCfgFile() {
		return cfgFile;
	}
	public void setCfgFile(String cfgFile) {
		this.cfgFile = cfgFile;
	}
    public String getFaultCode() {
        return faultCode;
    }
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileContent() {
        return fileContent;
    }
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
	
	
}
