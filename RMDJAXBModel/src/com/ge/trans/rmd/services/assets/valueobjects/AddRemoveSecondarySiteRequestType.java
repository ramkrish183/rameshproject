package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addRemoveSecondarySiteRequestType", propOrder = {
        "contactObjId", "siteObjId", "contactRole",
        "arlAddRemoveSecondarySiteRequestType" })
@XmlRootElement
public class AddRemoveSecondarySiteRequestType {

    @XmlElement(required = true)
    protected String contactObjId;
    @XmlElement(required = true)
    protected String siteObjId;
    @XmlElement(required = true)
    protected String contactRole;
    @XmlElement(required = true)
    protected List<AddRemoveSecondarySiteRequestType> arlAddRemoveSecondarySiteRequestType;

    public String getContactObjId() {
        return contactObjId;
    }

    public void setContactObjId(String contactObjId) {
        this.contactObjId = contactObjId;
    }

    public String getSiteObjId() {
        return siteObjId;
    }

    public void setSiteObjId(String siteObjId) {
        this.siteObjId = siteObjId;
    }

    public String getContactRole() {
        return contactRole;
    }

    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }

    public List<AddRemoveSecondarySiteRequestType> getArlAddRemoveSecondarySiteRequestType() {
        return arlAddRemoveSecondarySiteRequestType;
    }

    public void setArlAddRemoveSecondarySiteRequestType(
            List<AddRemoveSecondarySiteRequestType> arlAddRemoveSecondarySiteRequestType) {
        this.arlAddRemoveSecondarySiteRequestType = arlAddRemoveSecondarySiteRequestType;
    }

}
