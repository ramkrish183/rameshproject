package com.ge.trans.eoa.services.rxtranslation.service.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;

public class RxTransTaskDetailVO {
    private String strTaskObjID;
    private String strTaskId;    
    private String strTaskDesc;
    private String strTransTaskDesc; 
    private String strLanguage; 
    private String usl;
    private String lsl;
    private String target;
    private List<AddEditRxTaskDocVO> addEditRxTaskDocVO;
    private List<AddEditRxTaskDocVO> rxTransTaskDocVO;
    
    public RxTransTaskDetailVO(String strTaskObjID,String strTaskId,String strTaskDesc,String strTransTaskDesc){
    	this.strTaskObjID=strTaskObjID;
    	this.strTaskId=strTaskId;
    	this.strTaskDesc=strTaskDesc;
    	this.strTransTaskDesc=strTransTaskDesc;
    }    
    
	public RxTransTaskDetailVO() {
	}

	public List<AddEditRxTaskDocVO> getRxTransTaskDocVO() {
		return rxTransTaskDocVO;
	}


	public void setRxTransTaskDocVO(List<AddEditRxTaskDocVO> rxTransTaskDocVO) {
		this.rxTransTaskDocVO = rxTransTaskDocVO;
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
	public List<AddEditRxTaskDocVO> getAddEditRxTaskDocVO() {
		return addEditRxTaskDocVO;
	}
	public void setAddEditRxTaskDocVO(List<AddEditRxTaskDocVO> addEditRxTaskDocVO) {
		this.addEditRxTaskDocVO = addEditRxTaskDocVO;
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
