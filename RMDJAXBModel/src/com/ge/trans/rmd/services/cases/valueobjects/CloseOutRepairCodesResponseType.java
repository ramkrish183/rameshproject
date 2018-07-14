package com.ge.trans.rmd.services.cases.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.solutions.valueobjects.RepairCodeType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "closeOutRepairCodesResponseType", propOrder = { "id", "task", "feedback", "repairCode", "description",
        "repairCodeId","repairCodes" })
@XmlRootElement
public class CloseOutRepairCodesResponseType {
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String task;
    @XmlElement(required = true)
    protected String feedback;
    @XmlElement(required = true)
    protected String repairCode;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String repairCodeId;
    @XmlElement(required = true)
    protected List<RepairCodeType> repairCodes;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRepairCode() {
        return repairCode;
    }

    public void setRepairCode(String repairCode) {
        this.repairCode = repairCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepairCodeId() {
        return repairCodeId;
    }

    public void setRepairCodeId(String repairCodeId) {
        this.repairCodeId = repairCodeId;
    }
    
    public List<RepairCodeType> getRepairCodes() {
		return repairCodes;
	}

	public void setRepairCodes(List<RepairCodeType> repairCodes) {
		this.repairCodes = repairCodes;
	}

	public void addRepairCodes(RepairCodeType repairCode) {
		if(this.repairCodes == null){
			this.repairCodes = new ArrayList<RepairCodeType>();
		}
		this.repairCodes.add(repairCode);
	}
}
