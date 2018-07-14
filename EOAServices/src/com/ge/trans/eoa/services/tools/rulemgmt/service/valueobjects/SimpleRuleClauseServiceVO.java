/**
 * ============================================================
 * File : SimpleRuleClauseServiceVO.java
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
public class SimpleRuleClauseServiceVO extends BaseVO implements Cloneable {

    static final long serialVersionUID = 787749933562L;
    private String strParamName;
    private String strColumnName;
    private String strFunction;
    private String strValue1;
    private String strValue2;
    private String strColumnId;
    private String strColumnType;
    private String strSeqVal;
    private String anomTechniqueId;
    private String strPixels;
    private String strClauseId;
    private String notes;
	private String uomAbbr;
	
	public String getUomAbbr() {
		return uomAbbr;
	}
	public void setUomAbbr(String uomAbbr) {
		this.uomAbbr = uomAbbr;
	}
    /**
     * @param strColumnName
     * @param strFunction
     * @param strValue1
     * @param strValue2
     */
    public SimpleRuleClauseServiceVO(String strColumnName, String strFunction, String strValue1, String strValue2) {
        super();
        this.strColumnName = strColumnName;
        this.strFunction = strFunction;
        this.strValue1 = strValue1;
        this.strValue2 = strValue2;
    }

    public String getStrColumnId() {
        return strColumnId;
    }

    public void setStrColumnId(String strColumnId) {
        this.strColumnId = strColumnId;
    }

    public String getStrColumnType() {
        return strColumnType;
    }

    public void setStrColumnType(String strColumnType) {
        this.strColumnType = strColumnType;
    }

    public String getStrSeqVal() {
        return strSeqVal;
    }

    public void setStrSeqVal(String strSeqVal) {
        this.strSeqVal = strSeqVal;
    }

    public String getAnomTechniqueId() {
        return anomTechniqueId;
    }

    public void setAnomTechniqueId(String anomTechniqueId) {
        this.anomTechniqueId = anomTechniqueId;
    }

    public SimpleRuleClauseServiceVO() {
        super();
    }

    /**
     * @return the strColumnName
     */
    public String getStrColumnName() {
        return strColumnName;
    }

    /**
     * @param strColumnName
     *            the strColumnName to set
     */
    public void setStrColumnName(String strColumnName) {
        this.strColumnName = strColumnName;
    }

    /**
     * @return the strFunction
     */
    public String getStrFunction() {
        return strFunction;
    }

    /**
     * @param strFunction
     *            the strFunction to set
     */
    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    /**
     * @return the strValue1
     */
    public String getStrValue1() {
        return strValue1;
    }

    /**
     * @param strValue1
     *            the strValue1 to set
     */
    public void setStrValue1(String strValue1) {
        this.strValue1 = strValue1;
    }

    /**
     * @return the strValue2
     */
    public String getStrValue2() {
        return strValue2;
    }

    /**
     * @param strValue2
     *            the strValue2 to set
     */
    public void setStrValue2(String strValue2) {
        this.strValue2 = strValue2;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strColumnName", strColumnName)
                .append("strFunction", strFunction).append("strValue1", strValue1).append("strValue2", strValue2)
                .toString();
    }

    public String getStrPixels() {
        return strPixels;
    }

    public void setStrPixels(String strPixels) {
        this.strPixels = strPixels;
    }

    public String getStrParamName() {
        return strParamName;
    }

    public void setStrParamName(String strParamName) {
        this.strParamName = strParamName;
    }

    public String getStrClauseId() {
        return strClauseId;
    }

    public void setStrClauseId(String strClauseId) {
        this.strClauseId = strClauseId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
