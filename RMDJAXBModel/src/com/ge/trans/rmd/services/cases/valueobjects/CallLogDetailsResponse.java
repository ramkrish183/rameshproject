package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallLogDetailsResponse", propOrder = {"asset",
        "location","count","customer","bussinessType","date"
})
@XmlRootElement
public class CallLogDetailsResponse {

	@XmlElement(required = true)
	private String asset;
	@XmlElement(required = true)
	private String location;
	@XmlElement(required = true)
	private String count;
	@XmlElement(required = true)
    private String customer;
	private String bussinessType;
	private String date;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
    public String getBussinessType() {
        return bussinessType;
    }
    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    
    
	
	

}
