/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   BaseVO.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This class contains getToStringStyleObject() method which will return
 * ToStringStyle object. 
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class contains getToStringStyleObject() method which will return
 * ToStringStyle object.
 **/

public abstract class BaseVO implements Serializable {

    /**
     * ToStringBuilder class from commons-lang helps in creating strings
     * returned by the toString() method. We don't need the object hashcode in
     * the toString(), and the fields must be separted by a semi-colon. These
     * properties are being set here.
     * 
     * @param void
     * @return ToStringStyle
     */
    public static ToStringStyle getToStringStyleObject() {
        StandardToStringStyle stdToStringStyle = new StandardToStringStyle();

        stdToStringStyle.setUseIdentityHashCode(false);
        stdToStringStyle.setContentStart("{");
        stdToStringStyle.setContentEnd("}");
        stdToStringStyle.setFieldSeparator(";");
        return stdToStringStyle;
    }

}
