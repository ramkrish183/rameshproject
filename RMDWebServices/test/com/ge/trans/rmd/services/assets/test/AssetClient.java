/**
 * ============================================================
 * Classification: GE Confidential
 * File : AssetClient.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.assets.test;
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

package com.ge.trans.rmd.services.assets.test;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.services.assets.valueobjects.HealthCheckRequestType;
import com.ge.trans.rmd.services.assets.valueobjects.Model;

/*******************************************************************************
 *
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This is the client class used for calling Post methods in
 *              Resources
 *
 *
 * @History :
 *
 ******************************************************************************/
public class AssetClient {

	private JAXBContext jc;

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AssetClient.class);

	/**
	 * @Description:This is the main method used to call other methods in the
	 *                   class
	 *
	 */
	public static void main(final String[] args) {

		final AssetClient objAssetClient = new AssetClient();
		try {
			
			objAssetClient.getModel();
			// objAssetClient.requestHealthCheck();
		} catch (Exception excep) {
			LOG.error("Error Occurred in AssetClient-Main Method" + excep);
		}
	}

	/**
	 * @Description:This method is the constructor of the class
	 *
	 */
	public AssetClient() {
		try {
			jc = JAXBContext.newInstance("com.ge.trans.rmd.services.assets.valueobjects");
		} catch (JAXBException jexcep) {
			LOG.error("Cannot create JAXBContext " + jexcep);
		}

	}

	/**
	 *
	 * @return void
	 * @Description:This method is used for requesting the HealthCheck
	 */
	public void requestHealthCheck() {
		try {

			final URL url = new URL("http://localhost:8080/RMDWebServices/assetservice/requestHealthCheck");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");

			// START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			// END:: Base Resources Attribute

			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			final HealthCheckRequestType objHealthCheckRequestType = new HealthCheckRequestType();
			objHealthCheckRequestType.setAssetNumber("10");
			// objHealthCheckRequestType.setLanguage("zh");
			// objHealthCheckRequestType.setUserLanguage("en");
			jc.createMarshaller().marshal(objHealthCheckRequestType, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			connection.disconnect();

		} catch (Exception excep) {
			LOG.error("Error Occurred in AssetClient-requestHealthCheck method"+ excep);
		}
	}
	
	
	public void getModel(){
		try {

			JAXBContext jaxbCtx  = JAXBContext.newInstance(Model[].class);
			final URL url = new URL("http://localhost:8080/RMDWebServices/assetservice/getModels");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");

			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());

			connection.disconnect();

		} catch (Exception excep) {
			LOG.error("Error Occurred in AssetResource -getModel method"+ excep);
		}		
		
	}

}
