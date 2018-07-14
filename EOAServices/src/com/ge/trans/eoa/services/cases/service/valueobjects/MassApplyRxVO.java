/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: MassApplyRxVO.java
 * Description 			:
 * Package 				: com.ge.trans.eoa.services.cases.service.valueobjects;
 * Author 				: iGATE Global Solutions Ltd.
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

package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :21-07-2015
 * @Date Modified :21-07-2015
 * @Modified By :Vamshi
 * @Contact :
 * @Description :This is plain POJO class which carries log details.It Contains
 *              Corresponding variable Declarations along with their respective
 *              getters and setters.
 * @History :
 ******************************************************************************/
public class MassApplyRxVO {

    private String assetgroupName;
    private String customerName;
    private String customerId;
    private String language;
    // private String userId;
    private String userName;
    private List<String> assetList;
    private int caseCondition;
    private String caseTitle;
    private String caseType;
    private String priority;
    private String severity;
    private String isMassApplyRx;
    private String userLanguage;
    private String urgency;
    private String estRepairTime;
    private String msdcNotes;
    private String isCaseSplit;
    private String isFromTooloutput;
    private List<RecommDelvDocVO> lstAttachment=new ArrayList<RecommDelvDocVO>();
    
	public List<RecommDelvDocVO> getLstAttachment() {
		return lstAttachment;
	}

	public void setLstAttachment(List<RecommDelvDocVO> lstAttachment) {
		this.lstAttachment = lstAttachment;
	}

	public String getIsCaseSplit() {
        return isCaseSplit;
    }

    public void setIsCaseSplit(String isCaseSplit) {
        this.isCaseSplit = isCaseSplit;
    }

    public String getMsdcNotes() {
        return msdcNotes;
    }

    public void setMsdcNotes(String msdcNotes) {
        this.msdcNotes = msdcNotes;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getEstRepairTime() {
        return estRepairTime;
    }

    public void setEstRepairTime(String estRepairTime) {
        this.estRepairTime = estRepairTime;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    private RxDetailsVO objRxDetailsVO;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsMassApplyRx() {
        return isMassApplyRx;
    }

    public void setIsMassApplyRx(String isMassApplyRx) {
        this.isMassApplyRx = isMassApplyRx;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public RxDetailsVO getObjRxDetailsVO() {
        return objRxDetailsVO;
    }

    public void setObjRxDetailsVO(RxDetailsVO objRxDetailsVO) {
        this.objRxDetailsVO = objRxDetailsVO;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public int getCaseCondition() {
        return caseCondition;
    }

    public void setCaseCondition(int caseCondition) {
        this.caseCondition = caseCondition;
    }

    public String getAssetgroupName() {
        return assetgroupName;
    }

    public void setAssetgroupName(String assetgroupName) {
        this.assetgroupName = assetgroupName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<String> assetList) {
        this.assetList = assetList;
    }

	public String getIsFromTooloutput() {
		return isFromTooloutput;
	}

	public void setIsFromTooloutput(String isFromTooloutput) {
		this.isFromTooloutput = isFromTooloutput;
	}

	
}
