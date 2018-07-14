/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

/**
 * @author MSHIRAJUDDIN
 *
 */
public class TemplatesType {
	
	private String templateType;
	private String templateNumberVersion;
	private String description;
	
	
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TemplatesVO [templateType=" + templateType
				+ ", templateNumberVersion=" + templateNumberVersion
				+ ", description=" + description + "]";
	}


}
