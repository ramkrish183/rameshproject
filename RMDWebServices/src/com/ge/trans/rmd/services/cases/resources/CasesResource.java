/****
 * ============================================================
 * Classification: GE Confidential
 * File : CasesResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.resources
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 21, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.cases.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.service.intf.RolesEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
import com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.CaseRxMgmtServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindCaseLiteEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindCaseServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.QueueCasesServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.WorkQueueServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CMPrivilegeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseAppendServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseConvertionVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMergeServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMgmtUsersDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTypeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CasesHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CloseOutRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CustomerFdbkVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MassApplyRxVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MaterialUsageVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ReCloseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecommDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxStatusHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ScoreRxEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SolutionBean;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputActEntryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewLogVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.intf.OpenRxServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.intf.RecommEoaServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;
import com.ge.trans.rmd.caseapi.services.service.intf.CaseServiceIntf;
import com.ge.trans.rmd.caseapi.services.valueobjects.CreateCaseVO;
import com.ge.trans.rmd.caseapi.services.valueobjects.DispatchCaseVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RecomDelvStatusVO;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.RxVehicleBomResponseType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.AssetHeaderResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseConversionResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseHeaderResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseHistoryResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseHistoryType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseInfoType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseMgmtUsersResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseRxDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseScoreRepairCodesResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseTrendResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseTypeResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CasesRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CloseOutRepairCodesResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CmPrivilegeResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CustomerFeedbackResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.FindCasesDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.FindCasesRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.LatestCaseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.LatestCaseRuleRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.MassApplyRXRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.MassApplyRxResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.MaterialUsageResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.OpenCaseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.OpenCaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.QueueCaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.QueueDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.ReCloseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.RepairCodeDetailsType;
import com.ge.trans.rmd.services.cases.valueobjects.RxDelvDocType;
import com.ge.trans.rmd.services.cases.valueobjects.RxDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.RxDetailsType;
import com.ge.trans.rmd.services.cases.valueobjects.RxHistoryResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.RxStatusHistoryResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.SolutionInfoType;
import com.ge.trans.rmd.services.cases.valueobjects.StickyNotesDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.ToolOutputActEntry;
import com.ge.trans.rmd.services.cases.valueobjects.ToolOutputDetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.ToolOutputResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.UnitConfigResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.UnitShipDetailsRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.ViewCaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.ViewLogReponseType;
import com.ge.trans.rmd.services.gpocnotes.valueobjects.GeneralNotesResponseType;
import com.ge.trans.rmd.services.notes.valueobjects.NotesRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.RepairCodeType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionExecutionResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*****************************************************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Web services for fetching case relevant details from the RMD
 *              Service Layer
 * @History :
 ********************************************************************************************************************/
