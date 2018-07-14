package com.ge.trans.eoa.services.cases.service.valueobjects;

public class CaseRepairCodeVO {
	String repairCodeId;
	String repairCode;
	String repairCodeDescription;
	
	public CaseRepairCodeVO() {
		super();
	}
	public CaseRepairCodeVO(String repairCode,
			String repairCodeDesc, String repairCodeId) {
		this.repairCode = repairCode;
		this.repairCodeDescription = repairCodeDesc;
		this.repairCodeId = repairCodeId;
	}
	public String getRepairCodeId() {
		return repairCodeId;
	}
	public void setRepairCodeId(String repairCodeId) {
		this.repairCodeId = repairCodeId;
	}
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public String getRepairCodeDescription() {
		return repairCodeDescription;
	}
	public void setRepairCodeDescription(String repairCodeDescription) {
		this.repairCodeDescription = repairCodeDescription;
	}
}
