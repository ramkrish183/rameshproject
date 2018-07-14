package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactDetailsResponseType", propOrder = { "firstName",
        "lastName", "phNo", "siteName", "city", "country", "contactRole",
        "contactObjId", "siteObjId", "emailId", "fax" })
@XmlRootElement
public class ContactDetailsResponseType {

    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String phNo;
    @XmlElement(required = true)
    protected String siteName;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String contactRole;
    @XmlElement(required = true)
    protected String contactObjId;
    @XmlElement(required = true)
    protected String siteObjId;
    @XmlElement(required = true)
    protected String emailId;
    @XmlElement(required = true)
    protected String fax;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactRole() {
        return contactRole;
    }

    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }

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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

}
