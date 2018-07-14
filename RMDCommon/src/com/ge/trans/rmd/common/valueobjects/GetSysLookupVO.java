/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetSysLookupHVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
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
package com.ge.trans.rmd.common.valueobjects;

import java.util.Date;
import java.util.Set;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class GetSysLookupVO extends BaseVO {

    private static final long serialVersionUID = 635654132L;
    private GetSysLookupVO getSysLookup;
    private Long getSysLookupSeqId;
    private String listName;
    private String listDescription;
    private String lookValue;
    private String lookState;
    private String displayName;
    private Long sortOrder;
    private Date lastUpdatedDate;
    private String urgency;
    private String filterSearchBy;// For search By field
    private String filterOption;// For condition field
    private String fieldValue;// For text box field
    private String eoaUserId;
    private String oldListName;
    private String lookValueDesc;


    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    private String isEditable = RMDCommonConstants.LETTER_Y;

    public GetSysLookupVO() {
        super();
    }

    public GetSysLookupVO(String listName) {
        super();
        this.listName = listName;
    }

    private Set getSysLookupMultilangs;

    /**
     * @return the getSysLookupSeqId
     */
    public Long getGetSysLookupSeqId() {
        return getSysLookupSeqId;
    }

    /**
     * @param getSysLookupSeqId
     *            the getSysLookupSeqId to set
     */
    public void setGetSysLookupSeqId(final Long getSysLookupSeqId) {
        this.getSysLookupSeqId = getSysLookupSeqId;
    }

    /**
     * @return the listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * @param listName
     *            the listName to set
     */
    public void setListName(final String listName) {
        this.listName = listName;
    }

    /**
     * @return the listDescription
     */
    public String getListDescription() {
        return listDescription;
    }

    /**
     * @param listDescription
     *            the listDescription to set
     */
    public void setListDescription(final String listDescription) {
        this.listDescription = listDescription;
    }

    /**
     * @return the lookValue
     */
    public String getLookValue() {
        return lookValue;
    }

    /**
     * @param lookValue
     *            the lookValue to set
     */
    public void setLookValue(final String lookValue) {
        this.lookValue = lookValue;
    }

    /**
     * @return the lookState
     */
    public String getLookState() {
        return lookState;
    }

    /**
     * @param lookState
     *            the lookState to set
     */
    public void setLookState(final String lookState) {
        this.lookState = lookState;
    }

    /**
     * @return the sortOrder
     */
    public Long getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(final Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the isEditable
     */
    public String getIsEditable() {
        return isEditable;
    }

    public String getLookValueDesc() {
		return lookValueDesc;
	}

	public void setLookValueDesc(String lookValueDesc) {
		this.lookValueDesc = lookValueDesc;
	}

	/**
     * @param isEditable
     *            the isEditable to set
     */
    public void setIsEditable(final String isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * @return the getSysLookup
     */
    public GetSysLookupVO getGetSysLookup() {
        return getSysLookup;
    }

    /**
     * @param getSysLookup
     *            the getSysLookup to set
     */
    public void setGetSysLookup(GetSysLookupVO getSysLookup) {
        this.getSysLookup = getSysLookup;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the getSysLookupMultilangs
     */
    public Set getGetSysLookupMultilangs() {
        return getSysLookupMultilangs;
    }

    /**
     * @param getSysLookupMultilangs
     *            the getSysLookupMultilangs to set
     */
    public void setGetSysLookupMultilangs(final Set getSysLookupMultilangs) {
        this.getSysLookupMultilangs = getSysLookupMultilangs;
    }

    public String getFilterSearchBy() {
        return filterSearchBy;
    }

    public void setFilterSearchBy(String filterSearchBy) {
        this.filterSearchBy = filterSearchBy;
    }

    public String getFilterOption() {
        return filterOption;
    }

    public void setFilterOption(String filterOption) {
        this.filterOption = filterOption;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getEoaUserId() {
        return eoaUserId;
    }

    public void setEoaUserId(String eoaUserId) {
        this.eoaUserId = eoaUserId;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getOldListName() {
        return oldListName;
    }

    public void setOldListName(String oldListName) {
        this.oldListName = oldListName;
    }

}