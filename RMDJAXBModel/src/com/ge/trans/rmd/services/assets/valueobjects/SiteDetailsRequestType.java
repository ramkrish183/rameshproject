package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "siteDetailsRequestType", propOrder = { "strType",
        "strSiteObjId", "strSiteID", "strSiteName", "strAddress", "strCity",
        "strState", "strAccountID", "strAccountName",
        "strInclInactiveContacts", "strCustomer", "strShipTo", "strBillTo",
        "strPrefShipMethod", "strCellPhone", "strFax", "strStatus",
        "strCreateType", "primAddObjId", "shipAddObjId", "billAddObjId",
        "strAddressEx", "strShipToEx", "strBillToEx"})
@XmlRootElement
public class SiteDetailsRequestType {
    @XmlElement(required = true)
    protected String strType;
    @XmlElement(required = true)
    protected String strSiteObjId;
    @XmlElement(required = true)
    protected String strSiteID;
    @XmlElement(required = true)
    protected String strSiteName;
    @XmlElement(required = true)
    protected String strAddress;
    @XmlElement(required = true)
    protected String strAddressEx;
    @XmlElement(required = true)
    protected String strCity;
    @XmlElement(required = true)
    protected String strState;
    @XmlElement(required = true)
    protected String strAccountID;
    @XmlElement(required = true)
    protected String strAccountName;
    @XmlElement(required = true)
    protected String strInclInactiveContacts;
    @XmlElement(required = true)
    protected String strCustomer;
    @XmlElement(required = true)
    protected String strShipTo;
    @XmlElement(required = true)
    protected String strShipToEx;
    @XmlElement(required = true)
    protected String strBillTo;
    @XmlElement(required = true)
    protected String strBillToEx;
    @XmlElement(required = true)
    protected String strPrefShipMethod;
    @XmlElement(required = true)
    protected String strCellPhone;
    @XmlElement(required = true)
    protected String strFax;
    @XmlElement(required = true)
    protected String strStatus;
    @XmlElement(required = true)
    protected String strCreateType;
    @XmlElement(required = true)
    protected String primAddObjId;
    @XmlElement(required = true)
    protected String shipAddObjId;
    @XmlElement(required = true)
    protected String billAddObjId;

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getStrSiteObjId() {
        return strSiteObjId;
    }

    public void setStrSiteObjId(String strSiteObjId) {
        this.strSiteObjId = strSiteObjId;
    }

    public String getStrSiteID() {
        return strSiteID;
    }

    public void setStrSiteID(String strSiteID) {
        this.strSiteID = strSiteID;
    }

    public String getStrSiteName() {
        return strSiteName;
    }

    public void setStrSiteName(String strSiteName) {
        this.strSiteName = strSiteName;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrCity() {
        return strCity;
    }

    public void setStrCity(String strCity) {
        this.strCity = strCity;
    }

    public String getStrState() {
        return strState;
    }

    public void setStrState(String strState) {
        this.strState = strState;
    }

    public String getStrAccountID() {
        return strAccountID;
    }

    public void setStrAccountID(String strAccountID) {
        this.strAccountID = strAccountID;
    }

    public String getStrAccountName() {
        return strAccountName;
    }

    public void setStrAccountName(String strAccountName) {
        this.strAccountName = strAccountName;
    }

    public String getStrInclInactiveContacts() {
        return strInclInactiveContacts;
    }

    public void setStrInclInactiveContacts(String strInclInactiveContacts) {
        this.strInclInactiveContacts = strInclInactiveContacts;
    }

    public String getStrCustomer() {
        return strCustomer;
    }

    public void setStrCustomer(String strCustomer) {
        this.strCustomer = strCustomer;
    }

    public String getStrShipTo() {
        return strShipTo;
    }

    public void setStrShipTo(String strShipTo) {
        this.strShipTo = strShipTo;
    }

    public String getStrBillTo() {
        return strBillTo;
    }

    public void setStrBillTo(String strBillTo) {
        this.strBillTo = strBillTo;
    }

    public String getStrPrefShipMethod() {
        return strPrefShipMethod;
    }

    public void setStrPrefShipMethod(String strPrefShipMethod) {
        this.strPrefShipMethod = strPrefShipMethod;
    }

    public String getStrCellPhone() {
        return strCellPhone;
    }

    public void setStrCellPhone(String strCellPhone) {
        this.strCellPhone = strCellPhone;
    }

    public String getStrFax() {
        return strFax;
    }

    public void setStrFax(String strFax) {
        this.strFax = strFax;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrCreateType() {
        return strCreateType;
    }

    public void setStrCreateType(String strCreateType) {
        this.strCreateType = strCreateType;
    }

    public String getPrimAddObjId() {
        return primAddObjId;
    }

    public void setPrimAddObjId(String primAddObjId) {
        this.primAddObjId = primAddObjId;
    }

    public String getShipAddObjId() {
        return shipAddObjId;
    }

    public void setShipAddObjId(String shipAddObjId) {
        this.shipAddObjId = shipAddObjId;
    }

    public String getBillAddObjId() {
        return billAddObjId;
    }

    public void setBillAddObjId(String billAddObjId) {
        this.billAddObjId = billAddObjId;
    }

    public String getStrAddressEx() {
        return strAddressEx;
    }

    public void setStrAddressEx(String strAddressEx) {
        this.strAddressEx = strAddressEx;
    }

    public String getStrShipToEx() {
        return strShipToEx;
    }

    public void setStrShipToEx(String strShipToEx) {
        this.strShipToEx = strShipToEx;
    }

    public String getStrBillToEx() {
        return strBillToEx;
    }

    public void setStrBillToEx(String strBillToEx) {
        this.strBillToEx = strBillToEx;
    }

}
