/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetCmContactInfoHVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 22, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class GetUsrUserPreferenceEOAVO extends BaseVO {

    static final long serialVersionUID = 635654132L;

    private Long getUsrUserPreferenceSeqId;
    private GetUsrUsersEOAVO getUsrUsersVO;
    private long userId;
    private String customerChange;
    private String userPreferernceType;
    private String userPreferenceValue;
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public Long getGetUsrUserPreferenceSeqId() {
        return getUsrUserPreferenceSeqId;
    }

    public void setGetUsrUserPreferenceSeqId(final Long getUsrUserPreferenceSeqId) {
        this.getUsrUserPreferenceSeqId = getUsrUserPreferenceSeqId;
    }

    public String getUserPreferernceType() {
        return userPreferernceType;
    }

    public void setUserPreferernceType(final String userPreferernceType) {
        this.userPreferernceType = userPreferernceType;
    }

    public String getUserPreferenceValue() {
        return userPreferenceValue;
    }

    public void setUserPreferenceValue(final String userPreferenceValue) {
        this.userPreferenceValue = userPreferenceValue;
    }

    public GetUsrUsersEOAVO getGetUsrUsersVO() {
        return getUsrUsersVO;
    }

    public void setGetUsrUsersVO(final GetUsrUsersEOAVO getUsrUsersVO) {
        this.getUsrUsersVO = getUsrUsersVO;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public String getCustomerChange() {
        return customerChange;
    }

    public void setCustomerChange(String customerChange) {
        this.customerChange = customerChange;
    }

}