/**
 * ============================================================
 * File : RxExecTaskDetailsServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects;
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

import java.util.List;
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
public class RxExecTaskDetailsServiceVO extends BaseVO {

    private static final long serialVersionUID = 12357844L;
    private java.util.Date dtRxIssueDate;
    private java.util.Date dtRxClosedDate;
    private String strAssetNumber;
    private String strRxTitle;
    private String strCaseId;
    private String strRepairAction;
    private String strClosedBy;
    private String strDefectMode;
    private List<RxExecTaskServiceVO> arlExecTasks;
    private String strRxExecutionId;
    private String strRxDelvId;
    private List<String> arlRecomTaskList;
    private String strRxCloseFlag;
    private String strFileName;
    private String strFilePath;
    private String strSolutionCaseId;
    private String notes;
    private String locationId;
    private String locoImpact;
    private String endUserScoring;
    private String userSeqId;
    

	private String strRecomId;
    private String isRxSaved;
    
    /* Added missing fields for Sprint - 13 - Rx Display data */
    private String strRxCaseId;
    private String eoaUserId;
    private String version;
    private String isMobile;
    
    private String recomNotes;
    private String rxDescription;
    private String rxDeliveredBy;
    private String prerequisites;
    private String customerName;
    private List<RxDeliveryAttachmentVO> rxDeliveryAttachments;
    
    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	


	public String getStrRxCaseId() {
        return strRxCaseId;
    }

    public void setStrRxCaseId(String strRxCaseId) {
        this.strRxCaseId = strRxCaseId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEoaUserId() {
        return eoaUserId;
    }

    public void setEoaUserId(String eoaUserId) {
        this.eoaUserId = eoaUserId;
    }

    /**
     * @return the strRxCaseId
     */
    public String getStrSolutionCaseId() {
        return strSolutionCaseId;
    }

    /**
     * @param strRxCaseId
     *            the strRxCaseId to set
     */
    public void setStrSolutionCaseId(String strSolutionCaseId) {
        this.strSolutionCaseId = strSolutionCaseId;
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
     * @return the strRxCloseFlag
     */
    public String getStrRxCloseFlag() {
        return strRxCloseFlag;
    }

    /**
     * @param strRxCloseFlag
     *            the strRxCloseFlag to set
     */
    public void setStrRxCloseFlag(String strRxCloseFlag) {
        this.strRxCloseFlag = strRxCloseFlag;
    }

    /**
     * @return the arlRecomTaskList
     */
    public List<String> getArlRecomTaskList() {
        return arlRecomTaskList;
    }

    /**
     * @param arlRecomTaskList
     *            the arlRecomTaskList to set
     */
    public void setArlRecomTaskList(List<String> arlRecomTaskList) {
        this.arlRecomTaskList = arlRecomTaskList;
    }

    /**
     * @return the strRxExecutionId
     */
    public String getStrRxExecutionId() {
        return strRxExecutionId;
    }

    /**
     * @param strRxExecutionId
     *            the strRxExecutionId to set
     */
    public void setStrRxExecutionId(String strRxExecutionId) {
        this.strRxExecutionId = strRxExecutionId;
    }

    /**
     * @return the strRxDelvId
     */
    public String getStrRxDelvId() {
        return strRxDelvId;
    }

    /**
     * @param strRxDelvId
     *            the strRxDelvId to set
     */
    public void setStrRxDelvId(String strRxDelvId) {
        this.strRxDelvId = strRxDelvId;
    }

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    /**
     * @return the arlExecTasks
     */
    public List<RxExecTaskServiceVO> getArlExecTasks() {
        return arlExecTasks;
    }

    /**
     * @param arlExecTasks
     *            the arlExecTasks to set
     */
    public void setArlExecTasks(List<RxExecTaskServiceVO> arlExecTasks) {
        this.arlExecTasks = arlExecTasks;
    }

    /**
     * @return the strAssetNumber
     */
    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    /**
     * @return the dtRxIssueDate
     */
    public java.util.Date getDtRxIssueDate() {
        return dtRxIssueDate;
    }

    /**
     * @param dtRxIssueDate
     *            the dtRxIssueDate to set
     */
    public void setDtRxIssueDate(java.util.Date dtRxIssueDate) {
        this.dtRxIssueDate = dtRxIssueDate;
    }

    /**
     * @return the dtRxClosedDate
     */
    public java.util.Date getDtRxClosedDate() {
        return dtRxClosedDate;
    }

    /**
     * @param dtRxClosedDate
     *            the dtRxClosedDate to set
     */
    public void setDtRxClosedDate(java.util.Date dtRxClosedDate) {
        this.dtRxClosedDate = dtRxClosedDate;
    }

    /**
     * @return the strRepairAction
     */
    public String getStrRepairAction() {
        return strRepairAction;
    }

    /**
     * @param strRepairAction
     *            the strRepairAction to set
     */
    public void setStrRepairAction(String strRepairAction) {
        this.strRepairAction = strRepairAction;
    }

    /**
     * @return the strClosedBy
     */
    public String getStrClosedBy() {
        return strClosedBy;
    }

    /**
     * @param strClosedBy
     *            the strClosedBy to set
     */
    public void setStrClosedBy(String strClosedBy) {
        this.strClosedBy = strClosedBy;
    }

    /**
     * @return the strDefectMode
     */
    public String getStrDefectMode() {
        return strDefectMode;
    }

    /**
     * @param strDefectMode
     *            the strDefectMode to set
     */
    public void setStrDefectMode(String strDefectMode) {
        this.strDefectMode = strDefectMode;
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
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLocoImpact() {
        return locoImpact;
    }

    public void setLocoImpact(String locoImpact) {
        this.locoImpact = locoImpact;
    }

    public String getStrRecomId() {
        return strRecomId;
    }

    public void setStrRecomId(String strRecomId) {
        this.strRecomId = strRecomId;
    }
    
    public String getIsRxSaved() {
		return isRxSaved;
	}

	public void setIsRxSaved(String isRxSaved) {
		this.isRxSaved = isRxSaved;
	}

	public String getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(String isMobile) {
		this.isMobile = isMobile;
	}

	public String getEndUserScoring() {
		return endUserScoring;
	}

	public void setEndUserScoring(String endUserScoring) {
		this.endUserScoring = endUserScoring;
	}

    public String getUserSeqId() {
        return userSeqId;
    }

    public void setUserSeqId(String userSeqId) {
        this.userSeqId = userSeqId;
    }

	public String getRecomNotes() {
		return recomNotes;
	}

	public void setRecomNotes(String recomNotes) {
		this.recomNotes = recomNotes;
	}

	public String getRxDescription() {
		return rxDescription;
	}

	public void setRxDescription(String rxDescription) {
		this.rxDescription = rxDescription;
	}

	public String getRxDeliveredBy() {
		return rxDeliveredBy;
	}

	public void setRxDeliveredBy(String rxDeliveredBy) {
		this.rxDeliveredBy = rxDeliveredBy;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<RxDeliveryAttachmentVO> getRxDeliveryAttachments() {
		return rxDeliveryAttachments;
	}

	public void setRxDeliveryAttachments(List<RxDeliveryAttachmentVO> rxDeliveryAttachments) {
		this.rxDeliveryAttachments = rxDeliveryAttachments;
	}
}
