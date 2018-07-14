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
public class AssetTemplateListType {

	private String templateCategory;
	private String agtMessage;

	private List<AssetTemplateType> templateList;

	/**
	 * @return the templateCategory
	 */
	public String getTemplateCategory() {
		return templateCategory;
	}

	/**
	 * @param templateCategory the templateCategory to set
	 */
	public void setTemplateCategory(String templateCategory) {
		this.templateCategory = templateCategory;
	}

	/**
	 * @return the agtMessage
	 */
	public String getAgtMessage() {
		return agtMessage;
	}

	/**
	 * @param agtMessage the agtMessage to set
	 */
	public void setAgtMessage(String agtMessage) {
		this.agtMessage = agtMessage;
	}

	/**
	 * @return the templateList
	 */
	public List<AssetTemplateType> getTemplateList() {
		return templateList;
	}

	/**
	 * @param templateList the templateList to set
	 */
	public void setTemplateList(List<AssetTemplateType> templateList) {
		this.templateList = templateList;
	}

}
