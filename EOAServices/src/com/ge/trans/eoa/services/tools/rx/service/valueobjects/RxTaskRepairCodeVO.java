package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

public class RxTaskRepairCodeVO {
	private String strRepairCode;
    private String strRepairCodeDesc;
    private String strRepairCodeId;
    
	public RxTaskRepairCodeVO(String strRepairCode, String strRepairCodeDesc,
			String strRepairCodeId) {
		super();
		this.strRepairCode = strRepairCode;
		this.strRepairCodeDesc = strRepairCodeDesc;
		this.strRepairCodeId = strRepairCodeId;
	}
	public RxTaskRepairCodeVO() {
		// TODO Auto-generated constructor stub
	}
	public String getStrRepairCode() {
		return strRepairCode;
	}
	public void setStrRepairCode(String strRepairCode) {
		this.strRepairCode = strRepairCode;
	}
	public String getStrRepairCodeDesc() {
		return strRepairCodeDesc;
	}
	public void setStrRepairCodeDesc(String strRepairCodeDesc) {
		this.strRepairCodeDesc = strRepairCodeDesc;
	}
	public String getStrRepairCodeId() {
		return strRepairCodeId;
	}
	public void setStrRepairCodeId(String strRepairCodeId) {
		this.strRepairCodeId = strRepairCodeId;
	}
}
