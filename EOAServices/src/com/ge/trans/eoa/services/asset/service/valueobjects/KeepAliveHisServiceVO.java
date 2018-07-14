/**
 * ============================================================
 * Classification: GE Confidential
 * File : KeepAliveHisServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 8, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 8, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class KeepAliveHisServiceVO extends BaseVO {

    private static final long serialVersionUID = -8874104674147355643L;

    private Date date;
    private String strCCA;

    /**
     * @return the strDate
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param strDate
     *            the strDate to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the strCCA
     */
    public String getStrCCA() {
        return strCCA;
    }

    /**
     * @param strCCA
     *            the strCCA to set
     */
    public void setStrCCA(String strCCA) {
        this.strCCA = strCCA;
    }
}
