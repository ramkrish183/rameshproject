/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgTemplateVO.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.service.valueobjects;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;
import java.util.Map;

public class VehicleCfgTemplateVO {
    private String objId;
    private String configFile;
    private String template;
    private String version;
    private String title;
    private String status;
    private String assetGrpName;
    private String assetNumber;
    private String customer;
    private String userName;
    private String cntrlCnfg;
    private Map<String, List<String>> rnMap;
    private String vehObjId;
    private String customerId;
    private String offboardStatus;
    private String onboardStatus;
    private String vehStatusObjId;
   

    public String getCntrlCnfg() {
        return cntrlCnfg;
    }

    public void setCntrlCnfg(String cntrlCnfg) {
        this.cntrlCnfg = cntrlCnfg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Map<String, List<String>> getRnMap() {
        return rnMap;
    }

    public void setRnMap(Map<String, List<String>> rnMap) {
        this.rnMap = rnMap;
    }

    public String getVehObjId() {
        return vehObjId;
    }

    public void setVehObjId(String vehObjId) {
        this.vehObjId = vehObjId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

	public String getOffboardStatus() {
		return offboardStatus;
	}

	public void setOffboardStatus(String offboardStatus) {
		this.offboardStatus = offboardStatus;
	}

	public String getOnboardStatus() {
		return onboardStatus;
	}

	public void setOnboardStatus(String onboardStatus) {
		this.onboardStatus = onboardStatus;
	}

    public String getVehStatusObjId() {
        return vehStatusObjId;
    }

    public void setVehStatusObjId(String vehStatusObjId) {
        this.vehStatusObjId = vehStatusObjId;
    }
	

}
