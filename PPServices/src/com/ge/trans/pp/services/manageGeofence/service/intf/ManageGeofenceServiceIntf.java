package com.ge.trans.pp.services.manageGeofence.service.intf;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ManageGeofenceServiceIntf {
    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDServiceException;

    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException;

    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException;

    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException;

}
