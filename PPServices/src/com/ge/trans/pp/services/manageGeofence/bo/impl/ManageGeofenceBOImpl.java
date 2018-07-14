package com.ge.trans.pp.services.manageGeofence.bo.impl;

import java.util.List;

import com.ge.trans.pp.services.manageGeofence.bo.intf.ManageGeofenceBOIntf;
import com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class ManageGeofenceBOImpl implements ManageGeofenceBOIntf {
    private ManageGeofenceDAOIntf objManageGeofenceDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ManageGeofenceBOImpl.class);

    public ManageGeofenceBOImpl(final ManageGeofenceDAOIntf objManageGeofenceDAOIntf) {
        this.objManageGeofenceDAOIntf = objManageGeofenceDAOIntf;
    }

    @Override
    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDBOException {
        List<ManageGeofenceRespVO> lstGeofenceReport;
        try {
            lstGeofenceReport = objManageGeofenceDAOIntf.getGeofenceProximityData(objManageGeofenceReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstGeofenceReport;
    }

    @Override
    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException {
        try {
            objManageGeofenceDAOIntf.saveUpdateGeofenceDetails(objManageGeofenceReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException {
        String isGeofenceValid;
        try {
            isGeofenceValid = objManageGeofenceDAOIntf.validateGeofenceData(objManageGeofenceReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return isGeofenceValid;
    }

    @Override
    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDBOException {
        try {
            objManageGeofenceDAOIntf.deleteGeofenceDetails(objManageGeofenceReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }
}
