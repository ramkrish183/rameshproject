package com.ge.trans.pp.services.geofencereport.service.impl;

import java.util.List;

import com.ge.trans.pp.common.util.RMDServiceErrorHandler;
import com.ge.trans.pp.services.geofencereport.bo.intf.GeofenceReportBOIntf;
import com.ge.trans.pp.services.geofencereport.service.intf.GeofenceReportServiceIntf;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class GeofenceReportServiceImpl implements GeofenceReportServiceIntf {

    private GeofenceReportBOIntf objGeofenceReportBOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(GeofenceReportServiceImpl.class);

    public GeofenceReportServiceImpl(final GeofenceReportBOIntf objGeofenceReportBOIntf) {
        this.objGeofenceReportBOIntf = objGeofenceReportBOIntf;
    }

    /**
     * 
     */

    @Override
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDServiceException {
        List<GeofenceReportVO> geofenceReportLst = null;
        try {

            geofenceReportLst = objGeofenceReportBOIntf.getGeofenceReport(objGeofenceRoadRangeReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objGeofenceRoadRangeReqVO.getStrLanguage());
        }
        return geofenceReportLst;
    }
}
