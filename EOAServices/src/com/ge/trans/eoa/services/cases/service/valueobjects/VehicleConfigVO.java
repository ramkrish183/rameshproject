/**
 * ============================================================
 * Classification	: GE Confidential
 * File 			: VehicleConfigVO.java
 * Description 		:
 *
 * Package			: com.ge.trans.eoa.services.cases.service.valueobjects
 * Author 			: iGATE Global Solutions Ltd.
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		:
 * History
 * Modified By 		: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created : Nov 12 , 2015
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :This is a plain POJO class which carries details about
 *              queueNames along with thier Id's.
 * @History :
 ******************************************************************************/
public class VehicleConfigVO {

    String caseObjId;
    String model;
    String rxObjId;

    public String getCaseObjId() {
        return caseObjId;
    }

    public void setCaseObjId(String caseObjId) {
        this.caseObjId = caseObjId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRxObjId() {
        return rxObjId;
    }

    public void setRxObjId(String rxObjId) {
        this.rxObjId = rxObjId;
    }

}
