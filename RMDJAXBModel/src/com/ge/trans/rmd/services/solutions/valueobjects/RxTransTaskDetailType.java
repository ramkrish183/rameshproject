package com.ge.trans.rmd.services.solutions.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxTransTaskDetailType", propOrder = { "strTaskObjID", "strTaskId", "strTaskDesc", "strTransTaskDesc","strLanguage","taskDocType","transTaskDocType","usl","lsl","target"})
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RxTransTaskDetailType {
	@XmlElement(required = true)
    protected String strTaskObjID;
	@XmlElement(required = true)
    protected String strTaskId;    
	@XmlElement(required = true)
    protected String strTaskDesc;
	@XmlElement(required = true)
    protected String strTransTaskDesc; 
	@XmlElement(required = true)
    protected String strLanguage; 
	@XmlElement(required = true)
    protected String usl;
    @XmlElement(required = true)
    protected String lsl;
    @XmlElement(required = true)
    protected String target;
	@XmlElement(required = true)
    protected List<TaskDocType> taskDocType;
	@XmlElement(required = true)
    protected List<TaskDocType> transTaskDocType;
	
	public List<TaskDocType> getTransTaskDocType() {
		return transTaskDocType;
	}
	public void setTransTaskDocType(List<TaskDocType> transTaskDocType) {
		this.transTaskDocType = transTaskDocType;
	}
	public List<TaskDocType> getTaskDocType() {
		return taskDocType;
	}
	public void setTaskDocType(List<TaskDocType> taskDocType) {
		this.taskDocType = taskDocType;
	}
	public String getStrLanguage() {
		return strLanguage;
	}
	public void setStrLanguage(String strLanguage) {
		this.strLanguage = strLanguage;
	}
	public String getStrTaskObjID() {
		return strTaskObjID;
	}
	public void setStrTaskObjID(String strTaskObjID) {
		this.strTaskObjID = strTaskObjID;
	}
	public String getStrTaskId() {
		return strTaskId;
	}
	public void setStrTaskId(String strTaskId) {
		this.strTaskId = strTaskId;
	}
	public String getStrTaskDesc() {
		return strTaskDesc;
	}
	public void setStrTaskDesc(String strTaskDesc) {
		this.strTaskDesc = strTaskDesc;
	}
	public String getStrTransTaskDesc() {
		return strTransTaskDesc;
	}
	public void setStrTransTaskDesc(String strTransTaskDesc) {
		this.strTransTaskDesc = strTransTaskDesc;
	}
	public String getUsl() {
		return usl;
	}
	public void setUsl(String usl) {
		this.usl = usl;
	}
	public String getLsl() {
		return lsl;
	}
	public void setLsl(String lsl) {
		this.lsl = lsl;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
    

}
