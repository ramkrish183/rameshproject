/**
 * 
 */
package com.ge.trans.pp.services.proximity.resources;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.resources.BaseResource;
import com.ge.trans.pp.common.util.BeanUtility;
import com.ge.trans.pp.common.util.PPRMDWebServiceErrorHandler;
import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.proximity.service.intf.ProximityServiceIntf;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.pp.services.proximity.valueobjects.CityType;
import com.ge.trans.pp.services.proximity.valueobjects.CountryType;
import com.ge.trans.pp.services.proximity.valueobjects.ProximityRequestType;
import com.ge.trans.pp.services.proximity.valueobjects.ProximityResponseType;
import com.ge.trans.pp.services.proximity.valueobjects.StateType;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: May 24, 2013
 * @Date Modified : May 24, 2013
 * @Modified By :
 * @Contact :
 * @Description : This Class act as proximityservice Webservices and provide the
 *              proximity related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.PROXIMITY_SERVICE)
@Component
public class ProximityResource extends BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ProximityResource.class);

    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(ProximityResource.class);
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    ProximityServiceIntf objProximityServiceIntf;

    /**
     * This Method is used for retrieving cities for given country and state
     * code
     * 
     * @param countryCode
     *            , stateCode
     * @return List of cities
     * @throws RMDServiceException
     */

    @GET
    @Path(OMDConstants.GET_CITIES_FOR_GIVEN_STATE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CityType> getAllPPCitiesForState(@PathParam(OMDConstants.COUNTRYCODE) final String countryCode,
            @PathParam(OMDConstants.STATECODE) final String stateCode) throws RMDServiceException {
        List<CityType> citiesForState = null;
        try {
            MultivaluedMap<String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.COUNTRYCODE, countryCode);
            map.add(OMDConstants.STATECODE, stateCode);
            int[] methodConstants = { RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(map, null, methodConstants, OMDConstants.COUNTRYCODE,
                    OMDConstants.STATECODE)) {
                List<PPCityVO> cityList = objProximityServiceIntf.getCitiesForGivenState(countryCode, stateCode);
                if (RMDCommonUtility.isCollectionNotEmpty(cityList)) {
                    citiesForState = new ArrayList<CityType>();
                    CityType cityType = null;
                    PPCityVO cityVO = null;
                    for (int i = 0; i < cityList.size(); i++) {
                        cityType = new CityType();
                        cityVO = cityList.get(i);
                        if (cityVO.getCityName() != null) {
                            cityType.setCityName(cityVO.getCityName());
                        }
                        if (cityVO.getLatitude() != 0) {
                            cityType.setLatitude((int) cityVO.getLatitude());
                        }
                        if (cityVO.getLongitude() != 0) {
                            cityType.setLongitude((int) cityVO.getLongitude());
                        }
                        citiesForState.add(cityType);
                    }
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception ex) {
            
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return citiesForState;
    }

    /**
     * This Method is used for retrieving states and country list
     * 
     * @param ui
     *            not mandatory
     * @return List of country,states
     * @throws RMDServiceException
     */

    @GET
    @Path(OMDConstants.GET_COUNTRY_STATE_LIST)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CountryType> getAllPPCountryStates(@Context final UriInfo ui) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        String countryName = null;
        List<PPCountryStateVO> countryStateListVO = null;
        List<CountryType> countryStateTypeLst = new ArrayList<CountryType>();
        StateType objState = null;
        PPCountryStateVO countryStateVO = null;
        CountryType countryStateResType = null;
        List<StateType> stateList = null;

        try {

            int[] methodConstants = { RMDCommonConstants.ALPHABETS };
            queryParams = ui.getQueryParameters();
            // extract the queary param and call the service layer
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.COUNTRYCODE)) {
                if (queryParams.containsKey(OMDConstants.COUNTRYCODE)) {
                    countryName = queryParams.getFirst(OMDConstants.COUNTRYCODE);
                }

                countryStateListVO = objProximityServiceIntf.getAllPPCountryStates(countryName);
                // loop the country state list and populate the value to
                // response type

                if (RMDCommonUtility.isCollectionNotEmpty(countryStateListVO)) {

                    for (int i = 0; i < countryStateListVO.size(); i++) {
                        stateList = new ArrayList<StateType>();
                        countryStateResType = new CountryType();
                        countryStateVO = countryStateListVO.get(i);
                        countryStateResType.setCountryCode(countryStateVO.getCountryName());
                        List<String> statelist = countryStateVO.getStateList();
                        if (RMDCommonUtility.isCollectionNotEmpty(statelist)) {

                            Iterator<String> itr = statelist.iterator();
                            while (itr.hasNext()) {
                                objState = new StateType();
                                objState.setStateCode(itr.next());
                                stateList.add(objState);
                            }

                        }
                        countryStateResType.setState(stateList);
                        countryStateTypeLst.add(countryStateResType);
                    }

                }

            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception ex) {
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return countryStateTypeLst;
    }

    /**
     * This Method is used for getting proximity Details
     * 
     * @param cProximityRequestType
     * @return List<ProximityResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_PROXIMITY_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ProximityResponseType> getProximityDetails(final ProximityRequestType proxReq)
            throws RMDServiceException {
        List<ProximityResponseType> proximityList = new ArrayList<ProximityResponseType>();
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar lstMsgTime = null;
        List<PPProximityResponseVO> proximityVOList = null;
        String timeZone = getDefaultTimeZone();
        try {

            if (validateProximityReqObject(proxReq)) {
                if (proxReq.getLatitude() != null && proxReq.getLongitude() != null) {
                    proximityVOList = objProximityServiceIntf.getProximityDetails(
                            BeanUtility.stripXSSCharacters(proxReq.getCustomerId()),
                            BeanUtility.stripXSSCharacters(proxReq.getCityName()),
                            BeanUtility.stripXSSCharacters(proxReq.getStateCode()),
                            BeanUtility.stripXSSCharacters(proxReq.getCountryCode()),
                            BeanUtility.stripXSSCharctersFromInteger(proxReq.getLatitude().intValue()),
                            BeanUtility.stripXSSCharctersFromInteger(proxReq.getLongitude().intValue()),
                            BeanUtility.stripXSSCharctersFromFloat(proxReq.getRangeInMiles()),
                            BeanUtility.stripXSSCharctersfromList(proxReq.getProductNames()));
                } else if (proxReq.getCityName() != null && proxReq.getStateCode() != null
                        && proxReq.getCountryCode() != null) {
                    proximityVOList = objProximityServiceIntf.getProximityDetails(
                            BeanUtility.stripXSSCharacters(proxReq.getCustomerId()),
                            BeanUtility.stripXSSCharacters(proxReq.getCityName()),
                            BeanUtility.stripXSSCharacters(proxReq.getStateCode()),
                            BeanUtility.stripXSSCharacters(proxReq.getCountryCode()), -1, -1,
                            BeanUtility.stripXSSCharctersFromFloat(proxReq.getRangeInMiles()),
                            BeanUtility.stripXSSCharctersfromList(proxReq.getProductNames()));
                }
                if (RMDCommonUtility.isCollectionNotEmpty(proximityVOList)) {

                    ProximityResponseType proximityType = null;
                    PPProximityResponseVO proximityVO = null;
                    for (int i = 0; i < proximityVOList.size(); i++) {
                        proximityType = new ProximityResponseType();
                        proximityVO = proximityVOList.get(i);
                        if (proximityVO.getRoadInitial() != null) {
                            proximityType.setRoadInitialName(proximityVO.getRoadInitial());
                        }
                        if (proximityVO.getRoadNo() != null) {
                            proximityType.setRoadNo(proximityVO.getRoadNo());
                        }
                        if (proximityVO.getModel() != null) {
                            proximityType.setModel(proximityVO.getModel());
                        }
                        if (proximityVO.getDistance() != null) {
                            proximityType.setDistance(proximityVO.getDistance());
                        }
                        if (proximityVO.getDirection() != null) {
                            proximityType.setDirection(proximityVO.getDirection());
                        }
                        if (proximityVO.getLocation() != null) {
                            proximityType.setCityName(proximityVO.getLocation());
                        }
                        if (proximityVO.getStateCode() != null) {
                            proximityType.setStateCode(proximityVO.getStateCode());
                        }
                        if (proximityVO.getMilePost() != null) {
                            proximityType.setMilepost(proximityVO.getMilePost());
                        }
                        if (proximityVO.getRegion() != null) {
                            proximityType.setRegion(proximityVO.getRegion());
                        }
                        if (proximityVO.getSubRegion() != null) {
                            proximityType.setSubRegion(proximityVO.getSubRegion());
                        }

                        proximityType.setLatitude(new Integer((int) proximityVO.getLatitude()));
                        proximityType.setLongitude(new Integer((int) proximityVO.getLongitude()));

                        if (proximityVO.getLastMsgTime() != null) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar = RMDCommonUtility
                                    .getGMTGregorianCalendar(proximityVO.getLastMsgTime());
                            lstMsgTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            proximityType.setLastMsgTime(lstMsgTime);
                        }
                        if (proximityVO.getCustomerId() != null) {
                            proximityType.setCustomerId(proximityVO.getCustomerId());
                        }
                        proximityList.add(proximityType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            
            PPRMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return proximityList;
    }

    /**
     * @param proxReq
     * @return This method is used to validate proximity request Objects
     */
    private static boolean validateProximityReqObject(final ProximityRequestType proxReq) {
        if (proxReq != null) {
            if (!((proxReq.getCityName() != null && proxReq.getStateCode() != null && proxReq.getCountryCode() != null)
                    || (proxReq.getLatitude() != null && proxReq.getLongitude() != null))) {
                return false;
            }
            if (proxReq.getCustomerId() == null || proxReq.getCustomerId().trim().equals(OMDConstants.EMPTY_STRING)
                    || !AppSecUtil.checkAlphaNumeric(BeanUtility.stripXSSCharacters(proxReq.getCustomerId()))) {
                return false;
            }
            if (proxReq.getCityName() != null && (proxReq.getCityName().trim().equals(OMDConstants.EMPTY_STRING)
                    || !AppSecUtil.checkAlphaNumericBrackets(BeanUtility.stripXSSCharacters(proxReq.getCityName())))) {
                return false;
            }
            if (proxReq.getStateCode() != null && (proxReq.getStateCode().trim().equals(OMDConstants.EMPTY_STRING)
                    || !AppSecUtil.checkAlphabets(BeanUtility.stripXSSCharacters(proxReq.getStateCode())))) {
                return false;
            }
            if (proxReq.getCountryCode() != null && (proxReq.getCountryCode().trim().equals(OMDConstants.EMPTY_STRING)
                    || !AppSecUtil.checkAlphabets(BeanUtility.stripXSSCharacters(proxReq.getCountryCode())))) {
                return false;
            }
            if (proxReq.getLatitude() != null && proxReq.getLatitude().intValue() == 0) {
                return false;
            }
            if (proxReq.getLongitude() != null && proxReq.getLongitude().intValue() == 0) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
