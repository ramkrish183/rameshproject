/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   BaseVO.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   BaseVO .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ge.trans.rmd.omi.beans.Date;

public abstract class BaseVO implements Serializable {

    private static final long serialVersionUID = 1718064627624450366L;

    public ToStringStyle getToStringStyle() {
        StandardToStringStyle stdStyle = new StandardToStringStyle();

        stdStyle.setUseIdentityHashCode(false);
        stdStyle.setContentStart("{");
        stdStyle.setContentEnd("}");
        stdStyle.setFieldSeparator(";");
        return stdStyle;
    }

    public final String getString(String value) {
        return trimToNull(value);
    }

    public final String getString(Object value) {
        return trimToNull(String.valueOf(value));
    }

    public final Integer getInteger(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (Integer.valueOf(localvalue)) : null);
    }

    public final Long getLong(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (Long.valueOf(localvalue)) : null);
    }

    public final Float getFloat(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (new Float(localvalue)) : null);
    }

    public final Double getDouble(String value) {
        String localvalue = value;
        localvalue = trimToNull(localvalue);
        return ((localvalue != null) ? (new Double(localvalue)) : null);
    }

    public final com.ge.trans.rmd.omi.beans.Date getDate(Long value) {
        java.util.Date date = new java.util.Date(value * 1000);
        return (new Date(date));
    }

    public static final String trimToNull(String value) {
        return StringUtils.trimToNull(value);
    }

    public static final String trimToEmpty(String value) {
        return StringUtils.trimToEmpty(value);
    }

    @Override
    public final String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}