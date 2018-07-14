package com.ge.trans.rmd.services.alert.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertResponseType", propOrder = { "fleetId", "fleetNo","strModel","customerId"})
@XmlRootElement
public class AlertResponseType 
{
	@XmlElement(required = true)
    protected String fleetId;
    @XmlElement(required = true)
    protected String fleetNo;
    @XmlElement(required = true)
    protected String strModel;
    @XmlElement(required = true)
    protected String customerId;
	public String getFleetId() {
		return fleetId;
	}
	public void setFleetId(String fleetId) {
		this.fleetId = fleetId;
	}
	public String getFleetNo() {
		return fleetNo;
	}
	public void setFleetNo(String fleetNo) {
		this.fleetNo = fleetNo;
	}
	public String getStrModel() {
		return strModel;
	}
	public void setStrModel(String strModel) {
		this.strModel = strModel;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
    
    

}
