/**
 * ============================================================
 * File : FindLocContResultServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 27, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 27, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FindLocContResultServiceVO extends BaseVO {

    /**
     * Serial version Id
     */
    private static final long serialVersionUID = 8486877858140988470L;
    private Long contactInfoSeqId;
    private String strPhone;
    private String strFirstName;
    private String strLastName;

    /**
     * @return the contactInfoSeqId
     */
    public Long getContactInfoSeqId() {
        return contactInfoSeqId;
    }

    /**
     * @param contactInfoSeqId
     *            the contactInfoSeqId to set
     */
    public void setContactInfoSeqId(final Long contactInfoSeqId) {
        this.contactInfoSeqId = contactInfoSeqId;
    }

    /**
     * @return the strPhone
     */
    public String getStrPhone() {
        return strPhone;
    }

    /**
     * @param strPhone
     *            the strPhone to set
     */
    public void setStrPhone(final String strPhone) {
        this.strPhone = strPhone;
    }

    /**
     * @return the strFirstName
     */
    @Override
    public String getStrFirstName() {
        return strFirstName;
    }

    /**
     * @param strFirstName
     *            the strFirstName to set
     */
    @Override
    public void setStrFirstName(final String strFirstName) {
        this.strFirstName = strFirstName;
    }

    /**
     * @return the strLastName
     */
    @Override
    public String getStrLastName() {
        return strLastName;
    }

    /**
     * @param strLastName
     *            the strLastName to set
     */
    @Override
    public void setStrLastName(final String strLastName) {
        this.strLastName = strLastName;
    }
}
