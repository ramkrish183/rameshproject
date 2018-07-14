/**
 * ============================================================
 * File : VirtualDefCustomerServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class VirtualDefCustomerServiceVO extends BaseVO {

    static final long serialVersionUID = 3132253624L;
    private String strCustomer;
    private String strCustomerID;
    private String strIsExclude;
    private List<VirtualDefFleetServiceVO> arlVirtualDefFleet;

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
    public void setStrCustomer(String strCustomer) {
        this.strCustomer = strCustomer;
    }

    /**
     * @return the strCustomerID
     */
    public String getStrCustomerID() {
        return strCustomerID;
    }

    /**
     * @param strCustomerID
     *            the strCustomerID to set
     */
    public void setStrCustomerID(String strCustomerID) {
        this.strCustomerID = strCustomerID;
    }

    /**
     * @return the strIsExclude
     */
    public String getStrIsExclude() {
        return strIsExclude;
    }

    /**
     * @param strIsExclude
     *            the strIsExclude to set
     */
    public void setStrIsExclude(String strIsExclude) {
        this.strIsExclude = strIsExclude;
    }

    /**
     * @return the arlVirtualDefFleet
     */
    public List<VirtualDefFleetServiceVO> getArlVirtualDefFleet() {
        return arlVirtualDefFleet;
    }

    /**
     * @param arlVirtualDefFleet
     *            the arlVirtualDefFleet to set
     */
    public void setArlVirtualDefFleet(List<VirtualDefFleetServiceVO> arlVirtualDefFleet) {
        this.arlVirtualDefFleet = arlVirtualDefFleet;
    }
}
