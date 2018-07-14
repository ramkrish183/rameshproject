/**
 * ============================================================
 * Classification: GE Confidential
 * File : BaseVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.valueobjects;

import java.io.Serializable;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringStyle;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 1234L;

    private String strUserName;
    private String strLanguage;
    private String strUserLanguage;

    // Added as part of sprint 21 Misc Changes
    private String strFirstName;
    private String strLastName;
    private Integer rowVersion;

    /**
     * ToStringBuilder class from commons-lang helps in creating strings
     * returned by the toString() method. We don't need the object hashcode in
     * the toString(), and the fields must be separated by a semi-colon. These
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

    public String getStrLanguage() {
        return strLanguage;
    }

    public void setStrLanguage(final String strLanguage) {
        this.strLanguage = strLanguage;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(final String strUserName) {
        this.strUserName = strUserName;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(final Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getStrUserLanguage() {
        return strUserLanguage;
    }

    public void setStrUserLanguage(final String strUserLanguage) {
        this.strUserLanguage = strUserLanguage;
    }

    public String getStrFirstName() {
        return strFirstName;
    }

    public void setStrFirstName(String strFirstName) {
        this.strFirstName = strFirstName;
    }

    public String getStrLastName() {
        return strLastName;
    }

    public void setStrLastName(String strLastName) {
        this.strLastName = strLastName;
    }

}