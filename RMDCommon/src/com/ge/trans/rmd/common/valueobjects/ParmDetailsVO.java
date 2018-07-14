package com.ge.trans.rmd.common.valueobjects;

/**
 * Stores Rule Parm details in VO
 * 
 * @author 212556286
 * 
 */
public class ParmDetailsVO extends BaseVO {

    private static final long serialVersionUID = 3239758906697947618L;

    private String parmObjId;
    private String parmName;
    private String parmValue;

    public String getParmObjId() {
        return parmObjId;
    }

    public void setParmObjId(String parmObjId) {
        this.parmObjId = parmObjId;
    }

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

}
