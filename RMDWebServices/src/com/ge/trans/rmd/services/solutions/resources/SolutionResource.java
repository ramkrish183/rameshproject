/**
 * ============================================================
 * Classification: GE Confidential
 * File : SolutionResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.solutions.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :UST on March 27 2012
 * Version : 1.0
 * Created on : August 5, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.solutions.resources;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ScoreRxEoaVO;
import com.ge.trans.eoa.services.tools.rx.service.intf.RecommEoaServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.intf.RxExecutionEoaServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.ActionableRxTypeVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxPlotVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskResultVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxConfigVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxHistServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultEoaLiteServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxTaskRepairCodeVO;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.cases.valueobjects.RxWorstUrgencyVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDDirectoryCreationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.RxDetailsType;
import com.ge.trans.rmd.services.solutions.valueobjects.ActionableRxTypeResponse;
import com.ge.trans.rmd.services.solutions.valueobjects.LookupResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.RepairCodeType;
import com.ge.trans.rmd.services.solutions.valueobjects.RxDeliveryAttachmentResponse;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionConfigType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionExecutionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionExecutionResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionHistoryType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionLiteResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionPlotType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDocType;
import com.ge.trans.rmd.services.solutions.valueobjects.UrgencyResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Aug 5, 2011
 * @Date Modified : Aug 5, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Solution Web services and provide the
 *              Solution related functionalities
 * @History :
 * 
 ******************************************************************************/

