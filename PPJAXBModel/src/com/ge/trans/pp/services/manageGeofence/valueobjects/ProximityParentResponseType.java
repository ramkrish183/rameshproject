package com.ge.trans.pp.services.manageGeofence.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "proximityParentResponseType", propOrder = { "proximityParentList" })

@XmlRootElement
public class ProximityParentResponseType {

    @XmlElement(required = true)
    protected List<String> proximityParentList;

    /**
     * @return the proximityParentList
     */
    public List<String> getProximityParentList() {
        return proximityParentList;
    }

    /**
     * @param proximityParentList
     *            the proximityParentList to set
     */
    public void setProximityParentList(List<String> proximityParentList) {
        this.proximityParentList = proximityParentList;
    }

}
