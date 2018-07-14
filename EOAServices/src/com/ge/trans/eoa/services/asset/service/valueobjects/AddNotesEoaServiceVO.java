/**
 * ============================================================
 * Classification: GE Confidential
 * File : NotesServiceVO.java
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
package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@SuppressWarnings("unchecked")
public class AddNotesEoaServiceVO extends BaseVO {

    public List<String> getAssetNumbersList() {
        return assetNumbersList;
    }

    public void setAssetNumbersList(List<String> assetNumbersList) {
        this.assetNumbersList = assetNumbersList;
    }

    static final long serialVersionUID = 13897677L;
    /** The case id. */
    private String caseId;
    /** The sticky. */
    private String sticky;
    /** The apply level. */
    private String applyLevel;
    /** The from rn. */
    private String fromAsset;
    /** The to rn. */
    private String toAsset;
    /** The notes. */
    private String notes;
    private String strExistNotes;
    /** The email. */
    private String email;
    /** The fromPage. */
    private String strFromPage;
    private String strExistMsg;
    private String strCreatedBy;
    private String customer;
    private String assetGrpName;
    private String assetHeader;
    private String strSave;
    private String strStatus;
    private String strAssetHeader;

    private boolean bStickyExist;
    private boolean bAssetStickyExist;

    private Date dtCreatedOn;

    private List lstFromAsset = new ArrayList();
    private List lstAssetHeader = new ArrayList();

    private String eoaUserId;

    /* For Add notes added by Vamshi begin */
    private String assetNumber;

    private String customerId;
    private String noteDescription;
    private String userId;
    /* Added by Murali for Add Notes to Vehicle (addNotes) screen */
    String fromRN;
    String toRN;
    String modelId;
    String ctrlId;
    String fleetId;
    String overWriteFlag;
    String stickyObjId;
    /* End of Add Notes to Vehicle */
    private List<String> assetNumbersList;

    /* For Add notes added by Vamshi end */

    /**
     * @param noteDescriptionto
     *            setNoteDescription
     */
    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    /**
     * @param customerId
     *            to setCustomerId
     */

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @param userId
     *            to setUserId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @param assetNumber
     *            to setAssetNumber
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * @param eoaUserId
     *            to setEoaUserId
     */
    public void setEoaUserId(final String eoaUserId) {
        this.eoaUserId = eoaUserId;
    }

    /**
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return assetNumber
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @return noteDescription
     */
    public String getNoteDescription() {
        return noteDescription;
    }

    /**
     * @return eoaUserId
     */

    public String getEoaUserId() {
        return eoaUserId;
    }

    /**
     * @return assetGrpName
     */
    public String getAssetGrpName() {
        return assetGrpName;
    }

    /**
     * @param assetGrpName
     *            to set assetGrpName
     */
    public void setAssetGrpName(final String assetGrpName) {
        this.assetGrpName = assetGrpName;
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
     * @param caseId
     *            the caseId to set
     */
    public void setCaseId(final String caseId) {
        this.caseId = caseId;
    }

    /**
     * Gets the sticky.
     *
     * @return the sticky
     */
    public String getSticky() {
        return sticky;
    }

    /**
     * Sets the sticky.
     *
     * @param sticky
     *            the sticky to set
     */
    public void setSticky(final String sticky) {
        this.sticky = sticky;
    }

    /**
     * Gets the apply level.
     *
     * @return the applyLevel
     */
    public String getApplyLevel() {
        return applyLevel;
    }

    /**
     * Sets the apply level.
     *
     * @param applyLevel
     *            the applyLevel to set
     */
    public void setApplyLevel(final String applyLevel) {
        this.applyLevel = applyLevel;
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
     * @param notes
     *            the notes to set
     */
    public void setNotes(final String notes) {
        this.notes = notes;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the fromAsset
     */
    public String getFromAsset() {
        return fromAsset;
    }

    /**
     * @param fromAsset
     *            the fromAsset to set
     */
    public void setFromAsset(final String fromAsset) {
        this.fromAsset = fromAsset;
    }

    /**
     * @return the toAsset
     */
    public String getToAsset() {
        return toAsset;
    }

    /**
     * @param toAsset
     *            the toAsset to set
     */
    public void setToAsset(final String toAsset) {
        this.toAsset = toAsset;
    }

    /**
     * @return the lstFromAsset
     */
    public List getLstFromAsset() {
        return lstFromAsset;
    }

    /**
     * @param lstFromAsset
     *            the lstFromAsset to set
     */
    public void setLstFromAsset(final List lstFromAsset) {
        this.lstFromAsset = lstFromAsset;
    }

    /**
     * @return the strFromPage
     */
    public String getStrFromPage() {
        return strFromPage;
    }

    /**
     * @param strFromPage
     *            the strFromPage to set
     */
    public void setStrFromPage(final String strFromPage) {
        this.strFromPage = strFromPage;
    }

    /**
     * @return the bStickyExist
     */
    public boolean isBStickyExist() {
        return bStickyExist;
    }

    /**
     * @param stickyExist
     *            the bStickyExist to set
     */
    public void setBStickyExist(final boolean stickyExist) {
        bStickyExist = stickyExist;
    }

    /**
     * @return the bAssetStickyExist
     */
    public boolean isBAssetStickyExist() {
        return bAssetStickyExist;
    }

    /**
     * @param assetStickyExist
     *            the bAssetStickyExist to set
     */
    public void setBAssetStickyExist(final boolean assetStickyExist) {
        bAssetStickyExist = assetStickyExist;
    }

    /**
     * @return the strExistMsg
     */
    public String getStrExistMsg() {
        return strExistMsg;
    }

    /**
     * @param strExistMsg
     *            the strExistMsg to set
     */
    public void setStrExistMsg(final String strExistMsg) {
        this.strExistMsg = strExistMsg;
    }

    /**
     * @return the strCreatedBy
     */
    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    /**
     * @param strCreatedBy
     *            the strCreatedBy to set
     */
    public void setStrCreatedBy(final String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    /**
     * @return the strExistNotes
     */
    public String getStrExistNotes() {
        return strExistNotes;
    }

    /**
     * @param strExistNotes
     *            the strExistNotes to set
     */
    public void setStrExistNotes(final String strExistNotes) {
        this.strExistNotes = strExistNotes;
    }

    /**
     * @return the strSave
     */
    public String getStrSave() {
        return strSave;
    }

    /**
     * @param strSave
     *            the strSave to set
     */
    public void setStrSave(final String strSave) {
        this.strSave = strSave;
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
     * @return the customer
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(final String customer) {
        this.customer = customer;
    }

    /**
     * @return the assetHeader
     */
    public String getAssetHeader() {
        return assetHeader;
    }

    /**
     * @param assetHeader
     *            the assetHeader to set
     */
    public void setAssetHeader(final String assetHeader) {
        this.assetHeader = assetHeader;
    }

    /**
     * @return the strAssetHeader
     */
    public String getStrAssetHeader() {
        return strAssetHeader;
    }

    /**
     * @param strAssetHeader
     *            the strAssetHeader to set
     */
    public void setStrAssetHeader(final String strAssetHeader) {
        this.strAssetHeader = strAssetHeader;
    }

    /**
     * @return the lstAssetHeader
     */
    public List getLstAssetHeader() {
        return lstAssetHeader;
    }

    /**
     * @param lstAssetHeader
     *            the lstAssetHeader to set
     */
    public void setLstAssetHeader(final List lstAssetHeader) {
        this.lstAssetHeader = lstAssetHeader;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("caseId", caseId).append("sticky", sticky)
                .append("applyLevel", applyLevel).append("fromAsset", fromAsset).append("toAsset", toAsset)
                .append("notes", notes).append("strExistNotes", strExistNotes).append("email", email)
                .append("strFromPage", strFromPage).append("strExistMsg", strExistMsg)
                .append("strCreatedBy", strCreatedBy).append("dtCreatedOn", dtCreatedOn)
                .append("bStickyExist", bStickyExist).append("customer", customer).append("assetHeader", assetHeader)
                .append("strSave", strSave).append("strStatus", strStatus).append("strAssetHeader", strAssetHeader)
                .append("lstFromAsset", lstFromAsset).append("lstAssetHeader", lstAssetHeader).toString();
    }

    /**
     * @return the dtCreatedOn
     */
    public Date getDtCreatedOn() {
        return dtCreatedOn;
    }

    /**
     * @param dtCreatedOn
     *            the dtCreatedOn to set
     */
    public void setDtCreatedOn(final Date dtCreatedOn) {
        this.dtCreatedOn = dtCreatedOn;
    }

    /**
     * @return the fromRN
     */
    public String getFromRN() {
        return fromRN;
    }

    /**
     * @param fromRN
     *            the fromRN to set
     */
    public void setFromRN(String fromRN) {
        this.fromRN = fromRN;
    }

    /**
     * @return the toRN
     */
    public String getToRN() {
        return toRN;
    }

    /**
     * @param toRN
     *            the toRN to set
     */
    public void setToRN(String toRN) {
        this.toRN = toRN;
    }

    /**
     * @return the modelId
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * @param modelId
     *            the modelId to set
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * @return the ctrlId
     */
    public String getCtrlId() {
        return ctrlId;
    }

    /**
     * @param ctrlId
     *            the ctrlId to set
     */
    public void setCtrlId(String ctrlId) {
        this.ctrlId = ctrlId;
    }

    /**
     * @return the fleetId
     */
    public String getFleetId() {
        return fleetId;
    }

    /**
     * @param fleetId
     *            the fleetId to set
     */
    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    /**
     * @return the overWriteFlag
     */
    public String getOverWriteFlag() {
        return overWriteFlag;
    }

    /**
     * @param overWriteFlag
     *            the overWriteFlag to set
     */
    public void setOverWriteFlag(String overWriteFlag) {
        this.overWriteFlag = overWriteFlag;
    }

    /**
     * @return the stickyObjId
     */
    public String getStickyObjId() {
        return stickyObjId;
    }

    /**
     * @param stickyObjId
     *            the stickyObjId to set
     */
    public void setStickyObjId(String stickyObjId) {
        this.stickyObjId = stickyObjId;
    }

}
