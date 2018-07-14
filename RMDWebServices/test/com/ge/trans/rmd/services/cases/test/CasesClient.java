/**
 * ============================================================
 * Classification: GE Confidential
 * File : CasesClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.test
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 21, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */

package com.ge.trans.rmd.services.cases.test;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.services.cases.valueobjects.CaseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.CasesRequestType;

/*****************************************************************************************************************
 *
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 *
 ********************************************************************************************************************/
public class CasesClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CasesClient.class);

	private String strURL="http://localhost:8080/omdservices/caseservice";

	public static void main(final String[] args) {
		final CasesClient objCasesClient=new CasesClient();
		objCasesClient.dispatchToQueue();
	}

	public void dispatchToQueue(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType objCaseRequestType=null;
		URL url=null;
		String strdispatchToQueue=null;
		try{
			strdispatchToQueue=strURL+"/dispatchToQueue";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");

			final OutputStream os = connection.getOutputStream();
			objCaseRequestType=new CaseRequestType();
			objCaseRequestType.setCaseID("11-007254");
			objCaseRequestType.setQueueName("Work");
			objCaseRequestType.setVersion(1);
			objCaseRequestType.setAssetNumber("1");
			jc.createMarshaller().marshal(objCaseRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}catch (Exception excep) {
			LOG.error("Error occured in CasesClient - dispatchToQueue"+excep);
		}

	}
	public void takeOwnershipTest(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType objCaseRequestType=null;
		URL url=null;
		String strdispatchToQueue=null;
		try {
			strdispatchToQueue=strURL+"/takeOwnerShip";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			final OutputStream os = connection.getOutputStream();
			objCaseRequestType=new CaseRequestType();
			objCaseRequestType.setCaseID("11-007254");
			objCaseRequestType.setQueueName("Work");
			objCaseRequestType.setUserName("admin");
			objCaseRequestType.setVersion(10);

			jc.createMarshaller().marshal(objCaseRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - takeOwnershipTest method."+excep);
		}

	}

	public void saveCaseTest(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType objCaseRequestType=null;
		URL url=null;
		String strdispatchToQueue=null;
		try {
			strdispatchToQueue=strURL+"/saveCaseDetails";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");

			final OutputStream os = connection.getOutputStream();
			objCaseRequestType=new CaseRequestType();
			objCaseRequestType.setCaseID("11-006724");
			objCaseRequestType.setCaseTitle("test value for case modified through web service xxd");

			jc.createMarshaller().marshal(objCaseRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - saveCaseTest method."+excep);
		}

	}

	public void reOpenCaseTest(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType objCaseRequestType=null;
		URL url=null;
		String strdispatchToQueue=null;
		try {
			strdispatchToQueue=strURL+"/reOpenCase";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");

			final OutputStream os = connection.getOutputStream();
			objCaseRequestType=new CaseRequestType();
			objCaseRequestType.setCaseID("11-007389");
			objCaseRequestType.setUserName("admin");
			objCaseRequestType.setLanguage("zh");

			jc.createMarshaller().marshal(objCaseRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - reOpenCaseTest method."+excep);
		}

	}

	public void closeCaseTest(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType closeCase=null;
		URL url=null;
		String strdispatchToQueue=null;
		try {
			strdispatchToQueue=strURL+"/closeCase";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			final OutputStream os = connection.getOutputStream();
			closeCase = new CaseRequestType();
			closeCase.setCaseID("11-007389");

			jc.createMarshaller().marshal(closeCase, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - reOpenCaseTest method."+excep);
		}

	}
	public void createCase(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		CaseRequestType objCaseRequestType=null;
		URL url=null;
		String strdispatchToQueue=null;
		try {
			strdispatchToQueue=strURL+"/createCase";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			final OutputStream os = connection.getOutputStream();
			objCaseRequestType=new CaseRequestType();
			objCaseRequestType.setCaseTitle("WS testing new case created");
			objCaseRequestType.setCaseType("Diagnostics");
			objCaseRequestType.setAssetNumber("30270");
			objCaseRequestType.setUrgency("High");
			objCaseRequestType.setQueueName("Work");
			objCaseRequestType.setUserName("QTR");

			jc.createMarshaller().marshal(objCaseRequestType, os);
			os.flush();
			LOG.debug(connection.getResponseCode());
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - createCase method."+excep);
		}

	}

	public void deliverSolution() {
		JAXBContext jc;
		try {
		final	URL url = new URL("http://localhost:8080/omdservices/caseservice/deliverSolution");
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");

			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");

			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			final OutputStream os = connection.getOutputStream();

			CaseSolutionRequestType objSolutionRequestType = new CaseSolutionRequestType();
			objSolutionRequestType.setCaseID("11-007164");
			objSolutionRequestType.setSolutionID("11401");
			objSolutionRequestType.setEstmRepTime("3");
			objSolutionRequestType.setUrgRepair("W");
			objSolutionRequestType.setSolutionFilePath("C:\\export\\home\\rmdmgrs\\apps\\");
			objSolutionRequestType.setRecomNotes("Delivered by Web-Services testing aaa");
			jc.createMarshaller().marshal(objSolutionRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code "+ connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CasesClient - deliverSolution method."+excep);
		}
	}
	
	public void  getOpenCases (){
		

		JAXBContext jc;
		try {
		final	URL url = new URL("http://localhost:8080/omdservices/caseservice/getCases");
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.cases.valueobjects");

			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			final OutputStream os = connection.getOutputStream();

			CasesRequestType caseReqType = new CasesRequestType();
			
			caseReqType.setCustomerId("CP");
			caseReqType.setDwq("DWQ");
			jc.createMarshaller().marshal(caseReqType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code "+ connection.getResponseMessage());

			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in CaseResource  getCases()."+excep);
		}
	
		
	}
}