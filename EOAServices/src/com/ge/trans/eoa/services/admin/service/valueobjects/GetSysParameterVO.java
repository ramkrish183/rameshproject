/**
 * ============================================================
 * Classification: GE Confidential
 * File : GetSysLookupHVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.service.valueobjects;

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

public class GetSysParameterVO extends BaseVO {

    private static final long serialVersionUID = -4457421669965534243L;
    private String title;
    private String value;

    /**
     * method to get title
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * method to set title
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * method to get value
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * method to set value
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
