/**
 * ============================================================
 * Classification: GE Confidential
 * File : FaultStrategyResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.faultstrategy.resources
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
package com.ge.trans.rmd.services.faultstrategy.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.eoa.services.tools.fault.service.intf.FaultServiceStgyServiceIntf;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleCommonServiceIntf;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.FaultServiceStrategyResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.ControlerResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultClassificationDetailType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultCodesResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultDataType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultStrategyDetailType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultStrategyRequestType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultStrategyResponseType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.SubIdResponseType;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Aug 26, 2011
 * @Date Modified : Aug 26, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Web services for fetching Fault related data
 * @History :
 ******************************************************************************/
@Path(OMDConstants.FAULTSTRATEGYSERVICE)
@Component
public class FaultStrategyResource extends BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FaultStrategyResource.class);

    @Autowired
    FaultServiceStgyServiceIntf faultServiceStgyIntf;

    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;

    @Autowired
    RuleCommonServiceIntf ruleCommonServiceIntf;

    /**
     * This method is used for fetching Fault related data for the input -
     * faultCodeValue
     * 
     * @param faultCodeValue
     * @return List<FaultStrategyDetailType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETFAULTCODES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultStrategyDetailType> getFaultCodes(@Context UriInfo ui) throws RMDServiceException {
        FaultServiceStgyServiceVO objFaultServiceStgyServiceVO = new FaultServiceStgyServiceVO();
        FaultStrategyDetailType objFaultStrategyDetailTypeVO = null;
        List<FaultServiceStgyServiceVO> objFaultServiceStgyServiceVOList = new ArrayList<FaultServiceStgyServiceVO>();
        final List<FaultStrategyDetailType> objFaultStrategyDetailType = new ArrayList<FaultStrategyDetailType>();
		String isGERuleAUthor=null;		
        try {
            
			LOG.debug("*****getFaultCodes WEBSERVICE BEGIN**** "+new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
			isGERuleAUthor = getRequestHeader(OMDConstants.IS_GE_RULE_AUTHOR);

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
				String strFaultCode = OMDConstants.EMPTY_STRING, strFamily = OMDConstants.EMPTY_STRING,ruleType=OMDConstants.EMPTY_STRING;
            int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_HYPEN, OMDConstants.ALPHA_NUMERIC_HYPEN };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.FAULT_CODE,
                    OMDConstants.FAMILY)) {
                if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                    strFaultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
                }
                if (queryParams.containsKey(OMDConstants.FAMILY)) {
                    strFamily = queryParams.getFirst(OMDConstants.FAMILY);
                }
                if (queryParams.containsKey(OMDConstants.MODEL)) {
                    objFaultServiceStgyServiceVO.setModelName(queryParams.getFirst(OMDConstants.MODEL));
                }
				if (queryParams.containsKey(OMDConstants.RULE_TYPE)) {
					ruleType = queryParams.getFirst(OMDConstants.RULE_TYPE);
				}
				
				if((null!=isGERuleAUthor&&isGERuleAUthor.equalsIgnoreCase(RMDCommonConstants.N_LETTER_UPPER))||ruleType.equalsIgnoreCase(OMDConstants.ALERTS)){
					objFaultServiceStgyServiceVO.setExternalRuleAUthor(true);
				}else{
					objFaultServiceStgyServiceVO.setExternalRuleAUthor(false);
				}
                objFaultServiceStgyServiceVO.setStrFaultCode(strFaultCode);
                objFaultServiceStgyServiceVO.setFamily(strFamily);
                objFaultServiceStgyServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                LOG.debug("*****getFaultCodes WEBSERVICE CALLING SERVICE METHODS**** "
                        + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
                objFaultServiceStgyServiceVOList = faultServiceStgyIntf.findFaultCode(objFaultServiceStgyServiceVO);

                for (FaultServiceStgyServiceVO faultServiceStgyServiceVO : objFaultServiceStgyServiceVOList) {
                    objFaultStrategyDetailTypeVO = new FaultStrategyDetailType();
                    objFaultStrategyDetailTypeVO.setFaultCode(faultServiceStgyServiceVO.getStrFaultCode());
                    objFaultStrategyDetailTypeVO.setFaultCodeDescription(faultServiceStgyServiceVO.getStrDescription());
                    objFaultStrategyDetailTypeVO.setSubIds(faultServiceStgyServiceVO.getSubIds());
                    objFaultStrategyDetailType.add(objFaultStrategyDetailTypeVO);

                }
                LOG.debug("getFaultCodes WEBSERVICE after copied service VO's"
                        + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("*****getFaultCodes WEBSERVICE ENDS**** "
                + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
        return objFaultStrategyDetailType;
    }

    /**
     * This method is used to get the list of subId for the give fault code &
     * subid
     * 
     * @param ui
     * @return List<SearchSubId>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETSUBID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SubIdResponseType> getSubID(@Context UriInfo ui) throws RMDServiceException {
        List<SubIdResponseType> lstElementResponse = new ArrayList<SubIdResponseType>();
        ElementVO element;
        SubIdResponseType objParameterResponseType;
        List<ElementVO> elements = null;
        String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
        String family = OMDConstants.EMPTY_STRING;
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            String strFaultCode = OMDConstants.EMPTY_STRING;
            String strSubId = OMDConstants.EMPTY_STRING;
            int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_HYPEN, OMDConstants.NUMERIC, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.FAULT_CODE,
                    OMDConstants.SUB_ID, OMDConstants.FAMILY)) {
                if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                    strFaultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
                }
                if (queryParams.containsKey(OMDConstants.SUB_ID)) {
                    strSubId = queryParams.getFirst(OMDConstants.SUB_ID);
                }
                if (queryParams.containsKey(OMDConstants.FAMILY)) {
                    family = queryParams.getFirst(OMDConstants.FAMILY);
                }

                elements = ruleCommonServiceIntf.getSubID(strFaultCode, strSubId, strLanguage, family);

                if (RMDCommonUtility.isCollectionNotEmpty(elements)) {
                    Iterator it = elements.iterator();
                    while (it.hasNext()) {
                        element = (ElementVO) it.next();
                        objParameterResponseType = new SubIdResponseType();
                        objParameterResponseType.setSubID(element.getId());
                        lstElementResponse.add(objParameterResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lstElementResponse;
    }

    /**
     * This method is used for fetching Fault related data for the input -
     * faultCodeValue
     * 
     * @param faultCodeValue
     * @return FaultStrategyResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETFAULTCODES_FAULTVALUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FaultStrategyResponseType getFaultCodes(@PathParam(OMDConstants.FAULTCODEVALUE) final String faultCodeValue)
            throws RMDServiceException {
        FaultServiceStgyServiceVO objFaultServiceStgyServiceVO = new FaultServiceStgyServiceVO();
        List<ElementVO> lstFaultClassification = null;
        final FaultStrategyResponseType objFaultStrategyResponseType = new FaultStrategyResponseType();
        final FaultStrategyDetailType objFaultStrategyDetailType = new FaultStrategyDetailType();
        FaultClassificationDetailType objFaultClassificationDetailType = new FaultClassificationDetailType();
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar lastModifiedDate;
        List<ElementVO> lstFaultData = null;
        FaultDataType objFaultDataType = new FaultDataType();
        Iterator<ElementVO> iteratorFaultClassification;
        Iterator<ElementVO> iteratorFaultData;
        try {
            if (null == faultCodeValue) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.FAULT_CODE_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.FAULT_CODE_NOT_PROVIDED), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                objFaultServiceStgyServiceVO.setStrFaultCode(faultCodeValue);
            }
            objFaultServiceStgyServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objFaultServiceStgyServiceVO = faultServiceStgyIntf.findFault(objFaultServiceStgyServiceVO);

            objGregorianCalendar = new GregorianCalendar();
            objGregorianCalendar.setTime(objFaultServiceStgyServiceVO.getDtLastModifiedDate());
            lastModifiedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
            objFaultStrategyResponseType.setLastUpdatedDate(lastModifiedDate);
            lstFaultClassification = objFaultServiceStgyServiceVO.getAlFaultClassification();
            if (RMDCommonUtility.isCollectionNotEmpty(lstFaultClassification)) {
                for (iteratorFaultClassification = lstFaultClassification.iterator(); iteratorFaultClassification
                        .hasNext();) {
                    final ElementVO elementVO = iteratorFaultClassification.next();
                    objFaultClassificationDetailType = new FaultClassificationDetailType();
                    BeanUtility.copyBeanProperty(elementVO, objFaultClassificationDetailType);

                    objFaultStrategyResponseType.getFaultClassificationDetail().add(objFaultClassificationDetailType);

                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility.getErrorCode(OMDConstants.EMPTY_FAULT_CLASSIFICATION_DETAILS),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.EMPTY_FAULT_CLASSIFICATION_DETAILS),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

            lstFaultData = objFaultServiceStgyServiceVO.getAlFaultData();
            if (RMDCommonUtility.isCollectionNotEmpty(lstFaultData)) {
                for (iteratorFaultData = lstFaultData.iterator(); iteratorFaultData.hasNext();) {
                    final ElementVO elementVO = iteratorFaultData.next();
                    objFaultDataType = new FaultDataType();
                    BeanUtility.copyBeanProperty(elementVO, objFaultDataType);
                    objFaultStrategyResponseType.getFaultData().add(objFaultDataType);
                }
            }
            BeanUtility.copyBeanProperty(objFaultServiceStgyServiceVO, objFaultStrategyDetailType);
            objFaultStrategyResponseType.setFaultStrategyDetail(objFaultStrategyDetailType);
            BeanUtility.copyBeanProperty(objFaultServiceStgyServiceVO, objFaultStrategyResponseType);

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return objFaultStrategyResponseType;
    }

    /**
     * This method is used for Saving Fault code details from the inputs passed
     * 
     * @param objFaultStrategyRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVEFAULTCODEDETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    public void saveFaultCodeDetails(final FaultStrategyRequestType objFaultStrategyRequestType)
            throws RMDServiceException {
        final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO = new FaultServiceStgyServiceVO();
        FaultStrategyDetailType objFaultStrategyDetailType = new FaultStrategyDetailType();
        final String struserID = null;
        try {
            objFaultServiceStgyServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
            objFaultServiceStgyServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objFaultServiceStgyServiceVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));

            if (null == objFaultStrategyRequestType) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                if (RMDCommonUtility.isCollectionNotEmpty(objFaultStrategyRequestType.getFaultClassification())) {
                    for (int isize = 0; isize < objFaultStrategyRequestType.getFaultClassification().size(); isize++) {
                        BeanUtility.copyBeanProperty(objFaultStrategyRequestType.getFaultClassification().get(isize),
                                objFaultServiceStgyServiceVO);
                    }
                } else {
                    throw new OMDNoResultFoundException(
                            BeanUtility.getErrorCode(OMDConstants.EMPTY_FAULT_CLASSIFICATION_DETAILS),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.EMPTY_FAULT_CLASSIFICATION_DETAILS),
                                    new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
                if (RMDCommonUtility.isCollectionNotEmpty(objFaultStrategyRequestType.getFaultData())) {
                    for (int isize = 0; isize < objFaultStrategyRequestType.getFaultData().size(); isize++) {
                        BeanUtility.copyBeanProperty(objFaultStrategyRequestType.getFaultData().get(isize),
                                objFaultServiceStgyServiceVO);
                    }
                }
                objFaultStrategyDetailType = objFaultStrategyRequestType.getFaultStrategyDetail();
                BeanUtility.copyBeanProperty(objFaultStrategyDetailType, objFaultServiceStgyServiceVO);
                BeanUtility.copyBeanProperty(objFaultStrategyRequestType, objFaultServiceStgyServiceVO);

                if (objFaultServiceStgyServiceVO != null) {
                    faultServiceStgyIntf.saveUpdate(objFaultServiceStgyServiceVO, struserID);
                }
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
    }

    /**
     * @Author:
     * @param :queryParams
     * @return:FaultServiceStrategyResponseType
     * @throws Exception
     * @Description: This method is used for fetching the Fault Strategy
     *               Details.It accepts fsObjId as an Input Parameter and
     *               returns FaultServiceStrategyResponseType.
     */
    @GET
    @Path(OMDConstants.GET_FAULT_STRATEGY_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public FaultServiceStrategyResponseType getFaultStrategyDetails(@Context UriInfo ui) throws RMDServiceException {
        String fsObjId = null;
        String language = OMDConstants.DEFAULT_LANGUAGE;
        FaultServiceStrategyResponseType objFaultStrategyResponseType = new FaultServiceStrategyResponseType();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar lastUpdatedDate;
        String timeZone = getDefaultTimeZone();
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FAULT_STRATEGY_OBJID)) {
                fsObjId = queryParams.getFirst(OMDConstants.FAULT_STRATEGY_OBJID);
            }
            FaultServiceStrategyVO objFaultServiceStrategyVO = faultServiceStgyIntf.getFaultStrategyDetails(fsObjId);
            if (!RMDCommonUtility.checkNull(objFaultServiceStrategyVO)) {
                objFaultStrategyResponseType.setFltStart2FltCode(objFaultServiceStrategyVO.getFltStart2FltCode());
                objFaultStrategyResponseType.setFaultOrigin(objFaultServiceStrategyVO.getFaultOrigin());
                objFaultStrategyResponseType.setFaultCode(objFaultServiceStrategyVO.getFaultCode());
                objFaultStrategyResponseType.setFaultSubId(objFaultServiceStrategyVO.getFaultSubId());
                objFaultStrategyResponseType.setFaultDesc(objFaultServiceStrategyVO.getFaultDesc());
                objFaultStrategyResponseType.setFaultLabel(objFaultServiceStrategyVO.getFaultLabel());
                objFaultStrategyResponseType.setLstDiagnosticWeight(objFaultServiceStrategyVO.getDiagnosticWeight());
                objFaultStrategyResponseType.setLstSubSysWeight(objFaultServiceStrategyVO.getSubSysWeight());
                objFaultStrategyResponseType.setLstModeRestriction(objFaultServiceStrategyVO.getModeRestriction());
                objFaultStrategyResponseType
                        .setLstFaultClassification(objFaultServiceStrategyVO.getFaultClassification());
                objFaultStrategyResponseType.setFaultCriticalFlag(objFaultServiceStrategyVO.getFaultCriticalFlag());

                if (null != objFaultServiceStrategyVO.getLastUpdatedDate()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objFaultServiceStrategyVO.getLastUpdatedDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    lastUpdatedDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    objFaultStrategyResponseType.setLastUpdatedDate(lastUpdatedDate);
                }

                objFaultStrategyResponseType.setLastUpdatedBy(objFaultServiceStrategyVO.getLastUpdatedBy());
                objFaultStrategyResponseType.setFaultLagTime(objFaultServiceStrategyVO.getFaultLagTime());
                objFaultStrategyResponseType.setFaultNotes(objFaultServiceStrategyVO.getFaultNotes());
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getFaultStrategyDetails() METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return objFaultStrategyResponseType;
    }

    /**
     * @Author:
     * @param :
     * @return:List<FaultServiceStrategyResponseType>
     * @throws:Exception
     * @Description: This method is used to get values to display the Fault Rule
     *               & Desc.
     */
    @GET
    @Path(OMDConstants.GET_RULE_DESC)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultServiceStrategyResponseType> getRuleDesc(@Context UriInfo ui) throws RMDServiceException {
        String faultCode = null;
        List<FaultServiceStrategyVO> lstFaultServiceStrategyVO = new ArrayList<FaultServiceStrategyVO>();
        List<FaultServiceStrategyResponseType> arlResponseType = new ArrayList<FaultServiceStrategyResponseType>();
        final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        if (queryParams.containsKey(OMDConstants.FAULT_CODE_ID)) {
            faultCode = queryParams.getFirst(OMDConstants.FAULT_CODE_ID);
        }
        try {
            lstFaultServiceStrategyVO = faultServiceStgyIntf.getRuleDesc(faultCode);

            if (RMDCommonUtility.isCollectionNotEmpty(lstFaultServiceStrategyVO)) {
                for (FaultServiceStrategyVO objElementVO : lstFaultServiceStrategyVO) {
                    FaultServiceStrategyResponseType responseType = new FaultServiceStrategyResponseType();
                    responseType.setFaultRule(objElementVO.getFaultRule());
                    responseType.setFaultRuleDesc(objElementVO.getFaultRuleDesc());
                    arlResponseType.add(responseType);
                }
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlResponseType;
    }

    /**
     * This method is used to Update FaultServiceStrategy.
     * 
     * @param FaultServiceStrategyResponseType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.UPDATE_FAULT_SERVICE_STRATEGY)
    @Consumes(MediaType.APPLICATION_XML)
    public void updateFaultServiceStrategy(FaultServiceStrategyResponseType objFaultStrategyResponseType)
            throws RMDServiceException {
        FaultServiceStrategyVO objFaultServiceStrategyVO = new FaultServiceStrategyVO();
        try {
            if (objFaultStrategyResponseType != null) {
                objFaultServiceStrategyVO.setUserId(objFaultStrategyResponseType.getUserId());
                objFaultServiceStrategyVO.setCmAliasName(objFaultStrategyResponseType.getCmAliasName());
                objFaultServiceStrategyVO.setFltStart2FltCode(objFaultStrategyResponseType.getFltStart2FltCode());
                objFaultServiceStrategyVO.setFaultDesc(objFaultStrategyResponseType.getFaultDesc());
                objFaultServiceStrategyVO.setDiagnosticWeight(objFaultStrategyResponseType.getLstDiagnosticWeight());
                objFaultServiceStrategyVO.setSubSysWeight(objFaultStrategyResponseType.getLstSubSysWeight());
                objFaultServiceStrategyVO.setModeRestriction(objFaultStrategyResponseType.getLstModeRestriction());
                objFaultServiceStrategyVO
                        .setFaultClassification(objFaultStrategyResponseType.getLstFaultClassification());
                objFaultServiceStrategyVO.setFaultCriticalFlag(objFaultStrategyResponseType.getFaultCriticalFlag());
                objFaultServiceStrategyVO.setFaultLagTime(objFaultStrategyResponseType.getFaultLagTime());
                objFaultServiceStrategyVO.setFaultNotes(objFaultStrategyResponseType.getFaultNotes());
                objFaultServiceStrategyVO.setFsObjId(objFaultStrategyResponseType.getFsObjId());
                faultServiceStgyIntf.updateFaultServiceStrategy(objFaultServiceStrategyVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    rmdServiceException.getErrorDetail().getErrorMessage(),
                    rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
    }

    /**
     * @param
     * @return List<FaultCodesResponseType>
     * @throws RMDServiceException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */

    @GET
    @Path(OMDConstants.GET_FAULT_ORIGIN)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultCodesResponseType> getFaultOrigin() throws RMDServiceException {

        List<FaultCodesResponseType> arlFaultCodesResponseType = new ArrayList<FaultCodesResponseType>();
        try {

            List<FaultCodeVO> arlFaultOrigin = faultServiceStgyIntf.getFaultOrigin();
            for (FaultCodeVO objFaultCodeVO : arlFaultOrigin) {
                FaultCodesResponseType objCodesResponseType = new FaultCodesResponseType();
                objCodesResponseType.setFaultOrigin(objFaultCodeVO.getFaultOrigin());
                arlFaultCodesResponseType.add(objCodesResponseType);

            }

        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return arlFaultCodesResponseType;
    }

    /**
     * @param
     * @return List<FaultCodesResponseType>
     * @throws RMDWebException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */

    @GET
    @Path(OMDConstants.GET_FAULT_CODE_SUBID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultCodesResponseType> getFaultCodeSubId(@Context UriInfo ui) throws RMDServiceException {
        List<FaultCodesResponseType> arlFaultCodesResponseType = new ArrayList<FaultCodesResponseType>();
        String faultCode = null;
        String faultOrigin = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                faultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
            }
            if (queryParams.containsKey(OMDConstants.FAULT_ORIGIN)) {
                faultOrigin = queryParams.getFirst(OMDConstants.FAULT_ORIGIN);
            }
            if (RMDCommonUtility.isNullOrEmpty(faultCode)) {
                throw new OMDInValidInputException(OMDConstants.FAULT_CODE_NOT_PROVIDED);
            }

            if (RMDCommonUtility.isNullOrEmpty(faultOrigin)) {
                throw new OMDInValidInputException(OMDConstants.FAULT_ORIGIN_NOT_PROVIDED);
            }
            if (!AppSecUtil.checkAlphaNumeric(faultOrigin)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_FAULT_ORIGIN);
            }

            List<FaultCodeVO> subIdList = faultServiceStgyIntf.getFaultCodeSubId(faultCode, faultOrigin);
            for (FaultCodeVO objFaultCodeVO : subIdList) {
                FaultCodesResponseType objFaultCodesResponseType = new FaultCodesResponseType();
                objFaultCodesResponseType.setFaultSubId(objFaultCodeVO.getSubId());
                arlFaultCodesResponseType.add(objFaultCodesResponseType);

            }

        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return arlFaultCodesResponseType;

    }

    /**
     * @param
     * @return List<FaultCodeVO>
     * @throws RMDWebException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */

    @GET
    @Path(OMDConstants.GET_VIEW_FSS_FAULT_CODE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FaultCodesResponseType> getViewFSSFaultCode(@Context UriInfo ui) {

        List<FaultCodesResponseType> arlFaultCodesResponseTypes = new ArrayList<FaultCodesResponseType>();
        String faultCode = OMDConstants.EMPTY_STRING;
        String faultOrigin = OMDConstants.EMPTY_STRING;
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                faultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
            }
            if (queryParams.containsKey(OMDConstants.FAULT_ORIGIN)) {
                faultOrigin = queryParams.getFirst(OMDConstants.FAULT_ORIGIN);
            }

            if (!RMDCommonUtility.isNullOrEmpty(faultOrigin)) {
                if (!AppSecUtil.checkAlphaNumeric(faultOrigin)) {
                    throw new OMDInValidInputException(OMDConstants.INVALID_FAULT_ORIGIN);
                }
            }
            List<FaultCodeVO> arlFaultCodes = faultServiceStgyIntf.getViewFSSFaultCode(faultCode, faultOrigin);
            for (FaultCodeVO objFaultCodeVO : arlFaultCodes) {
                FaultCodesResponseType objFaultCodesResponseType = new FaultCodesResponseType();
                objFaultCodesResponseType.setFaultOrigin(objFaultCodeVO.getFaultOrigin());
                objFaultCodesResponseType.setFaultCode(objFaultCodeVO.getFaultCode());
                objFaultCodesResponseType.setFaultDescription(objFaultCodeVO.getFaultDescription());
                arlFaultCodesResponseTypes.add(objFaultCodesResponseType);
            }

        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return arlFaultCodesResponseTypes;

    }

    /**
     * @param
     * @return String
     * @throws RMDWebException
     * @Description This method is used to get Fault Code ObjId based upon
     *              FaultCode,Fault SubID,Fault Origin
     */
    @GET
    @Path(OMDConstants.GET_FAULT_STRATEGY_OBJID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getFaultStrategyObjId(@Context UriInfo ui) {
        String faultStrategyObjId = OMDConstants.FAILURE;
        String faultCode = null;
        String faultOrigin = null;
        String faultCodeSubId = null;
        FaultCodeVO objFaultCodeVO = new FaultCodeVO();
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FAULT_CODE)) {
                faultCode = queryParams.getFirst(OMDConstants.FAULT_CODE);
            }
            if (queryParams.containsKey(OMDConstants.FAULT_ORIGIN)) {
                faultOrigin = queryParams.getFirst(OMDConstants.FAULT_ORIGIN);
            }
            if (queryParams.containsKey(OMDConstants.FAULT_CODE_SUBID)) {
                faultCodeSubId = queryParams.getFirst(OMDConstants.FAULT_CODE_SUBID);
            }

            if (!RMDCommonUtility.isNullOrEmpty(faultCode)) {
                objFaultCodeVO.setFaultCode(faultCode);
            } else {
                throw new OMDInValidInputException(OMDConstants.FAULT_CODE_NOT_PROVIDED);

            }

            if (!RMDCommonUtility.isNullOrEmpty(faultOrigin)) {
                if (AppSecUtil.checkAlphaNumeric(faultOrigin)) {
                    objFaultCodeVO.setFaultOrigin(faultOrigin);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_FAULT_ORIGIN);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.FAULT_ORIGIN_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(faultCodeSubId)) {
                if (AppSecUtil.checkAlphaNumeric(faultCodeSubId)) {
                    objFaultCodeVO.setSubId(faultCodeSubId);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_FAULT_CODE_SUB_ID);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.FAULT_CODE_SUBID_NOT_PROVIDED);
            }
            faultStrategyObjId = faultServiceStgyIntf.getFaultStrategyObjId(objFaultCodeVO);

        } catch (Exception e) {
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    e);
        }
        return faultStrategyObjId;
    }

    /**
     * This method is used to get the list of controllerIds
     * 
     * @param ui
     * @return List<SearchSubId>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETCOUNTROLLERIDS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ControlerResponseType> getControllerID(@Context UriInfo ui) throws RMDServiceException {
        List<ControlerResponseType> lstElementResponse = new ArrayList<ControlerResponseType>();
        ElementVO element;
        ControlerResponseType objParameterResponseType;
        List<ElementVO> elements = null;
        String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
        try {

            elements = ruleCommonServiceIntf.getControllerID(strLanguage);

            if (RMDCommonUtility.isCollectionNotEmpty(elements)) {
                Iterator it = elements.iterator();
                while (it.hasNext()) {
                    element = (ElementVO) it.next();
                    objParameterResponseType = new ControlerResponseType();

                    objParameterResponseType.setId(element.getId());
                    objParameterResponseType.setName(element.getName());
                    lstElementResponse.add(objParameterResponseType);
                }
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lstElementResponse;
    }
}