/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseRxTitlesServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 28, 2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: May 28, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FindCaseRxTitlesServiceVO extends BaseVO {
    static final long serialVersionUID = 32544474L;
    private String strRxTitle;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxTitle", strRxTitle).toString();
    }

    public String getStrRxTitle() {
        return strRxTitle;
    }

    public void setStrRxTitle(final String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }
}
