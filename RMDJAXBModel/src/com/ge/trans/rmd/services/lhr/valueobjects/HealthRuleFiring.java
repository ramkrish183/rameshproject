package com.ge.trans.rmd.services.lhr.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthRuleFiring", propOrder = {
 "ruleId",
 "hasFired",
 "runDate"
})
public class HealthRuleFiring {
	
	@XmlElement(required = true)
	protected long ruleId;
	@XmlElement(name = "hasFired")
	protected boolean hasFired;
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar runDate;

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long value) {
		this.ruleId = value;
	}

	public boolean isHasFired() {
		return hasFired;
	}

	public void setHasFired(boolean hasFired) {
		this.hasFired = hasFired;
	}

	public XMLGregorianCalendar getRunDate() {
		return runDate;
	}

	public void setRunDate(XMLGregorianCalendar runDate) {
		this.runDate = runDate;
	}
}





