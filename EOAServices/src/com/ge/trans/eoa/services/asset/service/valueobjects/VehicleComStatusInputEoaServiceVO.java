/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleComStatusInputServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 12, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 12, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VehicleComStatusInputEoaServiceVO extends BaseVO {

    private static final long serialVersionUID = 12334L;
    private String strRoadNumber;
    private String assetGroupName;
    private String customerID;
    private boolean isLastFault = false;

    /**
     * @return
     */
    public boolean isLastFault() {
        return isLastFault;
    }

    /**
     * @param isLastFault
     */
    public void setLastFault(boolean isLastFault) {
        this.isLastFault = isLastFault;
    }

    /**
     * @return assetGroupName
     */
    public String getAssetGroupName() {
        return assetGroupName;
    }

    /**
     * @param assetGroupName
     *            to set assetGroupName
     */
    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    /**
     * @return customerID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     *            to set customerID
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the strRoadNumber
     */
    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    /**
     * @param strRoadNumber
     *            the strRoadNumber to set
     */
    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }
}
