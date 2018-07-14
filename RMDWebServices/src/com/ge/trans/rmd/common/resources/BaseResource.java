/**
 * ============================================================
 * Classification: GE Confidential
 * File : BaseResource.java
 * Description : 
 * Package : com.ge.trans.rmd.services.common.resources
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.resources;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ge.trans.rmd.common.constants.OMDConstants;
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
@Component
public class BaseResource {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(BaseResource.class);

    @Value("${" + RMDCommonConstants.DEFAULT_TIMEZONE_VALUE + "}")
    public String defaultTimezone;

    @Context
    protected transient HttpHeaders objHttpHeaders;

    public String getRequestHeader(final String strHeader) {
        String strHeaderValue = null;
        final List<String> lstRequestHeader = objHttpHeaders.getRequestHeader(strHeader);

        if (strHeader.equals(OMDConstants.LANGUAGE) && lstRequestHeader != null) {
            strHeaderValue = objHttpHeaders.getRequestHeader(OMDConstants.LANGUAGE).get(0);
        }

        if (RMDCommonUtility.isCollectionNotEmpty(lstRequestHeader)) {
            strHeaderValue = lstRequestHeader.get(0);
            if (RMDCommonUtility.isNull(strHeaderValue)) {
                LOG.debug("Getting Null Header request");
            } else {
                LOG.debug("GETTING HEADER REQUEST ");
                return strHeaderValue;
            }
        } else if (strHeader.equalsIgnoreCase(OMDConstants.LANGUAGE)) {
            strHeaderValue = OMDConstants.DEFAULT_LANGUAGE;
        } else if (strHeader.equalsIgnoreCase(OMDConstants.USERID)) {
            strHeaderValue = OMDConstants.DEFAULT_USERNAME;
        } else if (strHeader.equalsIgnoreCase(OMDConstants.USERLANGUAGE)) {
            strHeaderValue = OMDConstants.DEFAULT_LANGUAGE;
        } else if (strHeader.equalsIgnoreCase(OMDConstants.STR_USERNAME)) {
            strHeaderValue = OMDConstants.DEFAULT_USERNAME;
        }
        return strHeaderValue;
    }

    /**
     * Getting the timezone value returned from properties file
     */
    public String getDefaultTimeZone() {
        String timeZone = defaultTimezone;
        return timeZone;
    }

    public boolean checkinvalidcharacters(String sourcestring) {

        return (sourcestring.contains(OMDConstants.NOT) || sourcestring.contains(OMDConstants.AT)
                || sourcestring.contains(OMDConstants.HASH) || sourcestring.contains(OMDConstants.DOLLAR)
                || sourcestring.contains(OMDConstants.PERCENTAGE) || sourcestring.contains(OMDConstants.CARROT)
                || sourcestring.contains(OMDConstants.AMPERSAND) || sourcestring.contains(OMDConstants.STAR)
                || sourcestring.contains(OMDConstants.OPEN_BRACKET)
                || sourcestring.contains(OMDConstants.CLOSED_BRACKET) || sourcestring.contains(OMDConstants.PLUS)
                || sourcestring.contains(OMDConstants.SLASH));
    }
}
