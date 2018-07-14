/**
 * 
 */
package com.ge.trans.eoa.services.common.constants;

/*******************************************************************************
 * Title:		FaultLogHelper.java
 * 
 * Description:	
 * 
 * Copyright:   Copyright (c) 2001
 * 
 * Company: 	General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author		:	iGate		
 * @Created Date:	1-Feb-08
 * @version		:	1.0
 * 				
 * Revision History		  :			
 * Last Modification Date :
 * Last Modified By       :
 * Last Modification      :
 * 
 ******************************************************************************/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ge.trans.eoa.services.cases.dao.impl.FaultLogDAO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultDetailsTO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderDetailVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderGroupVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultLogTO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultTO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;

public class FaultLogHelper implements FaultLogConstants {
    /* Commented by Prithwi */
    // private static WriteLog writeLog = null;
    /* Commented by Prithwi */
    static {
        try {

            setLogFile();
        } catch (Exception e) {
            log("unable to initialise log file for NEW data screen");
        }
    }

    public FaultTO formatData(FaultTO objFaultTO, ArrayList arlHeaderData) throws Exception {

        log("formatData : start" + System.currentTimeMillis());

        int intGroupHeaderSize = 0;
        String strGroupColor = null;
        ArrayList arlHeader = new ArrayList();
        int intHeaderSize = 0;
        String strLowerBound = null;
        String strUpperBound = null;
        ArrayList arlLowerBound = new ArrayList();
        ArrayList arlUpperBound = new ArrayList();
        int intcounter = 0;
        HashMap hmColor = new HashMap();
        ArrayList arlRowData = new ArrayList();
        int intRowDataSize = 0;
        ArrayList arlData = new ArrayList();
        int intDataSize = 0;
        String strData = null;
        String strLower = null;
        String strUpper = null;
        String strNotch = null;
        String strColor = null;
        String strAnomInd = null;
        ArrayList arlAnomInd = new ArrayList();
        String strAnom = null;
        String strBgColor = null;
        String strHeaderWidth = null;
        String strWidth = null;
        ArrayList arlHeaderWidth = new ArrayList();
        String strDataToolTipFlag = null;
        ArrayList arlDataToolTipFlag = new ArrayList();
        String strDataToolTip = null;
        String strColumnName = null;
        int intFixedDataCnt = -1;
        String strRecordType = null;
        String strFaultStrategyID = null;
        String strFaultOrigin = null;
        String strSampleNo = null;
        int intToolRunCnt = -1;
        int intSnapshotCnt = -1;
        int intLocationCnt = -1;
        String strProximityDesc = null;
        HashMap hmSampleNoDetails = new HashMap();
        String strFaultObjid = null;
        Integer FaultCnt = null;
        int intFaultCnt = 0;
        int intLinkId = 1;
        int intSampleNo = 0;
        boolean blnParent = false;
        HashMap hmSampleNo = new HashMap();
        FaultHeaderDetailVO objFaultHeaderDetailVO = null;
        Integer LinkId = null;
        FaultDetailsTO objFaultDetailsTO = null;
        Integer intCount = null;
        try {
            intGroupHeaderSize = arlHeaderData.size();
            FaultHeaderGroupVO objFaultHeaderGroupVO = null;
            Integer counter = null;
            for (int i = 0; i < intGroupHeaderSize; i++) {
                objFaultHeaderGroupVO = (FaultHeaderGroupVO) arlHeaderData.get(i);
                strGroupColor = objFaultHeaderGroupVO.getGroupName();
                arlHeader = objFaultHeaderGroupVO.getFaultHeaderDetails();
                intHeaderSize = arlHeader.size();

                for (int j = 0; j < intHeaderSize; j++) {
                    intcounter++;
                    counter = Integer.valueOf(intcounter);
                    hmColor.put(counter, strGroupColor);
                    objFaultHeaderDetailVO = (FaultHeaderDetailVO) arlHeader.get(j);
                    strLowerBound = objFaultHeaderDetailVO.getLowerBound();
                    arlLowerBound.add(strLowerBound);
                    strUpperBound = objFaultHeaderDetailVO.getUpperBound();
                    arlUpperBound.add(strUpperBound);
                    strHeaderWidth = objFaultHeaderDetailVO.getHeaderWidth();
                    arlHeaderWidth.add(strHeaderWidth);
                    strAnomInd = objFaultHeaderDetailVO.getAnomInd();
                    arlAnomInd.add(strAnomInd);
                    strDataToolTipFlag = objFaultHeaderDetailVO.getDataToolTipFlag();
                    arlDataToolTipFlag.add(strDataToolTipFlag);
                    strColumnName = objFaultHeaderDetailVO.getColumnName();

                    if (STR_FAULT_CODE.equals(strColumnName)) {
                        intFixedDataCnt = j;
                    }
                    if (STR_TOOLRUN_IND.equals(strColumnName)) {
                        intToolRunCnt = j;
                    }
                    if (STR_EX_SNAPSHOT_FLG.equals(strColumnName)) {
                        intSnapshotCnt = j;
                    }
                    if (STR_PROXIMITY_LABEL.equals(strColumnName)) {
                        intLocationCnt = j;
                    }
                }
            }

            arlRowData = objFaultTO.getFaultData();
            hmSampleNoDetails = objFaultTO.getSampleDetails();
            intRowDataSize = arlRowData.size();

            FaultLogTO objFaultLogTO = null;

            for (int k = 0; k < intRowDataSize; k++) {
                intSampleNo = -1;
                objFaultLogTO = (FaultLogTO) arlRowData.get(k);
                strNotch = objFaultLogTO.getNonDisplayableCols(STR_NOTCH_FLAG);
                strFaultObjid = objFaultLogTO.getFaultObjid();
                FaultCnt = (Integer) hmSampleNoDetails.get(strFaultObjid);
                if (null != FaultCnt) {
                    intFaultCnt = FaultCnt.intValue();
                }

                if (intFaultCnt > 1) {
                    try {
                        intSampleNo = Integer.parseInt(objFaultLogTO.getNonDisplayableCols(STR_V_SAMPLE_NO));
                    } catch (Exception ee) {
                        intSampleNo = -1;
                    }
                    if (intSampleNo == 0) {
                        blnParent = true;
                    } else {
                        blnParent = false;
                    }
                    objFaultLogTO.setParentFlag(blnParent);
                    if (!hmSampleNo.containsKey(strFaultObjid)) {
                        intLinkId++;
                        objFaultLogTO.setLinkID(intLinkId);
                        LinkId = Integer.valueOf(intLinkId);
                        hmSampleNo.put(strFaultObjid, LinkId);
                    } else {

                        objFaultLogTO.setLinkID(intLinkId);
                        LinkId = Integer.valueOf(intLinkId);
                        hmSampleNo.put(strFaultObjid, LinkId);
                    }
                }
                arlData = objFaultLogTO.getFaultData();
                intDataSize = arlData.size();
                for (int l = 0; l < intDataSize; l++) {
                    objFaultDetailsTO = (FaultDetailsTO) arlData.get(l);
                    intCount = Integer.valueOf(l + 1);
                    strColor = (String) hmColor.get(intCount);
                    strData = objFaultDetailsTO.getData();
                    strLower = (String) arlLowerBound.get(l);
                    strUpper = (String) arlUpperBound.get(l);
                    strAnom = (String) arlAnomInd.get(l);
                    strWidth = (String) arlHeaderWidth.get(l);
                    strDataToolTip = (String) arlDataToolTipFlag.get(l);
                    formatDataDetails(strData, strLower, strUpper, strDataToolTip, strAnom, objFaultDetailsTO, strColor,
                            strNotch);
                    objFaultDetailsTO.setWidth(strWidth);

                    if (l == intFixedDataCnt) {
                        strRecordType = objFaultLogTO.getNonDisplayableCols(STR_RECORD_TYPE);
                        strFaultStrategyID = objFaultLogTO.getNonDisplayableCols(STR_FAULT_STRATEGY_OBJID);
                        strFaultOrigin = objFaultLogTO.getNonDisplayableCols(STR_FAULT_ORIGIN);
                        strSampleNo = objFaultLogTO.getNonDisplayableCols(STR_V_SAMPLE_NO);
                        formatFaultCode(strData, strRecordType, strFaultStrategyID, strFaultOrigin, strSampleNo,
                                objFaultDetailsTO);
                    }

                    if (l == intToolRunCnt) {
                        formatToolRunInd(strDataToolTip, strData, objFaultDetailsTO);
                    }
                    if (l == intSnapshotCnt) {
                        formatSnapShot(strDataToolTip, strData, objFaultDetailsTO);
                    }
                    if (l == intLocationCnt) {
                        strProximityDesc = objFaultLogTO.getNonDisplayableCols(STR_V_PROXIMITY_DESC);
                        formatLocation(strProximityDesc, strDataToolTip, strData, objFaultDetailsTO);
                    }

                }

            }

            log("End Time formatData in FaultLogHelper : " + System.currentTimeMillis());
        } catch (Exception e) {
            log("Error in FaultLogHelper - formatData : " + e);
            throw e;
            //
        }
        log("formatData : end" + System.currentTimeMillis());
        return objFaultTO;
    }

