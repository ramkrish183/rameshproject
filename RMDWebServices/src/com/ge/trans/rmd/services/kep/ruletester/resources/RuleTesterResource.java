package com.ge.trans.rmd.services.kep.ruletester.resources;

/**
 * ============================================================
 * File : RuleTesterResource.java
 * Description : 
 * 
 * Package : com.ge.trans.services.kep.tester.resources
 * Author : iGATE Patni
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.kep.common.constants.KEPCommonConstants;
import com.ge.trans.eoa.services.kep.ruletester.service.intf.RuleTesterServiceIntf;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RTRequestVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RuleTesterVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterDetailsVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterResultVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterSummaryVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.kep.knowledgeseeker.valueobjects.SystemLookupResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.AssetIDResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.ClearingLogicIDResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.RoadNumberResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.RuleTestSearchResultResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.RuleTesterCriteriaResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.RuleTesterRequestType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.RuleTesterResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.TesterResultResponseType;
import com.ge.trans.rmd.services.kep.ruletester.valueobjects.TesterSummaryResponseType;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.sun.jersey.core.util.MultivaluedMapImpl;
/*******************************************************************************
 * 
 * @Author :-p-p[=[]
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the Web services Resource file which will call
 *              the rule tester services files 
 * 
 * @History :
 * 
 ******************************************************************************/
@Path(OMDConstants.RULESERVICE)
@Component
public class RuleTesterResource {

	@Autowired
	private RuleTesterServiceIntf ruleTesterIntf;
	@Autowired
	OMDResourceMessagesIntf omdRMDIntf;

