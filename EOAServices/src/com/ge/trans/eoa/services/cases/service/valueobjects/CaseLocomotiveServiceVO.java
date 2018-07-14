/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseLocomotiveServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 21, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CaseLocomotiveServiceVO extends BaseVO {

    static final long serialVersionUID = 13406665L;
    private String strCustomer;
    private String strAssetNo;
    private String strModel;
    private String strCurrentLoc;
    private String strFleet;
    private Date dtLastFault;
    private Date dtLastKeepALive;
    private String strAssetGroup;
    private String strAssetGroupSeqId;
    private String strCustomerId;

    /**
     * @return the strCustomer
     */
    public String getStrCustomer() {
        return strCustomer;
    }

    /**
     * @param strCustomer
     *            the strCustomer to set
     */
    public void setStrCustomer(final String strCustomer) {
        this.strCustomer = strCustomer;
    }

    /**
     * @return the strAssetNo
     */
    public String getStrAssetNo() {
        return strAssetNo;
    }

    /**
     * @param strAssetNo
     *            the strAssetNo to set
     */
    public void setStrAssetNo(final String strAssetNo) {
        this.strAssetNo = strAssetNo;
    }

    /**
     * @return the strModel
     */
    public String getStrModel() {
        return strModel;
    }

    /**
     * @param strModel
     *            the strModel to set
     */
    public void setStrModel(final String strModel) {
        this.strModel = strModel;
    }

    /**
     * @return the strCurrentLoc
     */
    public String getStrCurrentLoc() {
        return strCurrentLoc;
    }

    /**
     * @param strCurrentLoc
     *            the strCurrentLoc to set
     */
    public void setStrCurrentLoc(final String strCurrentLoc) {
        this.strCurrentLoc = strCurrentLoc;
    }

    /**
     * @return the strFleet
     */
    public String getStrFleet() {
        return strFleet;
    }

    /**
     * @param strFleet
     *            the strFleet to set
     */
    public void setStrFleet(final String strFleet) {
        this.strFleet = strFleet;
    }

    /**
     * @return the strAssetGroup
     */
    public String getStrAssetGroup() {
        return strAssetGroup;
    }

    /**
     * @param strAssetGroup
     *            the strAssetGroup to set
     */
    public void setStrAssetGroup(final String strAssetGroup) {
        this.strAssetGroup = strAssetGroup;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strCustomer", strCustomer)
                .append("strAssetNo", strAssetNo).append("strModel", strModel).append("strCurrentLoc", strCurrentLoc)
                .append("strFleet", strFleet).append("strFleet", strFleet).append("dtLastFault", dtLastFault)
                .append("dtLastKeepALive", dtLastKeepALive).append("strAssetGroup", strAssetGroup).toString();
    }

    /**
     * @return the dtLastKeepALive
     */
    public Date getDtLastKeepALive() {
        return dtLastKeepALive;
    }

    /**
     * @param dtLastKeepALive
     *            the dtLastKeepALive to set
     */
    public void setDtLastKeepALive(final Date dtLastKeepALive) {
        this.dtLastKeepALive = dtLastKeepALive;
    }

    /**
     * @return the dtLastFault
     */
    public Date getDtLastFault() {
        return dtLastFault;
    }

    /**
     * @param dtLastFault
     *            the dtLastFault to set
     */
    public void setDtLastFault(final Date dtLastFault) {
        this.dtLastFault = dtLastFault;
    }

    /**
     * @return the strAssetGroupSeqId
     */
    public String getStrAssetGroupSeqId() {
        return strAssetGroupSeqId;
    }

    /**
     * @param strAssetGroupSeqId
     *            the strAssetGroupSeqId to set
     */
    public void setStrAssetGroupSeqId(final String strAssetGroupSeqId) {
        this.strAssetGroupSeqId = strAssetGroupSeqId;
    }

    public String getStrCustomerId() {
        return strCustomerId;
    }

    public void setStrCustomerId(final String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }

}
