package com.ge.trans.rmd.services.admin.valueobjects;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDeleteRequestType", propOrder = {"userIds"})
@Consumes({"application/xml", "application/json"})
@XmlRootElement
public class UserDeleteRequestType {
	
	 @XmlElement(required = true)
	    protected List<String> userIds;
	/* @XmlElement(required = true)
	    protected List<String> userIds;
	 @XmlElement(required = true)
	    protected List<Integer> status;*/

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	 
	 


}
