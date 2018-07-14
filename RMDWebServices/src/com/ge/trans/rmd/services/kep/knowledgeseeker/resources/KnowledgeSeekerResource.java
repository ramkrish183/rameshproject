/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerResource.java
 * Description : 
 * Package : com.ge.trans.kep.services.knowledgeseeker.resources
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.kep.knowledgeseeker.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;

import com.ge.trans.eoa.services.kep.common.constants.KEPCommonConstants;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.intf.KnowledgeSeekerServiceIntf;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.AnalysisVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.BaseVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.KSRequestVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.SummaryVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.TrackingVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.AnalysisDataResponseType;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.BaseDataResponseType;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.SummaryDataResponseType;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.SystemLookupResponseType;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.TrackingDataResponseType;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.TrackingSummaryResponseType;
import com.ge.trans.rmd.services.kep.request.valueobjects.KnowledgeSeekerReqRequestType;
import com.ge.trans.rmd.services.kep.request.valueobjects.KnowledgeSeekerReqResponseType;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: November 2, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as WebService for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
@Path(OMDConstants.KEPSERVICE_KNOWLEDGESEEKER)
@Component
@SuppressWarnings("unchecked")
public class KnowledgeSeekerResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KnowledgeSeekerResource.class);
    @Autowired
    KnowledgeSeekerServiceIntf objKnowledgeSeekerServiceIntf;
    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * This method is used for fetching data details based on the input
     * 
     * @param uriParam
     * @return BaseDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETPATTERNSUMMARYDETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<BaseDataResponseType> getPatternSummaryDetails(@Context UriInfo uriParam) throws Exception {
        List<BaseVO> arlBaseVO = null;
        BaseDataResponseType objBaseDataResponseType = null;
        List<BaseDataResponseType> arlBaseDataResponseType = new ArrayList<BaseDataResponseType>();
        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams = uriParam.getQueryParameters();
        String strpatternName = null;
        String strtrackingID = null;
        String strPatternSeqId = null;
        String strpatternCategory = null;
        try {
            int[] paramFlag = { OMDConstants.NUMERIC, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.PATTERN_SEQ_ID,
                    OMDConstants.PATTERN_CATEGORY)) {
                /*
                 * if (queryParams.containsKey(OMDConstants.PATTERN_NAME)) {
                 * strpatternName = queryParams
                 * .getFirst(OMDConstants.PATTERN_NAME); }
                 */
                if (queryParams.containsKey(OMDConstants.PATTERN_SEQ_ID)) {
                    strPatternSeqId = queryParams.getFirst(OMDConstants.PATTERN_SEQ_ID);
                }
                if (queryParams.containsKey(OMDConstants.PATTERN_CATEGORY)) {
                    strpatternCategory = queryParams.getFirst(OMDConstants.PATTERN_CATEGORY);
                }

                arlBaseVO = objKnowledgeSeekerServiceIntf.getPatternDetails(strPatternSeqId, strpatternCategory);
                if (BeanUtility.isCollectionNotEmpty(arlBaseVO)) {
                    for (Iterator<BaseVO> iterator = arlBaseVO.iterator(); iterator.hasNext();) {
                        BaseVO objBaseVO = iterator.next();
                        objBaseDataResponseType = new BaseDataResponseType();
                        BeanUtils.copyProperties(objBaseVO, objBaseDataResponseType, new String[] {
                                OMDConstants.START_DATE, OMDConstants.END_DATE, OMDConstants.CREATE_DATE });
                        arlBaseDataResponseType.add(objBaseDataResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlBaseDataResponseType;
    }

    /**
     * This method is used for fetching list of the Tracking ID details based on
     * the tracking id
     * 
     * @param PathParam
     *            trackingID
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    /*
     * @GET
     * @Path(OMDConstants.GETTRACKINGIDS_TRACKINGID)
     * @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
     * public List<TrackingDataResponseType> getTrackingIds(
     * @PathParam(OMDConstants.TRACKING_ID) String strTrackingID) {
     * List<TrackingDataResponseType> arlTrackingDataResponseType = new
     * ArrayList<TrackingDataResponseType>(); TrackingDataResponseType
     * objTrackingDataResponseType = null; List<TrackingVO> arlTrackingVO =
     * null; BaseDataResponseType objBaseDataResponseType = null;
     * MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
     * try { int[] paramFlag = {OMDConstants.NUMERIC}; String[] userInput =
     * {OMDConstants.TRACKING_ID}; multivaluedMap.add(OMDConstants.TRACKING_ID,
     * strTrackingID); if(AppSecUtil.validateWebServiceInput(multivaluedMap,
     * null, paramFlag, userInput)){ arlTrackingVO =
     * objKnowledgeSeekerServiceIntf .getTrackingIDs(strTrackingID); if
     * (BeanUtility.isCollectionNotEmpty(arlTrackingVO)) { for (TrackingVO
     * trackingVO : arlTrackingVO) { objTrackingDataResponseType = new
     * TrackingDataResponseType(); objBaseDataResponseType = new
     * BaseDataResponseType(); BeanUtils.copyProperties(trackingVO,
     * objBaseDataResponseType, new String[] { OMDConstants.START_DATE,
     * OMDConstants.END_DATE, OMDConstants.CREATE_DATE });
     * objTrackingDataResponseType
     * .setBaseDataResponseType(objBaseDataResponseType);
     * arlTrackingDataResponseType .add(objTrackingDataResponseType); } } }else{
     * throw new OMDInValidInputException(OMDConstants.INVALID_VALUE); } } catch
     * (OMDInValidInputException objOMDInValidInputException) { throw
     * objOMDInValidInputException; } catch (OMDNoResultFoundException
     * objOMDNoResultFoundException) { throw objOMDNoResultFoundException; }
     * catch (Exception e) { throw new OMDApplicationException(BeanUtility
     * .getErrorCode(OMDConstants.GENERALEXCEPTION),
     * omdResourceMessagesIntf.getMessage(BeanUtility
     * .getErrorCode(OMDConstants.GENERALEXCEPTION), new String[] {},
     * BeanUtility .getLocale(OMDConstants.DEFAULT_LANGUAGE))); } return
     * arlTrackingDataResponseType; }
     */
    /**
     * This method is used for fetching list of the CreatedBy details based on
     * the CreatedBy
     * 
     * @param PathParam
     *            CreatedBy
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETCREATORS_CREATBY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<TrackingDataResponseType> getCreators(@PathParam(OMDConstants.CREATED_BY) String createdBy) {
        List<TrackingDataResponseType> arlTrackingDataResponseType = new ArrayList<TrackingDataResponseType>();
        TrackingDataResponseType objTrackingDataResponseType = null;
        List<TrackingVO> arlTrackingVO = null;
        BaseDataResponseType objBaseDataResponseType = null;
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
        try {

            // Commenting to enabling Alpha Numeric for Created by
            /*
             * int[] paramFlag = {OMDConstants.ALPHA_UNDERSCORE};
             */
            int[] paramFlag = { OMDConstants.AlPHA_NUMERIC };
            String[] userInput = { OMDConstants.CREATED_BY };
            multivaluedMap.add(OMDConstants.CREATED_BY, createdBy);
            if (AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)) {
                arlTrackingVO = objKnowledgeSeekerServiceIntf.getCreators(createdBy);
                if (BeanUtility.isCollectionNotEmpty(arlTrackingVO)) {
                    for (TrackingVO trackingVO : arlTrackingVO) {
                        objTrackingDataResponseType = new TrackingDataResponseType();
                        objBaseDataResponseType = new BaseDataResponseType();
                        BeanUtils.copyProperties(trackingVO, objBaseDataResponseType, new String[] {
                                OMDConstants.START_DATE, OMDConstants.END_DATE, OMDConstants.CREATE_DATE });
                        objTrackingDataResponseType.setBaseDataResponseType(objBaseDataResponseType);
                        arlTrackingDataResponseType.add(objTrackingDataResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlTrackingDataResponseType;
    }

    /**
     * This method is used for fetching list of the runName details based on the
     * runName
     * 
     * @param PathParam
     *            runName
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETRUNNAMES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<TrackingDataResponseType> getRunNames(@PathParam(OMDConstants.RUN_NAME) String runName) {
        List<TrackingDataResponseType> arlTrackingDataResponseType = new ArrayList<TrackingDataResponseType>();
        TrackingDataResponseType objTrackingDataResponseType = null;
        List<TrackingVO> arlTrackingVO = null;
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
        try {
            int[] paramFlag = { OMDConstants.ALPHA_NUMERIC_UNDERSCORE };
            String[] userInput = { OMDConstants.RUN_NAME };
            multivaluedMap.add(OMDConstants.RUN_NAME, runName);
            if (AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)) {
                arlTrackingVO = objKnowledgeSeekerServiceIntf.getRunNames(runName);
                if (BeanUtility.isCollectionNotEmpty(arlTrackingVO)) {
                    for (TrackingVO trackingVO : arlTrackingVO) {
                        objTrackingDataResponseType = new TrackingDataResponseType();
                        BeanUtils.copyProperties(trackingVO, objTrackingDataResponseType,
                                new String[] { OMDConstants.BASEDATARESPONSETYPE });
                        arlTrackingDataResponseType.add(objTrackingDataResponseType);
                    }
                } else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlTrackingDataResponseType;
    }

    /**
     * This method is used for fetching list of the summary details based on the
     * trackingID,createdBy,fromDate,toDate,runName,status
     * 
     * @param URIINFo
     * @return list of TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETKNOWLEDGESEEKERSTRACKINGID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<TrackingDataResponseType> getKnowledgeSeekerTrackings(@Context UriInfo ui) {
        List<TrackingDataResponseType> arlTrackingDataResponseType = new ArrayList<TrackingDataResponseType>();
        TrackingDataResponseType objTrackingDataResponseType = null;
        List<TrackingVO> arlTrackingVO = null;
        BaseDataResponseType objBaseDataResponseType = null;
        GregorianCalendar objGregorianCalendar = null;
        XMLGregorianCalendar createddate = null;
        XMLGregorianCalendar startddate = null;
        XMLGregorianCalendar endddate = null;
        TrackingVO objTrackingVO = null;
        String strTrackingID;
        String strCreatedBy;
        String strrunName;
        String strStatus;
        String strFromdate;
        String strCreateDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(OMDConstants.DATE_FORMAT);
        LOG.debug("Inside getKnowledgeSeekerTrackings--Start");
        try {
            MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
            int[] paramFlag = { OMDConstants.NUMERIC, OMDConstants.AlPHA_NUMERIC, OMDConstants.VALID_DATE,
                    OMDConstants.VALID_DATE, OMDConstants.ALPHABETS, OMDConstants.ALPHA_NUMERIC_UNDERSCORE,
                    OMDConstants.VALID_DATE };
            String[] userInput = { OMDConstants.TRACKING_ID, OMDConstants.CREATED_BY, OMDConstants.FROM_DATE,
                    OMDConstants.TO_DATE, OMDConstants.STATUS, OMDConstants.RUN_NAME, OMDConstants.CREATE_DATE };
            if (AppSecUtil.validateWebServiceInput(queryParams, OMDConstants.DATE_FORMAT, paramFlag, userInput)) {
                objTrackingVO = new TrackingVO();
                if (queryParams.containsKey(OMDConstants.TRACKING_ID)) {
                    strTrackingID = queryParams.getFirst(OMDConstants.TRACKING_ID);
                    if (null != strTrackingID && strTrackingID.length() > 0) {
                        objTrackingVO.setTrackID(strTrackingID);
                    }
                }
                if (queryParams.containsKey(OMDConstants.CREATED_BY)) {
                    strCreatedBy = queryParams.getFirst(OMDConstants.CREATED_BY);
                    if (null != strCreatedBy && strCreatedBy.length() > 0) {
                        objTrackingVO.setCreatedBy(strCreatedBy);
                    }
                }
                if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
                    strFromdate = queryParams.getFirst(OMDConstants.FROM_DATE);
                    if (null != strFromdate && strFromdate.length() > 0) {
                        Date fromDate = dateFormat.parse(strFromdate);
                        objTrackingVO.setDtStartDate(fromDate);
                    }
                }
                if (queryParams.containsKey(OMDConstants.TO_DATE)) {
                    String strTodate = queryParams.getFirst(OMDConstants.TO_DATE);
                    if (null != strTodate && strTodate.length() > 0) {
                        Date toDate = dateFormat.parse(strTodate);
                        objTrackingVO.setDtEndDate(toDate);
                    }
                }
                if (queryParams.containsKey(OMDConstants.RUN_NAME)) {
                    strrunName = queryParams.getFirst(OMDConstants.RUN_NAME);
                    if (null != strrunName && strrunName.length() > 0) {
                        objTrackingVO.setRunName(strrunName);
                    }
                }
                if (queryParams.containsKey(OMDConstants.STATUS)) {
                    strStatus = queryParams.getFirst(OMDConstants.STATUS);
                    if (null != strStatus && strStatus.length() > 0) {
                        objTrackingVO.setStatus(strStatus);
                    }
                }
                if (queryParams.containsKey(OMDConstants.CREATE_DATE)) {
                    strCreateDate = queryParams.getFirst(OMDConstants.CREATE_DATE);
                    if (null != strCreateDate && strCreateDate.length() > 0) {
                        Date createdDate = dateFormat.parse(strCreateDate);
                        objTrackingVO.setDtCreateDate(createdDate);
                    }
                }
                arlTrackingVO = objKnowledgeSeekerServiceIntf.getKnowledgeSeekerTrackings(objTrackingVO);
                if (BeanUtility.isCollectionNotEmpty(arlTrackingVO)) {
                    for (TrackingVO trackingVO : arlTrackingVO) {
                        objTrackingDataResponseType = new TrackingDataResponseType();
                        objBaseDataResponseType = new BaseDataResponseType();
                        BeanUtils.copyProperties(trackingVO, objBaseDataResponseType, new String[] {
                                OMDConstants.START_DATE, OMDConstants.END_DATE, OMDConstants.CREATE_DATE });
                        BeanUtils.copyProperties(trackingVO, objTrackingDataResponseType,
                                new String[] { OMDConstants.BASEDATARESPONSETYPE });

                        if (!RMDCommonUtility.checkNull(trackingVO.getDtStartDate())) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(trackingVO.getDtStartDate());
                            startddate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objBaseDataResponseType.setStartDate(startddate);
                        }
                        if (!RMDCommonUtility.checkNull(trackingVO.getDtCreateDate())) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(trackingVO.getDtCreateDate());
                            createddate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objBaseDataResponseType.setCreateDate(createddate);
                        }
                        if (!RMDCommonUtility.checkNull(trackingVO.getDtEndDate())) {
                            objGregorianCalendar = new GregorianCalendar();
                            objGregorianCalendar.setTime(trackingVO.getDtEndDate());
                            endddate = DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar);
                            objBaseDataResponseType.setEndDate(endddate);
                        }

                        objTrackingDataResponseType.setBaseDataResponseType(objBaseDataResponseType);
                        arlTrackingDataResponseType.add(objTrackingDataResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        LOG.debug("Inside getKnowledgeSeekerTrackings--END");
        return arlTrackingDataResponseType;
    }

    /**
     * This method is used for fetching list of the summary details based on the
     * trackingID and also it will retrieve the Tracking details as well
     * 
     * @param PathParam
     *            trackingID
     * @return list of SummaryDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETTRACKINGDEATILS_TRACKINGID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public TrackingSummaryResponseType getTrackingDetails(@PathParam(OMDConstants.TRACKING_ID) String trackingID) {
        SummaryDataResponseType objSummaryDataResponseType = null;
        List<SummaryDataResponseType> listSummaryDataResponseType = new ArrayList<SummaryDataResponseType>();
        BaseDataResponseType objBaseDataResponseType = null;
        List<AnalysisDataResponseType> arlAnalysisDataList = new ArrayList<AnalysisDataResponseType>();
        AnalysisDataResponseType objAnalysisDataResponseType;
        List<AnalysisVO> lstTrackingSummary = new ArrayList<AnalysisVO>();
        TrackingSummaryResponseType objTrackingSummaryResponseType = new TrackingSummaryResponseType();
        MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
        try {
            int[] paramFlag = { OMDConstants.NUMERIC };
            String[] userInput = { OMDConstants.TRACKING_ID };
            multivaluedMap.add(OMDConstants.TRACKING_ID, trackingID);
            if (AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)) {
                lstTrackingSummary = objKnowledgeSeekerServiceIntf.getTrackingDetails(trackingID);
                if (BeanUtility.isCollectionNotEmpty(lstTrackingSummary)) {
                    Iterator<AnalysisVO> iterator = lstTrackingSummary.iterator();
                    List<AnalysisVO> arlAnalysisServiceVO = (List<AnalysisVO>) iterator.next();
                    for (AnalysisVO analysisVO : arlAnalysisServiceVO) {
                        if (analysisVO != null) {
                            objAnalysisDataResponseType = new AnalysisDataResponseType();
                            objBaseDataResponseType = new BaseDataResponseType();
                            BeanUtils.copyProperties(analysisVO, objAnalysisDataResponseType);
                            BeanUtils.copyProperties(analysisVO, objBaseDataResponseType);
                            objAnalysisDataResponseType.setBaseDataResponseType(objBaseDataResponseType);
                            objTrackingSummaryResponseType.getAnalysisDataResponseType()
                                    .add(objAnalysisDataResponseType);
                            arlAnalysisDataList.add(objAnalysisDataResponseType);
                        }
                    }
                    List<SummaryVO> arlSummaryVO = (List<SummaryVO>) iterator.next();
                    for (SummaryVO objsummaryVO : arlSummaryVO) {
                        if (objsummaryVO != null) {
                            objBaseDataResponseType = new BaseDataResponseType();
                            objSummaryDataResponseType = new SummaryDataResponseType();
                            BeanUtils.copyProperties(objsummaryVO, objBaseDataResponseType, new String[] {
                                    OMDConstants.START_DATE, OMDConstants.END_DATE, OMDConstants.CREATE_DATE });
                            BeanUtils.copyProperties(objsummaryVO, objSummaryDataResponseType,
                                    new String[] { OMDConstants.BASEDATARESPONSETYPE });
                            objSummaryDataResponseType.setBaseDataResponseType(objBaseDataResponseType);
                            objTrackingSummaryResponseType.getSummaryDataResponseType().add(objSummaryDataResponseType);
                            listSummaryDataResponseType.add(objSummaryDataResponseType);
                        }
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objTrackingSummaryResponseType;
    }

    @POST
    @Path(OMDConstants.CREATEREQUEST)
    @Consumes({ MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public KnowledgeSeekerReqResponseType saveKnowledgeSeekerRequest(
            KnowledgeSeekerReqRequestType objKSSearchRequestType) throws RMDServiceException {
        KSRequestVO objKSRequestVO = null;
        KnowledgeSeekerReqResponseType objKSReqResponseType = new KnowledgeSeekerReqResponseType();
        int trackingId = 0;

        try {
            if (validateSaveKnowledgeSeekerRequest(objKSSearchRequestType)) {
                if (objKSSearchRequestType != null) {
                    objKSRequestVO = new KSRequestVO();

                    LOG.debug(
                            "Road number that we have got from UI into Resource " + objKSSearchRequestType.getRoadNo());

                    objKSRequestVO.setRunname(objKSSearchRequestType.getRunname());

                    objKSRequestVO.setCustomers(objKSSearchRequestType.getCustomerIds());
                    objKSRequestVO.setFleets(objKSSearchRequestType.getFleets());
                    objKSRequestVO.setModels(objKSSearchRequestType.getModels());
                    objKSRequestVO.setConfigs(objKSSearchRequestType.getConfigs());
                    objKSRequestVO.setUnitNumbers(objKSSearchRequestType.getUnitNos());
                    objKSRequestVO.setRoadNumber(objKSSearchRequestType.getRoadNo());
                    objKSRequestVO.setUserName(objKSSearchRequestType.getUserName());

                    // Added for New changes
                    objKSRequestVO.setClassification(objKSSearchRequestType.getClassification());

                    objKSRequestVO.setFilterFlag(objKSSearchRequestType.getFilterFlag());

                    objKSRequestVO.setClassificationWindow(objKSSearchRequestType.getClassificationWindow());
                    objKSRequestVO.setAlgorithm(objKSSearchRequestType.getAlgorithm());
                    objKSRequestVO.setCoverage(objKSSearchRequestType.getCoverage());
                    objKSRequestVO.setNormalData(objKSSearchRequestType.getNormalData());
                    objKSRequestVO.setProgWindow(objKSSearchRequestType.getProgWindow());
                    objKSRequestVO.setRootCause(objKSSearchRequestType.getRootCause());
                    objKSRequestVO.setSymptom(objKSSearchRequestType.getSymptom());
                    objKSRequestVO.setDaysAfterEvent(objKSSearchRequestType.getDaysAfterEvent());
                    objKSRequestVO.setFromdate(objKSSearchRequestType.getFromdate());
                    objKSRequestVO.setTodate(objKSSearchRequestType.getTodate());
                    objKSRequestVO.setFaultCode(objKSSearchRequestType.getFaultCode());

                    objKSRequestVO.setRx(objKSSearchRequestType.getRx());

                    trackingId = objKnowledgeSeekerServiceIntf.createKnowledgeSeekerRequest(objKSRequestVO);
                    objKSReqResponseType.setTrackingID(trackingId);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        }

        catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return objKSReqResponseType;
    }

    /**
     * This method is used for fetching list of the Status details based on the
     * Status
     * 
     * @param PathParam
     *            Status
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETSTATUS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getStatus(@Context UriInfo uriParam) {
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] pathParamFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, pathParamFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                } else {

                    throw new OMDInValidInputException(OMDConstants.LISTNAME_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * This is the method used for fetching the classification values through
     * jquery call
     * 
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETCLASSIFICATION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getClassification(@Context UriInfo uriParam) {

        LOG.debug("Inside knowledgeseeker resource in getClassification Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                }

                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the
     *                   ClassificationWindow values.
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETCLASSIFICATIONWINDOW)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getClassificationWindow(@Context UriInfo uriParam) {

        LOG.debug("Inside knowledgeseeker resource in getClassificationWindow Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                }

                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the DaysAfterEvent
     *                   values.
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETDAYSAFTEREVENT)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getDaysAfterEvent(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getDaysAfterEvent Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                }

                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the Algorithm values.
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETALGORITHM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getAlgorithm(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getAlgorithm Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                }

                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the Root Cause values.
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETROOTCAUSE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getRootCause(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getRootCause Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strModel = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        String strFromDate = OMDConstants.EMPTY_STRING;
        String strToDate = OMDConstants.EMPTY_STRING;
        String strFleet = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] pathParam = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, pathParam, KEPCommonConstants.CUSTOMER,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(KEPCommonConstants.CUSTOMER)) {
                    strCustomer = queryParams.getFirst(KEPCommonConstants.CUSTOMER);
                }
                if (queryParams.containsKey(KEPCommonConstants.MODEL)) {
                    strModel = queryParams.getFirst(KEPCommonConstants.MODEL);
                }
                if (queryParams.containsKey(KEPCommonConstants.FROM_DATE)) {
                    strFromDate = queryParams.getFirst(KEPCommonConstants.FROM_DATE);
                }
                if (queryParams.containsKey(KEPCommonConstants.TO_DATE)) {
                    strToDate = queryParams.getFirst(KEPCommonConstants.TO_DATE);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                if (queryParams.containsKey(KEPCommonConstants.FLEET)) {
                    strFleet = queryParams.getFirst(KEPCommonConstants.FLEET);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getRootCause(strCustomer, strModel, strFleet,
                        strFromDate, strToDate);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the Symptom values
     *                   through jquery call
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETSYMPTOM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getSymptom(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getSymptom Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strModel = OMDConstants.EMPTY_STRING;
        String strFleet = OMDConstants.EMPTY_STRING;
        String strFromDate = OMDConstants.EMPTY_STRING;
        String strToDate = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, KEPCommonConstants.CUSTOMER,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(KEPCommonConstants.CUSTOMER)) {
                    strCustomer = queryParams.getFirst(KEPCommonConstants.CUSTOMER);
                }
                if (queryParams.containsKey(KEPCommonConstants.MODEL)) {
                    strModel = queryParams.getFirst(KEPCommonConstants.MODEL);
                }
                if (queryParams.containsKey(KEPCommonConstants.FLEET)) {
                    strFleet = queryParams.getFirst(KEPCommonConstants.FLEET);
                }
                if (queryParams.containsKey(KEPCommonConstants.FROM_DATE)) {
                    strFromDate = queryParams.getFirst(KEPCommonConstants.FROM_DATE);
                }
                if (queryParams.containsKey(KEPCommonConstants.TO_DATE)) {
                    strToDate = queryParams.getFirst(KEPCommonConstants.TO_DATE);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getSymptom(strCustomer, strModel, strFleet,
                        strFromDate, strToDate);

                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @Description:This is the method used for fetching the Prognostic window
     *                   values through jquery call
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETPROGNOSTICWINDOW)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getPrognosticWindow(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getPrognosticWindow Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                }

                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    @GET
    @Path(OMDConstants.GETCONFIGFUNCTION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getConfigFunction(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getConfigFunction Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
            }

            if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
            }
            arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
            if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                for (ElementVO elementVO : arlLookupDetails) {
                    objSystemLookupResponseType = new SystemLookupResponseType();
                    BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                    arlSystemLookupResponseType.add(objSystemLookupResponseType);
                }
            }

        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * This method is used for fetching list of the Filter flags
     * 
     * @param PathParam
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETFILTERFLAG)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getFilterFlag(@Context UriInfo uriParam) {
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] pathParamFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, pathParamFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                } else {

                    throw new OMDInValidInputException(OMDConstants.LISTNAME_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * This method is used for fetching list of the Data Filter
     * 
     * @param PathParam
     * @return TrackingDataResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETDATA_FILTER)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getDataFilter(@Context UriInfo uriParam) {
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strListName = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] pathParamFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, pathParamFlag, OMDConstants.LIST_NAME,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(OMDConstants.LIST_NAME)) {
                    strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
                } else {

                    throw new OMDInValidInputException(OMDConstants.LISTNAME_NOT_PROVIDED);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getLookUPDetails(strListName, strLanguage);
                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }

                else {
                    throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

    /**
     * @param seekerReqRequestType
     * @return Method validates the object type KnowledgeSeekerReqRequestType,
     *         used to validate iMine create new request page
     */
    public static boolean validateSaveKnowledgeSeekerRequest(KnowledgeSeekerReqRequestType seekerReqRequestType) {
        boolean isValidData = true;
        String dataArray[] = null;
        // customer id
        if (null != seekerReqRequestType.getCustomerIds() && !seekerReqRequestType.getCustomerIds().isEmpty()
                && !seekerReqRequestType.getCustomerIds().equalsIgnoreCase(OMDConstants.all)) {
            dataArray = seekerReqRequestType.getCustomerIds().split(OMDConstants.COMMA);
            for (String value : dataArray) {
                if (!AppSecUtil.checkAlphaNumeric(value)) {
                    return false;
                }
            }
        }

        // fleet
        if (null != seekerReqRequestType.getFleets() && !seekerReqRequestType.getFleets().isEmpty()
                && !seekerReqRequestType.getFleets().equalsIgnoreCase(OMDConstants.all)) {
            dataArray = seekerReqRequestType.getFleets().split(OMDConstants.COMMA);
            for (String value : dataArray) {
                if (!AppSecUtil.checkAlphaNumeralsUnderscore(value)) {
                    return false;
                }
            }
        }

        // model
        if (null != seekerReqRequestType.getModels() && !seekerReqRequestType.getModels().isEmpty()
                && !seekerReqRequestType.getModels().equalsIgnoreCase(OMDConstants.all)) {
            dataArray = seekerReqRequestType.getModels().split(OMDConstants.COMMA);
            for (String value : dataArray) {
                if (!AppSecUtil.checkAlphaNumericHypen(value)) {
                    return false;
                }
            }
        }

        // config
        if (null != seekerReqRequestType.getConfigs() && !seekerReqRequestType.getConfigs().isEmpty()
                && !seekerReqRequestType.getConfigs().equalsIgnoreCase(OMDConstants.all)) {
            dataArray = seekerReqRequestType.getConfigs().split(OMDConstants.COMMA);
            for (String value : dataArray) {
                if (!AppSecUtil.validateConfigFunction(value)) {
                    return false;
                }
            }
        }

        // unit no
        if (null != seekerReqRequestType.getUnitNos() && !seekerReqRequestType.getUnitNos().isEmpty()
                && !seekerReqRequestType.getUnitNos().equalsIgnoreCase(OMDConstants.all)) {
            dataArray = seekerReqRequestType.getUnitNos().split(OMDConstants.COMMA);
            for (String value : dataArray) {
                if (!AppSecUtil.checkAlphaNumeric(value)) {
                    return false;
                }
            }
        }

        // from date
        if (null != seekerReqRequestType.getFromdate() && !seekerReqRequestType.getFromdate().isEmpty()) {
            if (!AppSecUtil.validateDate(seekerReqRequestType.getFromdate(), OMDConstants.DATE_FORMAT)) {
                return false;
            }
        }

        // to date
        if (null != seekerReqRequestType.getTodate() && !seekerReqRequestType.getTodate().isEmpty()) {
            if (!AppSecUtil.validateDate(seekerReqRequestType.getTodate(), OMDConstants.DATE_FORMAT)) {
                return false;
            }
        }

        // run name
        if (seekerReqRequestType.getRunname() != null && seekerReqRequestType.getRunname().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumeralsUnderscore(seekerReqRequestType.getRunname())) {
                return false;
            }
        }

        // username
        if (null != seekerReqRequestType.getUserName() && !seekerReqRequestType.getUserName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericHypen(seekerReqRequestType.getUserName())) {
                return false;
            }
        }

        // classification
        if (null != seekerReqRequestType.getClassification() && !seekerReqRequestType.getClassification().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(seekerReqRequestType.getClassification())) {
                return false;
            }
        }

        // classification window
        if (null != seekerReqRequestType.getClassificationWindow()
                && !seekerReqRequestType.getClassificationWindow().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(seekerReqRequestType.getClassificationWindow())) {
                return false;
            }
        }
        // days after event
        if (null != seekerReqRequestType.getDaysAfterEvent() && !seekerReqRequestType.getDaysAfterEvent().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(seekerReqRequestType.getDaysAfterEvent())) {
                return false;
            }
        }

        // prognostic window
        if (null != seekerReqRequestType.getProgWindow() && !seekerReqRequestType.getProgWindow().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(seekerReqRequestType.getProgWindow())) {
                return false;
            }
        }

        // algorithm
        if (null != seekerReqRequestType.getAlgorithm() && !seekerReqRequestType.getAlgorithm().isEmpty()) {
            if (!AppSecUtil.checkAlphabets(seekerReqRequestType.getAlgorithm())) {
                return false;
            }
        }

        // root cause
        if (null != seekerReqRequestType.getRootCause() && !seekerReqRequestType.getRootCause().isEmpty()) {
            if (!AppSecUtil.checkForwardSlash(seekerReqRequestType.getRootCause())) {
                return false;
            }
        }

        // symptoms
        if (null != seekerReqRequestType.getSymptom() && !seekerReqRequestType.getSymptom().isEmpty()) {
            if (!AppSecUtil.checkForwardSlash(seekerReqRequestType.getSymptom())) {
                return false;
            }
        }

        // rxs
        if (null != seekerReqRequestType.getRx() && !seekerReqRequestType.getRx().isEmpty()) {
            if (!AppSecUtil.checkForwardSlash(seekerReqRequestType.getRx())) {
                return false;
            }
        }

        // coverage
        if (null != seekerReqRequestType.getCoverage() && !seekerReqRequestType.getCoverage().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(seekerReqRequestType.getCoverage())) {
                return false;
            }
        }

        // normal data
        if (null != seekerReqRequestType.getNormalData() && !seekerReqRequestType.getNormalData().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(seekerReqRequestType.getNormalData())) {
                return false;
            }
        }

        return isValidData;
    }

    /**
     * @Description:This is the method used for fetching the Rx values through
     *                   jquery call
     * @param @return
     *            List<SystemLookupResponseType> @throws
     */
    @GET
    @Path(OMDConstants.GETRX)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SystemLookupResponseType> getRx(@Context UriInfo uriParam) {
        LOG.debug("Inside knowledgeseeker resource in getRx Method");
        List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
        List<ElementVO> arlLookupDetails = null;
        MultivaluedMap<String, String> queryParams = null;
        String strCustomer = OMDConstants.EMPTY_STRING;
        String strModel = OMDConstants.EMPTY_STRING;
        String strFleet = OMDConstants.EMPTY_STRING;
        String strFromDate = OMDConstants.EMPTY_STRING;
        String strToDate = OMDConstants.EMPTY_STRING;
        String strLanguage = OMDConstants.EMPTY_STRING;
        SystemLookupResponseType objSystemLookupResponseType = null;
        try {
            queryParams = uriParam.getQueryParameters();
            int[] paramFlag = { OMDConstants.ALPHA_UNDERSCORE, OMDConstants.ALPHABETS };
            if (AppSecUtil.validateWebServiceInput(queryParams, null, paramFlag, KEPCommonConstants.CUSTOMER,
                    OMDConstants.LANGUAGE)) {
                if (queryParams.containsKey(KEPCommonConstants.CUSTOMER)) {
                    strCustomer = queryParams.getFirst(KEPCommonConstants.CUSTOMER);
                }
                if (queryParams.containsKey(KEPCommonConstants.MODEL)) {
                    strModel = queryParams.getFirst(KEPCommonConstants.MODEL);
                }
                if (queryParams.containsKey(KEPCommonConstants.FLEET)) {
                    strFleet = queryParams.getFirst(KEPCommonConstants.FLEET);
                }
                if (queryParams.containsKey(KEPCommonConstants.FROM_DATE)) {
                    strFromDate = queryParams.getFirst(KEPCommonConstants.FROM_DATE);
                }
                if (queryParams.containsKey(KEPCommonConstants.TO_DATE)) {
                    strToDate = queryParams.getFirst(KEPCommonConstants.TO_DATE);
                }
                if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
                    strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
                }
                arlLookupDetails = objKnowledgeSeekerServiceIntf.getRx(strCustomer, strModel, strFleet, strFromDate,
                        strToDate);

                if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
                    for (ElementVO elementVO : arlLookupDetails) {
                        objSystemLookupResponseType = new SystemLookupResponseType();
                        BeanUtils.copyProperties(elementVO, objSystemLookupResponseType);
                        arlSystemLookupResponseType.add(objSystemLookupResponseType);
                    }
                }
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

            }
        } catch (Exception ex) {

            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlSystemLookupResponseType;
    }

}