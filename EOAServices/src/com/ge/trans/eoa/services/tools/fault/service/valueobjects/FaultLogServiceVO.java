/**
 * ============================================================
 * File : FaultLogServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
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
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 21, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FaultLogServiceVO extends BaseVO {

    static final long serialVersionUID = 91515468L;

    private List arlFaultDataDetails;
    private String strNotch = null;

    /**
     * @return the arlFaultDataDetails
     */
    public List getArlFaultDataDetails() {
        return arlFaultDataDetails;
    }

    /**
     * @param arlFaultDataDetails
     *            the arlFaultDataDetails to set
     */
    public void setArlFaultDataDetails(final List arlFaultDataDetails) {
        this.arlFaultDataDetails = arlFaultDataDetails;
    }

    /**
     * @return the strNotch
     */
    public String getStrNotch() {
        return strNotch;
    }

    /**
     * @param strNotch
     *            the strNotch to set
     */
    public void setStrNotch(final String strNotch) {
        this.strNotch = strNotch;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlFaultDataDetails", arlFaultDataDetails)
                .append("strNotch", strNotch)
                .toString();
    }
}
