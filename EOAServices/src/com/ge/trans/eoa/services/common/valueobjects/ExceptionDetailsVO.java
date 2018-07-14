package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class ExceptionDetailsVO extends BaseVO{

	private static final long serialVersionUID = 428136107091113412L;
	private String userId;
	private String screenName;
	private String businessKeys;
	private String exceptionType;
	private String exceptionDesc;
	private String traceLog;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getBusinessKeys() {
		return businessKeys;
	}
	public void setBusinessKeys(String businessKeys) {
		this.businessKeys = businessKeys;
	}
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getExceptionDesc() {
		return exceptionDesc;
	}
	public void setExceptionDesc(String exceptionDesc) {
		this.exceptionDesc = exceptionDesc;
	}
	public String getTraceLog() {
		return traceLog;
	}
	public void setTraceLog(String traceLog) {
		this.traceLog = traceLog;
	}
}
