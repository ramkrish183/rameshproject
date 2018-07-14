package com.ge.trans.rmd.cases.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class RxWorstUrgencyVO extends BaseVO {

    private String assetNumber;
    private String assetGrpName;
    private String worstUrgency;
    private String customerId;

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

    public String getWorstUrgency() {
        return worstUrgency;
    }

    public void setWorstUrgency(final String worstUrgency) {
        this.worstUrgency = worstUrgency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

}
