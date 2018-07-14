/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.locovision.valueobjects.ActiveLcvLDVRCamDetailsType;
import com.ge.trans.rmd.services.locovision.valueobjects.LDVRPreservationRequestTemplate;

/**
 * @author MSHIRAJUDDIN
 *
 */
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assetLDVRDataType", propOrder = { "activePreservationTemplates","activeLcvLDVRCamDetails"})
*/
@XmlRootElement
public class AssetLDVRDataType {
	
	private List<LDVRPreservationRequestTemplate>  activePreservationTemplates;
	private List<ActiveLcvLDVRCamDetailsType>  activeLcvLDVRCamDetails;
	/**
	 * @return the activePreservationTemplates
	 */
	public List<LDVRPreservationRequestTemplate> getActivePreservationTemplates() {
		return activePreservationTemplates;
	}
	/**
	 * @param activePreservationTemplates the activePreservationTemplates to set
	 */
	public void setActivePreservationTemplates(
			List<LDVRPreservationRequestTemplate> activePreservationTemplates) {
		this.activePreservationTemplates = activePreservationTemplates;
	}
	/**
	 * @return the activeLcvLDVRCamDetails
	 */
	public List<ActiveLcvLDVRCamDetailsType> getActiveLcvLDVRCamDetails() {
		return activeLcvLDVRCamDetails;
	}
	/**
	 * @param activeLcvLDVRCamDetails the activeLcvLDVRCamDetails to set
	 */
	public void setActiveLcvLDVRCamDetails(
			List<ActiveLcvLDVRCamDetailsType> activeLcvLDVRCamDetails) {
		this.activeLcvLDVRCamDetails = activeLcvLDVRCamDetails;
	}


}
