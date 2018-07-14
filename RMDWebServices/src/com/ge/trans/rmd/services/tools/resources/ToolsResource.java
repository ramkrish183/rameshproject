/*** ============================================================
 * Classification: GE Confidential
 * File : ToolsResource.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.resources
 * Author : Capgemini
 * Last Edited By :
 * Version : 1.0
 * Created on : August 17, 2017
 * History
 * Modified By : iGATE Patni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.tools.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.tools.runtools.service.intf.RunToolsServiceIntf;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolRunRequestVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.ElementResponseType;
import com.ge.trans.rmd.services.tools.valueobjects.ToolRunRequestType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : Capgemini
 * @Version : 1.0
 * @Date Created: August 17, 2017
 * @Date Modified : August 17, 2017
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Tools Web services and provide the Tools
 *              related funtionalities
 * @History :
 ******************************************************************************/

@Path(OMDConstants.TOOLS_SERVICE)
@Component
public class ToolsResource extends BaseResource {
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(ToolsResource.class);

	@Autowired
	RunToolsServiceIntf objRunToolsServiceIntf;
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;

	/**
	 * This is the method used for fetching the next scheduled run
	 * 
	 * @param String
	 *            vehicleObjId
	 * @return String
	 * @throws RMDServiceException
	 */

	@GET
	@Path(OMDConstants.GET_NEXT_SCHEDULE_RUN)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getNextScheduleRun(
			@PathParam(OMDConstants.VEHICLE_OBJID) final String vehicleObjId)
			throws RMDServiceException {
		String date = RMDCommonConstants.EMPTY_STRING;
		try {

			if (null != vehicleObjId
					&& RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(vehicleObjId)) {
				date = objRunToolsServiceIntf.getNextScheduleRun(vehicleObjId);
			} else {
				throw new OMDNoResultFoundException(
						OMDConstants.NORECORDFOUNDEXCEPTION);
			}

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return date;
	}

	/**
	 * This is the method used for fetching the last diagnostic run details
	 * 
	 * @param String
	 *            vehicleObjId
	 * @return String
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_LAST_DIAGNOSTIC_RUN_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getLastDiagnosticRunDetails(
			@PathParam(OMDConstants.VEHICLE_OBJID) final String vehicleObjId)
			throws RMDServiceException {
		String date = RMDCommonConstants.EMPTY_STRING;
		try {

			if (null != vehicleObjId
					&& RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(vehicleObjId)) {
				date = objRunToolsServiceIntf.getNextScheduleRun(vehicleObjId);
			} else {
				throw new OMDNoResultFoundException(
						OMDConstants.NORECORDFOUNDEXCEPTION);
			}

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return date;
	}

	/**
	 * @Author:
	 * @return:String
	 * @param :FindCaseServiceVO
	 * @throws:RMDServiceException
	 * @Description:This method is for updating case TITLE
	 */
	@POST
	@Path(OMDConstants.RUN_TOOLS)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String runTools(final ToolRunRequestType objToolRunRequestType)
			throws RMDServiceException {
		String result = OMDConstants.FAILURE;
		ToolRunRequestVO objRequestVO = null;
		try {

			if (null != objToolRunRequestType) {
				
				if(null==objToolRunRequestType.getScheduleRunDate()&&null==objToolRunRequestType.getVehicleObjid()&&null==objToolRunRequestType
						.getIsCurrentDateRun()){
					throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
				}else if(null==objToolRunRequestType.getScheduleRunDate()&&null!=objToolRunRequestType.getVehicleObjid()&&null==objToolRunRequestType
						.getIsCurrentDateRun()){
					throw new OMDInValidInputException(OMDConstants.REQUIRED_INPUT);
				}
				objRequestVO = new ToolRunRequestVO();	
				objRequestVO.setUserName(getRequestHeader(OMDConstants.USERID));
				if (null != objToolRunRequestType.getScheduleRunDate()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getScheduleRunDate())) {
					if(RMDCommonUtility.isValidFormat(RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE_FORMAT, objToolRunRequestType
							.getScheduleRunDate())){
					objRequestVO.setScheduleRunDate(objToolRunRequestType
							.getScheduleRunDate());
					}else{
						throw new OMDInValidInputException(OMDConstants.INVALID_DATE_FORMAT);
					}
				}
				if (null != objToolRunRequestType.getVehicleObjid()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getVehicleObjid())) {
					objRequestVO.setVehicleObjid(objToolRunRequestType
							.getVehicleObjid());
				}
				
				if (null != objToolRunRequestType.getIsCurrentDateRun()) {
					objRequestVO.setCurrentDateRun(objToolRunRequestType
							.getIsCurrentDateRun());
				}
				result = objRunToolsServiceIntf.runTools(objRequestVO);

			}else{
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return result;
	}
	
	/**
	 * @Author:
	 * @return:String
	 * @param :FindCaseServiceVO
	 * @throws:RMDServiceException
	 * @Description:This method is for updating case TITLE
	 */
	@POST
	@Path(OMDConstants.RUN_TOOLS_NOW)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String runToolsNow(final ToolRunRequestType objToolRunRequestType)
			throws RMDServiceException {
		String result = OMDConstants.FAILURE;
		ToolRunRequestVO objRequestVO = null;
		try {

			if (null != objToolRunRequestType) {
				
				objRequestVO = new ToolRunRequestVO();	
				objRequestVO.setUserName(getRequestHeader(OMDConstants.USERID));
				if (null != objToolRunRequestType.getScheduleRunDate()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getScheduleRunDate())) {
					if(RMDCommonUtility.isValidFormat(RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE_FORMAT, objToolRunRequestType
							.getScheduleRunDate())){
					objRequestVO.setScheduleRunDate(objToolRunRequestType
							.getScheduleRunDate());
					}else{
						throw new OMDInValidInputException(OMDConstants.INVALID_DATE_FORMAT);
					}
				}
				if (null != objToolRunRequestType.getAssetHeader()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getAssetHeader())) {
					objRequestVO.setAssetHeader(objToolRunRequestType
							.getAssetHeader());
				}else{
					throw new OMDInValidInputException(OMDConstants.ASSET_GROUP_NAME_NOT_PROVIDED);
				}
				if (null != objToolRunRequestType.getAssetNumber()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getAssetNumber())) {
					objRequestVO.setAssetNumber(objToolRunRequestType
							.getAssetNumber());
				}else{
					throw new OMDInValidInputException(OMDConstants.ASSET_NUMBER_NOT_PROVIDED);
				}
				if (null != objToolRunRequestType.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getCustomerId())) {
					objRequestVO.setCustomerId(objToolRunRequestType
							.getCustomerId());
				}else{
					throw new OMDInValidInputException(OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
				}
								
				if (null != objToolRunRequestType.getIsCurrentDateRun()) {
					objRequestVO.setCurrentDateRun(objToolRunRequestType
							.getIsCurrentDateRun());
				}
				
				if (null != objToolRunRequestType.getIncludeShopData()) {
					objRequestVO.setIncludeShopData(objToolRunRequestType
							.getIncludeShopData());
				}
				if (null != objToolRunRequestType.getLookbackRange()) {
					objRequestVO.setLookbackRange(objToolRunRequestType
							.getLookbackRange());
				}
				if (null != objToolRunRequestType.getDiagnosticService()) {
					objRequestVO.setDiagnosticService(objToolRunRequestType
							.getDiagnosticService());
				}
				result = objRunToolsServiceIntf.runToolsNow(objRequestVO);

			}else{
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return result;
	}

