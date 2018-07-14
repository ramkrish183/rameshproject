package com.ge.trans.eoa.services.rxtranslation.service.valueobjects;

public class RxTransHistVO {
	private String strDateCreated;
    private String strCreatedBy;
    private String strRevisionNotes;
    private String strLanguage;  
    
    public RxTransHistVO(String strDateCreated,String strCreatedBy,String strRevisionNotes){
    	this.strCreatedBy=strCreatedBy;
    	this.strDateCreated=strDateCreated;
    	this.strRevisionNotes=strRevisionNotes;
    	
    }
    
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