@Path(OMDConstants.CASE_SERVICE)
@Component
public class CasesResource extends BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CasesResource.class);

    @Autowired
    private CaseRxMgmtServiceIntf objCaseRxMgmtServiceIntf;
    @Autowired
    private CaseServiceIntf caseAPIServiceIntf;
    @Autowired
    private WorkQueueServiceIntf objWorkQueueServiceIntf;
    @Autowired
    private FindCaseServiceIntf objFindCaseService;
    @Autowired
    private OpenRxServiceIntf openRxServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    RecommEoaServiceIntf objRecommEoaServiceIntf;
    @Autowired
    private FindCaseLiteEoaServiceIntf findCaseLiteEoaServiceIntf;
    @Autowired
    CaseEoaServiceIntf objCaseEoaServiceIntf;
    @Autowired
    CaseServiceIntf objCaseServiceIntf;
    @Autowired
    RolesEoaServiceIntf objRolesEoaServiceIntf;
    @Autowired
    private QueueCasesServiceIntf objQueueCasesServiceIntf;

    /**
     * This method is used for fetching cases based on the parameter passed
     * 
     * @param String
     *            userId
     * @return CmPrivilegeResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.HAS_CM_PRIVILEGE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CmPrivilegeResponseType hasCMPrivilege(@PathParam(OMDConstants.USER_ID) final String userId)
            throws RMDServiceException {
        CmPrivilegeResponseType cmPrivilegeResponseType = null;
        try {

            if (null != userId && !userId.equals(OMDConstants.EMPTY_STRING)) {

                CMPrivilegeVO cmPrivilegeVO = objCaseEoaServiceIntf.hasCMPrivilege(userId);
                if (null != cmPrivilegeVO) {
                    cmPrivilegeResponseType = new CmPrivilegeResponseType();
                    if (cmPrivilegeVO.isCMPrivilege()) {
                        cmPrivilegeResponseType.setCmAliasName(cmPrivilegeVO.getCmAliasName());
                    }
                    cmPrivilegeResponseType.setIsCMPrivilege(cmPrivilegeVO.isCMPrivilege());
                }

                return cmPrivilegeResponseType;
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.USERID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.USERID_NOT_PROVIDED),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for creating a case and apply RX to the created case
     * 
     * @param CaseSolutionRequestType
     *            caseSolutionRequestType
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.MASS_APPLY_RX)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MassApplyRxResponseType> massApplyRX(final MassApplyRXRequestType massApplyRXRequestType)
            throws RMDServiceException {
        List<MassApplyRxResponseType> massApplyrxResult = new ArrayList<MassApplyRxResponseType>();
        return massApplyrxResult;
    }

    /**
     * This method is used for fetching cases based on the parameter passed
     * 
     * @param uriParam
     * @return List of cases
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path(OMDConstants.GET_CASES)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseResponseType> getCases(final CasesRequestType objCasesRequestType) throws RMDServiceException {
        List<SelectCaseHomeVO> caseList = null;
        List<SelectCaseHomeVO> userCaseList = null;
        final List<CaseResponseType> caseResLst = new ArrayList<CaseResponseType>();
        ListIterator<SelectCaseHomeVO> caseLstIter = null;
        ListIterator<SelectCaseHomeVO> caseHomeLstIter = null;
        FindCaseServiceVO FindCaseServiceVO = null;
        FindCaseServiceVO findCaseServiceVO = null;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar lastModifyDate;
        String urgency = RMDCommonConstants.EMPTY_STRING;
        String casetype = RMDCommonConstants.EMPTY_STRING;
        String modelId = RMDCommonConstants.EMPTY_STRING;
        String fleetId = RMDCommonConstants.EMPTY_STRING;
        String rxId = RMDCommonConstants.EMPTY_STRING;
        String[] strUrgencyArray;
        String[] strCasetypeArray;
        String[] modelIdArray;
        String[] fleetIdArray;
        String[] strRxIdArray;
        SelectCaseHomeVO selCaseHomeInfo = null;
        SelectCaseHomeVO selCaseHomeEoaVO = null;
        CaseInfoType caseInfo = null;
        CaseResponseType caseResType = null;
        String timeZone = getDefaultTimeZone();
        try {
            if (objCasesRequestType.getIsMyCases()) {
                // Fetches list of cases related to a user.
                FindCaseServiceVO = new FindCaseServiceVO();
                if (validateGetCases(objCasesRequestType)) {
                    if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
                        FindCaseServiceVO.setStrCustomerId(objCasesRequestType.getCustomerId());
                    } else {
                        FindCaseServiceVO.setStrCustomerId(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objCasesRequestType.getUserID()) {
                        FindCaseServiceVO.setStrUserId(objCasesRequestType.getUserID());
                    }
                    userCaseList = objCaseEoaServiceIntf.getUserCases(FindCaseServiceVO);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            }
            /* Fetches list of cases for all other case conditions */
            else {
                findCaseServiceVO = new FindCaseServiceVO();
                if (null != objCasesRequestType.getProdAssetMap() && !objCasesRequestType.getProdAssetMap().isEmpty()) {
                    CMBeanUtility
                            .copyCustomerAssetToServiceVO(objCasesRequestType.getProdAssetMap(), findCaseServiceVO);
                }
                if (validateGetCases(objCasesRequestType)) {
                    CMBeanUtility.copyJaxbObjToServiceVO(findCaseServiceVO, objCasesRequestType);
                    if (null != objCasesRequestType.getUrgency() && !objCasesRequestType.getUrgency().isEmpty()) {
                        urgency = objCasesRequestType.getUrgency();
                        if (!RMDCommonUtility.isNullOrEmpty(urgency)) {
                            strUrgencyArray = RMDCommonUtility
                                    .splitString(urgency, RMDCommonConstants.COMMMA_SEPARATOR);
                            findCaseServiceVO.setUrgency(strUrgencyArray);
                        }
                    }
                    /* Filter stories phase2 sprint 1 changes */

                    /* Filter stories phase2 sprint 2 changes Starts */
                    if (null != objCasesRequestType.getRxId() && !objCasesRequestType.getRxId().isEmpty()) {
                        rxId = objCasesRequestType.getRxId();
                        if (!RMDCommonUtility.isNullOrEmpty(rxId)) {
                            strRxIdArray = RMDCommonUtility.splitString(rxId, RMDCommonConstants.COMMMA_SEPARATOR);
                            findCaseServiceVO.setRxIds(strRxIdArray);
                        }
                    }
                    /* Filter stories phase2 sprint 2 changes Ends */

                    /* Filter stories phase2 sprint4 changes */
                    if (null != objCasesRequestType.getCaseType() && !objCasesRequestType.getCaseType().isEmpty()) {
                        casetype = objCasesRequestType.getCaseType();
                        if (!RMDCommonUtility.isNullOrEmpty(casetype)) {
                            // Converting the comma String to String Array
                            strCasetypeArray = RMDCommonUtility.splitString(casetype,
                                    RMDCommonConstants.COMMMA_SEPARATOR);
                            findCaseServiceVO.setCaseType(strCasetypeArray);
                        }
                    }
                    /* Filter stories phase2 sprint 4 changes */

                    /* Filter stories phase2 sprint4 changes model & fleets */
                    if (null != objCasesRequestType.getModelId() && !objCasesRequestType.getModelId().isEmpty()) {
                        modelId = objCasesRequestType.getModelId();
                        if (!RMDCommonUtility.isNullOrEmpty(modelId)) {
                            // Converting the comma String to String Array
                            modelIdArray = RMDCommonUtility.splitString(modelId, RMDCommonConstants.COMMMA_SEPARATOR);
                            findCaseServiceVO.setModelId(modelIdArray);
                        }
                    }

                    if (null != objCasesRequestType.getFleetId() && !objCasesRequestType.getFleetId().isEmpty()) {
                        fleetId = objCasesRequestType.getFleetId();
                        if (!RMDCommonUtility.isNullOrEmpty(fleetId)) {
                            // Converting the comma String to String Array
                            fleetIdArray = RMDCommonUtility.splitString(fleetId, RMDCommonConstants.COMMMA_SEPARATOR);
                            findCaseServiceVO.setFleetId(fleetIdArray);
                        }
                    }
                    final long startTime = System.currentTimeMillis();
                    caseList = (ArrayList<SelectCaseHomeVO>) objFindCaseService.getCases(findCaseServiceVO);

                    final long endTime = System.currentTimeMillis();
                    LOG.debug("Case Resource Query Construction Execution Time reqd start::" + startTime + "::end time"
                            + endTime + "Diff:" + (endTime - startTime));
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            }

            if (RMDCommonUtility.isCollectionNotEmpty(caseList)) {

                long startTime = System.currentTimeMillis();
                caseHomeLstIter = caseList.listIterator();
                DatatypeFactory dtf = DatatypeFactory.newInstance();

                while (caseHomeLstIter.hasNext()) {
                    selCaseHomeInfo = (SelectCaseHomeVO) caseHomeLstIter.next();
                    caseInfo = new CaseInfoType();
                    caseResType = new CaseResponseType();
                    CMBeanUtility.copyCaseHomeVOToCaseInfoType(selCaseHomeInfo, caseInfo);
                    if (null != selCaseHomeInfo.getDtCreationDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(selCaseHomeInfo.getDtCreationDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        createdDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                        caseInfo.setCreatedDate(createdDate);
                    }
                    if (null != selCaseHomeInfo.getDtHistDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(selCaseHomeInfo.getDtHistDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lastModifyDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                        caseInfo.setHistDate(lastModifyDate);
                    }
                    caseResType.setCustomerName(selCaseHomeInfo.getStrcustomerName());
                    CMBeanUtility.copyCaseHomeVOToCaseResType(selCaseHomeInfo, caseResType);
                    caseResType.setCaseInfo(caseInfo);
                    caseResLst.add(caseResType);
                }
                final long endTime = System.currentTimeMillis();
                LOG.debug("Case Resource Time for copying properties reqd start::" + startTime + "::end time" + endTime
                        + "Diff:" + (endTime - startTime));
            }
            if (RMDCommonUtility.isCollectionNotEmpty(userCaseList)) {
                long startTime = System.currentTimeMillis();
                caseLstIter = userCaseList.listIterator();
                DatatypeFactory dtf = DatatypeFactory.newInstance();

                while (caseLstIter.hasNext()) {
                    selCaseHomeEoaVO = (SelectCaseHomeVO) caseLstIter.next();
                    caseInfo = new CaseInfoType();
                    caseResType = new CaseResponseType();
                    CMBeanUtility.copyCaseHomeVOToCaseInfoType(selCaseHomeEoaVO, caseInfo);
                    if (null != selCaseHomeEoaVO.getDtCreationDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(selCaseHomeEoaVO.getDtCreationDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        createdDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                        caseInfo.setCreatedDate(createdDate);
                    }
                    if (null != selCaseHomeEoaVO.getDtHistDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(selCaseHomeEoaVO.getDtHistDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        lastModifyDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                        caseInfo.setHistDate(lastModifyDate);
                    }
                    caseResType.setCustomerName(selCaseHomeEoaVO.getStrcustomerName());
                    CMBeanUtility.copyCaseHomeVOToCaseResType(selCaseHomeEoaVO, caseResType);
                    caseResType.setCaseInfo(caseInfo);
                    caseResLst.add(caseResType);
                }
                final long endTime = System.currentTimeMillis();
                LOG.debug("Case Resource Time for copying properties reqd start::" + startTime + "::end time" + endTime
                        + "Diff:" + (endTime - startTime));

            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResLst;
    }

    /**
     * This method is used for fetching delivered solution details
     * 
     * @param uriParam
     * @return list of solution details
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_DELIVERED_SOLN_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SolutionDetailType> getDeliveredSolutionDetails(@Context final UriInfo uriParam)
            throws RMDServiceException {
        Iterator<RecomDelvServiceVO> iterSolution;
        final List<SolutionDetailType> arlSolutionDetailTypes = new ArrayList<SolutionDetailType>();
        RecomDelvServiceVO objDelvServiceVO;
        List<RecomDelvServiceVO> arlRecomDelv;
        GregorianCalendar objCalendar;
        XMLGregorianCalendar rxDelvDt = null;
        String strCaseId = null;
        String strLanguage = null;
        String userLanguage = null;
        SolutionDetailType objSolutionDetailType = null;
        try {

        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return arlSolutionDetailTypes;

    }

    /**
     * This method is used for dispatching cases to queue
     * 
     * @param objCaseRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.DISPATCH_TO_QUEUE)
    @Consumes(MediaType.APPLICATION_XML)
    public void dispatchToQueue(final CaseRequestType objCaseRequestType) throws RMDServiceException {
        DispatchCaseVO objDispatchCaseVO = null;
        try {

            objDispatchCaseVO = new DispatchCaseVO();
            if (objCaseRequestType != null) {
                BeanUtility.copyBeanProperty(objCaseRequestType, objDispatchCaseVO);

                objDispatchCaseVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                objDispatchCaseVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                objDispatchCaseVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
                caseAPIServiceIntf.dispatchCase(objDispatchCaseVO);
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for take Ownership of cases
     * 
     * @param objCaseRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.TAKE_OWNERSHIP)
    @Consumes(MediaType.APPLICATION_XML)
    public void takeOwnerShip(final CaseRequestType objCaseRequestType) throws RMDServiceException {
        AcceptCaseEoaVO objAcceptCaseEoaVO = null;
        try {
            objAcceptCaseEoaVO = new AcceptCaseEoaVO();
            if (objCaseRequestType != null) {
                BeanUtility.copyBeanProperty(objCaseRequestType, objAcceptCaseEoaVO);
                objAcceptCaseEoaVO.setStrUserName(objCaseRequestType.getUserName());
                objAcceptCaseEoaVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                objAcceptCaseEoaVO.setStrCaseId(objCaseRequestType.getCaseID());
                objCaseEoaServiceIntf.takeOwnership(objAcceptCaseEoaVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for Creating case
     * 
     * @param objCaseRequestType
     * @throws RMDServiceException
     */

    @POST
    @Path(OMDConstants.CREATE_CASE)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String createCase(CaseRequestType objCaseRequestType) throws RMDServiceException {
        CreateCaseVO createCaseVO = new CreateCaseVO();
        String caseId = null;
        try {
            if (objCaseRequestType != null) {

                createCaseVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                createCaseVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

                createCaseVO.setStrUserName(objCaseRequestType.getUserName());
                createCaseVO.setStrLanguage(objCaseRequestType.getUserLanguage());

                BeanUtility.copyBeanProperty(objCaseRequestType, createCaseVO);
                createCaseVO.setStrAssetNumber(objCaseRequestType.getAssetNumber());
                createCaseVO.setStrCustomerName(objCaseRequestType.getCustomerId());
                createCaseVO.setStrCustomerId(objCaseRequestType.getCustomerId());
                caseId = caseAPIServiceIntf.createCase(createCaseVO);
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }*/
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseId;
    }

    /**
     * This method is used for retrieving Tool output details
     * 
     * @param uriParam
     * @return list of
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_TOOL_OUTPUT_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ToolOutputDetailsResponseType> getToolOutputDetails(@Context final UriInfo uriParam)
            throws RMDServiceException {

        String caseId = OMDConstants.EMPTY_STRING;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        List<ToolOutputEoaServiceVO> lstToolOutputService = null;
        ToolOutputDetailsResponseType toolOutput = null;
        List<ToolOutputDetailsResponseType> toolOutputList = new ArrayList<ToolOutputDetailsResponseType>();
        Iterator<ToolOutputEoaServiceVO> iteratorToolOutputServiceVO = null;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            int[] methodConstants = {RMDCommonConstants.ALPHA_NUMERIC_HYPEN};

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASEID)
                        && AppSecUtil.validateWebServiceInput(queryParams, null, methodConstants, OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                lstToolOutputService = objCaseEoaServiceIntf.getToolOutputDetails(caseId);
                if (RMDCommonUtility.isCollectionNotEmpty(lstToolOutputService) && lstToolOutputService != null) {
                    iteratorToolOutputServiceVO = lstToolOutputService.iterator();
                    ToolOutputEoaServiceVO tooloutputVo = null;
                    while (iteratorToolOutputServiceVO.hasNext()) {
                        tooloutputVo = new ToolOutputEoaServiceVO();
                        tooloutputVo = (ToolOutputEoaServiceVO) iteratorToolOutputServiceVO.next();
                        toolOutput = new ToolOutputDetailsResponseType();
                        CMBeanUtility.copyToolOputSerVOToToolOputDetResType(tooloutputVo, toolOutput);
                        if (tooloutputVo.getDtLastRunDt() != null) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(tooloutputVo.getDtLastRunDt());
                            createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            toolOutput.setLastRunDt(createdDate);
                        }
                        if (tooloutputVo.getDtRxDelvDate() != null) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(tooloutputVo.getDtRxDelvDate());
                            createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            toolOutput.setSolutionDelvDate(createdDate);
                        }
                        toolOutputList.add(toolOutput);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

                }*/
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return toolOutputList;
    }

    /**
     * This method is used for Saving Case details
     * 
     * @param objCaseRequestType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVE_CASE_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    public void saveCaseDetails(final CaseRequestType objCaseRequestType) throws RMDServiceException {
        final CaseInfoServiceVO caseInfoServiceVO = new CaseInfoServiceVO();
        try {
            if (objCaseRequestType != null) {
                if (validateCaseRequestType(objCaseRequestType)) {
                    BeanUtility.copyBeanProperty(objCaseRequestType, caseInfoServiceVO);

                    caseInfoServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                    caseInfoServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

                    objCaseEoaServiceIntf.save(caseInfoServiceVO, getRequestHeader(OMDConstants.USERID));
                } else {
                    throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                            omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                    new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

                }

            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for populating
     * 
     * @param objSolutionRequestType
     * @throws RMDServiceException
     */

    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_LATEST_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LatestCaseRequestType> getLatestCases(@Context final UriInfo uriParam) throws RMDServiceException {
        List<SelectCaseHomeVO> caseList = null;
        List<LatestCaseRequestType> caseResLst = new ArrayList<LatestCaseRequestType>();
        ListIterator<SelectCaseHomeVO> caseLstIter = null;
        FindCaseServiceVO findCaseServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String strUserLanguage = OMDConstants.EMPTY_STRING;
        SelectCaseHomeVO selCaseHomeInfo = null;
        LatestCaseRequestType caseResType = null;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            int[] methodConstants = {RMDCommonConstants.NUMERIC, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.ALPHA_NUMERIC_HYPEN,
                    RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.VALID_DATE,
                    RMDCommonConstants.VALID_DATE, RMDCommonConstants.ALPHABETS, RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.ALPHABETS,};
            /* Fetches list of cases for all other case conditions */
            findCaseServiceVO = new FindCaseServiceVO();
            if (AppSecUtil.validateWebServiceInput(queryParams, RMDServiceConstants.DATE_FORMAT2, methodConstants,
                    OMDConstants.NUMBER_OF_DAYS, OMDConstants.LANGUAGE, OMDConstants.USER_LANGUAGE,
                    OMDConstants.ASSET_HEADER, OMDConstants.QUEUE_NAME, OMDConstants.ASSET_NUMBER,
                    OMDConstants.CASE_ID1, OMDConstants.CASE_TITLE, OMDConstants.CASE_STATUS, OMDConstants.USER_ID1,
                    OMDConstants.STARTDATE, OMDConstants.ENDDATE, OMDConstants.SOLUTION_STATUS,
                    OMDConstants.CUSTOMER_ID, OMDConstants.ASSET_GROUP_NAME)) {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                CMBeanUtility.copyQueryParamsToServiceVO(findCaseServiceVO, queryParams);
                findCaseServiceVO.setStrLanguage(strLanguage);
                findCaseServiceVO.setStrUserLanguage(strUserLanguage);
                long startTime = System.currentTimeMillis();
                caseList = (ArrayList<SelectCaseHomeVO>) objFindCaseService.getLatestCases(findCaseServiceVO);
                long endTime = System.currentTimeMillis();
                LOG.debug("Case Resource Query Construction Execution Time reqd start::" + startTime + "::end time"
                        + endTime + "Diff:" + (endTime - startTime));
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            if (RMDCommonUtility.isCollectionNotEmpty(caseList)) {
                LOG.debug("Size of the list ");

                caseLstIter = caseList.listIterator();
                while (caseLstIter.hasNext()) {
                    selCaseHomeInfo = (SelectCaseHomeVO) caseLstIter.next();
                    String caseId = selCaseHomeInfo.getStrCaseId();
                    caseResType = new LatestCaseRequestType();
                    caseResType.setCaseId(caseId);
                    caseResLst.add(caseResType);
                }

            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }*/
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return caseResLst;
    }

    /**
     * @Author:
     * @param:CaseRequestType
     * @return: String
     * @throws:RMDServiceException
     * @Description: This method reopens the case by invoking caseseoaserviceimpl.reOpenCase()
     *               method.
     */
    @POST
    @Path(OMDConstants.REOPEN_CASE)
    @Consumes(MediaType.APPLICATION_XML)
    public String reOpenCase(final CaseRequestType objCaseRequestType) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            if (objCaseRequestType != null) {
                if (AppSecUtil.checkAlphaNumeric(objCaseRequestType.getCaseID())
                        && AppSecUtil.checkAlphaNumeric(objCaseRequestType.getUserName())) {
                    String caseId = objCaseRequestType.getCaseID();
                    String userName = objCaseRequestType.getUserName();
                    result = objCaseEoaServiceIntf.reOpenCase(caseId, userName);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return result;
    }

    /**
     * This method is used for Fetching solution details in open status
     * 
     * @param uriParam
     * @return List<CaseResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_OPEN_RX_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseResponseType> getOpenRXDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        CaseResponseType objCaseResponseType = null;
        CaseInfoType objCaseInfoType = null;
        OpenRxServiceVO objRxServiceVO = null;
        List<OpenRxServiceVO> listOpenRxResult = null;
        MultivaluedMap<String, String> queryParams = null;
        List<CaseResponseType> listOpenRXResponse = null;
        OpenRxServiceVO openRxServiceVO = null;
        try {
            queryParams = uriParam.getQueryParameters();

            objRxServiceVO = new OpenRxServiceVO();
            objRxServiceVO.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
            objRxServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objRxServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
            listOpenRxResult = new ArrayList<OpenRxServiceVO>();
            listOpenRxResult = openRxServiceIntf.fetchOpenRxs(objRxServiceVO);
            listOpenRXResponse = new ArrayList<CaseResponseType>();
            if (RMDCommonUtility.isCollectionNotEmpty(listOpenRxResult)) {
                for (Iterator<OpenRxServiceVO> iteratorOpenRxServiceVO = listOpenRxResult.iterator(); iteratorOpenRxServiceVO
                        .hasNext();) {
                    openRxServiceVO = (OpenRxServiceVO) iteratorOpenRxServiceVO.next();
                    objCaseResponseType = new CaseResponseType();
                    objCaseInfoType = new CaseInfoType();
                    BeanUtility.copyBeanProperty(openRxServiceVO, objCaseInfoType);
                    BeanUtility.copyBeanProperty(openRxServiceVO, objCaseResponseType);
                    objCaseResponseType.setCaseInfo(objCaseInfoType);
                    listOpenRXResponse.add(objCaseResponseType);
                }
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
        } catch (OMDInValidInputException objOMDInValidInputException) {

            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {

            throw rmdServiceException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return listOpenRXResponse;
    }

    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_LATEST_CASERULES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LatestCaseRuleRequestType> getLatestCaseRules(@Context final UriInfo uriParam)
            throws RMDServiceException {
        Map caseMap = null;
        List<LatestCaseRuleRequestType> caseResLst = new ArrayList<LatestCaseRuleRequestType>();
        FindCaseServiceVO findCaseServiceVO = null;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String strUserLanguage = OMDConstants.EMPTY_STRING;
        LatestCaseRuleRequestType caseResType = null;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            int[] methodConstants = {RMDCommonConstants.NUMERIC, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.ALPHA_NUMERIC_HYPEN,
                    RMDCommonConstants.ALPHABETS, RMDCommonConstants.ALPHABETS,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.VALID_DATE,
                    RMDCommonConstants.VALID_DATE, RMDCommonConstants.ALPHABETS, RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.ALPHABETS,};
            /* Fetches list of cases for all other case conditions */
            findCaseServiceVO = new FindCaseServiceVO();
            if (AppSecUtil.validateWebServiceInput(queryParams, RMDServiceConstants.DATE_FORMAT2, methodConstants,
                    OMDConstants.NUMBER_OF_DAYS, OMDConstants.LANGUAGE, OMDConstants.USER_LANGUAGE,
                    OMDConstants.ASSET_HEADER, OMDConstants.QUEUE_NAME, OMDConstants.ASSET_NUMBER,
                    OMDConstants.CASE_ID1, OMDConstants.CASE_TITLE, OMDConstants.CASE_STATUS, OMDConstants.USER_ID1,
                    OMDConstants.STARTDATE, OMDConstants.ENDDATE, OMDConstants.SOLUTION_STATUS,
                    OMDConstants.CUSTOMER_ID, OMDConstants.ASSET_GROUP_NAME)) {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                CMBeanUtility.copyQueryParamsToServiceVO(findCaseServiceVO, queryParams);
                findCaseServiceVO.setStrLanguage(strLanguage);
                findCaseServiceVO.setStrUserLanguage(strUserLanguage);
                long startTime = System.currentTimeMillis();
                caseMap = (HashMap) findCaseLiteEoaServiceIntf.getLatestCaseRules(findCaseServiceVO);
                long endTime = System.currentTimeMillis();
                LOG.debug("Case Resource Query Construction Execution Time reqd start::" + startTime + "::end time"
                        + endTime + "Diff:" + (endTime - startTime));
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            if (caseMap != null && caseMap.size() > 0) {
                LOG.debug("Size of the list ");

                Set caseKeySet = caseMap.keySet();
                Iterator caseItr = caseKeySet.iterator();
                while (caseItr.hasNext()) {
                    String caseId = (String) caseItr.next();

                    caseResType = new LatestCaseRuleRequestType();
                    caseResType.setCaseId(caseId);

                    if (caseMap.get(caseId) != null) {
                        Map ruleMap = (HashMap) caseMap.get(caseId);
                        Set ruleKeySet = ruleMap.keySet();
                        Iterator ruleItr = ruleKeySet.iterator();

                        List<ToolOutputDetailsResponseType> toolOutputDetailsResponse = new ArrayList();
                        while (ruleItr.hasNext()) {
                            String strRuleDefId = (String) ruleItr.next();;
                            String strRuleId = (String) ruleMap.get(strRuleDefId);
                            ToolOutputDetailsResponseType caseRule = new ToolOutputDetailsResponseType();
                            caseRule.setRuleDefId(strRuleDefId);
                            caseRule.setRuleID(strRuleId);
                            toolOutputDetailsResponse.add(caseRule);
                        }
                        if (toolOutputDetailsResponse.size() > 0) {
                            caseResType.setToolOutputDetailsResponse(toolOutputDetailsResponse);
                        }
                    }
                    caseResLst.add(caseResType);
                }
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResLst;
    }

    private static boolean validateCaseRequestType(CaseRequestType requestType) {

        boolean isValidData = true;

        // case id
        if (null != requestType.getCaseID() && !requestType.getCaseID().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericHypen(requestType.getCaseID())) {
                return false;
            }
        }

        // queue name
        if (null != requestType.getQueueName() && !requestType.getQueueName().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(requestType.getQueueName())) {
                return false;
            }
        }

        // user name
        if (null != requestType.getUserName() && !requestType.getUserName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeralsUnderscore(requestType.getUserName())) {
                return false;
            }
        }

        // asset number
        if (null != requestType.getAssetNumber() && !requestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(requestType.getAssetNumber())) {
                return false;
            }
        }

        // urgency
        if (null != requestType.getUrgency() && !requestType.getUrgency().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(requestType.getUrgency())) {
                return false;
            }
        }

        // language
        if (null != requestType.getLanguage() && !requestType.getLanguage().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(requestType.getLanguage())) {
                return false;
            }
        }

        // user language
        if (null != requestType.getUserLanguage() && !requestType.getUserLanguage().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(requestType.getUserLanguage())) {
                return false;
            }
        }
        if (null != requestType.getCaseType() && !requestType.getCaseType().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(requestType.getCaseType())) {
                return false;
            }
        }

        return isValidData;

    }

    /**
     * This method is used for getting the case types
     * 
     * @param ui
     * @return List<UsersResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_EOA_CASE_TYPE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseTypeResponseType> getEoaCaseType(@Context final UriInfo uriParam) throws RMDServiceException {

        CaseTypeEoaVO caseTypeVO = null;
        List<CaseTypeEoaVO> caseTypeLst = null;
        CaseTypeResponseType responseType = null;
        final List<CaseTypeResponseType> caseResponseTypes = new ArrayList<CaseTypeResponseType>();

        try {

            caseTypeVO = new CaseTypeEoaVO();
            caseTypeVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            caseTypeVO.setStrUserName(getRequestHeader(OMDConstants.USERID));

            caseTypeLst = objCaseEoaServiceIntf.getCaseTypes(caseTypeVO);

            if (RMDCommonUtility.isCollectionNotEmpty(caseTypeLst)) {
                for (CaseTypeEoaVO objCaseType : caseTypeLst) {
                    responseType = new CaseTypeResponseType();
                    if (objCaseType.getCaseTypeId() != null) {
                        responseType.setCaseTypeSeqId(objCaseType.getCaseTypeId().toString());
                    }
                    if (objCaseType.getCaseTypeTitle() != null) {
                        responseType.setCaseTypeTitle(objCaseType.getCaseTypeTitle());
                    }
                    caseResponseTypes.add(responseType);
                }
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResponseTypes;
    }

    /**
     * This method is used for getting the case types
     * 
     * @param ui
     * @return List<UsersResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASE_TYPE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseTypeResponseType> getCaseType(@Context final UriInfo uriParam) throws RMDServiceException {

        CaseTypeVO caseTypeVO = null;
        List<CaseTypeVO> caseTypeLst = null;
        CaseTypeResponseType responseType = null;
        final List<CaseTypeResponseType> caseResponseTypes = new ArrayList<CaseTypeResponseType>();

        try {

            caseTypeVO = new CaseTypeVO();
            caseTypeVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            caseTypeVO.setStrUserName(getRequestHeader(OMDConstants.USERID));

            caseTypeLst = objFindCaseService.getCaseType(caseTypeVO);

            if (RMDCommonUtility.isCollectionNotEmpty(caseTypeLst)) {
                for (CaseTypeVO objCaseType : caseTypeLst) {
                    responseType = new CaseTypeResponseType();

                    responseType.setCaseTypeSeqId(objCaseType.getCaseTypeId().toString());
                    responseType.setCaseTypeTitle(objCaseType.getCaseTypeTitle());
                    caseResponseTypes.add(responseType);
                }
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResponseTypes;
    }

    /**
     * This method is used to add an RX for a particular case
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.ADD_RX_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addRXToCase(CaseSolutionRequestType objSolutionRequestType) throws RMDServiceException {
        RecomDelvInfoServiceVO caseRXVO = null;
        String strRxids = null;
        try {
            caseRXVO = new RecomDelvInfoServiceVO();
            if (validateCaseRXRequest(objSolutionRequestType)) {
                if (null == objSolutionRequestType.getCaseID() || objSolutionRequestType.getCaseID().isEmpty()) {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);

                }
                if (null == objSolutionRequestType.getSolutionID() || objSolutionRequestType.getSolutionID().isEmpty()) {
                    throw new OMDInValidInputException(OMDConstants.SOLUTION_ID_NOT_PROVIDED);
                }
                strRxids = objSolutionRequestType.getSolutionID();
                List<String> listRxids = RMDCommonUtility.cnvrtStringToList(strRxids);
                caseRXVO.setStrCaseID(objSolutionRequestType.getCaseID());
                caseRXVO.setRxIdsList(listRxids);
                caseRXVO.setStrUserName(objSolutionRequestType.getUserName());
                objCaseRxMgmtServiceIntf.addRXToCase(caseRXVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

    }

    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASE_RX_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CaseRxDetailsResponseType getCaseRXDetails(@PathParam(OMDConstants.CASE_Id) final String caseId)
            throws RMDServiceException {

        CaseRxDetailsResponseType objCaseRxDetailsResponseType = new CaseRxDetailsResponseType();
        List<RecomDelvInfoServiceVO> rxList = null;
        try {
            if (null != caseId && !caseId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkAlphaNumericHypen(caseId)) {
                    objCaseRxDetailsResponseType.setCaseID(caseId);
                    rxList = objCaseRxMgmtServiceIntf.getCaseRXDetails(caseId);
                    if (RMDCommonUtility.isCollectionNotEmpty(rxList)) {
                        Iterator<RecomDelvInfoServiceVO> it = rxList.iterator();
                        while (it.hasNext()) {
                            RecomDelvInfoServiceVO tempVO = it.next();
                            SolutionInfoType solutionInfoType = new SolutionInfoType();
                            if (null != tempVO.getStrRxObjid() && !tempVO.getStrRxObjid().isEmpty()) {
                                solutionInfoType.setSolutionOBJID(tempVO.getStrRxObjid());
                            }
                            if (null != tempVO.getStrRxTitle() && !tempVO.getStrRxTitle().isEmpty()) {
                                solutionInfoType.setSolutionTitle(tempVO.getStrRxTitle());
                            }
                            if (null != tempVO.getStrUrgRepair() && !tempVO.getStrUrgRepair().isEmpty()) {
                                solutionInfoType.setUrgency(tempVO.getStrUrgRepair());
                            }
                            if (null != tempVO.getStrEstmRepTime() && !tempVO.getStrEstmRepTime().isEmpty()) {
                                solutionInfoType.setEstmRepTime(tempVO.getStrEstmRepTime());
                            }
                            objCaseRxDetailsResponseType.getSolutionInfo().add(solutionInfoType);
                        }
                    } /*else {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }*/
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objCaseRxDetailsResponseType;
    }

    /**
     * This method is used for fetching case details
     * 
     * @param uriParam
     * @return CaseDetailsResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASEHISTORY_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CaseDetailsResponseType getCaseHistoryDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        final List<CaseDetailsResponseType> lstCaseDetails = new ArrayList<CaseDetailsResponseType>();

        String caseId = OMDConstants.EMPTY_STRING;
        String language = OMDConstants.EMPTY_STRING;
        String userLanguage = OMDConstants.EMPTY_STRING;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        CaseDetailsResponseType caseDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        CaseHistoryType history = null;

        CaseInfoType caseInfo = new CaseInfoType();
        String timeZone = getDefaultTimeZone();

        try {
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseDetails;
    }

    /**
     * This method is used for real time check for owners in EOA and OMD
     * 
     * @param PathParam
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CURRENT_OWNERSHIP)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getCurrentOwnership(@PathParam(OMDConstants.CASE_Id) final String caseId) throws RMDServiceException {
        String owner = null;
        String cmAliasName = null;
        boolean isCaseClosed = true;

        try {
            if (null != caseId && !caseId.equals(OMDConstants.EMPTY_STRING)) {
                cmAliasName = getRequestHeader(OMDConstants.CM_ALIAS_NAME);
                isCaseClosed = objCaseEoaServiceIntf.isCaseClosed(caseId);
                if (isCaseClosed) {
                    throw new OMDApplicationException(OMDConstants.RMD_203, omdResourceMessagesIntf.getMessage(
                            BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION), new String[]{},
                            BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), OMDConstants.MINOR_ERROR);
                }
                owner = objCaseEoaServiceIntf.getEoaCurrentOwnership(caseId);
                if (null == owner) {
                    throw new OMDApplicationException(OMDConstants.RMD_203, omdResourceMessagesIntf.getMessage(
                            BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION), new String[]{},
                            BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), OMDConstants.MINOR_ERROR);
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDApplicationException objOMDApplicationException) {
            throw objOMDApplicationException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getClass(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return owner;
    }

    /**
     * This method is used to validate CaseSolutionRequestType for add RX and deliver RX
     * 
     * @param CaseSolutionRequestType
     * @return boolean
     * @throws
     */
    public static boolean validateCaseRXRequest(CaseSolutionRequestType objSolutionRequestType) {

        if (null != objSolutionRequestType.getCaseID() && !objSolutionRequestType.getCaseID().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericHypen(objSolutionRequestType.getCaseID())) {
                return false;
            }
        }
        if (null != objSolutionRequestType.getSolutionID() && !objSolutionRequestType.getSolutionID().isEmpty()) {
            if (!AppSecUtil.checkIntNumberComma(objSolutionRequestType.getSolutionID())) {
                return false;
            }
        }
        if (null != objSolutionRequestType.getSolutionTitle() && !objSolutionRequestType.getSolutionTitle().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objSolutionRequestType.getSolutionID())) {
                return false;
            }
        }
        if (null != objSolutionRequestType.getRecomNotes() && !objSolutionRequestType.getRecomNotes().isEmpty()) {
            if (RMDCommonUtility.isSpecialCharactersFound(objSolutionRequestType.getRecomNotes())) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is used to validate the data used for getting cases CasesRequestType
     * 
     * @return boolean
     */
    public static boolean validateGetCases(final CasesRequestType objCasesRequestType) {

        if (null != objCasesRequestType.getAssetNumber() && !objCasesRequestType.getAssetNumber().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objCasesRequestType.getAssetNumber())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericComma(objCasesRequestType.getCustomerId())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getAssetGrName() && !objCasesRequestType.getAssetGrName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getAssetGrName())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getCaseID() && !objCasesRequestType.getCaseID().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getCaseID())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getEstRepTm() && !objCasesRequestType.getEstRepTm().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getEstRepTm())) {
                return false;
            }
        }

        if (null != objCasesRequestType.getSolutionId() && !objCasesRequestType.getSolutionId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeric(objCasesRequestType.getSolutionId())) {
                return false;
            }
        }
        if (null != objCasesRequestType.getCaseLike() && !objCasesRequestType.getCaseLike().isEmpty()) {
            if (AppSecUtil.isSpecialCharactersFound(objCasesRequestType.getCaseLike())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param rxCaseId
     * @throws RMDServiceException
     */
    private void scoreRxRTC(String rxCaseId) throws RMDServiceException {

        // RTC check
        String caseSuccess = null;
        caseSuccess = objRecommEoaServiceIntf.getRxScoreStatus(rxCaseId, getRequestHeader(OMDConstants.LANGUAGE));
        if (caseSuccess != null) {
            String errorCode = RMDCommonUtility.getErrorCode(OMDConstants.RX_SCORED_205);

            throw new RMDServiceException(errorCode, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    getRequestHeader(OMDConstants.LANGUAGE)));

        }
        // RTC check.

    }

    /**
     * @param caseId
     * @throws RMDServiceException
     */
    private void closeCaseRTC(String caseId) throws RMDServiceException {

        boolean isCaseClosed = objCaseEoaServiceIntf.isCaseClosed(caseId);
        if (isCaseClosed) {
            String errorCode = RMDCommonUtility.getErrorCode(OMDConstants.RX_SCORED_206);
            throw new RMDServiceException(errorCode, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    getRequestHeader(OMDConstants.LANGUAGE)));
        }

    }

    /**
     * @param rxDeliveryStatus
     * @param solutionId
     * @throws RMDServiceException
     */
    private void replaceAndModifyRTCheck(String caseId, String solutionId, boolean isReplaceRx)
            throws RMDServiceException {
        // TODO Auto-generated method stub.
        // check the RTC for replace status
        boolean isRTCPassed = true;

        RecomDelvStatusVO rxDeliveryStatus = objRecommEoaServiceIntf.getRxDeliveryStatus(caseId,
                getRequestHeader(OMDConstants.LANGUAGE));

        if (rxDeliveryStatus != null) {
            Date delvDate = rxDeliveryStatus.getDelvDate();

            if (delvDate == null) {
                isRTCPassed = false;
            } else if (delvDate != null
                    && OMDConstants.SERVICE_STATUS_OPEN.equalsIgnoreCase(rxDeliveryStatus.getServiceReqIdStatus())) {
                isRTCPassed = false;
            }
            if (isReplaceRx) {
                if (delvDate != null
                        && !OMDConstants.SERVICE_STATUS_OPEN.equalsIgnoreCase(rxDeliveryStatus.getServiceReqIdStatus())
                        && (null != rxDeliveryStatus.getRecomDelvTorecom() && rxDeliveryStatus.getRecomDelvTorecom()
                                .equals(RMDCommonUtility.convertObjectToLong(solutionId)))) {
                    isRTCPassed = false;
                }
            } else {
                if (delvDate != null
                        && !OMDConstants.SERVICE_STATUS_OPEN.equalsIgnoreCase(rxDeliveryStatus.getServiceReqIdStatus())
                        && (null != rxDeliveryStatus.getRecomDelvTorecom() && !rxDeliveryStatus.getRecomDelvTorecom()
                                .equals(RMDCommonUtility.convertObjectToLong(solutionId)))) {

                    isRTCPassed = false;
                }
            }
        }

        if (!isRTCPassed) {
            // throw exception
            String errorCode = RMDCommonUtility.getErrorCode(OMDConstants.REPLACE_MODIFY_RX_210);
            throw new RMDServiceException(errorCode, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    getRequestHeader(OMDConstants.LANGUAGE)));
        }
    }

    /**
     * This method is used for fetching repair code which are closed out
     * 
     * @param String
     *            userId
     * @return List<RepairCodeDetailsType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CLOSE_OUT_REPAIR_CODES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getCloseOutRepairCodes(@PathParam(OMDConstants.RECOM_ID) final String recomId)
            throws RMDServiceException {
        List<RepairCodeDetailsType> repaircodesList = new ArrayList<RepairCodeDetailsType>();
        RepairCodeEoaDetailsVO repairCodeInputType = new RepairCodeEoaDetailsVO();
        List<RepairCodeEoaDetailsVO> repairCodeResponse = null;
        try {

            if (null != recomId && !recomId.equals(OMDConstants.EMPTY_STRING)) {
                repairCodeInputType.setRecomId(recomId);
                repairCodeResponse = objCaseEoaServiceIntf.getCloseOutRepairCodes(repairCodeInputType);
                for (RepairCodeEoaDetailsVO repairCodeDetail : repairCodeResponse) {
                    RepairCodeDetailsType currentRepairCode = new RepairCodeDetailsType();
                    currentRepairCode.setTaskDesc(repairCodeDetail.getTaskDesc());
                    currentRepairCode.setRepaidCodeDesc(repairCodeDetail.getRepairCodeDesc());
                    currentRepairCode.setRepairCode(repairCodeDetail.getRepairCode());
                    currentRepairCode.setRepairCodeId(repairCodeDetail.getRepairCodeId());
                    currentRepairCode.setTaskId(repairCodeDetail.getTaskId());
                    repaircodesList.add(currentRepairCode);
                }

                return repaircodesList;
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INPUTS_NOT_GIVEN),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INPUTS_NOT_GIVEN),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for fetching cases based on the parameter passed
     * 
     * @param uriParam
     * @return List of cases
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path(OMDConstants.SEARCH_CASES)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseResponseType> searchCases(final CasesRequestType objCasesRequestType) throws RMDServiceException {
        List<CaseResponseType> caseResponseList = new ArrayList<CaseResponseType>();
        return caseResponseList;
    }

    /**
     * @Author:
     * @param case id
     * @return CaseBean
     * @Description: return the list of all the rx associated with the case
     */
    @GET
    @Path(OMDConstants.GET_CASE_RX_HISTORY)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SolutionInfoType> getCaseRxHistory(@PathParam(OMDConstants.CASE_Id) final String caseId)
            throws RMDServiceException {

        SolutionInfoType solutionBean = null;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        String timeZone = getDefaultTimeZone();
        List<SolutionInfoType> caseRxHistoryVoList = new ArrayList<SolutionInfoType>();
        MultiLangValuesVO multiLangValuesVO = null;
        try {
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDApplicationException objOMDApplicationException) {
            throw objOMDApplicationException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return caseRxHistoryVoList;
    }

    /*
     * Added by Vamsee Deepak For Dispatch Functionality * / /**
     * @Author:
     * @param case id
     * @return QueueDetailsVO
     * @Description: return the list of all the rx associated with the case
     */
    @GET
    @Path(OMDConstants.GET_QUEUE_NAMES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<QueueDetailsResponseType> getQueueNames(@Context UriInfo ui) throws RMDServiceException {
        List<QueueDetailsVO> queueDetailsVoList = null;
        List<QueueDetailsResponseType> queueDetailsResponseList = new ArrayList<QueueDetailsResponseType>();
        String caseId = null;
        String caseType = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }

            if (null == caseId) {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }

            queueDetailsVoList = objCaseEoaServiceIntf.getQueueNames(caseId);

            for (QueueDetailsVO obj : queueDetailsVoList) {
                QueueDetailsResponseType objqueueDetailsRepsonseType = new QueueDetailsResponseType();
                objqueueDetailsRepsonseType.setQueueId(obj.getQueueId());
                objqueueDetailsRepsonseType.setQueueName(obj.getQueueName());
                queueDetailsResponseList.add(objqueueDetailsRepsonseType);
            }

        } catch (Exception e) {
            LOG.error("ERROR IN GET QUEUE NAMES METHOD:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return queueDetailsResponseList;

    }

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDServiceException
     * @Description:This method is used for a dispatching case to dynamic work queues selected by
     *                   the user. This is done by invoking dispatchCaseToWorkQueue() of
     *                   CaseEoaServiceImpl Layer.
     */

    @POST
    @Path(OMDConstants.DISPATCH_CASE_TO_QUEUE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String dispatchCaseToWorkQueue(final CaseRequestType objCaseRequestType) throws RMDServiceException {

        String result = OMDConstants.FAILURE;
        String caseId = null;
        String userId = null;
        long queueId = 0L;
        try {

            if (null != objCaseRequestType) {
                if (null != objCaseRequestType.getCaseID()) {
                    if (AppSecUtil.checkAlphaNumeric(objCaseRequestType.getCaseID())) {
                        caseId = objCaseRequestType.getCaseID();
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);

                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                if (0L != objCaseRequestType.getQueueId()) {

                    queueId = objCaseRequestType.getQueueId();
                } else {
                    throw new OMDInValidInputException(OMDConstants.QUEUEID_NOT_PROVIDED);
                }
                if (null != objCaseRequestType.getUserId()) {

                    if (AppSecUtil.checkAlphaNumeric(objCaseRequestType.getUserId())) {
                        userId = objCaseRequestType.getUserId();
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_USERID);
                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.USERID_NOT_PROVIDED);
                }

                result = objCaseEoaServiceIntf.dispatchCaseToWorkQueue(queueId, caseId, userId);
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT DISPATCH CASE TO WORK QUEUE METHOD:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding Case notes for a given case.This is done by
     *                   invoking addNotesToCase() of CaseEoaServiceImpl Layer.
     */
    @POST
    @Path(OMDConstants.ADD_NOTES_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String addNotesToCase(final NotesRequestType requestobj) throws RMDServiceException {
        String result = null;
        AddNotesEoaServiceVO objAddNotesServiceVO = null;
        try {

            if (null != requestobj) {
                objAddNotesServiceVO = new AddNotesEoaServiceVO();
                if (null != requestobj.getCaseId()) {

                    if (AppSecUtil.checkAlphaNumeric(requestobj.getCaseId())) {
                        objAddNotesServiceVO.setCaseId(requestobj.getCaseId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);

                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                if (null != requestobj.getNotes()) {
                    objAddNotesServiceVO.setNoteDescription(EsapiUtil.resumeSpecialChars(requestobj.getNotes()));
                } else {
                    throw new OMDInValidInputException(OMDConstants.NOTE_DESCRIPTION_NOT_PROVIDED);
                }
                if (null != requestobj.getApplyLevel()) {
                    objAddNotesServiceVO.setApplyLevel(requestobj.getApplyLevel());
                } else {
                    throw new OMDInValidInputException(OMDConstants.APPLY_LEVEL_NOT_PROVIDED);
                }

                if (null != requestobj.getUserId()) {
                    if (AppSecUtil.checkAlphaNumeric(requestobj.getUserId())) {
                        objAddNotesServiceVO.setUserId(requestobj.getUserId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_USERID);

                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.USERID_NOT_PROVIDED);
                }
                objAddNotesServiceVO.setSticky(requestobj.getSticky());
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);

            }
            result = objCaseEoaServiceIntf.addNotesToCase(objAddNotesServiceVO);

        }

        catch (Exception e) {
            LOG.error("ERROR IN ADD NOTES TO CASE IN CASERESOURCE:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching unit Sticky notes details for a given case.This is
     *                   done by invoking fetchStickyUnitNotes() of CaseEoaServiceImpl Layer.
     */
    @GET
    @Path(OMDConstants.FETCH_UNIT_STICKY_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StickyNotesDetailsResponseType fetchStickyUnitNotes(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        StickyNotesDetailsResponseType objStickyDetailsResponseType = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar entryTime;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (null == caseId) {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }
            if (!AppSecUtil.checkAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            StickyNotesDetailsVO objStickyNotesDetailsVO = objCaseEoaServiceIntf.fetchStickyUnitNotes(caseId);
            if (null != objStickyNotesDetailsVO) {
                objStickyDetailsResponseType = new StickyNotesDetailsResponseType();
                objStickyDetailsResponseType.setApplyLevel(objStickyNotesDetailsVO.getApplyLevel());
                objStickyDetailsResponseType.setAdditionalInfo(objStickyNotesDetailsVO.getAdditionalInfo());
                objStickyDetailsResponseType.setCreatedBy(objStickyNotesDetailsVO.getCreatedBy());
                objStickyDetailsResponseType.setObjId(objStickyNotesDetailsVO.getObjId());
                if (null != objStickyNotesDetailsVO.getEntryTime()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objStickyNotesDetailsVO.getEntryTime());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    entryTime = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    objStickyDetailsResponseType.setEntryTime(entryTime);	
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT FETCH STICKY UNIT NOTES METHOD IN CASERESOURCE:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objStickyDetailsResponseType;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching case Sticky notes details for a given case.This is
     *                   done by invoking fetchStickyCaseNotes() of CaseEoaServiceImpl Layer.
     */

    @GET
    @Path(OMDConstants.FETCH_CASE_STICKY_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StickyNotesDetailsResponseType fetchStickyCaseNotes(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar entryTime;
        StickyNotesDetailsResponseType objStickyNotesResponseType = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {

                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (null == caseId) {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }
            if (!AppSecUtil.checkAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }

            StickyNotesDetailsVO objStickyDetailsVO = objCaseEoaServiceIntf.fetchStickyCaseNotes(caseId);
            if (null != objStickyDetailsVO) {
                objStickyNotesResponseType = new StickyNotesDetailsResponseType();
                objStickyNotesResponseType.setApplyLevel(objStickyDetailsVO.getApplyLevel());
                objStickyNotesResponseType.setAdditionalInfo(objStickyDetailsVO.getAdditionalInfo());
                objStickyNotesResponseType.setCreatedBy(objStickyDetailsVO.getCreatedBy());
                objStickyNotesResponseType.setObjId(objStickyDetailsVO.getObjId());
                if (null != objStickyDetailsVO.getEntryTime()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objStickyDetailsVO.getEntryTime());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    entryTime = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    objStickyNotesResponseType.setEntryTime(entryTime);
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT FETCH STICKY CASE NOTES METHOD IN CASERESOURCE:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return objStickyNotesResponseType;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId, applyLevel
     * @throws :RMDServiceException
     * @Description:This method is used for removing a unit Level as well as case Level Sticky Notes
     *                   for a given case.This is done by invoking removeStickyNotes() of
     *                   CaseEoaServiceImpl Layer.
     */
    @POST
    @Path(OMDConstants.REMOVE_STICKY_NOTES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String removeStickyNotes(final StickyNotesDetailsResponseType stickyDetailsResponseType)
            throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        String unitStickyObjId = null;
        String caseStickyObjId = null;
        String applyLevel = null;
        try {

            if (null != stickyDetailsResponseType.getUnitStickyObjId()) {
                unitStickyObjId = stickyDetailsResponseType.getUnitStickyObjId();
            }

            if (null != stickyDetailsResponseType.getUnitStickyObjId()) {
                caseStickyObjId = stickyDetailsResponseType.getCaseStickyObjId();
            }

            if (null != stickyDetailsResponseType.getApplyLevel()) {
                applyLevel = stickyDetailsResponseType.getApplyLevel();
            } else {
                throw new OMDInValidInputException(OMDConstants.APPLY_LEVEL_NOT_PROVIDED);
            }

            result = objCaseEoaServiceIntf.removeStickyNotes(unitStickyObjId, caseStickyObjId, applyLevel);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            LOG.error("ERROR OCCURED AT REMOVE STICKY NOTES METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return result;
    }

    /**
     * @Author:
     * @return:String
     * @param :FindCaseServiceVO
     * @throws:RMDServiceException
     * @Description:This method is for updating case details based upon user choice.
     */
    @POST
    @Path(OMDConstants.UPDATE_CASE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String updateCaseDetails(final CaseRequestType caseRequestType) throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        FindCaseServiceVO caseDetails = new FindCaseServiceVO();
        try {

            if (null != caseRequestType) {
                if (null != caseRequestType.getCaseID()) {

                    if (AppSecUtil.checkAlphaNumeric(caseRequestType.getCaseID())) {
                        caseDetails.setCaseID(caseRequestType.getCaseID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);

                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                if (null != caseRequestType.getCaseType()) {
                    caseDetails.setCaseTypes(caseRequestType.getCaseType());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASETYPE_NOT_PROVIDED);
                }
                if (null != caseRequestType.getCaseTitle()) {
                    caseDetails.setStrCaseTitle(EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(caseRequestType.getCaseTitle())));
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASETITLE_NOT_PROVIDED);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
            result = objCaseEoaServiceIntf.updateCaseDetails(caseDetails);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return result;
    }

    /**
     * This method is used for getting the case types
     * 
     * @param ui
     * @return List<UsersResponseType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASE_TYPES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseTypeResponseType> getCaseTypes() throws RMDServiceException {

        CaseTypeVO caseTypeVO = null;
        List<CaseTypeVO> caseTypeLst = null;
        CaseTypeResponseType responseType = null;
        final List<CaseTypeResponseType> caseResponseTypes = new ArrayList<CaseTypeResponseType>();

        try {

            caseTypeVO = new CaseTypeVO();
            caseTypeVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            caseTypeVO.setStrUserName(getRequestHeader(OMDConstants.USERID));

            caseTypeLst = objFindCaseService.getCaseType(caseTypeVO);

            if (RMDCommonUtility.isCollectionNotEmpty(caseTypeLst)) {
                for (CaseTypeVO objCaseType : caseTypeLst) {
                    responseType = new CaseTypeResponseType();

                    responseType.setCaseTypeSeqId(objCaseType.getCaseTypeId().toString());
                    responseType.setCaseTypeTitle(objCaseType.getCaseTypeTitle());
                    caseResponseTypes.add(responseType);
                }
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResponseTypes;
    }
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method returns the list of roles which have Case Management Privilege by
     *                   calling the getCaseMgmtRoles() method of Service Layer.
     */
    @GET
    @Path(OMDConstants.GET_CM_PRIVILEGE_ROLES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RolesResponseType> getCaseMgmtRoles() throws RMDServiceException {
        List<RolesResponseType> rolesResponseList = new ArrayList<RolesResponseType>();
        try {
            List<RolesVO> cmPreviligeRoleList = objRolesEoaServiceIntf.getCaseMgmtRoles();

            for (RolesVO objRolesVO : cmPreviligeRoleList) {
                RolesResponseType objRolesResponseType = new RolesResponseType();
                objRolesResponseType.setRoleSeqId(objRolesVO.getGetUsrRolesSeqId());
                objRolesResponseType.setRoleName(objRolesVO.getRoleName());
                rolesResponseList.add(objRolesResponseType);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return rolesResponseList;
    }

    /**
     * @Author :
     * @return :SolutionDetailType
     * @param :caseObjId
     * @throws DatatypeConfigurationException
     * @throws :RMDServiceException,Exception
     * @Description:This method is used for fetching the selected solutions/Recommendations for a
     *                   Case.
     */
    @GET
    @Path(OMDConstants.GET_SOLUTIONS_FOR_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SolutionDetailType> getSolutionsForCase(@Context final UriInfo uriParam) throws RMDServiceException,
            DatatypeConfigurationException {
        String caseObjId = null;
        String language = RMDCommonConstants.LANGUAGE;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar deliverDate;
        String timeZone = getDefaultTimeZone();
        DatatypeFactory objDatatypeFactory = DatatypeFactory.newInstance();
        List<SolutionBean> solutionsList = null;
        List<SolutionDetailType> selectedSolResponseList = new ArrayList<SolutionDetailType>();
        try {
            final MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (null != caseObjId && RMDCommonUtility.isNumeric(caseObjId)) {
                solutionsList = objCaseEoaServiceIntf.getSolutionsForCase(caseObjId, language);
                for (SolutionBean objSolutionBean : solutionsList) {
                    SolutionDetailType objSolutionDetailType = new SolutionDetailType();
                    objSolutionDetailType.setRxObjId(objSolutionBean.getRxObjId());
                    objSolutionDetailType.setSolutionTitle(objSolutionBean.getTitle());
                    objSolutionDetailType.setSolutionType(objSolutionBean.getRxType());
                    if (null != objSolutionBean.getDeliveryDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objSolutionBean.getDeliveryDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        deliverDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                        objSolutionDetailType.setDeliveryDate(deliverDate);
                    }
                    objSolutionDetailType.setUrgRepair(objSolutionBean.getUrgency());
                    objSolutionDetailType.setEstmTimeRepair(objSolutionBean.getRepairTime());
                    objSolutionDetailType.setSolutionRevNo(objSolutionBean.getRevisionNo());
                    objSolutionDetailType.setMessageId(objSolutionBean.getMessageObjId());
                    objSolutionDetailType.setReIssue(objSolutionBean.getReIssue());
                    if (null != objSolutionBean.getCreationDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objSolutionBean.getCreationDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        createdDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                        objSolutionDetailType.setCreationDate(createdDate);
                    }
                    selectedSolResponseList.add(objSolutionDetailType);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            LOG.error("ERROR OCCURED AT  getSolutionsForCase() METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return selectedSolResponseList;
    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to add a recommendation to a given
     *               Case.
     */
    @POST
    @Path(OMDConstants.ADD_RX_FOR_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String addRxToCase(CasesRequestType objCasesRequestType) throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        RecomDelvInfoServiceVO objDelvInfoServiceVO = new RecomDelvInfoServiceVO();
        try {
            if (null != objCasesRequestType) {
                List<RxDetailsVO> detailsVOsList = new ArrayList<RxDetailsVO>();
                for (SolutionDetailType objSolutionDetailType : objCasesRequestType.getSolutionDetailType()) {
                    RxDetailsVO objDetailsVO = new RxDetailsVO();
                    objDetailsVO.setRxObjid(objSolutionDetailType.getSolutionID());
                    objDetailsVO.setUrgency(objSolutionDetailType.getUrgRepair());
                    objDetailsVO.setEstRepTime(objSolutionDetailType.getEstmTimeRepair());
                    objDetailsVO.setStrVersion(objSolutionDetailType.getVersion());
                    detailsVOsList.add(objDetailsVO);
                }
                objDelvInfoServiceVO.setArlRxDetailsVO(detailsVOsList);

                if (null != objCasesRequestType.getCaseObjId()
                        && RMDCommonUtility.isNumeric(objCasesRequestType.getCaseObjId())) {
                    objDelvInfoServiceVO.setCaseObjId(objCasesRequestType.getCaseObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
                }
                if (null != objCasesRequestType.getUserID()) {
                    objDelvInfoServiceVO.setUserId(objCasesRequestType.getUserID());
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_USERID);
                }
                result = objCaseEoaServiceIntf.addRxToCase(objDelvInfoServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT addRxToCase() METHOD OF CASERESOURCE , e", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;

    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to add a recommendation to a given
     *               Case.
     */
    @POST
    @Path(OMDConstants.DELETE_RX_FOR_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String deleteRxToCase(CasesRequestType objCasesRequestType) throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        RecomDelvInfoServiceVO objDelvInfoServiceVO = null;
        try {
            if (null != objCasesRequestType) {
                objDelvInfoServiceVO = new RecomDelvInfoServiceVO();
                if (null != objCasesRequestType.getRxObjId()
                        && RMDCommonUtility.isNumeric(objCasesRequestType.getRxObjId().toString())) {
                    objDelvInfoServiceVO.setRxObjId(objCasesRequestType.getRxObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                }
                if (null != objCasesRequestType.getCaseObjId()
                        && RMDCommonUtility.isNumeric(objCasesRequestType.getCaseObjId())) {
                    objDelvInfoServiceVO.setCaseObjId(objCasesRequestType.getCaseObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
                }
                result = objCaseEoaServiceIntf.deleteRxToCase(objDelvInfoServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT deleteRxToCase() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;

    }

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseInfoServiceVO
     * @throws DatatypeConfigurationException
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the case Information.It accepts caseId as an
     *               Input Parameter and returns caseBean List.
     */
    @GET
    @Path(OMDConstants.GET_CASE_INFO)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CaseInfoType getCaseInfo(@Context UriInfo ui) throws RMDServiceException, DatatypeConfigurationException {
        String caseId = null;
        String language = OMDConstants.DEFAULT_LANGUAGE;
        CaseInfoType objCaseInfoType = new CaseInfoType();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar appendedDate;
        XMLGregorianCalendar nextScheduledRun;
        String timeZone = getDefaultTimeZone();
        DatatypeFactory objDatatypeFactory = DatatypeFactory.newInstance();
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (null == caseId) {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }
            if (!AppSecUtil.checkAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            CaseInfoServiceVO objCaseInfoServiceVO = objCaseEoaServiceIntf.getCaseInfo(caseId, language);

            if (!RMDCommonUtility.checkNull(objCaseInfoServiceVO)) {
                objCaseInfoType.setStrcaseObjId(objCaseInfoServiceVO.getCaseObjId());
                objCaseInfoType.setCustomerName(objCaseInfoServiceVO.getCustomerName());
                objCaseInfoType.setRoadNumber(objCaseInfoServiceVO.getRoadNumber());
                objCaseInfoType.setModel(objCaseInfoServiceVO.getModel());
                objCaseInfoType.setCaseNumber(objCaseInfoServiceVO.getCaseNumber());
                objCaseInfoType.setCustomerRNH(objCaseInfoServiceVO.getCustomerRNH());
                objCaseInfoType.setOnBoardRNH(objCaseInfoServiceVO.getOnBoardRNH());
                objCaseInfoType.setCaseTitle(objCaseInfoServiceVO.getCaseTitle());
                objCaseInfoType.setCasePriority(objCaseInfoServiceVO.getCasePriority());

                objCaseInfoType.setCaseType(objCaseInfoServiceVO.getCaseType());
                objCaseInfoType.setFleet(objCaseInfoServiceVO.getFleet());
                objCaseInfoType.setGpsLatitude(objCaseInfoServiceVO.getGpsLatitude());
                objCaseInfoType.setGpslongitude(objCaseInfoServiceVO.getGpslongitude());
                objCaseInfoType.setGpsHeading(objCaseInfoServiceVO.getGpsHeading());
                objCaseInfoType.setBadActor(objCaseInfoServiceVO.getBadActor());
                objCaseInfoType.setVehicleObjId(objCaseInfoServiceVO.getVehicleObjId());
                objCaseInfoType.setServices(objCaseInfoServiceVO.getServices());
                objCaseInfoType.setRecPendingAlert(objCaseInfoServiceVO.getRecPendingAlert());
                objCaseInfoType.setPendingFaults(objCaseInfoServiceVO.getPendingFaults());
                objCaseInfoType.setControllerConfig(objCaseInfoServiceVO.getControllerConfig());
                if (null != objCaseInfoServiceVO.getDtCreateDate()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objCaseInfoServiceVO.getDtCreateDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    createdDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setCreatedDate(createdDate);
                }
                if (null != objCaseInfoServiceVO.getNextScheduledRun()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objCaseInfoServiceVO.getNextScheduledRun());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    nextScheduledRun = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setNextScheduledRun(nextScheduledRun);
                }
                if (null != objCaseInfoServiceVO.getAppendedDate()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objCaseInfoServiceVO.getAppendedDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    appendedDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setAppendedDate(appendedDate);
                }
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getCaseInfo() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objCaseInfoType;
    }

    @POST
    @Path(OMDConstants.YANK_CASE)
    @Consumes(MediaType.APPLICATION_XML)
    public void yankCase(final CaseRequestType objCaseRequestType) throws RMDServiceException {

        // RTU Start
        AcceptCaseEoaVO objAcceptCaseEoaVO = null;
        // RTU End
        try {

            // RTU Start
            objAcceptCaseEoaVO = new AcceptCaseEoaVO();
            // RTU End

            if (objCaseRequestType != null) {
                // RTU Start
                BeanUtility.copyBeanProperty(objCaseRequestType, objAcceptCaseEoaVO);
                objAcceptCaseEoaVO.setStrUserName(objCaseRequestType.getUserName());
                objAcceptCaseEoaVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

                objAcceptCaseEoaVO.setStrCaseId(objCaseRequestType.getCaseID());
                objCaseEoaServiceIntf.yankCase(objAcceptCaseEoaVO);
                // RTU End
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);

            }
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * @Author:
     * @param:
     * @return: List<CaseMgmtUsersResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the list of users by invoking
     *               caseseoaserviceimpl.getCaseMgmtUsersDetails() method.
     */
    @GET
    @Path(OMDConstants.GET_CASE_MGMT_USER_NAMES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseMgmtUsersResponseType> getCaseMgmtUsersDetails() throws RMDServiceException {

        List<CaseMgmtUsersDetailsVO> objCaseMgmtUsersDetailsList = null;
        List<CaseMgmtUsersResponseType> objCaseMgmtUsersResponseTypeList = new ArrayList<CaseMgmtUsersResponseType>();
        try {

            objCaseMgmtUsersDetailsList = objCaseEoaServiceIntf.getCaseMgmtUsersDetails();

            for (CaseMgmtUsersDetailsVO obj : objCaseMgmtUsersDetailsList) {
                CaseMgmtUsersResponseType objUserNamesResponseType = new CaseMgmtUsersResponseType();
                objUserNamesResponseType.setUserId(obj.getUserId());
                objUserNamesResponseType.setFirstName(obj.getFirstName());
                objUserNamesResponseType.setLastName(obj.getLastName());
                objUserNamesResponseType.setSso(obj.getSso());
                objCaseMgmtUsersResponseTypeList.add(objUserNamesResponseType);
            }

        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return objCaseMgmtUsersResponseTypeList;
    }

    /**
     * @Author:
     * @param:CaseRequestType
     * @return: String
     * @throws:RMDServiceException
     * @Description: This method fetches the assigns case to user of by invoking
     *               caseseoaserviceimpl.assignCaseToUser() method.
     */
    @POST
    @Path(OMDConstants.ASSIGN_TO_USER)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String assignCaseToUser(CaseRequestType objCaseRequestType) throws RMDServiceException {
        String userId;
        String caseId;
        String result = OMDConstants.FAILURE;

        try {

            if (null != objCaseRequestType.getCaseID()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCaseID())) {
                if (AppSecUtil.checkAlphaNumeric(objCaseRequestType.getCaseID()))

                {
                    caseId = objCaseRequestType.getCaseID();
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }
            if (null != objCaseRequestType.getUserId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getUserId())) {

                userId = objCaseRequestType.getUserId();

            } else {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }

            result = objCaseEoaServiceIntf.assignCaseToUser(userId, caseId);

        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return result;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return: String
     * @throws:RMDServiceException
     * @Description: This method fetches the owner for case by invoking
     *               caseseoaserviceimpl.getOwnerForCase() method.
     */
    @GET
    @Path(OMDConstants.GET_OWNER_FOR_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CaseInfoType getCaseCurrentOwnerDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        String caseId;
        SelectCaseHomeVO objDetailsVO = new SelectCaseHomeVO();
        CaseInfoType objQueueDetailsResponseType = new CaseInfoType();
        MultivaluedMap<String, String> queryParams = null;

        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                    if (AppSecUtil.checkAlphaNumeric(queryParams.getFirst(OMDConstants.CASE_Id))) {
                        caseId = queryParams.getFirst(OMDConstants.CASE_Id);
                        objDetailsVO = objCaseEoaServiceIntf.getCaseCurrentOwnerDetails(caseId);
                        if (null != objDetailsVO) {
                            objQueueDetailsResponseType.setOwner(objDetailsVO.getStrOwner());
                            objQueueDetailsResponseType.setQueueName(objDetailsVO.getStrQueue());
                            objQueueDetailsResponseType.setCondition(objDetailsVO.getCondition());
                        }
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }

        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return objQueueDetailsResponseType;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CaseActivityLogResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the list of activities for case by invoking
     *               caseseoaserviceimpl.getActivityLog() method.
     */
    @GET
    @Path(OMDConstants.CASE_HISTORY)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseHistoryResponseType> getCaseHistory(@Context final UriInfo uriParam) throws RMDServiceException {
        String caseId = null;
        MultivaluedMap<String, String> queryParams = null;
        List<HistoyVO> caseHistoryVOList = null;
        List<CaseHistoryResponseType> caseHistoryResponseTypeList = new ArrayList<CaseHistoryResponseType>();
        String timeZone = getDefaultTimeZone();
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_Id)) {

                    if (AppSecUtil.checkAlphaNumeric(queryParams.getFirst(OMDConstants.CASE_Id))) {
                        caseId = queryParams.getFirst(OMDConstants.CASE_Id);
                        caseHistoryVOList = objCaseEoaServiceIntf.getCaseHistory(caseId);
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
            } else {
                LOG.debug("queryparams is empty");
            }

            if (caseHistoryVOList != null && caseHistoryVOList.size() > 0) {
                caseHistoryResponseTypeList = new ArrayList<CaseHistoryResponseType>(caseHistoryVOList.size());
            }
            for (HistoyVO obj : caseHistoryVOList) {
                CaseHistoryResponseType objCaseHistoryResponseType = new CaseHistoryResponseType();
                objCaseHistoryResponseType.setActivity(obj.getActivity());
                if (obj.getCreateDate() != null) {
                    objCaseHistoryResponseType.setCreateDate(obj.getCreateDate());
                }
                objCaseHistoryResponseType.setUser(obj.getUser());
                objCaseHistoryResponseType.setAddInfo(obj.getAddInfo());
                objCaseHistoryResponseType.setActivityType(obj.getActivityType());
                objCaseHistoryResponseType.setObjId(obj.getObjId());
                objCaseHistoryResponseType.setDescription(obj.getDescription());
                caseHistoryResponseTypeList.add(objCaseHistoryResponseType);
            }
            caseHistoryVOList = null;
        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return caseHistoryResponseTypeList;
    }

    /**
     * @Author:
     * @param:CaseRequestType
     * @return:List<CaseInfoType>
     * @throws:RMDServiceException
     * @Description: This method fetches the set of cases for asset by invoking
     *               caseseoaserviceimpl.getAssetCases() method.
     */
    @POST
    @Path(OMDConstants.GET_ASSET_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseInfoType> getAssetCases(CaseRequestType objCaseRequestType) throws RMDServiceException {

        String customerName;
        String roadNo;
        String roadInitial;
        String caseType;
        String noOfDays;
        String caseLike;
        String appendFlag;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar closeDate;
        List<CaseInfoType> caseInfotypeList = new ArrayList<CaseInfoType>();
        CaseInfoType objCaseInfoType = new CaseInfoType();
        FindCaseServiceVO objFindCaseServiceVO = new FindCaseServiceVO();
        List<SelectCaseHomeVO> objSelectCaseHomeVOList = new ArrayList<SelectCaseHomeVO>();
        String timeZone = getDefaultTimeZone();
        try {

            if (null != objCaseRequestType.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCustomerId())) {
                customerName = objCaseRequestType.getCustomerId();
                objFindCaseServiceVO.setStrCustomerId(customerName);
            }
            if (null != objCaseRequestType.getAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetNumber())) {
                roadNo = objCaseRequestType.getAssetNumber();
                objFindCaseServiceVO.setStrAssetNumber(roadNo);
            }
            if (null != objCaseRequestType.getAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetGrpName())) {
                roadInitial = objCaseRequestType.getAssetGrpName();
                objFindCaseServiceVO.setStrAssetGrpName(roadInitial);
            }
            if (null != objCaseRequestType.getCaseType()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCaseType())) {
                caseType = objCaseRequestType.getCaseType();
                objFindCaseServiceVO.setStrCaseType(caseType);
            }
            if (null != objCaseRequestType.getNoOfDays())

            {
                noOfDays = objCaseRequestType.getNoOfDays();
                objFindCaseServiceVO.setNoOfDays(noOfDays);
            }
            if (null != objCaseRequestType.getCaseLike()) {
                caseLike = objCaseRequestType.getCaseLike();
                objFindCaseServiceVO.setCaseID(caseLike);
            }
            if (null != objCaseRequestType.getAppendFlag()) {
                appendFlag = objCaseRequestType.getAppendFlag();
                objFindCaseServiceVO.setAppendFlag(appendFlag);
            }
            objSelectCaseHomeVOList = objCaseEoaServiceIntf.getAssetCases(objFindCaseServiceVO);
            for (SelectCaseHomeVO details : objSelectCaseHomeVOList) {
                objCaseInfoType = new CaseInfoType();
                objCaseInfoType.setCaseTitle(details.getStrTitle());
                objCaseInfoType.setCaseID(details.getStrCaseId());
                objCaseInfoType.setCaseType(details.getStrCaseType());
                if (details.getDtCreationDate() != null) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(details.getDtCreationDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setCreatedDate(createdDate);
                }
                objCaseInfoType.setPriority(details.getStrPriority());
                objCaseInfoType.setOwner(details.getStrOwner());
                objCaseInfoType.setReason(details.getStrReason());
                objCaseInfoType.setCondition(details.getCondition());
                objCaseInfoType.setQueueName(details.getStrQueue());
                if (details.getCloseDate() != null) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(details.getCloseDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    closeDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setClosedDate(closeDate);
                }

                objCaseInfoType.setAppend(details.getIsAppend());
                objCaseInfoType.setCaseObjid(details.getCaseObjid());
                objCaseInfoType.setRoadNumber(details.getStrAssetNumber());
                objCaseInfoType.setCustomerId(details.getStrAssetHeader());
                objCaseInfoType.setCustomerName(details.getStrcustomerName());
                caseInfotypeList.add(objCaseInfoType);
            }

        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return caseInfotypeList;

    }

    @POST
    @Path(OMDConstants.GET_VIEW_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ViewCaseResponseType> getViewCases(CaseRequestType objCaseRequestType) throws RMDServiceException {
        String customerName;
        String roadNo;
        String roadInitial;
        String caseType;
        String noOfDays;
        String caseLike;
        String appendFlag;
        List<ViewCaseResponseType> caseInfotypeList = new ArrayList<ViewCaseResponseType>();
        ViewCaseResponseType objCaseInfoType = new ViewCaseResponseType();
        FindCaseServiceVO objFindCaseServiceVO = new FindCaseServiceVO();
        List<ViewCaseVO> objSelectCaseHomeVOList = new ArrayList<ViewCaseVO>();
        try {

            if (null != objCaseRequestType.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCustomerId())) {
                customerName = objCaseRequestType.getCustomerId();
                objFindCaseServiceVO.setStrCustomerId(customerName);
            }
            if (null != objCaseRequestType.getAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetNumber())) {
                roadNo = objCaseRequestType.getAssetNumber();
                objFindCaseServiceVO.setStrAssetNumber(roadNo);
            }
            if (null != objCaseRequestType.getAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetGrpName())) {
                roadInitial = objCaseRequestType.getAssetGrpName();
                objFindCaseServiceVO.setStrAssetGrpName(roadInitial);
            }
            if (null != objCaseRequestType.getCaseType()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCaseType())) {
                caseType = objCaseRequestType.getCaseType();
                objFindCaseServiceVO.setStrCaseType(caseType);
            }
            if (null != objCaseRequestType.getNoOfDays()) {
                noOfDays = objCaseRequestType.getNoOfDays();
                objFindCaseServiceVO.setNoOfDays(noOfDays);
            }
            if (null != objCaseRequestType.getCaseLike()) {
                caseLike = objCaseRequestType.getCaseLike();
                objFindCaseServiceVO.setCaseID(caseLike);
            }
            if (null != objCaseRequestType.getAppendFlag()) {
                appendFlag = objCaseRequestType.getAppendFlag();
                objFindCaseServiceVO.setAppendFlag(appendFlag);
            }    
            objSelectCaseHomeVOList = objCaseEoaServiceIntf.getViewCases(objFindCaseServiceVO);
            if (objSelectCaseHomeVOList != null && objSelectCaseHomeVOList.size() > 0) {
                caseInfotypeList = new ArrayList<ViewCaseResponseType>(objSelectCaseHomeVOList.size());
            }
            for (ViewCaseVO details : objSelectCaseHomeVOList) {
                objCaseInfoType = new ViewCaseResponseType();
                objCaseInfoType.setStrTitle(details.getStrTitle());
                objCaseInfoType.setStrCaseId(details.getStrCaseId());
                objCaseInfoType.setCaseType(details.getCaseType());
                if (details.getDtCreationDate() != null) {
                    objCaseInfoType.setDtCreationDate(details.getDtCreationDate());
                }
                objCaseInfoType.setStrPriority(details.getStrPriority());
                objCaseInfoType.setStrOwner(details.getStrOwner());
                objCaseInfoType.setStrReason(details.getStrReason());
                objCaseInfoType.setCondition(details.getCondition());
                objCaseInfoType.setStrQueue(details.getStrQueue());
                if (details.getCloseDate() != null) {
                    objCaseInfoType.setCloseDate(details.getCloseDate());
                }
                objCaseInfoType.setIsAppend(details.getIsAppend());
                objCaseInfoType.setCaseObjid(details.getCaseObjid());
                caseInfotypeList.add(objCaseInfoType);
            }
            objSelectCaseHomeVOList = null;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return caseInfotypeList;

    }

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseSolutionRequestType
     * @throws:RMDServiceException
     * @Description: This method is used for delivering recommendations to a case by calling
     *               deliverRx() of CaseEoaServiceIntf.java
     */

    @POST
    @Path(OMDConstants.DELIVER_RX)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String deliverRx(CaseSolutionRequestType objCaseSolutionRequestType) throws RMDServiceException {
        String result = RMDCommonConstants.SUCCESS;
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
        try {

            if (null != objCaseSolutionRequestType) {

                RxDetailsVO objRxDetailsVO = new RxDetailsVO();

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                        .getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getUrgRepair())) {
                        objRxDetailsVO.setUrgency(objCaseSolutionRequestType.getObjSolutionDetailType().getUrgRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_URGENCY);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.URGENCY_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                        .getEstmTimeRepair())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getEstmTimeRepair())) {
                        objRxDetailsVO.setEstRepTime(objCaseSolutionRequestType.getObjSolutionDetailType()
                                .getEstmTimeRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ESTIMATED_REPAIR_TIME);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.ESTREPAIR_VALUE_NOT_PROVIDED);
                }

                String isFromDeliver = objCaseSolutionRequestType.getIsFromdeliver();
                if (RMDCommonUtility.isNullOrEmpty(isFromDeliver)
                        || RMDCommonConstants.STR_TRUE.equalsIgnoreCase(isFromDeliver)) {
                    if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getVersion())) {
                        objRxDetailsVO
                                .setStrVersion(objCaseSolutionRequestType.getObjSolutionDetailType().getVersion());
                    }
                }
                objDelvInfoEoaServiceVO.setIsFromDeliver(isFromDeliver);

                objDelvInfoEoaServiceVO.setRxDetailsVO(objRxDetailsVO);

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCaseObjId())) {
                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getCaseObjId())) {

                        objDelvInfoEoaServiceVO.setCaseObjId(objCaseSolutionRequestType.getCaseObjId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCaseID())) {
                    if (RMDCommonUtility.isAlphaNumeric(objCaseSolutionRequestType.getCaseID())) {
                        objDelvInfoEoaServiceVO.setStrCaseID(objCaseSolutionRequestType.getCaseID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getSolutionID())) {
                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getSolutionID())) {
                        objDelvInfoEoaServiceVO.setStrRxObjId(objCaseSolutionRequestType.getSolutionID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
                }

                objDelvInfoEoaServiceVO.setStrRecomNotes(objCaseSolutionRequestType.getRecomNotes());

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCustomerName())) {
                    objDelvInfoEoaServiceVO.setCustomerName(objCaseSolutionRequestType.getCustomerName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUserName())) {
                    objDelvInfoEoaServiceVO.setStrUserName(objCaseSolutionRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USERNAME_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getEstmRepTime())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseSolutionRequestType.getEstmRepTime())) {
                        objDelvInfoEoaServiceVO.setStrEstmRepTime(objCaseSolutionRequestType.getEstmRepTime());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ESTIMATED_REPAIR_TIME);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.ESTREPAIR_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseSolutionRequestType.getUrgRepair())) {
                        objDelvInfoEoaServiceVO.setStrUrgRepair(objCaseSolutionRequestType.getUrgRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_URGENCY);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.URGENCY_VALUE_NOT_PROVIDED);
                }
                
                if (null!=objCaseSolutionRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objCaseSolutionRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	objDelvInfoEoaServiceVO.setLstAttachment(arlDelvDocVO);
                }

                result = objCaseEoaServiceIntf.deliverRx(objDelvInfoEoaServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            LOG.error("ERROR OCCURED AT  deliverRx() METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseSolutionRequestType
     * @throws:RMDServiceException
     * @Description: This method is used to modify the Recommendation by calling modifyRx() of
     *               CaseEoaServiceIntf.java
     */
    @POST
    @Path(OMDConstants.MODIFY_RX_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String modifyRx(CaseSolutionRequestType objCaseSolutionRequestType) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
        try {

            if (null != objCaseSolutionRequestType) {

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCaseID())) {
                    if (RMDCommonUtility.isAlphaNumeric(objCaseSolutionRequestType.getCaseID())) {
                        objDelvInfoEoaServiceVO.setStrCaseID(objCaseSolutionRequestType.getCaseID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getSolutionID())) {
                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getSolutionID())) {
                        objDelvInfoEoaServiceVO.setStrRxObjId(objCaseSolutionRequestType.getSolutionID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
                }

                objDelvInfoEoaServiceVO.setStrRecomNotes(objCaseSolutionRequestType.getRecomNotes());

                if (0 != objCaseSolutionRequestType.getFdbkObjId()) {
                    objDelvInfoEoaServiceVO.setFdbkObjId(objCaseSolutionRequestType.getFdbkObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.FDBK_OBJID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCustomerName())) {
                    objDelvInfoEoaServiceVO.setCustomerName(objCaseSolutionRequestType.getCustomerName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUserName())) {
                    objDelvInfoEoaServiceVO.setStrUserName(objCaseSolutionRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USERNAME_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getEstmRepTime())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseSolutionRequestType.getEstmRepTime())) {
                        objDelvInfoEoaServiceVO.setStrEstmRepTime(objCaseSolutionRequestType.getEstmRepTime());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ESTIMATED_REPAIR_TIME);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.ESTREPAIR_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseSolutionRequestType.getUrgRepair())) {
                        objDelvInfoEoaServiceVO.setStrUrgRepair(objCaseSolutionRequestType.getUrgRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_URGENCY);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.URGENCY_VALUE_NOT_PROVIDED);
                }
                
                if (null!=objCaseSolutionRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objCaseSolutionRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	objDelvInfoEoaServiceVO.setLstAttachment(arlDelvDocVO);
                }

                result = objCaseEoaServiceIntf.modifyRx(objDelvInfoEoaServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            LOG.error("ERROR OCCURED AT modifyRx() METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseSolutionRequestType
     * @throws:RMDServiceException
     * @Description: This method is used for This method is used to replace Recommendation by
     *               calling deliverRx() of CaseEoaServiceIntf.java
     */
    @POST
    @Path(OMDConstants.REPLACE_RX_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String replaceRx(CaseSolutionRequestType objCaseSolutionRequestType) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
        try {
            if (null != objCaseSolutionRequestType) {

                RxDetailsVO objRxDetailsVO = new RxDetailsVO();

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                        .getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getUrgRepair())) {
                        objRxDetailsVO.setUrgency(objCaseSolutionRequestType.getObjSolutionDetailType().getUrgRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_URGENCY);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.URGENCY_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                        .getEstmTimeRepair())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getEstmTimeRepair())) {
                        objRxDetailsVO.setEstRepTime(objCaseSolutionRequestType.getObjSolutionDetailType()
                                .getEstmTimeRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ESTIMATED_REPAIR_TIME);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.ESTREPAIR_VALUE_NOT_PROVIDED);
                }

                String isFromDeliver = objCaseSolutionRequestType.getIsFromdeliver();
                if (RMDCommonUtility.isNullOrEmpty(isFromDeliver)
                        || RMDCommonConstants.STR_TRUE.equalsIgnoreCase(isFromDeliver)) {
                    if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getObjSolutionDetailType()
                            .getVersion())) {
                        objRxDetailsVO
                                .setStrVersion(objCaseSolutionRequestType.getObjSolutionDetailType().getVersion());
                    }
                }
                objDelvInfoEoaServiceVO.setIsFromDeliver(isFromDeliver);

                objDelvInfoEoaServiceVO.setRxDetailsVO(objRxDetailsVO);

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCaseObjId())) {
                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getCaseObjId())) {

                        objDelvInfoEoaServiceVO.setCaseObjId(objCaseSolutionRequestType.getCaseObjId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCaseID())) {
                    if (RMDCommonUtility.isAlphaNumeric(objCaseSolutionRequestType.getCaseID())) {
                        objDelvInfoEoaServiceVO.setStrCaseID(objCaseSolutionRequestType.getCaseID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getSolutionID())) {
                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getSolutionID())) {
                        objDelvInfoEoaServiceVO.setStrRxObjId(objCaseSolutionRequestType.getSolutionID());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
                }

                objDelvInfoEoaServiceVO.setStrRecomNotes(objCaseSolutionRequestType.getRecomNotes());

                if (0L != objCaseSolutionRequestType.getFdbkObjId()) {
                    objDelvInfoEoaServiceVO.setFdbkObjId(objCaseSolutionRequestType.getFdbkObjId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.FDBK_OBJID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getCustomerName())) {
                    objDelvInfoEoaServiceVO.setCustomerName(objCaseSolutionRequestType.getCustomerName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUserName())) {
                    objDelvInfoEoaServiceVO.setStrUserName(objCaseSolutionRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USERNAME_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUserId())) {
                    objDelvInfoEoaServiceVO.setUserId(objCaseSolutionRequestType.getUserId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USER_ID_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getEstmRepTime())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseSolutionRequestType.getEstmRepTime())) {
                        objDelvInfoEoaServiceVO.setStrEstmRepTime(objCaseSolutionRequestType.getEstmRepTime());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_ESTIMATED_REPAIR_TIME);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.ESTREPAIR_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseSolutionRequestType.getUrgRepair())) {
                        objDelvInfoEoaServiceVO.setStrUrgRepair(objCaseSolutionRequestType.getUrgRepair());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_URGENCY);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.URGENCY_VALUE_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseSolutionRequestType.getDelvrdRxObjId())) {

                    if (RMDCommonUtility.isNumeric(objCaseSolutionRequestType.getDelvrdRxObjId())) {
                        objDelvInfoEoaServiceVO.setDelvrdRxObjId(objCaseSolutionRequestType.getDelvrdRxObjId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_DELIVERED_RX_OBJID);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.DELIVERED_RX_OBJID_NOT_PROVIDED);
                }
                
                if (null!=objCaseSolutionRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objCaseSolutionRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	objDelvInfoEoaServiceVO.setLstAttachment(arlDelvDocVO);
                }
                result = objCaseEoaServiceIntf.replaceRx(objDelvInfoEoaServiceVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            LOG.error("ERROR OCCURED AT  replaceRx() METHOD OF CASERESOURCE ");
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching pendingFdbkServiceStatus by invoking
     *               CaseEoaServiceIntf.pendingFdbkServiceStatus() method.
     */
    @GET
    @Path(OMDConstants.PENDING_FDBK_SERIVICE_STATUS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SolutionDetailType> pendingFdbkServiceStatus(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        List<RecomDelvInfoServiceVO> alrDelvInfoEoaServiceVOs = null;
        List<SolutionDetailType> alrDetailTypes = new ArrayList<SolutionDetailType>();
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_ID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            alrDelvInfoEoaServiceVOs = objCaseEoaServiceIntf.pendingFdbkServiceStatus(caseId);
            for (RecomDelvInfoServiceVO RecomDelvInfoServiceVO : alrDelvInfoEoaServiceVOs) {
                SolutionDetailType objSolutionDetailType = new SolutionDetailType();
                objSolutionDetailType.setFdbkStatus(RecomDelvInfoServiceVO.getPendingFeedBack());
                objSolutionDetailType.setRxObjId(RecomDelvInfoServiceVO.getRxObjId());
                alrDetailTypes.add(objSolutionDetailType);
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  pendingFdbkServiceStatus() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return alrDetailTypes;
    }

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDWebException
     * @Description: This method is used for fetching ServiceReqId by invoking
     *               CaseEoaServiceIntf.getServiceReqIdStatus() method.
     */
    @GET
    @Path(OMDConstants.GET_SERVICE_REQUESTID_STATUS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getServiceReqIdStatus(@Context UriInfo ui) throws RMDServiceException {

        String fdbkObjId = null;
        String serviceReqIdStatus = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FDBK_OBJID)) {
                fdbkObjId = queryParams.getFirst(OMDConstants.FDBK_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(fdbkObjId)) {
                throw new OMDInValidInputException(OMDConstants.FDBK_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(fdbkObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_FDBK_OBJID);
            }
            serviceReqIdStatus = objCaseEoaServiceIntf.getServiceReqIdStatus(fdbkObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getServiceReqIdStatus() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return serviceReqIdStatus;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDWebException
     * @Description: This method is used for fetching t2requestId by invoking
     *               CaseEoaServiceIntf.getT2Req() method.
     */
    @GET
    @Path(OMDConstants.GET_T2_REQUEST)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getT2Req(@Context UriInfo ui) throws RMDServiceException {

        String caseId = null;
        String t2RequestStatus = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_ID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            t2RequestStatus = objCaseEoaServiceIntf.getT2Req(caseId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getT2Req() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return t2RequestStatus;
    }

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDWebException
     * @Description: This method fetches Unit Ship Details by invoking
     *               CaseEoaServiceIntf.getUnitShipDetails() method.
     */
    @GET
    @Path(OMDConstants.GET_UNIT_SHIP_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getUnitShipDetails(@Context UriInfo ui) throws RMDServiceException {
        String caseObjId = null;
        String unitShipDetails = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
            unitShipDetails = objCaseEoaServiceIntf.getUnitShipDetails(caseObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getUnitShipDetails() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return unitShipDetails;
    }

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDWebException
     * @Description: This method is used for fetching case Score by invoking
     *               CaseEoaServiceIntf.getCaseScore() method.
     */
    @GET
    @Path(OMDConstants.GET_CASE_SCORE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SolutionExecutionResponseType> getCaseScore(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        List<SolutionExecutionResponseType> arlExecutionResponseTypes = new ArrayList<SolutionExecutionResponseType>();
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_ID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            List<RecomDelvInfoServiceVO> arlDelvInfoEoaServiceVO = objCaseEoaServiceIntf.getCaseScore(caseId);
            SolutionExecutionResponseType objsolutionExecutionResponseType = null;
            for (RecomDelvInfoServiceVO RecomDelvInfoServiceVO : arlDelvInfoEoaServiceVO) {
                objsolutionExecutionResponseType = new SolutionExecutionResponseType();
                objsolutionExecutionResponseType.setRxCaseId(RecomDelvInfoServiceVO.getStrRxCaseID());
                objsolutionExecutionResponseType.setRxTitle(RecomDelvInfoServiceVO.getStrRxTitle());
                arlExecutionResponseTypes.add(objsolutionExecutionResponseType);
            }

        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getCaseScore() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlExecutionResponseTypes;
    }

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDWebException
     * @Description: This method is used for fetching ready to deliver status by invoking
     *               CaseEoaServiceIntf.getReadyToDelv() method.
     */
    @GET
    @Path(OMDConstants.GET_READY_TO_DELIVER)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getReadyToDelv(@Context UriInfo ui) throws RMDServiceException {
        String rxObjId = null;
        String readyTodelvStatus = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RX_OBJID)) {
                rxObjId = queryParams.getFirst(OMDConstants.RX_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(rxObjId)) {
                throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(rxObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
            }
            readyTodelvStatus = objCaseEoaServiceIntf.getReadyToDelv(rxObjId);

        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getReadyToDelv() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return readyTodelvStatus;
    }

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching DeliveryDate by invoking
     *               CaseEoaServiceIntf.getDelvDateForRx() method.
     */
    @GET
    @Path(OMDConstants.GET_DELV_DATE_FOR_RX)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getDelvDateForRx(@Context UriInfo ui) throws RMDServiceException {
        String rxObjId = null;
        String caseObjId = null;
        String deliveryDate = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RX_OBJID)) {
                rxObjId = queryParams.getFirst(OMDConstants.RX_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(rxObjId)) {
                throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(rxObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
            deliveryDate = objCaseEoaServiceIntf.getDelvDateForRx(caseObjId, rxObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getDelvDateForRx() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return deliveryDate;
    }

    /**
     * @Author:
     * @param:String caseId,String rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching customer feed back object Id by invoking
     *               CaseEoaServiceIntf.getCustomerFdbkObjId() method.
     */

    @GET
    @Path(OMDConstants.GET_PENDING_RECOMMENDATIONS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SolutionExecutionResponseType getPendingRcommendation(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        SolutionExecutionResponseType objSolutionExecutionResponseType = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_ID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isAlphaNumeric(caseId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEID);
            }
            RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = objCaseEoaServiceIntf.getPendingRcommendation(caseId);
            if (null != objDelvInfoEoaServiceVO) {
                objSolutionExecutionResponseType = new SolutionExecutionResponseType();
                objSolutionExecutionResponseType.setSolutionId(objDelvInfoEoaServiceVO.getStrRxObjId());
                objSolutionExecutionResponseType.setRxTitle(objDelvInfoEoaServiceVO.getStrRxTitle());
                objSolutionExecutionResponseType.setRxCaseId(objDelvInfoEoaServiceVO.getRxCaseId());
                objSolutionExecutionResponseType.setFdbkObjId(objDelvInfoEoaServiceVO.getFdbkObjId());
                objSolutionExecutionResponseType.setCaseObjId(objDelvInfoEoaServiceVO.getCaseObjId());
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getCustomerFdbkObjId() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objSolutionExecutionResponseType;
    }

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for checking Whether delivery mechanism is present for
     *               particular Customer are not by invoking
     *               CaseEoaServiceInf.checkForDelvMechanism() method.
     */
    @GET
    @Path(OMDConstants.CHECK_FOR_DELV_MECHANISM)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String checkForDelvMechanism(@Context UriInfo ui) throws RMDServiceException {
        String result = null;
        String customerName = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CUSTOMER_NAME)) {
                customerName = queryParams.getFirst(OMDConstants.CUSTOMER_NAME);
            }
            if (RMDCommonUtility.isNullOrEmpty(customerName)) {
                throw new OMDInValidInputException(OMDConstants.CUSTOMERNAME_NOT_PROVIDED);
            }
            result = objCaseEoaServiceIntf.checkForDelvMechanism(customerName);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  checkForDelvMechanism() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid,String fromScreen
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching msdcNotes by invoking
     *               CaseEoaServiceIntf.getMsdcNotes() method.
     */
    @GET
    @Path(OMDConstants.GET_MSDC_NOTES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getMsdcNotes(@Context UriInfo ui) throws RMDServiceException {
        String rxObjId = null;
        String caseObjId = null;
        String deliveryDate = null;
        String fromScreen = null;
        String custFdbkObjId = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RX_OBJID)) {
                rxObjId = queryParams.getFirst(OMDConstants.RX_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.FROM_SCREEN)) {
                fromScreen = queryParams.getFirst(OMDConstants.FROM_SCREEN);
            }
            if (queryParams.containsKey(OMDConstants.CUST_FDBK_OBJ_ID)) {
                custFdbkObjId = queryParams.getFirst(OMDConstants.CUST_FDBK_OBJ_ID);
            }
            if (RMDCommonConstants.CLOSURE_SCREEN.equalsIgnoreCase(fromScreen)) {
                if (RMDCommonUtility.isNullOrEmpty(custFdbkObjId)) {
                    throw new OMDInValidInputException(OMDConstants.CUST_FDBK_OBJ_ID_NOT_PROVIDED);
                }
            }else{
                if (RMDCommonUtility.isNullOrEmpty(rxObjId)) {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNumeric(rxObjId)) {
                    throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                }
            }
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
            deliveryDate = objCaseEoaServiceIntf.getMsdcNotes(caseObjId, rxObjId,fromScreen,custFdbkObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getMsdcNotes() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return deliveryDate;
    }

    /**
     * This method is used to Score and Close a particular Rx.
     * 
     * @param CaseRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.SCORERX_AND_CLOSECASE)
    @Consumes(MediaType.APPLICATION_XML)
    public void scoreAndCloseCase(RxDetailsType rxDetailsType) throws RMDServiceException {
        ScoreRxEoaVO scoreRxEoaVO = null;
        try {
            if (rxDetailsType != null) {
                final String rxCaseId = rxDetailsType.getRxCaseID();
                final String rxScoreCode = rxDetailsType.getRxScoreCode();
                final String caseId = rxDetailsType.getCaseID();
                final String rxNote =EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxDetailsType.getRxNote()));
                final String fdbkType = rxDetailsType.getFdbkType();
                if (null == rxCaseId || rxCaseId.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(rxCaseId))) {
                    throw new OMDInValidInputException(OMDConstants.RX_CASEID_NOT_PROVIDED);
                }
                if (null == rxScoreCode || rxScoreCode.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(rxScoreCode))) {
                    throw new OMDInValidInputException(OMDConstants.SCORE_CODE_NOT_PROVIDED);
                }
                scoreRxEoaVO = new ScoreRxEoaVO();
                scoreRxEoaVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                scoreRxEoaVO.setRxCaseId(rxCaseId);
                scoreRxEoaVO.setRxScoreCode(rxScoreCode);
                scoreRxEoaVO.setCaseId(caseId);
                scoreRxEoaVO.setRxNote(rxNote);
                scoreRxEoaVO.setGoodFdbk(fdbkType);
                if (rxDetailsType.isScoreRx()) {
                    scoreRxEoaVO.setScoreRx(OMDConstants.TRUE);
                }
                if (null != rxDetailsType.getRxScoreCode()) {
                    String[] successAndCodeArray = new String[2];
                    successAndCodeArray = rxDetailsType.getRxScoreCode().split(OMDConstants.HYPHEN);
                    scoreRxEoaVO.setCaseSuccess(successAndCodeArray[0]);
                    if (successAndCodeArray.length > 1) {
                        scoreRxEoaVO.setMissCode(successAndCodeArray[1]);
                    }
                }
                scoreRxEoaVO.setRepairCodes(rxDetailsType.getRepaircodeList());
                objCaseEoaServiceIntf.scoreRx(scoreRxEoaVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used to Close a particular Rx after it is scored.
     * 
     * @param CaseRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.CLOSE_CASE)
    @Consumes(MediaType.APPLICATION_XML)
    public void closeCase(RxDetailsType objCaseRequestType) throws RMDServiceException {
        try {
            if (objCaseRequestType != null) {
                objCaseEoaServiceIntf.closeCase(objCaseRequestType.getCaseID(), getRequestHeader(OMDConstants.USERID));
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used to Score a particular Rx
     * 
     * @param RxDetailsType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.SCORE_RX)
    @Consumes(MediaType.APPLICATION_XML)
    public void scoreRx(RxDetailsType rxDetailsType) throws RMDServiceException {
        ScoreRxEoaVO scoreRxEoaVO = null;
        try {
            if (rxDetailsType != null) {
                final String rxCaseId = rxDetailsType.getRxCaseID();
                final String rxScoreCode = rxDetailsType.getRxScoreCode();
                final String caseId = rxDetailsType.getCaseID();
                final String rxNote = rxDetailsType.getRxNote();
                final String fdbkType = rxDetailsType.getFdbkType();

                if (null == rxCaseId || rxCaseId.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(rxCaseId))) {
                    throw new OMDInValidInputException(OMDConstants.RX_CASEID_NOT_PROVIDED);
                }
                if (null == rxScoreCode || rxScoreCode.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(rxScoreCode))) {
                    throw new OMDInValidInputException(OMDConstants.SCORE_CODE_NOT_PROVIDED);
                }
                scoreRxEoaVO = new ScoreRxEoaVO();
                scoreRxEoaVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                scoreRxEoaVO.setRxCaseId(rxCaseId);
                scoreRxEoaVO.setRxScoreCode(rxScoreCode);
                scoreRxEoaVO.setCaseId(caseId);
                scoreRxEoaVO.setRxNote(rxNote);
                scoreRxEoaVO.setGoodFdbk(fdbkType);
                if (rxDetailsType.isScoreRx()) {
                    scoreRxEoaVO.setScoreRx(OMDConstants.TRUE);
                }
                if (null != rxDetailsType.getRxScoreCode()) {
                    String[] successAndCodeArray = new String[2];
                    successAndCodeArray = rxDetailsType.getRxScoreCode().split(OMDConstants.HYPHEN);
                    scoreRxEoaVO.setCaseSuccess(successAndCodeArray[0]);
                    if (successAndCodeArray.length > 1) {
                        scoreRxEoaVO.setMissCode(successAndCodeArray[1]);
                    }
                }
                scoreRxEoaVO.setRepairCodes(rxDetailsType.getRepaircodeList());
                objCaseEoaServiceIntf.scoreRx(scoreRxEoaVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for fetching cases repair codes
     * 
     * @param String
     *            userId
     * @return List<RxDetailsType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASE_REPAIR_CODE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getCaseRepairCodes(@PathParam(OMDConstants.CASE_Id) final String caseId)
            throws RMDServiceException {
        List<RepairCodeDetailsType> repaircodesList = new ArrayList<RepairCodeDetailsType>();
        RepairCodeEoaDetailsVO repairCodeInputType = new RepairCodeEoaDetailsVO();
        List<RepairCodeEoaDetailsVO> repairCodeResponse = null;
        try {

            if (null != caseId && !caseId.equals(OMDConstants.EMPTY_STRING)) {
                repairCodeInputType.setCaseId(caseId);
                repairCodeResponse = objCaseEoaServiceIntf.getCaseRepairCodes(repairCodeInputType);
                for (RepairCodeEoaDetailsVO repairCodeDetail : repairCodeResponse) {
                    RepairCodeDetailsType currentRepairCode = new RepairCodeDetailsType();
                    currentRepairCode.setRepaidCodeDesc(repairCodeDetail.getRepairCodeDesc());
                    currentRepairCode.setRepairCode(repairCodeDetail.getRepairCode());
                    currentRepairCode.setRepairCodeId(repairCodeDetail.getRepairCodeId());
                    repaircodesList.add(currentRepairCode);
                }

                return repaircodesList;
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INPUTS_NOT_GIVEN),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.INPUTS_NOT_GIVEN),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used for fetching cases repair codes
     * 
     * @param String
     *            userId
     * @return List<RepairCodeDetailsType>
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_REPAIR_CODE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getRepairCodes(@Context final UriInfo uriParam) throws RMDServiceException {
        List<RepairCodeDetailsType> repaircodesList = new ArrayList<RepairCodeDetailsType>();
        RepairCodeEoaDetailsVO repairCodeInputType = new RepairCodeEoaDetailsVO();
        List<RepairCodeEoaDetailsVO> repairCodeResponse = null;
        MultivaluedMap<String, String> queryParams = null;
        try {

            queryParams = uriParam.getQueryParameters();
            if (RMDCommonUtility.isSpecialCharactersFound(queryParams.getFirst(OMDConstants.VALUE))) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CHARACTER);
            }

            repairCodeInputType.setModel(queryParams.getFirst(OMDConstants.MODEL));
            repairCodeInputType.setSearchBy(queryParams.getFirst(OMDConstants.SEARCH_BY));
            repairCodeInputType.setSearchValue(queryParams.getFirst(OMDConstants.VALUE));
            repairCodeResponse = objCaseEoaServiceIntf.getRepairCodes(repairCodeInputType);
            for (RepairCodeEoaDetailsVO repairCodeDetail : repairCodeResponse) {
                RepairCodeDetailsType currentRepairCode = new RepairCodeDetailsType();
                currentRepairCode.setRepaidCodeDesc(repairCodeDetail.getRepairCodeDesc());
                currentRepairCode.setRepairCode(repairCodeDetail.getRepairCode());
                currentRepairCode.setRepairCodeId(repairCodeDetail.getRepairCodeId());
                repaircodesList.add(currentRepairCode);
            }
            return repaircodesList;

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used to add an RepairCode for a particular case
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.REMOVE_REPAIRCODE_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void removeRepairCodeToCase(RepairCodeDetailsType removeRepairCodeDetails) throws RMDServiceException {
        CaseRepairCodeEoaVO addRepairCodeVO = null;
        try {
            addRepairCodeVO = new CaseRepairCodeEoaVO();
            if (null != removeRepairCodeDetails) {
                if (null == removeRepairCodeDetails.getCaseID() || removeRepairCodeDetails.getCaseID().isEmpty()) {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                addRepairCodeVO.setCaseId(removeRepairCodeDetails.getCaseID());
                addRepairCodeVO.setRepairCodeIdList(removeRepairCodeDetails.getRepaircodeList());
                objCaseEoaServiceIntf.removeCaseRepairCodes(addRepairCodeVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
    }

    /**
     * This method is used to add an RepairCode for a particular case
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.ADD_REPAIRCODE_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addRepairCodeToCase(RepairCodeDetailsType addRepaircodeDetails) throws RMDServiceException {
        CaseRepairCodeEoaVO addRepairCodeVO = null;
        try {
            addRepairCodeVO = new CaseRepairCodeEoaVO();
            if (null != addRepaircodeDetails) {
                if (null == addRepaircodeDetails.getCaseID() || addRepaircodeDetails.getCaseID().isEmpty()) {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                addRepairCodeVO.setUserId(getRequestHeader(OMDConstants.USERID));
                addRepairCodeVO.setCaseId(addRepaircodeDetails.getCaseID());
                addRepairCodeVO.setRepairCodeIdList(addRepaircodeDetails.getRepaircodeList());
                if (addRepaircodeDetails.isAddAndClose()) {
                    addRepairCodeVO.setAddAndClose(OMDConstants.TRUE);
                }
                objCaseEoaServiceIntf.addCaseRepairCodes(addRepairCodeVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<RxStatusHistoryResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the RxStatus History for case by invoking
     *               caseseoaserviceimpl.getRxstatusHistory() method.
     */
    @GET
    @Path(OMDConstants.GET_RX_STATUS_HISTORY)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RxStatusHistoryResponseType> getRxstatusHistory(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<RxStatusHistoryVO> objRxStatusHistoryVOlist = null;
        List<RxStatusHistoryResponseType> objRxStatusHistoryResponseTypeList = new ArrayList<RxStatusHistoryResponseType>();
        String serviceReqId = OMDConstants.SERVICE_REQ_ID;
        MultivaluedMap<String, String> queryParams = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar rxStatusDate;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.SERVICE_REQ_ID)) {
                    serviceReqId = queryParams.getFirst(OMDConstants.SERVICE_REQ_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.SERVICERQID_NOT_PROVIDED);
                }
                objRxStatusHistoryVOlist = objCaseEoaServiceIntf.getRxstatusHistory(serviceReqId);
                if (RMDCommonUtility.isCollectionNotEmpty(objRxStatusHistoryVOlist)) {
                    for (RxStatusHistoryVO obj : objRxStatusHistoryVOlist) {
                        RxStatusHistoryResponseType objRxStsHstResponseType = new RxStatusHistoryResponseType();
                        if (null != obj.getRxStatusDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(obj.getRxStatusDate());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            rxStatusDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                            objRxStsHstResponseType.setRxStatusDate(rxStatusDate);
                        }
                        objRxStsHstResponseType.setStatus(obj.getStatus());
                        objRxStsHstResponseType.setComments(obj.getComments());
                        objRxStatusHistoryResponseTypeList.add(objRxStsHstResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objRxStatusHistoryResponseTypeList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<RxHistoryVOResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the Rx History for case by invoking
     *               caseseoaserviceimpl.getRxHistory() method.
     */
    @GET
    @Path(OMDConstants.GET_RX_HISTORY)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RxHistoryResponseType> getRxHistory(@Context final UriInfo uriParam) throws RMDServiceException {
        List<RxHistoryVO> objRxHistoryVOlist = null;
        List<RxHistoryResponseType> objRxHistoryVOResponseTypeList = new ArrayList<RxHistoryResponseType>();
        String caseObjId = OMDConstants.CASE_OBJID;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar rxStatusDate;
        MultivaluedMap<String, String> queryParams = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                    caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                objRxHistoryVOlist = objCaseEoaServiceIntf.getRxHistory(caseObjId);
                if (RMDCommonUtility.isCollectionNotEmpty(objRxHistoryVOlist)) {
                    for (RxHistoryVO obj : objRxHistoryVOlist) {
                        RxHistoryResponseType objRxHistoryVOResponseType = new RxHistoryResponseType();
                        objRxHistoryVOResponseType.setRxCaseId(obj.getRxCaseId());
                        objRxHistoryVOResponseType.setRxFeedback(obj.getRxFeedback());
                        objRxHistoryVOResponseType.setRxSuccess(obj.getRxSuccess());
                        objRxHistoryVOResponseType.setMissCode(obj.getMissCode());
                        objRxHistoryVOResponseType.setGoodFeedback(obj.getGoodFeedback());
                        objRxHistoryVOResponseType.setCustFdbkObjId(obj.getCustFdbkObjId());
                        objRxHistoryVOResponseType.setServiceRequestId(obj.getServiceRequestId());
                        if (null != obj.getRxCloseDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(obj.getRxCloseDate());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            rxStatusDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                            objRxHistoryVOResponseType.setRxCloseDate(rxStatusDate);
                        }
                        objRxHistoryVOResponseTypeList.add(objRxHistoryVOResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objRxHistoryVOResponseTypeList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CustomerFeedbackResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the ServiceReqId & CustFdbkObjId for case by invoking
     *               caseseoaserviceimpl.getServiceReqId() method.
     */
    @GET
    @Path(OMDConstants.GET_SERVICE_REQ_ID)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CustomerFeedbackResponseType> getServiceReqId(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<CustomerFdbkVO> objCustomerFdbkList = null;
        MultivaluedMap<String, String> queryParams = null;
        String caseObjId = OMDConstants.CASE_OBJID;
        List<CustomerFeedbackResponseType> objCustomerFeedbackResponseTypeList = new ArrayList<CustomerFeedbackResponseType>();

        try {
            queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                    caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                objCustomerFdbkList = objCaseEoaServiceIntf.getServiceReqId(caseObjId);
                if (RMDCommonUtility.isCollectionNotEmpty(objCustomerFdbkList)) {
                    for (CustomerFdbkVO obj : objCustomerFdbkList) {
                        CustomerFeedbackResponseType objCusFdbkResponseType = new CustomerFeedbackResponseType();
                        objCusFdbkResponseType.setCustFdbkObjId(obj.getCustFdbkObjId());
                        objCusFdbkResponseType.setServiceReqId(obj.getServiceReqId());
                        objCustomerFeedbackResponseTypeList.add(objCusFdbkResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objCustomerFeedbackResponseTypeList;

    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method fetches the Good Feedback for case by invoking
     *               caseseoaserviceimpl.getClosureFdbk() method.
     */
    @GET
    @Path(OMDConstants.GET_CLOSURE_FDBK)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getClosureFdbk(@Context final UriInfo uriParam) throws RMDServiceException {
        String rxCaseId = OMDConstants.RX_CASE_ID;
        MultivaluedMap<String, String> queryParams = null;
        String objClosureFdbk = null;
        try {
            queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.RX_CASE_ID)) {
                    rxCaseId = queryParams.getFirst(OMDConstants.RX_CASE_ID);
                    objClosureFdbk = objCaseEoaServiceIntf.getClosureFdbk(rxCaseId);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objClosureFdbk;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CloseOutRepairCodesResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the CloseOut Repair Codes for case by invoking
     *               caseseoaserviceimpl.getCloseOutRepairCode() method.
     */
    @GET
    @Path(OMDConstants.GET_CLOSEOUT_REPAIR_CODE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CloseOutRepairCodesResponseType> getCloseOutRepairCode(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<CloseOutRepairCodeVO> objCloseOutRepairCodeVOlist = null;
        List<CloseOutRepairCodesResponseType> objCloseOutRepairCodesResponseTypeList = new ArrayList<CloseOutRepairCodesResponseType>();
        String custFdbkObjId = OMDConstants.CUST_FDBK_OBJ_ID;
        String serviceReqId = OMDConstants.SERVICE_REQ_ID;
        MultivaluedMap<String, String> queryParams = null;
        try {

            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUST_FDBK_OBJ_ID)) {
                    custFdbkObjId = queryParams.getFirst(OMDConstants.CUST_FDBK_OBJ_ID);
                    serviceReqId = queryParams.getFirst(OMDConstants.SERVICE_REQ_ID);
                }
                objCloseOutRepairCodeVOlist = objCaseEoaServiceIntf.getCloseOutRepairCode(custFdbkObjId, serviceReqId);
                if (RMDCommonUtility.isCollectionNotEmpty(objCloseOutRepairCodeVOlist)) {
                    for (CloseOutRepairCodeVO obj : objCloseOutRepairCodeVOlist) {
                        CloseOutRepairCodesResponseType objCloseOutRepairCodesResponseType = new CloseOutRepairCodesResponseType();
                        objCloseOutRepairCodesResponseType.setId(obj.getId());
                        objCloseOutRepairCodesResponseType.setTask(obj.getTask());
                        objCloseOutRepairCodesResponseType.setFeedback(obj.getFeedback());
                        objCloseOutRepairCodesResponseType.setRepairCode(obj.getRepairCode());
                        objCloseOutRepairCodesResponseType.setDescription(obj.getDescription());
                        objCloseOutRepairCodesResponseType.setRepairCodeId(obj.getRepairCodeId());
                        /*Adding repair code list*/
                        if(RMDCommonUtility.isCollectionNotEmpty(obj.getRepairCodes())) {
							for(CaseRepairCodeVO repairCodeVO : obj.getRepairCodes()) {
	    						RepairCodeType repairCodeType = new RepairCodeType();
								repairCodeType.setRepairCode(repairCodeVO.getRepairCode());
	    						repairCodeType.setRepairCodeId(repairCodeVO.getRepairCodeId());
	    						repairCodeType.setRepairCodeDesc(repairCodeVO.getRepairCodeDescription());
	    						objCloseOutRepairCodesResponseType.addRepairCodes(repairCodeType);
	                        }
                        }
                        objCloseOutRepairCodesResponseTypeList.add(objCloseOutRepairCodesResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objCloseOutRepairCodesResponseTypeList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CloseOutRepairCodesResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the Attached Details for case by invoking
     *               caseseoaserviceimpl.getAttachedDetails() method.
     */
    @GET
    @Path(OMDConstants.GET_ATTACHED_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CloseOutRepairCodesResponseType> getAttachedDetails(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<CloseOutRepairCodeVO> objAttachedDetailslist = null;
        List<CloseOutRepairCodesResponseType> objCloseOutRepairCodesResponseTypeList = new ArrayList<CloseOutRepairCodesResponseType>();
        String caseId = OMDConstants.VIEW_CLOSURE_CASE_ID;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.VIEW_CLOSURE_CASE_ID)) {
                    caseId = queryParams.getFirst(OMDConstants.VIEW_CLOSURE_CASE_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                objAttachedDetailslist = objCaseEoaServiceIntf.getAttachedDetails(caseId);
                if (RMDCommonUtility.isCollectionNotEmpty(objAttachedDetailslist)) {
                    for (CloseOutRepairCodeVO obj : objAttachedDetailslist) {
                        CloseOutRepairCodesResponseType objCloseOutRepairCodesResponseType = new CloseOutRepairCodesResponseType();
                        objCloseOutRepairCodesResponseType.setId(obj.getId());
                        objCloseOutRepairCodesResponseType.setRepairCode(obj.getRepairCode());
                        objCloseOutRepairCodesResponseType.setDescription(obj.getDescription());
                        objCloseOutRepairCodesResponseTypeList.add(objCloseOutRepairCodesResponseType);
                    }
                }/*
                  * else { throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                  * }
                  */
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objCloseOutRepairCodesResponseTypeList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method fetches the Rx Notes for case by invoking
     *               caseseoaserviceimpl.getRxNote() method.
     */
    @GET
    @Path(OMDConstants.GET_RX_NOTE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getRxNote(@Context final UriInfo uriParam) throws RMDServiceException {
        String caseObjId = OMDConstants.CASE_OBJID;
        MultivaluedMap<String, String> queryParams = null;
        String rxNote = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                    caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                rxNote = objCaseEoaServiceIntf.getRxNote(caseObjId);
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return rxNote;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:CustomerFeedbackResponseType
     * @throws:RMDServiceException
     * @Description: This method fetches the closure details for case by invoking
     *               caseseoaserviceimpl.getClosureDetails() method.
     */
    @GET
    @Path(OMDConstants.GET_CLOSURE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CustomerFeedbackResponseType getClosureDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        CustomerFdbkVO objCustomerFdbk = new CustomerFdbkVO();
        MultivaluedMap<String, String> queryParams = null;
        String caseObjId = OMDConstants.CASE_OBJID;
        CustomerFeedbackResponseType objCustFdbkRespType = new CustomerFeedbackResponseType();
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                    caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                objCustomerFdbk = objCaseEoaServiceIntf.getClosureDetails(caseObjId);
                if (!RMDCommonUtility.checkNull(objCustomerFdbk)) {
                    objCustFdbkRespType.setCustFdbkObjId(objCustomerFdbk.getCustFdbkObjId());
                    objCustFdbkRespType.setServiceReqId(objCustomerFdbk.getServiceReqId());
                    objCustFdbkRespType.setCaseSuccess(objCustomerFdbk.getCaseSuccess());
                    objCustFdbkRespType.setIsRxPresent(objCustomerFdbk.getIsRxPresent());
                    objCustFdbkRespType.setRxCloseDate(objCustomerFdbk.getRxCloseDate());
                    objCustFdbkRespType.setRxFdbk(objCustomerFdbk.getRxFdbk());
                    objCustFdbkRespType.setRxCaseId(objCustomerFdbk.getRxCaseId());

                }
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objCustFdbkRespType;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method does eservice validation invoking doEserviceValidation()method in
     *               CaseEOAServiceImpl.java
     */
    @GET
    @Path(OMDConstants.DO_ESERVICE_VALIDATION)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String doEserviceValidation(@Context final UriInfo uriParam) throws RMDServiceException {
        String result = null;
        String caseObjId;
        String rxCaseId;
        MultivaluedMap<String, String> queryParams = null;
        ScoreRxEoaVO objScoreRxEoaVO = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                objScoreRxEoaVO = new ScoreRxEoaVO();
                if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                    caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
                    objScoreRxEoaVO.setCaseObjid(caseObjId);
                } else {
                    result = OMDConstants.CASEID_NOT_PROVIDED;
                }
                if (queryParams.containsKey(OMDConstants.RX_CASE_ID)) {
                    rxCaseId = queryParams.getFirst(OMDConstants.RX_CASE_ID);
                    objScoreRxEoaVO.setRxCaseId(rxCaseId);
                } else {
                    result = OMDConstants.RX_CASE_ID_NOT_PROVIDED;
                }
                result = objCaseEoaServiceIntf.doEserviceValidation(objScoreRxEoaVO);
            }
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return result;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for checking Controller Config by calling
     *               checkForContollerConfig() method in CaseEOAServiceImpl.java
     */

    @GET
    @Path(OMDConstants.CHECK_FOR_CONTOLLER_CONFIG)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String checkForContollerConfig(@Context final UriInfo uriParam) throws RMDServiceException {
        String rxObjid = null;
        String caseObjId = null;
        String model = null;
        VehicleConfigVO objConfigVO = new VehicleConfigVO();
        String result = null;
        try {
            final MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RX_OBJID)) {
                rxObjid = queryParams.getFirst(OMDConstants.RX_OBJID);
            }
            if (!RMDCommonUtility.isNullOrEmpty(rxObjid)) {
                if (RMDCommonUtility.isNumeric(rxObjid)) {
                    objConfigVO.setRxObjId(rxObjid);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_RX_OBJID);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
            }
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (!RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                if (RMDCommonUtility.isNumeric(caseObjId)) {
                    objConfigVO.setCaseObjId(caseObjId);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }

            if (queryParams.containsKey(OMDConstants.MODEL)) {
                model = queryParams.getFirst(OMDConstants.MODEL);
            }

            if (!RMDCommonUtility.isNullOrEmpty(model)) {
                if (AppSecUtil.checkAlphaNumericHypen(model)) {
                    objConfigVO.setModel(model);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_MODEL);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.MODEL_NOT_PROVIDED);
            }
            result = objCaseEoaServiceIntf.checkForContollerConfig(objConfigVO);
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return result;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching list of Queues by calling getQueueList()
     *               method in QueueCasesServiceIntf.java
     */
    @GET
    @Path(OMDConstants.GET_QUEUE_LIST)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<QueueCaseResponseType> getQueueList(@Context final UriInfo ui) {
        LOG.debug("CaseResource : Inside getQueueList() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String roleId = OMDConstants.EMPTY_STRING;
        List<QueueCaseVO> lstQueueCaseVO = null;
        QueueCaseVO queueCaseVO = null;
        List<QueueCaseResponseType> lstQueueCaseResType = new ArrayList<QueueCaseResponseType>();
        try {

            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            int[] methodConstants = {RMDCommonConstants.AlPHA_NUMERIC, RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHABETS};
            if (queryParams.containsKey(OMDConstants.ROLE_ID)
            /*
             * && AppSecUtil.validateWebServiceInput(queryParams, nu66ll, methodConstants,
             * OMDConstants.USERId)
             */) {
                roleId = queryParams.getFirst(OMDConstants.ROLE_ID);
                lstQueueCaseVO = objQueueCasesServiceIntf.getQueueList(roleId);
                if (RMDCommonUtility.isCollectionNotEmpty(lstQueueCaseVO)) {
                    for (Iterator iterator = lstQueueCaseVO.iterator(); iterator.hasNext();) {
                        queueCaseVO = (QueueCaseVO) iterator.next();
                        QueueCaseResponseType queueCaseRespType = new QueueCaseResponseType();
                        queueCaseRespType.setQueueObjId(queueCaseVO.getQueueObjId());
                        queueCaseRespType.setQueueTitle(queueCaseVO.getQueueTitle());
                        lstQueueCaseResType.add(queueCaseRespType);
                    }
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("CaseResource : Inside getQueueList() method:::::End ");
        return lstQueueCaseResType;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching list of Cases Associated to particular Queue
     *               by calling getQueueCases() method in QueueCasesServiceIntf.java
     */

    @GET
    @Path(OMDConstants.GET_QUEUE_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<QueueCaseResponseType> getQueueCases(@Context final UriInfo ui) {
        LOG.debug("CaseResource : Inside getQueueCases() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String queueobjid = OMDConstants.EMPTY_STRING;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        List<QueueCaseVO> lstQueueCaseVO = null;
        QueueCaseVO queueCaseVO = null;
        List<QueueCaseResponseType> lstQueueCaseResType = null;
        QueueCaseResponseType queueCaseRespType = null;
        try {
            if (getRequestHeader(OMDConstants.LANGUAGE) != null)
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.QUEUE_OBJ_ID)) {
                queueobjid = queryParams.getFirst(OMDConstants.QUEUE_OBJ_ID);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                strCustomer = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            int[] methodConstants = {RMDCommonConstants.AlPHA_NUMERIC};

            lstQueueCaseVO = objQueueCasesServiceIntf.getQueueCases(queueobjid, strCustomer);

            if (RMDCommonUtility.isCollectionNotEmpty(lstQueueCaseVO)) {
                lstQueueCaseResType = new ArrayList<QueueCaseResponseType>(lstQueueCaseVO.size());
                for (Iterator iterator = lstQueueCaseVO.iterator(); iterator.hasNext();) {
                    queueCaseVO = (QueueCaseVO) iterator.next();
                    queueCaseRespType = new QueueCaseResponseType();
                    queueCaseRespType.setCaseId(queueCaseVO.getCaseId());
                    queueCaseRespType.setSitepartSerialNo(queueCaseVO.getSitepartSerialNo());
                    queueCaseRespType.setVehHdrCust(queueCaseVO.getVehHdrCust());
                    queueCaseRespType.setVehHdr(queueCaseVO.getVehHdr());
                    queueCaseRespType.setTitle(queueCaseVO.getTitle());
                    queueCaseRespType.setPriority(queueCaseVO.getPriority());
                    queueCaseRespType.setSeverity(queueCaseVO.getSeverity());

                    if (null != queueCaseVO.getAge()) {
                        queueCaseRespType.setAge(queueCaseVO.getAge());
                    }

                    if (null != queueCaseVO.getCreationTime()) {
                        queueCaseRespType.setCreationTime(queueCaseVO.getCreationTime());
                    }
                    queueCaseRespType.setStatus(queueCaseVO.getStatus());
                    queueCaseRespType.setCondition(queueCaseVO.getCondition());
                    queueCaseRespType.setUserLoginName(queueCaseVO.getUserLoginName());
                    queueCaseRespType.setCustomerId(queueCaseVO.getCustomerId());
                    lstQueueCaseResType.add(queueCaseRespType);
                }
                lstQueueCaseVO = null;
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("CaseResource : Inside getQueueCases() method:::::End ");
        return lstQueueCaseResType;
    }

    /**
     * @Author:
     * @param :
     * @return:List<LookupResponseType>
     * @throws:Exception
     * @Description: This method is used to get values from lookup to populate the subsystem drop
     *               down list.
     */
    @GET
    @Path(OMDConstants.GET_ROAD_NUMBER_HEADERS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AssetHeaderResponseType> getRoadnumberHeaders(@Context final UriInfo uriParam)
            throws RMDServiceException {

        List<ElementVO> arlRoadInitials = new ArrayList<ElementVO>();
        AssetHeaderResponseType responseType = null;
        List<AssetHeaderResponseType> arlResponseType = new ArrayList<AssetHeaderResponseType>();
        MultivaluedMap<String, String> queryParams = null;
        String customerId = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }
                arlRoadInitials = objCaseEoaServiceIntf.getRoadNumberHeaders(customerId);

                if (RMDCommonUtility.isCollectionNotEmpty(arlRoadInitials)) {
                    for (ElementVO objElementVO : arlRoadInitials) {
                        responseType = new AssetHeaderResponseType();
                        responseType.setAssetGroupNameOrder(objElementVO.getId());
                        responseType.setAssetGroupName(objElementVO.getName());
                        arlResponseType.add(responseType);
                    }
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlResponseType;
    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used to checking foe maximum numbers of units on which mass
     *               apply rx can be applied.
     */

    @GET
    @Path(OMDConstants.GET_MAX_MASS_APPLY_UNITS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getMaxMassApplyUnits() throws RMDServiceException {
        String maxLimit = null;
        try {
            maxLimit = objCaseEoaServiceIntf.getMaxMassApplyUnits();
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return maxLimit;
    }

    /**
     * @author
     * @param objCaseRequestType
     * @throws RMDServiceException
     * @throws DatatypeConfigurationException
     * @Description:This method is used for Creating case and delivering Recommendations to multiple
     *                   assets.
     */

    @POST
    @Path(OMDConstants.MASS_APPY_RX)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ViewLogReponseType> massApplyRx(CaseRequestType objCaseRequestType) throws RMDServiceException,
            DatatypeConfigurationException {
        MassApplyRxVO objMassApplyRxVO = new MassApplyRxVO();
        List<ViewLogVO> arlViewLogVo = null;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        DatatypeFactory objDatatypeFactory = DatatypeFactory.newInstance();
        String timeZone = getDefaultTimeZone();
        List<ViewLogReponseType> arlViewLogReponseTypes = new ArrayList<ViewLogReponseType>();
        try {
            if (objCaseRequestType != null) {
                objMassApplyRxVO.setLanguage(OMDConstants.LANGUAGE);
                objMassApplyRxVO.setPriority(OMDConstants.PRIORITY_LOW_GBSTELM2GBSTLST);
                objMassApplyRxVO.setSeverity(OMDConstants.SEVERITY_MEDIUM_GBSTELM2GBSTLST);
                objMassApplyRxVO.setCaseCondition(OMDConstants.OPEN_DISPATCH_CONDITION);
                objMassApplyRxVO.setCaseTitle(OMDConstants.OPEN_DISPATCH);
                objMassApplyRxVO.setUserLanguage(objCaseRequestType.getUserLanguage());
                objMassApplyRxVO.setIsMassApplyRx(objCaseRequestType.getIsMassApplyRx());

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getAssetGrpName())) {

                    objMassApplyRxVO.setAssetgroupName(objCaseRequestType.getAssetGrpName());
                } else {
                    if (!objCaseRequestType.getAssetNumberList().isEmpty()) {
                        String assetData = objCaseRequestType.getAssetNumberList().get(0);
                        String assetGrpName = assetData.substring(0, assetData.lastIndexOf("-"));
                        objMassApplyRxVO.setAssetgroupName(assetGrpName);
                    }
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getUserName())) {
                    objMassApplyRxVO.setUserName(objCaseRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USERNAME_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCustomerId())) {
                    objMassApplyRxVO.setCustomerId(objCaseRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }
                if (!objCaseRequestType.getAssetNumberList().isEmpty()) {
                    objMassApplyRxVO.setAssetList(objCaseRequestType.getAssetNumberList());
                } else {
                    throw new OMDInValidInputException(OMDConstants.ASSET_LIST_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCaseType())) {
                    objMassApplyRxVO.setCaseType(objCaseRequestType.getCaseType());

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASETYPE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCustomerName())) {
                    objMassApplyRxVO.setCustomerName(objCaseRequestType.getCustomerName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERNAME_NOT_PROVIDED);

                }
                // Setting Deliver-Rx Parameters
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getUrgency())) {
                    objMassApplyRxVO.setUrgency(objCaseRequestType.getUrgency());
                } else if (!RMDCommonUtility
                        .isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                    objMassApplyRxVO.setUrgency(objCaseRequestType.getObjSolutionDetailType().getUrgRepair());
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getStrEstRepairTime())) {
                    objMassApplyRxVO.setEstRepairTime(objCaseRequestType.getStrEstRepairTime());

                } else if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType()
                        .getEstmTimeRepair())) {
                    objMassApplyRxVO
                            .setEstRepairTime(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair());
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getMsdcNotes())) {

                    objMassApplyRxVO.setMsdcNotes(objCaseRequestType.getMsdcNotes());
                } else {
                    objMassApplyRxVO.setMsdcNotes(OMDConstants.EMPTY_STRING);
                }

                RxDetailsVO objRxDetailsVO = new RxDetailsVO();

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getSolutionID())) {
                    if (RMDCommonUtility.isNumeric(objCaseRequestType.getObjSolutionDetailType().getSolutionID())) {
                        objRxDetailsVO.setRxObjid(objCaseRequestType.getObjSolutionDetailType().getSolutionID());
                    }
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                        objRxDetailsVO.setUrgency(objCaseRequestType.getObjSolutionDetailType().getUrgRepair());
                    }
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseRequestType.getObjSolutionDetailType()
                            .getEstmTimeRepair())) {
                        objRxDetailsVO.setEstRepTime(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair());
                    }
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getVersion())) {
                    objRxDetailsVO.setStrVersion(objCaseRequestType.getObjSolutionDetailType().getVersion());
                }
                
                if (null!=objCaseRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objCaseRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	objMassApplyRxVO.setLstAttachment(arlDelvDocVO);
                }
                objMassApplyRxVO.setObjRxDetailsVO(objRxDetailsVO);
                arlViewLogVo = objCaseEoaServiceIntf.massApplyRx(objMassApplyRxVO);
                for (ViewLogVO objViewLogVO : arlViewLogVo) {
                    ViewLogReponseType objLogReponseType = new ViewLogReponseType();
                    objLogReponseType.setCaseId(objViewLogVO.getCaseId());
                    if (null != objViewLogVO.getCreationDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objViewLogVO.getCreationDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        createdDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                        objLogReponseType.setCreationDate(createdDate);
                    }
                    objLogReponseType.setRoadNumber(objViewLogVO.getRoadNumber());
                    objLogReponseType.setRoadNumberHeader(objViewLogVO.getRoadNumberHeader());
                    objLogReponseType.setRxQueue(objViewLogVO.getRxQueue());
                    objLogReponseType.setCustomerId(objViewLogVO.getCustomerId());
                    objLogReponseType.setCaseTitle(objViewLogVO.getCaseTitle());
                    arlViewLogReponseTypes.add(objLogReponseType);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlViewLogReponseTypes;
    }

    /**
     * This method is used to enable/disable Append and Close buttons on tool output screen
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.ENABLE_APPEND)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    // public boolean getEnabledRxsAppendClose(CaseRequestType objSolutionRequestType)
    public List<RxHistoryResponseType> getEnabledRxsAppendClose(@PathParam(OMDConstants.CASE_Id) final String caseId,
            @PathParam(OMDConstants.USER_ID) final String userId, @Context final UriInfo uriParam)

    throws RMDServiceException {
        String caseType = OMDConstants.EMPTY_STRING;
        List<String> rxList = null;
        List<RxHistoryResponseType> respTypeList = new ArrayList<RxHistoryResponseType>();
        CaseAppendServiceVO caseAppendVO = null;
        RxHistoryResponseType rxResp = null;
        try {

            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty() && queryParams.containsKey(OMDConstants.CASE_TYPE)) {
                caseType = queryParams.getFirst(OMDConstants.CASE_TYPE);
            }
            caseAppendVO = new CaseAppendServiceVO();
            caseAppendVO.setCaseId(caseId);
            caseAppendVO.setCaseType(caseType);
            caseAppendVO.setUserId(userId);
            rxList = objCaseEoaServiceIntf.getEnabledRxsAppendClose(caseAppendVO);

            LOG.info("rxList::" + rxList);
            if (rxList != null) {
                for (String str : rxList) {
                    rxResp = new RxHistoryResponseType();
                    rxResp.setRxCaseId(str);
                    LOG.info("RxID to be disabled:::" + rxResp.getRxCaseId());
                    respTypeList.add(rxResp);

                }
            }
            if (respTypeList != null) {
                for (RxHistoryResponseType a : respTypeList) {

                    LOG.info("rxcaseid:::" + a.getRxCaseId());
                }
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        LOG.info("respTypeList:::" + respTypeList);

        return respTypeList;
    }

    /**
     * This method is used to display previous cases related to the selected case.
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_PREV_CASES)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> getPreviousCases(CaseRequestType objSolutionRequestType) throws RMDServiceException {

        CaseAppendServiceVO caseAppendVO = null;
        List<String> enabledRxs = null;
        try {
            caseAppendVO = new CaseAppendServiceVO();
            caseAppendVO.setCaseId(objSolutionRequestType.getCaseID());
            LOG.error("caseIddddd" + caseAppendVO);

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        LOG.error("rxs" + enabledRxs);
        return enabledRxs;
    }
    /**
     * This method is used to append Rx to a case
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.APPEND_RX_TO_CASE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String appendRx(@PathParam(OMDConstants.CASE_Id) final String caseId,
            @PathParam(OMDConstants.TO_CASE_ID) final String toCaseId,
            @PathParam(OMDConstants.USER_ID) final String userId, @PathParam(OMDConstants.RX_ID) final String rxId,
            @PathParam(OMDConstants.RULE_DEFINITION_ID) final String ruleDefId,
            @PathParam(OMDConstants.ASSET_GROUP_NAME) final String assetGrpName,
            @PathParam(OMDConstants.ASSET_NUMBER) final String assetNumber,
            @PathParam(OMDConstants.CUSTOMER_ID) final String customerId,
            @PathParam(OMDConstants.TOOL_ID) final String toolId,
            @PathParam(OMDConstants.TOOL_OBJID) final String toolObjId) throws RMDServiceException {

        CaseAppendServiceVO caseAppendVO = null;
        String message = OMDConstants.SUCCESS;
        boolean isCaseClosed = false;

        try {
            caseAppendVO = new CaseAppendServiceVO();
            caseAppendVO.setCaseId(caseId); // FromCase
            caseAppendVO.setUserId(userId); // UserId
            caseAppendVO.setToCaseId(toCaseId); // ToCase
            caseAppendVO.setRuleDefId(ruleDefId);
            caseAppendVO.setAssetGrpName(assetGrpName);
            caseAppendVO.setAssetNumber(assetNumber);
            caseAppendVO.setCustomerId(customerId);
            caseAppendVO.setRxId(rxId);
            caseAppendVO.setToolId(toolId);
            caseAppendVO.setToolObjId(toolObjId);

            LOG.debug("Append From Case" + caseId);
            LOG.debug("Append To Case" + toCaseId);
            LOG.debug("RX id" + rxId);
            LOG.debug("User id" + userId);
            LOG.debug("customerId " + customerId);
            LOG.debug("assetNumber " + assetNumber);
            LOG.debug("assetGrpName " + assetGrpName);
            LOG.debug("ruleDefId " + ruleDefId);
            LOG.debug("toolId " + toolId);

            if (caseId == null || toCaseId == null || rxId == null || customerId == null || assetNumber == null
                    || assetGrpName == null || ruleDefId == null) {

                LOG.debug("One more more input params are null");
                throw new Exception();
            }

            /**
             * 1. Update CASE_VICTIM2CASE column in table_case
             * objCaseEoaServiceIntf.updateCaseVictimToCase(caseAppendVO);
             **/

            /** .Check if To case is closed and reopen it if closed.The id passed is id_number **/
            LOG.info("******************************Checkin if case is closed***********************************************************");

            isCaseClosed = objCaseEoaServiceIntf.isCaseClosed(caseAppendVO.getToCaseId());
            if (isCaseClosed) {

                LOG.debug("Case is closed.Reopening " + toCaseId);
                message = objCaseEoaServiceIntf.reOpenCase(caseAppendVO.getToCaseId(), caseAppendVO.getUserId());
                if (message != OMDConstants.SUCCESS) {
                    LOG.debug(" Case cant be reopened ...");
                    throw new Exception();
                }

                else {
                    message = "Reopened";
                    LOG.debug("Reopened ...");
                }
                LOG.debug("Reopened ...");
            }

            LOG.debug("******************************Appending Rx********************************************************************");
            objCaseEoaServiceIntf.appendRx(caseAppendVO);
            LOG.debug("******************************Appended Successfully***********************************************************");

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        LOG.debug("message" + message);
        return message;
    }
    /**
     * @Author:
     * @param case id
     * @return CaseBean
     * @Description: return the count of Open FL work order and will be called while closing the
     *               Case will return the open work order count from the eservices
     */
    @GET
    @Path(OMDConstants.GET_OPEN_FL_COUNT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getOpenFLCount(@Context final UriInfo uriParam) throws RMDServiceException {
        int openFLCount = 0;
        String strOpenFLCount = OMDConstants.EMPTY_STRING;
        String lmsLocoId = OMDConstants.EMPTY_STRING;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.Lms_Id)) {
                    lmsLocoId = queryParams.getFirst(OMDConstants.Lms_Id);
                }
                openFLCount = objCaseEoaServiceIntf.getOpenFLCount(lmsLocoId);
                strOpenFLCount = String.valueOf(openFLCount);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return strOpenFLCount;
    }
    /**
     * @Author:
     * @param case id
     * @return CaseBean
     * @Description: return case title
     */
    @GET
    @Path(OMDConstants.GET_CASE_TITLE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getCaseTitle(@PathParam(OMDConstants.CASE_Id) final String caseId) throws RMDServiceException {
        String title = OMDConstants.EMPTY_STRING;
        try {
            title = objCaseEoaServiceIntf.getCaseTitle(caseId);

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return title;
    }

    /**
     * @Author:
     * @param case id
     * @return CaseBean
     * @Description: return lms locoId for a case
     */
    @GET
    @Path(OMDConstants.GET_LMS_LOCO_ID)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getLmsLocoID(@Context final UriInfo uriParam) throws RMDServiceException {
        String lmsLocoId = OMDConstants.EMPTY_STRING;
        String caseId = OMDConstants.EMPTY_STRING;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {

                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                }
                lmsLocoId = objCaseEoaServiceIntf.getLmsLocoID(caseId);
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return lmsLocoId;
    }
    /**
     * This method is used for retrieving Tool output details
     * 
     * @param uriParam
     * @return list of
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_TOOL_OUTPUT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ToolOutputResponseType> getToolOutput(@Context final UriInfo uriParam) throws RMDServiceException,
            DatatypeConfigurationException {
        String caseId = OMDConstants.EMPTY_STRING;
        String timezone = OMDConstants.EMPTY_STRING;
        List<ToolOutputEoaServiceVO> lstToolOutputService = null;
        ToolOutputResponseType toolOutput = null;
        List<ToolOutputResponseType> toolOutputList = new ArrayList<ToolOutputResponseType>();
        Iterator<ToolOutputEoaServiceVO> iteratorToolOutputServiceVO = null;
        try {

            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {

                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                }
                if (queryParams.containsKey(OMDConstants.TIMEZONE)) {
                    timezone = queryParams.getFirst(OMDConstants.TIMEZONE);
                }
                lstToolOutputService = objCaseEoaServiceIntf.getToolOutput(caseId);

                if (lstToolOutputService != null)
                    if (RMDCommonUtility.isCollectionNotEmpty(lstToolOutputService) && lstToolOutputService != null) {
                        iteratorToolOutputServiceVO = lstToolOutputService.iterator();

                        ToolOutputEoaServiceVO tooloutputVo = null;
                        while (iteratorToolOutputServiceVO.hasNext()) {
                            tooloutputVo = new ToolOutputEoaServiceVO();
                            tooloutputVo = (ToolOutputEoaServiceVO) iteratorToolOutputServiceVO.next();
                            toolOutput = new ToolOutputResponseType();

                            toolOutput.setSolutionID(tooloutputVo.getStrRecomId());
                            toolOutput.setSolutionTitle(tooloutputVo.getStrRecomTitle());
                            toolOutput.setSolutionProb(tooloutputVo.getStrRecomProb());
                            toolOutput.setToolID(tooloutputVo.getStrToolId());
                            toolOutput.setStrViz(tooloutputVo.getStrViz());
                            toolOutput.setPlotVizPresent(tooloutputVo.isPlotVizPresent());
                            if (null != tooloutputVo.getStrFalseAlarmPct()) {
                                if (!tooloutputVo.getStrFalseAlarmPct().equals(OMDConstants.HYPHEN))
                                    toolOutput.setFalseAlarmPct(Float.parseFloat(tooloutputVo.getStrFalseAlarmPct()));

                            }
                            if (null != tooloutputVo.getStrToolCovgPct()) {
                                if (!tooloutputVo.getStrToolCovgPct().equals(OMDConstants.HYPHEN))
                                    toolOutput.setToolCovgPct(Float.parseFloat(tooloutputVo.getStrToolCovgPct()));
                            }

                            if (null != tooloutputVo.getStrMdscPerfPct()) {
                                if (!tooloutputVo.getStrMdscPerfPct().equals(OMDConstants.HYPHEN))
                                    toolOutput.setMdscPerfPct(Float.parseFloat(tooloutputVo.getStrMdscPerfPct()));
                            }

                            if (null != tooloutputVo.getDtRxDelvDate()) {

                                toolOutput.setDate(RMDCommonUtility.convertXMLGregorianCalenderToString(
                                        RMDCommonUtility.convertDateToXMLGregorianCalender(
                                                tooloutputVo.getDtRxDelvDate(), getDefaultTimeZone()), timezone));
                            }
                            if (null != tooloutputVo.getFaultCode()) {
                                toolOutput.setFaultCode(tooloutputVo.getFaultCode());
                            }
                            if (null != tooloutputVo.getToolObjId()) {
                                toolOutput.setToolObjId(tooloutputVo.getToolObjId());
                            }
                            toolOutput.setFCFault(tooloutputVo.isFCFault());
                            toolOutput.setRuleDefID(tooloutputVo.getStrRuleDefId());
                            toolOutput.setToolIdDes(tooloutputVo.getStrToolIdDes());
                            toolOutput.setUrgency(tooloutputVo.getStrUrgency());
                            toolOutput.setEstRepairTime(tooloutputVo.getStrEstRepairTime());
                            toolOutput.setSolutionRevision(tooloutputVo.getRxRevision());
                            toolOutput.setFinRuleId(tooloutputVo.getFinRuleId());
                            toolOutputList.add(toolOutput);

                        }
                    } /*
                       * else { throw new
                       * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION); }
                       */
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);

            }

        } catch (Exception ex) {
            LOG.info("Exception in gettingToolOutput" + ex.getMessage());
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return toolOutputList;
    }

    /**
     * This method is used to move tooloutput
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.MOVE_TOOL_OUTPUT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String moveToolOutput(@PathParam(OMDConstants.CASE_Id) final String caseId,
            @PathParam(OMDConstants.TO_CASE_ID) final String toCaseId,
            @PathParam(OMDConstants.USER_ID) final String userId, @PathParam(OMDConstants.RX_ID) final String rxId,
            @PathParam(OMDConstants.RULE_DEFINITION_ID) final String ruleDefId,
            @PathParam(OMDConstants.ASSET_GROUP_NAME) final String assetGrpName,
            @PathParam(OMDConstants.ASSET_NUMBER) final String assetNumber,
            @PathParam(OMDConstants.CUSTOMER_ID) final String customerId,
            @PathParam(OMDConstants.TOOL_ID) final String toolId,
            @PathParam(OMDConstants.SKIP_COUNT_CLOSE) final String skipCount,
            @PathParam(OMDConstants.RX_DELV) final String rxDelv,
            @PathParam(OMDConstants.TOOL_OBJID) final String toolObjId)

    throws RMDServiceException {

        CaseAppendServiceVO caseAppendVO = null;
        String idNumber = null;

        try {
            LOG.info("Cases resourcellll");
            caseAppendVO = new CaseAppendServiceVO();
            caseAppendVO.setCaseId(caseId); // FromCase
            caseAppendVO.setUserId(userId); // UserId
            caseAppendVO.setToCaseId(toCaseId); // ToCase
            caseAppendVO.setRuleDefId(ruleDefId);
            caseAppendVO.setAssetGrpName(assetGrpName);
            caseAppendVO.setAssetNumber(assetNumber);
            caseAppendVO.setCustomerId(customerId);
            caseAppendVO.setRxId(rxId);
            caseAppendVO.setToolId(toolId);
            caseAppendVO.setSkipCount(Integer.parseInt(skipCount));
            caseAppendVO.setRxDelv(rxDelv);
            caseAppendVO.setToolObjId(toolObjId);

            LOG.debug("From Case" + caseId);
            LOG.debug("To Case" + toCaseId);
            LOG.debug("RX id" + rxId);
            LOG.debug("User id" + userId);
            LOG.debug("customerId " + customerId);
            LOG.debug("assetNumber " + assetNumber);
            LOG.debug("assetGrpName " + assetGrpName);
            LOG.debug("ruleDefId " + ruleDefId);
            LOG.debug("toolId " + toolId);
            LOG.debug("skipCount " + skipCount);
            LOG.debug("rxDelv " + rxDelv);

            if (caseId == null || toCaseId == null || rxId == null || customerId == null || assetNumber == null
                    || assetGrpName == null) {

                LOG.debug("One more more input params are null");
                throw new Exception();
            }

            LOG.debug("******************************Creating Case and Moving Tool Output********************************************************************");
            idNumber = objCaseEoaServiceIntf.moveToolOutput(caseAppendVO);

        } catch (OMDInValidInputException objOMDInValidInputException) {
            LOG.debug(objOMDInValidInputException.getClass(), objOMDInValidInputException);
            return OMDConstants.FAILURE;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            LOG.debug(objOMDNoResultFoundException.getMessage(), objOMDNoResultFoundException);
            return OMDConstants.FAILURE;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            return OMDConstants.FAILURE;

        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            return OMDConstants.FAILURE;
        }
        LOG.debug("idNumber" + idNumber);
        return idNumber;
    }

    @POST
    @Path(OMDConstants.TOOL_OUTPUT_CREATECASE)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ViewLogReponseType> toolOutputCreateCase(CaseRequestType objCaseRequestType)
            throws RMDServiceException, DatatypeConfigurationException {
        MassApplyRxVO objMassApplyRxVO = new MassApplyRxVO();
        List<ViewLogVO> arlViewLogVo = null;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        DatatypeFactory objDatatypeFactory = DatatypeFactory.newInstance();
        String timeZone = getDefaultTimeZone();
        List<ViewLogReponseType> arlViewLogReponseTypes = new ArrayList<ViewLogReponseType>();
        try {
            if (objCaseRequestType != null) {
                objMassApplyRxVO.setLanguage(OMDConstants.LANGUAGE);
                objMassApplyRxVO.setPriority(OMDConstants.PRIORITY_LOW_GBSTELM2GBSTLST);
                objMassApplyRxVO.setSeverity(OMDConstants.SEVERITY_MEDIUM_GBSTELM2GBSTLST);
                objMassApplyRxVO.setCaseTitle(objCaseRequestType.getSolutionTitle());
                objMassApplyRxVO.setIsFromTooloutput(OMDConstants.YES);
                objMassApplyRxVO.setCaseCondition(OMDConstants.OPEN_DISPATCH_CONDITION);
                objMassApplyRxVO.setUserLanguage(objCaseRequestType.getUserLanguage());
                objMassApplyRxVO.setIsMassApplyRx(RMDCommonConstants.LETTER_Y);

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getAssetGrpName())) {

                    objMassApplyRxVO.setAssetgroupName(objCaseRequestType.getAssetGrpName());
                } else {
                    if (!objCaseRequestType.getAssetNumberList().isEmpty()) {
                        String assetData = objCaseRequestType.getAssetNumberList().get(0);
                        String assetGrpName = assetData.substring(0, assetData.lastIndexOf("-"));
                        objMassApplyRxVO.setAssetgroupName(assetGrpName);
                    }
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getUserName())) {
                    objMassApplyRxVO.setUserName(objCaseRequestType.getUserName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.USERNAME_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCustomerId())) {
                    objMassApplyRxVO.setCustomerId(objCaseRequestType.getCustomerId());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCustomerName())) {
                    objMassApplyRxVO.setCustomerName(objCaseRequestType.getCustomerName());
                }
                if (!objCaseRequestType.getAssetNumberList().isEmpty()) {
                    objMassApplyRxVO.setAssetList(objCaseRequestType.getAssetNumberList());
                } else {
                    throw new OMDInValidInputException(OMDConstants.ASSET_LIST_NOT_PROVIDED);
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getCaseType())) {
                    objMassApplyRxVO.setCaseType(objCaseRequestType.getCaseType());

                } else {
                    throw new OMDInValidInputException(OMDConstants.CASETYPE_NOT_PROVIDED);
                }
                // Setting Deliver-Rx Parameters
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getUrgency())) {
                    objMassApplyRxVO.setUrgency(objCaseRequestType.getUrgency());
                } else if (!RMDCommonUtility
                        .isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                    objMassApplyRxVO.setUrgency(objCaseRequestType.getObjSolutionDetailType().getUrgRepair());
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getStrEstRepairTime())) {
                    objMassApplyRxVO.setEstRepairTime(objCaseRequestType.getStrEstRepairTime());

                } else if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType()
                        .getEstmTimeRepair())) {
                    objMassApplyRxVO.setUrgency(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair());
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getMsdcNotes())) {

                    objMassApplyRxVO.setMsdcNotes(objCaseRequestType.getMsdcNotes());
                } else {
                    objMassApplyRxVO.setMsdcNotes(OMDConstants.EMPTY_STRING);
                }

                RxDetailsVO objRxDetailsVO = new RxDetailsVO();

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getSolutionID())) {
                    if (RMDCommonUtility.isNumeric(objCaseRequestType.getObjSolutionDetailType().getSolutionID())) {
                        objRxDetailsVO.setRxObjid(objCaseRequestType.getObjSolutionDetailType().getSolutionID());
                    }
                }

                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                    if (RMDCommonUtility.isAlphabet(objCaseRequestType.getObjSolutionDetailType().getUrgRepair())) {
                        objRxDetailsVO.setUrgency(objCaseRequestType.getObjSolutionDetailType().getUrgRepair());
                    }
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair())) {
                    if (RMDCommonUtility.isNumbersOnly(objCaseRequestType.getObjSolutionDetailType()
                            .getEstmTimeRepair())) {
                        objRxDetailsVO.setEstRepTime(objCaseRequestType.getObjSolutionDetailType().getEstmTimeRepair());
                    }
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCaseRequestType.getObjSolutionDetailType().getVersion())) {
                    objRxDetailsVO.setStrVersion(objCaseRequestType.getObjSolutionDetailType().getVersion());
                }
                if (null!=objCaseRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objCaseRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	objMassApplyRxVO.setLstAttachment(arlDelvDocVO);
                }
                objMassApplyRxVO.setObjRxDetailsVO(objRxDetailsVO);
                arlViewLogVo = objCaseEoaServiceIntf.massApplyRx(objMassApplyRxVO);
                for (ViewLogVO objViewLogVO : arlViewLogVo) {
                    ViewLogReponseType objLogReponseType = new ViewLogReponseType();
                    objLogReponseType.setCaseId(objViewLogVO.getCaseId());
                    if (null != objViewLogVO.getCreationDate()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objViewLogVO.getCreationDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                        createdDate = objDatatypeFactory.newXMLGregorianCalendar(objGregorianCalendar);
                        objLogReponseType.setCreationDate(createdDate);
                    }
                    objLogReponseType.setRoadNumber(objViewLogVO.getRoadNumber());
                    objLogReponseType.setRoadNumberHeader(objViewLogVO.getRoadNumberHeader());
                    objLogReponseType.setRxQueue(objViewLogVO.getRxQueue());
                    objLogReponseType.setCustomerId(objViewLogVO.getCustomerId());
                    objLogReponseType.setCaseObjId(objViewLogVO.getCaseObjid());
                    objLogReponseType.setCaseTitle(objViewLogVO.getCaseTitle());
                    arlViewLogReponseTypes.add(objLogReponseType);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlViewLogReponseTypes;
    }

    @POST
    @Path(OMDConstants.SAVE_TOOL_OUTPUT_ACT_ENTRY)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String toolOutputActEntry(ToolOutputActEntry objToolOutputActEntry) throws RMDServiceException,
            DatatypeConfigurationException {
        ToolOutputActEntryVO objToolOutputActEntryVO = new ToolOutputActEntryVO();
        String result = RMDCommonConstants.FAILURE;
        try {
            if (objToolOutputActEntry != null) {
                objToolOutputActEntryVO.setCaseTitle(objToolOutputActEntry.getCaseTitle());
                objToolOutputActEntryVO.setManualcaseID(objToolOutputActEntry.getManualcaseID());
                objToolOutputActEntryVO.setParentCaseID(objToolOutputActEntry.getParentCaseID());
                objToolOutputActEntryVO.setArlCaseObjid(objToolOutputActEntry.getCaseObjids());
                objToolOutputActEntryVO.setParentCaseObjId(objToolOutputActEntry.getParentCaseObjid());
                objToolOutputActEntryVO.setUserName(objToolOutputActEntry.getUserName());

                result = objCaseEoaServiceIntf.saveToolOutputActEntry(objToolOutputActEntryVO);

            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return result;
    }

    /**
     * This method is used for fetching cases repair codes
     * 
     * @param String
     *            userId
     * @return List<RepairCodeDetailsType>
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.GET_TO_REPAIR_CODE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getTORepairCodes(String repaircode) throws RMDServiceException {
        List<RepairCodeDetailsType> repaircodesList = new ArrayList<RepairCodeDetailsType>();
        List<RepairCodeEoaDetailsVO> repairCodeResponse = null;
        List<String> repairCodelst = new ArrayList<String>();
        try {

            repairCodeResponse = objCaseEoaServiceIntf.getRepairCodes(repaircode);

            for (RepairCodeEoaDetailsVO repairCodeDetail : repairCodeResponse) {
                RepairCodeDetailsType currentRepairCode = new RepairCodeDetailsType();
                currentRepairCode.setRepaidCodeDesc(repairCodeDetail.getRepairCodeDesc());
                currentRepairCode.setRepairCode(repairCodeDetail.getRepairCode());
                currentRepairCode.setRepairCodeId(repairCodeDetail.getRepairCodeId());
                repaircodesList.add(currentRepairCode);
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)), rmdServiceException
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return repaircodesList;
    }
    /**
     * This method is used to move tooloutput
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.MOVE_DELIVER_TOOL_OUTPUT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String moveDeliverToolOutput(@Context final UriInfo uriParam) throws RMDServiceException {

        String message = null;
        String caseId = null;
        String userId = null;
        String toCaseId = null;
        String rxId = null;
        String assetNumber = null;
        String assetGroupName = null;
        String customerId = null;
        String ruleDefId = null;
        String toolId = null;
        String caseType = null;
        String toolObjId = null;
        try {
            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                    userId = queryParams.getFirst(OMDConstants.userId);
                    toCaseId = queryParams.getFirst(OMDConstants.TO_CASE_ID);
                    rxId = queryParams.getFirst(OMDConstants.RX_ID);
                    assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
                    assetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                    ruleDefId = queryParams.getFirst(OMDConstants.RULE_DEFINITION_ID);
                    toolId = queryParams.getFirst(OMDConstants.TOOL_ID);
                    caseType = queryParams.getFirst(OMDConstants.CASE_TYPE);
                    toolObjId = queryParams.getFirst(OMDConstants.TOOL_OBJID);
                }

            } else {
                throw new OMDInValidInputException("One more more input params are null");
            }

            LOG.debug("******************************Creating Case and Moving Tool Output********************************************************************");
            message = objCaseEoaServiceIntf.moveDeliverToolOutput(userId, caseId, toCaseId, rxId, assetNumber,
                    assetGroupName, customerId, ruleDefId, toolId, caseType, toolObjId);

        } catch (OMDInValidInputException objOMDInValidInputException) {
            LOG.debug(objOMDInValidInputException.getMessage(), objOMDInValidInputException);
            return OMDConstants.FAILURE;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            LOG.debug(objOMDNoResultFoundException.getMessage(), objOMDNoResultFoundException);
            return OMDConstants.FAILURE;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            return OMDConstants.FAILURE;

        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            return OMDConstants.FAILURE;
        }

        return message;
    }
    /**
     * This method is used to Check if Active Rx Exists in the case.
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.ACTIVE_RX_EXISTS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String activeRxExistsInCase(@PathParam(OMDConstants.CASE_Id) final String caseId) throws RMDServiceException {

        String activeRxExistsInCase = null;
        boolean isActive = false;
        try {

            isActive = objCaseEoaServiceIntf.activeRxExistsInCase(caseId);
            if (isActive)
                activeRxExistsInCase = OMDConstants.Y;

        } catch (OMDInValidInputException objOMDInValidInputException) {
            LOG.debug(objOMDInValidInputException.getMessage(), objOMDInValidInputException);;
            return OMDConstants.FAILURE;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            LOG.debug(objOMDNoResultFoundException.getMessage(), objOMDNoResultFoundException);
            return OMDConstants.FAILURE;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            return OMDConstants.FAILURE;
        } catch (Exception ex) {
            LOG.debug(ex.getMessage(), ex);
            return OMDConstants.FAILURE;
        }
        return activeRxExistsInCase;

    }
    /**
     * This method is used to disable Rxs delivered against Unit
     * 
     * @param CaseSolutionRequestType
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ENABLED_UNIT_RXS_DELIVER)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public RxVehicleBomResponseType getEnabledUnitRxsDeliver(@Context final UriInfo uriParam)
            throws RMDServiceException {

        String caseId = null;
        String assetNumber = null;
        String assetGroupName = null;
        String customerId = null;
        String caseType = null;
        String currentUser = null;
        RxVehicleBomResponseType objRxVehicleBomResponseType=null;
        Map<String, List<String>> resultMap=null;
        
        try {

            MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                    assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
                    assetGroupName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                    caseType = queryParams.getFirst(OMDConstants.CASE_TYPE);
                    currentUser = queryParams.getFirst(OMDConstants.USER_ID);
                }

            } else {
                throw new OMDInValidInputException("One more more input params are null");
            }

            resultMap = objCaseEoaServiceIntf.getEnabledUnitRxsDeliver(customerId, assetGroupName,
                    assetNumber, caseId, caseType,currentUser);
            
            if(null!=resultMap){
                objRxVehicleBomResponseType=new RxVehicleBomResponseType();
                objRxVehicleBomResponseType.setDisabledRxList(resultMap.get(RMDCommonConstants.DISABLED_RX_LIST));
                objRxVehicleBomResponseType.setBomMismatchRxList(resultMap.get(RMDCommonConstants.BOM_MISMATCH_RX_LIST));
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

        return objRxVehicleBomResponseType;
    }
    /**
     * @Author:
     * @return:String
     * @param :FindCaseServiceVO
     * @throws:RMDServiceException
     * @Description:This method is for updating case TITLE
     */
    @POST
    @Path(OMDConstants.UPDATE_CASE_TITLE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String updateCaseTitle(final CaseRequestType caseRequestType) throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        FindCaseServiceVO caseDetails = new FindCaseServiceVO();
        try {

            caseDetails.setCaseID(caseRequestType.getCaseID());
            caseDetails.setStrCaseTitle(caseRequestType.getCaseTitle());
            result = objCaseEoaServiceIntf.updateCaseTitle(caseDetails);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return result;
    }

    /**
     * @Author:
     * @param:CaseRequestType
     * @return:List<CaseInfoType>
     * @throws:RMDServiceException
     * @Description: This method fetches the set of cases for asset by invoking
     *               caseseoaserviceimpl.getAssetCases() method.
     */
    @POST
    @Path(OMDConstants.GET_HEADER_SEARCH_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseInfoType> getHeaderSearchCases(CaseRequestType objCaseRequestType) throws RMDServiceException {

        String customerName;
        String roadNo;
        String roadInitial;
        String caseType;
        String noOfDays;
        String caseLike;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar createdDate;
        XMLGregorianCalendar closeDate;
        List<CaseInfoType> caseInfotypeList = new ArrayList<CaseInfoType>();
        CaseInfoType objCaseInfoType = new CaseInfoType();
        FindCaseServiceVO objFindCaseServiceVO = new FindCaseServiceVO();
        List<SelectCaseHomeVO> objSelectCaseHomeVOList = new ArrayList<SelectCaseHomeVO>();
        String timeZone = getDefaultTimeZone();
        try {

            if (null != objCaseRequestType.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCustomerId())) {
                customerName = objCaseRequestType.getCustomerId();
                objFindCaseServiceVO.setStrCustomerId(customerName);
            }
            if (null != objCaseRequestType.getAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetNumber())) {
                roadNo = objCaseRequestType.getAssetNumber();
                objFindCaseServiceVO.setStrAssetNumber(roadNo);
            }
            if (null != objCaseRequestType.getAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetGrpName())) {
                roadInitial = objCaseRequestType.getAssetGrpName();
                objFindCaseServiceVO.setStrAssetGrpName(roadInitial);
            }
            if (null != objCaseRequestType.getCaseType()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCaseType())) {
                caseType = objCaseRequestType.getCaseType();
                objFindCaseServiceVO.setStrCaseType(caseType);
            }
            if (null != objCaseRequestType.getNoOfDays())

            {
                noOfDays = objCaseRequestType.getNoOfDays();
                objFindCaseServiceVO.setNoOfDays(noOfDays);
            }
            if (null != objCaseRequestType.getCaseLike()) {
                caseLike = objCaseRequestType.getCaseLike();
                objFindCaseServiceVO.setCaseID(caseLike);
            }
            objSelectCaseHomeVOList = objCaseEoaServiceIntf.getHeaderSearchCases(objFindCaseServiceVO);
            for (SelectCaseHomeVO details : objSelectCaseHomeVOList) {
                objCaseInfoType = new CaseInfoType();
                objCaseInfoType.setCaseTitle(details.getStrTitle());
                objCaseInfoType.setCaseID(details.getStrCaseId());
                objCaseInfoType.setCaseType(details.getStrCaseType());
                if (details.getDtCreationDate() != null) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(details.getDtCreationDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    createdDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setCreatedDate(createdDate);
                }
                objCaseInfoType.setPriority(details.getStrPriority());
                objCaseInfoType.setOwner(details.getStrOwner());
                objCaseInfoType.setReason(details.getStrReason());
                objCaseInfoType.setCondition(details.getCondition());
                objCaseInfoType.setQueueName(details.getStrQueue());
                if (details.getCloseDate() != null) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(details.getCloseDate());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    closeDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                    objCaseInfoType.setClosedDate(closeDate);
                }

                objCaseInfoType.setAppend(details.getIsAppend());
                objCaseInfoType.setCaseObjid(details.getCaseObjid());
                objCaseInfoType.setRoadNumber(details.getStrAssetNumber());
                objCaseInfoType.setCustomerId(details.getStrAssetHeader());
                objCaseInfoType.setCustomerName(details.getStrcustomerName());
                caseInfotypeList.add(objCaseInfoType);
            }

        }

        catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return caseInfotypeList;

    }

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param : caseObjId,vehicleObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     */
    @GET
    @Path(OMDConstants.GET_RECOMM_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public RxDetailsResponseType getRxDetails(@Context UriInfo ui) throws RMDServiceException {
        String vehicleObjId = null;
        String caseObjId = null;
        RxDetailsResponseType objRxDetailsResponseType = new RxDetailsResponseType();
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.VEHICLE_OBJID)) {
                vehicleObjId = queryParams.getFirst(OMDConstants.VEHICLE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(vehicleObjId)) {
                throw new OMDInValidInputException(OMDConstants.VEHICLE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(vehicleObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_VEHICLE_OBJID);
            }
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
            RecommDetailsVO objRecommDetailsVO = objCaseEoaServiceIntf.getRxDetails(caseObjId, vehicleObjId);

            if (null != objRecommDetailsVO) {
                objRxDetailsResponseType.seteServiceRxStatus(objRecommDetailsVO.geteServiceRxStatus());
                objRxDetailsResponseType.setB2BRxStatus(objRecommDetailsVO.getB2BRxStatus());
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getRxDetails() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objRxDetailsResponseType;
    }
    /**
     * @Author:
     * @return:List<UnitConfigResponseType>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting unit configuration details in data screen.
     */
    @GET
    @Path(OMDConstants.UNIT_CONFIG)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UnitConfigResponseType> getUnitConfigDetails(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<UnitConfigResponseType> unitconfigList = new ArrayList<UnitConfigResponseType>();
        List<UnitConfigVO> objUnitConfigVOList = null;
        String caseId = null;
        String locoId = null;
        FindCaseServiceVO objFindCaseServiceVO = null;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                }
                if (queryParams.containsKey(OMDConstants.LOCO_ID)) {
                    locoId = queryParams.getFirst(OMDConstants.LOCO_ID);
                }
                objFindCaseServiceVO = new FindCaseServiceVO();
                objFindCaseServiceVO.setLocoId(locoId);
                objFindCaseServiceVO.setStrCaseId(caseId);
                objUnitConfigVOList = findCaseLiteEoaServiceIntf.getUnitConfigDetails(objFindCaseServiceVO);
                for (UnitConfigVO obj : objUnitConfigVOList) {
                    UnitConfigResponseType objResponseType = new UnitConfigResponseType();
                    objResponseType.setModelName(obj.getModelName());
                    objResponseType.setConfigGroup(obj.getConfigGroup());
                    objResponseType.setConfigItem(obj.getConfigItem());
                    unitconfigList.add(objResponseType);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return unitconfigList;
    }

    /**
     * @Author:
     * @return:List<RepairCodeDetailsType>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting false alarm details in data screen.
     */
    @GET
    @Path(OMDConstants.FALSE_ALARM_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getFalseAlarmDetails(@PathParam(OMDConstants.RX_OBJ_ID) final String rxObjId)
            throws RMDServiceException {
        List<RepairCodeDetailsType> objRepairCodeDetailsTypeList = new ArrayList<RepairCodeDetailsType>();
        List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
        try {
            if (null != rxObjId && !rxObjId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkIntNumber(rxObjId)) {
                    objRepairCodeEoaDetailsVOList = findCaseLiteEoaServiceIntf.getFalseAlarmDetails(rxObjId);
                    for (RepairCodeEoaDetailsVO obj : objRepairCodeEoaDetailsVOList) {
                        RepairCodeDetailsType objResponseType = new RepairCodeDetailsType();
                        objResponseType.setRepairCode(obj.getRepairCode());
                        objResponseType.setRepaidCodeDesc(obj.getRepairCodeDesc());
                        objResponseType.setNoOfCases(obj.getNoOfCases());
                        objRepairCodeDetailsTypeList.add(objResponseType);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NUMBER_FORMAT_EXCEPTION);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return objRepairCodeDetailsTypeList;
    }

    /**
     * @Author:
     * @return:List<RxDetailsVO>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting false alarm details in data screen.
     */
    @GET
    @Path(OMDConstants.RX_FALSE_ALARM_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RxDetailsType> getRXFalseAlarmDetails(@PathParam(OMDConstants.RX_OBJ_ID) final String rxObjId)
            throws RMDServiceException {
        List<RxDetailsType> objRxDetailsType = new ArrayList<RxDetailsType>();
        List<RxDetailsVO> objRxDetailsVOList = null;
        try {
            if (null != rxObjId && !rxObjId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkIntNumber(rxObjId)) {
                    objRxDetailsVOList = findCaseLiteEoaServiceIntf.getRXFalseAlarmDetails(rxObjId);
                    for (RxDetailsVO obj : objRxDetailsVOList) {
                        RxDetailsType objResponseType = new RxDetailsType();
                        objResponseType.setRxTitle(obj.getRxTitle());
                        objResponseType.setNoOfCases(obj.getNoOfCases());
                        objRxDetailsType.add(objResponseType);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NUMBER_FORMAT_EXCEPTION);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return objRxDetailsType;
    }

    /**
     * @Author:
     * @return:List<RepairCodeDetailsType>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting mdsc accurate details in data screen.
     */
    @GET
    @Path(OMDConstants.MDSC_ACCURATE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RepairCodeDetailsType> getMDSCAccurateDetails(@PathParam(OMDConstants.RX_OBJ_ID) final String rxObjId)
            throws RMDServiceException {
        List<RepairCodeDetailsType> objRepairCodeDetailsTypeList = new ArrayList<RepairCodeDetailsType>();
        List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
        try {
            if (null != rxObjId && !rxObjId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkIntNumber(rxObjId)) {
                    objRepairCodeEoaDetailsVOList = findCaseLiteEoaServiceIntf.getMDSCAccurateDetails(rxObjId);
                    for (RepairCodeEoaDetailsVO obj : objRepairCodeEoaDetailsVOList) {
                        RepairCodeDetailsType objResponseType = new RepairCodeDetailsType();
                        objResponseType.setRepairCode(obj.getRepairCode());
                        objResponseType.setRepaidCodeDesc(obj.getRepairCodeDesc());
                        objResponseType.setNoOfCases(obj.getNoOfCases());
                        objRepairCodeDetailsTypeList.add(objResponseType);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NUMBER_FORMAT_EXCEPTION);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return objRepairCodeDetailsTypeList;
    }

    /**
     * @Author:
     * @return:List<CaseRequestType>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting mdsc accurate case details in data screen.
     */
    @GET
    @Path(OMDConstants.MDSC_ACCURATE_GET_CASE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseRequestType> getCaseDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        List<CaseRequestType> objCaseDetails = new ArrayList<CaseRequestType>();
        List<CaseInfoServiceVO> objCaseInfoServiceVO = null;
        String rxObjId = null;
        String repObjId = null;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.RX_OBJ_ID)) {
                    rxObjId = queryParams.getFirst(OMDConstants.RX_OBJ_ID);
                }
                if (queryParams.containsKey(OMDConstants.REP_OBJ_ID)) {
                    repObjId = queryParams.getFirst(OMDConstants.REP_OBJ_ID);
                }
                if (AppSecUtil.checkIntNumber(rxObjId)) {
                    if (AppSecUtil.checkIntNumber(repObjId)) {
                        objCaseInfoServiceVO = findCaseLiteEoaServiceIntf.getCaseDetails(rxObjId, repObjId);
                        for (CaseInfoServiceVO obj : objCaseInfoServiceVO) {
                            CaseRequestType objResponseType = new CaseRequestType();
                            objResponseType.setCaseID(obj.getStrCaseId());
                            objResponseType.setCaseTitle(obj.getStrTitle());
                            objCaseDetails.add(objResponseType);
                        }
                    } else {
                        throw new OMDInValidInputException(OMDConstants.REP_OBJID_NUMBER_FORMAT_EXCEPTION);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NUMBER_FORMAT_EXCEPTION);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return objCaseDetails;
    }

    /**
     * @Author:
     * @return:List<RxDetailsType>
     * @param :UriInfo
     * @throws:RMDServiceException
     * @Description:This method is for getting mdsc accurate-rx details in data screen.
     */
    @GET
    @Path(OMDConstants.MDSC_ACCURATE_GET_RX_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RxDetailsType> getMDSCRxDetails(@PathParam(OMDConstants.REP_OBJ_ID) final String repObjId)
            throws RMDServiceException {
        List<RxDetailsType> objRxDetailsType = new ArrayList<RxDetailsType>();
        List<RxDetailsVO> objRxDetailsVOList = null;
        try {
            if (null != repObjId && !repObjId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkIntNumber(repObjId)) {
                    objRxDetailsVOList = findCaseLiteEoaServiceIntf.getMDSCRxDetails(repObjId);
                    for (RxDetailsVO obj : objRxDetailsVOList) {
                        RxDetailsType objResponseType = new RxDetailsType();
                        objResponseType.setRxTitle(obj.getRxTitle());
                        objResponseType.setMdscPerformance(obj.getMdscPerformance());
                        objRxDetailsType.add(objResponseType);
                    }
                } else {
                    throw new OMDInValidInputException(OMDConstants.REP_OBJID_NUMBER_FORMAT_EXCEPTION);
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return objRxDetailsType;
    }

    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDServiceException
     * @Description:This method is used for Checking weather unit is Shipped or not.
     */
    @POST
    @Path(OMDConstants.CHECK_FOR_UNIT_SHIP_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String checkForUnitShipDetails(UnitShipDetailsRequestType objUnitShipDetailsRequestType)
            throws RMDServiceException {
        String unitShipDetails = null;
        UnitShipDetailsVO objUnitShipDetailsVO = new UnitShipDetailsVO();
        try {
            if (!RMDCommonUtility.isNullOrEmpty(objUnitShipDetailsRequestType.getAssetGrpName())) {
                objUnitShipDetailsVO.setAssetGrpName(objUnitShipDetailsRequestType.getAssetGrpName());
            } else {
                throw new OMDInValidInputException(OMDConstants.ASSETGRPNAME_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objUnitShipDetailsRequestType.getAssetNumber())) {
                if (RMDCommonUtility.isAlphaNumeric(objUnitShipDetailsRequestType.getAssetNumber())) {
                    objUnitShipDetailsVO.setAssetNumber(objUnitShipDetailsRequestType.getAssetNumber());
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_ASSET_NUMBER);
                }

            } else {
                throw new OMDInValidInputException(OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objUnitShipDetailsRequestType.getAssetGrpName())) {
                objUnitShipDetailsVO.setCustomerId(objUnitShipDetailsRequestType.getCustomerId());
            } else {
                throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
            }
            unitShipDetails = objCaseEoaServiceIntf.checkForUnitShipDetails(objUnitShipDetailsVO);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return unitShipDetails;
    }

    /**
     * @Author:
     * @param:FindCasesDetailsResponseType
     * @return:List<FindCasesDetailsResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Cases Details.
     */
    @POST
    @Path(OMDConstants.GET_FIND_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FindCasesDetailsResponseType> getFindCases(FindCasesRequestType objFindCasesRequestType)
            throws RMDServiceException {
        FindCasesVO objFindCasesVO = new FindCasesVO();
        List<FindCasesDetailsVO> objFindCasesDetailsVOlst = new ArrayList<FindCasesDetailsVO>();
        List<FindCasesDetailsResponseType> objFindCasesDetailsResponseTypelst = new ArrayList<FindCasesDetailsResponseType>();
        FindCasesDetailsResponseType objFindCasesDetailsResponseType = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            if (null != objFindCasesRequestType.getCustomerName()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getCustomerName())) {
                objFindCasesVO.setCustomerName(objFindCasesRequestType.getCustomerName());
            }
            if (null != objFindCasesRequestType.getRoadNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getRoadNumber())) {
                objFindCasesVO.setRoadNumber(objFindCasesRequestType.getRoadNumber());
            }
            if (null != objFindCasesRequestType.getRoadNumberHeader()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getRoadNumberHeader())) {
                objFindCasesVO.setRoadNumberHeader(objFindCasesRequestType.getRoadNumberHeader());
            }
            if (null != objFindCasesRequestType.getSearchOption()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getSearchOption())) {
                objFindCasesVO.setSearchOption(objFindCasesRequestType.getSearchOption());
            }
            if (null != objFindCasesRequestType.getFilterOption()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getFilterOption())) {
                objFindCasesVO.setFilterOption(objFindCasesRequestType.getFilterOption());
            }
            if (null != objFindCasesRequestType.getFieldValue()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getFieldValue())) {
                objFindCasesVO.setFieldValue(objFindCasesRequestType.getFieldValue());
            }
            if (null != objFindCasesRequestType.getCaseType()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getCaseType())) {
                objFindCasesVO.setCaseType(objFindCasesRequestType.getCaseType());
            }
            if (null != objFindCasesRequestType.getStartdate()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getStartdate())) {
                objFindCasesVO.setStartdate(objFindCasesRequestType.getStartdate());
            }
            if (null != objFindCasesRequestType.getEndDate()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType.getEndDate())) {
                objFindCasesVO.setEndDate(objFindCasesRequestType.getEndDate());
            }
            if (null != objFindCasesRequestType.getRoadNumberFilterSelected()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCasesRequestType
                            .getRoadNumberFilterSelected())) {
                objFindCasesVO.setRoadNumberFilterSelected(objFindCasesRequestType.getRoadNumberFilterSelected());
            }
            objFindCasesDetailsVOlst = objCaseEoaServiceIntf.getFindCases(objFindCasesVO);
            if (RMDCommonUtility.isCollectionNotEmpty(objFindCasesDetailsVOlst)) {
                objFindCasesDetailsResponseTypelst = new ArrayList<FindCasesDetailsResponseType>(
                        objFindCasesDetailsVOlst.size());
                for (FindCasesDetailsVO obj : objFindCasesDetailsVOlst) {
                    objFindCasesDetailsResponseType = new FindCasesDetailsResponseType();
                    objFindCasesDetailsResponseType.setCaseID(obj.getCaseID());
                    objFindCasesDetailsResponseType.setTitle(obj.getTitle());
                    objFindCasesDetailsResponseType.setCondition(obj.getCondition());
                    objFindCasesDetailsResponseType.setStatus(obj.getStatus());
                    objFindCasesDetailsResponseType.setContact(obj.getContact());

                    if (null != obj.getCreationTime()) {
                        objFindCasesDetailsResponseType.setCreationTime(obj.getCreationTime());
                    }

                    objFindCasesDetailsResponseType.setCaseType(obj.getCaseType());
                    objFindCasesDetailsResponseType.setCustomerId(obj.getCustomerId());
                    objFindCasesDetailsResponseType.setCustRNH(obj.getCustRNH());
                    objFindCasesDetailsResponseType.setRn(obj.getRn());
                    objFindCasesDetailsResponseType.setQueue(obj.getQueue());
                    objFindCasesDetailsResponseTypelst.add(objFindCasesDetailsResponseType);
                }
            }
            objFindCasesDetailsVOlst = null;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objFindCasesDetailsResponseTypelst;
    }

    /**
     * @Author:Mohamed
     * @param:uriParam
     * @return:List<MaterialUsageResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the list of part for case by invoking
     *               caseseoaserviceimpl.getMaterialUsage() method.
     */
    @GET
    @Path(OMDConstants.GET_MATERIAL_USAGE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MaterialUsageResponseType> getMaterilaUsage(@Context final UriInfo uriParam) throws RMDServiceException {
        List<MaterialUsageVO> objMaterialUsageVOlist = null;
        List<MaterialUsageResponseType> objMaterialUsageResponseTypeList = new ArrayList<MaterialUsageResponseType>();
        String serviceReqId = OMDConstants.SERVICE_REQ_ID;
        String lookUpDays = OMDConstants.MATERIAL_USAGE_LOOKUP_DAYS;
        MultivaluedMap<String, String> queryParams = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar creationDate;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.SERVICE_REQ_ID)) {
                    serviceReqId = queryParams.getFirst(OMDConstants.SERVICE_REQ_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.SERVICERQID_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.MATERIAL_USAGE_LOOKUP_DAYS)) {
                    lookUpDays = queryParams.getFirst(OMDConstants.MATERIAL_USAGE_LOOKUP_DAYS);
                } else {
                    throw new OMDInValidInputException(OMDConstants.MATERIAL_USAGE_LOOKUP_DAYS_NOT_PROVIDED);
                }
                objMaterialUsageVOlist = objCaseEoaServiceIntf.getMaterialUsage(serviceReqId, lookUpDays);
                if (RMDCommonUtility.isCollectionNotEmpty(objMaterialUsageVOlist)) {
                    for (MaterialUsageVO obj : objMaterialUsageVOlist) {
                        MaterialUsageResponseType objMaterialUsageResponseType = new MaterialUsageResponseType();
                        objMaterialUsageResponseType.setPartNo(obj.getPartNo());
                        objMaterialUsageResponseType.setDescription(obj.getDescription());
                        if (null != obj.getCreationDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(obj.getCreationDate());
                            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                            creationDate = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                            objMaterialUsageResponseType.setCreationDate(creationDate);
                        }
                        objMaterialUsageResponseType.setLocation(obj.getLocation());
                        objMaterialUsageResponseType.setQuantity(obj.getQuantity());
                        objMaterialUsageResponseTypeList.add(objMaterialUsageResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objMaterialUsageResponseTypeList;
    }

    @GET
    @Path(OMDConstants.MERGED_TO)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String mergeRx(@PathParam(OMDConstants.CASE_Id) final String caseId,
            @PathParam(OMDConstants.TO_CASE_ID) final String toCaseId,
            @PathParam(OMDConstants.USER_ID) final String userId, @PathParam(OMDConstants.RX_ID) final String rxId,
            @PathParam(OMDConstants.MERGE_TO_ID) final String mergedTo,
            @PathParam(OMDConstants.RULE_DEFINITION_ID) final String ruleDefId,
            @PathParam(OMDConstants.ASSET_GROUP_NAME) final String assetGrpName,
            @PathParam(OMDConstants.ASSET_NUMBER) final String assetNumber,
            @PathParam(OMDConstants.CUSTOMER_ID) final String customerId,
            @PathParam(OMDConstants.TOOL_ID) final String toolId,
            @PathParam(OMDConstants.TOOL_OBJID) final String toolObjId) throws RMDServiceException {

        CaseMergeServiceVO caseMergeServiceVO = null;
        String message = OMDConstants.SUCCESS;
        boolean isCaseClosed = false;

        try {
            caseMergeServiceVO = new CaseMergeServiceVO();
            caseMergeServiceVO.setCaseId(caseId);
            caseMergeServiceVO.setToCaseId(toCaseId);
            caseMergeServiceVO.setUserId(userId); // UserId
            caseMergeServiceVO.setRuleDefId(ruleDefId);
            caseMergeServiceVO.setAssetGrpName(assetGrpName);
            caseMergeServiceVO.setAssetNumber(assetNumber);
            caseMergeServiceVO.setCustomerId(customerId);
            caseMergeServiceVO.setRxId(rxId);
            caseMergeServiceVO.setMergedTo(mergedTo);
            caseMergeServiceVO.setToolId(toolId);
            caseMergeServiceVO.setToolObjId(toolObjId);

            LOG.debug("Case is" + caseId);
            LOG.debug("RX id" + rxId);
            LOG.debug("Merge Rx Id" + mergedTo);
            LOG.debug("User id" + userId);
            LOG.debug("customerId " + customerId);
            LOG.debug("assetNumber " + assetNumber);
            LOG.debug("assetGrpName " + assetGrpName);
            LOG.debug("ruleDefId " + ruleDefId);
            LOG.debug("toolId " + toolId);
            LOG.debug("ToCaseId " + toCaseId);

            if (caseId == null || rxId == null || mergedTo == null || customerId == null || assetNumber == null
                    || assetGrpName == null || ruleDefId == null || toCaseId == null) {

                LOG.debug("One more more input params are null");
                throw new Exception();
            }

            LOG.debug("******************************Appending Rx********************************************************************");
            objCaseEoaServiceIntf.mergeRx(caseMergeServiceVO);
            LOG.debug("******************************Merge Successfully***********************************************************");

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        LOG.debug("message" + message);
        return message;
    }

    /**
     * @Author :Mohamed
     * @return :List<CaseInfoType>
     * @param : UriInfo
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or not
     */
    @GET
    @Path(OMDConstants.GET_RX_DETAILS_FOR_RECLOSE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseInfoType> getRxDetailsForReClose(@Context final UriInfo uriParam) throws RMDServiceException {
        List<CaseInfoType> caseInfoTypeList = new ArrayList<CaseInfoType>();
        List<FindCaseServiceVO> objFindCaseServiceVOList = null;
        String caseId = null;
        String locoId = null;
        FindCaseServiceVO objFindCaseServiceVO = null;
        MultivaluedMap<String, String> queryParams = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            queryParams = uriParam.getQueryParameters();

            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CASEID)) {
                    caseId = queryParams.getFirst(OMDConstants.CASEID);
                }
                objFindCaseServiceVO = new FindCaseServiceVO();
                objFindCaseServiceVO.setStrCaseId(caseId);
                objFindCaseServiceVOList = objCaseEoaServiceIntf.getRxDetailsForReClose(objFindCaseServiceVO);
                if (RMDCommonUtility.isCollectionNotEmpty(objFindCaseServiceVOList)) {
                    for (FindCaseServiceVO obj : objFindCaseServiceVOList) {
                        CaseInfoType caseInfoType = new CaseInfoType();
                        caseInfoType.setCaseSolutionStatus(obj.getStrRxStatus());

                        caseInfoTypeList.add(caseInfoType);
                    }
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));

        }
        return caseInfoTypeList;
    }
    /**
     * This method is used to Score and Close a particular Rx.
     * 
     * @param CaseRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.CLOSE_CASE_RESULT)
    @Consumes(MediaType.APPLICATION_XML)
    public void updateCloseCaseResult(ReCloseRequestType reClose) throws RMDServiceException {
        ReCloseVO objReCloseVO = null;
        try {
            if (reClose != null) {
                final String caseId = reClose.getCaseId();
                final String userId = reClose.getUserId();
                final String action = reClose.getReCloseAction();
                final String appendCaseId = reClose.getAppendCaseId();
                if (null == caseId || caseId.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(caseId))) {
                    throw new OMDInValidInputException(OMDConstants.RX_CASEID_NOT_PROVIDED);
                }
                objReCloseVO = new ReCloseVO();
                objReCloseVO.setCaseId(caseId);
                objReCloseVO.setUserId(userId);
                objReCloseVO.setReCloseAction(action);
                objReCloseVO.setAppendCaseId(appendCaseId);
                objCaseEoaServiceIntf.updateCloseCaseResult(objReCloseVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * This method is used to Score and Close a particular Rx.
     * 
     * @param CaseRequestType
     * @return void
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.RECLOSE_RESET_FAULTS)
    @Consumes(MediaType.APPLICATION_XML)
    public void reCloseResetFaults(ReCloseRequestType reCloseRequestType) throws RMDServiceException {
        ScoreRxEoaVO scoreRxEoaVO = null;
        try {
            if (reCloseRequestType != null) {
                final String userName = reCloseRequestType.getUserId();
                final String caseId = reCloseRequestType.getCaseId();
                if (null == caseId || caseId.equals(OMDConstants.EMPTY_STRING)
                        || (!RMDCommonUtility.isAlphaNumericWithHyphen(caseId))) {
                    throw new OMDInValidInputException(OMDConstants.RX_CASEID_NOT_PROVIDED);
                }
                ReCloseVO reCloseVO = new ReCloseVO();
                reCloseVO.setUserId(userName);
                reCloseVO.setCaseId(caseId);
                objCaseEoaServiceIntf.reCloseResetFaults(reCloseVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            LOG.debug(rmdServiceException.getMessage(), rmdServiceException);
            throw new OMDApplicationException(rmdServiceException.getErrorDetail().getErrorCode(), rmdServiceException
                    .getErrorDetail().getErrorMessage(), rmdServiceException.getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding unit level notes
     */
    @POST
    @Path(OMDConstants.ADD_NOTES_TO_UNIT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String addNotesToUnit(final NotesRequestType requestobj) throws RMDServiceException {
        String result = null;
        AddNotesEoaServiceVO objAddNotesServiceVO = null;
        try {
            if (null != requestobj) {
                objAddNotesServiceVO = new AddNotesEoaServiceVO();
                if (null != requestobj.getAssestNumber()) {
                    objAddNotesServiceVO.setAssetNumber(requestobj.getAssestNumber());
                } else {
                    throw new OMDInValidInputException(OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
                }
                if (null != requestobj.getCustomerID()) {
                    objAddNotesServiceVO.setCustomerId(requestobj.getCustomerID());
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }
                if (null != requestobj.getAssetGrpName()) {
                    objAddNotesServiceVO.setAssetGrpName(requestobj.getAssetGrpName());
                } else {
                    throw new OMDInValidInputException(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
                }
                if (null != requestobj.getNotes()) {
                    objAddNotesServiceVO.setNoteDescription(EsapiUtil.resumeSpecialChars(requestobj.getNotes()));
                } else {
                    throw new OMDInValidInputException(OMDConstants.NOTE_DESCRIPTION_NOT_PROVIDED);
                }
                if (null != requestobj.getApplyLevel()) {
                    objAddNotesServiceVO.setApplyLevel(requestobj.getApplyLevel());
                } else {
                    throw new OMDInValidInputException(OMDConstants.APPLY_LEVEL_NOT_PROVIDED);
                }

                if (null != requestobj.getUserId()) {
                    if (AppSecUtil.checkAlphaNumeric(requestobj.getUserId())) {
                        objAddNotesServiceVO.setUserId(requestobj.getUserId());
                    } else {
                        throw new OMDInValidInputException(OMDConstants.INVALID_USERID);

                    }

                } else {
                    throw new OMDInValidInputException(OMDConstants.USERID_NOT_PROVIDED);
                }
                objAddNotesServiceVO.setSticky(requestobj.getSticky());
            } else {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);

            }
            result = objCaseEoaServiceIntf.addNotesToUnit(objAddNotesServiceVO);

        }

        catch (Exception e) {
            LOG.error("ERROR IN ADD NOTES TO CASE IN CASERESOURCE:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching unit Sticky notes details for a given case.This is
     *                   done by invoking fetchStickyUnitNotes() of CaseEoaServiceImpl Layer.
     */
    @GET
    @Path(OMDConstants.FETCH_UNIT_LEVEL_STICKY_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StickyNotesDetailsResponseType fetchStickyUnitLevelNotes(@Context UriInfo ui) throws RMDServiceException {
        String caseId = null;
        StickyNotesDetailsResponseType objStickyDetailsResponseType = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar entryTime;
        String assetNumber = null;
        String customerId = null;
        String assetGrpName = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                assetNumber = queryParams.getFirst(OMDConstants.ASSET_NUMBER);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                assetGrpName = queryParams.getFirst(OMDConstants.ASSET_GROUP_NAME);
            }
            StickyNotesDetailsVO objStickyNotesDetailsVO = objCaseEoaServiceIntf.fetchStickyUnitLevelNotes(assetNumber,
                    customerId, assetGrpName);
            if (null != objStickyNotesDetailsVO) {
                objStickyDetailsResponseType = new StickyNotesDetailsResponseType();
                objStickyDetailsResponseType.setApplyLevel(objStickyNotesDetailsVO.getApplyLevel());
                objStickyDetailsResponseType.setAdditionalInfo(objStickyNotesDetailsVO.getAdditionalInfo());
                objStickyDetailsResponseType.setCreatedBy(objStickyNotesDetailsVO.getCreatedBy());
                objStickyDetailsResponseType.setObjId(objStickyNotesDetailsVO.getObjId());
                if (null != objStickyNotesDetailsVO.getEntryTime()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objStickyNotesDetailsVO.getEntryTime());
                    RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                    entryTime = dtf.newXMLGregorianCalendar(objGregorianCalendar);
                    objStickyDetailsResponseType.setEntryTime(entryTime);
                }
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT FETCH STICKY UNIT NOTES METHOD IN CASERESOURCE:" + e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objStickyDetailsResponseType;
    }

    /**
     * @Author :
     * @return :String
     * @param : RepairCodeDetailsType objRepairCodeDetailsType
     * @throws :RMDServiceException
     * @Description:This method is used to add an RepairCode for a particular case.
     */
    @POST
    @Path(OMDConstants.CAST_GPOC_VOTE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String castGPOCVote(RepairCodeDetailsType objRepairCodeDetailsType) throws RMDServiceException {
        CaseRepairCodeEoaVO objRepairCodeVO = null;
        String repsonseMsg = null;
        try {
            objRepairCodeVO = new CaseRepairCodeEoaVO();
            if (RMDCommonUtility.isNullOrEmpty(objRepairCodeDetailsType.getCaseID())) {
                throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
            }
            if (RMDCommonUtility.isNullOrEmpty(objRepairCodeDetailsType.getRepairCode())) {
                throw new OMDInValidInputException(OMDConstants.REPAIRCODE_NOT_PROVIDED);
            }
            objRepairCodeVO.setUserId(objRepairCodeDetailsType.getCmAliasName());
            objRepairCodeVO.setCaseId(objRepairCodeDetailsType.getCaseID());
            objRepairCodeVO.setRepairCode(objRepairCodeDetailsType.getRepairCode());
            repsonseMsg = objCaseEoaServiceIntf.castGPOCVote(objRepairCodeVO);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  castGPOCVote() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return repsonseMsg;
    }

    /**
     * @Author :
     * @return :String
     * @param : caseObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     */
    @GET
    @Path(OMDConstants.GET_PREVIOUS_VOTE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getPreviousVote(@Context UriInfo ui) throws RMDServiceException {
        String previousVote = null;
        String caseObjId = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CASE_OBJID)) {
                caseObjId = queryParams.getFirst(OMDConstants.CASE_OBJID);
            }
            if (RMDCommonUtility.isNullOrEmpty(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.CASE_OBJID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNumeric(caseObjId)) {
                throw new OMDInValidInputException(OMDConstants.INVALID_CASEOBJID);
            }
            previousVote = objCaseEoaServiceIntf.getPreviousVote(caseObjId);

        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT  getPreviousVote() METHOD OF CASERESOURCE ", e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return previousVote;
    }
    /**
     * This method is used for fetching open cases based on the parameter passed
     * 
     * @param uriParam
     * @return List of cases
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @POST
    @Path(OMDConstants.OPEN_CASES)
    @Consumes(MediaType.APPLICATION_XML)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<OpenCaseResponseType> openCases(final OpenCaseRequestType objCasesRequestType)
            throws RMDServiceException {
        List<OpenCaseHomeVO> userCaseList = null;
        List<OpenCaseResponseType> caseResLst = new ArrayList<OpenCaseResponseType>();
        ListIterator<OpenCaseHomeVO> caseLstIter = null;
        OpenCaseServiceVO objOpenCaseServiceVO = null;
        OpenCaseHomeVO selCaseHomeEoaVO = null;
        OpenCaseResponseType caseResType = null;
        try {
            /* Fetches the list of cases for dynamic work queue */
            if (null != objCasesRequestType.getDwq() && !objCasesRequestType.getDwq().isEmpty()) {
                objOpenCaseServiceVO = new OpenCaseServiceVO();
                if (validateOpenCases(objCasesRequestType)) {
                    if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
                        objOpenCaseServiceVO.setStrCustomerId(objCasesRequestType.getCustomerId());
                    } else {
                        objOpenCaseServiceVO.setStrCustomerId(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != objCasesRequestType.getLanguage() && !objCasesRequestType.getLanguage().isEmpty()) {
                        objOpenCaseServiceVO.setStrLanguage(objCasesRequestType.getLanguage());
                    } else {
                        objOpenCaseServiceVO.setStrLanguage(OMDConstants.DEFAULT_LANGUAGE);
                    }
                    userCaseList = objWorkQueueServiceIntf.getDynamicWorkQueueCasesSummary(objOpenCaseServiceVO);
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            }

            if (RMDCommonUtility.isCollectionNotEmpty(userCaseList)) {
                caseResLst = new ArrayList<OpenCaseResponseType>(userCaseList.size());
                long startTime = System.currentTimeMillis();
                caseLstIter = userCaseList.listIterator();
                while (caseLstIter.hasNext()) {
                    selCaseHomeEoaVO = (OpenCaseHomeVO) caseLstIter.next();
                    caseResType = new OpenCaseResponseType();
                    if (null != selCaseHomeEoaVO.getDtCreationDate()) {
                        caseResType.setCreatedDate(selCaseHomeEoaVO.getDtCreationDate());
                    }
                    CMBeanUtility.copyOpenCaseHomeVOToCaseResType(selCaseHomeEoaVO, caseResType);
                    caseResLst.add(caseResType);
                }
                final long endTime = System.currentTimeMillis();
                LOG.debug("Case Resource Time for copying properties reqd start::" + startTime + "::end time" + endTime
                        + "Diff:" + (endTime - startTime));
                userCaseList = null;
            } /*
               * else { throw new OMDNoResultFoundException( OMDConstants.NORECORDFOUNDEXCEPTION); }
               */
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseResLst;

    }

    /**
     * This method is used to validate the data used for getting cases CasesRequestType
     * 
     * @return boolean
     */
    public static boolean validateOpenCases(final OpenCaseRequestType objCasesRequestType) {

        if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericComma(objCasesRequestType.getCustomerId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Author:
     * @param:CaseRequestType
     * @return:List<CaseHeaderResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the set of cases for asset by invoking
     *               caseseoaserviceimpl.getAssetCases() method.
     */
    @POST
    @Path(OMDConstants.HEADER_SEARCH_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseHeaderResponseType> getHeaderSearchCasesData(CaseRequestType objCaseRequestType)
            throws RMDServiceException {

        String customerName;
        String roadNo;
        String roadInitial;
        String caseType;
        String noOfDays;
        String caseLike;
        List<CaseHeaderResponseType> caseInfotypeList = new ArrayList<CaseHeaderResponseType>();
        CaseHeaderResponseType objCaseInfoType = null;
        FindCaseServiceVO objFindCaseServiceVO = new FindCaseServiceVO();
        List<CasesHeaderVO> objSelectCaseHomeVOList = new ArrayList<CasesHeaderVO>();

        try {

            if (null != objCaseRequestType.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCustomerId())) {
                customerName = objCaseRequestType.getCustomerId();
                objFindCaseServiceVO.setStrCustomerId(customerName);
            }
            if (null != objCaseRequestType.getAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetNumber())) {
                roadNo = objCaseRequestType.getAssetNumber();
                objFindCaseServiceVO.setStrAssetNumber(roadNo);
            }
            if (null != objCaseRequestType.getAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getAssetGrpName())) {
                roadInitial = objCaseRequestType.getAssetGrpName();
                objFindCaseServiceVO.setStrAssetGrpName(roadInitial);
            }
            if (null != objCaseRequestType.getCaseType()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCaseRequestType.getCaseType())) {
                caseType = objCaseRequestType.getCaseType();
                objFindCaseServiceVO.setStrCaseType(caseType);
            }
            if (null != objCaseRequestType.getNoOfDays())

            {
                noOfDays = objCaseRequestType.getNoOfDays();
                objFindCaseServiceVO.setNoOfDays(noOfDays);
            }
            if (null != objCaseRequestType.getCaseLike()) {
                caseLike = objCaseRequestType.getCaseLike();
                objFindCaseServiceVO.setCaseID(caseLike);
            }
            objSelectCaseHomeVOList = objCaseEoaServiceIntf.getHeaderSearchCasesData(objFindCaseServiceVO);
            if (objSelectCaseHomeVOList != null && objSelectCaseHomeVOList.size() > 0) {
                caseInfotypeList = new ArrayList<CaseHeaderResponseType>(objSelectCaseHomeVOList.size());
                for (CasesHeaderVO details : objSelectCaseHomeVOList) {
                    objCaseInfoType = new CaseHeaderResponseType();
                    objCaseInfoType.setCaseID(details.getStrCaseId());
                    objCaseInfoType.setRoadNumber(details.getStrAssetNumber());
                    objCaseInfoType.setCustomerId(details.getStrAssetHeader());
                    objCaseInfoType.setCustomerName(details.getStrcustomerName());
                    caseInfotypeList.add(objCaseInfoType);
                }
            }
            objSelectCaseHomeVOList = null;
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return caseInfotypeList;

    }
    /**
     * @Author :
     * @return :String
     * @param : caseObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     */
    @GET
    @Path(OMDConstants.GET_OPEN_COMM_RX_COUNT)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseTrendResponseType> getOpenCommRxCount(@Context UriInfo ui) throws RMDServiceException {
        List<CaseTrendResponseType> responseList = new ArrayList<CaseTrendResponseType>();
        List<CaseTrendVO> valueList = new ArrayList<CaseTrendVO>();
        CaseTrendResponseType objCaseTrendResponseType = null;
        try {
            LOG.debug("*****getOpenCommRxCount WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            valueList = objCaseEoaServiceIntf.getOpenCommRxCount();
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<CaseTrendResponseType>(valueList.size());
            }
            for (CaseTrendVO objCaseTrendVO : valueList) {
                objCaseTrendResponseType = new CaseTrendResponseType();
                objCaseTrendResponseType.setCustomer(objCaseTrendVO.getCustomer());
                objCaseTrendResponseType.setRxCount(objCaseTrendVO.getCount());
                responseList.add(objCaseTrendResponseType);

            }
            valueList = null;

        } catch (Exception e) {
            LOG.error("Exception occuered in getOpenCommRxCount() method of CaseTrendResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return responseList;
    }
    /**
     * @Author :
     * @return :String
     * @param : caseObjId
     * @throws :RMDWebException
     * @Description: This method is used to get Rx Details of the Case.
     */
    @GET
    @Path(OMDConstants.GET_CASE_CONVERSION_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseConversionResponseType> getCaseConversionDetails(@Context UriInfo ui) throws RMDServiceException {
        List<CaseConversionResponseType> responseList = new ArrayList<CaseConversionResponseType>();
        List<CaseConvertionVO> valueList = new ArrayList<CaseConvertionVO>();
        CaseConversionResponseType objCaseConversionResponseType = null;
        try {
            LOG.debug("*****getOpenCommRxCount WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            valueList = objCaseEoaServiceIntf.getCaseConversionDetails();
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<CaseConversionResponseType>(valueList.size());
            }
            for (CaseConvertionVO objCaseConvertionVO : valueList) {
                objCaseConversionResponseType = new CaseConversionResponseType();
                objCaseConversionResponseType.setMonth(objCaseConvertionVO.getMonth());
                objCaseConversionResponseType.setYear(objCaseConvertionVO.getYear());
                objCaseConversionResponseType.setConvCount(objCaseConvertionVO.getConvCount());
                responseList.add(objCaseConversionResponseType);

            }
            valueList = null;

        } catch (Exception e) {
            LOG.error("Exception occuered in getOpenCommRxCount() method of CaseTrendResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return responseList;
    }
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get case conversion percentage for past 24 hours.
     */
    @GET
    @Path(OMDConstants.GET_CASE_CONVERSION_PERCENTAGE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getCaseConversionPercentage(@Context UriInfo ui) throws RMDServiceException {
        String convPercentage = null;
        try {
            LOG.debug("*****getCaseConversionPercentage WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            convPercentage = objCaseEoaServiceIntf.getCaseConversionPercentage();

        } catch (Exception e) {
            LOG.error("Exception occuered in getCaseConversionPercentage() method of CaseTrendResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return convPercentage;
    }
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get case conversion percentage for past 24 hours.
     */
    @GET
    @Path(OMDConstants.GET_TOP_NO_ACTION_RX_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseConversionResponseType> getTopNoActionRXDetails(@Context UriInfo ui) throws RMDServiceException {
        List<CaseConversionResponseType> responseList = new ArrayList<CaseConversionResponseType>();
        List<CaseConvertionVO> valueList = new ArrayList<CaseConvertionVO>();
        CaseConversionResponseType objCaseConversionResponseType = null;
        try {
            LOG.debug("*****getTopNoActionRXDetails WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            valueList = objCaseEoaServiceIntf.getTopNoActionRXDetails();
            if (valueList != null && !valueList.isEmpty()) {
                responseList = new ArrayList<CaseConversionResponseType>(valueList.size());
            }
            for (CaseConvertionVO objCaseConvertionVO : valueList) {
                objCaseConversionResponseType = new CaseConversionResponseType();
                objCaseConversionResponseType.setRxTitle(objCaseConvertionVO.getRxTitle());
                objCaseConversionResponseType.setRxCount(objCaseConvertionVO.getRxCount());
                responseList.add(objCaseConversionResponseType);

            }
            valueList = null;

        } catch (Exception e) {
            LOG.error("Exception occuered in getTopNoActionRXDetails() method of CaseResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return responseList;
    }

    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get comm notes which are having flag as Y.
     */
    @GET
    @Path(OMDConstants.GET_COMM_NOTES_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GeneralNotesResponseType> getCommNotesDetails(@Context UriInfo ui) throws RMDServiceException {
        List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
        GeneralNotesResponseType objGeneralNotesResponseType =null;
        List<GeneralNotesResponseType> arlGeneralNotesResponseType = null;
        try {
            LOG.debug("*****getCommNotesDetails WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS).format(new Date()));
            arlGeneralNotesEoaServiceVO = objCaseEoaServiceIntf.getCommNotesDetails();
            if(RMDCommonUtility.isCollectionNotEmpty(arlGeneralNotesEoaServiceVO)){
                arlGeneralNotesResponseType = new ArrayList<GeneralNotesResponseType>(arlGeneralNotesEoaServiceVO.size());
                for(GeneralNotesEoaServiceVO objNotes : arlGeneralNotesEoaServiceVO){
                    objGeneralNotesResponseType = new GeneralNotesResponseType();
                    objGeneralNotesResponseType.setNotesdesc(objNotes.getNotesdesc());
                    objGeneralNotesResponseType.setEnteredby(objNotes.getEnteredby());
                    arlGeneralNotesResponseType.add(objGeneralNotesResponseType);
                }
            }
            
        }catch (Exception e) {
            LOG.error("Exception occuered in getCommNotesDetails() method of CaseResource" + e);
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return arlGeneralNotesResponseType;
    }

    /**  */
    /**
     * @Author :
     * @return :String
     * @param : UriInfo ui
     * @throws :RMDWebException
     * @Description: To enforce an additional code for non RU repair codes,before close. return
     *               false if additional repair code is needed..
     */
    @GET
    @Path(OMDConstants.GET_REP_CODE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getAddRepCodeDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        String retValue = null;
        boolean result = true;
        String caseId = OMDConstants.VIEW_CLOSURE_CASE_ID;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.VIEW_CLOSURE_CASE_ID)) {
                    caseId = queryParams.getFirst(OMDConstants.VIEW_CLOSURE_CASE_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                result = objCaseEoaServiceIntf.getAddRepCodeDetails(caseId);
                
                retValue = String.valueOf(result);
                

            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return retValue;
    }

    @GET
    @Path(OMDConstants.GET_LOOKUP_REP_CODE_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getLookUpRepCodeDetails(@Context final UriInfo uriParam) throws RMDServiceException {
        String retValue = null;
        boolean result = true;
        String repairCodeList = OMDConstants.REPAIR_CODE_LIST;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.REPAIR_CODE_LIST)) {
                    repairCodeList = queryParams.getFirst(OMDConstants.REPAIR_CODE_LIST);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CASEID_NOT_PROVIDED);
                }
                result = objCaseEoaServiceIntf.getLookUpRepCodeDetails(repairCodeList);
                
                retValue = String.valueOf(result);
                

            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return retValue;
    }
    
    @GET
    @Path(OMDConstants.GET_CASESCORE_REPAIR_CODE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CaseScoreRepairCodesResponseType> getCaseScoreRepairCodes(@Context final UriInfo uriParam)
            throws RMDServiceException {
        List<CaseScoreRepairCodeVO> objCloseOutRepairCodeVOlist = null;
        List<CaseScoreRepairCodesResponseType> objCloseOutRepairCodesResponseTypeList = new ArrayList<CaseScoreRepairCodesResponseType>();
        String rxCaseId = OMDConstants.RX_CASE_ID;
        MultivaluedMap<String, String> queryParams = null;
        try {

            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.RX_CASE_ID)) {
                	rxCaseId = queryParams.getFirst(OMDConstants.RX_CASE_ID);
                }
                objCloseOutRepairCodeVOlist = objCaseEoaServiceIntf.getCaseScoreRepairCodes(rxCaseId);
                if (RMDCommonUtility.isCollectionNotEmpty(objCloseOutRepairCodeVOlist)) {
                    for (CaseScoreRepairCodeVO obj : objCloseOutRepairCodeVOlist) {
                    	CaseScoreRepairCodesResponseType objCloseOutRepairCodesResponseType = new CaseScoreRepairCodesResponseType();
                        objCloseOutRepairCodesResponseType.setRepairCode(obj.getRepairCode());
                        objCloseOutRepairCodesResponseType.setDescription(obj.getDescription());
                        objCloseOutRepairCodesResponseType.setRepairCodeId(obj.getRepairCodeId());
                        objCloseOutRepairCodesResponseTypeList.add(objCloseOutRepairCodesResponseType);
                    }
                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objCloseOutRepairCodesResponseTypeList;
    }
    
    @GET
    @Path(OMDConstants.VALIDATE_VEH_BOMS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public RxVehicleBomResponseType validateVehBoms(@Context final UriInfo uriParam) throws RMDServiceException {
        List<String> result;
        String customer = OMDConstants.EMPTY_STRING;
        String rnh = OMDConstants.EMPTY_STRING;
        String rn = OMDConstants.EMPTY_STRING;
        String rxObjId = OMDConstants.EMPTY_STRING;
        String fromScreen = OMDConstants.EMPTY_STRING;
        RxVehicleBomResponseType objRxVehicleBomResponseType=null;
        MultivaluedMap<String, String> queryParams = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (!queryParams.isEmpty()) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                } else {
                    throw new OMDInValidInputException(OMDConstants.CUSTOMERNAME_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.RNH)) {
                    rnh = queryParams.getFirst(OMDConstants.RNH);
                } else {
                    throw new OMDInValidInputException(OMDConstants.RNH_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.ROAD_NUMBER)) {
                    rn = queryParams.getFirst(OMDConstants.ROAD_NUMBER);
                } else {
                    throw new OMDInValidInputException(OMDConstants.ROAD_NUMBER_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.RX_OBJ_ID)) {
                    rxObjId = queryParams.getFirst(OMDConstants.RX_OBJ_ID);
                } else {
                    throw new OMDInValidInputException(OMDConstants.RX_OBJID_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.FROM_SCREEN)) {
                    fromScreen = queryParams.getFirst(OMDConstants.FROM_SCREEN);
                }
                result = objCaseEoaServiceIntf.validateVehBoms(customer,rnh,rn,rxObjId,fromScreen);
                if(RMDCommonUtility.isCollectionNotEmpty(result)){ 
                    objRxVehicleBomResponseType=new RxVehicleBomResponseType();
                    objRxVehicleBomResponseType.setAssetList(result);
                }
                             
            } else {
                throw new OMDInValidInputException(OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objRxVehicleBomResponseType;
    }
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method returns the list of roles which have EOA Onsite Privilege by
     *                   calling the getEoaOnsiteRoles() method of Service Layer.
     */
    @GET
    @Path(OMDConstants.GET_EOA_ONSITE_ROLES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<RolesResponseType> getEoaOnsiteRoles() throws RMDServiceException {
        List<RolesResponseType> rolesResponseList = new ArrayList<RolesResponseType>();
        try {
            List<RolesVO> eoaOnsitePreviligeRoleList = objRolesEoaServiceIntf.getEoaOnsiteRoles();

            for (RolesVO objRolesVO : eoaOnsitePreviligeRoleList) {
                RolesResponseType objRolesResponseType = new RolesResponseType();
                objRolesResponseType.setRoleSeqId(objRolesVO.getGetUsrRolesSeqId());
                objRolesResponseType.setRoleName(objRolesVO.getRoleName());
                rolesResponseList.add(objRolesResponseType);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return rolesResponseList;
    }
    
    
    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CASE_RX_DELV_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CaseRxDetailsResponseType getCaseRXDelvDetails(@PathParam(OMDConstants.CASE_Id) final String caseId)
            throws RMDServiceException {

        CaseRxDetailsResponseType objCaseRxDetailsResponseType = new CaseRxDetailsResponseType();
        List<RecomDelvInfoServiceVO> rxList = null;
        try {
            if (null != caseId && !caseId.equals(OMDConstants.EMPTY_STRING)) {
                if (AppSecUtil.checkAlphaNumericHypen(caseId)) {
                    objCaseRxDetailsResponseType.setCaseID(caseId);
                    rxList = objCaseEoaServiceIntf.getCaseRXDelvDetails(caseId);
                    if (RMDCommonUtility.isCollectionNotEmpty(rxList)) {
                    	List<SolutionInfoType> arlSolutionInfoType=new ArrayList<SolutionInfoType>();
                        Iterator<RecomDelvInfoServiceVO> it = rxList.iterator();
                        while (it.hasNext()) {
                            RecomDelvInfoServiceVO tempVO = it.next();
                            SolutionInfoType solutionInfoType = new SolutionInfoType();
                            if (null != tempVO.getStrRxObjid() && !tempVO.getStrRxObjid().isEmpty()) {
                                solutionInfoType.setSolutionOBJID(tempVO.getStrRxObjid());
                            }
                            if (null != tempVO.getStrRxTitle() && !tempVO.getStrRxTitle().isEmpty()) {
                                solutionInfoType.setSolutionTitle(tempVO.getStrRxTitle());
                            }
                            if (null != tempVO.getStrUrgRepair() && !tempVO.getStrUrgRepair().isEmpty()) {
                                solutionInfoType.setUrgency(tempVO.getStrUrgRepair());
                            }
                            if (null != tempVO.getStrEstmRepTime() && !tempVO.getStrEstmRepTime().isEmpty()) {
                                solutionInfoType.setEstmRepTime(tempVO.getStrEstmRepTime());
                            }
                            arlSolutionInfoType.add(solutionInfoType);                            
                        }
                        objCaseRxDetailsResponseType.setSolutionInfo(arlSolutionInfoType);
                    } /*else {
                        throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                    }*/
                } else {
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.CASEID_NOT_PROVIDED),
                                new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[]{}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return objCaseRxDetailsResponseType;
    }

}
