package com.ge.trans.eoa.services.cases.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.cases.dao.intf.FaultLogCacheDAOIntf;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class FaultLogCacheDAOImpl extends BaseDAO implements FaultLogCacheDAOIntf {

    @Override
    @Cacheable("dataScreen_DefaultRecordTypesCache")
    public HashMap<String, ArrayList<String>> getAllDefaultRecordTypes() throws Exception {
        HashMap<String, ArrayList<String>> recordTypes = new HashMap<String, ArrayList<String>>();
        Session session = null;
        Query stmtDefRecType = null;
        List rsDefRecType = null;
        StringBuilder sbDefaultRecType = new StringBuilder();
        try {
            session = getHibernateSession();
            sbDefaultRecType.append(" SELECT CASE_PROBLEM_TYPE, RECORD_TYPE ");
            sbDefaultRecType.append(" FROM GETS_SD.GETS_SD_CASETYPE_TO_FLT ");
            stmtDefRecType = session.createSQLQuery(sbDefaultRecType.toString());
            stmtDefRecType.setFetchSize(100);
            rsDefRecType = stmtDefRecType.list();
            if (RMDCommonUtility.isCollectionNotEmpty(rsDefRecType)) {
                for (int index = 0; index < rsDefRecType.size(); index++) {
                    Object data[] = (Object[]) rsDefRecType.get(index);
                    if (recordTypes.get(RMDCommonUtility.convertObjectToString(data[0])) == null) {
                        ArrayList<String> recordList = new ArrayList<String>();
                        recordList.add(RMDCommonUtility.convertObjectToString(data[1]));
                        recordTypes.put(RMDCommonUtility.convertObjectToString(data[0]), recordList);
                    } else {
                        ArrayList<String> recordList = recordTypes.get(RMDCommonUtility.convertObjectToString(data[0]));
                        recordList.add(RMDCommonUtility.convertObjectToString(data[1]));
                        recordTypes.put(RMDCommonUtility.convertObjectToString(data[0]), recordList);
                    }
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting Record Types in FaultLogDAO : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for Record Types : " + ex);
            }
        }
        return recordTypes;
    }

    @Override
    @Cacheable("dataScreen_RecordTypesCache")
    public HashMap<String, String> getAllRecordTypes() throws Exception {
        HashMap<String, String> recordTypes = new HashMap<String, String>();
        String sbRecordTypeQuery = null;
        Session session = null;
        Query hibernateQuery = null;
        List recordTypeList = null;
        try {
            sbRecordTypeQuery = " Select title, value from gets_rmd.gets_rmd_sysparm";
            session = getHibernateSession();
            hibernateQuery = session.createSQLQuery(sbRecordTypeQuery);
            hibernateQuery.setFetchSize(500);
            recordTypeList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(recordTypeList)) {
                for (int index = 0; index < recordTypeList.size(); index++) {
                    Object data[] = (Object[]) recordTypeList.get(index);
                    recordTypes.put(RMDCommonUtility.convertObjectToString(data[0]),
                            RMDCommonUtility.convertObjectToString(data[1]));
                }
            }
        } catch (Exception e) {
            FaultLogHelper.log("Error in getting Record Types :" + e);
            throw e;
        } finally {
            releaseSession(session);
        }
        return recordTypes;
    }

    @Override
    @Cacheable(value = "commonQueryMap_cache")
    public Map<String, ArrayList<HashMap<String, String>>> getCommonQueryMap() throws Exception {
        Map<String, ArrayList<HashMap<String, String>>> commonQueryMap = new HashMap<String, ArrayList<HashMap<String, String>>>();
        ArrayList<HashMap<String, String>> sequenceList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> innerMap = new HashMap<String, String>();
        Session session = null;
        session = getHibernateSession();
        Query pstmt = null;
        pstmt = session.createSQLQuery(
                "select objid, screen_type, select_col, where_col, orderby, ctrl_cfg, view_name, sequence_no, query, "
                        + " casecondition from get_cm.get_cm_datascreen_builder order by screen_type, select_col, where_Col, orderby, sequence_no");
        pstmt.setFetchSize(200);
        List commonQryList = pstmt.list();
        int index = 0;
        String screenType = null, select = null, where = null, orderBy = null, ctrlCfg = null, view = null;
        String query = null, operation = null, key = null, casecondition = null;
        int sequence = -1;
        int prevSeq = -1;
        try {
            if (RMDCommonUtility.isCollectionNotEmpty(commonQryList)) {
                while (index < commonQryList.size()) {
                    Object data[] = (Object[]) commonQryList.get(index);

                    screenType = RMDCommonUtility.convertObjectToString(data[1]);
                    ctrlCfg = RMDCommonUtility.convertObjectToString(data[5]);
                    view = RMDCommonUtility.convertObjectToString(data[6]);
                    sequence = RMDCommonUtility.convertObjectToInt(data[7]);
                    query = RMDCommonUtility.convertObjectToString(data[8]);
                    select = RMDCommonUtility.convertObjectToString(data[2]);
                    casecondition = RMDCommonUtility.convertObjectToString(data[9]);
                    if (select != null && select.trim().equals("Y")) {
                        key = ctrlCfg == null ? "-" : ctrlCfg;
                        if (casecondition == null) {
                            key += "*-";
                        } else {
                            key += "*" + casecondition;
                        }
                        operation = "SELECT";
                    }
                    where = RMDCommonUtility.convertObjectToString(data[3]);
                    if (where != null && where.trim().equals("Y")) {
                        key = ctrlCfg == null ? "-" : ctrlCfg;
                        if (view == null) {
                            key += "*-";
                        } else {
                            key += "*" + view;
                        }
                        if (casecondition == null) {
                            key += "*-";
                        } else {
                            key += "*" + casecondition;
                        }
                        operation = "WHERE";
                    }
                    orderBy = RMDCommonUtility.convertObjectToString(data[4]);
                    if (orderBy != null && orderBy.trim().equals("Y")) {
                        key = ctrlCfg == null ? "-" : ctrlCfg;
                        if (casecondition == null) {
                            key += "*-";
                        } else {
                            key += "*" + casecondition;
                        }
                        operation = "ORDERBY";
                    }

                    if (commonQueryMap.get(screenType + "-" + operation) == null) {
                        sequenceList = new ArrayList<HashMap<String, String>>();
                        commonQueryMap.put(screenType + "-" + operation, sequenceList);
                        prevSeq = -1;
                    }
                    if (prevSeq != sequence) {
                        innerMap = new HashMap<String, String>();
                        sequenceList.add(innerMap);
                        prevSeq = sequence;
                    }
                    innerMap.put(key, query);
                    index++;
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting  SelectQueryMap : " + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
                throw e;
            }
        }
        return commonQueryMap;
    }

    @Override
    @Cacheable(value = "selectQueryMap_cache")
    public Map<String, String> getSelectQueryMap() throws Exception {
        Session session = null;
        session = getHibernateSession();
        Query pstmt = null;
        pstmt = session.createSQLQuery(
                "select objid, anomind, column_name, ctrl_cfg, upperbound, lowerbound, datatooltip_flag, "
                        + "screen_type, query from get_cm.get_cm_datascreen_mapping");
        pstmt.setFetchSize(1000);
        List selectQryList = pstmt.list();
        String key = "", qry = "";
        Map<String, String> selectQryMap = new HashMap<String, String>();
        try {
            if (RMDCommonUtility.isCollectionNotEmpty(selectQryList)) {
                int index = 0;
                while (index < selectQryList.size()) {
                    key = "";
                    qry = "";
                    Object data[] = (Object[]) selectQryList.get(index);
                    key += RMDCommonUtility.convertObjectToString(data[1]);
                    if (formatString(RMDCommonUtility.convertObjectToString(data[2])).trim().equals("")) {
                        key += "*-";
                    } else {
                        key += "*" + formatString(RMDCommonUtility.convertObjectToString(data[2]));
                    }
                    key += "*" + RMDCommonUtility.convertObjectToString(data[3]);
                    key += "*" + RMDCommonUtility.convertObjectToString(data[4]);
                    key += "*" + RMDCommonUtility.convertObjectToString(data[5]);
                    key += "*" + RMDCommonUtility.convertObjectToString(data[6]);
                    key += "*" + RMDCommonUtility.convertObjectToString(data[7]);
                    qry = RMDCommonUtility.convertObjectToString(data[8]);
                    selectQryMap.put(key, qry);
                    index++;
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting  SelectQueryMap : " + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
                throw e;
            }
        }
        return selectQryMap;
    }

    @Override
    @Cacheable(value = "dataScreenLimit_Cache")
    public String getDataScreenLimit() throws RMDDAOException {
        Session session = null;
        try {
            String datascreenLimit = "5000";
            session = getHibernateSession();
            final String queryString = "select OBJID, LIST_NAME, LOOK_VALUE, LIST_DESCRIPTION, LOOK_STATE, SORT_ORDER FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME=:listName ORDER BY SORT_ORDER ASC";
            Query query = session.createSQLQuery(queryString);
            query.setParameter(RMDCommonConstants.LIST_NAME, RMDCommonConstants.OMD_DATASCREEN_RECORD_LIMIT);
            query.setFetchSize(10);
            // Get the list based on the search criteria(if any)
            List<Object> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {

                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {

                    final Object[] lookupRecord = (Object[]) iter.next();

                    datascreenLimit = RMDCommonUtility.convertObjectToString(lookupRecord[2]);

                }
            }

            return datascreenLimit;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }
}
