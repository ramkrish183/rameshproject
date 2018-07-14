package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

public class VehicleVO {

    private long objid;
    private String customerId;
    private String roadNumber;
    private String fleetId;
    private String modelId;
    private int controllerId = 0;

    public VehicleVO(long objid, String customerId, String roadNumber, String fleetId, String modelId) {
        this.objid = objid;
        this.customerId = customerId;
        this.roadNumber = roadNumber;
        this.fleetId = fleetId;
        this.modelId = modelId;
    }

    public VehicleVO(long objid, String customerId, String roadNumber, String fleetId, String modelId,
            int controllerSourceId) {
        this.objid = objid;
        this.customerId = customerId;
        this.roadNumber = roadNumber;
        this.fleetId = fleetId;
        this.modelId = modelId;
        this.controllerId = controllerSourceId;
    }

    public VehicleVO(String vehicleId) {
        long objid = 0L;
        try {
            objid = new Long(vehicleId).longValue();
        } catch (NumberFormatException nfe) {
        }
        this.objid = objid;
    }

    public long getObjid() {
        return objid;
    }

    public void setObjid(long objid) {
        this.objid = objid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getObjidAsString() {
        return new String("" + objid);
    }

    public String getFleetId() {
        return fleetId;
    }

    public void setFleetId(String fleetId) {
        this.fleetId = fleetId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public int getControllerId() {
        return controllerId;
    }

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

}
