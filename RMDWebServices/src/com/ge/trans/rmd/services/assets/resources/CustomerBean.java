package com.ge.trans.rmd.services.assets.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class CustomerBean {

    private String customerID;
    private String customerName;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(final String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

}
