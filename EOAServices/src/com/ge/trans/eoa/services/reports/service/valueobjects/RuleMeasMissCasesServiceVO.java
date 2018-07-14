/**
 * ============================================================
 * File : RuleMeasMissCasesServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.reports.service.valueobjectss
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.reports.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleMeasMissCasesServiceVO extends BaseVO {

    private static final long serialVersionUID = 1377990122224175894L;

    private String strRoadNumber;
    private String strCaseId;
    private String strCaseTitle;
    private Date strCaseCreationDt;

    /**
     * @return the strRoadNumber
     */
    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    /**
     * @param strRoadNumber
     *            the strRoadNumber to set
     */
    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }

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
    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    /**
     * @return the strCaseTitle
     */
    public String getStrCaseTitle() {
        return strCaseTitle;
    }

    /**
     * @param strCaseTitle
     *            the strCaseTitle to set
     */
    public void setStrCaseTitle(String strCaseTitle) {
        this.strCaseTitle = strCaseTitle;
    }

    /**
     * @return the strCaseCreationDt
     */
    public Date getStrCaseCreationDt() {
        return strCaseCreationDt;
    }

    /**
     * @param strCaseCreationDt
     *            the strCaseCreationDt to set
     */
    public void setStrCaseCreationDt(Date strCaseCreationDt) {
        this.strCaseCreationDt = strCaseCreationDt;
    }

}