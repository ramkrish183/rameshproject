package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.io.Serializable;

public class ControllerModelVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String controllerSrcId;
    private String modelName;
    private String modelObjId;
    private String dsViewName;
    private String vehicleObjid;
    private String partStatus;
    private String controllerCfg;

    public ControllerModelVO(String controllerSrcId, String modelName, String modelObjId, String dsViewName,
            String vehicleObjid, String partStatus, String controllerCfg) {
        this.controllerSrcId = controllerSrcId;
        this.modelName = modelName;
        this.modelObjId = modelObjId;
        this.dsViewName = dsViewName;
        this.vehicleObjid = vehicleObjid;
        this.partStatus = partStatus;
        this.controllerCfg = controllerCfg;
    }
    
    public ControllerModelVO(String controllerSrcId, String modelName, String modelObjId, String dsViewName,
            String vehicleObjid, String controllerCfg) {
        this.controllerSrcId = controllerSrcId;
        this.modelName = modelName;
        this.modelObjId = modelObjId;
        this.dsViewName = dsViewName;
        this.vehicleObjid = vehicleObjid;
        this.controllerCfg = controllerCfg;
    }

    public String getControllerCfg() {
        return controllerCfg;
    }

    public void setControllerCfg(String controllerCfg) {
        this.controllerCfg = controllerCfg;
    }

    public String getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(String partStatus) {
        this.partStatus = partStatus;
    }

    public String getVehicleObjid() {
        return vehicleObjid;
    }

    public void setVehicleObjid(String vehicleObjid) {
        this.vehicleObjid = vehicleObjid;
    }

    public String getControllerSrcId() {
        return controllerSrcId;
    }

    public void setControllerSrcId(String controllerSrcId) {
        this.controllerSrcId = controllerSrcId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelObjId() {
        return modelObjId;
    }

    public void setModelObjId(String modelObjId) {
        this.modelObjId = modelObjId;
    }

    public String getDsViewName() {
        return dsViewName;
    }

    public void setDsViewName(String dsViewName) {
        this.dsViewName = dsViewName;
    }
}
