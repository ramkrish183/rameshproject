/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: ViewLogVO.java
 * Description 			:
 * Package 				: com.ge.trans.eoa.services.cases.service.valueobjects;
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

import java.util.Date;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :21-07-2015
 * @Date Modified :21-07-2015
 * @Modified By :Vamshi
 * @Contact :
 * @Description :This is plain POJO class which carries log details.It Contains
 *              Corresponding variable Declarations along with their respective
 *              getters and setters.
 * @History :
 ******************************************************************************/
public class ViewLogVO {

    private Date creationDate;
    private String roadNumberHeader;
    private String roadNumber;
    private String caseId;
    private String rxQueue;
    private String customerId;
    private String caseTitle;
    private String caseObjid;

    public String getCaseObjid() {
        return caseObjid;
    }

    public void setCaseObjid(String caseObjid) {
        this.caseObjid = caseObjid;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getRoadNumberHeader() {
        return roadNumberHeader;
    }

    public void setRoadNumberHeader(String roadNumberHeader) {
        this.roadNumberHeader = roadNumberHeader;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getRxQueue() {
        return rxQueue;
    }

    public void setRxQueue(String rxQueue) {
        this.rxQueue = rxQueue;
    }

}