    public void formatDataDetails(String strDataDetails, String strLower, String strUpper, String strDataToolTip,
            String strAnomInd, FaultDetailsTO objFaultDetailsTO, String strColor, String strNotch) throws Exception {

        StringTokenizer st;
        String strData = null;
        String strResidualFlag = null;
        String strDataLow = null;
        String strDataHigh = null;
        String strToolTip = null;
        String strDataColor = strColor;
        double dblDataLow = 0;
        String strLHInd = null;

        try {

            strDataColor = strColor;
            if (null != strDataDetails && !"".equals(strDataDetails)) {
                st = new StringTokenizer(strDataDetails, "^");
                strData = st.nextToken();
                objFaultDetailsTO.setData(strData);

                if (null != strUpper && null != strLower) {
                    if (st.hasMoreTokens()) {
                        strLHInd = st.nextToken();
                        if (STR_8.equals(strNotch)) {
                            if (STR_L.equals(strLHInd)) {
                                strDataColor = strColor + STR_Y;
                            } else if (STR_H.equals(strLHInd)) {
                                strDataColor = strColor + STR_R;
                            }
                        }
                    }
                }

                if (STR_Y.equals(strDataToolTip)) {
                    if (st.hasMoreTokens()) {
                        if (STR_N.equals(strAnomInd)) {
                            if (st.hasMoreTokens()) {
                                strDataLow = st.nextToken();
                                dblDataLow = Double.parseDouble(strDataLow);
                                strDataLow = DEC.format(dblDataLow);
                                strDataHigh = st.nextToken();
                                double dblDataHigh = Double.parseDouble(strDataHigh);
                                strDataHigh = DEC.format(dblDataHigh);
                                strToolTip = "Low: " + strDataLow + " High: " + strDataHigh;
                            }
                        }
                        /*
                         * else // commented for future use { strToolTip =
                         * st.nextToken(); }
                         */
                        objFaultDetailsTO.setDataToolTip(strToolTip);
                    }
                }
            }
            objFaultDetailsTO.setColor(strDataColor);
        } catch (Exception e) {
            log("Error in formatting the data in FaultLogHelper : " + e);
            throw e;
        }
    }

