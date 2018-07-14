/**
 * ============================================================
 * Classification	: GE Confidential
 * File 			: QueueDetailsVO.java
 * Description 		:
 * Package 			: com.ge.trans.eoa.services.cases.service.valueobjects
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
 * @Date Created :
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This is a plain POJO class which carries details about queue to
 *              be dispatched.
 * @History :
 ******************************************************************************/
public class DispatchToQueueVO {

    private String queueId;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

}
