/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   ComponentStatusVO.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   ComponentStatusVO
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.xml;

import java.io.Serializable;

public class ComponentStatusVO implements Serializable {

    /**
    * 
    */
    private static final long serialVersionUID = -7522199630629898324L;
    private String strName;
    private String strStatus;
    private String strTimeStamp;

    /**
     * @return the strName
     */
    public String getStrName() {
        return strName;
    }

    /**
     * @param strName
     *            the strName to set
     */
    public void setStrName(String strName) {
        this.strName = strName;
    }

    /**
     * @return the strStatus
     */
    public String getStrStatus() {
        return strStatus;
    }

    /**
     * @param strStatus
     *            the strStatus to set
     */
    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    /**
     * @return the strTimeStamp
     */
    public String getStrTimeStamp() {
        return strTimeStamp;
    }

    /**
     * @param strTimeStamp
     *            the strTimeStamp to set
     */
    public void setStrTimeStamp(String strTimeStamp) {
        this.strTimeStamp = strTimeStamp;
    }

}
