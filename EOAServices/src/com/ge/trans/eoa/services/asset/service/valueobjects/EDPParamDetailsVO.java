package com.ge.trans.eoa.services.asset.service.valueobjects;

public class EDPParamDetailsVO {

    private String paramObjId;
    private String paramNo;
    private String ctrlName;
    private String paramDesc;
    private String uom;
    private String usageRate;
    private String destType;

    public String getParamObjId() {
        return paramObjId;
    }

    public void setParamObjId(String paramObjId) {
        this.paramObjId = paramObjId;
    }

    public String getParamNo() {
        return paramNo;
    }

    public void setParamNo(String paramNo) {
        this.paramNo = paramNo;
    }

    public String getCtrlName() {
        return ctrlName;
    }

    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }

    public String getDestType() {
        return destType;
    }

    public void setDestType(String destType) {
        this.destType = destType;
    }

}
