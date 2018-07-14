package com.ge.trans.rmd.common.valueobjects;

public class AlertRuleVO extends BaseVO {

    private static final long serialVersionUID = -3445392061686054885L;
    private String fireId;
    private String ruleId;
    private String title;
    private String family;
    private String version;
    private String vehObjId;
    private String vehHdr;
    private String vehSerialNo;
    private String createdOn;
    private String diagService;
    private String alertStatus;
    private String strCustomer;    

    public String getStrCustomer() {
		return strCustomer;
	}

	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}

	public String getFireId() {
        return fireId;
    }

    public void setFireId(String fireId) {
        this.fireId = fireId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVehObjId() {
        return vehObjId;
    }

    public void setVehObjId(String vehObjId) {
        this.vehObjId = vehObjId;
    }

    public String getVehHdr() {
        return vehHdr;
    }

    public void setVehHdr(String vehHdr) {
        this.vehHdr = vehHdr;
    }

    public String getVehSerialNo() {
        return vehSerialNo;
    }

    public void setVehSerialNo(String vehSerialNo) {
        this.vehSerialNo = vehSerialNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDiagService() {
        return diagService;
    }

    public void setDiagService(String diagService) {
        this.diagService = diagService;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }
}
