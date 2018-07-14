package com.ge.trans.pp.services.geofencereport.bo.impl;

import java.util.List;

import com.ge.trans.pp.services.geofencereport.bo.intf.GeofenceReportBOIntf;
import com.ge.trans.pp.services.geofencereport.dao.intf.GeofenceReportDAOIntf;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class GeofenceReportBOImpl implements GeofenceReportBOIntf {
    private GeofenceReportDAOIntf objGeofenceReportDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(GeofenceReportBOImpl.class);

    public GeofenceReportBOImpl(final GeofenceReportDAOIntf objGeofenceReportDAOIntf) {
        this.objGeofenceReportDAOIntf = objGeofenceReportDAOIntf;
    }

    @Override
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDBOException {
        List<GeofenceReportVO> lstGeofenceReport;
        try {
            lstGeofenceReport = objGeofenceReportDAOIntf.getGeofenceReport(objGeofenceRoadRangeReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstGeofenceReport;
    }
}
