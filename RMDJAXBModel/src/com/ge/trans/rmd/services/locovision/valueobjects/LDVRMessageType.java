package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ldvrMessageType", propOrder = { "id","assetOwnerName","roadInitial","roadNumber",
		"messageDirection","messageGenerateTime",
		"messageOutId", "messagePriority", "messageRecievedTime",
		"messageSentTime","messageSize","messageStatus","messageId","applicationId","messageTypeName","requestor",
		"requestDateTime","statusDateTime","templateNumber","templateVersion","commReasonCode","appRetryCount",
		"nackRetryCount","createdBy","createdDate","lastUpdatedBy","lastUpdatedDate","hexMessage","fileName",
		"device","originalMsgId","omrStatus","destination" })

@XmlRootElement
public class LDVRMessageType {
	protected String id;
	protected String assetOwnerName;
	protected String roadInitial;
	protected String roadNumber;
	protected String messageDirection;
	protected String messageGenerateTime;
	protected String messageOutId;
	protected String messagePriority;
	protected String messageRecievedTime;
	protected String messageSentTime;
	protected String messageSize;
	protected String messageStatus;
	protected String messageId;
	protected String applicationId;
	protected String messageTypeName;
	protected String requestor;
	protected String requestDateTime;
	protected String statusDateTime;
	protected String templateNumber;
	protected String templateVersion;
	protected String commReasonCode;
	protected String appRetryCount;
	protected String nackRetryCount;
	protected String createdBy;
	protected String createdDate;
	protected String lastUpdatedBy;
	protected String lastUpdatedDate;
	protected String hexMessage;
	protected String fileName;
	protected String device;
	protected String originalMsgId;
	protected String omrStatus;
	protected String destination;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAssetOwnerName() {
		return assetOwnerName;
	}
	public void setAssetOwnerName(String assetOwnerName) {
		this.assetOwnerName = assetOwnerName;
	}
	public String getRoadInitial() {
		return roadInitial;
	}
	public void setRoadInitial(String roadInitial) {
		this.roadInitial = roadInitial;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getMessageDirection() {
		return messageDirection;
	}
	public void setMessageDirection(String messageDirection) {
		this.messageDirection = messageDirection;
	}
	public String getMessageGenerateTime() {
		return messageGenerateTime;
	}
	public void setMessageGenerateTime(String messageGenerateTime) {
		this.messageGenerateTime = messageGenerateTime;
	}
	public String getMessageOutId() {
		return messageOutId;
	}
	public void setMessageOutId(String messageOutId) {
		this.messageOutId = messageOutId;
	}
	public String getMessagePriority() {
		return messagePriority;
	}
	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}
	public String getMessageRecievedTime() {
		return messageRecievedTime;
	}
	public void setMessageRecievedTime(String messageRecievedTime) {
		this.messageRecievedTime = messageRecievedTime;
	}
	public String getMessageSentTime() {
		return messageSentTime;
	}
	public void setMessageSentTime(String messageSentTime) {
		this.messageSentTime = messageSentTime;
	}
	public String getMessageSize() {
		return messageSize;
	}
	public void setMessageSize(String messageSize) {
		this.messageSize = messageSize;
	}
	public String getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getMessageTypeName() {
		return messageTypeName;
	}
	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public String getStatusDateTime() {
		return statusDateTime;
	}
	public void setStatusDateTime(String statusDateTime) {
		this.statusDateTime = statusDateTime;
	}
	public String getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	public String getTemplateVersion() {
		return templateVersion;
	}
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	public String getCommReasonCode() {
		return commReasonCode;
	}
	public void setCommReasonCode(String commReasonCode) {
		this.commReasonCode = commReasonCode;
	}
	public String getAppRetryCount() {
		return appRetryCount;
	}
	public void setAppRetryCount(String appRetryCount) {
		this.appRetryCount = appRetryCount;
	}
	public String getNackRetryCount() {
		return nackRetryCount;
	}
	public void setNackRetryCount(String nackRetryCount) {
		this.nackRetryCount = nackRetryCount;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getHexMessage() {
		return hexMessage;
	}
	public void setHexMessage(String hexMessage) {
		this.hexMessage = hexMessage;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getOriginalMsgId() {
		return originalMsgId;
	}
	public void setOriginalMsgId(String originalMsgId) {
		this.originalMsgId = originalMsgId;
	}
	public String getOmrStatus() {
		return omrStatus;
	}
	public void setOmrStatus(String omrStatus) {
		this.omrStatus = omrStatus;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}
