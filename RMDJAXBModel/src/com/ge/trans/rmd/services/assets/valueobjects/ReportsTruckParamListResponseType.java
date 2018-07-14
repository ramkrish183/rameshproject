package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsTruckParamListResponseType", propOrder = { "truckParamsList" })

@XmlRootElement
public class ReportsTruckParamListResponseType {
	Map<String, ReportsListWrapper> truckParamsList = new HashMap<String, ReportsListWrapper>();

	public Map<String, ReportsListWrapper> getTruckParamsList() {
		return truckParamsList;
	}

	public void setTruckParamsList(
			Map<String, ReportsListWrapper> truckParamsList) {
		this.truckParamsList = truckParamsList;
	}
	

	
}
