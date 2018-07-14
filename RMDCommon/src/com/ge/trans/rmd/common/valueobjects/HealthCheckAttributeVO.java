package com.ge.trans.rmd.common.valueobjects;

public class HealthCheckAttributeVO {

    private String signed;
    private String destType;
    private String noOfBytes;
    private String sourceApp;
    private String mPRateNum;
    private String requestParamNum;

    /**
     * @return the signed
     */
    public String getSigned() {
        return signed;
    }

    /**
     * @param signed
     *            the signed to set
     */
    public void setSigned(String signed) {
        this.signed = signed;
    }

    /**
     * @return the destType
     */
    public String getDestType() {
        return destType;
    }

    /**
     * @param destType
     *            the destType to set
     */
    public void setDestType(String destType) {
        this.destType = destType;
    }

    /**
     * @return the noOfBytes
     */
    public String getNoOfBytes() {
        return noOfBytes;
    }

    /**
     * @param noOfBytes
     *            the noOfBytes to set
     */
    public void setNoOfBytes(String noOfBytes) {
        this.noOfBytes = noOfBytes;
    }

    /**
     * @return the sourceApp
     */
    public String getSourceApp() {
        return sourceApp;
    }

    /**
     * @param sourceApp
     *            the sourceApp to set
     */
    public void setSourceApp(String sourceApp) {
        this.sourceApp = sourceApp;
    }

    /**
     * @return the mPRateNum
     */
    public String getmPRateNum() {
        return mPRateNum;
    }

    /**
     * @param mPRateNum
     *            the mPRateNum to set
     */
    public void setmPRateNum(String mPRateNum) {
        this.mPRateNum = mPRateNum;
    }

    /**
     * @return the requestParamNum
     */
    public String getRequestParamNum() {
        return requestParamNum;
    }

    /**
     * @param requestParamNum
     *            the requestParamNum to set
     */
    public void setRequestParamNum(String requestParamNum) {
        this.requestParamNum = requestParamNum;
    }

}
