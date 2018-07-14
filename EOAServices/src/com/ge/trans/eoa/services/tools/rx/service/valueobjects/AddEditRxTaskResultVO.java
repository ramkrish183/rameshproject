/**
 * ============================================================
 * File : EditRxTaskResultVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AddEditRxTaskResultVO extends BaseVO {

    static final long serialVersionUID = 176548L;
    private String strMand;
    private String strTaskObjID;
    private String strConditionalTask;
    private String strConditionalValues;
    private String strTaskType;
    private String strRowId;
    private String strNO;
    private String strTaskDeails;
    private String strSubTask;
    private String strUSL;
    private String strLSL;
    private String strTarget;
    // private String strFeedback;
    private String strRepairCode;
    private String strClosurePerc;
    private String strRepairCodeDesc;
    private String strRepairCodeId;

    // added for the new requirement on May 2012
    private String strURL;
    private List<AddEditRxTaskDocVO> addEditRxTaskDocVO;
    private List<RxTaskRepairCodeVO> repairCodes;
    /**
     * @return the strRowId
     */
    public String getStrRowId() {
        return strRowId;
    }

    /**
     * @param strRowId
     *            the strRowId to set
     */
    public void setStrRowId(String strRowId) {
        this.strRowId = strRowId;
    }

    /**
     * @return the strTaskObjID
     */
    public String getStrTaskObjID() {
        return strTaskObjID;
    }

    /**
     * @param strRowId
     *            the strTaskObjID to set
     */
    public void setStrTaskObjID(String strTaskObjID) {
        this.strTaskObjID = strTaskObjID;
    }

    public String getStrTaskType() {
        return strTaskType;
    }

    public void setStrTaskType(String strTaskType) {
        this.strTaskType = strTaskType;
    }

    public String getStrConditionalTask() {
        return strConditionalTask;
    }

    public void setStrConditionalTask(String strConditionalTask) {
        this.strConditionalTask = strConditionalTask;
    }

    public String getStrConditionalValues() {
        return strConditionalValues;
    }

    public void setStrConditionalValues(String strConditionalValues) {
        this.strConditionalValues = strConditionalValues;
    }

    /**
     * @return the strNO
     */
    public String getStrNO() {
        return strNO;
    }

    /**
     * @param strNO
     *            the strNO to set
     */
    public void setStrNO(String strNO) {
        this.strNO = strNO;
    }

    /**
     * @return the strTaskDeails
     */
    public String getStrTaskDeails() {
        return strTaskDeails;
    }

    /**
     * @param strTaskDeails
     *            the strTaskDeails to set
     */
    public void setStrTaskDeails(String strTaskDeails) {
        this.strTaskDeails = strTaskDeails;
    }

    /**
     * @return the strSubTask
     */
    public String getStrSubTask() {
        return strSubTask;
    }

    /**
     * @param strSubTask
     *            the strSubTask to set
     */
    public void setStrSubTask(String strSubTask) {
        this.strSubTask = strSubTask;
    }

    /**
     * @return the strUSL
     */
    public String getStrUSL() {
        return strUSL;
    }

    /**
     * @param strUSL
     *            the strUSL to set
     */
    public void setStrUSL(String strUSL) {
        this.strUSL = strUSL;
    }

    /**
     * @return the strLSL
     */
    public String getStrLSL() {
        return strLSL;
    }

    /**
     * @param strLSL
     *            the strLSL to set
     */
    public void setStrLSL(String strLSL) {
        this.strLSL = strLSL;
    }

    /**
     * @return the strTarget
     */
    public String getStrTarget() {
        return strTarget;
    }

    /**
     * @param strTarget
     *            the strTarget to set
     */
    public void setStrTarget(String strTarget) {
        this.strTarget = strTarget;
    }

    /**
     * @return the strMand
     */
    public String getStrMand() {
        return strMand;
    }

    /**
     * @param strMand
     *            the strMand to set
     */
    public void setStrMand(String strMand) {
        this.strMand = strMand;
    }
    /**
     * @return the strFeedback
     */
    /*
     * Removing feedback column from UI , it is achievable by Task Type public
     * String getStrFeedback() { return strFeedback; }
     *//**
       * @param strFeedback
       *            the strFeedback to set
       *//*
         * public void setStrFeedback(String strFeedback) { this.strFeedback =
         * strFeedback; }
         */

    /**
     * @return the strRepairCode
     */
    public String getStrRepairCode() {
        return strRepairCode;
    }

    /**
     * @param strRepairCode
     *            the strRepairCode to set
     */
    public void setStrRepairCode(String strRepairCode) {
        this.strRepairCode = strRepairCode;
    }

    /**
     * @return the strClosurePerc
     */
    public String getStrClosurePerc() {
        return strClosurePerc;
    }

    /**
     * @param strClosurePerc
     *            the strClosurePerc to set
     */
    public void setStrClosurePerc(String strClosurePerc) {
        this.strClosurePerc = strClosurePerc;
    }

    /**
     * @return the strURL
     */
    public String getStrURL() {
        return strURL;
    }

    /**
     * @param strURL
     *            the strURL to set
     */
    public void setStrURL(String strURL) {
        this.strURL = strURL;
    }

    /**
     * @return the addEditRxTaskDocVO
     */
    public List<AddEditRxTaskDocVO> getAddEditRxTaskDocVO() {
        return addEditRxTaskDocVO;
    }

    /**
     * @param addEditRxTaskDocVO
     *            the addEditRxTaskDocVO to set
     */
    public void setAddEditRxTaskDocVO(List<AddEditRxTaskDocVO> addEditRxTaskDocVO) {
        this.addEditRxTaskDocVO = addEditRxTaskDocVO;
    }

    public String getStrRepairCodeDesc() {
        return strRepairCodeDesc;
    }

    public void setStrRepairCodeDesc(String strRepairCodeDesc) {
        this.strRepairCodeDesc = strRepairCodeDesc;
    }

    public String getStrRepairCodeId() {
        return strRepairCodeId;
    }

    public void setStrRepairCodeId(String strRepairCodeId) {
        this.strRepairCodeId = strRepairCodeId;
    }

	public List<RxTaskRepairCodeVO> getRepairCodes() {
		return repairCodes;
	}

	public void setRepairCodes(List<RxTaskRepairCodeVO> repairCodes) {
		this.repairCodes = repairCodes;
	}
    
	public void addRepairCodes(RxTaskRepairCodeVO repairCode) {
		if(this.repairCodes == null) {
			this.repairCodes = new ArrayList<RxTaskRepairCodeVO>();
		}
		this.repairCodes.add(repairCode);
	}
}
