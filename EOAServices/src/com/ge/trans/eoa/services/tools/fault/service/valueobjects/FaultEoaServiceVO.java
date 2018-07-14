/**
 * ============================================================
 * File : FaultServiceVO.java
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

import java.util.ArrayList;

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
@SuppressWarnings("unchecked")
public class FaultEoaServiceVO extends BaseVO {

    static final long serialVersionUID = 3377651144L;
    private ArrayList arlFaultData = null;

    /**
     * @return the arlFaultData
     */
    public ArrayList getArlFaultData() {
        return arlFaultData;
    }

    /**
     * @param arlFaultData
     *            the arlFaultData to set
     */
    public void setArlFaultData(final ArrayList arlFaultData) {
        this.arlFaultData = arlFaultData;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlFaultData", arlFaultData).toString();
    }
}
