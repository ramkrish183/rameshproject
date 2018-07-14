package com.ge.trans.pp.services.proximity.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface ProximityDAOIntf {

    /**
     * @param countryName
     * @Author:
     * @param GetSysParameterVO
     * @return
     * @throws RMDDAOException
     * @Description:this is method declaration for fetching the country state
     *                   list
     */
    List<PPCountryStateVO> getAllPPCountryStates(String countryName) throws RMDDAOException;

    /**
     * @param countryCode
     * @param stateCode
     * @return PPCityVO
     * @throws RMDDAOException
     * @Description:this is method declaration for fetching the city list
     */
    List<PPCityVO> getCitiesForGivenState(String countryCode, String stateCode) throws RMDDAOException;

    /**
     * @param customerId
     * @param city
     * @param stateCode
     * @param countryCode
     * @param gpsLat
     * @param gpsLong
     * @param mileRange
     * @return List<PPProximityResponseVO>
     * @throws RMDDAOException
     * @Description:this is method declaration for fetching the road initial
     *                   list for given customer
     */
    List<PPProximityResponseVO> getProximityDetails(String customerId, String city, String stateCode,
            String countryCode, long gpsLat, long gpsLong, float mileRange, List<String> products)
            throws RMDDAOException;

}
