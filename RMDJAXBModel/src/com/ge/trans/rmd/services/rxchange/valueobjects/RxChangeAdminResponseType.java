/**
 * 
 */
package com.ge.trans.rmd.services.rxchange.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.ge.trans.rmd.services.admin.valueobjects.ElementResponseType;

/**
 * @author 307009968
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "rxChangeAdminResponseType", propOrder = { "objId",
        "rxChangeRequestId", "newRxCreated","anyChangeinMaterial","triggerLogicChange", "unfamiliarSystemChange","rxsImpacted","noOfRxAttachment","modelsImpacted","customers", "summaryOfChanges", "acceptanceFlag", "internalNotes", "reviewerNotes", "targetImplementationDate", "rxChangeReasons", "userId","strModel","strCustomer","rxList","additionalReviewer", "rxchangeAuditInfoLst"})

@XmlRootElement
public class RxChangeAdminResponseType { 

	private String objId;
	private String rxChangeRequestId;
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
	private String strModel;
	private String strCustomer;
	private String rxList;
	private String additionalReviewer;
	private List<RxChangeResponseType> rxchangeAuditInfoLst;
	
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
	 *//*
	public List<String> getRxsImpacted() {
		return rxsImpacted;
	}
	*//**
	 * @param rxsImpacted the rxsImpacted to set
	 *//*
	public void setRxsImpacted(List<String> rxsImpacted) {
		this.rxsImpacted = rxsImpacted;
	}*/
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
	public String getRxChangeReasons() {
		return rxChangeReasons;
	}
	/**
	 * @param rxChangeReasons the rxChangeReasons to set
	 */
	public void setRxChangeReasons(String rxChangeReasons) {
		this.rxChangeReasons = rxChangeReasons;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**rxChangeAdminResponseType
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the strModel
	 */
	public String getStrModel() {
		return strModel;
	}
	/**
	 * @param strModel the strModel to set
	 */
	public void setStrModel(String strModel) {
		this.strModel = strModel;
	}
	/**
	 * @return the strCustomer
	 */
	public String getStrCustomer() {
		return strCustomer;
	}
	/**
	 * @param strCustomer the strCustomer to set
	 */
	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
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
    public List<RxChangeResponseType> getRxchangeAuditInfoLst() {
        return rxchangeAuditInfoLst;
    }
    public void setRxchangeAuditInfoLst(List<RxChangeResponseType> rxchangeAuditInfoLst) {
        this.rxchangeAuditInfoLst = rxchangeAuditInfoLst;
    }  
}
