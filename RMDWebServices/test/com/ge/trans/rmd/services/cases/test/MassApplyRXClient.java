package com.ge.trans.rmd.services.cases.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseRequestType;
import com.ge.trans.rmd.services.cases.valueobjects.MassApplyRXRequestType;

public class MassApplyRXClient {
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(MassApplyRXClient.class);

	private String strURL="http://localhost:8080/omdservices/caseservice";

	public static void main(final String[] args) {
		final MassApplyRXClient objCasesClient=new MassApplyRXClient();
		objCasesClient.massApplyRX();
	}

	public void massApplyRX(){
		JAXBContext jc;
		HttpURLConnection connection=null;
		MassApplyRXRequestType massApplyRXRequest=null;
		URL url=null;
		String strdispatchToQueue=null;
		try{
			strdispatchToQueue=strURL+"/massApplyRX";
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
			massApplyRXRequest=new MassApplyRXRequestType();
			massApplyRXRequest.setUserId("502172132");
			massApplyRXRequest.setSolutionID("123456");
			massApplyRXRequest.setCustomerId("CP");
			massApplyRXRequest.setAssetGrpName("CP");
			massApplyRXRequest.setAssetNumber("8808");
			jc.createMarshaller().marshal(massApplyRXRequest, os);
			os.flush();
			connection.getResponseCode();
			connection.disconnect();
		}catch (Exception excep) {
			LOG.error("Error occured in CasesClient - dispatchToQueue"+excep);
		}

	}
}
