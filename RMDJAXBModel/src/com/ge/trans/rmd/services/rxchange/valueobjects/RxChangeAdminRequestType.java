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
 * @author 307009968
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "rxChangeAdminRequestType", propOrder = { "objId", 
        "rxChangeRequestId", "reViewerUserSeqId","newRxCreated","anyChangeinMaterial","triggerLogicChange", "unfamiliarSystemChange","rxsImpacted","noOfRxAttachment", "modelsImpacted","customers", "summaryOfChanges", "acceptanceFlag", "internalNotes", "reviewerNotes", "targetImplementationDate", "rxChangeReasons", "userId","saveAsDraft", "adminEmailIdLst","draftmodelImpacted","draftcustomerImpacted","draftrxsImpacted","additionalReviewer"})

@XmlRootElement
public class RxChangeAdminRequestType { 

	private String objId;
	private String rxChangeRequestId;
	private long reViewerUserSeqId;
	private String newRxCreated;
	private String anyChangeinMaterial;
	private String triggerLogicChange;
	private String unfamiliarSystemChange;
	private List<String> rxsImpacted;
	private String noOfRxAttachment;
	private List<String> modelsImpacted;
	private List<String> customers;
	private String summaryOfChanges;
	private String acceptanceFlag;
	private String internalNotes;
	private String reviewerNotes;
	private String targetImplementationDate;
	private List<String> rxChangeReasons;
	private String userId;
	private String saveAsDraft;
	private String adminEmailIdLst;
	private List<String> draftmodelImpacted;
	private List<String> draftcustomerImpacted;
	private List<String> draftrxsImpacted;
	private List<String> additionalReviewer;
	
		
	/**
	 * @return the objId
	 */
	public String getObjId() {
		return objId;
	}
	/**
	 * @param objId the objId to set
	 */
	public void setObjId(String objId) {
		this.objId = objId;
	}
	/**
	 * @return the rxChangeRequestId
	 */
	public String getRxChangeRequestId() {
		return rxChangeRequestId;
	}
	/**
	 * @param rxChangeRequestId the rxChangeRequestId to set
	 */
	public void setRxChangeRequestId(String rxChangeRequestId) {
		this.rxChangeRequestId = rxChangeRequestId;
	}
	
