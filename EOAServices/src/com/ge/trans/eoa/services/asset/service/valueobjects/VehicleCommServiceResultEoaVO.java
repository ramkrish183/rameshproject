/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommServiceResultVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 8, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 8, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VehicleCommServiceResultEoaVO extends BaseVO {

    private static final long serialVersionUID = -1251361747721387829L;
    private Date dtLastFaultResetTime;
    private Date dtLastFaultReserved;
    private Date dtLastHealthChkRequest;
    private Date dtLastKeepAliveMsgRevd;
    private Date dtLastProcessedDate;
    private String strControllerConfig;
    private String strSoftwareVersion;
    private String strStatusColor;
    private String strReportStatus;
    private String strAssetNumber;
    private String strCustomerId;
    private String strAssetGrpName;
    private ArrayList<KeepAliveHisServiceVO> arlKeepAliveHisServiceVO;

    /**
     * @return the dtLastFaultResetTime
     */
    public Date getDtLastFaultResetTime() {
        return dtLastFaultResetTime;
    }

    /**
     * @param dtLastFaultResetTime
     *            the dtLastFaultResetTime to set
     */
    public void setDtLastFaultResetTime(Date dtLastFaultResetTime) {
        this.dtLastFaultResetTime = dtLastFaultResetTime;
    }

    /**
     * @return the dtLastFaultReserved
     */
    public Date getDtLastFaultReserved() {
        return dtLastFaultReserved;
    }

    /**
     * @param dtLastFaultReserved
     *            the dtLastFaultReserved to set
     */
    public void setDtLastFaultReserved(Date dtLastFaultReserved) {
        this.dtLastFaultReserved = dtLastFaultReserved;
    }

    /**
     * @return the dtLastHealthChkRequest
     */
    public Date getDtLastHealthChkRequest() {
        return dtLastHealthChkRequest;
    }

    /**
     * @param dtLastHealthChkRequest
     *            the dtLastHealthChkRequest to set
     */
    public void setDtLastHealthChkRequest(Date dtLastHealthChkRequest) {
        this.dtLastHealthChkRequest = dtLastHealthChkRequest;
    }

    /**
     * @return the dtLastKeepAliveMsgRevd
     */
    public Date getDtLastKeepAliveMsgRevd() {
        return dtLastKeepAliveMsgRevd;
    }

    /**
     * @param dtLastKeepAliveMsgRevd
     *            the dtLastKeepAliveMsgRevd to set
     */
    public void setDtLastKeepAliveMsgRevd(Date dtLastKeepAliveMsgRevd) {
        this.dtLastKeepAliveMsgRevd = dtLastKeepAliveMsgRevd;
    }

    /**
     * @return the strControllerConfig
     */
    public String getStrControllerConfig() {
        return strControllerConfig;
    }

    /**
     * @param strControllerConfig
     *            the strControllerConfig to set
     */
    public void setStrControllerConfig(String strControllerConfig) {
        this.strControllerConfig = strControllerConfig;
    }

    /**
     * @return the strSoftwareVersion
     */
    public String getStrSoftwareVersion() {
        return strSoftwareVersion;
    }

    /**
     * @param strSoftwareVersion
     *            the strSoftwareVersion to set
     */
    public void setStrSoftwareVersion(String strSoftwareVersion) {
        this.strSoftwareVersion = strSoftwareVersion;
    }

    /**
     * @return the arlKeepAliveHisServiceVO
     */
    public ArrayList<KeepAliveHisServiceVO> getArlKeepAliveHisServiceVO() {
        return arlKeepAliveHisServiceVO;
    }

    /**
     * @param arlKeepAliveHisServiceVO
     *            the arlKeepAliveHisServiceVO to set
     */
    public void setArlKeepAliveHisServiceVO(ArrayList<KeepAliveHisServiceVO> arlKeepAliveHisServiceVO) {
        this.arlKeepAliveHisServiceVO = arlKeepAliveHisServiceVO;
    }

    /**
     * @return the strStatusColor
     */
    public String getStrStatusColor() {
        return strStatusColor;
    }

    /**
     * @param strStatusColor
     *            the strStatusColor to set
     */
    public void setStrStatusColor(String strStatusColor) {
        this.strStatusColor = strStatusColor;
    }

    /**
     * @return the strReportStatus
     */
    public String getStrReportStatus() {
        return strReportStatus;
    }

    /**
     * @param strReportStatus
     *            the strReportStatus to set
     */
    public void setStrReportStatus(String strReportStatus) {
        this.strReportStatus = strReportStatus;
    }

    public Date getDtLastProcessedDate() {
        return dtLastProcessedDate;
    }

    public void setDtLastProcessedDate(Date dtLastProcessedDate) {
        this.dtLastProcessedDate = dtLastProcessedDate;
    }

    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    public String getStrCustomerId() {
        return strCustomerId;
    }

    public void setStrCustomerId(String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }

    public String getStrAssetGrpName() {
        return strAssetGrpName;
    }

    public void setStrAssetGrpName(String strAssetGrpName) {
        this.strAssetGrpName = strAssetGrpName;
    }

}
