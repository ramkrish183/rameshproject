package com.ge.trans.pp.services.geofencereport.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface GeofenceReportDAOIntf {

    /**
     * @param objGeofenceRoadRangeReqVO
     * @return List
     * @throws RMDDAOException
     */
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDDAOException;

}
