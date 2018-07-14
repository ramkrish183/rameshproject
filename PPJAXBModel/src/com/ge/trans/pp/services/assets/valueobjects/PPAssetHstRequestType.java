package com.ge.trans.pp.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ppAssetHstRequestType", propOrder = { "status", "rnhId", "rnId" })
@XmlRootElement
public class PPAssetHstRequestType {
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String rnhId;
    @XmlElement(required = true)
    protected String rnId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRnhId() {
        return rnhId;
    }

    public void setRnhId(String rnhId) {
        this.rnhId = rnhId;
    }

    public String getRnId() {
        return rnId;
    }

    public void setRnId(String rnId) {
        this.rnId = rnId;
    }

}
