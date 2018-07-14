package com.ge.trans.pp.services.manageGeofence.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stateProvinceResponseType", propOrder = { "stateProvinceList", "regionList", "subRegionList" })

@XmlRootElement
public class StateProvinceResponseType {

    @XmlElement(required = true)
    protected List<String> stateProvinceList;
    @XmlElement(required = true)
    protected List<String> regionList;
    @XmlElement(required = true)
    protected List<String> subRegionList;

    /**
     * @return the stateProvinceList
     */
    public List<String> getStateProvinceList() {
        return stateProvinceList;
    }

    /**
     * @param stateProvinceList
     *            the stateProvinceList to set
     */
    public void setStateProvinceList(List<String> stateProvinceList) {
        this.stateProvinceList = stateProvinceList;
    }

    public List<String> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public List<String> getSubRegionList() {
        return subRegionList;
    }

    public void setSubRegionList(List<String> subRegionList) {
        this.subRegionList = subRegionList;
    }

}