    public void formatFaultCode(String strData, String strRecordType, String strFaultStrategyCode,
            String strFaultOrigin, String strSampleNo, FaultDetailsTO objFaultDetailsTO) throws Exception {

        String strDataToolTip = null;
        try {
            if (null == strSampleNo || STR_0.equals(strSampleNo) || "".equals(strSampleNo)) {
                if (!STR_HC.equalsIgnoreCase(strRecordType)) {
                    if (null != strFaultStrategyCode) {
                        if (null != strData) {
                            objFaultDetailsTO.setData(strData);
                            strDataToolTip = "Record Type:" + strRecordType
                                    + ",On click will take to Fault Service Strategy Screen";
                            objFaultDetailsTO.setDataToolTip(strDataToolTip);
                        }
                    } else {
                        objFaultDetailsTO.setData(strData);
                        strDataToolTip = "FaultOrigin:" + strFaultOrigin + " Record Type:" + strRecordType
                                + " ,No Fault Service Strategy Record";
                        objFaultDetailsTO.setDataToolTip(strDataToolTip);

                    }
                }
            }
        } catch (Exception e) {
            log("Error in formatting fault code" + e);
            throw e;
        }
    }

    public void formatToolRunInd(String strDataToolTipFlag, String strData, FaultDetailsTO objFaultDetailsTO)
            throws Exception {
        String strDataToolTip = null;
        String strColor = null;
        try {
            if (STR_Y.equals(strDataToolTipFlag)) {

                objFaultDetailsTO.setData(strData);
                strDataToolTip = "* -- Tool Run on Fault, F -- Fault Filtered,Blank -- No Tool Run and Not Filtered";
                objFaultDetailsTO.setDataToolTip(strDataToolTip);
                strColor = STR_FIXED;
                objFaultDetailsTO.setColor(strColor);
            }

        } catch (Exception e) {
            log("Exception in Formatting Tool Run Ind : " + e);
            throw e;
        }
    }

