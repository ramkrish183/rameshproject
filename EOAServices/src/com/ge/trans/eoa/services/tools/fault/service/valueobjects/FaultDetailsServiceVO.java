/**
 * ============================================================
 * File : FaultDetailsServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
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
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

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
public class FaultDetailsServiceVO extends BaseVO {
    static final long serialVersionUID = 3962598654L;
    // private String strParamNumber = null;
    private String strData = null;
    // private String strUpperBound = null;
    // private String strLowerBound = null;
    // private String strWidth = null;
    // private String strProximityDesc = null;
    // private String strNotch = null;
    // private String strColor = null;
    // private String strDataToolTip = null;

    /**
     * @return the strParamNumber
     */
    /*
     * public String getStrParamNumber() { return strParamNumber; }
     */

    /**
     * @param strParamNumber
     *            the strParamNumber to set
     */
    /*
     * public void setStrParamNumber(final String strParamNumber) {
     * this.strParamNumber = strParamNumber; }
     */

    /**
     * @return the strData
     */
    public String getStrData() {
        return strData;
    }

    /**
     * @param strData
     *            the strData to set
     */
    public void setStrData(final String strData) {
        this.strData = strData;
    }

    /**
     * @return the strUpperBound
     */
    /*
     * public String getStrUpperBound() { return strUpperBound; }
     */

    /**
     * @param strUpperBound
     *            the strUpperBound to set
     */
    /*
     * public void setStrUpperBound(final String strUpperBound) {
     * this.strUpperBound = strUpperBound; }
     */

    /**
     * @return the strLowerBound
     */
    /*
     * public String getStrLowerBound() { return strLowerBound; }
     */

    /**
     * @param strLowerBound
     *            the strLowerBound to set
     */
    /*
     * public void setStrLowerBound(final String strLowerBound) {
     * this.strLowerBound = strLowerBound; }
     */

    /**
     * @return the strWidth
     */
    /*
     * public String getStrWidth() { return strWidth; }
     */

    /**
     * @param strWidth
     *            the strWidth to set
     */
    /*
     * public void setStrWidth(final String strWidth) { this.strWidth =
     * strWidth; }
     */

    /**
     * @return the strProximityDesc
     */
    /*
     * public String getStrProximityDesc() { return strProximityDesc; }
     */

    /**
     * @param strProximityDesc
     *            the strProximityDesc to set
     */
    /*
     * public void setStrProximityDesc(final String strProximityDesc) {
     * this.strProximityDesc = strProximityDesc; }
     */

    /**
     * @return the strNotch
     */
    /*
     * public String getStrNotch() { return strNotch; }
     */

    /**
     * @param strNotch
     *            the strNotch to set
     */
    /*
     * public void setStrNotch(final String strNotch) { this.strNotch =
     * strNotch; }
     */

    /**
     * @return the strColor
     */
    /*
     * public String getStrColor() { return strColor; }
     */

    /**
     * @param strColor
     *            the strColor to set
     */
    /*
     * public void setStrColor(final String strColor) { this.strColor =
     * strColor; }
     */

    /**
     * @return the strDataToolTip
     */
    /*
     * public String getStrDataToolTip() { return strDataToolTip; }
     */

    /**
     * @param strDataToolTip
     *            the strDataToolTip to set
     */
    /*
     * public void setStrDataToolTip(final String strDataToolTip) {
     * this.strDataToolTip = strDataToolTip; }
     */

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject())
                // .append("strParamNumber", strParamNumber)
                .append("strData", strData)
                // .append("strUpperBound", strUpperBound)
                // .append("strLowerBound", strLowerBound)
                // .append("strWidth", strWidth)
                // .append("strProximityDesc", strProximityDesc)
                // .append("strNotch", strNotch)
                // .append("strColor", strColor)
                // .append("strDataToolTip", strDataToolTip)
                .toString();
    }
}
