package com.ge.trans.pp.services.geofencereport.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class GeofenceReportListReqVO extends BaseVO {

    private List<GeofenceReportReqVO> geofenceReportReqVOList;
    private List<String> geofences;
    private String roleId;

    public List<String> getGeofences() {
        return geofences;
    }

    public void setGeofences(List<String> geofences) {
        this.geofences = geofences;
    }

    public List<GeofenceReportReqVO> getGeofenceReportReqVOList() {
        return geofenceReportReqVOList;
    }

    public void setGeofenceReportReqVOList(List<GeofenceReportReqVO> geofenceReportReqVOList) {
        this.geofenceReportReqVOList = geofenceReportReqVOList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    

}
