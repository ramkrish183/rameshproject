/**
 * ============================================================
 * File : RMDCommonErrorMessageHelper.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.messaging
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 11, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.messaging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ErrorMessageVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 11, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDCommonErrorMessageHelper {

    /**
     * @Author:
     * @param strHeader
     * @param strMsg
     * @Description: Build request message for MQ
     */
    public static String buildMqRequest(final ErrorMessageVO objVO) throws Exception {
        String strErrMsg = null;
        String strAssetId;
        String strSubRunId;
        String strService;
        try {
            final Date currentDate = new Date();
            final String strCurrDate = RMDCommonUtility.convertToString(RMDCommonUtility.convertToGMT(currentDate),
                    RMDCommonConstants.DateConstants.yyyyMMddHHmmssSSS);
            if (objVO.getIntAssetId() < 1) {
                strAssetId = RMDCommonConstants.EMPTY_STRING;
            } else {
                strAssetId = RMDCommonConstants.EMPTY_STRING + objVO.getIntAssetId();
            }
            if (objVO.getIntSubRunId() < 1) {
                strSubRunId = RMDCommonConstants.NA;
            } else {
                strSubRunId = RMDCommonConstants.EMPTY_STRING + objVO.getIntSubRunId();
            }
            if (RMDCommonUtility.isNullOrEmpty(objVO.getStrService())) {
                strService = RMDCommonConstants.NA;
            } else {
                strService = objVO.getStrService();
            }
            final StringWriter sWriter = new StringWriter();
            final PrintWriter pWriter = new PrintWriter(sWriter);
            if (objVO.getObjStack() != null) {
                objVO.getObjStack().printStackTrace(pWriter);
            }
            strErrMsg = "<error-header asset-id =\"" + strAssetId + "\" type=\"" + objVO.getStrType() + "\" message=\""
                    + objVO.getStrErrDetail() + "\" code=\"" + objVO.getStrErrCode() + "\" occurred-on=\"" + strCurrDate
                    + "\" component-name=\"" + objVO.getStrToolId() + "\"	origin-queue=\"" + ""
                    + "\" destination-queue = \"" + "" + "\">" + "<error-category name=\"" + objVO.getStrCategory()
                    + "\">" + "<param index=\"" + 1 + "\" value=\"" + strSubRunId + "\"/>" + "<param index=\"" + 2
                    + "\" value=\"" + strService + "\"/> " + "</error-category>" + "<error-stack>"
                    + StringEscapeUtils.escapeXml(sWriter.getBuffer().toString()) + "</error-stack>"
                    + "</error-header>";
        } catch (Exception e) {
            throw e;
        }
        return strErrMsg;
    }
}
