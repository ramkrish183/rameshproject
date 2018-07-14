/**
 * ============================================================
 * Classification: GE Confidential
 * File : FaultStrategyClient.java
 * Description : 
 * Package : com.ge.trans.omd.services.faultstrategy.test
 * Author : iGATE Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.faultstrategy.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultClassificationDetailType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultStrategyDetailType;
import com.ge.trans.rmd.services.faultstrategy.valueobjects.FaultStrategyRequestType;
/*******************************************************************************
 * @Author : iGATE Patni 
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as FaultStrategy Client will pass request to the FaultstrategyResource
 *              related funtionalities
 * @History :
 ******************************************************************************/

public class FaultStrategyClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FaultStrategyClient.class);

	private String strURL="http://localhost:8080/omdservices/faultstrategyservice";

	public static void main(final String[] args) {
		final FaultStrategyClient objFaultStrategyClient=new FaultStrategyClient();
		objFaultStrategyClient.saveFaultCodeDetails();
	}

	public void saveFaultCodeDetails(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		URL url=null;
		String strdispatchToQueue=null;
		FaultStrategyRequestType objFaultStrategyRequestType=null;
		FaultStrategyDetailType objFaultStrategyDetailType =null;
		FaultClassificationDetailType objFaultClassificationDetailType=null;
		try {
			strdispatchToQueue=strURL+"/saveFaultCodeDetails";
			url=new URL(strdispatchToQueue);
			jc = JAXBContext.newInstance("com.ge.trans.omd.services.faultstrategy.valueobjects");			
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			//START:: Base Resources Attribute
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");	
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");
			//END:: Base Resources Attribute

			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			objFaultStrategyRequestType=new FaultStrategyRequestType();
			objFaultStrategyDetailType=new FaultStrategyDetailType();
			objFaultStrategyDetailType.setFaultCode("26-0501");
			objFaultStrategyDetailType.setToolUrgency("N");
			objFaultStrategyDetailType.setNote("Webservices testing");
			objFaultStrategyRequestType.setLastUpdatedBy("QTRTesting");
			objFaultStrategyRequestType.setFaultSeqID(176943);
			objFaultStrategyDetailType.setFaultCodeDescription("LPS +5Ã¤Â¼ï¿½ Ã§â€�ÂµÃ¥Å½â€¹Ã¤Â¸ï¿½Ã¥Å“Â¨Ã¥â€¦Â¬Ã¥Â·Â®Ã¨Å’Æ’Ã¥â€ºÂ´Ã¤Â¹â€¹Ã¥â€ â€¦");
			objFaultClassificationDetailType=new FaultClassificationDetailType();
			objFaultClassificationDetailType.setFltClassificationID("Critical Fault");
			objFaultClassificationDetailType.setFltClassificationName("Critical Fault");
			objFaultStrategyRequestType.getFaultClassification().add(objFaultClassificationDetailType);
			objFaultStrategyRequestType.setFaultStrategyDetail(objFaultStrategyDetailType);		
			objFaultStrategyRequestType.setFaultClassSelected("No Action Fault");

			jc.createMarshaller().marshal(objFaultStrategyRequestType, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		} catch (Exception excep) {
			LOG.error("Error occured in FaultStrategyClient - saveFaultCodeDetails method. "+excep);
		}

	}

}
