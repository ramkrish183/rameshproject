package com.ge.trans.eoa.services.common.util;

import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.ge.trans.eoa.services.asset.service.valueobjects.RCIMessageBodyVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RCIMessageHeaderVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RCIMessageRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/**
 * ============================================================
 * File : ConfigMaintenanceUtility.java
 * Description : 
 * 
 * Package :com.ge.trans.eoa.services.common.util
 * Author : Capgemini
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 8, 2018
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2014 General Electric Company. All rights reserved
 * Classification : GE Sensitive
 * ============================================================
 */
public final class ConfigMaintenanceUtility {
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(ConfigMaintenanceUtility.class);
    
    
    public static String generateRCIXMLOutput(RCIMessageRequestVO rciMessageRequestVO) throws RMDBOException {
        try {

            final Marshaller marshaller = JAXBContext.newInstance(RCIMessageRequestVO.class)
                    .createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter strWriter = new StringWriter();
            marshaller.marshal(rciMessageRequestVO, strWriter);
            return strWriter.toString();

        } catch (JAXBException je) {
            LOG.error("ConfigMaintenanceUtility : JAXBException in generateXML :", je);
            throw new RMDBOException(je.getMessage());
        } catch (Exception e) {
            LOG.error("ConfigMaintenanceUtility : Exception in generateXML :", e);
            throw new RMDBOException(e.getMessage());
        }
    }
	
	public static RCIMessageRequestVO transformStringToRCIObject(String input, String userName) throws RMDBOException {
		String[] splittedString;
		RCIMessageRequestVO rciMessageRequestVO = new RCIMessageRequestVO();
		RCIMessageHeaderVO rciMessageHeaderVO = new RCIMessageHeaderVO();
		RCIMessageBodyVO rciMessageBodyVO = new RCIMessageBodyVO();
		try {
			splittedString = input.split("\\|");
			// input String format below
			// OB_MSG_ID |COMM_ID|CUST_ID|ROADNUMBER Header | Road Number |
			// faultCode | Template No | Version | Apply

			if (splittedString.length > 0) {
				// Setting MessageHeader attributes
				rciMessageHeaderVO
						.setTransactionIndicator(RMDCommonConstants.RCI_TRANSACTION_INDICATOR);
				rciMessageHeaderVO.setRequestNo(splittedString[0]);
				rciMessageHeaderVO
						.setActionType(RMDCommonConstants.RCI_ACTION_TYPE);
				rciMessageHeaderVO.setPhysResId(splittedString[1]);
				rciMessageHeaderVO.setCustId(splittedString[2]);
				rciMessageHeaderVO.setVehicleInitial(splittedString[3]);
				rciMessageHeaderVO.setVehicleNo(splittedString[4]);
				rciMessageHeaderVO.setSource(RMDCommonConstants.EOA_APP);
				rciMessageHeaderVO.setHeaderDate(RMDCommonUtility.formatDate(new Date(), RMDCommonConstants.SDF_FORMAT_WITH_T));
				rciMessageHeaderVO.setRequestor(userName);
				// setting message body attributes
				rciMessageBodyVO.setFaultNumber(splittedString[5]);
				rciMessageBodyVO.setTemplateNumber(splittedString[6]);
				rciMessageBodyVO.setTemplateVersion(splittedString[7]);
				rciMessageBodyVO.setTemplateAction(splittedString[8]);
			}
			// setting message body and header into root
			rciMessageRequestVO.setRciMessageBodyVO(rciMessageBodyVO);
			rciMessageRequestVO.setRciMessageHeaderVO(rciMessageHeaderVO);
		} catch (Exception e) {
			  LOG.error("ConfigMaintenanceUtility : Exception in generateXML :", e);
			  throw new RMDBOException(e.getMessage());
		}
		return rciMessageRequestVO;
	}


}
