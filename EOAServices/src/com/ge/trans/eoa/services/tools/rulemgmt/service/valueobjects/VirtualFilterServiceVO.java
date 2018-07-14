/**
 * ============================================================
 * Classification: GE Confidential
 * File : VirtualFilterServiceVO.java
 * Description : Service Interface for Virtual Screen
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Mar 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VirtualFilterServiceVO extends BaseVO {

    private static final long serialVersionUID = -1556608204214879729L;

    private String strFilterSeqId;
    private String strParameter;
    private String strFunction;
    private String strValue1;
    private String strValue2;

    /**
     * @return the strFilterSeqId
     */
    public String getStrFilterSeqId() {
        return strFilterSeqId;
    }

    /**
     * @param strFilterSeqId
     *            the strFilterSeqId to set
     */
    public void setStrFilterSeqId(String strFilterSeqId) {
        this.strFilterSeqId = strFilterSeqId;
    }

    /**
     * @return the strParameter
     */
    public String getStrParameter() {
        return strParameter;
    }

    /**
     * @param strParameter
     *            the strParameter to set
     */
    public void setStrParameter(String strParameter) {
        this.strParameter = strParameter;
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
