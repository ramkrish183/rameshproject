package com.ge.trans.rmd.services.solutions.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repairCodeType", propOrder = { "repairCode", "repairCodeId", "repairCodeDesc" })
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepairCodeType {
	@XmlElement(required = true)
    protected String repairCode;
    @XmlElement(required = true)
    protected String repairCodeId;
    @XmlElement(required = true)
    protected String repairCodeDesc;
    
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public String getRepairCodeId() {
		return repairCodeId;
	}
	public void setRepairCodeId(String repairCodeId) {
		this.repairCodeId = repairCodeId;
	}
	public String getRepairCodeDesc() {
		return repairCodeDesc;
	}
	public void setRepairCodeDesc(String repairCodeDesc) {
		this.repairCodeDesc = repairCodeDesc;
	}
}
