/**
 * ============================================================
 * File : RxRepSearResServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.reports.service.valueobjects
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
package com.ge.trans.eoa.services.reports.service.valueobjects;

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
public class RxRepSearResServiceVO extends BaseVO {

    static final long serialVersionUID = 4689758L;
    private String strTitle;
    private String strRxId;
    private String strToolSugg;
    private String strToolSuggDelv;
    private String strToolSuggAccu;
    private String strDelvAccu;
    private String strToolAccuPct;
    private String strToolCovgPct;
    private String strMdscDelv;
    private String strMdscDelvAccu;
    private String strMdscfalseAlarm;
    private String strMdscAccuPct;
    private String strMissCount;

    public RxRepSearResServiceVO() {
        super();
    }

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle
     *            the strTitle to set
     */
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * @return the strRxId
     */
    public String getStrRxId() {
        return strRxId;
    }

    /**
     * @param strRxId
     *            the strRxId to set
     */
    public void setStrRxId(String strRxId) {
        this.strRxId = strRxId;
    }

    /**
     * @return the strToolSugg
     */
    public String getStrToolSugg() {
        return strToolSugg;
    }

    /**
     * @param strToolSugg
     *            the strToolSugg to set
     */
    public void setStrToolSugg(String strToolSugg) {
        this.strToolSugg = strToolSugg;
    }

    /**
     * @return the strToolSuggDelv
     */
    public String getStrToolSuggDelv() {
        return strToolSuggDelv;
    }

    /**
     * @param strToolSuggDelv
     *            the strToolSuggDelv to set
     */
    public void setStrToolSuggDelv(String strToolSuggDelv) {
        this.strToolSuggDelv = strToolSuggDelv;
    }

    /**
     * @return the strToolSuggAccu
     */
    public String getStrToolSuggAccu() {
        return strToolSuggAccu;
    }

    /**
     * @param strToolSuggAccu
     *            the strToolSuggAccu to set
     */
    public void setStrToolSuggAccu(String strToolSuggAccu) {
        this.strToolSuggAccu = strToolSuggAccu;
    }

    /**
     * @return the strDelvAccu
     */
    public String getStrDelvAccu() {
        return strDelvAccu;
    }

    /**
     * @param strDelvAccu
     *            the strDelvAccu to set
     */
    public void setStrDelvAccu(String strDelvAccu) {
        this.strDelvAccu = strDelvAccu;
    }

    /**
     * @return the strToolAccuPct
     */
    public String getStrToolAccuPct() {
        return strToolAccuPct;
    }

    /**
     * @param strToolAccuPct
     *            the strToolAccuPct to set
     */
    public void setStrToolAccuPct(String strToolAccuPct) {
        this.strToolAccuPct = strToolAccuPct;
    }

    /**
     * @return the strToolCovgPct
     */
    public String getStrToolCovgPct() {
        return strToolCovgPct;
    }

    /**
     * @param strToolCovgPct
     *            the strToolCovgPct to set
     */
    public void setStrToolCovgPct(String strToolCovgPct) {
        this.strToolCovgPct = strToolCovgPct;
    }

    /**
     * @return the strMdscDelv
     */
    public String getStrMdscDelv() {
        return strMdscDelv;
    }

    /**
     * @param strMdscDelv
     *            the strMdscDelv to set
     */
    public void setStrMdscDelv(String strMdscDelv) {
        this.strMdscDelv = strMdscDelv;
    }

    /**
     * @return the strMdscDelvAccu
     */
    public String getStrMdscDelvAccu() {
        return strMdscDelvAccu;
    }

    /**
     * @param strMdscDelvAccu
     *            the strMdscDelvAccu to set
     */
    public void setStrMdscDelvAccu(String strMdscDelvAccu) {
        this.strMdscDelvAccu = strMdscDelvAccu;
    }

    /**
     * @return the strMdscfalseAlarm
     */
    public String getStrMdscfalseAlarm() {
        return strMdscfalseAlarm;
    }

    /**
     * @param strMdscfalseAlarm
     *            the strMdscfalseAlarm to set
     */
    public void setStrMdscfalseAlarm(String strMdscfalseAlarm) {
        this.strMdscfalseAlarm = strMdscfalseAlarm;
    }

    /**
     * @return the strMdscAccuPct
     */
    public String getStrMdscAccuPct() {
        return strMdscAccuPct;
    }

    /**
     * @param strMdscAccuPct
     *            the strMdscAccuPct to set
     */
    public void setStrMdscAccuPct(String strMdscAccuPct) {
        this.strMdscAccuPct = strMdscAccuPct;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strTitle", strTitle)
                .append("strRxId", strRxId).append("strToolSugg", strToolSugg)
                .append("strToolSuggDelv", strToolSuggDelv).append("strToolSuggAccu", strToolSuggAccu)
                .append("strDelvAccu", strDelvAccu).append("strToolAccuPct", strToolAccuPct)
                .append("strToolCovgPct", strToolCovgPct).append("strMdscDelv", strMdscDelv)
                .append("strMdscDelvAccu", strMdscDelvAccu).append("strMdscfalseAlarm", strMdscfalseAlarm)
                .append("strMdscAccuPct", strMdscAccuPct).toString();
    }

    /**
     * @return the strMissCount
     */
    public String getStrMissCount() {
        return strMissCount;
    }

    /**
     * @param strMissCount
     *            the strMissCount to set
     */
    public void setStrMissCount(String strMissCount) {
        this.strMissCount = strMissCount;
    }
}
