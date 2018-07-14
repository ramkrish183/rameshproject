package com.ge.trans.rmd.services.cbr.resources;

/**
 * ============================================================
 * Classification: GE Confidential
 * File : CBRAdminResource.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.cbr
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : June 22,2016
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.cbr.service.intf.CBRAdminServiceIntf;
import com.ge.trans.eoa.services.cbr.service.valueobjects.CBRAdminVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.cbr.valueobjects.CBRAdminRequestType;
import com.ge.trans.rmd.services.cbr.valueobjects.CBRAdminResponseType;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 22,2016
 * @Date Modified : June 22,2016
 * @Modified By :
 * @Contact :
 * @Description : This Class act as CBR Web services and provide the CBR related
 *              functionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.CBR_SERVICE)
@Component
public class CBRAdminResource {

    @Autowired
    private CBRAdminServiceIntf objCBRServiceIntf;
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    /**
     * @Description:This method is used for fetching Rx details for CBR
     * @param uriParam
     * @return List of CBRResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CBR_RX_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CBRAdminResponseType> getRxMaintenanceDetails(@Context final UriInfo ui) throws RMDServiceException {
        List<CBRAdminResponseType> arlRxCBRCases = new ArrayList<CBRAdminResponseType>();
        CBRAdminResponseType objCBRResponseType = new CBRAdminResponseType();
        CBRAdminVO objCbrCasesVO = new CBRAdminVO();
        String strStatus = null;
        String rxTitle = null;
        try {
            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

            if (queryParams.containsKey(OMDConstants.STATUS)) {
                strStatus = queryParams.getFirst(OMDConstants.STATUS);
                if (null != strStatus && !RMDCommonConstants.EMPTY_STRING.equals(strStatus)) {
                    objCbrCasesVO.setStatus(strStatus);
                }
            }
            if (queryParams.containsKey(OMDConstants.RX_TITLE)) {
                rxTitle = queryParams.getFirst(OMDConstants.RX_TITLE);
                if (null != rxTitle && !RMDCommonConstants.EMPTY_STRING.equals(rxTitle)) {
                    objCbrCasesVO.setRxTitle(rxTitle);
                }
            }
            List<CBRAdminVO> arlCBRCasesVO = objCBRServiceIntf.getRxMaintenanceDetails(objCbrCasesVO);
            if (null != arlCBRCasesVO && !arlCBRCasesVO.isEmpty()) {
                for (Iterator iterator = arlCBRCasesVO.iterator(); iterator.hasNext();) {
                    objCBRResponseType = new CBRAdminResponseType();
                    CBRAdminVO objCBRCasesVO = (CBRAdminVO) iterator.next();
                    objCBRResponseType.setRxId(objCBRCasesVO.getRxId());
                    objCBRResponseType.setRxTitle(objCBRCasesVO.getRxTitle());
                    objCBRResponseType.setStatus(objCBRCasesVO.getStatus());
                    objCBRResponseType.setComments(objCBRCasesVO.getComments());
                    arlRxCBRCases.add(objCBRResponseType);
                }
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlRxCBRCases;
    }

    /**
     * @Description:This method is used for fetching Case details for CBR
     * @param uriParam
     * @return List of CBRAdminResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_CBR_CASE_DETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CBRAdminResponseType> getCaseMaintenanceDetails(@Context final UriInfo ui) throws RMDServiceException {
        List<CBRAdminResponseType> arlCBRCases = new ArrayList<CBRAdminResponseType>();
        CBRAdminResponseType objCBRResponseType = new CBRAdminResponseType();
        CBRAdminVO objCbrCasesVO = new CBRAdminVO();
        String strStatus = null;
        String caseId = null;
        String rxTitle = null;
        try {

            final MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

            if (queryParams.containsKey(OMDConstants.STATUS)) {
                strStatus = queryParams.getFirst(OMDConstants.STATUS);
                if (null != strStatus && !RMDCommonConstants.EMPTY_STRING.equals(strStatus)) {
                    objCbrCasesVO.setStatus(strStatus);
                }
            }

            if (queryParams.containsKey(OMDConstants.CASE_Id)) {
                caseId = queryParams.getFirst(OMDConstants.CASE_Id);
                if (null != caseId && !RMDCommonConstants.EMPTY_STRING.equals(caseId)) {
                    objCbrCasesVO.setCaseId(caseId);
                }
            }

            if (queryParams.containsKey(OMDConstants.RX_TITLE)) {
                rxTitle = queryParams.getFirst(OMDConstants.RX_TITLE);
                if (null != rxTitle && !RMDCommonConstants.EMPTY_STRING.equals(rxTitle)) {
                    objCbrCasesVO.setRxTitle(rxTitle);
                }
            }

            List<CBRAdminVO> arlCBRCasesVO = objCBRServiceIntf.getCaseMaintenanceDetails(objCbrCasesVO);
            if (null != arlCBRCasesVO && !arlCBRCasesVO.isEmpty()) {
                for (Iterator iterator = arlCBRCasesVO.iterator(); iterator.hasNext();) {
                    objCBRResponseType = new CBRAdminResponseType();
                    CBRAdminVO objCBRCasesVO = (CBRAdminVO) iterator.next();
                    objCBRResponseType.setCaseId(objCBRCasesVO.getCaseId());
                    objCBRResponseType.setRxTitle(objCBRCasesVO.getRxTitle());
                    objCBRResponseType.setStatus(objCBRCasesVO.getStatus());
                    objCBRResponseType.setComments(objCBRCasesVO.getComments());
                    objCBRResponseType.setSimonObjid(objCBRCasesVO.getSimonObjid());
                    arlCBRCases.add(objCBRResponseType);

                }

            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return arlCBRCases;
    }

    /**
     * @Description:This method is used for activate/deactivate cases
     * @param objCBRRequestType
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.CBR_CASE_ACTIVATE_DEACTIVATE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String caseActivateDeactivate(CBRAdminRequestType objCBRRequestType) throws RMDServiceException {
        String objid = null;
        String status = null;
        String caseActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            if (null != objCBRRequestType) {

                if (null != objCBRRequestType.getObjid()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCBRRequestType.getObjid())) {
                    objid = objCBRRequestType.getObjid();
                }
                if (null != objCBRRequestType.getStatus()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCBRRequestType.getStatus())) {
                    status = objCBRRequestType.getStatus();
                }
            }
            caseActivateDeactivate = objCBRServiceIntf.caseActivateDeactivate(objid, status);

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseActivateDeactivate;
    }

    /**
     * @Description:This method is used for activate/deactivate Rx
     * @param objCBRRequestType
     * @return String
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.CBR_RX_ACTIVATE_DEACTIVATE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String cbrRxActivateDeactivate(CBRAdminRequestType objCBRRequestType) throws RMDServiceException {
        String objid = null;
        String status = null;
        String userName = null;
        String caseActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            if (null != objCBRRequestType) {

                if (null != objCBRRequestType.getObjid()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCBRRequestType.getObjid())) {
                    objid = objCBRRequestType.getObjid();
                }
                if (null != objCBRRequestType.getStatus()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCBRRequestType.getStatus())) {
                    status = objCBRRequestType.getStatus();
                }
                if (null != objCBRRequestType.getStatus()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objCBRRequestType.getStatus())) {
                    userName = objCBRRequestType.getStatus();
                }
            }
            caseActivateDeactivate = objCBRServiceIntf.rxActivateDeactivate(objid, status, userName);

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return caseActivateDeactivate;
    }

}
