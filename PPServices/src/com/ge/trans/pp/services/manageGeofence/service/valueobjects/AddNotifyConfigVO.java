/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: AddNotifyConfigVO.java
 * Description 			:
 * Package 				: com.ge.trans.rmd.cm.valueobjects;
 * Author 				: iGATE Global Solutions Ltd.
 * Last Edited By 		:
 * Version 				: 1.0
 * Created on 			:
 * History				:
 * Modified By 			: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.pp.services.manageGeofence.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This is plain POJO class which contains Add notify config
 *              Declarations along with their respective getters and setters.
 * @History :
 ******************************************************************************/

public class AddNotifyConfigVO {

    private String userName;
    private String customer;
    private String modelObjId;
    private String notifyFlagName;
    private String notifyFlagValue;
    private String thresholdName;
    private String thresholdValue;
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getModelObjId() {
        return modelObjId;
    }

    public void setModelObjId(String modelObjId) {
        this.modelObjId = modelObjId;
    }

    public String getNotifyFlagName() {
        return notifyFlagName;
    }

    public void setNotifyFlagName(String notifyFlagName) {
        this.notifyFlagName = notifyFlagName;
    }

    public String getNotifyFlagValue() {
        return notifyFlagValue;
    }

    public void setNotifyFlagValue(String notifyFlagValue) {
        this.notifyFlagValue = notifyFlagValue;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

}
