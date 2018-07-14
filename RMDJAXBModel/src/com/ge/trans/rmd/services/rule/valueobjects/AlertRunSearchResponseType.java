package com.ge.trans.rmd.services.rule.valueobjects;

/**
 * Java Class ofr AlertRunSearch Response Type.
 * @author 212556286
 */
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertRunSearchResponseType", propOrder = { "runObjId",
        "runDate", "run2Vehicle", "vehHdrCust", "serialNo", "family", "ruleId",
"ruleTitle","strCustomer" })
@XmlRootElement
public class AlertRunSearchResponseType {

    @XmlElement(required = true, name = "RunId")
    private String runObjId;

    @XmlElement(required = true, name = "RunDate")
    private String runDate;

    @XmlElement(required = true, name = "Run2Vehicle")
    private String run2Vehicle;

    @XmlElement(required = true, name = "VehHdrCust")
    private String vehHdrCust;

    @XmlElement(required = true, name = "SerialNo")
    private String serialNo;

    @XmlElement(required = true, name = "Family")
    private String family;

    @XmlElement(required = true, name = "RuleId")
    private String ruleId;

    @XmlElement(required = true, name = "RuleTitle")
    private String ruleTitle;
    @XmlElement(required = true, name = "strCustomer")
    protected String strCustomer;

    
    public String getStrCustomer() {
		return strCustomer;
	}

	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}

	public String getRunObjId() {
        return runObjId;
    }

    public void setRunObjId(String runObjId) {
        this.runObjId = runObjId;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getRun2Vehicle() {
        return run2Vehicle;
    }

    public void setRun2Vehicle(String run2Vehicle) {
        this.run2Vehicle = run2Vehicle;
    }

    public String getVehHdrCust() {
        return vehHdrCust;
    }

    public void setVehHdrCust(String vehHdrCust) {
        this.vehHdrCust = vehHdrCust;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

}
