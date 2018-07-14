/**
 * 
 */
package com.ge.trans.rmd.services.rxchange.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 307009968
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "rxChangeOverviewRequestType", propOrder =  {"userId", "customerIdLst",
        "fromDate","toDate","modelLst","status","rxTitle","recomObjIdLst","rxChangeReqObjId","rxChangeProcObjId","typeOfChangeReq", "typeOfChangeReqLst"})
@XmlRootElement
public class RxChangeOverviewRequestType { 

    private String userId;
    private List<String> customerIdLst;
    private String fromDate;
    private String toDate;
    private List<String> modelLst;
    private List<String> status;
    private String rxTitle;
    private List<String> recomObjIdLst;
    private String rxChangeReqObjId;
    private String rxChangeProcObjId;
    private String typeOfChangeReq;
    private List<String> typeOfChangeReqLst;
    
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the customerIdLst
	 */
	public List<String> getCustomerIdLst() {
		return customerIdLst;
	}
	/**
	 * @param customerIdLst the customerIdLst to set
	 */
	public void setCustomerIdLst(List<String> customerIdLst) {
		this.customerIdLst = customerIdLst;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the modelLst
	 */
	public List<String> getModelLst() {
		return modelLst;
	}
	/**
	 * @param modelLst the modelLst to set
	 */
	public void setModelLst(List<String> modelLst) {
		this.modelLst = modelLst;
	}
	/**
	 * @return the status
	 */
	public List<String> getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(List<String> status) {
		this.status = status;
	}
	/**
	 * @return the rxTitle
	 */
	public String getRxTitle() {
		return rxTitle;
	}
	/**
	 * @param rxTitle the rxTitle to set
	 */
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	/**
	 * @return the recomObjIdLst
	 */
	public List<String> getRecomObjIdLst() {
		return recomObjIdLst;
	}
	/**
	 * @param recomObjIdLst the recomObjIdLst to set
	 */
	public void setRecomObjIdLst(List<String> recomObjIdLst) {
		this.recomObjIdLst = recomObjIdLst;
	}
	/**
	 * @return the rxChangeReqObjId
	 */
	public String getRxChangeReqObjId() {
		return rxChangeReqObjId;
	}
	/**
	 * @param rxChangeReqObjId the rxChangeReqObjId to set
	 */
	public void setRxChangeReqObjId(String rxChangeReqObjId) {
		this.rxChangeReqObjId = rxChangeReqObjId;
	}
	/**
	 * @return the rxChangeProcObjId
	 */
	public String getRxChangeProcObjId() {
		return rxChangeProcObjId;
	}
	/**
	 * @param rxChangeProcObjId the rxChangeProcObjId to set
	 */
	public void setRxChangeProcObjId(String rxChangeProcObjId) {
		this.rxChangeProcObjId = rxChangeProcObjId;
	}
    public String getTypeOfChangeReq() {
        return typeOfChangeReq;
    }
    public void setTypeOfChangeReq(String typeOfChangeReq) {
        this.typeOfChangeReq = typeOfChangeReq;
    }
    public List<String> getTypeOfChangeReqLst() {
        return typeOfChangeReqLst;
    }
    public void setTypeOfChangeReqLst(List<String> typeOfChangeReqLst) {
        this.typeOfChangeReqLst = typeOfChangeReqLst;
    }
    @Override
    public String toString() {
        return "RxChangeOverviewRequestType [userId=" + userId + ", customerIdLst=" + customerIdLst + ", fromDate="
                + fromDate + ", toDate=" + toDate + ", modelLst=" + modelLst + ", status=" + status + ", rxTitle="
                + rxTitle + ", recomObjIdLst=" + recomObjIdLst + ", rxChangeReqObjId=" + rxChangeReqObjId
                + ", rxChangeProcObjId=" + rxChangeProcObjId + ", typeOfChangeReq=" + typeOfChangeReq
                + ", typeOfChangeReqLst=" + typeOfChangeReqLst + "]";
    }
}
