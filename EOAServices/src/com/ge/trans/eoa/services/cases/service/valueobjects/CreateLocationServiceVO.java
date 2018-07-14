/**
 * ============================================================
 * Classification: GE Confidential
 * File : CreateLocationServiceVO.java
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
package com.ge.trans.eoa.services.cases.service.valueobjects;

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
public class CreateLocationServiceVO extends BaseVO {

    static final long serialVersionUID = 76124869666L;
    private String strLocationId;
    private String strLocationName;
    private String strLocationType;
    private String strSiteAddress;
    private String strCity;
    private String strState;
    private String strCountry;
    private String strZipCode;
    private String strTimeZone;
    private String strStatus;
    private String strPhone;
    private String strFirstName;
    private String strLastName;
    private String strSiteAddress1;
    private String strSiteAddress2;

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
     * @return the strSiteAddress
     */
    public String getStrSiteAddress() {
        return strSiteAddress;
    }

    /**
     * @param strSiteAddress
     *            the strSiteAddress to set
     */
    public void setStrSiteAddress(final String strSiteAddress) {
        this.strSiteAddress = strSiteAddress;
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
     * @return the strStatus
     */
    public String getStrStatus() {
        return strStatus;
    }

    /**
     * @param strStatus
     *            the strStatus to set
     */
    public void setStrStatus(final String strStatus) {
        this.strStatus = strStatus;
    }

    /**
     * @return the strPhone
     */
    public String getStrPhone() {
        return strPhone;
    }

    /**
     * @param strPhone
     *            the strPhone to set
     */
    public void setStrPhone(final String strPhone) {
        this.strPhone = strPhone;
    }

    /**
     * @return the strFirstName
     */
    @Override
    public String getStrFirstName() {
        return strFirstName;
    }

    /**
     * @param strFirstName
     *            the strFirstName to set
     */
    @Override
    public void setStrFirstName(final String strFirstName) {
        this.strFirstName = strFirstName;
    }

    /**
     * @return the strLastName
     */
    @Override
    public String getStrLastName() {
        return strLastName;
    }

    /**
     * @param strLastName
     *            the strLastName to set
     */
    @Override
    public void setStrLastName(final String strLastName) {
        this.strLastName = strLastName;
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
}
