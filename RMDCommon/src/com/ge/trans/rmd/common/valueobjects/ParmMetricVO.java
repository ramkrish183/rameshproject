package com.ge.trans.rmd.common.valueobjects;

public class ParmMetricVO extends BaseVO {

    /**
     * 
     */
    private static final long serialVersionUID = 7792155600233870245L;
    private String paramDBName;
    private String sourceUOM;
    private String paramType;
    private String paramDisplayName;

    public String getSourceUOM() {
        return sourceUOM;
    }

    public void setSourceUOM(String sourceUOM) {
        this.sourceUOM = sourceUOM;
    }

    public String getParamDBName() {
        return paramDBName;
    }

    public void setParamDBName(String paramDBName) {
        this.paramDBName = paramDBName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamDisplayName() {
        return paramDisplayName;
    }

    public void setParamDisplayName(String paramDisplayName) {
        this.paramDisplayName = paramDisplayName;
    }

}
