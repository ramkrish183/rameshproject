/**
 * ============================================================
 * File : SubIDServiceVO.java
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

import java.util.ArrayList;

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
public class SubIDServiceVO extends BaseVO {

    static final long serialVersionUID = 97715465284L;
    private String strSubID;
    private ArrayList<SubIDServiceVO> arlSubID;

    /**
     * @return the strSubID
     */
    public String getStrSubID() {
        return strSubID;
    }

    /**
     * @param strSubID
     *            the strSubID to set
     */
    public void setStrSubID(String strSubID) {
        this.strSubID = strSubID;
    }

    /**
     * @return the arlSubID
     */
    public ArrayList<SubIDServiceVO> getArlSubID() {
        return arlSubID;
    }

    /**
     * @param arlSubID
     *            the arlSubID to set
     */
    public void setArlSubID(ArrayList<SubIDServiceVO> arlSubID) {
        this.arlSubID = arlSubID;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strSubID", strSubID)
                .append("arlSubID", arlSubID).toString();
    }
}
