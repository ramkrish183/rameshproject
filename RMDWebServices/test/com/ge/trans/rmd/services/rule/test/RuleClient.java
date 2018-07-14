/**
 * ============================================================
 * Classification: GE Confidential
 * File : RuleClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.rule.test
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 11, 2011
 * History
 * Modified By : iGATE Patni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.rule.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.assets.valueobjects.Fleet;
import com.ge.trans.rmd.services.rule.valueobjects.ComplexRule;
import com.ge.trans.rmd.services.rule.valueobjects.FinalRuleRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.FinalVirtualRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.ManualRunRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinition;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionConfig;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionCustomer;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionFleet;
import com.ge.trans.rmd.services.rule.valueobjects.RuleDefinitionModel;
import com.ge.trans.rmd.services.rule.valueobjects.RuleHistory;
import com.ge.trans.rmd.services.rule.valueobjects.RuleRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RuleTesterRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.RunReCreateRequestType;
import com.ge.trans.rmd.services.rule.valueobjects.SimpleRule;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefConfig;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefCustomer;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefFleet;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualDefinition;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualEquation;
import com.ge.trans.rmd.services.rule.valueobjects.VirtualFilter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

;

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
public class RuleClient {

	private JAXBContext jc;
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleClient.class);

	public static void main(String[] args) {

		RuleClient objRuleClient = new RuleClient();
		try {
			objRuleClient.saveRule();

		} catch (Exception excep) {
			LOG.error("Error occured in main method of RuleClient" + excep);
		}
	}

	public RuleClient() {
		try {
			jc = JAXBContext
			.newInstance("com.ge.trans.rmd.services.rule.valueobjects");

		} catch (JAXBException jexcep) {
			LOG.error("Error occured in RuleClient method of RuleClient"
					+ jexcep);
		}

	}

	public void requestManualRun() {
		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/ruleservice/requestManualRun");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			// Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			// End--Setting the header values
			final OutputStream os = connection.getOutputStream();
			final ManualRunRequestType objManualRunRequestType = new ManualRunRequestType();
			objManualRunRequestType.setAssetNumber(21L);
			objManualRunRequestType.setMode("DS");

			final String strFromdt = "08/21/2011 12:22:34";
			final String strEnd = "08/24/2011 12:22:34";

			final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat(
					"MM/dd/yyyy hh:mm:ss");
			final Date Dtfrom = SDF_FORMAT.parse(strFromdt);
			final Date DtEnd = SDF_FORMAT.parse(strEnd);

			final GregorianCalendar objGregorianCalendar = new GregorianCalendar();
			objGregorianCalendar.setTime(Dtfrom);
			final XMLGregorianCalendar fromDate = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(objGregorianCalendar);
			objManualRunRequestType.setFromDate(fromDate);
			objGregorianCalendar.setTime(DtEnd);

			final XMLGregorianCalendar toDate = DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(objGregorianCalendar);
			objManualRunRequestType.setToDate(toDate);

			jc.createMarshaller().marshal(objManualRunRequestType, os);

			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in requestManualRun method of RuleClient"
					+ excep);
		}

	}

	public void requestRuleTest() {

		final List<String> arlAssetId = new ArrayList<String>();
		try {
			final URL url = new URL(
			"http://localhost:8089/omdservices/ruleservice/requestRuleTest");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			// Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			// End--Setting the header values
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final RuleTesterRequestType objRuleTesterRequestType = new RuleTesterRequestType();
			objRuleTesterRequestType.setRuleID("10028");
			objRuleTesterRequestType.setNoofdays("3");
			arlAssetId.add("10");
			arlAssetId.add("3");
			objRuleTesterRequestType.getAssetNumbers().addAll(arlAssetId);
			jc.createMarshaller().marshal(objRuleTesterRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode()
					+ "message " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in requestRuleTest method of RuleClient"
					+ excep);
		}
	}

	public void saveRule() {
		
		HttpURLConnection connection = null;
		FinalRuleRequestType objfinalRuleRequestType = null;
		ComplexRule objComplexRule = null;
		SimpleRule objSimpleRule = null;
		RuleDefinition objRuleDefinition = null;
		List<ComplexRule> listComplexRule = null;
		List<SimpleRule> listSimpleRule = null;
		List<RuleHistory> listRuleHistory = null;
		RuleHistory objRuleHistory = null;
		final List<RuleDefinition> listRuleDefinition = new ArrayList<RuleDefinition>();

		List<RuleDefinitionModel> ruleDefinitionModelList;
		RuleDefinitionModel objRuleDefinitionModel = null;
		List<RuleDefinitionConfig> ruleDefinitionConfig;
		RuleDefinitionConfig objruleDefinitionConfig = null;
		List<RuleDefinitionFleet> ruleDefinitionFleet;
		RuleDefinitionFleet objruleDefinitionFleet = null;
		RuleDefinitionModel ruleDefinitionModel;
		List<RuleDefinitionCustomer> ruleDefinitionCustomer;
		RuleDefinitionCustomer objRuleDefinitionCustomer = null;

		try {

			objfinalRuleRequestType = new FinalRuleRequestType();

			listSimpleRule = new ArrayList<SimpleRule>();
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("101");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("01");
			com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause smc1 = new com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause();
			smc1.setColumnName("ECU Load Pot HP - 1012");
			smc1.setColumnType("ANM");
			smc1.setColumnId("9168");
			smc1.setAnomTechniqueId("SHIFT");
			smc1.setFunction("> AND <");
			smc1.setValue1("55");
			smc1.setValue2("299");
			objSimpleRule.getSimpleRuleClause().add(smc1);
			listSimpleRule.add(objSimpleRule);
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("102");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("02");
			com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause smc2 = new com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause();
			smc2.setColumnName("Radfan State-6028");
			smc2.setColumnType("EDP");
			smc2.setColumnId("9284");
			smc2.setSeqVal("0");
			smc2.setFunction("=");
			smc2.setValue1("99");
			objSimpleRule.getSimpleRuleClause().add(smc2);
			listSimpleRule.add(objSimpleRule);
			
			/**3rd simple rule**/
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("103");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("03");
			listSimpleRule.add(objSimpleRule);
			
			
			/**4rd simple rule**/
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("104");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("04");
			com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause smc3 = new com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause();
			smc3.setColumnName("Radfan State-6028");
			smc3.setColumnType("ENG");
			smc3.setColumnId("9284");
			smc3.setFunction("!=");
			smc3.setValue1("199");
			objSimpleRule.getSimpleRuleClause().add(smc3);
			listSimpleRule.add(objSimpleRule);
			
			
			/**5rd simple rule**/
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("105");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("05");
			com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause smc4 = new com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause();
			smc4.setColumnName("Radfan State-6028");
			smc4.setColumnType("ENG");
			smc4.setColumnId("9284");
			smc4.setFunction("!=");
			smc4.setValue1("4697");
			objSimpleRule.getSimpleRuleClause().add(smc4);
			listSimpleRule.add(objSimpleRule);
			
			/**6th simple rule**/
			objSimpleRule = new SimpleRule();
			objSimpleRule.setSimpleruleID("106");
			objSimpleRule.setFaultCode("01-6065");
			objSimpleRule.setSubID("06");
			com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause smc6 = new com.ge.trans.rmd.services.rule.valueobjects.SimpleRuleClause();
			smc6.setColumnName("Radfan State-6028");
			smc6.setColumnType("ENG");
			smc6.setColumnId("9284");
			smc6.setFunction("!=");
			smc6.setValue1("4697");
			objSimpleRule.getSimpleRuleClause().add(smc6);
			listSimpleRule.add(objSimpleRule);
			objfinalRuleRequestType.getSimpleRule().addAll(listSimpleRule);

			objComplexRule = new ComplexRule();
			listComplexRule = new ArrayList<ComplexRule>();
			objComplexRule.setComplexRuleID("400");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("P");
			objComplexRule.setRule1("101");
			objComplexRule.setRule2("102");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("24");
			listComplexRule.add(objComplexRule);

			/**2nd complex rule*/
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("401");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("P");
			objComplexRule.setRule1("103");
			objComplexRule.setRule2("104");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("5");
			listComplexRule.add(objComplexRule);
			/**2nd complex rule ends*/
			
			/**3nd complex rule*/
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("402");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("H");
			objComplexRule.setRule1("105");
			objComplexRule.setRule2("106");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("2");
			listComplexRule.add(objComplexRule);
			//objfinalRuleRequestType.getComplexRule().addAll(listComplexRule);
			/**3nd complex rule ends*/
			
			/**4nd complex rule*/
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("403");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("H");
			objComplexRule.setRule1("400");
			objComplexRule.setRule2("402");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("2");
			listComplexRule.add(objComplexRule);
			//objfinalRuleRequestType.getComplexRule().addAll(listComplexRule);
			/**4nd complex rule ends*/
			
			/**5nd complex rule*/
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("404");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("H");
			objComplexRule.setRule1("401");
			objComplexRule.setRule2("403");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("1");
			listComplexRule.add(objComplexRule);
			
			/**6nd complex rule ends for rule on rule*/
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("405");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("H");
			objComplexRule.setRule1("404");
			objComplexRule.setRule2("403");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("1");
			listComplexRule.add(objComplexRule);
			
			
			
			objComplexRule = new ComplexRule();
			objComplexRule.setComplexRuleID("406");
			objComplexRule.setFrequency("1");
			objComplexRule.setTimeFlag("H");
			objComplexRule.setRule1("148067");
			objComplexRule.setRule2("405");
			objComplexRule.setFunction("AND");
			objComplexRule.setTimewindow("1");
			listComplexRule.add(objComplexRule);
			
			//objfinalRuleRequestType.getComplexRule().addAll(listComplexRule);
			//Rule definition
			objRuleDefinition = new RuleDefinition();
			objRuleDefinition.setRecommendation("AA V/hz clone 12");
			// objRuleDefinition.setRuleDefID("2587");
			objRuleDefinition.setRuleType("FILTER");
			objRuleDefinition.setMessage("EOA Rule auth testing");
			//	Model
			// ruledefintion--->RuleDefinitionModel list
			objRuleDefinitionModel = new RuleDefinitionModel();
			objRuleDefinitionModel.setModelID("268436600");
			objRuleDefinitionModel.setModelName("ACCCA");
			objRuleDefinition.getRuleDefinitionModelList().add(
					objRuleDefinitionModel);

			objRuleDefinitionModel = new RuleDefinitionModel();
			objRuleDefinitionModel.setModelID("268436601");
			objRuleDefinitionModel.setModelName("DCCCA");
			objRuleDefinition.getRuleDefinitionModelList().add(
					objRuleDefinitionModel);

			RuleDefinitionConfig objruleDefinitionConfig1 = new RuleDefinitionConfig();
			objruleDefinitionConfig1.setConfiguration("1102(14, 1): Alternator Selection Option");
			objruleDefinitionConfig1.setValue1("1");
			objruleDefinitionConfig1.setFunction(">");
			objRuleDefinition.getRuleDefinitionConfig().add(
					objruleDefinitionConfig1);

			RuleDefinitionCustomer objRuleDefinitionCustomer1 = new RuleDefinitionCustomer();
			Fleet flt = null;
			ArrayList arlfleets = new ArrayList<Fleet>();
			flt = new Fleet();
			flt.setFleet("NJ");
			flt.setFleetID("1504");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("NJ1");
			flt.setFleetID("1504");
			arlfleets.add(flt);
			objRuleDefinitionCustomer1.setRuleDefFleet(arlfleets);
			objRuleDefinitionCustomer1.setCustomerID("268435460");
			objRuleDefinition.getRuleDefinitionCustomer().add(
					objRuleDefinitionCustomer1);

		/*	objRuleDefinitionCustomer1 = new RuleDefinitionCustomer();
			objRuleDefinitionCustomer1.setCustomerID("268435458");
			objRuleDefinition.getRuleDefinitionCustomer().add(
					objRuleDefinitionCustomer1);*/
			objfinalRuleRequestType.getRuleDefinitionList().add(
					objRuleDefinition);
			/*******************second rule definition details --Start********************/
			
			objRuleDefinition = new RuleDefinition();
			objRuleDefinition.setRecommendation("INV1 Ground Fault");
			// objRuleDefinition.setRuleDefID("2587");
			objRuleDefinition.setRuleType("DIAGNOSTIC");
			objRuleDefinition.setMessage("rule authoring testing");
			
			objruleDefinitionConfig1 = new RuleDefinitionConfig();
			objruleDefinitionConfig1.setConfiguration("1103(10, 1): Snow Blaster Option");
			objruleDefinitionConfig1.setValue1("1");
			objruleDefinitionConfig1.setValue2("100");
			objruleDefinitionConfig1.setFunction("> AND <");
			objRuleDefinition.getRuleDefinitionConfig().add(
					objruleDefinitionConfig1);

			objRuleDefinitionCustomer1 = new RuleDefinitionCustomer();
			 arlfleets = new ArrayList<Fleet>();
			flt = new Fleet();
			flt.setFleet("CSX17");
			flt.setFleetID("901");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("CSX10");
			flt.setFleetID("1601");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("CSX1C");
			flt.setFleetID("2004");
			arlfleets.add(flt);
			
			objRuleDefinitionCustomer1.setRuleDefFleet(arlfleets);
			objRuleDefinitionCustomer1.setCustomerID("268435462");
			objRuleDefinition.getRuleDefinitionCustomer().add(
					objRuleDefinitionCustomer1);

			objRuleDefinitionCustomer1 = new RuleDefinitionCustomer();
			objRuleDefinitionCustomer1.setCustomerID("268435458");
			arlfleets = new ArrayList<Fleet>();
			flt = new Fleet();
			flt.setFleet("UP4");
			flt.setFleetID("5220");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("UP16");
			flt.setFleetID("1506");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("UP25");
			flt.setFleetID("2201");
			arlfleets.add(flt);
			flt = new Fleet();
			flt.setFleet("UP25B");
			flt.setFleetID("2701");
			arlfleets.add(flt);
			objRuleDefinitionCustomer1.setRuleDefFleet(arlfleets);
			objRuleDefinition.getRuleDefinitionCustomer().add(
					objRuleDefinitionCustomer1);
			/*objfinalRuleRequestType.getRuleDefinitionList().add(
					objRuleDefinition);*/
			/*******************second rule definition details --End********************/
			//second rule definition details --End			
			objRuleHistory = new RuleHistory();
			
			
			
			//  objRuleHistory.setOriginalID("15403856");
			 objRuleHistory.setRevisionHistory("WS Testing rulhistory New*** ");
			  //objRuleHistory.setRuleID("15403856");
			 //objRuleHistory.setVersionNumber("1");
			 
			//objRuleHistory.set
			objfinalRuleRequestType.getRuleHistory().add(objRuleHistory);
			// listRuleDefinition.add(objRuleDefinition);
			/*objfinalRuleRequestType.getRuleDefinitionList().add(
					objRuleDefinition);*/
			//objfinalRuleRequestType.setRuledescription("wdqw");
			objfinalRuleRequestType.setRuleTitle("TestRule for WS## 14");
			objfinalRuleRequestType.setFamily("CCA");

			objfinalRuleRequestType.setUserName("ptg");
			objfinalRuleRequestType.setLanguage("en");
			objfinalRuleRequestType.setUserlanguage("en");
			objfinalRuleRequestType.setCompletionStatus("Yes");
			objfinalRuleRequestType.setFinalruleID("186178");
			objfinalRuleRequestType.setSubSystem("Train Control & Integration");
			objfinalRuleRequestType.setRuleDescription("Testing Webservices ");
			/*
			 * ObjectMapper obj = new ObjectMapper(); obj.writeValue(os,
			 * objfinalRuleRequestType);
			 * 
			 * jc.createMarshaller().marshal(objfinalRuleRequestType, os);
			 * os.flush();
			 */


			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

			WebResource webResource = client
					.resource("http://localhost:8080/RMDWebServices/ruleservice/saveRule");


			webResource.accept(MediaType.APPLICATION_XML);
			ClientResponse response = webResource.type("application/xml").header("language", "en").header("userID", "ptg")
					.post(ClientResponse.class, objfinalRuleRequestType);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

		} catch (Exception excep) {
			LOG.error("Error occured in saveRule method of RuleClient" + excep);
		}

	}

	public void activateDeactivateRule() {
		try {

			final RuleRequestType objRuleRequestType = new RuleRequestType();
			objRuleRequestType.setIsactive("N");
			// objRuleRequestType.setLanguage("en");
			objRuleRequestType.setRuleID("119167");
		
			
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);

			WebResource webResource = client
					.resource("http://localhost:8080/RMDWebServices/ruleservice/activateDeactivateRule");

		
			
			
			webResource.accept(MediaType.APPLICATION_XML,MediaType.TEXT_HTML);
			ClientResponse response = webResource.type("application/xml")
					.put(ClientResponse.class, objRuleRequestType);

//		
			if (response.getStatus() != 201) {
				if(response.getStatus()==204){
					
				}else{
				OMDException omdException=response.getEntity(OMDException.class);
				final String strResExcepMessage=omdException.getMessage();
				final String strResExcepCode=omdException.getCode();
				
			}
			}
			
			
		} catch (Exception excep) {
			LOG.error("Error occured in activateDeactivateRule method of RuleClient"
					+ excep);
		}
	}

	public void requestRunrecreate() {

		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/ruleservice/requestRunrecreate");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE,
					OMDConstants.CHINESE_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERLANGUAGE,
					OMDConstants.DEFAULT_LANGUAGE);
			connection.setRequestProperty(OMDConstants.USERID,
					OMDConstants.DEFAULT_USERNAME);
			// End--Setting the header values

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final RunReCreateRequestType objRunReCreateRequestType = new RunReCreateRequestType();
			objRunReCreateRequestType.setRuleID("10038");
			objRunReCreateRequestType.setRunID("7460");
			objRunReCreateRequestType.setCaseID("11-007224");
			jc.createMarshaller().marshal(objRunReCreateRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode()
					+ "message " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in requestRunrecreate method of RuleClient"
					+ excep);
		}
	}

	public void saveVirtual() {
		FinalVirtualRequestType objFinalVirtualRequestType;
		VirtualEquation objVirtualEquation;
		VirtualFilter objVirtualFilter;
		VirtualDefinition objVirtualDefinition;
		VirtualDefCustomer objVirtualDefCustomer;
		VirtualDefFleet objVirtualDefFleet;
		VirtualDefConfig objVirtualDefConfig;

		List<VirtualEquation> arlVirtualEquation = new ArrayList<VirtualEquation>();
		List<VirtualDefinition> arlVirtualDef = new ArrayList<VirtualDefinition>();
		List<VirtualFilter> arlVirtualFilter = new ArrayList<VirtualFilter>();
		List<VirtualDefCustomer> arlVirtualDefCustomer = new ArrayList<VirtualDefCustomer>();
		List<String> arlModel = new ArrayList<String>();
		List<VirtualDefConfig> arlVirtualDefConfig = new ArrayList<VirtualDefConfig>();
		List<VirtualDefFleet> arlVirtualDefFleet = new ArrayList<VirtualDefFleet>();
		List<String> lstCustomParam = new ArrayList<String>();

		try {
			final URL url = new URL(
			"http://localhost:8080/omdservices/ruleservice/saveVirtual");
			final HttpURLConnection connection = (HttpURLConnection) url
			.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();

			// Virtual Details
			objFinalVirtualRequestType = new FinalVirtualRequestType();
			objFinalVirtualRequestType.setVirtualName("V_WebService_4");
			objFinalVirtualRequestType
			.setVirtualDesc("Virtual that created through web service");
			objFinalVirtualRequestType.setVirtualType("C");
			objFinalVirtualRequestType.setFamily("CCA");

			// First Virtual Equation
			objVirtualEquation = new VirtualEquation();
			objVirtualEquation.setEquationId("100");
			objVirtualEquation.setEquation("$1???$*10");
			objVirtualEquation.setActive("Y");
			objVirtualEquation.setVirtualType("I");
			arlVirtualEquation.add(objVirtualEquation);

			// Second Virtual Equation
			objVirtualEquation = new VirtualEquation();
			objVirtualEquation.setEquationId("101");
			objVirtualEquation.setCustomFileName("Web Service");
			lstCustomParam.add("MP_0011");
			lstCustomParam.add("MP_0012");
			objVirtualEquation.setCustomParameter(lstCustomParam);
			objVirtualEquation.setActive("Y");
			objVirtualEquation.setVirtualType("I");
			arlVirtualEquation.add(objVirtualEquation);

			// Third Virtual Equation
			objVirtualEquation = new VirtualEquation();
			objVirtualEquation.setEquationId("102");
			objVirtualEquation.setEquation("#100#*#101#");
			objVirtualEquation.setActive("Y");
			objVirtualEquation.setLookBackPoints("20");
			objVirtualEquation.setLookBackTime("30");
			objVirtualEquation.setVirtualType("C");
			arlVirtualEquation.add(objVirtualEquation);
			objFinalVirtualRequestType.setVirtualEquation(arlVirtualEquation);

			// First Filter
			objVirtualFilter = new VirtualFilter();
			objVirtualFilter.setParameter("1???");
			objVirtualFilter.setFunction("=");
			objVirtualFilter.setValue1("12");
			objVirtualFilter.setValue2("13");
			arlVirtualFilter.add(objVirtualFilter);

			// Second Filter
			objVirtualFilter = new VirtualFilter();
			objVirtualFilter.setParameter("2???");
			objVirtualFilter.setFunction(">");
			objVirtualFilter.setValue1("12");
			objVirtualFilter.setValue2("13");
			arlVirtualFilter.add(objVirtualFilter);
			objFinalVirtualRequestType.setVirtualFilter(arlVirtualFilter);

			// First Rule Definition
			objVirtualDefinition = new VirtualDefinition();

			// First Customer
			objVirtualDefCustomer = new VirtualDefCustomer();
			objVirtualDefCustomer.setCustomer("QTR");

			// First Customer - First Fleet
			objVirtualDefFleet = new VirtualDefFleet();
			objVirtualDefFleet.setFleet("NJ2");
			arlVirtualDefFleet.add(objVirtualDefFleet);

			// First Customer - Second Fleet
			objVirtualDefFleet = new VirtualDefFleet();
			objVirtualDefFleet.setFleet("NJ3");
			arlVirtualDefFleet.add(objVirtualDefFleet);
			objVirtualDefCustomer.setVirtualDefFleet(arlVirtualDefFleet);
			arlVirtualDefCustomer.add(objVirtualDefCustomer);

			// Second Customer
			objVirtualDefCustomer = new VirtualDefCustomer();
			arlVirtualDefFleet = new ArrayList<VirtualDefFleet>();
			objVirtualDefCustomer.setCustomer("FAST");

			// Second Customer - First fleet
			objVirtualDefFleet = new VirtualDefFleet();
			objVirtualDefFleet.setFleet("FAST1");
			arlVirtualDefFleet.add(objVirtualDefFleet);

			// Second Customer - second fleet
			objVirtualDefFleet = new VirtualDefFleet();
			objVirtualDefFleet.setFleet("FAST2");
			arlVirtualDefFleet.add(objVirtualDefFleet);
			objVirtualDefCustomer.setVirtualDefFleet(arlVirtualDefFleet);
			arlVirtualDefCustomer.add(objVirtualDefCustomer);

			objVirtualDefinition.setVirtualDefCustomer(arlVirtualDefCustomer);

			// Model
			arlModel.add("CCA1");
			arlModel.add("CCA2");
			objVirtualDefinition.setModel(arlModel);

			// First config
			objVirtualDefConfig = new VirtualDefConfig();
			objVirtualDefConfig.setConfiguration("Test_Cfg1");
			objVirtualDefConfig.setFunction("=");
			objVirtualDefConfig.setValue1("108");
			objVirtualDefConfig.setValue2("109");
			arlVirtualDefConfig.add(objVirtualDefConfig);

			// Second config
			objVirtualDefConfig = new VirtualDefConfig();
			objVirtualDefConfig.setConfiguration("Test_Cfg2");
			objVirtualDefConfig.setFunction(">");
			objVirtualDefConfig.setValue1("118");
			objVirtualDefConfig.setValue2("129");
			arlVirtualDefConfig.add(objVirtualDefConfig);

			objVirtualDefinition.setVirtualDefConfig(arlVirtualDefConfig);

			arlVirtualDef.add(objVirtualDefinition);
			objFinalVirtualRequestType.setVirtualDefinition(arlVirtualDef);

			jc.createMarshaller().marshal(objFinalVirtualRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in saveVirtual method of RuleClient"
					+ excep);
		}

	}

}