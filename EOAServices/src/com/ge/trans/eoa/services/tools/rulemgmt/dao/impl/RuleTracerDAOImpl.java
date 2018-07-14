package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmDsParminfoHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmTracerLinkHVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class RuleTracerDAOImpl extends BaseDAO implements RuleTracerDAOIntf {

    private static final long serialVersionUID = 1L;

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTracerDAOImpl.class);

    @Override
    public FaultDataDetailsServiceVO getFaults(TracerServiceVO tracerServiceVO) throws RMDDAOException {
        LOG.debug("Enter into searchResult method in RuleTracerDAOImpl");
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();

        LOG.debug("Enter into searchResult method in RuleTracerDAOImpl");
        Session objHibernateSession = null;
        ArrayList arlHeader = new ArrayList();
        ArrayList arlFaultLog = new ArrayList();
        ArrayList arlDataDetails = new ArrayList();
        List arlGrpHeaderList = new ArrayList();
        List arlColTooltips = new ArrayList();
        String strToolTip = RMDCommonConstants.EMPTY_STRING;
        try {
            String strSelectedRuleId = tracerServiceVO.getStrSelRuleID();
            String strSelectedRuleType = tracerServiceVO.getStrSelectedRuleType();

            String strLang = tracerServiceVO.getStrUserLanguage();
            objHibernateSession = getHibernateSession();

            arlHeader = getArlHeaders(strLang);

            arlColTooltips = getColToolTips(strSelectedRuleId, strSelectedRuleType, strLang);

            arlGrpHeaderList = getHeaderGroup(strLang);// For getting the
            // ArlGrpHeaders

            int intArlHeaderSize = 0;
            intArlHeaderSize = arlHeader.size();
            GetToolDsParminfoServiceVO objParminfoServiceVO = new GetToolDsParminfoServiceVO();
            for (int i = 0; i < intArlHeaderSize; i++) {
                objParminfoServiceVO = (GetToolDsParminfoServiceVO) arlHeader.get(i);
            }

            String strFinalHeaderWidth = objParminfoServiceVO.getStrHeaderWidth().substring(0,
                    objParminfoServiceVO.getStrHeaderWidth().length() - 1);
            objFaultDataDetailsServiceVO
                    .setFixedHeaderWidthTotal(objParminfoServiceVO.getFixedHeaderWidthTotal() + RMDServiceConstants.PX);
            objFaultDataDetailsServiceVO.setVariableHeaderWidthTotal(
                    objParminfoServiceVO.getVariableHeaderWidthTotal() + RMDServiceConstants.PX);
            objFaultDataDetailsServiceVO.setStrHeaderWidth(strFinalHeaderWidth);
            objFaultDataDetailsServiceVO.setArlHeader(arlHeader);
            objFaultDataDetailsServiceVO.setArlGrpHeader(arlGrpHeaderList);

            String strColumnName = RMDCommonConstants.EMPTY_STRING;
            String strDataColumns = RMDCommonConstants.EMPTY_STRING;
            String strAlName = RMDCommonConstants.EMPTY_STRING;
            int intArlHeaderListSize = arlHeader.size();

            for (int i = 0; i < intArlHeaderListSize; i++) {
                GetToolDsParminfoServiceVO objParmServiceVO = (GetToolDsParminfoServiceVO) arlHeader.get(i);

                objParmServiceVO.getUpperBound();
                objParmServiceVO.getLowerBound();
                String strFormat = objParmServiceVO.getFormat();
                strColumnName = objParmServiceVO.getColumnName();

                if (RMDServiceConstants.OCCUR_DATE.equals(strColumnName)
                        || RMDServiceConstants.FAULT_RESET_DATE.equals(strColumnName)) {
                    strAlName = strColumnName;
                    if (RMDServiceConstants.OCCUR_DATE.equals(strColumnName)) {
                        strColumnName = " FAULT." + strColumnName;
                    }
                    strDataColumns += "NVL2(" + strColumnName + ",to_char(" + strColumnName
                            + ",'MM/dd/yyyy hh:mi:SS AM') || '^' || 'date','')" + strAlName + " ,";
                } else if ("CREATION_DATE".equals(strColumnName)) {
                    strDataColumns += " FAULT." + strColumnName + ",";
                } else if (RMDServiceConstants.LOCATION.equals(strColumnName)) {
                    strDataColumns += " (SELECT DISTINCT PD.PROXIMITY_LABEL || '" + RMDServiceConstants.DS_DELIMITTER
                            + "' || PD.PROXIMITY_DESC ";
                    strDataColumns += " FROM GET_KM.GET_KM_PROXIMITY_DEF PD,";
                    strDataColumns += "	GET_TOOL.GET_TOOL_FLT_PROX_FILTER PROX";
                    strDataColumns += "	WHERE  FAULT.GET_DATA_FAULT_SEQ_ID = PROX.LINK_FAULT";
                    strDataColumns += "	AND PROX.LINK_PROX_DEF = PD.GET_KM_PROXIMITY_DEF_SEQ_ID) " + strColumnName
                            + ",";
                } else if (RMDServiceConstants.LINK_FAULT_CODES.equals(strColumnName)) {
                    strDataColumns += " NVL(( SELECT FAULT_CODE FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                            + strColumnName + " ),FAULT.FAULT_CODE) " + strColumnName + " , ";
                } else if (RMDServiceConstants.FAULT_DESC.equalsIgnoreCase(strColumnName)) {
                    if (null != strFormat) {
                        strDataColumns += " ( SELECT NVL2(FAULT_DESC," + strFormat.substring(0, strFormat.indexOf('$'))
                                + strColumnName + strFormat.substring(strFormat.indexOf('$') + 1, strFormat.length())
                                + ",' ') FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                                + RMDServiceConstants.LINK_FAULT_CODES + " ) " + strColumnName + " , ";
                    } else {
                        strDataColumns += " ( SELECT NVL2(FAULT_DESC,FAULT_DESC,' ') FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                                + RMDServiceConstants.LINK_FAULT_CODES + " ) " + strColumnName + " , ";
                    }
                } else {
                    strToolTip = RMDCommonConstants.EMPTY_STRING;
                    if (arlColTooltips.contains(strColumnName)) {

                        strToolTip += " ||decode(  db_column_name , ";
                        strToolTip += "'" + strColumnName + "',    '^' ||tooltip,    NULL) ";
                    }

                    // Decode code comes here
                    String strColumnForQuery = strColumnName;
                    if (null != strFormat) {
                        strDataColumns += "NVL2(" + strColumnName + ",("
                                + strFormat.substring(0, strFormat.indexOf('$')) + strColumnForQuery
                                + strFormat.substring(strFormat.indexOf('$') + 1, strFormat.length()) + "" + strToolTip
                                + "), null)" + strColumnName + ",";
                    } else {
                        strDataColumns += "NVL2(" + strColumnName + ",( " + strColumnName + "" + strToolTip + "), null)"
                                + strColumnForQuery + ",";
                    }

                }

            }

            strDataColumns = strDataColumns.substring(0, strDataColumns.length() - 1);
            LOG.debug("strDataColumns :" + strDataColumns);
            StringBuilder sbDataQuery = new StringBuilder();
            sbDataQuery.append(" SELECT INN.*,COUNT(*) OVER (ORDER BY RNUM) CNT FROM "
                    + "(SELECT FAULT.*, 1 RNUM FROM  (SELECT " + strDataColumns);
            sbDataQuery.append(" FROM ");
            sbDataQuery.append(" GET_DATA.GET_DATA_FAULT FAULT,GET_DATA.GET_DATA_MP MP ");
            sbDataQuery.append("  , GET_KM.GET_KM_TRACER TRACER ");
            sbDataQuery.append(
                    " , GET_KM.GET_KM_FIRED_SIMRUL PROCESSEDSIMRUL, GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT, ");

            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.COMPLEX)) {

                sbDataQuery.append("(SELECT DISTINCT REF_LINK_SIMRUL SIMRUL_ID ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbDataQuery.append(" WHERE LINK_CMPRUL IN ");
                sbDataQuery.append(" (SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = " + strSelectedRuleId
                        + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbDataQuery.append(" UNION ");
                sbDataQuery.append(" SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbDataQuery.append(" (SELECT FINRUL.LINK_CMPRUL ");
                sbDataQuery.append(" FROM ");
                sbDataQuery.append(" (SELECT DISTINCT REF_LINK_FINRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = " + strSelectedRuleId
                        + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbDataQuery.append(" REFFINRUL, ");
                sbDataQuery.append(" GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbDataQuery.append(" WHERE REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID) ");
                sbDataQuery.append(" CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbDataQuery.append(" UNION ");
                sbDataQuery.append(" SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbDataQuery.append(" (SELECT FINRUL.LINK_CMPRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK CMPLINK, ");
                sbDataQuery.append(" GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbDataQuery.append(" WHERE LINK_ID IN ");
                sbDataQuery.append(" (SELECT LINK_ID ");
                sbDataQuery.append(" FROM ");
                sbDataQuery.append(" (SELECT DISTINCT LINK_ID, ");
                sbDataQuery.append(" (SELECT LINK_CMPRUL ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_FINRUL ");
                sbDataQuery.append(" WHERE GET_KM_DPD_FINRUL_SEQ_ID = REF_LINK_FINRUL) ");
                sbDataQuery.append(" CHILDCMP ");
                sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbDataQuery.append(" WHERE REF_LINK_FINRUL IS NOT NULL) ");
                sbDataQuery.append(
                        " START WITH LINK_ID =  " + strSelectedRuleId + " CONNECT BY PRIOR CHILDCMP = LINK_ID) ");
                sbDataQuery.append(" AND REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID ");
                sbDataQuery.append(" AND REF_LINK_FINRUL IS NOT NULL) ");
                sbDataQuery.append(" CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbDataQuery.append(" ) ");
                sbDataQuery.append(" AND REF_LINK_SIMRUL IS NOT NULL) CHILDSIMPLE , ");
            }

            sbDataQuery.append(
                    " (SELECT DISTINCT PROCESSEDFLT.LINK_FAULT ,   NVL2(SIMFEA.VALUE2,   COL.DB_COLUMN_NAME || SIMFN.FCN || SIMFEA.VALUE1 || ',' || ");
            sbDataQuery.append(
                    "     SIMFEA.VALUE2,   COL.COLUMN_NAME || SIMFN.FCN || SIMFEA.VALUE1) TOOLTIP, DB_COLUMN_NAME,  COLUMN_NAME COLNAME ");
            sbDataQuery.append(" FROM GET_KM.GET_KM_DPD_SIMRUL SIM, ");
            sbDataQuery.append("   GET_KM.GET_KM_DPD_SIMFEA SIMFEA, ");
            sbDataQuery.append("   GET_KM.GET_KM_DPD_SIMFCN SIMFN, ");
            sbDataQuery.append("   GET_KM.GET_KM_DPD_COLNAME COL, ");
            sbDataQuery.append("   GET_KM.GET_KM_FIRED_SIMRUL PROCESSEDSIMRUL, ");
            sbDataQuery.append("   GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT ");

            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.SIMPLE)) {

                sbDataQuery.append("   WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbDataQuery.append("  AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID ");
                sbDataQuery.append(" 	 AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME ");
                sbDataQuery.append(" 	 AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbDataQuery.append("  AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbDataQuery.append("  AND GET_KM_DPD_SIMRUL_SEQ_ID = " + strSelectedRuleId + ") SIMRULEFEA  ");

            }
            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.COMPLEX)) {

                sbDataQuery.append("  ,   (SELECT DISTINCT REF_LINK_SIMRUL SIMRUL_ID ");
                sbDataQuery.append("    FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbDataQuery.append("    WHERE LINK_CMPRUL IN ");
                sbDataQuery.append("     (SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL =" + strSelectedRuleId
                        + "  CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbDataQuery.append("      UNION ");
                sbDataQuery.append("      SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbDataQuery.append("       (SELECT FINRUL.LINK_CMPRUL ");
                sbDataQuery.append("        FROM ");
                sbDataQuery.append("         (SELECT DISTINCT REF_LINK_FINRUL ");
                sbDataQuery.append("          FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = "
                        + strSelectedRuleId + "  CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbDataQuery.append("       REFFINRUL, ");
                sbDataQuery.append("          GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbDataQuery.append("        WHERE REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID) ");
                sbDataQuery.append("     CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbDataQuery.append("      UNION ");
                sbDataQuery.append("      SELECT DISTINCT LINK_CMPRUL ");
                sbDataQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbDataQuery.append("       (SELECT FINRUL.LINK_CMPRUL ");
                sbDataQuery.append("        FROM GET_KM.GET_KM_DPD_CMP_LINK CMPLINK, ");
                sbDataQuery.append("          GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbDataQuery.append("        WHERE LINK_ID IN ");
                sbDataQuery.append("         (SELECT LINK_ID ");
                sbDataQuery.append("          FROM ");
                sbDataQuery.append("           (SELECT DISTINCT LINK_ID, ");
                sbDataQuery.append("               (SELECT LINK_CMPRUL ");
                sbDataQuery.append("              FROM GET_KM.GET_KM_DPD_FINRUL ");
                sbDataQuery.append("              WHERE GET_KM_DPD_FINRUL_SEQ_ID = REF_LINK_FINRUL) ");
                sbDataQuery.append("           CHILDCMP ");
                sbDataQuery.append("            FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbDataQuery.append("            WHERE REF_LINK_FINRUL IS NOT NULL) ");
                sbDataQuery.append("         START WITH LINK_ID = " + strSelectedRuleId
                        + "  CONNECT BY PRIOR CHILDCMP = LINK_ID) ");
                sbDataQuery.append("       AND REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID ");
                sbDataQuery.append("        AND REF_LINK_FINRUL IS NOT NULL) ");
                sbDataQuery.append("     CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbDataQuery.append("   AND REF_LINK_SIMRUL IS NOT NULL) ");
                sbDataQuery.append(" CHILDSIMPLE ");
                sbDataQuery.append(" WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbDataQuery.append("  AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID ");
                sbDataQuery.append("  AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME ");
                sbDataQuery.append("  AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbDataQuery.append("  AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbDataQuery.append("  AND GET_KM_DPD_SIMRUL_SEQ_ID = CHILDSIMPLE.SIMRUL_ID) SIMRULEFEA  ");
            }

            sbDataQuery.append(" WHERE ");
            sbDataQuery.append(" MP.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID  ");
            sbDataQuery.append(" AND PROCESSEDFLT.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID ");
            sbDataQuery.append("  AND PROCESSEDSIMRUL.LINK_PROCESSED_FLT = PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID ");
            sbDataQuery.append("  AND PROCESSEDSIMRUL.LINK_TRACER = TRACER.GET_KM_TRACER_SEQ_ID ");
            sbDataQuery.append(" AND TRACER.GET_KM_TRACER_SEQ_ID = ");
            sbDataQuery.append(tracerServiceVO.getStrTraceID());

            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.SIMPLE)) {
                sbDataQuery.append(" AND PROCESSEDSIMRUL.LINK_SIMRUL = ");
                sbDataQuery.append(strSelectedRuleId);
            } else if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.COMPLEX)) {
                sbDataQuery.append(" AND PROCESSEDSIMRUL.LINK_SIMRUL = CHILDSIMPLE.SIMRUL_ID ");
            }

            sbDataQuery.append("   AND SIMRULEFEA.LINK_FAULT(+) = FAULT.GET_DATA_FAULT_SEQ_ID ");

            sbDataQuery.append(" ORDER BY FAULT.OCCUR_TIME DESC ) FAULT)INN");

            LOG.debug("sbDataQuery :" + sbDataQuery.toString());
            Query dataFaultQuery = objHibernateSession.createSQLQuery(sbDataQuery.toString());
            if (tracerServiceVO.getIntNoOfRecs() >= 0) {
                dataFaultQuery.setFirstResult(tracerServiceVO.getIntStartPos());
                dataFaultQuery.setMaxResults(tracerServiceVO.getIntNoOfRecs());
            }
            List arlData = dataFaultQuery.list();
            int intArlDataSize = arlData.size();
            FaultServiceVO objFaultServiceVO = new FaultServiceVO();
            arlFaultLog = new ArrayList();
            FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
            arlDataDetails = new ArrayList();
            String strTotCnt = null;
            FaultDetailsServiceVO objFaultDetailsServiceVO = null;
            for (int i = 0; i < intArlDataSize; i++) {
                objFaultLogServiceVO = new FaultLogServiceVO();
                arlDataDetails = new ArrayList();
                Object[] objDataArray = (Object[]) arlData.get(i);
                String strTmpDataHolder = null;
                for (int k = 0; k < objDataArray.length - 1; k++) {
                    if (null != objDataArray[k]) {
                        strTmpDataHolder = objDataArray[k].toString();
                    } else {
                        strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                    }
                    objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                    objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                    arlDataDetails.add(objFaultDetailsServiceVO);
                }
                strTotCnt = objDataArray[intArlHeaderListSize + 1].toString();
                objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                arlFaultLog.add(objFaultLogServiceVO);
            }
            /*
             * modified added -1 since total no of records are also fetched as a
             * part of the query
             */
            /*
             * added to set the total number of records available in DB on the
             * whole
             */
            if (intArlDataSize > 0) {
                objFaultDataDetailsServiceVO.setIntTotalRecsAvail(RMDCommonUtility.getIntValue(strTotCnt));
            }
            objFaultServiceVO.setArlFaultData(arlFaultLog);
            objFaultDataDetailsServiceVO.setObjFaultServiceVO(objFaultServiceVO);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_SERCH_RESULT, e);
        } finally {
            releaseSession(objHibernateSession);
            arlHeader = null;
            arlFaultLog = null;
            arlDataDetails = null;
        }
        LOG.debug("End of searchResult method in RuleTracerDAOImpl");
        return objFaultDataDetailsServiceVO;
    }

    /**
     * @param strLanguage
     * @return
     */
    private ArrayList getArlHeaders(String strLanguage) {
        Session hibernateSession = getHibernateSession();
        ArrayList arlHeader = new ArrayList();
        ArrayList arlHeaderList = new ArrayList();
        String strHeaderWidth = RMDCommonConstants.EMPTY_STRING;
        String strWidth = RMDCommonConstants.EMPTY_STRING;
        try {
            StringBuilder sbHeaderQuery = new StringBuilder();
            sbHeaderQuery.append(
                    " SELECT GET_CM_DS_PARMINFO_SEQ_ID,LINK_PARMDEF,INFO,LOWER_BOUND,UPPER_BOUND,HEADER_HTML,SORT_ORDER,PARM_GROUP,HEADER_WIDTH,DATA_SUBSTRING,COLUMN_NAME,DATA_TOOLTIP_FLAG,STYLE,FORMAT,DS_COL_AVAIL,VVF_COL_AVAIL,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,LANGUAGE ");
            sbHeaderQuery.append(" FROM GET_CM.GET_CM_DS_PARMINFO PARMINFO WHERE RT_COL_AVAIL='Y' AND LANGUAGE = '"
                    + strLanguage + "'");
            sbHeaderQuery.append(" ORDER BY SORT_ORDER ");
            Query query = hibernateSession.createSQLQuery(sbHeaderQuery.toString())
                    .addEntity(RMDCommonConstants.PARMINFO, GetCmDsParminfoHVO.class);
            arlHeader = (ArrayList) query.list();
            GetToolDsParminfoServiceVO objParminfoServiceVO;
            int intArlHeaderSize = arlHeader.size();
            int fixedHeaderWidthTotal = 0;
            int variableHeaderWidthTotal = 0;
            int intWidthLen = 0;
            StringBuilder sbfDummyScrollHeader;
            for (int i = 0; i < intArlHeaderSize; i++) {
                sbfDummyScrollHeader = new StringBuilder();
                GetCmDsParminfoHVO objGetCmDsParminfoHVO = (GetCmDsParminfoHVO) arlHeader.get(i);
                objParminfoServiceVO = new GetToolDsParminfoServiceVO();
                objParminfoServiceVO.setColumnName(objGetCmDsParminfoHVO.getColumnName());
                objParminfoServiceVO.setHeaderHtml(objGetCmDsParminfoHVO.getHeaderHtml());
                objParminfoServiceVO.setStyle(objGetCmDsParminfoHVO.getStyle());
                objParminfoServiceVO.setInfo(objGetCmDsParminfoHVO.getInfo());
                objParminfoServiceVO.setLowerBound(objGetCmDsParminfoHVO.getLowerBound());
                objParminfoServiceVO.setUpperBound(objGetCmDsParminfoHVO.getUpperBound());
                objParminfoServiceVO.setDataTooltipFlag(objGetCmDsParminfoHVO.getDataTooltipFlag());
                strWidth = objGetCmDsParminfoHVO.getHeaderWidth().toString();
                intWidthLen = Integer.valueOf(strWidth);
                if (i < 4) {
                    fixedHeaderWidthTotal += objGetCmDsParminfoHVO.getHeaderWidth();
                    if (i == 0) {
                        intWidthLen = intWidthLen - 20;
                    }
                    for (int index = 0; index < intWidthLen; index++) {
                        sbfDummyScrollHeader.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                } else {
                    variableHeaderWidthTotal += objGetCmDsParminfoHVO.getHeaderWidth();
                    for (int index = 0; index < intWidthLen; index++) {
                        sbfDummyScrollHeader.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                }
                String temp = objGetCmDsParminfoHVO.getHeaderWidth() + "px,";
                strHeaderWidth = strHeaderWidth + temp;
                objParminfoServiceVO.setHeaderWidth(strWidth + RMDServiceConstants.PX);
                objParminfoServiceVO.setDummyHeaderHtml(sbfDummyScrollHeader.toString());
                objParminfoServiceVO.setFormat(objGetCmDsParminfoHVO.getFormat());
                objParminfoServiceVO.setLowerBound(objGetCmDsParminfoHVO.getLowerBound());
                objParminfoServiceVO.setUpperBound(objGetCmDsParminfoHVO.getUpperBound());
                objParminfoServiceVO.setFixedHeaderWidthTotal(String.valueOf(fixedHeaderWidthTotal));
                objParminfoServiceVO.setVariableHeaderWidthTotal(String.valueOf(variableHeaderWidthTotal));
                objParminfoServiceVO.setStrHeaderWidth(strHeaderWidth);
                arlHeaderList.add(objParminfoServiceVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTracerDAOImpl getArlHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlHeaderList;
    }

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @Description:
     */
    public List getHeaderGroup(String strLanguage) {
        Session hibernateSession = null;
        List arlHeaderGroup = new ArrayList();
        try {
            hibernateSession = getHibernateSession();
            String strQuery = "SELECT PARM_GROUP, STYLE, SUM(HEADER_WIDTH) HEADERWIDTH,COUNT(*) NO_COLUMNS , MIN(SORT_ORDER) SORTORDER FROM GET_CM.GET_CM_DS_PARMINFO WHERE RT_COL_AVAIL='Y' AND LANGUAGE = :LANGUAGE GROUP BY PARM_GROUP, STYLE ORDER BY SORTORDER";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.LANGUAGE, strLanguage);
            List headerGroupResult = query.list();
            int iheaderGroupResult = headerGroupResult.size();
            if (RMDCommonUtility.isCollectionNotEmpty(headerGroupResult)) {
                for (int row = 0; row < iheaderGroupResult; row++) {
                    Object[] resultRow = (Object[]) headerGroupResult.get(row);
                    ToolDsParmGroupServiceVO parmGroupInfo = new ToolDsParmGroupServiceVO();
                    parmGroupInfo.setParamGroup((String) resultRow[0]);
                    parmGroupInfo.setStyle((String) resultRow[1]);
                    long groupHeaderWidth = ((BigDecimal) resultRow[2]).longValue();
                    long no_of_cols = ((BigDecimal) resultRow[3]).longValue();
                    parmGroupInfo.setColspan(no_of_cols + RMDCommonConstants.EMPTY_STRING);
                    parmGroupInfo.setGroupHeaderWidth(Long.valueOf(groupHeaderWidth) + RMDServiceConstants.PX);
                    if (row > 0) {
                    }
                    StringBuilder sbfDummyHeaderGroup = new StringBuilder();
                    for (int index = 0; index < groupHeaderWidth; index++) {
                        sbfDummyHeaderGroup.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                    parmGroupInfo.setDummyParamGroup(sbfDummyHeaderGroup.toString());
                    arlHeaderGroup.add(parmGroupInfo);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTracerDAOImpl getArlGrpHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlHeaderGroup;
    }

    public List getColToolTips(String strSelectedRuleId, String strSelectedRuleType, String strLanguage) {
        Session hibernateSession = getHibernateSession();
        List arlColToolTips = new ArrayList();
        try {
            // To get the Column name and the Tool Tip based on Rule
            StringBuilder sbColTooltipQuery = new StringBuilder();
            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.SIMPLE)) {

                sbColTooltipQuery.append(" SELECT DISTINCT    COL.DB_COLUMN_NAME ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_SIMRUL SIM, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_DPD_SIMFEA SIMFEA, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_DPD_SIMFCN SIMFN, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_DPD_COLNAME COL, ");
                sbColTooltipQuery.append("  GET_KM.GET_KM_FIRED_SIMRUL PROCESSEDSIMRUL, ");
                sbColTooltipQuery.append("  GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT ");
                sbColTooltipQuery.append(" WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append(" AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID ");
                sbColTooltipQuery.append("   AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME ");
                sbColTooltipQuery.append("  AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append("   AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbColTooltipQuery.append("  AND GET_KM_DPD_SIMRUL_SEQ_ID =  ");

                sbColTooltipQuery.append(strSelectedRuleId);
            } else if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.COMPLEX)) {
                // Complex Query here
                sbColTooltipQuery.append(" SELECT DISTINCT  COL.DB_COLUMN_NAME ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_SIMRUL SIM, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_DPD_SIMFEA SIMFEA, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_DPD_SIMFCN SIMFN, ");
                sbColTooltipQuery.append("   get_km.get_km_dpd_colname COL, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_FIRED_SIMRUL PROCESSEDSIMRUL, ");
                sbColTooltipQuery.append("   GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT, ");
                sbColTooltipQuery.append("     (SELECT DISTINCT REF_LINK_SIMRUL SIMRUL_ID ");
                sbColTooltipQuery.append("    FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbColTooltipQuery.append("    WHERE LINK_CMPRUL IN ");
                sbColTooltipQuery.append("     (SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = "
                        + strSelectedRuleId + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbColTooltipQuery.append("      UNION ");
                sbColTooltipQuery.append("      SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbColTooltipQuery.append("       (SELECT FINRUL.LINK_CMPRUL ");
                sbColTooltipQuery.append("        FROM ");
                sbColTooltipQuery.append("         (SELECT DISTINCT REF_LINK_FINRUL ");
                sbColTooltipQuery.append("          FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = "
                        + strSelectedRuleId + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbColTooltipQuery.append("       REFFINRUL, ");
                sbColTooltipQuery.append("          GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbColTooltipQuery.append("        WHERE REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID) ");
                sbColTooltipQuery.append("     CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbColTooltipQuery.append("      UNION ");
                sbColTooltipQuery.append("      SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append("      FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbColTooltipQuery.append("       (SELECT FINRUL.LINK_CMPRUL ");
                sbColTooltipQuery.append("        FROM GET_KM.GET_KM_DPD_CMP_LINK CMPLINK, ");
                sbColTooltipQuery.append("          GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbColTooltipQuery.append("        WHERE LINK_ID IN ");
                sbColTooltipQuery.append("         (SELECT LINK_ID ");
                sbColTooltipQuery.append("          FROM ");
                sbColTooltipQuery.append("           (SELECT DISTINCT LINK_ID, ");
                sbColTooltipQuery.append("               (SELECT LINK_CMPRUL ");
                sbColTooltipQuery.append("              FROM GET_KM.GET_KM_DPD_FINRUL ");
                sbColTooltipQuery.append("              WHERE GET_KM_DPD_FINRUL_SEQ_ID = REF_LINK_FINRUL) ");
                sbColTooltipQuery.append("           CHILDCMP ");
                sbColTooltipQuery.append("            FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbColTooltipQuery.append("            WHERE REF_LINK_FINRUL IS NOT NULL) ");
                sbColTooltipQuery.append("         START WITH LINK_ID = " + strSelectedRuleId
                        + " CONNECT BY PRIOR CHILDCMP = LINK_ID) ");
                sbColTooltipQuery.append("       AND REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID ");
                sbColTooltipQuery.append("        AND REF_LINK_FINRUL IS NOT NULL) ");
                sbColTooltipQuery.append("     CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbColTooltipQuery.append("   AND REF_LINK_SIMRUL IS NOT NULL) ");
                sbColTooltipQuery.append(" CHILDSIMPLE ");
                sbColTooltipQuery.append(" WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append("  AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID ");
                sbColTooltipQuery.append("  AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME ");
                sbColTooltipQuery.append("  AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append("  AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbColTooltipQuery.append("  AND GET_KM_DPD_SIMRUL_SEQ_ID = CHILDSIMPLE.SIMRUL_ID  ");
            }
            Query query = hibernateSession.createSQLQuery(sbColTooltipQuery.toString());
            arlColToolTips = query.list();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTracerDAOImpl getArlHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlColToolTips;
    }

    @Override
    public String getTraceIDForMJ(TracerServiceVO tracerServiceVO) throws RMDDAOException {
        String trackingIDMJ;
        String strFinRul;
        Session session = null;
        List<GetKmTracerLinkHVO> arlResult;
        GetKmTracerLinkHVO getKmTracerLinkHVO = null;
        String traceID = null;
        try {
            session = getHibernateSession();
            trackingIDMJ = tracerServiceVO.getStrTrackingID();
            strFinRul = tracerServiceVO.getStrFinalRuleID();
            Criteria criteria = session.createCriteria(GetKmTracerLinkHVO.class);
            criteria.setFetchMode(RMDCommonConstants.GET_KM_MAN_RUN, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GET_KM_MAN_RUN, RMDCommonConstants.MAN_RUN);
            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER, RMDCommonConstants.TRACER);
            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, RMDCommonConstants.FINRUL);
            criteria.add(Restrictions.eq(RMDCommonConstants.MANRUN_GET_KM_RUNSEQID, Long.valueOf(trackingIDMJ)));
            criteria.add(Restrictions.eq(RMDCommonConstants.FINRUL_GETKMDPDFIN_SEQID, Long.valueOf(strFinRul)));

            arlResult = criteria.list();
            for (Iterator<GetKmTracerLinkHVO> iterator = arlResult.iterator(); iterator.hasNext();) {
                getKmTracerLinkHVO = iterator.next();
            }
            if (getKmTracerLinkHVO != null && getKmTracerLinkHVO.getGetKmTracer() != null) {
                traceID = getKmTracerLinkHVO.getGetKmTracer().getGetKmTracerSeqId().toString();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return traceID;

    }

    /**
     * Refer the Corresponding method in the Intf
     */
    @Override
    public String getTraceIDForTester(TracerServiceVO tracerServiceVO) throws RMDDAOException {
        String trackingIDTester;
        Session session = null;
        String strFinRul;
        String traceID = null;
        List<GetKmTracerLinkHVO> arlResult;
        GetKmTracerLinkHVO getKmTracerLinkHVO = null;
        try {
            session = getHibernateSession();
            trackingIDTester = tracerServiceVO.getStrTrackingID();
            strFinRul = tracerServiceVO.getStrFinalRuleID();
            Criteria criteria = session.createCriteria(GetKmTracerLinkHVO.class);
            criteria.setFetchMode("getToolJdpadTracking", FetchMode.JOIN).createAlias("getToolJdpadTracking", "tester");

            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER, RMDCommonConstants.TRACER);
            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, RMDCommonConstants.FINRUL);
            criteria.add(Restrictions.eq(RMDCommonConstants.TESTER_GETTOOLJDPAD_TRACKINGSEQID,
                    Long.valueOf(trackingIDTester)));
            criteria.add(Restrictions.eq(RMDCommonConstants.FINRUL_GETKMDPDFIN_SEQID, Long.valueOf(strFinRul)));

            arlResult = criteria.list();
            for (Iterator<GetKmTracerLinkHVO> iterator = arlResult.iterator(); iterator.hasNext();) {
                getKmTracerLinkHVO = iterator.next();
            }
            if (getKmTracerLinkHVO != null && getKmTracerLinkHVO.getGetKmTracer() != null) {
                traceID = getKmTracerLinkHVO.getGetKmTracer().getGetKmTracerSeqId().toString();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return traceID;

    }

    /**
     * Refer the Corresponding method in the Intf
     */
    @Override
    public String getTraceIDForRunRecreator(TracerServiceVO tracerServiceVO) throws RMDDAOException {
        String trackingIDRunRecrt;
        Session session = null;
        String strFinRul;
        String traceID = null;
        List<GetKmTracerLinkHVO> arlResult;
        GetKmTracerLinkHVO getKmTracerLinkHVO = null;
        try {
            session = getHibernateSession();
            trackingIDRunRecrt = tracerServiceVO.getStrTrackingID();
            strFinRul = tracerServiceVO.getStrFinalRuleID();
            Criteria criteria = session.createCriteria(GetKmTracerLinkHVO.class);
            criteria.setFetchMode(RMDCommonConstants.GETKM_RUNRECREATOR, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKM_RUNRECREATOR, RMDCommonConstants.RUNRECT);
            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER, RMDCommonConstants.TRACER);
            criteria.setFetchMode(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMTRACER_GETKMDPDFINRUL, RMDCommonConstants.FINRUL);

            criteria.add(
                    Restrictions.eq(RMDCommonConstants.RUNRECT_GETKMRUNRECREATSEQID, Long.valueOf(trackingIDRunRecrt)));
            criteria.add(Restrictions.eq(RMDCommonConstants.FINRUL_GETKMDPDFIN_SEQID, Long.valueOf(strFinRul)));
            arlResult = criteria.list();
            for (Iterator<GetKmTracerLinkHVO> iterator = arlResult.iterator(); iterator.hasNext();) {
                getKmTracerLinkHVO = iterator.next();
            }
            if (getKmTracerLinkHVO != null && getKmTracerLinkHVO.getGetKmTracer() != null) {
                traceID = getKmTracerLinkHVO.getGetKmTracer().getGetKmTracerSeqId().toString();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return traceID;

    }

    /**
     * Refer the Corresponding method in the Intf
     */
    @Override
    public String getTraceIDForOnTheFlyTrace(TracerServiceVO tracerServiceVO) throws RMDDAOException {
        String strCaseID = null;
        Session session = null;
        Query query = null;
        String traceID = null;
        StringBuilder queryString = null;
        List arlResult;
        try {
            session = getHibernateSession();
            strCaseID = tracerServiceVO.getStrCaseId();
            tracerServiceVO.getStrFinalRuleID();
            queryString = new StringBuilder();
            queryString.append(" SELECT LINK_TRACER FROM GET_KM.GET_KM_TRACER_LINK WHERE LINK_SUBRUN=(   ");
            queryString.append(" SELECT  DISTINCT GET_TOOL_SUBRUN_SEQ_ID FROM   ");
            queryString.append(" GET_TOOL.GET_TOOL_RUN RUN,   ");
            queryString.append(" GET_TOOL.GET_TOOL_SUBRUN SUBRUN   ");
            queryString.append(" WHERE LINK_RUN = GET_TOOL_RUN_SEQ_ID   ");
            queryString.append(" AND SUBRUN.TOOL_ID = 'JDPAD'   ");
            queryString.append(" AND RUN.LAST_UPDATED_BY = 'Case Creation'  ");
            queryString.append(" AND RUN.LINK_CASE = " + strCaseID + "");
            queryString.append(" )");
            query = session.createSQLQuery(queryString.toString());
            arlResult = query.list();
            if (!arlResult.isEmpty())
                traceID = arlResult.get(0).toString();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, tracerServiceVO.getStrUserLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return traceID;
    }

}
