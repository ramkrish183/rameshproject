package com.ge.trans.eoa.services.asset.service.valueobjects;

public class VerifyCfgTemplateVO {

	private String objid;
	private String template;
	private String version;
	private String title;
	private String cfgType;
	private String customer;
	private String cfgFile;
	private String status;
	private String faultCode;
	private String fileName;
	private String fileContent;
	
	
	public String getStatus() {
		return status;
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

    public void setStatus(String status) {
		this.status = status;
	}

	public String getCfgFile() {
		return cfgFile;
	}

	public void setCfgFile(String cfgFile) {
		this.cfgFile = cfgFile;
	}

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

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
	
}
