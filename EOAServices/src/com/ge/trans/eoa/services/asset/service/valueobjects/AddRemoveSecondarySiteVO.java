package com.ge.trans.eoa.services.asset.service.valueobjects;

public class AddRemoveSecondarySiteVO {
    private String contactObjId;
    private String siteObjId;
    private String contactRole;

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

}
