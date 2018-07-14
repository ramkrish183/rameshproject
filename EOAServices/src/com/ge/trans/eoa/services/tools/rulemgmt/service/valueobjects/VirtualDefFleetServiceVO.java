/**
 * ============================================================
 * File : VirtualDefFleetServiceVO.java
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
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VirtualDefFleetServiceVO extends BaseVO {

    static final long serialVersionUID = 375541125468L;
    private String strfleet;
    private List<String> arlFleet;
    private String strIsExclude;

    /**
     * @return the strfleet
     */
    public String getStrfleet() {
        return strfleet;
    }

    /**
     * @param strfleet
     *            the strfleet to set
     */
    public void setStrfleet(String strfleet) {
        this.strfleet = strfleet;
    }

    /**
     * @return the arlFleet
     */
    public List<String> getArlFleet() {
        return arlFleet;
    }

    /**
     * @param arlFleet
     *            the arlFleet to set
     */
    public void setArlFleet(List<String> arlFleet) {
        this.arlFleet = arlFleet;
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
}
