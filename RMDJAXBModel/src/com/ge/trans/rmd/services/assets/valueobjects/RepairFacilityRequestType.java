package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "repairFacilityRequestType", propOrder = { "customerId", "site", "railwayDesigCode", "repairLocation",
        "locationCode", "status", "userName", "objId", "arlRepairFaciityRequestType" })

@XmlRootElement
public class RepairFacilityRequestType {

    @XmlElement(required = true)
    protected String customerId;
    @XmlElement(required = true)
    protected String site;
    @XmlElement(required = true)
    protected String railwayDesigCode;
    @XmlElement(required = true)
    protected String repairLocation;
    @XmlElement(required = true)
    protected String locationCode;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected String objId;
    @XmlElement(required = true)
    protected List<RepairFacilityRequestType> arlRepairFaciityRequestType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getRailwayDesigCode() {
        return railwayDesigCode;
    }

    public void setRailwayDesigCode(String railwayDesigCode) {
        this.railwayDesigCode = railwayDesigCode;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public List<RepairFacilityRequestType> getArlRepairFaciityRequestType() {
        return arlRepairFaciityRequestType;
    }

    public void setArlRepairFaciityRequestType(List<RepairFacilityRequestType> arlRepairFaciityRequestType) {
        this.arlRepairFaciityRequestType = arlRepairFaciityRequestType;
    }

}
