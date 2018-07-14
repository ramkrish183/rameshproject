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
package com.ge.trans.pp.services.notification.resources;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;

import com.ge.trans.pp.services.notification.valueobjects.PpNotificationRequestType;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
public class NotificationClient {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotificationClient.class);

    private JAXBContext jc;

    public NotificationClient() {
        try {
            jc = JAXBContext.newInstance("com.ge.trans.pp.services.notification.valueobjects");
        } catch (JAXBException je) {
            LOG.debug("Cannot create JAXBContext " + je.getMessage(), je);
        }
    }

    public static void main(final String[] args) {

        final NotificationClient objNotificationClient = new NotificationClient();
        try {
            objNotificationClient.testNotificationHistory();
        } catch (Exception excep) {
            LOG.error("Exception occured in NotificationClient - main method" + excep, excep);
        }
    }

    public void testNotificationHistory() {
        try {
            final URL url = new URL("http://localhost:8080/PPWebServices/notificationservice/getPPNotificationHistory");
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
            DateFormat dFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
            final PpNotificationRequestType objPpNotificationReqType = new PpNotificationRequestType();
            objPpNotificationReqType.setCustomerId("NS");
            objPpNotificationReqType.setInactiveNotification(true);
            objPpNotificationReqType.setTimeZone("EST");
            objPpNotificationReqType.setNotificationType("'low_fuel_alert'"); // ,not_moving_alert,dwell_inform
            String date = "05/30/2013 12:11:12";
            String date1 = "05/31/2013 12:11:12";
            GregorianCalendar objGregorianCalendar = new GregorianCalendar();
            objGregorianCalendar.setTime(dFormat.parse(date));
            RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, "US/Eastern");
            objPpNotificationReqType.setUserTimeZone("GMT");
            objPpNotificationReqType
                    .setFromDate(date);
            objPpNotificationReqType
                    .setToDate(date1);

            jc.createMarshaller().marshal(objPpNotificationReqType, os);
            os.flush();
            connection.getResponseCode();
            connection.disconnect();
        } catch (Exception excep) {
            LOG.error("Exception occured in NotificationClient - saveUserDetails method" + excep.getMessage(), excep);
        }
    }

}