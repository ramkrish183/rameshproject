package com.ge.trans.rmd.services.rxtranslation.resource;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.rxtranslation.service.intf.RxTranslationServiceIntf;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransHistVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransTaskDetailVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.assets.valueobjects.ListWrapperResponseType;
import com.ge.trans.rmd.services.rxtranslation.valueobjects.MultilingualRxFilterRequestType;
import com.ge.trans.rmd.services.rxtranslation.valueobjects.MultilingualRxResponseType;
import com.ge.trans.rmd.services.rxtranslation.valueobjects.TranslationLanguageStatus;
import com.ge.trans.rmd.services.solutions.valueobjects.RxTransDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.RxTransHistRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.RxTransTaskDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDocType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.RX_TRANSLATION_SERVICE)
@Component
public class RxTranslationResource extends BaseResource{
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;
	@Autowired
	RxTranslationServiceIntf rxTranslationService;
	
	@GET
    @Path(OMDConstants.GET_RX_TRANSLATION_LANGUAGE)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ListWrapperResponseType getTranslationLanguage(@Context final UriInfo uriParam) throws RMDServiceException{
		List<String> transLanguages = null;
		ListWrapperResponseType langs = new ListWrapperResponseType();
		try{
			transLanguages = rxTranslationService.getTranslationLanguage();
			langs.setElements(transLanguages);
		} catch(Exception e){
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}		
		return langs;
		
	}
	
