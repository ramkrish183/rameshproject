/**
 * ============================================================
 * Classification: GE Confidential
 * File 			: UnitConfigResponseType.java
 * Description 		: Response Type for unit config
 * Package 			: com.ge.trans.rmd.services.cases.valueobjects
 * Author 			: 
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		: 
 * History
 * Modified By 		: Initial Release
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.cases.valueobjects;

/*******************************************************************************
*
* @Author 		:
* @Version 	    : 1.0
* @Date Created : 
* @Date Modified:
* @Modified By  :
* @Contact 	    :
* @Description  :
* @History		:
*
******************************************************************************/
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unitConfigResponseType", propOrder = { "modelName", "configGroup", "configItem" })
@XmlRootElement
public class UnitConfigResponseType {
    @XmlElement(required = true)
    protected String modelName;
    @XmlElement(required = true)
    protected String configGroup;
    @XmlElement(required = true)
    protected String configItem;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }

    public String getConfigItem() {
        return configItem;
    }

    public void setConfigItem(String configItem) {
        this.configItem = configItem;
    }
}
