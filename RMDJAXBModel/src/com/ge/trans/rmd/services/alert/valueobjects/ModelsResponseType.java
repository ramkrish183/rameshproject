package com.ge.trans.rmd.services.alert.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modelsResponseType", propOrder = { "modelObjId", "modelName", "customerId", "modelFamily", "modelGeneral" })
@XmlRootElement
public class ModelsResponseType {

    @XmlElement(required = true)
    protected String modelObjId;
    @XmlElement(required = true)
    protected String modelName;
    @XmlElement(required = true)
    protected String customerId;
    @XmlElement(required = true)
    protected String modelFamily;
    @XmlElement(required = true)
    protected String modelGeneral;
    
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
