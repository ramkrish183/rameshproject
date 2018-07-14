package com.ge.trans.rmd.services.admin.valueobjects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "newsDeleteRequestType", propOrder = {"objId"})
@XmlRootElement
public class NewsDeleteRequestType {

	 @XmlElement(required = true)
	    protected String objId;

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	
	 
	 
}
