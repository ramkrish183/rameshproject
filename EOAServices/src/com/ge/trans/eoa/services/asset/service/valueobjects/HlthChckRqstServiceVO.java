/**
 * ============================================================
 * Classification: GE Confidential
 * File : HlthChckRqstServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 7, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class HlthChckRqstServiceVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    private String strAssetNumber;
    private String errorMessage;

    /**
     * @return the StrAssetNumber
     */
    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    public HlthChckRqstServiceVO() {
        super();
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public HlthChckRqstServiceVO(String strAssetNumber) {
        super();
        this.strAssetNumber = strAssetNumber;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
