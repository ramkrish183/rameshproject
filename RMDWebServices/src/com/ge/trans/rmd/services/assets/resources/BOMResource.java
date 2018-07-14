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
import java.util.Date;
import java.util.List;

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

import com.ge.trans.eoa.services.asset.service.intf.BOMServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.BOMMaintenanceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.BOMRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.BOMResponseType;

@Path(OMDConstants.REQ_URI_BOM_CONFIG)
@Component
public class BOMResource {
	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;

	@Autowired
	private BOMServiceIntf objBOMServiceIntf;

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(BOMResource.class);

	@GET
	@Path(OMDConstants.GET_BOM_CONFIG_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<BOMResponseType> getConfigList(@Context UriInfo ui)
			throws RMDServiceException {
		List<BOMResponseType> responseList = new ArrayList<BOMResponseType>();
		List<BOMMaintenanceVO> valueList = new ArrayList<BOMMaintenanceVO>();
		BOMResponseType objBOMResponseType = null;
		try {
			LOG.debug("*****getConfigList WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			valueList = objBOMServiceIntf.getConfigList();
			if (valueList != null && valueList.size() > 0) {
				responseList = new ArrayList<BOMResponseType>(valueList.size());
			}
			for (BOMMaintenanceVO objBOMMaintenanceVO : valueList) {
				objBOMResponseType = new BOMResponseType();
				objBOMResponseType.setObjid(objBOMMaintenanceVO.getObjid());
				objBOMResponseType.setConfigList(objBOMMaintenanceVO
						.getConfigList());
				responseList.add(objBOMResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
	}

	@GET
	@Path(OMDConstants.POPULATE_CONFIG_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<BOMResponseType> populateConfigDetails(@Context UriInfo ui)
			throws RMDServiceException {
		List<BOMResponseType> responseList = new ArrayList<BOMResponseType>();
		List<BOMMaintenanceVO> valueList = new ArrayList<BOMMaintenanceVO>();
		BOMResponseType objBOMResponseType = null;

		final MultivaluedMap<String, String> queryParams = ui
				.getQueryParameters();
		String configId = OMDConstants.EMPTY_STRING;

		try {
			LOG.debug("*****populateConfigDetails WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
				configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
			}
			valueList = objBOMServiceIntf.populateConfigDetails(configId);
			if (valueList != null && valueList.size() > 0) {
				responseList = new ArrayList<BOMResponseType>(valueList.size());
			}
			for (BOMMaintenanceVO objBOMMaintenanceVO : valueList) {
				objBOMResponseType = new BOMResponseType();
				objBOMResponseType.setConfigItem(objBOMMaintenanceVO
						.getConfigItem());
				objBOMResponseType.setValue(objBOMMaintenanceVO.getValue());
				objBOMResponseType.setStatus(objBOMMaintenanceVO.getStatus());
				objBOMResponseType.setBomObjid(objBOMMaintenanceVO
						.getBomObjid());
				objBOMResponseType.setParameterObjid(objBOMMaintenanceVO
						.getParameterObjid());
				objBOMResponseType.setParameterNumber(objBOMMaintenanceVO
						.getParameterNumber());
				objBOMResponseType.setParameterDesc(objBOMMaintenanceVO
						.getParameterDesc());
				objBOMResponseType.setNotificationFlag(objBOMMaintenanceVO
						.getNotificationFlag());
				responseList.add(objBOMResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
	}

	@GET
	@Path(OMDConstants.GET_PARAMETER_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<BOMResponseType> getParameterList(@Context UriInfo ui)
			throws RMDServiceException {
		List<BOMResponseType> responseList = new ArrayList<BOMResponseType>();
		List<BOMMaintenanceVO> valueList = new ArrayList<BOMMaintenanceVO>();
		BOMResponseType objBOMResponseType = null;
		final MultivaluedMap<String, String> queryParams = ui
				.getQueryParameters();
		String configId = OMDConstants.EMPTY_STRING;
		try {
			LOG.debug("*****getVehicleBomConfig WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(
							RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			if (queryParams.containsKey(OMDConstants.CONFIG_ID)) {
				configId = queryParams.getFirst(OMDConstants.CONFIG_ID);
			}
			valueList = objBOMServiceIntf.getParameterList(configId);
			if (valueList != null && valueList.size() > 0) {
				responseList = new ArrayList<BOMResponseType>(valueList.size());
			}
			for (BOMMaintenanceVO objBOMMaintenanceVO : valueList) {
				objBOMResponseType = new BOMResponseType();
				objBOMResponseType.setParameterNumber(objBOMMaintenanceVO
						.getParameterNumber());
				objBOMResponseType.setParameterDesc(objBOMMaintenanceVO
						.getParameterDesc());
				objBOMResponseType.setParameterObjid(objBOMMaintenanceVO
						.getParameterObjid());
				responseList.add(objBOMResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
	}

	@POST
	@Path(OMDConstants.UPDATE_BOM_CFG_ITEMS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String saveBOMDetails(BOMRequestType objBOMRequestType)
			throws RMDServiceException {
		String status = OMDConstants.FAILURE;
		List<BOMMaintenanceVO> arlVehicleCfgVOs = new ArrayList<BOMMaintenanceVO>();
		List<BOMRequestType> arlBOMRequestType = objBOMRequestType
				.getArlBOMRequestType();
		BOMMaintenanceVO objBOMMaintenanceVO = null;
		try {
			CaseInfoServiceVO objCaseInfoServiceVO = null;
			for (BOMRequestType bOMRequestType : arlBOMRequestType) {
				objBOMMaintenanceVO = new BOMMaintenanceVO();
				if (null != bOMRequestType.getAction()) {
					objBOMMaintenanceVO.setAction(bOMRequestType.getAction());
				}
				if (null != bOMRequestType.getConfigId()) {
					objBOMMaintenanceVO.setConfigId(bOMRequestType
							.getConfigId());
				}
				if (null != bOMRequestType.getConfigItem()) {
					objBOMMaintenanceVO.setConfigItem(bOMRequestType
							.getConfigItem());
				}
				if (null != bOMRequestType.getObjid()) {
					objBOMMaintenanceVO.setObjid(bOMRequestType.getObjid());
				}
				if (null != bOMRequestType.getParameterDesc()) {
					objBOMMaintenanceVO.setParameterDesc(bOMRequestType
							.getParameterDesc());
				}
				if (null != bOMRequestType.getParameterNumber()) {
					objBOMMaintenanceVO.setParameterNumber(bOMRequestType
							.getParameterNumber());
				}
				if (null != bOMRequestType.getParameterObjid()) {
					objBOMMaintenanceVO.setParameterObjid(bOMRequestType
							.getParameterObjid());
				}
				if (null != bOMRequestType.getStatus()) {
					objBOMMaintenanceVO.setStatus(bOMRequestType.getStatus());
				}
				if (null != bOMRequestType.getValue()) {
					objBOMMaintenanceVO.setValue(bOMRequestType.getValue());
				}
				if (null != bOMRequestType.getUserName()) {
					objBOMMaintenanceVO.setUserName(bOMRequestType
							.getUserName());
				}
				if (null != bOMRequestType.getNotificationFlag()) {
					objBOMMaintenanceVO.setNotificationFlag(bOMRequestType
							.getNotificationFlag());
				}
				
				arlVehicleCfgVOs.add(objBOMMaintenanceVO);
			}
			status = objBOMServiceIntf.saveBOMDetails(arlVehicleCfgVOs);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}

}
