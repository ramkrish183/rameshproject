package com.ge.trans.rmd.common.valueobjects;


@SuppressWarnings("unchecked")
public class GetInboundTurnoverVO extends BaseVO {

	private static final long serialVersionUID = 635654132L;
	
	private String caseId;
    private String roadNumberHeader;
    private String roadNumber;
    private String gpocComments;
    private String count;
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getRoadNumberHeader() {
		return roadNumberHeader;
	}
	public void setRoadNumberHeader(String roadNumberHeader) {
		this.roadNumberHeader = roadNumberHeader;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getGpocComments() {
		return gpocComments;
	}
	public void setGpocComments(String gpocComments) {
		this.gpocComments = gpocComments;
	}

	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
    
    
}