    public void formatSnapShot(String strDataToolTipFlag, String strData, FaultDetailsTO objFaultDetailsTO)
            throws Exception {

        String strDataToolTip = null;
        String strColor = null;
        try {
            if (STR_Y.equals(strDataToolTipFlag)) {

                objFaultDetailsTO.setData(strData);
                strDataToolTip = "* -- same SDP";
                objFaultDetailsTO.setDataToolTip(strDataToolTip);
                strColor = STR_FIXED;
                objFaultDetailsTO.setColor(strColor);
            }

        } catch (Exception e) {
            log("Exception in Formatting SnapShot : " + e);
            throw e;
        }
    }

    public void formatLocation(String strProximityDesc, String strDataToolTipFlag, String strData,
            FaultDetailsTO objFaultDetailsTO) throws Exception {
        String strDataToolTip = null;
        String localProximityDesc = strProximityDesc;
        try {
            if (STR_Y.equals(strDataToolTipFlag)) {

                objFaultDetailsTO.setData(strData);
                if (null == localProximityDesc) {
                    localProximityDesc = STR_NBSP;
                }
                strDataToolTip = "Proximity Desc : " + localProximityDesc;
                objFaultDetailsTO.setDataToolTip(strDataToolTip);

            }
        } catch (Exception e) {
            log("Exception in formatting Location : " + e);
            throw e;
        }
    }

