/**
 * 
 */
package com.ge.trans.rmd.services.rxchange.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 212338353
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxChangeResponseType", propOrder = { "model", "requestId",
		"requestor", "requestLoggedDate", "customer", "revisionType",
		"roadNumber", "caseId", "rxTitle", "notes", "requestOwner","status","attachment","changesSuggested",
		"objId","filePath","fileName","userName","saveAsDraftFlag","rxChangeProcObjId","requestOwnerSSO","notestoRequestor",
		"typeOfChangeReq", "whitePaperPdffileName", "whitePaperPdffilePath", "rxchangeAuditInfoLst","recomObjId" })
@XmlRootElement
public class RxChangeResponseType {
	
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
	private String requestOwner;
	private String status;
	private String attachment;
	private String changesSuggested;
	private Long objId;
	private String filePath;
	private String fileName;
    private String userName;
    private String saveAsDraftFlag;
    private String rxChangeProcObjId;
    private String requestOwnerSSO;
    private String notestoRequestor;
    private String typeOfChangeReq;
    private String whitePaperPdffileName;
    private String whitePaperPdffilePath;
    private List<RxChangeResponseType> rxchangeAuditInfoLst;
    private String recomObjId;
    
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRequestOwner() {
		return requestOwner;
	}

	public void setRequestOwner(String requestOwner) {
		this.requestOwner = requestOwner;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getChangesSuggested() {
		return changesSuggested;
	}

	public void setChangesSuggested(String changesSuggested) {
		this.changesSuggested = changesSuggested;
	}

	/**
	 * @return the objId
	 */
	public Long getObjId() {
		return objId;
	}

	/**
	 * @param objId the objId to set
	 */
	public void setObjId(Long objId) {
		this.objId = objId;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the saveAsDraftFlag
	 */
	public String getSaveAsDraftFlag() {
		return saveAsDraftFlag;
	}

	/**
	 * @param saveAsDraftFlag the saveAsDraftFlag to set
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
	 * @param rxChangeProcObjId the rxChangeProcObjId to set
	 */
	public void setRxChangeProcObjId(String rxChangeProcObjId) {
		this.rxChangeProcObjId = rxChangeProcObjId;
	}

	/**
	 * @return the requestOwnerSSO
	 */
	public String getRequestOwnerSSO() {
		return requestOwnerSSO;
	}

	/**
	 * @param requestOwnerSSO the requestOwnerSSO to set
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
	 * @param notestoRequestor the notestoRequestor to set
	 */
	public void setNotestoRequestor(String notestoRequestor) {
		this.notestoRequestor = notestoRequestor;
	}

    public String getTypeOfChangeReq() {
        return typeOfChangeReq;
    }

    public void setTypeOfChangeReq(String typeOfChangeReq) {
        this.typeOfChangeReq = typeOfChangeReq;
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

    public List<RxChangeResponseType> getRxchangeAuditInfoLst() {
        return rxchangeAuditInfoLst;
    }

    public void setRxchangeAuditInfoLst(List<RxChangeResponseType> rxchangeAuditInfoLst) {
        this.rxchangeAuditInfoLst = rxchangeAuditInfoLst;
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
    
}
