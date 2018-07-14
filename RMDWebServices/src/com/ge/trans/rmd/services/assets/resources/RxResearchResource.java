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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.asset.service.intf.RxResearchServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.RxRepairResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.RxResearchResponseType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.RX_RESEARCH_SERVICE)
@Component
public class RxResearchResource {
	
	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;

	@Autowired
	private RxResearchServiceIntf objRxResearchServiceIntf;

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(RxResearchResource.class);
	@GET
	@Path(OMDConstants.GET_SEARCH_SOLUTION)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RxResearchResponseType> getSearchSolution(@Context UriInfo ui)
			throws RMDServiceException {
		List<RxResearchResponseType> responseList = new ArrayList<RxResearchResponseType>();
		List<RxResearchEOAVO> valueList = new ArrayList<RxResearchEOAVO>();
		RxResearchResponseType objRxResearchResponseType = null;
		RxResearchEOAVO queryParamVal = null;
			LOG.debug("*****getSearchSolution WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			queryParamVal = new RxResearchEOAVO();
			   final MultivaluedMap<String, String> queryParams = ui
	                    .getQueryParameters();
	            String selectBy = OMDConstants.EMPTY_STRING;
	            String condition = OMDConstants.EMPTY_STRING;
	            String searchVal = OMDConstants.EMPTY_STRING;
	            String rxType = OMDConstants.EMPTY_STRING;
	            try {
	                if (queryParams.containsKey(OMDConstants.SELECT_BY_OPTIONS)) {
	                	selectBy = queryParams.getFirst(OMDConstants.SELECT_BY_OPTIONS);
	                	queryParamVal.setSelectBy(selectBy);
	                }
	                if (queryParams.containsKey(OMDConstants.CONDITION)) {
	                    condition = queryParams.getFirst(OMDConstants.CONDITION);
	                    queryParamVal.setCondition(condition);
	                }
	                if (queryParams.containsKey(OMDConstants.SEARCH_VAL)) {
	                	searchVal = queryParams.getFirst(OMDConstants.SEARCH_VAL);
	                	queryParamVal.setSearchVal(searchVal);
	                }
	                if (queryParams.containsKey(OMDConstants.RX_TYPE)) {
	                	rxType = queryParams.getFirst(OMDConstants.RX_TYPE);
	                	queryParamVal.setRxType(rxType);
	                }
	                if (RMDCommonUtility.isNullOrEmpty(selectBy)) {
	                    throw new OMDInValidInputException(
	                            OMDConstants.SELECTBY_NOT_PROVIDED);
	                }

	                if (RMDCommonUtility.isNullOrEmpty(condition)) {
	                    throw new OMDInValidInputException(
	                            OMDConstants.CONDITION_NOT_PROVIDED);
	                }

	                if (RMDCommonUtility.isNullOrEmpty(searchVal)) {
	                    throw new OMDInValidInputException(
	                            OMDConstants.SEARCH_VAL_NOT_PROVIDED);
	                }
	                if (RMDCommonUtility.isNullOrEmpty(rxType)) {
	                    throw new OMDInValidInputException(
	                            OMDConstants.RXTYPE_NOT_PROVIDED);
	                }
			valueList = objRxResearchServiceIntf.getSearchSolution(queryParamVal);
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<RxResearchResponseType>(valueList.size());
			}
			for (RxResearchEOAVO objRxResearchEOAVO : valueList) {
				objRxResearchResponseType = new RxResearchResponseType();
				objRxResearchResponseType.setObjId(objRxResearchEOAVO.getRecomObjid());
				objRxResearchResponseType.setRxTitle(objRxResearchEOAVO.getRxTitle());
				responseList.add(objRxResearchResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
		
	}
	
	@GET
	@Path(OMDConstants.GET_SEARCH_GRAPH_DATA)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RxResearchResponseType> getGraphicalData(@Context UriInfo ui)
			throws RMDServiceException {
		List<RxResearchResponseType> responseList = new ArrayList<RxResearchResponseType>();
		List<RxResearchEOAVO> valueList = new ArrayList<RxResearchEOAVO>();
		RxResearchResponseType objRxResearchResponseType = null;
		RxResearchEOAVO queryParamVal = null;
			LOG.debug("*****getSearchSolution WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			queryParamVal = new RxResearchEOAVO();
			   final MultivaluedMap<String, String> queryParams = ui
	                    .getQueryParameters();
	            String selectBy = OMDConstants.EMPTY_STRING;
	            String condition = OMDConstants.EMPTY_STRING;
	            String searchVal = OMDConstants.EMPTY_STRING;
	            String rxType = OMDConstants.EMPTY_STRING;
	            String startDate = OMDConstants.EMPTY_STRING;
	            String endDate = OMDConstants.EMPTY_STRING;
	            String caseType = OMDConstants.EMPTY_STRING;
	            String customerId = OMDConstants.EMPTY_STRING;
	            String fleet = OMDConstants.EMPTY_STRING;
	            String model = OMDConstants.EMPTY_STRING;
	            String rnh = OMDConstants.EMPTY_STRING;
	            String isGoodFdbk = OMDConstants.EMPTY_STRING;
	            try {
	                if (queryParams.containsKey(OMDConstants.SELECT_BY_OPTIONS)) {
	                	selectBy = queryParams.getFirst(OMDConstants.SELECT_BY_OPTIONS);
	                	queryParamVal.setSelectBy(selectBy);
	                }
	                if (queryParams.containsKey(OMDConstants.CONDITION)) {
	                    condition = queryParams.getFirst(OMDConstants.CONDITION);
	                    queryParamVal.setCondition(condition);
	                }
	                if (queryParams.containsKey(OMDConstants.SEARCH_VAL)) {
	                	searchVal = queryParams.getFirst(OMDConstants.SEARCH_VAL);
	                	queryParamVal.setSearchVal(searchVal);
	                }
	                if (queryParams.containsKey(OMDConstants.RX_TYPE)) {
	                	rxType = queryParams.getFirst(OMDConstants.RX_TYPE);
	                	queryParamVal.setRxType(rxType);
	                }
	                if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
	                	startDate = queryParams.getFirst(OMDConstants.FROM_DATE);
	                	queryParamVal.setStartDate(startDate);
	                }
	                if (queryParams.containsKey(OMDConstants.TO_DATE)) {
	                	endDate = queryParams.getFirst(OMDConstants.TO_DATE);
	                    queryParamVal.setEndDate(endDate);
	                }
	                if (queryParams.containsKey(OMDConstants.CASE_TYPE)) {
	                	caseType = queryParams.getFirst(OMDConstants.CASE_TYPE);
	                	queryParamVal.setCaseType(caseType);
	                }
	                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
	                	customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
	                	queryParamVal.setCustomerId(customerId);
	                }
	                if (queryParams.containsKey(OMDConstants.FLEET)) {
	                	fleet = queryParams.getFirst(OMDConstants.FLEET);
	                	queryParamVal.setFleet(fleet);
	                }
	                if (queryParams.containsKey(OMDConstants.MODEL)) {
	                	model = queryParams.getFirst(OMDConstants.MODEL);
	                	queryParamVal.setModel(model);
	                }
	                if (queryParams.containsKey(OMDConstants.RNH)) {
	                	rnh = queryParams.getFirst(OMDConstants.RNH);
	                	queryParamVal.setRnh(rnh);
	                }
	                if (queryParams.containsKey(OMDConstants.ISGOODFDBK)) {
	                	isGoodFdbk = queryParams.getFirst(OMDConstants.ISGOODFDBK);
	                	queryParamVal.setIsGoodFdbk(isGoodFdbk);
	                }
			valueList = objRxResearchServiceIntf.getGraphicalData(queryParamVal);
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<RxResearchResponseType>(valueList.size());
			}
			for (RxResearchEOAVO objRxResearchEOAVO : valueList) {
				objRxResearchResponseType = new RxResearchResponseType();
				objRxResearchResponseType.setRepairCode(objRxResearchEOAVO.getRepairCode());
				objRxResearchResponseType.setRxCount(objRxResearchEOAVO.getRxCount());
				objRxResearchResponseType.setRepairDesc(objRxResearchEOAVO.getRepairDesc());
				responseList.add(objRxResearchResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
		
	}
	
	@GET
	@Path(OMDConstants.POPULATE_REPAIRCODE_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RxRepairResponseType> populateRepairCodeDetails(@Context UriInfo ui)
			throws RMDServiceException {
		List<RxRepairResponseType> responseList = new ArrayList<RxRepairResponseType>();
		List<RxRepairDetailsVO> valueList = new ArrayList<RxRepairDetailsVO>();
		RxRepairResponseType objRxRepairResponseType = null;
		RxResearchEOAVO queryParamVal = new RxResearchEOAVO();
			LOG.debug("*****populateRepairCodeDetails WEBSERVICE BEGIN**** "
					+ new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS)
							.format(new Date()));
			   final MultivaluedMap<String, String> queryParams = ui
	                    .getQueryParameters();
	            String repairCode = OMDConstants.EMPTY_STRING;
	            String searchVal = OMDConstants.EMPTY_STRING;
	            String rxType = OMDConstants.EMPTY_STRING;
	            String startDate = OMDConstants.EMPTY_STRING;
	            String endDate = OMDConstants.EMPTY_STRING;
	            String caseType = OMDConstants.EMPTY_STRING;
	            String customerId = OMDConstants.EMPTY_STRING;
	            String fleet = OMDConstants.EMPTY_STRING;
	            String model = OMDConstants.EMPTY_STRING;
	            String rnh = OMDConstants.EMPTY_STRING;
	            String initLoad = OMDConstants.EMPTY_STRING;
	            String isGoodFdbk = OMDConstants.EMPTY_STRING;
	            try {
	                if (queryParams.containsKey(OMDConstants.REPAIR_CODE)) {
	                	repairCode = queryParams.getFirst(OMDConstants.REPAIR_CODE);
	                	queryParamVal.setRepairCode(repairCode);
	                }
	                if (queryParams.containsKey(OMDConstants.SEARCH_VAL)) {
	                	searchVal = queryParams.getFirst(OMDConstants.SEARCH_VAL);
	                	queryParamVal.setSearchVal(searchVal);
	                }
	                if (queryParams.containsKey(OMDConstants.RX_TYPE)) {
	                	rxType = queryParams.getFirst(OMDConstants.RX_TYPE);
	                	queryParamVal.setRxType(rxType);
	                }
	                if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
	                	startDate = queryParams.getFirst(OMDConstants.FROM_DATE);
	                	queryParamVal.setStartDate(startDate);
	                }
	                if (queryParams.containsKey(OMDConstants.TO_DATE)) {
	                	endDate = queryParams.getFirst(OMDConstants.TO_DATE);
	                    queryParamVal.setEndDate(endDate);
	                }
	                if (queryParams.containsKey(OMDConstants.CASE_TYPE)) {
	                	caseType = queryParams.getFirst(OMDConstants.CASE_TYPE);
	                	queryParamVal.setCaseType(caseType);
	                }
	                if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
	                	customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
	                	queryParamVal.setCustomerId(customerId);
	                }
	                if (queryParams.containsKey(OMDConstants.FLEET)) {
	                	fleet = queryParams.getFirst(OMDConstants.FLEET);
	                	queryParamVal.setFleet(fleet);
	                }
	                if (queryParams.containsKey(OMDConstants.MODEL)) {
	                	model = queryParams.getFirst(OMDConstants.MODEL);
	                	queryParamVal.setModel(model);
	                }
	                if (queryParams.containsKey(OMDConstants.RNH)) {
	                	rnh = queryParams.getFirst(OMDConstants.RNH);
	                	queryParamVal.setRnh(rnh);
	                }
	                if (queryParams.containsKey(OMDConstants.INIT_LOAD)) {
	                	initLoad = queryParams.getFirst(OMDConstants.INIT_LOAD);
	                	queryParamVal.setInitLoad(initLoad);
	                }
	                if (queryParams.containsKey(OMDConstants.ISGOODFDBK)) {
	                	isGoodFdbk = queryParams.getFirst(OMDConstants.ISGOODFDBK);
	                	queryParamVal.setIsGoodFdbk(isGoodFdbk);
	                }
	               
			valueList = objRxResearchServiceIntf.populateRepairCodeDetails(queryParamVal);
			if (valueList != null && !valueList.isEmpty()) {
				responseList = new ArrayList<RxRepairResponseType>(valueList.size());
			}
			for (RxRepairDetailsVO objRxRepairDetailsVO : valueList) {
				objRxRepairResponseType = new RxRepairResponseType();
				objRxRepairResponseType.setRxCaseID(objRxRepairDetailsVO.getRxCaseID());
				objRxRepairResponseType.setCustomerId(objRxRepairDetailsVO.getCustomerId());
				objRxRepairResponseType.setVehicleNo(objRxRepairDetailsVO.getVehicleNo());
				objRxRepairResponseType.setCaseSuccess(objRxRepairDetailsVO.getCaseSuccess());
				objRxRepairResponseType.setRepairCode(objRxRepairDetailsVO.getRepairCode());
				objRxRepairResponseType.setMissCode(objRxRepairDetailsVO.getMissCode());
				objRxRepairResponseType.setGoodFdbk(objRxRepairDetailsVO.getGoodFdbk());
				objRxRepairResponseType.setRxTitle(objRxRepairDetailsVO.getRxTitle());
				objRxRepairResponseType.setRxUrgency(objRxRepairDetailsVO.getRxUrgency());
				objRxRepairResponseType.setCaseType(objRxRepairDetailsVO.getCaseType());
				objRxRepairResponseType.setRxOpenDate(objRxRepairDetailsVO.getRxOpenDate());
				objRxRepairResponseType.setRxCloseDate(objRxRepairDetailsVO.getRxCloseDate());
				objRxRepairResponseType.setRxFeedback(objRxRepairDetailsVO.getRxFeedback());
				objRxRepairResponseType.setRepairCodeDesc(objRxRepairDetailsVO.getRepairCodeDesc());
				responseList.add(objRxRepairResponseType);

			}
			valueList = null;

		} catch (Exception e) {
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}

		return responseList;
		
	}
}

