package com.ge.trans.eoa.services.asset.service.valueobjects;

public class VehicleCfgVO {

    private String configItem;
    private String currentVersion;
    private String expectedVersion;
    private String serialNumber;
    private String parmeterNo;
    private String configObjId;
    private String masterBOMObjId;
    private String userName;
    private String notificationFlag;
    private String vehicleObjId;
    
    public String getVehicleObjId() {
		return vehicleObjId;
	}

	public void setVehicleObjId(String vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getParmeterNo() {
        return parmeterNo;
    }

    public void setParmeterNo(String parmeterNo) {
        this.parmeterNo = parmeterNo;
    }

    public String getConfigObjId() {
        return configObjId;
    }

    public void setConfigObjId(String configObjId) {
        this.configObjId = configObjId;
    }

    public String getMasterBOMObjId() {
        return masterBOMObjId;
    }

    public void setMasterBOMObjId(String masterBOMObjId) {
        this.masterBOMObjId = masterBOMObjId;
    }

}
