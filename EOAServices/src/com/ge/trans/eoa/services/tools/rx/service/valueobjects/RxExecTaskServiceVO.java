/**
 * ============================================================
 * File : RxExecTaskServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Jun 7, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jun 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RxExecTaskServiceVO extends BaseVO {

	private static final long serialVersionUID = 1234334L;
	private String strRecomTaskId;
	private String strTaskDeails;
	private String strTaskId;
	private String strSetFlag;
	private String strLastUpdatedBy;
	private String strDisable;
	private String strUSL;
	private String strLSL;
	private String strTarget;
	private Date lastUpdateDate;
	private String strTaskNotes;
	private String strTaskExecutedUserId;
	private Date creationDate;
	private String isRxModified;
    private String docTitle;
    private String docPath;

	/**
	 * @return the strDisable
	 */
	public String getStrDisable() {
		return strDisable;
	}

	/**
	 * @param strDisable
	 *            the strDisable to set
	 */
	public void setStrDisable(String strDisable) {
		this.strDisable = strDisable;
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
	 * @return the strRecomTaskId
	 */
	public String getStrRecomTaskId() {
		return strRecomTaskId;
	}

	/**
	 * @param strRecomTaskId
	 *            the strRecomTaskId to set
	 */
	public void setStrRecomTaskId(String strRecomTaskId) {
		this.strRecomTaskId = strRecomTaskId;
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
	 * @return the strTaskId
	 */
	public String getStrTaskId() {
		return strTaskId;
	}

	/**
	 * @param strTaskId
	 *            the strTaskId to set
	 */
	public void setStrTaskId(String strTaskId) {
		this.strTaskId = strTaskId;
	}

	/**
	 * @return the strSetFlag
	 */
	public String getStrSetFlag() {
		return strSetFlag;
	}

	/**
	 * @param strSetFlag
	 *            the strSetFlag to set
	 */
	public void setStrSetFlag(String strSetFlag) {
		this.strSetFlag = strSetFlag;
	}

	/**
	 * @return the strLastUpdatedBy
	 */
	public String getStrLastUpdatedBy() {
		return strLastUpdatedBy;
	}

	/**
	 * @param strLastUpdatedBy
	 *            the strLastUpdatedBy to set
	 */
	public void setStrLastUpdatedBy(String strLastUpdatedBy) {
		this.strLastUpdatedBy = strLastUpdatedBy;
	}

	/**
	 * Get the last updated Date of the task.
	 * 
	 * @return lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Put the last updated date of the task.
	 * 
	 * @param lastUpdateDate
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getStrTaskNotes() {
		return strTaskNotes;
	}

	public void setStrTaskNotes(String strTaskNotes) {
		this.strTaskNotes = strTaskNotes;
	}

	public String getStrTaskExecutedUserId() {
		return strTaskExecutedUserId;
	}

	public void setStrTaskExecutedUserId(String strTaskExecutedUserId) {
		this.strTaskExecutedUserId = strTaskExecutedUserId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getIsRxModified() {
		return isRxModified;
	}

	public void setIsRxModified(String isRxModified) {
		this.isRxModified = isRxModified;
	}
    public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
}
