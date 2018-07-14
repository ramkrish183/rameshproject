/**
 * ============================================================
 * Classification: GE Confidential
 * File : RxChangeResource.java
 * Description : 
 * Package : com.ge.trans.rmd.rxchange.admin.resources
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * History
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.rxchange.resources;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.rxchange.service.intf.RxChangeServiceIntf;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.alert.valueobjects.ModelsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.UserEOADetailsResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.RxDelvDocType;
import com.ge.trans.rmd.services.rxchange.valueobjects.RxChangeAdminRequestType;
import com.ge.trans.rmd.services.rxchange.valueobjects.RxChangeAdminResponseType;
import com.ge.trans.rmd.services.rxchange.valueobjects.RxChangeOverviewRequestType;
import com.ge.trans.rmd.services.rxchange.valueobjects.RxChangeRequestType;
import com.ge.trans.rmd.services.rxchange.valueobjects.RxChangeResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionLiteResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.RXCHANGE_SERVICE)
@Component

public class RxChangeResource extends BaseResource {
    public static final RMDLogger RxChangeResourceLOG = RMDLoggerHelper
            .getLogger(RxChangeResource.class);

    @Autowired
    private RxChangeServiceIntf objRxChangeServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    @POST
    @Path(OMDConstants.RXCHANGE_OVERVIEW_DATA)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RxChangeResponseType> getRxChangeOverviewData(
			final RxChangeOverviewRequestType rxChangeOverviewRequestType)
			throws RMDServiceException {
		List<RxChangeResponseType> lstRxChangeResponse = new ArrayList<RxChangeResponseType>();
		RxChangeResponseType rxChangeResponseType = null;
		try {
			RxChangeSearchVO objRxChangeSearchVO = new RxChangeSearchVO();
			objRxChangeSearchVO.setUserId(rxChangeOverviewRequestType
					.getUserId());
			objRxChangeSearchVO.setCustomerIdLst(rxChangeOverviewRequestType
					.getCustomerIdLst());
			objRxChangeSearchVO.setFromDate(rxChangeOverviewRequestType
					.getFromDate());
			objRxChangeSearchVO.setToDate(rxChangeOverviewRequestType
					.getToDate());
			objRxChangeSearchVO.setStatus(rxChangeOverviewRequestType
					.getStatus());
			objRxChangeSearchVO.setRxTitle(rxChangeOverviewRequestType
					.getRxTitle());
			objRxChangeSearchVO.setModelLst(rxChangeOverviewRequestType
					.getModelLst());
			objRxChangeSearchVO.setTypeOfChangeLst(rxChangeOverviewRequestType
			        .getTypeOfChangeReqLst());
			objRxChangeSearchVO.setTypeOfChange(rxChangeOverviewRequestType
                    .getTypeOfChangeReq());
			
			
			List<RxChangeVO> rxChangeVOLst = objRxChangeServiceIntf
					.getRxChangeOverviewData(objRxChangeSearchVO);
			if (RMDCommonUtility.isCollectionNotEmpty(rxChangeVOLst)) {
				lstRxChangeResponse = new ArrayList<RxChangeResponseType>(
						rxChangeVOLst.size());
				for (RxChangeVO rxChangeVO : rxChangeVOLst) {
					rxChangeResponseType = new RxChangeResponseType();
					rxChangeResponseType
					.setObjId(rxChangeVO.getObjid());
					rxChangeResponseType
							.setRequestId(rxChangeVO.getRequestId());
					rxChangeResponseType.setRequestOwner(rxChangeVO
							.getRequestOwner());
					rxChangeResponseType
							.setRequestor(rxChangeVO.getRequestor());
					rxChangeResponseType.setRevisionType(rxChangeVO.getRevisionType());
					rxChangeResponseType.setModel(rxChangeVO.getModel());
					rxChangeResponseType.setCustomer(rxChangeVO.getCustomer());
					rxChangeResponseType.setStatus(rxChangeVO.getStatus());
					rxChangeResponseType.setRxTitle(rxChangeVO.getRxTitle());
					rxChangeResponseType.setRevisionType(rxChangeVO
							.getRevisionType());
					rxChangeResponseType.setRoadNumber(rxChangeVO
							.getRoadNumber());
					rxChangeResponseType.setAttachment(rxChangeVO
							.getAttachment());
					rxChangeResponseType.setNotes(rxChangeVO
							.getNotes());
					rxChangeResponseType.setCaseId(rxChangeVO.getCaseId());
					rxChangeResponseType.setRequestLoggedDate(rxChangeVO.getRequestLoggedDate());
					rxChangeResponseType.setChangesSuggested(rxChangeVO.getChangesSuggested());
					rxChangeResponseType.setFileName(rxChangeVO.getFileName());
					rxChangeResponseType.setFilePath(rxChangeVO.getFilePath());	
					rxChangeResponseType.setUserName(rxChangeVO.getUserName());	
					rxChangeResponseType.setSaveAsDraftFlag(rxChangeVO
							.getSaveAsDraftFlag());
					rxChangeResponseType.setSaveAsDraftFlag(rxChangeVO
							.getSaveAsDraftFlag());
					rxChangeResponseType.setRxChangeProcObjId(rxChangeVO
							.getRxChangeProcObjId());
					rxChangeResponseType.setNotestoRequestor(rxChangeVO
							.getNotestoRequestor());
					rxChangeResponseType.setRequestOwnerSSO(rxChangeVO
							.getRequestOwnerSSO());
					rxChangeResponseType.setTypeOfChangeReq(rxChangeVO.getTypeOfRxChange());
					rxChangeResponseType.setWhitePaperPdffileName(rxChangeVO.getWhitePaperPdffileName());
					rxChangeResponseType.setWhitePaperPdffilePath(rxChangeVO.getWhitePaperPdffilePath());
					rxChangeResponseType.setRecomObjId(rxChangeVO.getRecomObjId());
					
					List<RxChangeResponseType> rxchangeAuditInfoLst = new ArrayList<RxChangeResponseType>();
					if(rxChangeVO.getRxchangeAuditInfoLst() != null){
					for(RxChangeVO tempRxChangeVO : rxChangeVO.getRxchangeAuditInfoLst()){
					    RxChangeResponseType tempRxChangeResponseType = new RxChangeResponseType();
					    tempRxChangeResponseType.setStatus(tempRxChangeVO.getStatus());
					    tempRxChangeResponseType.setRequestLoggedDate(tempRxChangeVO.getRequestLoggedDate());
					    tempRxChangeResponseType.setUserName(tempRxChangeVO.getUserName());	
					    tempRxChangeResponseType.setNotes(tempRxChangeVO.getNotes());    
					    rxchangeAuditInfoLst.add(tempRxChangeResponseType);
					}
					}
					rxChangeResponseType.setRxchangeAuditInfoLst(rxchangeAuditInfoLst);
					
					lstRxChangeResponse.add(rxChangeResponseType);
				}

			}

		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		return lstRxChangeResponse;
	}

    @GET
    @Path(OMDConstants.GET_RXCHANGE_USER_CASES)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getUserCases(@Context final UriInfo uriParam) throws RMDServiceException{
        boolean userCasesMap = RMDCommonConstants.FALSE;
        
        final MultivaluedMap<String, String> queryParams = uriParam
                .getQueryParameters();
        try{
            String userId = queryParams.getFirst(OMDConstants.USER_ID); 
            String caseId = queryParams.getFirst(OMDConstants.SEARCH_VAL);
            userCasesMap = objRxChangeServiceIntf.getUserCases(userId, caseId);          
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        if(userCasesMap) return "TRUE";
        else 
        return "FALSE";
    }
    
    @GET
    @Path(OMDConstants.GET_RXCHANGE_RX_TITLE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionLiteResponseType> getRxTitle(@Context final UriInfo uriParam) throws RMDServiceException{
        Map<String, String> rxTitleMap = null;
        List<SolutionLiteResponseType> arlResponse = new ArrayList<SolutionLiteResponseType>();
        SolutionLiteResponseType objResponse;
        
        final MultivaluedMap<String, String> queryParams = uriParam
                .getQueryParameters();
        try{
            String rxTitle = queryParams.getFirst(OMDConstants.SEARCH_VAL);
            String modelId = queryParams.getFirst(OMDConstants.MODELID);
            System.out.println("modelId "+modelId);
            
            rxTitleMap = objRxChangeServiceIntf.getRxTitles(rxTitle, modelId); 
            
            if (null != rxTitleMap && rxTitleMap.size() != 0) {
                
                for (Map.Entry<String,String> entry : rxTitleMap.entrySet())  {
                    objResponse = new SolutionLiteResponseType();
                    objResponse.setSolutionId(entry.getKey());
                    objResponse.setSolutionTitle(entry.getValue());
                    arlResponse.add(objResponse);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return arlResponse;
    }
    
    @GET
    @Path(OMDConstants.GET_RXCHANGE_REQUESTORS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<SolutionLiteResponseType> getRxChangeRequestor(@Context final UriInfo uriParam) throws RMDServiceException{
        Map<String, String> userMap = null;
        List<SolutionLiteResponseType> arlResponse = new ArrayList<SolutionLiteResponseType>();
        SolutionLiteResponseType objResponse;
        
        final MultivaluedMap<String, String> queryParams = uriParam
                .getQueryParameters();
        try{
            String fName = queryParams.getFirst(OMDConstants.FIRST_NAME);
            String sName = queryParams.getFirst(OMDConstants.LAST_NAME);
            String userId = queryParams.getFirst(OMDConstants.USER_ID);
            
            userMap = objRxChangeServiceIntf.getOmdUsers(fName, sName, userId); 
            if (null != userMap && userMap.size() != 0) {
                for (Map.Entry<String,String> entry : userMap.entrySet())  {
                    objResponse = new SolutionLiteResponseType();
                    objResponse.setSolutionId(entry.getKey());
                    objResponse.setSolutionTitle(entry.getValue());
                    arlResponse.add(objResponse);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        
        return arlResponse;
    }

    
    @SuppressWarnings("unused")
    @POST
    @Path(OMDConstants.SAVE_RXCHANGE_DETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    public String saveRxChangeDetails(RxChangeRequestType objRxChangeRequestType) throws RMDServiceException{
        RxChangeVO rxChangeVO = new RxChangeVO();
        String result = null;
        
        try {
            
            rxChangeVO.setAdminEmailIdLst(objRxChangeRequestType.getAdminEmailIdLst());
            rxChangeVO.setStrUserName(objRxChangeRequestType.getUserId());
            rxChangeVO.setPdfEmailIdList(objRxChangeRequestType.getPdfEmailIdList());
            rxChangeVO.setTriggerLogicNote(objRxChangeRequestType.getTriggerLogicNote());
            
                if(!RMDCommonUtility.isNullOrEmpty(objRxChangeRequestType.getObjId())){
                    rxChangeVO.setObjid(Long.parseLong(objRxChangeRequestType.getObjId()));
                    rxChangeVO.setStatusObjId(objRxChangeRequestType.getStatusObjId());
                    rxChangeVO.setStatus(objRxChangeRequestType.getStatus());
                    rxChangeVO.setNotes(objRxChangeRequestType.getNotes());
                    rxChangeVO.setRequestOwner(objRxChangeRequestType.getRequestOwner());
                    rxChangeVO.setChangesSuggested(objRxChangeRequestType.getChangesSuggested());
                    
                }else{
                rxChangeVO.setRequestor(objRxChangeRequestType.getRequestor());
                rxChangeVO.setRevisionType(objRxChangeRequestType.getRevisionType());
                rxChangeVO.setRoadNumber(objRxChangeRequestType.getRoadNumber());
                rxChangeVO.setSpecificRxTitle(objRxChangeRequestType.getSpecificRxTitle());
                rxChangeVO.setGeneralRxTitle(objRxChangeRequestType.getGeneralRxTitle());
                rxChangeVO.setCaseId(objRxChangeRequestType.getCaseId());
                rxChangeVO.setChangesSuggested(objRxChangeRequestType.getChangesSuggested());
                rxChangeVO.setNotes(objRxChangeRequestType.getNotes());
                rxChangeVO.setListCustomer(objRxChangeRequestType.getListCustomerIds());
                rxChangeVO.setListModel(objRxChangeRequestType.getListModelIds());
                /**
				 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page Start
				 */
               /* rxChangeVO.setFileName(objRxChangeRequestType.getFileName());
                rxChangeVO.setFileData(objRxChangeRequestType.getFileData());
                rxChangeVO.setFilePath(objRxChangeRequestType.getFilePath());*/
                /**
				 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page End
				 */
                rxChangeVO.setStatusObjId(objRxChangeRequestType.getStatusObjId());
                rxChangeVO.setStatus(objRxChangeRequestType.getStatus());
                rxChangeVO.setTypeOfRxChangeLst(objRxChangeRequestType.getTypeOfRxChange());
                rxChangeVO.setUserName(objRxChangeRequestType.getUserId());
                /**
				 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page Start
				 */
                if (null!=objRxChangeRequestType.getLstAttachment()) {
                	List<RecommDelvDocVO> arlDelvDocVO=new ArrayList<RecommDelvDocVO>();
                	for (Iterator iterator = objRxChangeRequestType.getLstAttachment().iterator(); iterator
							.hasNext();) {
						RxDelvDocType objRxDelvDocType = (RxDelvDocType) iterator.next();
						RecommDelvDocVO objRecommDelvDocVO=new RecommDelvDocVO();
						objRecommDelvDocVO.setDocData(objRxDelvDocType.getDocData());
						objRecommDelvDocVO.setDocPath(objRxDelvDocType.getDocPath());
						objRecommDelvDocVO.setDocTitle(objRxDelvDocType.getDocTitle());
						arlDelvDocVO.add(objRecommDelvDocVO);
					}
                	rxChangeVO.setLstAttachment(arlDelvDocVO);
				}
                rxChangeVO.setScreenName(objRxChangeRequestType.getScreenName());
                /**
				 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page End
				 */
			}

            result = objRxChangeServiceIntf.saveRxChangeDetails(rxChangeVO);
            RxChangeResourceLOG.debug("Rx Change request created : " + result);
        } catch (Exception ex) {
            result = RMDCommonConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return result;
    }

    @GET
    @Path(OMDConstants.RXCHANGE_MODEL_FOR_RX_TITLE)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModelsResponseType> getModelForRxTitle(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ModelsResponseType> lstModelResponse = new ArrayList<ModelsResponseType>();
		ModelsResponseType modelResponseType = null;
		String recomObjId = "";
		try {
			RxChangeSearchVO objRxChangeSearchVO = new RxChangeSearchVO();
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (null != queryParams
					&& queryParams.containsKey(OMDConstants.RECOM_ID)) {
				recomObjId = queryParams.getFirst(OMDConstants.RECOM_ID);
			}
			objRxChangeSearchVO.setRecomObjIdLst(new ArrayList<String>(Arrays
                    .asList(recomObjId.split(OMDConstants.COMMA))));

			List<ModelVO> modelVOLst = objRxChangeServiceIntf
					.getModelForRxTitle(objRxChangeSearchVO);
			if (RMDCommonUtility.isCollectionNotEmpty(modelVOLst)) {
				lstModelResponse = new ArrayList<ModelsResponseType>(
						modelVOLst.size());
				for (ModelVO modelVO : modelVOLst) {
					modelResponseType = new ModelsResponseType();
					modelResponseType.setModelFamily(modelVO.getModelFamily());
					modelResponseType
							.setModelGeneral(modelVO.getModelGeneral());
					modelResponseType.setModelName(modelVO.getModelName());
					modelResponseType.setModelObjId(modelVO.getModelObjId());
					lstModelResponse.add(modelResponseType);
				}
			}

		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		return lstModelResponse;
	}

	@POST
	@Path(OMDConstants.RXCHANGE_SAVE_UPDATE_ADMIN_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String saveUpdateRxChangeAdminDetails(
			final RxChangeAdminRequestType rxChangeAdminRequestType)
			throws RMDServiceException {
		String result = null;
		try {
			RxChangeAdminVO objRxChangeAdminVO = new RxChangeAdminVO();
			if (null != rxChangeAdminRequestType) {
			    objRxChangeAdminVO.setAdminEmailIdLst(rxChangeAdminRequestType.getAdminEmailIdLst());
			    objRxChangeAdminVO.setReViewerUserSeqId(rxChangeAdminRequestType.getReViewerUserSeqId());
				objRxChangeAdminVO.setUserId(rxChangeAdminRequestType
						.getUserId());
				objRxChangeAdminVO
						.setRxChangeRequestId(rxChangeAdminRequestType
								.getRxChangeRequestId());
				objRxChangeAdminVO
						.setObjId(rxChangeAdminRequestType.getObjId());
				objRxChangeAdminVO.setNewRxCreated(rxChangeAdminRequestType
						.getNewRxCreated());
				objRxChangeAdminVO
						.setAnyChangeinMaterial(rxChangeAdminRequestType
								.getAnyChangeinMaterial());
				objRxChangeAdminVO
						.setTriggerLogicChange(rxChangeAdminRequestType
								.getTriggerLogicChange());
				objRxChangeAdminVO
						.setUnfamiliarSystemChange(rxChangeAdminRequestType
								.getUnfamiliarSystemChange());
				objRxChangeAdminVO.setNoOfRxAttachment(rxChangeAdminRequestType
						.getNoOfRxAttachment());
				objRxChangeAdminVO.setRxsImpacted(rxChangeAdminRequestType
						.getRxsImpacted());
				objRxChangeAdminVO.setAcceptanceFlag(rxChangeAdminRequestType
						.getAcceptanceFlag());
				objRxChangeAdminVO.setCustomers(rxChangeAdminRequestType
						.getCustomers());
				objRxChangeAdminVO.setModelsImpacted(rxChangeAdminRequestType
						.getModelsImpacted());
				objRxChangeAdminVO.setReviewerNotes(rxChangeAdminRequestType
						.getReviewerNotes());
				objRxChangeAdminVO.setInternalNotes(rxChangeAdminRequestType
						.getInternalNotes());
				objRxChangeAdminVO.setRxChangeReasonsLst(rxChangeAdminRequestType
						.getRxChangeReasons());
				objRxChangeAdminVO
						.setTargetImplementationDate(rxChangeAdminRequestType
								.getTargetImplementationDate());
				objRxChangeAdminVO
				.setSaveAsDraft(rxChangeAdminRequestType
						.getSaveAsDraft());
				objRxChangeAdminVO.setSummaryOfChanges(rxChangeAdminRequestType
						.getSummaryOfChanges());
				objRxChangeAdminVO.setAdditionalReviewerLst(rxChangeAdminRequestType
						.getAdditionalReviewer());           
				result = objRxChangeServiceIntf
						.saveUpdateRxChangeAdminDetails(objRxChangeAdminVO);
			}
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return result;
	}

	@POST
	@Path(OMDConstants.RXCHANGE_ADMIN_DATA)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RxChangeAdminResponseType getRxChangeAdminData(
			final RxChangeOverviewRequestType rxChangeOverviewRequestType)
			throws RMDServiceException {
		RxChangeAdminResponseType rxChangeAdminResponseType = null;
		RxChangeAdminVO rxChangeAdminVO = null;
		try {
			RxChangeSearchVO objRxChangeSearchVO = new RxChangeSearchVO();
			objRxChangeSearchVO
					.setRxChangeProcObjId(rxChangeOverviewRequestType
							.getRxChangeProcObjId());
			objRxChangeSearchVO.setRxChangeReqObjId(rxChangeOverviewRequestType
					.getRxChangeReqObjId());
			rxChangeAdminVO = objRxChangeServiceIntf
					.getRxChangeAdminData(objRxChangeSearchVO);
			if (null != rxChangeAdminVO) {
				rxChangeAdminResponseType = new RxChangeAdminResponseType();
				rxChangeAdminResponseType.setRxChangeRequestId(rxChangeAdminVO
						.getRxChangeRequestId());
				rxChangeAdminResponseType.setObjId(rxChangeAdminResponseType
						.getObjId());
				rxChangeAdminResponseType.setNewRxCreated(rxChangeAdminVO
						.getNewRxCreated());
				rxChangeAdminResponseType
						.setAnyChangeinMaterial(rxChangeAdminVO
								.getAnyChangeinMaterial());
				rxChangeAdminResponseType.setTriggerLogicChange(rxChangeAdminVO
						.getTriggerLogicChange());
				rxChangeAdminResponseType
						.setUnfamiliarSystemChange(rxChangeAdminVO
								.getUnfamiliarSystemChange());
				rxChangeAdminResponseType.setNoOfRxAttachment(rxChangeAdminVO
						.getNoOfRxAttachment());
				rxChangeAdminResponseType.setSummaryOfChanges(rxChangeAdminVO
						.getSummaryOfChanges());
				rxChangeAdminResponseType.setStrCustomer(rxChangeAdminVO.getCustomer());
				rxChangeAdminResponseType.setStrModel(rxChangeAdminVO.getModel());
				rxChangeAdminResponseType.setRxList(rxChangeAdminVO.getRxList());
				rxChangeAdminResponseType.setAcceptanceFlag(rxChangeAdminVO
						.getAcceptanceFlag());
				rxChangeAdminResponseType.setReviewerNotes(rxChangeAdminVO
						.getReviewerNotes());
				rxChangeAdminResponseType.setInternalNotes(rxChangeAdminVO
						.getInternalNotes());
				rxChangeAdminResponseType.setRxChangeReasons(rxChangeAdminVO
						.getRxChangeReasons());
				rxChangeAdminResponseType
						.setTargetImplementationDate(rxChangeAdminVO
								.getTargetImplementationDate());
				rxChangeAdminResponseType.setAdditionalReviewer(rxChangeAdminVO
						.getAdditionalReviewer());
				
				List<RxChangeResponseType> rxchangeAuditInfoLst = new ArrayList<RxChangeResponseType>();
                for(RxChangeVO tempRxChangeVO : rxChangeAdminVO.getRxchangeAuditInfoLst()){
                    RxChangeResponseType tempRxChangeResponseType = new RxChangeResponseType();
                    tempRxChangeResponseType.setStatus(tempRxChangeVO.getStatus());
                    tempRxChangeResponseType.setRequestLoggedDate(tempRxChangeVO.getRequestLoggedDate());
                    tempRxChangeResponseType.setUserName(tempRxChangeVO.getUserName()); 
                    tempRxChangeResponseType.setNotes(tempRxChangeVO.getNotes());    
                    rxchangeAuditInfoLst.add(tempRxChangeResponseType);
                }
                rxChangeAdminResponseType.setRxchangeAuditInfoLst(rxchangeAuditInfoLst);
			}

			} catch (RMDServiceException rmdServiceException) {
				throw rmdServiceException;
			} catch (Exception ex) {
				RMDWebServiceErrorHandler.handleException(ex,
						omdResourceMessagesIntf);
			}

			return rxChangeAdminResponseType;
		}
	
	@GET
    @Path(OMDConstants.GET_RXCHANGE_ADMIN_USERS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<UserEOADetailsResponseType> getRxChangeAdminUsers() throws RMDServiceException{

        List<UserServiceVO> userResponse = new ArrayList<UserServiceVO>();
        List<UserEOADetailsResponseType> userDetailRespType = null;

        try{
            userResponse = objRxChangeServiceIntf.getRxChangeAdminUsers(); 
            if (null != userResponse) {
                userDetailRespType = new ArrayList<UserEOADetailsResponseType>(userResponse.size());
                for (UserServiceVO objUser : userResponse) {
                    UserEOADetailsResponseType userVO = new UserEOADetailsResponseType();
                    userVO.setUserId(String.valueOf(objUser.getGetUsrUsersSeqId()));
                    userVO.setFirstName(objUser.getStrFirstName());
                    userVO.setLastName(objUser.getStrLastName());
                    userVO.setEmailId(objUser.getStrEmail());
                    userDetailRespType.add(userVO);
                }
            }
            
            }
        catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        
        return userDetailRespType;
    }
	
	@GET
    @Path(OMDConstants.GET_RXCHANGE_AUDIT_TRIAL_INFO)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RxChangeResponseType> getRxChangeAuditTrailInfo(@Context final UriInfo ui)
            throws RMDServiceException {
        List<RxChangeVO> rxChangeVOLst = new ArrayList<RxChangeVO>();
        List<RxChangeResponseType> rxchangeAuditInfoLst = new ArrayList<RxChangeResponseType>();
        String rxChngObjid = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (null != queryParams
                    && queryParams.containsKey(OMDConstants.RX_CHANGE_REQ_OBJ_ID)) {
                rxChngObjid = queryParams.getFirst(OMDConstants.RX_CHANGE_REQ_OBJ_ID);
            }
            
            rxChangeVOLst = objRxChangeServiceIntf.getRxChangeAuditTrailInfo(rxChngObjid);
            
            for(RxChangeVO tempRxChangeVO : rxChangeVOLst){
                RxChangeResponseType tempRxChangeResponseType = new RxChangeResponseType();
                tempRxChangeResponseType.setStatus(tempRxChangeVO.getStatus());
                tempRxChangeResponseType.setRequestLoggedDate(tempRxChangeVO.getRequestLoggedDate());
                tempRxChangeResponseType.setUserName(tempRxChangeVO.getUserName()); 
                tempRxChangeResponseType.setNotes(tempRxChangeVO.getNotes());    
                rxchangeAuditInfoLst.add(tempRxChangeResponseType);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return rxchangeAuditInfoLst;   
    }
	
	/**
     * @Author:
     * @param:
     * @return: List<UserServiceVO>
     * @throws:GenericAjaxException,RMDWebException
     * @Description: This method is used for Sending Escalations.
     */
    @GET
    @Path(OMDConstants.SEND_RX_CHANGE_ESCALATION)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String sendRxChangeEscalation() throws Exception {        
        String escalationData = "";

        try{
             objRxChangeServiceIntf.sendRxChangeEscalation(escalationData);
            }catch (Exception ex) {
                escalationData =  RMDCommonConstants.FAILURE;
                RMDWebServiceErrorHandler.handleException(ex,
                        omdResourceMessagesIntf);
            }
        return RMDCommonConstants.SUCCESS;
    }
}