	/**
	 * This is the method used for fetching the diagnostic services
	 * 
	 * @param String
	 *            vehicleObjId
	 * @return String
	 * @throws List
	 *             <ElementResponseType>
	 */
	@GET
	@Path(OMDConstants.GET_DIAGNOSTIC_SERVICES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ElementResponseType> getDiagnosticServices(
			@PathParam(OMDConstants.VEHICLE_OBJID) final String vehicleObjId)
			throws RMDServiceException {
		List<ElementVO> lstElementVO = null;
		List<ElementResponseType> lstElementResponseType = null;
		ElementResponseType objElementResponseType = null;
		try {

			if (null != vehicleObjId
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(vehicleObjId)) {
				lstElementVO = objRunToolsServiceIntf
						.getDiagnosticServices(vehicleObjId);

				if (!lstElementVO.isEmpty()) {
					lstElementResponseType = new ArrayList<ElementResponseType>();
					for (ElementVO elementVo : lstElementVO) {
						objElementResponseType = new ElementResponseType();
						objElementResponseType.setId(elementVo.getId());
						objElementResponseType.setName(elementVo.getName());
						lstElementResponseType.add(objElementResponseType);
					}
				}
			} else {
				throw new OMDNoResultFoundException(
						OMDConstants.NORECORDFOUNDEXCEPTION);
			}

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return lstElementResponseType;
	}

	
	/**
	 * @Author:
	 * @return:String
	 * @param :FindCaseServiceVO
	 * @throws:RMDServiceException
	 * @Description:This method is for updating case TITLE
	 */
	@POST
	@Path(OMDConstants.RUN_ON_PAST_DATA)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String runToolsOnPastData(final ToolRunRequestType objToolRunRequestType)
			throws RMDServiceException {
		String result = OMDConstants.FAILURE;
		ToolRunRequestVO objRequestVO = null;
		try {

			if (null != objToolRunRequestType) {
				
				if(null==objToolRunRequestType.getVehicleObjid()){
					throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
				}
				objRequestVO = new ToolRunRequestVO();	
				objRequestVO.setUserName(getRequestHeader(OMDConstants.USERID));
				
				if (null != objToolRunRequestType.getVehicleObjid()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(objToolRunRequestType
										.getVehicleObjid())) {
					objRequestVO.setVehicleObjid(objToolRunRequestType
							.getVehicleObjid());
				}
				objRequestVO.setRunOnPastData(true);
				result = objRunToolsServiceIntf.runTools(objRequestVO);

			}else{
				throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return result;
	}
	
}
