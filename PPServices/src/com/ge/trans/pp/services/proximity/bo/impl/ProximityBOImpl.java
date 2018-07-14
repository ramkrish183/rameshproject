package com.ge.trans.pp.services.proximity.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.proximity.bo.intf.ProximityBOIntf;
import com.ge.trans.pp.services.proximity.dao.intf.ProximityDAOIntf;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class ProximityBOImpl implements ProximityBOIntf {
    @Autowired
    private ProximityDAOIntf objProximityDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ProximityBOImpl.class);

    public ProximityBOImpl(final ProximityDAOIntf objProximityDAOIntf) {
        this.objProximityDAOIntf = objProximityDAOIntf;
    }

    /**
     * @Author:
     * @param countryName
     *            not mandatory
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the country and state list
     */
    @Override
    public List<PPCountryStateVO> getAllPPCountryStates(String countryName) throws RMDBOException {
        try {
            return objProximityDAOIntf.getAllPPCountryStates(countryName);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<PPCityVO> getCitiesForGivenState(String countryCode, String stateCode) throws RMDBOException {
        try {
            return objProximityDAOIntf.getCitiesForGivenState(countryCode, stateCode);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<PPProximityResponseVO> getProximityDetails(String customerId, String city, String stateCode,
            String countryCode, long gpsLat, long gpsLong, float mileRange, List<String> products)
            throws RMDBOException {
        List<PPProximityResponseVO> objPPProximityResponseVOLst = null;
        try {
            objPPProximityResponseVOLst = objProximityDAOIntf.getProximityDetails(customerId, city, stateCode,
                    countryCode, gpsLat, gpsLong, mileRange, products);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objPPProximityResponseVOLst;
    }

}
