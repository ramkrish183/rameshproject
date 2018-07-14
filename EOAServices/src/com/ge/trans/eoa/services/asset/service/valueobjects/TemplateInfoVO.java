package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;

public class TemplateInfoVO extends AssetServiceVO{

	String vehicleObjId;
	String applyType;
	String roadNumber;
	String roadNumberHeader;
	String sitePartObjid;
	String msgDefObjidApply;
	String msgDefObjidDelete ;
	String Template;
	String cfgFile ;
	String version;
	String cfgTemplateObjid;
	String userName ;
	String services;
	String msgPriority;
	String partStatus;
	String templateDescription;
	String templateStatus;
	String offboardStatus;
	String onboardStatus;
	String onboardStatusDate;
	String offboardStatusDate;
	private FaultCodeVO faultCodeVO;
	private String templateFileName;
	private String templateFileContent;
	private String controllerConfig;
	private String faultCode;
	

	public String getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	public String getVehicleObjId() {
		return vehicleObjId;
	}
	public void setVehicleObjId(String vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getRoadNumberHeader() {
		return roadNumberHeader;
	}
	public void setRoadNumberHeader(String roadNumberHeader) {
		this.roadNumberHeader = roadNumberHeader;
	}
	public String getSitePartObjid() {
		return sitePartObjid;
	}
	public void setSitePartObjid(String sitePartObjid) {
		this.sitePartObjid = sitePartObjid;
	}
	public String getMsgDefObjidApply() {
		return msgDefObjidApply;
	}
	public void setMsgDefObjidApply(String msgDefObjidApply) {
		this.msgDefObjidApply = msgDefObjidApply;
	}
	public String getMsgDefObjidDelete() {
		return msgDefObjidDelete;
	}
	public void setMsgDefObjidDelete(String msgDefObjidDelete) {
		this.msgDefObjidDelete = msgDefObjidDelete;
	}
	public String getTemplate() {
		return Template;
	}
	public void setTemplate(String template) {
		Template = template;
	}
	public String getCfgFile() {
		return cfgFile;
	}
	public void setCfgFile(String cfgFile) {
		this.cfgFile = cfgFile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCfgTemplateObjid() {
		return cfgTemplateObjid;
	}
	public void setCfgTemplateObjid(String cfgTemplateObjid) {
		this.cfgTemplateObjid = cfgTemplateObjid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getMsgPriority() {
		return msgPriority;
	}
	public void setMsgPriority(String msgPriority) {
		this.msgPriority = msgPriority;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	/**
	 * @return the templateStatus
	 */
	public String getTemplateStatus() {
		return templateStatus;
	}
	/**
	 * @param templateStatus the templateStatus to set
	 */
	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
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
	 * @return the onboardStatus
	 */
	public String getOnboardStatus() {
		return onboardStatus;
	}
	/**
	 * @param onboardStatus the onboardStatus to set
	 */
	public void setOnboardStatus(String onboardStatus) {
		this.onboardStatus = onboardStatus;
	}
	/**
	 * @return the onboardStatusDate
	 */
	public final String getOnboardStatusDate() {
		return onboardStatusDate;
	}
	/**
	 * @param onboardStatusDate the onboardStatusDate to set
	 */
	public final void setOnboardStatusDate(String onboardStatusDate) {
		this.onboardStatusDate = onboardStatusDate;
	}
	/**
	 * @return the offboardStatusDate
	 */
	public final String getOffboardStatusDate() {
		return offboardStatusDate;
	}
	/**
	 * @param offboardStatusDate the offboardStatusDate to set
	 */
	public final void setOffboardStatusDate(String offboardStatusDate) {
		this.offboardStatusDate = offboardStatusDate;
	}
	public FaultCodeVO getFaultCodeVO() {
		return faultCodeVO;
	}
	public void setFaultCodeVO(FaultCodeVO faultCodeVO) {
		this.faultCodeVO = faultCodeVO;
	}
	public String getTemplateFileName() {
		return templateFileName;
	}
	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}
	public String getTemplateFileContent() {
		return templateFileContent;
	}
	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}
	public String getControllerConfig() {
		return controllerConfig;
	}
	public void setControllerConfig(String controllerConfig) {
		this.controllerConfig = controllerConfig;
	}
    public String getFaultCode() {
        return faultCode;
    }
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
	
	
}
