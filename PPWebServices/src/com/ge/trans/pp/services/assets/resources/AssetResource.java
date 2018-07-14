/**
 * ============================================================
 * Classification: GE Confidential
 * File : AssetResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.resources;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.pp.services.assets.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.resources.BaseResource;
import com.ge.trans.pp.common.util.BeanUtility;
import com.ge.trans.pp.common.util.PPRMDWebServiceErrorHandler;
import com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf;
import com.ge.trans.pp.services.asset.service.valueobjects.MetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryHdrVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryParmDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHstDtlsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPMetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.StatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionVO;
import com.ge.trans.pp.services.assets.valueobjects.MetricsResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PPAssetHResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PPAssetHistHdrResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PPAssetHistoryDetailsResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PPAssetHistoryParmDtlsResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PPAssetHstRequestType;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetRequestType;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetStatusHstryResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PpAssetStatusResponseType;
import com.ge.trans.pp.services.assets.valueobjects.PpStateResponseType;
import com.ge.trans.pp.services.assets.valueobjects.StateInfoType;
import com.ge.trans.pp.services.assets.valueobjects.UnitConversionBeanResponseType;
import com.ge.trans.pp.services.assets.valueobjects.UnitConversionBeanType;
import com.ge.trans.pp.services.assets.valueobjects.UnitConversionRequestType;
import com.ge.trans.pp.services.assets.valueobjects.UnitConversionResponseType;
import com.ge.trans.pp.services.util.PPBeanUtility;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as assetservice Webservices and provide the
 *              Asset related funtionalities
 * @History :
 * 
 ******************************************************************************/

@Path(OMDConstants.ASSET_SERVICE)
@Component
public class AssetResource extends BaseResource {
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(AssetResource.class);

	public static final RMDLogger RMDLOGGER = RMDLoggerHelper
			.getLogger(AssetResource.class);

	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;
	@Autowired
	private PPAssetServiceIntf objPPServiceIntf;

	/**
	 * This Method is used for retrieving list of Model details for the
	 * parameters passed in UriInfo
	 * 
	 * @param ui
	 * @return List of Model - id,name
	 * @throws RMDServiceException
	 * 
	 *             Change made in method to retrive list of Model details based
	 *             on Customer, Fleet, Unit No and Config
	 */
	@POST
	@Path(OMDConstants.GET_CURRENT_PPSTATUS)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PpAssetStatusResponseType> getCurrentPPStatus(
			final PpAssetRequestType objAssetReqType)
			throws RMDServiceException {
		final PPAssetStatusVO objPPAssetStatusVO = new PPAssetStatusVO();
		List<PPAssetStatusResponseVO> ppAssetStatus = null;
		ListIterator<PPAssetStatusResponseVO> ppStatusLstIter = null;
		PPAssetStatusResponseVO objPPStatusResponse = null;
		PpAssetStatusResponseType objPPStatusResponseType = null;
		GregorianCalendar objGregorianCalendar;
		final List<PpAssetStatusResponseType> objPPStatusResponseTypeLst = new ArrayList<PpAssetStatusResponseType>();
		String timeZone = getDefaultTimeZone();
		try {

			String strLanguage = BeanUtility.stripXSSCharacters(objAssetReqType
					.getLanguage());
			objPPAssetStatusVO.setStrLanguage(strLanguage);
			// Copy product assets from request type to service VO
			if (null != objAssetReqType.getProdAssetMap()
					&& objAssetReqType.getProdAssetMap().size() > 0) {
				BeanUtility.copyCustomerAssetToServiceVO(
						objAssetReqType.getProdAssetMap(), objPPAssetStatusVO);
			}

			if (validatePPAssetStatus(objAssetReqType)) {
				// added for look back days
				if (objAssetReqType.getPpLookBackDays() > 0) {
					objPPAssetStatusVO.setPpLookBackDays(BeanUtility
							.stripXSSCharctersFromInteger(objAssetReqType
									.getPpLookBackDays()));
				}
				if (null != objAssetReqType.getAssetNumber()
						&& !objAssetReqType.getAssetNumber().isEmpty()) {
					objPPAssetStatusVO.setAssetNumber(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetNumber()));
				} else {
					objPPAssetStatusVO
							.setAssetNumber(OMDConstants.EMPTY_STRING);
				}

				if (null != objAssetReqType.getAssetGroupName()
						&& !objAssetReqType.getAssetGroupName().isEmpty()) {
					objPPAssetStatusVO.setAssetGroupName(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetGroupName()));
				} else {
					objPPAssetStatusVO
							.setAssetGroupName(OMDConstants.EMPTY_STRING);
				}

				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {
					objPPAssetStatusVO
							.setCustomerId(BeanUtility
									.stripXSSCharacters(objAssetReqType
											.getCustomerId()));
				} else {
					objPPAssetStatusVO.setCustomerId(OMDConstants.EMPTY_STRING);
				}

				ppAssetStatus = objPPServiceIntf
						.getCurrentPPStatus(objPPAssetStatusVO);
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
			if (RMDCommonUtility.isCollectionNotEmpty(ppAssetStatus)) {
				ppStatusLstIter = ppAssetStatus.listIterator();
				DatatypeFactory dtf = DatatypeFactory.newInstance();
				while (ppStatusLstIter.hasNext()) {
					objPPStatusResponse = (PPAssetStatusResponseVO) ppStatusLstIter
							.next();
					objPPStatusResponseType = new PpAssetStatusResponseType();

					PPBeanUtility.copyPPAsstStatResVOToPpAsstStatResType(
							objPPStatusResponse, objPPStatusResponseType);
					if (objPPStatusResponse.getSnapLatitude() != null) {
						objPPStatusResponseType
								.setSnapshotLatitude(objPPStatusResponse
										.getSnapLatitude());
					}
					if (objPPStatusResponse.getSnapLongitude() != null) {
						objPPStatusResponseType
								.setSnapshotLongitude(objPPStatusResponse
										.getSnapLongitude());
					}
					if (objPPStatusResponse.getSnapShotTime() != null) {
						objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(objPPStatusResponse
								.getSnapShotTime());
						RMDCommonUtility.setZoneOffsetTime(
								objGregorianCalendar, timeZone);
						objPPStatusResponseType.setSnapshotTime(dtf
								.newXMLGregorianCalendar(objGregorianCalendar));
					}
					if (objPPStatusResponse.getHistLatitude() != null) {
						objPPStatusResponseType
								.setHistLatitude(objPPStatusResponse
										.getHistLatitude());
					}
					if (objPPStatusResponse.getHistLongitude() != null) {
						objPPStatusResponseType
								.setHistLongitude(objPPStatusResponse
										.getHistLongitude());
					}
					if (objPPStatusResponse.getHistTime() != null) {
						objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(objPPStatusResponse
								.getHistTime());
						RMDCommonUtility.setZoneOffsetTime(
								objGregorianCalendar, timeZone);
						objPPStatusResponseType.setHistTime(dtf
								.newXMLGregorianCalendar(objGregorianCalendar));
					}
					if (objPPStatusResponse.getDtLstFuelReadingRcvd() != null) {
						objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(objPPStatusResponse
								.getDtLstFuelReadingRcvd());
						RMDCommonUtility.setZoneOffsetTime(
								objGregorianCalendar, timeZone);
						objPPStatusResponseType.setLstFuelReadingRcvd(dtf
								.newXMLGregorianCalendar(objGregorianCalendar));
					}
					if (objPPStatusResponse.getDtMessageTime() != null) {

						objGregorianCalendar = RMDCommonUtility
								.getGMTGregorianCalendar(objPPStatusResponse
										.getDtMessageTime());

						objPPStatusResponseType.setMessageTime(dtf
								.newXMLGregorianCalendar(objGregorianCalendar));
					}

					objPPStatusResponseTypeLst.add(objPPStatusResponseType);
				}
			} /*
			 * else { throw new OMDNoResultFoundException(
			 * OMDConstants.NORECORDFOUNDEXCEPTION);
			 * 
			 * }
			 */
		} catch (Exception ex) {

			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("PPAssetResource : Inside getCurrentPPStatus() method:::END");
		return objPPStatusResponseTypeLst;

	}

