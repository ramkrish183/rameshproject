/**
 * ============================================================
    * Classification: GE Confidential
 * File : BeanUtils.java
 * Description :
 * Package : com.ge.trans.rmd.services.util
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 21, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetLocatorBean;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationResultServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesSearchResultVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataBean;
import com.ge.trans.eoa.services.kpi.service.valueobjects.KPIDataEoaBean;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.rmd.cm.mcs.assetslistservice.AssetsListServiceResponse.AssetsData.Asset;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.AddressDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.LocationResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.PersonalDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.RoleType;
import com.ge.trans.rmd.services.admin.valueobjects.RolesType;
import com.ge.trans.rmd.services.admin.valueobjects.SystemTimeZoneResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.UserDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.UsersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.AssetResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Customer;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDataDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDataResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultDetailsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.FaultLogServiceResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.LastDownLoadStatusResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.LastFaultStatusResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ProdAssetMapRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.ToolParamInfoResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.ToolsParamGroupResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.VehicleCommStatusResponseType;
import com.ge.trans.rmd.services.authorization.valueobjects.PrivilegeDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.RoleManagementRequestType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolePrivilegesType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseInfoType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CasesRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.OpenCaseResponseType;
import com.ge.trans.rmd.services.cases.valueobjects.ToolOutputDetailsResponseType;
import com.ge.trans.rmd.services.notes.valueobjects.NotesResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.LookupResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionDetailType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionExecutionResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.SolutionResponseType;
import com.ge.trans.rmd.services.solutions.valueobjects.TaskDetailType;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.rmd.utilities.RMDPropertiesLoader;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : August 31, 2012
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Common File for Webservices and provide the
 *              Case related funtionalities
 * @History :
 ******************************************************************************/
