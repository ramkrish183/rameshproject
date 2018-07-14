/**
 * ============================================================
 * Classification: GE Confidential
 * File : CommonResource.java
 * Description : 
 * Package :  com.ge.trans.rmd.services.common
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 11, 2012
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.common.service.intf.CommonServiceIntf;
import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.error.valueobjects.ExceptionRequestType;
import com.ge.trans.rmd.services.solutions.valueobjects.CustLookupResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.LookupResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: May 11, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as common Webservices for KM and CM and provide
 *              the common related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.COMMONSERVICE)
@Component
@SuppressWarnings("unchecked")
public class CommonResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CommonResource.class);

    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    @Autowired
    private CommonServiceIntf commonServiceIntf;

    /**
     * @Description:This method is used for fetching Queues values
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */

    @GET
    @Path(OMDConstants.GETQUEUES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getQueues() throws RMDServiceException {

        List<ElementVO> arlLookupList;
        List<LookupResponseType> arlLookupTypes = null;
        LookupResponseType objLookupResponseType = null;

        String strLanguage = null, userLanguage = null;
        ElementVO objElementVO = null;
        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            userLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);

            arlLookupList = commonServiceIntf.getSearchQueueMenu(strLanguage,
                    userLanguage);

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
            } /*else {
                throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

        }catch (Exception ex) {
        
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
        return arlLookupTypes;
    }
    
    
    
    /**
     * @Description:This method is used for fetching function values and it returns function id and function name
     * @param uriParam
     * @return List of LookupResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_FUNCTIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<LookupResponseType> getFunctions() throws RMDServiceException {
        // TODO Auto-generated method stub
        List<LookupResponseType> arlModels = null;
        String strLanguage = null, userLanguage = null;
        List<ElementVO> arlLookupList;
        Iterator<ElementVO> iterator = null;
        LookupResponseType objLookupResponseType = null;
        ElementVO objElementVO = null;
        try {
            LOG.debug("Start getFunctions Webservice method");
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            userLanguage = getRequestHeader(OMDConstants.USERLANGUAGE);
            arlLookupList = commonServiceIntf.getFunctions(strLanguage,
                    userLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlLookupList)) {
                iterator = arlLookupList.iterator();
                arlModels = new ArrayList<LookupResponseType>();
                while (iterator.hasNext()) {
                    objElementVO = (ElementVO) iterator.next();
                    objLookupResponseType = new LookupResponseType();
                    BeanUtility.copyBeanProperty(objElementVO,
                            objLookupResponseType);
                    arlModels.add(objLookupResponseType);
                }
            } /*else {
                throw new OMDNoResultFoundException(
                        BeanUtility
                                .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility
                                        .getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                                new String[] {},
                                BeanUtility
                                        .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
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
        LOG.debug("End getFunctions Webservice method");
        return arlModels;
    }
    
    
    /**
     * @Description This method is to get the look up value from customer look
     *              up table
     * @param UriInfo
     * @return String
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_SD_CUST_LOOKUP)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CustLookupResponseType> getSDCustLookup(
            @Context final UriInfo ui) throws RMDServiceException {
        List<CustLookupVO> lookupValueList = null;
        List<CustLookupResponseType> lookupResponseList = null;
        CustLookupResponseType custLookupResponse = null;
        CustLookupVO custLookupVO = null;
        try {

            lookupValueList = commonServiceIntf.getSDCustLookup();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupValueList)) {
                lookupResponseList = new ArrayList<CustLookupResponseType>();
                for (int i = 0; i < lookupValueList.size(); i++) {
                    custLookupVO = new CustLookupVO();
                    custLookupVO = lookupValueList.get(i);
                    custLookupResponse = new CustLookupResponseType();
                    custLookupResponse.setFamPrivilege(custLookupVO
                            .getFamPrivilege());
                    custLookupResponse.setMetricsPrivilege(custLookupVO
                            .getMetricsPrivilege());
                    custLookupResponse.setCustomerId(custLookupVO
                            .getCustomerId());
                    custLookupResponse.setRxEmailCheck(custLookupVO
                            .getRxEmailCheck());
                    custLookupResponse.setDateFormat(custLookupVO
                            .getDateFormat());
                    custLookupResponse.setConfigAlertFlg(custLookupVO
                            .getConfigAlertFlg());
                    custLookupResponse.setFlagMPData(custLookupVO
                            .getFlagMPData());
                    lookupResponseList.add(custLookupResponse);
                }
            }

        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e,
                    omdResourceMessagesIntf);
        }
        return lookupResponseList;

    }
    
    @POST
    @Path("/saveExceptionDetails")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void saveExceptionDetails(ExceptionRequestType exceptionRequestType) throws RMDServiceException {
    	ExceptionDetailsVO exceptionDetailsVO = new ExceptionDetailsVO();
    	exceptionDetailsVO.setBusinessKeys(exceptionRequestType.getBusinessKeys());
    	exceptionDetailsVO.setExceptionDesc(exceptionRequestType.getExceptionDesc());
    	exceptionDetailsVO.setExceptionType(exceptionRequestType.getExceptionType());
    	exceptionDetailsVO.setScreenName(exceptionRequestType.getScreenName());
    	exceptionDetailsVO.setTraceLog(exceptionRequestType.getTraceLog());
    	exceptionDetailsVO.setUserId(exceptionRequestType.getUserId());
    	commonServiceIntf.saveExceptionDetails(exceptionDetailsVO);
    }
    
    /**
	 * @return String
	 * @Description: method to get Asset Panel parameters
	 */
	@GET
	@Path(OMDConstants.GET_ASSET_PANEL_PARAMETERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getAssetPanelParameters() throws RMDServiceException {
		String assetPanelParams = "";
		try {
			assetPanelParams = commonServiceIntf.getAssetPanelParameters();
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return assetPanelParams;
	}
}