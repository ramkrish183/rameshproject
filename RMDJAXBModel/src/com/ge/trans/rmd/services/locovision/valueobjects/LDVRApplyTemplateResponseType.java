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
public class LDVRApplyTemplateResponseType {

	private List<TemplatesType> templates;
	private List<MessageStatusType> messageStatus;
	private List<ExcludedAssetsType> excludedAssets;
	private List<ApplyTemplatesType> applyTemplates;
	private List<SkipTemplatesType> skipTemplates;
	
	/**
	 * @return the templates
	 */
	public List<TemplatesType> getTemplates() {
		return templates;
	}
	/**
	 * @param templates the templates to set
	 */
	public void setTemplates(List<TemplatesType> templates) {
		this.templates = templates;
	}
	/**
	 * @return the messageStatus
	 */
	public List<MessageStatusType> getMessageStatus() {
		return messageStatus;
	}
	/**
	 * @param messageStatus the messageStatus to set
	 */
	public void setMessageStatus(List<MessageStatusType> messageStatus) {
		this.messageStatus = messageStatus;
	}
	/**
	 * @return the excludedAssets
	 */
	public List<ExcludedAssetsType> getExcludedAssets() {
		return excludedAssets;
	}
	/**
	 * @param excludedAssets the excludedAssets to set
	 */
	public void setExcludedAssets(List<ExcludedAssetsType> excludedAssets) {
		this.excludedAssets = excludedAssets;
	}
	/**
	 * @return the applyTemplates
	 */
	public List<ApplyTemplatesType> getApplyTemplates() {
		return applyTemplates;
	}
	/**
	 * @param applyTemplates the applyTemplates to set
	 */
	public void setApplyTemplates(List<ApplyTemplatesType> applyTemplates) {
		this.applyTemplates = applyTemplates;
	}
	/**
	 * @return the skipTemplates
	 */
	public List<SkipTemplatesType> getSkipTemplates() {
		return skipTemplates;
	}
	/**
	 * @param skipTemplates the skipTemplates to set
	 */
	public void setSkipTemplates(List<SkipTemplatesType> skipTemplates) {
		this.skipTemplates = skipTemplates;
	}
	
}
