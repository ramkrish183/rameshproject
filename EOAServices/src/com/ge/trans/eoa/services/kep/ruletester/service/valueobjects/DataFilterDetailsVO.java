/**
 * ============================================================
 * File : DataFilterDetailsVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.ruletester.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */

package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This Class consists of the value objects for DataFilterDetails
 * @History :
 ******************************************************************************/
public class DataFilterDetailsVO {

    private String dataName;
    private String value1;
    private String value2;
    private String func;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(final String dataName) {
        this.dataName = dataName;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(final String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(final String value2) {
        this.value2 = value2;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(final String func) {
        this.func = func;
    }

}
