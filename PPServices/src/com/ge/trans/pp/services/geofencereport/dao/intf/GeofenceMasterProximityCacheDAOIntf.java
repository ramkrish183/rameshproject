package com.ge.trans.pp.services.geofencereport.dao.intf;

import java.util.Map;

import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceMasterProximityVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface GeofenceMasterProximityCacheDAOIntf {
    /**
     * @param GeofenceMasterProximityVO
     * @return
     * @throws RMDDAOException
     */
    public Map<String, GeofenceMasterProximityVO> getGeofenceMasterProximities() throws RMDDAOException;
}
