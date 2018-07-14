package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activePreservationDataRequestType", propOrder = {
        "assetOwnerId", "roadInitial", "roadNumber", "device" })
@XmlRootElement
public class ActivePreservationDataRequestType {

    @XmlElement(required = true)
    private String assetOwnerId;
    @XmlElement(required = true)
    private String roadInitial;
    @XmlElement(required = true)
    private String roadNumber;
    @XmlElement(required = true)
    private String device;

    public String getAssetOwnerId() {
        return assetOwnerId;
    }

    public void setAssetOwnerId(String assetOwnerId) {
        this.assetOwnerId = assetOwnerId;
    }

    public String getRoadInitial() {
        return roadInitial;
    }

    public void setRoadInitial(String roadInitial) {
        this.roadInitial = roadInitial;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
