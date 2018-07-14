package com.ge.trans.eoa.services.news.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;

public class SaveNewsVO extends BaseVO {

	
	private static final long serialVersionUID = 1L;
	private List <String> customerarray;
    private String newsDesc;
    private String expiryDate;
    private String userId;
    private RecommDelvDocVO objDocDetails;
    private RecommDelvDocVO objImgDetails;
    private String objId;
    private String status;
    private String fileRemoved;
	
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getCustomerarray() {
		return customerarray;
	}
	public void setCustomerarray(List<String> customerarray) {
		this.customerarray = customerarray;
	}
	public String getNewsDesc() {
		return newsDesc;
	}
	public void setNewsDesc(String newsDesc) {
		this.newsDesc = newsDesc;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
    public RecommDelvDocVO getObjDocDetails() {
        return objDocDetails;
    }
    public void setObjDocDetails(RecommDelvDocVO objDocDetails) {
        this.objDocDetails = objDocDetails;
    }
    public RecommDelvDocVO getObjImgDetails() {
        return objImgDetails;
    }
    public void setObjImgDetails(RecommDelvDocVO objImgDetails) {
        this.objImgDetails = objImgDetails;
    }
    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFileRemoved() {
        return fileRemoved;
    }
    public void setFileRemoved(String fileRemoved) {
        this.fileRemoved = fileRemoved;
    }
    
    
}
