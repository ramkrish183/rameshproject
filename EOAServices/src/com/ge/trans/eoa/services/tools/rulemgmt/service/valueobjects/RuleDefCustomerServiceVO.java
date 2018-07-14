/**
 * ============================================================
 * File : RuleDefCustomerServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

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
@SuppressWarnings("unchecked")
public class RuleDefCustomerServiceVO extends BaseVO {

    static final long serialVersionUID = 3132253624L;
    private String strCustomer;
    private String strCustomerID;
    private String strIsExclude;
    private List arlRuleDefFleet;
    private String strFleet;

    /**
     * @param strCustomer
     * @param strCustomerID
     * @param strIsExclude
     */
    public RuleDefCustomerServiceVO(final String strCustomer, final String strCustomerID, final String strIsExclude) {
        super();
        this.strCustomer = strCustomer;
        this.strCustomerID = strCustomerID;
        this.strIsExclude = strIsExclude;
    }

    public RuleDefCustomerServiceVO() {
    }

    /**
     * @return the strCustomer
     */
    public String getStrCustomer() {
        return strCustomer;
    }

    /**
     * @param strCustomer
     *            the strCustomer to set
     */
    public void setStrCustomer(final String strCustomer) {
        this.strCustomer = strCustomer;
    }

    /**
     * @return the strCustomerID
     */
    public String getStrCustomerID() {
        return strCustomerID;
    }

    /**
     * @param strCustomerID
     *            the strCustomerID to set
     */
    public void setStrCustomerID(final String strCustomerID) {
        this.strCustomerID = strCustomerID;
    }

    /**
     * @return the strIsExclude
     */
    public String getStrIsExclude() {
        return strIsExclude;
    }

    /**
     * @param strIsExclude
     *            the strIsExclude to set
     */
    public void setStrIsExclude(final String strIsExclude) {
        this.strIsExclude = strIsExclude;
    }

    public List getArlRuleDefFleet() {
        return arlRuleDefFleet;
    }

    public void setArlRuleDefFleet(List arlRuleDefFleet) {
        this.arlRuleDefFleet = arlRuleDefFleet;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strCustomer", strCustomer)
                .append("strCustomerID", strCustomerID).append("strIsExclude", strIsExclude)
                .append("arlRuleDefFleet", arlRuleDefFleet).toString();
    }

    public String getStrFleet() {
        return strFleet;
    }

    public void setStrFleet(final String strFleet) {
        this.strFleet = strFleet;
    }
}
