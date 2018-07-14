package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

@SuppressWarnings("unchecked")
public class FaultMobileDataDetailsServiceVO  {

    static final long serialVersionUID = 86249531L;
    private ArrayList arlHeader = null;
    private List arlGrpHeader = null;
    private FaultMobileServiceVO objFaultServiceVO = null;
    private int intTotalRecsAvail = 0;
    private String recordsTotal;
    private String ctrlCfg;
    private String caseSortOrder;
    private String caseLookBackDays;
    private String partStatus;
    private String strFixedHeaderWidth;
    private String strHeaderWidth;
    private String fixedHeaderWidthTotal;
    private String variableHeaderWidthTotal;

    public String getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(String recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public String getCaseSortOrder() {
        return caseSortOrder;
    }

    public void setCaseSortOrder(String caseSortOrder) {
        this.caseSortOrder = caseSortOrder;
    }

    public String getCaseLookBackDays() {
        return caseLookBackDays;
    }

    public void setCaseLookBackDays(String caseLookBackDays) {
        this.caseLookBackDays = caseLookBackDays;
    }

    public String getCtrlCfg() {
        return ctrlCfg;
    }

    public void setCtrlCfg(String ctrlCfg) {
        this.ctrlCfg = ctrlCfg;
    }

    public String getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
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
     * @return the arlHeader
     */
    public ArrayList getArlHeader() {
        return arlHeader;
    }

    /**
     * @param arlHeader
     *            the arlHeader to set
     */
    public void setArlHeader(final ArrayList arlHeader) {
        this.arlHeader = arlHeader;
    }

    /**
     * @return the objFaultServiceVO
     */
    public FaultMobileServiceVO getObjFaultServiceVO() {
        return objFaultServiceVO;
    }

    /**
     * @param objFaultServiceVO
     *            the objFaultServiceVO to set
     */
    public void setObjFaultServiceVO(final FaultMobileServiceVO objFaultServiceVO) {
        this.objFaultServiceVO = objFaultServiceVO;
    }

    /**
     * @return the strFixedHeaderWidth
     */
    public String getStrFixedHeaderWidth() {
        return strFixedHeaderWidth;
    }

    /**
     * @param strFixedHeaderWidth
     *            the strFixedHeaderWidth to set
     */
    public void setStrFixedHeaderWidth(final String strFixedHeaderWidth) {
        this.strFixedHeaderWidth = strFixedHeaderWidth;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlHeader", arlHeader)
                .append("objFaultServiceVO", objFaultServiceVO).toString();
    }

    public int getIntTotalRecsAvail() {
        return intTotalRecsAvail;
    }

    public void setIntTotalRecsAvail(final int intTotalRecsAvail) {
        this.intTotalRecsAvail = intTotalRecsAvail;
    }

    /**
     * @return the arlGrpHeader
     */
    public List getArlGrpHeader() {
        return arlGrpHeader;
    }

    /**
     * @param arlGrpHeader
     *            the arlGrpHeader to set
     */
    public void setArlGrpHeader(final List arlGrpHeader) {
        this.arlGrpHeader = arlGrpHeader;
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
