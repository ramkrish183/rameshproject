package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * Title:		FaultHeaderDetailTVO.java
 * 
 * Description:	Used to store the fault header deails and transfer the data
 * 				between across layers.
 * 
 * Copyright:   Copyright (c) 2001
 * 
 * Company: 	General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author		:	iGate		
 * @Created Date:	1-Feb-08
 * @version		:	1.0
 * 				
 * Revision History		  :			
 * Last Modification Date :
 * Last Modified By       :
 * Last Modification      :
 * 
 ******************************************************************************/

import java.io.Serializable;

/*
 * This class stores the header details for the particular controller
 */
public class FaultHeaderDetailVO implements Serializable, Cloneable {

    /*
     * Stores the param number
     */
    private String strParamNumber = null;

    /*
     * Stores the header description
     */
    private String strHeaderDesc = null;

    /*
     * Stores the tool tip text for the header
     */
    private String strToolTip = null;

    /*
     * Stores the group detail for the header
     */
    private char isHeatMap;

    /*
     * Stores the width for the header which is used to set the cell width
     */
    private String strHeaderWidth = null;

    /*
     * Stores the Column Name
     */
    private String strColumnName = null;

    /*
     * Stores the Upper Bound
     */
    private String strUpperBound = null;

    /*
     * Stores the Upper Bound
     */
    private String strLowerBound = null;

    /*
     * Stores the Upper Bound
     */
    private String strAnomInd = null;

    /*
     * Stores the Display Parm Name
     */
    private String strParmName = null;

    /*
     * Stores the Sub String
     */
    private String strSubstring = null;

    /*
     * Stores the Data Tool Tip Flag value
     */
    private String strDataToolTipFlag = null;

    /*
     * Stores the Data format value
     */
    private String strFormat = null;

    /*
     * Stores the Data Style value
     */
    private String strStyle = null;

    /*
     * Stores the Flag for Data Screen's Column availability
     */
    private String strDataScreenColAvail = null;

    /*
     * Stores the Flag for View Vehicle Fault Screen's Column availability
     */
    private String strVvfColAvail = null;

    /*
     * Stores the Flag for Rapid Response Screen's Column availability
     */
    private String strRapidRespColAvail = null;

    /*
     * Stores the Flag for Incident Screen's Column availability
     */
    private String strIncidentColAvail = null;

    /*
     * Stores the Flag for QNX Screen's Column availability
     */
    private String strQnxColAvail = null;

    /*
     * Stores the DB Column Name for Oil Screens
     */
    private String strDBColumnName = null;

    /*
     * Stores the Flag for Oil Screen's External Column availability
     */
    private String strExternalColAvail = null;

    /*
     * Stores the Flag for Oil Screen's Internal Column availability
     */
    private String strInternalColAvail = null;

    private String strParmObjid = null;

    private String strDhmsColAvail = null;

    private String strCustID = null;

    private String charLength = null;

    public String getCharLength() {
        return charLength;
    }

    public void setCharLength(String charLength) {
        this.charLength = charLength;
    }

    public String getStrCustID() {
        return strCustID;
    }

    public void setStrCustID(String strCustID) {
        this.strCustID = strCustID;
    }

    public String getStrDhmsColAvail() {
        return strDhmsColAvail;
    }

    public void setStrDhmsColAvail(String strDhmsColAvail) {
        this.strDhmsColAvail = strDhmsColAvail;
    }

    /**
     * @return the strParmObjid
     */
    public String getParmObjid() {
        return strParmObjid;
    }

    /**
     * @param strParmObjid
     *            the strParmObjid to set
     */
    public void setParmObjid(final String strParmObjid) {
        this.strParmObjid = strParmObjid;
    }

    /*
     * Gets the header description
     */
    public String getHeaderDesc() {
        return strHeaderDesc;
    }

    /*
     * Sets the header description
     */
    public void setHeaderDesc(final String strHeaderDesc) {
        this.strHeaderDesc = strHeaderDesc;
    }

    /*
     * Gets the header tool tip text
     */
    public String getToolTip() {
        return strToolTip;
    }

    /*
     * Sets the header tool tip text
     */
    public void setToolTip(final String strToolTip) {
        this.strToolTip = strToolTip;
    }

    /*
     * Gets the width for the header
     */
    public String getHeaderWidth() {
        return strHeaderWidth;
    }

    /*
     * Sets the width for the header
     */
    public void setHeaderWidth(final String strHeaderWidth) {
        this.strHeaderWidth = strHeaderWidth;
    }

    /*
     * Gets the heap map value for the header
     */
    public char getIsHeatMap() {
        return isHeatMap;
    }

    /*
     * Sets the heap map value for the header
     */
    public void setIsHeatMap(final char isHeatMap) {
        this.isHeatMap = isHeatMap;
    }

    /*
     * Gets the heap map value for the header
     */
    public String getParamNumber() {
        return strParamNumber;
    }

    /*
     * Sets the header
     */
    public void setParamNumber(final String strParamNumber) {
        this.strParamNumber = strParamNumber;
    }

    /*
     * Gets the column Name
     */
    public String getColumnName() {
        return strColumnName;
    }

