package com.ge.trans.pp.services.manageGeofence.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "proximityDetailsResponseType", propOrder = { "proxObjId", "activeFlag", "custProxId", "smppProxId",
        "proxDesc", "proxType", "proxParent", "upperLeftLat", "upperLeftLon", "lowerRightLat", "lowerRightLon",
        "arrivalFlag", "departureFlag", "dwelFlag", "interchangeFlag", "consistFlag", "userId"

})

@XmlRootElement
public class ProximityDetailsResponseType {

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

    /**
     * @return the proxObjId
     */
    public String getProxObjId() {
        return proxObjId;
    }

    /**
     * @param proxObjId
     *            the proxObjId to set
     */
    public void setProxObjId(String proxObjId) {
        this.proxObjId = proxObjId;
    }

    /**
     * @return the activeFlag
     */
    public String getActiveFlag() {
        return activeFlag;
    }

    /**
     * @param activeFlag
     *            the activeFlag to set
     */
    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * @return the custProxId
     */
    public String getCustProxId() {
        return custProxId;
    }

    /**
     * @param custProxId
     *            the custProxId to set
     */
    public void setCustProxId(String custProxId) {
        this.custProxId = custProxId;
    }

    /**
     * @return the smppProxId
     */
    public String getSmppProxId() {
        return smppProxId;
    }

    /**
     * @param smppProxId
     *            the smppProxId to set
     */
    public void setSmppProxId(String smppProxId) {
        this.smppProxId = smppProxId;
    }

    /**
     * @return the proxDesc
     */
    public String getProxDesc() {
        return proxDesc;
    }

    /**
     * @param proxDesc
     *            the proxDesc to set
     */
    public void setProxDesc(String proxDesc) {
        this.proxDesc = proxDesc;
    }

    /**
     * @return the proxType
     */
    public String getProxType() {
        return proxType;
    }

    /**
     * @param proxType
     *            the proxType to set
     */
    public void setProxType(String proxType) {
        this.proxType = proxType;
    }

    /**
     * @return the proxParent
     */
    public String getProxParent() {
        return proxParent;
    }

    /**
     * @param proxParent
     *            the proxParent to set
     */
    public void setProxParent(String proxParent) {
        this.proxParent = proxParent;
    }

    /**
     * @return the upperLeftLat
     */
    public String getUpperLeftLat() {
        return upperLeftLat;
    }

    /**
     * @param upperLeftLat
     *            the upperLeftLat to set
     */
    public void setUpperLeftLat(String upperLeftLat) {
        this.upperLeftLat = upperLeftLat;
    }

    /**
     * @return the upperLeftLon
     */
    public String getUpperLeftLon() {
        return upperLeftLon;
    }

    /**
     * @param upperLeftLon
     *            the upperLeftLon to set
     */
    public void setUpperLeftLon(String upperLeftLon) {
        this.upperLeftLon = upperLeftLon;
    }

    /**
     * @return the lowerRightLat
     */
    public String getLowerRightLat() {
        return lowerRightLat;
    }

    /**
     * @param lowerRightLat
     *            the lowerRightLat to set
     */
    public void setLowerRightLat(String lowerRightLat) {
        this.lowerRightLat = lowerRightLat;
    }

    /**
     * @return the lowerRightLon
     */
    public String getLowerRightLon() {
        return lowerRightLon;
    }

    /**
     * @param lowerRightLon
     *            the lowerRightLon to set
     */
    public void setLowerRightLon(String lowerRightLon) {
        this.lowerRightLon = lowerRightLon;
    }

    /**
     * @return the arrivalFlag
     */
    public String getArrivalFlag() {
        return arrivalFlag;
    }

    /**
     * @param arrivalFlag
     *            the arrivalFlag to set
     */
    public void setArrivalFlag(String arrivalFlag) {
        this.arrivalFlag = arrivalFlag;
    }

    /**
     * @return the departureFlag
     */
    public String getDepartureFlag() {
        return departureFlag;
    }

    /**
     * @param departureFlag
     *            the departureFlag to set
     */
    public void setDepartureFlag(String departureFlag) {
        this.departureFlag = departureFlag;
    }

    /**
     * @return the dwelFlag
     */
    public String getDwelFlag() {
        return dwelFlag;
    }

    /**
     * @param dwelFlag
     *            the dwelFlag to set
     */
    public void setDwelFlag(String dwelFlag) {
        this.dwelFlag = dwelFlag;
    }

    /**
     * @return the interchangeFlag
     */
    public String getInterchangeFlag() {
        return interchangeFlag;
    }

    /**
     * @param interchangeFlag
     *            the interchangeFlag to set
     */
    public void setInterchangeFlag(String interchangeFlag) {
        this.interchangeFlag = interchangeFlag;
    }

    /**
     * @return the consistFlag
     */
    public String getConsistFlag() {
        return consistFlag;
    }

    /**
     * @param consistFlag
     *            the consistFlag to set
     */
    public void setConsistFlag(String consistFlag) {
        this.consistFlag = consistFlag;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
