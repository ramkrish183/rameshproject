package com.ge.trans.rmd.services.locovision.valueobjects;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ldvrMessageHistoryResponseType", propOrder = { "messages","errorCode","errorDescription","maxRecordsReturned" })

@XmlRootElement
public class LDVRMessageHistoryResponseType {

	protected List<LDVRMessageType> messages;
	protected String errorCode;
	protected String errorDescription;
	protected String maxRecordsReturned;
	
	public List<LDVRMessageType> getMessages(){
		if(messages == null){
			messages = new ArrayList<LDVRMessageType>();
		}
		return this.messages;
	}
	
	public void setMessages(List<LDVRMessageType> messages){
		this.messages = messages;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getMaxRecordsReturned() {
		return maxRecordsReturned;
	}

	public void setMaxRecordsReturned(String maxRecordsReturned) {
		this.maxRecordsReturned = maxRecordsReturned;
	}
	
}