    public static StringBuilder getExportDetails(FaultTO objFaultTO, ArrayList arlDataGrps,
            FaultHeaderVO objFaultHeaderVOClone, String strControllerSrcId) throws Exception {
        FaultHeaderVO objFaultHeaderVO = new FaultHeaderVO();
        FaultLogDAO objFaultLogDAO = new FaultLogDAO();
        ArrayList arlDataexport = new ArrayList();
        ArrayList arlDataDetails = new ArrayList();
        ArrayList arlFixedlist = new ArrayList();
        ArrayList arlFixedColumns = new ArrayList();
        StringBuilder formHTML = new StringBuilder();
        FaultHeaderGroupVO objFaultHeaderGroupVO = null;
        FaultHeaderDetailVO objHeaderDetail = null;
        FaultDetailsTO objFaultDetailsTO = null;
        FaultLogTO objFaultLogTO = null;
        int intDataSize = 0;
        int intDataDetailSize = 0;
        int intFixed = 0;
        int intFxdColSize = 0;
        String retString = null;
        String strFixedColumnName = null;
        String strData = null;
        String strHeaderInfo = null;
        String strFormattedHeaderInfo = null;
        FaultLogHelper objFaultLogHelper = new FaultLogHelper();
        int intDataIndex = 0;

        try {

            arlDataexport = objFaultTO.getFaultData();
            intDataSize = arlDataexport.size();

            intFixed = arlDataGrps.size();

            int intFaultCode = 0;
            for (int h = 0; h < intFixed; h++) {
                objFaultHeaderGroupVO = (FaultHeaderGroupVO) arlDataGrps.get(h);
                arlFixedColumns = objFaultHeaderGroupVO.getFaultHeaderDetails();
                intFxdColSize = arlFixedColumns.size();
                for (int c = 0; c < intFxdColSize; c++) {
                    objHeaderDetail = (FaultHeaderDetailVO) arlFixedColumns.get(c);
                    // formHTML.append("<td class='TableHeader'>");
                    strFixedColumnName = objHeaderDetail.getColumnName();
                    if (null != strFixedColumnName) {

                        formHTML.append(strFixedColumnName);
                        // formHTML.append("</td>");
                        if (STR_FAULT_CODE.equalsIgnoreCase(strFixedColumnName)) {
                            intFaultCode = c;
                        }
                    } else {
                        strHeaderInfo = objHeaderDetail.getHeaderDesc();
                        strFormattedHeaderInfo = objFaultLogHelper.formatString(strHeaderInfo);
                        if ((RMDCommonConstants.TOOL_RUN_IND).equalsIgnoreCase(strFormattedHeaderInfo)) {
                            formHTML.append(strFormattedHeaderInfo);
                        } else if (strFormattedHeaderInfo.indexOf(RMDCommonConstants.EPS) != -1) {
                            formHTML.append(RMDCommonConstants.EX_SNAPSHOT_FLAG);
                        } else if ((RMDCommonConstants.MSA).equalsIgnoreCase(strFormattedHeaderInfo)) {
                            formHTML.append(strFormattedHeaderInfo);
                        } else if ((RMDCommonConstants.ID).equalsIgnoreCase(strHeaderInfo)) {
                            formHTML.append(RMDCommonConstants.SINGLE_QTE);
                            formHTML.append(strHeaderInfo);
                            formHTML.append(RMDCommonConstants.SINGLE_QTE);
                        } else {
                            formHTML.append(strHeaderInfo);
                        }

                    }
                    // formHTML.append(",");
                    formHTML.append(RMDCommonConstants.TAB_SPACE);
                }
            }
            // objFaultHeaderVO =
            // (FaultHeaderVO)hmHeaders.get(strControllerSrcId);
            arlFixedlist = objFaultHeaderVOClone.getFaultGroups();
            intFixed = arlFixedlist.size();
            for (int h = 0; h < intFixed; h++) {
                objFaultHeaderGroupVO = (FaultHeaderGroupVO) arlFixedlist.get(h);
                arlFixedColumns = objFaultHeaderGroupVO.getFaultHeaderDetails();
                intFxdColSize = arlFixedColumns.size();
                for (int c = 0; c < intFxdColSize; c++) {
                    objHeaderDetail = (FaultHeaderDetailVO) arlFixedColumns.get(c);
                    // formHTML.append("<td class='TableHeader'>");
                    strFixedColumnName = objHeaderDetail.getColumnName();

                    if (null == strFixedColumnName || "".equals(strFixedColumnName.trim())) {
                        strFixedColumnName = objHeaderDetail.getHeaderDesc();
                    }
                    formHTML.append(strFixedColumnName);
                    // formHTML.append("</td>");
                    // formHTML.append(",");
                    formHTML.append(RMDCommonConstants.TAB_SPACE);

                }
            }
            // formHTML.append("</tr>");
            formHTML.append(RMDCommonConstants.CARRIAGE_RETURN);

            for (int j = 0; j < intDataSize; j++) {
                objFaultLogTO = (FaultLogTO) arlDataexport.get(j);
                arlDataDetails = objFaultLogTO.getFaultData();
                intDataDetailSize = arlDataDetails.size();
                // formHTML.append("<tr>");

                for (int k = 0; k < intDataDetailSize; k++) {

                    objFaultDetailsTO = (FaultDetailsTO) arlDataDetails.get(k);
                    strData = objFaultDetailsTO.getData();
                    // formHTML.append("<td class='TableData'>");
                    if (null == strData) {
                        strData = RMDCommonConstants.BLANK_SPACE;
                    }
                    if (k == intFaultCode) {
                        formHTML.append(RMDCommonConstants.SINGLE_QTE);
                        formHTML.append(strData);
                        formHTML.append(RMDCommonConstants.SINGLE_QTE);
                    } else {
                        intDataIndex = strData.indexOf('^');
                        if (intDataIndex != -1) {
                            strData = strData.substring(0, intDataIndex);
                        }
                        formHTML.append(strData);
                    }

                    formHTML.append(RMDCommonConstants.TAB_SPACE);

                }
                // formHTML.append("</tr>");
                formHTML.append(RMDCommonConstants.CARRIAGE_RETURN);

            }
        } catch (Exception e) {

            log("Error in calling exportDetails in FaultLogHelper : " + e);
            throw e;
        }

        return formHTML;
    }

