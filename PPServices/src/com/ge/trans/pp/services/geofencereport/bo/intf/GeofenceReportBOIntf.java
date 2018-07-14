package com.ge.trans.pp.services.geofencereport.bo.intf;

import java.util.List;

import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface GeofenceReportBOIntf {

    /**
     * @param objGeofenceRoadRangeReqVO
     * @return List
     * @throws RMDServiceException
     */
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDBOException;
}
