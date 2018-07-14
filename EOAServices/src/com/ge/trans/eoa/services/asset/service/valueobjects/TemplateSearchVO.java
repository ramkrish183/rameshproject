/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

/**
 * @author 502166888
 *
 */
public class TemplateSearchVO extends AssetSearchVO {

	private String templateName;
	private String configFile;
	private String templateVersion;
	private String templateNo;
	private String templateDesc;
	private String controllerConfig;
	private boolean isConfigInvolved;
	private boolean isVehiclesBasedSearch;
	private boolean isTemplateInvolved;
	private String templateStatus;
	

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * @return the configFile
	 */
	public String getConfigFile() {
		return configFile;
	}
	/**
	 * @param configFile the configFile to set
	 */
	public void setConfigFile(String configFile) {
		this.configFile = configFile;
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
	 * @return the templateNo
	 */
	public String getTemplateNo() {
		return templateNo;
	}
	/**
	 * @param templateNo the templateNo to set
	 */
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	/**
	 * @return the templateDesc
	 */
	public String getTemplateDesc() {
		return templateDesc;
	}
	/**
	 * @param templateDesc the templateDesc to set
	 */
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	/**
	 * @return the controllerConfig
	 */
	public String getControllerConfig() {
		return controllerConfig;
	}
	/**
	 * @param controllerConfig the controllerConfig to set
	 */
	public void setControllerConfig(String controllerConfig) {
		this.controllerConfig = controllerConfig;
	}
	
	/**
	 * @return the isConfigInvolved
	 */
	public final boolean isConfigInvolved() {
		return isConfigInvolved;
	}
	/**
	 * @param isConfigInvolved the isConfigInvolved to set
	 */
	public final void setConfigInvolved(boolean isConfigInvolved) {
		this.isConfigInvolved = isConfigInvolved;
	}
	/**
	 * @return the isVehiclesBasedSearch
	 */
	public final boolean isVehiclesBasedSearch() {
		return isVehiclesBasedSearch;
	}
	/**
	 * @param isVehiclesBasedSearch the isVehiclesBasedSearch to set
	 */
	public final void setVehiclesBasedSearch(boolean isVehiclesBasedSearch) {
		this.isVehiclesBasedSearch = isVehiclesBasedSearch;
	}
	/**
	 * @return the isTemplateInvolved
	 */
	public final boolean isTemplateInvolved() {
		return isTemplateInvolved;
	}
	/**
	 * @param isTemplateInvolved the isTemplateInvolved to set
	 */
	public final void setTemplateInvolved(boolean isTemplateInvolved) {
		this.isTemplateInvolved = isTemplateInvolved;
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
	
}
