package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "massApplyCfgResponseType", propOrder = {
    "objId",
    "template",
    "version",
    "title",
    "cfgStatus",
    "vehicleStatus",
    "vehicleStatusDate",
    "offboardStatus",
    "onBoardStatus",
    "onBoardStatusDate"
})

@XmlRootElement
public class MassApplyCfgResponseType {

	
	@XmlElement(required = true)
	protected String objId;
	
	@XmlElement(required = true)
	protected String template;
	
	@XmlElement(required = true)
	protected String version;
	
	@XmlElement(required = true)
	protected String title;
	
	@XmlElement(required = true)
	protected String cfgStatus;
	
	@XmlElement(required = true)
	protected String vehicleStatus;
	
	@XmlElement(required = true)
	protected String vehicleStatusDate;
	
	@XmlElement(required = true)
	protected String offboardStatus;
	@XmlElement(required = true)
	protected String onBoardStatus;
	@XmlElement(required = true)
	protected String onBoardStatusDate;
	
	
	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCfgStatus() {
		return cfgStatus;
	}

	public void setCfgStatus(String cfgStatus) {
		this.cfgStatus = cfgStatus;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getVehicleStatusDate() {
		return vehicleStatusDate;
	}

	public void setVehicleStatusDate(String vehicleStatusDate) {
		this.vehicleStatusDate = vehicleStatusDate;
	}

    public String getOffboardStatus() {
        return offboardStatus;
    }

    public void setOffboardStatus(String offboardStatus) {
        this.offboardStatus = offboardStatus;
    }

    public String getOnBoardStatus() {
        return onBoardStatus;
    }

    public void setOnBoardStatus(String onBoardStatus) {
        this.onBoardStatus = onBoardStatus;
    }

    public String getOnBoardStatusDate() {
        return onBoardStatusDate;
    }

    public void setOnBoardStatusDate(String onBoardStatusDate) {
        this.onBoardStatusDate = onBoardStatusDate;
    }
	
	


}
