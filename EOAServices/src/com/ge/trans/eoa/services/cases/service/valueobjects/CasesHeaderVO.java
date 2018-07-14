/**
 * ============================================================
 * Classification: GE Confidential
 * File : CasesHeaderVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : 
 * Last Edited By :
 * Version : 1.0
 * Created on : 27/09/2016
 * History
 * Modified By : 
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================*/
package com.ge.trans.eoa.services.cases.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CasesHeaderVO extends BaseVO {

    static final long serialVersionUID = 1211566523L;
    private String strcustomerName;
    private String strAssetHeader;
    private String strAssetNumber;
    private String strCaseId;

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrcustomerName() {
        return strcustomerName;
    }

    public void setStrcustomerName(String strcustomerName) {
        this.strcustomerName = strcustomerName;
    }

    public String getStrAssetHeader() {
        return strAssetHeader;
    }

    public void setStrAssetHeader(String strAssetHeader) {
        this.strAssetHeader = strAssetHeader;
    }

    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
