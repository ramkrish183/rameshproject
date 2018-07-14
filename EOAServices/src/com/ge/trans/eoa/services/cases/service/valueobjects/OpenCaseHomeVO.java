package com.ge.trans.eoa.services.cases.service.valueobjects;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : OpenCaseHomeVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Aug 25, 2016
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================*/

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Aug 25, 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class OpenCaseHomeVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String dtCreationDate;
    private String strTitle;
    private String strCaseId;
    private String strAssetNumber;
    private String strPriority;
    private String strOwner;
    private String strReason;
    private String strGrpName;
    private String strCustomerId;
    private String condition;
    private String strCaseType;
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDtCreationDate() {
        return dtCreationDate;
    }

    public void setDtCreationDate(String dtCreationDate) {
        this.dtCreationDate = dtCreationDate;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrCaseId() {
        return strCaseId;
    }

    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public String getStrPriority() {
        return strPriority;
    }

    public void setStrPriority(String strPriority) {
        this.strPriority = strPriority;
    }

    public String getStrOwner() {
        return strOwner;
    }

    public void setStrOwner(String strOwner) {
        this.strOwner = strOwner;
    }

    public String getStrReason() {
        return strReason;
    }

    public void setStrReason(String strReason) {
        this.strReason = strReason;
    }

    public String getStrGrpName() {
        return strGrpName;
    }

    public void setStrGrpName(String strGrpName) {
        this.strGrpName = strGrpName;
    }

    public String getStrCustomerId() {
        return strCustomerId;
    }

    public void setStrCustomerId(String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStrCaseType() {
        return strCaseType;
    }

    public void setStrCaseType(String strCaseType) {
        this.strCaseType = strCaseType;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