    /*
     * public StringBuilder getCustomerExportData(OilCustomerVO
     * objOilCustomerVO)throws Exception { StringBuilder sbExportDetails = new
     * StringBuilder(); try { sbExportDetails.append("AAR");
     * sbExportDetails.append("\t"); sbExportDetails.append("RN");
     * sbExportDetails.append("\t"); sbExportDetails.append("Fleet Name");
     * sbExportDetails.append("\t"); sbExportDetails.append("Fleet Description"
     * ); sbExportDetails.append("\t"); sbExportDetails.append("Model");
     * sbExportDetails.append("\t"); sbExportDetails.append("Sample Status");
     * sbExportDetails.append("\t"); sbExportDetails.append("Sample Date");
     * sbExportDetails.append("\t"); sbExportDetails.append(
     * "Last Oil Task Closure"); sbExportDetails.append("\t");
     * sbExportDetails.append("Last Oil Rx Closure");
     * sbExportDetails.append("\t"); sbExportDetails.append("Next RM Date 1");
     * sbExportDetails.append("\t"); sbExportDetails.append("Next RM Date 2");
     * sbExportDetails.append("\t");
     * sbExportDetails.append(RMDCommonConstants.CARRIAGE_RETURN); ArrayList
     * arlCustomerDate = objOilCustomerVO.getRecentData(); int
     * intCustomerDataSize = arlCustomerDate.size(); String strFleetDesc = null;
     * String strModel = null; String strLastOilTaskClosure = null; String
     * strLastOilRxClosure = null; String strFutureOilDate1 = null; String
     * strFutureOilDate2 = null; int i; for(i = 0; i < intCustomerDataSize ;
     * i++) { OilCustomerVO objOilCustVO =
     * (OilCustomerVO)arlCustomerDate.get(i);
     * sbExportDetails.append(objOilCustVO.getVehicleHeader());
     * sbExportDetails.append("\t");
     * sbExportDetails.append(objOilCustVO.getSerialNo());
     * sbExportDetails.append("\t");
     * sbExportDetails.append(objOilCustVO.getFleetName());
     * sbExportDetails.append("\t"); strFleetDesc =
     * objOilCustVO.getFleetDescription(); strFleetDesc = ((strFleetDesc ==
     * null)?"":strFleetDesc); sbExportDetails.append(strFleetDesc);
     * sbExportDetails.append("\t"); strModel = objOilCustVO.getModel();
     * strModel = ((strModel == null)?"":strModel);
     * sbExportDetails.append(strModel); sbExportDetails.append("\t");
     * sbExportDetails.append(objOilCustVO.getType());
     * sbExportDetails.append("\t");
     * sbExportDetails.append(objOilCustVO.getSampleDate());
     * sbExportDetails.append("\t"); strLastOilTaskClosure =
     * objOilCustVO.getLastOilTaskClosure(); strLastOilTaskClosure =
     * ((strLastOilTaskClosure == null)?"":strLastOilTaskClosure);
     * sbExportDetails.append(strLastOilTaskClosure);
     * sbExportDetails.append("\t"); strLastOilTaskClosure =
     * objOilCustVO.getLastOilRxClosure(); strLastOilTaskClosure =
     * ((strLastOilTaskClosure == null)?"":strLastOilTaskClosure);
     * sbExportDetails.append(strLastOilTaskClosure);
     * sbExportDetails.append("\t"); strFutureOilDate1 =
     * objOilCustVO.getNextRMDate1(); strFutureOilDate1 = ((strFutureOilDate1 ==
     * null)?"":strFutureOilDate1); sbExportDetails.append(strFutureOilDate1);
     * sbExportDetails.append("\t"); strFutureOilDate2 =
     * objOilCustVO.getNextRMDate2(); strFutureOilDate2 = ((strFutureOilDate2 ==
     * null)?"":strFutureOilDate2); sbExportDetails.append(strFutureOilDate2);
     * sbExportDetails.append("\t");
     * sbExportDetails.append(RMDCommonConstants.CARRIAGE_RETURN); } }
     * catch(Exception e) { log(
     * "Error in building Customer Data for Export in FaultLogHelper :"+e); }
     * return sbExportDetails; }
     */

    public static void setLogFile() throws Exception {
        try {

            String eoaData = null;
            String logpath = null;

        } catch (Exception e) {
            log("Error in calling exportDetails in FaultLogHelper : " + e);
            throw e;

        }
    }

    public static void log(String str) {

    }

    public static void debug(String str) {

    }

    public static void error(String str) {

    }

