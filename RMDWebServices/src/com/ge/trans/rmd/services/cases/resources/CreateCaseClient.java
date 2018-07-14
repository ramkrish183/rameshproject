package com.ge.trans.rmd.services.cases.resources;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseRequestType;

public class CreateCaseClient {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CreateCaseClient.class);

    private String strURL = "http://localhost:8080/RMDWebServices/caseservice";

    public static void main(final String[] args) {
        final CreateCaseClient objCasesClient = new CreateCaseClient();
        objCasesClient.createCase();
    }

    public void createCase() {
        JAXBContext jc;
        HttpURLConnection connection = null;
        CaseRequestType caseRequest = null;
        URL url = null;
        String strdispatchToQueue = null;
        try {
            strdispatchToQueue = strURL + "/createCase";
            url = new URL(strdispatchToQueue);
            jc = JAXBContext.newInstance("com.ge.trans.rmd.services.cases.valueobjects");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            LOG.debug(connection.getRequestMethod());
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty(OMDConstants.USERID, "Kiran");
            connection.setRequestProperty(OMDConstants.LANGUAGE, "en");
            connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");

            final OutputStream os = connection.getOutputStream();
            caseRequest = new CaseRequestType();
            caseRequest.setCustomerId("CP");
            caseRequest.setAssetGrpName("CP");
            caseRequest.setAssetNumber("8901");
            caseRequest.setCaseType("Diagnostics");
            caseRequest.setPriority("High");
            caseRequest.setCaseTitle("Field Request");
            jc.createMarshaller().marshal(caseRequest, os);
            os.flush();
            connection.getResponseCode();
            connection.disconnect();
        } catch (Exception excep) {
            LOG.error("Error occured in CreateCaseClient - createCase" + excep);
        }

    }
}
