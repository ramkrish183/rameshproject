/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: FaultCodeVO.java
 * Description 			:
 * Package 				: com.ge.trans.rmd.cm.valueobjects;
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
public class FaultCodeVO {
    private String faultOrigin;
    private String faultCode;
    private String faultDescription;
    private String subId;
    private Integer faultCodeObjid;

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getFaultOrigin() {
        return faultOrigin;
    }

    public void setFaultOrigin(String faultOrigin) {
        this.faultOrigin = faultOrigin;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

	/**
	 * @return the faultCodeObjid
	 */
	public Integer getFaultCodeObjid() {
		return faultCodeObjid;
	}

	/**
	 * @param faultCodeObjid the faultCodeObjid to set
	 */
	public void setFaultCodeObjid(Integer faultCodeObjid) {
		this.faultCodeObjid = faultCodeObjid;
	}

}
