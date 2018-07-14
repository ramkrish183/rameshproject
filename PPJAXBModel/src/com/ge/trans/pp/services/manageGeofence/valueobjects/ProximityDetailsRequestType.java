package com.ge.trans.pp.services.manageGeofence.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "proximityDetailsRequestType", propOrder = { "proxObjId", "activeFlag", "custProxId", "smppProxId",
        "proxDesc", "proxType", "proxParent", "upperLeftLat", "upperLeftLon", "lowerRightLat", "lowerRightLon",
        "arrivalFlag", "departureFlag", "dwelFlag", "interchangeFlag", "consistFlag", "userId", "customerName",
        "arlProxDeatislRequestType"

})

@XmlRootElement
public class ProximityDetailsRequestType {

    @XmlElement(required = true)
    protected String proxObjId;
    @XmlElement(required = true)
    protected String activeFlag;
    @XmlElement(required = true)
    protected String custProxId;
    @XmlElement(required = true)
    protected String smppProxId;
    @XmlElement(required = true)
    protected String proxDesc;
    @XmlElement(required = true)
    protected String proxType;
    @XmlElement(required = true)
    protected String proxParent;
    @XmlElement(required = true)
    protected String upperLeftLat;
    @XmlElement(required = true)
    protected String upperLeftLon;
    @XmlElement(required = true)
    protected String lowerRightLat;
    @XmlElement(required = true)
    protected String lowerRightLon;
    @XmlElement(required = true)
    protected String arrivalFlag;
    @XmlElement(required = true)
    protected String departureFlag;
    @XmlElement(required = true)
    protected String dwelFlag;
    @XmlElement(required = true)
    protected String interchangeFlag;
    @XmlElement(required = true)
    protected String consistFlag;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected List<ProximityDetailsRequestType> arlProxDeatislRequestType;

    public String getProxObjId() {
        return proxObjId;
    }

    public void setProxObjId(String proxObjId) {
        this.proxObjId = proxObjId;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getCustProxId() {
        return custProxId;
    }

    public void setCustProxId(String custProxId) {
        this.custProxId = custProxId;
    }

    public String getSmppProxId() {
        return smppProxId;
    }

    public void setSmppProxId(String smppProxId) {
        this.smppProxId = smppProxId;
    }

    public String getProxDesc() {
        return proxDesc;
    }

    public void setProxDesc(String proxDesc) {
        this.proxDesc = proxDesc;
    }

    public String getProxType() {
        return proxType;
    }

    public void setProxType(String proxType) {
        this.proxType = proxType;
    }

    public String getProxParent() {
        return proxParent;
    }

    public void setProxParent(String proxParent) {
        this.proxParent = proxParent;
    }

    public String getUpperLeftLat() {
        return upperLeftLat;
    }

    public void setUpperLeftLat(String upperLeftLat) {
        this.upperLeftLat = upperLeftLat;
    }

    public String getUpperLeftLon() {
        return upperLeftLon;
    }

    public void setUpperLeftLon(String upperLeftLon) {
        this.upperLeftLon = upperLeftLon;
    }

    public String getLowerRightLat() {
        return lowerRightLat;
    }

    public void setLowerRightLat(String lowerRightLat) {
        this.lowerRightLat = lowerRightLat;
    }

    public String getLowerRightLon() {
        return lowerRightLon;
    }

    public void setLowerRightLon(String lowerRightLon) {
        this.lowerRightLon = lowerRightLon;
    }

    public String getArrivalFlag() {
        return arrivalFlag;
    }

    public void setArrivalFlag(String arrivalFlag) {
        this.arrivalFlag = arrivalFlag;
    }

    public String getDepartureFlag() {
        return departureFlag;
    }

    public void setDepartureFlag(String departureFlag) {
        this.departureFlag = departureFlag;
    }

    public String getDwelFlag() {
        return dwelFlag;
    }

    public void setDwelFlag(String dwelFlag) {
        this.dwelFlag = dwelFlag;
    }

    public String getInterchangeFlag() {
        return interchangeFlag;
    }

    public void setInterchangeFlag(String interchangeFlag) {
        this.interchangeFlag = interchangeFlag;
    }

    public String getConsistFlag() {
        return consistFlag;
    }

    public void setConsistFlag(String consistFlag) {
        this.consistFlag = consistFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<ProximityDetailsRequestType> getArlProxDeatislRequestType() {
        return arlProxDeatislRequestType;
    }

    public void setArlProxDeatislRequestType(List<ProximityDetailsRequestType> arlProxDeatislRequestType) {
        this.arlProxDeatislRequestType = arlProxDeatislRequestType;
    }

}
