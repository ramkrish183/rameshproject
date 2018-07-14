package com.ge.trans.rmd.services.cases.test;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;


public class AddRXClient {

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AddRXClient.class);

	private String strURL="http://localhost:8080/RMDWebServices/caseservice";

	public static void main(final String[] args) {
		final AddRXClient objCasesClient=new AddRXClient();
		objCasesClient.addRX();
	}

	public void addRX(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		
		URL url=null;
		String addRX=null;
		try{
			addRX=strURL+"/addRX";
			url=new URL(addRX);
			jc = JAXBContext.newInstance("com.ge.trans.rmd.services.cases.valueobjects");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("PUT");
			LOG.debug(connection.getRequestMethod());
			connection.setRequestProperty("Content-Type", "application/xml");
			connection.setRequestProperty(OMDConstants.USERID, "admin");
			connection.setRequestProperty(OMDConstants.LANGUAGE, "zh");
			connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");

			final OutputStream os = connection.getOutputStream();
			CaseSolutionRequestType caseSol = new CaseSolutionRequestType();
			caseSol.setCaseID("K145769");
			caseSol.setSolutionID("112228");
			caseSol.setSolutionTitle("LocoCAM/LDVR no video input to CMU");
			caseSol.setUserName("kkiran");
			jc.createMarshaller().marshal(caseSol, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}catch (Exception excep) {
			LOG.error("Error occured in AddRXClient -  "+excep);
		}

	}

}
