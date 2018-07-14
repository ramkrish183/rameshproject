package com.ge.trans.eoa.services.heatmap.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class HeatMapSearchVO extends BaseVO {
    
    private String customerId;
    private List<String> modelLst;
    private List <String> assetNumLst;
    private List <String> assetHeaderLst;
    private String noOfDays;
    private String fromDate;
    private String toDate;    
    private List<String> faultCodeLst;
    private String isNonGPOCUser;
    
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
     * @return the assetNumLst
     */
    public List<String> getAssetNumLst() {
        return assetNumLst;
    }
    /**
     * @param assetNumLst the assetNumLst to set
     */
    public void setAssetNumLst(List<String> assetNumLst) {
        this.assetNumLst = assetNumLst;
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
     * @return the noOfDays
     */
    public String getNoOfDays() {
        return noOfDays;
    }
    /**
     * @param noOfDays the noOfDays to set
     */
    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
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
	 * @return the isNonGPOCUser
	 */
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
