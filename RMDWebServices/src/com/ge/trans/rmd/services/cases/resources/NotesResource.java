/**
 * ============================================================
 * Classification: GE Confidential
 * File : NotesResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.resources
 * Author : iGATE-Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Aug 3 2011
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
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.AddNotesEoaServiceintf;
import com.ge.trans.eoa.services.asset.service.intf.AssetEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.intf.AddNotesServiceIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindNotesEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesSearchResultVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
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
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.ControllerResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.CreatorResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.StickyNotesDetailsResponseType;
import com.ge.trans.rmd.services.notes.valueobjects.FindNotesResponseType;
import com.ge.trans.rmd.services.notes.valueobjects.NotesInfo;
import com.ge.trans.rmd.services.notes.valueobjects.NotesRequestType;
import com.ge.trans.rmd.services.notes.valueobjects.NotesResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 *
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Aug 3 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as noteservice Web services and provide the Case
 *              related funtionalities
 * @History :
 *
 ******************************************************************************/
@Path(OMDConstants.NOTESSERVICE)
@Component
public class NotesResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotesResource.class);
    
    @Autowired
    private FindNotesEoaServiceIntf objFindNotesEoaServiceIntf;
    @Autowired
    private AddNotesServiceIntf objAddNotesIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    private AssetEoaServiceIntf objAssetEoaServiceIntf;
    @Autowired
    private AddNotesEoaServiceintf addNotesEoaServiceintf;
    /**
     * This Method is used for retrieving notes based on the inputs passed
     *
     * @param uriParam
     * @return list of notes details
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETNOTES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<NotesResponseType> getNotes(@Context UriInfo uriParam)
            throws RMDServiceException {
        FindNotesServiceVO objfindNotesServiceVO = null;
        MultivaluedMap<String, String> queryParams = null;
        final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(RMDServiceConstants.DATE_FORMAT2);
        NotesResponseType objNotesResponseType = null;
        List<FindNotesSearchResultVO> lstNotesResult = null;
        List<NotesResponseType> lstNotesResponse = null;
        FindNotesSearchResultVO findNotesSearchResultVO = null;
        try {
            queryParams = uriParam.getQueryParameters();
            objfindNotesServiceVO = new FindNotesServiceVO();
                int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE,
                    RMDCommonConstants.ALPHABETS, 
                    RMDCommonConstants.VALID_DATE,
                    RMDCommonConstants.VALID_DATE,
                    RMDCommonConstants.AlPHA_NUMERIC,
                    RMDCommonConstants.AlPHA_NUMERIC
                    };
            if(AppSecUtil.validateWebServiceInput(queryParams,
                    RMDServiceConstants.DATE_FORMAT2, methodConstants, OMDConstants.ASSET_NUMBER_FROM, 
                    OMDConstants.ASSET_NUMBER_TO,OMDConstants.CREATED_BY,OMDConstants.NOTE_TYPE,OMDConstants.CREATION_DATE_FROM,
                    OMDConstants.CREATION_DATE_TO,OMDConstants.GROUP_NAME,OMDConstants.CUSTOMER_ID)){
            // copying the query params
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER_FROM)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetFrom(queryParams.getFirst(OMDConstants.ASSET_NUMBER_FROM));
            } else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetFrom(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.ASSET_NUMBER_TO)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetTo(queryParams.getFirst(OMDConstants.ASSET_NUMBER_TO));
            } else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetTo(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.CREATED_BY)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setCreator(queryParams.getFirst(OMDConstants.CREATED_BY));
            } else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setCreator(OMDConstants.SELECT);
            }
            if (queryParams.containsKey(OMDConstants.NOTE_TYPE)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setNoteType(queryParams.getFirst(OMDConstants.NOTE_TYPE));
            } else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setNoteType(OMDConstants.ALL);
            }
            if (queryParams.containsKey(OMDConstants.CREATION_DATE_FROM)) {

                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setDateFrom(
                        (SDF_FORMAT.parse(queryParams
                                .getFirst(OMDConstants.CREATION_DATE_FROM))));
            }
            if (queryParams.containsKey(OMDConstants.CREATION_DATE_TO)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setDateFrom(
                        (SDF_FORMAT.parse(queryParams
                                .getFirst(OMDConstants.CREATION_DATE_TO))));
            }
            if (queryParams.containsKey(OMDConstants.KEYWORD)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setKeyWords(queryParams.getFirst(OMDConstants.KEYWORD));
            } else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setKeyWords(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.GROUP_NAME)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetGroupName(queryParams
                        .getFirst(OMDConstants.GROUP_NAME));
            } 
            else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setAssetGroupName(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setCustomerID(queryParams
                        .getFirst(OMDConstants.CUSTOMER_ID));
            } 
            else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setCustomerID(OMDConstants.EMPTY_STRING);
            }
            if (queryParams.containsKey(OMDConstants.IS_NON_GPOC_USER)) {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setIsNonGPOCUser(queryParams
                        .getFirst(OMDConstants.IS_NON_GPOC_USER));
            }
            else {
                objfindNotesServiceVO.getFindNotesSearchCriteriaVO()
                .setIsNonGPOCUser(OMDConstants.EMPTY_STRING);
            }
            
            objfindNotesServiceVO
            .setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
            objfindNotesServiceVO
            .setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));

            objFindNotesEoaServiceIntf.getNotes(objfindNotesServiceVO);
                lstNotesResult = objfindNotesServiceVO.getNotesSearchList();
                if (RMDCommonUtility.isCollectionNotEmpty(lstNotesResult)) {
                    lstNotesResponse = new ArrayList<NotesResponseType>(lstNotesResult.size());
                    for (final Iterator<FindNotesSearchResultVO> iteratorFindNotesSearch = lstNotesResult.iterator(); iteratorFindNotesSearch
                            .hasNext();) {
                        findNotesSearchResultVO = (FindNotesSearchResultVO) iteratorFindNotesSearch
                                .next();
                        objNotesResponseType = new NotesResponseType();

                        CMBeanUtility.copyFndNoteSrchResultVOToNoteResType(findNotesSearchResultVO, objNotesResponseType);
                        /**Added By Rohini For FleetviewBasic get notes Start:*/
                        objNotesResponseType.setAssetGroupName(findNotesSearchResultVO.getAssetGroupName());
                        objNotesResponseType.setCustomerId(findNotesSearchResultVO.getCustomerId());
                        /**Added By Rohini For FleetviewBasic get notes End:*/
                        
                        /**Added By Veera For AssetOverview add notes Start:*/
                        objNotesResponseType.setNoteDescription(findNotesSearchResultVO.getNotes());
                        /**Added By Veera For AssetOverview add notes End:*/
                        objNotesResponseType.setCreationDate(findNotesSearchResultVO.getDate());
                        lstNotesResponse.add(objNotesResponseType);
                        
                        findNotesSearchResultVO = null;
                        objNotesResponseType = null;
                    }
                } /*else {
                     throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                }*/
                
            }     /*else{ 
                  throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
                   
              }*/
        } catch (Exception ex) {
        	
        	//RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        } finally {
            lstNotesResult = null;
            findNotesSearchResultVO = null;
            objNotesResponseType = null;
        }

        return lstNotesResponse;
    }

    /**
     * This method is used for Adding new note from the inputs passed
     * 
     * @param objNotesRequestType
     * @throws RMDServiceException
     */
    // Need to check as the BaseVO as its attributes need to copy there
    @PUT
    @Path(OMDConstants.ADDNOTES)
    @Consumes(MediaType.APPLICATION_XML)
    public void addNotes( final NotesRequestType objNotesRequestType)
            throws RMDServiceException {
        AddNotesEoaServiceVO objAddNotesServiceVO = null;
        NotesInfo objNotesInfo = null;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {

            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);
            if (null == objNotesRequestType) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            } else if(validateAddNotes(objNotesRequestType)){
                objNotesInfo = objNotesRequestType.getNotesInfo();
                objAddNotesServiceVO = new AddNotesEoaServiceVO();
                BeanUtility.copyBeanProperty(objNotesRequestType,objAddNotesServiceVO);
                BeanUtility.copyBeanProperty(objNotesInfo, objAddNotesServiceVO);
                objAddNotesServiceVO.setStrLanguage(strLanguage);
                objAddNotesServiceVO.setStrUserLanguage(strUserLanguage);
                objAddNotesServiceVO.setStrUserName(strUserID);
                /**Added By Veera For AssetOverview add notes Start:*/
                objAddNotesServiceVO.setNotes(objNotesRequestType.getNotes());
                /**Added By Veera For AssetOverview add notes End:*/
                objAssetEoaServiceIntf.saveNotes(objAddNotesServiceVO);
                
            }else{
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
        
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }

    }

    /**
     * This method is used for editing the Note based on the given inputs
     *
     * @param objNotesRequestType
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.EDITNOTES)
    @Consumes(MediaType.APPLICATION_XML)
    public void editNotes(NotesRequestType objNotesRequestType)
            throws RMDServiceException {
        AddNotesServiceVO objAddNotesServiceVO = null;
        NotesInfo objNotesInfo = null;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);

            if (null == objNotesRequestType)  {
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                        .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                new String[] {},
                                BeanUtility
                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }else{
                objNotesInfo = objNotesRequestType.getNotesInfo();
                objAddNotesServiceVO = new AddNotesServiceVO();
                BeanUtility.copyBeanProperty(objNotesRequestType,objAddNotesServiceVO);
                BeanUtility.copyBeanProperty(objNotesInfo, objAddNotesServiceVO);
                objAddNotesServiceVO.setStrLanguage(strLanguage);
                objAddNotesServiceVO.setStrUserLanguage(strUserLanguage);
                objAddNotesServiceVO.setStrUserName(strUserID);
                objAddNotesIntf.overwriteNotes(objAddNotesServiceVO);
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

    }

    /**
     * This method is used for deleting the note based on the given inputs
     *
     * @param objNotesRequestType
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.DELETENOTES)
    @Consumes(MediaType.APPLICATION_XML)
    public void deletenote( final NotesRequestType objNotesRequestType)
            throws RMDServiceException {
        AddNotesServiceVO objAddNotesServiceVO = null;
        NotesInfo objNotesInfo = null;
        String strLanguage = null;
        String strUserLanguage = null;
        String strUserID = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            strUserLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            strUserID = getRequestHeader(OMDConstants.USERID);
            if (null == objNotesRequestType)  {
                throw new OMDInValidInputException(
                        BeanUtility
                                .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                        .getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                                new String[] {},
                                BeanUtility
                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }else{
                objNotesInfo = objNotesRequestType.getNotesInfo();
                objAddNotesServiceVO = new AddNotesServiceVO();
                BeanUtility.copyBeanProperty(objNotesRequestType,objAddNotesServiceVO);
                BeanUtility.copyBeanProperty(objNotesInfo, objAddNotesServiceVO);

                objAddNotesServiceVO.setStrLanguage(strLanguage);
                objAddNotesServiceVO.setStrUserLanguage(strUserLanguage);
                objAddNotesServiceVO.setStrUserName(strUserID);
                objAddNotesIntf.deleteFleetRemark(objAddNotesServiceVO);
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
    }
    
private static boolean validateAddNotes(NotesRequestType obj){
        
        if(null != obj.getNotes() && !obj.getNotes().isEmpty()){
            if(!AppSecUtil.checkEncode(obj.getNotes())||RMDCommonUtility.isSpecialCharactersFound(obj.getNotes())){
                return false;
            }
        }
        
        if(null != obj.getSticky() && !obj.getSticky().isEmpty()){
            if(!AppSecUtil.checkAlphabets(obj.getSticky())){
                return false;
            }
        }
        
        if(null != obj.getFromAsset() && !obj.getFromAsset().isEmpty()){
            if(!AppSecUtil.checkAlphaNumericUnderscore(obj.getFromAsset())){
                return false;
            }
        }
        
        if(null != obj.getToAsset() && !obj.getToAsset().isEmpty()){
            if(!AppSecUtil.checkAlphaNumericUnderscore(obj.getToAsset())){
                return false;
            }
        }
        
        if(null != obj.getAssetGrpName() && !obj.getAssetGrpName().isEmpty()){
            if(!AppSecUtil.checkAlphaNumeric(obj.getAssetGrpName())){
                return false;
            }
        }
        
        
        return true;
    }

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of controllers.
     * 
     */
    @GET
    @Path(OMDConstants.GET_ALL_CONTROLLERS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ControllerResponseType> getAllControllers()
        throws RMDServiceException {
        
        final List<ControllerResponseType> listControllerResponse = new ArrayList<ControllerResponseType>();;
        List<ControllerListVO> arlController = null;
        ControllerResponseType objCtontrollerResponse = null;
        try{
            
            arlController=addNotesEoaServiceintf.getAllControllers();
            LOG.debug("Controller List retrieved from RMD Service ");
            if (RMDCommonUtility.isCollectionNotEmpty(arlController)) {
                for (final Iterator iterator = arlController.iterator(); iterator
                .hasNext();) {
                    final ControllerListVO objControllerVO = (ControllerListVO) iterator.next();
                    objCtontrollerResponse = new ControllerResponseType();
                    objCtontrollerResponse.setControllerId(objControllerVO.getControllerId());
                    objCtontrollerResponse.setControllerName(objControllerVO.getControllerName());
                    listControllerResponse.add(objCtontrollerResponse);
                }
            }
    
           /* else { 
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

            LOG.debug("Arraylist of Controller Response " + listControllerResponse);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return listControllerResponse;
    }
    
    /**
     * @Author :
     * @return :
     * @param :
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of Creators.
     * 
     */
    @GET
    @Path(OMDConstants.GET_ALL_CREATORS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public CreatorResponseType getNotesCreatersList()
        throws RMDServiceException {
        
        final CreatorResponseType objCreatorResponseType = new CreatorResponseType();
        List<String> arlCreatorList = null;
        ControllerResponseType objCtontrollerResponse = null;
        try{
            
            arlCreatorList=addNotesEoaServiceintf.getNotesCreatersList();
            LOG.debug("Creator List retrieved from RMD Service ");
            if (arlCreatorList != null) {               
                objCreatorResponseType.setCreatorList(arlCreatorList);
            }   
            /*else { 
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

            LOG.debug("Arraylist of Creators Response " + objCreatorResponseType);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return objCreatorResponseType;
    }

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDWebException
     * @Description: This method adds the notes for the selected customer.
     * 
     */
    @POST
    @Path(OMDConstants.ADD_NOTES_TO_VEHICLE )
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String addNotesToVehicle(final NotesRequestType objNotesRequestType) throws RMDServiceException
    {
        String  status=null;
        AddNotesEoaServiceVO objAddNotesServiceVO = null;
        
        try
        {   
            if(null!=objNotesRequestType)
            {
                objAddNotesServiceVO = new AddNotesEoaServiceVO();
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getCustomerID())) {
                    objAddNotesServiceVO.setCustomerId(objNotesRequestType
                            .getCustomerID());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getSticky())) {
                    objAddNotesServiceVO.setSticky(objNotesRequestType
                            .getSticky());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getFromRN())) {
                    objAddNotesServiceVO.setFromRN(objNotesRequestType
                            .getFromRN());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getToRN())) {
                    objAddNotesServiceVO.setToRN(objNotesRequestType
                            .getToRN());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getModelId())) {
                    objAddNotesServiceVO.setModelId(objNotesRequestType
                            .getModelId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getCtrlId())) {
                    objAddNotesServiceVO.setCtrlId(objNotesRequestType
                            .getCtrlId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getFleetId())) {
                    objAddNotesServiceVO.setFleetId(objNotesRequestType
                            .getFleetId());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getNotes())) {
                    objAddNotesServiceVO.setNoteDescription(EsapiUtil.resumeSpecialChars(objNotesRequestType
                            .getNotes()));
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getOverWriteFlag())) {
                    objAddNotesServiceVO.setOverWriteFlag(objNotesRequestType.getOverWriteFlag());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                        .getStickyObjId())) {
                    objAddNotesServiceVO.setStickyObjId(objNotesRequestType.getStickyObjId());
                }
                objAddNotesServiceVO.setStrUserName(objNotesRequestType.getUserId());
                if (RMDCommonUtility.isCollectionNotEmpty(objNotesRequestType
                        .getFromAssetList())) {
                objAddNotesServiceVO.setAssetNumbersList(objNotesRequestType.getFromAssetList());
                }
            }else{
                throw new OMDInValidInputException(
                    OMDConstants.GETTING_NULL_REQUEST_OBJECT);      
            }
            status=addNotesEoaServiceintf.addNotesToVehicle(objAddNotesServiceVO);
        }
        catch(Exception e)
        {
            LOG.error("ERROR IN ADD NOTES TO VEHICLE IN NOTESRESOURCE:"+e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return status;
            
    }
    

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :@Context UriInfo ui
     * @throws :RMDWebException
     * @Description: This method is used to get the existing sticky details.
     * 
     */
    @GET
    @Path(OMDConstants.FETCH_VEHICLE_STICKY_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        public List<StickyNotesDetailsResponseType> fetchStickyCaseNotes(
            @Context UriInfo ui) throws RMDServiceException {
        String customerID = null;
        String fromRN = null;
        String toRN = null;
        String noOfUnits = null;
        GregorianCalendar objGregorianCalendar;
        String timeZone = getDefaultTimeZone();
        XMLGregorianCalendar entryTime;
        // List<StickyNotesDetailsResponseType>
        // objStickyNotesDetailsResponseTypeList = new
        // ArrayList<StickyNotesDetailsResponseType>();
        List<StickyNotesDetailsVO> objStickyNotesDetailsVO = new ArrayList<StickyNotesDetailsVO>();
        List<StickyNotesDetailsResponseType> objStickyNotesDetailsResponse = new ArrayList<StickyNotesDetailsResponseType>();
        StickyNotesDetailsResponseType stickyNotesDetailsResponse = null;
        try {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {

                customerID = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
            }
            if (queryParams.containsKey(OMDConstants.FROM_RN)) {

                fromRN = queryParams.getFirst(OMDConstants.FROM_RN);
            }
            if (queryParams.containsKey(OMDConstants.NO_OF_UNITS)) {

                noOfUnits = queryParams.getFirst(OMDConstants.NO_OF_UNITS);
            }
            objStickyNotesDetailsVO = addNotesEoaServiceintf
                    .fetchVehicleStickyCaseNotes(customerID, fromRN, noOfUnits);
            for (StickyNotesDetailsVO stickyNotesDetailsVODetails : objStickyNotesDetailsVO) {
                stickyNotesDetailsResponse = new StickyNotesDetailsResponseType();
                    stickyNotesDetailsResponse
                            .setAdditionalInfo(stickyNotesDetailsVODetails
                                    .getAdditionalInfo());
                    stickyNotesDetailsResponse
                            .setCreatedBy(stickyNotesDetailsVODetails
                                    .getCreatedBy());
                    stickyNotesDetailsResponse
                            .setObjId(stickyNotesDetailsVODetails.getObjId());
                    if (null != stickyNotesDetailsVODetails.getEntryTime()) {
                        objGregorianCalendar = new GregorianCalendar();
                        objGregorianCalendar
                                .setTime(stickyNotesDetailsVODetails
                                        .getEntryTime());
                        RMDCommonUtility.setZoneOffsetTime(
                                objGregorianCalendar, timeZone);
                        entryTime = dtf
                                .newXMLGregorianCalendar(objGregorianCalendar);
                        stickyNotesDetailsResponse.setEntryTime(entryTime);
                    }
                    stickyNotesDetailsResponse
                            .setStickyAssets(stickyNotesDetailsVODetails
                                    .getStickyAssets());
                objStickyNotesDetailsResponse.add(stickyNotesDetailsResponse);
            }
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT FETCH STICKY CASE NOTES METHOD IN CASERESOURCE:"
                    + e);
            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }

        return objStickyNotesDetailsResponse;
    }
    
    /**
     * @Author:
     * @param:NotesRequestType
     * @return:List<FindNotesResponseType>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Notes Details.
     */
    @POST
    @Path(OMDConstants.GET_FIND_NOTES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<FindNotesResponseType> getFindNotes(
            NotesRequestType objNotesRequestType)
            throws RMDServiceException {
        FindNotesEoaServiceVO objFindNotesEoaServiceVO = new FindNotesEoaServiceVO();
        List<FindNotesDetailsVO> objFindNotesDetailsVOList = new ArrayList<FindNotesDetailsVO>();
        List<FindNotesResponseType> findNotesResponseTypeList = null;
        FindNotesResponseType findNotesResponseType = null;
        
        try {
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getCustomerID())) {
                objFindNotesEoaServiceVO.setCustomerId(objNotesRequestType
                        .getCustomerID());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getFromRN())) {
                objFindNotesEoaServiceVO.setFromRN(objNotesRequestType
                        .getFromRN());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                    .getToRN())) {
            objFindNotesEoaServiceVO.setToRN(objNotesRequestType
                    .getToRN());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getModelId())) {
                objFindNotesEoaServiceVO.setModelId(objNotesRequestType
                        .getModelId());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getFleetId())) {
                objFindNotesEoaServiceVO.setFleetId(objNotesRequestType
                        .getFleetId());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getCtrlId())) {
                objFindNotesEoaServiceVO.setCtrlId(objNotesRequestType
                        .getCtrlId());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getCreatedBy())) {
                objFindNotesEoaServiceVO.setCreatedBy(objNotesRequestType
                        .getCreatedBy());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getNoteType())) {
                objFindNotesEoaServiceVO.setNoteType(objNotesRequestType
                        .getNoteType());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getStartDate())) {
                objFindNotesEoaServiceVO.setStartDate(objNotesRequestType
                        .getStartDate());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                                    .getEndDate())) {
                objFindNotesEoaServiceVO.setEndDate(objNotesRequestType.getEndDate());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objNotesRequestType
                    .getSearchKeyWord())) {
                objFindNotesEoaServiceVO.setSearchKeyWord(objNotesRequestType
                                    .getSearchKeyWord());
            }
            objFindNotesDetailsVOList = addNotesEoaServiceintf
                    .getFindNotes(objFindNotesEoaServiceVO);
            if (RMDCommonUtility.isCollectionNotEmpty(objFindNotesDetailsVOList)) {
                
                findNotesResponseTypeList = new ArrayList<FindNotesResponseType>(objFindNotesDetailsVOList.size());
                
                for (FindNotesDetailsVO objNotesDetails : objFindNotesDetailsVOList) {
                    findNotesResponseType = new FindNotesResponseType();
                    findNotesResponseType.setCustomerName(objNotesDetails.getCustomerName());
                    findNotesResponseType.setAssetNumber(objNotesDetails.getRn());
                    findNotesResponseType.setCaseID(objNotesDetails
                            .getCaseId());
                    if (null != objNotesDetails.getDate()) {
                        findNotesResponseType.setCreationDate(objNotesDetails.getDate());
                    }
                    findNotesResponseType.setModel(objNotesDetails
                            .getModelName());
                    findNotesResponseType.setCtrlName(objNotesDetails
                            .getCtrlName());
                    findNotesResponseType
                            .setCreatedBy(objNotesDetails.getCreatedBy());
                    findNotesResponseType.setNoteType(objNotesDetails.getNoteType());
                    findNotesResponseType.setNoteSeqID(objNotesDetails.getNoteObjId());
                    findNotesResponseType.setNoteDescription(EsapiUtil.resumeSpecialChars(objNotesDetails.getNoteDescription()));
                    findNotesResponseType.setStickyFlag(objNotesDetails.getStickyFlag());
                    findNotesResponseTypeList
                            .add(findNotesResponseType);
                }
            } else {
                findNotesResponseTypeList = new ArrayList<FindNotesResponseType>(0); 
            }
            
        } catch (Exception e) {
        	 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
        } finally {
            objFindNotesEoaServiceVO = null;
            objFindNotesDetailsVOList = null;
        }
        return findNotesResponseTypeList;
    }
    
    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId, caseStickyObjId
     * @throws :RMDServiceException
     * @Description:This method is used for removing a unit Level as well as
     *                   case Level Sticky Notes from find notes screen.
     */
    @POST
    @Path(OMDConstants.FIND_NOTES_REMOVE_STICKY)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String removeSticky(final StickyNotesDetailsResponseType stickyDetailsResponseType)
            throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        String unitStickyObjId = null;
        String caseStickyObjId = null;
        try {

            if (null != stickyDetailsResponseType.getUnitStickyObjId()) {
                unitStickyObjId = stickyDetailsResponseType
                        .getUnitStickyObjId();
            }

            if (null != stickyDetailsResponseType.getUnitStickyObjId()) {
                caseStickyObjId = stickyDetailsResponseType
                        .getCaseStickyObjId();
            }

            result = addNotesEoaServiceintf.removeSticky(unitStickyObjId,
                    caseStickyObjId);
        } catch (Exception e) {
            LOG.error("ERROR OCCURED AT REMOVE STICKY  METHOD OF NOTESRESOURCE ");
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        
        return result;
    }
            
    
}