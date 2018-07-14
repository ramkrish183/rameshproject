package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "callLogNotesResponseType", propOrder = { "callLogID",
		"callerName", "agentSSO", "agentName", "busniessArea", "callType",
		"location", "issueType", "notes", "fromDate", "toDate", "assetNumber",
		"customerId", "creationDate", "callEndedOn","callDurationSeconds","callStartedOn"})

@XmlRootElement
public class CallLogNotesResponseType {
    protected String callLogID;

    protected String callerName;

    protected String agentSSO;

    protected String agentName;

    protected String busniessArea;

    protected String callType;

    protected String location;

    protected String issueType;

    protected String notes;

    protected String fromDate;

    protected String toDate;

    protected String assetNumber;

    protected String customerId;

	protected String creationDate;
	
	private String callEndedOn;
	
	private String callDurationSeconds;
	
	private String callStartedOn;
	
    /**
     * @return the callLogID
     */
    public String getCallLogID() {
        return callLogID;
    }

    /**
     * @param callLogID
     *            the callLogID to set
     */
    public void setCallLogID(String callLogID) {
        this.callLogID = callLogID;
    }

    /**
     * @return the callerName
     */
    public String getCallerName() {
        return callerName;
    }

    /**
     * @param callerName
     *            the callerName to set
     */
    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    /**
     * @return the agentSSO
     */
    public String getAgentSSO() {
        return agentSSO;
    }

    /**
     * @param agentSSO
     *            the agentSSO to set
     */
    public void setAgentSSO(String agentSSO) {
        this.agentSSO = agentSSO;
    }

    /**
     * @return the agentName
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * @param agentName
     *            the agentName to set
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * @return the busniessArea
     */
    public String getBusniessArea() {
        return busniessArea;
    }

    /**
     * @param busniessArea
     *            the busniessArea to set
     */
    public void setBusniessArea(String busniessArea) {
        this.busniessArea = busniessArea;
    }

    /**
     * @return the callType
     */
    public String getCallType() {
        return callType;
    }

    /**
     * @param callType
     *            the callType to set
     */
    public void setCallType(String callType) {
        this.callType = callType;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the issueType
     */
    public String getIssueType() {
        return issueType;
    }

    /**
     * @param issueType
     *            the issueType to set
     */
    public void setIssueType(String issueType) {
        this.issueType = issueType;
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

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the assetNumber
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param assetNumber
     *            the assetNumber to set
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

	
	/**
	 * @return the callEndedOn
	 */
	public String getCallEndedOn() {
		return callEndedOn;
	}

	/**
	 * @param callEndedOn the callEndedOn to set
	 */
	public void setCallEndedOn(String callEndedOn) {
		this.callEndedOn = callEndedOn;
	}

	/**
	 * @return the callDurationSeconds
	 */
	public String getCallDurationSeconds() {
		return callDurationSeconds;
	}

	/**
	 * @param callDurationSeconds the callDurationSeconds to set
	 */
	public void setCallDurationSeconds(String callDurationSeconds) {
		this.callDurationSeconds = callDurationSeconds;
	}

    /**
     * @return the callStartedOn
     */
    public String getCallStartedOn() {
        return callStartedOn;
    }

    /**
     * @param callStartedOn the callStartedOn to set
     */
    public void setCallStartedOn(String callStartedOn) {
        this.callStartedOn = callStartedOn;
    }	
	
}
