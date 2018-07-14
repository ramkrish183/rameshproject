/**
 * ============================================================
 * Classification: GE Confidential
 * File : ToolsClient.java
 * Description : This Class act as ToolsClient Webservices and used to test
 * 				the PUT,POST,DELETE HttpRequest in toolsresource.java
 * Package : com.ge.trans.rmd.services.tools.test;
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 11, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.tools.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.tools.valueobjects.ReferCaseRequestType;
import com.ge.trans.rmd.services.tools.valueobjects.ScheduleRequestType;
import com.ge.trans.rmd.common.constants.OMDConstants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as  Testing to post uris
 * @History :
 ******************************************************************************/

public class ToolsClient {
	private JAXBContext jc;
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ToolsClient.class);
	public static void main(final String[] args) {

		final ToolsClient objToolsClient = new ToolsClient();
		try {
			 objToolsClient.scheduleTools();
		} catch (Exception excep) {
			LOG.error("Error occured in main method of ToolsClient class "+excep);
		}
	}

	public ToolsClient() {
		try {
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.tools.valueobjects");
		} catch (JAXBException je) {
			LOG.error("Cannot create JAXBContext " + je);
		}
	}


	/**
	 * This method is used for Testing Refer cases uri
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void referCase() {
		List<String> arlAssetId = new ArrayList<String>();
		try {
			final URL url = new URL("http://localhost:8080/omdservices/toolservice/referCase");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			//Start--Setting the header values
			connection.setRequestProperty(OMDConstants.LANGUAGE, OMDConstants.CHINESE_LANGUAGE);
			//End--Setting the header values
			final OutputStream os = connection.getOutputStream();
			final ReferCaseRequestType objRequestType = new ReferCaseRequestType();
			objRequestType.setReferFromCaseId("10-006425");
			objRequestType.setReferToCaseId("11-007366");
			jc.createMarshaller().marshal(objRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code "+ connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in Refercase() in Toolclient class"+excep);
		}
	}
	/**
	 * This method is used for Testing scheduleTools uri
	 * @param
	 * @return
	 * @throws RMDServiceException
	 */
	public void scheduleTools() {

		List<String> arlAssetId = new ArrayList<String>();
		try {
			final URL url = new URL( "http://localhost:8080/omdservices/toolservice/scheduleTools");
			final HttpURLConnection connection = (HttpURLConnection) url .openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final ScheduleRequestType objRequestType = new ScheduleRequestType();
			objRequestType.setAssetSeqId("30255");
			objRequestType.setCriticalFlag("N");
			objRequestType.setPriority("4");
			objRequestType.setService("EOA");
			objRequestType.setScheduleType("NOR");
			jc.createMarshaller().marshal(objRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in scheduleTools() in Toolclient class"+excep);
		}
	}

}
