/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

/**
 * @author MSHIRAJUDDIN
 *
 */
public class MessageStatusType {

	private String roadInitialAndNumber;
	private String device;
	private String templateType;
	private String templateNumberVersion;
	private String outboundMessageId;
	private String messageStatus;
	/**
	 * @return the roadInitialAndNumber
	 */
	public String getRoadInitialAndNumber() {
		return roadInitialAndNumber;
	}
	/**
	 * @param roadInitialAndNumber the roadInitialAndNumber to set
	 */
	public void setRoadInitialAndNumber(String roadInitialAndNumber) {
		this.roadInitialAndNumber = roadInitialAndNumber;
	}
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}
	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	/**
	 * @return the templateNumberVersion
	 */
	public String getTemplateNumberVersion() {
		return templateNumberVersion;
	}
	/**
	 * @param templateNumberVersion the templateNumberVersion to set
	 */
	public void setTemplateNumberVersion(String templateNumberVersion) {
		this.templateNumberVersion = templateNumberVersion;
	}
	/**
	 * @return the outboundMessageId
	 */
	public String getOutboundMessageId() {
		return outboundMessageId;
	}
	/**
	 * @param outboundMessageId the outboundMessageId to set
	 */
	public void setOutboundMessageId(String outboundMessageId) {
		this.outboundMessageId = outboundMessageId;
	}
	/**
	 * @return the messageStatus
	 */
	public String getMessageStatus() {
		return messageStatus;
	}
	/**
	 * @param messageStatus the messageStatus to set
	 */
	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageStatusVO [roadInitialAndNumber=" + roadInitialAndNumber
				+ ", device=" + device + ", templateType=" + templateType
				+ ", templateNumberVersion=" + templateNumberVersion
				+ ", outboundMessageId=" + outboundMessageId
				+ ", messageStatus=" + messageStatus + "]";
	}


}
