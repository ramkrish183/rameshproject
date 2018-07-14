/**
 * ============================================================
 * Classification: GE Confidential
 * File : LookupSearchServiceVO.java
 * Description : Service Search VO for Popup List Admin
 *
 * Package : com.ge.trans.rmd.services.admin.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 16, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class LookupSearchServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 3256345453455297L;
    /** strSearchBy of Type String **/
    private String strSearchBy;
    /** strValue of Type String **/
    private String strValue;

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getStrSearchBy() {
        return strSearchBy;
    }

    /**
     * @Author:
     * @param strSearchBy
     * @Description:
     */
    public void setStrSearchBy(final String strSearchBy) {
        this.strSearchBy = strSearchBy;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getStrValue() {
        return strValue;
    }

    /**
     * @Author:
     * @param strValue
     * @Description:
     */
    public void setStrValue(final String strValue) {
        this.strValue = strValue;
    }
}