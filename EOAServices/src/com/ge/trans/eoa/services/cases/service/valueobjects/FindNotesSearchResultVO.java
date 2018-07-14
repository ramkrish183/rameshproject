/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesSearchResultVO.java
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 *
 * @Author      :
 * @Version     : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact     :
 * @Description :
 * @History     :
 *
 ******************************************************************************/
public class FindNotesSearchResultVO extends BaseVO{
    private static final long serialVersionUID = 756845245L;

    /** The Boolean Sticky. */
    private boolean bSticky;

    /** The notes seq id. */
    private String noteSeqId;
    /** The road header. */
    private String assetHeader;
    /** The road number. */
    private String assetNumber;
    /** The case id. */
    private String caseId;
    /** The creator. */
    private String creator;
    /** The type. */
    private String type;
    /** The remove sticky. */
    private String removeSticky;
    /** The notes. */
    private String notes;
    /** The model */
    private String model;

    /** The date. */
    private String date;
    private Long longAssetNumber;
    
    private String customerId;
    private String assetGroupName;
    /**
     * @return the bSticky
     */
    public boolean isBSticky() {
        return bSticky;
    }

    /**
     * @param sticky the bSticky to set
     */
    public void setBSticky(final boolean sticky) {
        bSticky = sticky;
    }

    /**
     * @return the noteSeqId
     */
    public String getNoteSeqId() {
        return noteSeqId;
    }

    /**
     * @param noteSeqId the noteSeqId to set
     */
    public void setNoteSeqId(final String noteSeqId) {
        this.noteSeqId = noteSeqId;
    }

    /**
     * @return the assetNumber
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param assetNumber the assetNumber to set
     */
    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }


    /**
     * Gets the case id.
     *
     * @return the caseId
     */
    public String getCaseId() {
        return caseId;
    }

    /**
     * Sets the case id.
     *
     * @param caseId the caseId to set
     */
    public void setCaseId(final String caseId) {
        this.caseId = caseId;
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
     * @param creator the creator to set
     */
    public void setCreator(final String creator) {
        this.creator = creator;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Gets the remove sticky.
     *
     * @return the removeSticky
     */
    public String getRemoveSticky() {
        return removeSticky;
    }

    /**
     * Sets the remove sticky.
     *
     * @param removeSticky the removeSticky to set
     */
    public void setRemoveSticky(final String removeSticky) {
        this.removeSticky = removeSticky;
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes the notes to set
     */
    public void setNotes(final String notes) {
        this.notes = notes;
    }

    /**
     * @return the assetHeader
     */
    public String getAssetHeader() {
        return assetHeader;
    }

    /**
     * @param assetHeader the assetHeader to set
     */
    public void setAssetHeader(final String assetHeader) {
        this.assetHeader = assetHeader;
    }



    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append(
                "noteSeqId", noteSeqId).append("assetHeader",
                        assetHeader).append("assetNumber", assetNumber).append(
                "caseId", caseId).append("date",
                        date).append("creator", creator)
                .append("type", type).append("removeSticky",
                        removeSticky).append("notes",
                                notes).append("bSticky",
                                        bSticky).toString();
    }


    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model  to set
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(final String date) {
        this.date = date;
    }

    /**
     * @return the longAssetNumber
     */
    public Long getLongAssetNumber() {
        return longAssetNumber;
    }

    /**
     * @param longAssetNumber the longAssetNumber to set
     */
    public void setLongAssetNumber(final Long longAssetNumber) {
        this.longAssetNumber = longAssetNumber;
    }

    /*
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /*
     * @param customerId the customerId to set
     */
    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
    /*
     * @return the assetGroupName
     */
    public String getAssetGroupName() {
        return assetGroupName;
    }

    /*
     * @param assetGroupName the assetGroupName to set
     */
    public void setAssetGroupName(final String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }
}