	public long getReViewerUserSeqId() {
        return reViewerUserSeqId;
    }
    public void setReViewerUserSeqId(long reViewerUserSeqId) {
        this.reViewerUserSeqId = reViewerUserSeqId;
    }
    /**
	 * @return the newRxCreated
	 */
	public String getNewRxCreated() {
		return newRxCreated;
	}
	/**
	 * @param newRxCreated the newRxCreated to set
	 */
	public void setNewRxCreated(String newRxCreated) {
		this.newRxCreated = newRxCreated;
	}
	/**
	 * @return the anyChangeinMaterial
	 */
	public String getAnyChangeinMaterial() {
		return anyChangeinMaterial;
	}
	/**
	 * @param anyChangeinMaterial the anyChangeinMaterial to set
	 */
	public void setAnyChangeinMaterial(String anyChangeinMaterial) {
		this.anyChangeinMaterial = anyChangeinMaterial;
	}
	/**
	 * @return the triggerLogicChange
	 */
	public String getTriggerLogicChange() {
		return triggerLogicChange;
	}
	/**
	 * @param triggerLogicChange the triggerLogicChange to set
	 */
	public void setTriggerLogicChange(String triggerLogicChange) {
		this.triggerLogicChange = triggerLogicChange;
	}
	/**
	 * @return the unfamiliarSystemChange
	 */
	public String getUnfamiliarSystemChange() {
		return unfamiliarSystemChange;
	}
	/**
	 * @param unfamiliarSystemChange the unfamiliarSystemChange to set
	 */
	public void setUnfamiliarSystemChange(String unfamiliarSystemChange) {
		this.unfamiliarSystemChange = unfamiliarSystemChange;
	}
	/**
	 * @return the rxsImpacted
	 */
	public List<String> getRxsImpacted() {
		return rxsImpacted;
	}
	/**
	 * @param rxsImpacted the rxsImpacted to set
	 */
	public void setRxsImpacted(List<String> rxsImpacted) {
		this.rxsImpacted = rxsImpacted;
	}
	/**
	 * @return the noOfRxAttachment
	 */
	public String getNoOfRxAttachment() {
		return noOfRxAttachment;
	}
	/**
	 * @param noOfRxAttachment the noOfRxAttachment to set
	 */
	public void setNoOfRxAttachment(String noOfRxAttachment) {
		this.noOfRxAttachment = noOfRxAttachment;
	}
	/**
	 * @return the modelsImpacted
	 */
	public List<String> getModelsImpacted() {
		return modelsImpacted;
	}
	/**
	 * @param modelsImpacted the modelsImpacted to set
	 */
	public void setModelsImpacted(List<String> modelsImpacted) {
		this.modelsImpacted = modelsImpacted;
	}
	/**
	 * @return the customers
	 */
	public List<String> getCustomers() {
		return customers;
	}
	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(List<String> customers) {
		this.customers = customers;
	}
	/**
	 * @return the summaryOfChanges
	 */
	public String getSummaryOfChanges() {
		return summaryOfChanges;
	}
	/**
	 * @param summaryOfChanges the summaryOfChanges to set
	 */
	public void setSummaryOfChanges(String summaryOfChanges) {
		this.summaryOfChanges = summaryOfChanges;
	}
	/**
	 * @return the acceptanceFlag
	 */
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}
	/**
	 * @param acceptanceFlag the acceptanceFlag to set
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}
	/**
	 * @return the internalNotes
	 */
	public String getInternalNotes() {
		return internalNotes;
	}
	/**
	 * @param internalNotes the internalNotes to set
	 */
	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}
	/**
	 * @return the reviewerNotes
	 */
	public String getReviewerNotes() {
		return reviewerNotes;
	}
	/**
	 * @param reviewerNotes the reviewerNotes to set
	 */
	public void setReviewerNotes(String reviewerNotes) {
		this.reviewerNotes = reviewerNotes;
	}
	/**
	 * @return the targetImplementationDate
	 */
	public String getTargetImplementationDate() {
		return targetImplementationDate;
	}
	/**
	 * @param targetImplementationDate the targetImplementationDate to set
	 */
	public void setTargetImplementationDate(String targetImplementationDate) {
		this.targetImplementationDate = targetImplementationDate;
	}
	/**
	 * @return the rxChangeReasons
	 */
	public List<String> getRxChangeReasons() {
		return rxChangeReasons;
	}
	/**
	 * @param rxChangeReasons the rxChangeReasons to set
	 */
	public void setRxChangeReasons(List<String> rxChangeReasons) {
		this.rxChangeReasons = rxChangeReasons;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the saveAsDraft
	 */
	public String getSaveAsDraft() {
		return saveAsDraft;
	}
	/**
	 * @param saveAsDraft the saveAsDraft to set
	 */
	public void setSaveAsDraft(String saveAsDraft) {
		this.saveAsDraft = saveAsDraft;
	}
	public String getAdminEmailIdLst() {
        return adminEmailIdLst;
    }
    public void setAdminEmailIdLst(String adminEmailIdLst) {
        this.adminEmailIdLst = adminEmailIdLst;
    }
    /**
	 * @return the draftmodelImpacted
	 */
	public List<String> getDraftmodelImpacted() {
		return draftmodelImpacted;
	}
	/**
	 * @param draftmodelImpacted the draftmodelImpacted to set
	 */
	public void setDraftmodelImpacted(List<String> draftmodelImpacted) {
		this.draftmodelImpacted = draftmodelImpacted;
	}
	/**
	 * @return the draftcustomerImpacted
	 */
	public List<String> getDraftcustomerImpacted() {
		return draftcustomerImpacted;
	}
	/**
	 * @param draftcustomerImpacted the draftcustomerImpacted to set
	 */
	public void setDraftcustomerImpacted(List<String> draftcustomerImpacted) {
		this.draftcustomerImpacted = draftcustomerImpacted;
	}
	/**
	 * @return the draftrxsImpacted
	 */
	public List<String> getDraftrxsImpacted() {
		return draftrxsImpacted;
	}
	/**
	 * @param draftrxsImpacted the draftrxsImpacted to set
	 */
	public void setDraftrxsImpacted(List<String> draftrxsImpacted) {
		this.draftrxsImpacted = draftrxsImpacted;
	}
	/**
	 * @return the additionalReviewer
	 */
	public List<String> getAdditionalReviewer() {
		return additionalReviewer;
	}
	/**
	 * @param additionalReviewer the additionalReviewer to set
	 */
	public void setAdditionalReviewer(List<String> additionalReviewer) {
		this.additionalReviewer = additionalReviewer;
	}
	@Override
    public String toString() {
        return "RxChangeAdminRequestType [objId=" + objId + ", rxChangeRequestId=" + rxChangeRequestId
                + ", reViewerUserSeqId=" + reViewerUserSeqId + ", newRxCreated=" + newRxCreated
                + ", anyChangeinMaterial=" + anyChangeinMaterial + ", triggerLogicChange=" + triggerLogicChange
                + ", unfamiliarSystemChange=" + unfamiliarSystemChange + ", rxsImpacted=" + rxsImpacted
                + ", noOfRxAttachment=" + noOfRxAttachment + ", modelsImpacted=" + modelsImpacted + ", customers="
                + customers + ", summaryOfChanges=" + summaryOfChanges + ", acceptanceFlag=" + acceptanceFlag
                + ", internalNotes=" + internalNotes + ", reviewerNotes=" + reviewerNotes
                + ", targetImplementationDate=" + targetImplementationDate + ", rxChangeReasons=" + rxChangeReasons
                + ", userId=" + userId + ", saveAsDraft=" + saveAsDraft + ", adminEmailIdLst=" + adminEmailIdLst + "]";
    }
   
}
