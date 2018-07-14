/**
 * ============================================================
 * File : RxConfigVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 16 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RxConfigVO extends BaseVO {
    private static final long serialVersionUID = 12343L;
    private String configFunction;
    private String configName;
    private String configValue1;
    private String configValue2;

    public String getConfigFunction() {
        return configFunction;
    }

    public void setConfigFunction(String configFunction) {
        this.configFunction = configFunction;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue1() {
        return configValue1;
    }

    public void setConfigValue1(String configValue1) {
        this.configValue1 = configValue1;
    }

    public String getConfigValue2() {
        return configValue2;
    }

    public void setConfigValue2(String configValue2) {
        this.configValue2 = configValue2;
    }

}
