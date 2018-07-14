/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   MessageID.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.msg;

import java.io.Serializable;

public class MessageID implements Serializable {

    private static final long serialVersionUID = 3119511473645618188L;

    private String strValue1 = null;
    private String strValue2 = null;
    private String strValue3 = null;
    private final String DOT = ".";

    /**
     * Gets the iD.
     * 
     * @return the iD
     */
    public String getID() {
        StringBuilder sBuffer = new StringBuilder("");

        strValue1 = BaseMessage.trimToNull(strValue1);
        if (strValue1 != null) {
            sBuffer.append(strValue1);
        }

        strValue2 = BaseMessage.trimToNull(strValue2);
        if (strValue2 != null) {
            if (strValue1 != null) {
                sBuffer.append(DOT);
            }

            sBuffer.append(strValue2);
        }

        strValue3 = BaseMessage.trimToNull(strValue3);
        if (strValue3 != null) {
            if (strValue1 != null || strValue2 != null) {
                sBuffer.append(DOT);
            }

            sBuffer.append(strValue3);
        }

        return ("".equals(sBuffer.toString()) ? null : sBuffer.toString());
    }

    /**
     * Gets the str value1.
     * 
     * @return the str value1
     */
    public String getStrValue1() {
        return strValue1;
    }

    /**
     * Sets the str value1.
     * 
     * @param pStrValue1
     *            the new str value1
     */
    public void setStrValue1(String pStrValue1) {
        strValue1 = pStrValue1;
    }

    /**
     * Gets the str value2.
     * 
     * @return the str value2
     */
    public String getStrValue2() {
        return strValue2;
    }

    /**
     * Sets the str value2.
     * 
     * @param pStrValue2
     *            the new str value2
     */
    public void setStrValue2(String pStrValue2) {
        strValue2 = pStrValue2;
    }

    /**
     * Gets the str value3.
     * 
     * @return the str value3
     */
    public String getStrValue3() {
        return strValue3;
    }

    /**
     * Sets the str value3.
     * 
     * @param pStrValue3
     *            the new str value3
     */
    public void setStrValue3(String pStrValue3) {
        strValue3 = pStrValue3;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getID();
    }
}