public class CMBeanUtility {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CMBeanUtility.class);
	public static DatatypeFactory datatypeFactory=null;
	static {
		try {
			datatypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			LOG.error("Error occured in CMBeanUtility - Datatype factory initialising" + e);
		}
	}
	@Autowired
	OMDResourceMessagesIntf omdResourceMessagesIntf;
	/**
	 * @Author:
	 * @Description:This method is used copy servicevo to Webservice VO
	 */
	public static void copyPropertiesFromServiceVOToCaseResponse(
			final CaseResponseType caseResType, final CaseInfoType caseInfo,
			final SelectCaseHomeVO selCaseHomeInfo) {
		try {
			caseInfo.setCaseID(selCaseHomeInfo.getStrCaseId());
			LOG.debug("casesresource-getcases-caseid-----" + selCaseHomeInfo.getStrCaseId());

			caseInfo.setCaseTitle(selCaseHomeInfo.getStrTitle());
			caseInfo.setReason(selCaseHomeInfo.getStrReason());
			final GregorianCalendar gregCal = new GregorianCalendar();
			gregCal.setTime(selCaseHomeInfo.getDtCreationDate());
			final XMLGregorianCalendar date2 = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(gregCal);
			caseInfo.setCreatedDate(date2);
			LOG.debug("casesresource-getcases-case created date-----" + date2);

			caseResType.setAssetNumber(selCaseHomeInfo.getStrAssetNumber());
			LOG.debug("caseresource-getcases-findcases-longassetnummmmmmm-->>"
					+ selCaseHomeInfo.getLongAssetNumber());
			LOG.debug("casesresource-getcases-assetnum--->>>"
					+ caseResType.getAssetNumber());
			caseResType.setCreator(selCaseHomeInfo.getStrCreator());
			caseInfo.setPriority(selCaseHomeInfo.getStrPriority());
			caseInfo.setCaseStatus(selCaseHomeInfo.getStrStatus());
			LOG.debug("casesresource-getcases-casestatus->>>>"
					+ caseInfo.getCaseStatus());
			gregCal.setTime(selCaseHomeInfo.getDtHistDate());
			final XMLGregorianCalendar histDate = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(gregCal);
			caseInfo.setHistDate(histDate);
			LOG.debug("casesresource-getcases-histdate----->>>"
					+ caseInfo.getHistDate());

		} catch (Exception excep) {
			LOG.error("Error occured in BeanUtility - copyPropertiesFromServiceVOToCaseResponse" + excep);
		}
	}

	/**
	 * @param objFindCaseServiceVO
	 * @param queryParams
	 */
	public static void copyQueryParamsToServiceVO(
			final FindCaseServiceVO findCaseServiceVO,
			final MultivaluedMap<String, String> queryParams) {
		final Calendar calendarStart = Calendar.getInstance();
		final Calendar calendarEnd = Calendar.getInstance();
		final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(
				RMDServiceConstants.DATE_FORMAT2);

		try {
			final Date endDate = calendarEnd.getTime();
			if (queryParams.containsKey(OMDConstants.NUMBER_OF_DAYS)) {

				calendarStart.add(Calendar.DATE,
						-Integer.parseInt((queryParams.getFirst(OMDConstants.NUMBER_OF_DAYS))));
				final Date startDate = calendarStart.getTime();
				findCaseServiceVO.setSelectedFromDate(startDate);
				findCaseServiceVO.setSelectedToDate(endDate);
			}
			if (queryParams.containsKey(OMDConstants.LANGUAGE)) {
				findCaseServiceVO.setStrLanguage(queryParams
						.getFirst(OMDConstants.LANGUAGE));
			} else {
				findCaseServiceVO.setStrLanguage(OMDConstants.CHINESE_LANGUAGE);
			}
			if (queryParams.containsKey(OMDConstants.USER_LANGUAGE)) {
				findCaseServiceVO.setStrUserLanguage(queryParams
						.getFirst(OMDConstants.USER_LANGUAGE));
			} else {
				findCaseServiceVO.setStrUserLanguage(OMDConstants.DEFAULT_LANGUAGE);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_HEADER)) {
				findCaseServiceVO.setStrAssetHeader(queryParams
						.getFirst(OMDConstants.ASSET_HEADER));
			} else {
				findCaseServiceVO
				.setStrAssetHeader(RMDServiceConstants.QTR_CUSTOMER);
			}
			if (queryParams.containsKey(OMDConstants.QUEUE_NAME)) {
				findCaseServiceVO
				.setStrQueue(queryParams.getFirst(OMDConstants.QUEUE_NAME));
			} else {
				findCaseServiceVO.setStrQueue(OMDConstants.ALL);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_NUMBER)) {
				findCaseServiceVO.setStrAssetNumber(queryParams
						.getFirst(OMDConstants.ASSET_NUMBER));
			} else {
				findCaseServiceVO
				.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.CASE_ID1)) {

				findCaseServiceVO.setStrCaseId(queryParams.getFirst(OMDConstants.CASE_ID1));
			} else {
				findCaseServiceVO.setStrCaseId(RMDCommonConstants.EMPTY_STRING);

			}
			if (queryParams.containsKey(OMDConstants.CASE_TITLE)) {
				findCaseServiceVO.setStrCaseTitle(queryParams
						.getFirst(OMDConstants.CASE_TITLE));
			} else {
				findCaseServiceVO
				.setStrCaseTitle(RMDCommonConstants.EMPTY_STRING);

			}
			if (queryParams.containsKey(OMDConstants.CASE_STATUS)) {
				findCaseServiceVO.setStrCaseStatus(queryParams
						.getFirst(OMDConstants.CASE_STATUS));
			} else {
				findCaseServiceVO
				.setStrCaseStatus(RMDCommonConstants.EMPTY_STRING);

			}
			if (queryParams.containsKey(OMDConstants.USER_ID1)) {
				findCaseServiceVO.setStrUserId(queryParams.getFirst(OMDConstants.USER_ID1));
			} else {
				findCaseServiceVO.setStrUserId(RMDCommonConstants.EMPTY_STRING);

			}
			if (queryParams.containsKey(OMDConstants.ASSET_HEADER)) {
				findCaseServiceVO.setStrAssetHeader(queryParams
						.getFirst(OMDConstants.ASSET_HEADER));
			}
			if (queryParams.containsKey(OMDConstants.STARTDATE)) {
				findCaseServiceVO.setSelectedFromDate(SDF_FORMAT
						.parse(queryParams.getFirst(OMDConstants.STARTDATE)));
			}
			if (queryParams.containsKey(OMDConstants.ENDDATE)) {
				findCaseServiceVO.setSelectedToDate(SDF_FORMAT
						.parse(queryParams.getFirst(OMDConstants.ENDDATE)));
			}
			if (queryParams.containsKey(OMDConstants.SOLUTION_STATUS)) {
				findCaseServiceVO.setStrRxStatus(queryParams
						.getFirst(OMDConstants.SOLUTION_STATUS));
			} else {
				findCaseServiceVO.setStrRxStatus(RMDCommonConstants.ALL);
			}
			if (queryParams.containsKey(OMDConstants.SOLUTION_TITLE)) {
				findCaseServiceVO.setStrRxTitle(queryParams
						.getFirst(OMDConstants.SOLUTION_TITLE));
			} else {
				findCaseServiceVO
				.setStrRxTitle(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.SOLUTION_FEEDBACK)) {
				findCaseServiceVO.setStrRxFdbk(queryParams
						.getFirst(OMDConstants.SOLUTION_FEEDBACK));
			} else {
				findCaseServiceVO.setStrRxFdbk(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.SOLUTION_ID)) {
				findCaseServiceVO
				.setStrRxId(queryParams.getFirst(OMDConstants.SOLUTION_ID));
			} else {
				findCaseServiceVO.setStrRxId(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.RULEDEFID)) {
				findCaseServiceVO.setStrRuleDefId(queryParams
						.getFirst(OMDConstants.RULEDEFID));
			} else {
				findCaseServiceVO
				.setStrRuleDefId(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
				findCaseServiceVO.setStrCustomerId(queryParams
						.getFirst(OMDConstants.CUSTOMER_ID));
			} else {
				findCaseServiceVO
				.setStrCustomerId(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.ASSET_GROUP_NAME)) {
				findCaseServiceVO.setStrAssetGrpName(queryParams
						.getFirst(OMDConstants.ASSET_GROUP_NAME));
			} else {
				findCaseServiceVO
				.setStrAssetGrpName(RMDCommonConstants.EMPTY_STRING);
			}
			if (queryParams.containsKey(OMDConstants.EST_REP_TIME)) {
				findCaseServiceVO.setEstRepairTime(queryParams
						.getFirst(OMDConstants.EST_REP_TIME));
			} else {
				findCaseServiceVO
				.setEstRepairTime(RMDCommonConstants.EMPTY_STRING);
			}
		} catch (Exception excep) {
			LOG.error("Error occured in copyQueryParamsToServiceVO - BeanUtility" + excep);
		}
	}

	public static Object copyBeanProperty(final Object source,
			final Object target) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			FileNotFoundException, IOException, ClassNotFoundException {

		final BeanUtilsBean bub = new BeanUtilsBean();
		final Map<Object, Object> sorceVOMap = BeanUtils.describe(source);
		Properties pro = new Properties();
		try {
			pro = RMDPropertiesLoader
			.loadProperties(OMDConstants.BEAN_PROPERTIES);

			for (Map.Entry entry : sorceVOMap.entrySet()) {

				final String sourceKey = (String) entry.getKey();
				final String sourcename = source.getClass().getCanonicalName();
				final String sourceClassName = sourcename.substring(
						sourcename.lastIndexOf(RMDCommonConstants.DOT) + 1, sourcename.length());
				final String targetname = target.getClass().getCanonicalName();
				final String targetClassName = targetname.substring(
						targetname.lastIndexOf(RMDCommonConstants.DOT) + 1, targetname.length());
				final String name = sourceClassName + RMDCommonConstants.DOT + targetClassName
				+ RMDCommonConstants.DOT + sourceKey;
				final String targetkey = pro.getProperty(name);
				String sourceType = ((String) sorceVOMap.get(sourceKey));
				if (targetkey != null) {

					if (!targetkey.equals(OMDConstants.EMPTY_STRING) && sourceType != null
							&& sourceType.trim().indexOf(OMDConstants.SYMBOL_ATRATE) > -1) {
						final String soucreobjtype = bub.getPropertyUtils()
						.getPropertyType(source, sourceKey).toString();

						if (soucreobjtype
								.equalsIgnoreCase(OMDConstants.INTERFACE_JAVAUTIL)) {

							final List propList = (List) bub.getPropertyUtils()
							.getProperty(source, sourceKey);

							final Iterator it = propList.iterator();
							Object srcObj;
							final List targetList = new ArrayList();
							while (it.hasNext()) {
								srcObj = it.next();
								Object insClass;
								try {
									final String instanceForClass = source
									.getClass().getCanonicalName()
									+ RMDCommonConstants.DOT
									+ target.getClass()
									.getCanonicalName()
									+ RMDCommonConstants.DOT
									+ sourceKey +OMDConstants.CLOSED_TO_LOAD;
									insClass = Class.forName(
											pro.getProperty(instanceForClass))
											.newInstance();
									copyBeanProperty(srcObj, insClass);
									targetList.add(insClass);
								} catch (InstantiationException excep) {
									LOG.error("Error occured in BeanUtility - copyBeanProperty method"
											+ excep);
								}

							}
							bub.copyProperty(target, targetkey, targetList);
						} else {
							String targetType = bub.getPropertyUtils()
							.getPropertyType(target, targetkey)
							.toString();
							targetType = targetType.substring(
									targetType.indexOf(OMDConstants.STRING_EMPTY),
									targetType.length());
							final int sourceIndex = sourceType.indexOf(OMDConstants.SYMBOL_ATRATE);
							if (sourceIndex > -1) {
								sourceType = sourceType.substring(0,
										sourceIndex);
								if (sourceType.trim().equals(targetType.trim())) {
									bub.copyProperty(
											target,
											targetkey,
											bub.getPropertyUtils()
											.getNestedProperty(
													source,
													(String) entry
													.getKey()));
								} else {
									final Object subSource = bub
									.getPropertyUtils().getProperty(
											source,
											(String) entry.getKey());
									final Object subTarget = bub
									.getPropertyUtils().getProperty(
											target, targetkey);

									copyBeanProperty(subSource, subTarget);

									bub.copyProperty(target, targetkey,
											subTarget);
								}
							}
						}
					} else {
						new BeanUtilsBean().copyProperty(
								target,
								targetkey,
								bub.getPropertyUtils().getNestedProperty(
										source, (String) entry.getKey()));
					}
				}
			}
		} catch (Exception excep) {
			LOG.error("Error occured in copyBeanProperty - BeanUtility" + excep);
		}
		return target;
	}

	private Properties getPropertiesFromClasspath(final String propFileName)
	throws IOException {
		final Properties props = new Properties();
		final InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		try{
			if (inputStream == null) {
				throw new FileNotFoundException("property file '" + propFileName
						+ "' not found in the classpath");
	
			} else {
				props.load(inputStream);
			}
		}catch(Exception e)
		{
			 RMDWebServiceErrorHandler.handleException(e,
                     omdResourceMessagesIntf);
		}finally
		{
			if(inputStream!=null)
				inputStream.close();
		}
		return props;
	}

	public static XMLGregorianCalendar convertDateToXMLDate(
			final Date dateValue, final XMLGregorianCalendar dtXMLDate) {
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar dtXMLGregorianDate = dtXMLDate;
		try {
			if (dateValue != null) {
				objGregorianCalendar = new GregorianCalendar();
				objGregorianCalendar.setTime(dateValue);
				dtXMLGregorianDate = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(objGregorianCalendar);
			}
		} catch (Exception excep) {
			LOG.error("Error occured in convertDateToXMLDate - BeanUtility"+excep);
		}
		return dtXMLGregorianDate;
	}

	public static FaultDataDetailsResponseType copyFaultDataToResponse(
			final FaultDataDetailsResponseType faultDataDetails,
			final FaultDataDetailsServiceVO faultDataDetailsVO)
	throws IllegalAccessException, InvocationTargetException,
	NoSuchMethodException, FileNotFoundException, IOException,
	ClassNotFoundException {
		final FaultDataDetailsResponseType faultdataDetailsResponse;
		final Iterator it = faultDataDetailsVO.getArlHeader().iterator();
		ToolParamInfoResponseType toolsParam = null;
		while (it.hasNext()) {
			toolsParam = new ToolParamInfoResponseType();
			faultDataDetails.getHeader().add(
					(ToolParamInfoResponseType) BeanUtility.copyBeanProperty(
							it.next(), toolsParam));
		}

		if(null!=faultDataDetailsVO.getArlGrpHeader()){
			
		final Iterator groupit = faultDataDetailsVO.getArlGrpHeader()
		.iterator();
		ToolsParamGroupResponseType toolsParamGroup = null;
		while (groupit.hasNext()) {
			toolsParamGroup = new ToolsParamGroupResponseType();
			faultDataDetails.getGrpheader().add(
					(ToolsParamGroupResponseType) BeanUtility.copyBeanProperty(
							groupit.next(), toolsParamGroup));
		}
		}
		
		final Iterator<FaultLogServiceVO> it1 = faultDataDetailsVO.getObjFaultServiceVO()
		.getArlFaultData().iterator();
		FaultLogServiceVO faultLogService = null;
		final List<FaultLogServiceResponseType> lstLogResponse = new ArrayList<FaultLogServiceResponseType>();
		List<FaultDetailsResponseType> lstFaultDetailsResponse = new ArrayList<FaultDetailsResponseType>();
		FaultLogServiceResponseType faultLogResponse = null;
		while (it1.hasNext()) {
			faultLogService = new FaultLogServiceVO();
			faultLogService = (FaultLogServiceVO) it1.next();
			faultLogResponse = new FaultLogServiceResponseType();

			FaultDetailsServiceVO faultDetails = null;
			FaultDetailsResponseType faultDetailsResponse = null;
			final Iterator<FaultDetailsServiceVO> it2 = faultLogService.getArlFaultDataDetails()
			.iterator();
			lstFaultDetailsResponse = new ArrayList<FaultDetailsResponseType>();
			while (it2.hasNext()) {
				faultDetails = new FaultDetailsServiceVO();
				faultDetailsResponse = new FaultDetailsResponseType();
				faultDetails = (FaultDetailsServiceVO) it2.next();
				faultDetailsResponse.setParamData(faultDetails.getStrData());
				lstFaultDetailsResponse.add(faultDetailsResponse);

			}
			faultLogResponse.getLstFaultdataDetails().addAll(
					lstFaultDetailsResponse);

			lstFaultDetailsResponse = null;
			faultLogResponse = (FaultLogServiceResponseType) BeanUtility
			.copyBeanProperty(faultLogService, faultLogResponse);

			lstLogResponse.add(faultLogResponse);

		}

		final FaultDataResponseType faultDataResponse = new FaultDataResponseType();
		faultDataResponse.getFaultdata().addAll(lstLogResponse);
		faultDataDetails.setFaultDataResponse(faultDataResponse);

		faultdataDetailsResponse = (FaultDataDetailsResponseType) BeanUtility
		.copyBeanProperty(faultDataDetailsVO, faultDataDetails);
		return faultdataDetailsResponse;
	}

	public static String getErrorCode(final String key) {
		String name = OMDConstants.EMPTY_STRING;
		try {
			name = new CMBeanUtility().getPropertiesFromClasspath(
					OMDConstants.PROPERTIES_FILE_NAME).getProperty(key);
		} catch (IOException excep) {
			LOG.error("Error occured in getErrorCode - BeanUtility" + excep);
		}
		return name;
	}

	public static Locale getLocale(final String language) {
		Locale local = Locale.ENGLISH;
		if (OMDConstants.CHINESE_LANGUAGE.equals(language)) {
			local = Locale.SIMPLIFIED_CHINESE;
		}
		return local;
	}
	/**
	 * @Author:
	 * @param list
	 * @return
	 * @Description: This method will check whether the passed collection is not
	 *               null & not empty
	 */
	public static boolean isCollectionNotEmpty(final Collection list) {
		return (list != null && !list.isEmpty());
	}
	
	/**
	* @Author:
	* @Description:This method is used copy SelectCaseHomeVO to CaseInfoType
	*/
	public static void copyCaseHomeVOToCaseInfoType(
			final SelectCaseHomeVO selectCaseHomeVO, 
			final CaseInfoType caseInfoType) {

		try {
			caseInfoType.setOwner(selectCaseHomeVO.getStrOwner());
			caseInfoType.setReason(selectCaseHomeVO.getStrReason());
			caseInfoType.setCaseType(selectCaseHomeVO.getStrCaseType());
			caseInfoType.setQueueName(selectCaseHomeVO.getStrQueue());
			caseInfoType.setCaseID(selectCaseHomeVO.getStrCaseId());
			caseInfoType.setCaseTitle(selectCaseHomeVO.getStrTitle());
			caseInfoType.setCaseStatus(selectCaseHomeVO.getStrStatus());
			caseInfoType.setPriority(selectCaseHomeVO.getStrPriority());
			caseInfoType.setCustomerName(selectCaseHomeVO.getStrcustomerName());
			caseInfoType.setAge(selectCaseHomeVO.getStrAge());
			caseInfoType.setCaseCondition(selectCaseHomeVO.getCondition());
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copySelectCaseHomeVOToCaseInfoType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy SelectCaseHomeVO to CaseResponseType
	*/
	public static void copyCaseHomeVOToCaseResType(
			final SelectCaseHomeVO selectCaseHomeVO,
			final CaseResponseType caseResponseType) {

		try {
			
			caseResponseType.setCreator(selectCaseHomeVO.getStrCreator());
			caseResponseType.setAssetNumber(selectCaseHomeVO.getStrAssetNumber());
			caseResponseType.setUrgency(selectCaseHomeVO.getStrUrgency());
			caseResponseType.setAssetGrpName(selectCaseHomeVO.getStrGrpName());
			caseResponseType.setRowVersion(selectCaseHomeVO.getRowVersion());
			caseResponseType.setModelName(selectCaseHomeVO.getModelName());
			caseResponseType.setFleetName(selectCaseHomeVO.getFleetName());
			caseResponseType.setCustomerName(selectCaseHomeVO.getStrcustomerName());
			caseResponseType.setRxType(selectCaseHomeVO.getRxType());
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copySelectCaseHomeVOToCaseResponseType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy SelectCaseHomeVO to CaseResponseType
	*/
	public static void copyOpenCaseHomeVOToCaseResType(
			final OpenCaseHomeVO selectCaseHomeVO,
			final OpenCaseResponseType caseResponseType) {

		try {
			caseResponseType.setAssetNumber(selectCaseHomeVO.getStrAssetNumber());
			caseResponseType.setAssetGrpName(selectCaseHomeVO.getStrGrpName());
			caseResponseType.setCustomerName(selectCaseHomeVO.getStrCustomerId());
			caseResponseType.setOwner(selectCaseHomeVO.getStrOwner());
			caseResponseType.setReason(selectCaseHomeVO.getStrReason());
			caseResponseType.setCaseType(selectCaseHomeVO.getStrCaseType());
			caseResponseType.setCaseID(selectCaseHomeVO.getStrCaseId());
			caseResponseType.setCaseTitle(selectCaseHomeVO.getStrTitle());
			caseResponseType.setPriority(selectCaseHomeVO.getStrPriority());
			caseResponseType.setAge(selectCaseHomeVO.getAge());
			caseResponseType.setCaseCondition(selectCaseHomeVO.getCondition());			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copySelectCaseHomeVOToCaseResponseType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy VehicleCommServiceResultVO to VehicleCommStatusResponseType
	*/
	public static void copyVehicleResultVOToVehicleResType(
			final VehicleCommServiceResultEoaVO vehicleCommServiceResultVO,
			final VehicleCommStatusResponseType vehicleCommStatusResponseType) {

		try {
			vehicleCommStatusResponseType.setControllerConfig(vehicleCommServiceResultVO.getStrControllerConfig());
			vehicleCommStatusResponseType.setSoftwareVersion(vehicleCommServiceResultVO.getStrSoftwareVersion());
			vehicleCommStatusResponseType.setStatusColor(vehicleCommServiceResultVO.getStrStatusColor());
			vehicleCommStatusResponseType.setReportStatus(vehicleCommServiceResultVO.getStrReportStatus());
			vehicleCommStatusResponseType.setAssetNumber(vehicleCommServiceResultVO.getStrAssetNumber());
			vehicleCommStatusResponseType.setCustomerId(vehicleCommServiceResultVO.getStrCustomerId());
			vehicleCommStatusResponseType.setAssetGrpName(vehicleCommServiceResultVO.getStrAssetGrpName());
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyVehicleCommServiceResultVOToVehicleCommStatusResponseType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy AssetServiceVO to AssetResponseType
	*/
	public static void copyAsstServiceVOToAsstRespType(
			final AssetServiceVO assetServiceVO,
			final AssetResponseType assetResponseType) {

		try {
			assetResponseType.setAssetNumber(assetServiceVO.getStrAssetNumber());
			assetResponseType.setAssetGroupName(assetServiceVO.getAssetGroupName());
			assetResponseType.setAssetGroupNumber(RMDCommonUtility.convertObjectToLong(assetServiceVO.getAssetGroupNumber()));
			assetResponseType.setCustomerID(assetServiceVO.getCustomerID());
			assetResponseType.setCustomerName(assetServiceVO.getCustomerName());
			assetResponseType.setFleet(assetServiceVO.getFleet());
			assetResponseType.setModel(assetServiceVO.getModel());
			assetResponseType.setLocation(assetServiceVO.getLocation());
			assetResponseType.setAssetId(RMDCommonUtility.convertObjectToLong(assetServiceVO.getAssetId()));
			assetResponseType.setLocation(assetServiceVO.getLocation());
			assetResponseType.setDsControllerConfig(assetServiceVO.getDsControllerConfig());
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyAssetServiceVOToAssetResponseType" + excep);
		}

	}
	
	/**
	* @Author:
	* @Description:This method is used copy AssetServiceVO to AssetResponseType
	*/
	public static void copyAsstEoaServiceVOToAsstRespType(
			final AssetServiceVO assetServiceVO,
			final AssetResponseType assetResponseType) {
		final GregorianCalendar gregCal = new GregorianCalendar();
		try {
			assetResponseType.setAssetNumber(assetServiceVO.getStrAssetNumber());
			assetResponseType.setAssetGroupName(assetServiceVO.getAssetGroupName());
			assetResponseType.setAssetGroupNumber(RMDCommonUtility.convertObjectToLong(assetServiceVO.getAssetGroupNumber()));
			assetResponseType.setCustomerID(assetServiceVO.getCustomerID());
			assetResponseType.setCustomerName(assetServiceVO.getCustomerName());
			assetResponseType.setFleet(assetServiceVO.getFleet());
			assetResponseType.setModel(assetServiceVO.getModel());
			assetResponseType.setLocation(assetServiceVO.getLocation());
			assetResponseType.setAssetId(RMDCommonUtility.convertObjectToLong(assetServiceVO.getSourceObjid()));
			assetResponseType.setLocation(assetServiceVO.getLocation());
			assetResponseType.setControllerCfg(assetServiceVO.getControllerConfig());
			assetResponseType.setDsControllerConfig(assetServiceVO.getControllerConfig());
			assetResponseType.setCmuId(assetServiceVO.getCmuId());
			assetResponseType.setEgaCommId(assetServiceVO.getEgaCommId());
			assetResponseType.setAssetObjid(assetServiceVO.getSourceObjid());
			if(null !=assetServiceVO.getInstallationDate() ){
				gregCal.setTime(assetServiceVO.getInstallationDate());
				assetResponseType.setInstallationDate(datatypeFactory.newXMLGregorianCalendar(gregCal));	
			}
			assetResponseType.setPartStatus(assetServiceVO.getPartStatus());
			assetResponseType.setSoftwareVersion(assetServiceVO.getSoftwareVersion());
			assetResponseType.setFleetId(assetServiceVO.getStrFleetId());
			assetResponseType.setModelId(assetServiceVO.getStrModelId());
			assetResponseType.setLmsLocoId(assetServiceVO.getLmsLocoId());
			assetResponseType.setBadActor(assetServiceVO.getBadActor());
			assetResponseType.setSourceObjid(assetServiceVO.getSourceObjid());

		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyAssetServiceVOToAssetResponseType" + excep);
			excep.printStackTrace();
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy FindNotesSearchResultVO to NotesResponseType
	*/
	public static void copyFndNoteSrchResultVOToNoteResType(
			final FindNotesSearchResultVO findNotesSearchResultVO,
			final NotesResponseType notesResponseType) {

		try {
			notesResponseType.setAssetHeader(findNotesSearchResultVO.getAssetHeader());
			notesResponseType.setAssetNumber(findNotesSearchResultVO.getAssetNumber());
			notesResponseType.setCaseID(findNotesSearchResultVO.getCaseId());
			notesResponseType.setCreatedBy(findNotesSearchResultVO.getCreator());
			notesResponseType.setNoteType(findNotesSearchResultVO.getType());
			notesResponseType.setStickyNote(findNotesSearchResultVO.getRemoveSticky());
			notesResponseType.setNoteDescription(findNotesSearchResultVO.getNotes());
			notesResponseType.setModel(findNotesSearchResultVO.getModel());
			notesResponseType.setNoteSeqID(findNotesSearchResultVO.getNoteSeqId());
					
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - FindNotesSearchResultVOToNotesResponseType: " + excep);
		}
	}
	
	/**
	* @Author:
	* @Description:This method is used copy RxExecTaskServiceVO to TaskDetailType
	*/
	public static void copyRxExecTaskServiceVOToTaskDetailType(
			final RxExecTaskServiceVO rxExecTaskServiceVO,
			final TaskDetailType taskDetailType, String timeZone) {

		try {
			taskDetailType.setRecomTaskId(rxExecTaskServiceVO.getStrRecomTaskId());
			taskDetailType.setTaskDesc(rxExecTaskServiceVO.getStrTaskDeails());
			taskDetailType.setTaskID(rxExecTaskServiceVO.getStrTaskId());
			taskDetailType.setCheckedFlag(rxExecTaskServiceVO.getStrSetFlag());
			taskDetailType.setLastUpdatedBy(rxExecTaskServiceVO.getStrLastUpdatedBy());
			taskDetailType.setDisable(rxExecTaskServiceVO.getStrDisable());
			taskDetailType.setUsl(rxExecTaskServiceVO.getStrUSL());
			taskDetailType.setLsl(rxExecTaskServiceVO.getStrLSL());
			taskDetailType.setTarget(rxExecTaskServiceVO.getStrTarget());
			taskDetailType.setTaskComments(rxExecTaskServiceVO.getStrTaskNotes());
			taskDetailType.setTaskExecutedUserId(rxExecTaskServiceVO.getStrTaskExecutedUserId());			
			if(rxExecTaskServiceVO.getCreationDate() != null){
			final GregorianCalendar gregCal = new GregorianCalendar();
			gregCal.setTime(rxExecTaskServiceVO.getCreationDate());
			RMDCommonUtility.setZoneOffsetTime(gregCal, timeZone);
			final XMLGregorianCalendar date = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gregCal);
			taskDetailType.setCreationDate(date);
			}
			taskDetailType.setDocPath(rxExecTaskServiceVO.getDocPath());
			taskDetailType.setDocTitle(rxExecTaskServiceVO.getDocTitle());	
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyRxExecTaskServiceVOToTaskDetailType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy RxExecTaskDetailsServiceVO to SolutionExecutionResponseType
	*/
	public static void copyRxExTaskDetVOToSolnExResType(
			final RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO,
			final SolutionExecutionResponseType solutionExecutionResponseType) {

		try {
			solutionExecutionResponseType.setAssetNumber(rxExecTaskDetailsServiceVO.getStrAssetNumber());
			solutionExecutionResponseType.setRxTitle(rxExecTaskDetailsServiceVO.getStrRxTitle());
			solutionExecutionResponseType.setCaseID(rxExecTaskDetailsServiceVO.getStrCaseId());
			solutionExecutionResponseType.setRepairAction(rxExecTaskDetailsServiceVO.getStrRepairAction());
			solutionExecutionResponseType.setClosedBy(rxExecTaskDetailsServiceVO.getStrClosedBy());
			solutionExecutionResponseType.setDefectMode(rxExecTaskDetailsServiceVO.getStrDefectMode());
			solutionExecutionResponseType.setSolutionExecutionID(rxExecTaskDetailsServiceVO.getStrRxExecutionId());
			solutionExecutionResponseType.setSolutionDelvID(rxExecTaskDetailsServiceVO.getStrRxDelvId());
			solutionExecutionResponseType.setRxCloseFlag(rxExecTaskDetailsServiceVO.getStrRxCloseFlag());
			solutionExecutionResponseType.setSolutionFileName(rxExecTaskDetailsServiceVO.getStrFileName());
			solutionExecutionResponseType.setSolutionFilePath(rxExecTaskDetailsServiceVO.getStrFilePath());
			solutionExecutionResponseType.setRxCaseId(rxExecTaskDetailsServiceVO.getStrRxCaseId());
			solutionExecutionResponseType.setSolutionId(rxExecTaskDetailsServiceVO.getStrRecomId());
			solutionExecutionResponseType.setIsRxSaved(rxExecTaskDetailsServiceVO.getIsRxSaved());
			solutionExecutionResponseType.setRecomNotes(rxExecTaskDetailsServiceVO.getRecomNotes());
			solutionExecutionResponseType.setRxDescription(rxExecTaskDetailsServiceVO.getRxDescription());
			solutionExecutionResponseType.setRxDeliveredBy(rxExecTaskDetailsServiceVO.getRxDeliveredBy());
			solutionExecutionResponseType.setPrerequisites(rxExecTaskDetailsServiceVO.getPrerequisites());
			solutionExecutionResponseType.setCustomerName(rxExecTaskDetailsServiceVO.getCustomerName());
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyRxExecTaskServiceVOToSolutionExecutionResponseType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy LastDownloadStatusResponseVO to LastDownLoadStatusResponseType
	*/
	public static void copyDwnldStatVOToDwnLdStatResType(
			final LastDownloadStatusResponseEoaVO lastDownloadStatusResponseVO,
			final LastDownLoadStatusResponseType lastDownLoadStatusResponseType) {

		try {
			lastDownLoadStatusResponseType.setCustHeader(lastDownloadStatusResponseVO.getCustHeader());
			lastDownLoadStatusResponseType.setCustomerHeader(lastDownloadStatusResponseVO.getCustomerHeader());
			lastDownLoadStatusResponseType.setAssetNumber(lastDownloadStatusResponseVO.getAssetNumber());
			lastDownLoadStatusResponseType.setAssetNoHeader(lastDownloadStatusResponseVO.getAssetNoHeader());
			lastDownLoadStatusResponseType.setLstDownloadHeader(lastDownloadStatusResponseVO.getLstDownloadHeader());
			lastDownLoadStatusResponseType.setState(lastDownloadStatusResponseVO.getState());
			lastDownLoadStatusResponseType.setStateHeader(lastDownloadStatusResponseVO.getStateHeader());
			lastDownLoadStatusResponseType.setVax(lastDownloadStatusResponseVO.getVax());
			lastDownLoadStatusResponseType.setVaxHeader(lastDownloadStatusResponseVO.getVaxHeader());
			lastDownLoadStatusResponseType.setEgu(lastDownloadStatusResponseVO.getEgu());
			lastDownLoadStatusResponseType.setEguHeader(lastDownloadStatusResponseVO.getEguHeader());
			lastDownLoadStatusResponseType.setInc(lastDownloadStatusResponseVO.getInc());
			lastDownLoadStatusResponseType.setIncHeader(lastDownloadStatusResponseVO.getIncHeader());
			lastDownLoadStatusResponseType.setSnp(lastDownloadStatusResponseVO.getSnp());
			lastDownLoadStatusResponseType.setSnpHeader(lastDownloadStatusResponseVO.getSnpHeader());
				
						
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyLastDownloadStatusResponseVOToLastDownLoadStatusResponseType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy ElementVO to Customer
	*/
	public static void copyElementVOToCustomer(
			final ElementVO elementVO,
			final Customer customer) {

		try {			
			customer.setCustomerID(elementVO.getId());
			customer.setCustomerName(elementVO.getName());
			customer.setCustomerSeqId(elementVO.getCustomerSeqId());
			customer.setIsDefault(elementVO.getIsdefault());
			customer.setIsAllCustomer(elementVO.isAllCustomer());
			customer.setIsAllCustomerdefault(elementVO.isAllCustomerdefault());
			//Added for US251801:Rx Update Workflow - Migrate this process within EoA/OMD - Ryan & Todd
			customer.setCustomerFullName(elementVO.getCustomerFullName());
									
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyElementVOToCustomer" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy FindLocationResultServiceVO to AddressDetailType
	*/
	public static void copyFndLocResltVOToAdresDetType(
			final FindLocationResultServiceVO findLocationResultServiceVO,
			final AddressDetailType addressDetailType) {

		try {
			
			addressDetailType.setCity(findLocationResultServiceVO.getStrCity());
			addressDetailType.setState(findLocationResultServiceVO.getStrState());
			addressDetailType.setCountry(findLocationResultServiceVO.getStrCountry());
			addressDetailType.setZipcode(findLocationResultServiceVO.getStrZipCode());
			addressDetailType.setTimeZone(findLocationResultServiceVO.getStrTimeZone());
			addressDetailType.setStatus(findLocationResultServiceVO.getStrStatus());
			addressDetailType.setPhone(findLocationResultServiceVO.getStrPhone());
			addressDetailType.setSiteAddress1(findLocationResultServiceVO.getStrSiteAddress1());
			addressDetailType.setSiteAddress2(findLocationResultServiceVO.getStrSiteAddress2());
			addressDetailType.setSiteAddress(findLocationResultServiceVO.getStrSiteAddress());
									
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyFindLocationResultServiceVOToAddressDetailType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy FindLocationResultServiceVO to LocationResponseType
	*/
	public static void copyFndLocResltVOToLocResType(
			final FindLocationResultServiceVO findLocationResultServiceVO,
			final LocationResponseType locationResponseType) {

		try {
			
			locationResponseType.setLocationId(findLocationResultServiceVO.getStrLocationId());
			locationResponseType.setLocationType(findLocationResultServiceVO.getStrLocationType());
			locationResponseType.setLocationName(findLocationResultServiceVO.getStrLocationName());
												
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyFindLocationResultServiceVOToLocationResponseType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy ElementVO to RolePrivilegesType
	*/
	public static void copyElementVOToRolePrivType(
			final ElementVO elementVO,
			final RolePrivilegesType rolePrivilegesType) {

		try {
			
			rolePrivilegesType.setRolePrivilege(elementVO.getId());
			rolePrivilegesType.setAccessLevel(elementVO.getName());
			rolePrivilegesType.setResourceType(elementVO.getType());
			if(null!=elementVO && null!=elementVO.getSortOrder() ){
				rolePrivilegesType.setSortOrder(elementVO.getSortOrder());
			}
														
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyElementVOToRolePrivilegesType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy GetCmTimeZoneVO to SystemTimeZoneResponseType
	*/
	public static void copyGetTmeZonVOToSysTmeZonResType(
			final GetCmTimeZoneVO getCmTimeZoneVO,
			final SystemTimeZoneResponseType systemTimeZoneResponseType) {

		try {
			
			systemTimeZoneResponseType.setName(getCmTimeZoneVO.getName());
			systemTimeZoneResponseType.setGmtDiff(getCmTimeZoneVO.getGmtDiff());
			systemTimeZoneResponseType.setFullName(getCmTimeZoneVO.getFullName());
			systemTimeZoneResponseType.setTimeZoneId(getCmTimeZoneVO.getTimeZoneId());
														
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyGetTmeZonVOToSysTmeZonResType" + excep);
		}

	}

	/**
	* @Author:
	* @Description:This method is used copy toolOutputEoaServiceVO to ToolOutputDetailsResponseType
	*/
	public static void copyToolOputSerVOToToolOputDetResType(
			final ToolOutputEoaServiceVO toolOutputEoaServiceVO,
			final ToolOutputDetailsResponseType toolOutputDetailsResponseType) {

		try {

			toolOutputDetailsResponseType.setSolutionID(toolOutputEoaServiceVO
					.getStrRecomId());
			toolOutputDetailsResponseType.setSolutionTitle(toolOutputEoaServiceVO
					.getStrRecomTitle());
			toolOutputDetailsResponseType.setSolutionProb(toolOutputEoaServiceVO
					.getStrRecomProb());
			toolOutputDetailsResponseType.setSolutionStatus(toolOutputEoaServiceVO
					.getStrRecomStatus());
			toolOutputDetailsResponseType.setSolutionDelv(toolOutputEoaServiceVO
					.getStrRecomDelv());
			toolOutputDetailsResponseType
					.setSolutionAccuracy(toolOutputEoaServiceVO.getStrUrgency());
			toolOutputDetailsResponseType.setEstRepairTime(toolOutputEoaServiceVO
					.getStrEstRepairTime());
			toolOutputDetailsResponseType.setUrgRepairTime(toolOutputEoaServiceVO
					.getStrUrgRepairValue());
			toolOutputDetailsResponseType
					.setEstRepairTimeValue(toolOutputEoaServiceVO
							.getStrEstRepairTimeValue());
			toolOutputDetailsResponseType
					.setSolutionStatusValue(toolOutputEoaServiceVO
							.getStrRecomStatusValue());
			toolOutputDetailsResponseType.setRuleDefId(toolOutputEoaServiceVO
					.getStrRuleDefId());
			toolOutputDetailsResponseType.setRuleID(toolOutputEoaServiceVO
					.getStrRuleId());
			//added for fetch tool output detail
			if (null != toolOutputEoaServiceVO.getStrFalseAlarmPct()) {
				toolOutputDetailsResponseType.setFalseAlarmPct(Float
						.parseFloat(toolOutputEoaServiceVO.getStrFalseAlarmPct()));
			}
			if (null != toolOutputEoaServiceVO.getStrToolCovgPct()) {
				toolOutputDetailsResponseType.setToolCovgPct(Float
						.parseFloat(toolOutputEoaServiceVO.getStrToolCovgPct()));
			}
			if (null != toolOutputEoaServiceVO.getStrMdscPerfPct()) {
				toolOutputDetailsResponseType.setMdscPerfPct(Float
						.parseFloat(toolOutputEoaServiceVO.getStrMdscPerfPct()));
			}
			toolOutputDetailsResponseType
					.setFalseAlarmColor(toolOutputEoaServiceVO
							.getStrFalseAlarmColor());
			toolOutputDetailsResponseType.setToolCovgColor(toolOutputEoaServiceVO
					.getStrToolCovgColor());
			toolOutputDetailsResponseType.setMdscPerfColor(toolOutputEoaServiceVO
					.getStrMdscPerfColor());
			toolOutputDetailsResponseType.setFalseAlarmInd(toolOutputEoaServiceVO
					.getStrFalseAlarmInd());
			toolOutputDetailsResponseType.setToolCovgInd(toolOutputEoaServiceVO
					.getStrToolCovgInd());
			toolOutputDetailsResponseType.setMdscPerfInd(toolOutputEoaServiceVO
					.getStrMdscPerfInd());
			toolOutputDetailsResponseType
			.setUrgency(toolOutputEoaServiceVO.getStrUrgency());
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyToolOputSerVOToToolOputDetResType"
					+ excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy ElementVO to LookupResponseType
	*/
	public static void copyElementVOToLookupResponseType(
			final ElementVO elementVO,
			final LookupResponseType lookupResponseType) {

		try {
			
			lookupResponseType.setLookupID(elementVO.getId());
			lookupResponseType.setLookupValue(elementVO.getName());
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyElementVOToLookupResponseType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy RxSearchResultServiceVO to SolutionResponseType
	*/
	public static void copyRxSearchResltSerVOToSolnResType(
			final RxSearchResultServiceVO rxSearchResultServiceVO,
			final SolutionResponseType solutionResponseType) {

		try {
			
			solutionResponseType.setSolutionStatus(rxSearchResultServiceVO.getStrStatus());
			solutionResponseType.setNotes(rxSearchResultServiceVO.getNotes());
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyRxSearchResltSerVOToSolnResType" + excep);
		}

	}
	
	/**
	* @Author:
	* @Description:This method is used copy RxSearchResultServiceVO to SolutionDetailType
	*/
	public static void copyRxSearchResltSerVOToSolnResType(
			final RxSearchResultServiceVO rxSearchResultServiceVO,
			final SolutionDetailType solutionDetailType) {

		try {
			
			solutionDetailType.setSolutionID(rxSearchResultServiceVO.getStrRxId());
			solutionDetailType.setSolutionTitle(rxSearchResultServiceVO.getStrTitle());
			solutionDetailType.setSolutionStatus(rxSearchResultServiceVO.getStrStatusValue());
			solutionDetailType.setUrgRepair(rxSearchResultServiceVO.getStrUrgRepairValue());
			solutionDetailType.setEstmTimeRepair(rxSearchResultServiceVO.getStrEstRepairTimeValue());
			solutionDetailType.setSolutionRevNo(rxSearchResultServiceVO.getStrVersion());
			solutionDetailType.setLocomotiveImpact(rxSearchResultServiceVO.getStrLocoImpact());
			solutionDetailType.setAuthor(rxSearchResultServiceVO.getStrAuthor());
			solutionDetailType.setSolutionType(rxSearchResultServiceVO.getStrType());
			solutionDetailType.setModel(rxSearchResultServiceVO.getStrModel());
			solutionDetailType.setLastEdited(rxSearchResultServiceVO.getLastEdited());
			solutionDetailType.setNoOfActiveWeeks(rxSearchResultServiceVO.getNoOfActiveWeeks());
			solutionDetailType.setSolutionRank(rxSearchResultServiceVO.getStrRank());
			solutionDetailType.setSolutionPrecision(rxSearchResultServiceVO.getStrPrecision());
			solutionDetailType.setAccuracy(rxSearchResultServiceVO.getStrAccuracy());
			solutionDetailType.setDeliveries(rxSearchResultServiceVO.getStrDeliveries());
			solutionDetailType.setReIssuePercent(rxSearchResultServiceVO.getStrReIssuePercent());
			solutionDetailType.setNtfPercent(rxSearchResultServiceVO.getStrNTFPercent());
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyRxSearchResltSerVOToSolnResType" + excep);
		}

	}
	
	/**
	* @Author: GE
	* @Description:This method is used copy LastDownloadStatusResponseVO to LastDownLoadStatusResponseType
	*/
	public static void copyFaultStatVOToFaultStatResType(
			final LastFaultStatusResponseEoaVO lastFaultStatusResponseVO,
			final LastFaultStatusResponseType lastFaultStatusResponseType) {

		try {
			lastFaultStatusResponseType.setAssetNumber(lastFaultStatusResponseVO.getAssetNumber());
			lastFaultStatusResponseType.setLstEOAFaultHeader(lastFaultStatusResponseVO.getLstEOAFaultHeader());
			lastFaultStatusResponseType.setLstESTPDownloadHeader(lastFaultStatusResponseVO.getLstESTPDownloadHeader());
			lastFaultStatusResponseType.setLstPPATSMsgHeader(lastFaultStatusResponseVO.getLstPPATSMsgHeader());
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyFaultStatVOToFaultStatResType" + excep);
		}

	}

	/**
	 * @param objFindCaseServiceVO
	 * @param queryParams
	 */
	public static void copyJaxbObjToServiceVO(
			final FindCaseServiceVO findCaseServiceVO,
			final CasesRequestType objCasesRequestType) {
		final Calendar calendarStart = Calendar.getInstance();
		final Calendar calendarEnd = Calendar.getInstance();
		final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(
				RMDServiceConstants.DATE_FORMAT2);

		try {
			final Date endDate = calendarEnd.getTime();
			if (null != objCasesRequestType.getNoDays() && !objCasesRequestType.getNoDays().isEmpty()) {

				calendarStart.add(Calendar.DATE,
						-Integer.parseInt((objCasesRequestType.getNoDays())));
				final Date startDate = calendarStart.getTime();
				findCaseServiceVO.setSelectedFromDate(startDate);
				findCaseServiceVO.setSelectedToDate(endDate);
			}
			if (null != objCasesRequestType.getLanguage() && !objCasesRequestType.getLanguage().isEmpty()) {
				findCaseServiceVO.setStrLanguage(objCasesRequestType.getLanguage());
			} else {
				findCaseServiceVO.setStrLanguage(OMDConstants.DEFAULT_LANGUAGE);
			}
			
			//Added for default userLanguage..
			if (null != objCasesRequestType.getUserLanguage() && !objCasesRequestType.getUserLanguage().isEmpty()) {
				findCaseServiceVO.setStrUserLanguage(objCasesRequestType.getUserLanguage());
			} else {
				findCaseServiceVO.setStrUserLanguage(OMDConstants.DEFAULT_LANGUAGE);
			}
			
			if (null != objCasesRequestType.getAssetHeader() && !objCasesRequestType.getAssetHeader().isEmpty()) {
				findCaseServiceVO.setStrAssetHeader(objCasesRequestType.getAssetHeader());
			} else {
				findCaseServiceVO
				.setStrAssetHeader(RMDServiceConstants.QTR_CUSTOMER);
			}
			if (null != objCasesRequestType.getQueueName() && !objCasesRequestType.getQueueName().isEmpty()) {
				findCaseServiceVO
				.setStrQueue(objCasesRequestType.getQueueName());
			} else {
				findCaseServiceVO.setStrQueue(OMDConstants.ALL);
			}
			if (null != objCasesRequestType.getAssetNumber() && !objCasesRequestType.getAssetNumber().isEmpty()) {
				findCaseServiceVO.setStrAssetNumber(objCasesRequestType.getAssetNumber());
			} else {
				findCaseServiceVO
				.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getCaseID() && !objCasesRequestType.getCaseID().isEmpty()) {

				findCaseServiceVO.setStrCaseId(objCasesRequestType.getCaseID());
			} else {
				findCaseServiceVO.setStrCaseId(RMDCommonConstants.EMPTY_STRING);

			}
			
			if (null != objCasesRequestType.getRxCaseID() && !objCasesRequestType.getRxCaseID().isEmpty()) {

				findCaseServiceVO.setStrRxCaseId(objCasesRequestType.getRxCaseID());
			} else {
				findCaseServiceVO.setStrRxCaseId(RMDCommonConstants.EMPTY_STRING);

			}
			
			if (null != objCasesRequestType.getCaseTitle() && !objCasesRequestType.getCaseTitle().isEmpty()) {
				findCaseServiceVO.setStrCaseTitle(objCasesRequestType.getCaseTitle());
			} else {
				findCaseServiceVO
				.setStrCaseTitle(RMDCommonConstants.EMPTY_STRING);

			}
			if (null != objCasesRequestType.getCaseStatus() && !objCasesRequestType.getCaseStatus().isEmpty()) {
				findCaseServiceVO.setStrCaseStatus(objCasesRequestType.getCaseStatus());
			} else {
				findCaseServiceVO
				.setStrCaseStatus(RMDCommonConstants.EMPTY_STRING);

			}
			if (null != objCasesRequestType.getUserName() && !objCasesRequestType.getUserName().isEmpty()) {
				findCaseServiceVO.setStrUserId(objCasesRequestType.getUserName());
			} else {
				findCaseServiceVO.setStrUserId(RMDCommonConstants.EMPTY_STRING);

			}
		
			if (null != objCasesRequestType.getStartDate() && !objCasesRequestType.getStartDate().isEmpty()) {
				findCaseServiceVO.setSelectedFromDate(SDF_FORMAT
						.parse(objCasesRequestType.getStartDate()));
			}
			if (null != objCasesRequestType.getEndDate() && !objCasesRequestType.getEndDate().isEmpty()) {
				findCaseServiceVO.setSelectedToDate(SDF_FORMAT
						.parse(objCasesRequestType.getEndDate()));
			}
			if (null != objCasesRequestType.getSolutionStatus() && !objCasesRequestType.getSolutionStatus().isEmpty()) {
				findCaseServiceVO.setStrRxStatus(objCasesRequestType.getSolutionStatus());
			} else {
				findCaseServiceVO.setStrRxStatus(RMDCommonConstants.ALL);
			}
			if (null != objCasesRequestType.getSolutionTitle() && !objCasesRequestType.getSolutionTitle().isEmpty()) {
				findCaseServiceVO.setStrRxTitle(objCasesRequestType.getSolutionTitle());
			} else {
				findCaseServiceVO
				.setStrRxTitle(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getSolFeedback() && !objCasesRequestType.getSolFeedback().isEmpty()) {
				findCaseServiceVO.setStrRxFdbk(objCasesRequestType.getSolFeedback());
			} else {
				findCaseServiceVO.setStrRxFdbk(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getSolutionId() && !objCasesRequestType.getSolutionId().isEmpty()) {
				findCaseServiceVO
				.setStrRxId(objCasesRequestType.getSolutionId());
			} else {
				findCaseServiceVO.setStrRxId(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getRuleDefId() && !objCasesRequestType.getRuleDefId().isEmpty()) {
				findCaseServiceVO.setStrRuleDefId(objCasesRequestType.getRuleDefId());
			} else {
				findCaseServiceVO
				.setStrRuleDefId(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getCustomerId() && !objCasesRequestType.getCustomerId().isEmpty()) {
				findCaseServiceVO.setStrCustomerId(objCasesRequestType.getCustomerId());
			} else {
				findCaseServiceVO
				.setStrCustomerId(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getAssetGrName() && !objCasesRequestType.getAssetGrName().isEmpty()) {
				findCaseServiceVO.setStrAssetGrpName(objCasesRequestType.getAssetGrName());
			} else {
				findCaseServiceVO
				.setStrAssetGrpName(RMDCommonConstants.EMPTY_STRING);
			}
			if (null != objCasesRequestType.getEstRepTm() && !objCasesRequestType.getEstRepTm().isEmpty()) {
				findCaseServiceVO.setEstRepairTime(objCasesRequestType.getEstRepTm());
			} else {
				findCaseServiceVO
				.setEstRepairTime(RMDCommonConstants.EMPTY_STRING);
			}
			findCaseServiceVO.setClosedRxLookBackDays(objCasesRequestType.getClosedRxlookBackDays());			
		} catch (Exception excep) {
			LOG.error("Error occured in copyQueryParamsToServiceVO - BeanUtility" + excep);
		}
	}	
/**
 * To copy the customer asset map in to selected service VO
 * @param lstAsstMap
 * @param serviceVO
 */
	
	
	public static void copyCustomerAssetToServiceVO(final List<ProdAssetMapRequestType> lstAsstMap, Object serviceVO){
		try {
			List<String> productList=new ArrayList<String>();
			
			for(ProdAssetMapRequestType objAsstReqType:lstAsstMap){
				productList.add(objAsstReqType.getProductName());
				}
			if(serviceVO instanceof AssetServiceVO){
			((AssetServiceVO) serviceVO).setProducts(productList);
			}
			if(serviceVO instanceof AssetLocatorBean){
				((AssetLocatorBean) serviceVO).setProducts(productList);
			}
			if(serviceVO instanceof FindCaseServiceVO){
				((FindCaseServiceVO) serviceVO).setProducts(productList);
			}
			if(serviceVO instanceof KPIDataBean){
				((KPIDataBean) serviceVO).setProducts(productList);
			}
			if (serviceVO instanceof AssetServiceVO) {
				((AssetServiceVO) serviceVO).setProducts(productList);
			}
			if (serviceVO instanceof FindCaseServiceVO) {
				((FindCaseServiceVO) serviceVO).setProducts(productList);
			}
			if (serviceVO instanceof KPIDataEoaBean) {
				((KPIDataEoaBean) serviceVO).setProducts(productList);
			}			
			if(serviceVO instanceof com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean){
				((com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean) serviceVO).setProducts(productList);
			}
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyCustomerAssetToServiceVO" + excep);
		}
	}

	public static void copyCaseSolnReqTypeToRecomDelvInfoServiceVO(
			CaseSolutionRequestType objSolutionRequestType,
			RecomDelvInfoServiceVO caseRXVO) {
		if (null != objSolutionRequestType.getCaseID()
				&& !objSolutionRequestType.getCaseID().isEmpty()) {
			caseRXVO.setStrCaseID(objSolutionRequestType.getCaseID());
		} else {
			caseRXVO.setStrCaseID(RMDCommonConstants.EMPTY_STRING);
		}
		if (null != objSolutionRequestType.getSolutionID()
				&& !objSolutionRequestType.getSolutionID().isEmpty()) {
			caseRXVO.setStrRxObjid(objSolutionRequestType.getSolutionID());
		} else {
			caseRXVO.setStrRxObjid(RMDCommonConstants.EMPTY_STRING);
		}
		if (null != objSolutionRequestType.getSolutionTitle()
				&& !objSolutionRequestType.getSolutionTitle().isEmpty()) {
			caseRXVO.setStrRxTitle(objSolutionRequestType.getSolutionTitle());
		} else {
			caseRXVO.setStrRxTitle(RMDCommonConstants.EMPTY_STRING);
		}
		if (null != objSolutionRequestType.getUserName()
				&& !objSolutionRequestType.getUserName().isEmpty()) {
			caseRXVO.setStrUserName(objSolutionRequestType.getUserName());
		} else {
			caseRXVO.setStrRxTitle(RMDCommonConstants.EMPTY_STRING);
		}
		if (null != objSolutionRequestType.getRecomNotes()
				&& !objSolutionRequestType.getRecomNotes().isEmpty()) {
			caseRXVO.setStrRecomNotes(objSolutionRequestType.getRecomNotes());
		} else {
			caseRXVO.setStrRecomNotes(RMDCommonConstants.EMPTY_STRING);
		}
		if (null != objSolutionRequestType.getUrgRepair()
				&& !objSolutionRequestType.getUrgRepair().isEmpty()) {
			caseRXVO.setStrUrgRepair(objSolutionRequestType.getUrgRepair());
		} 
		if (null != objSolutionRequestType.getEstmRepTime()
				&& !objSolutionRequestType.getEstmRepTime().isEmpty()) {
			caseRXVO.setStrEstmRepTime(objSolutionRequestType.getEstmRepTime());
		} 
		if (null != objSolutionRequestType.getCustomerId()
				&& !objSolutionRequestType.getCustomerId().isEmpty()) {
			caseRXVO.setCustomerId(objSolutionRequestType.getCustomerId());
		}
	}
	
	public static void copyRoleManagementRequestTypeToVO(
			final RoleManagementVO objRoleManagementVO,
			final RoleManagementRequestType objRoleManagementRequestType) {
		List<PrivilegesVO> listPrivilegesVO = new ArrayList<PrivilegesVO>();
		PrivilegesVO privilegeDetail = null;
		objRoleManagementVO.setRoleId(objRoleManagementRequestType
				.getRoleId());
		objRoleManagementVO.setRoleName(objRoleManagementRequestType
				.getRoleName());
		objRoleManagementVO
				.setRoleDescription(objRoleManagementRequestType
						.getRoleDescription());
		for (PrivilegeDetailsType detailsVO : objRoleManagementRequestType
				.getPrivileges()) {
			privilegeDetail = new PrivilegesVO();
			privilegeDetail.setPrivilegeID(detailsVO.getPrivilegeId());
			privilegeDetail.setSortOrder(detailsVO.getSortOrder());
			listPrivilegesVO.add(privilegeDetail);
		}
		objRoleManagementVO.setPrivileges(listPrivilegesVO);

	
 	}
	/**
	 * method to copy the rmd side assetServiceVo to assetEoaServiceVo
	 * @param AssetServiceVO
	 * @param AssetEoaServiceVO
	 */
	public static void copyAsstSeviceVOToAsstEoaServiceVO(
			final AssetServiceVO objAssetServiceVO,
			final AssetServiceVO objAssetEoaServiceVO) {
		objAssetEoaServiceVO.setAssetGroupName(objAssetServiceVO
				.getAssetGroupName());
		objAssetEoaServiceVO.setCustomerID(objAssetServiceVO.getCustomerID());
		objAssetEoaServiceVO.setAssetFrom(objAssetServiceVO.getAssetFrom());
		objAssetEoaServiceVO.setAssetTo(objAssetServiceVO.getAssetTo());
		objAssetEoaServiceVO.setProducts(objAssetServiceVO.getProducts());
		objAssetEoaServiceVO.setAssetNumberLike(objAssetServiceVO.getAssetNumberLike());
		objAssetEoaServiceVO.setStrAssetNumber(objAssetServiceVO.getStrAssetNumber());
		objAssetEoaServiceVO.setStrModelId(objAssetServiceVO.getStrModelId());
		objAssetEoaServiceVO.setStrFleetId(objAssetServiceVO.getStrFleetId());
	}
	
	/**
	* @Author:
	* @Description:This method is used copy RxExecTaskServiceVO to TaskDetailType
	*/
	public static void copyRxTaskDetailToTaskDetailType(
			final RxTaskDetailsVO rxTaskDetailsVO,
			final TaskDetailType taskDetailType) {

		try {
			taskDetailType.setTaskDesc( rxTaskDetailsVO.getTaskDescription());
			// Note . task sequence id in rxTaskDetailsVO  is task id in task detail type
			// 1, 2, 3 .
			taskDetailType.setTaskID(rxTaskDetailsVO.getTaskSequence());
			taskDetailType.setCheckedFlag(rxTaskDetailsVO.getCheckedFlag());
			taskDetailType.setUsl(rxTaskDetailsVO.getUsl());
			taskDetailType.setLsl(rxTaskDetailsVO.getLsl());
			taskDetailType.setTarget(rxTaskDetailsVO.getTarget());
			// Note . rxTaskDetailsVO.getTaskId is  object id
			taskDetailType.setRecomTaskId(rxTaskDetailsVO.getTaskId());
			
			taskDetailType.setLastUpdatedBy(rxTaskDetailsVO.getLastUpdateBy());

		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyRxTaskDetailToTaskDetailType" + excep);
		}

	}
	/**
	* @Author:
	* @Description:This method is used copy Asset to AssetResponseType
	*/
	public static void copyAsstMCSResponseToAsstRespType(Asset mcsAsset,
			AssetResponseType assetResponseType) {
		final GregorianCalendar gregCal = new GregorianCalendar();
		final SimpleDateFormat MCS_FORMAT = new SimpleDateFormat(
				RMDServiceConstants.DATE_FORMAT_MCS);
		try {
			assetResponseType.setAssetNumber(mcsAsset.getRoadNumber());
			assetResponseType.setAssetGroupName(mcsAsset.getRoadInitial());
			assetResponseType.setCustomerID(mcsAsset.getAssetOwnerId());
			assetResponseType.setFleet(mcsAsset.getFleet());
			assetResponseType.setModel(mcsAsset.getModel());
			assetResponseType.setControllerCfg(mcsAsset.getControllerConfig());
			assetResponseType.setDsControllerConfig(mcsAsset.getControllerConfig());
			assetResponseType.setCmuId(mcsAsset.getCmuSN());
			assetResponseType.setEgaCommId(mcsAsset.getEgaCommId());
			if(null != mcsAsset.getInstallDate().getValue() && !mcsAsset.getInstallDate().getValue().isEmpty()){
				gregCal.setTime(MCS_FORMAT.parse(mcsAsset.getInstallDate().getValue()));	
				assetResponseType.setInstallationDate(datatypeFactory.newXMLGregorianCalendar(gregCal));
			}
			
			assetResponseType.setPartStatus(mcsAsset.getAssetStatus());
			assetResponseType.setSoftwareVersion(mcsAsset.getSwManifest());
			
			
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyAsstMCSResponseToAsstRespType" + excep);
			excep.printStackTrace();
		}
		
	}

	
	/**
	 * @throws Exception 
	 * @Author:
	 * @Description:This method is used copy UserServiceVO to
	 *                   UsersResponseType
	 */
	public static void copyUserServiceVOToUsersResponseType(
			final UserServiceVO userServiceVO,
			final UsersResponseType responseType) throws Exception {
		PersonalDetailType personalDetailType = new PersonalDetailType();
		UserDetailType userDetailType = new UserDetailType();
		RoleType roleResp = null;
		RolesType roleType = null;
		try {
			if (null != userServiceVO) {
				personalDetailType
						.setFirstName(userServiceVO.getStrFirstName());
				personalDetailType.setLastName(userServiceVO.getStrLastName());
				//Added for replacing middle name with email.
				personalDetailType.setEmail(userServiceVO
						.getStrEmail());

				userDetailType
						.setUserSeqId(userServiceVO.getGetUsrUsersSeqId());
				userDetailType.setUserId(userServiceVO.getUserId());
				userDetailType.setUserType(userServiceVO.getUserType());
				userDetailType.setStatus(RMDCommonUtility
						.convertObjectToString(userServiceVO.getStatus()));
				userDetailType.setPassword(userServiceVO.getPassword());
				userDetailType.setCustomerId(userServiceVO.getCustomerId());
				userDetailType.setListCustomerIds(userServiceVO
						.getListCustomer());
				userDetailType.setStrLanguage(userServiceVO.getStrLanguage());
				userDetailType.setLastUpdatedBy(userServiceVO.getLastUpdatedBy());
				userDetailType.setLastUpdatedTime(userServiceVO.getLastUpdatedTime());
				//Added by Murali Medicherla for Rally Id : US226051
				userDetailType.setMobileAccess(userServiceVO.getMobileAccess());
				userDetailType.setEndUserScoring(userServiceVO.getEndUserScoring());
				responseType.setCustomerId(userServiceVO.getCustomerId());
				responseType.setDefaultRole(userServiceVO.getStrRole());
				responseType.setLanguage(userServiceVO.getStrLanguage());
				responseType.setTimeZone(userServiceVO.getStrTimeZone());
				if (null != personalDetailType) {
					responseType.setPersonalDetail(personalDetailType);
				}
				if (null != userDetailType) {
					responseType.setUserDetail(userDetailType);
				}

				if (null != userServiceVO.getRoles()
						&& RMDCommonUtility.isCollectionNotEmpty(userServiceVO
								.getRoles())) {
					for (RolesVO role : userServiceVO.getRoles()) {
						roleType = new RolesType();
						roleType.setUserRolesSeqId(role.getGetUsrRolesSeqId());
						roleType.setRoleName(role.getRoleName());
						responseType.getRolesDetail().add(roleType);
					}

				}
				roleResp = new RoleType();
				responseType.setRoles(roleResp);
			}
		} catch (Exception excep) {
			LOG.error("Error occured in CMBeanUtility - copyUserServiceVOToUsersResponseType"
					+ excep);
			throw excep;
		}

	}
	
	
}