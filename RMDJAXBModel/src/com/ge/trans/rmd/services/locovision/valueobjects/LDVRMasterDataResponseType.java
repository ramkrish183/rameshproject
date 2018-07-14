/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author MSHIRAJUDDIN
 *
 */
@XmlRootElement
public class LDVRMasterDataResponseType {
	
	
	private DataSetType[] dataSet;

	/**
	 * @return the dataSet
	 */
	public DataSetType[] getDataSet() {
		return dataSet;
	}

	/**
	 * @param dataSet the dataSet to set
	 */
	public void setDataSet(DataSetType[] dataSet) {
		this.dataSet = dataSet;
	}


}
