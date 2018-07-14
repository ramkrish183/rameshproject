package com.ge.trans.pp.services.manageGeofence.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "milePostDetailsResponseType", propOrder = { "milePostObjId", "activeFlag", "milePostId",
        "milePostDesc", "stateProvince", "region", "subRegion", "gpsLat", "gpsLon", "userId", "customerName"

})
@XmlRootElement
public class MilePostDetailsResponseType {

    @XmlElement(required = true)
    protected String milePostObjId;
    @XmlElement(required = true)
    protected String activeFlag;
    @XmlElement(required = true)
    protected String milePostId;
    @XmlElement(required = true)
    protected String milePostDesc;
    @XmlElement(required = true)
    protected String stateProvince;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String subRegion;
    @XmlElement(required = true)
    protected String gpsLat;
    @XmlElement(required = true)
    protected String gpsLon;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String customerName;

    /**
     * @return the milePostObjId
     */
    public String getMilePostObjId() {
        return milePostObjId;
    }

    /**
     * @param milePostObjId
     *            the milePostObjId to set
     */
    public void setMilePostObjId(String milePostObjId) {
        this.milePostObjId = milePostObjId;
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
     * @return the milePostId
     */
    public String getMilePostId() {
        return milePostId;
    }

    /**
     * @param milePostId
     *            the milePostId to set
     */
    public void setMilePostId(String milePostId) {
        this.milePostId = milePostId;
    }

    /**
     * @return the miePostDesc
     */
    public String getMilePostDesc() {
        return milePostDesc;
    }

    /**
     * @param miePostDesc
     *            the miePostDesc to set
     */
    public void setMilePostDesc(String miePostDesc) {
        this.milePostDesc = miePostDesc;
    }

    /**
     * @return the stateProvince
     */
    public String getStateProvince() {
        return stateProvince;
    }

    /**
     * @param stateProvince
     *            the stateProvince to set
     */
    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region
     *            the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the subRegion
     */
    public String getSubRegion() {
        return subRegion;
    }

    /**
     * @param subRegion
     *            the subRegion to set
     */
    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    /**
     * @return the gpsLat
     */
    public String getGpsLat() {
        return gpsLat;
    }

    /**
     * @param gpsLat
     *            the gpsLat to set
     */
    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    /**
     * @return the gpsLon
     */
    public String getGpsLon() {
        return gpsLon;
    }

    /**
     * @param gpsLon
     *            the gpsLon to set
     */
    public void setGpsLon(String gpsLon) {
        this.gpsLon = gpsLon;
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

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
