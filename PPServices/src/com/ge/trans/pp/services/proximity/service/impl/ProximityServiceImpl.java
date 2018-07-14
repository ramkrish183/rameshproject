package com.ge.trans.pp.services.proximity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.proximity.bo.intf.ProximityBOIntf;
import com.ge.trans.pp.services.proximity.service.intf.ProximityServiceIntf;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class ProximityServiceImpl implements ProximityServiceIntf {
    @Autowired
    private ProximityBOIntf objProximityBOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ProximityServiceImpl.class);

    public ProximityServiceImpl(final ProximityBOIntf objProximityBOIntf) {
        this.objProximityBOIntf = objProximityBOIntf;
    }

    /**
     * @Author:
     * @param country
     *            not mandatory
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the country and state list
     */

    @Override
    public List<PPCountryStateVO> getAllPPCountryStates(String countryName) throws RMDServiceException {
        List<PPCountryStateVO> objGetCountryStateListVO = null;
        try {
            objGetCountryStateListVO = objProximityBOIntf.getAllPPCountryStates(countryName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objGetCountryStateListVO;
    }

    @Override
    public List<PPCityVO> getCitiesForGivenState(String countryCode, String stateCode) throws RMDServiceException {
        List<PPCityVO> cityList = null;
        try {
            cityList = objProximityBOIntf.getCitiesForGivenState(countryCode, stateCode);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return cityList;
    }

    @Override
    public List<PPProximityResponseVO> getProximityDetails(String customerId, String city, String stateCode,
            String countryCode, long gpsLat, long gpsLong, float mileRange, List<String> products)
            throws RMDServiceException {
        List<PPProximityResponseVO> objPPProximityResponseVOLst = null;
        try {
            objPPProximityResponseVOLst = objProximityBOIntf.getProximityDetails(customerId, city, stateCode,
                    countryCode, gpsLat, gpsLong, mileRange, products);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objPPProximityResponseVOLst;
    }

}