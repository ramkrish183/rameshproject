package com.ge.trans.pp.services.proximity.service.intf;

import java.util.List;

import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface ProximityServiceIntf {
    /**
     * @Author:
     * @param countryName
     *            not mandatory
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for fetching the country and state list
     */

    List<PPCountryStateVO> getAllPPCountryStates(String countryName) throws RMDServiceException;

    /**
     * @param countryCode
     * @param stateCode
     * @return PPCityVO
     * @throws RMDServiceException
     * @Description:this is method declaration for fetching the city list
     */
    List<PPCityVO> getCitiesForGivenState(String countryCode, String stateCode) throws RMDServiceException;

    /**
     * @param customerId
     * @param city
     * @param stateCode
     * @param countryCode
     * @param g
     * @param h
     * @param f
     * @param products
     * @return List<PPProximityResponseVO>
     * @throws RMDServiceException
     * @Description:this is method declaration for fetching the road initial
     *                   list for given customer
     */
    List<PPProximityResponseVO> getProximityDetails(String customerId, String city, String stateCode,
            String countryCode, long g, long h, float f, List<String> products) throws RMDServiceException;

}
