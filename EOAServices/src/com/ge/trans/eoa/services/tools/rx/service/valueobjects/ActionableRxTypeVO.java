package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class ActionableRxTypeVO extends BaseVO {
    static final long serialVersionUID = 1299447734L;
    private String rxTypeCd;
    private String rxGroupId;
    private String rxTitle;
    private String rxTitleId;
    private String customerName;
    private String customerId;

    public String getRxTypeCd() {
        return rxTypeCd;
    }

    public void setRxTypeCd(String rxTypeCd) {
        this.rxTypeCd = rxTypeCd;
    }

    public String getRxGroupId() {
        return rxGroupId;
    }

    public void setRxGroupId(String rxGroupId) {
        this.rxGroupId = rxGroupId;
    }

    public String getRxTitle() {
        return rxTitle;
    }

    public void setRxTitle(String rxTitle) {
        this.rxTitle = rxTitle;
    }

    public String getRxTitleId() {
        return rxTitleId;
    }

    public void setRxTitleId(String rxTitleId) {
        this.rxTitleId = rxTitleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
