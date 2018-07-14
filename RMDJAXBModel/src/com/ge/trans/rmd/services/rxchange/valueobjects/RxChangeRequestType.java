/**
 * 
 */
package com.ge.trans.rmd.services.rxchange.valueobjects;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.cases.valueobjects.RxDelvDocType;

/**
 * @author 307009968
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "rxChangeRequestType", propOrder = { "requestor",
        "revisionType", "rxTitle","specificRxTitle","generalRxTitle", "roadNumber","changesSuggested","caseId", "notes", "userId", "listCustomerIds", "listModelIds", "fileName", "fileData", "status", "statusObjId","filePath", "objId","requestOwner","userEmail", "adminEmailIdLst", "typeOfRxChange", "pdfEmailIdList", "triggerLogicNote","lstAttachment","screenName"})

@XmlRootElement
public class RxChangeRequestType { 

    private String objId;
    private String requestor;
    private String revisionType;
    private String rxTitle;
    private String specificRxTitle;
    private String generalRxTitle;
    private String roadNumber;
    private String caseId;
    private String changesSuggested;
    private String notes;
    private String userId;
    protected List<String> listCustomerIds;
    protected List<String> listModelIds;
    private String fileName;
    private byte[] fileData;
    private String status;
    private long statusObjId;
    private String filePath;
    private String requestOwner;
    private String userEmail;
    private String adminEmailIdLst;
    private List<String> typeOfRxChange;
    private String pdfEmailIdList;
    private String triggerLogicNote;
    private List<RxDelvDocType> lstAttachment;
    private String screenName;
    
    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getRequestor() {
        return requestor;
    }
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
    public String getRevisionType() {
        return revisionType;
    }
    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }
    public String getRxTitle() {
        return rxTitle;
    }
    public void setRxTitle(String rxTitle) {
        this.rxTitle = rxTitle;
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
    public String getChangesSuggested() {
        return changesSuggested;
    }
    public void setChangesSuggested(String changesSuggested) {
        this.changesSuggested = changesSuggested;
    }

    public List<String> getListCustomerIds() {
        return listCustomerIds;
    }
    public void setListCustomerIds(List<String> listCustomerIds) {
        this.listCustomerIds = listCustomerIds;
    }
    public List<String> getListModelIds() {
        return listModelIds;
    }
    public void setListModelIds(List<String> listModelIds) {
        this.listModelIds = listModelIds;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @return the requestOwner
	 */
	public String getRequestOwner() {
		return requestOwner;
	}
	/**
	 * @param requestOwner the requestOwner to set
	 */
	public void setRequestOwner(String requestOwner) {
		this.requestOwner = requestOwner;
	}
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getAdminEmailIdLst() {
        return adminEmailIdLst;
    }
    public void setAdminEmailIdLst(String adminEmailIdLst) {
        this.adminEmailIdLst = adminEmailIdLst;
    }
    public List<String> getTypeOfRxChange() {
        return typeOfRxChange;
    }
    public void setTypeOfRxChange(List<String> typeOfRxChange) {
        this.typeOfRxChange = typeOfRxChange;
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
    
    /**
	 * @return the lstAttachment
	 */
	public List<RxDelvDocType> getLstAttachment() {
		return lstAttachment;
	}
	/**
	 * @param lstAttachment the lstAttachment to set
	 */
	public void setLstAttachment(List<RxDelvDocType> lstAttachment) {
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
	@Override
    public String toString() {
        return "RxChangeRequestType [objId=" + objId + ", requestor=" + requestor + ", revisionType=" + revisionType
                + ", rxTitle=" + rxTitle + ", specificRxTitle=" + specificRxTitle + ", generalRxTitle="
                + generalRxTitle + ", roadNumber=" + roadNumber + ", caseId=" + caseId + ", changesSuggested="
                + changesSuggested + ", notes=" + notes + ", userId=" + userId + ", listCustomerIds=" + listCustomerIds
                + ", listModelIds=" + listModelIds + ", fileName=" + fileName + ", fileData="
                + Arrays.toString(fileData) + ", status=" + status + ", statusObjId=" + statusObjId + ", filePath="
                + filePath + ", requestOwner=" + requestOwner + ", userEmail=" + userEmail + ", adminEmailIdLst="
                + adminEmailIdLst + ", typeOfRxChange=" + typeOfRxChange + "pdfEmailIdstList = "+pdfEmailIdList+"triggerLogicNote"+triggerLogicNote+"]";
    }
}
