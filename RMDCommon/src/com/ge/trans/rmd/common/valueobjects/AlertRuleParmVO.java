package com.ge.trans.rmd.common.valueobjects;

import java.util.ArrayList;
/**
 * VO for AlertRuleParam data
 * @author 212556286
 */
import java.util.List;

public class AlertRuleParmVO extends BaseVO {

    private static final long serialVersionUID = 3989075400164037791L;

    private String faultObjId;
    private String recordType;
    private String cmuTime;
    private String incidentTime;
    private String faultCode;
    private List<String> parmNames = new ArrayList<String>();
    private List<String> parmDisplayNames = new ArrayList<String>();
    private List<RuleParmVO> parmSet = new ArrayList<RuleParmVO>();

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getCmuTime() {
        return cmuTime;
    }

    public void setCmuTime(String cmuTime) {
        this.cmuTime = cmuTime;
    }

    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    public List<String> getParmNames() {
        return parmNames;
    }

    public void setParmNames(List<String> parmNames) {
        this.parmNames = parmNames;
    }

    public List<RuleParmVO> getParmSet() {
        return parmSet;
    }

    public void setParmSet(List<RuleParmVO> parmSet) {
        this.parmSet = parmSet;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultObjId() {
        return faultObjId;
    }

    public void setFaultObjId(String faultObjId) {
        this.faultObjId = faultObjId;
    }

    public List<String> getParmDisplayNames() {
        return parmDisplayNames;
    }

    public void setParmDisplayNames(List<String> parmDisplayNames) {
        this.parmDisplayNames = parmDisplayNames;
    }

}
