package com.ge.trans.rmd.services.locovision.valueobjects;
import java.util.ArrayList;
import java.util.List;



public class LDVRMessageHistoryRequestType {
	private List<String> messageId;
	private String applicationId;
	private String messageDirection;
	private String messageStatus;
	private List<LDVRAssetCriteriaType> assetCriteria;
	private String dateTimeFrom;
	private String dateTimeTo;


	public List<String> getMessageId() {
		return messageId;
	}

	public void setMessageId(List<String> messageId) {
		this.messageId = messageId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessageDirection() {
		return messageDirection;
	}

	public void setMessageDirection(String messageDirection) {
		this.messageDirection = messageDirection;
	}

	public List<LDVRAssetCriteriaType> getAssetCriteria() {
		if (assetCriteria == null) {
			assetCriteria = new ArrayList<LDVRAssetCriteriaType>();
		}
		return this.assetCriteria;
	}

	public String getDateTimeFrom() {
		return dateTimeFrom;
	}

	public void setDateTimeFrom(String dateTimeFrom) {
		this.dateTimeFrom = dateTimeFrom;
	}

	public String getDateTimeTo() {
		return dateTimeTo;
	}

	public void setDateTimeTo(String dateTimeTo) {
		this.dateTimeTo = dateTimeTo;
	}

	public void setAssetCriteria(List<LDVRAssetCriteriaType> assetCriteria) {
		this.assetCriteria = assetCriteria;
	}
	
	@Override
	public String toString() {
		String val = "applicationId: "+applicationId
						+"\nmessageId: "+messageId
						+"\ndateTimeFrom: "+dateTimeFrom
						+"\ndateTimeTo: "+dateTimeTo
						+"\nmessageDirection: "+messageDirection						
						+"\nmessageStatus: "+messageStatus;
		return val;
	}
}
