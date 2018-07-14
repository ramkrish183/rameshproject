/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipDetailsVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 28-Sep-2015	
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * @Author : iGATE Global Solutions Ltd.
 * @Version : 1.0
 * @Date Created : 28-Sep-2015
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class UnitShipDetailsVO {

    private String assetNumber;
    private String assetGrpName;
    private String customerId;
    private String isDefaultLoad;
    private String customerList;

    public String getCustomerList() {
        return customerList;
    }

    public void setCustomerList(String customerList) {
        this.customerList = customerList;
    }

    public String getIsDefaultLoad() {
        return isDefaultLoad;
    }

    public void setIsDefaultLoad(String isDefaultLoad) {
        this.isDefaultLoad = isDefaultLoad;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
