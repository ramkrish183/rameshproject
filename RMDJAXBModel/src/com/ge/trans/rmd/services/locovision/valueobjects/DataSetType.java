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
public class DataSetType {

	 private String dataSetName;
	 private String parentSetName;
	 private DataType[] data;

	 
	 
	/**
	 * @return the data
	 */
	public DataType[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(DataType[] data) {
		this.data = data;
	}

	/**
	 * @return the dataSetName
	 */
	public String getDataSetName() {
		return dataSetName;
	}

	/**
	 * @param dataSetName the dataSetName to set
	 */
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	/**
	 * @return the parentSetName
	 */
	public String getParentSetName() {
		return parentSetName;
	}

	/**
	 * @param parentSetName the parentSetName to set
	 */
	public void setParentSetName(String parentSetName) {
		this.parentSetName = parentSetName;
	}	
}
