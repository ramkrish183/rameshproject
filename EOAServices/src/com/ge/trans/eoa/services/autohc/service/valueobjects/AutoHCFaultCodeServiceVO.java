package com.ge.trans.eoa.services.autohc.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AutoHCFaultCodeServiceVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	
	
	private String fault_code;
	private String record_type;
	private String message_def_id;
	public String getFault_code() {
		return fault_code;
	}
	public void setFault_code(String fault_code) {
		this.fault_code = fault_code;
	}
	public String getRecord_type() {
		return record_type;
	}
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	public String getMessage_def_id() {
		return message_def_id;
	}
	public void setMessage_def_id(String message_def_id) {
		this.message_def_id = message_def_id;
	}

	
	

}
