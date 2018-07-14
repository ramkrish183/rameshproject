package com.ge.trans.eoa.services.gpoc.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
/*******************************************************************************
 * @Author : Sonal
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified : March 10 2017
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/

public class GeneralNotesEoaServiceVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	private Long generalnotesSeqId;
	private Long commnotesSeqId;
	private String notesdesc;
	private String enteredby;
	private String enteredDate;
	private String lastUpdatedBy;
	private String lastUpdatedTime;
	private String visibilityFlag;
	private String fromScreen;
	
	
	
	
	public Long getGeneralnotesSeqId() {
		return generalnotesSeqId;
	}
	public void setGeneralnotesSeqId(Long generalnotesSeqId) {
		this.generalnotesSeqId = generalnotesSeqId;
	}
	
	public Long getCommnotesSeqId() {
		return commnotesSeqId;
	}
	public void setCommnotesSeqId(Long commnotesSeqId) {
		this.commnotesSeqId = commnotesSeqId;
	}
	public String getNotesdesc() {
		return notesdesc;
	}
	public void setNotesdesc(String notesdesc) {
		this.notesdesc = notesdesc;
	}
	public String getEnteredby() {
		return enteredby;
	}
	public void setEnteredby(String enteredby) {
		this.enteredby = enteredby;
	}
	
	
	public String getEnteredDate() {
		return enteredDate;
	}
	public void setEnteredDate(String enteredDate) {
		this.enteredDate = enteredDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
    public String getVisibilityFlag() {
        return visibilityFlag;
    }
    public void setVisibilityFlag(String visibilityFlag) {
        this.visibilityFlag = visibilityFlag;
    }
    public String getFromScreen() {
        return fromScreen;
    }
    public void setFromScreen(String fromScreen) {
        this.fromScreen = fromScreen;
    }
    	

}
