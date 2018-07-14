/**
 * 
 */
package com.ge.trans.rmd.services.heatmap.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 212338353
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "faultHeatMapResponseType", propOrder = { "strCustomerId",
        "strFaultCode", "strFaultDescription","strSubId","strFaultObjId","strAssetNumber","strAssetHeader","strSerialNo","occurDate","strCustomerName","strGPSLatitude",
        "strGPSLongitude","strModelName","strModelId","offBoardLoadDate","worstUrgency"})

@XmlRootElement
public class HeatMapFaultResponseType { 

    private String strCustomerId;
    private String strFaultCode;
    private String strFaultDescription;
    private String strSubId;
    private String strFaultObjId;
    private String strAssetNumber;
    private String strAssetHeader;
    private String strSerialNo;
    private String occurDate;
    private String strCustomerName;
    private String strGPSLatitude;
    private String strGPSLongitude;
    private String strModelName;
    private String strModelId;
    private String offBoardLoadDate;
    private String worstUrgency;
    /**
     * @return the strCustomerId
     */
    public String getStrCustomerId() {
        return strCustomerId;
    }
    /**
     * @param strCustomerId the strCustomerId to set
     */
    public void setStrCustomerId(String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }
    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }
    /**
     * @param strFaultCode the strFaultCode to set
     */
    public void setStrFaultCode(String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }
    /**
     * @return the strFaultDescription
     */
    public String getStrFaultDescription() {
        return strFaultDescription;
    }
    /**
     * @param strFaultDescription the strFaultDescription to set
     */
    public void setStrFaultDescription(String strFaultDescription) {
        this.strFaultDescription = strFaultDescription;
    }
    /**
     * @return the strSubId
     */
    public String getStrSubId() {
        return strSubId;
    }
    /**
     * @param strSubId the strSubId to set
     */
    public void setStrSubId(String strSubId) {
        this.strSubId = strSubId;
    }
    /**
     * @return the strFaultObjId
     */
    public String getStrFaultObjId() {
        return strFaultObjId;
    }
    /**
     * @param strFaultObjId the strFaultObjId to set
     */
    public void setStrFaultObjId(String strFaultObjId) {
        this.strFaultObjId = strFaultObjId;
    }
    /**
     * @return the strAssetNumber
     */
    public String getStrAssetNumber() {
        return strAssetNumber;
    }
    /**
     * @param strAssetNumber the strAssetNumber to set
     */
    public void setStrAssetNumber(String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }
    /**
     * @return the strAssetHeader
     */
    public String getStrAssetHeader() {
        return strAssetHeader;
    }
    /**
     * @param strAssetHeader the strAssetHeader to set
     */
    public void setStrAssetHeader(String strAssetHeader) {
        this.strAssetHeader = strAssetHeader;
    }
    /**
     * @return the strSerialNo
     */
    public String getStrSerialNo() {
        return strSerialNo;
    }
    /**
     * @param strSerialNo the strSerialNo to set
     */
    public void setStrSerialNo(String strSerialNo) {
        this.strSerialNo = strSerialNo;
    }
    /**
     * @return the occurDate
     */
    public String getOccurDate() {
        return occurDate;
    }
    /**
     * @param occurDate the occurDate to set
     */
    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }
    /**
     * @return the strCustomerName
     */
    public String getStrCustomerName() {
        return strCustomerName;
    }
    /**
     * @param strCustomerName the strCustomerName to set
     */
    public void setStrCustomerName(String strCustomerName) {
        this.strCustomerName = strCustomerName;
    }
    /**
     * @return the strGPSLatitude
     */
    public String getStrGPSLatitude() {
        return strGPSLatitude;
    }
    /**
     * @param strGPSLatitude the strGPSLatitude to set
     */
    public void setStrGPSLatitude(String strGPSLatitude) {
        this.strGPSLatitude = strGPSLatitude;
    }
    /**
     * @return the strGPSLongitude
     */
    public String getStrGPSLongitude() {
        return strGPSLongitude;
    }
    /**
     * @param strGPSLongitude the strGPSLongitude to set
     */
    public void setStrGPSLongitude(String strGPSLongitude) {
        this.strGPSLongitude = strGPSLongitude;
    }
    /**
     * @return the strModelName
     */
    public String getStrModelName() {
        return strModelName;
    }
    /**
     * @param strModelName the strModelName to set
     */
    public void setStrModelName(String strModelName) {
        this.strModelName = strModelName;
    }
    /**
     * @return the strModelId
     */
    public String getStrModelId() {
        return strModelId;
    }
    /**
     * @param strModelId the strModelId to set
     */
    public void setStrModelId(String strModelId) {
        this.strModelId = strModelId;
    }
    /**
     * @return the offBoardLoadDate
     */
    public String getOffBoardLoadDate() {
        return offBoardLoadDate;
    }
    /**
     * @param offBoardLoadDate the offBoardLoadDate to set
     */
    public void setOffBoardLoadDate(String offBoardLoadDate) {
        this.offBoardLoadDate = offBoardLoadDate;
    }
    /**
     * @return the worstUrgency
     */
    public String getWorstUrgency() {
        return worstUrgency;
    }
    /**
     * @param worstUrgency the worstUrgency to set
     */
    public void setWorstUrgency(String worstUrgency) {
        this.worstUrgency = worstUrgency;
    }
    
   
}
