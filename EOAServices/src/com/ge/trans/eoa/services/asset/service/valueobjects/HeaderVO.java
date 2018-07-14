package com.ge.trans.eoa.services.asset.service.valueobjects;

/**
 * ============================================================
 * File : HeaderVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.egahc.vo
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 6, 2014
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2014 General Electric Company. All rights reserved
 * Classification : GE Sensitive
 * ============================================================
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerVO", propOrder = { "strTransactionIndicator", "requestNo", "headerDate", "actionType",
        "physResId", "custId", "vehicleInitial", "vehicleNo", "source" })
@XmlRootElement(name = "HEADER")
public class HeaderVO implements Serializable {
    /**
     * Serial Version Id
     */
    private static final long serialVersionUID = -1362178026407566357L;

    @XmlElement(name = "TRANSACTION_INDICATOR")
    private String strTransactionIndicator;
    @XmlElement(name = "REQUEST_NO")
    private String requestNo;
    @XmlElement(name = "HEADER_DATE")
    private String headerDate;
    @XmlElement(name = "ACTION_TYPE")
    private String actionType;
    @XmlElement(name = "PHYS_RES_ID")
    private String physResId;
    @XmlElement(name = "CUST_ID")
    private String custId;
    @XmlElement(name = "VEHICLE_INITIAL")
    private String vehicleInitial;
    @XmlElement(name = "VEHICLE_NO")
    private String vehicleNo;
    @XmlElement(name = "SOURCE")
    private String source;

    /**
     * @return the strTransactionIndicator
     */
    public String getStrTransactionIndicator() {
        return strTransactionIndicator;
    }

    /**
     * @param strTransactionIndicator
     *            the strTransactionIndicator to set
     */
    public void setStrTransactionIndicator(String strTransactionIndicator) {
        this.strTransactionIndicator = strTransactionIndicator;
    }

    /**
     * @return the requestNo
     */
    public String getRequestNo() {
        return requestNo;
    }

    /**
     * @param requestNo
     *            the requestNo to set
     */
    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    /**
     * @return the headerDate
     */
    public String getHeaderDate() {
        return headerDate;
    }

    /**
     * @param headerDate
     *            the headerDate to set
     */
    public void setHeaderDate(String headerDate) {
        this.headerDate = headerDate;
    }

    /**
     * @return the actionType
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * @param actionType
     *            the actionType to set
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * @return the physResId
     */
    public String getPhysResId() {
        return physResId;
    }

    /**
     * @param physResId
     *            the physResId to set
     */
    public void setPhysResId(String physResId) {
        this.physResId = physResId;
    }

    /**
     * @return the custId
     */
    public String getCustId() {
        return custId;
    }

    /**
     * @param custId
     *            the custId to set
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * @return the vehicleInitial
     */
    public String getVehicleInitial() {
        return vehicleInitial;
    }

    /**
     * @param vehicleInitial
     *            the vehicleInitial to set
     */
    public void setVehicleInitial(String vehicleInitial) {
        this.vehicleInitial = vehicleInitial;
    }

    /**
     * @return the vehicleNo
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * @param vehicleNo
     *            the vehicleNo to set
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

}
