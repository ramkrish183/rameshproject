/**
 * ============================================================
 * File : ComplexRuleLinkServiceVO.java
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
public class ComplexRuleLinkServiceVO extends BaseVO {

    static final long serialVersionUID = 77488554L;
    private String strSimpleRule;
    private String strComplexRule;
    private String strFinalRule;

    /**
     * @param strComplexRule
     * @param strFinalRule
     * @param strSimpleRule
     */
    public ComplexRuleLinkServiceVO(final String strComplexRule, final String strFinalRule,
            final String strSimpleRule) {
        super();
        this.strComplexRule = strComplexRule;
        this.strFinalRule = strFinalRule;
        this.strSimpleRule = strSimpleRule;
    }

    /**
     *
     */
    public ComplexRuleLinkServiceVO() {
    }

    /**
     * @return the strSimpleRule
     */
    public String getStrSimpleRule() {
        return strSimpleRule;
    }

    /**
     * @param strSimpleRule
     *            the strSimpleRule to set
     */
    public void setStrSimpleRule(final String strSimpleRule) {
        this.strSimpleRule = strSimpleRule;
    }

    /**
     * @return the strComplexRule
     */
    public String getStrComplexRule() {
        return strComplexRule;
    }

    /**
     * @param strComplexRule
     *            the strComplexRule to set
     */
    public void setStrComplexRule(final String strComplexRule) {
        this.strComplexRule = strComplexRule;
    }

    /**
     * @return the strFinalRule
     */
    public String getStrFinalRule() {
        return strFinalRule;
    }

    /**
     * @param strFinalRule
     *            the strFinalRule to set
     */
    public void setStrFinalRule(final String strFinalRule) {
        this.strFinalRule = strFinalRule;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strSimpleRule", strSimpleRule)
                .append("strComplexRule", strComplexRule).append("strFinalRule", strFinalRule).toString();
    }
}
