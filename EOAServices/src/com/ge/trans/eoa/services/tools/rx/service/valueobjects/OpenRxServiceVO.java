/**
 * ============================================================
 * Classification: GE Confidential
 * File : OpenRxServiceVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.cases.valueobjects
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
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

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
public class OpenRxServiceVO extends BaseVO {

    static final long serialVersionUID = 94376189L;
    private String strRoadNumber;
    private String strCaseId;
    private String strRxObjid;
    private String strRxTitle;
    private String strUrgRepair;
    private String strEstmRepTime;
    private String strAge;
    private Date dtHistDate;
    private Long longAssetNumber;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRoadNumber", strRoadNumber)
                .append("strCaseId", strCaseId).append("strRxObjid", strRxObjid).append("strRxTitle", strRxTitle)
                .append("strUrgRepair", strUrgRepair).append("strEstmRepTime", strEstmRepTime).append("strAge", strAge)
                .append("dtHistDate", dtHistDate).toString();
    }

    /**
     * @return the strRxObjid
     */
    public String getStrRxObjid() {
        return strRxObjid;
    }

    /**
     * @param strRxObjid
     *            the strRxObjid to set
     */
    public void setStrRxObjid(String strRxObjid) {
        this.strRxObjid = strRxObjid;
    }

    /**
     * @return the strRxTitle
     */
    public String getStrRxTitle() {
        return strRxTitle;
    }

    /**
     * @param strRxTitle
     *            the strRxTitle to set
     */
    public void setStrRxTitle(String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }

    /**
     * @return the strUrgRepair
     */
    public String getStrUrgRepair() {
        return strUrgRepair;
    }

    /**
     * @param strUrgRepair
     *            the strUrgRepair to set
     */
    public void setStrUrgRepair(String strUrgRepair) {
        this.strUrgRepair = strUrgRepair;
    }

    /**
     * @return the strEstmRepTime
     */
    public String getStrEstmRepTime() {
        return strEstmRepTime;
    }

    /**
     * @param strEstmRepTime
     *            the strEstmRepTime to set
     */
    public void setStrEstmRepTime(String strEstmRepTime) {
        this.strEstmRepTime = strEstmRepTime;
    }

    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }

    public String getStrCaseId() {
        return strCaseId;
    }

    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrAge() {
        return strAge;
    }

    public void setStrAge(String strAge) {
        this.strAge = strAge;
    }

    public Date getDtHistDate() {
        return dtHistDate;
    }

    public void setDtHistDate(Date dtHistDate) {
        this.dtHistDate = dtHistDate;
    }

    public Long getLongAssetNumber() {
        return longAssetNumber;
    }

    public void setLongAssetNumber(Long longAssetNumber) {
        this.longAssetNumber = longAssetNumber;
    }
}
