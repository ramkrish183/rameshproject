package com.ge.trans.rmd.services.assets.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customers")
public class CustomerMaster {
    private List<CustomerBean> custlistdata;

    public List<CustomerBean> getCustlistdata() {
        return custlistdata;
    }

    public void setCustlistdata(List<CustomerBean> custlistdata) {
        this.custlistdata = custlistdata;
    }

}
