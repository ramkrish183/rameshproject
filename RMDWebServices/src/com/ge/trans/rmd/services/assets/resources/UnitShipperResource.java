/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipperResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : March 17, 2016
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.UnitShipperServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.UnitShippersVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ShipUnitVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.AssetNumberResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.UnitShippersRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.UnitShippersResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseTrendResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_UNIT_SHIPPER_SERVICE)
@Component
public class UnitShipperResource extends BaseResource {
    public static final RMDLogger RMDLOGGER = RMDLoggerHelper
            .getLogger(AssetResource.class);
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    @Autowired
    private UnitShipperServiceIntf objUnitShipperServiceIntf;

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :@Context final UriInfo ui
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */
    @GET
    @Path(OMDConstants.GET_UNITS_TO_BE_SHIPPED)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UnitShippersResponseType> getUnitsToBeShipped(
            @Context final UriInfo ui) throws RMDServiceException {
        String assetGrpName = null;
        String roadNumber = null;
        String customerList = null;
        UnitShippersResponseType objShippersType = null;
        List<UnitShippersVO> arlUnitsTobeShippedVO=null;
        List<UnitShippersResponseType> arlUnitsTobeShipped = new ArrayList<UnitShippersResponseType>();
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            UnitShipDetailsVO objUnitShipDetailsVO = new UnitShipDetailsVO();

            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                assetGrpName = queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME);
                if (!RMDCommonUtility.isNullOrEmpty(assetGrpName)) {
                    objUnitShipDetailsVO.setAssetGrpName(assetGrpName);
                }
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                roadNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
                if (!RMDCommonUtility.isNullOrEmpty(roadNumber)) {
                    objUnitShipDetailsVO.setAssetNumber(roadNumber);
                }
            }

            if (queryParams.containsKey(OMDConstants.CUSTOMER_LIST)) {
                customerList = queryParams.getFirst(OMDConstants.CUSTOMER_LIST);
                if (!RMDCommonUtility.isNullOrEmpty(customerList)) {
                    objUnitShipDetailsVO.setCustomerList(customerList);
                }
            }

             arlUnitsTobeShippedVO = objUnitShipperServiceIntf
                    .getUnitsToBeShipped(objUnitShipDetailsVO);
            if (RMDCommonUtility.isCollectionNotEmpty(arlUnitsTobeShippedVO)){
                arlUnitsTobeShipped = new ArrayList<UnitShippersResponseType>(arlUnitsTobeShippedVO.size());
            for (UnitShippersVO objShippersVO : arlUnitsTobeShippedVO) {
                objShippersType = new UnitShippersResponseType();
                objShippersType.setObjId(objShippersVO.getObjId());
                objShippersType.setRnh(objShippersVO.getRnh());
                objShippersType.setRoadNumber(objShippersVO.getRoadNumber());
                objShippersType.setTestTrackDate(objShippersVO.getTestTrackDate());
                arlUnitsTobeShipped.add(objShippersType);
            }
            arlUnitsTobeShippedVO=null;
         }
        
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlUnitsTobeShipped;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :@Context final UriInfo ui
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */
    @GET
    @Path(OMDConstants.GET_LAST_SHIPPED_UNITS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UnitShippersResponseType> getLastShippedUnits(
            @Context final UriInfo ui) throws RMDServiceException {
        String customerList = null;
        String assetGrpName = null;
        String roadNumber = null;
        String isDefaultLoad = null;
        List<UnitShippersResponseType> arlLastShippedUnits = new ArrayList<UnitShippersResponseType>();
        UnitShippersResponseType objShippersType = null;

        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            UnitShipDetailsVO objUnitShipDetailsVO = new UnitShipDetailsVO();
            if (queryParams.containsKey(OMDConstants.CUSTOMER_LIST)) {
                customerList = queryParams.getFirst(OMDConstants.CUSTOMER_LIST);
                if (!RMDCommonUtility.isNullOrEmpty(customerList)) {
                    objUnitShipDetailsVO.setCustomerList(customerList);
                }
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                assetGrpName = queryParams
                        .getFirst(OMDConstants.ASSET_GROUP_NAME);
                if (!RMDCommonUtility.isNullOrEmpty(assetGrpName)) {
                    objUnitShipDetailsVO.setAssetGrpName(assetGrpName);
                }
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                roadNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
                if (!RMDCommonUtility.isNullOrEmpty(roadNumber)) {
                    objUnitShipDetailsVO.setAssetNumber(roadNumber);
                }
            }

            if (queryParams.containsKey(OMDConstants.IS_DEFAULT_LOAD)) {
                isDefaultLoad = queryParams
                        .getFirst(OMDConstants.IS_DEFAULT_LOAD);
                if (!RMDCommonUtility.isNullOrEmpty(isDefaultLoad)) {
                    objUnitShipDetailsVO.setIsDefaultLoad(isDefaultLoad);
                }
            }

            List<UnitShippersVO> arlUnitsTobeShippedVO = objUnitShipperServiceIntf
                    .getLastShippedUnits(objUnitShipDetailsVO);
            if(arlUnitsTobeShippedVO!=null&&arlUnitsTobeShippedVO.size()>0){
                arlLastShippedUnits = new ArrayList<UnitShippersResponseType>(arlUnitsTobeShippedVO.size());
                for (UnitShippersVO objShippersVO : arlUnitsTobeShippedVO) {
                     objShippersType = new UnitShippersResponseType();
                    objShippersType.setObjId(objShippersVO.getObjId());
                    objShippersType.setRnh(objShippersVO.getRnh());
                    objShippersType.setRoadNumber(objShippersVO.getRoadNumber());
                    objShippersType.setTestTrackDate(objShippersVO.getTestTrackDate());
                    objShippersType.setShipDate(objShippersVO.getShipDate());
                    
                    arlLastShippedUnits.add(objShippersType);
                }
                
            }
            
            arlUnitsTobeShippedVO=null;
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }

        return arlLastShippedUnits;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDWebException
     * @Description: This Method is used to update the shipped units.
     * 
     */
    @POST
    @Path(OMDConstants.UPDATE_UNITS_TO_BE_SHIPPED)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String updateUnitsToBeShipped(
            UnitShippersRequestType objUnitShippersRequestType)
            throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<UnitShippersVO> arlUnitShippersVOs = new ArrayList<UnitShippersVO>();
        UnitShippersVO objShippersVO =null;
        try {
            List<UnitShippersRequestType> arlShipperUnits = objUnitShippersRequestType
                    .getArlUnitShipperReqType();
            if(arlShipperUnits!=null&&arlShipperUnits.size()>0){
                arlUnitShippersVOs = new ArrayList<UnitShippersVO>(arlShipperUnits.size());
            }
            for (UnitShippersRequestType objUnitShippersType : arlShipperUnits) {
                 objShippersVO = new UnitShippersVO();
                if (null != objUnitShippersType.getShipDate()) {
                    objShippersVO.setShipDate(RMDCommonUtility
                            .convertXMLGregorianCalenderToString(
                                    objUnitShippersType.getShipDate(),
                                    getDefaultTimeZone()));
                }
                if (null != objUnitShippersType.getTestTrackDate()) {
                    objShippersVO.setTestTrackDate(RMDCommonUtility
                            .convertXMLGregorianCalenderToString(
                                    objUnitShippersType.getTestTrackDate(),
                                    getDefaultTimeZone()));
                }
                if (!RMDCommonUtility.isNullOrEmpty(objUnitShippersType
                        .getObjId())) {
                    objShippersVO.setObjId(objUnitShippersType.getObjId());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.OBJID_NOT_PROVIDED);
                }
                arlUnitShippersVOs.add(objShippersVO);
            }
            arlShipperUnits=null;
            status = objUnitShipperServiceIntf
                    .updateUnitsToBeShipped(arlUnitShippersVOs);
            arlUnitShippersVOs=null;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * This method is used for retrieving Asset details for the parameters
     * passed in UriInfo
     * 
     * @param ui
     * @return List of AssetResponseType
     * @throws RMDServiceException
     * 
     *             Change made in method to retrive list of Model details based
     *             on Customer, Fleet, Model and Config
     */
    @POST
    @Path(OMDConstants.GET_ASSET_NUMBERS_FOR_SHIP_UNITS)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<AssetNumberResponseType> getAssetNumbersForShipUnits(
            final AssetsRequestType objAssetReqType) throws RMDServiceException {

        List<AssetNumberVO> eoaAssetsLst = new ArrayList<AssetNumberVO>();
        AssetNumberResponseType objAssetResponseType = null;
        AssetServiceVO objAssetServiceVO;
        List<AssetNumberResponseType> arlAssetResponseType = new ArrayList<AssetNumberResponseType>();
        AssetNumberVO objAssetServiceVO2=null;
        try {
            objAssetServiceVO = new AssetServiceVO();
            // if any one of the search values are present
            

                if (validateGetAssets(objAssetReqType)) {
                    
                    if (null != objAssetReqType.getAssetGrpName()
                            && !objAssetReqType.getAssetGrpName().isEmpty()) {
                        objAssetServiceVO.setAssetGroupName(objAssetReqType
                                .getAssetGrpName());
                    } else {
                        objAssetServiceVO
                                .setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
                    }
                    
                    if (null != objAssetReqType.getModelId()
                            && !objAssetReqType.getModelId().isEmpty()) {
                        objAssetServiceVO.setStrModelId(objAssetReqType
                                .getModelId());
                    } else {
                        objAssetServiceVO.setModel(RMDCommonConstants.EMPTY_STRING);
                    }
                    
                    if (null != objAssetReqType.getFleetId()
                            && !objAssetReqType.getFleetId().isEmpty()) {
                        objAssetServiceVO.setStrFleetId(objAssetReqType
                                .getFleetId());
                    } else {
                        objAssetServiceVO.setFleet(RMDCommonConstants.EMPTY_STRING);
                    }

                    if (null != objAssetReqType.getAssetNumberLike()
                            && !objAssetReqType.getAssetNumberLike().isEmpty()) {
                        objAssetServiceVO.setAssetNumberLike(objAssetReqType
                                .getAssetNumberLike());
                    } else {
                        objAssetServiceVO
                                .setAssetNumberLike(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getCustomerId()
                            && !objAssetReqType.getCustomerId().isEmpty()) {
                        
                        if (RMDCommonConstants.ALL_CUSTOMER.equalsIgnoreCase(objAssetReqType.getCustomerId())) {                        
                            objAssetServiceVO.setCustomerID(RMDCommonConstants.EMPTY_STRING);
                        }else{
                            objAssetServiceVO.setCustomerID(objAssetReqType
                                .getCustomerId());                          
                        }
                        
                    } else {
                        objAssetServiceVO
                                .setCustomerID(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getAssetNumber()
                            && !objAssetReqType.getAssetNumber().isEmpty()) {
                        objAssetServiceVO.setStrAssetNumber(objAssetReqType
                                .getAssetNumber());
                    } else {
                        objAssetServiceVO
                                .setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objAssetReqType.getAssetFrom()) {
                        objAssetServiceVO.setAssetFrom(objAssetReqType
                                .getAssetFrom());
                    }
                    if (null != objAssetReqType.getAssetTo()) {
                        objAssetServiceVO.setAssetTo(objAssetReqType
                                .getAssetTo());
                    }
                    if (null != objAssetReqType.getProdAssetMap()
                            && objAssetReqType.getProdAssetMap().size() > 0) {
                        CMBeanUtility.copyCustomerAssetToServiceVO(
                                objAssetReqType.getProdAssetMap(), objAssetServiceVO);
                    }
                    
                    
                eoaAssetsLst = objUnitShipperServiceIntf
                    .getAssetNumbersForShipUnits(objAssetServiceVO);
                if(eoaAssetsLst!=null&&eoaAssetsLst.size()>0){
                    arlAssetResponseType=new ArrayList<AssetNumberResponseType>(eoaAssetsLst.size());
                    for (Iterator iterator = eoaAssetsLst.iterator(); iterator
                            .hasNext();) {
                         objAssetResponseType = new AssetNumberResponseType();
                          objAssetServiceVO2 = (AssetNumberVO) iterator
                                .next();
                          //objAssetResponseType.setAssetNumber(objAssetServiceVO2.getStrAssetNumber());
                          objAssetResponseType.setAssetNumber(EsapiUtil.stripXSSCharacters((String)
                       			(objAssetServiceVO2.getStrAssetNumber())));
                         //objAssetResponseType.setCustomerID(objAssetServiceVO2.getCustomerID());
                          objAssetResponseType.setCustomerID(EsapiUtil.stripXSSCharacters((String)
                         			(objAssetServiceVO2.getCustomerID())));
                         arlAssetResponseType.add(objAssetResponseType);

                    }
                }
                
                    
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.INVALID_VALUE);
                }
            
                 Collections.sort(arlAssetResponseType, new Comparator() {
                      public int compare(Object o1, Object o2) {
                          AssetNumberResponseType a1 =(AssetNumberResponseType) o1;
                          AssetNumberResponseType a2 =(AssetNumberResponseType) o2;
                          if(a1.getAssetNumber().length()<a2.getAssetNumber().length()){
                            return -1;
                        }else if(a1.getAssetNumber().length()>a2.getAssetNumber().length()){
                            return 1;
                        }
                        return a1.getAssetNumber().compareTo(a2.getAssetNumber());
                    }
                 });
         eoaAssetsLst=null;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return arlAssetResponseType;
    }
    
    /**
     * This method is used to validate the data used for getting assets
     * AssetsRequestType
     * 
     * @return boolean
     */
    public static boolean validateGetAssets(
            final AssetsRequestType objAssetsRequestType) {

        if (null != objAssetsRequestType.getAssetNumber()
                && !objAssetsRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetsRequestType
                    .getAssetNumber())) {
                return false;
            }
        }
        
        
        if (null != objAssetsRequestType.getAssetNumberLike()
                && !objAssetsRequestType.getAssetNumberLike().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetsRequestType
                    .getAssetNumberLike())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getCustomerId()
                && !objAssetsRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil
                    .checkAlphaNumericComma(objAssetsRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getCustomer()
                && !objAssetsRequestType.getCustomer().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getCustomer())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getAssetGrpName()
                && !objAssetsRequestType.getAssetGrpName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType
                    .getAssetGrpName())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getModel()
                && !objAssetsRequestType.getModel().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getModel())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getConfig()
                && !objAssetsRequestType.getConfig().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getConfig())) {
                return false;
            }
        }

        if (null != objAssetsRequestType.getFleet()
                && !objAssetsRequestType.getFleet().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objAssetsRequestType.getFleet())) {
                return false;
            }
        }
        return true;
    }
    /**
     * @Author :
     * @return :String
     * @param : caseObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     * 
     */
    @GET
    @Path(OMDConstants.GET_SHIP_UNIT_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UnitShippersResponseType> getShipUnitDetails(@Context UriInfo ui)
            throws RMDServiceException {
    	List<UnitShippersResponseType> responseList = new ArrayList<UnitShippersResponseType>();
		List<ShipUnitVO> valueList = new ArrayList<ShipUnitVO>();
		UnitShippersResponseType objUnitShippersResponseType = null;
		try {
			LOG.debug("*****getShipUnitDetails WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objUnitShipperServiceIntf.getShipUnitDetails();
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<UnitShippersResponseType>(
						valueList.size());
			}
			for (ShipUnitVO objshipUnitVO : valueList) {
				objUnitShippersResponseType = new UnitShippersResponseType();
				objUnitShippersResponseType.setRnh(objshipUnitVO
						.getRoadNumberHeader());
				objUnitShippersResponseType.setRoadNumber(objshipUnitVO.getRoadNumber());
				objUnitShippersResponseType.setShipDate(objshipUnitVO.getShipDate());
				objUnitShippersResponseType.setCode(objshipUnitVO.getCode());
				objUnitShippersResponseType.setComment(objshipUnitVO.getComment());
				responseList.add(objUnitShippersResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			LOG.error("Exception occuered in getShipUnitDetails() method of UnitShipperResource"
					+ e);
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}

		return responseList;
    }
    /**
     * @Author :
     * @return :String
     * @param : caseObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     * 
     */
    @GET
    @Path(OMDConstants.GET_INFANCY_FAILURE_UNITS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UnitShippersResponseType> getInfancyFailureDetails(@Context UriInfo ui)
            throws RMDServiceException {
    	List<UnitShippersResponseType> responseList = new ArrayList<UnitShippersResponseType>();
		List<ShipUnitVO> valueList = new ArrayList<ShipUnitVO>();
		UnitShippersResponseType objUnitShippersResponseType = null;
		try {
			LOG.debug("*****getInfancyFailureDetails WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objUnitShipperServiceIntf.getInfancyFailureDetails();
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<UnitShippersResponseType>(
						valueList.size());
			}
			for (ShipUnitVO objshipUnitVO : valueList) {
				objUnitShippersResponseType = new UnitShippersResponseType();
				objUnitShippersResponseType.setRnh(objshipUnitVO
						.getRoadNumberHeader());
				objUnitShippersResponseType.setRoadNumber(objshipUnitVO.getRoadNumber());
				objUnitShippersResponseType.setShipDate(objshipUnitVO.getShipDate());
				objUnitShippersResponseType.setCode(objshipUnitVO.getCode());
				objUnitShippersResponseType.setComment(objshipUnitVO.getComment());
				responseList.add(objUnitShippersResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			LOG.error("Exception occuered in getInfancyFailureDetails() method of UnitShipperResource"
					+ e);
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}

		return responseList;
    }
}
