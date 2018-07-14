/**
 * ============================================================
 * File : SimpleRuleServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

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
public class SimpleRuleServiceVO extends BaseVO implements Cloneable {

    static final long serialVersionUID = 1392452224898L;
    private Long simpleSequenceId;
    private String name;
    private String strFaultCode;
    private String strFaultDesc;
    private String strSubID;
    private String strControllerid;
    private String strDataPackElement;
    private String strSimpleRuleId;
    private ArrayList<SimpleRuleClauseServiceVO> arlSimpleRuleClauseVO;
    private ArrayList<SimpleRuleServiceVO> arlSubID;
    private ArrayList<SimpleRuleServiceVO> arlFunctionList;
    private String strPixels;
    private List<String> pixelsMultifaults;
    private List<String> notesMultifaults;
    private String notes;

    /**
     * @param strFaultCode
     * @param strSubID
     * @param arlSimpleRuleClauseVO
     */
    public SimpleRuleServiceVO(String strFaultCode, String strSubID,
            ArrayList<SimpleRuleClauseServiceVO> arlSimpleRuleClauseVO) {
        super();
        this.strFaultCode = strFaultCode;
        this.strSubID = strSubID;
        this.arlSimpleRuleClauseVO = arlSimpleRuleClauseVO;
    }

    /**
     * @param simpleSequenceId
     * @param name
     */
    public SimpleRuleServiceVO(Long simpleSequenceId, String name) {
        super();
        this.simpleSequenceId = simpleSequenceId;
        this.name = name;
    }

    /**
     * @return the arlFunctionList
     */
    public ArrayList<SimpleRuleServiceVO> getArlFunctionList() {
        return arlFunctionList;
    }

    /**
     * @param arlFunctionList
     *            the arlFunctionList to set
     */
    public void setArlFunctionList(ArrayList<SimpleRuleServiceVO> arlFunctionList) {
        this.arlFunctionList = arlFunctionList;
    }

    /**
     * @return the arlSubID
     */
    public ArrayList<SimpleRuleServiceVO> getArlSubID() {
        return arlSubID;
    }

    public SimpleRuleServiceVO() {
    }

    /**
     * @param arlSubID
     *            the arlSubID to set
     */
    public void setArlSubID(ArrayList<SimpleRuleServiceVO> arlSubID) {
        this.arlSubID = arlSubID;
    }

    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }

    /**
     * @param strFaultCode
     *            the strFaultCode to set
     */
    public void setStrFaultCode(String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /**
     * @return the strSubID
     */
    public String getStrSubID() {
        return strSubID;
    }

    /**
     * @param strSubID
     *            the strSubID to set
     */
    public void setStrSubID(String strSubID) {
        this.strSubID = strSubID;
    }

    public String getStrControllerid() {
        return strControllerid;
    }

    public void setStrControllerid(String strControllerid) {
        this.strControllerid = strControllerid;
    }

    /**
     * @return the arlSimpleRuleClauseVO
     */
    public ArrayList<SimpleRuleClauseServiceVO> getArlSimpleRuleClauseVO() {
        return arlSimpleRuleClauseVO;
    }

    /**
     * @param arlSimpleRuleClauseVO
     *            the arlSimpleRuleClauseVO to set
     */
    public void setArlSimpleRuleClauseVO(ArrayList<SimpleRuleClauseServiceVO> arlSimpleRuleClauseVO) {
        this.arlSimpleRuleClauseVO = arlSimpleRuleClauseVO;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getStrDataPackElement() {
        return strDataPackElement;
    }

    public void setStrDataPackElement(String strDataPackElement) {
        this.strDataPackElement = strDataPackElement;
    }

    /**
     * @return the simpleSequenceId
     */
    public Long getSimpleSequenceId() {
        return simpleSequenceId;
    }

    /**
     * @param simpleSequenceId
     *            the simpleSequenceId to set
     */
    public void setSimpleSequenceId(Long simpleSequenceId) {
        this.simpleSequenceId = simpleSequenceId;
    }

    public void setStrSimpleRuleId(String strSimpleRuleId) {
        this.strSimpleRuleId = strSimpleRuleId;
    }

    public String getStrSimpleRuleId() {
        return strSimpleRuleId;
    }

    /**
     * @return the strFaultDesc
     */
    public String getStrFaultDesc() {
        return strFaultDesc;
    }

    /**
     * @param strFaultDesc
     *            the strFaultDesc to set
     */
    public void setStrFaultDesc(String strFaultDesc) {
        this.strFaultDesc = strFaultDesc;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("simpleSequenceId", simpleSequenceId)
                .append("name", name).append("strFaultCode", strFaultCode).append("strFaultDesc", strFaultDesc)
                .append("strSubID", strSubID).append("strDataPackElement", strDataPackElement)
                .append("strSimpleRuleId", strSimpleRuleId).append("arlSimpleRuleClauseVO", arlSimpleRuleClauseVO)
                .append("arlSubID", arlSubID).append("arlFunctionList", arlFunctionList).toString();
    }

    public String getStrPixels() {
        return strPixels;
    }

    public void setStrPixels(String strPixels) {
        this.strPixels = strPixels;
    }

    public List<String> getPixelsMultifaults() {
        return pixelsMultifaults;
    }

    public void setPixelsMultifaults(List<String> pixelsMultifaults) {
        this.pixelsMultifaults = pixelsMultifaults;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getNotesMultifaults() {
        return notesMultifaults;
    }

    public void setNotesMultifaults(List<String> notesMultifaults) {
        this.notesMultifaults = notesMultifaults;
    }

    @Override
    public Object clone() {
        // shallow copy
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
