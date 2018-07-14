package com.ge.trans.pp.services.manageGeofence.bo.intf;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface ManageGeofenceBOIntf {

    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDBOException;

    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException;

    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException;

    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException;

}