    public String replaceString(String strSource, String strFromString, String strToString) throws Exception {
        String localString = strSource;
        StringBuilder strbuffOutPutString = new StringBuilder();
        int intIndex = 0;
        int intStart = 0;

        while ((intIndex = localString.indexOf(strFromString, intStart)) != -1) {
            intStart = intIndex;
            strbuffOutPutString.setLength(0);
            strbuffOutPutString.append(localString.substring(0, intIndex));
            strbuffOutPutString.append(strToString);
            strbuffOutPutString.append(localString.substring(intIndex + strFromString.length(), localString.length()));

            localString = strbuffOutPutString.toString();
            intStart = intStart + strToString.length();
        }
        return (localString);
    }

    public String formatHTMLString(String strSource) throws Exception {
        String localString = strSource;
        if (!(localString == null || localString.equals(RMDCommonConstants.EMPTY_STRING))) {
            localString = replaceString(localString, "\r\n", "&#13;");
            localString = replaceString(localString, "\r", "&#10;");
            localString = replaceString(localString, RMDCommonConstants.CARRIAGE_RETURN, "&#13;");
            localString = replaceString(localString, "\"", "&#34;");
            localString = replaceString(localString, "\'", "&#39;");
            localString = replaceString(localString, "<", "&lt;");
            localString = replaceString(localString, ">", "&gt;");
            localString = replaceString(localString, "+", " ");

        }
        return localString;
    }

    public String formatString(String strSource) throws Exception {
        String localString = strSource;
        if (!(localString == null || localString.equals(""))) {
            localString = replaceString(localString, "/", "");
            localString = replaceString(localString, " ", "");
            localString = replaceString(localString, "-", "");
            localString = replaceString(localString, "\r", "");
        }
        return localString;
    }

    public void highlightData(String strTypeCheck, String strDataDetails, FaultDetailsTO objFaultDetailsTO,
            String strColor) throws Exception {
        try {
            // log(" strTypeCheck :"+strTypeCheck+" strDataDetails
            // :"+strDataDetails+" strColor:"+strColor);
            objFaultDetailsTO.setData(strDataDetails);
            if ((RMDCommonConstants.CRITICAL).equals(strTypeCheck)) {
                // log("Inside critical");
                objFaultDetailsTO.setColor(strColor + "HighLight");
            } else {
                // log("Inisde else");
                objFaultDetailsTO.setColor(strColor);
            }
        } catch (Exception e) {
            log("Error in highlightData in FaultLogHelper :" + e);
        }

    }

