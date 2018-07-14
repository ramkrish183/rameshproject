package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repairFacilityDetailsResponseType", propOrder = { "objId", "railWayDesigCode", "repairLocation",
        "locationCode", "status" })

@XmlRootElement
public class RepairFacilityDetailsResponseType {

    @XmlElement(required = true)
    protected String objId;
    @XmlElement(required = true)
    protected String railWayDesigCode;
    @XmlElement(required = true)
    protected String repairLocation;
    @XmlElement(required = true)
    protected String locationCode;
    @XmlElement(required = true)
    protected String status;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getRailWayDesigCode() {
        return railWayDesigCode;
    }

    public void setRailWayDesigCode(String railWayDesigCode) {
        this.railWayDesigCode = railWayDesigCode;
    }

    public String getRepairLocation() {
        return repairLocation;
    }

    public void setRepairLocation(String repairLocation) {
        this.repairLocation = repairLocation;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
