/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesSearchCriteriaVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
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
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FindNotesSearchCriteriaVO extends BaseVO {
    private static final long serialVersionUID = 5423542369L;

    /** The rn from. */
    private String assetFrom;
    /** The rn to. */
    private String assetTo;
    /** The creator. */
    private String creator;
    /** The note type. */
    private String noteType;
    /** The key words. */
    private String keyWords;
    /** The asset Header */
    private String assetHdr;

    /** The date from. */
    private Date dateFrom;
    /** The date to. */
    private Date dateTo;

    private String assetGroupName;

    private String customerID;

    private String isNonGPOCUser;

    /**
     * @return assetGroupName
     */
    public String getAssetGroupName() {
        return assetGroupName;
    }

    /**
     * @param assetGroupName
     *            the assetGroupName to set
     */
    public void setAssetGroupName(final String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    /**
     * @return customerID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     *            the customerID to set
     */
    public void setCustomerID(final String customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the assetFrom
     */
    public String getAssetFrom() {
        return assetFrom;
    }

    /**
     * @param assetFrom
     *            the assetFrom to set
     */
    public void setAssetFrom(final String assetFrom) {
        this.assetFrom = assetFrom;
    }

    /**
     * @return the assetTo
     */
    public String getAssetTo() {
        return assetTo;
    }

    /**
     * @param assetTo
     *            the assetTo to set
     */
    public void setAssetTo(final String assetTo) {
        this.assetTo = assetTo;
    }

    /**
     * Gets the creator.
     *
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets the creator.
     *
     * @param creator
     *            the creator to set
     */
    public void setCreator(final String creator) {
        this.creator = creator;
    }

    /**
     * Gets the note type.
     *
     * @return the noteType
     */
    public String getNoteType() {
        return noteType;
    }

    /**
     * Sets the note type.
     *
     * @param noteType
     *            the noteType to set
     */
    public void setNoteType(final String noteType) {
        this.noteType = noteType;
    }

    /**
     * Gets the key words.
     *
     * @return the keyWords
     */
    public String getKeyWords() {
        return keyWords;
    }

    /**
     * Sets the key words.
     *
     * @param keyWords
     *            the keyWords to set
     */
    public void setKeyWords(final String keyWords) {
        this.keyWords = keyWords;
    }

    /**
     * @return the assetHdr
     */
    public String getAssetHdr() {
        return assetHdr;
    }

    /**
     * @param assetHdr
     *            the assetHdr to set
     */
    public void setAssetHdr(final String assetHdr) {
        this.assetHdr = assetHdr;
    }

    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom
     *            the dateFrom to set
     */
    public void setDateFrom(final Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo
     *            the dateTo to set
     */
    public void setDateTo(final Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getIsNonGPOCUser() {
        return isNonGPOCUser;
    }

    public void setIsNonGPOCUser(String isNonGPOCUser) {
        this.isNonGPOCUser = isNonGPOCUser;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("assetFrom", assetFrom)
                .append("assetTo", assetTo).append("creator", creator).append("noteType", noteType)
                .append("dateFrom", dateFrom).append("dateTo", dateTo).append("keyWords", keyWords).toString();
    }
}
