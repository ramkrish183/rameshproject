package com.ge.trans.rmd.services.solutions.valueobjects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxTransHistRequestType", propOrder = { "strDateCreated", "strCreatedBy", "strRevisionNotes", "strLanguage" })
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RxTransHistRequestType {
	@XmlElement(required = true)
	protected String strDateCreated;
	@XmlElement(required = true)
    protected String strCreatedBy;
	@XmlElement(required = true)
    protected String strRevisionNotes;
	@XmlElement(required = true)
    protected String strLanguage;    
	public String getStrLanguage() {
		return strLanguage;
	}
	public void setStrLanguage(String strLanguage) {
		this.strLanguage = strLanguage;
	}
	public String getStrDateCreated() {
		return strDateCreated;
	}
	public void setStrDateCreated(String strDateCreated) {
		this.strDateCreated = strDateCreated;
	}
	public String getStrCreatedBy() {
		return strCreatedBy;
	}
	public void setStrCreatedBy(String strCreatedBy) {
		this.strCreatedBy = strCreatedBy;
	}
	public String getStrRevisionNotes() {
		return strRevisionNotes;
	}
	public void setStrRevisionNotes(String strRevisionNotes) {
		this.strRevisionNotes = strRevisionNotes;
	}
    
}
