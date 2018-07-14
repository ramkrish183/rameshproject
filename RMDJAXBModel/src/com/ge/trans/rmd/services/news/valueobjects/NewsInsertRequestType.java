package com.ge.trans.rmd.services.news.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.cases.valueobjects.RxDelvDocType;


@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "NewsInsertRequestType", propOrder = {"customerarray","newsDesc","expiryDate","userId","objDocDetails","objImgDetails","status","objId","fileRemoved"})


@XmlRootElement
public class NewsInsertRequestType {
	private List <String> customerarray;
    private String newsDesc;
    private String expiryDate;
    private String userId;
    private RxDelvDocType objDocDetails;
    private RxDelvDocType objImgDetails;
    private String status;
    private String objId;
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
    public RxDelvDocType getObjDocDetails() {
        return objDocDetails;
    }
    public void setObjDocDetails(RxDelvDocType objDocDetails) {
        this.objDocDetails = objDocDetails;
    }
    public RxDelvDocType getObjImgDetails() {
        return objImgDetails;
    }
    public void setObjImgDetails(RxDelvDocType objImgDetails) {
        this.objImgDetails = objImgDetails;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getFileRemoved() {
        return fileRemoved;
    }
    public void setFileRemoved(String fileRemoved) {
        this.fileRemoved = fileRemoved;
    }
    
	
	
}
