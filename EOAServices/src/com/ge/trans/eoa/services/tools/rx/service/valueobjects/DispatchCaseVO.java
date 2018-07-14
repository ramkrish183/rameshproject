/**
 * ============================================================
 * File : AcceptCaseVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 31, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * Classification : GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Mar 31, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class DispatchCaseVO extends BaseVO {

    static final long serialVersionUID = 965321L;
    private String strCaseId;
    private String strQueueName;
    private String repairAction;
    private String rxCaseID;
    private String eoaUserId;
    private String locationId;

    /**
     * @return the locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the eoaUserId
     */
    public String getEoaUserId() {
        return eoaUserId;
    }

    /**
     * @param eoaUserId
     *            the eoaUserId to set
     */
    public void setEoaUserId(final String eoaUserId) {
        this.eoaUserId = eoaUserId;
    }

    /**
     * @return the strQueueName
     */
    public String getStrQueueName() {
        return strQueueName;
    }

    /**
     * @param strQueueName
     *            the strQueueName to set
     */
    public void setStrQueueName(final String strQueueName) {
        this.strQueueName = strQueueName;
    }

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getRepairAction() {
        return repairAction;
    }

    public void setRepairAction(final String repairAction) {
        this.repairAction = repairAction;
    }

    public String getRxCaseID() {
        return rxCaseID;
    }

    public void setRxCaseID(final String rxCaseID) {
        this.rxCaseID = rxCaseID;
    }
}