	@GET
    @Path(OMDConstants.GET_RX_TRANSLATION_LAST_MODIFIED_BY)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ListWrapperResponseType getTranslationLastModifiedBy(@Context final UriInfo uriParam) throws RMDServiceException{
		List<String> transLastModifiedByList = null;
		ListWrapperResponseType lastModList = new ListWrapperResponseType();
		try{
			transLastModifiedByList = rxTranslationService.getTranslationLastModifiedBy();
			lastModList.setElements(transLastModifiedByList);
		} catch(Exception e){
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return lastModList;
		
	}
	
	@POST
    @Path(OMDConstants.GET_RX_TRANSLATION_LIST)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<MultilingualRxResponseType> getFilteredRx(MultilingualRxFilterRequestType objMultiLingFilterRequest) {
		List<MultilingualRxResponseType> filteredRxResponseList = new ArrayList<MultilingualRxResponseType>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(OMDConstants.DATE_FORMAT);
		if(objMultiLingFilterRequest != null){
			try{
				MultiLingualFilterVO multiLingualFilter = new MultiLingualFilterVO();
				multiLingualFilter.setTitle(objMultiLingFilterRequest.getTitle());
				multiLingualFilter.setModel(objMultiLingFilterRequest.getModel());
				multiLingualFilter.setTranslationStatus(objMultiLingFilterRequest.getTranslationStatus());
				multiLingualFilter.setType(objMultiLingFilterRequest.getType());
				multiLingualFilter.setLanguage(objMultiLingFilterRequest.getLanguage());
				multiLingualFilter.setLastModifiedBy(objMultiLingFilterRequest.getLastModifiedBy());
				XMLGregorianCalendar lastModifiedOn = objMultiLingFilterRequest.getLastModifiedOn();
				if(lastModifiedOn != null){
					multiLingualFilter.setLastModifiedOn(lastModifiedOn.toGregorianCalendar().getTime());
				}
				XMLGregorianCalendar lastModifiedOnTo = objMultiLingFilterRequest.getLastModifiedOnTo();
				if(lastModifiedOnTo != null){
					multiLingualFilter.setLastModifiedOnTo(lastModifiedOnTo.toGregorianCalendar().getTime());
				}
				
				multiLingualFilter.setDefaultLoad(objMultiLingFilterRequest.isDefaultLoad());
				List<MultiLingualRxVO> filteredRxlist = rxTranslationService.getTranslationRxList(multiLingualFilter);
				if(RMDCommonUtility.isCollectionNotEmpty(filteredRxlist)){
					MultilingualRxResponseType rxResponse = null;
					String appTimeZone=getRequestHeader("appTime");
					for(MultiLingualRxVO filteredRx : filteredRxlist){
						rxResponse = new MultilingualRxResponseType();
						rxResponse.setTitle(filteredRx.getTitle());
						rxResponse.setRxStatus(filteredRx.getRxStatus());
						rxResponse.setType(filteredRx.getType());
						if(filteredRx.getModels() != null)
							rxResponse.getModels().addAll(filteredRx.getModels());
						rxResponse.setLastModifiedBy(filteredRx.getLastModifiedBy());
						if(appTimeZone == null)
							appTimeZone = RMDCommonConstants.DateConstants.EST_US;
						rxResponse.setLastModifiedOn(toXMLGregorianCalendar(filteredRx.getLastModifiedOn(), appTimeZone));
						rxResponse.setApprovedBy(filteredRx.getApprovedBy());
						rxResponse.setApprovedOn(toXMLGregorianCalendar(filteredRx.getApprovedOn(), appTimeZone));
						rxResponse.setRxObjid(filteredRx.getRxObjid());
						Map<String, String> translatedLanguages = filteredRx.getTranslatedLanguages();
						if(translatedLanguages != null) {
							for (Map.Entry<String,String> entry : translatedLanguages.entrySet()) {
								TranslationLanguageStatus langStatus = new TranslationLanguageStatus();
								langStatus.setLanguage(entry.getKey());
								langStatus.setStatus(entry.getValue());	
								rxResponse.getTranslatedLanguages().add(langStatus);
							}
						}
						filteredRxResponseList.add(rxResponse);
					}
				}
				
			} catch(Exception e){
				RMDWebServiceErrorHandler.handleException(e,
						omdResourceMessagesIntf);
			}
		}
		return filteredRxResponseList;
	}
	public XMLGregorianCalendar toXMLGregorianCalendar(Date date, String timezone){
		XMLGregorianCalendar xmlCalendar = null;
		if(date != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.setTimeZone(TimeZone.getTimeZone(timezone));

			try {
				xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			} catch (Exception ex) {
				RMDWebServiceErrorHandler.handleException(ex,
						omdResourceMessagesIntf);
			}
		}
		return xmlCalendar;
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
    @Path(OMDConstants.GET_RX_TRANS_DETAIL)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RxTransDetailType getRxTransDetail(            
            @Context UriInfo uriParam) throws RMDServiceException {
        MultivaluedMap<String, String> queryParams = null;
        String rxObjid=RMDCommonConstants.EMPTY_STRING;
        String language=RMDCommonConstants.EMPTY_STRING;
        RxTransDetailType objRxTransDetailType=null;
        RxTransDetailVO objRxTransDetailVO=null;
        RxTransHistRequestType objHistRequestType=null;
        RxTransTaskDetailType objRxTransTaskDetailType=null;
        try {
            LOG.debug("Start getSolutionDetails Webservice method");
            queryParams = uriParam.getQueryParameters();
            if (queryParams.containsKey(OMDConstants.RX_OBJ_ID)) {
            	rxObjid = queryParams.getFirst(OMDConstants.RX_OBJ_ID);
            }
            if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
            	language = queryParams.getFirst(OMDConstants.LANGUAGE);
            }
            objRxTransDetailVO = rxTranslationService
                    .getRxTransDetail(rxObjid,language); 
            if(null!=objRxTransDetailVO){
            	objRxTransDetailType=new RxTransDetailType();
            	objRxTransDetailType.setRxObjid(objRxTransDetailVO.getRxObjid());
            	objRxTransDetailType.setRxTitle(objRxTransDetailVO.getRxTitle());
            	objRxTransDetailType.setRxTransTitle(objRxTransDetailVO.getRxTransTitle());
            	objRxTransDetailType.setApprovedBy(objRxTransDetailVO.getApprovedBy());
            	objRxTransDetailType.setApprovedOn(objRxTransDetailVO.getApprovedOn());
            	objRxTransDetailType.setLastModifiedBy(objRxTransDetailVO.getLastModifiedBy());
            	objRxTransDetailType.setTransStatus(objRxTransDetailVO.getTransStatus());
            	objRxTransDetailType.setRxStatus(objRxTransDetailVO.getRxStatus());
            	objRxTransDetailType.setLanguage(objRxTransDetailVO.getLanguage());
            	objRxTransDetailType.setLastModifiedOn(objRxTransDetailVO.getLastModifiedOn());
            	objRxTransDetailType.setStrUrgRepair(objRxTransDetailVO.getStrUrgRepair());
            	objRxTransDetailType.setStrEstmTimeRepair(objRxTransDetailVO.getStrEstmTimeRepair());
            	objRxTransDetailType.setStrSelAssetImp(objRxTransDetailVO.getStrSelAssetImp());
            	objRxTransDetailType.setRxDescription(objRxTransDetailVO.getRxDescription());
            	objRxTransDetailType.setTransDescription(objRxTransDetailVO.getTransDescription());
            	List<RxTransHistVO> arlTransHistVO=objRxTransDetailVO.getArlRxTransHistVO();
            	if(null!=arlTransHistVO&&!arlTransHistVO.isEmpty()){
					for (Iterator iterator = arlTransHistVO.iterator(); iterator
							.hasNext();) {
						RxTransHistVO rxTransHistVO = (RxTransHistVO) iterator
								.next();
						objHistRequestType=new RxTransHistRequestType();
						objHistRequestType.setStrCreatedBy(rxTransHistVO
								.getStrCreatedBy());
						objHistRequestType.setStrDateCreated(rxTransHistVO
								.getStrDateCreated());
						objHistRequestType.setStrRevisionNotes(rxTransHistVO
								.getStrRevisionNotes());
						objRxTransDetailType.getRxTransHist().add(objHistRequestType);
					}
            	}
            	List<RxTransTaskDetailVO> arlRxTransTaskDetailVO=objRxTransDetailVO.getArlRxTransTaskDetailVO();
            	if(null!=arlRxTransTaskDetailVO&&!arlRxTransTaskDetailVO.isEmpty()){
					for (Iterator iterator = arlRxTransTaskDetailVO.iterator(); iterator
							.hasNext();) {
						RxTransTaskDetailVO objRxTransTaskDetailVO = (RxTransTaskDetailVO) iterator
								.next();
						objRxTransTaskDetailType=new RxTransTaskDetailType();
						objRxTransTaskDetailType.setStrTaskObjID(objRxTransTaskDetailVO.getStrTaskObjID());
						objRxTransTaskDetailType.setStrTaskId(objRxTransTaskDetailVO.getStrTaskId());
						objRxTransTaskDetailType.setStrTaskDesc(objRxTransTaskDetailVO.getStrTaskDesc());
						objRxTransTaskDetailType.setStrTransTaskDesc(objRxTransTaskDetailVO.getStrTransTaskDesc());						
						objRxTransTaskDetailType.setLsl(objRxTransTaskDetailVO.getLsl());
						objRxTransTaskDetailType.setUsl(objRxTransTaskDetailVO.getUsl());
						objRxTransTaskDetailType.setTarget(objRxTransTaskDetailVO.getTarget());
						List<AddEditRxTaskDocVO> taskDocVo=objRxTransTaskDetailVO.getAddEditRxTaskDocVO();
						if (null != taskDocVo && !taskDocVo.isEmpty()) {
							List<TaskDocType> arlTaskDocType = new ArrayList<TaskDocType>();
							for (AddEditRxTaskDocVO objTaskDoc : taskDocVo) {
								TaskDocType objTaskDocType = new TaskDocType();
								objTaskDocType.setDocID(objTaskDoc
										.getStrTaskDocSeqId());
								objTaskDocType.setDocData(objTaskDoc
										.getStrDocData());
								objTaskDocType.setDocPath(objTaskDoc
										.getStrDocPath());
								objTaskDocType.setDocTitle(objTaskDoc
										.getStrDocTitle());
								objTaskDocType.setDelete(objTaskDoc
										.getStrDelete());
								arlTaskDocType.add(objTaskDocType);
							}
							objRxTransTaskDetailType.setTaskDocType(arlTaskDocType);
						}
						
						List<AddEditRxTaskDocVO> transTaskDocVo=objRxTransTaskDetailVO.getRxTransTaskDocVO();
						if (null != transTaskDocVo && !transTaskDocVo.isEmpty()) {
							List<TaskDocType> arlTransTaskDocType = new ArrayList<TaskDocType>();
							for (AddEditRxTaskDocVO objTaskDoc : transTaskDocVo) {
								TaskDocType objTaskDocType = new TaskDocType();
								objTaskDocType.setDocID(objTaskDoc
										.getStrTaskDocSeqId());
								objTaskDocType.setDocData(objTaskDoc
										.getStrDocData());
								objTaskDocType.setDocPath(objTaskDoc
										.getStrDocPath());
								objTaskDocType.setDocTitle(objTaskDoc
										.getStrDocTitle());
								objTaskDocType.setDelete(objTaskDoc
										.getStrDelete());
								arlTransTaskDocType.add(objTaskDocType);
							}
							objRxTransTaskDetailType.setTransTaskDocType(arlTransTaskDocType);
						}						
						objRxTransDetailType.getRxTransTask().add(objRxTransTaskDetailType);	
						
					}
            	}
            }
            
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        }
        return objRxTransDetailType;
    }
    
    @POST
    @Path(OMDConstants.SAVE_RX_TRANS_DETAIL)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveRxTranslation(RxTransDetailType objRxTransDetilType) {
    	String updatedRxId = null;
    	try{
    		RxTransDetailVO rxTransDetailVO = null;
    		if(null != objRxTransDetilType){
    			String strUserID = getRequestHeader(OMDConstants.STR_USERNAME);
    			rxTransDetailVO = new RxTransDetailVO();
    			rxTransDetailVO.setRxObjid(objRxTransDetilType.getRxObjid());
    			rxTransDetailVO.setRxTitle(objRxTransDetilType.getRxTitle());
    			rxTransDetailVO.setRxTransTitle(objRxTransDetilType.getRxTransTitle());
    			rxTransDetailVO.setApprovedBy(strUserID);
    			rxTransDetailVO.setApprovedOn(objRxTransDetilType.getApprovedOn());
    			rxTransDetailVO.setLastModifiedBy(strUserID);
    			rxTransDetailVO.setTransStatus(objRxTransDetilType.getTransStatus());
    			rxTransDetailVO.setRevisionNote(objRxTransDetilType.getRevisionNote());
    			rxTransDetailVO.setTransNewStatus(objRxTransDetilType.getNewTransStatus());
    			rxTransDetailVO.setLanguage(objRxTransDetilType.getLanguage());
    			rxTransDetailVO.setTransDescription(objRxTransDetilType.getTransDescription());
    			List<RxTransTaskDetailType> translatedTasks = objRxTransDetilType.getRxTransTask();
    			List<RxTransTaskDetailVO> translatedTasksList = new ArrayList<RxTransTaskDetailVO>();
    			if(null != translatedTasks){
    				for(RxTransTaskDetailType translatedTask : translatedTasks){
    					RxTransTaskDetailVO rxTaskDetail = new RxTransTaskDetailVO();
    					rxTaskDetail.setStrTaskObjID(translatedTask.getStrTaskObjID());
    					rxTaskDetail.setStrTaskId(translatedTask.getStrTaskId());
    					rxTaskDetail.setStrTaskDesc(translatedTask.getStrTaskDesc());
    					rxTaskDetail.setStrTransTaskDesc(translatedTask.getStrTransTaskDesc());
    					rxTaskDetail.setStrLanguage(translatedTask.getStrLanguage());
						List<TaskDocType> taskDocTypes = translatedTask.getTransTaskDocType();
						if (null != taskDocTypes && !taskDocTypes.isEmpty()) {
							List<AddEditRxTaskDocVO> arlTaskDoc = new ArrayList<AddEditRxTaskDocVO>();
							for (TaskDocType objTaskDocType : taskDocTypes) {
								AddEditRxTaskDocVO objTaskDoc = new AddEditRxTaskDocVO();
								objTaskDoc.setStrTaskDocSeqId(objTaskDocType
										.getDocID());
								objTaskDoc.setStrDocData(objTaskDocType
										.getDocData());
								objTaskDoc.setStrDocPath(objTaskDocType
										.getDocPath());
								objTaskDoc.setStrDocTitle(objTaskDocType
										.getDocTitle());
								objTaskDoc.setStrDelete(objTaskDocType
										.getDelete());	
								arlTaskDoc.add(objTaskDoc);
							}
							rxTaskDetail.setRxTransTaskDocVO(arlTaskDoc);
						}
						translatedTasksList.add(rxTaskDetail);
    				}
    				rxTransDetailVO.setArlRxTransTaskDetailVO(translatedTasksList);
    			}
    			updatedRxId = rxTranslationService.saveRxTranslation(rxTransDetailVO);
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
    	} catch (Exception e) {
    		RMDWebServiceErrorHandler.handleException(e,
    				omdResourceMessagesIntf);
    	}
    	return updatedRxId;
    }
    
    @POST
    @Path(OMDConstants.PREVIEW_PDF)
    @Produces( MediaType.APPLICATION_OCTET_STREAM )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response previewRxPdf(RxTransDetailType objRxTransDetilType)
            throws RMDServiceException {
    	try{
    		RxTransDetailVO rxTransDetailVO = null;
    		if(null != objRxTransDetilType){
    			rxTransDetailVO = new RxTransDetailVO();
    			rxTransDetailVO.setRxObjid(objRxTransDetilType.getRxObjid());
    			rxTransDetailVO.setRxTitle(objRxTransDetilType.getRxTitle());
    			rxTransDetailVO.setRxTransTitle(objRxTransDetilType.getRxTransTitle());
    			rxTransDetailVO.setApprovedBy(objRxTransDetilType.getApprovedBy());
    			rxTransDetailVO.setApprovedOn(objRxTransDetilType.getApprovedOn());
    			rxTransDetailVO.setLastModifiedBy(objRxTransDetilType.getLastModifiedBy());
    			rxTransDetailVO.setTransStatus(objRxTransDetilType.getTransStatus());
    			rxTransDetailVO.setRevisionNote(objRxTransDetilType.getRevisionNote());
    			rxTransDetailVO.setTransNewStatus(objRxTransDetilType.getNewTransStatus());
    			rxTransDetailVO.setLanguage(objRxTransDetilType.getLanguage());
    			rxTransDetailVO.setStrUrgRepair(objRxTransDetilType.getStrUrgRepair());
    			rxTransDetailVO.setStrEstmTimeRepair(objRxTransDetilType.getStrEstmTimeRepair());
    			rxTransDetailVO.setStrSelAssetImp(objRxTransDetilType.getStrSelAssetImp());
    			List<RxTransTaskDetailType> translatedTasks = objRxTransDetilType.getRxTransTask();
    			List<RxTransTaskDetailVO> translatedTasksList = new ArrayList<RxTransTaskDetailVO>();
    			if(null != translatedTasks){
    				for(RxTransTaskDetailType translatedTask : translatedTasks){
    					RxTransTaskDetailVO rxTaskDetail = new RxTransTaskDetailVO();
    					rxTaskDetail.setStrTaskObjID(translatedTask.getStrTaskObjID());
    					rxTaskDetail.setStrTaskId(translatedTask.getStrTaskId());
    					rxTaskDetail.setStrTaskDesc(translatedTask.getStrTaskDesc());
    					rxTaskDetail.setStrTransTaskDesc(translatedTask.getStrTransTaskDesc());
    					rxTaskDetail.setStrLanguage(translatedTask.getStrLanguage());
    					rxTaskDetail.setUsl(translatedTask.getUsl());
    					rxTaskDetail.setLsl(translatedTask.getLsl());
    					rxTaskDetail.setTarget(translatedTask.getTarget());
    					List<TaskDocType> taskDocTypes = translatedTask.getTransTaskDocType();
    					if (null != taskDocTypes && !taskDocTypes.isEmpty()) {
    						List<AddEditRxTaskDocVO> arlTaskDoc = new ArrayList<AddEditRxTaskDocVO>();
    						for (TaskDocType objTaskDocType : taskDocTypes) {
    							AddEditRxTaskDocVO objTaskDoc = new AddEditRxTaskDocVO();
    							objTaskDoc.setStrTaskDocSeqId(objTaskDocType
    									.getDocID());
    							objTaskDoc.setStrDocData(objTaskDocType
    									.getDocData());
    							objTaskDoc.setStrDocPath(objTaskDocType
    									.getDocPath());
    							objTaskDoc.setStrDocTitle(objTaskDocType
    									.getDocTitle());
    							objTaskDoc.setStrDelete(objTaskDocType
    									.getDelete());	
    							arlTaskDoc.add(objTaskDoc);
    						}
    						rxTaskDetail.setRxTransTaskDocVO(arlTaskDoc);
    					}
    					translatedTasksList.add(rxTaskDetail);
    				}
    				rxTransDetailVO.setArlRxTransTaskDetailVO(translatedTasksList);
    			}
    			String filePath = rxTranslationService
    					.previewRxPdf(rxTransDetailVO);
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
    		}else {
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
    	}catch (OMDInValidInputException objOMDInValidInputException) {
    		throw objOMDInValidInputException;
    	} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
    		throw objOMDNoResultFoundException;
    	} catch (RMDServiceException rmdServiceException) {
    		throw rmdServiceException;
    	} catch (Exception e) {
    		LOG.debug("Exception occured in prevewPDF()"+e);
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
}
