package com.ge.trans.eoa.services.alert.service.valueobjects;

public class ModelVO {
    private String customerId;
    private String modelObjId;
    private String modelName;
    private String modelFamily;
    private String modelGeneral;
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getModelObjId() {
        return modelObjId;
    }

    public void setModelObjId(String modelObjId) {
        this.modelObjId = modelObjId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

	public String getModelFamily() {
		return modelFamily;
	}

	public void setModelFamily(String modelFamily) {
		this.modelFamily = modelFamily;
	}

    public String getModelGeneral() {
        return modelGeneral;
    }

    public void setModelGeneral(String modelGeneral) {
        this.modelGeneral = modelGeneral;
    }
}
