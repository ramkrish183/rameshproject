package com.ge.trans.eoa.services.rxtranslation.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MultiLingualRxVO {
	private String title;
	private String rxStatus;
	private String type;
	private Map<String, String> translatedLanguages;
	private List<String> models;
	private String lastModifiedBy;
	private Date lastModifiedOn;
	private String approvedBy;
	private Date approvedOn;
	private long rxObjid;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getTranslatedLanguages() {
		return translatedLanguages;
	}
	public void setTranslatedLanguages(Map<String, String> translatedLanguages) {
		this.translatedLanguages = translatedLanguages;
	}
	public List<String> getModels() {
		return models;
	}
	public void setModels(List<String> models) {
		this.models = models;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public long getRxObjid() {
		return rxObjid;
	}
	public void setRxObjid(long rxObjid) {
		this.rxObjid = rxObjid;
	}
	public void loadModels(String stringModel) {
		if (models == null) {
			models = new ArrayList<String>();
		} 
		if (!models.contains(stringModel)) {
			models.add(stringModel);
		}
	}
	public void loadTranslationStatus(String langType,String status) {
		if (translatedLanguages == null) {
			translatedLanguages = new TreeMap<String,String>();
		} 
		if (!translatedLanguages.containsKey(langType)) {
			translatedLanguages.put(langType,status);
		}
	}
}
