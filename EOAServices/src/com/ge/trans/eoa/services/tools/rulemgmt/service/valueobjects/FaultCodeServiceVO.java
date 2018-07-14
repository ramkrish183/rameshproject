/**
 * ============================================================
 * File : FaultCodeServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

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
public class FaultCodeServiceVO extends BaseVO {

    static final long serialVersionUID = 94328652156L;
    private String strFaultCode;
    private String strFaultDescription;
    private ArrayList<FaultCodeServiceVO> arlFaultCode;
    private ArrayList<FaultCodeServiceVO> arlFaultDescription;

    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }

    /**
     * @param strFaultCode
     *            the strFaultCode to set
     */
    public void setStrFaultCode(final String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /**
     * @return the strFaultDescription
     */
    public String getStrFaultDescription() {
        return strFaultDescription;
    }

    /**
     * @param strFaultDescription
     *            the strFaultDescription to set
     */
    public void setStrFaultDescription(final String strFaultDescription) {
        this.strFaultDescription = strFaultDescription;
    }

    /**
     * @return the arlFaultCode
     */
    public ArrayList<FaultCodeServiceVO> getArlFaultCode() {
        return arlFaultCode;
    }

    /**
     * @param arlFaultCode
     *            the arlFaultCode to set
     */
    public void setArlFaultCode(final ArrayList<FaultCodeServiceVO> arlFaultCode) {
        this.arlFaultCode = arlFaultCode;
    }

    /**
     * @return the arlFaultDescription
     */
    public ArrayList<FaultCodeServiceVO> getArlFaultDescription() {
        return arlFaultDescription;
    }

    /**
     * @param arlFaultDescription
     *            the arlFaultDescription to set
     */
    public void setArlFaultDescription(final ArrayList<FaultCodeServiceVO> arlFaultDescription) {
        this.arlFaultDescription = arlFaultDescription;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFaultCode", strFaultCode)
                .append("strFaultDescription", strFaultDescription).append("arlFaultCode", arlFaultCode)
                .append("arlFaultDescription", arlFaultDescription).toString();
    }
}
