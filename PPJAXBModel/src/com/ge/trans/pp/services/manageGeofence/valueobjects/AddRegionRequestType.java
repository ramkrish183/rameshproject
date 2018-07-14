package com.ge.trans.pp.services.manageGeofence.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addRegionrequestType", propOrder = { "customerId", "region", "subRegion", "userId"

})
@XmlRootElement
public class AddRegionRequestType {

    @XmlElement(required = true)
    protected String customerId;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String subRegion;
    @XmlElement(required = true)
    protected String userId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
