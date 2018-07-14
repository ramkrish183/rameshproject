/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseCreateServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 2, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CaseCreateServiceVO extends BaseVO {

    private static final long serialVersionUID = 433901506016414678L;
    private String strRecomId;
    private String strAssetNumber;
    private String strFromAsset;
    private String strToAsset;
    private String strIncomingCall;

    /**
     * @return the strRecomId
     */
    public String getStrRecomId() {
        return strRecomId;
    }

    /**
     * @param strRecomId
     *            the strRecomId to set
     */
    public void setStrRecomId(final String strRecomId) {
        this.strRecomId = strRecomId;
    }

    /**
     * @return the strFromAsset
     */
    public String getStrFromAsset() {
        return strFromAsset;
    }

    /**
     * @param strFromAsset
     *            the strFromAsset to set
     */
    public void setStrFromAsset(final String strFromAsset) {
        this.strFromAsset = strFromAsset;
    }

    /**
     * @return the strToAsset
     */
    public String getStrToAsset() {
        return strToAsset;
    }

    /**
     * @param strToAsset
     *            the strToAsset to set
     */
    public void setStrToAsset(final String strToAsset) {
        this.strToAsset = strToAsset;
    }

    /**
     * @return the strIncomingCall
     */
    public String getStrIncomingCall() {
        return strIncomingCall;
    }

    /**
     * @param strIncomingCall
     *            the strIncomingCall to set
     */
    public void setStrIncomingCall(final String strIncomingCall) {
        this.strIncomingCall = strIncomingCall;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRecomId", strRecomId)
                .append("strAssetNumber", strAssetNumber).append("strFromAsset", strFromAsset)
                .append("strToAsset", strToAsset).append("strIncomingCall", strIncomingCall).toString();
    }

    /**
     * @return the strAssetNumber
     */
    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public void setStrAssetNumber(final String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }
}
