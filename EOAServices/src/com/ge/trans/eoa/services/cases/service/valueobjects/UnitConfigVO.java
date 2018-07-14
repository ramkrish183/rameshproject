/**
 * ============================================================
 * Classification: GE Confidential
 * File 			: UnitConfigVO.java
 * Description 		: VO for unit configuration
 * Package 			: com.ge.trans.eoa.services.cases.service.valueobjects
 * Author 			: 
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		: 
 * History
 * Modified By 		: Initial Release
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class UnitConfigVO {
    private String modelName;
    private String configGroup;
    private String configItem;

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
