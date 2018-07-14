/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: NotifyConfigVO.java
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
 * @Description :This is plain POJO class which contains notify config
 *              Declarations along with their respective getters and setters.
 * @History :
 ******************************************************************************/
public class NotifyConfigVO {

    private String cfgobjId;
    private String orgObjId;
    private String modelObjId;
    private String customer;
    private String model;
    private String cfgParamName;
    private String cfgValue;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCfgobjId() {
        return cfgobjId;
    }

    public void setCfgobjId(String cfgobjId) {
        this.cfgobjId = cfgobjId;
    }

    public String getOrgObjId() {
        return orgObjId;
    }

    public void setOrgObjId(String orgObjId) {
        this.orgObjId = orgObjId;
    }

    public String getModelObjId() {
        return modelObjId;
    }

    public void setModelObjId(String modelObjId) {
        this.modelObjId = modelObjId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCfgParamName() {
        return cfgParamName;
    }

    public void setCfgParamName(String cfgParamName) {
        this.cfgParamName = cfgParamName;
    }

    public String getCfgValue() {
        return cfgValue;
    }

    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue;
    }

}
