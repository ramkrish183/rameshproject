package com.ge.trans.eoa.services.asset.service.valueobjects;

public class VisualizationMPParmNumVO {

    private String parmNumber;
    private String parmDescription;
    private String parmDescriptionName;
    private String columnName;
    private String tableName;
    private String controllerCfg;
    private String parmNumColName;
    private String controllerType;
    private String parmType;
    
    public String getParmType() {
		return parmType;
	}

	public void setParmType(String parmType) {
		this.parmType = parmType;
	}

	public String getParmNumColName() {
        return parmNumColName;
    }

    public void setParmNumColName(String parmNumColName) {
        this.parmNumColName = parmNumColName;
    }

    public String getParmDescriptionName() {
        return parmDescriptionName;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public void setParmDescriptionName(String parmDescriptionName) {
        this.parmDescriptionName = parmDescriptionName;
    }

    public String getParmNumber() {
        return parmNumber;
    }

    public void setParmNumber(String parmNumber) {
        this.parmNumber = parmNumber;
    }

    public String getParmDescription() {
        return parmDescription;
    }

    public void setParmDescription(String parmDescription) {
        this.parmDescription = parmDescription;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getControllerCfg() {
        return controllerCfg;
    }

    public void setControllerCfg(String controllerCfg) {
        this.controllerCfg = controllerCfg;
    }

}
