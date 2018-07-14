/**
 * 
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 502166888
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "headerVO", propOrder = { "transactionIndicator", "requestNo", "headerDate", "actionType",
        "physResId", "custId", "vehicleInitial", "vehicleNo", "source" ,"requestor"})
@XmlRootElement(name = "HEADER")
public class RCIMessageHeaderVO {
	
    @XmlElement(name = "TRANSACTION_INDICATOR")
    private String transactionIndicator;
    @XmlElement(name = "REQUEST_NO")
    private String requestNo;
    @XmlElement(name = "HEADER_DATE")
    private String headerDate;
    @XmlElement(name = "ACTION_TYPE")
    private String actionType;
    @XmlElement(name = "PHYS_RES_ID")
    private String physResId;
    @XmlElement(name = "CUST_ID")
    private String custId;
    @XmlElement(name = "VEHICLE_INITIAL")
    private String vehicleInitial;
    @XmlElement(name = "VEHICLE_NO")
    private String vehicleNo;
    @XmlElement(name = "SOURCE")
    private String source;
    @XmlElement(name = "REQUESTER")
    private String requestor;
	/**
	 * @return the transactionIndicator
	 */
	public final String getTransactionIndicator() {
		return transactionIndicator;
	}
	/**
	 * @param transactionIndicator the transactionIndicator to set
	 */
	public final void setTransactionIndicator(String transactionIndicator) {
		this.transactionIndicator = transactionIndicator;
	}
	/**
	 * @return the requestNo
	 */
	public final String getRequestNo() {
		return requestNo;
	}
	/**
	 * @param requestNo the requestNo to set
	 */
	public final void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	/**
	 * @return the headerDate
	 */
	public final String getHeaderDate() {
		return headerDate;
	}
	/**
	 * @param headerDate the headerDate to set
	 */
	public final void setHeaderDate(String headerDate) {
		this.headerDate = headerDate;
	}
	/**
	 * @return the actionType
	 */
	public final String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public final void setActionType(String actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the physResId
	 */
	public final String getPhysResId() {
		return physResId;
	}
	/**
	 * @param physResId the physResId to set
	 */
	public final void setPhysResId(String physResId) {
		this.physResId = physResId;
	}
	/**
	 * @return the custId
	 */
	public final String getCustId() {
		return custId;
	}
	/**
	 * @param custId the custId to set
	 */
	public final void setCustId(String custId) {
		this.custId = custId;
	}
	/**
	 * @return the vehicleInitial
	 */
	public final String getVehicleInitial() {
		return vehicleInitial;
	}
	/**
	 * @param vehicleInitial the vehicleInitial to set
	 */
	public final void setVehicleInitial(String vehicleInitial) {
		this.vehicleInitial = vehicleInitial;
	}
	/**
	 * @return the vehicleNo
	 */
	public final String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public final void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	/**
	 * @return the source
	 */
	public final String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public final void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the requestor
	 */
	public final String getRequestor() {
		return requestor;
	}
	/**
	 * @param requestor the requestor to set
	 */
	public final void setRequestor(String requestor) {
		this.requestor = requestor;
	}
    
    
    
    

}
