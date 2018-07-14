package com.ge.trans.eoa.services.rxchange.service.valueobjects;

import java.util.List;

public class RxChangeSearchVO{
    
    private String userId;
    private List<String> customerIdLst;
    private List<String> modelLst;
    private String fromDate;
    private String toDate;
    private List<String> status;
    private String rxTitle;
    private List<String> recomObjIdLst;
    private String rxChangeReqObjId;
    private String rxChangeProcObjId;
    private List<String> typeOfChangeLst; 
    private String typeOfChange;

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

	public List<String> getCustomerIdLst() {
		return customerIdLst;
	}

	public void setCustomerIdLst(List<String> customerIdLst) {
		this.customerIdLst = customerIdLst;
	}

	public List<String> getModelLst() {
		return modelLst;
	}

	public void setModelLst(List<String> modelLst) {
		this.modelLst = modelLst;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public String getRxTitle() {
		return rxTitle;
	}

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

    public List<String> getTypeOfChangeLst() {
        return typeOfChangeLst;
    }

    public void setTypeOfChangeLst(List<String> typeOfChangeLst) {
        this.typeOfChangeLst = typeOfChangeLst;
    }

    public String getTypeOfChange() {
        return typeOfChange;
    }

    public void setTypeOfChange(String typeOfChange) {
        this.typeOfChange = typeOfChange;
    }

    @Override
    public String toString() {
        return "RxChangeSearchVO [userId=" + userId + ",\n customerIdLst=" + customerIdLst + ",\n modelLst=" + modelLst
                + ",\n fromDate=" + fromDate + ",\n toDate=" + toDate + ",\n status=" + status + ",\n rxTitle=" + rxTitle
                + ",\n recomObjIdLst=" + recomObjIdLst + ",\n rxChangeReqObjId=" + rxChangeReqObjId
                + ",\n rxChangeProcObjId=" + rxChangeProcObjId + ",\n typeOfChangeLst=" + typeOfChangeLst
                + ",\n typeOfChange=" + typeOfChange + "]";
    }
}
