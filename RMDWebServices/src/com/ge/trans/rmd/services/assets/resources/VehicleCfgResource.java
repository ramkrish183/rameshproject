/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : March 17, 2016
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.resources;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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

import com.ge.trans.eoa.services.asset.service.intf.VehicleCfgServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MdscStartUpControllersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.ApplicationParametersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.CtrlCfgResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.MdscStartUpControllersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VehCfgTemplateRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VehCfgTemplateResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VehicleCfgRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.VehicleCfgResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_VEHICLE_CONFIG)
@Component
public class VehicleCfgResource {
    @Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

    @Autowired
    private VehicleCfgServiceIntf objVehicleCfgServiceIntf;

    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(VehicleCfgResource.class);

    @GET
    @Path(OMDConstants.GET_VEHICLE_BOM_CONFIGS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VehicleCfgResponseType> getVehicleBOMConfigs(@Context UriInfo ui)
            throws RMDServiceException {
        List<VehicleCfgResponseType> subIdList = new ArrayList<VehicleCfgResponseType>();
        List<VehicleCfgVO> valueList = new ArrayList<VehicleCfgVO>();
        VehicleCfgResponseType objVehicleCfgResponseType = null;
            LOG.debug("*****getVehicleBomConfig WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                            .format(new Date()));

            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            String customer = OMDConstants.EMPTY_STRING;
            String rnh = OMDConstants.EMPTY_STRING;
            String roadNumber = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
                if (queryParams.containsKey(OMDConstants.RNH)) {
                    rnh = queryParams.getFirst(OMDConstants.RNH);
                }
                if (queryParams.containsKey(OMDConstants.ROAD_NUMBER)) {
                    roadNumber = queryParams.getFirst(OMDConstants.ROAD_NUMBER);
                }
                if (RMDCommonUtility.isNullOrEmpty(customer)) {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(rnh)) {
                    throw new OMDInValidInputException(
                            OMDConstants.RNH_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(roadNumber)) {
                    throw new OMDInValidInputException(
                            OMDConstants.ROAD_NUMBER_NOT_PROVIDED);
                }
                valueList = objVehicleCfgServiceIntf.getVehicleBOMConfigs(
                        customer, rnh, roadNumber);
                for (VehicleCfgVO objVehicleCfgVO : valueList) {
                    objVehicleCfgResponseType = new VehicleCfgResponseType();
                    objVehicleCfgResponseType.setConfigItem(objVehicleCfgVO
                            .getConfigItem());
                    objVehicleCfgResponseType.setConfigObjId(objVehicleCfgVO
                            .getConfigObjId());
                    objVehicleCfgResponseType.setCurrentVersion(objVehicleCfgVO
                            .getCurrentVersion());
                    objVehicleCfgResponseType
                            .setExpectedVersion(objVehicleCfgVO
                                    .getExpectedVersion());
                    objVehicleCfgResponseType.setMasterBOMObjId(objVehicleCfgVO
                            .getMasterBOMObjId());
                    objVehicleCfgResponseType.setParmeterNo(objVehicleCfgVO
                            .getParmeterNo());
                    objVehicleCfgResponseType.setSerialNumber(objVehicleCfgVO
						.getSerialNumber());
					objVehicleCfgResponseType.setNotificationFlag(objVehicleCfgVO
						.getNotificationFlag());
					objVehicleCfgResponseType.setVehicleObjId(objVehicleCfgVO
						.getVehicleObjId());
                    subIdList.add(objVehicleCfgResponseType);

                }

            } catch (Exception e) {
            	 RMDWebServiceErrorHandler.handleException(e,
                         omdResourceMessagesIntf);
            }

            return subIdList;
    }

    @GET
    @Path(OMDConstants.GET_MDSC_CONTROLLERS_INFO)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public MdscStartUpControllersResponseType getMDSCStartUpControllersInfo(
            @Context UriInfo ui) throws RMDServiceException {
        MdscStartUpControllersResponseType objMdscStartUpControllersResponseType = new MdscStartUpControllersResponseType();
        MdscStartUpControllersVO mdscStartUpControllersVOList = new MdscStartUpControllersVO();
            LOG.debug("*****getVehicleBomConfig WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                            .format(new Date()));

            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            String customer = OMDConstants.EMPTY_STRING;
            String rnh = OMDConstants.EMPTY_STRING;
            String roadNumber = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
                if (queryParams.containsKey(OMDConstants.RNH)) {
                    rnh = queryParams.getFirst(OMDConstants.RNH);
                }
                if (queryParams.containsKey(OMDConstants.ROAD_NUMBER)) {
                    roadNumber = queryParams.getFirst(OMDConstants.ROAD_NUMBER);
                }
                if (RMDCommonUtility.isNullOrEmpty(customer)) {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(rnh)) {
                    throw new OMDInValidInputException(
                            OMDConstants.RNH_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(roadNumber)) {
                    throw new OMDInValidInputException(
                            OMDConstants.ROAD_NUMBER_NOT_PROVIDED);
                }
                mdscStartUpControllersVOList = objVehicleCfgServiceIntf
                        .getMDSCStartUpControllersInfo(customer, rnh,
                                roadNumber);
                if (mdscStartUpControllersVOList != null) {
                    objMdscStartUpControllersResponseType
                            .setAcComm(mdscStartUpControllersVOList.getAcComm());
                    objMdscStartUpControllersResponseType
                            .setAuxComm(mdscStartUpControllersVOList
                                    .getAuxComm());
                    objMdscStartUpControllersResponseType
                            .setCabComm(mdscStartUpControllersVOList
                                    .getCabComm());
                    objMdscStartUpControllersResponseType
                            .setCaxComm(mdscStartUpControllersVOList
                                    .getCaxComm());
                    objMdscStartUpControllersResponseType
                            .setCcaComm(mdscStartUpControllersVOList
                                    .getCcaComm());
                    objMdscStartUpControllersResponseType
                            .setEabComm(mdscStartUpControllersVOList
                                    .getEabComm());
                    objMdscStartUpControllersResponseType
                            .setEfiComm(mdscStartUpControllersVOList
                                    .getEfiComm());
                    objMdscStartUpControllersResponseType
                            .setErComm(mdscStartUpControllersVOList.getErComm());
                    objMdscStartUpControllersResponseType
                            .setExcComm(mdscStartUpControllersVOList
                                    .getExcComm());
                    objMdscStartUpControllersResponseType
                            .setFlmComm(mdscStartUpControllersVOList
                                    .getFlmComm());
                    objMdscStartUpControllersResponseType
                            .setServicePdp(mdscStartUpControllersVOList
                                    .getServicePdp());
                    objMdscStartUpControllersResponseType
                            .setSmsEnabled(mdscStartUpControllersVOList
                                    .getSmsEnabled());

                }

            } catch (Exception e) {
            	 RMDWebServiceErrorHandler.handleException(e,
                         omdResourceMessagesIntf);
            }

        return objMdscStartUpControllersResponseType;
    }

    @GET
    @Path(OMDConstants.GET_VEHICLE_CFG_TEMPLATES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VehCfgTemplateResponseType> getVehicleCfgTemplates(
            @Context UriInfo ui) throws RMDServiceException {
        List<VehCfgTemplateResponseType> vehCfgTemplateResponseTypeList = new ArrayList<VehCfgTemplateResponseType>();
        List<VehicleCfgTemplateVO> vehicleCfgTemplateList = new ArrayList<VehicleCfgTemplateVO>();
        VehCfgTemplateResponseType objVehCfgTemplateResponseType = null;
            LOG.debug("*****getVehicleCfgTemplates WEBSERVICE BEGIN**** "
                    + new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
                            .format(new Date()));

            final MultivaluedMap<String, String> queryParams = ui
                    .getQueryParameters();
            String customer = OMDConstants.EMPTY_STRING;
            String rnh = OMDConstants.EMPTY_STRING;
            String roadNumber = OMDConstants.EMPTY_STRING;
            try {
                if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
                    customer = queryParams.getFirst(OMDConstants.CUSTOMER);
                }
                if (queryParams.containsKey(OMDConstants.RNH)) {
                    rnh = queryParams.getFirst(OMDConstants.RNH);
                }
                if (queryParams.containsKey(OMDConstants.ROAD_NUMBER)) {
                    roadNumber = queryParams.getFirst(OMDConstants.ROAD_NUMBER);
                }
                if (RMDCommonUtility.isNullOrEmpty(customer)) {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMERID_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(rnh)) {
                    throw new OMDInValidInputException(
                            OMDConstants.RNH_NOT_PROVIDED);
                }

                if (RMDCommonUtility.isNullOrEmpty(roadNumber)) {
                    throw new OMDInValidInputException(
                            OMDConstants.ROAD_NUMBER_NOT_PROVIDED);
                }
                vehicleCfgTemplateList = objVehicleCfgServiceIntf
                        .getVehicleCfgTemplates(customer, rnh, roadNumber);
                for (VehicleCfgTemplateVO objVehicleCfgTemplateVO : vehicleCfgTemplateList) {
                    objVehCfgTemplateResponseType = new VehCfgTemplateResponseType();
                    objVehCfgTemplateResponseType
                            .setObjId(objVehicleCfgTemplateVO.getObjId());
                    objVehCfgTemplateResponseType
                            .setConfigFile(objVehicleCfgTemplateVO
                                    .getConfigFile());
                    objVehCfgTemplateResponseType
                            .setObjId(objVehicleCfgTemplateVO.getObjId());
                    objVehCfgTemplateResponseType
                            .setStatus(objVehicleCfgTemplateVO.getStatus());
                    objVehCfgTemplateResponseType
                            .setTemplate(objVehicleCfgTemplateVO.getTemplate());
                    objVehCfgTemplateResponseType
                            .setTitle(objVehicleCfgTemplateVO.getTitle());
                    objVehCfgTemplateResponseType
                            .setVersion(objVehicleCfgTemplateVO.getVersion());
                    objVehCfgTemplateResponseType.setOffboardStatus(objVehicleCfgTemplateVO.getOffboardStatus());
                    objVehCfgTemplateResponseType.setOnboardStatus(objVehicleCfgTemplateVO.getOnboardStatus());
                    objVehCfgTemplateResponseType.setVehStatusObjId(objVehicleCfgTemplateVO.getVehStatusObjId());
                    vehCfgTemplateResponseTypeList
                            .add(objVehCfgTemplateResponseType);

                }

            } catch (Exception e) {
            	 RMDWebServiceErrorHandler.handleException(e,
                         omdResourceMessagesIntf);
            }
        return vehCfgTemplateResponseTypeList;
    }

    @POST
    @Path(OMDConstants.UPDATE_VEHICLE_CFG_ITEMS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String saveVehicleBOMConfigs(
            VehicleCfgRequestType objVehicleCfgRequestType)
            throws RMDServiceException {
        String status = OMDConstants.FAILURE;
        List<VehicleCfgVO> arlVehicleCfgVOs = new ArrayList<VehicleCfgVO>();
        List<VehicleCfgRequestType> arlVehicleCfgRequestTypes=objVehicleCfgRequestType.getArlVehicleCfgRequestTypes();
        String isCaseVehicleCfg=objVehicleCfgRequestType.getIsCaseVehicleConfig();
        try {
            CaseInfoServiceVO objCaseInfoServiceVO=null;
            for (VehicleCfgRequestType vehicleCfgRequestType : arlVehicleCfgRequestTypes) {
                VehicleCfgVO objVehicleCfgVO = new VehicleCfgVO();
                if (null != vehicleCfgRequestType.getCurrentVersion()) {
                    objVehicleCfgVO.setCurrentVersion(vehicleCfgRequestType
                            .getCurrentVersion());
                }
                if (null != vehicleCfgRequestType.getExpectedVersion()) {
                    objVehicleCfgVO.setExpectedVersion(vehicleCfgRequestType
                            .getExpectedVersion());
                }
                if (!RMDCommonUtility.isNullOrEmpty(vehicleCfgRequestType
                        .getSerialNumber())) {
                    objVehicleCfgVO.setSerialNumber(vehicleCfgRequestType
                            .getSerialNumber());
                }
                if (!RMDCommonUtility.isNullOrEmpty(vehicleCfgRequestType
                        .getConfigObjId())) {
                    objVehicleCfgVO.setConfigObjId(vehicleCfgRequestType
                            .getConfigObjId());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CONFIG_OBJ_ID_NOT_PROVIDED);
                }
                
                if (!RMDCommonUtility.isNullOrEmpty(vehicleCfgRequestType
                        .getUserName())) {
                    objVehicleCfgVO.setUserName(vehicleCfgRequestType
                            .getUserName());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.USERID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(vehicleCfgRequestType
                        .getNotificationgFlag())) {
                    objVehicleCfgVO.setNotificationFlag(vehicleCfgRequestType
                            .getNotificationgFlag());
                }
				if (!RMDCommonUtility.isNullOrEmpty(vehicleCfgRequestType
						.getNotificationgFlag())) {
					objVehicleCfgVO.setVehicleObjId(vehicleCfgRequestType
							.getVehicleObjId());
				}
                arlVehicleCfgVOs.add(objVehicleCfgVO);
            }
            

            if(OMDConstants.Y.equalsIgnoreCase(isCaseVehicleCfg))
            {
                objCaseInfoServiceVO=new CaseInfoServiceVO();
                objCaseInfoServiceVO.setCaseNumber(objVehicleCfgRequestType.getObjCaseInfoType().getCaseID());
                objCaseInfoServiceVO.setCaseObjId(objVehicleCfgRequestType.getObjCaseInfoType().getStrcaseObjId());
                objCaseInfoServiceVO.setCaseType(objVehicleCfgRequestType.getObjCaseInfoType().getCaseType());
                objCaseInfoServiceVO.setStrOwner(objVehicleCfgRequestType.getObjCaseInfoType().getOwnerName());
                objCaseInfoServiceVO.setUserName(objVehicleCfgRequestType.getObjCaseInfoType().getUserName());
                objCaseInfoServiceVO.setCaseStatus(objVehicleCfgRequestType.getObjCaseInfoType().getCaseStatus());
            }
            
            status = objVehicleCfgServiceIntf
                    .saveVehicleBOMConfigs(arlVehicleCfgVOs,objCaseInfoServiceVO,isCaseVehicleCfg);
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param : VehCfgTemplateRequestType
     * @throws :RMDWebException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    @POST
    @Path(OMDConstants.DELETE_VEHICLE_CFG_TEMPLATE)
    @Consumes(MediaType.APPLICATION_XML)
    public String deleteVehicleCfgTemplate(
            VehCfgTemplateRequestType objCfgTemplateRequestType)
            throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        try {
            VehicleCfgTemplateVO objCfgTemplateVO = new VehicleCfgTemplateVO();

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getConfigFile())) {
                objCfgTemplateVO.setConfigFile(objCfgTemplateRequestType
                        .getConfigFile());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.OBJID_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getCustomer())) {
                objCfgTemplateVO.setCustomer(objCfgTemplateRequestType
                        .getCustomer());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.CUSTOMERNAME_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getObjId())) {
                objCfgTemplateVO.setObjId(objCfgTemplateRequestType.getObjId());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.OBJID_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getAssetGrpName())) {
                objCfgTemplateVO.setAssetGrpName(objCfgTemplateRequestType
                        .getAssetGrpName());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getAssetNumber())) {
                objCfgTemplateVO.setAssetNumber(objCfgTemplateRequestType
                        .getAssetNumber());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.ASSETNUMBER_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getStatus())) {
                objCfgTemplateVO.setStatus(objCfgTemplateRequestType
                        .getStatus());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.STATUS_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getTemplate())) {
                objCfgTemplateVO.setTemplate(objCfgTemplateRequestType
                        .getTemplate());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TEMPLATE_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getTitle())) {
                objCfgTemplateVO.setTitle(objCfgTemplateRequestType
                        .getTitle());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TITLE_NOT_PROVIDED);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getVersion())) {
                objCfgTemplateVO.setVersion(objCfgTemplateRequestType
                        .getVersion());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.TITLE_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getUserName())) {
                objCfgTemplateVO.setUserName(objCfgTemplateRequestType
                        .getUserName());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.USERID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getCustomerId())) {
                objCfgTemplateVO.setCustomerId(objCfgTemplateRequestType
                        .getCustomerId());
            } else {
                throw new OMDInValidInputException(
                        OMDConstants.CUSTOMERID_NOT_PROVIDED);
            }
            if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                    .getVehStatusObjId())) {
                objCfgTemplateVO.setVehStatusObjId(objCfgTemplateRequestType
                        .getVehStatusObjId());
            } /*else {
                throw new OMDInValidInputException(
                        OMDConstants.CUSTOMERID_NOT_PROVIDED);
            }*/

            result = objVehicleCfgServiceIntf
                    .deleteVehicleCfgTemplate(objCfgTemplateVO);
        } catch (Exception ex) {
            result = OMDConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return result;
    }
    
    /**
     * @Author:
     * @param :
     * @return:List<CtrlCfgResponseType>
     * @throws:RMDServiceException
     * @Description:This method returns the list of controller configs.
     */
    @GET
    @Path(OMDConstants.GET_CONTROLLER_CONFIGURATIONS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<CtrlCfgResponseType> getControllerConfigs()
            throws RMDServiceException {
        List<ControllerConfigVO> cnfgVOList = null;
        List<CtrlCfgResponseType> cnfgResponseList = null;
        try {
            cnfgVOList = objVehicleCfgServiceIntf.getControllerConfigs();
            if (RMDCommonUtility.isCollectionNotEmpty(cnfgVOList)) {
                cnfgResponseList = new ArrayList<CtrlCfgResponseType>(
                        cnfgVOList.size());
                for (ControllerConfigVO obj : cnfgVOList) {
                    CtrlCfgResponseType objCtrlCfgResponseType = new CtrlCfgResponseType();
                    objCtrlCfgResponseType.setCtrlCfgObjId(obj.getObjId());
                    objCtrlCfgResponseType
                            .setCtrlCfgName(obj.getCtrlCnfgName());
                    cnfgResponseList.add(objCtrlCfgResponseType);
                }
                cnfgVOList = null;
            } /*else {
                throw new OMDNoResultFoundException(
                        OMDConstants.NORECORDFOUNDEXCEPTION);
            }*/

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return cnfgResponseList;
    }

    /**
     * @Author:
     * @param :@Context UriInfo
     * @return:List<VehCfgTemplateResponseType>
     * @throws:RMDServiceException
     * @Description:This method returns the list of controller config templates
     */
    @GET
    @Path(OMDConstants.GET_CONTROLLER_CONFIGURATION_TEMPLATES)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<VehCfgTemplateResponseType> getControllerConfigTemplates(
            @Context UriInfo ui) throws RMDServiceException {
        List<VehicleCfgTemplateVO> tempVOList = null;
        List<VehCfgTemplateResponseType> templateRespList = null;
        final MultivaluedMap<String, String> queryParams = ui
                .getQueryParameters();
        String cntrlCnfg = OMDConstants.EMPTY_STRING;
        String cnfgFile = OMDConstants.EMPTY_STRING;
        try {
            if (queryParams.containsKey(OMDConstants.CONTROLLER_CONFIGURATION)) {
                cntrlCnfg = queryParams
                        .getFirst(OMDConstants.CONTROLLER_CONFIGURATION);
            }
            if (queryParams.containsKey(OMDConstants.CONFIGURATION_FILE)) {
                cnfgFile = queryParams
                        .getFirst(OMDConstants.CONFIGURATION_FILE);
            }
            tempVOList = objVehicleCfgServiceIntf.getControllerConfigTemplates(
                    cntrlCnfg, cnfgFile);
            if (RMDCommonUtility.isCollectionNotEmpty(tempVOList)) {
                templateRespList = new ArrayList<VehCfgTemplateResponseType>(
                        tempVOList.size());
                for (VehicleCfgTemplateVO obj : tempVOList) {
                    VehCfgTemplateResponseType objVehCfgTemplateResponseType = new VehCfgTemplateResponseType();
                    objVehCfgTemplateResponseType.setObjId(obj.getObjId());
                    objVehCfgTemplateResponseType.setCntrlCnfg(obj
                            .getCntrlCnfg());
                    objVehCfgTemplateResponseType.setConfigFile(obj
                            .getConfigFile());
                    objVehCfgTemplateResponseType
                            .setTemplate(obj.getTemplate());
                    objVehCfgTemplateResponseType.setVersion(obj.getVersion());
                    objVehCfgTemplateResponseType.setStatus(obj.getStatus());
                    objVehCfgTemplateResponseType.setTitle(obj.getTitle());
                    templateRespList.add(objVehCfgTemplateResponseType);
                }
            }
            tempVOList=null;
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return templateRespList;
    }

    /**
     * @Author :
     * @return :String
     * @param : VehCfgTemplateRequestType
     * @throws :RMDServiceException
     * @Description: This method is Responsible for Deleting vehicle
     *               Configuration Template.
     * 
     */
    @POST
    @Path(OMDConstants.DELETE_VEHICLE_CFG)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String deleteVehicleCfg(VehCfgTemplateRequestType objRequestType)
            throws RMDServiceException {
        String result = OMDConstants.FAILURE;
        try {
            List<VehicleCfgTemplateVO> arlVehicleCfgTemplateVO = null;
            List<String> selRoadNumberList = new LinkedList<String>();
            Map<String, List<String>> rnMap= null;
            List<VehCfgTemplateRequestType> arlVehCfgTemplateRequestType = objRequestType
                    .getArrVehCfgTemplateRequestType();
            arlVehicleCfgTemplateVO = new ArrayList<VehicleCfgTemplateVO>(arlVehCfgTemplateRequestType.size());
            for (VehCfgTemplateRequestType objCfgTemplateRequestType : arlVehCfgTemplateRequestType) {
                VehicleCfgTemplateVO objCfgTemplateVO = new VehicleCfgTemplateVO();
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getObjId())) {
                    objCfgTemplateVO.setObjId(objCfgTemplateRequestType
                            .getObjId());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.OBJID_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getCntrlCnfg())) {
                    objCfgTemplateVO.setCntrlCnfg(objCfgTemplateRequestType
                            .getCntrlCnfg());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CNTRLCNFG_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getConfigFile())) {
                    objCfgTemplateVO.setConfigFile(objCfgTemplateRequestType
                            .getConfigFile());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CNFGFILE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getTemplate())) {
                    objCfgTemplateVO.setTemplate(objCfgTemplateRequestType
                            .getTemplate());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.TEMPLATE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getVersion())) {
                    objCfgTemplateVO.setVersion(objCfgTemplateRequestType
                            .getVersion());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.VERSION_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getTitle())) {
                    objCfgTemplateVO.setTitle(objCfgTemplateRequestType
                            .getTitle());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.TITLE_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getStatus())) {
                    objCfgTemplateVO.setStatus(objCfgTemplateRequestType
                            .getStatus());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.STATUS_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getCustomer())) {
                    objCfgTemplateVO.setCustomer(objCfgTemplateRequestType
                            .getCustomer());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.CUSTOMERNAME_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getAssetNumber())) {            
                	if(selRoadNumberList.isEmpty()){          
                		selRoadNumberList = new LinkedList<String>(Arrays.asList(objCfgTemplateRequestType
                                .getAssetNumber()
            					.split(RMDCommonConstants.COMMMA_SEPARATOR)));
                		 rnMap = new HashMap<String, List<String>>();
                		for(String rn : selRoadNumberList){
                			String rnh = rn.substring(0, rn.indexOf(RMDCommonConstants.HYPHEN));
                			String rno = rn.substring(rn.indexOf(RMDCommonConstants.HYPHEN)+1);
                			if(rnMap.get(rnh) !=null){
    							rnMap.get(rnh).add(rno);
    						}else{
    							List<String> rnList= new ArrayList<String>();
    							rnList.add(rno);
    							rnMap.put(rnh, rnList);
    						}
                		}
	                    objCfgTemplateVO.setRnMap(rnMap);
                	}else{
                		 objCfgTemplateVO.setRnMap(rnMap);
                	}
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.ASSETNUMBER_NOT_PROVIDED);
                }
                if (!RMDCommonUtility.isNullOrEmpty(objCfgTemplateRequestType
                        .getUserName())) {
                    objCfgTemplateVO.setUserName(objCfgTemplateRequestType
                            .getUserName());
                } else {
                    throw new OMDInValidInputException(
                            OMDConstants.USERID_NOT_PROVIDED);
                }
                arlVehicleCfgTemplateVO.add(objCfgTemplateVO);
            }
            result = objVehicleCfgServiceIntf
                    .deleteVehicleCfg(arlVehicleCfgTemplateVO);
        } catch (Exception ex) {
            result = OMDConstants.FAILURE;
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return result;
    }
    /**
     * @Author:
     * @param :
     * @return:List<CtrlCfgResponseType>
     * @throws:RMDServiceException
     * @Description:This method returns the list of controller configs.
     */
    @GET
    @Path(OMDConstants.GET_LOOKUP_VALUE_TOOLTIP)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<ApplicationParametersResponseType> getLookupValueTooltip()
            throws RMDServiceException {
        List<GetSysLookupVO> getSysLookupVOList = null;
        List<ApplicationParametersResponseType> appParamResTypeList = null;
        try {
        	getSysLookupVOList = objVehicleCfgServiceIntf.getLookupValueTooltip();
            if (RMDCommonUtility.isCollectionNotEmpty(getSysLookupVOList)) {
            	appParamResTypeList = new ArrayList<ApplicationParametersResponseType>(
            			getSysLookupVOList.size());
                for (GetSysLookupVO obj : getSysLookupVOList) {
                	ApplicationParametersResponseType objApplicationParametersResponseType = new ApplicationParametersResponseType();
                	objApplicationParametersResponseType.setLookupValue(obj.getLookValue());
                	objApplicationParametersResponseType
                            .setLookValueDesc(obj.getLookValueDesc());
                	appParamResTypeList.add(objApplicationParametersResponseType);
                }
                getSysLookupVOList = null;
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex,
                    omdResourceMessagesIntf);
        }
        return appParamResTypeList;
    }

}
