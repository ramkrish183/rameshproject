/**
 * ============================================================
 * Classification: GE Confidential
 * File : AdminClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.admin.test
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.admin.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.services.admin.valueobjects.ApplicationParametersRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.ColumnInfoType;
import com.ge.trans.rmd.services.admin.valueobjects.ContactRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.DataInfoType;
import com.ge.trans.rmd.services.admin.valueobjects.LocationRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.MasterTemplateRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.PersonalDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.ProximityRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.SaveFavFilterType;
import com.ge.trans.rmd.services.admin.valueobjects.TemplateDetailsRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.TemplateParamRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.TemplateSummaryRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.UserDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.UserRequestType;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AdminClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AdminClient.class);

	private JAXBContext jc;

	public static void main(final String[] args) {

		final AdminClient objAdminClient = new AdminClient();
		try {
			objAdminClient.addFilter();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - main method" + excep);
		}
	}

	public AdminClient() {
		try {
			jc = JAXBContext
			.newInstance("com.ge.trans.rmd.services.admin.valueobjects");
		} catch (JAXBException je) {
			LOG.debug("Cannot create JAXBContext " + je);
		}

	}

	public void saveUserDetails() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveUserDetails");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final UserRequestType objUserRequestType = new UserRequestType();
			final PersonalDetailType objPersonalDetailType = new PersonalDetailType();
			objPersonalDetailType.setUserContactSeqId(745);
			//objPersonalDetailType.setMiddleName("TESTxdsdfsdfsd");
			objPersonalDetailType.setFirstName("RAMANAsdfssd");
			objPersonalDetailType.setLastName("KUSETTYfsdfsdfsdfsd");
			objPersonalDetailType.setHomePhone("5188941399");
			objUserRequestType.setPersonalDetail(objPersonalDetailType);
			objUserRequestType.setRole("388");
			final UserDetailType objUserDetailType = new UserDetailType();
			objUserDetailType.setUserName("testuser0");
			objUserDetailType.setHomePage("HOME");
			objUserDetailType.setStatus("1");
			objUserDetailType.setUserId("testuser0000");
			objUserDetailType.setUserSeqId(594);
			objUserRequestType.setUserDetail(objUserDetailType);
			jc.createMarshaller().marshal(objUserRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - saveUserDetails method"
					+ excep);
		}
	}

	public void createLocation() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/createLocation");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final LocationRequestType objLocationRequestType = new LocationRequestType();
			objLocationRequestType.setLocationId("WS-test2");
			objLocationRequestType.setLocationType("Shop");
			objLocationRequestType.setLocationName("loction");
			objLocationRequestType.setCity("com");
			objLocationRequestType.setState("Tel");
			objLocationRequestType.setCountry("Pakis");
			objLocationRequestType.setZipCode("1223");
			objLocationRequestType.setTimeZone("119");
			objLocationRequestType.setStatus("Active");
			objLocationRequestType.setPhone("8976");
			objLocationRequestType.setSiteAddress1("Qwer street");
			objLocationRequestType.setSiteAddress2("mnbv nagar");
			jc.createMarshaller().marshal(objLocationRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - createLocation method"
					+ excep);
		}
	}

	public void createContact() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/createContact");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final ContactRequestType objContactRequestType = new ContactRequestType();
			objContactRequestType.setContactSeqID(811);
			objContactRequestType.setStatus("Active");
			objContactRequestType.setFirstName("WS-testing");
			objContactRequestType.setLastName("Service-testing");
			objContactRequestType.setPhone("99407");
			objContactRequestType.setEmail("ant");
			objContactRequestType.setJobTitle("Web Service Testing");
			objContactRequestType.setTimeZone(null);
			objContactRequestType.setDialComm(null);
			objContactRequestType.setCellPhone("99408");
			objContactRequestType.setHomePhone("99409");
			objContactRequestType.setLocationID("WS-test");
			jc.createMarshaller().marshal(objContactRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - createContact method"
					+ excep);
		}
	}

	public void saveProximityDetails() {
		ProximityRequestType objProximityRequestType = null;
		JAXBContext jc;
		URL url = null;
		HttpURLConnection connection = null;
		final Calendar calendar = Calendar.getInstance();
		final GregorianCalendar objGregorianCalendar = new GregorianCalendar();
		XMLGregorianCalendar createdDate;
		try {

			jc = JAXBContext
			.newInstance("com.ge.trans.omd.services.admin.valueobjects");
			url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveProximityDetails");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			// END:: Base Resources Attribute

			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			objProximityRequestType = new ProximityRequestType();
			objProximityRequestType.setProximitySeqID("281");
			objProximityRequestType
			.setProximityDesc("Webservices Testing aug 31");
			objProximityRequestType.setSeLatitude("6000");
			objProximityRequestType.setSeLongitude("6000");
			objProximityRequestType.setStatus("Y");
			objProximityRequestType.setProximityLabel("So");
			objProximityRequestType
			.setLastUpdatedBy(OMDConstants.DEFAULT_USERNAME);
			objProximityRequestType.setNwLatitude("4000");
			objProximityRequestType.setNwLatitude("4000");
			final Date lastUpdatedDate = calendar.getTime();
			objGregorianCalendar.setTime(lastUpdatedDate);
			createdDate = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(objGregorianCalendar);
			objProximityRequestType.setLastUpdatedDate(createdDate);
			jc.createMarshaller().marshal(objProximityRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();

		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - saveProximityDetails method"
					+ excep);
		}

	}

	public void resetPassword() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/resetPassword");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final UserRequestType objUserRequestType = new UserRequestType();
			final UserDetailType objUserDetailType = new UserDetailType();
			objUserDetailType.setUserName("Case Creation");
			objUserDetailType.setUserId("Case Creation");
			objUserDetailType.setPassword("grdcloudrmd");
			objUserDetailType.setUserSeqId(498);
			objUserRequestType.setUserDetail(objUserDetailType);
			jc.createMarshaller().marshal(objUserRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - resetPassword method"
					+ excep);
		}
	}

	public void createUser() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/createUser");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final UserRequestType objUserRequestType = new UserRequestType();
			final PersonalDetailType objPersonalDetailType = new PersonalDetailType();
			//objPersonalDetailType.setMiddleName("Singh");
			objPersonalDetailType.setFirstName("Mahindra");
			objPersonalDetailType.setLastName("Dhoni");
			objPersonalDetailType.setHomePhone("12346543");
			objUserRequestType.setPersonalDetail(objPersonalDetailType);
			objUserRequestType.setRole("388");
			final UserDetailType objUserDetailType = new UserDetailType();
			objUserDetailType.setHomePage("HOME");
			objUserDetailType.setStatus("1");
			objUserDetailType.setUserId("TESTUSR");
			objUserRequestType.setUserDetail(objUserDetailType);
			jc.createMarshaller().marshal(objUserRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - createUser method"
					+ excep);
		}
	}

	public void saveApplicationParameters() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveApplicationParameters");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final ApplicationParametersRequestType objRequestType = new ApplicationParametersRequestType();

			objRequestType
			.setListDescription("This is the name of Closure program");
			objRequestType.setListName("TOOLS_CLOSURE");

			jc.createMarshaller().marshal(objRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - saveApplicationParameters method"
					+ excep);
		}
	}

	public void applyTemplate() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/applyTemplate");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final TemplateSummaryRequestType objTemplateRequestType = new TemplateSummaryRequestType();
			objTemplateRequestType.setCustomer("EOA");
			objTemplateRequestType.setFleet("FAST2");
			objTemplateRequestType.setModel("CCA1");
			objTemplateRequestType.setTemplateID(101);

			jc.createMarshaller().marshal(objTemplateRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - applyTemplate method"
					+ excep);
		}
	}

	public void saveMasterTemplate() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveMasterTemplate");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final MasterTemplateRequestType objTemplateRequestType = new MasterTemplateRequestType();
			objTemplateRequestType.setMasterParamID(407);
			objTemplateRequestType.setDefaultValue(5);
			objTemplateRequestType.setLength(25);
			objTemplateRequestType.setLinkColname(1840);
			objTemplateRequestType.setLowerBound(4);
			objTemplateRequestType.setUpperBound(6);
			objTemplateRequestType.setDataFormat("SUBSTR(round($,2),0,5)");
			objTemplateRequestType
			.setParamDesc("Webservices Testing HTML/J2EE/Oracle");
			objTemplateRequestType.setToolTip("Tool Tip for WS");
			objTemplateRequestType.setHeaderHtml("Webservices Testing HTML");
			objTemplateRequestType.setHeaderWidth((short) 110);
			objTemplateRequestType.setSortOrder(199);
			objTemplateRequestType.setStatus("N");

			jc.createMarshaller().marshal(objTemplateRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - saveMasterTemplate method"
					+ excep);
		}
	}

	public void saveTemplate() {
		TemplateParamRequestType objTemplateParamRequestType;
		final List<TemplateParamRequestType> lstTemplateParamRequestType = new ArrayList<TemplateParamRequestType>();
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/adminservice/saveTemplate");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			final TemplateDetailsRequestType objTemplateRequestType = new TemplateDetailsRequestType();
			final TemplateSummaryRequestType objSummaryRequestType = new TemplateSummaryRequestType();

			objSummaryRequestType.setStatus("Y");
			objSummaryRequestType.setCreatedBy("WS Tester");
			objSummaryRequestType.setCustomer("QTR");
			objSummaryRequestType.setFleet("NJ2");
			objSummaryRequestType.setLevel(88);
			objSummaryRequestType.setModel("CCA2");
			objSummaryRequestType.setService("4785");
			objSummaryRequestType
			.setTemplateDesc("WebService Testing - verify-2");
			objSummaryRequestType.setTemplateID(0);
			objSummaryRequestType.setTemplateName("WebService Testing");
			objSummaryRequestType.setView("4784");
			objTemplateRequestType
			.setTemplateSummaryRequest(objSummaryRequestType);

			objTemplateParamRequestType = new TemplateParamRequestType();
			objTemplateParamRequestType.setParameterId("166");
			objTemplateParamRequestType.setDataFormat("SubString('1,2')");
			objTemplateParamRequestType.setLength("12");
			objTemplateParamRequestType.setLowerBound("26");
			objTemplateParamRequestType.setParameterDesc("first-verify");
			objTemplateParamRequestType.setParameterName("Ã¦Â¡Â£Ã¤Â½ï¿½");
			objTemplateParamRequestType.setParameterValue("28");
			objTemplateParamRequestType.setSortOrder("201");
			objTemplateParamRequestType.setStatus("Y");
			objTemplateParamRequestType
			.setToolTip("For 11st1 Parameter Tool Tip-verify");
			objTemplateParamRequestType.setUpperBound("30");
			lstTemplateParamRequestType.add(objTemplateParamRequestType);

			objTemplateParamRequestType = new TemplateParamRequestType();
			objTemplateParamRequestType.setParameterId("168");
			objTemplateParamRequestType.setDataFormat("SubString('1,2')");
			objTemplateParamRequestType.setLength("12");
			objTemplateParamRequestType.setLowerBound("26");
			objTemplateParamRequestType.setParameterDesc("last-verify");
			objTemplateParamRequestType.setParameterName("Ã¦Â¡Â£Ã¤Â½ï¿½");
			objTemplateParamRequestType.setParameterValue("28");
			objTemplateParamRequestType.setSortOrder("201");
			objTemplateParamRequestType.setStatus("Y");
			objTemplateParamRequestType.setToolTip("last");
			objTemplateParamRequestType.setUpperBound("30");
			lstTemplateParamRequestType.add(objTemplateParamRequestType);

			objTemplateParamRequestType = new TemplateParamRequestType();
			objTemplateParamRequestType.setParameterId("167");
			objTemplateParamRequestType.setDataFormat("SubString('2,4')");
			objTemplateParamRequestType.setLength("24");
			objTemplateParamRequestType.setLowerBound("52");
			objTemplateParamRequestType.setParameterDesc("second-verify");
			objTemplateParamRequestType
			.setParameterName("Ã§â€°ÂµÃ¥Â¼â€¢Ã¤Â¸Â»Ã¥â€ºÅ¾Ã¨Â·Â¯Ã§â€ºÂ´Ã¦Âµï¿½Ã§â€?ÂµÃ¥Å½â€¹");
			objTemplateParamRequestType.setParameterValue("56");
			objTemplateParamRequestType.setSortOrder("202");
			objTemplateParamRequestType.setStatus("Y");
			objTemplateParamRequestType
			.setToolTip("For 22nd2 Parameter Tool Tip");
			objTemplateParamRequestType.setUpperBound("60");
			lstTemplateParamRequestType.add(objTemplateParamRequestType);

			objTemplateParamRequestType = new TemplateParamRequestType();
			objTemplateParamRequestType.setParameterId("");
			objTemplateParamRequestType.setDataFormat("SubString('2,4')");
			objTemplateParamRequestType.setLength("36");
			objTemplateParamRequestType.setLowerBound("104");
			objTemplateParamRequestType.setParameterDesc("third-verify");
			objTemplateParamRequestType
			.setParameterName("Ã¨â€œâ€žÃ§â€?ÂµÃ¦Â±Â Ã¥â€¦â€¦Ã§â€?ÂµÃ¦Å½Â§Ã¥Ë†Â¶Ã¥â„¢Â¨Ã¥ï¿½Â Ã§Â©ÂºÃ¦Â¯â€?");
			objTemplateParamRequestType.setParameterValue("112");
			objTemplateParamRequestType.setSortOrder("203");
			objTemplateParamRequestType.setStatus("N");
			objTemplateParamRequestType
			.setToolTip("For third Parameter Tool Tip");
			objTemplateParamRequestType.setUpperBound("120");
			lstTemplateParamRequestType.add(objTemplateParamRequestType);

			objTemplateRequestType
			.setTemplateParamRequest(lstTemplateParamRequestType);

			jc.createMarshaller().marshal(objTemplateRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - saveTemplate method"
					+ excep);
		}
	}

	public void deleteApplicationParameter() {
		try {
			final URL url = new URL("http://localhost:8080/omdservices/adminservice/deleteApplicationParameter/lookupSeqId/4864");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("DELETE");


			//START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, OMDConstants.DEFAULT_LANGUAGE);
			//END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");

			LOG.debug("response code " + connection.getResponseCode());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - deleteApplicationParameter method" + excep);
		}
	}


	public void deleteApplicationParameterTest() {
		try {
			final URL url = new URL("http://localhost:8080/omdservices/adminservice/deleteApplicationParameter/lookupName/DEMO_SEP7");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("DELETE");


			//START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, OMDConstants.DEFAULT_LANGUAGE);
			//END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			LOG.debug("response code " + connection.getResponseCode());

			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - deleteApplicationParameter method" + excep);
		}
	}
	
	public void deleteFavoriteFilter() {
		try {
			final URL url = new URL("http://localhost:8080/RMDWebServices/adminservice/deleteFilter?filterId=1");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("DELETE");


			//START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, OMDConstants.DEFAULT_USERNAME);
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, OMDConstants.DEFAULT_LANGUAGE);
			//END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			LOG.debug("response code " + connection.getResponseCode());

			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Exception occured in AdminClient - deleteApplicationParameter method" + excep);
		}
	}
	
	public void addFilter() {
		try {
			final URL url = new URL(
			"http://3.239.241.37:8080/RMDWebServices/adminservice/addFilter");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			// START:: Base Resources Attribute
			connection.setRequestProperty("userId",
					"admin");
			connection.setRequestProperty("language",
					"en");
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final SaveFavFilterType objUserRequestType = new SaveFavFilterType();
			objUserRequestType.setScreenName("FLEETS");
			objUserRequestType.setLinkUsrRoleSeqId("582");
//			objUserRequestType.setFilterId("1");
			ColumnInfoType objcol=new ColumnInfoType();
			List<String> columnName=new ArrayList<String>();
			List<String> columnType=new ArrayList<String>();
			columnName.add("URGENCY");
			columnType.add("text");
			columnName.add("EST_REPAIR_TIME");
			columnType.add("number");
			objcol.setColumnName(columnName);
			objcol.setColumnType(columnType);
			objUserRequestType.setColumnInfo(objcol);
			List<String> daList = new ArrayList<String>();
			daList.add("R,Y,W");
			daList.add("3,2");
			DataInfoType objData= new DataInfoType();
			objData.setDataValue(daList);
			objUserRequestType.setDataInfo(objData);
		/*	final PersonalDetailType objPersonalDetailType = new PersonalDetailType();
			objPersonalDetailType.setMiddleName("Singh");
			objPersonalDetailType.setFirstName("Mahindra");
			objPersonalDetailType.setLastName("Dhoni");
			objPersonalDetailType.setHomePhone("12346543");
			objUserRequestType.setPersonalDetail(objPersonalDetailType);
			objUserRequestType.setRole("21");
			final UserDetailType objUserDetailType = new UserDetailType();
			objUserDetailType.setHomePage("HOME");
			objUserDetailType.setStatus("1");
			objUserDetailType.setUserId("TESTUSR1");
			objUserDetailType.setUserSeqId(461);
			objUserDetailType.setCustomerId("CSX");
			objUserRequestType.setUserDetail(objUserDetailType);*/
			jc.createMarshaller().marshal(objUserRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
		}
	}
}
