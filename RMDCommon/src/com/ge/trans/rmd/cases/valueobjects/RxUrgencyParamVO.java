package com.ge.trans.rmd.cases.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class RxUrgencyParamVO extends BaseVO {

    private String customerId;
    private String assetNumber;
    private String assetGrpName;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(final String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

}
