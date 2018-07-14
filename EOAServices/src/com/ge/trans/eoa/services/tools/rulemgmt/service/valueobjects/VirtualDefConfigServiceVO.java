/**
 * ============================================================
 * File : VirtualDefConfigServiceVO.java
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
public class VirtualDefConfigServiceVO extends BaseVO {

    static final long serialVersionUID = 879658222L;
    private String strConfiguration;
    private String strFunction;
    private String strValue1;
    private String strValue2;

    /**
     * @return the strConfiguration
     */
    public String getStrConfiguration() {
        return strConfiguration;
    }

    /**
     * @param strConfiguration
     *            the strConfiguration to set
     */
    public void setStrConfiguration(String strConfiguration) {
        this.strConfiguration = strConfiguration;
    }

    /**
     * @return the strFunction
     */
    public String getStrFunction() {
        return strFunction;
    }

    /**
     * @param strFunction
     *            the strFunction to set
     */
    public void setStrFunction(String strFunction) {
        this.strFunction = strFunction;
    }

    /**
     * @return the strValue1
     */
    public String getStrValue1() {
        return strValue1;
    }

    /**
     * @param strValue1
     *            the strValue1 to set
     */
    public void setStrValue1(String strValue1) {
        this.strValue1 = strValue1;
    }

    /**
     * @return the strValue2
     */
    public String getStrValue2() {
        return strValue2;
    }

    /**
     * @param strValue2
     *            the strValue2 to set
     */
    public void setStrValue2(String strValue2) {
        this.strValue2 = strValue2;
    }

}
