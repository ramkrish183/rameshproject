package com.ge.trans.eoa.services.autohc.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AutoHCTemplateSaveVO extends BaseVO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tempObjId;
	private String sampleRate;
	private String postSamples;
	private String frequency;
	private String fcrtObjid;
	private String templateNo;
	private String versionNo;
	private String title;
	private String status;
	private String configCtrlObjid;
	public String getTempObjId() {
		return tempObjId;
	}
	public void setTempObjId(String tempObjId) {
		this.tempObjId = tempObjId;
	}
	public String getSampleRate() {
		return sampleRate;
	}
	public void setSampleRate(String sampleRate) {
		this.sampleRate = sampleRate;
	}
	public String getPostSamples() {
		return postSamples;
	}
	public void setPostSamples(String postSamples) {
		this.postSamples = postSamples;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getFcrtObjid() {
		return fcrtObjid;
	}
	public void setFcrtObjid(String fcrtObjid) {
		this.fcrtObjid = fcrtObjid;
	}
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfigCtrlObjid() {
		return configCtrlObjid;
	}
	public void setConfigCtrlObjid(String configCtrlObjid) {
		this.configCtrlObjid = configCtrlObjid;
	}
	
}
