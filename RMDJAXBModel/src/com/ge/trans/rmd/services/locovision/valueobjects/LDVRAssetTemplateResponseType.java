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
public class LDVRAssetTemplateResponseType {
	
	private String device;
	private List<AssetTemplateListType>  assetTemplateLists;
	
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public List<AssetTemplateListType> getAssetTemplateLists() {
		return assetTemplateLists;
	}
	public void setAssetTemplateLists(List<AssetTemplateListType> assetTemplateLists) {
		this.assetTemplateLists = assetTemplateLists;
	}

}
