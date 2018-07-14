/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddEditRxServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 3, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RecomDelvInfoServiceVO extends BaseVO {

    static final long serialVersionUID = 35462589L;
    private String strRxObjid;
    private String strCaseID;
    private String strRxTitle;
    private String strUrgRepair;
    private String strEstmRepTime;
    private String strRxCaseID;
    private String strRxStatus;
    private String strRecomNotes;
    private String strFileName;
    private String strFilePath;
    private Date dtRxDelvDate;
    private int iSequence;
    private String customerId;
    private RxDetailsVO rxDetailsVO;
    private List<RxTaskDetailsVO> taskDetailsList;
    private List<String> rxIdsList;
    private String pdfURL;
    private String revisionNo;
    private Long rxObjId;
    private String caseObjId;
    private String userId;
    private List<RxDetailsVO> arlRxDetailsVO;
    private String customerName;
    private int fdbkObjId;
    private String rxCaseId;
    private String pendingFeedBack;
    private String delvrdRxObjId;
    private String strRxObjId;
    private String isFromDeliver;
    private List<RecommDelvDocVO> lstAttachment;

    public List<RecommDelvDocVO> getLstAttachment() {
		return lstAttachment;
	}

	public void setLstAttachment(List<RecommDelvDocVO> lstAttachment) {
		this.lstAttachment = lstAttachment;
	}

	public String getIsFromDeliver() {
        return isFromDeliver;
    }

    public void setIsFromDeliver(String isFromDeliver) {
        this.isFromDeliver = isFromDeliver;
    }

    public String getStrRxObjId() {
        return strRxObjId;
    }

    public void setStrRxObjId(String strRxObjId) {
        this.strRxObjId = strRxObjId;
    }

    public String getDelvrdRxObjId() {
        return delvrdRxObjId;
    }

    public void setDelvrdRxObjId(String delvrdRxObjId) {
        this.delvrdRxObjId = delvrdRxObjId;
    }

    public String getStrRxObjid() {
        return strRxObjid;
    }

    public void setStrRxObjid(String strRxObjid) {
        this.strRxObjid = strRxObjid;
    }

    public int getiSequence() {
        return iSequence;
    }

    public void setiSequence(int iSequence) {
        this.iSequence = iSequence;
    }

    public String getPendingFeedBack() {
        return pendingFeedBack;
    }

    public void setPendingFeedBack(String pendingFeedBack) {
        this.pendingFeedBack = pendingFeedBack;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRxCaseId() {
        return rxCaseId;
    }

    public void setRxCaseId(String rxCaseId) {
        this.rxCaseId = rxCaseId;
    }

    public int getFdbkObjId() {
        return fdbkObjId;
    }

    public void setFdbkObjId(int fdbkObjId) {
        this.fdbkObjId = fdbkObjId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<RxDetailsVO> getArlRxDetailsVO() {
        return arlRxDetailsVO;
    }

    public void setArlRxDetailsVO(List<RxDetailsVO> arlRxDetailsVO) {
        this.arlRxDetailsVO = arlRxDetailsVO;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRevisionNo() {
        return revisionNo;
    }

    public void setRevisionNo(String revisionNo) {
        this.revisionNo = revisionNo;
    }

    public Long getRxObjId() {
        return rxObjId;
    }

    public void setRxObjId(Long rxObjId) {
        this.rxObjId = rxObjId;
    }

    public String getCaseObjId() {
        return caseObjId;
    }

    public void setCaseObjId(String caseObjId) {
        this.caseObjId = caseObjId;
    }

    /**
     * @return the pdfURL
     */
    public String getPdfURL() {
        return pdfURL;
    }

    /**
     * @param pdfURL
     *            the pdfURL to set
     */
    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public List<String> getRxIdsList() {
        return rxIdsList;
    }

    public void setRxIdsList(List<String> rxIdsList) {
        this.rxIdsList = rxIdsList;
    }

    public RxDetailsVO getRxDetailsVO() {
        return rxDetailsVO;
    }

    public void setRxDetailsVO(RxDetailsVO rxDetailsVO) {
        this.rxDetailsVO = rxDetailsVO;
    }

    public List<RxTaskDetailsVO> getTaskDetailsList() {
        return taskDetailsList;
    }

    public void setTaskDetailsList(List<RxTaskDetailsVO> taskDetailsList) {
        this.taskDetailsList = taskDetailsList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public RecomDelvInfoServiceVO() {
        super();
    }

    /**
     * @return the strRxTitle
     */
    public String getStrRxTitle() {
        return strRxTitle;
    }

    /**
     * @param strRxTitle
     *            the strRxTitle to set
     */
    public void setStrRxTitle(String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }

    /**
     * @return the strCaseID
     */
    public String getStrCaseID() {
        return strCaseID;
    }

    /**
     * @param strCaseID
     *            the strCaseID to set
     */
    public void setStrCaseID(String strCaseID) {
        this.strCaseID = strCaseID;
    }

    /**
     * @return the strUrgRepair
     */
    public String getStrUrgRepair() {
        return strUrgRepair;
    }

    /**
     * @param strUrgRepair
     *            the strUrgRepair to set
     */
    public void setStrUrgRepair(String strUrgRepair) {
        this.strUrgRepair = strUrgRepair;
    }

    /**
     * @return the strEstmRepTime
     */
    public String getStrEstmRepTime() {
        return strEstmRepTime;
    }

    /**
     * @param strEstmRepTime
     *            the strEstmRepTime to set
     */
    public void setStrEstmRepTime(String strEstmRepTime) {
        this.strEstmRepTime = strEstmRepTime;
    }

    /**
     * @return the strRxCaseID
     */
    public String getStrRxCaseID() {
        return strRxCaseID;
    }

    /**
     * @param strRxCaseID
     *            the strRxCaseID to set
     */
    public void setStrRxCaseID(String strRxCaseID) {
        this.strRxCaseID = strRxCaseID;
    }

    /**
     * @return the strRxStatus
     */
    public String getStrRxStatus() {
        return strRxStatus;
    }

    /**
     * @param strRxStatus
     *            the strRxStatus to set
     */
    public void setStrRxStatus(String strRxStatus) {
        this.strRxStatus = strRxStatus;
    }

    /**
     * @return the strRecomNotes
     */
    public String getStrRecomNotes() {
        return strRecomNotes;
    }

    /**
     * @param strRecomNotes
     *            the strRecomNotes to set
     */
    public void setStrRecomNotes(String strRecomNotes) {
        this.strRecomNotes = strRecomNotes;
    }

    /**
     * @return the strFileName
     */
    public String getStrFileName() {
        return strFileName;
    }

    /**
     * @param strFileName
     *            the strFileName to set
     */
    public void setStrFileName(String strFileName) {
        this.strFileName = strFileName;
    }

    /**
     * @return the strFilePath
     */
    public String getStrFilePath() {
        return strFilePath;
    }

    /**
     * @param strFilePath
     *            the strFilePath to set
     */
    public void setStrFilePath(String strFilePath) {
        this.strFilePath = strFilePath;
    }

    /**
     * @return the iSequence
     */
    public int getISequence() {
        return iSequence;
    }

    /**
     * @param sequence
     *            the iSequence to set
     */
    public void setISequence(int sequence) {
        iSequence = sequence;
    }

    /**
     * @return the dtRxDelvDate
     */
    public Date getDtRxDelvDate() {
        return dtRxDelvDate;
    }

    /**
     * @param dtRxDelvDate
     *            the dtRxDelvDate to set
     */
    public void setDtRxDelvDate(Date dtRxDelvDate) {
        this.dtRxDelvDate = dtRxDelvDate;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxObjid", strRxObjid)
                .append("strCaseID", strCaseID).append("strRxTitle", strRxTitle).append("strUrgRepair", strUrgRepair)
                .append("strEstmRepTime", strEstmRepTime).append("strRxCaseID", strRxCaseID)
                .append("strRxStatus", strRxStatus).append("strRecomNotes", strRecomNotes)
                .append("strFileName", strFileName).append("strFilePath", strFilePath).append("iSequence", iSequence)
                .toString();
    }
}
