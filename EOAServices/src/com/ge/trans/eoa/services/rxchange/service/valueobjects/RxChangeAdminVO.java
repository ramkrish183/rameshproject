package com.ge.trans.eoa.services.rxchange.service.valueobjects;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvServiceVO;
import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;

public class RxChangeAdminVO extends BaseVO {

	private static final long serialVersionUID = 4986269726343465474L;
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
	private String rxChangeReasons;
	private String userId;
	private String model;
	private String customer;
	private String rxList;
	private String saveAsDraft;
	private String adminEmailIdLst;
	private String additionalReviewer;
	private List<String> additionalReviewerLst;
	private List<String> rxChangeReasonsLst;
	private List<RxChangeVO> rxchangeAuditInfoLst;
	
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
	 * @param newRxCreated
	 *            the newRxCreated to set
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
	 * @param anyChangeinMaterial
	 *            the anyChangeinMaterial to set
	 */
	public void setAnyChangeinMaterial(String anyChangeinMaterial) {
		this.anyChangeinMaterial = anyChangeinMaterial;
	}

	/**
	 * @return the unfamiliarSystemChange
	 */
	public String getUnfamiliarSystemChange() {
		return unfamiliarSystemChange;
	}

	/**
	 * @param unfamiliarSystemChange
	 *            the unfamiliarSystemChange to set
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
	 * @param rxsImpacted
	 *            the rxsImpacted to set
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
	 * @param noOfRxAttachment
	 *            the noOfRxAttachment to set
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
	 * @param modelsImpacted
	 *            the modelsImpacted to set
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
	 * @param customers
	 *            the customers to set
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
	 * @param summaryOfChanges
	 *            the summaryOfChanges to set
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
	 * @param acceptanceFlag
	 *            the acceptanceFlag to set
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
	 * @param internalNotes
	 *            the internalNotes to set
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
	 * @param reviewerNotes
	 *            the reviewerNotes to set
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
	 * @param targetImplementationDate
	 *            the targetImplementationDate to set
	 */
	public void setTargetImplementationDate(String targetImplementationDate) {
		this.targetImplementationDate = targetImplementationDate;
	}

	/**
	 * @return the rxChangeReasons
	 */
	public String getRxChangeReasons() {
		return rxChangeReasons;
	}

	/**
	 * @param rxChangeReasons
	 *            the rxChangeReasons to set
	 */
	public void setRxChangeReasons(String rxChangeReasons) {
		this.rxChangeReasons = rxChangeReasons;
	}

	/**
	 * @return the triggerLogicChange
	 */
	public String getTriggerLogicChange() {
		return triggerLogicChange;
	}

	/**
	 * @param triggerLogicChange
	 *            the triggerLogicChange to set
	 */
	public void setTriggerLogicChange(String triggerLogicChange) {
		this.triggerLogicChange = triggerLogicChange;
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
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	
	/**
	 * @return the rxList
	 */
	public String getRxList() {
		return rxList;
	}

	/**
	 * @param rxList the rxList to set
	 */
	public void setRxList(String rxList) {
		this.rxList = rxList;
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
	 * @return the additionalReviewer
	 */
	public String getAdditionalReviewer() {
		return additionalReviewer;
	}

	/**
	 * @param additionalReviewer the additionalReviewer to set
	 */
	public void setAdditionalReviewer(String additionalReviewer) {
		this.additionalReviewer = additionalReviewer;
	}

	/**
	 * @return the additionalReviewerLst
	 */
	public List<String> getAdditionalReviewerLst() {
		return additionalReviewerLst;
	}

	/**
	 * @param additionalReviewerLst the additionalReviewerLst to set
	 */
	public void setAdditionalReviewerLst(List<String> additionalReviewerLst) {
		this.additionalReviewerLst = additionalReviewerLst;
	}

	/**
	 * @return the rxChangeReasonsLst
	 */
	public List<String> getRxChangeReasonsLst() {
		return rxChangeReasonsLst;
	}

	/**
	 * @param rxChangeReasonsLst the rxChangeReasonsLst to set
	 */
	public void setRxChangeReasonsLst(List<String> rxChangeReasonsLst) {
		this.rxChangeReasonsLst = rxChangeReasonsLst;
	}
	

	public List<RxChangeVO> getRxchangeAuditInfoLst() {
        return rxchangeAuditInfoLst;
    }

    public void setRxchangeAuditInfoLst(List<RxChangeVO> rxchangeAuditInfoLst) {
        this.rxchangeAuditInfoLst = rxchangeAuditInfoLst;
    }

	@Override
    public String toString() {
        return "RxChangeAdminVO [objId=" + objId + ",\n rxChangeRequestId=" + rxChangeRequestId + ",\n reViewerUserSeqId="
                + reViewerUserSeqId + ",\n newRxCreated=" + newRxCreated + ",\n anyChangeinMaterial=" + anyChangeinMaterial
                + ",\n triggerLogicChange=" + triggerLogicChange + ",\n unfamiliarSystemChange=" + unfamiliarSystemChange
                + ",\n rxsImpacted=" + rxsImpacted + ",\n noOfRxAttachment=" + noOfRxAttachment + ",\n modelsImpacted="
                + modelsImpacted + ",\n customers=" + customers + ",\n summaryOfChanges=" + summaryOfChanges
                + ",\n acceptanceFlag=" + acceptanceFlag + ",\n internalNotes=" + internalNotes + ",\n reviewerNotes="
                + reviewerNotes + ",\n targetImplementationDate=" + targetImplementationDate + ",\n rxChangeReasons="
                + rxChangeReasons + ",\n userId=" + userId + ",\n model=" + model + ",\n customer=" + customer + ",\n rxList="
                + rxList + ",\n saveAsDraft=" + saveAsDraft + ",\n adminEmailIdLst=" + adminEmailIdLst + "]";
    }
}