	/**
	 * This Method is used for retrieving list of Model details for the
	 * parameters passed in UriInfo
	 * 
	 * @param ui
	 * @return List of Model - id,name
	 * @throws RMDServiceException
	 * 
	 *             Change made in method to retrive list of Model details based
	 *             on Customer, Fleet, Unit No and Config
	 */
	@GET
	@Path(OMDConstants.GET_PPSTATUS_HISTORY)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PPAssetHistoryDetailsResponseType> getPPStatusHistory(
			@Context final UriInfo ui) throws RMDServiceException {

		MultivaluedMap<String, String> queryParams = null;
		final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO = new PPAssetStatusHistoryVO();
		List<PPAssetStatusHistoryVO> ppAssetStatusHstryList = null;
		ListIterator<PPAssetStatusHistoryVO> ppStatusLstIter = null;
		PpAssetStatusHstryResponseType objPpAssetStatusHstryResponse = null;
		String timeZone = getDefaultTimeZone();
		final List<PpAssetStatusHstryResponseType> objPPStatusHstryResponseTypeLst = new ArrayList<PpAssetStatusHstryResponseType>();
		int recordCount = 0;

		List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
		PPAssetHistoryDetailsResponseType rowList = new PPAssetHistoryDetailsResponseType();
		List<PPAssetHistoryDetailsResponseType> objPPAssetHistoryDetailsResponseTypeList = new ArrayList<PPAssetHistoryDetailsResponseType>();
		PPAssetHResponseType ppAssetHResponseType = null;
		PPAssetHistHdrResponseType ppAssetHistHdrResponseType = null;

		try {
			queryParams = ui.getQueryParameters();

			// copying the query params
			if (queryParams.containsKey(OMDConstants.PP_VEHICLE_NUMBER)) {
				objPPAssetStatusHistoryVO.setAssetNumber(queryParams
						.getFirst(OMDConstants.PP_VEHICLE_NUMBER));
			} else {
				objPPAssetStatusHistoryVO
						.setAssetNumber(OMDConstants.EMPTY_STRING);
			}

			if (queryParams.containsKey(OMDConstants.PP_VEHICLE_HEADER)) {
				objPPAssetStatusHistoryVO.setAssetGroupName(queryParams
						.getFirst(OMDConstants.PP_VEHICLE_HEADER));
			} else {
				objPPAssetStatusHistoryVO
						.setAssetGroupName(OMDConstants.EMPTY_STRING);
			}

			if (queryParams.containsKey(OMDConstants.PP_BUS_ORG_ID)) {
				objPPAssetStatusHistoryVO.setCustomerId(queryParams
						.getFirst(OMDConstants.PP_BUS_ORG_ID));
			} else {
				objPPAssetStatusHistoryVO
						.setCustomerId(OMDConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.FLAG_MP_DATA)) {
				objPPAssetStatusHistoryVO.setFlagMPData(queryParams
						.getFirst(OMDConstants.FLAG_MP_DATA));
			}
			// unit conversion via user preference
			if (queryParams.containsKey(OMDConstants.CONV_PREFERENCE)) {
				objPPAssetStatusHistoryVO.setConversionPreference(queryParams
						.getFirst(OMDConstants.CONV_PREFERENCE));
			}
			// Set from date, to date and no of days
			if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
				objPPAssetStatusHistoryVO.setFromDate(queryParams
						.getFirst(OMDConstants.FROM_DATE));
			}

			if (queryParams.containsKey(OMDConstants.TO_DATE)) {
				objPPAssetStatusHistoryVO.setToDate(queryParams
						.getFirst(OMDConstants.TO_DATE));
			}

			if (queryParams.containsKey(OMDConstants.PP_NO_OF_DAYS)) {
				objPPAssetStatusHistoryVO.setNoOfDays(queryParams
						.getFirst(OMDConstants.PP_NO_OF_DAYS));
			} else {
				objPPAssetStatusHistoryVO
						.setNoOfDays(OMDConstants.EMPTY_STRING);
			}

			if (null != getRequestHeader(OMDConstants.LANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.LANGUAGE))) {
				objPPAssetStatusHistoryVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

			} else if (null != getRequestHeader(OMDConstants.USERLANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.USERLANGUAGE))) {
				objPPAssetStatusHistoryVO
						.setStrLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
			} else {
				objPPAssetStatusHistoryVO
						.setStrLanguage(RMDCommonConstants.ENGLISH_LANGUAGE);
			}
			if (queryParams.containsKey(OMDConstants.START_ROW)) {
				objPPAssetStatusHistoryVO.setStartRow(queryParams
						.getFirst(OMDConstants.START_ROW));
			} else {
				objPPAssetStatusHistoryVO
						.setStartRow(OMDConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.RECORDSTARTINDEX)) {
				objPPAssetStatusHistoryVO.setRecordsPerPage(queryParams
						.getFirst(OMDConstants.RECORDSTARTINDEX));
			} else {
				objPPAssetStatusHistoryVO
						.setRecordsPerPage(OMDConstants.EMPTY_STRING);
			}

			/*
			 * ppAssetStatusHstryList = objPPServiceIntf
			 * .getPPStatusHistory(objPPAssetStatusHistoryVO);
			 */

			ppAssetHistoryDtlsVOLst = objPPServiceIntf
					.getPPStatusHistory(objPPAssetStatusHistoryVO);
			// System.out.println("------------------- IN AssetResource ----------------------- "+ppAssetHistoryDtlsVOLst.toString());
			if (RMDCommonUtility.isCollectionNotEmpty(ppAssetHistoryDtlsVOLst)) {
				for (Iterator iterator = ppAssetHistoryDtlsVOLst.iterator(); iterator
						.hasNext();) {
					PPAssetHistoryDetailsVO ppAssetHistoryDetailsVO = (PPAssetHistoryDetailsVO) iterator
							.next();
					rowList = new PPAssetHistoryDetailsResponseType();
					recordCount = ppAssetHistoryDetailsVO.getTotalRecord();

					for (Iterator iterator2 = ppAssetHistoryDetailsVO
							.getPpAssetHistoryHdrlst().iterator(); iterator2
							.hasNext();) {
						PPAssetHistoryHdrVO ppAssetHistoryHdrVO = (PPAssetHistoryHdrVO) iterator2
								.next();
						ppAssetHistHdrResponseType = new PPAssetHistHdrResponseType();
						ppAssetHistHdrResponseType
								.setDisplayName(ppAssetHistoryHdrVO
										.getDisplayName());
						ppAssetHistHdrResponseType
								.setExportColumnName(ppAssetHistoryHdrVO
										.getExportColumnName());
						rowList.getPpAssetHistoryHdrlst().add(
								ppAssetHistHdrResponseType);
					}

					for (Iterator iterator3 = ppAssetHistoryDetailsVO
							.getPpAssetHistorylst().iterator(); iterator3
							.hasNext();) {
						PPAssetHistoryVO ppAssetHistoryVO = (PPAssetHistoryVO) iterator3
								.next();
						ppAssetHResponseType = new PPAssetHResponseType();
						List<PPAssetHistoryParmDetailsVO> ppAssetHistoryVOlst = ppAssetHistoryVO
								.getPpAssetHistoryVOlst();
						List<PPAssetHistoryParmDtlsResponseType> arl = new ArrayList<PPAssetHistoryParmDtlsResponseType>();
						for (Iterator iterator2 = ppAssetHistoryVOlst
								.iterator(); iterator2.hasNext();) {
							PPAssetHistoryParmDtlsResponseType objPPAssetHstParmDtlsRTpe = new PPAssetHistoryParmDtlsResponseType();
							PPAssetHistoryParmDetailsVO ppAssetHistoryParmDetailsVO = (PPAssetHistoryParmDetailsVO) iterator2
									.next();
							objPPAssetHstParmDtlsRTpe
									.setColumnName(ppAssetHistoryParmDetailsVO
											.getColumnName());
							objPPAssetHstParmDtlsRTpe
									.setColumnValue(ppAssetHistoryParmDetailsVO
											.getColumnValue());
							arl.add(objPPAssetHstParmDtlsRTpe);
						}
						ppAssetHResponseType.setPpAssetHistoryVOlst(arl);
						rowList.getPpAssetHistorylst()
								.add(ppAssetHResponseType);
						rowList.setRecordCount(recordCount);
					}
					objPPAssetHistoryDetailsResponseTypeList.add(rowList);
				}
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("PPAssetResource : Inside getPPStatusHistory() method:::END");
		return objPPAssetHistoryDetailsResponseTypeList;

	}

	/**
	 * This Method is used for retrieving states and country for assets
	 * parameters passed in UriInfo
	 * 
	 * @param ui
	 * @return List of country,states,latitude,longitude
	 * @throws RMDServiceException
	 * 
	 */
	@GET
	@Path(OMDConstants.GET_PPSTATES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PpStateResponseType> getPPStates(@Context final UriInfo ui)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		PPStatesResponseVO objPPStateRespVO = new PPStatesResponseVO();
		List<PPStatesResponseVO> objPPStateRespVOLst = null;
		PpStateResponseType objPPStateResponseType = null;
		PPStatesVO objPPStatesVO = new PPStatesVO();
		ListIterator<PPStatesResponseVO> ppStatesLstIter = null;
		StateInfoType objStateInfo = null;
		final List<PpStateResponseType> objPPStateResponseTypeLst = new ArrayList<PpStateResponseType>();
		try {
			queryParams = ui.getQueryParameters();

			// copying the header params
			if (null != getRequestHeader(OMDConstants.LANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.LANGUAGE))) {
				objPPStatesVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

			} else if (null != getRequestHeader(OMDConstants.USERLANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.USERLANGUAGE))) {
				objPPStatesVO
						.setStrLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
			} else {
				objPPStatesVO
						.setStrLanguage(RMDCommonConstants.ENGLISH_LANGUAGE);
			}

			objPPStateRespVOLst = objPPServiceIntf.getPPStates(objPPStatesVO);

			if (RMDCommonUtility.isCollectionNotEmpty(objPPStateRespVOLst)) {
				ppStatesLstIter = objPPStateRespVOLst.listIterator();
				while (ppStatesLstIter.hasNext()) {
					objPPStateRespVO = (PPStatesResponseVO) ppStatesLstIter
							.next();

					objPPStateResponseType = new PpStateResponseType();

					/*
					 * BeanUtility.copyBeanProperty(objPPStateRespVO,
					 * objPPStateResponseType);
					 */
					objPPStateResponseType.setCountryName(objPPStateRespVO
							.getCountryName());

					if (objPPStateRespVO.getStateList() != null
							&& !objPPStateRespVO.getStateList().isEmpty()) {

						objStateInfo = new StateInfoType();
						String stateName = null;
						List<String> statenameLst = new ArrayList<String>();
						for (StatesVO objStatesVO : objPPStateRespVO
								.getStateList()) {
							stateName = objStatesVO.getStateName();
							statenameLst.add(stateName);
						}
						objStateInfo.setStateName(statenameLst);
						objPPStateResponseType.setStateInfo(objStateInfo);
					}

					objPPStateResponseTypeLst.add(objPPStateResponseType);
				}

			} /*
			 * else { throw new OMDNoResultFoundException(
			 * OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER.debug("PPAssetResource : Inside getPPStates() method:::END");
		return objPPStateResponseTypeLst;

	}

	/*
	 * This method is used for retrieving Asset details for the pinpoint passed
	 * in UriInfo
	 * 
	 * @param ui
	 * 
	 * @return List of AssetResponseType
	 * 
	 * @throws RMDServiceException
	 * 
	 * Change made in method to retrive list of Model details based on Customer,
	 * Fleet, Model and Config
	 */
	@POST
	@Path(OMDConstants.GET_PP_ASSETS)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PpAssetResponseType> getPPAssets(
			final PpAssetRequestType objAssetReqType)
			throws RMDServiceException {

		Map<String, List<Long>> arlAssets = null;
		List<PpAssetResponseType> arlAssetResponseType = new ArrayList<PpAssetResponseType>();
		List<PPAssetDetailsVO> arlAssetResponseVO = null;
		PPAssetDetailsVO assetVO = new PPAssetDetailsVO();
		PpAssetResponseType assetResponse = null;
		try {

			if (validatePPAssetStatus(objAssetReqType)) {
				if (null != objAssetReqType.getAssetGroupName()
						&& !objAssetReqType.getAssetGroupName().isEmpty()) {
					assetVO.setAssetGrpName(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetGroupName()));
				} else {
					assetVO.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {
					assetVO.setCustomerId(BeanUtility
							.stripXSSCharacters(objAssetReqType.getCustomerId()));
				} else {
					assetVO.setCustomerId(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getAssetNumber()
						&& !objAssetReqType.getAssetNumber().isEmpty()) {
					assetVO.setAssetNumber(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetNumber()));
				} else {
					assetVO.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
				}

				arlAssetResponseVO = objPPServiceIntf.getPPAssets(assetVO);
				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetResponseVO)) {
					for (PPAssetDetailsVO tempVO : arlAssetResponseVO) {
						assetResponse = new PpAssetResponseType();
						assetResponse.setCustomerId(BeanUtility
								.stripXSSCharacters(tempVO.getCustomerId()));
						assetResponse.setAssetGroupName(BeanUtility
								.stripXSSCharacters(tempVO.getAssetGrpName()));
						assetResponse.setAssetNumber(BeanUtility
								.stripXSSCharacters(tempVO.getAssetNumber()));
						arlAssetResponseType.add(assetResponse);
					}
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlAssetResponseType;
	}

	/**
	 * This method is used to validate the data passed as an input for getting
	 * pinpoint screen
	 * 
	 * @param objAssetReqType
	 * @return
	 */
	public static boolean validatePPAssetStatus(
			final PpAssetRequestType objAssetReqType) {

		if (null != objAssetReqType.getAssetNumber()
				&& !objAssetReqType.getAssetNumber().isEmpty()) {
			if (!AppSecUtil.checkAlphaNumericUnderscore(objAssetReqType
					.getAssetNumber())) {
				return false;
			}
		}

		if (null != objAssetReqType.getCustomerId()
				&& !objAssetReqType.getCustomerId().isEmpty()) {
			if (!AppSecUtil.checkAlphaNumericComma(BeanUtility
					.stripXSSCharacters(objAssetReqType.getCustomerId()))) {
				return false;
			}
		}

		if (null != objAssetReqType.getAssetGroupName()
				&& !objAssetReqType.getAssetGroupName().isEmpty()) {
			if (!AppSecUtil.checkAlphabets(BeanUtility
					.stripXSSCharacters(objAssetReqType.getAssetGroupName()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Status value for
	 *              particular Road Number.
	 * 
	 */
	@GET
	@Path(OMDConstants.GET_STATUS_VALUE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getStatusValue(@Context final UriInfo uriParam)
			throws RMDServiceException {
		String rnhId = OMDConstants.RNH_ID;
		String rnId = OMDConstants.RN_ID;
		MultivaluedMap<String, String> queryParams = null;
		String statusValue = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.RNH_ID)) {
					rnhId = queryParams.getFirst(OMDConstants.RNH_ID);
				}
				if (queryParams.containsKey(OMDConstants.RN_ID)) {
					rnId = queryParams.getFirst(OMDConstants.RN_ID);
				}
				statusValue = objPPServiceIntf.getStatusValue(rnhId, rnId);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return statusValue;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Status value for
	 *              particular Road Number.
	 * 
	 */
	@GET
	@Path(OMDConstants.GET_PPASSET_MODEL_VALUE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getPPAssetModel(@Context final UriInfo uriParam)
			throws RMDServiceException {
		String rnhId = OMDConstants.RNH_ID;
		String rnId = OMDConstants.RN_ID;
		MultivaluedMap<String, String> queryParams = null;
		String modelValue = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.RNH_ID)) {
					rnhId = queryParams.getFirst(OMDConstants.RNH_ID);
				}
				if (queryParams.containsKey(OMDConstants.RN_ID)) {
					rnId = queryParams.getFirst(OMDConstants.RN_ID);
				}
				modelValue = objPPServiceIntf.getPPAssetModel(rnhId, rnId);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return modelValue;
	}

	/**
	 * 
	 * @param
	 * @return
	 * @Description * This method is used to update the Status value for
	 *              particular Road Number.
	 * 
	 */
	@POST
	@Path(OMDConstants.CHANGE_STATUS)
	@Consumes(MediaType.APPLICATION_XML)
	public void changeStatus(PPAssetHstRequestType objPPAssetHstRequestType)
			throws RMDServiceException {
		String rnhId = null;
		String rnId = null;
		String status = null;
		try {
			if (objPPAssetHstRequestType.getStatus() != null) {
				status = objPPAssetHstRequestType.getStatus();
			}
			if (objPPAssetHstRequestType.getRnhId() != null) {
				rnhId = objPPAssetHstRequestType.getRnhId();
			}
			if (objPPAssetHstRequestType.getRnId() != null) {
				rnId = objPPAssetHstRequestType.getRnId();
			} else {
				throw new OMDInValidInputException(
						OMDConstants.NO_PARAMETERS_FOUND);
			}
			objPPServiceIntf.changeStatus(status, rnhId, rnId);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	}

	/**
	 * 
	 * @param
	 * @return List<PPAssetHistoryDetailsResponseType>
	 * @Description * This method is used to get the PP Asset History data for
	 *              Customer.
	 * 
	 */
	@GET
	@Path(OMDConstants.GET_ASSET_HISTORY)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PPAssetHistoryDetailsResponseType> getAssetHistory(
			@Context final UriInfo uriParam) throws RMDServiceException {
		String rnhId = OMDConstants.RNH_ID;
		String rnId = OMDConstants.RN_ID;
		String customerName = OMDConstants.CUSTOMER_NAME;
		String days = OMDConstants.NO_OF_DAYS;
		String fromDate = OMDConstants.FROM_DATE;
		String toDate = OMDConstants.TO_DATE;
		String sortBy = null;
		MultivaluedMap<String, String> queryParams = null;
		List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
		List<PPAssetHistoryDetailsResponseType> objPPAssetHistoryDetailsResponseTypeList = new ArrayList<PPAssetHistoryDetailsResponseType>();
		PPAssetHistHdrResponseType ppAssetHistHdrResponseType = null;
		PPAssetHResponseType ppAssetHResponseType = null;
		PPAssetHistoryDetailsResponseType rowList = new PPAssetHistoryDetailsResponseType();
		List<PPAssetHResponseType> ppAssetHistoryList = new ArrayList<PPAssetHResponseType>();
		PPAssetHstDtlsVO objPPAssetHstDtlsVO = new PPAssetHstDtlsVO();
		try {
			queryParams = uriParam.getQueryParameters();

			if (queryParams.containsKey(OMDConstants.RNH_ID)) {
				objPPAssetHstDtlsVO.setRnhId(queryParams
						.getFirst(OMDConstants.RNH_ID));
			}
			if (queryParams.containsKey(OMDConstants.RN_ID)) {
				objPPAssetHstDtlsVO.setRnId(queryParams
						.getFirst(OMDConstants.RN_ID));
			}
			if (queryParams.containsKey(OMDConstants.CUSTOMER_NAME)) {
				objPPAssetHstDtlsVO.setCustomerName(queryParams
						.getFirst(OMDConstants.CUSTOMER_NAME));
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
			if (queryParams.containsKey(OMDConstants.NO_OF_DAYS)) {
				objPPAssetHstDtlsVO.setDays(queryParams
						.getFirst(OMDConstants.NO_OF_DAYS));
			}
			if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
				objPPAssetHstDtlsVO.setFromDate(queryParams
						.getFirst(OMDConstants.FROM_DATE));
			}
			if (queryParams.containsKey(OMDConstants.TO_DATE)) {
				objPPAssetHstDtlsVO.setToDate(queryParams
						.getFirst(OMDConstants.TO_DATE));
			}
			if (queryParams.containsKey(OMDConstants.SORT_BY)) {
				objPPAssetHstDtlsVO.setSortBy(queryParams
						.getFirst(OMDConstants.SORT_BY));
			}
			if (queryParams.containsKey(OMDConstants.ROLE_ID)) {
                objPPAssetHstDtlsVO.setRoleId(queryParams
                        .getFirst(OMDConstants.ROLE_ID));
            }
			ppAssetHistoryDtlsVOLst = objPPServiceIntf
					.getAssetHistory(objPPAssetHstDtlsVO);

			if (RMDCommonUtility.isCollectionNotEmpty(ppAssetHistoryDtlsVOLst)) {
				for (Iterator iterator = ppAssetHistoryDtlsVOLst.iterator(); iterator
						.hasNext();) {
					PPAssetHistoryDetailsVO ppAssetHistoryDetailsVO = (PPAssetHistoryDetailsVO) iterator
							.next();
					rowList = new PPAssetHistoryDetailsResponseType();
					for (Iterator iterator2 = ppAssetHistoryDetailsVO
							.getPpAssetHistoryHdrlst().iterator(); iterator2
							.hasNext();) {
						PPAssetHistoryHdrVO ppAssetHistoryHdrVO = (PPAssetHistoryHdrVO) iterator2
								.next();
						ppAssetHistHdrResponseType = new PPAssetHistHdrResponseType();

						ppAssetHistHdrResponseType
								.setDisplayName(ppAssetHistoryHdrVO
										.getDisplayName());
						ppAssetHistHdrResponseType
								.setColumnAliasName(ppAssetHistoryHdrVO
										.getColumnAliasName());
						ppAssetHistHdrResponseType
								.setTableHdType(ppAssetHistoryHdrVO
										.getTableHdType());
						ppAssetHistHdrResponseType
								.setAsetSrchType(ppAssetHistoryHdrVO
										.getAsetSrchType());
						ppAssetHistHdrResponseType
								.setToolTipInfo(ppAssetHistoryHdrVO
										.getToolTipInfo());
						ppAssetHistHdrResponseType
								.setColColp(ppAssetHistoryHdrVO.getColColp());
						ppAssetHistHdrResponseType
								.setSortOrder(ppAssetHistoryHdrVO
										.getSortOrder());

						rowList.getPpAssetHistoryHdrlst().add(
								ppAssetHistHdrResponseType);
					}
					for (Iterator iterator3 = ppAssetHistoryDetailsVO
							.getPpAssetHistorylst().iterator(); iterator3
							.hasNext();) {
						PPAssetHistoryVO ppAssetHistoryVO = (PPAssetHistoryVO) iterator3
								.next();
						ppAssetHResponseType = new PPAssetHResponseType();
						List<PPAssetHistoryParmDetailsVO> ppAssetHistoryVOlst = ppAssetHistoryVO
								.getPpAssetHistoryVOlst();
						List<PPAssetHistoryParmDtlsResponseType> arl = new ArrayList<PPAssetHistoryParmDtlsResponseType>();
						for (Iterator iterator2 = ppAssetHistoryVOlst
								.iterator(); iterator2.hasNext();) {
							PPAssetHistoryParmDtlsResponseType objPPAssetHstParmDtlsRTpe = new PPAssetHistoryParmDtlsResponseType();
							PPAssetHistoryParmDetailsVO ppAssetHistoryParmDetailsVO = (PPAssetHistoryParmDetailsVO) iterator2
									.next();
							objPPAssetHstParmDtlsRTpe
									.setColumnName(ppAssetHistoryParmDetailsVO
											.getColumnName());
							objPPAssetHstParmDtlsRTpe
									.setColumnValue(ppAssetHistoryParmDetailsVO
											.getColumnValue());
							arl.add(objPPAssetHstParmDtlsRTpe);
						}
						ppAssetHResponseType.setPpAssetHistoryVOlst(arl);
						rowList.getPpAssetHistorylst()
								.add(ppAssetHResponseType);
					}
					objPPAssetHistoryDetailsResponseTypeList.add(rowList);
				}
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objPPAssetHistoryDetailsResponseTypeList;
	}

	/**
	 * @Author:
	 * @param: PpAssetRequestType
	 * @return:List<PpAssetResponseType>
	 * @throws:RMDWebException,Exception
	 * @Description: This method is used for fetching the ATS Assets from
	 *               database.
	 */

	@POST
	@Path(OMDConstants.GET_PP_SEARCH_ASSETS)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PpAssetResponseType> getPPSearchAssets(
			final PpAssetRequestType objAssetReqType)
			throws RMDServiceException {

		List<PPAssetDetailsVO> eoaAssetsLst = new ArrayList<PPAssetDetailsVO>();
		PPAssetStatusVO objAssetServiceVO;
		final List<PpAssetResponseType> arlAssetResponseType = new ArrayList<PpAssetResponseType>();
		try {
			objAssetServiceVO = new PPAssetStatusVO();

			if (validatePPAssetStatus(objAssetReqType)) {

				if (null != objAssetReqType.getAssetGroupName()
						&& !objAssetReqType.getAssetGroupName().isEmpty()) {
					objAssetServiceVO.setAssetGroupName(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetGroupName()));
				} else {
					objAssetServiceVO
							.setAssetGroupName(RMDCommonConstants.EMPTY_STRING);
				}

				if (null != objAssetReqType.getAssetNumber()
						&& !objAssetReqType.getAssetNumber().isEmpty()) {
					objAssetServiceVO.setAssetNumber(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetNumber()));
				} else {
					objAssetServiceVO
							.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {

					if (RMDCommonConstants.ALL_CUSTOMER
							.equalsIgnoreCase(objAssetReqType.getCustomerId())) {
						objAssetServiceVO
								.setCustomerId(RMDCommonConstants.EMPTY_STRING);
					} else {
						objAssetServiceVO.setCustomerId(BeanUtility
								.stripXSSCharacters(objAssetReqType
										.getCustomerId()));
					}

				} else {
					objAssetServiceVO
							.setCustomerId(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getLanguage()
						&& !objAssetReqType.getLanguage().isEmpty()) {
					objAssetServiceVO
							.setLanguage(objAssetReqType.getLanguage());
				} else {
					objAssetServiceVO
							.setLanguage(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getProdAssetMap()
						&& objAssetReqType.getProdAssetMap().size() > 0) {
					BeanUtility.copyCustomerAssetToServiceVO(
							objAssetReqType.getProdAssetMap(),
							objAssetServiceVO);
				}
				eoaAssetsLst = objPPServiceIntf
						.getPPSearchAssets(objAssetServiceVO);
				for (Iterator iterator = eoaAssetsLst.iterator(); iterator
						.hasNext();) {

					PPAssetDetailsVO ppAssetDetailsVO = (PPAssetDetailsVO) iterator
							.next();
					PpAssetResponseType responseType = new PpAssetResponseType();
					responseType.setAssetNumber(ppAssetDetailsVO
							.getAssetNumber());
					responseType.setAssetGroupName(ppAssetDetailsVO
							.getAssetGrpName());
					responseType.setAssetGroupNumber(ppAssetDetailsVO
							.getAssetGroupNumber());
					responseType.setCustomerId(EsapiUtil
							.stripXSSCharacters(ppAssetDetailsVO
									.getCustomerId()));
					responseType.setModel(EsapiUtil
							.stripXSSCharacters(ppAssetDetailsVO.getModel()));
					responseType.setFleet(EsapiUtil
							.stripXSSCharacters(ppAssetDetailsVO.getFleet()));

					arlAssetResponseType.add(responseType);
				}

			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}

			Collections.sort(arlAssetResponseType, new Comparator() {
				public int compare(Object o1, Object o2) {
					PpAssetResponseType a1 = (PpAssetResponseType) o1;
					PpAssetResponseType a2 = (PpAssetResponseType) o2;
					if (a1.getAssetNumber().length() < a2.getAssetNumber()
							.length()) {
						return -1;
					} else if (a1.getAssetNumber().length() > a2
							.getAssetNumber().length()) {
						return 1;
					}
					return a1.getAssetNumber().compareTo(a2.getAssetNumber());
				}
			});

		} catch (Exception e) {
			PPRMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return arlAssetResponseType;
	}

	@GET
	@Path(OMDConstants.GET_CONVERSION_UNITS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MetricsResponseType> getMetricsConversion(
			@Context final UriInfo ui) throws RMDServiceException {

		MultivaluedMap<String, String> queryParams = null;
		List<PPMetricsVO> objPPAssetStatusHistoryVO = null;
		MetricsResponseType objPpAssetStatusHstryResponse = null;
		List<MetricsResponseType> aurizon = new ArrayList<MetricsResponseType>();
		try {
			queryParams = ui.getQueryParameters();

			// copying the query params

			objPPAssetStatusHistoryVO = objPPServiceIntf.getMetricsConversion();

			if (null != objPPAssetStatusHistoryVO) {

				for (PPMetricsVO pPAssetStatusHistoryVO : objPPAssetStatusHistoryVO) {
					objPpAssetStatusHstryResponse = new MetricsResponseType();
					objPpAssetStatusHstryResponse
							.setToUnit(pPAssetStatusHistoryVO.getToUnit());
					objPpAssetStatusHstryResponse
							.setConvReq(pPAssetStatusHistoryVO.getConvReq());
					objPpAssetStatusHstryResponse
							.setFromUnit(pPAssetStatusHistoryVO.getFromUnit());
					objPpAssetStatusHstryResponse
							.setCustomerID(pPAssetStatusHistoryVO
									.getCustomerName());
					objPpAssetStatusHstryResponse
							.setColumnName(pPAssetStatusHistoryVO
									.getColumnName());
					objPpAssetStatusHstryResponse
							.setScreenName(pPAssetStatusHistoryVO
									.getScreenName());
					aurizon.add(objPpAssetStatusHstryResponse);
				}
			}/*
			 * else { throw new OMDNoResultFoundException(
			 * OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("PPAssetResource : Inside getAurizonUnits() method:::END");
		return aurizon;

	}

	@GET
	@Path(OMDConstants.GET_PP_HEADERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getPPAssetHistoryHeaders(@Context final UriInfo ui)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		String responseJson = null;
		final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO = new PPAssetStatusHistoryVO();
		try {
			queryParams = ui.getQueryParameters();

			// copying the query params
			if (queryParams.containsKey(OMDConstants.PP_VEHICLE_NUMBER)) {
				objPPAssetStatusHistoryVO.setAssetNumber(queryParams
						.getFirst(OMDConstants.PP_VEHICLE_NUMBER));
			} else {
				objPPAssetStatusHistoryVO
						.setAssetNumber(OMDConstants.EMPTY_STRING);
			}

			if (queryParams.containsKey(OMDConstants.PP_VEHICLE_HEADER)) {
				objPPAssetStatusHistoryVO.setAssetGroupName(queryParams
						.getFirst(OMDConstants.PP_VEHICLE_HEADER));
			} else {
				objPPAssetStatusHistoryVO
						.setAssetGroupName(OMDConstants.EMPTY_STRING);
			}

			if (queryParams.containsKey(OMDConstants.PP_BUS_ORG_ID)) {
				objPPAssetStatusHistoryVO.setCustomerId(queryParams
						.getFirst(OMDConstants.PP_BUS_ORG_ID));
			} else {
				objPPAssetStatusHistoryVO
						.setCustomerId(OMDConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.FLAG_MP_DATA)) {
				objPPAssetStatusHistoryVO.setFlagMPData(queryParams
						.getFirst(OMDConstants.FLAG_MP_DATA));
			}
			if (null != getRequestHeader(OMDConstants.LANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.LANGUAGE))) {
				objPPAssetStatusHistoryVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

			} else if (null != getRequestHeader(OMDConstants.USERLANGUAGE)
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(getRequestHeader(OMDConstants.USERLANGUAGE))) {
				objPPAssetStatusHistoryVO
						.setStrLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
			} else {
				objPPAssetStatusHistoryVO
						.setStrLanguage(RMDCommonConstants.ENGLISH_LANGUAGE);
			}
			responseJson = objPPServiceIntf
					.getPPAssetHistoryHeaders(objPPAssetStatusHistoryVO);

		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("PPAssetResource : Inside getPPAssetHistoryHeaders() method:::END");
		return responseJson;
	}
	@POST
	@Path(OMDConstants.GET_UNIT_CONVERSION_DATA)
	@Consumes({  MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Produces({  MediaType.APPLICATION_JSON })
	public List<UnitConversionResponseType> getUnitConversionData(UnitConversionRequestType unitConversionRequestType)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		List<PPMetricsVO> responseJson = new ArrayList<PPMetricsVO>();
		 List<UnitConversionDetailsVO> objUnitConversionDetailsVO = new ArrayList<UnitConversionDetailsVO>();
		List<UnitConversionResponseType> objUnitConversionResponseType = new ArrayList<UnitConversionResponseType>();
		UnitConversionResponseType unitConversionResponseType = null;
		List<UnitConversionRequestType> arlUnitConversionRequestType = unitConversionRequestType.getArlUnitConversionRequestType();
		UnitConversionDetailsVO unitConversionDetailsVO = new UnitConversionDetailsVO();
		UnitConversionVO unitConversionVO = null;
		List<UnitConversionVO> lstUnitConversionVO = null;
		UnitConversionBeanResponseType conversionType = null;
		List<UnitConversionBeanResponseType> lstUnitConversionBeanResponseType =null;
		try {
			  for (UnitConversionRequestType unitConversionRequestType1 : arlUnitConversionRequestType) {
				  unitConversionDetailsVO = new UnitConversionDetailsVO();
				  lstUnitConversionVO = new ArrayList<UnitConversionVO>();
	                if (null != unitConversionRequestType1.getUserId()) {
	                	unitConversionDetailsVO.setUserId(unitConversionRequestType1.getUserId());
	                }
	                for(UnitConversionBeanType unitType : unitConversionRequestType1.getLstUnitConversionBeanType()){
	                	unitConversionVO = new UnitConversionVO();
	                if (null != unitType.getParamId()) {
	                	unitConversionVO.setParamId(unitType.getParamId());
	                }
	                if (null != unitType.getConvertValue()) {
	                	unitConversionVO.setConvertValue(unitType.getConvertValue());
	                }
	                lstUnitConversionVO.add(unitConversionVO);
	                unitConversionDetailsVO.setLstUnitConversionVO(lstUnitConversionVO);
	                }
	                if (null != unitConversionRequestType1.getMeasurementSystem()) {
	                	unitConversionDetailsVO.setMeasurement(unitConversionRequestType1.getMeasurementSystem());
	                }
	                
	                objUnitConversionDetailsVO.add(unitConversionDetailsVO);
	            }
			responseJson = objPPServiceIntf
					.getUnitConversionData(objUnitConversionDetailsVO);
if(responseJson!=null){
	for(PPMetricsVO metric: responseJson){
		unitConversionResponseType = new UnitConversionResponseType();
		lstUnitConversionBeanResponseType = new ArrayList<UnitConversionBeanResponseType>();
		for(MetricsVO metrics : metric.getLstMetricsVO()){
			conversionType = new UnitConversionBeanResponseType();
			conversionType.setConvertedValue(metrics.getConvertedValue());
			conversionType.setUnitName(metrics.getUnitName());
			conversionType.setParamId(metrics.getParamId());
			lstUnitConversionBeanResponseType.add(conversionType);
		}
		
		unitConversionResponseType.setUserId(metric.getUserId());
		unitConversionResponseType.setLstUnitConversionBeanResponseType(lstUnitConversionBeanResponseType);
		objUnitConversionResponseType.add(unitConversionResponseType);
	}
}
			
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		RMDLOGGER
				.debug("PPAssetResource : Inside getUnitConversionData() method:::END");
		return objUnitConversionResponseType;
	}
}
