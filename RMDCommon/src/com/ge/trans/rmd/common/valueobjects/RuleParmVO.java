package com.ge.trans.rmd.common.valueobjects;

public class RuleParmVO extends BaseVO {

    private static final long serialVersionUID = -4059401922203434046L;

    private String parmName;
    private String parmValue;
    private String unitAbbr;

    public String getParmName() {
        return parmName;
    }

    public void setParmName(String parmName) {
        this.parmName = parmName;
    }

    public String getParmValue() {
        return parmValue;
    }

    public void setParmValue(String parmValue) {
        this.parmValue = parmValue;
    }

    public String getUnitAbbr() {
        return unitAbbr;
    }

    public void setUnitAbbr(String unitAbbr) {
        this.unitAbbr = unitAbbr;
    }

}
