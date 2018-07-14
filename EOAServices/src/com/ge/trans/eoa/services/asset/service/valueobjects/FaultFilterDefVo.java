package com.ge.trans.eoa.services.asset.service.valueobjects;

public class FaultFilterDefVo {
    private String configId;
    private String template;
    private String version;
    private String title;
    private String status;
    private String parameterTitle;
    private String operatorFrom;
    private String operatorTo;
    private String valueFrom;
    private String valueTo;
    private String conjunction;
    private String userName;
    private String action;
    private String parameterObjId;
    private String descObjId;
    private String objId;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParameterTitle() {
        return parameterTitle;
    }

    public void setParameterTitle(String parameterTitle) {
        this.parameterTitle = parameterTitle;
    }

    public String getOperatorFrom() {
        return operatorFrom;
    }

    public void setOperatorFrom(String operatorFrom) {
        this.operatorFrom = operatorFrom;
    }

    public String getOperatorTo() {
        return operatorTo;
    }

    public void setOperatorTo(String operatorTo) {
        this.operatorTo = operatorTo;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public String getConjunction() {
        return conjunction;
    }

    public void setConjunction(String conjunction) {
        this.conjunction = conjunction;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    private String configValue;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getDescObjId() {
        return descObjId;
    }

    public void setDescObjId(String descObjId) {
        this.descObjId = descObjId;
    }

    public String getParameterObjId() {
        return parameterObjId;
    }

    public void setParameterObjId(String parameterObjId) {
        this.parameterObjId = parameterObjId;
    }

}
