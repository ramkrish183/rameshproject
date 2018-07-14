/**
 * ============================================================
 * File : ReportBOImpl.java
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

import java.util.Date;

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
public class RxRepSearCritServiceVO extends BaseVO {
    static final long serialVersionUID = 4111782789L;
    private String strRxTitle;
    private Date dtCreationDate;
    private Date dtLastUpdDate;
    private String strSortBy;
    private String strVersion;
    private String strRxId;

    /**
     * @return the dtCreationDate
     */
    public Date getDtCreationDate() {
        return dtCreationDate;
    }

    /**
     * @param dtCreationDate
     *            the dtCreationDate to set
     */
    public void setDtCreationDate(Date dtCreationDate) {
        this.dtCreationDate = dtCreationDate;
    }

    /**
     * @return the dtLastUpdDate
     */
    public Date getDtLastUpdDate() {
        return dtLastUpdDate;
    }

    /**
     * @param dtLastUpdDate
     *            the dtLastUpdDate to set
     */
    public void setDtLastUpdDate(Date dtLastUpdDate) {
        this.dtLastUpdDate = dtLastUpdDate;
    }

    public RxRepSearCritServiceVO() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the strSortBy
     */
    public String getStrSortBy() {
        return strSortBy;
    }

    /**
     * @param strSortBy
     *            the strSortBy to set
     */
    public void setStrSortBy(String strSortBy) {
        this.strSortBy = strSortBy;
    }

    /**
     * @return the strRxTitle
     */
    public String getStrRxTitle() {
        return strRxTitle;
    }

    /**
     * @param strRxTitle
     *            the strRxTitle to set
     */
    public void setStrRxTitle(String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxTitle", strRxTitle)
                .append("dtCreationDate", dtCreationDate).append("dtLastUpdDate", dtLastUpdDate)
                .append("strSortBy", strSortBy).toString();
    }

    /**
     * @return the strVersion
     */
    public String getStrVersion() {
        return strVersion;
    }

    /**
     * @param strVersion
     *            the strVersion to set
     */
    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
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
}
