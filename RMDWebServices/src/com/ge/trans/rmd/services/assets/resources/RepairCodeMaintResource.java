/**
 * ============================================================
 * Classification: GE Confidential
 * File : RepairCodeMaintResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Sep 7, 2016
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.assets.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.RepairCodeMaintServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.RepairCodeDetailsType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_REPAIRCODE_MAINTENANCE)
@Component
public class RepairCodeMaintResource {
	
	@Autowired
	private RepairCodeMaintServiceIntf objRepairCodeMaintServiceIntf;

	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;
	
	public static final RMDLogger LOG = RMDLoggerHelper
	.getLogger(VehicleCfgResource.class);
	
	
	/**
	 * @Author:
	 * @param:uriParam
	 * @return:List<RepairCodeDetailsType>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching repair codes based on
	 *               search criteria.
	 */
	@GET
	@Path(OMDConstants.GET_REPAIRCODE_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RepairCodeDetailsType> getRepairCodesList(
			@Context final UriInfo uriParam) throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		String selectBy = null;
		String condition = null;
		String status = null;
		String models = null;
		String value = null;
		List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
		List<RepairCodeDetailsType> objRepairCodeDetailsTypeList =null;
		RepairCodeDetailsType objRepairCodeDetailsType = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.CREATCASE_SELECTBY)) {
					selectBy = queryParams
							.getFirst(OMDConstants.CREATCASE_SELECTBY);
				}
				if (queryParams.containsKey(OMDConstants.CREATCASE_CONDITION)) {
					condition = queryParams
							.getFirst(OMDConstants.CREATCASE_CONDITION);
				}
				if (queryParams.containsKey(OMDConstants.MODEL)) {
					models = queryParams.getFirst(OMDConstants.MODEL);
				}
				if (queryParams.containsKey(OMDConstants.STATUS)) {
					status = queryParams.getFirst(OMDConstants.STATUS);
				}
				if (queryParams.containsKey(OMDConstants.VALUE)) {
					value = queryParams.getFirst(OMDConstants.VALUE);
				}
				objRepairCodeEoaDetailsVOList = objRepairCodeMaintServiceIntf
						.getRepairCodesList(selectBy, condition, status,
								models, value);
				if (RMDCommonUtility
						.isCollectionNotEmpty(objRepairCodeEoaDetailsVOList)) {
					objRepairCodeDetailsTypeList=new ArrayList<RepairCodeDetailsType>(objRepairCodeEoaDetailsVOList.size());
					for (RepairCodeEoaDetailsVO obj : objRepairCodeEoaDetailsVOList) {
						objRepairCodeDetailsType = new RepairCodeDetailsType();
						objRepairCodeDetailsType.setRepairCode(obj
								.getRepairCode());
						objRepairCodeDetailsType.setRepaidCodeDesc(EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(obj
								.getRepairCodeDesc())));
						objRepairCodeDetailsType.setStatus(obj.getStatus());
						objRepairCodeDetailsType.setModels(obj.getModels());
						objRepairCodeDetailsTypeList
								.add(objRepairCodeDetailsType);
					}
					objRepairCodeEoaDetailsVOList=null;
				} /*else {
					throw new OMDNoResultFoundException(
							OMDConstants.NORECORDFOUNDEXCEPTION);
				}*/
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objRepairCodeDetailsTypeList;
	}
	
	/**
	 * @Author:
	 * @param:uriParam
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@GET
	@Path(OMDConstants.REPAIRCODE_VALIDATIONS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String repairCodeValidations(@Context final UriInfo uriParam)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		String repairCode = null;
		String repairCodeDesc = null;
		String flag = null;
		String result = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE)) {
					repairCode = queryParams.getFirst(OMDConstants.REPAIR_CODE);
				}
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE_DESC)) {
					repairCodeDesc = queryParams
							.getFirst(OMDConstants.REPAIR_CODE_DESC);
				}
				if (queryParams.containsKey(OMDConstants.FLAG)) {
					flag = queryParams.getFirst(OMDConstants.FLAG);
				}

				result = objRepairCodeMaintServiceIntf.repairCodeValidations(
						repairCode, repairCodeDesc, flag);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return result;
	}
	
	/**
	 * @Author:
	 * @param:uriParam
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@GET
	@Path(OMDConstants.ADD_REPAIR_CODES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addRepairCodes(@Context final UriInfo uriParam)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		String repairCode = null;
		String repairCodeDesc = null;
		String status = null;
		String model=null;
		String result = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE)) {
					repairCode = queryParams.getFirst(OMDConstants.REPAIR_CODE);
				}
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE_DESC)) {
					repairCodeDesc = queryParams
							.getFirst(OMDConstants.REPAIR_CODE_DESC);
				}
				if (queryParams.containsKey(OMDConstants.STATUS)) {
					status = queryParams.getFirst(OMDConstants.STATUS);
				}
				if (queryParams.containsKey(OMDConstants.MODEL)) {
					model = queryParams.getFirst(OMDConstants.MODEL);
				}
				result = objRepairCodeMaintServiceIntf.addRepairCodes(
						repairCode, repairCodeDesc, status,model);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return result;
	}
	
	/**
	 * @Author:
	 * @param:uriParam
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@GET
	@Path(OMDConstants.UPDATE_REPAIR_CODES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updateRepairCodes(@Context final UriInfo uriParam)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		String repairCode = null;
		String repairCodeDesc = null;
		String status = null;
		String model=null;
		String result = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE)) {
					repairCode = queryParams.getFirst(OMDConstants.REPAIR_CODE);
				}
				if (queryParams.containsKey(OMDConstants.REPAIR_CODE_DESC)) {
					repairCodeDesc = queryParams
							.getFirst(OMDConstants.REPAIR_CODE_DESC);
				}
				if (queryParams.containsKey(OMDConstants.STATUS)) {
					status = queryParams.getFirst(OMDConstants.STATUS);
				}
				if (queryParams.containsKey(OMDConstants.MODEL)) {
					model = queryParams.getFirst(OMDConstants.MODEL);
				}
				result = objRepairCodeMaintServiceIntf.updateRepairCodes(
						repairCode, repairCodeDesc, status,model);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return result;
	}
}
