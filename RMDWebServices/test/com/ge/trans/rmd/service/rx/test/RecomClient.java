package com.ge.trans.rmd.service.rx.test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;


public class RecomClient {
	
	private JAXBContext jc;

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RecomClient.class);
	
	public static void main(String[] args) {
		
		RecomClient client = new RecomClient();
		client.testReplaceRx();
		
	}
	
	
	public RecomClient() {
		try {
			jc = JAXBContext
			.newInstance("com.ge.trans.rmd.services.cases.valueobjects");
		} catch (JAXBException je) {
			LOG.debug("Cannot create JAXBContext " + je);
		}

	}

	public void testReplaceRx (){
		
		try{
			
			CaseSolutionRequestType caseRequest = new CaseSolutionRequestType();

			
			caseRequest.setRxCaseID("P279161");
			caseRequest.setCaseID("P279161");
			caseRequest.setCustomerId("CP");
			caseRequest.setSolutionID("1");
			caseRequest.setEstmRepTime("2");
			caseRequest.setUrgRepair("R");
			caseRequest.setUserName("212338764");
			
			LOG.debug("Opening Connection" );
			
			final URL url = new URL("http://localhost:8080/RMDWebServices/caseservice/replaceRx");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			
			connection.setRequestProperty("Content-Type", "application/xml");
			final OutputStream os = connection.getOutputStream();
			
			jc.createMarshaller().marshal(caseRequest, os);
			os.flush();
			connection.getResponseCode();
			LOG.debug("response code " + connection.getResponseCode());
			LOG.debug("response code " + connection.getResponseMessage());
			
		}catch ( Exception e ){
			LOG.error("Exception occured in RecomClient - testReplaceRx method"
					+ e);
			
			
			
		}
		
	}
}
