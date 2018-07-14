package com.ge.trans.eoa.services.news.impl;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AllNewsVO extends BaseVO {
	
	private String objid;
	private String customerId;
	private String description;
	private String attachmentPath;
	private String status;
	private String createdBy;
	private String expiryDate;
	private String creationDate;
	private String attName;
	private String newsObjId;
	private String readNewsFlag;
	private String lastUpdatedDate;
	private String lastUpdatedBy;
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getAttName() {
        return attName;
    }
    public void setAttName(String attName) {
        this.attName = attName;
    }
	public String getNewsObjId() {
		return newsObjId;
	}
	public void setNewsObjId(String newsObjId) {
		this.newsObjId = newsObjId;
	}
	public String getReadNewsFlag() {
		return readNewsFlag;
	}
	public void setReadNewsFlag(String readNewsFlag) {
		this.readNewsFlag = readNewsFlag;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	

}
