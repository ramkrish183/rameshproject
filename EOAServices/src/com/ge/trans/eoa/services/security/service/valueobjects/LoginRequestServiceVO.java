/**
 * ============================================================
 * Classification: GE Confidential
 * File : UserDetailVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.security.valueobjects
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
package com.ge.trans.eoa.services.security.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 *
 * @Author 		: 
 * @Version 	: 1.0
 * @DateCreated: Oct 31, 2009
 * @DateModified : 
 * @ModifiedBy :   
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 19, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class LoginRequestServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 42158769598L;

    /** strPassword of Type String **/
    private String strPassword;
    /** strLocale of Type String **/
    private String strLocale;
    /** timeZoneOffset of Type Long **/
    private Long timeZoneOffset;

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getStrPassword() {
        return strPassword;
    }

    /**
     * @Author:
     * @param strPassword
     * @Description:
     */
    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getStrLocale() {
        return strLocale;
    }

    /**
     * @Author:
     * @param strLocale
     * @Description:
     */
    public void setStrLocale(String strLocale) {
        this.strLocale = strLocale;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Long getTimeZoneOffset() {
        return timeZoneOffset;
    }

    /**
     * @Author:
     * @param timeZoneOffset
     * @Description:
     */
    public void setTimeZoneOffset(Long timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }
}