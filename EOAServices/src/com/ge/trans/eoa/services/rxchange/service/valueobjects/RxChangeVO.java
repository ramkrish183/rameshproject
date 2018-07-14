package com.ge.trans.eoa.services.rxchange.service.valueobjects;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;

public class RxChangeVO extends BaseVO {

    private static final long serialVersionUID = 4986269726343465474L;
    private Long objid;
    private String model;
    private String requestId;
    private String requestor;
    private String requestLoggedDate;
    private String customer;
    private String revisionType;
    private String roadNumber;
    private String caseId;
    private String rxTitle;
    private String notes;
    private String changesSuggested;
    private List<String> listCustomer;
    private List<String> listModel;
    private String status;
    private String attachment;
    private String requestOwner;
    private String specificRxTitle;
    private String generalRxTitle;
    private String fileName;
    private byte[] fileData;
    private long statusObjId;
    private String filePath;
    private Date lastUpdatedDate;
    private Date creationDate;
    private String lastUpdatedBy;
    private String createdBy;
    private String userName;
    private String userEmail;
    private String saveAsDraftFlag;
    private String rxChangeProcObjId;
    private String adminEmailIdLst;
    private String requestOwnerSSO;
    private String notestoRequestor;
    private String typeOfRxChange;
    private List<String> typeOfRxChangeLst;
    private String whitePaperPdffileName;
    private String whitePaperPdffilePath;
    private String pdfEmailIdList;
    private String triggerLogicNote;
    private List<String> rxChangeAuditStrLst;
    private List<RxChangeVO> rxchangeAuditInfoLst;
    private List<RecommDelvDocVO> lstAttachment;
    private String screenName;
    private String recomObjId;
    
