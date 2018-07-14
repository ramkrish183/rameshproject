package com.ge.trans.pp.services.manageGeofence.service.impl;

import java.util.List;

import com.ge.trans.pp.common.util.RMDServiceErrorHandler;
import com.ge.trans.pp.services.manageGeofence.bo.intf.ManageGeofenceBOIntf;
import com.ge.trans.pp.services.manageGeofence.service.intf.ManageGeofenceServiceIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class ManageGeofenceServiceImpl implements ManageGeofenceServiceIntf {

    private ManageGeofenceBOIntf objManageGeofenceBOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ManageGeofenceServiceImpl.class);

    public ManageGeofenceServiceImpl(final ManageGeofenceBOIntf objManageGeofenceBOIntf) {
        this.objManageGeofenceBOIntf = objManageGeofenceBOIntf;
    }

    @Override
    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDServiceException {
        List<ManageGeofenceRespVO> geofenceProximityList = null;
        try {

            geofenceProximityList = objManageGeofenceBOIntf.getGeofenceProximityData(objManageGeofenceReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objManageGeofenceReqVO.getStrLanguage());
        }
        return geofenceProximityList;
    }

    @Override
    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException {
        try {

            objManageGeofenceBOIntf.saveUpdateGeofenceDetails(objManageGeofenceReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objManageGeofenceReqVO.getStrLanguage());
        }
    }

    @Override
    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException {
        String isGeofenceValid = null;
        try {

            isGeofenceValid = objManageGeofenceBOIntf.validateGeofenceData(objManageGeofenceReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objManageGeofenceReqVO.getStrLanguage());
        }
        return isGeofenceValid;
    }

    @Override
    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDServiceException {
        try {

            objManageGeofenceBOIntf.deleteGeofenceDetails(objManageGeofenceReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objManageGeofenceReqVO.getStrLanguage());
        }
    }
}
