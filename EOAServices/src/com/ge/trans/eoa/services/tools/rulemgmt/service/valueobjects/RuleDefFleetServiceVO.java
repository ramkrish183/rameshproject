/**
 * ============================================================
 * File : RuleDefFleetVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.tools.rule.valueobjects
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

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleDefFleetServiceVO extends BaseVO {

    static final long serialVersionUID = 375541125468L;
    private String strfleet;
    private String[] strFleetNumber;
    private ArrayList arlFleet;
    private String strIsExclude;

    public RuleDefFleetServiceVO() {

    }

    /**
     * @return the strfleet
     */
    public String getStrfleet() {
        return strfleet;
    }

    /**
     * @param strfleet
     *            the strfleet to set
     */
    public void setStrfleet(final String strfleet) {
        this.strfleet = strfleet;
    }

    /**
     * @return the arlFleet
     */
    public ArrayList getArlFleet() {
        return arlFleet;
    }

    /**
     * @param arlFleet
     *            the arlFleet to set
     */
    public void setArlFleet(final ArrayList arlFleet) {
        this.arlFleet = arlFleet;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("arlFleet", arlFleet)
                .append("strIsExclude", strIsExclude).append("strfleet", strfleet).toString();
    }

    public String[] getStrFleetNumber() {
        return strFleetNumber;
    }

    public void setStrFleetNumber(final String[] strFleetNumber) {
        this.strFleetNumber = strFleetNumber;
    }

    public String getStrIsExclude() {
        return strIsExclude;
    }

    public void setStrIsExclude(final String strIsExclude) {
        this.strIsExclude = strIsExclude;
    }
}
