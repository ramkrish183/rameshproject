/**
 * ============================================================
 * Classification	: GE Confidential
 * File 			: QueueDetailsVO.java
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
 * @Date Created : Mar 16 , 2015
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :This is a plain POJO class which carries details about
 *              queueNames along with thier Id's.
 * @History :
 ******************************************************************************/
public class QueueDetailsVO {
    private String queueId;
    private String queueName;
    private String caseOwner;

    public String getCaseOwner() {
        return caseOwner;
    }

    public void setCaseOwner(String caseOwner) {
        this.caseOwner = caseOwner;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public QueueDetailsVO() {
        super();

    }

}
