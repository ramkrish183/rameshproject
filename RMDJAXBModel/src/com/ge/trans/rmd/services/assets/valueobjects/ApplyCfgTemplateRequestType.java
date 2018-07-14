/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: LogMessagesResponseType.java
 * Description 			:
 * Package 				: com.ge.trans.rmd.cm.valueobjects;
 * Author 				: Capgemini India.
 * Last Edited By 		:
 * Version 				: 1.0
 * Created on 			:
 * History				:
 * Modified By 			: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applyCfgTemplateRequestType", propOrder = {
		"arlAssetSearchRequestTypes",
		"userName",
		"searchCriteria",
		"cfgTemplateList",
		"ctrlCfgObjId",
		"ctrlCfgName",
		"isDeleteCfg",
		"cfgAGTTemplateList",
		"userId",
		"vehHdrNo",
		"customerId",
		"device"
})

@XmlRootElement
public class ApplyCfgTemplateRequestType {

	@XmlElement
	protected List<CfgAssetSearchRequestType> arlAssetSearchRequestTypes;

	@XmlElement
	protected List<VerifyCfgTemplatesRequestType> cfgTemplateList;
	
	@XmlElement
	protected String userName;
	
	@XmlElement
	protected String searchCriteria;
	
	@XmlElement
	protected String ctrlCfgObjId;

	@XmlElement
	protected String ctrlCfgName;

	@XmlElement
	protected boolean isDeleteCfg;
	
	@XmlElement
    protected List<VerifyCfgTemplatesRequestType> cfgAGTTemplateList;
	
	@XmlElement
    protected String userId;
	
	@XmlElement
    protected String vehHdrNo;
	
	@XmlElement
    protected String customerId;
	
	@XmlElement
    protected String device;

	
	public List<CfgAssetSearchRequestType> getArlAssetSearchRequestTypes() {
		return arlAssetSearchRequestTypes;
	}

	public void setArlAssetSearchRequestTypes(
			List<CfgAssetSearchRequestType> arlAssetSearchRequestTypes) {
		this.arlAssetSearchRequestTypes = arlAssetSearchRequestTypes;
	}

	public List<VerifyCfgTemplatesRequestType> getCfgTemplateList() {
		return cfgTemplateList;
	}

	public void setCfgTemplateList(
			List<VerifyCfgTemplatesRequestType> cfgTemplateList) {
		this.cfgTemplateList = cfgTemplateList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public String getCtrlCfgObjId() {
		return ctrlCfgObjId;
	}

	public void setCtrlCfgObjId(String ctrlCfgObjId) {
		this.ctrlCfgObjId = ctrlCfgObjId;
	}

	public String getCtrlCfgName() {
		return ctrlCfgName;
	}

	public void setCtrlCfgName(String ctrlCfgName) {
		this.ctrlCfgName = ctrlCfgName;
	}

	public boolean isDeleteCfg() {
		return isDeleteCfg;
	}

	public void setDeleteCfg(boolean isDeleteCfg) {
		this.isDeleteCfg = isDeleteCfg;
	}

    public List<VerifyCfgTemplatesRequestType> getCfgAGTTemplateList() {
        return cfgAGTTemplateList;
    }

    public void setCfgAGTTemplateList(
            List<VerifyCfgTemplatesRequestType> cfgAGTTemplateList) {
        this.cfgAGTTemplateList = cfgAGTTemplateList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVehHdrNo() {
        return vehHdrNo;
    }

    public void setVehHdrNo(String vehHdrNo) {
        this.vehHdrNo = vehHdrNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
    
    
    
    
	
	

}
