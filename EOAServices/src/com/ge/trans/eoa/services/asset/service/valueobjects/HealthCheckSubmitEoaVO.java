package com.ge.trans.eoa.services.asset.service.valueobjects;

public class HealthCheckSubmitEoaVO {
    private String customerID;
    private String roadIntial;
    private Long roadNumber;
    private String assetType;
    private Long mpGroupId;
    private String mpGroupName;
    private Long sampleRate;
    private Long postSamples;
    private String platform;
    private String requestNum;
    private String assetIp;
    private String userId;
    private String vehicleObjID;
    private String mpNumber;
    private String template;
    private Long edpTemplate;
    private Long sdpTemplate;
    private Long objId;
    private String typeOfUser;

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    /**
     * @return the assetIp
     */
    public String getAssetIp() {
        return assetIp;
    }

    /**
     * @param assetIp
     *            the assetIp to set
     */
    public void setAssetIp(String assetIp) {
        this.assetIp = assetIp;
    }

    /**
     * @return the objId
     */
    public Long getObjId() {
        return objId;
    }

    /**
     * @param objId
     *            the objId to set
     */
    public void setObjId(Long objId) {
        this.objId = objId;
    }

    /**
     * @return the edpTemplate
     */
    public Long getEdpTemplate() {
        return edpTemplate;
    }

    /**
     * @param edpTemplate
     *            the edpTemplate to set
     */
    public void setEdpTemplate(Long edpTemplate) {
        this.edpTemplate = edpTemplate;
    }

    /**
     * @return the sdpTemplate
     */
    public Long getSdpTemplate() {
        return sdpTemplate;
    }

    /**
     * @param sdpTemplate
     *            the sdpTemplate to set
     */
    public void setSdpTemplate(Long sdpTemplate) {
        this.sdpTemplate = sdpTemplate;
    }

    /**
     * @return the mpNumber
     */
    public String getMpNumber() {
        return mpNumber;
    }

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template
     *            the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @param mpNumber
     *            the mpNumber to set
     */
    public void setMpNumber(String mpNumber) {
        this.mpNumber = mpNumber;
    }

    public String getVehicleObjID() {
        return vehicleObjID;
    }

    public void setVehicleObjID(String vehicleObjID) {
        this.vehicleObjID = vehicleObjID;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    /**
     * @return the customerID
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     *            the customerID to set
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the roadIntial
     */
    public String getRoadIntial() {
        return roadIntial;
    }

    /**
     * @param roadIntial
     *            the roadIntial to set
     */
    public void setRoadIntial(String roadIntial) {
        this.roadIntial = roadIntial;
    }

    /**
     * @return the roadNumber
     */
    public Long getRoadNumber() {
        return roadNumber;
    }

    /**
     * @param roadNumber
     *            the roadNumber to set
     */
    public void setRoadNumber(Long roadNumber) {
        this.roadNumber = roadNumber;
    }

    /**
     * @return the assetType
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     * @param assetType
     *            the assetType to set
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /**
     * @return the mpGroupId
     */
    public Long getMpGroupId() {
        return mpGroupId;
    }

    /**
     * @param mpGroupId
     *            the mpGroupId to set
     */
    public void setMpGroupId(Long mpGroupId) {
        this.mpGroupId = mpGroupId;
    }

    /**
     * @return the mpGroupName
     */
    public String getMpGroupName() {
        return mpGroupName;
    }

    /**
     * @param mpGroupName
     *            the mpGroupName to set
     */
    public void setMpGroupName(String mpGroupName) {
        this.mpGroupName = mpGroupName;
    }

    /**
     * @return the sampleRate
     */
    public Long getSampleRate() {
        return sampleRate;
    }

    /**
     * @param sampleRate
     *            the sampleRate to set
     */
    public void setSampleRate(Long sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * @return the postSamples
     */
    public Long getPostSamples() {
        return postSamples;
    }

    /**
     * @param postSamples
     *            the postSamples to set
     */
    public void setPostSamples(Long postSamples) {
        this.postSamples = postSamples;
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform
     *            the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
