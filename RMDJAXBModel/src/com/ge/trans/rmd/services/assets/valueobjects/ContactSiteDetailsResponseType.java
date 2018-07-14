package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactSiteDetailsResponseType", propOrder = { "contactObjId",
        "jobTitle", "contactStatus", "firstName", "lastName", "phNo", "fax",
        "emailId", "timeZone", "siteId", "siteName", "siteType", "address1",
        "address2", "city", "state", "country", "zipCode", "salutation",
        "contactRole", "dailComm", "homePh", "cellPh", "creater", "locObjId",
        "voiceMail" })
@XmlRootElement
public class ContactSiteDetailsResponseType {

    @XmlElement(required = true)
    protected String contactObjId;
    @XmlElement(required = true)
    protected String jobTitle;
    @XmlElement(required = true)
    protected String contactStatus;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String phNo;
    @XmlElement(required = true)
    protected String fax;
    @XmlElement(required = true)
    protected String emailId;
    @XmlElement(required = true)
    protected String timeZone;
    @XmlElement(required = true)
    protected String siteId;
    @XmlElement(required = true)
    protected String siteName;
    @XmlElement(required = true)
    protected String siteType;
    @XmlElement(required = true)
    protected String address1;
    @XmlElement(required = true)
    protected String address2;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String state;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String zipCode;
    @XmlElement(required = true)
    protected String salutation;
    @XmlElement(required = true)
    protected String contactRole;
    @XmlElement(required = true)
    protected String dailComm;
    @XmlElement(required = true)
    protected String homePh;
    @XmlElement(required = true)
    protected String cellPh;
    @XmlElement(required = true)
    protected String creater;
    @XmlElement(required = true)
    protected String locObjId;
    @XmlElement(required = true)
    protected String voiceMail;

    public String getContactObjId() {
        return contactObjId;
    }

    public void setContactObjId(String contactObjId) {
        this.contactObjId = contactObjId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getContactRole() {
        return contactRole;
    }

    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }

    public String getDailComm() {
        return dailComm;
    }

    public void setDailComm(String dailComm) {
        this.dailComm = dailComm;
    }

    public String getHomePh() {
        return homePh;
    }

    public void setHomePh(String homePh) {
        this.homePh = homePh;
    }

    public String getCellPh() {
        return cellPh;
    }

    public void setCellPh(String cellPh) {
        this.cellPh = cellPh;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getLocObjId() {
        return locObjId;
    }

    public void setLocObjId(String locObjId) {
        this.locObjId = locObjId;
    }

    public String getVoiceMail() {
        return voiceMail;
    }

    public void setVoiceMail(String voiceMail) {
        this.voiceMail = voiceMail;
    }

}