    public Long getObjid() {
        return objid;
    }
    public void setObjid(Long objid) {
        this.objid = objid;
    }
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }
    /**
     * @param model
     *            the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }
    /**
     * @param requestId
     *            the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    /**
     * @return the requestor
     */
    public String getRequestor() {
        return requestor;
    }
    /**
     * @param requestor
     *            the requestor to set
     */
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
    /**
     * @return the requestLoggedDate
     */
    public String getRequestLoggedDate() {
        return requestLoggedDate;
    }
    /**
     * @param requestLoggedDate
     *            the requestLoggedDate to set
     */
    public void setRequestLoggedDate(String requestLoggedDate) {
        this.requestLoggedDate = requestLoggedDate;
    }

    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    /**
     * @return the revisionType
     */
    public String getRevisionType() {
        return revisionType;
    }
    /**
     * @param revisionType
     *            the revisionType to set
     */
    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }
    /**
     * @return the roadNumber
     */
    public String getRoadNumber() {
        return roadNumber;
    }
    /**
     * @param roadNumber
     *            the roadNumber to set
     */
    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }
    /**
     * @return the caseId
     */
    public String getCaseId() {
        return caseId;
    }
    /**
     * @param caseId
     *            the caseId to set
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    /**
     * @return the rxTitle
     */
    public String getRxTitle() {
        return rxTitle;
    }
    /**
     * @param rxTitle
     *            the rxTitle to set
     */
    public void setRxTitle(String rxTitle) {
        this.rxTitle = rxTitle;
    }
    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }
    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getChangesSuggested() {
        return changesSuggested;
    }
    public void setChangesSuggested(String changesSuggested) {
        this.changesSuggested = changesSuggested;
    }
    public List<String> getListCustomer() {
        return listCustomer;
    }
    public void setListCustomer(List<String> listCustomer) {
        this.listCustomer = listCustomer;
    }
    public List<String> getListModel() {
        return listModel;
    }
    public void setListModel(List<String> listModel) {
        this.listModel = listModel;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public String getRequestOwner() {
        return requestOwner;
    }
    public void setRequestOwner(String requestOwner) {
        this.requestOwner = requestOwner;
    }
    public String getSpecificRxTitle() {
        return specificRxTitle;
    }
    public void setSpecificRxTitle(String specificRxTitle) {
        this.specificRxTitle = specificRxTitle;
    }
    public String getGeneralRxTitle() {
        return generalRxTitle;
    }
    public void setGeneralRxTitle(String generalRxTitle) {
        this.generalRxTitle = generalRxTitle;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public byte[] getFileData() {
        return fileData;
    }
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public long getStatusObjId() {
        return statusObjId;
    }
    public void setStatusObjId(long statusObjId) {
        this.statusObjId = statusObjId;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }
    /**
     * @param filePath
     *            the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }
    /**
     * @param userEmail
     *            the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    /**
     * @return the saveAsDraftFlag
     */
    public String getSaveAsDraftFlag() {
        return saveAsDraftFlag;
    }
    /**
     * @param saveAsDraftFlag
     *            the saveAsDraftFlag to set
     */
    public void setSaveAsDraftFlag(String saveAsDraftFlag) {
        this.saveAsDraftFlag = saveAsDraftFlag;
    }

    /**
     * @return the rxChangeProcObjId
     */
    public String getRxChangeProcObjId() {
        return rxChangeProcObjId;
    }
    /**
     * @param rxChangeProcObjId
     *            the rxChangeProcObjId to set
     */
    public void setRxChangeProcObjId(String rxChangeProcObjId) {
        this.rxChangeProcObjId = rxChangeProcObjId;
    }

    public String getAdminEmailIdLst() {
        return adminEmailIdLst;
    }

    public void setAdminEmailIdLst(String adminEmailIdLst) {
        this.adminEmailIdLst = adminEmailIdLst;
    }
    /**
     * @return the requestOwnerSSO
     */
    public String getRequestOwnerSSO() {
        return requestOwnerSSO;
    }
    /**
     * @param requestOwnerSSO
     *            the requestOwnerSSO to set
     */
    public void setRequestOwnerSSO(String requestOwnerSSO) {
        this.requestOwnerSSO = requestOwnerSSO;
    }

    /**
     * @return the notestoRequestor
     */
    public String getNotestoRequestor() {
        return notestoRequestor;
    }
    /**
     * @param notestoRequestor
     *            the notestoRequestor to set
     */
    public void setNotestoRequestor(String notestoRequestor) {
        this.notestoRequestor = notestoRequestor;
    }
    public String getTypeOfRxChange() {
        return typeOfRxChange;
    }
    public void setTypeOfRxChange(String typeOfRxChange) {
        this.typeOfRxChange = typeOfRxChange;
    }
    
    /**
	 * @return the typeOfRxChangeLst
	 */
	public List<String> getTypeOfRxChangeLst() {
		return typeOfRxChangeLst;
	}
	/**
	 * @param typeOfRxChangeLst the typeOfRxChangeLst to set
	 */
	public void setTypeOfRxChangeLst(List<String> typeOfRxChangeLst) {
		this.typeOfRxChangeLst = typeOfRxChangeLst;
	}
	public String getWhitePaperPdffileName() {
        return whitePaperPdffileName;
    }
    public void setWhitePaperPdffileName(String whitePaperPdffileName) {
        this.whitePaperPdffileName = whitePaperPdffileName;
    }
    public String getWhitePaperPdffilePath() {
        return whitePaperPdffilePath;
    }
    public void setWhitePaperPdffilePath(String whitePaperPdffilePath) {
        this.whitePaperPdffilePath = whitePaperPdffilePath;
    }
    
    public String getPdfEmailIdList() {
        return pdfEmailIdList;
    }
    public void setPdfEmailIdList(String pdfEmailIdList) {
        this.pdfEmailIdList = pdfEmailIdList;
    }   
    
    public String getTriggerLogicNote() {
        return triggerLogicNote;
    }
    public void setTriggerLogicNote(String triggerLogicNote) {
        this.triggerLogicNote = triggerLogicNote;
    }
    public List<RxChangeVO> getRxchangeAuditInfoLst() {
        return rxchangeAuditInfoLst;
    }
    public void setRxchangeAuditInfoLst(List<RxChangeVO> rxchangeAuditInfoLst) {
        this.rxchangeAuditInfoLst = rxchangeAuditInfoLst;
    }
    public List<String> getRxChangeAuditStrLst() {
        return rxChangeAuditStrLst;
    }
    public void setRxChangeAuditStrLst(List<String> rxChangeAuditStrLst) {
        this.rxChangeAuditStrLst = rxChangeAuditStrLst;
    }
    
    /**
	 * @return the lstAttachment
	 */
	public List<RecommDelvDocVO> getLstAttachment() {
		return lstAttachment;
	}
	/**
	 * @param lstAttachment the lstAttachment to set
	 */
	public void setLstAttachment(List<RecommDelvDocVO> lstAttachment) {
		this.lstAttachment = lstAttachment;
	}
	
	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}
	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	/**
	 * @return the recomObjId
	 */
	public String getRecomObjId() {
		return recomObjId;
	}
	/**
	 * @param recomObjId the recomObjId to set
	 */
	public void setRecomObjId(String recomObjId) {
		this.recomObjId = recomObjId;
	}
	@Override
    public String toString() {
        return "RxChangeVO [objid=" + objid + ",\n model=" + model + ",\n requestId=" + requestId + ",\n requestor="
                + requestor + ",\n requestLoggedDate=" + requestLoggedDate + ",\n customer=" + customer + ",\n revisionType="
                + revisionType + ",\n roadNumber=" + roadNumber + ",\n caseId=" + caseId + ",\n rxTitle=" + rxTitle
                + ",\n notes=" + notes + ",\n changesSuggested=" + changesSuggested + ",\n listCustomer=" + listCustomer
                + ",\n listModel=" + listModel + ",\n status=" + status + ",\n attachment=" + attachment + ",\n requestOwner="
                + requestOwner + ",\n specificRxTitle=" + specificRxTitle + ",\n generalRxTitle=" + generalRxTitle
                + ",\n fileName=" + fileName + ",\n fileData=" + Arrays.toString(fileData) + ",\n statusObjId=" + statusObjId
                + ",\n filePath=" + filePath + ",\n lastUpdatedDate=" + lastUpdatedDate + ",\n creationDate=" + creationDate
                + ",\n lastUpdatedBy=" + lastUpdatedBy + ",\n createdBy=" + createdBy + ",\n userName=" + userName
                + ",\n userEmail=" + userEmail + ",\n saveAsDraftFlag=" + saveAsDraftFlag + ",\n rxChangeProcObjId="
                + rxChangeProcObjId + ",\n adminEmailIdLst=" + adminEmailIdLst + ",\n requestOwnerSSO=" + requestOwnerSSO
                + ",\n notestoRequestor=" + notestoRequestor + ",\n typeOfRxChange=" + typeOfRxChange
                + ",\n whitePaperPdffileName=" + whitePaperPdffileName + ",\n whitePaperPdffilePath=" + whitePaperPdffilePath + "pdfEmailIdList = "+pdfEmailIdList+"]";
    }

    /*
     * public String toString() { return "\033[0;1mRxChange RequestID: " + this.requestId + "<br />"
     * + "\033[0;1mLogged By: " + this.requestor + "<br />"+ "\033[0;1mLogged Date: " +
     * this.requestLoggedDate + "<br />" + "\033[0;1mModel: " + this.model + "<br />" +
     * "\033[0;1mCustomer: " + this.customer + "<br />" + "\033[0;1mRoad Number: " + this.roadNumber
     * + "<br />" + "\033[0;1mRx Title: " + this.rxTitle + "<br />" + "\033[0;1mCase Id: " +
     * this.caseId + "<br />" + "\033[0;1mNotes: " + this.notes + "<br />" +
     * "\033[0;1mChanges Suggested: " + this.changesSuggested; }//("\033[0;1mThis Text Is Bold")
     */
}
