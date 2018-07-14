package com.ge.trans.eoa.services.cases.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.cases.dao.intf.FaultLogCacheDAOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FaultLogDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.common.constants.FaultLogConstants;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetMobileToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.omi.exception.DataException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class FaultLogDAO extends AbsFaultLogDAO implements FaultLogConstants, FaultLogDAOIntf {

    private static final long serialVersionUID = -1031481161087873902L;
    private FaultLogCacheDAOIntf objFaultLogCacheDAOIntf;
    Map<String, String> cacheQryMap = null;
    Codec ORACLE_CODEC = new OracleCodec();
    String caseCreateToDT ="CASECREATETODT";
    String equipNt = "EQUIP-NT";
    String dPEab ="DP/EAB";
    String rrVehicle = "RR-VEHICLE";
    String rrEquipNt = "RR-EQUIP-NT";

    public void setObjFaultLogCacheDAOIntf(FaultLogCacheDAOIntf objFaultLogCacheDAOIntf) {
        this.objFaultLogCacheDAOIntf = objFaultLogCacheDAOIntf;
    }

    @Override
    public FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        Session session = null;
        Query pstmt = null;
        List<FaultDetailsServiceVO> arlDataDetails = null;
        String strNotch = null;
        String faultStrategyID = null;
        String proximityDesc = null;
        String faultOrigin = null;
        StringBuilder sbDataQuery = new StringBuilder();

        FaultServiceVO objFaultServiceVO = new FaultServiceVO();
        int loopSize = arlHeaderDetails.size();
        try {
            if (objFaultRequestVO.getScreen() != null && ("INCIDENT").equals(objFaultRequestVO.getScreen().trim())) {
                session = getDWHibernateSession();
            } else {
                session = getHibernateSession();
            }
            sbDataQuery = getFaultQuery(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            pstmt = session.createSQLQuery(sbDataQuery.toString());

            pstmt.setParameter(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVehicleID()));
            if (objFaultRequestVO.getCaseFrom() != null && objFaultRequestVO.getCaseFrom().trim().equals("APPENDED")) {
                pstmt.setString(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
                if (objFaultRequestVO.getStrMaxUpdatedDate() != null) {
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxUpdatedDate()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                } else {
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                }
            } else if (objFaultRequestVO.getCaseFrom() != null
                    && objFaultRequestVO.getCaseFrom().trim().equals("CASECREATION")) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
                pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
            } else if (objFaultRequestVO.getCaseFrom() != null
                    && objFaultRequestVO.getCaseFrom().trim().equals(caseCreateToDT)) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  String.valueOf(objFaultRequestVO.getCaseObjID())));
            } else if (objFaultRequestVO.getNoOfDays() != null && !objFaultRequestVO.getNoOfDays().trim().equals("")) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
            } else if (objFaultRequestVO.getVvfStartDate() != null && objFaultRequestVO.getVvfEndDate() != null) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVvfStartDate()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVvfEndDate()));
            } else if (objFaultRequestVO.getJdpadCbrCrit() != null) {
                if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CBR)
                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)) {
                    pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  String.valueOf(objFaultRequestVO.getCaseObjID())));
                } else if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_JDPAD)) {
                    pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getCaseId()));
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                    if (null != objFaultRequestVO.getRuleDefID() && !"".equals(objFaultRequestVO.getRuleDefID())) {
                        pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getRuleDefID()));
                    }
                }
            }
            pstmt.setFetchSize(1000);
            List aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                List<FaultLogServiceVO> arlFaultLog = new ArrayList<FaultLogServiceVO>(aliasToValueMapList.size());
                FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
                FaultDetailsServiceVO objFaultDetailsServiceVO = null;
                DateFormat zoneFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                int specialCharAscii = 25;
                char specialChar = (char) specialCharAscii;

                for (int i = 0; i < aliasToValueMapList.size(); i++) {
                    Object[] objMap = (Object[]) aliasToValueMapList.get(i);
                    objFaultLogServiceVO = new FaultLogServiceVO();
                    arlDataDetails = new ArrayList<FaultDetailsServiceVO>();
                    strNotch = null;
                    for (int j = 0; j < loopSize; j++) {
                        String strTmpDataHolder = null;
                        if (null != objMap[j]) {
                            if (objMap[j] instanceof java.util.Date) {
                                strTmpDataHolder = zoneFormater.format(objMap[j]);
                            } else if (objMap[j] instanceof java.math.BigDecimal) {
                                strTmpDataHolder = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[j]);
                            } else {
                                strTmpDataHolder = RMDCommonUtility.convertObjectToString(objMap[j])
                                        .replaceAll(String.valueOf(specialChar), "");
                            }
                            if (j == 13) {
                                strNotch = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                        } else {
                            strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                        }
                        objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                        objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                        arlDataDetails.add(objFaultDetailsServiceVO);
                    }
                    if (objMap.length > loopSize) {
                        faultStrategyID = null;

                        if (objFaultRequestVO.getScreen().indexOf("CASE") > -1) {
                            if (null != objMap[loopSize]) {
                                faultStrategyID = RMDCommonUtility.convertObjectToString(objMap[loopSize]);
                            } else {
                                faultStrategyID = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(faultStrategyID);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            if (objFaultRequestVO.getScreen().indexOf("OIL") == -1) {
                                proximityDesc = null;
                                if (null != objMap[loopSize + 1]) {
                                    proximityDesc = RMDCommonUtility.convertObjectToString(objMap[loopSize + 1]);
                                } else {
                                    proximityDesc = RMDCommonConstants.EMPTY_STRING;
                                }
                                objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                                objFaultDetailsServiceVO.setStrData(proximityDesc);
                                arlDataDetails.add(objFaultDetailsServiceVO);
                            }
                        } else {
                            if (null != objMap[loopSize]) {
                                faultStrategyID = RMDCommonUtility.convertObjectToString(objMap[loopSize]);
                            } else {
                                faultStrategyID = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(faultStrategyID);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            proximityDesc = null;
                            if (null != objMap[loopSize + 1]) {
                                proximityDesc = RMDCommonUtility.convertObjectToString(objMap[loopSize + 1]);
                            } else {
                                proximityDesc = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(proximityDesc);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            if (("VEHICLE").equals(objFaultRequestVO.getScreen())) {
                                faultOrigin = null;
                                if (null != objMap[loopSize + 2]) {
                                    faultOrigin = RMDCommonUtility.convertObjectToString(objMap[loopSize + 2]);
                                } else {
                                    faultOrigin = RMDCommonConstants.EMPTY_STRING;
                                }
                                objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                                objFaultDetailsServiceVO.setStrData(faultOrigin);
                                arlDataDetails.add(objFaultDetailsServiceVO);
                            }
                        }
                    }
                    if (i == 0 && (null == objFaultRequestVO.getViewAll() || !STR_Y.equalsIgnoreCase(objFaultRequestVO.getViewAll()))) {
                        objFaultServiceVO.setTotalRecords(RMDCommonUtility.convertObjectToInt(objMap[objMap.length - 1]));
                    }
                    objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                    objFaultLogServiceVO.setStrNotch(strNotch);
                    arlFaultLog.add(objFaultLogServiceVO);
                }
                objFaultServiceVO.setArlFaultData(arlFaultLog);
            } else {
                objFaultServiceVO.setArlFaultData((ArrayList) aliasToValueMapList);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Fault Data Details : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing the ResultSet for Data: " + ex);
            }
        }
        FaultLogHelper.debug("End getFaultData :" + System.currentTimeMillis());
        return objFaultServiceVO;
    }

    
    @Override
    public FaultMobileServiceVO getMobileFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        Session session = null;
        Query pstmt = null;
        ArrayList arlDataDetails = null;
        String strNotch = null;
        String faultStrategyID = null;
        String proximityDesc = null;
        String faultOrigin = null;
        StringBuilder sbDataQuery = new StringBuilder();

        FaultMobileServiceVO objFaultServiceVO = new FaultMobileServiceVO();
        int loopSize = arlHeaderDetails.size();
        try {
            if (objFaultRequestVO.getScreen() != null && ("INCIDENT").equals(objFaultRequestVO.getScreen().trim())) {
                session = getDWHibernateSession();
            } else {
                session = getHibernateSession();
            }
            sbDataQuery = getMobileFaultQuery(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            pstmt = session.createSQLQuery(sbDataQuery.toString());

            pstmt.setParameter(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVehicleID()));
            if (objFaultRequestVO.getCaseFrom() != null && objFaultRequestVO.getCaseFrom().trim().equals("APPENDED")) {
                pstmt.setString(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
                if (objFaultRequestVO.getStrMaxUpdatedDate() != null) {
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxUpdatedDate()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                } else {
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                }
            } else if (objFaultRequestVO.getCaseFrom() != null
                    && objFaultRequestVO.getCaseFrom().trim().equals("CASECREATION")) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
                pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
            } else if (objFaultRequestVO.getCaseFrom() != null
                    && objFaultRequestVO.getCaseFrom().trim().equals(caseCreateToDT)) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrCreationTime()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  String.valueOf(objFaultRequestVO.getCaseObjID())));
            } else if (objFaultRequestVO.getNoOfDays() != null && !objFaultRequestVO.getNoOfDays().trim().equals("")) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getNoOfDays()));
            } else if (objFaultRequestVO.getVvfStartDate() != null && objFaultRequestVO.getVvfEndDate() != null) {
                pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVvfStartDate()));
                pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getVvfEndDate()));
            } else if (objFaultRequestVO.getJdpadCbrCrit() != null) {
                if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CBR)
                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)) {
                    pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  String.valueOf(objFaultRequestVO.getCaseObjID())));
                } else if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_JDPAD)) {
                    pstmt.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getCaseId()));
                    pstmt.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMinObjid()));
                    pstmt.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getStrMaxObjid()));
                    if (null != objFaultRequestVO.getRuleDefID() && !"".equals(objFaultRequestVO.getRuleDefID())) {
                        pstmt.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,  objFaultRequestVO.getRuleDefID()));
                    }
                }
            }
            pstmt.setFetchSize(1000);
            List aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                ArrayList arlFaultLog = new ArrayList(aliasToValueMapList.size());
                FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
                FaultDetailsServiceVO objFaultDetailsServiceVO = null;
                DateFormat zoneFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                int specialCharAscii = 25;
                char specialChar = (char) specialCharAscii;

                for (int i = 0; i < aliasToValueMapList.size(); i++) {
                    Object[] objMap = (Object[]) aliasToValueMapList.get(i);
                    objFaultLogServiceVO = new FaultLogServiceVO();
                    arlDataDetails = new ArrayList();
                    strNotch = null;
                    for (int j = 0; j < loopSize; j++) {
                        String strTmpDataHolder = null;
                        if (null != objMap[j]) {
                            if (objMap[j] instanceof java.util.Date) {
                                strTmpDataHolder = zoneFormater.format(objMap[j]);
                            } else if (objMap[j] instanceof java.math.BigDecimal) {
                                strTmpDataHolder = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[j]);
                            } else {
                                strTmpDataHolder = RMDCommonUtility.convertObjectToString(objMap[j])
                                        .replaceAll(String.valueOf(specialChar), "");
                            }
                            if (j == 13) {
                                strNotch = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                        } else {
                            strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                        }
                        objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                        objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                        arlDataDetails.add(objFaultDetailsServiceVO);
                    }
                    if (objMap.length > loopSize) {
                        faultStrategyID = null;

                        if (objFaultRequestVO.getScreen().indexOf("CASE") > -1) {
                            if (null != objMap[loopSize]) {
                                faultStrategyID = RMDCommonUtility.convertObjectToString(objMap[loopSize]);
                            } else {
                                faultStrategyID = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(faultStrategyID);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            if (objFaultRequestVO.getScreen().indexOf("OIL") == -1) {
                                proximityDesc = null;
                                if (null != objMap[loopSize + 1]) {
                                    proximityDesc = RMDCommonUtility.convertObjectToString(objMap[loopSize + 1]);
                                } else {
                                    proximityDesc = RMDCommonConstants.EMPTY_STRING;
                                }
                                objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                                objFaultDetailsServiceVO.setStrData(proximityDesc);
                                arlDataDetails.add(objFaultDetailsServiceVO);
                            }
                        } else {
                            if (null != objMap[loopSize]) {
                                faultStrategyID = RMDCommonUtility.convertObjectToString(objMap[loopSize]);
                            } else {
                                faultStrategyID = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(faultStrategyID);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            proximityDesc = null;
                            if (null != objMap[loopSize + 1]) {
                                proximityDesc = RMDCommonUtility.convertObjectToString(objMap[loopSize + 1]);
                            } else {
                                proximityDesc = RMDCommonConstants.EMPTY_STRING;
                            }
                            objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                            objFaultDetailsServiceVO.setStrData(proximityDesc);
                            arlDataDetails.add(objFaultDetailsServiceVO);

                            if (("VEHICLE").equals(objFaultRequestVO.getScreen())) {
                                faultOrigin = null;
                                if (null != objMap[loopSize + 2]) {
                                    faultOrigin = RMDCommonUtility.convertObjectToString(objMap[loopSize + 2]);
                                } else {
                                    faultOrigin = RMDCommonConstants.EMPTY_STRING;
                                }
                                objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                                objFaultDetailsServiceVO.setStrData(faultOrigin);
                                arlDataDetails.add(objFaultDetailsServiceVO);
                            }
                        }
                    }
                    if (i == 0 && (null == objFaultRequestVO.getViewAll()
                            || !STR_Y.equalsIgnoreCase(objFaultRequestVO.getViewAll()))) {
                        objFaultServiceVO
                                .setTotalRecords(RMDCommonUtility.convertObjectToInt(objMap[objMap.length - 1]));
                    }
                    objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                    objFaultLogServiceVO.setStrNotch(strNotch);
                    arlFaultLog.add(objFaultLogServiceVO);
                }
                objFaultServiceVO.setArlFaultData(arlFaultLog);
            } else {
                objFaultServiceVO.setArlFaultData((ArrayList) aliasToValueMapList);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Fault Data Details : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing the ResultSet for Data: " + ex);
            }
        }
        FaultLogHelper.debug("End getFaultData :" + System.currentTimeMillis());
        return objFaultServiceVO;
    }
    
    private StringBuilder getFaultQuery(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbWhereQuery = new StringBuilder();
        StringBuilder sbOrderQuery = new StringBuilder();
        StringBuilder sbDataQuery = new StringBuilder();
        try {
            sbSelectQuery = selectClause(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            sbWhereQuery = whereClause(objFaultRequestVO.getStrViewName(), objFaultRequestVO);
            sbOrderQuery = orderClause(objFaultRequestVO.getStrViewName(), objFaultRequestVO);
            sbDataQuery.append(sbSelectQuery);
            sbDataQuery.append(sbWhereQuery);
            sbDataQuery.append(sbOrderQuery);
            String strViewAll = objFaultRequestVO.getViewAll();
            if (null == strViewAll || !STR_Y.equalsIgnoreCase(strViewAll)) {
                sbDataQuery = getPagination(objFaultRequestVO, sbDataQuery, sbOrderQuery);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building FaultData Query : " + e);
            throw e;
        }
        FaultLogHelper.error("End getFaultQuery :" + System.currentTimeMillis());
        return sbDataQuery;
    }
    
    
    private StringBuilder getMobileFaultQuery(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbWhereQuery = new StringBuilder();
        StringBuilder sbOrderQuery = new StringBuilder();
        StringBuilder sbDataQuery = new StringBuilder();
        try {
            sbSelectQuery = selectClauseForMobile(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            sbWhereQuery = whereClause(objFaultRequestVO.getStrViewName(), objFaultRequestVO);
            sbOrderQuery = orderClause(objFaultRequestVO.getStrViewName(), objFaultRequestVO);
            sbDataQuery.append(sbSelectQuery);
            sbDataQuery.append(sbWhereQuery);
            sbDataQuery.append(sbOrderQuery);
            String strViewAll = objFaultRequestVO.getViewAll();
            if (null == strViewAll || !STR_Y.equalsIgnoreCase(strViewAll)) {
                sbDataQuery = getPagination(objFaultRequestVO, sbDataQuery, sbOrderQuery);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building FaultData Query : " + e);
            throw e;
        }
        FaultLogHelper.error("End getFaultQuery :" + System.currentTimeMillis());
        return sbDataQuery;
    }

    private StringBuilder getPagination(FaultRequestVO objFaultRequestVO, StringBuilder sbDataQuery,
            StringBuilder sbOrderQuery) throws Exception {
        StringBuilder sbAllData = new StringBuilder();
        StringBuilder sbOrder = new StringBuilder();
        try {
            String strPaginationHit = objFaultRequestVO.getPaginationHit();
            String strPaginationCounter = objFaultRequestVO.getPaginationCounter();
            int intPaginationCounter = INT_DEF_PAGINATION_COUNTER;
            if (strPaginationHit == null || "".equals(strPaginationHit)) {
                strPaginationHit = STR_0;
            }
            if (strPaginationCounter != null) {
                intPaginationCounter = RMDCommonUtility.convertObjectToInt(strPaginationCounter);
            }
            int intPaginationHit = RMDCommonUtility.convertObjectToInt(strPaginationHit);
            int intStartRowNum = (intPaginationHit * intPaginationCounter) + 1;
            int intFinalRowNum = (intPaginationHit + 1) * intPaginationCounter;

            sbOrder = paginationOrderClause(objFaultRequestVO, sbOrderQuery);

            sbAllData.append(" SELECT * FROM (SELECT   DD.*, DENSE_RANK () OVER ( " + sbOrder);
            sbAllData.append(" ) RANK , COUNT(*) OVER ( ) CNT FROM ( " + sbDataQuery);
            sbAllData.append(" )dd  ) WHERE RANK BETWEEN " + intStartRowNum);
            sbAllData.append(" AND " + intFinalRowNum);
            sbAllData.append(" ORDER BY RANK ASC ");
        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        }
        return sbAllData;
    }

    private StringBuilder paginationOrderClause(FaultRequestVO objFaultRequestVO, StringBuilder sbOrderQuery)
            throws Exception {
        StringBuilder sbPaginationOrder = new StringBuilder();
        String strCase = objFaultRequestVO.getCaseFrom();
        try {
            if (STR_AC6000.equalsIgnoreCase(objFaultRequestVO.getCtrlCfg())) {
                if (objFaultRequestVO.getNoOfDays() != null && !objFaultRequestVO.getNoOfDays().trim().equals("")
                        && (STR_CASE_CREATION.equalsIgnoreCase(strCase)
                                || STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCase))) {
                    sbPaginationOrder.append(sbOrderQuery);
                    sbPaginationOrder.append(",fltindex desc, fault_objid ,v_sample_no DESC ");
                } else {
                    sbPaginationOrder.append(sbOrderQuery);
                }
            } else {
                sbPaginationOrder.append(sbOrderQuery);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building order clause for pagination in FaultLogDAO : " + e);
        }
        return sbPaginationOrder;
    }

    private String getRecordTypes(String strTitle) throws Exception {
        String strRecordType = RMDCommonConstants.EMPTY_STRING;
        String strRecTypes = null;
        StringTokenizer st = null;
        String strData = null;
        try {
            strRecTypes = objFaultLogCacheDAOIntf.getAllRecordTypes().get(strTitle);
            st = new StringTokenizer(strRecTypes, RMDCommonConstants.COMMMA_SEPARATOR);
            while (st.hasMoreTokens()) {
                if (st.hasMoreTokens()) {
                    strData = st.nextToken();
                    strRecordType += RMDCommonConstants.SINGLE_QTE + strData + RMDCommonConstants.SINGLE_QTE
                            + RMDCommonConstants.COMMMA_SEPARATOR;
                }
            }
            strRecordType = strRecordType.substring(0, strRecordType.length() - 1);
            FaultLogHelper.log("strRecType after :" + strRecordType);
        } catch (Exception e) {
            FaultLogHelper.log("Error in getting Record Types :" + e);
            throw e;
        }
        return strRecordType;
    }

    private String getCaseRecordTypes(String problemType, String HC) throws Exception {

        ArrayList<String> recTypes = null;

        String strData = "";
        try {
            recTypes = objFaultLogCacheDAOIntf.getAllDefaultRecordTypes().get(problemType);
            if (recTypes != null) {
                for (int i = 0; i < recTypes.size(); i++) {
                    if (recTypes.get(i) != null && !recTypes.get(i).trim().equals("")) {
                        if ((HC == null || !(("YES").equals(HC.trim()))) && ("HC").equals(recTypes.get(i).trim())) {
                            continue;
                        } else {
                            if (strData.trim().equals("")) {
                                strData += RMDCommonConstants.SINGLE_QTE + recTypes.get(i)
                                        + RMDCommonConstants.SINGLE_QTE;
                            } else {
                                strData += RMDCommonConstants.COMMMA_SEPARATOR + RMDCommonConstants.SINGLE_QTE
                                        + recTypes.get(i) + RMDCommonConstants.SINGLE_QTE;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            FaultLogHelper.log("Error in getting Record Types :" + e);
            throw e;
        }
        return strData;
    }

    private String getQryPart(String anomInd, String column_Name, String ctrl_Cfg, String upperBound, String lowerBound,
            String datatooltipFlag, String screen_type, boolean oilFaultinnerQry) {
        if (ctrl_Cfg != null && ctrl_Cfg.trim().length() != 0) {
            if (!(("ac").equalsIgnoreCase(ctrl_Cfg)) && !(("dc").equalsIgnoreCase(ctrl_Cfg))) {
                ctrl_Cfg = "!ACDC";
            }
        } else {
            ctrl_Cfg = "!ACDC";
        }
        String inputKey;
        if (!"N".equalsIgnoreCase(anomInd)) {
            anomInd = "Y";
        }
        String qryPart = null;
        if ("Y".equalsIgnoreCase(anomInd)) {
            inputKey = anomInd + "*" + column_Name + (oilFaultinnerQry ? "~INNER" : "") + "*" + ctrl_Cfg + "*" + "-"
                    + "*" + "-" + "*"
                    + ((datatooltipFlag != null && ("Y").equalsIgnoreCase(datatooltipFlag)) ? "Y" : "!Y") + "*"
                    + screen_type;
            qryPart = getQueryPart(inputKey);
            if (qryPart == null || qryPart.trim().length() == 0) {
                inputKey = anomInd + "*" + column_Name + (oilFaultinnerQry ? "~INNER" : "") + "*" + ctrl_Cfg + "*"
                        + ((upperBound == null || upperBound.trim().length() == 0) ? "NULL" : "!NULL") + "*"
                        + ((lowerBound == null || lowerBound.trim().length() == 0) ? "NULL" : "!NULL") + "*"
                        + ((datatooltipFlag != null && ("Y").equalsIgnoreCase(datatooltipFlag)) ? "Y" : "!Y") + "*"
                        + screen_type;
                qryPart = getQueryPart(inputKey);
            }
        } else {
            inputKey = anomInd + "*" + column_Name + (oilFaultinnerQry ? "~INNER" : "") + "*" + ctrl_Cfg + "*"
                    + ((upperBound == null || upperBound.trim().length() == 0) ? "NULL" : "!NULL") + "*"
                    + ((lowerBound == null || lowerBound.trim().length() == 0) ? "NULL" : "!NULL") + "*"
                    + ((datatooltipFlag != null && ("Y").equalsIgnoreCase(datatooltipFlag)) ? "Y" : "!Y") + "*"
                    + screen_type;
            qryPart = getQueryPart(inputKey);
        }
        return qryPart;
    }

    private String getQueryPart(String inputKey) {
        Set<String> mapKeySet = cacheQryMap.keySet();
        String qryPart = null;
        for (String mapKey : mapKeySet) {
            if (isEqual(mapKey, inputKey)) {
                qryPart = cacheQryMap.get(mapKey);
                break;
            }
        }
        return qryPart;
    }

    private boolean isEqual(String mapKey, String inputKey) {
        String[] mapKeyArr = mapKey.split("\\*");
        String[] inputKeyArr = inputKey.split("\\*");
        boolean match = true;
        for (int i = 0; i < mapKeyArr.length; i++) {
            try {
                if (!mapKeyArr[i].trim().equals("-") && !mapKeyArr[i].trim().equals("-~INNER")
                        && !mapKeyArr[i].trim().equalsIgnoreCase(inputKeyArr[i].trim())) {
                    match = false;
                    break;
                }
            } catch (Exception e) {
                FaultLogHelper.log("Exception : " + e);
            }
        }
        return match;
    }

    private StringBuilder selectClause(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        cacheQryMap = objFaultLogCacheDAOIntf.getSelectQueryMap();
        Session session = null;
        List rsCase = null;
        List rsCaseAppend = null;
        Query pstmtCase = null;
        Query pstmtCaseAppend = null;
        String strCaseQuery = null;
        String strCustomer = null;
        String strHeaderNo = null;
        String strSerialNo = null;
        String strColumnName = null;
        String strAnomInd = null;
        String strDisplayName = null;
        String strDBColumn = null;

        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbBuildQuery = new StringBuilder();
        StringBuilder sbCaseAppendQuery = new StringBuilder();
        StringBuilder sbOilFaultInnerQuery = new StringBuilder();
        String strUpperBound = null;
        String strLowerBound = null;
        String strDataToolTipFlag = null;
        String strFormatData = null;
        String strFormattedColumn = null;

        try {
            strCustomer = objFaultRequestVO.getCustomer();
            strCustomer = strCustomer.trim();
            strHeaderNo = objFaultRequestVO.getVehicleHeader();
            strHeaderNo = strHeaderNo.trim();
            strSerialNo = objFaultRequestVO.getSerialNo();
            strSerialNo = strSerialNo.trim();

            session = getHibernateSession();

            if (objFaultRequestVO.getCaseId() != null && !objFaultRequestVO.getCaseId().trim().equals("")) {
                strCaseQuery = " SELECT OBJID, TO_CHAR(CREATION_TIME,'YYYYMMDDHH24MISS') CREATION_TIME FROM TABLE_CASE WHERE ID_NUMBER = ? ";
                pstmtCase = session.createSQLQuery(strCaseQuery.toString());
                pstmtCase.setString(0, objFaultRequestVO.getCaseId());
                pstmtCase.setFetchSize(10);
                rsCase = pstmtCase.list();
                if (RMDCommonUtility.isCollectionNotEmpty(rsCase)) {
                    int index = 0;
                    if (index < rsCase.size()) {
                        Object[] data = (Object[]) rsCase.get(index);
                        objFaultRequestVO.setCaseObjID(RMDCommonUtility.convertObjectToInt(data[0].toString()));
                        objFaultRequestVO
                                .setStrCreationTime(RMDCommonUtility.convertObjectToString(data[1].toString()));
                    }
                }
                if ((objFaultRequestVO.getCaseFrom() != null && (objFaultRequestVO.getCaseFrom().equals("APPENDED")
                        || objFaultRequestVO.getCaseFrom().equals(caseCreateToDT)))
                        || (objFaultRequestVO.getJdpadCbrCrit() != null
                                && (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_JDPAD)
                                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CBR)
                                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)))) {
                    sbCaseAppendQuery.append(
                            " SELECT * FROM (SELECT TO_CHAR (MAX (last_updated_date), 'YYYYMMDDHH24MISS') last_updated_date ");
                    sbCaseAppendQuery.append(" FROM gets_tools.gets_tool_run WHERE run2case = ?");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) upd, ");
                    sbCaseAppendQuery.append(" (SELECT MIN (run_processedmin_objid) min_objid ");
                    sbCaseAppendQuery.append(" FROM gets_tool_run WHERE run2case = ? ");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) min_objid, ");
                    sbCaseAppendQuery.append(" (SELECT MAX (run_processedmax_objid) max_objid ");
                    sbCaseAppendQuery.append(" FROM gets_tool_run WHERE run2case = ? ");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) max_objid ");

                    pstmtCaseAppend = session.createSQLQuery(sbCaseAppendQuery.toString());
                    pstmtCaseAppend.setInteger(0, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setInteger(1, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setInteger(2, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setFetchSize(10);
                    rsCaseAppend = pstmtCaseAppend.list();

                    if (RMDCommonUtility.isCollectionNotEmpty(rsCaseAppend)) {
                        int index = 0;
                        if (index < rsCaseAppend.size()) {
                            Object[] data = (Object[]) rsCaseAppend.get(index);
                            objFaultRequestVO.setStrMaxUpdatedDate(RMDCommonUtility.convertObjectToString(data[0]));
                            objFaultRequestVO.setStrMinObjid(RMDCommonUtility.convertObjectToString(data[1]));
                            objFaultRequestVO.setStrMaxObjid(RMDCommonUtility.convertObjectToString(data[2]));
                        }
                    }
                    if (objFaultRequestVO.getStrMinObjid() == null) {
                        objFaultRequestVO.setStrMinObjid("0");
                    }
                    if (objFaultRequestVO.getStrMaxObjid() == null) {
                        objFaultRequestVO.setStrMaxObjid("0");
                    }
                }
            }
            String viewName = objFaultRequestVO.getStrViewName();
            objFaultRequestVO.setStrViewName("");
            if (("VEHICLE").equals(objFaultRequestVO.getScreen()) || ("CASE-REM").equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(viewName);
            } else if ((equipNt).equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(viewName);
            } else if ((dPEab).equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(DPEAB_VIEW);
            } else if (("DHMS").equals(objFaultRequestVO.getScreen()) || ("CASE-DHMS").equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName("GETS_RMD.GETS_SD_FLT_DHMS_V");
            } else if ((rrVehicle).equals(objFaultRequestVO.getScreen()) || (rrEquipNt).equals(objFaultRequestVO.getScreen())) {
                if (STR_ACCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_ACCCA_V");
                } else if (STR_DCCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_DCCCA_V");
                } else if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_AC6000_V");
                } else {
                    throw new DataException(" NOT SUPPORTED FOR VIEWING RAPID RESPONSE DATA");
                }
            } else if (("INCIDENT").equals(objFaultRequestVO.getScreen())) {
                if (STR_ACCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_ACCCA_V");
                } else if (STR_DCCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_DCCCA_V");
                } else if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_AC6K_V");
                } else {
                    throw new DataException(" NOT SUPPORTED FOR VIEWING INCIDENT DATA");
                }
            }
            int intHeaders = 0;

            GetToolDsParminfoServiceVO objFaultHeaderDetailVO = null;
            intHeaders = arlHeaderDetails.size();
            for (int j = 0; j < intHeaders; j++) {
                objFaultHeaderDetailVO = (GetToolDsParminfoServiceVO) arlHeaderDetails.get(j);
                strColumnName = objFaultHeaderDetailVO.getColumnName().toUpperCase();
                
                if (("FAULTDESC").equalsIgnoreCase(strColumnName.trim())
                        && objFaultRequestVO.getScreen().indexOf("QNX") > -1) {
                    strColumnName = "FAULT_DESC";
                }
                strAnomInd = objFaultHeaderDetailVO.getAnomInd();
                strDisplayName = objFaultHeaderDetailVO.getParmName();

                strUpperBound = objFaultHeaderDetailVO.getUpperBound().toString();
                if (strUpperBound.equalsIgnoreCase("0.0")) {
                    strUpperBound = null;
                }
                strLowerBound = objFaultHeaderDetailVO.getLowerBound().toString();
                if (strLowerBound.equalsIgnoreCase("0.0")) {
                    strLowerBound = null;
                }
                strDataToolTipFlag = objFaultHeaderDetailVO.getDataTooltipFlag();
                strFormatData = objFaultHeaderDetailVO.getFormat();
                strDBColumn = objFaultHeaderDetailVO.getDBColumnName();

                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL") && null != strFormatData
                        && !"".equalsIgnoreCase(strFormatData)) {
                    strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$"))
                            + (strDBColumn == null ? strColumnName : strDBColumn)
                            + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                } else {
                    if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)
                            && !objFaultRequestVO.getStrViewName().trim().equals("")) {
                        strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$"))
                                + objFaultRequestVO.getStrViewName() + "." + strColumnName
                                + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                    } else if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)
                            && objFaultRequestVO.getStrViewName().trim().equals("")) {
                        strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$")) + strColumnName
                                + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                    }
                }
                String s = getQryPart(strAnomInd, formatString(strColumnName), strControllerCfg, strUpperBound,
                        strLowerBound, strDataToolTipFlag, objFaultRequestVO.getScreen(), false);
                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL") && s == null) {
                    s = "<<DBColumn>>, ";
                }
                s = s.replaceAll("@", objFaultRequestVO.getStrViewName().equals("") ? "null" : objFaultRequestVO.getStrViewName());
                if (strColumnName != null && strColumnName.indexOf("#") > -1) {
                    String columnNameTemp = strColumnName.replaceAll("#", "~");
                    s = s.replaceAll("#", formatString(columnNameTemp) == null ? "null" : formatString(columnNameTemp));
                    s = s.replaceAll("~", "#");
                } else {
                    s = s.replaceAll("#", formatString(strColumnName) == null ? "null" : formatString(strColumnName));
                }
                
                s = s.replaceAll("<<lowerBound>>", strLowerBound == null ? "null" : strLowerBound);
                s = s.replaceAll("<<upperBound>>", strUpperBound == null ? "null" : strUpperBound);
                s = s.replaceAll("<<formattedColumn>>", strFormattedColumn == null ? "null" : strFormattedColumn);
                s = s.replaceAll("<<caseId>>", String.valueOf(objFaultRequestVO.getCaseObjID()));
                s = s.replaceAll("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
                s = s.replaceAll("<<displayName>>", strDisplayName);
                if (strDBColumn != null) {
                    s = s.replaceAll("<<DBColumn>>", strDBColumn);
                }
                if("".equals(objFaultRequestVO.getStrViewName()) && "OCCUR_DATE_CUSTOM".equals(strColumnName)){
                	s = s.replaceFirst("OCCUR_DATE_CUSTOM", "OCCUR_DATE");
                }
                if("".equals(objFaultRequestVO.getStrViewName()) && "FAULT_RESET_DATE_CUSTOM".equals(strColumnName)){
                	s = s.replaceFirst("FAULT_RESET_DATE_CUSTOM", "FAULT_RESET_DATE");
                }
                sbSelectQuery.append(s);

                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL")) {
                    s = null;
                    s = getQryPart(strAnomInd, formatString(strColumnName), strControllerCfg, strUpperBound,
                            strLowerBound, strDataToolTipFlag, objFaultRequestVO.getScreen(), true);
                    if (s == null) {
                        s = "<<formattedColumn>>||(DECODE (colname,'#', '^' ||tooltip,NULL)) <<DBColumn>>, ";
                    }
                    s = s.replaceAll("#", formatString(strColumnName) == null ? "null" : formatString(strColumnName));
                    s = s.replaceAll("<<formattedColumn>>", strFormattedColumn == null ? "null" : strFormattedColumn);
                    s = s.replaceAll("<<caseId>>", String.valueOf(objFaultRequestVO.getCaseObjID()));
                    s = s.replaceAll("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
                    s = s.replaceAll("<<displayName>>", strDisplayName);
                    if (strDBColumn != null) {
                        s = s.replaceAll("<<DBColumn>>", strDBColumn);
                    }
                    sbOilFaultInnerQuery.append(s);
                }
            }
            ArrayList<HashMap<String, String>> commonSeqList = objFaultLogCacheDAOIntf.getCommonQueryMap()
                    .get(objFaultRequestVO.getScreen() + "-SELECT");
            if (commonSeqList != null && !commonSeqList.isEmpty()) {
                for (int i = 0; i < commonSeqList.size(); i++) {
                    HashMap<String, String> commonSelQryMap = commonSeqList.get(i);
                    String s = "";
                    if (commonSelQryMap.get(strControllerCfg + "*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonSelQryMap.get(strControllerCfg + "*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonSelQryMap.get("-*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonSelQryMap.get("-*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonSelQryMap.get(strControllerCfg + "*-") != null) {
                        s = commonSelQryMap.get(strControllerCfg + "*-");
                    } else if (commonSelQryMap.get("-*-") != null) {
                        s = commonSelQryMap.get("-*-");
                    }
                    s = s.replaceAll("@",
                            objFaultRequestVO.getStrViewName() == null ? "null" : objFaultRequestVO.getStrViewName());
                    if (s.trim().equals("<<CompleteQuery>>")) {
                        sbBuildQuery.append(sbSelectQuery.toString());
                    } else if (s.trim().equals("<<OilFaultInnerQuery>>")) {
                        sbBuildQuery.append(sbOilFaultInnerQuery.toString());
                    } else {
                        sbBuildQuery.append(s);
                    }
                }
            }
            if (objFaultRequestVO.getHideL3() != null && objFaultRequestVO.getHideL3().trim().equals(STR_Y)) {
                sbBuildQuery.append(" ,gets_rmd_flt_strategy fltstgy, GETS_RMD_FAULT_CODES fltcd ");
            }
            if (objFaultRequestVO.getJdpadCbrCrit() != null && !objFaultRequestVO.getJdpadCbrCrit().trim().equals("")) {
                if (STR_JDPAD.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())) {
                    sbBuildQuery.append(
                            " ,gets_rmd.gets_tool_ar_list gtar, table_case tc, gets_rmd.gets_tool_rprldwn gtrl ");
                    sbBuildQuery.append(
                            " ,gets_rmd.gets_tool_dpd_ruledef gtdr, gets_rmd.gets_tool_dpd_finrul gtdf, gets_rmd.gets_tool_fltdown gtfd ");
                    if (objFaultRequestVO.getScreen().equals("CASE-DHMS")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbBuildQuery.append(",gets_rmd.gets_tool_fault gtft ");
                    }
                } else if (STR_CBR.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())
                        || STR_CRITICAL_FAULT.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())) {
                    sbBuildQuery.append(" , gets_tools.gets_tool_case_mtm_fault mtm ");
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
            }
        }
        String str = sbBuildQuery.toString();
        if (str.indexOf("$$") > -1) {
            str = str.replace("$$", objFaultRequestVO.getUserTimeZone());
        }
        str = str.replace("@",
                objFaultRequestVO.getStrViewName() == null ? "null" : objFaultRequestVO.getStrViewName());
        str = str.replace("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
        if (str.indexOf("<<dbLink>>") > -1) {
            str = str.replaceAll("<<dbLink>>", "@");
        }
        return new StringBuilder(str);
    }

    private StringBuilder whereClause(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        StringBuilder sbWhereQuery = new StringBuilder();
        try {
            ArrayList<HashMap<String, String>> commonWhereList = objFaultLogCacheDAOIntf.getCommonQueryMap()
                    .get(objFaultRequestVO.getScreen() + "-WHERE");
            if (commonWhereList != null && !commonWhereList.isEmpty()) {
                for (int i = 0; i < commonWhereList.size(); i++) {
                    HashMap<String, String> commonWhereQryMap = commonWhereList.get(i);
                    String s = "";
                    String caseFrom = objFaultRequestVO.getCaseFrom();
                    if (caseFrom == null) {
                        caseFrom = "";
                    }
                    if (commonWhereQryMap
                            .get(objFaultRequestVO.getCtrlCfg() + "*" + strViewName + "*" + caseFrom) != null) {
                        s = commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*" + strViewName + "*" + caseFrom);
                    } else if (commonWhereQryMap
                            .get(objFaultRequestVO.getCtrlCfg() + "*" + strViewName + "*-") != null) {
                        s = commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*" + strViewName + "*-");
                    } else if (commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-*" + caseFrom) != null) {
                        s = commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-*" + caseFrom);
                    } else if (commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-*-") != null) {
                        s = commonWhereQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-*-");
                    } else if (commonWhereQryMap.get("-*" + strViewName + "*" + caseFrom) != null) {
                        s = commonWhereQryMap.get("-*" + strViewName + "*" + caseFrom);
                    } else if (commonWhereQryMap.get("-*" + strViewName + "*-") != null) {
                        s = commonWhereQryMap.get("-*" + strViewName + "*-");
                    } else if (commonWhereQryMap.get("-*-*" + caseFrom) != null) {
                        s = commonWhereQryMap.get("-*-*" + caseFrom);
                    } else if (commonWhereQryMap.get("-*-*-") != null) {
                        s = commonWhereQryMap.get("-*-*-");
                    }
                    if (s.trim().equals("") && caseFrom.trim().equals("APPENDED")
                            && objFaultRequestVO.getStrMaxUpdatedDate() != null
                            && commonWhereQryMap.get("-*-*APPENDEDMAXDT") != null) {
                        s = commonWhereQryMap.get("-*-*APPENDEDMAXDT");
                    } else if (s.trim().equals("") && caseFrom.trim().equals("APPENDED")
                            && objFaultRequestVO.getStrMaxUpdatedDate() == null
                            && commonWhereQryMap.get("-*-*APPENDEDNOMAXDT") != null) {
                        s = commonWhereQryMap.get("-*-*APPENDEDNOMAXDT");
                    }
                    s = s.replaceAll("@", strViewName == null ? "null" : strViewName);
                    s = s.replaceAll("<<SortOrder>>", objFaultRequestVO.getSortOrder());
                    sbWhereQuery.append(s);
                }
            }
            String strInitialLoad = objFaultRequestVO.getInitialLoad();
            String strCaseType = objFaultRequestVO.getCaseType();
            String strSortColumn = objFaultRequestVO.getSortOrder();
            String formattedViewName = "";
            if (strViewName != null && !strViewName.trim().equals("")) {
                formattedViewName = strViewName + ".";
            }
            if (objFaultRequestVO.getCaseFrom() != null && !objFaultRequestVO.getCaseFrom().trim().equals("")
                    && objFaultRequestVO.getJdpadCbrCrit() == null) {
                if (STR_Y.equalsIgnoreCase(strInitialLoad)) {
                    if (STR_ESTP_PROBLEM.equalsIgnoreCase(strCaseType)) {
                        sbWhereQuery.append(
                                " AND to_char(" + formattedViewName + "offboard_load_date+1, 'YYYYMMDDHH24MISS') >= '"
                                        + objFaultRequestVO.getStrCreationTime() + "'");
                    } else if (STR_EOA_PROBLEM.equalsIgnoreCase(strCaseType)) {
                        sbWhereQuery.append(
                                " AND to_char( " + formattedViewName + "offboard_load_date+7,'YYYYMMDDHH24MISS') >= '"
                                        + objFaultRequestVO.getStrCreationTime() + "'");
                    }
                }
            } else if (objFaultRequestVO.getNoOfDays() != null && !objFaultRequestVO.getNoOfDays().trim().equals("")
                    && objFaultRequestVO.getJdpadCbrCrit() == null) {
                sbWhereQuery.append(
                        " ABS(SYSDATE+ " + STR_GMT + "- " + formattedViewName + strSortColumn + " ) <= ? + " + STR_GMT);
            } else if (objFaultRequestVO.getVvfStartDate() != null
                    && !objFaultRequestVO.getVvfStartDate().trim().equals("")
                    && objFaultRequestVO.getVvfEndDate() != null && !objFaultRequestVO.getVvfEndDate().trim().equals("")
                    && objFaultRequestVO.getJdpadCbrCrit() == null) {
                sbWhereQuery.append(formattedViewName + strSortColumn
                        + " BETWEEN to_date(? ,'MM/DD/YYYY HH24:MI:SS') AND to_date(? ,'MM/DD/YYYY HH24:MI:SS') ");
            }
            if (objFaultRequestVO.getJdpadCbrCrit() != null) {
                if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CBR)
                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)) {
                    if (objFaultRequestVO.getScreen().equals("CASE-DHMS")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbWhereQuery.append(" AND " + formattedViewName + "fault_objid BETWEEN ? AND ? ");
                    } else {
                        sbWhereQuery.append(" AND flt.objid BETWEEN ? AND ? ");
                    }
                    if (objFaultRequestVO.getJdpadCbrCrit() != null
                            && objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)) {
                        sbWhereQuery.append(" AND fault_classification = 'Critical Fault' AND " + formattedViewName
                                + "fault2rule_defn IS NULL ");
                    }
                    if (objFaultRequestVO.getScreen().equals("CASE-DHMS")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbWhereQuery.append(" AND " + formattedViewName + "fault_objid = mtm.mtm2fault(+) ");
                    } else {
                        sbWhereQuery.append(" AND flt.objid = mtm.mtm2fault(+) ");
                    }
                    sbWhereQuery.append(" AND (mtm.mtm2case = ? OR mtm.mtm2case IS NULL) ");
                } else if (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_JDPAD)) {
                    if (objFaultRequestVO.getScreen().equals("CASE-QNX")
                            || objFaultRequestVO.getScreen().equals("CASE-OIL")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbWhereQuery.append(" AND ");
                    }
                    sbWhereQuery.append(" tc.id_number = ? AND gtar.ar_list2case = tc.objid ");
                    sbWhereQuery.append(" AND gtrl.rprldwn2ar_list = gtar.objid ");
                    sbWhereQuery
                            .append(" AND gtdr.objid = gtrl.rprldwn2rule_defn AND gtdf.objid = gtdr.ruledef2finrul ");
                    sbWhereQuery.append(" AND gtfd.fltdown2rule_defn = gtdr.objid ");
                    if (objFaultRequestVO.getScreen().equals("CASE-QNX")
                            || objFaultRequestVO.getScreen().equals("CASE-OIL")) {
                        sbWhereQuery.append(" AND flt.objid = gtfd.fltdown2fault ");
                    } else {
                        sbWhereQuery
                                .append(" AND gtft.objid = gtfd.fltdown2fault AND gtft.objid = gtfd.fltdown2fault ");
                    }
                    if (objFaultRequestVO.getScreen().equals("CASE-DHMS")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbWhereQuery.append(" AND " + formattedViewName + "fault_objid = gtft.objid ");
                        sbWhereQuery.append(" AND " + formattedViewName + "fault_objid BETWEEN ? AND ?");
                    } else {
                        sbWhereQuery.append(" AND flt.objid BETWEEN ? AND ?");
                    }
                    if (null != objFaultRequestVO.getRuleDefID() && !"".equals(objFaultRequestVO.getRuleDefID())) {
                        sbWhereQuery.append(" AND fltdown2rule_defn = ?");
                    }
                }
            }
            if (objFaultRequestVO.getNotch() != null && objFaultRequestVO.getNotch().equals("8")
                    && objFaultRequestVO.getCaseType() != null
                    && objFaultRequestVO.getCaseType().indexOf("DHMS") == -1) {
                if (objFaultRequestVO.getCaseType() != null && (objFaultRequestVO.getCaseType().indexOf("QNX") > -1
                        || objFaultRequestVO.getCaseType().indexOf("Oil") > -1)) {
                    sbWhereQuery.append(" AND " + formattedViewName + "notch = '8' ");
                } else if (objFaultRequestVO.getCtrlCfg() != null
                        && (objFaultRequestVO.getCtrlCfg().equalsIgnoreCase("AC")
                                || ("DC").equalsIgnoreCase(objFaultRequestVO.getCtrlCfg()))) {
                    sbWhereQuery.append(" AND " + formattedViewName + "notch = '8' ");
                } else {
                    sbWhereQuery.append(" AND " + formattedViewName + "basic_notch = '8' ");
                }
            }
            if (objFaultRequestVO.getHideL3() != null && objFaultRequestVO.getHideL3().trim().equals(STR_Y)) {
                sbWhereQuery.append(" AND " + strViewName + ".fault_strategy_objid =  FLTSTGY.objid ");
                sbWhereQuery.append(" AND fltcd.OBJID = fltstgy.FLT_STRAT2FLT_CODE AND " + strViewName
                        + ".FAULT_CODE = fltcd.FAULT_CODE AND DIAGNOSTIC_WEIGHT != 3 ");
            }
            String strTitle = null;
            if (("VEHICLE").equals(objFaultRequestVO.getScreen())) {
                strTitle = STR_VVF_FAULTS_FOR_LOCO;
            } else if ((equipNt).equals(objFaultRequestVO.getScreen())) {
                strTitle = STR_VVF_FAULTS_FOR_RMD;
            } else if (objFaultRequestVO.getScreen().indexOf("QNX") > -1) {
                strTitle = STR_VVF_FAULTS_FOR_QNX;
            } else if ((rrVehicle).equals(objFaultRequestVO.getScreen())) {
                strTitle = STR_RR_FAULTS_FOR_LOCO;
            } else if ((rrEquipNt).equals(objFaultRequestVO.getScreen())) {
                strTitle = STR_RR_RMD_EQUIPMENT;
            } else if (("DHMS").equals(objFaultRequestVO.getScreen())) {
                strTitle = STR_FAULTS_DHMS;
            }
            String strRecordTypes = null;
            if ((dPEab).equals(objFaultRequestVO.getScreen())) {
                strRecordTypes = "'" + RMDCommonConstants.DP + "', '" + RMDCommonConstants.EAB + "'";
            } else if (objFaultRequestVO.getScreen().equals("CASE-REM")
                    || objFaultRequestVO.getScreen().equals("CASE-QNX")
                    || objFaultRequestVO.getScreen().equals("CASE-DHMS")) {
                strRecordTypes = getCaseRecordTypes(strCaseType, objFaultRequestVO.getHC());
            } else if (!(("INCIDENT").equals(objFaultRequestVO.getScreen()))
                    && !(("CASE-OIL").equals(objFaultRequestVO.getScreen()))) {
                strRecordTypes = getRecordTypes(strTitle);
            }
            if (objFaultRequestVO.getHC() != null && ("YES").equals(objFaultRequestVO.getHC().trim())
                    && objFaultRequestVO.getScreen().indexOf("CASE") == -1) {
                strRecordTypes += ", 'HC'";
            }
            if (strRecordTypes != null && !strRecordTypes.trim().equals("")) {
                sbWhereQuery.append(" AND " + formattedViewName);
                sbWhereQuery.append("record_type in ( ");
                sbWhereQuery.append(strRecordTypes);
                sbWhereQuery.append(") ");
            }
        } catch (Exception e) {
            FaultLogHelper.log("Error in building the where clause : " + e);
            throw e;
        }
        return sbWhereQuery;
    }

    private StringBuilder orderClause(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        StringBuilder sbOrderQuery = new StringBuilder();
        String strSortOrder = objFaultRequestVO.getSortOrder();
        try {
            ArrayList<HashMap<String, String>> commonOrderList = objFaultLogCacheDAOIntf.getCommonQueryMap()
                    .get(objFaultRequestVO.getScreen() + "-ORDERBY");
            if (commonOrderList != null && !commonOrderList.isEmpty()) {
                for (int i = 0; i < commonOrderList.size(); i++) {
                    HashMap<String, String> commonOrderQryMap = commonOrderList.get(i);
                    String s = "";
                    if (commonOrderQryMap
                            .get(objFaultRequestVO.getCtrlCfg() + "*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonOrderQryMap
                                .get(objFaultRequestVO.getCtrlCfg() + "*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonOrderQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-") != null) {
                        s = commonOrderQryMap.get(objFaultRequestVO.getCtrlCfg() + "*-");
                    } else if (commonOrderQryMap.get("-*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonOrderQryMap.get("-*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonOrderQryMap.get("-*-") != null) {
                        s = commonOrderQryMap.get("-*-");
                    }
                    s = s.replaceAll("@", strViewName == null ? "null" : strViewName);
                    s = s.replaceAll("<<SortOrder>>", strSortOrder);
                    sbOrderQuery.append(s);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.log("Exception in building the order clause : " + e);
            throw e;
        }
        return sbOrderQuery;
    }
    
    
    private StringBuilder selectClauseForMobile(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        cacheQryMap = objFaultLogCacheDAOIntf.getSelectQueryMap();
        Session session = null;
        List rsCase = null;
        List rsCaseAppend = null;
        Query pstmtCase = null;
        Query pstmtCaseAppend = null;
        String strCaseQuery = null;
        String strCustomer = null;
        String strHeaderNo = null;
        String strSerialNo = null;
        String strColumnName = null;
        String strAnomInd = null;
        String strDisplayName = null;
        String strDBColumn = null;

        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbBuildQuery = new StringBuilder();
        StringBuilder sbCaseAppendQuery = new StringBuilder();
        StringBuilder sbOilFaultInnerQuery = new StringBuilder();
        String strUpperBound = null;
        String strLowerBound = null;
        String strDataToolTipFlag = null;
        String strFormatData = null;
        String strFormattedColumn = null;

        try {
            strCustomer = objFaultRequestVO.getCustomer();
            strCustomer = strCustomer.trim();
            strHeaderNo = objFaultRequestVO.getVehicleHeader();
            strHeaderNo = strHeaderNo.trim();
            strSerialNo = objFaultRequestVO.getSerialNo();
            strSerialNo = strSerialNo.trim();

            session = getHibernateSession();

            if (objFaultRequestVO.getCaseId() != null && !objFaultRequestVO.getCaseId().trim().equals("")) {
                strCaseQuery = " SELECT OBJID, TO_CHAR(CREATION_TIME,'YYYYMMDDHH24MISS') CREATION_TIME FROM TABLE_CASE WHERE ID_NUMBER = ? ";
                pstmtCase = session.createSQLQuery(strCaseQuery.toString());
                pstmtCase.setString(0, objFaultRequestVO.getCaseId());
                pstmtCase.setFetchSize(10);
                rsCase = pstmtCase.list();
                if (RMDCommonUtility.isCollectionNotEmpty(rsCase)) {
                    int index = 0;
                    if (index < rsCase.size()) {
                        Object[] data = (Object[]) rsCase.get(index);
                        objFaultRequestVO.setCaseObjID(RMDCommonUtility.convertObjectToInt(data[0].toString()));
                        objFaultRequestVO
                                .setStrCreationTime(RMDCommonUtility.convertObjectToString(data[1].toString()));
                    }
                }
                if ((objFaultRequestVO.getCaseFrom() != null && (objFaultRequestVO.getCaseFrom().equals("APPENDED")
                        || objFaultRequestVO.getCaseFrom().equals(caseCreateToDT)))
                        || (objFaultRequestVO.getJdpadCbrCrit() != null
                                && (objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_JDPAD)
                                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CBR)
                                        || objFaultRequestVO.getJdpadCbrCrit().trim().equals(STR_CRITICAL_FAULT)))) {
                    sbCaseAppendQuery.append(
                            " SELECT * FROM (SELECT TO_CHAR (MAX (last_updated_date), 'YYYYMMDDHH24MISS') last_updated_date ");
                    sbCaseAppendQuery.append(" FROM gets_tools.gets_tool_run WHERE run2case = ?");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) upd, ");
                    sbCaseAppendQuery.append(" (SELECT MIN (run_processedmin_objid) min_objid ");
                    sbCaseAppendQuery.append(" FROM gets_tool_run WHERE run2case = ? ");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) min_objid, ");
                    sbCaseAppendQuery.append(" (SELECT MAX (run_processedmax_objid) max_objid ");
                    sbCaseAppendQuery.append(" FROM gets_tool_run WHERE run2case = ? ");
                    sbCaseAppendQuery.append(" AND run_cpt IS NOT NULL) max_objid ");

                    pstmtCaseAppend = session.createSQLQuery(sbCaseAppendQuery.toString());
                    pstmtCaseAppend.setInteger(0, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setInteger(1, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setInteger(2, objFaultRequestVO.getCaseObjID());
                    pstmtCaseAppend.setFetchSize(10);
                    rsCaseAppend = pstmtCaseAppend.list();

                    if (RMDCommonUtility.isCollectionNotEmpty(rsCaseAppend)) {
                        int index = 0;
                        if (index < rsCaseAppend.size()) {
                            Object[] data = (Object[]) rsCaseAppend.get(index);
                            objFaultRequestVO.setStrMaxUpdatedDate(RMDCommonUtility.convertObjectToString(data[0]));
                            objFaultRequestVO.setStrMinObjid(RMDCommonUtility.convertObjectToString(data[1]));
                            objFaultRequestVO.setStrMaxObjid(RMDCommonUtility.convertObjectToString(data[2]));
                        }
                    }
                    if (objFaultRequestVO.getStrMinObjid() == null) {
                        objFaultRequestVO.setStrMinObjid("0");
                    }
                    if (objFaultRequestVO.getStrMaxObjid() == null) {
                        objFaultRequestVO.setStrMaxObjid("0");
                    }
                }
            }
            String viewName = objFaultRequestVO.getStrViewName();
            objFaultRequestVO.setStrViewName("");
            if (("VEHICLE").equals(objFaultRequestVO.getScreen())
                    || ("CASE-REM").equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(viewName);
            } else if ((equipNt).equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(viewName);
            } else if ((dPEab).equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName(DPEAB_VIEW);
            } else if (("DHMS").equals(objFaultRequestVO.getScreen())
                    || ("CASE-DHMS").equals(objFaultRequestVO.getScreen())) {
                objFaultRequestVO.setStrViewName("GETS_RMD.GETS_SD_FLT_DHMS_V");
            } else if ((rrVehicle).equals(objFaultRequestVO.getScreen())
                    || (rrEquipNt).equals(objFaultRequestVO.getScreen())) {
                if (STR_ACCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_ACCCA_V");
                } else if (STR_DCCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_DCCCA_V");
                } else if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_SD_FLT_TIER2_AC6000_V");
                } else {
                    throw new DataException(" NOT SUPPORTED FOR VIEWING RAPID RESPONSE DATA");
                }
            } else if (("INCIDENT").equals(objFaultRequestVO.getScreen())) {
                if (STR_ACCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_ACCCA_V");
                } else if (STR_DCCCA.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_DCCCA_V");
                } else if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                    objFaultRequestVO.setStrViewName("GETS_DW_EOA_TIER2_AC6K_V");
                } else {
                    throw new DataException(" NOT SUPPORTED FOR VIEWING INCIDENT DATA");
                }
            }
            int intHeaders = 0;

            GetMobileToolDsParminfoServiceVO objFaultHeaderDetailVO = null;
            intHeaders = arlHeaderDetails.size();
            for (int j = 0; j < intHeaders; j++) {
                objFaultHeaderDetailVO = (GetMobileToolDsParminfoServiceVO) arlHeaderDetails.get(j);
                strColumnName = objFaultHeaderDetailVO.getColumnName().toUpperCase();
                if (("FAULTDESC").equalsIgnoreCase(strColumnName.trim())
                        && objFaultRequestVO.getScreen().indexOf("QNX") > -1) {
                    strColumnName = "FAULT_DESC";
                }
                strUpperBound = objFaultHeaderDetailVO.getUpperBound().toString();
                if (strUpperBound.equalsIgnoreCase("0.0")) {
                    strUpperBound = null;
                }
                strLowerBound = objFaultHeaderDetailVO.getLowerBound().toString();
                if (strLowerBound.equalsIgnoreCase("0.0")) {
                    strLowerBound = null;
                }
                strDataToolTipFlag = objFaultHeaderDetailVO.getDataTooltipFlag();
                strFormatData = objFaultHeaderDetailVO.getFormat();
                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL") && null != strFormatData
                        && !"".equalsIgnoreCase(strFormatData)) {
                    strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$"))
                            + (strDBColumn == null ? strColumnName : strDBColumn)
                            + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                } else {
                    if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)
                            && !objFaultRequestVO.getStrViewName().trim().equals("")) {
                        strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$"))
                                + objFaultRequestVO.getStrViewName() + "." + strColumnName
                                + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                    } else if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)
                            && objFaultRequestVO.getStrViewName().trim().equals("")) {
                        strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$")) + strColumnName
                                + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                    }
                }
                String s = getQryPart(strAnomInd, formatString(strColumnName), strControllerCfg, strUpperBound,
                        strLowerBound, strDataToolTipFlag, objFaultRequestVO.getScreen(), false);
                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL") && s == null) {
                    s = "<<DBColumn>>, ";
                }
                s = s.replaceAll("@",
                        objFaultRequestVO.getStrViewName().equals("") ? "null" : objFaultRequestVO.getStrViewName());
                if (strColumnName != null && strColumnName.indexOf("#") > -1) {
                    String columnNameTemp = strColumnName.replaceAll("#", "~");
                    s = s.replaceAll("#", formatString(columnNameTemp) == null ? "null" : formatString(columnNameTemp));
                    s = s.replaceAll("~", "#");
                } else {
                    s = s.replaceAll("#", formatString(strColumnName) == null ? "null" : formatString(strColumnName));
                }
                s = s.replaceAll("<<lowerBound>>", strLowerBound == null ? "null" : strLowerBound);
                s = s.replaceAll("<<upperBound>>", strUpperBound == null ? "null" : strUpperBound);
                s = s.replaceAll("<<formattedColumn>>", strFormattedColumn == null ? "null" : strFormattedColumn);
                s = s.replaceAll("<<caseId>>", String.valueOf(objFaultRequestVO.getCaseObjID()));
                s = s.replaceAll("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
                s = s.replaceAll("<<displayName>>", strDisplayName);
                if (strDBColumn != null) {
                    s = s.replaceAll("<<DBColumn>>", strDBColumn);
                }
                sbSelectQuery.append(s);

                if (objFaultRequestVO.getScreen().trim().equals("CASE-OIL")) {
                    s = null;
                    s = getQryPart(strAnomInd, formatString(strColumnName), strControllerCfg, strUpperBound,
                            strLowerBound, strDataToolTipFlag, objFaultRequestVO.getScreen(), true);
                    if (s == null) {
                        s = "<<formattedColumn>>||(DECODE (colname,'#', '^' ||tooltip,NULL)) <<DBColumn>>, ";
                    }
                    s = s.replaceAll("#", formatString(strColumnName) == null ? "null" : formatString(strColumnName));
                    s = s.replaceAll("<<formattedColumn>>", strFormattedColumn == null ? "null" : strFormattedColumn);
                    s = s.replaceAll("<<caseId>>", String.valueOf(objFaultRequestVO.getCaseObjID()));
                    s = s.replaceAll("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
                    s = s.replaceAll("<<displayName>>", strDisplayName);
                    if (strDBColumn != null) {
                        s = s.replaceAll("<<DBColumn>>", strDBColumn);
                    }
                    sbOilFaultInnerQuery.append(s);
                }
            }
            ArrayList<HashMap<String, String>> commonSeqList = objFaultLogCacheDAOIntf.getCommonQueryMap()
                    .get(objFaultRequestVO.getScreen() + "-SELECT");
            if (commonSeqList != null && !commonSeqList.isEmpty()) {
                for (int i = 0; i < commonSeqList.size(); i++) {
                    HashMap<String, String> commonSelQryMap = commonSeqList.get(i);
                    String s = "";
                    if (commonSelQryMap.get(strControllerCfg + "*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonSelQryMap.get(strControllerCfg + "*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonSelQryMap.get("-*" + objFaultRequestVO.getCaseFrom()) != null) {
                        s = commonSelQryMap.get("-*" + objFaultRequestVO.getCaseFrom());
                    } else if (commonSelQryMap.get(strControllerCfg + "*-") != null) {
                        s = commonSelQryMap.get(strControllerCfg + "*-");
                    } else if (commonSelQryMap.get("-*-") != null) {
                        s = commonSelQryMap.get("-*-");
                    }
                    s = s.replaceAll("@",
                            objFaultRequestVO.getStrViewName() == null ? "null" : objFaultRequestVO.getStrViewName());
                    if (s.trim().equals("<<CompleteQuery>>")) {
                        sbBuildQuery.append(sbSelectQuery.toString());
                    } else if (s.trim().equals("<<OilFaultInnerQuery>>")) {
                        sbBuildQuery.append(sbOilFaultInnerQuery.toString());
                    } else {
                        sbBuildQuery.append(s);
                    }
                }
            }
            if (objFaultRequestVO.getHideL3() != null && objFaultRequestVO.getHideL3().trim().equals(STR_Y)) {
                sbBuildQuery.append(" ,gets_rmd_flt_strategy fltstgy, GETS_RMD_FAULT_CODES fltcd ");
            }
            if (objFaultRequestVO.getJdpadCbrCrit() != null && !objFaultRequestVO.getJdpadCbrCrit().trim().equals("")) {
                if (STR_JDPAD.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())) {
                    sbBuildQuery.append(
                            " ,gets_rmd.gets_tool_ar_list gtar, table_case tc, gets_rmd.gets_tool_rprldwn gtrl ");
                    sbBuildQuery.append(
                            " ,gets_rmd.gets_tool_dpd_ruledef gtdr, gets_rmd.gets_tool_dpd_finrul gtdf, gets_rmd.gets_tool_fltdown gtfd ");
                    if (objFaultRequestVO.getScreen().equals("CASE-DHMS")
                            || objFaultRequestVO.getScreen().equals("CASE-REM")) {
                        sbBuildQuery.append(",gets_rmd.gets_tool_fault gtft ");
                    }
                } else if (STR_CBR.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())
                        || STR_CRITICAL_FAULT.equalsIgnoreCase(objFaultRequestVO.getJdpadCbrCrit())) {
                    sbBuildQuery.append(" , gets_tools.gets_tool_case_mtm_fault mtm ");
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
            }
        }
        String str = sbBuildQuery.toString();
        str = str.replace("@",
                objFaultRequestVO.getStrViewName() == null ? "null" : objFaultRequestVO.getStrViewName());
        str = str.replace("<<vehicleId>>", String.valueOf(objFaultRequestVO.getVehicleID()));
        if (str.indexOf("<<dbLink>>") > -1) {
            str = str.replaceAll("<<dbLink>>", "@");
        }
        return new StringBuilder(str);
    }
}
