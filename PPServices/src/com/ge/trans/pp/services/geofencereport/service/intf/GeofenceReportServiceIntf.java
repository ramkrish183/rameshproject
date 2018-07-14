package com.ge.trans.pp.services.geofencereport.service.intf;

import java.util.List;

import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface GeofenceReportServiceIntf {

    /**
     * @param objGeofenceRoadRangeReqVO
     * @return List
     * @throws RMDServiceException
     */
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDServiceException;
}
