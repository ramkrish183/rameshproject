/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AssetKey.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AssetKey .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys;

public class AssetKey {
    private Integer number = null;
    private Integer group = null;

    public AssetKey(Integer pNumber, Integer pGroup) {
        number = pNumber;
        group = pGroup;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param pNumber
     *            the number to set
     */
    public void setNumber(Integer pNumber) {
        number = pNumber;
    }

    /**
     * @return the group
     */
    public Integer getGroup() {
        return group;
    }

    /**
     * @param pGroup
     *            the group to set
     */
    public void setGroup(Integer pGroup) {
        group = pGroup;
    }
}
