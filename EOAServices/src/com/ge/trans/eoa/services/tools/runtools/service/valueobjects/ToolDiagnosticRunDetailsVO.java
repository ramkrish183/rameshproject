package com.ge.trans.eoa.services.tools.runtools.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class ToolDiagnosticRunDetailsVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String lastToolRun;
	private String diagnosticService;
	public String getLastToolRun() {
		return lastToolRun;
	}
	public void setLastToolRun(String lastToolRun) {
		this.lastToolRun = lastToolRun;
	}
	public String getDiagnosticService() {
		return diagnosticService;
	}
	public void setDiagnosticService(String diagnosticService) {
		this.diagnosticService = diagnosticService;
	}
	
	

	
}
