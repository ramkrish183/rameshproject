package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vehicleCfgResponseType", propOrder = { "configItem", "currentVersion", "expectedVersion",
        "serialNumber", "parmeterNo", "configObjId", "masterBOMObjId","notificationFlag","vehicleObjId" })

@XmlRootElement
public class VehicleCfgResponseType {

    @XmlElement(required = true)
    protected String configItem;
    @XmlElement(required = true)
    protected String currentVersion;
    @XmlElement(required = true)
    protected String expectedVersion;
    @XmlElement(required = true)
    protected String serialNumber;
    @XmlElement(required = true)
    protected String parmeterNo;
    @XmlElement(required = true)
    protected String configObjId;
    @XmlElement(required = true)
    protected String masterBOMObjId;
    @XmlElement
    protected String notificationFlag;
    @XmlElement
    protected String vehicleObjId;
    

    public String getVehicleObjId() {
		return vehicleObjId;
	}

	public void setVehicleObjId(String vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}

	public String getConfigItem() {
        return configItem;
    }

    public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getExpectedVersion() {
        return expectedVersion;
    }

    public void setExpectedVersion(String expectedVersion) {
        this.expectedVersion = expectedVersion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getConfigObjId() {
        return configObjId;
    }

    public void setConfigObjId(String configObjId) {
        this.configObjId = configObjId;
    }

    public String getParmeterNo() {
        return parmeterNo;
    }

    public void setParmeterNo(String parmeterNo) {
        this.parmeterNo = parmeterNo;
    }

    public String getMasterBOMObjId() {
        return masterBOMObjId;
    }

    public void setMasterBOMObjId(String masterBOMObjId) {
        this.masterBOMObjId = masterBOMObjId;
    }

}
