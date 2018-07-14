/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author MSHIRAJUDDIN
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ldvrMasterDataRequestType", propOrder = {"masterDataSet"})

@XmlRootElement
public class LDVRMasterDataRequestType {
	
	private List<String> masterDataSet;

	/**
	 * @return the masterDataSet
	 */
	public List<String> getMasterDataSet() {
		return masterDataSet;
	}

	/**
	 * @param masterDataSet the masterDataSet to set
	 */
	public void setMasterDataSet(List<String> masterDataSet) {
		this.masterDataSet = masterDataSet;
	}



}
