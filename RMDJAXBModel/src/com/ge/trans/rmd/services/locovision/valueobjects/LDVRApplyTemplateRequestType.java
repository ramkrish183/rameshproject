/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;


/**
 * @author MSHIRAJUDDIN
 *
 */
public class LDVRApplyTemplateRequestType {
	private List<Long> templateObjid;
	private List<AssetsType> assets;
	private String device;
	private String userId;
	private String messagePriority;

	/**
	 * @return the templateObjid
	 */
	public List<Long> getTemplateObjid() {
		return templateObjid;
	}
	/**
	 * @param templateObjid the templateObjid to set
	 */
	public void setTemplateObjid(List<Long> templateObjid) {
		this.templateObjid = templateObjid;
	}
	
	
	
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the messagePriority
	 */
	public String getMessagePriority() {
		return messagePriority;
	}
	/**
	 * @param messagePriority the messagePriority to set
	 */
	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}
	/**
	 * @return the assets
	 */
	public List<AssetsType> getAssets() {
		return assets;
	}
	/**
	 * @param assets the assets to set
	 */
	public void setAssets(List<AssetsType> assets) {
		this.assets = assets;
	}

	
}
