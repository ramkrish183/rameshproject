/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

/**
 * @author 212338353
 *
 */
public class ApplyTemplatesType {

    private String owner;

    private String roadInitial;

    private String fromToCmuSerial;

    private String alerts;

    private String uniqueID;

    private String detailsOwnerName;

    private String detailsRoadInitial;

    private String detailsRoadNumberFrom;

    private String detailsRoadNumberTo;

    private String detailsDevice;

    private List<ExcludedAssetsType> excludeReason;

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the roadInitial
     */
    public String getRoadInitial() {
        return roadInitial;
    }

    /**
     * @param roadInitial
     *            the roadInitial to set
     */
    public void setRoadInitial(String roadInitial) {
        this.roadInitial = roadInitial;
    }

    /**
     * @return the fromToCmuSerial
     */
    public String getFromToCmuSerial() {
        return fromToCmuSerial;
    }

    /**
     * @param fromToCmuSerial
     *            the fromToCmuSerial to set
     */
    public void setFromToCmuSerial(String fromToCmuSerial) {
        this.fromToCmuSerial = fromToCmuSerial;
    }

    /**
     * @return the alerts
     */
    public String getAlerts() {
        return alerts;
    }

    /**
     * @param alerts
     *            the alerts to set
     */
    public void setAlerts(String alerts) {
        this.alerts = alerts;
    }

    /**
     * @return the uniqueID
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * @param uniqueID
     *            the uniqueID to set
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * @return the detailsOwnerName
     */
    public String getDetailsOwnerName() {
        return detailsOwnerName;
    }

    /**
     * @param detailsOwnerName
     *            the detailsOwnerName to set
     */
    public void setDetailsOwnerName(String detailsOwnerName) {
        this.detailsOwnerName = detailsOwnerName;
    }

    /**
     * @return the detailsRoadInitial
     */
    public String getDetailsRoadInitial() {
        return detailsRoadInitial;
    }

    /**
     * @param detailsRoadInitial
     *            the detailsRoadInitial to set
     */
    public void setDetailsRoadInitial(String detailsRoadInitial) {
        this.detailsRoadInitial = detailsRoadInitial;
    }

    /**
     * @return the detailsRoadNumberFrom
     */
    public String getDetailsRoadNumberFrom() {
        return detailsRoadNumberFrom;
    }

    /**
     * @param detailsRoadNumberFrom
     *            the detailsRoadNumberFrom to set
     */
    public void setDetailsRoadNumberFrom(String detailsRoadNumberFrom) {
        this.detailsRoadNumberFrom = detailsRoadNumberFrom;
    }

    /**
     * @return the detailsRoadNumberTo
     */
    public String getDetailsRoadNumberTo() {
        return detailsRoadNumberTo;
    }

    /**
     * @param detailsRoadNumberTo
     *            the detailsRoadNumberTo to set
     */
    public void setDetailsRoadNumberTo(String detailsRoadNumberTo) {
        this.detailsRoadNumberTo = detailsRoadNumberTo;
    }

    /**
     * @return the detailsDevice
     */
    public String getDetailsDevice() {
        return detailsDevice;
    }

    /**
     * @param detailsDevice
     *            the detailsDevice to set
     */
    public void setDetailsDevice(String detailsDevice) {
        this.detailsDevice = detailsDevice;
    }

    /**
     * @return the excludeReason
     */
    public List<ExcludedAssetsType> getExcludeReason() {
        return excludeReason;
    }

    /**
     * @param excludeReason
     *            the excludeReason to set
     */
    public void setExcludeReason(List<ExcludedAssetsType> excludeReason) {
        this.excludeReason = excludeReason;
    }

}
