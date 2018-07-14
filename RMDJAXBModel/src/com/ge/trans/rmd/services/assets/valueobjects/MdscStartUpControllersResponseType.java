package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mdscStartUpControllersResponseType", propOrder = { "cabComm", "caxComm", "excComm", "auxComm",
        "efiComm", "smsEnabled", "flmComm", "AcComm", "ccaComm", "servicePdp", "eabComm", "erComm" })

@XmlRootElement
public class MdscStartUpControllersResponseType {

    @XmlElement(required = true)
    protected String cabComm;
    @XmlElement(required = true)
    protected String caxComm;
    @XmlElement(required = true)
    protected String excComm;
    @XmlElement(required = true)
    protected String auxComm;
    @XmlElement(required = true)
    protected String efiComm;
    @XmlElement(required = true)
    protected String smsEnabled;
    @XmlElement(required = true)
    protected String flmComm;
    @XmlElement(required = true)
    protected String AcComm;
    @XmlElement(required = true)
    protected String ccaComm;
    @XmlElement(required = true)
    protected String servicePdp;
    @XmlElement(required = true)
    protected String eabComm;
    @XmlElement(required = true)
    protected String erComm;

    public String getAcComm() {
        return AcComm;
    }

    public void setAcComm(String acComm) {
        AcComm = acComm;
    }

    public String getCcaComm() {
        return ccaComm;
    }

    public void setCcaComm(String ccaComm) {
        this.ccaComm = ccaComm;
    }

    public String getServicePdp() {
        return servicePdp;
    }

    public void setServicePdp(String servicePdp) {
        this.servicePdp = servicePdp;
    }

    public String getEabComm() {
        return eabComm;
    }

    public void setEabComm(String eabComm) {
        this.eabComm = eabComm;
    }

    public String getErComm() {
        return erComm;
    }

    public void setErComm(String erComm) {
        this.erComm = erComm;
    }

    public String getCabComm() {
        return cabComm;
    }

    public void setCabComm(String cabComm) {
        this.cabComm = cabComm;
    }

    public String getCaxComm() {
        return caxComm;
    }

    public void setCaxComm(String caxComm) {
        this.caxComm = caxComm;
    }

    public String getExcComm() {
        return excComm;
    }

    public void setExcComm(String excComm) {
        this.excComm = excComm;
    }

    public String getAuxComm() {
        return auxComm;
    }

    public void setAuxComm(String auxComm) {
        this.auxComm = auxComm;
    }

    public String getSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(String smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public String getEfiComm() {
        return efiComm;
    }

    public void setEfiComm(String efiComm) {
        this.efiComm = efiComm;
    }

    public String getFlmComm() {
        return flmComm;
    }

    public void setFlmComm(String flmComm) {
        this.flmComm = flmComm;
    }

}
