/**
 * ============================================================
 * File : RxHistServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.Date;

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
public class RxHistServiceVO extends BaseVO {

    private static final long serialVersionUID = -328261616351520563L;
    private String strVersionNumber;
	private String strDateCreated;
    private String strCreatedBy;
    private String strRevisionHistory;
    private String strRxID;
    private String strOriginalID;
    private String active;
    private String changeDetail;
    
    /**
     * @return the changeDetail
     */
    public String getChangeDetail() {
		return changeDetail;
	}
   
    /**
     * @param setChangeDetail
     *            the setChangeDetail to set
     */
	public void setChangeDetail(String changeDetail) {
		this.changeDetail = changeDetail;
	}

	/**
     * @return the strVersionNumber
     */
    public String getStrVersionNumber() {
        return strVersionNumber;
    }

    /**
     * @param strVersionNumber
     *            the strVersionNumber to set
     */
    public void setStrVersionNumber(String strVersionNumber) {
        this.strVersionNumber = strVersionNumber;
    }

    /**
     * @return the strDateCreated
     */
	public String getStrDateCreated() {
        return strDateCreated;
    }

    /**
     * @param strDateCreated
     *            the strDateCreated to set
     */
	public void setStrDateCreated(String strDateCreated) {
        this.strDateCreated = strDateCreated;
    }

    /**
     * @return the strCreatedBy
     */
    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    /**
     * @param strCreatedBy
     *            the strCreatedBy to set
     */
    public void setStrCreatedBy(String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    /**
     * @return the strRevisionHistory
     */
    public String getStrRevisionHistory() {
        return strRevisionHistory;
    }

    /**
     * @param strRevisionHistory
     *            the strRevisionHistory to set
     */
    public void setStrRevisionHistory(String strRevisionHistory) {
        this.strRevisionHistory = strRevisionHistory;
    }

    /**
     * @return the strRxID
     */
    public String getStrRxID() {
        return strRxID;
    }

    /**
     * @param strRxID
     *            the strRxID to set
     */
    public void setStrRxID(String strRxID) {
        this.strRxID = strRxID;
    }

    /**
     * @return the strOriginalID
     */
    public String getStrOriginalID() {
        return strOriginalID;
    }

    /**
     * @param strOriginalID
     *            the strOriginalID to set
     */
    public void setStrOriginalID(String strOriginalID) {
        this.strOriginalID = strOriginalID;
    }

    /**
     * @return the active
     */
    public String getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
}
