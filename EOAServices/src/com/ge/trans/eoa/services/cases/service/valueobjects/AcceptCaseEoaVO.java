/**
 * ============================================================
 * File : AcceptCaseVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 31, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * Classification : GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Mar 31, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AcceptCaseEoaVO extends BaseVO {

    static final long serialVersionUID = 965321L;
    private String strCaseId;

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }
}
