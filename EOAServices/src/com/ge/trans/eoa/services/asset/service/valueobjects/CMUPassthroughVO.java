/**
 * ============================================================
 * Classification: GE Confidential
 * File : CMUPassthroughVO.java
 * Description :
 * 
 * Author : Capgemini.
 *
 * Copyright (C) 2017 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.asset.service.valueobjects;


public class CMUPassthroughVO {

	String requestNo;
	String headerDate;
	String phyResId;
	String vehicleInitial;
	String vehicleNo;
	String custId;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getHeaderDate() {
		return headerDate;
	}

	public void setHeaderDate(String headerDate) {
		this.headerDate = headerDate;
	}

	public String getPhyResId() {
		return phyResId;
	}

	public void setPhyResId(String phyResId) {
		this.phyResId = phyResId;
	}

	public String getVehicleInitial() {
		return vehicleInitial;
	}

	public void setVehicleInitial(String vehicleInitial) {
		this.vehicleInitial = vehicleInitial;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
