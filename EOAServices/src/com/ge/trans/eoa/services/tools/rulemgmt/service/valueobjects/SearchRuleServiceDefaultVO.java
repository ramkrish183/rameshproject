/**
 * ============================================================
 * File : SearchRuleServiceDefaultVO.java
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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 1, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class SearchRuleServiceDefaultVO extends BaseVO {

    static final long serialVersionUID = 6238112L;
    private String id;
    private String name;
    private String strRecommendation;
    private String strFaultCode;
    private ArrayList arlSubSystemList;
    private ArrayList arlCreatedByList;
    private ArrayList arlActivatedByList;
    private ArrayList arlRXList;
    private ArrayList arlFaultCodeList;

    public SearchRuleServiceDefaultVO(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     *
     */
    public SearchRuleServiceDefaultVO() {
        super();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * @return the strRecommendation
     */
    public String getStrRecommendation() {
        return strRecommendation;
    }

    /**
     * @param strRecommendation
     *            the strRecommendation to set
     */
    public void setStrRecommendation(String strRecommendation) {
        this.strRecommendation = strRecommendation;
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
     * @return the arlSubSystemList
     */
    public ArrayList getArlSubSystemList() {
        return arlSubSystemList;
    }

    /**
     * @param arlSubSystemList
     *            the arlSubSystemList to set
     */
    public void setArlSubSystemList(ArrayList arlSubSystemList) {
        this.arlSubSystemList = arlSubSystemList;
    }

    /**
     * @return the arlCreatedByList
     */
    public ArrayList getArlCreatedByList() {
        return arlCreatedByList;
    }

    /**
     * @param arlCreatedByList
     *            the arlCreatedByList to set
     */
    public void setArlCreatedByList(ArrayList arlCreatedByList) {
        this.arlCreatedByList = arlCreatedByList;
    }

    /**
     * @return the arlActivatedByList
     */
    public ArrayList getArlActivatedByList() {
        return arlActivatedByList;
    }

    /**
     * @param arlActivatedByList
     *            the arlActivatedByList to set
     */
    public void setArlActivatedByList(ArrayList arlActivatedByList) {
        this.arlActivatedByList = arlActivatedByList;
    }

    /**
     * @return the arlRXList
     */
    public ArrayList getArlRXList() {
        return arlRXList;
    }

    /**
     * @param arlRXList
     *            the arlRXList to set
     */
    public void setArlRXList(ArrayList arlRXList) {
        this.arlRXList = arlRXList;
    }

    /**
     * @return the arlFaultCodeList
     */
    public ArrayList getArlFaultCodeList() {
        return arlFaultCodeList;
    }

    /**
     * @param arlFaultCodeList
     *            the arlFaultCodeList to set
     */
    public void setArlFaultCodeList(ArrayList arlFaultCodeList) {
        this.arlFaultCodeList = arlFaultCodeList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("id", id).append("name", name)
                .append("strRecommendation", strRecommendation).append("strFaultCode", strFaultCode)
                .append("arlSubSystemList", arlSubSystemList).append("arlCreatedByList", arlCreatedByList)
                .append("arlActivatedByList", arlActivatedByList).append("arlRXList", arlRXList)
                .append("arlFaultCodeList", arlFaultCodeList).toString();
    }
}
