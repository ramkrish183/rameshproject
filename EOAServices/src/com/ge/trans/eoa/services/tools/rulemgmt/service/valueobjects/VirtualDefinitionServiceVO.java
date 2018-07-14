/**
 * ============================================================
 * File : VirtualDefinitionServiceVO.java
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

import java.util.ArrayList;
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
public class VirtualDefinitionServiceVO extends BaseVO {

    static final long serialVersionUID = 115415664L;
    private String strMasterVirtualId;
    private String strVirtualDefID;
    private List<VirtualDefCustomerServiceVO> arlVirtualDefCustomer;
    private List<String> arlModel;
    private ArrayList<VirtualDefConfigServiceVO> arlVirtualDefConfig;

    /**
     * @return the strMasterVirtualId
     */
    public String getStrMasterVirtualId() {
        return strMasterVirtualId;
    }

    /**
     * @param strMasterVirtualId
     *            the strMasterVirtualId to set
     */
    public void setStrMasterVirtualId(String strMasterVirtualId) {
        this.strMasterVirtualId = strMasterVirtualId;
    }

    /**
     * @return the strVirtualDefID
     */
    public String getStrVirtualDefID() {
        return strVirtualDefID;
    }

    /**
     * @param strVirtualDefID
     *            the strVirtualDefID to set
     */
    public void setStrVirtualDefID(String strVirtualDefID) {
        this.strVirtualDefID = strVirtualDefID;
    }

    /**
     * @return the arlVirtualDefCustomer
     */
    public List<VirtualDefCustomerServiceVO> getArlVirtualDefCustomer() {
        return arlVirtualDefCustomer;
    }

    /**
     * @param arlVirtualDefCustomer
     *            the arlVirtualDefCustomer to set
     */
    public void setArlVirtualDefCustomer(List<VirtualDefCustomerServiceVO> arlVirtualDefCustomer) {
        this.arlVirtualDefCustomer = arlVirtualDefCustomer;
    }

    /**
     * @return the arlModel
     */
    public List<String> getArlModel() {
        return arlModel;
    }

    /**
     * @param arlModel
     *            the arlModel to set
     */
    public void setArlModel(List<String> arlModel) {
        this.arlModel = arlModel;
    }

    /**
     * @return the arlVirtualDefConfig
     */
    public ArrayList<VirtualDefConfigServiceVO> getArlVirtualDefConfig() {
        return arlVirtualDefConfig;
    }

    /**
     * @param arlVirtualDefConfig
     *            the arlVirtualDefConfig to set
     */
    public void setArlVirtualDefConfig(ArrayList<VirtualDefConfigServiceVO> arlVirtualDefConfig) {
        this.arlVirtualDefConfig = arlVirtualDefConfig;
    }

}
