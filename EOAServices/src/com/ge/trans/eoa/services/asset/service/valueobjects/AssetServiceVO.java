package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.Date;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AssetServiceVO extends BaseVO {

    private String strAssetNumber;
    private String assetGroupName;
    private long assetGroupNumber;
    private String customerID;
    private String customerName;
    private String location;
    private String model;
    private String fleet;
    private long assetId;
    // Controller config related to datascreen
    private String dsControllerConfig;
    private List<String> products;
    private String[] modelId;
    private String[] fleetId;
    private String assetTo;
    private String assetFrom;
    private String cmuId;
    private String egaCommId;
    private String partStatus;
    private Date installationDate;
    private String SoftwareVersion;
    private String sourceObjid;
    private String controllerConfig;
    private String assetNumberLike;
    private String fromDate;
    private String toDate;
    private String strModelId;
    private String strFleetId;
    private String lmsLocoId;
    private String badActor;
    private boolean hideL3Fault;
	private String ctrlCfgObjId;

    public String getLmsLocoId() {
        return lmsLocoId;
    }

    public void setLmsLocoId(String lmsLocoId) {
        this.lmsLocoId = lmsLocoId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStrModelId() {
        return strModelId;
    }

    public void setStrModelId(String strModelId) {
        this.strModelId = strModelId;
    }

    public String getStrFleetId() {
        return strFleetId;
    }

    public void setStrFleetId(String strFleetId) {
        this.strFleetId = strFleetId;
    }

    public String getAssetNumberLike() {
        return assetNumberLike;
    }

    public void setAssetNumberLike(String assetNumberLike) {
        this.assetNumberLike = assetNumberLike;
    }

    public String getCmuId() {
        return cmuId;
    }

    public void setCmuId(String cmuId) {
        this.cmuId = cmuId;
    }

    public String getEgaCommId() {
        return egaCommId;
    }

    public void setEgaCommId(String egaCommId) {
        this.egaCommId = egaCommId;
    }

    public String getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public String getSoftwareVersion() {
        return SoftwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        SoftwareVersion = softwareVersion;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getDsControllerConfig() {
        return dsControllerConfig;
    }

    public boolean isHideL3Fault() {
		return hideL3Fault;
	}

	public void setHideL3Fault(boolean hideL3Fault) {
		this.hideL3Fault = hideL3Fault;
	}

	public void setDsControllerConfig(String dsControllerConfig) {
        this.dsControllerConfig = dsControllerConfig;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public long getAssetGroupNumber() {
        return assetGroupNumber;
    }

    public void setAssetGroupNumber(long assetGroupNumber) {
        this.assetGroupNumber = assetGroupNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the assetId
     */
    public long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId
     *            the assetId to set
     */
    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public String[] getModelId() {
        return modelId;
    }

    public void setModelId(String[] modelId) {
        this.modelId = modelId;
    }

    public String[] getFleetId() {
        return fleetId;
    }

    public void setFleetId(String[] fleetId) {
        this.fleetId = fleetId;
    }

    public String getAssetFrom() {
        return assetFrom;
    }

    public void setAssetFrom(String assetFrom) {
        this.assetFrom = assetFrom;
    }

    public String getAssetTo() {
        return assetTo;
    }

    public void setAssetTo(String assetTo) {
        this.assetTo = assetTo;
    }

    public String getSourceObjid() {
        return sourceObjid;
    }

    public void setSourceObjid(String sourceObjid) {
        this.sourceObjid = sourceObjid;
    }

    public String getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(String controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public String getBadActor() {
        return badActor;
    }

    public void setBadActor(String badActor) {
        this.badActor = badActor;
    }

	/**
	 * @return the ctrlCfgObjId
	 */
	public final String getCtrlCfgObjId() {
		return ctrlCfgObjId;
	}

	/**
	 * @param ctrlCfgObjId the ctrlCfgObjId to set
	 */
	public final void setCtrlCfgObjId(String ctrlCfgObjId) {
		this.ctrlCfgObjId = ctrlCfgObjId;
	}

}
