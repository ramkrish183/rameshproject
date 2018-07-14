/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: RecommDetailsVO.java
 * Description 			:
 * Package 				:com.ge.trans.eoa.services.cases.service.valueobjects;
 * Author 				: iGATE Global Solutions Ltd.
 * Last Edited By 		:
 * Version 				: 1.0
 * Created on 			:
 * History				:
 * Modified By 			: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.cases.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This is plain POJO class which contains queueId,queueName
 *              Declarations along with their respective getters and setters.
 * @History :
 ******************************************************************************/
public class RecommDetailsVO {

    private String eServiceRxStatus;
    private String B2BRxStatus;

    public String geteServiceRxStatus() {
        return eServiceRxStatus;
    }

    public void seteServiceRxStatus(String eServiceRxStatus) {
        this.eServiceRxStatus = eServiceRxStatus;
    }

    public String getB2BRxStatus() {
        return B2BRxStatus;
    }

    public void setB2BRxStatus(String b2bRxStatus) {
        B2BRxStatus = b2bRxStatus;
    }

}
