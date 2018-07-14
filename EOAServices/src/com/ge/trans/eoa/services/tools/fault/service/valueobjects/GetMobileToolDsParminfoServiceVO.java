package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class GetMobileToolDsParminfoServiceVO {

    private static final long serialVersionUID = 1664661893670796406L;
    private String headerHtml;
    private String columnName;
    private String info;
    private String format;
    private Double upperBound;
    private Double lowerBound;
    private String headerWidth;
    private String strHeaderWidth;
    private String dataTooltipFlag;
    private String groupName;
    private String charLength;
    private String filter;
    private String style;
    private String toolTip = null;
    private char isHeatMap;
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
    public GetMobileToolDsParminfoServiceVO() {
    }


    public String getHeaderHtml() {
        return headerHtml;
    }

    public void setHeaderHtml(final String headerHtml) {
        this.headerHtml = headerHtml;
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


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public String getStyle() {
		return style;
	}

	public void setStyle(final String style) {
		this.style = style;
	}

	public static ToStringStyle getToStringStyleObject() {
        StandardToStringStyle stdToStringStyle = new StandardToStringStyle();
        stdToStringStyle.setUseIdentityHashCode(false);
        stdToStringStyle.setContentStart("{");
        stdToStringStyle.setContentEnd("}");
        stdToStringStyle.setFieldSeparator(";");
        return stdToStringStyle;
    }

}
