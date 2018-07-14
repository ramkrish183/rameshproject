package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class CloseOutRepairCodeVO {
    private String Id;
    private String task;
    private String feedback;
    private String repairCode;
    private String description;
    private String repairCodeId;
    private List<CaseRepairCodeVO> repairCodes;
    
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
    
    public List<CaseRepairCodeVO> getRepairCodes() {
		return repairCodes;
	}
	public void setRepairCodes(List<CaseRepairCodeVO> repairCodes) {
		this.repairCodes = repairCodes;
	}
	public void addRepairCodes(CaseRepairCodeVO repairCode) {
		if(this.repairCodes == null) {
			this.repairCodes = new ArrayList<CaseRepairCodeVO>();
		}
		this.repairCodes.add(repairCode);
	}
}
