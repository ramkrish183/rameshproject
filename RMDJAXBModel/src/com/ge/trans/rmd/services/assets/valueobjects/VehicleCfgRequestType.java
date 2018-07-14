package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.cases.valueobjects.CaseInfoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vehicleCfgRequestType", propOrder = { "configItem", "currentVersion", "expectedVersion",
        "serialNumber", "configObjId", "userName", "arlVehicleCfgRequestTypes", "objCaseInfoType",
        "isCaseVehicleConfig" ,"notificationgFlag","vehicleObjId"})

@XmlRootElement
public class VehicleCfgRequestType {

    @XmlElement(required = true)
    protected String configItem;
    @XmlElement(required = true)
    protected String currentVersion;
    @XmlElement(required = true)
    protected String expectedVersion;
    @XmlElement(required = true)
    protected String serialNumber;

    @XmlElement(required = true)
    protected String configObjId;

    @XmlElement(required = true)
    protected String userName;

    @XmlElement(required = true)
    protected List<VehicleCfgRequestType> arlVehicleCfgRequestTypes;

    @XmlElement(required = true)
    protected CaseInfoType objCaseInfoType;

    @XmlElement(required = true)
    protected String isCaseVehicleConfig;

    @XmlElement
    protected String notificationgFlag;
    
    @XmlElement
    protected String vehicleObjId;
    
    
    public String getVehicleObjId() {
		return vehicleObjId;
	}

	public void setVehicleObjId(String vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}

	public String getNotificationgFlag() {
		return notificationgFlag;
	}

	public void setNotificationgFlag(String notificationgFlag) {
		this.notificationgFlag = notificationgFlag;
	}

	public String getIsCaseVehicleConfig() {
        return isCaseVehicleConfig;
    }

    public void setIsCaseVehicleConfig(String isCaseVehicleConfig) {
        this.isCaseVehicleConfig = isCaseVehicleConfig;
    }

    public CaseInfoType getObjCaseInfoType() {
        return objCaseInfoType;
    }

    public void setObjCaseInfoType(CaseInfoType objCaseInfoType) {
        this.objCaseInfoType = objCaseInfoType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<VehicleCfgRequestType> getArlVehicleCfgRequestTypes() {
        return arlVehicleCfgRequestTypes;
    }

    public void setArlVehicleCfgRequestTypes(List<VehicleCfgRequestType> arlVehicleCfgRequestTypes) {
        this.arlVehicleCfgRequestTypes = arlVehicleCfgRequestTypes;
    }

    public String getConfigItem() {
        return configItem;
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

}
