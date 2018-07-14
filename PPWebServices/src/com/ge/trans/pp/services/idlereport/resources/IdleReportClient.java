/**
 * ============================================================
 * Classification: GE Confidential
 * File : AdminClient.java
 * Description :
 * Package : com.ge.trans.rmd.services.admin.test
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.pp.services.idlereport.resources;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportDetailType;
import com.ge.trans.pp.services.idlereport.valueobjects.IdleReportRequestType;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

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
public class IdleReportClient {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(IdleReportClient.class);

    private JAXBContext jc;

    public IdleReportClient() {
        try {
            jc = JAXBContext.newInstance("com.ge.trans.pp.services.idlereport.valueobjects");
        } catch (JAXBException je) {
            LOG.debug("Cannot create JAXBContext " + je.getMessage(), je);
        }

    }

    public static void main(final String[] args) {

        final IdleReportClient objIdleReportClient = new IdleReportClient();
        try {
            objIdleReportClient.testIdleDetailsReport();
        } catch (Exception excep) {
            LOG.error("Exception occured in IdleReportClient - main method" + excep.getMessage(), excep);
        }
    }

    public void testIdleSummaryReport() {
        try {
            final URL url = new URL("http://localhost:8080/PPWebServices/idlereportservice/getIdleReportSummary");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");

            // START:: Base Resources Attribute
            connection.setRequestProperty("language", "zh");
            // END:: Base Resources Attribute

            connection.setRequestProperty("Content-Type", "application/xml");
            final OutputStream os = connection.getOutputStream();

            final IdleReportRequestType objIdleReportReqType = new IdleReportRequestType();
            objIdleReportReqType.setCustomerId("CP");
            objIdleReportReqType.setElapsedTime(600000);
            objIdleReportReqType.setRegion("Calgary Vicinity");

            jc.createMarshaller().marshal(objIdleReportReqType, os);
            os.flush();
            connection.getResponseCode();

            connection.disconnect();
        } catch (Exception excep) {
            LOG.error("Exception occured in IdleReportClient - main method" + excep.getMessage(), excep);

        }
    }

    public void testIdleDetailsReport() {
        try {
            final URL url = new URL("http://localhost:8080/PPWebServices/idlereportservice/getIdleReportDetails");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");

            // START:: Base Resources Attribute
            connection.setRequestProperty("language", "zh");
            // END:: Base Resources Attribute

            connection.setRequestProperty("Content-Type", "application/xml");
            final OutputStream os = connection.getOutputStream();

            final IdleReportDetailType objIdleReportReqType = new IdleReportDetailType();
            objIdleReportReqType.setCustomerId("CP");
            objIdleReportReqType.setElapsedTime(60);
            objIdleReportReqType.setRegion("Calgary Vicinity");
            objIdleReportReqType.setIdleType("Not Moving –All");

            jc.createMarshaller().marshal(objIdleReportReqType, os);
            os.flush();
            connection.getResponseCode();

            connection.disconnect();
        } catch (Exception excep) {
            LOG.error("Exception occured in IdleReportClient - main method" + excep.getMessage(), excep);

        }
    }

}