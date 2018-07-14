package com.ge.trans.rmd.services.news.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "newsAllResponseType", propOrder = { "objid", "customerId", "description", "attachmentPath","status","createdBy","expiryDate","creationDate","attName","readNewsFlag","newsObjId","lastUpdatedBy","lastUpdatedDate" })
@XmlRootElement
public class NewsAllResponseType {

	protected String objid;
	protected String customerId;
	protected String description;
	protected String attachmentPath;
	protected String status;
	protected String createdBy;
	protected String expiryDate;
	protected String creationDate;
	protected String attName;
	protected String readNewsFlag;
	protected String newsObjId;
	protected String lastUpdatedBy;
	protected String lastUpdatedDate;
	
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
	public String getReadNewsFlag() {
		return readNewsFlag;
	}
	public void setReadNewsFlag(String readNewsFlag) {
		this.readNewsFlag = readNewsFlag;
	}
	public String getNewsObjId() {
		return newsObjId;
	}
	public void setNewsObjId(String newsObjId) {
		this.newsObjId = newsObjId;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
}
