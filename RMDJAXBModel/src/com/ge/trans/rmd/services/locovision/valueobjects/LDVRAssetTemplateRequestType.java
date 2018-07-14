/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MSHIRAJUDDIN
 *
 */
@XmlRootElement
public class LDVRAssetTemplateRequestType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String requestType;
	private List<String> lstDevice;
	private String templateView;



	/**
	 * @return the lstDevice
	 */
	public List<String> getLstDevice() {
		return lstDevice;
	}

	/**
	 * @param lstDevice the lstDevice to set
	 */
	public void setLstDevice(List<String> lstDevice) {
		this.lstDevice = lstDevice;
	}



	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getAssetOwnerId() {
		return assetOwnerId;
	}

	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

	public String getRoadInitial() {
		return roadInitial;
	}

	public void setRoadInitial(String value) {
		this.roadInitial = value;
	}
	
	

	public String getTemplateView() {
        return templateView;
    }

    public void setTemplateView(String templateView) {
        this.templateView = templateView;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LDVRAssetCriteriaType [assetOwnerId=" + assetOwnerId
				+ ", roadInitial=" + roadInitial + ", roadNumber=" + roadNumber
				+ ", requestType=" + requestType + ", lstDevice=" + lstDevice
				+ "]";
	}


	

}
