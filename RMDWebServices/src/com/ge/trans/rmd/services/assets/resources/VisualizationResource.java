package com.ge.trans.rmd.services.assets.resources;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : VisualizationResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
import java.util.ArrayList;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.VisualizationServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationParmDetailsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.GraphDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.MPParmDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.RxVisualizationPlotInfoType;
import com.ge.trans.rmd.services.assets.valueobjects.VirtualParametersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VisualizationDetailsRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VisualizationDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VisualizationEventDataRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VisualizationEventDataResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VisualizationParmDetailsType;
import com.ge.trans.rmd.utilities.AppSecUtil;
/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as visualizationservice Webservices and provide
 *              the visualization related funtionalities
 * @History :
 ******************************************************************************/

@Path(OMDConstants.VISUALIZATION_SERVICE)
@Component
public class VisualizationResource {
    public static final RMDLogger RMDLOGGER = RMDLoggerHelper.getLogger(VisualizationResource.class);
    @Autowired
    private VisualizationServiceIntf objVisualizationServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * This method is used for fetching graphNames of visualization screen
     * 
     * @param @Context
     *            UriInfo ui
     * @return List<GraphDetailsResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_VISUALIZATION_GRAPH_NAMES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<GraphDetailsResponseType> getVisualizationGraphNames(@Context UriInfo ui) throws RMDServiceException {
        VisualizationGraphDetailsVO objGraphDetailsVO;
        GraphDetailsResponseType objGraphDetailsResponseType;
        List<GraphDetailsResponseType> arlDetailsResponseType = new ArrayList<GraphDetailsResponseType>();
        List<VisualizationGraphDetailsVO> arlGraphDetailsVO = new ArrayList<VisualizationGraphDetailsVO>();
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            objGraphDetailsVO = new VisualizationGraphDetailsVO();
            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_UNDERSCORE };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.CONTROLLER_CONFIG,
                    OMDConstants.SOURCE_TYPE, OMDConstants.DATASOURCE)) {
                if (queryParams.containsKey(OMDConstants.CONTROLLER_CONFIG)) {
                    if (null != queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG)
                            && !queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG).isEmpty()) {
                        objGraphDetailsVO.setControllercfg(queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG));
                    }
                }
                if (queryParams.containsKey(OMDConstants.SOURCE_TYPE)) {

                    objGraphDetailsVO.setSourceType(queryParams.getFirst(OMDConstants.SOURCE_TYPE));
                }
                if (queryParams.containsKey(OMDConstants.DATASOURCE)) {
                    objGraphDetailsVO.setSource(queryParams.getFirst(OMDConstants.DATASOURCE));
                }
                arlGraphDetailsVO = objVisualizationServiceIntf.getGraphDetails(objGraphDetailsVO);
                if (null != arlGraphDetailsVO && !arlGraphDetailsVO.isEmpty()) {
                    for (Iterator iterator = arlGraphDetailsVO.iterator(); iterator.hasNext();) {
                        objGraphDetailsResponseType = new GraphDetailsResponseType();
                        VisualizationGraphDetailsVO objVisualizationGraphDetailsVO = (VisualizationGraphDetailsVO) iterator
                                .next();
                        objGraphDetailsResponseType.setGraphMPDesc(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getGraphMPDesc())));
                        objGraphDetailsResponseType.setGraphMPNum(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getGraphMPNum())));
                        objGraphDetailsResponseType.setGraphName(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getGraphName())));
                        objGraphDetailsResponseType.setSortOrder(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getSortOrder())));
                        objGraphDetailsResponseType.setSourceType(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getSourceType())));
                        objGraphDetailsResponseType.setControllerCfg(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getControllercfg())));
                        objGraphDetailsResponseType.setStackOrder(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationGraphDetailsVO.getStackOrder())));
                        arlDetailsResponseType.add(objGraphDetailsResponseType);

                    }

                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return arlDetailsResponseType;

    }

    /**
     * This method is used for fetching Visualization fault details based on
     * source,source,assetNumer etc
     * 
     * @param @Context
     *            UriInfo ui
     * @return List<GraphDetailsResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_VISUALIZATION_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VisualizationDetailsResponseType> getVisualizationDetails(
            VisualizationDetailsRequestType objDetailsRequestType) throws RMDServiceException {
        VisualizationDetailsRequestVO objVisualizationRequestVO = null;
        List<VisualizationDetailsResponseType> arlDetailsResponseType = new ArrayList<VisualizationDetailsResponseType>();
        VisualizationDetailsResponseType objVisualizationDetailsResponseType = null;
        VisualizationParmDetailsType objVisualizationParmDetailsType = null;
        try {

            if (null != objDetailsRequestType) {
                objVisualizationRequestVO = new VisualizationDetailsRequestVO();
                if (null != objDetailsRequestType.getAssetGroupName()
                        && !objDetailsRequestType.getAssetGroupName().isEmpty()) {
                    objVisualizationRequestVO.setAssetGroupName(objDetailsRequestType.getAssetGroupName());
                } else {
                    throw new OMDInValidInputException(
                            BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objDetailsRequestType.getAssetNumber()
                        && !objDetailsRequestType.getAssetNumber().isEmpty()) {
                    objVisualizationRequestVO.setAssetNumber(objDetailsRequestType.getAssetNumber());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objDetailsRequestType.getControllerCfg()
                        && !objDetailsRequestType.getControllerCfg().isEmpty()) {
                    objVisualizationRequestVO.setControllerCfg(objDetailsRequestType.getControllerCfg());
                } else {
                    throw new OMDInValidInputException(
                            BeanUtility.getErrorCode(OMDConstants.CONTROLLER_CONFIG_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.CONTROLLER_CONFIG_NOT_PROVIDED),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objDetailsRequestType.getCustomerId() && !objDetailsRequestType.getCustomerId().isEmpty()) {
                    objVisualizationRequestVO.setCustomerId(objDetailsRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objDetailsRequestType.getSource() && !objDetailsRequestType.getSource().isEmpty()) {
                    objVisualizationRequestVO.setSource(objDetailsRequestType.getSource());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.SOURCE_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.SOURCE_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objDetailsRequestType.getSourceType() && !objDetailsRequestType.getSourceType().isEmpty()) {
                    objVisualizationRequestVO.setSourceType(objDetailsRequestType.getSourceType());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.SOURCE_TYPE_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.SOURCE_TYPE_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objDetailsRequestType.getMpNumbers() && !objDetailsRequestType.getMpNumbers().isEmpty()) {
                    objVisualizationRequestVO.setMpNumbers(objDetailsRequestType.getMpNumbers());
                } else if (null == objDetailsRequestType.getVirtualParameters()
                        && objDetailsRequestType.getVirtualParameters().isEmpty()) {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.MP_NUMBERS_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.MP_NUMBERS_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objDetailsRequestType.getAssetObjid() && !objDetailsRequestType.getAssetObjid().isEmpty()) {
                    objVisualizationRequestVO.setAssetObjid(objDetailsRequestType.getAssetObjid());
                }

                if (null != objDetailsRequestType.getVirtualParameters()
                        && !objDetailsRequestType.getVirtualParameters().isEmpty()) {
                    objVisualizationRequestVO.setVirtualParameters(objDetailsRequestType.getVirtualParameters());
                }

                if (null != objDetailsRequestType.getAnomParms() && !objDetailsRequestType.getAnomParms().isEmpty()) {
                    objVisualizationRequestVO.setAnomParameters(objDetailsRequestType.getAnomParms());
                }

                objVisualizationRequestVO.setControllerSrc(objDetailsRequestType.getControllerType());
                objVisualizationRequestVO.setFromDate(objDetailsRequestType.getFromDate());
                objVisualizationRequestVO.setToDate(objDetailsRequestType.getToDate());
                objVisualizationRequestVO.setFamily(objDetailsRequestType.getFamily());
                objVisualizationRequestVO.setFaultCode(objDetailsRequestType.getFaultCode());
                objVisualizationRequestVO.setLocoState(objDetailsRequestType.getLocoState());
                objVisualizationRequestVO.setAmbientTempOperator(objDetailsRequestType.getAmbientTempOperator());
                objVisualizationRequestVO.setAmbientTempValue1(objDetailsRequestType.getAmbientTempValue1());
                objVisualizationRequestVO.setAmbientTempValue2(objDetailsRequestType.getAmbientTempValue2());
                objVisualizationRequestVO.setEngineGHPOperator(objDetailsRequestType.getEngineGHPOperator());
                objVisualizationRequestVO.setEngineGHPValue1(objDetailsRequestType.getEngineGHPValue1());
                objVisualizationRequestVO.setEngineGHPValue2(objDetailsRequestType.getEngineGHPValue2());
                objVisualizationRequestVO.setNotchOperator(objDetailsRequestType.getNotchOperator());
                objVisualizationRequestVO.setNotchValue(objDetailsRequestType.getNotchValue());
                objVisualizationRequestVO.setEngineSpeedOperator(objDetailsRequestType.getEngineSpeedOperator());
                objVisualizationRequestVO.setEngineSpeedValue1(objDetailsRequestType.getEngineSpeedValue1());
                objVisualizationRequestVO.setEngineSpeedValue2(objDetailsRequestType.getEngineSpeedValue2());
                objVisualizationRequestVO.setSourceTypeCd(objDetailsRequestType.getSourceTypeCd());
                // added for Anom changes
                objVisualizationRequestVO.setOilInletOp(objDetailsRequestType.getOilInletOp());
                objVisualizationRequestVO.setOilInletValue1(objDetailsRequestType.getOilInletValue1());
                objVisualizationRequestVO.setOilInletValue2(objDetailsRequestType.getOilInletValue2());
                objVisualizationRequestVO.setBarometricPressOp(objDetailsRequestType.getBarometricPressOp());
                objVisualizationRequestVO.setBarometricPressValue1(objDetailsRequestType.getBarometricPressValue1());
                objVisualizationRequestVO.setBarometricPressValue2(objDetailsRequestType.getBarometricPressValue2());
                objVisualizationRequestVO.setHpAvailableOp(objDetailsRequestType.getHpAvailableOp());
                objVisualizationRequestVO.setHpAvailableValue1(objDetailsRequestType.getHpAvailableValue1());
                objVisualizationRequestVO.setHpAvailableValue2(objDetailsRequestType.getHpAvailableValue2());
                objVisualizationRequestVO.setNoOfDays(objDetailsRequestType.getNoOfDays());

                List<VisualizationDetailsVO> arlVisualizationDetailsVO = objVisualizationServiceIntf
                        .getVisualizationDetails(objVisualizationRequestVO);
                for (Iterator iterator = arlVisualizationDetailsVO.iterator(); iterator.hasNext();) {
                    VisualizationDetailsVO visualizationDetailsVO = (VisualizationDetailsVO) iterator.next();
                    objVisualizationDetailsResponseType = new VisualizationDetailsResponseType();
                    objVisualizationDetailsResponseType.setStackOrder(visualizationDetailsVO.getStackOrder());
                    objVisualizationDetailsResponseType.setParmDescription(visualizationDetailsVO.getParmDescription());
                    objVisualizationDetailsResponseType.setAsset(visualizationDetailsVO.getAsset());
                    objVisualizationDetailsResponseType.setParmNumber(visualizationDetailsVO.getParmNumber());
                    objVisualizationDetailsResponseType.setDisplayName(visualizationDetailsVO.getDisplayName());
                    for (Iterator iterator2 = visualizationDetailsVO.getArlParmdetails().iterator(); iterator2
                            .hasNext();) {
                        VisualizationParmDetailsVO visualizationParmDetailsVO = (VisualizationParmDetailsVO) iterator2
                                .next();
                        objVisualizationParmDetailsType = new VisualizationParmDetailsType();
                        objVisualizationParmDetailsType.setOccurTime(visualizationParmDetailsVO.getOccurTime());
                        objVisualizationParmDetailsType.setParmValue(visualizationParmDetailsVO.getParmValue());
                        objVisualizationDetailsResponseType.getLstParmDetails().add(objVisualizationParmDetailsType);
                    }
                    arlDetailsResponseType.add(objVisualizationDetailsResponseType);
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return arlDetailsResponseType;

    }

    /**
     * This method is used to validate the data used for getting Visualization
     * screen VisualizationDetailsRequestType
     * 
     * @return boolean
     */

    public static boolean validateVisualizationDetails(final VisualizationDetailsRequestType objDetailsRequestType) {

        if (null != objDetailsRequestType.getAssetNumber() && !objDetailsRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objDetailsRequestType.getAssetNumber())) {
                return false;
            }
        }

        if (null != objDetailsRequestType.getCustomerId() && !objDetailsRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objDetailsRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objDetailsRequestType.getAssetGroupName() && !objDetailsRequestType.getAssetGroupName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objDetailsRequestType.getAssetGroupName())) {
                return false;
            }
        }
        if (null != objDetailsRequestType.getControllerCfg() && !objDetailsRequestType.getControllerCfg().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objDetailsRequestType.getControllerCfg())) {
                return false;
            }
        }

        if (null != objDetailsRequestType.getSource() && !objDetailsRequestType.getSource().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(objDetailsRequestType.getSource())) {
                return false;
            }
        }

        if (null != objDetailsRequestType.getSourceType() && !objDetailsRequestType.getSourceType().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(objDetailsRequestType.getSourceType())) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used for fetching mpNumbers of visualization screen
     * 
     * @param @Context
     *            UriInfo ui
     * @return List<GraphDetailsResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_VISUALIZATION_PARM_NUMBERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<MPParmDetailsResponseType> getParmNumbers(@Context UriInfo ui) throws RMDServiceException {
        MPParmDetailsResponseType objMPParmDetailsResponseType;
        List<MPParmDetailsResponseType> arlParmDetailsResponseType = new ArrayList<MPParmDetailsResponseType>();
        List<VisualizationMPParmNumVO> arlVisualizationMPParmNumVO = new ArrayList<VisualizationMPParmNumVO>();
        String sourceType = RMDCommonConstants.EMPTY_STRING;
        String source = RMDCommonConstants.EMPTY_STRING;
        String cntrlCfg = RMDCommonConstants.EMPTY_STRING;
        String language = RMDCommonConstants.EMPTY_STRING;
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_UNDERSCORE };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.CONTROLLER_CONFIG,
                    OMDConstants.SOURCE_TYPE, OMDConstants.DATASOURCE)) {
                if (queryParams.containsKey(OMDConstants.CONTROLLER_CONFIG)) {
                    if (null != queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG)
                            && !queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG).isEmpty()) {
                        cntrlCfg = queryParams.getFirst(OMDConstants.CONTROLLER_CONFIG);
                    }
                }
                if (queryParams.containsKey(OMDConstants.SOURCE_TYPE)) {

                    sourceType = queryParams.getFirst(OMDConstants.SOURCE_TYPE);
                }
                if (queryParams.containsKey(OMDConstants.DATASOURCE)) {
                    source = queryParams.getFirst(OMDConstants.DATASOURCE);
                }
                arlVisualizationMPParmNumVO = objVisualizationServiceIntf.getParmNumbers(source, sourceType, language);
                if (null != arlVisualizationMPParmNumVO && !arlVisualizationMPParmNumVO.isEmpty()) {
                    for (Iterator iterator = arlVisualizationMPParmNumVO.iterator(); iterator.hasNext();) {
                        objMPParmDetailsResponseType = new MPParmDetailsResponseType();
                        VisualizationMPParmNumVO objVisualizationMPParmNumVO = (VisualizationMPParmNumVO) iterator
                                .next();
                       objMPParmDetailsResponseType.setColumnName(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationMPParmNumVO.getColumnName())));
                        objMPParmDetailsResponseType.setControllerCfg(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationMPParmNumVO.getControllerCfg())));
                        objMPParmDetailsResponseType
                                .setParmDescription(EsapiUtil.stripXSSCharacters((String)
                            			(objVisualizationMPParmNumVO.getParmDescription())));
                        objMPParmDetailsResponseType.setParmNumber(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationMPParmNumVO.getParmNumber())));
                        objMPParmDetailsResponseType
                                .setParmDescriptionName(EsapiUtil.stripXSSCharacters((String)
                            			(objVisualizationMPParmNumVO.getParmDescriptionName())));
                        objMPParmDetailsResponseType.setParmNumColName(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationMPParmNumVO.getParmNumColName())));
                        objMPParmDetailsResponseType.setControllerType(EsapiUtil.stripXSSCharacters((String)
                    			(objVisualizationMPParmNumVO.getControllerType())));
                        arlParmDetailsResponseType.add(objMPParmDetailsResponseType);

                    }

                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return arlParmDetailsResponseType;

    }

    /**
     * This method is used for fetching event data of visualization screen
     * 
     * @param VisualizationEventDataRequestType
     * @return List<VisualizationEventDataResponseType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_VISUALIZATION_EVENT_DATA)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VisualizationEventDataResponseType> getAssetVisualizationEventData(
            VisualizationEventDataRequestType objEventDataRequestType) throws RMDServiceException {
        VisualizationEventDataVO objVisualizationEventDataVO = null;
        List<VisualizationEventDataResponseType> arlEventDetailsResponseType = new ArrayList<VisualizationEventDataResponseType>();
        VisualizationEventDataResponseType objVisualizationEventDataResponseType = null;
        try {

            if (null != objEventDataRequestType) {
                objVisualizationEventDataVO = new VisualizationEventDataVO();
                if (null != objEventDataRequestType.getAssetGroupName()
                        && !objEventDataRequestType.getAssetGroupName().isEmpty()) {
                    objVisualizationEventDataVO.setAssetGroupName(objEventDataRequestType.getAssetGroupName());
                } else {
                    throw new OMDInValidInputException(
                            BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objEventDataRequestType.getAssetNumber()
                        && !objEventDataRequestType.getAssetNumber().isEmpty()) {
                    objVisualizationEventDataVO.setAssetNumber(objEventDataRequestType.getAssetNumber());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objEventDataRequestType.getCustomerId()
                        && !objEventDataRequestType.getCustomerId().isEmpty()) {
                    objVisualizationEventDataVO.setCustomerId(objEventDataRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                if (null != objEventDataRequestType.getControllerCfg()
                        && !objEventDataRequestType.getControllerCfg().isEmpty()) {
                    objVisualizationEventDataVO.setControllerCfg(objEventDataRequestType.getControllerCfg());
                } else {
                    throw new OMDInValidInputException(
                            BeanUtility.getErrorCode(OMDConstants.CONTROLLER_CONFIG_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.CONTROLLER_CONFIG_NOT_PROVIDED),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }

                objVisualizationEventDataVO.setFromDate(objEventDataRequestType.getFromDate());
                objVisualizationEventDataVO.setToDate(objEventDataRequestType.getToDate());
                objVisualizationEventDataVO.setNoOfDays(objEventDataRequestType.getNoOfDays());

                List<VisualizationEventDataVO> arlVisualizationDataVO = objVisualizationServiceIntf
                        .getAssetVisualizationEventData(objVisualizationEventDataVO);
                for (Iterator iterator = arlVisualizationDataVO.iterator(); iterator.hasNext();) {
                    VisualizationEventDataVO objVisualizationEventVO = (VisualizationEventDataVO) iterator.next();
                    objVisualizationEventDataResponseType = new VisualizationEventDataResponseType();
                    objVisualizationEventDataResponseType.setEventDetails(objVisualizationEventVO.getEventDetails());

                    objVisualizationEventDataResponseType.setEventEndDate(objVisualizationEventVO.getEventEndDate());

                    objVisualizationEventDataResponseType
                            .setEventOccurDate(objVisualizationEventVO.getEventOccurDate());

                    objVisualizationEventDataResponseType.setEventType(objVisualizationEventVO.getEventType());

                    objVisualizationEventDataResponseType
                            .setStrEventOccurDt(objVisualizationEventVO.getStrEvntOccurDt());

                    objVisualizationEventDataResponseType.setStrEventEndDt(objVisualizationEventVO.getStrEvntEndDt());

                    objVisualizationEventDataResponseType.setEventSummary(objVisualizationEventVO.getEventSummary());

                    arlEventDetailsResponseType.add(objVisualizationEventDataResponseType);
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return arlEventDetailsResponseType;

    }

    /**
     * This method is used for fetching virtual Parameters for visualization
     * screen
     * 
     * @param @Context
     *            UriInfo ui
     * @return List<VirtualParametersResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_VIZ_VIRTUAL_PARM_NUMBERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VirtualParametersResponseType> getVizVirtualParameters(@Context UriInfo ui) throws RMDServiceException {
        VirtualParametersResponseType objVirtualParametersResponseType;
        List<VirtualParametersResponseType> arlVirtualParametersResponseType = new ArrayList<VirtualParametersResponseType>();
        List<VirtualParametersVO> arlVirtualParametersVO = new ArrayList<VirtualParametersVO>();
        String database = RMDCommonConstants.EMPTY_STRING;
        String language = RMDCommonConstants.EMPTY_STRING;
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

            if (queryParams.containsKey(OMDConstants.DATABASE)) {

                database = queryParams.getFirst(OMDConstants.DATABASE);
            }

            arlVirtualParametersVO = objVisualizationServiceIntf.getVizVirtualParameters(database, language);
            if (null != arlVirtualParametersVO && !arlVirtualParametersVO.isEmpty()) {
                for (Iterator iterator = arlVirtualParametersVO.iterator(); iterator.hasNext();) {
                    objVirtualParametersResponseType = new VirtualParametersResponseType();
                    VirtualParametersVO objVirtualParametersVO = (VirtualParametersVO) iterator.next();
                    objVirtualParametersResponseType.setVirtualId(objVirtualParametersVO.getVirtualId());
                    objVirtualParametersResponseType.setVirtualName(objVirtualParametersVO.getVirtualName());
                    objVirtualParametersResponseType.setFamily(objVirtualParametersVO.getFamily());
                    arlVirtualParametersResponseType.add(objVirtualParametersResponseType);

                }

            }

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return arlVirtualParametersResponseType;

    }

    /**
     * This method is used for fetching asset family for visualization screen
     * 
     * @param String
     *            model
     * @return String
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_VIZ_VIRTUAL_ASSET_FAMILY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getVizVirtualAssetFamily(@PathParam(OMDConstants.MODEL) final String model)
            throws RMDServiceException {
        String family = RMDCommonConstants.EMPTY_STRING;
        String language = RMDCommonConstants.EMPTY_STRING;
        try {
            family = objVisualizationServiceIntf.getVizVirtualAssetFamily(model, language);

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return family;

    }
    
    
    @POST
    @Path(OMDConstants.GET_RX_VISUALIZATION_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VisualizationDetailsResponseType> getRxVisualizationDetails(
            VisualizationDetailsRequestType objDetailsRequestType) throws RMDServiceException {
        VisualizationDetailsRequestVO objVisualizationRequestVO = null;
        List<VisualizationDetailsResponseType> arlDetailsResponseType = new ArrayList<VisualizationDetailsResponseType>();
        VisualizationDetailsResponseType objVisualizationDetailsResponseType = null;
        VisualizationParmDetailsType objVisualizationParmDetailsType = null;
        try {

            if (null != objDetailsRequestType) {
                objVisualizationRequestVO = new VisualizationDetailsRequestVO();
                if (null != objDetailsRequestType.getAssetGroupName()
                        && !objDetailsRequestType.getAssetGroupName().isEmpty()) {
                    objVisualizationRequestVO.setAssetGroupName(objDetailsRequestType.getAssetGroupName());
                } else {
                    throw new OMDInValidInputException(
                            BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objDetailsRequestType.getAssetNumber()
                        && !objDetailsRequestType.getAssetNumber().isEmpty()) {
                    objVisualizationRequestVO.setAssetNumber(objDetailsRequestType.getAssetNumber());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.ASSET_NUMBER_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                
                if (null != objDetailsRequestType.getCustomerId() && !objDetailsRequestType.getCustomerId().isEmpty()) {
                    objVisualizationRequestVO.setCustomerId(objDetailsRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.CUSTOMER_ID_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (null != objDetailsRequestType.getRxObjId() && !objDetailsRequestType.getRxObjId().isEmpty()) {
                    objVisualizationRequestVO.setRxObjid(objDetailsRequestType.getRxObjId());
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.RX_OBJID_NOT_PROVIDED),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.RX_OBJID_NOT_PROVIDED), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                

                List<VisualizationDetailsVO> arlVisualizationDetailsVO = objVisualizationServiceIntf
                        .getRxVisualizationDetails(objVisualizationRequestVO);
                for (Iterator iterator = arlVisualizationDetailsVO.iterator(); iterator.hasNext();) {
                    VisualizationDetailsVO visualizationDetailsVO = (VisualizationDetailsVO) iterator.next();
                    objVisualizationDetailsResponseType = new VisualizationDetailsResponseType();
                    objVisualizationDetailsResponseType.setParmDescription(visualizationDetailsVO.getParmDescription());
                    objVisualizationDetailsResponseType.setAsset(visualizationDetailsVO.getAsset());
                    objVisualizationDetailsResponseType.setParmNumber(visualizationDetailsVO.getParmNumber());
                    objVisualizationDetailsResponseType.setDisplayName(visualizationDetailsVO.getDisplayName());
                    objVisualizationDetailsResponseType.setLeftDependentAxis(visualizationDetailsVO.getLeftDependentAxis());
                    objVisualizationDetailsResponseType.setRightDependentAxis(visualizationDetailsVO.getRightDependentAxis());
                    objVisualizationDetailsResponseType.setIndependentAxis(visualizationDetailsVO.getIndependentAxis());
                    objVisualizationDetailsResponseType.setAxisSide(visualizationDetailsVO.getAxisSide());
                    objVisualizationDetailsResponseType.setTitle(visualizationDetailsVO.getTitle());
                    for (Iterator iterator2 = visualizationDetailsVO.getArlParmdetails().iterator(); iterator2
                            .hasNext();) {
                        VisualizationParmDetailsVO visualizationParmDetailsVO = (VisualizationParmDetailsVO) iterator2
                                .next();
                        objVisualizationParmDetailsType = new VisualizationParmDetailsType();
                        objVisualizationParmDetailsType.setOccurTime(visualizationParmDetailsVO.getOccurTime());
                        objVisualizationParmDetailsType.setParmValue(visualizationParmDetailsVO.getParmValue());
                        objVisualizationDetailsResponseType.getLstParmDetails().add(objVisualizationParmDetailsType);
                    }
                    arlDetailsResponseType.add(objVisualizationDetailsResponseType);
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return arlDetailsResponseType;

    }
    
    /**
	 * This method is used for fetching graphDetails of visualization screen
	 * 
	 * @param VisualizationGraphDetailsVO
	 * @return List<VisualizationGraphDetailsVO>
	 * @throws RMDDAOException
	 */
	
    @GET
    @Path(OMDConstants.GET_RX_VISUALIZATION_PLOT_INFO)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RxVisualizationPlotInfoType getVizPlotInformations(@PathParam(OMDConstants.RX_OBJID) final String rxObjid)
            throws RMDServiceException {
    	RxVisualizationPlotInfoVO objPlotInfoVO=null;
    	RxVisualizationPlotInfoType objRxVisualizationPlotInfoType=null;
        try {
        	objPlotInfoVO = objVisualizationServiceIntf.getVizPlotInformations(rxObjid);
        	if(null!=objPlotInfoVO){
        		objRxVisualizationPlotInfoType=new RxVisualizationPlotInfoType();
        		objRxVisualizationPlotInfoType.setTitle(objPlotInfoVO.getTitle());
        		objRxVisualizationPlotInfoType.setRightDependentAxisLabel(objPlotInfoVO.getRightDependentAxisLabel());
        		objRxVisualizationPlotInfoType.setLeftDependentAxisLabel(objPlotInfoVO.getLeftDependentAxisLabel());
        		objRxVisualizationPlotInfoType.setIndependentAxisLabel(objPlotInfoVO.getIndependentAxisLabel());
        		objRxVisualizationPlotInfoType.setDataset1Axis(objPlotInfoVO.getDataset1Axis());
        		objRxVisualizationPlotInfoType.setDataset1Label(objPlotInfoVO.getDataset1Label());
        		objRxVisualizationPlotInfoType.setDataset1ParameterName(objPlotInfoVO.getDataset1ParameterName());
        		objRxVisualizationPlotInfoType.setDataset2Axis(objPlotInfoVO.getDataset2Axis());
        		objRxVisualizationPlotInfoType.setDataset2Label(objPlotInfoVO.getDataset2Label());
        		objRxVisualizationPlotInfoType.setDataset2ParameterName(objPlotInfoVO.getDataset2ParameterName());
        		objRxVisualizationPlotInfoType.setDataset3Axis(objPlotInfoVO.getDataset3Axis());
        		objRxVisualizationPlotInfoType.setDataset3Label(objPlotInfoVO.getDataset3Label());
        		objRxVisualizationPlotInfoType.setDataset3ParameterName(objPlotInfoVO.getDataset3ParameterName());
        		objRxVisualizationPlotInfoType.setDataset4Axis(objPlotInfoVO.getDataset4Axis());
        		objRxVisualizationPlotInfoType.setDataset4Label(objPlotInfoVO.getDataset4Label());
        		objRxVisualizationPlotInfoType.setDataset4ParameterName(objPlotInfoVO.getDataset4ParameterName());
        	}
        	

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            RMDLOGGER.debug("", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return objRxVisualizationPlotInfoType;

    }

}
