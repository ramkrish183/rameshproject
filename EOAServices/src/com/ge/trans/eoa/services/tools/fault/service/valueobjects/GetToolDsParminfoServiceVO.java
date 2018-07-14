/**
 * ============================================================
 * File : GetToolDsParminfoVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.tools.fault.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class GetToolDsParminfoServiceVO extends BaseVO {

    private static final long serialVersionUID = 1664661893670796406L;
    private String headerHtml;
    private String dummyHeaderHtml;
    private String columnName;

    private String info;
    private String format;
    private Double upperBound;
    private Double lowerBound;
    private String headerWidth;
    private String fixedHeaderWidthTotal;
    private String variableHeaderWidthTotal;
    private String strHeaderWidth;
    private String dataTooltipFlag;
    private String style;
    private String parmNumber;
    private String groupName;
    private String noOfHeaders;
    private String filter;
    private String charLength;

    public String getCharLength() {
        return charLength;
    }

    public void setCharLength(String charLength) {
        this.charLength = charLength;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getNoOfHeaders() {
        return noOfHeaders;
    }

    public void setNoOfHeaders(final String noOfHeaders) {
        this.noOfHeaders = noOfHeaders;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public String getParmNumber() {
        return parmNumber;
    }

    public void setParmNumber(final String parmNumber) {
        this.parmNumber = parmNumber;
    }

    private String paramNumber = null;

    /*
     * Stores the tool tip text for the header
     */
    private String toolTip = null;

    /*
     * Stores the group detail for the header
     */
    private char isHeatMap;

    /*
     * Stores the Upper Bound
     */
    private String anomInd = null;

    /*
     * Stores the Display Parm Name
     */
    private String parmName = null;

    /*
     * Stores the Sub String
     */
    private String substring = null;

    private String dataScreenFlag = null;

    /*
     * Stores the Flag for View Vehicle Fault Screen's Column availability
     */
    private String vvfFlag = null;

    /*
     * Stores the Flag for Rapid Response Screen's Column availability
     */
    private String rapidResponseFlag = null;

    /*
     * Stores the Flag for Incident Screen's Column availability
     */
    private String incidentFlag = null;

    /*
     * Stores the Flag for QNX Screen's Column availability
     */
    private String qnxColAvail = null;

    /*
     * Stores the DB Column Name for Oil Screens
     */
    private String dbColumnName = null;

    /*
     * Stores the Flag for Oil Screen's External Column availability
     */
    private String externalColAvail = null;

    /*
     * Stores the Flag for Oil Screen's Internal Column availability
     */
    private String internalColAvail = null;

    private String parmObjid = null;

    private String custID = null;

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    /**
     * 
     */
    public GetToolDsParminfoServiceVO() {
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getHeaderHtml() {
        return headerHtml;
    }

    public void setHeaderHtml(final String headerHtml) {
        this.headerHtml = headerHtml;
    }

    public String getDummyHeaderHtml() {
        return dummyHeaderHtml;
    }

    public void setDummyHeaderHtml(final String dummyHeaderHtml) {
        this.dummyHeaderHtml = dummyHeaderHtml;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(final String columnName) {
        this.columnName = columnName;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("headerHtml", headerHtml)
                .append("columnName", columnName).toString();
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     *            the info to set
     */
    public void setInfo(final String info) {
        this.info = info;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(final String format) {
        this.format = format;
    }

    /**
     * @return the upperBound
     */
    public Double getUpperBound() {
        return upperBound;
    }

    /**
     * @param upperBound
     *            the upperBound to set
     */
    public void setUpperBound(final Double upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * @return the lowerBound
     */
    public Double getLowerBound() {
        return lowerBound;
    }

    /**
     * @param lowerBound
     *            the lowerBound to set
     */
    public void setLowerBound(final Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * @return the HeaderWidth
     */
    public String getHeaderWidth() {
        return this.headerWidth;
    }

    /**
     * @param headerWidth
     *            the headerWidth to set
     */
    public void setHeaderWidth(final String headerWidth) {
        this.headerWidth = headerWidth;
    }

    /**
     * @return the fixedHeaderWidthTotal
     */
    public String getFixedHeaderWidthTotal() {
        return fixedHeaderWidthTotal;
    }

    /**
     * @param fixedHeaderWidthTotal
     *            the fixedHeaderWidthTotal to set
     */
    public void setFixedHeaderWidthTotal(final String fixedHeaderWidthTotal) {
        this.fixedHeaderWidthTotal = fixedHeaderWidthTotal;
    }

    /**
     * @return the variableHeaderWidthTotal
     */
    public String getVariableHeaderWidthTotal() {
        return variableHeaderWidthTotal;
    }

    /**
     * @param variableHeaderWidthTotal
     *            the variableHeaderWidthTotal to set
     */
    public void setVariableHeaderWidthTotal(final String variableHeaderWidthTotal) {
        this.variableHeaderWidthTotal = variableHeaderWidthTotal;
    }

    /**
     * @return the strHeaderWidth
     */
    public String getStrHeaderWidth() {
        return strHeaderWidth;
    }

    /**
     * @param strHeaderWidth
     *            the strHeaderWidth to set
     */
    public void setStrHeaderWidth(final String strHeaderWidth) {
        this.strHeaderWidth = strHeaderWidth;
    }

    /**
     * @return the dataTooltipFlag
     */
    public String getDataTooltipFlag() {
        return dataTooltipFlag;
    }

    /**
     * @param dataTooltipFlag
     *            the dataTooltipFlag to set
     */
    public void setDataTooltipFlag(final String dataTooltipFlag) {
        this.dataTooltipFlag = dataTooltipFlag;
    }

    public String getParamNumber() {
        return paramNumber;
    }

    public void setParamNumber(final String paramNumber) {
        this.paramNumber = paramNumber;
    }

    public char getIsHeatMap() {
        return isHeatMap;
    }

    public void setIsHeatMap(final char isHeatMap) {
        this.isHeatMap = isHeatMap;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setToolTip(final String toolTip) {
        this.toolTip = toolTip;
    }

    public String getAnomInd() {
        return anomInd;
    }

    public void setAnomInd(final String anomInd) {
        this.anomInd = anomInd;
    }

    public String getParmName() {
        return parmName;
    }

    public void setParmName(final String parmName) {
        this.parmName = parmName;
    }

    public String getSubstring() {
        return substring;
    }

    public void setSubstring(final String substring) {
        this.substring = substring;
    }

    public String getDataScreenFlag() {
        return dataScreenFlag;
    }

    public void setDataScreenFlag(final String dataScreenFlag) {
        this.dataScreenFlag = dataScreenFlag;
    }

    public String getVvfFlag() {
        return vvfFlag;
    }

    public void setVvfFlag(final String vvfFlag) {
        this.vvfFlag = vvfFlag;
    }

    public String getRapidResponseFlag() {
        return rapidResponseFlag;
    }

    public void setRapidResponseFlag(final String rapidResponseFlag) {
        this.rapidResponseFlag = rapidResponseFlag;
    }

    public String getIncidentFlag() {
        return incidentFlag;
    }

    public void setIncidentFlag(final String incidentFlag) {
        this.incidentFlag = incidentFlag;
    }

    public String getQnxColAvail() {
        return qnxColAvail;
    }

    public void setQnxColAvail(final String qnxColAvail) {
        this.qnxColAvail = qnxColAvail;
    }

    public String getDBColumnName() {
        return dbColumnName;
    }

    public void setDBColumnName(final String dBColumnName) {
        this.dbColumnName = dBColumnName;
    }

    public String getExternalColAvail() {
        return externalColAvail;
    }

    public void setExternalColAvail(final String externalColAvail) {
        this.externalColAvail = externalColAvail;
    }

    public String getInternalColAvail() {
        return internalColAvail;
    }

    public void setInternalColAvail(final String internalColAvail) {
        this.internalColAvail = internalColAvail;
    }

    public String getParmObjid() {
        return parmObjid;
    }

    public void setParmObjid(final String parmObjid) {
        this.parmObjid = parmObjid;
    }

}
