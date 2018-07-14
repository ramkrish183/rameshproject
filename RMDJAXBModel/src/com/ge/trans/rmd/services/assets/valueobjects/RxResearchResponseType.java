package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RxResearchResponseType", propOrder = { "objId", "rxTitle","rxCount","repairCode","repairDesc" })
@XmlRootElement
public class RxResearchResponseType {
	protected String objId;
    protected String rxTitle;
    protected String rxCount;
    protected String repairCode;
    protected String repairDesc;
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getRxTitle() {
		return rxTitle;
	}
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	public String getRxCount() {
		return rxCount;
	}
	public void setRxCount(String rxCount) {
		this.rxCount = rxCount;
	}
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public String getRepairDesc() {
		return repairDesc;
	}
	public void setRepairDesc(String repairDesc) {
		this.repairDesc = repairDesc;
	}
	
	
}
