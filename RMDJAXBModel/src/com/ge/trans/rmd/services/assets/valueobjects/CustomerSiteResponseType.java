package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerSiteResponseType", propOrder = { "siteObjId", "siteName"})
@XmlRootElement
public class CustomerSiteResponseType {

    @XmlElement(required = true)
    protected String siteObjId;
    @XmlElement(required = true)
    protected String siteName;

    public String getSiteObjId() {
        return siteObjId;
    }

    public void setSiteObjId(String siteObjId) {
        this.siteObjId = siteObjId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
