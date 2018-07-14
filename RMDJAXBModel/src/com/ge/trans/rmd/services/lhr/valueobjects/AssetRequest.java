package com.ge.trans.rmd.services.lhr.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assets", propOrder = {
    "roadNumberHeader",
    "roadNumber"
})
@XmlRootElement
public class AssetRequest {
	
	
	@XmlElement(required = true)
	protected String roadNumberHeader;
	@XmlElement(required = true)
	protected String roadNumber;

	public String getRoadNumberHeader() {
		return roadNumberHeader;
	}

	public void setRoadNumberHeader(String roadNumberHeader) {
		this.roadNumberHeader = roadNumberHeader;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
}
