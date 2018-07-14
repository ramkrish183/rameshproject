/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AssetVO.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This class contains asset information
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans.xml;

import java.io.Serializable;

/**
 * This class contains asset information...
 **/

public class AssetVO implements Serializable {

    private static final long serialVersionUID = 1938391915037892360L;

    private String strAssetID = null;
    private String strRoadInitial = null;
    private String strRoadInitialID = null;
    private String strRoadNumber = null;
    private String strSource = null;

    /**
     * @return the strAssetID
     */
    public String getStrAssetID() {
        return strAssetID;
    }

    /**
     * @param strAssetID
     *            the strAssetID to set
     */
    public void setStrAssetID(String strAssetID) {
        this.strAssetID = strAssetID;
    }

    /**
     * @return the strRoadInitial
     */
    public String getStrRoadInitial() {
        return strRoadInitial;
    }

    /**
     * @param strRoadInitial
     *            the strRoadInitial to set
     */
    public void setStrRoadInitial(String strRoadInitial) {
        this.strRoadInitial = strRoadInitial;
    }

    /**
     * @return the strRoadInitialID
     */
    public String getStrRoadInitialID() {
        return strRoadInitialID;
    }

    /**
     * @param strRoadInitialID
     *            the strRoadInitialID to set
     */
    public void setStrRoadInitialID(String strRoadInitialID) {
        this.strRoadInitialID = strRoadInitialID;
    }

    /**
     * @return the strRoadNumber
     */
    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    /**
     * @param strRoadNumber
     *            the strRoadNumber to set
     */
    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }

    /**
     * @return the strSource
     */
    public String getStrSource() {
        return strSource;
    }

    /**
     * @param strSource
     *            the strSource to set
     */
    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

}
