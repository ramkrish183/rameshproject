/**
 * ============================================================
 * Classification: GE Confidential
 * File : ParameterServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 14, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 14, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ParameterServiceVO extends BaseVO {

    static final long serialVersionUID = 76124869666L;
    public String parameterName;
    public String parameterNameID;
    public String columnType;
    public String value;
    public String paramDBName;
    public String virtualId;
	public String displayName;
	public String uomAbbr;
	
	public String getUomAbbr() {
		return uomAbbr;
	}

	public void setUomAbbr(String uomAbbr) {
		this.uomAbbr = uomAbbr;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterNameID() {
        return parameterNameID;
    }

    public void setParameterNameID(final String parameterNameID) {
        this.parameterNameID = parameterNameID;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(final String columnType) {
        this.columnType = columnType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParamDBName() {
        return paramDBName;
    }

    public void setParamDBName(String paramDBName) {
        this.paramDBName = paramDBName;
    }

    public String getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(String virtualId) {
        this.virtualId = virtualId;
    }

}