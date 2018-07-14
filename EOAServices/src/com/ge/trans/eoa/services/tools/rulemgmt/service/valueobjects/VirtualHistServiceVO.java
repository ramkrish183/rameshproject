package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*Added by Koushik as part of OMDKM-1869 - Add Revision Notes and History to Virtual Parameters */
public class VirtualHistServiceVO extends BaseVO {
	private static final long serialVersionUID = 1658836924436491658L;
	private String strDateCreated;
	private String createdBy;
	private String versionNumber;
	private String note;
	
	public String getStrDateCreated() {
		return strDateCreated;
	}
	public void setStrDateCreated(String strDateCreated) {
		this.strDateCreated = strDateCreated;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}