    /*
     * Sets the column Name
     */
    public void setColumnName(final String strColumnName) {
        this.strColumnName = strColumnName;
    }

    /*
     * Gets the upper bound
     */
    public String getUpperBound() {
        return strUpperBound;
    }

    /*
     * Sets the upper bound
     */
    public void setUpperBound(final String strUpperBound) {
        this.strUpperBound = strUpperBound;
    }

    /*
     * Gets the upper bound
     */
    public String getLowerBound() {
        return strLowerBound;
    }

    /*
     * Sets the upper bound
     */
    public void setLowerBound(final String strLowerBound) {
        this.strLowerBound = strLowerBound;
    }

    /*
     * Gets the upper bound
     */
    public String getAnomInd() {
        return strAnomInd;
    }

    /*
     * Sets the upper bound
     */
    public void setAnomInd(final String strAnomInd) {
        this.strAnomInd = strAnomInd;
    }

    /*
     * Gets the upper bound
     */
    public String getParmName() {
        return strParmName;
    }

    /*
     * Sets the upper bound
     */
    public void setParmName(final String strParmName) {
        this.strParmName = strParmName;
    }

    /*
     * Gets the upper bound
     */
    public String getSubstring() {
        return strSubstring;
    }

    /*
     * Sets the upper bound
     */
    public void setSubstring(final String strSubstring) {
        this.strSubstring = strSubstring;
    }

    /*
     * Gets the Data ToolTip Flag value
     */
    public String getDataToolTipFlag() {
        return strDataToolTipFlag;
    }

    /*
     * Sets the upper bound
     */
    public void setDataToolTipFlag(final String strDataToolTipFlag) {
        this.strDataToolTipFlag = strDataToolTipFlag;
    }

    /*
     * Gets the Format
     */
    public String getFormat() {
        return strFormat;
    }

    /*
     * Sets the Format
     */
    public void setFormat(final String strFormat) {
        this.strFormat = strFormat;
    }

    /*
     * Gets the Style
     */
    public String getStyle() {
        return strStyle;
    }

    /*
     * Sets the Style
     */
    public void setStyle(final String strStyle) {
        this.strStyle = strStyle;
    }

    /*
     * Gets the Flag for Data Screen's Column availability
     */
    public String getDataScreenFlag() {
        return strDataScreenColAvail;
    }

    /*
     * Sets the Flag for Data Screen's Column availability
     */
    public void setDataScreenFlag(final String strDataScreenColAvail) {
        this.strDataScreenColAvail = strDataScreenColAvail;
    }

    /*
     * Gets the Flag for View Vehicle Fault Screen's Column availability
     */
    public String getVvfFlag() {
        return strVvfColAvail;
    }

    /*
     * Sets the Flag for View Vehicle Fault Screen's Column availability
     */
    public void setVvfFlag(final String strVvfColAvail) {
        this.strVvfColAvail = strVvfColAvail;
    }

    /*
     * Gets the Flag for Rapid Response Screen's Column availability
     */
    public String getRapidResponseFlag() {
        return strRapidRespColAvail;
    }

    /*
     * Sets the Flag for Rapid Response Screen's Column availability
     */
    public void setRapidResponseFlag(final String strRapidRespColAvail) {
        this.strRapidRespColAvail = strRapidRespColAvail;
    }

    /*
     * Gets the Flag for Incident Screen's Column availability
     */
    public String getIncidentFlag() {
        return strIncidentColAvail;
    }

    /*
     * Sets the Flag for Incident Screen's Column availability
     */
    public void setIncidentFlag(final String strIncidentColAvail) {
        this.strIncidentColAvail = strIncidentColAvail;
    }

    @Override
    public Object clone() {
        try {

            FaultHeaderDetailVO objFaultHeaderDetailVO = (FaultHeaderDetailVO) super.clone();
            return objFaultHeaderDetailVO;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

    /*
     * Gets the Flag for QNX Screen's Column availability
     */
    public String getQnxColAvail() {
        return strQnxColAvail;
    }

    /*
     * Sets the Flag for QNX Screen's Column availability
     */
    public void setQnxColAvail(final String strQnxColAvail) {
        this.strQnxColAvail = strQnxColAvail;
    }

    /*
     * Gets the DB ColumnName for Oil Screens
     */
    public String getDBColumnName() {
        return strDBColumnName;
    }

    /*
     * Sets the DB ColumnName for Oil Screens
     */
    public void setDBColumnName(final String strDBColumnName) {
        this.strDBColumnName = strDBColumnName;
    }

    /*
     * Gets the Flag for Oil Screen's External Column availability
     */
    public String getExternalColAvail() {
        return strExternalColAvail;
    }

    /*
     * Sets the Flag for Oil Screen's External Column availability
     */
    public void setExternalColAvail(final String strExternalColAvail) {
        this.strExternalColAvail = strExternalColAvail;
    }

    /*
     * Gets the Flag for Oil Screen's Internal Column availability
     */
    public String getInternalColAvail() {
        return strInternalColAvail;
    }

    /*
     * Sets the Flag for Oil Screen's Internal Column availability
     */
    public void setInternalColAvail(final String strInternalColAvail) {
        this.strInternalColAvail = strInternalColAvail;
    }
}