	/**
	 * @param strRuleTitle
	 * @return List<RuleTestSearchResultResponseType>
	 * @Description: This method is used for getting the list of RuleTitle for
	 *               ajax populate
	 * 
	 */
	@GET
	@Path(OMDConstants.GETRULESTITLES)
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RuleTestSearchResultResponseType> getRuleTitles(
			@Context UriInfo uriParam)throws RMDServiceException{
		List<RuleTesterVO> lstRuleTitle;
		final List<RuleTestSearchResultResponseType> arlResponseType = new ArrayList<RuleTestSearchResultResponseType>();
		RuleTesterVO objRuleTesterVO;
		Iterator<RuleTesterVO> arlIter;
		MultivaluedMap<String, String> queryParams = null;
		MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
		String ruleTitleValue=RMDCommonConstants.EMPTY_STRING;
		try {
			queryParams = uriParam.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.RULE_TITLE)){
				ruleTitleValue = queryParams.getFirst(OMDConstants.RULE_TITLE);
			}
			int[] paramFlag = {OMDConstants.DOUBLE_HYPHEN};
			String[] userInput = {OMDConstants.RULE_TITLE};
			multivaluedMap.add(OMDConstants.RULE_TITLE, ruleTitleValue);
			if(AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)){
				lstRuleTitle = ruleTesterIntf
				.getRuleTitles(ruleTitleValue);
				RuleTestSearchResultResponseType ruleTesterResp;
				if (BeanUtility.isCollectionNotEmpty(lstRuleTitle)) {
					arlIter = lstRuleTitle.iterator();
					while (arlIter.hasNext()) {
						ruleTesterResp = new RuleTestSearchResultResponseType();
						objRuleTesterVO = (RuleTesterVO) arlIter.next();
						org.springframework.beans.BeanUtils.copyProperties(
								objRuleTesterVO, ruleTesterResp,
								new String[] { OMDConstants.FROM_DATE, OMDConstants.TO_DATE_FIELD });
						arlResponseType.add(ruleTesterResp);

					}
				}
				else {
					throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);
				}
			}else{
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);

			}
		}catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdRMDIntf);
		}
		return arlResponseType;

	}

	/**
	 * This method is used for fetching list of the Rule Type
	 * 
	 * @param PathParam
	 *            Status
	 * @return TrackingDataResponseType
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GETRULETYPE)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<SystemLookupResponseType> getRuleTypes(@Context UriInfo uriParam){
		List<SystemLookupResponseType> arlSystemLookupResponseType = new ArrayList<SystemLookupResponseType>();
		List<ElementVO> arlLookupDetails = null;
		MultivaluedMap<String, String> queryParams = null;
		String strListName=OMDConstants.EMPTY_STRING;
		String strLanguage=OMDConstants.EMPTY_STRING;
		SystemLookupResponseType objSystemLookupResponseType=null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.LIST_NAME)&&
					AppSecUtil.checkAlphaUnderScore(queryParams.getFirst(OMDConstants.LIST_NAME))){
				strListName = queryParams.getFirst(OMDConstants.LIST_NAME);
			}else{
				throw new OMDInValidInputException(OMDConstants.LISTNAME_NOT_PROVIDED);
			}
			if (queryParams.containsKey(OMDConstants.LANGUAGE) && 
					AppSecUtil.checkAlphabets(queryParams.getFirst(OMDConstants.LANGUAGE))) {
				strLanguage = queryParams.getFirst(OMDConstants.LANGUAGE);
			}else{
				throw new OMDInValidInputException(OMDConstants.INVALID_LANGUAGE);
			}
			arlLookupDetails = ruleTesterIntf			
			.getLookUPDetails(strListName, strLanguage);
			if (BeanUtility.isCollectionNotEmpty(arlLookupDetails)) {
				for (ElementVO elementVO : arlLookupDetails) {
					objSystemLookupResponseType = new SystemLookupResponseType();					
					BeanUtils.copyProperties(elementVO,
							objSystemLookupResponseType);					
					arlSystemLookupResponseType
					.add(objSystemLookupResponseType);
				}
			}

			else {
				throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION);

			}
		}
		catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdRMDIntf);
		}
		return arlSystemLookupResponseType;
	}

	/**
	 * @param strCreatedBy
	 * @return List<RuleTestSearchResultResponseType>
	 * @Description: This method is used for getting the list of CreatedBy for
	 *               ajax populate
	 * 
	 */

	@GET
	@Path(OMDConstants.GETCREATORS_CREATBY)
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RuleTestSearchResultResponseType> getCreators(
			@PathParam(OMDConstants.CREATED_BY) final String createdByValue) throws RMDServiceException{
		List<RuleTesterVO> lstCreatedBy;
		final List<RuleTestSearchResultResponseType> arlResponseType = new ArrayList<RuleTestSearchResultResponseType>();
		RuleTesterVO objRuleTesterVO;
		Iterator<RuleTesterVO> arlIter;

		try {

				lstCreatedBy = ruleTesterIntf
				.getCreators(createdByValue);
				RuleTestSearchResultResponseType ruleTesterResp;
				if (BeanUtility.isCollectionNotEmpty(lstCreatedBy)) {
					arlIter = lstCreatedBy.iterator();
					while (arlIter.hasNext()) {
						ruleTesterResp = new RuleTestSearchResultResponseType();
						objRuleTesterVO = (RuleTesterVO) arlIter.next();
						org.springframework.beans.BeanUtils.copyProperties(
								objRuleTesterVO, ruleTesterResp,
								new String[] { OMDConstants.FROM_DATE, OMDConstants.TO_DATE_FIELD });
						arlResponseType.add(ruleTesterResp);

					}
				}
				else{ 
					throw new OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION); 
				}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdRMDIntf);
		}
		return arlResponseType;

	}

	/**
	 * @param strTrackingId
	 * @return List<RuleTesterCriteriaResponseType>
	 * @Description: This method is used for getting the tester criteria details
	 *               for the particular TrackingId
	 * 
	 */

	@GET
	@Path(OMDConstants.GETTRACKINGDEATILS_TRACKINGID)
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RuleTesterCriteriaResponseType> getTrackingDetails(
			@PathParam(OMDConstants.TRACKING_ID) final String trackingIdValue)throws RMDServiceException{
		List<TesterDetailsVO> arlTesterCriteria;
		List<TesterSummaryVO> arlTesterSummary;
		List<TesterResultVO> arlTesterResult;
		final List<RuleTesterCriteriaResponseType> arlResponseType = new ArrayList<RuleTesterCriteriaResponseType>();
		TesterResultResponseType objTesterResultResponseType;
		TesterSummaryResponseType ruleTesterSumResponse;
		TesterDetailsVO objTesterDetailsVO;
		TesterSummaryVO objTesterSummaryVO;
		TesterResultVO objTesterResultVO;
		Iterator<TesterDetailsVO> arlIter;
		RuleTesterCriteriaResponseType ruleTesterCriteria=null;
		MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
		try {
			int[] paramFlag = {OMDConstants.NUMERIC};
			String[] userInput = {OMDConstants.TRACKING_ID};
			multivaluedMap.add(OMDConstants.TRACKING_ID, trackingIdValue);
			if(AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)){
				arlTesterCriteria = ruleTesterIntf
				.getTrackingDetails(trackingIdValue);

				if (BeanUtility.isCollectionNotEmpty(arlTesterCriteria)) {
					arlIter = arlTesterCriteria.iterator();
					while (arlIter.hasNext()) {
						ruleTesterCriteria = new RuleTesterCriteriaResponseType();
						objTesterDetailsVO = (TesterDetailsVO) arlIter.next();
						org.springframework.beans.BeanUtils.copyProperties(
								objTesterDetailsVO,
								ruleTesterCriteria);
						arlTesterResult = objTesterDetailsVO.getTesterResult();

						for (final Iterator<TesterResultVO> iterator = arlTesterResult
								.iterator(); iterator.hasNext();) {
							objTesterResultVO = (TesterResultVO) iterator.next();
							objTesterResultResponseType = new TesterResultResponseType();
							org.springframework.beans.BeanUtils.copyProperties(
									objTesterResultVO, objTesterResultResponseType);
							ruleTesterCriteria.getTesterResult()
							.add(objTesterResultResponseType);
						}
						arlTesterSummary = objTesterDetailsVO.getTesterSummary();

						for (final Iterator<TesterSummaryVO> iterator = arlTesterSummary
								.iterator(); iterator.hasNext();) {
							ruleTesterSumResponse = new TesterSummaryResponseType();
							objTesterSummaryVO = (TesterSummaryVO) iterator.next();
							org.springframework.beans.BeanUtils.copyProperties(
									objTesterSummaryVO,
									ruleTesterSumResponse);
							ruleTesterCriteria.getTesterSummary()
							.add(ruleTesterSumResponse);
						}
						arlResponseType.add(ruleTesterCriteria);
					}
				}
			}else{
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdRMDIntf.getMessage(
								BeanUtility
								.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
								.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (OMDInValidInputException omdInvalidInputEx) {
			throw omdInvalidInputEx;
		} catch (OMDNoResultFoundException noResultFoundEx) {
			throw noResultFoundEx;
		} catch (RMDServiceException rmdServiceEx) {
			throw rmdServiceEx;
		}catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
                    omdRMDIntf);	
		}
		return arlResponseType;
	}

	/**
	 * @param strPattern
	 * @return List<RuleTesterCriteriaResponseType>
	 * @Description: This method is used for getting the PatternDetails for
	 *               particular TrackingId
	 * 
	 */

	@GET
	@Path(OMDConstants.GETPATTERNDETAILS)
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<TesterResultResponseType> getPatternDetails(
			@PathParam(OMDConstants.PATTERN) final String strPattern,
			@PathParam(OMDConstants.TRACKING_ID) final String strTrackingId)throws RMDServiceException{
		List<TesterResultVO> arlPatternDetails;
		final List<TesterResultResponseType> arlResponseType = new ArrayList<TesterResultResponseType>();
		TesterResultVO objTesterResultVO;
		Iterator<TesterResultVO> arlIter;
		MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
		try {
			int[] paramFlag = {OMDConstants.NUMERIC, OMDConstants.ALPHABETS};
			String[] userInput = {OMDConstants.TRACKING_ID, OMDConstants.PATTERN};
			multivaluedMap.add(OMDConstants.TRACKING_ID, strTrackingId);
			multivaluedMap.add(OMDConstants.PATTERN, strPattern);
			if(AppSecUtil.validateWebServiceInput(multivaluedMap, null, paramFlag, userInput)){
				arlPatternDetails = ruleTesterIntf.getPatternDetails(
						strPattern, strTrackingId);
				TesterResultResponseType objTesterResultResponseType;
				if (BeanUtility.isCollectionNotEmpty(arlPatternDetails)) {
					arlIter = arlPatternDetails.iterator();
					while (arlIter.hasNext()) {
						objTesterResultResponseType = new TesterResultResponseType();
						objTesterResultVO = (TesterResultVO) arlIter.next();
						org.springframework.beans.BeanUtils.copyProperties(
								objTesterResultVO, objTesterResultResponseType);

						arlResponseType.add(objTesterResultResponseType);

					}
				}
			}else{
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdRMDIntf.getMessage(
								BeanUtility
								.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
								.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (OMDInValidInputException omdInvalidInputEx) {
			throw omdInvalidInputEx;
		} catch (OMDNoResultFoundException noResultFoundEx) {
			throw noResultFoundEx;
		} catch (RMDServiceException rmdServiceEx) {

			throw rmdServiceEx;
		}catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
                    omdRMDIntf);	
		}
		return arlResponseType;

	}

	/**
	 * @param RuleTesterVO
	 * @return List<RuleTestSearchResultResponseType>
	 * @Description: This method is used for getting the Details for values
	 *               given in UI(filtered Options)
	 * 
	 * 
	 */
	@GET
	@Path(OMDConstants.GETTESTERTRACKINGS)
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RuleTestSearchResultResponseType> getRuleTesterTrackings(
			@Context final UriInfo urlinfo) throws RMDServiceException{

		GregorianCalendar objGregorianCalendar;
		XMLGregorianCalendar dtStart = null;
		XMLGregorianCalendar dtEnd = null;
		String strDefaultLoad = RMDCommonConstants.EMPTY_STRING;
		boolean blnDefaultLoad = false;
		final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(
				OMDConstants.DATE_FORMAT);
		final MultivaluedMap<String, String> queryParams = urlinfo
		.getQueryParameters();
		RuleTesterVO objRuleTesterVO = new RuleTesterVO();
		List<RuleTesterVO> arlTesterResults;
		final List<RuleTestSearchResultResponseType> arlResponseType = new ArrayList<RuleTestSearchResultResponseType>();
		Iterator<RuleTesterVO> arlIter;
		RuleTestSearchResultResponseType objTestSearchResultResponseType;		

		try {
			int[] paramFlag = {OMDConstants.NUMERIC,OMDConstants.AlPHA_NUMERIC,OMDConstants.VALID_DATE, OMDConstants.VALID_DATE,OMDConstants.ALPHABETS,OMDConstants.NUMERIC,OMDConstants.ALPHABETS,OMDConstants.DOUBLE_HYPHEN};
			String[] userInput = {OMDConstants.TRACKING_ID,OMDConstants.CREATED_BY,OMDConstants.FROM_DATE,OMDConstants.TO_DATE,OMDConstants.STATUS, OMDConstants.RULE_ID,OMDConstants.RULE_TYPE,OMDConstants.RULE_TITLE};
			if(AppSecUtil.validateWebServiceInput(queryParams, OMDConstants.DATE_FORMAT, paramFlag,userInput)){
				if (queryParams.containsKey(OMDConstants.TRACKING_ID)) {
					objRuleTesterVO.setTrackingId(queryParams.getFirst(OMDConstants.TRACKING_ID));
				} else {
					objRuleTesterVO.setTrackingId(KEPCommonConstants.EMPTY_STRING);
				}
				if (queryParams.containsKey(OMDConstants.RULE_ID)) {
					objRuleTesterVO.setRuleId(queryParams.getFirst(OMDConstants.RULE_ID));
				} else {
					objRuleTesterVO.setRuleId(KEPCommonConstants.EMPTY_STRING);
				}
				if (queryParams.containsKey(OMDConstants.RULE_TYPE)) {
					objRuleTesterVO.setRuleType(queryParams.getFirst(OMDConstants.RULE_TYPE));
				} else {
					objRuleTesterVO.setRuleType(KEPCommonConstants.SELECT);
				}
				if (queryParams.containsKey(OMDConstants.RULE_TITLE)) {
					objRuleTesterVO.setRuleTitle(queryParams.getFirst(OMDConstants.RULE_TITLE));
				} else {
					objRuleTesterVO.setRuleTitle(KEPCommonConstants.EMPTY_STRING);
				}
				if (queryParams.containsKey(OMDConstants.CREATED_BY)) {
					objRuleTesterVO.setCreatedBy(queryParams.getFirst(OMDConstants.CREATED_BY));
				} else {
					objRuleTesterVO.setCreatedBy(KEPCommonConstants.EMPTY_STRING);
				}
				if (queryParams.containsKey(OMDConstants.STATUS)) {
					objRuleTesterVO.setStatus(queryParams.getFirst(OMDConstants.STATUS));
				} else {
					objRuleTesterVO.setStatus(KEPCommonConstants.SELECT);
				}
				if (queryParams.containsKey(OMDConstants.FROM_DATE)) {
					objRuleTesterVO.setFromDate(SDF_FORMAT.parse(queryParams
							.getFirst(OMDConstants.FROM_DATE)));
				}
				if (queryParams.containsKey(OMDConstants.IS_DEFAULT_LOAD)) {
					strDefaultLoad = queryParams.getFirst(OMDConstants.IS_DEFAULT_LOAD);
					if (strDefaultLoad
							.equalsIgnoreCase(RMDCommonConstants.LETTER_Y)) {
						blnDefaultLoad = RMDCommonConstants.TRUE;
					} else {
						blnDefaultLoad = RMDCommonConstants.FALSE;
					}
					objRuleTesterVO.setBlnDefaultLoad(blnDefaultLoad);
				}
				if (queryParams.containsKey(OMDConstants.TO_DATE)) {
					objRuleTesterVO.setDtTo(SDF_FORMAT
							.parse(queryParams.getFirst(OMDConstants.TO_DATE)));
				} /*else {
					objRuleTesterVO.setDtTo(new Date());
				}*/
				arlTesterResults = ruleTesterIntf
				.getRuleTesterTrackings(objRuleTesterVO);
				if (BeanUtility.isCollectionNotEmpty(arlTesterResults)) {
					arlIter = arlTesterResults.iterator();
					while (arlIter.hasNext()) {
						objTestSearchResultResponseType = new RuleTestSearchResultResponseType();
						objRuleTesterVO = (RuleTesterVO) arlIter.next();

						org.springframework.beans.BeanUtils.copyProperties(
								objRuleTesterVO, objTestSearchResultResponseType,
								new String[] { OMDConstants.FROM_DATE, OMDConstants.TO_DATE_FIELD });
						arlResponseType.add(objTestSearchResultResponseType);
						if (null != objRuleTesterVO.getFromDate()) {
							objGregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
						//	objGregorianCalendar.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
							objGregorianCalendar.setTime(objRuleTesterVO.getFromDate());
							dtStart = DatatypeFactory.newInstance()
							.newXMLGregorianCalendar(objGregorianCalendar);
							objTestSearchResultResponseType.setFromDt(dtStart);
						}
						if (null != objRuleTesterVO.getDtTo()) {
							objGregorianCalendar = new GregorianCalendar();
							objGregorianCalendar.setTime(objRuleTesterVO.getDtTo());
							objGregorianCalendar.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
							dtEnd = DatatypeFactory.newInstance()
							.newXMLGregorianCalendar(objGregorianCalendar);
							objTestSearchResultResponseType.setToDate(dtEnd);
						}

					}
				}
			}else{

				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdRMDIntf);
		}

		return arlResponseType;

	}

	@POST
	@Path(OMDConstants.CREATEREQUEST)
	@Consumes({ MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RuleTesterResponseType saveRuleTesterRequest(
			RuleTesterRequestType ruleTesterRequestType)throws RMDServiceException{
		RTRequestVO rtRequestVO = null;
		RuleTesterResponseType ruleTesterResponseType = new RuleTesterResponseType();
		int trackingId = 0;
		try {
			if(validateSaveRuleTesterRequest(ruleTesterRequestType)){
			if (ruleTesterRequestType != null) {
				rtRequestVO = new RTRequestVO();

				rtRequestVO.setCustomerIds(ruleTesterRequestType.getCustomerIds());
				rtRequestVO.setFleets(ruleTesterRequestType.getFleets());
				rtRequestVO.setModels(ruleTesterRequestType.getModels());
				rtRequestVO.setConfigs(ruleTesterRequestType.getConfigs());
				rtRequestVO.setUnitNumbers(ruleTesterRequestType.getUnitNos());
				rtRequestVO.setRoadNumber(ruleTesterRequestType.getRoadNo());
				rtRequestVO.setClearingLogic(ruleTesterRequestType.getClearingLogic());
				rtRequestVO.setRuleid(ruleTesterRequestType.getRuleId());
				rtRequestVO.setRuletitle(ruleTesterRequestType.getRuleTitle());
				rtRequestVO.setRuletype(ruleTesterRequestType.getRuleType());
				rtRequestVO.setFromdate(ruleTesterRequestType.getFromDate());
				rtRequestVO.setTodate(ruleTesterRequestType.getToDate());
				rtRequestVO.setClassification(ruleTesterRequestType.getClassification());
				rtRequestVO.setClassificationwin(ruleTesterRequestType.getClassificationWindow());
				rtRequestVO.setRootCause(ruleTesterRequestType.getRootCause());
				rtRequestVO.setSymptom(ruleTesterRequestType.getSymptom());
				rtRequestVO.setUserName(ruleTesterRequestType.getUserName());
				rtRequestVO.setRuleDetails(ruleTesterRequestType.getRuleDetails());
				rtRequestVO.setSegmentWindow(ruleTesterRequestType.getSegmentWindow());
				rtRequestVO.setRx(ruleTesterRequestType.getRx());

				trackingId = ruleTesterIntf.
				createRuleTesterRequest(rtRequestVO);
				ruleTesterResponseType.setTrackingID(trackingId);
			}
			}else{
				throw new OMDInValidInputException( OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,omdRMDIntf);
		}
		
		return ruleTesterResponseType;
	}

	
	/**
	 * @param ruleTesterRequestType
	 * @return
	 * @Description Method validates the data for saveRuleTesterRequest(), used in create new 
	 * request in iTest
	 */
	public static boolean validateSaveRuleTesterRequest(
			RuleTesterRequestType ruleTesterRequestType) {
		boolean isValidInput = true;
		String dataArray[] = null;
		// customer id
		if (null != ruleTesterRequestType.getCustomerIds()
				&& !ruleTesterRequestType.getCustomerIds().isEmpty()
				&& !ruleTesterRequestType.getCustomerIds().equalsIgnoreCase(
						OMDConstants.all)) {
			dataArray = ruleTesterRequestType.getCustomerIds().split(
					OMDConstants.COMMA);
			for (String value : dataArray) {
				if (!AppSecUtil.checkAlphaNumeric(value)) {
					return false;

				}
			}
		}
		// fleet
		if (null != ruleTesterRequestType.getFleets()
				&& !ruleTesterRequestType.getFleets().isEmpty()
				&& !ruleTesterRequestType.getFleets().equalsIgnoreCase(
						OMDConstants.all)) {
			dataArray = ruleTesterRequestType.getFleets().split(
					OMDConstants.COMMA);
			for (String value : dataArray) {
				if (!AppSecUtil.checkAlphaNumeralsUnderscoreForRx(value)) {
					return false;
				}
			}
		}
		// model
		if (null != ruleTesterRequestType.getModels()
				&& !ruleTesterRequestType.getModels().isEmpty()
				&& !ruleTesterRequestType.getModels().equalsIgnoreCase(
						OMDConstants.all)) {
			dataArray = ruleTesterRequestType.getModels().split(
					OMDConstants.COMMA);
			for (String value : dataArray) {
				if (!AppSecUtil.checkAlphaNumericHypen(value)) {
					return false;
				}
			}
		}

		// config
		if (null != ruleTesterRequestType.getConfigs()
				&& !ruleTesterRequestType.getConfigs().isEmpty()
				&& !ruleTesterRequestType.getConfigs().equalsIgnoreCase(
						OMDConstants.all)) {
			dataArray = ruleTesterRequestType.getConfigs().split(
					OMDConstants.COMMA);
			for (String value : dataArray) {
				if (!AppSecUtil.validateConfigFunction(value)) {
					return false;
				}
			}
		}

		// unit no
		if (null != ruleTesterRequestType.getUnitNos()
				&& !ruleTesterRequestType.getUnitNos().isEmpty()
				&& !ruleTesterRequestType.getUnitNos().equalsIgnoreCase(
						OMDConstants.all)) {
			dataArray = ruleTesterRequestType.getUnitNos().split(
					OMDConstants.COMMA);
			for (String value : dataArray) {
				if (!AppSecUtil.checkAlphaNumeric(value)) {
					return false;
				}
			}
		}

		// from date
		if (null != ruleTesterRequestType.getFromDate()
				&& !ruleTesterRequestType.getFromDate().isEmpty()) {
			if (!AppSecUtil.validateDate(ruleTesterRequestType.getFromDate(),
					OMDConstants.DATE_FORMAT)) {
				return false;
			}
		}

		// to date
		if (null != ruleTesterRequestType.getToDate()
				&& !ruleTesterRequestType.getToDate().isEmpty()) {
			if (!AppSecUtil.validateDate(ruleTesterRequestType.getToDate(),
					OMDConstants.DATE_FORMAT)) {
				return false;
			}
		}

		// rule id
		if (null != ruleTesterRequestType.getRuleId()
				&& !ruleTesterRequestType.getRuleId().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(ruleTesterRequestType.getRuleId())) {
				return false;
			}
		}

		// rule type
		if (null != ruleTesterRequestType.getRuleType()
				&& !ruleTesterRequestType.getRuleType().isEmpty()) {
			if (!AppSecUtil.checkAlphaNumericHypen(ruleTesterRequestType
					.getRuleType())) {
				return false;
			}
		}

		// rule title
		if (null != ruleTesterRequestType.getRuleTitle()
				&& !ruleTesterRequestType.getRuleTitle().isEmpty()) {
			if (!AppSecUtil.checkDoubleHyphen(ruleTesterRequestType
					.getRuleTitle())) {
				return false;
			}
		}

		// username
		if (null != ruleTesterRequestType.getUserName()
				&& !ruleTesterRequestType.getUserName().isEmpty()) {
			if (!AppSecUtil.checkAlphaNumericHypen(ruleTesterRequestType
					.getUserName())) {
				return false;
			}
		}

		// classification
		if (null != ruleTesterRequestType.getClassification()
				&& !ruleTesterRequestType.getClassification().isEmpty()) {
			if (!AppSecUtil.checkAlphabets(ruleTesterRequestType
					.getClassification())) {
				return false;
			}
		}

		// classification window
		if (null != ruleTesterRequestType.getClassificationWindow()
				&& !ruleTesterRequestType.getClassificationWindow().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(ruleTesterRequestType
					.getClassificationWindow())) {
				return false;
			}
		}

		// root cause
		if (null != ruleTesterRequestType.getRootCause()
				&& !ruleTesterRequestType.getRootCause().isEmpty()) {
			if (!AppSecUtil.checkForwardSlash(ruleTesterRequestType
					.getRootCause())) {
				return false;
			}
		}

		// symptoms
		if (null != ruleTesterRequestType.getSymptom()
				&& !ruleTesterRequestType.getSymptom().isEmpty()) {
			if (!AppSecUtil.checkForwardSlash(ruleTesterRequestType
					.getSymptom())) {
				return false;
			}
		}

		// segment window
		if (null != ruleTesterRequestType.getSegmentWindow()
				&& !ruleTesterRequestType.getSegmentWindow().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(ruleTesterRequestType
					.getSegmentWindow())) {
				return false;
			}
		}
		return isValidInput;
	}
	
	/**
	 * This method invokes the web service to get road numbers if the road number exists for
	 * given model and customer
	 * @return list of road numbers
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_ROAD_NUMBERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RoadNumberResponseType isValidRoadNumber(
			@Context final UriInfo ui) throws RMDServiceException {

		MultivaluedMap<String, String> queryParams = null;
		RoadNumberResponseType roadNumberResponseType = new RoadNumberResponseType();
		try {
			if (null != ui.getQueryParameters()) {
				queryParams = ui.getQueryParameters();
			}

				String model = queryParams.getFirst(OMDConstants.MODEL);
				String customer = queryParams.getFirst(OMDConstants.CUSTOMER);
				List<String> roadNumbers= (List<String>) ruleTesterIntf.getRoadNumbers(model, customer);
			
				roadNumberResponseType.setRoadNumber(roadNumbers);

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdRMDIntf);
		}
		return roadNumberResponseType;  
	}
	
	/**
	 * This method invokes the web service to get road numbers if the road number exists for
	 * given model and customer
	 * @return list of road numbers
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_ASSET_ID)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AssetIDResponseType getAssetId(
			@Context final UriInfo ui) throws RMDServiceException {
		List<String> assetIds = new ArrayList<String>();
		MultivaluedMap<String, String> queryParams = null;
		AssetIDResponseType assetIDResponseType = new AssetIDResponseType();
		try {
			if (null != ui.getQueryParameters()) {
				queryParams = ui.getQueryParameters();
			}

				String model = queryParams.getFirst(OMDConstants.MODEL);
				String customer = queryParams.getFirst(OMDConstants.CUSTOMER);
				String roadNo = queryParams.getFirst(OMDConstants.ROAD_NO);
				assetIds= (List<String>) ruleTesterIntf.getAssetId(roadNo, model, customer);

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdRMDIntf);
		}
		String assetId = assetIds.get(0).toString();
		assetIDResponseType.setAssetID(assetId);
		return assetIDResponseType ;  
	}
	
	/**
	 * This method invokes the web service to get Clearing Logic ID if exists for
	 * given ruleId
	 * @return ClearingLogicId
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_CLEARING_LOGIC_ID)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ClearingLogicIDResponseType getClearingLogicId(
			@Context final UriInfo ui) throws RMDServiceException {
		List<String> clearingLogicIds = new ArrayList<String>();
		MultivaluedMap<String, String> queryParams = null;
		ClearingLogicIDResponseType clearingLogicResponseType = new ClearingLogicIDResponseType();
		try {
			if (null != ui.getQueryParameters()) {
				queryParams = ui.getQueryParameters();
			}
				String ruleId = queryParams.getFirst(OMDConstants.RULE_ID);
				clearingLogicIds= (List<String>) ruleTesterIntf.getClearingLogicId(ruleId);
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdRMDIntf);
		}
		if (clearingLogicIds != null && clearingLogicIds.size() > 0) {
		String clearingLogicId = clearingLogicIds.get(0).toString();
		clearingLogicResponseType.setClearingLogicID(clearingLogicId);
		}
		return clearingLogicResponseType ;  
	}
	
}
