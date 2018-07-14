/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindLocationServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 14, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 14, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FindLocationEoaServiceVO extends BaseVO {

    static final long serialVersionUID = 76124869624L;
    private String strLocationId;
    private String strLocationName;
    private String strLocationType;
    private String strSiteAddress1;
    private String strSiteAddress2;
    private String strCity;
    private String strState;
    private String strCountry;
    private String strZipCode;
    private String strTimeZone;
    private boolean blnIncludeInactive;
    private String customerId;

    /**
     * @return the strLocationId
     */
    public String getStrLocationId() {
        return strLocationId;
    }

    /**
     * @param strLocationId
     *            the strLocationId to set
     */
    public void setStrLocationId(final String strLocationId) {
        this.strLocationId = strLocationId;
    }

    /**
     * @return the strLocationName
     */
    public String getStrLocationName() {
        return strLocationName;
    }

    /**
     * @param strLocationName
     *            the strLocationName to set
     */
    public void setStrLocationName(final String strLocationName) {
        this.strLocationName = strLocationName;
    }

    /**
     * @return the strLocationType
     */
    public String getStrLocationType() {
        return strLocationType;
    }

    /**
     * @param strLocationType
     *            the strLocationType to set
     */
    public void setStrLocationType(final String strLocationType) {
        this.strLocationType = strLocationType;
    }

    /**
     * @return the strSiteAddress1
     */
    public String getStrSiteAddress1() {
        return strSiteAddress1;
    }

    /**
     * @param strSiteAddress1
     *            the strSiteAddress1 to set
     */
    public void setStrSiteAddress1(final String strSiteAddress1) {
        this.strSiteAddress1 = strSiteAddress1;
    }

    /**
     * @return the strSiteAddress2
     */
    public String getStrSiteAddress2() {
        return strSiteAddress2;
    }

    /**
     * @param strSiteAddress2
     *            the strSiteAddress2 to set
     */
    public void setStrSiteAddress2(final String strSiteAddress2) {
        this.strSiteAddress2 = strSiteAddress2;
    }

    /**
     * @return the strCity
     */
    public String getStrCity() {
        return strCity;
    }

    /**
     * @param strCity
     *            the strCity to set
     */
    public void setStrCity(final String strCity) {
        this.strCity = strCity;
    }

    /**
     * @return the strState
     */
    public String getStrState() {
        return strState;
    }

    /**
     * @param strState
     *            the strState to set
     */
    public void setStrState(final String strState) {
        this.strState = strState;
    }

    /**
     * @return the strCountry
     */
    public String getStrCountry() {
        return strCountry;
    }

    /**
     * @param strCountry
     *            the strCountry to set
     */
    public void setStrCountry(final String strCountry) {
        this.strCountry = strCountry;
    }

    /**
     * @return the strZipCode
     */
    public String getStrZipCode() {
        return strZipCode;
    }

    /**
     * @param strZipCode
     *            the strZipCode to set
     */
    public void setStrZipCode(final String strZipCode) {
        this.strZipCode = strZipCode;
    }

    /**
     * @return the strTimeZone
     */
    public String getStrTimeZone() {
        return strTimeZone;
    }

    /**
     * @param strTimeZone
     *            the strTimeZone to set
     */
    public void setStrTimeZone(final String strTimeZone) {
        this.strTimeZone = strTimeZone;
    }

    /**
     * @return the blnIncludeInactive
     */
    public boolean isBlnIncludeInactive() {
        return blnIncludeInactive;
    }

    /**
     * @param blnIncludeInactive
     *            the blnIncludeInactive to set
     */
    public void setBlnIncludeInactive(final boolean blnIncludeInactive) {
        this.blnIncludeInactive = blnIncludeInactive;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strLocationType", strLocationType)
                .append("strLocationId", strLocationId).append("strLocationName", strLocationName)
                .append("strSiteAddress1", strSiteAddress1).append("strSiteAddress2", strSiteAddress2)
                .append("strSiteCity", strCity).append("strSiteState", strState).append("strCountry", strCountry)
                .append("strZipCode", strZipCode).append("strTimeZone", strTimeZone)
                .append("blnIncludeInactive", blnIncludeInactive).toString();
    }
}
