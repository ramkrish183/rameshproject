package com.ge.trans.eoa.services.alert.service.valueobjects;

import java.util.ArrayList;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AlertDetailsVO extends BaseVO{

    private ArrayList<String> alertNotifyTypeList;
    private String customerId;
    private boolean enabledFlag;
    private String ruleTitle;
    private String originalId;
    
    
    public ArrayList<String> getAlertNotifyTypeList() {
        return alertNotifyTypeList;
    }
    public void setAlertNotifyTypeList(ArrayList<String> alertNotifyTypeList) {
        this.alertNotifyTypeList = alertNotifyTypeList;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public boolean isEnabledFlag() {
        return enabledFlag;
    }
    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
    public String getRuleTitle() {
        return ruleTitle;
    }
    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }
    public String getOriginalId() {
        return originalId;
    }
    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }
    
    
}
