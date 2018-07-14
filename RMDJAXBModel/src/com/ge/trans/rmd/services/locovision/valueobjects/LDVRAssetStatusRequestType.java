/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author MSHIRAJUDDIN
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ldvrAssetStatusRequestType", propOrder = { "assetOwnerId","roadInitial","roadNumber",
		"device","daysToView"})

@XmlRootElement
public class LDVRAssetStatusRequestType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String daysToView;
	/**
	 * @return the assetOwnerId
	 */
	public String getAssetOwnerId() {
		return assetOwnerId;
	}
	/**
	 * @param assetOwnerId the assetOwnerId to set
	 */
	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}
	/**
	 * @return the roadInitial
	 */
	public String getRoadInitial() {
		return roadInitial;
	}
	/**
	 * @param roadInitial the roadInitial to set
	 */
	public void setRoadInitial(String roadInitial) {
		this.roadInitial = roadInitial;
	}
	/**
	 * @return the roadNumber
	 */
	public String getRoadNumber() {
		return roadNumber;
	}
	/**
	 * @param roadNumber the roadNumber to set
	 */
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
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
	 * @return the daysToView
	 */
	public String getDaysToView() {
		return daysToView;
	}
	/**
	 * @param daysToView the daysToView to set
	 */
	public void setDaysToView(String daysToView) {
		this.daysToView = daysToView;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LDVRAssetStatusRequestVO [assetOwnerId=" + assetOwnerId
				+ ", roadInitial=" + roadInitial + ", roadNumber=" + roadNumber
				+ ", device=" + device + ", daysToView=" + daysToView + "]";
	}
	

}
