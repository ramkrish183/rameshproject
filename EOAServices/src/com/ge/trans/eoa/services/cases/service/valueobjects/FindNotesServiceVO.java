/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesServiceVO.java
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

import java.util.ArrayList;

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
public class FindNotesServiceVO extends BaseVO {
    private static final long serialVersionUID = 245245245;

    private String strRmvSticky;
    private String strHidNotesSel;

    private ArrayList noteTypeList;
    private ArrayList creatorList;
    private ArrayList assetHeaderList;
    private ArrayList notesSearchList;

    private FindNotesSearchResultVO findNotesSearchResultVO;
    private FindNotesSearchCriteriaVO findNotesSearchCriteriaVO;

    public FindNotesServiceVO() {
        super();
        findNotesSearchResultVO = new FindNotesSearchResultVO();
        findNotesSearchCriteriaVO = new FindNotesSearchCriteriaVO();
    }

    /**
     * @return the notesSearchList
     */
    public ArrayList getNotesSearchList() {
        return notesSearchList;
    }

    /**
     * @param notesSearchList
     *            the notesSearchList to set
     */
    public void setNotesSearchList(final ArrayList notesSearchList) {
        this.notesSearchList = notesSearchList;
    }

    /**
     * @return the noteTypeList
     */
    public ArrayList getNoteTypeList() {
        return noteTypeList;
    }

    /**
     * @param noteTypeList
     *            the noteTypeList to set
     */
    public void setNoteTypeList(final ArrayList noteTypeList) {
        this.noteTypeList = noteTypeList;
    }

    /**
     * @return the creatorList
     */
    public ArrayList getCreatorList() {
        return creatorList;
    }

    /**
     * @return the assetHeaderList
     */
    public ArrayList getAssetHeaderList() {
        return assetHeaderList;
    }

    /**
     * @param assetHeaderList
     *            the assetHeaderList to set
     */
    public void setAssetHeaderList(final ArrayList assetHeaderList) {
        this.assetHeaderList = assetHeaderList;
    }

    /**
     * @param creatorList
     *            the creatorList to set
     */
    public void setCreatorList(final ArrayList creatorList) {
        this.creatorList = creatorList;
    }

    /**
     * @return the findNotesSearchResultVO
     */
    public FindNotesSearchResultVO getFindNotesSearchResultVO() {
        return findNotesSearchResultVO;
    }

    /**
     * @param findNotesSearchResultVO
     *            the findNotesSearchResultVO to set
     */
    public void setFindNotesSearchResultVO(final FindNotesSearchResultVO findNotesSearchResultVO) {
        this.findNotesSearchResultVO = findNotesSearchResultVO;
    }

    /**
     * @return the findNotesSearchCriteriaVO
     */
    public FindNotesSearchCriteriaVO getFindNotesSearchCriteriaVO() {
        return findNotesSearchCriteriaVO;
    }

    /**
     * @param findNotesSearchCriteriaVO
     *            the findNotesSearchCriteriaVO to set
     */
    public void setFindNotesSearchCriteriaVO(final FindNotesSearchCriteriaVO findNotesSearchCriteriaVO) {
        this.findNotesSearchCriteriaVO = findNotesSearchCriteriaVO;
    }

    /**
     * @return the strRmvSticky
     */
    public String getStrRmvSticky() {
        return strRmvSticky;
    }

    /**
     * @param strRmvSticky
     *            the strRmvSticky to set
     */
    public void setStrRmvSticky(final String strRmvSticky) {
        this.strRmvSticky = strRmvSticky;
    }

    /**
     * @return the strHidNotesSel
     */
    public String getStrHidNotesSel() {
        return strHidNotesSel;
    }

    /**
     * @param strHidNotesSel
     *            the strHidNotesSel to set
     */
    public void setStrHidNotesSel(final String strHidNotesSel) {
        this.strHidNotesSel = strHidNotesSel;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject())
                .append("findNotesSearchResultVO", findNotesSearchResultVO)
                .append("findNotesSearchCriteriaVO", findNotesSearchCriteriaVO).append("noteTypeList", noteTypeList)
                .append("creatorList", creatorList).append("strRmvSticky", strRmvSticky)
                .append("strHidNotesSel", strHidNotesSel).append("notesSearchList", notesSearchList).toString();
    }

}