@Path(OMDConstants.SOLUTION_SERVICE)
@Component
public class SolutionResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(SolutionResource.class);
    @Autowired
    RecommEoaServiceIntf objRecommEoaServiceIntf;
    @Autowired
    RxExecutionEoaServiceIntf objRxExecutionEoaServiceIntf;
    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    CaseEoaServiceIntf objCaseEoaServiceIntf;

    /**
     * @Description:This method is used for fetching Solution related lookup
     *                   values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_SOLUTION_PARAMETERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSolutionParameters(@Context UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;
        String strLookupId = OMDConstants.EMPTY_STRING;
        ElementVO objElementVO = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = {OMDConstants.ALPHA_UNDERSCORE};
            if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,OMDConstants.LIST_NAME)){
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strLookupId = queryParams.getFirst(OMDConstants.LIST_NAME);
                } else {
                    throw new OMDInValidInputException(OMDConstants.LISTNAME_NOT_PROVIDED);
                }

                arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
                .getRxLookupValues(strLookupId, getRequestHeader(OMDConstants.LANGUAGE));
                if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                    arlLookupTypes = new ArrayList<LookupResponseType>();
                    for (final Iterator<ElementVO> iterator = arlLookupList.iterator(); iterator.hasNext();) {
                        objElementVO =  iterator.next();
                        objLookupResponseType = new LookupResponseType();
                        CMBeanUtility.copyElementVOToLookupResponseType(objElementVO, objLookupResponseType);
                        arlLookupTypes.add(objLookupResponseType);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            }else{
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        }catch (Exception ex) {
        	LOG.error("getSolutionParameters", ex);
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupTypes;
    }

    /**
     * This method is used for fetching the Solution Execution details based on
     * the input - rxCaseId
     * 
     * @param strRxCaseId
     * @return SolutionExecutionResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SOLUTION_EXE_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SolutionExecutionResponseType getSolutionExecutionDetails(
            @PathParam(OMDConstants.RX_CASE_ID) final String strRxCaseId,@Context final UriInfo ui)
    throws RMDServiceException {
        RxExecTaskDetailsServiceVO objRxExecTaskDetailsServiceVO;
        SolutionExecutionResponseType objSolutionExecutionResponseType = null;
        TaskDetailType objTaskDetailType;
        List<RxExecTaskServiceVO> arlRxExecTask;
        List<String> arlRecomTaskList;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar issueDate = null;
        XMLGregorianCalendar closedDate = null;
        XMLGregorianCalendar lastUpDate = null;
        String strRXCaseID = null;
        String timeZone=getDefaultTimeZone();
        String customerId = null;
        boolean isMobileRequest=true;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            if (!queryParams.isEmpty() && queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            MultivaluedMap< String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.RX_CASE_ID, strRxCaseId);
            int[] methodConstants = {RMDCommonConstants.ALPHA_NUMERIC_HYPEN};
            if (strRxCaseId != null && AppSecUtil.validateWebServiceInput(map, null, methodConstants, OMDConstants.RX_CASE_ID)) {
                strRXCaseID = strRxCaseId;
            } else {
                throw new OMDInValidInputException(
                        BeanUtility.getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            objRxExecTaskDetailsServiceVO = objRxExecutionEoaServiceIntf
            .getRxExecutionDetails(strRXCaseID, getRequestHeader(OMDConstants.LANGUAGE),customerId,isMobileRequest);

            arlRxExecTask = objRxExecTaskDetailsServiceVO.getArlExecTasks();
            arlRecomTaskList = objRxExecTaskDetailsServiceVO.getArlRecomTaskList();

            objSolutionExecutionResponseType = new SolutionExecutionResponseType();
            if (arlRecomTaskList != null && RMDCommonUtility.isCollectionNotEmpty(arlRecomTaskList)) {
            	for(String strRecom : arlRecomTaskList){
                    objSolutionExecutionResponseType.getArlRecomTaskList().add(strRecom);
                }
            }
            if (RMDCommonUtility.isCollectionNotEmpty(arlRxExecTask)) {
            	for(RxExecTaskServiceVO objExecTaskServiceVO : arlRxExecTask){
                    objTaskDetailType = new TaskDetailType();
                    CMBeanUtility.copyRxExecTaskServiceVOToTaskDetailType(objExecTaskServiceVO, objTaskDetailType,timeZone);
                    objSolutionExecutionResponseType.getTaskDetail().add(objTaskDetailType);
                    if (objExecTaskServiceVO.getLastUpdateDate() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objExecTaskServiceVO.getLastUpdateDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);
                        lastUpDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                        objTaskDetailType.setLastUpdateDate(lastUpDate);

                    }
                }

            } else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            if (objRxExecTaskDetailsServiceVO.getDtRxClosedDate() != null) {

                objGregorianCalendar = new GregorianCalendar();
                objGregorianCalendar.setTime(objRxExecTaskDetailsServiceVO.getDtRxClosedDate());
                RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                closedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                objSolutionExecutionResponseType.setClosedDate(closedDate); 
            }
            if (objRxExecTaskDetailsServiceVO.getDtRxIssueDate() != null) {
                objGregorianCalendar = new GregorianCalendar();
                objGregorianCalendar.setTime(objRxExecTaskDetailsServiceVO.getDtRxIssueDate());
                issueDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                objSolutionExecutionResponseType.setIssueDate(issueDate);
            }
            objSolutionExecutionResponseType.setRxLocationId(objRxExecTaskDetailsServiceVO.getLocationId());
            objSolutionExecutionResponseType.setVersion(objRxExecTaskDetailsServiceVO.getVersion());
            CMBeanUtility.copyRxExTaskDetVOToSolnExResType(objRxExecTaskDetailsServiceVO, objSolutionExecutionResponseType);
            List<RxDeliveryAttachmentResponse> rxDeliveryAttachmentLists = new ArrayList<RxDeliveryAttachmentResponse>();
            if(!objRxExecTaskDetailsServiceVO.getRxDeliveryAttachments().isEmpty()){
            	for(RxDeliveryAttachmentVO attachmentVO : objRxExecTaskDetailsServiceVO.getRxDeliveryAttachments()){
             		RxDeliveryAttachmentResponse rxObject = new RxDeliveryAttachmentResponse();
            		rxObject.setDocumentTitle(attachmentVO.getStrDocumentTitle());
            		rxObject.setDocumentPath(attachmentVO.getStrDocumentPath());
            		rxDeliveryAttachmentLists.add(rxObject);
            	}
            }
            objSolutionExecutionResponseType.setDeliveryAttachmentDetails(rxDeliveryAttachmentLists);

        } catch (OMDInValidInputException objOMDInValidInputException) {
        	LOG.error("getSolutionExecutionDetails", objOMDInValidInputException);
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
        	LOG.error("getSolutionExecutionDetails", objOMDNoResultFoundException);
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
        	LOG.error("getSolutionExecutionDetails", rmdServiceException);
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.error("getSolutionExecutionDetails", e);
        	RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return objSolutionExecutionResponseType;
    }

    /**
     * The method is used to get all the Recommendation details from DataBase
     * using the Input given Input parameters
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SOLUTIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionResponseType> getSolutions(
            @Context final UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<SolutionResponseType> arlResponse = null;
        RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO;
        List<RxSearchResultServiceVO> arlResult = null;
        SolutionResponseType objResponse;
        String soltuionStatus = RMDCommonConstants.EMPTY_STRING;
        String soltuionTitle = RMDCommonConstants.EMPTY_STRING;
        String urgency = RMDCommonConstants.EMPTY_STRING;
        String estRepair = RMDCommonConstants.EMPTY_STRING;
        String solutionType = RMDCommonConstants.EMPTY_STRING;
        String strModel = RMDCommonConstants.EMPTY_STRING;
        String strLocoImpact = RMDCommonConstants.EMPTY_STRING;
        String strSubSystem = RMDCommonConstants.EMPTY_STRING;
        String strlastUpdatedBy = RMDCommonConstants.EMPTY_STRING;
        String strCreatedBy = RMDCommonConstants.EMPTY_STRING;
        String strSolutionNotes = RMDCommonConstants.EMPTY_STRING;
        String strExportControl = RMDCommonConstants.EMPTY_STRING;
        String strDefaultLoad = RMDCommonConstants.EMPTY_STRING;
        String strKM = RMDCommonConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.EMPTY_STRING;
        String strUserLanguage = RMDCommonConstants.EMPTY_STRING;
        String strFamily = RMDCommonConstants.EMPTY_STRING;
        
        String customer = RMDCommonConstants.EMPTY_STRING;
        String fromRN = RMDCommonConstants.EMPTY_STRING;
        String toRN = RMDCommonConstants.EMPTY_STRING;
        String fleet = RMDCommonConstants.EMPTY_STRING;
        String rnh = RMDCommonConstants.EMPTY_STRING;
        String assetList = RMDCommonConstants.EMPTY_STRING;
        
        boolean blnDefaultLoad = false;
        boolean isKM = false;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar lastUpdatedDate;
        SolutionDetailType objSolutionDetail;
        String strLocoImpactArray[];
        String strUrgencyArray[];
        String strSubsystemArray[];
        String strModelArray[];
        try {
            LOG.debug("*****getSolution WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                            .format(new Date()));
            arlResponse = new ArrayList<SolutionResponseType>();
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.DOUBLE_HYPHEN,
                    OMDConstants.ALPHA_NUMERIC_HYPEN, OMDConstants.ALPHABETS,
                    OMDConstants.ALPHABETS, OMDConstants.ALPHABETS,
                    OMDConstants.NUMERIC, OMDConstants.ALPHA_BRACKET,
                    OMDConstants.ALPHA_AMPERSAND, OMDConstants.ALPHABETS,
                    OMDConstants.DOUBLE_HYPHEN, OMDConstants.AlPHA_NUMERIC,
					OMDConstants.ALPHABETS, OMDConstants.SPECIAL_CHARACTER,OMDConstants.NUMERIC};
            String[] userInput = { OMDConstants.SOLUTION_TITLE,
                    OMDConstants.MODEL, OMDConstants.SOLUTION_STATUS,
                    OMDConstants.SOLUTION_TYPE, OMDConstants.URGENCY,
                    OMDConstants.REPAIR, OMDConstants.LOCOMOTIVE,
                    OMDConstants.SUB_SYSTEM, OMDConstants.LAST_UPDATED,
                    OMDConstants.SOLUTION_NOTES, OMDConstants.EXPORT_CONTROL,
					OMDConstants.DEFAULT_LOAD ,OMDConstants.CREATCASE_VALUE,OMDConstants.SOLUTION_Id};
            if (AppSecUtil.validateWebServiceInput(queryParams, null,
                    paramFlag, userInput)){
                rxSearchCriteriaServiceVO = new RxSearchCriteriaEoaServiceVO();
				if (queryParams.containsKey(OMDConstants.SOLUTION_Id)) {
					String solutionId = queryParams.getFirst(OMDConstants.SOLUTION_Id);
					if (null != solutionId	&& !RMDCommonConstants.EMPTY_STRING.equals(solutionId)) {
						rxSearchCriteriaServiceVO.setStrRxObjid(solutionId);
					}
				}
                if (queryParams.containsKey(OMDConstants.SOLUTION_TITLE)) {
                    soltuionTitle = queryParams.getFirst(OMDConstants.SOLUTION_TITLE);
                    if (null != soltuionTitle  && !RMDCommonConstants.EMPTY_STRING.equals(soltuionTitle)) {
                        rxSearchCriteriaServiceVO.setStrCondValue(soltuionTitle);
                        rxSearchCriteriaServiceVO.setStrSelectBy(RMDCommonConstants.TITLE);
                        rxSearchCriteriaServiceVO.setStrCondition(RMDCommonConstants.CONTAINS);
                    }
                }

                if (queryParams.containsKey(OMDConstants.SOLUTION_STATUS)) {
                    soltuionStatus = queryParams.getFirst(OMDConstants.SOLUTION_STATUS);
                    if (null != soltuionStatus && !RMDCommonConstants.EMPTY_STRING.equals(soltuionStatus)) {
                        rxSearchCriteriaServiceVO.setStrRxStatus(soltuionStatus);
                    }
                } else {
                    rxSearchCriteriaServiceVO.setStrRxStatus(OMDConstants.ALL);
                }
                if (queryParams.containsKey(OMDConstants.URGENCY)) {
                    urgency = queryParams.getFirst(OMDConstants.URGENCY);
                    if (!RMDCommonUtility.isNullOrEmpty(urgency)) {
                        // Converting the comma String to String Array
                        strUrgencyArray = RMDCommonUtility.splitString(urgency, RMDCommonConstants.COMMMA_SEPARATOR);
                        rxSearchCriteriaServiceVO.setStrRxUrgRepairArray(strUrgencyArray);
                    }
                }

                if (queryParams.containsKey(OMDConstants.EST_REPAIR)) {
                    estRepair = queryParams.getFirst(OMDConstants.EST_REPAIR);
                    if (null != estRepair  && !RMDCommonConstants.EMPTY_STRING.equals(estRepair)) {
                        rxSearchCriteriaServiceVO.setStrSelEstmTimeRepair(estRepair);
                    }
                } else {
                    rxSearchCriteriaServiceVO.setStrSelEstmTimeRepair(OMDConstants.ALL);
                }
                if (queryParams.containsKey(OMDConstants.SOLUTION_TYPE)) {
                    solutionType = queryParams.getFirst(OMDConstants.SOLUTION_TYPE);
                    if (null != solutionType
                            && !RMDCommonConstants.EMPTY_STRING.equals(solutionType)) {
                        rxSearchCriteriaServiceVO.setStrSelRxType(solutionType);
                    }
                }
                if (queryParams.containsKey(OMDConstants.MODEL)) {
                    strModel = queryParams.getFirst(OMDConstants.MODEL);
                    if (null != strModel  && !RMDCommonConstants.EMPTY_STRING.equals(strModel)) {
                        strModelArray = RMDCommonUtility.splitString(strModel,
                                RMDCommonConstants.COMMMA_SEPARATOR);
                        rxSearchCriteriaServiceVO.setStrRxModel(strModelArray);
                    }
                }
                if (queryParams.containsKey(OMDConstants.SOLUTION_NOTES)) {
                    strSolutionNotes = queryParams.getFirst(OMDConstants.SOLUTION_NOTES);
                    if (null != strSolutionNotes
                            && !RMDCommonConstants.EMPTY_STRING.equals(strSolutionNotes)) {
                        rxSearchCriteriaServiceVO.setStrRxNotes(strSolutionNotes);
                    }
                }
                if (queryParams.containsKey(OMDConstants.LOCO_IMPACT)) {
                    strLocoImpact = queryParams.getFirst(OMDConstants.LOCO_IMPACT);
                    if (!RMDCommonUtility.isNullOrEmpty(strLocoImpact)) {

                        // Converting the comma String to String Array
                        strLocoImpactArray = RMDCommonUtility.splitString(
                                strLocoImpact, RMDCommonConstants.COMMMA_SEPARATOR);

                        rxSearchCriteriaServiceVO.setStrRxLocoImpact(strLocoImpactArray);
                    }
                }
                if (queryParams.containsKey(OMDConstants.EXPORT_CONTROL)) {
                    strExportControl = queryParams.getFirst(OMDConstants.EXPORT_CONTROL);
                    if (null != strExportControl
                            && !RMDCommonConstants.EMPTY_STRING.equals(strExportControl)) {
                        rxSearchCriteriaServiceVO.setStrRxExportControl(strExportControl);
                    }
                }
                if (queryParams.containsKey(OMDConstants.LAST_UPDATED_BY)) {
                    strlastUpdatedBy = queryParams.getFirst(OMDConstants.LAST_UPDATED_BY);
                    if (null != strlastUpdatedBy
                            && !RMDCommonConstants.EMPTY_STRING.equals(strlastUpdatedBy)) {
                        rxSearchCriteriaServiceVO.setStrLastUpdatedBy(strlastUpdatedBy);
                    }
                }
                if (queryParams.containsKey(OMDConstants.RX_SUB_SYSTEM)) {
                    strSubSystem = queryParams.getFirst(OMDConstants.RX_SUB_SYSTEM);
                    if (!RMDCommonUtility.isNullOrEmpty(strSubSystem)) {
                        strSubsystemArray = RMDCommonUtility.splitString(
                                strSubSystem, RMDCommonConstants.COMMMA_SEPARATOR);
                        rxSearchCriteriaServiceVO.setStrRxSubSystemArray(strSubsystemArray);
                    }
                }
                if (queryParams.containsKey(OMDConstants.IS_DEFAULT_LOAD)) {
                    strDefaultLoad = queryParams.getFirst(OMDConstants.IS_DEFAULT_LOAD);
                    if (strDefaultLoad.equalsIgnoreCase(RMDCommonConstants.LETTER_Y)) {
                        blnDefaultLoad = RMDCommonConstants.TRUE;
                        rxSearchCriteriaServiceVO.setStrRxStatus(RMDCommonConstants.APPROVED);
                    } else {
                        blnDefaultLoad = RMDCommonConstants.FALSE;
                    }
                    rxSearchCriteriaServiceVO.setBlnDefaultLoad(blnDefaultLoad);
                }
                
                if (queryParams.containsKey(OMDConstants.IS_KM)) {
                    strKM = queryParams.getFirst(OMDConstants.IS_KM);
                    if (strKM.equalsIgnoreCase(RMDCommonConstants.LETTER_Y)) {
                        isKM = RMDCommonConstants.TRUE;
                    } else {
                        isKM = RMDCommonConstants.FALSE;
                    }
                    rxSearchCriteriaServiceVO.setBlnKM(isKM);
                }

                if (queryParams.containsKey(OMDConstants.CREATCASE_CONDITION)) {
                    String strcondition = queryParams.getFirst(OMDConstants.CREATCASE_CONDITION);
                    if (!RMDCommonUtility.isNullOrEmpty(strcondition)) {
                        rxSearchCriteriaServiceVO.setStrCondition(strcondition);
                    }
                }
                if (queryParams.containsKey(OMDConstants.CREATCASE_SELECTBY)) {
                    String strSelectBy = queryParams.getFirst(OMDConstants.CREATCASE_SELECTBY);
                    if (!RMDCommonUtility.isNullOrEmpty(strSelectBy)) {
                        rxSearchCriteriaServiceVO.setStrSelectBy(strSelectBy);
                    }
                }
                if (queryParams.containsKey(OMDConstants.CREATCASE_VALUE)) {
                    String strSolValue = queryParams.getFirst(OMDConstants.CREATCASE_VALUE);
                    if (!RMDCommonUtility.isNullOrEmpty(strSolValue)) {
                        rxSearchCriteriaServiceVO.setStrCondValue(strSolValue);
                    }
                }
                if (queryParams.containsKey(OMDConstants.IS_MASS_APPLY_RX)) {
                    String isMassApplyRx = queryParams.getFirst(OMDConstants.IS_MASS_APPLY_RX);
                    if (!RMDCommonUtility.isNullOrEmpty(isMassApplyRx)) {
                        rxSearchCriteriaServiceVO.setIsMassApplyRx(isMassApplyRx);
                    }
                }
                if (queryParams.containsKey(OMDConstants.ADD_RX_APPLY)) {
                    String addRxApply = queryParams.getFirst(OMDConstants.ADD_RX_APPLY);
                    if (!RMDCommonUtility.isNullOrEmpty(addRxApply)) {
                        rxSearchCriteriaServiceVO.setAddRxApply(addRxApply);
                    }
                }
                
                if (queryParams.containsKey(OMDConstants.FAMILY)) {
                    strFamily = queryParams
                    .getFirst(OMDConstants.FAMILY);
                    if (null != strFamily && !RMDCommonConstants.EMPTY_STRING.equals(strFamily)) {
                        rxSearchCriteriaServiceVO.setStrFamily(strFamily);
                    }
                }  
                
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                    if (null != customer && !RMDCommonConstants.EMPTY_STRING.equals(customer)) {
                        rxSearchCriteriaServiceVO.setCustomer(customer);
                    }
                } 
                if (queryParams.containsKey(OMDConstants.FROM_RN)) {
                    fromRN = queryParams.getFirst(OMDConstants.FROM_RN);
                    if (null != fromRN && !RMDCommonConstants.EMPTY_STRING.equals(fromRN)) {
                        rxSearchCriteriaServiceVO.setFromRN(fromRN);
                    }
                } 
                if (queryParams.containsKey(OMDConstants.TO_RN)) {
                    toRN = queryParams.getFirst(OMDConstants.TO_RN);
                    if (null != toRN && !RMDCommonConstants.EMPTY_STRING.equals(toRN)) {
                        rxSearchCriteriaServiceVO.setToRN(toRN);
                    }
                } 
                
                if (queryParams.containsKey(OMDConstants.FLEET)) {
                    fleet = queryParams.getFirst(OMDConstants.FLEET);
                    if (null != fleet && !RMDCommonConstants.EMPTY_STRING.equals(fleet)) {
                        rxSearchCriteriaServiceVO.setFleet(fleet);
                    }
                } 
                if (queryParams.containsKey(OMDConstants.RNH)) {
                    rnh = queryParams.getFirst(OMDConstants.RNH);
                    if (null != rnh && !RMDCommonConstants.EMPTY_STRING.equals(rnh)) {
                        rxSearchCriteriaServiceVO.setRnh(rnh);
                    }
                } 
                if (queryParams.containsKey(OMDConstants.ASSET_LIST)) {
                    assetList = queryParams.getFirst(OMDConstants.ASSET_LIST);
                    if (null != assetList && !RMDCommonConstants.EMPTY_STRING.equals(assetList)) {
                        rxSearchCriteriaServiceVO.setAssetList(assetList);
                    }
                }
                
                if (queryParams.containsKey(OMDConstants.RX_CREATED)) {
                	strCreatedBy = queryParams.getFirst(OMDConstants.RX_CREATED);
                    if (null != strCreatedBy && !RMDCommonConstants.EMPTY_STRING.equals(strCreatedBy)) {
                        rxSearchCriteriaServiceVO.setStrCreatedBy(strCreatedBy);
                    }
                }
                
                if (queryParams.containsKey(OMDConstants.RX_LST_FROM_DATE)) {
                	rxSearchCriteriaServiceVO.setStrLastUpdatedFromDate(queryParams.getFirst(OMDConstants.RX_LST_FROM_DATE));
                }
                if (queryParams.containsKey(OMDConstants.RX_LST_TO_DATE)) {
                	rxSearchCriteriaServiceVO.setStrLastUpdatedToDate(queryParams.getFirst(OMDConstants.RX_LST_TO_DATE));
                }
                if (queryParams.containsKey(OMDConstants.RX_FRM_DATE)) {
                	rxSearchCriteriaServiceVO.setStrCreatedByFromDate(queryParams.getFirst(OMDConstants.RX_FRM_DATE));
                }
                if (queryParams.containsKey(OMDConstants.RX_TO_DATE)) {
                	rxSearchCriteriaServiceVO.setStrCreatedByToDate(queryParams.getFirst(OMDConstants.RX_TO_DATE));
                }

                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                rxSearchCriteriaServiceVO.setStrLanguage(strLanguage);
                strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                rxSearchCriteriaServiceVO.setStrUserLanguage(strUserLanguage);

                // Accessing the Service Method by passing the service VO
                LOG.debug("getSolution WEBSERVICE calling Service Methods "
                        + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                                .format(new Date()));
                arlResult = objRecommEoaServiceIntf.searchRecomm(rxSearchCriteriaServiceVO);
                if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                    for (RxSearchResultServiceVO rxSearchResult : arlResult) {
                        objResponse = new SolutionResponseType();
                        objSolutionDetail = new SolutionDetailType();
                        if (null != rxSearchResult.getLastUpdateDate()) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(rxSearchResult.getLastUpdateDate());
                            lastUpdatedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objSolutionDetail.setLastEditedDate(lastUpdatedDate);
                        }
                        // copying RxSearchResultServiceVO to
                        // SolutionResponseType
                        CMBeanUtility.copyRxSearchResltSerVOToSolnResType(rxSearchResult, objResponse);
                        // copying RxSearchResultServiceVO to SolutionDetailType
                        CMBeanUtility.copyRxSearchResltSerVOToSolnResType(rxSearchResult, objSolutionDetail);
                        objSolutionDetail.setVersion(rxSearchResult.getStrVersion());
                        objResponse.setSolutionDetail(objSolutionDetail);
                        arlResponse.add(objResponse);
                    }
                    LOG.debug("getSolution WEBSERVICEs after copied service VO's"
                            + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                                    .format(new Date()));
                } else {
                	LOG.error("getSolution");
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
            	LOG.error("getSolution");
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }

        } catch (Exception ex) {
        	LOG.error("getSolution", ex);
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("****getSolution WEBSERVICES END ****"
                + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                        .format(new Date()));
        return arlResponse;
    }
    
    /**
     * This method is used for fetching RxTitles of Fleet screen as part of OMD Performance
     * 
     * @param uriParam
     * @return List<SolutionLiteResponseType> 
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_RX_TITLES_LITE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionLiteResponseType> getRxTitlesLite(
            @Context final UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<SolutionLiteResponseType> arlResponse = null;
        List<RxSearchResultEoaLiteServiceVO> arlResult = null;
        SolutionLiteResponseType objResponse;
        String solutionStatus = RMDCommonConstants.EMPTY_STRING;
        String strLanguage = RMDCommonConstants.EMPTY_STRING;

        try {
            LOG.debug("*****getRxTitlesLite WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                            .format(new Date()));
            arlResponse = new ArrayList<SolutionLiteResponseType>();
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHABETS };
            String[] userInput = { OMDConstants.SOLUTION_STATUS };
            
            if (AppSecUtil.validateWebServiceInput(queryParams, null,
                    paramFlag, userInput)) {
                if (queryParams.containsKey(OMDConstants.SOLUTION_STATUS)) {
                    solutionStatus = queryParams.getFirst(OMDConstants.SOLUTION_STATUS);
                } else {
                    solutionStatus = OMDConstants.ALL;
                }
                strLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
                LOG.debug("getRxTitlesLite WEBSERVICE calling Service Methods "
                        + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                                .format(new Date()));

                arlResult = objRecommEoaServiceIntf.getRxTitlesLite(
                        strLanguage, solutionStatus);
                if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                    for (RxSearchResultEoaLiteServiceVO rxSearchResult : arlResult) {
                        objResponse = new SolutionLiteResponseType();
                        objResponse.setSolutionId((rxSearchResult.getSolutionId()));
                        objResponse.setSolutionTitle((rxSearchResult.getSolutionTitle()));
                        arlResponse.add(objResponse);
                    }
                    LOG.debug("getRxTitlesLite WEBSERVICEs after copied service VO's"
                            + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                                    .format(new Date()));
                } 
            } else {
            	LOG.error("getRxTitlesLite");
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
        	LOG.error("getRxTitlesLite", ex);
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("****getRxTitlesLite WEBSERVICES END ****"
                + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                        .format(new Date()));
        return arlResponse;
    }

    /**
     * This method is used to save the manual feedback entered.
     * 
     * @param
     * 
     * 
     */
    @PUT
    @Path(OMDConstants.SAVE_MANUAL_FEEDBACK)
    @Consumes(MediaType.APPLICATION_XML)
    public void saveSolutionFeedback(RxDetailsType rxDetailsType) throws RMDServiceException {
        
        ScoreRxEoaVO scoreRxEoaVO = null;
        try {
            final String rxCaseId = rxDetailsType.getRxCaseID();
            final String rxScoreCode = rxDetailsType.getRxScoreCode();
            final String rxFeedback = rxDetailsType.getRxFeedBack();
            final String rxnote=rxDetailsType.getRxnotes();
            final String isReissue=rxDetailsType.getIsReissue();
            
            if ((!RMDCommonUtility.isAlphaNumericWithHyphen(rxCaseId))|| (!RMDCommonUtility.isAlphaNumericWithHyphen(rxScoreCode))) {
                throw new OMDInValidInputException(OMDConstants.INVALID_INPUTS);
            } else if (null == rxCaseId || rxCaseId.equals(OMDConstants.EMPTY_STRING)) {
                throw new OMDInValidInputException(OMDConstants.RX_CASEID_NOT_PROVIDED);
            } else if (null == rxScoreCode || rxScoreCode.equals(OMDConstants.EMPTY_STRING)) {
                throw new OMDInValidInputException(OMDConstants.SCORE_CODE_NOT_PROVIDED);
            } 
            else if (null == isReissue || isReissue.equals(OMDConstants.EMPTY_STRING)) {
                throw new OMDInValidInputException(OMDConstants.REISSUE_NOT_PROVIDED);
            }
            else {
                
                if (null != rxDetailsType.getCaseID() && !rxDetailsType.getCaseID().isEmpty()) {
                    scoreRxEoaVO = new ScoreRxEoaVO();
                    scoreRxEoaVO.setRxCaseId(rxCaseId);
                    scoreRxEoaVO.setCaseId(rxDetailsType.getCaseID());
                    scoreRxEoaVO.setRxScoreCode(rxScoreCode);
                    scoreRxEoaVO.setRxFeedback(rxFeedback);
                    scoreRxEoaVO.setEoaUserName(rxDetailsType.getEoaUserName());
                    scoreRxEoaVO.setStrUserName(rxDetailsType.getUserName());
                    scoreRxEoaVO.setRxCloseDate(RMDCommonUtility.getGMTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
                    if (null != rxDetailsType.getRxScoreCode()) {
                        String[] successAndCodeArray  = rxDetailsType.getRxScoreCode().split(OMDConstants.HYPHEN);
                        scoreRxEoaVO.setCaseSuccess(successAndCodeArray[0]);
                        if (successAndCodeArray.length > 1) {
                            scoreRxEoaVO.setMissCode(successAndCodeArray[1]);
                        }
                    }
                    scoreRxEoaVO.setRxnotes(rxnote);
                    scoreRxEoaVO.setIsReissue(isReissue);
                    scoreRxEoaVO.setRepairCodes(rxDetailsType.getRepaircodeList());
                    objCaseEoaServiceIntf.saveSolutionFeedback(scoreRxEoaVO);
                }
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
        	LOG.error("saveSolutionFeedback", objOMDInValidInputException);
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
        	LOG.error("saveSolutionFeedback", objOMDNoResultFoundException);
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
        	LOG.error("saveSolutionFeedback", rmdServiceException);
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.error("saveSolutionFeedback", e);
        	 RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        
    }
    
    /* Added for Sprint 14 Fleet View basic story */
    /**
     * The method is used to get worst urgency for assets from DataBase using
     * the Input given Input parameters
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_WORST_URGENCY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UrgencyResponseType> getWorstUrgency(@Context UriInfo uriParam)
    throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<UrgencyResponseType> objUrgencyResponseLst = new ArrayList<UrgencyResponseType>();
        RxUrgencyParamVO rxUrgencyParamVO;
        List<RxWorstUrgencyVO> rxUrgencyVOLst = null;
        UrgencyResponseType objUrgencyResponse;

        String customerId;
        String assetNumber;
        String assetGrpname;
        try {
            queryParams = uriParam.getQueryParameters();
            rxUrgencyParamVO = new RxUrgencyParamVO();
            int[] ch = { RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE, RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, ch,
                    OMDConstants.CUSTOMER_ID, OMDConstants.ASSET_NUMBER, OMDConstants.ASSET_GROUP_NAME)) {
                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                    if (null != customerId
                            && !RMDCommonConstants.EMPTY_STRING
                            .equals(customerId)) {
                        rxUrgencyParamVO.setCustomerId(customerId);
                    }
                }

                if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
                    assetNumber = queryParams
                    .getFirst(OMDConstants.ASSET_NUMBER);
                    if (null != assetNumber
                            && !RMDCommonConstants.EMPTY_STRING
                            .equals(assetNumber)) {
                        rxUrgencyParamVO.setAssetNumber(assetNumber);
                    }
                }

                if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
                    assetGrpname = queryParams
                    .getFirst(OMDConstants.ASSET_GROUP_NAME);
                    if (null != assetGrpname
                            && !RMDCommonConstants.EMPTY_STRING
                            .equals(assetGrpname)) {
                        rxUrgencyParamVO.setAssetGrpName(assetGrpname);
                    }
                }
                if (null != getRequestHeader(OMDConstants.LANGUAGE)
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(getRequestHeader(OMDConstants.LANGUAGE))) {
                    rxUrgencyParamVO
                    .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

                } else if (null != getRequestHeader(OMDConstants.USERLANGUAGE)
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(getRequestHeader(OMDConstants.USERLANGUAGE))) {
                    rxUrgencyParamVO
                    .setStrLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
                } else {
                    rxUrgencyParamVO
                    .setStrLanguage(RMDCommonConstants.ENGLISH_LANGUAGE);
                }
                rxUrgencyVOLst = objRecommEoaServiceIntf.getWorstUrgency(rxUrgencyParamVO);
                if (RMDCommonUtility.isCollectionNotEmpty(rxUrgencyVOLst)) {
                    for (RxWorstUrgencyVO rxUrgencyVO : rxUrgencyVOLst) {
                        objUrgencyResponse = new UrgencyResponseType();

                        objUrgencyResponse.setAssetNumber(rxUrgencyVO
                                .getAssetNumber());
                        objUrgencyResponse.setCustomerId(rxUrgencyVO
                                .getCustomerId());
                        objUrgencyResponse.setAssetGroupName(rxUrgencyVO
                                .getAssetGrpName());
                        objUrgencyResponse.setWorstUrgency(rxUrgencyVO
                                .getWorstUrgency());
                        objUrgencyResponseLst.add(objUrgencyResponse);

                    }
                    return objUrgencyResponseLst;
                } else {
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
            } else {
                throw new OMDInValidInputException(
                        BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.INVALID_VALUE),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.info("Exception occured in getWorstUrgency method"+e);
        	 throw new OMDApplicationException(
                     BeanUtility
                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                     omdResourceMessagesIntf
                             .getMessage(
                                     BeanUtility
                                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                     new String[] {},
                                     BeanUtility
                                             .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

    }

    /* Added for Sprint 14 Fleet View basic story */

    /**
     * The method is used to save Recommendation details
     * 
     * @param objSolutionRequestType
     * @return status
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.ADD_SOLUTION)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SolutionResponseType addSolution(
            final SolutionRequestType objSolutionRequestType)
    throws RMDServiceException {

        AddEditRxServiceVO objAddEditRxServiceVO;
        AddEditRxTaskResultVO objTaskResultVO;
        AddEditRxTaskDocVO objTaskDocVO;
        RxTaskRepairCodeVO objRepCodeVO;
        RxConfigVO objConfigVO;
        ArrayList<AddEditRxTaskResultVO> arlTasks;
        ArrayList<AddEditRxTaskDocVO> arlDoc;
        List<RxTaskRepairCodeVO> repairCodes;
        ArrayList<RxConfigVO> arlConfigVO;
        SolutionResponseType objSolutionResponseType = null;
        SolutionDetailType solutionDetailType = null;
       // String strStatus;
        String strRecommId;
        String strSolutionOrigId=RMDCommonConstants.EMPTY_STRING;
        try {
            objSolutionResponseType = new SolutionResponseType(); 
            solutionDetailType = new SolutionDetailType();
            if (null != objSolutionRequestType) { 
            	
                if (validateAddSolution(objSolutionRequestType,
                        objSolutionRequestType.getSolutionDetail(),
                        objSolutionRequestType.getTaskDetail())) {
                    objAddEditRxServiceVO = new AddEditRxServiceVO();
                    objAddEditRxServiceVO
                    .setStrUserName(getRequestHeader(OMDConstants.STR_USERNAME));
                    objAddEditRxServiceVO
                    .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                    objAddEditRxServiceVO
                    .setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
    
                    arlTasks = new ArrayList<AddEditRxTaskResultVO>();
    
                    BeanUtility.copyBeanProperty(objSolutionRequestType,
                            objAddEditRxServiceVO);
                if (objSolutionRequestType.getSolutionHistory() != null) {
                    strSolutionOrigId = objSolutionRequestType
                    .getSolutionHistory().getOrginalId();
                    objAddEditRxServiceVO.setStrOriginalId(strSolutionOrigId);
                }
                    BeanUtility.copyBeanProperty(
                            objSolutionRequestType.getSolutionDetail(),
                            objAddEditRxServiceVO);
                    if(objSolutionRequestType.getSolutionDetail() != null){
                    	objAddEditRxServiceVO.setParentSolutionId(objSolutionRequestType.getSolutionDetail().getParentSolutionId());
                    	objAddEditRxServiceVO.setClone(objSolutionRequestType.getSolutionDetail().isClone());
                    }
                    arlConfigVO = new ArrayList<RxConfigVO>();
                    for (SolutionConfigType objConfigType : objSolutionRequestType
                            .getSolutionDetail().getSolutionConfig()) {
                        objConfigVO = new RxConfigVO();
                        BeanUtils.copyProperties(objConfigType, objConfigVO);
                        arlConfigVO.add(objConfigVO);
                    }
                    objAddEditRxServiceVO.setArlRxConfig(arlConfigVO);
    
                    for (TaskDetailType objTaskDetail : objSolutionRequestType
                            .getTaskDetail()) {
                        objTaskResultVO = new AddEditRxTaskResultVO();
                        BeanUtility.copyBeanProperty(objTaskDetail, objTaskResultVO);
                        arlDoc = new ArrayList<AddEditRxTaskDocVO>();
                        for (TaskDocType objTaskDDoc : objTaskDetail.getTaskDoc()) {
                            objTaskDocVO = new AddEditRxTaskDocVO();
                            BeanUtility.copyBeanProperty(objTaskDDoc, objTaskDocVO);
                            arlDoc.add(objTaskDocVO);
                        }
                        objTaskResultVO.setAddEditRxTaskDocVO(arlDoc);
                        
                        repairCodes = new ArrayList<RxTaskRepairCodeVO>();
                        for (RepairCodeType objRepCode : objTaskDetail.getRepairCodes()) {
                            objRepCodeVO = new RxTaskRepairCodeVO();
                            BeanUtility.copyBeanProperty(objRepCode, objRepCodeVO);
                            repairCodes.add(objRepCodeVO);
                        }
						objTaskResultVO.setRepairCodes(repairCodes);
                        arlTasks.add(objTaskResultVO);
                    }
                    objAddEditRxServiceVO.setLstEditTaskRxResultVO(arlTasks);
                    
                  //Rx plot
                    if(null != objSolutionRequestType.getSolutionPlot()){ 
                    	AddEditRxPlotVO addEditRxPlotVO = new AddEditRxPlotVO();         
                    	SolutionPlotType solutionPlotType = objSolutionRequestType.getSolutionPlot();                   	
                    	addEditRxPlotVO.setStrPlotObjid(objSolutionRequestType.getSolutionPlot().getPlotObjid());
                    	addEditRxPlotVO.setStrPlotTitle(solutionPlotType.getPlotTitle());
                    	addEditRxPlotVO.setStrIndependentAxislabel(solutionPlotType.getIndependentAxislabel());
                    	addEditRxPlotVO.setStrLeftDependentAxislabel(solutionPlotType.getLeftDependentAxislabel());
                    	addEditRxPlotVO.setStrRightDependentAxislabel(solutionPlotType.getRightDependentAxislabel());      
                    	addEditRxPlotVO.setStrDataSetOneName(solutionPlotType.getDataSetOneName());
                    	addEditRxPlotVO.setStrDataSetOneLabel(solutionPlotType.getDataSetOneLabel());
                    	addEditRxPlotVO.setStrDataSetOneAxis(solutionPlotType.getDataSetOneAxis());                  	
                     	addEditRxPlotVO.setStrDataSetTwoName(solutionPlotType.getDataSetTwoName());
                    	addEditRxPlotVO.setStrDataSetTwoLabel(solutionPlotType.getDataSetTwoLabel());
                    	addEditRxPlotVO.setStrDataSetTwoAxis(solutionPlotType.getDataSetTwoAxis());                   	
                     	addEditRxPlotVO.setStrDataSetThreeName(solutionPlotType.getDataSetThreeName());
                    	addEditRxPlotVO.setStrDataSetThreeLabel(solutionPlotType.getDataSetThreeLabel());
                    	addEditRxPlotVO.setStrDataSetThreeAxis(solutionPlotType.getDataSetThreeAxis());   
                    	addEditRxPlotVO.setStrDataSetFourName(solutionPlotType.getDataSetFourName());
                    	addEditRxPlotVO.setStrDataSetFourLabel(solutionPlotType.getDataSetFourLabel());
                    	addEditRxPlotVO.setStrDataSetFourAxis(solutionPlotType.getDataSetFourAxis());   
                    	objAddEditRxServiceVO.setAddEditRxPlotVO(addEditRxPlotVO);
                    }  

                    strRecommId = objRecommEoaServiceIntf.saveRecomm(objAddEditRxServiceVO);
                    LOG.debug("Recom saved. Solution Id is:: " + strRecommId);
                    solutionDetailType.setSolutionID(EsapiUtil.stripXSSCharacters((String)(strRecommId)));
                    objSolutionResponseType.setSolutionDetail(solutionDetailType);

                }else{
                    throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
                }
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return objSolutionResponseType;
    }
    /**
     * The method is used to update the Rx details
     * 
     * @param objSolutionExecutionRequestType
     * @return
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVE_SOLUTION_EXE_DETAILS)
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void saveSolutionExecutionDetails(final SolutionExecutionRequestType objSolutionExecutionRequestType)
    throws RMDServiceException {
        RxExecTaskDetailsServiceVO objRxExecTaskDetailsServiceVO;
        RxExecTaskServiceVO objRxExecTaskServiceVO;
        List<RxExecTaskServiceVO> arlRxExecTask;
        try {
        
            if (null != objSolutionExecutionRequestType) {
                if (validateSolutionExecutionDetails(objSolutionExecutionRequestType)) {
                    objRxExecTaskDetailsServiceVO = new RxExecTaskDetailsServiceVO();
                    arlRxExecTask = new ArrayList<RxExecTaskServiceVO>();

                    objRxExecTaskDetailsServiceVO.setStrCaseId(objSolutionExecutionRequestType.getCaseID());
                    objRxExecTaskDetailsServiceVO.setStrRxCaseId(objSolutionExecutionRequestType.getSolutionCaseID());
                    objRxExecTaskDetailsServiceVO.setStrRxExecutionId(objSolutionExecutionRequestType.getSolutionExecutionId());
                    objRxExecTaskDetailsServiceVO.setArlRecomTaskList(objSolutionExecutionRequestType.getTaskListID());
                    objRxExecTaskDetailsServiceVO.setStrRxCloseFlag(objSolutionExecutionRequestType.getSolutionCloseFlag());
                    //Added for Add location for RX_EX Screen implementation:start
                    objRxExecTaskDetailsServiceVO.setLocationId(objSolutionExecutionRequestType.getRxLocationId());
                    
                    objRxExecTaskDetailsServiceVO.setStrRecomId(objSolutionExecutionRequestType.getSolutionId());
                    objRxExecTaskDetailsServiceVO.setIsMobile(objSolutionExecutionRequestType.getIsMobile());
                    //Added for Add location for RX_EX Screen implementation:end
                    for (TaskDetailType objTaskDetailType : objSolutionExecutionRequestType.getTaskDetail()) {
                        objRxExecTaskServiceVO = new RxExecTaskServiceVO();
                        BeanUtility.copyBeanProperty(objTaskDetailType, objRxExecTaskServiceVO);
                        objRxExecTaskServiceVO.setStrTaskId(objTaskDetailType.getTaskID());
                        objRxExecTaskServiceVO.setStrRecomTaskId(objTaskDetailType.getRecomTaskId());
                        objRxExecTaskServiceVO.setStrSetFlag(objTaskDetailType.getCheckedFlag());
                        objRxExecTaskServiceVO.setStrLastUpdatedBy(objTaskDetailType.getLastUpdatedBy());
                        objRxExecTaskServiceVO.setStrTaskNotes(objTaskDetailType.getTaskComments());
                        objRxExecTaskServiceVO.setIsRxModified(objTaskDetailType.getIsTaskModified());
                        arlRxExecTask.add(objRxExecTaskServiceVO);
                    }
                    objRxExecTaskDetailsServiceVO.setArlExecTasks(arlRxExecTask);
                    objRxExecTaskDetailsServiceVO.setStrRepairAction(objSolutionExecutionRequestType.getRepairAction());
                    String userId = getRequestHeader(OMDConstants.USERID);
                    objRxExecTaskDetailsServiceVO.setStrUserName(userId);
                    objRxExecTaskDetailsServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                    objRxExecTaskDetailsServiceVO.setStrFirstName(getRequestHeader(OMDConstants.USER_FNAME));
                    objRxExecTaskDetailsServiceVO.setStrLastName(getRequestHeader(OMDConstants.USER_LNAME));
                    objRxExecTaskDetailsServiceVO.setEndUserScoring(objSolutionExecutionRequestType.getEndUserScoring());
                    objRxExecTaskDetailsServiceVO.setUserSeqId(objSolutionExecutionRequestType.getUserSeqId());
                    LOG.info("User Details : UserId : " + userId + " User Sequence : "+objSolutionExecutionRequestType.getUserSeqId());
                    objRxExecutionEoaServiceIntf.saveRxExecutionDetails(objRxExecTaskDetailsServiceVO);
                } else {
                	LOG.error("saveSolutionExecutionDetails : Validation Failed");
                    throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                }
            } else {
            	LOG.error("saveSolutionExecutionDetails : SolutionExecutionRequestType Object is null");
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            }
        } catch (Exception ex) {
        	LOG.error("saveSolutionExecutionDetails", ex);
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
    }

    

    /* Added for sprint 9 Rx Search */
    /**
     * @Description:This method is used for fetching Solution related lookup
     *                   values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SOLUTION_INFORM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionResponseType> getSolutionsInformation(
            @Context final UriInfo uriParam) throws RMDServiceException {
        final List<SolutionResponseType> arlResponse = new ArrayList<SolutionResponseType>();
        List<RxSearchResultServiceVO> arlResult = null;
        SolutionResponseType objResponse;
        String strLanguage = RMDCommonConstants.EMPTY_STRING;
        SolutionDetailType objSolutionDetailType = null;
        MultivaluedMap<String, String> queryParams = null;
        String strSolutionNotes = RMDCommonConstants.EMPTY_STRING;
        String strSolutionTitle = RMDCommonConstants.EMPTY_STRING;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = {OMDConstants.DOUBLE_HYPHEN, OMDConstants.DOUBLE_HYPHEN};
            String[] userInput={OMDConstants.SOLUTION_NOTES,OMDConstants.SOLUTION_TITLES};
            if(AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag,userInput)){
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                if (queryParams.containsKey(OMDConstants.SOLUTION_NOTES)) {
                    strSolutionNotes = queryParams
                    .getFirst(OMDConstants.SOLUTION_NOTES);
                    if (!RMDCommonUtility.isNullOrEmpty(strSolutionNotes)) {
                        arlResult = objRecommEoaServiceIntf.getSolutionNotes(
                                strSolutionNotes, strLanguage);
                    }
                }
                if (queryParams.containsKey(OMDConstants.SOLUTION_TITLES)) {
                    strSolutionTitle = queryParams
                    .getFirst(OMDConstants.SOLUTION_TITLES);
                    if (!RMDCommonUtility.isNullOrEmpty(strSolutionTitle)) {
                        arlResult = objRecommEoaServiceIntf.getSolutionTitles(
                                strSolutionTitle, strLanguage);
                    }
                }

                if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                    for (RxSearchResultServiceVO rxSearchResultServiceVO : arlResult) {
                        objResponse = new SolutionResponseType();
                        objSolutionDetailType = new SolutionDetailType();
                        BeanUtils.copyProperties(rxSearchResultServiceVO,
                                objResponse);
                        CMBeanUtility.copyRxSearchResltSerVOToSolnResType(rxSearchResultServiceVO,
                                objSolutionDetailType);
                        objResponse.setSolutionDetail(objSolutionDetailType);
                        arlResponse.add(objResponse);
                    }

                } /*else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
            }else{
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlResponse;
    }

    /**
     * @Description:This method is used for fetching Solution related lookup
     *                   values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SOLUTION_LASTUPDATE_BY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionResponseType> getSolutionsLastUpdatedBy()
    throws RMDServiceException {
        List<SolutionResponseType> arlResponse = new ArrayList<SolutionResponseType>();
        List<RxSearchResultServiceVO> arlResult = null;
        SolutionResponseType objResponse;
        String strLanguage = RMDCommonConstants.EMPTY_STRING;
        SolutionDetailType objSolutionDetailType = null;
        try {

            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlResult = objRecommEoaServiceIntf
            .getSolutionLastUpdatedBy(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                for (RxSearchResultServiceVO rxSearchResultServiceVO : arlResult) {
                    objResponse = new SolutionResponseType();
                    objSolutionDetailType = new SolutionDetailType();
                    BeanUtils.copyProperties(rxSearchResultServiceVO,
                            objResponse);
                    BeanUtility.copyBeanProperty(rxSearchResultServiceVO,
                            objSolutionDetailType);
                    objResponse.setSolutionDetail(objSolutionDetailType);
                    arlResponse.add(objResponse);
                }

            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlResponse;
    }

    /**
     * @Description:This method is used for fetching Solution details
     * @param path
     *            Param solutionId, Query param
     * @return
     * @throws RMDServiceException
     */
    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_SOLUTION_DTLS_SOLUTION_ID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SolutionResponseType getSolutionDetails(
            @PathParam(OMDConstants.SOLUTION_Id) final String solutionId,
            @Context UriInfo uriParam) throws RMDServiceException {
        AddEditRxServiceVO objAddEditRxServiceVO = null;
        SolutionDetailType objSolutionDetailType = null;
        List<RxHistServiceVO> arlrxhistory = null;
        List<RxConfigVO> arlRxConfigVO = null;
        SolutionHistoryType objSolutionHistoryType = null;
        MultivaluedMap<String, String> queryParams = null;
        SolutionResponseType objSolutionResponseType = null;
        String strSolutionId = null;
        String strLanguage = null;
        String strPage = null;
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar createdDate = null;
        SolutionConfigType objSolutionConfig = null;
        XMLGregorianCalendar lastUpdatedDate;
        // Instance variables created for task details
        TaskDetailType taskDetailType = null;
        TaskDocType taskDocType = null;
        RepairCodeType repairCodeType = null;
        List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = null;
        List<RxTaskRepairCodeVO> lstRepairCodes = null;
        List<AddEditRxTaskResultVO> arlTaskDetails = null;
        try {
            LOG.debug("Start getSolutionDetails Webservice method");
			String applicationTimeZone=getRequestHeader("appTime");	
            queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.FROM_PAGE)) {
                strPage = queryParams.getFirst(OMDConstants.FROM_PAGE);
            }
            // Check for null solutionId and from page
            if (!RMDCommonUtility.isNullOrEmpty(solutionId)
                    && !RMDCommonUtility.isNullOrEmpty(strPage)
                    && AppSecUtil.checkIntNumber(solutionId)) {
                strSolutionId = solutionId;
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

            objAddEditRxServiceVO = new AddEditRxServiceVO();
            objSolutionResponseType = new SolutionResponseType();
            objSolutionDetailType = new SolutionDetailType();
            objAddEditRxServiceVO.setStrFromPage(strPage);
            objAddEditRxServiceVO.setStrRxId(strSolutionId);
            objAddEditRxServiceVO.setStrLanguage(strLanguage);
            objAddEditRxServiceVO.setStrUserLanguage(strLanguage);
            // calling the recom details method to fetch the recom info based on
            // the rx id passed
            // the output of this method i.e., the solution details like soltion
            // status,
            // type,title are saved in the same object objAddEditRxServiceVO
            // so keep using that object objAddEditRxServiceVO again
            objRecommEoaServiceIntf.fetchRecommDetails(objAddEditRxServiceVO);

            // iterating the list of Recom history and set it in
            // SolutionHistoryType
            if (!RMDCommonUtility.checkNull(objAddEditRxServiceVO)) {
                if (null != objAddEditRxServiceVO.getCreationDate()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objAddEditRxServiceVO
                            .getCreationDate());
                    createdDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(objGregorianCalendar);
                    objSolutionDetailType.setCreationDate(createdDate);
                }
                if (null != objAddEditRxServiceVO.getLastUpdatedDate()) {
                    objGregorianCalendar = new GregorianCalendar();
                    objGregorianCalendar.setTime(objAddEditRxServiceVO
                            .getLastUpdatedDate());
                    lastUpdatedDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(objGregorianCalendar);
                    objSolutionDetailType.setLastEditedDate(lastUpdatedDate);
                }
                // if the model list and subsystem values are not there through
                // exception, as these fields are
                // mandatory for the recommendation.
                if (RMDCommonUtility.isCollectionNotEmpty(objAddEditRxServiceVO
                        .getModelList())) {
                    objSolutionDetailType.getSolutionModel().addAll(
                            objAddEditRxServiceVO.getModelList());
                }
                if (RMDCommonUtility.isCollectionNotEmpty(objAddEditRxServiceVO
                        .getCustomerList())) {
                    objSolutionDetailType.getSolutionCustomer().addAll(
                            objAddEditRxServiceVO.getCustomerList());
                }
                
                if (RMDCommonUtility.isCollectionNotEmpty(objAddEditRxServiceVO
                        .getFleetList())) {
                    objSolutionDetailType.getSolutionFleet().addAll(
                            objAddEditRxServiceVO.getFleetList());
                }
                if (RMDCommonUtility.isCollectionNotEmpty(objAddEditRxServiceVO
                                .getSubSystemList())) {
                    objSolutionDetailType.getSolutionSubSystems().addAll(
                            objAddEditRxServiceVO.getSubSystemList());
                }
                objSolutionDetailType.setExclude(objAddEditRxServiceVO.getExclude());
                arlrxhistory = objAddEditRxServiceVO.getArlRxHistory();
                arlRxConfigVO = objAddEditRxServiceVO.getArlRxConfig();
                if (!RMDCommonUtility.checkNull(arlrxhistory)) {
                    for (RxHistServiceVO objRxHistServiceVO : arlrxhistory) {
                        objSolutionHistoryType = new SolutionHistoryType();
                        // converting the java date to XML date
                        if (null != objRxHistServiceVO.getStrDateCreated()) {
							objSolutionHistoryType.setCreationDate(RMDCommonUtility.getConvertedDate(objRxHistServiceVO
									.getStrDateCreated(),applicationTimeZone));
                        }
                        objSolutionHistoryType.setChangeDetail(objRxHistServiceVO.getChangeDetail());
                        BeanUtility.copyBeanProperty(objRxHistServiceVO,
                                objSolutionHistoryType);
                        objSolutionResponseType.getSolutionHistory().add(
                                objSolutionHistoryType);
                    }
                }
                if (!RMDCommonUtility.checkNull(arlRxConfigVO)) {
                    for (RxConfigVO rxConfigVO2 : arlRxConfigVO) {
                        objSolutionConfig = new SolutionConfigType();
                        BeanUtils
                        .copyProperties(rxConfigVO2, objSolutionConfig);
                        objSolutionDetailType.getSolutionConfig().add(
                                objSolutionConfig);
                    }
                }
                BeanUtility.copyBeanProperty(objAddEditRxServiceVO,
                        objSolutionDetailType);

                objSolutionResponseType
                .setSolutionDetail(objSolutionDetailType);
            }
            // Fetching Recom Task Details Start
            objRecommEoaServiceIntf.getTaskDetails(objAddEditRxServiceVO);
            arlTaskDetails = objAddEditRxServiceVO.getEditRxTaskResultList();
            if (RMDCommonUtility.isCollectionNotEmpty(arlTaskDetails)) {
                for (AddEditRxTaskResultVO addEditRxTaskResultVO2 : arlTaskDetails) {
                    taskDetailType = new TaskDetailType();                    
                    lstAddEditRxTaskDocVO = addEditRxTaskResultVO2
                    .getAddEditRxTaskDocVO();
                    if (!RMDCommonUtility.checkNull(lstAddEditRxTaskDocVO)) {
                        for (AddEditRxTaskDocVO objAddEditRxTaskDocVO : lstAddEditRxTaskDocVO) {
                        	taskDocType = new TaskDocType();
                            BeanUtility.copyBeanProperty(objAddEditRxTaskDocVO,
                                    taskDocType);
                            taskDetailType.getTaskDoc().add(taskDocType);
                        }
                    }
                    
                    lstRepairCodes = addEditRxTaskResultVO2
                    		.getRepairCodes();
                    if (!RMDCommonUtility.checkNull(lstRepairCodes)) {
                    	for (RxTaskRepairCodeVO objRepairCode : lstRepairCodes) {
                    		repairCodeType = new RepairCodeType();
                    		BeanUtility.copyBeanProperty(objRepairCode,
                    				repairCodeType);
                    		taskDetailType.getRepairCodes().add(repairCodeType);
                    	}
                    }
                    
                    BeanUtility.copyBeanProperty(addEditRxTaskResultVO2,
                            taskDetailType);
                    objSolutionResponseType.getLstTaskDetail().add(
                            taskDetailType);
                }
            } 
            // Fetching Recom Task Details End
            //Rx plot
            if(null !=objAddEditRxServiceVO.getAddEditRxPlotVO()){
            	SolutionPlotType solutionPlotType = new SolutionPlotType();
            	solutionPlotType.setPlotObjid(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrPlotObjid());
            	solutionPlotType.setPlotTitle(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrPlotTitle());
            	solutionPlotType.setIndependentAxislabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrIndependentAxislabel());
            	solutionPlotType.setLeftDependentAxislabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrLeftDependentAxislabel());
            	solutionPlotType.setRightDependentAxislabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrRightDependentAxislabel());
            	solutionPlotType.setDataSetOneName(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetOneName());
            	solutionPlotType.setDataSetOneLabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetOneLabel());
            	solutionPlotType.setDataSetOneAxis(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetOneAxis());
            	solutionPlotType.setDataSetTwoName(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetTwoName());
            	solutionPlotType.setDataSetTwoLabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetTwoLabel());
            	solutionPlotType.setDataSetTwoAxis(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetTwoAxis());
            	solutionPlotType.setDataSetThreeName(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetThreeName());
            	solutionPlotType.setDataSetThreeLabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetThreeLabel());
            	solutionPlotType.setDataSetThreeAxis(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetThreeAxis());
            	solutionPlotType.setDataSetFourName(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetFourName());
            	solutionPlotType.setDataSetFourLabel(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetFourLabel());
            	solutionPlotType.setDataSetFourAxis(objAddEditRxServiceVO.getAddEditRxPlotVO().getStrDataSetFourAxis());
            	objSolutionResponseType.setSolutionPlot(solutionPlotType);
            }  
            LOG.debug("End getSolutionDetails Webservice method");
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return objSolutionResponseType;
    }
    
    
    /**
     * @Description:This method is used for fetching Solution details
     * @param solutionId
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_RX_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SolutionDetailType getRxDetails(
            @PathParam(OMDConstants.SOLUTION_Id) final String solutionId)
            throws RMDServiceException {
        SolutionDetailType objSolutionResponseType = null;
        RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO;
        RxDetailsVO rxDetailsVO = null;
        try {
            if (!RMDCommonUtility.isNullOrEmpty(solutionId)
                    && AppSecUtil.checkIntNumber(solutionId)) {

                rxSearchCriteriaServiceVO = new RxSearchCriteriaEoaServiceVO();
                rxSearchCriteriaServiceVO.setStrRxObjid(solutionId);
                rxSearchCriteriaServiceVO
                        .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                rxDetailsVO = objRecommEoaServiceIntf
                        .getRecommDetails(rxSearchCriteriaServiceVO);
                if (null != rxDetailsVO) {
                    objSolutionResponseType = new SolutionDetailType();
                    objSolutionResponseType.setSolutionID(rxDetailsVO
                            .getRxObjid());
                    objSolutionResponseType.setSolutionTitle(rxDetailsVO
                            .getRxTitle());
                    objSolutionResponseType.setUrgRepair(rxDetailsVO
                            .getUrgency());
                    objSolutionResponseType.setEstmTimeRepair(rxDetailsVO
                            .getEstRepTime());
                    objSolutionResponseType.setLocomotiveImpact(rxDetailsVO
                            .getLocoImpact());
                    objSolutionResponseType.setVersion(String
                            .valueOf(rxDetailsVO.getVersion()));
                    objSolutionResponseType.setSolutionType(rxDetailsVO
                            .getRxType());
                    objSolutionResponseType.setSolutionStatus(rxDetailsVO
                            .getRxstatus());
                } else{
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                        .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return objSolutionResponseType;
    }
    /**
     * @Description:This method is used for fetching Solution task details
     * @param solutionId
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_RX_TASK_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<TaskDetailType> getRxTaskDetails(
            @PathParam(OMDConstants.SOLUTION_Id) final String solutionId)
            throws RMDServiceException {
        RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO;
        RxTaskDetailsVO taskDetailsVO = null;
        List<TaskDetailType> taskDetailsResponseList = null;
        List<RxTaskDetailsVO> taskDetailsList = null;
        TaskDetailType taskDetailType = null;
        try {
            if (!RMDCommonUtility.isNullOrEmpty(solutionId)
                    && AppSecUtil.checkIntNumber(solutionId)) {

                rxSearchCriteriaServiceVO = new RxSearchCriteriaEoaServiceVO();
                rxSearchCriteriaServiceVO.setStrRxObjid(solutionId);
                rxSearchCriteriaServiceVO
                        .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                taskDetailsList = objRecommEoaServiceIntf
                        .getRxTaskDetails(rxSearchCriteriaServiceVO);
                if (RMDCommonUtility.isCollectionNotEmpty(taskDetailsList)) {
                    taskDetailsResponseList = new ArrayList<TaskDetailType>();
                    Iterator<RxTaskDetailsVO> it = taskDetailsList.iterator();
                    while(it.hasNext()){
                        taskDetailsVO = it.next();
                        taskDetailType = new TaskDetailType();
                        taskDetailType.setTaskID(taskDetailsVO.getTaskId());
                        taskDetailType.setTaskSequence(taskDetailsVO.getTaskSequence());
                        taskDetailType.setTaskDesc(taskDetailsVO.getTaskDescription());
                        taskDetailType.setUsl(taskDetailsVO
                                .getUsl());
                        taskDetailType.setLsl(taskDetailsVO
                                .getLsl());
                        taskDetailType.setTarget(taskDetailsVO
                                .getTarget());
                        taskDetailsResponseList.add(taskDetailType);
                    }
                    
                } else{
                    throw new OMDNoResultFoundException(
                            BeanUtility
                            .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                    new String[] {},
                                    BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                }
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                        .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return taskDetailsResponseList;
    }
    
    
    /**
     * @Description:This method is used for updating the solutionStatus
     * @param path
     *            Param
     * @return updatestatus in SolutionResponseType
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.UPDATE_SOLUTION_STATUS)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SolutionResponseType updateSolutionStatus(SolutionRequestType objSolutionRequestType)
    throws RMDServiceException {
        AddEditRxServiceVO objAddEditRxServiceVO = null;
        String strSolutionId = null;
        String strLanguage = null;
        String strSolutionStatus = null;
        String strUpdateStatus = RMDCommonConstants.FAILURE;
        SolutionResponseType objSolutionResponseType = null;
        String strUserName = RMDCommonConstants.EMPTY_STRING;
        String strSolutionOrigId = RMDCommonConstants.EMPTY_STRING;
        String strLockedBy = RMDCommonConstants.EMPTY_STRING;
        String strSolutionType=null;
        try {
            LOG.debug("Start updateSolutionStatus Webservice method");
            objAddEditRxServiceVO = new AddEditRxServiceVO();
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserName = getRequestHeader(OMDConstants.STR_USERNAME);
            objAddEditRxServiceVO.setStrUserName(strUserName);
            objAddEditRxServiceVO.setStrLanguage(strLanguage);
            if (objSolutionRequestType != null
                    && objSolutionRequestType.getSolutionDetail() != null) {
                objSolutionResponseType = new SolutionResponseType();
                strSolutionId = objSolutionRequestType.getSolutionDetail()
                .getSolutionID();
                strSolutionStatus = objSolutionRequestType.getSolutionDetail()
                .getSolutionStatus();
                strLockedBy = objSolutionRequestType.getSolutionDetail()
                .getLockedBy();
                strSolutionType=objSolutionRequestType.getSolutionDetail().getSolutionType();
                // Since the EOA Web Services does not have original id concept
                // and OMD WS need original id to be passed,
                // so when the user accessing the EOA web services, he does not
                // have original id in this case, solution id itself passed as
                // original Id and it will not used in EOA SERVICES.
                if (objSolutionRequestType.getSolutionHistory() != null) {
                    strSolutionOrigId = objSolutionRequestType
                    .getSolutionHistory().getOrginalId();
                    if (RMDCommonUtility.isNullOrEmpty(strSolutionOrigId)) {
                        strSolutionOrigId = strSolutionId;
                    }
                }

                if ((null != strSolutionStatus && !strSolutionStatus.isEmpty())
                        && (null != strSolutionId && !strSolutionId.isEmpty())
                        && (null != strSolutionOrigId)){
                    objAddEditRxServiceVO.setStrRxId(EsapiUtil.stripXSSCharacters(strSolutionId));
                    objAddEditRxServiceVO.setStrRxStatus(EsapiUtil.stripXSSCharacters(strSolutionStatus));
                    objAddEditRxServiceVO.setStrLockedBy(EsapiUtil.stripXSSCharacters(strLockedBy));
                    objAddEditRxServiceVO.setStrRxType(EsapiUtil.stripXSSCharacters(strSolutionType));
                    objAddEditRxServiceVO.setStrOriginalId(EsapiUtil.stripXSSCharacters(strSolutionOrigId));
                    strUpdateStatus = objRecommEoaServiceIntf
                    .recommStatusUpdate(objAddEditRxServiceVO);
                    objSolutionResponseType
                    .setSolutionUpdateStatus(strUpdateStatus);
                } else {
                    throw new OMDInValidInputException(OMDConstants.NULL_SOLUTION_ID_STATUS);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.NULL_SOLUTION_ID_STATUS);
            }
            LOG.debug("End updateSolutionStatus Webservice method");
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return objSolutionResponseType;
    }

    @GET
    @Path(OMDConstants.GETSUBSYSTEMVALUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSubsystemValues()
    throws RMDServiceException {
        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;
        String strListName = OMDConstants.EMPTY_STRING;
        ElementVO objElementVO = null;
        try {
            LOG.debug("Start getSubsystemValues Webservice method");
            strListName = OMDConstants.OMD_SUBSYSTEM;
            arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
            .getSubsystemValues(strListName,
                    getRequestHeader(OMDConstants.LANGUAGE));
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                arlLookupTypes = new ArrayList<LookupResponseType>();
                for (final Iterator<ElementVO> iterator = arlLookupList
                        .iterator(); iterator.hasNext();) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(objElementVO,
                            objLookupResponseType);
                    arlLookupTypes.add(objLookupResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        LOG.debug("End getSubsystemValues Webservice method");
        return arlLookupTypes;
    }

    /**
     * @Description:This method is used for fetching Solution Repair codes
     *                   lookup values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */

    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GETSOLUTIONREPAIRCODE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSolutionRepairCodes(
            @PathParam(OMDConstants.OBJECT_ID) final String ObjID)
            throws RMDServiceException {

        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;

        ElementVO objElementVO = null;
        String strObjID = null;
        try {
            if (ObjID != null && !ObjID.isEmpty()) {
                strObjID = ObjID;
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

            arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
            .getRxRepairCodes(strObjID);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                arlLookupTypes = new ArrayList<LookupResponseType>();
                for (final Iterator<ElementVO> iterator = arlLookupList
                        .iterator(); iterator.hasNext();) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(objElementVO,
                            objLookupResponseType);
                    arlLookupTypes.add(objLookupResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return arlLookupTypes;

    }



    /**
     * @Description:This method is used for fetching Solution Task Desc lookup
     *                   values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */

    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GETSOLUTIONTASKDESC)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSolutionTaskDesc(
            @PathParam(OMDConstants.REPAIRCODE) final String repairCode)
            throws RMDServiceException {

        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;

        ElementVO objElementVO = null;
        String strRepairCode = null;
        try {
            if (repairCode != null && !repairCode.isEmpty()) {
                strRepairCode = repairCode;
            } else {
                throw new OMDInValidInputException(OMDConstants.RXDELVID_NOT_PROVIDED);
            }

            arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
            .getRxTaskdesc(strRepairCode);

            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                arlLookupTypes = new ArrayList<LookupResponseType>();
                for (final Iterator<ElementVO> iterator = arlLookupList
                        .iterator(); iterator.hasNext();) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(objElementVO,
                            objLookupResponseType);
                    arlLookupTypes.add(objLookupResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }

        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupTypes;

    }

    /**
     * @Description:This method is used for fetching Solution Status lookup values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETSOLUTIONVALUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSolutionStatus()
    throws RMDServiceException {

        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;

        ElementVO objElementVO = null;
        String strLookupId=RMDCommonConstants.RECOMMENDATION_STATUS_LISTNAME;
        try {

            arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
            .getRxLookupValues(strLookupId, getRequestHeader(OMDConstants.LANGUAGE));
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                arlLookupTypes = new ArrayList<LookupResponseType>();
                for (final Iterator<ElementVO> iterator = arlLookupList
                        .iterator(); iterator.hasNext();) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    if(objElementVO.getName().equalsIgnoreCase(RMDCommonConstants.DRAFT)
                            ||objElementVO.getName().equalsIgnoreCase(RMDCommonConstants.APPROVED)){
                            //||objElementVO.getName().equalsIgnoreCase(RMDCommonConstants.DISABLED)){
                        BeanUtility.copyBeanProperty(objElementVO,
                                objLookupResponseType);
                        arlLookupTypes.add(objLookupResponseType);
                    }                       

                }
            } else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupTypes;

    }

    /**
     * @Description:This method is used for fetching Solution Configurations
     *                   lookup values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */

    @SuppressWarnings("unchecked")
    @GET
    @Path(OMDConstants.GET_SOLUTION_CONFIGURATIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSolutionConfigurations()
    throws RMDServiceException {
        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;
        ElementVO objElementVO = null;
        String userLanguage;
        String strLanguage;
        try {
            LOG.debug("Start getSolutionConfigurations Webservice method");
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            userLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            arlLookupList = (ArrayList<ElementVO>) objRecommEoaServiceIntf
            .getRxConfigurations(strLanguage, userLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                arlLookupTypes = new ArrayList<LookupResponseType>();
                for (final Iterator<ElementVO> iterator = arlLookupList
                        .iterator(); iterator.hasNext();) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(objElementVO,
                            objLookupResponseType);
                    arlLookupTypes.add(objLookupResponseType);
                }
            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        LOG.debug("End getSolutionConfigurations Webservice method");
        return arlLookupTypes;
    }

    /**
     * @param executionRequestType
     * @return
     * Method used in SolutionResource.java, validates object type SolutionExecutionRequestType
     */
    private static boolean validateSolutionExecutionDetails(SolutionExecutionRequestType executionRequestType){
        boolean isValidData = true;
        // case id
        if(null != executionRequestType.getCaseID() && !executionRequestType.getCaseID().isEmpty()){
            if(! AppSecUtil.checkAlphaNumericHypen(executionRequestType.getCaseID())){
                return false;
            }
        }

        // solution execution id
        if(null != executionRequestType.getSolutionExecutionId() && !executionRequestType.getSolutionExecutionId().isEmpty()){
            if(! AppSecUtil.checkAlphaNumeric(executionRequestType.getSolutionExecutionId())){
                return false;
            }
        }

        // solution case flag
        if(null != executionRequestType.getSolutionCloseFlag() && !executionRequestType.getSolutionCloseFlag().isEmpty()){
            if(! AppSecUtil.checkAlphabets(executionRequestType.getSolutionCloseFlag())){
                return false;
            }
        }

        //language
        if(null != executionRequestType.getLanguage() && !executionRequestType.getLanguage().isEmpty()){
            if(! AppSecUtil.checkAlphabets(executionRequestType.getLanguage())){
                return false;
            }
        }   
      
        //Task Comments
        for (TaskDetailType currentTask : executionRequestType.getTaskDetail()) {
            if (RMDCommonUtility.isSpecialCharactersFound(currentTask
                    .getTaskComments())) {
                return false;
            }
        }
        return isValidData;
    }
    
    
    public static boolean validateAddSolution(SolutionRequestType requestType,
            SolutionDetailType detailType, List<TaskDetailType> taskDetailType) {
        boolean isValidData = true;
        
        //asset impact 
        if(null != requestType.getAssetImpact() && !requestType.getAssetImpact().isEmpty()){
            if(!AppSecUtil.validateTask(requestType.getAssetImpact())){
                return false;
            }
        }
        //solution type
        if(null != requestType.getSolutionType() && !requestType.getSolutionType().isEmpty()){
            if(!AppSecUtil.checkAlphabets(requestType.getSolutionType())){
                return false;
            }
        }
        // validation for solution details type
        
        // solution type, alpha
        if(null != detailType.getSolutionType() && !detailType.getSolutionType().isEmpty()){
            if(!AppSecUtil.checkAlphabets(detailType.getSolutionType())){
                return false;
            }
        }
        
        // created by
        if(null != detailType.getCreatedBy() && !detailType.getCreatedBy().isEmpty()){
            if(!AppSecUtil.checkAlphaUnderScore(detailType.getCreatedBy())){
                return false;
            }
        }
        
    
        // EstmTimeRepair
        if(null != detailType.getEstmTimeRepair() && !detailType.getEstmTimeRepair().isEmpty()){
            if(!AppSecUtil.checkAlphaNumeric(detailType.getEstmTimeRepair())){
                return false;
            }
        }
        // export control
        if(null != detailType.getExportControl() && !detailType.getExportControl().isEmpty()){
            if(!AppSecUtil.checkAlphaNumeric(detailType.getExportControl())){
                return false;
            }
        }
    
        // Locked
        if(null != detailType.getLockedBy() && !detailType.getLockedBy().isEmpty()){
            if(!AppSecUtil.checkAlphaNumeric(detailType.getLockedBy())){
                return false;
            }
        }
        // locomotive impact
        if(null != detailType.getLocomotiveImpact() && !detailType.getLocomotiveImpact().isEmpty()){
            if(!AppSecUtil.validateTask(detailType.getLocomotiveImpact())){
                return false;
            }
        }
        // model
        if(null != detailType.getModel() && !detailType.getModel().isEmpty()){
            if(!AppSecUtil.checkAlphaNumericHypen(detailType.getModel())){
                return false;
            }
        }
        
        // RevisionComments,  
        if(null != detailType.getRevisionComments() && !detailType.getRevisionComments().isEmpty()){
            //if(!AppSecUtil.checkEncode(detailType.getRevisionComments())){
            if(!AppSecUtil.validateRXTitle(detailType.getRevisionComments())){
                return false;
            }
        }
        // SolutionID
        if(null != detailType.getSolutionID() && !detailType.getSolutionID().isEmpty()){
            if(!AppSecUtil.checkIntNumber(detailType.getSolutionID())){
                return false;
            }
        }

        // SolutionRevNo
        if(null != detailType.getSolutionRevNo() && !detailType.getSolutionRevNo().isEmpty()){
            if(!AppSecUtil.checkIntNumber(detailType.getSolutionRevNo())){
                return false;
            }
        }
        // SolutionStatus
        if(null != detailType.getSolutionStatus() && !detailType.getSolutionStatus().isEmpty()){
            if(!AppSecUtil.checkAlphabets(detailType.getSolutionStatus())){
                return false;
            }
        }
        // solution title
        if(null != detailType.getSolutionTitle() && !detailType.getSolutionTitle().isEmpty()){
            //if(!AppSecUtil.checkEncode(detailType.getSolutionTitle())){
            //if(!AppSecUtil.validateTask(detailType.getSolutionTitle())){
            /* Adding validation not to allow @ in RX Title while adding and editing rx */
            if(!AppSecUtil.validateRXTitle(detailType.getSolutionTitle())){         
                return false;
            }
        }
        
		if (null != detailType.getSolutionSubSystems()) {
			StringTokenizer stringToken = null;
			for (String subSystem : detailType.getSolutionSubSystems()) {
				stringToken = new StringTokenizer(subSystem, "|");
				int count = stringToken.countTokens();
				if (count < 3)
					return false;
			}
		}
        
        Iterator<SolutionConfigType> config = detailType.getSolutionConfig().iterator();
        
        while(config.hasNext()){
                SolutionConfigType configType = (SolutionConfigType)config.next();
                
                //config
                if(null != configType.getConfigFunction() && !configType.getConfigFunction().isEmpty()){
                    
                        if(! AppSecUtil.checkConfigfilterFunction(configType.getConfigFunction())){
                            return false;
                        }
                    }
                
                if(null != configType.getConfigValue1() && !configType.getConfigValue1().isEmpty()){
                    
                    if(! AppSecUtil.checkAlphaNumeric(configType.getConfigValue1())){
                        return false;
                    }
                }
                
                if(null != configType.getConfigValue2() && !configType.getConfigValue2().isEmpty()){
                    
                    if(! AppSecUtil.checkAlphaNumeric(configType.getConfigValue2())){
                        return false;
                    }
                }
                
                if(null != configType.getConfigName() && !configType.getConfigName().isEmpty()){
                    
                    if(! AppSecUtil.validateTask(configType.getConfigName())){
                        return false;
                    }
                }
            
            }// eof while
        
        
        
        
        // validation for task detail type
    
        Iterator<TaskDetailType> taskDetailsList = taskDetailType.iterator();
        while(taskDetailsList.hasNext()){
            TaskDetailType taskType = (TaskDetailType)taskDetailsList.next();
            
    
            // closure percent
            if(null != taskType.getClosurePerc() && !taskType.getClosurePerc().isEmpty()){
                if(!AppSecUtil.isDoubleValue(taskType.getClosurePerc())){
                    return false;
                }
            }
        
            //feedback
            /* Removing feedback from RX Task Screen , this can be achievable  by new field task type
             * 
            if(null != taskType.getFeedback() && !taskType.getFeedback().isEmpty()){
                if(!AppSecUtil.validateFeedBack(taskType.getFeedback())){
                    return false;
                }
            }*/
            //last updated by
            if(null != taskType.getLastUpdatedBy() && !taskType.getLastUpdatedBy().isEmpty()){
                if(!AppSecUtil.checkAlphaUnderScore(taskType.getLastUpdatedBy())){
                    return false;
                }
            }
            //lsl 
            /*if(null != taskType.getLsl() && !taskType.getLsl().isEmpty()){
                if(!AppSecUtil.validateTarget(taskType.getLsl())){
                    return false;
                }
            }*/
            
            if(null != taskType.getLsl() && !taskType.getLsl().isEmpty()){
                if(!AppSecUtil.isNumericWithNegative(taskType.getLsl())){
                       return false;
                }
          }

            
            //usl
            /*if(null != taskType.getUsl() && !taskType.getUsl().isEmpty()){
                if(!AppSecUtil.validateTarget(taskType.getUsl())){
                    return false;
                }
            }*/
            if(null != taskType.getUsl() && !taskType.getUsl().isEmpty()){
                if(!AppSecUtil.isNumericWithNegative(taskType.getUsl())){
                       return false;
                }
              }

            
            //mandatory numbers
            if(null != taskType.getMandatory() && !taskType.getMandatory().isEmpty()){
                if(!AppSecUtil.checkIntNumber(taskType.getMandatory())){
                    return false;
                }
            }
            //recomm task
            if(null != taskType.getRecomTaskId() && !taskType.getRecomTaskId().isEmpty()){
                if(!AppSecUtil.checkIntNumber(taskType.getRecomTaskId())){
                    return false;
                }
            }
        
            //repair code
            if(null != taskType.getRepairCodeId() && !taskType.getRepairCodeId().isEmpty()){
                if(!AppSecUtil.checkIntNumber(taskType.getRepairCodeId())){
                    return false;
                }
            }
            //sub mask
            if(null != taskType.getSubTask() && !taskType.getSubTask().isEmpty()){
                if(!AppSecUtil.checkAlphabets(taskType.getSubTask())){
                    return false;
                }
            }
            // target
            /*if(null != taskType.getTarget() && !taskType.getTarget().isEmpty()){
                if(!AppSecUtil.validateTarget(taskType.getTarget())){
                    return false;
                }
            }*/
            if(null != taskType.getTarget() && !taskType.getTarget().isEmpty()){
                if(!AppSecUtil.isNumericWithNegative(taskType.getTarget())){
                    return false;
                }
            }
            // task desc
            /*if(null != taskType.getTaskDesc() && !taskType.getTaskDesc().isEmpty()){
                if(!AppSecUtil.validateTask(taskType.getTaskDesc())){
                    return false;
                }
            }*/
            // task id
            if(null != taskType.getTaskID() && !taskType.getTaskID().isEmpty()){
                if(!AppSecUtil.checkNumbersDot(taskType.getTaskID())){
                    return false;
                }
            }
            // task doc 
            List<TaskDocType> taskDoc = taskType.getTaskDoc();
            if(taskDoc != null && taskDoc.size() >0){
                Iterator<TaskDocType> taskDocList = taskDoc.iterator();
                while(taskDocList.hasNext()){
                    TaskDocType docType = (TaskDocType)taskDocList.next();
                
                    // doc id
//                  if(null != docType.getDocID() && !docType.getDocID().isEmpty()){
//                      if(!AppSecUtil.checkIntNumber(docType.getDocID())){
//                          return false;
//                      }
//                  }
//                  // doc path
//                  if(null != docType.getDocPath() && !docType.getDocPath().isEmpty()){
//                      if(!AppSecUtil.validateDocPath(docType.getDocPath())){
//                          return false;
//                      }
//                  }
//                  // doc title
                    if(null != docType.getDocTitle() && !docType.getDocTitle().isEmpty()){
                        if(!AppSecUtil.checkAlphaNumeralsUnderscoreForRx(docType.getDocTitle())){
                            return false;
                        }
                    }
                }
            }
    }
        return isValidData;
    }

    /**
     * @param request
     * @return
     * @throws OMDDirectoryCreationException
     * @throws Exception
     */
    @SuppressWarnings("all")
    @POST
    @Path(OMDConstants.RXTASK_UPLOAD_ATTACHMENT)
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Map<String, String> uploadRxSolutionTaskAttachment(
            @Context final HttpServletRequest request)
            throws OMDDirectoryCreationException, Exception {
        String taskId = OMDConstants.EMPTY_STRING;
        String loggedinuser = OMDConstants.EMPTY_STRING;
        String fileName = OMDConstants.EMPTY_STRING;
        int solutionid = 0;
        FileItem itemOrg = null;
        String tempPath = OMDConstants.EMPTY_STRING;
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        List<FileItem> items = uploadHandler.parseRequest(request);
        String userHome = System.getProperty(OMDConstants.USER_HOME);
        Map<String, String> response = new HashMap<String, String>();

        try {

            tempPath = OMDConstants.TEMP + File.separator
                    + OMDConstants.ATTACHMENT + File.separator
                    + OMDConstants.RXAUTH;
            if ((userHome == null)
                    || (userHome.indexOf(OMDConstants.SYMBOLE_ROOT) < 0 && userHome
                            .indexOf(OMDConstants.SYMBOL_DOUBLESLASH) < 0))
                userHome = OMDConstants.EMPTY_STRING;
            for (FileItem item : items) {
                String value = (item.isFormField()) ? item.getFieldName()
                        .trim() : item.getName();
                if (value.equalsIgnoreCase(OMDConstants.RX_RX_TASKEID))
                    taskId = item.getString();
                else if (value.equalsIgnoreCase(OMDConstants.RX_SOLUTION_ID))
                    solutionid = Integer.parseInt(item.getString());
                else if (value
                        .equalsIgnoreCase(OMDConstants.RX_LOEGGED_USER_ID))
                    loggedinuser = item.getString();
                else if (!item.isFormField()) {
                    itemOrg = item;
                    fileName = FilenameUtils.getName(itemOrg.getName());
                    if (fileName.isEmpty()
                            || fileName.toLowerCase().endsWith(".exe"))
                        throw new OMDInValidInputException(
                                BeanUtility
                                        .getErrorCode(OMDConstants.INVALID_FILE_NAME),
                                omdResourceMessagesIntf
                                        .getMessage(
                                                BeanUtility
                                                        .getErrorCode(OMDConstants.INVALID_FILE_NAME),
                                                new String[] {},
                                                BeanUtility
                                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));

                }
            }
            if (taskId.isEmpty() || loggedinuser.isEmpty())
                throw new OMDInValidInputException(
                        OMDConstants.INVALID_VALUE,
                        omdResourceMessagesIntf
                                .getMessage(
                                        BeanUtility
                                                .getErrorCode(OMDConstants.INVALID_VALUE),
                                        new String[] {},
                                        BeanUtility
                                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            String rxId = ((solutionid == 0) ? OMDConstants.NEW_RX : String
                    .valueOf(solutionid));
            File fileDir = new File(userHome + File.separator + tempPath
                    + File.separator + loggedinuser.trim() + File.separator
                    + rxId.trim() + File.separator + taskId.trim());
            if (!fileDir.exists()) {
                boolean success = fileDir.mkdirs();
                if (!success)
                    throw new OMDDirectoryCreationException(
                            BeanUtility
                                    .getErrorCode(OMDConstants.DIRECTORYCREATIONEXCEPTION),
                            omdResourceMessagesIntf
                                    .getMessage(
                                            BeanUtility
                                                    .getErrorCode(OMDConstants.DIRECTORYCREATIONEXCEPTION),
                                            new String[] { fileDir
                                                    .getAbsolutePath() },
                                            BeanUtility
                                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

            fileItemFactory.setRepository(fileDir);
            itemOrg.write(new File(fileDir, itemOrg.getName()));
            response.put(OMDConstants.SUCCESS, OMDConstants.SUCCESS);
            response.put(OMDConstants.RX_FILE_PATH, fileItemFactory
                    .getRepository().getAbsolutePath().trim());
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDDirectoryCreationException omdDirectoryCreationException) {
            throw omdDirectoryCreationException;
        } catch (Exception ex) {
            throw new OMDApplicationException(BeanUtility
                    .getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return response;

    }

    /**
     * 
     * @param uriParam
     * @param request
     * @return
     * @throws Exception
     */
    @GET
    @Path(OMDConstants.RXTASK_DOWNLOAD_ATTACHMENT)
    @Produces( { MediaType.APPLICATION_OCTET_STREAM })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response downloadRxSolutionTaskAttachment(
            @Context final UriInfo uriParam,
            @Context final HttpServletRequest request) throws Exception {

        try {
            MultivaluedMap<String, String> queryParams = uriParam
                    .getQueryParameters();
            if (queryParams.isEmpty())
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf
                                .getMessage(
                                        BeanUtility
                                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                        new String[] {},
                                        BeanUtility
                                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            String fileName = queryParams
                    .getFirst(OMDConstants.RX_DOWNLOAD_FILE_NAME);
            String filepath = queryParams.getFirst(OMDConstants.RX_FILE_PATH);
            File file = new File(filepath.trim() + File.separator
                    + fileName.trim());
            if (!file.exists())
                throw new OMDApplicationException(
                        BeanUtility
                                .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                        omdResourceMessagesIntf
                                .getMessage(
                                        BeanUtility
                                                .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                        new String[] {},
                                        BeanUtility
                                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            ResponseBuilder response = Response.ok((Object) file);
            response.header(OMDConstants.FILE_CONTENT_DISPOSITION,
                    OMDConstants.FILE_ATTACHMENT_NAME + fileName);
            return response.build();
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
            return null;

        }

    }

    /**
     * 
     * @param uriParam
     * @return
     * @throws Exception
     */

    @GET
    @Path(OMDConstants.RXTASK_DELETE_ATTACHMENT)
    public String deleteRxSolutionTaskAttachment(@Context final UriInfo uriParam)
            throws Exception {
        try {
            MultivaluedMap<String, String> queryParams = uriParam
                    .getQueryParameters();
            if (queryParams.isEmpty())
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf
                                .getMessage(
                                        BeanUtility
                                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                        new String[] {},
                                        BeanUtility
                                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            String fileName = queryParams
                    .getFirst(OMDConstants.RX_DELETE_FILE_NAME);
            String filepath = queryParams.getFirst(OMDConstants.RX_FILE_PATH);
            File file = new File(filepath.trim() + File.separator
                    + fileName.trim());
            if (!file.exists())
                throw new OMDApplicationException(
                        BeanUtility
                                .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                        omdResourceMessagesIntf
                                .getMessage(
                                        BeanUtility
                                                .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                        new String[] {},
                                        BeanUtility
                                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            file.delete();
            return OMDConstants.SUCCESS;
        } catch (Exception ex) {
        	LOG.info("Exception Occured in deleteRxSolutionTaskAttachment () "+ex);
        	 throw new OMDApplicationException(
                     BeanUtility
                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                     omdResourceMessagesIntf
                             .getMessage(
                                     BeanUtility
                                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                     new String[] {},
                                     BeanUtility
                                             .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

    }
    
    /**
     * 
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.RX_DOWNLOAD_PDF)
    @Produces( { MediaType.APPLICATION_OCTET_STREAM })
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response generateSolutionDetailsPDF(@Context final UriInfo uriParam)
            throws RMDServiceException {
        
        AddEditRxServiceVO objAddEditRxServiceVO = null;
        String strSolutionId = RMDCommonConstants.EMPTY_STRING;
        String strLanguage = null;
        String strPage = null;
        
        try {

                MultivaluedMap<String, String> queryParams = uriParam
                        .getQueryParameters();
                if (queryParams.isEmpty())
                    throw new OMDInValidInputException(
                            BeanUtility
                                    .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                            omdResourceMessagesIntf
                                    .getMessage(
                                            BeanUtility
                                                    .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                            new String[] {},
                                            BeanUtility
                                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                strSolutionId = queryParams
                        .getFirst(OMDConstants.RX_SOLUTION_ID_PDF);
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                objAddEditRxServiceVO = new AddEditRxServiceVO();
                objAddEditRxServiceVO.setStrFromPage(strPage);
                objAddEditRxServiceVO.setStrRxId(strSolutionId);
                objAddEditRxServiceVO.setStrLanguage(strLanguage);
                objAddEditRxServiceVO.setStrUserLanguage(strLanguage);
                objAddEditRxServiceVO
                        .setStrUserName(getRequestHeader(OMDConstants.USERID));
                String filePath = objRecommEoaServiceIntf
                        .generateSolutionDetailsPDF(objAddEditRxServiceVO);
                File file = new File(filePath);
                if (!file.exists())
                    throw new OMDApplicationException(
                            BeanUtility
                                    .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                            omdResourceMessagesIntf
                                    .getMessage(
                                            BeanUtility
                                                    .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                            new String[] {},
                                            BeanUtility
                                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                        .build();

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.debug("Exception occured in generateSolutionDetailsPDF ()"+e);
        	 throw new OMDApplicationException(
                     BeanUtility
                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                     omdResourceMessagesIntf
                             .getMessage(
                                     BeanUtility
                                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                     new String[] {},
                                     BeanUtility
                                             .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

    }
    
        /**
     * 
     * @param uriParam
     * @return
     * @throws Exception
     */
    @GET
    @Path(OMDConstants.GET_ACTIONABLE_RXTYPES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ActionableRxTypeResponse> getActionableRxTypes(
            @Context final UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        ActionableRxTypeVO objActionableRxTypeVO = null;
        List<ActionableRxTypeResponse> actionableRxTypeList = null;
        List<ActionableRxTypeVO> actionableRxTypes = null;
        ActionableRxTypeResponse objActionableRxTypeResponse = null;
        try {
            objActionableRxTypeVO = new ActionableRxTypeVO();
            actionableRxTypeList = new ArrayList<ActionableRxTypeResponse>();
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            queryParams = uriParam.getQueryParameters();

            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {

                String strCustomerId = queryParams
                        .getFirst(OMDConstants.CUSTOMER_ID);
                String strRxGroupId = queryParams
                        .getFirst(OMDConstants.RX_GROUP_ID);
                int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC,
                        RMDCommonConstants.NUMERIC };
                if (AppSecUtil.validateWebServiceInput(queryParams, null,
                        methodConstants, OMDConstants.CUSTOMER_ID,
                        OMDConstants.RX_GROUP_ID)) {
                    objActionableRxTypeVO.setCustomerId(strCustomerId);
                    objActionableRxTypeVO.setStrLanguage(strLanguage);
                    objActionableRxTypeVO.setRxGroupId(strRxGroupId);

                    actionableRxTypes = objRecommEoaServiceIntf
                            .getActionableRxTypes(objActionableRxTypeVO);
                    if (null != actionableRxTypes
                            && !actionableRxTypes.isEmpty()) {
                        for (ActionableRxTypeVO rxType : actionableRxTypes) {
                            objActionableRxTypeResponse = new ActionableRxTypeResponse();
                            objActionableRxTypeResponse.setRxType(rxType
                                    .getRxTypeCd());
                            objActionableRxTypeResponse.setRxGroupId(rxType
                                    .getRxGroupId());
                            objActionableRxTypeResponse.setRxTitle(rxType
                                    .getRxTitle());
                            objActionableRxTypeResponse.setRxTitleId(rxType
                                    .getRxTitleId());
                            objActionableRxTypeResponse.setCustomerName(rxType
                                    .getCustomerName());
                            objActionableRxTypeResponse.setCustomerId(rxType
                                    .getCustomerId());
                            actionableRxTypeList
                                    .add(objActionableRxTypeResponse);
                        }
                    }
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.CUSTOMERID_NOT_PROVIDED);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return actionableRxTypeList;
    }
    
    
    /**
     * 
     * @param uriParam
     * @return
     * @throws Exception
     */
    @GET
    @Path(OMDConstants.GET_NON_ACTIONABLE_RXTITLES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ActionableRxTypeResponse> getNonActionableRxTitles(@Context final UriInfo uriParam)
            throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        List<ActionableRxTypeResponse> actionableRxTypeList = null;
        List<ActionableRxTypeVO> actionableRxTypes = null;
        ActionableRxTypeResponse objActionableRxTypeResponse = null;
        try {
            actionableRxTypeList = new ArrayList<ActionableRxTypeResponse>();
            String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            queryParams = uriParam.getQueryParameters();
            String strCustomerId = queryParams
                    .getFirst(OMDConstants.CUSTOMER_ID);
            int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };
            if (AppSecUtil.validateWebServiceInput(queryParams, null,
                    methodConstants, OMDConstants.CUSTOMER_ID)) {
                actionableRxTypes = objRecommEoaServiceIntf.getNonActionableRxTitles(
                        strCustomerId, strLanguage);
                if (RMDCommonUtility.isCollectionNotEmpty(actionableRxTypes)) {
                    for (ActionableRxTypeVO rxType : actionableRxTypes) {
                        objActionableRxTypeResponse = new ActionableRxTypeResponse();
                        objActionableRxTypeResponse.setRxType(EsapiUtil.stripXSSCharacters((String)
                    			(rxType.getRxTypeCd())));
                        objActionableRxTypeResponse.setRxGroupId(EsapiUtil.stripXSSCharacters((String)
                        		(rxType.getRxGroupId())));
                        objActionableRxTypeResponse.setRxTitle(EsapiUtil.stripXSSCharacters((String)
                        		(rxType.getRxTitle())));
                        objActionableRxTypeResponse.setRxTitleId(EsapiUtil.stripXSSCharacters((String)
                        		(rxType.getRxTitleId())));
                        objActionableRxTypeResponse.setCustomerName(EsapiUtil.stripXSSCharacters((String)
                        		(rxType.getCustomerName())));
                        objActionableRxTypeResponse.setCustomerId(EsapiUtil.stripXSSCharacters((String)
                        		(rxType.getCustomerId())));
                        actionableRxTypeList.add(objActionableRxTypeResponse);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return actionableRxTypeList;
    }
    
    
    /**
     * @Author:
     * @param :
     * @return:List<LookupResponseType>
     * @throws:Exception
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */
    @GET
    @Path(OMDConstants.GET_RX_SUBSYSTEM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getSubsystem() throws RMDServiceException {

        List<ElementVO> arlSubsystem = new ArrayList<ElementVO>();
        LookupResponseType responseType = null;
        List<LookupResponseType> arlResponseType = new ArrayList<LookupResponseType>();
        try {
            arlSubsystem = objRecommEoaServiceIntf.getSubsystem();

            if (RMDCommonUtility.isCollectionNotEmpty(arlSubsystem)) {
                for (ElementVO objElementVO : arlSubsystem) {
                    responseType = new LookupResponseType();

                    responseType.setLookupID(objElementVO.getId());
                    responseType.setLookupValue(objElementVO.getName());
                    arlResponseType.add(responseType);
                }
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlResponseType;
    }
    
    /**
     * This method used to invoke the service layer to get active rules associated with RX
     * @param String
     * @param UriInfo
     * @return list of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_RX_ACTIVE_RULES_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getRecommActiveRules(
            @PathParam(OMDConstants.SOLUTION_Id) final String solutionId,
            @Context UriInfo uriParam) throws RMDServiceException {
        LOG.info("SolutionResource.getRecommActiveRules() Started");
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;
        ElementVO objElementVO = null;
        String strSolutionId = null;
        try {
            // Check for null solutionId and from page
            if (!RMDCommonUtility.isNullOrEmpty(solutionId) && AppSecUtil.checkIntNumber(solutionId)) {
                        strSolutionId = solutionId;
            } else {
                            throw new OMDInValidInputException(
                                    BeanUtility
                                    .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                                    omdResourceMessagesIntf.getMessage(
                                            BeanUtility
                                            .getErrorCode(OMDConstants.SOLUTION_ID_NOT_PROVIDED),
                                            new String[] {},
                                            BeanUtility
                                            .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }

                List<ElementVO> rxActiveLists = objRecommEoaServiceIntf.getRxActiveRules(strSolutionId);
                
                if (RMDCommonUtility.isCollectionNotEmpty(rxActiveLists)) {
                    arlLookupTypes = new ArrayList<LookupResponseType>();
                    for (final Iterator<ElementVO> iterator = rxActiveLists
                            .iterator(); iterator.hasNext();) {
                        objElementVO = (ElementVO) iterator.next();
                        objLookupResponseType = new LookupResponseType();
                        CMBeanUtility.copyElementVOToLookupResponseType(objElementVO,
                                objLookupResponseType);
                        arlLookupTypes.add(objLookupResponseType);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }   
        }catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        LOG.info("SolutionResource.getRecommActiveRules() Ended");
        return arlLookupTypes;
    }

    /**
     * 
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.DOWNLOAD_STREAM_ATTACHMENT)
    @Produces( { MediaType.APPLICATION_OCTET_STREAM })
    public Response downloadStreamAttachment(@Context final UriInfo uriParam) throws RMDServiceException {
        
        AddEditRxServiceVO objAddEditRxServiceVO = null;
        String strLanguage = null;
        StreamingOutput stream = null;
        String fileName = null;
        String filepath = null;
        MultivaluedMap<String, String> queryParams = uriParam.getQueryParameters();
        if (queryParams.isEmpty()) {
            throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                    new String[] {},
                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        fileName = queryParams.getFirst(OMDConstants.RX_DOWNLOAD_FILE_NAME);
        filepath = queryParams.getFirst(OMDConstants.RX_FILE_PATH);
        strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
        objAddEditRxServiceVO = new AddEditRxServiceVO();
        objAddEditRxServiceVO.setStrLanguage(strLanguage);
        objAddEditRxServiceVO.setStrUserLanguage(strLanguage);
        objAddEditRxServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
        try {
            final ByteArrayOutputStream os = (ByteArrayOutputStream) objRecommEoaServiceIntf.downloadAttachment(objAddEditRxServiceVO, filepath, fileName);
            if (os != null) {
                stream = new StreamingOutput() {
                    public void write(OutputStream los) throws IOException, WebApplicationException {
                        os.writeTo(los);
                    }
                };
            } else {
                throw new RuntimeException("File not found");
            }
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        	 }
        return Response.ok(stream, MediaType.APPLICATION_OCTET_STREAM).build();
    }
    
    @GET
    @Path(OMDConstants.GET_DELIVER_RX_URL)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getDeliverRxURL(@Context final UriInfo ui)
            throws RMDServiceException {
    	LOG
                .debug("AssetResource : Inside getDeliverRxURL() method:::::START ");
        MultivaluedMap<String, String> queryParams = null;
        String result = null;
        String caseId=null;
        try {
            if (null != ui.getQueryParameters()) {
                queryParams = ui.getQueryParameters();
            }
            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
            	caseId=queryParams
                        .getFirst(OMDConstants.CASE_Id);
            }
            result = objCaseEoaServiceIntf.getDeliverRxURL(caseId);
        } catch (Exception ex) {
        	LOG.debug(ex.getMessage(), ex);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return result;
    }
    @GET
    @Path(OMDConstants.GET_REPEATERS_RX_LIST)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionExecutionResponseType> getRepeaterRxsList() {
    	LOG.debug("AssetResource : Inside getRepeaterRxsList() method:::::START ");
    	List<RxExecTaskDetailsServiceVO> objRxExecTaskDetailsServiceVOList = null;
        List<SolutionExecutionResponseType> solutionExecutionResponseTypeList = new ArrayList<SolutionExecutionResponseType>();
        try {
        	objRxExecTaskDetailsServiceVOList = objRxExecutionEoaServiceIntf
                    .getRepeaterRxsList();
        	if(objRxExecTaskDetailsServiceVOList!=null)
        	{
                for (RxExecTaskDetailsServiceVO obj : objRxExecTaskDetailsServiceVOList) {
                	SolutionExecutionResponseType objSolutionExecutionResponseType = new SolutionExecutionResponseType();
                	objSolutionExecutionResponseType.setAssetNumber(obj.getStrAssetNumber());
                	objSolutionExecutionResponseType.setRxCaseId(obj.getStrRxCaseId());
                	objSolutionExecutionResponseType.setRxTitle(obj.getStrRxTitle());
                	objSolutionExecutionResponseType.setNotes(obj.getNotes());
                	solutionExecutionResponseTypeList
                            .add(objSolutionExecutionResponseType);
                }
        	}
        }
        catch (Exception e) {
        	LOG.debug(e.getMessage(), e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        finally{
        	objRxExecTaskDetailsServiceVOList=null;
        }
        return solutionExecutionResponseTypeList;
    }
    
    
	/**
	 * This method is used for fetching the Solution Execution details based on
	 * the input - rxCaseId and this Webservice is specially Designed for Mobile
	 * Application
	 * 
	 * @param strRxCaseId
	 * @return SolutionExecutionResponseType
	 * @throws RMDServiceException
	 */
    @GET
    @Path(OMDConstants.GET_RX_EXECUTION_DETAILS)
    @Produces(MediaType.APPLICATION_JSON)
    public SolutionExecutionResponseType getRxExecutionDetails(
            @PathParam(OMDConstants.RX_CASE_ID) final String strRxCaseId,@Context final UriInfo ui)
    throws RMDServiceException {
        Iterator<RxExecTaskServiceVO> iterExecTask;
        Iterator<String> iterRecomTaskList;
        String strRecom;
        RxExecTaskDetailsServiceVO objRxExecTaskDetailsServiceVO;
        SolutionExecutionResponseType objSolutionExecutionResponseType = null;
        TaskDetailType objTaskDetailType;
        List<RxExecTaskServiceVO> arlRxExecTask;
        List<String> arlRecomTaskList;
        RxExecTaskServiceVO objExecTaskServiceVO;
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar issueDate = null;
        XMLGregorianCalendar closedDate = null;
        XMLGregorianCalendar lastUpDate = null;
        String strRXCaseID = null;
        String timeZone=getDefaultTimeZone();
        String customerId = null;
        boolean isMobileRequest=true;
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (!queryParams.isEmpty() && queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                    customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
                }
           
            MultivaluedMap< String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.RX_CASE_ID, strRxCaseId);
            int[] methodConstants = {RMDCommonConstants.ALPHA_NUMERIC_HYPEN};
            if (strRxCaseId != null
                    && AppSecUtil.validateWebServiceInput(map,
                            null, methodConstants, OMDConstants.RX_CASE_ID)) {
                strRXCaseID = strRxCaseId;
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.RXDELVID_NOT_PROVIDED),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            objRxExecTaskDetailsServiceVO = objRxExecutionEoaServiceIntf
            .getRxExecutionDetails(strRXCaseID,
                    getRequestHeader(OMDConstants.LANGUAGE),customerId,isMobileRequest);

            arlRxExecTask = objRxExecTaskDetailsServiceVO.getArlExecTasks();
            arlRecomTaskList = objRxExecTaskDetailsServiceVO
            .getArlRecomTaskList();

            objSolutionExecutionResponseType = new SolutionExecutionResponseType();
            
			if (null != arlRecomTaskList
					&& RMDCommonUtility.isCollectionNotEmpty(arlRecomTaskList)) {

				iterRecomTaskList = arlRecomTaskList.iterator();

				while (iterRecomTaskList.hasNext()) {
					strRecom = iterRecomTaskList.next();
					objSolutionExecutionResponseType.getArlRecomTaskList().add(
							strRecom);
				}
			}
           
            if (RMDCommonUtility.isCollectionNotEmpty(arlRxExecTask)) {
                iterExecTask = arlRxExecTask.iterator();
                while (iterExecTask.hasNext()) {
                    objExecTaskServiceVO =  iterExecTask
                    .next();
                    objTaskDetailType = new TaskDetailType();
                    CMBeanUtility.copyRxExecTaskServiceVOToTaskDetailType(objExecTaskServiceVO,
                            objTaskDetailType,timeZone);
                    objSolutionExecutionResponseType.getTaskDetail().add(
                            objTaskDetailType);
                    if (objExecTaskServiceVO.getLastUpdateDate() != null) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar.setTime(objExecTaskServiceVO
                                .getLastUpdateDate());
                        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar,timeZone);
                        lastUpDate = DatatypeFactory.newInstance()
                        .newXMLGregorianCalendar(objGregorianCalendar);
                        objTaskDetailType.setLastUpdateDate(lastUpDate);

                    }
                }

            } else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
            if (objRxExecTaskDetailsServiceVO.getDtRxClosedDate() != null) {

                objGregorianCalendar = new GregorianCalendar();
                objGregorianCalendar.setTime(objRxExecTaskDetailsServiceVO
                        .getDtRxClosedDate());
                RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
                closedDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                objSolutionExecutionResponseType.setClosedDate(closedDate); 
            }
            if (objRxExecTaskDetailsServiceVO.getDtRxIssueDate() != null) {
                objGregorianCalendar = new GregorianCalendar();
                objGregorianCalendar.setTime(objRxExecTaskDetailsServiceVO
                        .getDtRxIssueDate());
                issueDate = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(objGregorianCalendar);
                objSolutionExecutionResponseType.setIssueDate(issueDate);
            }
            objSolutionExecutionResponseType
                    .setRxLocationId(objRxExecTaskDetailsServiceVO
                            .getLocationId());
            objSolutionExecutionResponseType.setVersion(objRxExecTaskDetailsServiceVO.getVersion());
            CMBeanUtility.copyRxExTaskDetVOToSolnExResType(objRxExecTaskDetailsServiceVO,
                    objSolutionExecutionResponseType);

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return objSolutionExecutionResponseType;
    }
    @GET		
    @Path(OMDConstants.VALIDATE_URL)		
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })		
    public List<TaskDetailType> validateURL(@Context final UriInfo ui) {		
    	LOG.debug("AssetResource : Inside validateURL() method:::::START ");		
    	List<String> objRxExecTaskServiceVO = null;		
        List<TaskDetailType> taskDetailType = new ArrayList<TaskDetailType>();		
        MultivaluedMap<String, String> queryParams = null;		
        String caseId=null;		
        String fileName=null;	
        String recommId = null;
        try {		
        	if (null != ui.getQueryParameters()) {		
                queryParams = ui.getQueryParameters();		
            }		
            if (queryParams !=null && queryParams.containsKey(OMDConstants.CASE_Id)) {		
            	caseId=queryParams		
                        .getFirst(OMDConstants.CASE_Id);		
            }		
            if (queryParams !=null && queryParams.containsKey(OMDConstants.FILENAME)) {		
            	fileName=queryParams		
                        .getFirst(OMDConstants.FILENAME);		
            }	
            if (queryParams !=null && queryParams.containsKey(OMDConstants.TASK_ID)) {		
            	recommId=queryParams		
                        .getFirst(OMDConstants.TASK_ID);		
            }
        	objRxExecTaskServiceVO = objRxExecutionEoaServiceIntf		
                    .validateURL(caseId,fileName,recommId);		
        	if(objRxExecTaskServiceVO!=null)		
        	{		
                for (String obj : objRxExecTaskServiceVO) {		
                	TaskDetailType objTaskDetailType = new TaskDetailType();		
                	objTaskDetailType.setDocTitle(obj);		
                	taskDetailType		
                            .add(objTaskDetailType);		
                }		
        	}		
        }		
        catch (Exception e) {		
        	LOG.debug(e.getMessage(), e);		
            throw new OMDApplicationException(		
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),		
                    omdResourceMessagesIntf.getMessage(BeanUtility		
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),		
                            new String[] {}, BeanUtility		
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));		
        }		
        finally{		
        	objRxExecTaskServiceVO=null;		
        }		
        return taskDetailType;		
    }
    
    
    /**
     * 
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.PREVIEW_PDF)
    @Produces( MediaType.APPLICATION_OCTET_STREAM )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response previewRxPdf(SolutionRequestType objSolutionRequestType)
            throws RMDServiceException {
        
    	AddEditRxServiceVO objAddEditRxServiceVO;
        AddEditRxTaskResultVO objTaskResultVO;
        AddEditRxTaskDocVO objTaskDocVO;
        RxConfigVO objConfigVO;
        ArrayList<AddEditRxTaskResultVO> arlTasks;
        ArrayList<AddEditRxTaskDocVO> arlDoc;
        ArrayList<RxConfigVO> arlConfigVO;
        try {
                    if (null != objSolutionRequestType) {             
                    objAddEditRxServiceVO = new AddEditRxServiceVO();
                    objAddEditRxServiceVO
                    .setStrUserName(getRequestHeader(OMDConstants.STR_USERNAME));
                    objAddEditRxServiceVO
                    .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                    objAddEditRxServiceVO
                    .setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
    
                    arlTasks = new ArrayList<AddEditRxTaskResultVO>();
    
                    BeanUtility.copyBeanProperty(objSolutionRequestType,
                            objAddEditRxServiceVO);
                
                    BeanUtility.copyBeanProperty(
                            objSolutionRequestType.getSolutionDetail(),
                            objAddEditRxServiceVO);
                    arlConfigVO = new ArrayList<RxConfigVO>();
                    for (SolutionConfigType objConfigType : objSolutionRequestType
                            .getSolutionDetail().getSolutionConfig()) {
                        objConfigVO = new RxConfigVO();
                        BeanUtils.copyProperties(objConfigType, objConfigVO);
                        arlConfigVO.add(objConfigVO);
                    }
                    objAddEditRxServiceVO.setArlRxConfig(arlConfigVO);
    
                    for (TaskDetailType objTaskDetail : objSolutionRequestType
                            .getTaskDetail()) {
                        objTaskResultVO = new AddEditRxTaskResultVO();
                        BeanUtility.copyBeanProperty(objTaskDetail, objTaskResultVO);
                        arlDoc = new ArrayList<AddEditRxTaskDocVO>();
                        for (TaskDocType objTaskDDoc : objTaskDetail.getTaskDoc()) {
                            objTaskDocVO = new AddEditRxTaskDocVO();
                            BeanUtility.copyBeanProperty(objTaskDDoc, objTaskDocVO);
                            arlDoc.add(objTaskDocVO);
                        }
                        objTaskResultVO.setAddEditRxTaskDocVO(arlDoc);
                        arlTasks.add(objTaskResultVO);
                    }
                    objAddEditRxServiceVO.setLstEditTaskRxResultVO(arlTasks);

                    String filePath = objRecommEoaServiceIntf
                            .previewRxPdf(objAddEditRxServiceVO);
                    File file = new File(filePath);
                    if (!file.exists())
                        throw new OMDApplicationException(
                                BeanUtility
                                        .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                omdResourceMessagesIntf
                                        .getMessage(
                                                BeanUtility
                                                        .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                                new String[] {},
                                                BeanUtility
                                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));

            		
//            		OutputStream attachmentStream = null;
//            		InputStream is = null;
//            		
//            	   
//            	    	    
//            	    	if (filePath != null && filePath.length() > 0 ) {
//            	    		
//            	    		try {
//            	    			is = new FileInputStream(file);
//            	    			attachmentStream = new ByteArrayOutputStream();
//            	
//            					int read = 0;
//            					byte[] bytes = new byte[1024];
//
//            					while ((read = is.read(bytes)) != -1) {
//            						attachmentStream.write(bytes, 0, read);
//            					}
//
//            					
//            					attachmentStream.flush();
//            					 final ByteArrayOutputStream os = (ByteArrayOutputStream) attachmentStream;
//            			            if (os != null) {
//            			                stream = new StreamingOutput() {
//            			                    public void write(OutputStream los) throws IOException, WebApplicationException {
//            			                        os.writeTo(los);
//            			                    }
//            			                };
//            			            } else {
//            			                throw new RuntimeException("File not found");
//            			            }
//            				} catch (Exception e) {
//                            
//            				} finally {
//            					if (is != null) {
//            						try {
//            							is.close();
//            							is = null;
//            						} catch (IOException e) {
//            							is = null;
//                                    LOG.debug(e.getMessage(), e);
//            						}
//            						
//            					}
//            					attachmentStream.close();
//            				}
//            	    	}
            	    

                        
                    
            	
                    return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                            .build();
                    //objSolutionResponseType.setSolutionStatus(strStatus);
                
            } else {
                throw new OMDInValidInputException(
                        BeanUtility
                        .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                new String[] {},
                                BeanUtility
                                .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        
                

        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.debug("Exception occured in generateSolutionDetailsPDF ()"+e);
        	 throw new OMDApplicationException(
                     BeanUtility
                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                     omdResourceMessagesIntf
                             .getMessage(
                                     BeanUtility
                                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                     new String[] {},
                                     BeanUtility
                                             .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

    }
    
    /**
	 * This method is used for fetching the Solution Execution details based on
	 * the input - rxCaseId and this Webservice is specially Designed for Mobile
	 * Application
	 * 
	 * @param strRxCaseId
	 * @return SolutionExecutionResponseType
	 * @throws RMDServiceException
	 */
    @GET
    @Path(OMDConstants.GET_RX_DELIVERY_ATTACHMENTS)
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public List<RxDeliveryAttachmentResponse> getRxDeliveryAttachments(
            @Context final UriInfo ui)
    throws RMDServiceException {
    	List<RxDeliveryAttachmentVO> arlDeliveryAttachmentVO=new ArrayList<RxDeliveryAttachmentVO>();
    	RxDeliveryAttachmentResponse objAttachmentResponse=null;
    	List<RxDeliveryAttachmentResponse> arlAttachmentResponse = new ArrayList<RxDeliveryAttachmentResponse>();
    	String caseObjid=null;
    	try{
    		final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (!queryParams.isEmpty() && queryParams.containsKey(OMDConstants.CASE_OBJID)) {
            	caseObjid = queryParams.getFirst(OMDConstants.CASE_OBJID);
                }
    	if(null!=caseObjid){
    		arlDeliveryAttachmentVO=objRxExecutionEoaServiceIntf.getRxDeliveryAttachments(caseObjid);
    		if(null!=arlDeliveryAttachmentVO){
    		for(RxDeliveryAttachmentVO objAttachmentVO:arlDeliveryAttachmentVO){
    			objAttachmentResponse=new RxDeliveryAttachmentResponse();
    			objAttachmentResponse.setDocumentPath(objAttachmentVO.getStrDocumentPath());
    			objAttachmentResponse.setDocumentTitle(objAttachmentVO.getStrDocumentTitle());
    			arlAttachmentResponse.add(objAttachmentResponse);
    		}
    		}
    	}
    	}catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
        	LOG.debug("Exception occured in generateSolutionDetailsPDF ()"+e);
        	 throw new OMDApplicationException(
                     BeanUtility
                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                     omdResourceMessagesIntf
                             .getMessage(
                                     BeanUtility
                                             .getErrorCode(OMDConstants.FILENOTFOUNDEXCEPTION),
                                     new String[] {},
                                     BeanUtility
                                             .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
		return arlAttachmentResponse;
    	
    }
    
    
    /**
     * @Description:This method is used for fetching Solution related lookup
     *                   values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SOLUTION_CREATED_BY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionDetailType> getSolutionsCreatedBy() throws RMDServiceException {
        List<SolutionDetailType> arlResponse = new ArrayList<SolutionDetailType>();
        List<RxSearchResultServiceVO> arlResult = null;
        String strLanguage = RMDCommonConstants.EMPTY_STRING;
        SolutionDetailType objSolutionDetailType = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlResult = objRecommEoaServiceIntf.getSolutionCreatedBy(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                for (RxSearchResultServiceVO rxSearchResultServiceVO : arlResult) {                    
                    objSolutionDetailType = new SolutionDetailType();
                    objSolutionDetailType.setCreatedBy(rxSearchResultServiceVO.getCreatedBy());                    
                    arlResponse.add(objSolutionDetailType);
                }
            } 

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlResponse;
    }
}
