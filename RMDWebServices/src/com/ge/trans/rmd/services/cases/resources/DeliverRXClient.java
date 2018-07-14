package com.ge.trans.rmd.services.cases.resources;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.cases.valueobjects.CaseSolutionRequestType;

public class DeliverRXClient {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(DeliverRXClient.class);

    private String strURL = "http://localhost:8080/RMDWebServices/caseservice";

    public static void main(final String[] args) {
        final DeliverRXClient objCasesClient = new DeliverRXClient();
        objCasesClient.deliverRX();
    }

    public void deliverRX() {
        JAXBContext jc;
        HttpURLConnection connection = null;

        URL url = null;
        String addRX = null;
        try {
            addRX = strURL + "/deliverSolution";
            url = new URL(addRX);
            jc = JAXBContext.newInstance("com.ge.trans.rmd.services.cases.valueobjects");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("PUT");
            LOG.debug(connection.getRequestMethod());
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty(OMDConstants.USERID, "Kiran");
            connection.setRequestProperty(OMDConstants.LANGUAGE, "en");
            connection.setRequestProperty(OMDConstants.USERLANGUAGE, "en");

            final OutputStream os = connection.getOutputStream();
            CaseSolutionRequestType caseSol = new CaseSolutionRequestType();
            caseSol.setCaseID("14-002208");
            caseSol.setSolutionID("112228");
            caseSol.setSolutionTitle("LocoCAM/LDVR no video input to CMU");
            caseSol.setUserName("Kiran");
            caseSol.setUrgRepair("R");
            caseSol.setEstmRepTime("1");
            caseSol.setRecomNotes("Test");
            jc.createMarshaller().marshal(caseSol, os);
            os.flush();
            connection.getResponseCode();
            connection.disconnect();
        } catch (Exception excep) {
            LOG.error("Error occured in AddRXClient -  " + excep);
        }

    }

}
