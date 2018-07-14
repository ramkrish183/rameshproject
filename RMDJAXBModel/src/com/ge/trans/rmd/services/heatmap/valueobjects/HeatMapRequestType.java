/**
 * 
 */
package com.ge.trans.rmd.services.heatmap.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 212338353
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "heatMapRequestType", propOrder = { "customerId",
        "modelLst","assetHeaderLst","assetNumberLst","fromDate","toDate","noOfdays","faultCodeLst","isNonGPOCUser"})


@XmlRootElement
public class HeatMapRequestType {
    private String customerId;
    private List<String> modelLst;
    private List <String> assetHeaderLst;
    private List <String> assetNumberLst;
    private String fromDate;
    private String toDate;
    private String noOfdays;
    private List<String> faultCodeLst;
    private String isNonGPOCUser;
    
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
     * @return the assetHeaderLst
     */
    public List<String> getAssetHeaderLst() {
        return assetHeaderLst;
    }
    /**
     * @param assetHeaderLst the assetHeaderLst to set
     */
    public void setAssetHeaderLst(List<String> assetHeaderLst) {
        this.assetHeaderLst = assetHeaderLst;
    }
    /**
     * @return the assetNumberLst
     */
    public List<String> getAssetNumberLst() {
        return assetNumberLst;
    }
    /**
     * @param assetNumberLst the assetNumberLst to set
     */
    public void setAssetNumberLst(List<String> assetNumberLst) {
        this.assetNumberLst = assetNumberLst;
    }
    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }
    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
     * @return the noOfdays
     */
    public String getNoOfdays() {
        return noOfdays;
    }
    /**
     * @param noOfdays the noOfdays to set
     */
    public void setNoOfdays(String noOfdays) {
        this.noOfdays = noOfdays;
    }
    /**
     * @return the faultCodeLst
     */
    public List<String> getFaultCodeLst() {
        return faultCodeLst;
    }
    /**
     * @param faultCodeLst the faultCodeLst to set
     */
    public void setFaultCodeLst(List<String> faultCodeLst) {
        this.faultCodeLst = faultCodeLst;
    }
    public String getIsNonGPOCUser() {
		return isNonGPOCUser;
	}
	/**
	 * @param isNonGPOCUser the isNonGPOCUser to set
	 */
	public void setIsNonGPOCUser(String isNonGPOCUser) {
		this.isNonGPOCUser = isNonGPOCUser;
	}    
}
