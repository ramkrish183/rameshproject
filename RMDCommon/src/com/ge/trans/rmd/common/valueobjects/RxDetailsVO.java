package com.ge.trans.rmd.common.valueobjects;

/**
 * @author kiran
 * @description VO which contains solution informations
 */
public class RxDetailsVO extends BaseVO {

    private String rxObjid;
    private String rxTitle;
    private String urgency;
    private String estRepTime;
    private String locoImpact;
    private int version;
    private String rxType;
    private String rxstatus;
    private String strVersion;
    private String noOfCases;
    private String mdscPerformance;

    public String getMdscPerformance() {
        return mdscPerformance;
    }

    public void setMdscPerformance(String mdscPerformance) {
        this.mdscPerformance = mdscPerformance;
    }

    public String getNoOfCases() {
        return noOfCases;
    }

    public void setNoOfCases(String noOfCases) {
        this.noOfCases = noOfCases;
    }

    public String getStrVersion() {
        return strVersion;
    }

    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }

    public String getRxObjid() {
        return rxObjid;
    }

    public void setRxObjid(String rxObjid) {
        this.rxObjid = rxObjid;
    }

    public String getRxTitle() {
        return rxTitle;
    }

    public void setRxTitle(String rxTitle) {
        this.rxTitle = rxTitle;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getEstRepTime() {
        return estRepTime;
    }

    public void setEstRepTime(String estRepTime) {
        this.estRepTime = estRepTime;
    }

    public String getLocoImpact() {
        return locoImpact;
    }

    public void setLocoImpact(String locoImpact) {
        this.locoImpact = locoImpact;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRxType() {
        return rxType;
    }

    public void setRxType(String rxType) {
        this.rxType = rxType;
    }

    public String getRxstatus() {
        return rxstatus;
    }

    public void setRxstatus(String rxstatus) {
        this.rxstatus = rxstatus;
    }

}