    public FaultTO formatOilData(ArrayList arlHeader, FaultTO objFaultTO) throws Exception {

        try {
            StringTokenizer st;
            StringTokenizer st1;
            ArrayList arlFaultData = objFaultTO.getFaultData();
            int intFaultDataSize = arlFaultData.size();
            ArrayList arlFaultDataDetails = new ArrayList();
            int intFaultDataDetails = 0;
            int i;
            int j;
            String strDataDetails = null;
            String strData = null;
            String strColumnName = null;
            String strRuleOperator = null;
            String strRuleValue = null;
            String strToolTip = RMDCommonConstants.EMPTY_STRING;
            int intFltCnt = 0;
            int intcounter = 0;
            Integer count;
            HashMap hmColor = new HashMap();

            String strHeaderInfo = null;
            String strFormattedHeaderInfo = null;
            String strColor = null;
            String strWidth = null;
            String strStyle = null;
            int intHeaderGroupSize = 0;
            int intHeaderDetailSize = 0;
            ArrayList arlHeaderGroup = new ArrayList();
            ArrayList arlHeaderDetails = new ArrayList();
            int intToolRunIndCnt = -1;
            int intSnapShotCnt = -1;
            int intFaultCode = -1;
            int intStatusCnt = -1;
            int intVehNoCnt = -1;
            String strTypeCheck = null;
            Integer intCount;

            int intHeaderSize = arlHeader.size();
            for (int m = 0; m < intHeaderSize; m++) {
                FaultHeaderVO objHeaderVO = (FaultHeaderVO) arlHeader.get(m);
                arlHeaderGroup = objHeaderVO.getFaultGroups();
                intHeaderGroupSize = arlHeaderGroup.size();
                for (int n = 0; n < intHeaderGroupSize; n++) {
                    FaultHeaderGroupVO objHeaderGroupVO = (FaultHeaderGroupVO) arlHeaderGroup.get(n);
                    strColor = objHeaderGroupVO.getGroupName();
                    arlHeaderDetails = objHeaderGroupVO.getFaultHeaderDetails();
                    intHeaderDetailSize = arlHeaderDetails.size();
                    for (int s = 0; s < intHeaderDetailSize; s++) {
                        intcounter++;
                        count = Integer.valueOf(intcounter);
                        FaultHeaderDetailVO objHeaderDetailVO = (FaultHeaderDetailVO) arlHeaderDetails.get(s);
                        strWidth = objHeaderDetailVO.getHeaderWidth();
                        hmColor.put(count, strColor + "," + strWidth);
                        strHeaderInfo = objHeaderDetailVO.getHeaderDesc();
                        strFormattedHeaderInfo = formatString(strHeaderInfo);
                        if ((RMDCommonConstants.TOOL_RUN_IND).equalsIgnoreCase(strFormattedHeaderInfo)) {
                            intToolRunIndCnt = s;
                        }

                        if (strFormattedHeaderInfo.indexOf(RMDCommonConstants.EPS) != -1) {
                            intSnapShotCnt = s;
                        }
                        if ((RMDCommonConstants.INCIDENT).equalsIgnoreCase(strFormattedHeaderInfo)) {
                            intFaultCode = s;
                        }
                        if ((RMDCommonConstants.STATUS).equals(strHeaderInfo)) {
                            intStatusCnt = s;
                        }
                        if ((RMDCommonConstants.RN).equals(strHeaderInfo)) {
                            intVehNoCnt = s;
                        }

                    }
                }
            }

            for (i = 0; i < intFaultDataSize; i++) {
                FaultLogTO objFaultLogTO = (FaultLogTO) arlFaultData.get(i);
                intFltCnt = objFaultLogTO.getFaultCount();
                strTypeCheck = objFaultLogTO.getTypeCheck();
                arlFaultDataDetails = objFaultLogTO.getFaultData();
                intFaultDataDetails = arlFaultDataDetails.size();
                for (j = 0; j < intFaultDataDetails; j++) {
                    FaultDetailsTO objFaultDetailsTO = (FaultDetailsTO) arlFaultDataDetails.get(j);
                    intCount = Integer.valueOf(j + 1);
                    strStyle = (String) hmColor.get(intCount);

                    if (null != strStyle) {
                        st1 = new StringTokenizer(strStyle, ",");
                        strColor = st1.nextToken();
                        objFaultDetailsTO.setColor(strColor);
                        if (st1.hasMoreTokens()) {
                            strWidth = st1.nextToken();
                            objFaultDetailsTO.setWidth(strWidth);
                        }

                    }

                    strDataDetails = objFaultDetailsTO.getData();
                    if (j == intToolRunIndCnt) {
                        formatToolRunInd(RMDCommonConstants.LETTER_Y, strDataDetails, objFaultDetailsTO);
                    } else if (j == intSnapShotCnt) {
                        formatSnapShot(RMDCommonConstants.LETTER_Y, strDataDetails, objFaultDetailsTO);
                    } else if (j == intStatusCnt || j == intVehNoCnt) {
                        // log("Inside else 11111");
                        highlightData(strTypeCheck, strDataDetails, objFaultDetailsTO, strColor);
                    } else {
                        if (null != strDataDetails && !"".equals(strDataDetails)) {
                            st = new StringTokenizer(strDataDetails, "^");
                            strData = st.nextToken();
                            objFaultDetailsTO.setData(strData);
                            if (st.hasMoreTokens()) {
                                strColumnName = st.nextToken();
                                strToolTip = strColumnName;
                                if (!st.hasMoreElements()) {
                                    objFaultDetailsTO.setColor(strColor);
                                    objFaultDetailsTO.setDataToolTip(strToolTip);
                                }
                                if (st.hasMoreTokens()) {
                                    strRuleOperator = st.nextToken();
                                    strToolTip += strRuleOperator;
                                    if (st.hasMoreTokens()) {
                                        strRuleValue = st.nextToken();
                                        strToolTip += strRuleValue;
                                        strColor += "HighLight";
                                        // objFaultDetailsTO.setColor(strColor);
                                    }
                                }
                                if (intFltCnt == 1) {
                                    objFaultDetailsTO.setColor(strColor);
                                    objFaultDetailsTO.setDataToolTip(strToolTip);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Error in formatting Oil Data :" + e);
            throw e;
        }
        return objFaultTO;
    }
}
