package com.ge.trans.eoa.services.rxtranslation.service.valueobjects;

import java.util.Date;
import java.util.List;

public class MultiLingualFilterVO {
    private String title;
    private String model;
    private String type;
    private List<String> language;
    private String translationStatus;
    private String lastModifiedBy;
    private Date lastModifiedOn;
    private Date lastModifiedOnTo;
    private boolean isDefaultLoad;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getLanguage() {
		return language;
	}
	public void setLanguage(List<String> language) {
		this.language = language;
	}
	public String getTranslationStatus() {
		return translationStatus;
	}
	public void setTranslationStatus(String translationStatus) {
		this.translationStatus = translationStatus;
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
	
	public Date getLastModifiedOnTo() {
		return lastModifiedOnTo;
	}
	public void setLastModifiedOnTo(Date lastModifiedOnTo) {
		this.lastModifiedOnTo = lastModifiedOnTo;
	}
	public boolean isDefaultLoad() {
		return isDefaultLoad;
	}
	public void setDefaultLoad(boolean isDefaultLoad) {
		this.isDefaultLoad = isDefaultLoad;
	}
	
}
