package com.ge.trans.eoa.services.cases.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.cases.dao.intf.FindCaseLiteEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import org.springframework.beans.factory.annotation.Autowired;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;
import com.ge.trans.eoa.services.common.dao.intf.CachedEoaDAOIntf;
import com.ge.trans.rmd.utilities.AppSecUtil;

public class FindCaseLiteEoaDAOImpl extends RMDCommonDAO implements FindCaseLiteEoaDAOIntf {

    private static final long serialVersionUID = 1L;
    @Autowired
    private CachedEoaDAOIntf cachedEoaDAOIntf;

    public CachedEoaDAOIntf getCachedEoaDAOIntf() {
        return cachedEoaDAOIntf;
    }

    public void setCachedEoaDAOIntf(CachedEoaDAOIntf cachedEoaDAOIntf) {
        this.cachedEoaDAOIntf = cachedEoaDAOIntf;
    }

    public FindCaseLiteEoaDAOImpl() {
        super();
    }

    public static final RMDLogger LOG = RMDLogger.getLogger(FindCaseLiteEoaDAOImpl.class);

    @Override
    public List<SelectCaseHomeVO> getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException {

        List<SelectCaseHomeVO> delCaseList = new ArrayList<SelectCaseHomeVO>();
        List<RecomDelvServiceVO> rxDelServiceVOList = new ArrayList<RecomDelvServiceVO>();

        List<RecomDelvServiceVO> tmpDelServiceLst = null;
        SelectCaseHomeVO objDelCase, tmpObjDelCase;
        RecomDelvServiceVO rxDelServiceVO;
        Date rXCloseDate = null;
        List delQueryResults = null;
        Session session = null;

        List<String> caseIdList = new ArrayList<String>();
        Map<String, SelectCaseHomeVO> delCaseMap = new LinkedHashMap<String, SelectCaseHomeVO>();

        Map urgRepMap = new HashMap();
        Map caseTypeMap = new HashMap();
        Map modelMap = new HashMap();
        Map fleetMap = new HashMap();
        Map rxTitleMap = new HashMap();
        if (findCaseSerVO.getUrgency() != null && findCaseSerVO.getUrgency().length > 0) {
            for (int i = 0; i < findCaseSerVO.getUrgency().length; i++) {
                urgRepMap.put(findCaseSerVO.getUrgency()[i], findCaseSerVO.getUrgency()[i]);
            }
        }
        if (findCaseSerVO.getCaseType() != null && findCaseSerVO.getCaseType().length > 0) {
            for (int i = 0; i < findCaseSerVO.getCaseType().length; i++) {
                caseTypeMap.put(findCaseSerVO.getCaseType()[i], findCaseSerVO.getCaseType()[i]);
            }
        }
        if (findCaseSerVO.getModelId() != null && findCaseSerVO.getModelId().length > 0) {
            for (int i = 0; i < findCaseSerVO.getModelId().length; i++) {
                modelMap.put(findCaseSerVO.getModelId()[i], findCaseSerVO.getModelId()[i]);
            }
        }
        if (findCaseSerVO.getFleetId() != null && findCaseSerVO.getFleetId().length > 0) {
            for (int i = 0; i < findCaseSerVO.getFleetId().length; i++) {
                fleetMap.put(findCaseSerVO.getFleetId()[i], findCaseSerVO.getFleetId()[i]);
            }
        }
        if (findCaseSerVO.getRxIds() != null && findCaseSerVO.getRxIds().length > 0) {
            for (int i = 0; i < findCaseSerVO.getRxIds().length; i++) {
                rxTitleMap.put(findCaseSerVO.getRxIds()[i], findCaseSerVO.getRxIds()[i]);
            }
        }
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            Map rxTitleMLMap = new HashMap();
            Map locoImpactMLMap = new HashMap();
            if (findCaseSerVO.getStrUserLanguage() != null
                    && !findCaseSerVO.getStrUserLanguage().toUpperCase().equals("EN")) {
                StringBuilder Query = new StringBuilder();
                Query.append(" SELECT LINK_RECOM, TRANS_RECOM_TITLE_TXT ");
                Query.append(" FROM GETS_SD.GETS_SD_RECOM_UT8 ");
                Query.append(" WHERE LANGUAGE_CD = ");
                Query.append(" (SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME = :language)");

                Query hibernateQuery = session.createSQLQuery(Query.toString());
                hibernateQuery.setString(RMDCommonConstants.LANGUAGE,
                        "LANG_" + findCaseSerVO.getStrUserLanguage().toUpperCase());
                hibernateQuery.setFetchSize(500);
                List rxTitleMLResults = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(rxTitleMLResults)) {
                    for (int i = 0; i < rxTitleMLResults.size(); i++) {
                        Object rxTitleMLData[] = (Object[]) rxTitleMLResults.get(i);
                        rxTitleMLMap.put(RMDCommonUtility.convertObjectToString(rxTitleMLData[0]),
                                RMDCommonUtility.convertObjectToString(rxTitleMLData[1]));
                    }
                }

                Query = new StringBuilder();
                Query.append(" SELECT LOCO_IMPACT_TXT, TRANS_LOCO_IMPACT_TXT FROM GETS_SD.GETS_SD_MSTR_LOCO_IMPACT ");
                Query.append(" WHERE UPPER(ACTIVE_FLG)='Y' ");
                Query.append(" AND LANGUAGE_CD=(SELECT LOOK_VALUE  FROM GETS_RMD_LOOKUP WHERE LIST_NAME = :language) ");

                hibernateQuery = session.createSQLQuery(Query.toString());
                hibernateQuery.setString(RMDCommonConstants.LANGUAGE,
                        "LANG_" + findCaseSerVO.getStrUserLanguage().toUpperCase());
                hibernateQuery.setFetchSize(500);
                List locoImpMLResults = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(locoImpMLResults)) {
                    for (int i = 0; i < locoImpMLResults.size(); i++) {
                        Object locoImpMLData[] = (Object[]) locoImpMLResults.get(i);
                        locoImpactMLMap.put(RMDCommonUtility.convertObjectToString(locoImpMLData[0]),
                                RMDCommonUtility.convertObjectToString(locoImpMLData[1]));
                    }
                }
            }

            StringBuilder Query = new StringBuilder();
		    Query.append(" SELECT RX_CASE_ID , CASE_ID , CASETITLE , CASE_TIME ,RX_TITLE,URGENCY , REPAIR_TIME ");
		    Query.append(" , LOCO_IMPACT , DELV_DATE , RECOM_NOTES , MODELOBJID , FLEETOBJID  ");
		    Query.append(", CASETYPEOBJID , RECOMOBJID ,  SERIAL_NO , S_ORG_ID , X_VEH_HDR, RX_CLOSE_DATE ,");
		    Query.append("  decode(REISSUE_FLAG,'Y',DELV_DATE) REISSUE_RX_DELVDATE ");
		    if(RMDCommonConstants.TECHNICIAN_CASES.equals(findCaseSerVO.getScreenName())){
    		    Query.append(", CASE ");
    			Query.append(" WHEN RECOM_TYPE = 'Custom' ");
    		    Query.append("  THEN 'Custom' ");
    			Query.append(" WHEN RECOM_TYPE = 'Standard' ");
    			Query.append("  THEN ");
    			Query.append(" (CASE ");
    			Query.append(" WHEN CREATION_DT > sysdate -180 THEN 'New' ");
    			Query.append(" WHEN CREATION_DT < sysdate -180 AND LAST_UPDATED_DT > sysdate -180 THEN 'Refreshed' ");
    			Query.append("  ELSE null ");
    			Query.append(" END) ");
    			Query.append(" ELSE null ");
    		    Query.append(" END as RX_TYPE ");
		    }
		    
            Query.append("  FROM ( SELECT FDBK.RX_CASE_ID , C.ID_NUMBER CASE_ID ");
            Query.append("  , C.TITLE CASETITLE , TO_CHAR(C.CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CASE_TIME");  
            Query.append("  , RECOM.TITLE RX_TITLE , RECOM.CREATION_DATE CREATION_DT, RECOM.LAST_UPDATED_DATE LAST_UPDATED_DT, ");
            Query.append(" CASERECOM.URGENCY URGENCY , CASERECOM.EST_REPAIR_TIME REPAIR_TIME ");
            Query.append(" , RECOM.LOCO_IMPACT , ");
            Query.append(" TO_CHAR(DELV.DELV_DATE, 'MM/DD/YYYY HH24:MI:SS') DELV_DATE ");
            Query.append(" , DELV.RECOM_NOTES , VEH.VEHICLE2MODEL MODELOBJID , VEH.VEHICLE2FLEET FLEETOBJID ");
            Query.append(" , C.CALLTYPE2GBST_ELM CASETYPEOBJID , RECOM.OBJID RECOMOBJID ");
            Query.append(" , TSP.SERIAL_NO , TBO.S_ORG_ID , TSP.X_VEH_HDR ");
            Query.append(
                    " , ROW_NUMBER() OVER ( PARTITION BY FDBK.RX_CASE_ID ORDER BY DELV.DELV_DATE DESC) ROWNUMBER ");
            Query.append(" , TO_CHAR(FDBK.RX_CLOSE_DATE, 'MM/DD/YYYY HH24:MI:SS') RX_CLOSE_DATE, CASERECOM.REISSUE_FLAG,RECOM_TYPE ");
            Query.append(" FROM TABLE_CASE C ,GETS_SD_RECOM RECOM ,GETS_SD_CASE_RECOM CASERECOM ");
            Query.append(" ,GETS_SD_RECOM_DELV DELV ,TABLE_SITE_PART TSP ,GETS_SD_CUST_FDBK FDBK ");
            Query.append(" ,GETS_RMD_VEHICLE VEH,  GETS_RMD_VEH_HDR VEHHDR, ");
            Query.append(" TABLE_BUS_ORG TBO WHERE DELV.RECOM_DELV2CASE = C.OBJID ");
            Query.append(" AND DELV.RECOM_DELV2RECOM = RECOM.OBJID ");
            Query.append(" AND CASERECOM.CASE_RECOM2CASE = C.OBJID ");
            Query.append(" AND CASERECOM.CASE_RECOM2RECOM = RECOM.OBJID ");
            if (null != findCaseSerVO.getStrRxCaseId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrRxCaseId())) {
                Query.append(" AND FDBK.RX_CASE_ID = :rxCaseID ");
            }
            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                Query.append(" AND TSP.SERIAL_NO = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                Query.append(" AND TBO.S_ORG_ID = :customerID ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                Query.append(" AND TSP.X_VEH_HDR = :assetGrpName ");
            }
            if (null != findCaseSerVO.getProducts() && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                if (!findCaseSerVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    Query.append(" AND TSP.OBJID in (" + getProductQuery(findCaseSerVO.getStrCustomerId()) + " ) ");
                }
            }
            Query.append(" AND CASE_PROD2SITE_PART = TSP.OBJID ");
            Query.append(" AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
            Query.append(" AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID ");
            Query.append(" AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            Query.append(" AND FDBK.CUST_FDBK2CASE = C.OBJID ");
            if (!RMDCommonConstants.CLOSED_CASE_RX_STATUS.equals(findCaseSerVO.getStrRxStatus())) {
                Query.append(" AND CASESTS2GBST_ELM IN (268435544,268435947,268435478,268435523) ");
            }
            if (RMDCommonConstants.CLOSED_CASE_RX_STATUS.equals(findCaseSerVO.getStrRxStatus())) {
                Query.append(" AND FDBK.RX_CLOSE_DATE IS NOT NULL ");
                if (findCaseSerVO.getClosedRxLookBackDays() != 0) {
                    Query.append(" AND  FDBK.RX_CLOSE_DATE > SYSDATE - :lookBackDays ");
                }

            } else {
                Query.append(" AND FDBK.RX_CLOSE_DATE IS NULL ");
            }
            Query.append(" AND FDBK.OBJID = DELV.RECOM_DELV2CUST_FDBK ");
            Query.append(" ) A WHERE ROWNUMBER = 1 ORDER BY  TO_DATE(CASE_TIME,'MM/dd/yyyy HH24:mi:ss') DESC");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(Query.toString());
                if (null != findCaseSerVO.getStrRxCaseId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrRxCaseId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.RXCASEID, findCaseSerVO.getStrRxCaseId());
                }
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }
                if (null != findCaseSerVO.getProducts() && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                    if (!findCaseSerVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        if (null != findCaseSerVO.getProducts()
                                && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                            hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                    findCaseSerVO.getProducts());
                            if (null != findCaseSerVO.getStrCustomerId()
                                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                                        findCaseSerVO.getStrCustomerId());
                            }
                        }
                    }
                }
                if (RMDCommonConstants.CLOSED_CASE_RX_STATUS.equals(findCaseSerVO.getStrRxStatus())
                        && findCaseSerVO.getClosedRxLookBackDays() != 0) {
                    hibernateQuery.setParameter(RMDCommonConstants.CLOSED_RX_LOOKBACK_DAYS,
                            findCaseSerVO.getClosedRxLookBackDays());
                }
                hibernateQuery.setFetchSize(500);
                delQueryResults = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(delQueryResults)) {
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                    for (int i = 0; i < delQueryResults.size(); i++) {
                        objDelCase = new SelectCaseHomeVO();
                        Object caseData[] = (Object[]) delQueryResults.get(i);
                        if (findCaseSerVO.getEstRepairTime() != null
                                && !findCaseSerVO.getEstRepairTime().trim().equals("")
                                && RMDCommonUtility.convertObjectToString(caseData[6]) != null
                                && !RMDCommonUtility.convertObjectToString(caseData[6]).trim()
                                        .equals(findCaseSerVO.getEstRepairTime().trim())) {
                            continue;
                        }
                        if (urgRepMap != null && urgRepMap.size() > 0
                                && urgRepMap.get(RMDCommonUtility.convertObjectToString(caseData[5])) == null) {
                            continue;
                        }
                        if (caseTypeMap != null && caseTypeMap.size() > 0
                                && caseTypeMap.get(RMDCommonUtility.convertObjectToString(caseData[12])) == null) {
                            continue;
                        }
                        if (modelMap != null && modelMap.size() > 0
                                && modelMap.get(RMDCommonUtility.convertObjectToString(caseData[10])) == null) {
                            continue;
                        }
                        if (fleetMap != null && fleetMap.size() > 0
                                && fleetMap.get(RMDCommonUtility.convertObjectToString(caseData[11])) == null) {
                            continue;
                        }
                        if (rxTitleMap != null && rxTitleMap.size() > 0
                                && rxTitleMap.get(RMDCommonUtility.convertObjectToString(caseData[13])) == null) {
                            continue;
                        }
                        objDelCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[1]));
                        objDelCase.setStrTitle(
                                AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(caseData[2])));
                        objDelCase.setStrUrgency(RMDCommonUtility.convertObjectToString(caseData[5]));
                        objDelCase.setStrGrpName(RMDCommonUtility.convertObjectToString(caseData[16]));
                        objDelCase.setStrAssetNumber(RMDCommonUtility.convertObjectToString(caseData[14]));
                        objDelCase.setStrcustomerName(RMDCommonUtility.convertObjectToString(caseData[15]));
                        /* Checking for dates datatype */
                        if (null != caseData[2] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[3])) {
                            Date createdDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[3]));
                            objDelCase.setDtCreationDate(createdDate);
                        }
                        rxDelServiceVO = new RecomDelvServiceVO();
                        rxDelServiceVO.setStrRxCaseId(RMDCommonUtility.convertObjectToString(caseData[0]));
                        String rxTitle = RMDCommonUtility.convertObjectToString(caseData[4]);
                        if(RMDCommonConstants.TECHNICIAN_CASES.equals(findCaseSerVO.getScreenName()))
                            objDelCase.setRxType(RMDCommonUtility.convertObjectToString(caseData[19]));
                        
                        
                        if (findCaseSerVO.getStrUserLanguage() != null
                                && !findCaseSerVO.getStrUserLanguage().toUpperCase().equals("EN")
                                && rxTitleMLMap.get(RMDCommonUtility.convertObjectToString(caseData[13])) != null) {
                        	rxTitle = (String) rxTitleMLMap.get(RMDCommonUtility.convertObjectToString(caseData[13]));
                        }
                        rxDelServiceVO.setStrRxTitle(AppSecUtil.htmlEscaping(rxTitle));
                        String locoImpact = RMDCommonUtility.convertObjectToString(caseData[7]);
                        if (findCaseSerVO.getStrUserLanguage() != null
                                && !findCaseSerVO.getStrUserLanguage().toUpperCase().equals("EN")
                                && locoImpactMLMap.get(locoImpact) != null) {
                            locoImpact = (String) locoImpactMLMap.get(locoImpact);
                        }
                        rxDelServiceVO.setLocomotiveImpact(AppSecUtil.htmlEscaping(locoImpact));

                        if (null != caseData[8] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[8])) {
                            Date delvDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[8]));
                            rxDelServiceVO.setDtRxDelvDate(delvDate);
                        }
                        rxDelServiceVO.setStrUrgRepair(RMDCommonUtility.convertObjectToString(caseData[5]));
                        rxDelServiceVO.setStrEstmRepTime(RMDCommonUtility.convertObjectToString(caseData[6]));
                        rxDelServiceVO.setSolutionNotes(
                                AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(caseData[9])));
                        if (null != caseData[17] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[17])) {
                            rXCloseDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[17]));
                            rxDelServiceVO.setDtRxClosedDate(rXCloseDate);
                        }
                        if (null != caseData[18] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[18])) {
                            Date reissueRxDelvDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[18]));
                            rxDelServiceVO.setReissuedRxDelvDate(reissueRxDelvDate);
                        }
                        /*
                         * Logic for getting multiple solutionInfo from a linear
                         * data
                         */
                        if (null != caseData[0] && caseIdList.contains(caseData[0].toString())) {
                            /* Adding if multiple solution info in case */
                            if (delCaseMap.containsKey(caseData[0].toString())) {
                                tmpDelServiceLst = new ArrayList<RecomDelvServiceVO>();
                                tmpObjDelCase = delCaseMap.get(caseData[0].toString());
                                tmpDelServiceLst = tmpObjDelCase.getArlRecomDelv();
                                tmpDelServiceLst.add(rxDelServiceVO);
                                tmpObjDelCase.setArlRecomDelv(tmpDelServiceLst);
                                delCaseMap.put(caseData[0].toString(), tmpObjDelCase);
                            } else {
                                delCaseMap.put(caseData[0].toString(), objDelCase);
                            }
                        } else {/* Adding if single solution info in case */
                            if (null != caseData[0]) {
                                caseIdList.add(caseData[0].toString());

                                rxDelServiceVOList = new ArrayList<RecomDelvServiceVO>();
                                rxDelServiceVOList.add(rxDelServiceVO);
                                objDelCase.setArlRecomDelv(rxDelServiceVOList);
                                if (delCaseMap.containsKey(caseData[0].toString())) {
                                    tmpObjDelCase = delCaseMap.get(caseData[0].toString());
                                    tmpDelServiceLst = tmpObjDelCase.getArlRecomDelv();
                                    tmpDelServiceLst.add(rxDelServiceVO);
                                    tmpObjDelCase.setArlRecomDelv(tmpDelServiceLst);
                                    delCaseMap.put(caseData[0].toString(), tmpObjDelCase);
                                } else {
                                    delCaseMap.put(caseData[0].toString(), objDelCase);
                                }
                            }
                        }
                    }
                    Collection collObject = delCaseMap.values();
                    // obtain an Iterator for Collection
                    Iterator<SelectCaseHomeVO> itr = collObject.iterator();
                    // iterate through HashMap values iterator to add the
                    // values in list
                    while (itr.hasNext()) {
                        delCaseList.add(itr.next());
                    }
                }
            }
            return delCaseList;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.FindCaseDAOIntf#
     * getLatestCaseRules(
     * com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseSerVO) throws RMDDAOException {
        Map<String, Map<String, String>> caseRuleMap = new HashMap();
        List arlSearchRes;
        Session session = null;
        int count = RMDServiceConstants.COUNT;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            final StringBuilder query = new StringBuilder();
            query.append(" SELECT CASE_OBJID , ID_NUMBER , RD.OBJID RECOM_DEL_OBJID , RULEDEF2FINRUL ");
            query.append(" , TEMP.CREATION_TIME FROM ( SELECT ID_NUMBER , CASE_OBJID , CREATION_TIME ");
            query.append(" FROM ( SELECT CI.ID_NUMBER , CI.OBJID  CASE_OBJID , CI.CREATION_TIME ");
            query.append(" FROM TABLE_CASE CI , TABLE_SITE_PART TSP , TABLE_BUS_ORG TBO WHERE ");
            query.append(" CI.CASE_PROD2SITE_PART = TSP.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                query.append(" AND TBO. ORG_ID = :customerID ");
            }
            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                query.append(" AND TSP.SERIAL_NO = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                query.append(" AND TSP.X_VEH_HDR = :assetGrpName ");
            }
            query.append(" ORDER BY CI.CREATION_TIME DESC ");
            query.append(" ) X WHERE ROWNUM <= :count ) TEMP , GETS_TOOL_AR_LIST ar , GETS_TOOL_RPRLDWN rp ");
            query.append(" , GETS_TOOL_DPD_RULEDEF rd , GETS_TOOL_DPD_FINRUL FR WHERE ");
            query.append(" RULEDEF2FINRUL = FR.OBJID (+) AND RP.RPRLDWN2AR_LIST (+) = AR.OBJID ");
            query.append(" AND RP.RPRLDWN2RULE_DEFN = RD.OBJID (+) AND AR.AR_LIST2CASE(+) = TEMP.CASE_OBJID ");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(query.toString());
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }
                hibernateQuery.setParameter(RMDCommonConstants.COUNT, count);

                hibernateQuery.setFetchSize(300);
                arlSearchRes = hibernateQuery.list();

                for (int i = 0; i < arlSearchRes.size(); i++) {
                    Object caseData[] = (Object[]) arlSearchRes.get(i);
                    String strCaseId = RMDCommonUtility.convertObjectToString(caseData[1]);
                    String strRuleDefId = RMDCommonUtility.convertObjectToString(caseData[2]);
                    String strRuleId = RMDCommonUtility.convertObjectToString(caseData[3]);
                    if (caseRuleMap.get(strCaseId) == null) {
                        Map<String, String> ruleMap = new HashMap();
                        caseRuleMap.put(strCaseId, ruleMap);
                    }
                    if (strRuleDefId != null && strRuleId != null) {
                        Map<String, String> ruleMap = caseRuleMap.get(strCaseId);
                        if (ruleMap.get(strRuleDefId) == null) {
                            ruleMap.put(strRuleDefId, strRuleId);
                            caseRuleMap.put(strCaseId, ruleMap);
                        }
                    }
                }
            }
            return caseRuleMap;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl latestCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlSearchRes = null;
        }
    }

    /*
     * This method is used for getting all the multi lang values for rx title
     * and locomotive impact
     */
    @Override
    public MultiLangValuesVO getMultiLangMap() throws RMDDAOException {
        Session session = null;
        MultiLangValuesVO multiLangValuesVO = new MultiLangValuesVO();
        try {
            multiLangValuesVO.setLocoImpactMLMap(cachedEoaDAOIntf.getAllLocoImpactMap());
            multiLangValuesVO.setTitleMLMap(cachedEoaDAOIntf.getAllRxTitleMap());
            return multiLangValuesVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE, e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException {
        List closedCaseList = new ArrayList();
        SelectCaseHomeVO objClosedCase;

        List closedQueryResults = null;
        Session session = null;

        try {
            session = getHibernateSession();
            session.flush();
            session.clear();

            StringBuilder Query = new StringBuilder();

            Query.append(
                    " SELECT table_case.id_number ID_NUMBER, table_case.title CASE_TITLE, table_gse_type.title CASE_TYPE, ");
            Query.append(
                    " DECODE(UPPER(table_gse_priority.TITLE),'6-CONDITIONAL',1,'5-ESTP',2,'2-HIGH',3,'3-MEDIUM',4,'4-LOW',5) PRIORITY, ");
            Query.append(
                    " DECODE(GETS_GET_CASE_REASON_DWQ_FN(table_case.objid) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation', ");
            Query.append(
                    " 'Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review','6-Yellow Rx Review', ");
            Query.append(
                    " 'Recommendation Closed','8-Recommendation Closed',GETS_GET_CASE_REASON_DWQ_FN(table_case.objid)) CASE_REASON, ");
            Query.append(" TO_CHAR(TABLE_CASE.CREATION_TIME,'MM-DD-YY HH24:MI:SS') CREATION_TIME, ");
            Query.append(" TO_CHAR(table_close_case.close_date,'MM-DD-YY HH24:MI:SS') CLOSE_DATE, ");
            Query.append(" table_queue.s_title CASE_QUEUE, table_user.login_name CASE_OWNER ");
            Query.append(" FROM table_gbst_elm table_gse_type, table_gbst_elm table_gse_severity, ");
            Query.append(" table_gbst_elm table_gse_status, table_gbst_elm table_gse_priority, ");
            Query.append(" table_condition table_condition, ");
            Query.append(" table_case table_case, table_queue, table_user, table_site, table_site_part, ");
            Query.append(" table_bus_org, table_close_case ");
            Query.append(" WHERE table_gse_type.objid     = table_case.calltype2gbst_elm ");
            Query.append(" AND table_gse_severity.objid   = table_case.respsvrty2gbst_elm ");
            Query.append(" AND table_gse_priority.objid   = table_case.RESPPRTY2GBST_ELM ");
            Query.append(" AND table_condition.objid      = table_case.CASE_STATE2CONDITION ");
            Query.append(" AND table_gse_status.objid     = table_case.casests2gbst_elm ");
            Query.append(" AND table_user.objid           = table_case.case_owner2user ");
            Query.append(" AND table_queue.objid (+)      = table_case.case_currq2queue ");
            Query.append(" AND table_site.objid           = table_case.case_reporter2site ");
            Query.append(" AND table_site_part.serial_no NOT LIKE '%BAD%' ");
            Query.append(" AND table_site_part.objid      = table_case.case_prod2site_part ");
            Query.append(" AND table_case.objid           = table_close_case.last_close2case ");
            Query.append(" AND table_case.creation_time  >= sysdate - 90 ");
            Query.append(" AND table_bus_org.objid        = table_site.primary2bus_org ");
            Query.append(" AND TABLE_BUS_ORG.ORG_ID      = :customerID ");
            Query.append(" AND table_site_part.serial_no = :assetNumber ");
            Query.append(" AND table_site_part.x_veh_hdr = :assetGrpName ");
            Query.append(" AND table_condition.title     = 'Closed' ");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(Query.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                hibernateQuery.setFetchSize(500);
                closedQueryResults = hibernateQuery.list();

                if (RMDCommonUtility.isCollectionNotEmpty(closedQueryResults)) {
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyhhmmss);
                    for (int i = 0; i < closedQueryResults.size(); i++) {
                        Object caseData[] = (Object[]) closedQueryResults.get(i);

                        objClosedCase = new SelectCaseHomeVO();
                        objClosedCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[0]));
                        objClosedCase.setStrTitle(RMDCommonUtility.convertObjectToString(caseData[1]));
                        objClosedCase.setStrCaseType(RMDCommonUtility.convertObjectToString(caseData[2]));
                        objClosedCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[3]));
                        objClosedCase.setStrReason(RMDCommonUtility.convertObjectToString(caseData[4]));
                        if (null != caseData[5] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[5])) {
                            Date createDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[5]));
                            objClosedCase.setDtCreationDate(createDate);
                        }
                        if (null != caseData[6] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[6])) {
                            Date closedDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[6]));
                            objClosedCase.setDtLastUpdatedDate(closedDate);
                        }
                        objClosedCase.setStrQueue(RMDCommonUtility.convertObjectToString(caseData[7]));
                        objClosedCase.setStrOwner(RMDCommonUtility.convertObjectToString(caseData[8]));

                        closedCaseList.add(objClosedCase);
                    }
                }
            }
            return closedCaseList;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseLiteDAOImpl getAssetClosedCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
    public List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException {

        List nonClosedCaseList = new ArrayList();
        SelectCaseHomeVO objNonClosedCase;

        List nonClosedQueryResults = null;
        Session session = null;

        try {
            session = getHibernateSession();
            session.flush();
            session.clear();

            StringBuilder Query = new StringBuilder();

            Query.append(" SELECT ID_NUMBER , CASE_TITLE , GETS_GBST_ELM_FN(CASE_TYPE) CASE_TYPE ");
            Query.append(" , PRIORITY , CASEREASON CASE_REASON , CREATION_TIME , NULL AS CLOSE_DATE ");
            Query.append(" , QUEUE CASE_QUEUE , WIPBIN_NAME CASE_OWNER ");
            Query.append(" FROM GETS_RMD.DYNAMIC_WORK_QUEUE_MV ");
            Query.append(" WHERE ORG_ID = :customerID AND SERIAL_NO = :assetNumber ");
            Query.append(" UNION ");
            Query.append(" SELECT /*+ NO_PARALLEL INDEX(C TABLE_CASE_I3) */ ");
            Query.append(
                    " C.ID_NUMBER ID_NUMBER , C.TITLE CASE_TITLE , gets_gbst_elm_fn(C.CALLTYPE2GBST_ELM) Case_Type ");
            Query.append(
                    " , DECODE(UPPER(GETS_GBST_ELM_FN(C.RESPPRTY2GBST_ELM)),'2-HIGH',1,'3-MEDIUM',2,'4-LOW',3,4) PRIORITY ");
            Query.append(
                    " , DECODE(GETS_GET_CASE_REASON_DWQ_FN(c.objid) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review', ");
            Query.append(
                    " '5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review','6-Yellow Rx Review', ");
            Query.append(
                    " 'Recommendation Closed','8-Recommendation Closed', GETS_GET_CASE_REASON_DWQ_FN(c.objid)) CASE_REASON ");
            Query.append(" , TO_CHAR(C.CREATION_TIME,'MM-DD-YY HH24:MI:SS') , NULL AS CLOSE_DATE ");
            Query.append(" , TQ.S_TITLE , DECODE(TCOND.TITLE,'Open-Dispatch',NULL,TABLE_USER.LOGIN_NAME) ");
            Query.append(" FROM TABLE_CASE C , GETS_SD_CASE_RECOM CASERECOM , GETS_SD_RECOM_DELV DELV ");
            Query.append(" , TABLE_SITE_PART TSP , GETS_RMD_VEHICLE VEH , GETS_RMD_VEH_HDR VEHHDR ");
            Query.append(" , TABLE_BUS_ORG TBO , GETS_SD_CUST_FDBK FDBK ");
            Query.append(" , TABLE_QUEUE TQ , TABLE_USER TABLE_USER , TABLE_CONDITION TCOND ");
            Query.append(" WHERE C.CASE_PROD2SITE_PART = TSP.OBJID ");
            Query.append(" AND C.CASESTS2GBST_ELM IN (268435544,268435947,268435478,268435523) ");
            Query.append(" AND CASERECOM.CASE_RECOM2CASE       = C.OBJID ");
            Query.append(" AND TQ.OBJID                        = C.CASE_CURRQ2QUEUE (+) ");
            Query.append(" AND CASERECOM.CASE_RECOM2RECOM_DELV = DELV.OBJID ");
            Query.append(" AND TABLE_USER.OBJID                = C.CASE_OWNER2USER ");
            Query.append(" AND TSP.OBJID                       = VEH.VEHICLE2SITE_PART ");
            Query.append(" AND VEH.VEHICLE2VEH_HDR             = VEHHDR.OBJID ");
            Query.append(" AND VEHHDR.VEH_HDR2BUSORG           = TBO.OBJID ");
            Query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            Query.append(" AND TBO.ORG_ID            = :customerID ");
            Query.append(" AND TSP.SERIAL_NO         = :assetNumber ");
            Query.append(" AND CASERECOM.URGENCY IN ('R','Y','W','B','G','C','O') ");
            Query.append(" AND FDBK.CUST_FDBK2CASE = C.OBJID ");
            Query.append(" AND DELV.RECOM_DELV2CUST_FDBK = FDBK.OBJID ");
            Query.append(" AND TCOND.OBJID = C.CASE_STATE2CONDITION ");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(Query.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                hibernateQuery.setFetchSize(500);
                nonClosedQueryResults = hibernateQuery.list();

                if (RMDCommonUtility.isCollectionNotEmpty(nonClosedQueryResults)) {
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyhhmmss);
                    for (int i = 0; i < nonClosedQueryResults.size(); i++) {
                        objNonClosedCase = new SelectCaseHomeVO();
                        Object caseData[] = (Object[]) nonClosedQueryResults.get(i);

                        objNonClosedCase = new SelectCaseHomeVO();
                        objNonClosedCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[0]));
                        objNonClosedCase.setStrTitle(RMDCommonUtility.convertObjectToString(caseData[1]));
                        objNonClosedCase.setStrCaseType(RMDCommonUtility.convertObjectToString(caseData[2]));
                        objNonClosedCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[3]));
                        objNonClosedCase.setStrReason(RMDCommonUtility.convertObjectToString(caseData[4]));
                        if (null != caseData[5] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[5])) {
                            Date createDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[5]));
                            objNonClosedCase.setDtCreationDate(createDate);
                        }
                        objNonClosedCase.setStrQueue(RMDCommonUtility.convertObjectToString(caseData[7]));
                        objNonClosedCase.setStrOwner(RMDCommonUtility.convertObjectToString(caseData[8]));

                        nonClosedCaseList.add(objNonClosedCase);
                    }
                }
            }
            return nonClosedCaseList;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<UnitConfigVO>
     * @throws:RMDDAOException
     * @Description:This method returns the unit configuration details in data
     *                   screen
     */
    @Override
    public List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        Session objSession = null;
        List<UnitConfigVO> unitconfigList = new ArrayList<UnitConfigVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder unitConfigQuery = new StringBuilder();
            unitConfigQuery.append(
                    "SELECT G.CFG_GROUP,CFG_ITEM,MODEL_NAME FROM GETS_SD_CFG_GROUP_ITEM I, GETS_RMD_LOOKUP L, GETS_RMD_MASTER_BOM M,");
            unitConfigQuery.append(
                    "GETS_RMD_VEHCFG V, GETS_SD_CFG_GROUP G,GETS_RMD_VEHICLE VEH,TABLE_CASE TC, GETS_RMD_MODEL MODEL WHERE ");
            unitConfigQuery.append(
                    "CFG_ITEM2RMD_LOOKUP = L.OBJID AND CFG_ITEM2CFG_GROUP = G.OBJID  AND L.LOOK_VALUE = M.CONFIG_ITEM AND ");
            unitConfigQuery.append(
                    "VEHCFG2MASTER_BOM = M.OBJID AND I.CURRENT_VERSION = V.CURRENT_VERSION AND VEH_CFG2VEHICLE = VEH.OBJID ");
            unitConfigQuery.append(
                    "AND TC.ID_NUMBER = :caseId AND VEHICLE2MODEL = MODEL.OBJID AND TC.CASE_PROD2SITE_PART = VEH.VEHICLE2SITE_PART ");
            if (null != objFindCaseServiceVO.getLocoId()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objFindCaseServiceVO.getLocoId())) {
                unitConfigQuery.append(
                        "UNION SELECT 'Tier Type',A.EMISSION_TIER_TYPE,'Model' FROM GETS_LMS_LOCOMOTIVE_ALL@ESERVICES.WORLD A,");
                unitConfigQuery.append(
                        "GETS_LMS_FLEET_ALL@ESERVICES.WORLD	FLEET,RA_CUSTOMERS@ESERVICES.WORLD B  WHERE   A.LOCOMOTIVE_ID = :locoId ");
                unitConfigQuery.append("AND B.CUSTOMER_ID = A.CUSTOMER_ID AND FLEET.FLEET_ID = A.FLEET_ID ORDER BY 1");
            } else {
                unitConfigQuery.append(" ORDER BY G.CFG_GROUP ");
            }
            Query caseHqry = objSession.createSQLQuery(unitConfigQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.CASE_ID, objFindCaseServiceVO.getStrCaseId());
            caseHqry.setParameter(RMDCommonConstants.LOCO_ID, objFindCaseServiceVO.getLocoId());
            List<Object[]> myCasesList = caseHqry.list();
            Object[] objUnitConfigVOModelName = myCasesList.get(0);
            String model = (String) objUnitConfigVOModelName[2];
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    UnitConfigVO objUnitConfigVO = new UnitConfigVO();
                    final Object[] caseHome = obj.next();
                    objUnitConfigVO.setModelName(model);
                    objUnitConfigVO.setConfigGroup(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objUnitConfigVO.setConfigItem(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    unitconfigList.add(objUnitConfigVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UNIT_CONFIG);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return unitconfigList;

    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDDAOException {
        Session objSession = null;
        List<RepairCodeEoaDetailsVO> falseAlarmDetailsList = new ArrayList<RepairCodeEoaDetailsVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder falseAlarmQuery = new StringBuilder();
            falseAlarmQuery.append(
                    "SELECT REPAIR_CODE,REPAIR_DESC,COUNT(DISTINCT(CCC.OBJID)) FROM GETS_RMD.GETS_SD_FINAL_REPCODE FRC,");
            falseAlarmQuery.append(
                    "GETS_RMD.GETS_SD_REPAIR_CODES RC,TABLE_CASE CCC,TABLE_USER U WHERE   FRC.FINAL_REPCODE2CASE = CCC.OBJID ");
            falseAlarmQuery.append(
                    "AND FRC.FINAL_REPCODE2REPCODE =RC.OBJID AND CCC.CASE_ORIGINATOR2USER = U.OBJID  AND LOGIN_NAME = 'case_creation' ");
            falseAlarmQuery.append(
                    "AND CCC.OBJID IN (  (SELECT DISTINCT C.OBJID CID FROM GETS_RMD.GETS_TOOL_AR_LIST AR,GETS_RMD.GETS_SD_RECOM R, ");
            falseAlarmQuery.append(
                    "SA.TABLE_CASE C  WHERE AR.AR_LIST2CASE = C.OBJID AND AR.AR_LIST2RECOM = R.OBJID AND C.CREATION_TIME > SYSDATE ");
            falseAlarmQuery.append(
                    "-(SELECT VALUE FROM GETS_RMD_SYSPARM   WHERE TITLE = 'auto_rx_case_lkbk_period')  AND R.OBJID = :rxObjId ");
            falseAlarmQuery.append(
                    ")MINUS  (SELECT DISTINCT C.OBJID  CID FROM  GETS_RMD.GETS_TOOL_AR_LIST AR,GETS_RMD.GETS_SD_FINAL_RECOM FR, ");
            falseAlarmQuery.append(
                    "GETS_RMD.GETS_SD_RECOM R,SA.TABLE_CASE C WHERE AR.AR_LIST2CASE = C.OBJID  AND FR.FINAL_RECOM2CASE  = C.OBJID ");
            falseAlarmQuery.append(
                    "AND FR.FINAL_RECOM2RECOM = R.OBJID  AND AR.AR_LIST2RECOM = R.OBJID AND C.CREATION_TIME > SYSDATE ");
            falseAlarmQuery.append(
                    "-(SELECT VALUE FROM GETS_RMD_SYSPARM   WHERE TITLE = 'auto_rx_case_lkbk_period')AND R.OBJID =:rxObjId ");
            falseAlarmQuery.append(") )GROUP BY REPAIR_CODE,REPAIR_DESC ORDER BY 3 DESC");

            Query caseHqry = objSession.createSQLQuery(falseAlarmQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.RX_OBJ_ID, rxObjId);
            List<Object[]> myCasesList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    RepairCodeEoaDetailsVO objRepairCodeEoaDetailsVO = new RepairCodeEoaDetailsVO();
                    final Object[] caseHome = obj.next();
                    objRepairCodeEoaDetailsVO.setRepairCode(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objRepairCodeEoaDetailsVO.setRepairCodeDesc(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    objRepairCodeEoaDetailsVO.setNoOfCases(RMDCommonUtility.convertObjectToString(caseHome[2]));
                    falseAlarmDetailsList.add(objRepairCodeEoaDetailsVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FALSE_ALARM_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return falseAlarmDetailsList;

    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDDAOException {
        Session objSession = null;
        List<RxDetailsVO> falseAlarmDetailsList = new ArrayList<RxDetailsVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder falseAlarmQuery = new StringBuilder();
            falseAlarmQuery.append(
                    "SELECT DECODE(RR.TITLE,NULL,'No Rx Delivered',RR.TITLE) ,COUNT(DISTINCT CC.ID_NUMBER) CID ");
            falseAlarmQuery.append(
                    "FROM GETS_RMD.GETS_SD_FINAL_RECOM FRR,SA.TABLE_CASE CC,GETS_RMD.GETS_SD_RECOM RR ,TABLE_USER U ");
            falseAlarmQuery
                    .append("WHERE  CC.OBJID = FRR.FINAL_RECOM2CASE(+)  AND FRR.FINAL_RECOM2RECOM = RR.OBJID(+) ");
            falseAlarmQuery.append(
                    "AND CC.CASE_ORIGINATOR2USER = U.OBJID  AND LOGIN_NAME = 'case_creation'  AND CC.OBJID IN ");
            falseAlarmQuery.append(
                    "((SELECT DISTINCT C.OBJID CID FROM GETS_RMD.GETS_TOOL_AR_LIST AR,GETS_RMD.GETS_SD_RECOM R,SA.TABLE_CASE C ");
            falseAlarmQuery.append(
                    "WHERE AR.AR_LIST2CASE = C.OBJID AND AR.AR_LIST2RECOM    = R.OBJID    AND C.CREATION_TIME > SYSDATE - 100 ");
            falseAlarmQuery.append(
                    "AND R.OBJID =:rxObjId) MINUS (SELECT DISTINCT C.OBJID  CID FROM   GETS_RMD.GETS_TOOL_AR_LIST AR,");
            falseAlarmQuery.append(
                    "GETS_RMD.GETS_SD_FINAL_RECOM FR ,GETS_RMD.GETS_SD_RECOM R,SA.TABLE_CASE C  WHERE AR.AR_LIST2CASE = C.OBJID ");
            falseAlarmQuery.append(
                    "AND FR.FINAL_RECOM2CASE  = C.OBJID AND FR.FINAL_RECOM2RECOM = R.OBJID   AND AR.AR_LIST2RECOM    = R.OBJID ");
            falseAlarmQuery.append(
                    "AND C.CREATION_TIME > SYSDATE - 100 AND R.OBJID = :rxObjId )) GROUP BY DECODE(RR.TITLE,NULL,'No Rx Delivered',");
            falseAlarmQuery.append("RR.TITLE) ORDER BY CID DESC ");

            Query caseHqry = objSession.createSQLQuery(falseAlarmQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.RX_OBJ_ID, rxObjId);
            List<Object[]> myCasesList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    RxDetailsVO objRxDetailsVO = new RxDetailsVO();
                    final Object[] caseHome = obj.next();
                    objRxDetailsVO.setRxTitle(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objRxDetailsVO.setNoOfCases(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    falseAlarmDetailsList.add(objRxDetailsVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FALSE_ALARM_RX_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return falseAlarmDetailsList;

    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accurate details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDDAOException {
        Session objSession = null;
        List<RepairCodeEoaDetailsVO> mdscAccurateDetails = new ArrayList<RepairCodeEoaDetailsVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder mdscAccurateQuery = new StringBuilder();
            mdscAccurateQuery.append(
                    "SELECT R.REPAIR_CODE,R.REPAIR_DESC,COUNT(R.REPAIR_CODE) FROM GETS_RMD.GETS_SD_FINAL_REPCODE FR,GETS_RMD.GETS_SD_REPAIR_CODES R,");
            mdscAccurateQuery.append(
                    "((SELECT DISTINCT FDBK1.CUST_FDBK2CASE  C_OBJID FROM GETS_RMD.GETS_SD_RECOM R1, GETS_RMD.GETS_SD_RECOM_DELV DELV1, ");
            mdscAccurateQuery.append(
                    "GETS_RMD.GETS_SD_CUST_FDBK FDBK1  WHERE DELV1.RECOM_DELV2CUST_FDBK  = FDBK1.OBJID AND DELV1.CREATION_DATE > (SYSDATE - ");
            mdscAccurateQuery.append(
                    "(SELECT VALUE FROM GETS_RMD_SYSPARM   WHERE TITLE = 'auto_rx_case_lkbk_period'))AND DELV1.RECOM_DELV2RECOM = R1.OBJID   ");
            mdscAccurateQuery.append(
                    " AND R1.OBJID = :rxObjId )MINUS (SELECT DISTINCT FDBK2.CUST_FDBK2CASE C_OBJID  FROM GETS_RMD.GETS_SD_RECOM R2,  ");
            mdscAccurateQuery.append(
                    "GETS_RMD.GETS_SD_RECOM_DELV DELV2,GETS_RMD.GETS_SD_CUST_FDBK FDBK2  WHERE DELV2.RECOM_DELV2CUST_FDBK  = FDBK2.OBJID AND ");
            mdscAccurateQuery.append(
                    "DELV2.RECOM_DELV2RECOM = R2.OBJID  AND R2.OBJID =:rxObjId AND DELV2.CREATION_DATE > (SYSDATE -  (SELECT VALUE FROM GETS_RMD_SYSPARM  ");
            mdscAccurateQuery.append(
                    "WHERE TITLE = 'auto_rx_case_lkbk_period'))  AND FDBK2.ACCU_RECOMM = 'Y')) S where 	fr.FINAL_REPCODE2CASE = S.C_OBJID	 ");
            mdscAccurateQuery.append(
                    "AND FR.FINAL_REPCODE2REPCODE = R.OBJID GROUP BY R.REPAIR_CODE,R.REPAIR_DESC ORDER BY 3 DESC");
            Query caseHqry = objSession.createSQLQuery(mdscAccurateQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.RX_OBJ_ID, rxObjId);
            List<Object[]> myCasesList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    RepairCodeEoaDetailsVO objRepairCodeEoaDetailsVO = new RepairCodeEoaDetailsVO();
                    final Object[] caseHome = obj.next();
                    objRepairCodeEoaDetailsVO.setRepairCode(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objRepairCodeEoaDetailsVO.setRepairCodeDesc(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    objRepairCodeEoaDetailsVO.setNoOfCases(RMDCommonUtility.convertObjectToString(caseHome[2]));
                    mdscAccurateDetails.add(objRepairCodeEoaDetailsVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MDSC_ACCURATE_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return mdscAccurateDetails;

    }

    /**
     * @Author:
     * @param:String,String
     * @return:List<CaseInfoServiceVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accurate-case details in data
     *                   screen
     */
    @Override
    public List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDDAOException {
        Session objSession = null;
        List<CaseInfoServiceVO> caseList = new ArrayList<CaseInfoServiceVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder caseQuery = new StringBuilder();
            caseQuery.append(
                    "SELECT  DISTINCT C.ID_NUMBER,C.TITLE FROM GETS_RMD.GETS_SD_FINAL_REPCODE FR,GETS_RMD.GETS_SD_REPAIR_CODES R,TABLE_CASE C,");
            caseQuery.append(
                    "((SELECT DISTINCT  FDBK1.CUST_FDBK2CASE   C_OBJID  FROM GETS_RMD.GETS_SD_RECOM R1,GETS_RMD.GETS_SD_RECOM_DELV  DELV1, ");
            caseQuery.append(
                    "GETS_RMD.GETS_SD_CUST_FDBK FDBK1 WHERE DELV1.RECOM_DELV2CUST_FDBK  = FDBK1.OBJID AND DELV1.CREATION_DATE > (SYSDATE -  ");
            caseQuery.append(
                    "(SELECT VALUE FROM GETS_RMD_SYSPARM   WHERE TITLE = 'auto_rx_case_lkbk_period')) AND DELV1.RECOM_DELV2RECOM = R1.OBJID ");
            caseQuery.append(
                    " AND R1.OBJID = :rxObjId) MINUS (SELECT DISTINCT FDBK2.CUST_FDBK2CASE  C_OBJID  FROM GETS_RMD.GETS_SD_RECOM R2,  ");
            caseQuery.append(
                    "GETS_RMD.GETS_SD_RECOM_DELV DELV2,GETS_RMD.GETS_SD_CUST_FDBK FDBK2 WHERE DELV2.RECOM_DELV2CUST_FDBK  = FDBK2.OBJID AND ");
            caseQuery.append(
                    "DELV2.RECOM_DELV2RECOM = R2.OBJID AND R2.OBJID = :rxObjId AND DELV2.CREATION_DATE > (SYSDATE -(SELECT VALUE FROM GETS_RMD_SYSPARM ");
            caseQuery.append(
                    "WHERE TITLE = 'auto_rx_case_lkbk_period'))  AND FDBK2.ACCU_RECOMM = 'Y')) S	 WHERE FR.FINAL_REPCODE2CASE = S.C_OBJID AND ");
            caseQuery.append(
                    "FR.FINAL_REPCODE2REPCODE = R.OBJID AND FINAL_REPCODE2CASE = C.OBJID AND REPAIR_CODE =:repObjId  ORDER BY 1 ");
            Query caseHqry = objSession.createSQLQuery(caseQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.RX_OBJ_ID, rxObjId);
            caseHqry.setParameter(RMDCommonConstants.REP_OBJ_ID, repObjId);
            List<Object[]> myCasesList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    CaseInfoServiceVO objCaseInfoServiceVO = new CaseInfoServiceVO();
                    final Object[] caseHome = obj.next();
                    objCaseInfoServiceVO.setStrCaseId(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objCaseInfoServiceVO.setStrTitle(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    caseList.add(objCaseInfoServiceVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MDSC_ACCURATE_CASE_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return caseList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accurate-rx details in data
     *                   screen
     */
    @Override
    public List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDDAOException {
        Session objSession = null;
        List<RxDetailsVO> rxDetailsList = new ArrayList<RxDetailsVO>();
        try {
            objSession = getHibernateSession();
            StringBuilder rxQuery = new StringBuilder();
            rxQuery.append(
                    "SELECT DISTINCT RECOM.TITLE,DECODE(MDSC_PERF_PCT,NULL,0,MDSC_PERF_PCT) FROM GETS_SD_RECOM_TASK RECOMTASK, ");
            rxQuery.append(
                    "GETS_SD_REPAIR_CODES RP,GETS_SD_RECOM RECOM ,GETS_TOOLS.GETS_TOOL_RX_METRICS   WHERE RECOM_TASK2REP_CODE = RP.OBJID ");
            rxQuery.append(
                    "AND RECOM.OBJID = RECOM_TASK2RECOM  AND REPAIR_CODE =:repObjId AND RX_METRICS2RECOM (+)= RECOM.OBJID ORDER BY 2 DESC ");
            Query caseHqry = objSession.createSQLQuery(rxQuery.toString());
            caseHqry.setParameter(RMDCommonConstants.REP_OBJ_ID, repObjId);
            List<Object[]> myCasesList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj.hasNext();) {
                    RxDetailsVO objRxDetailsVO = new RxDetailsVO();
                    final Object[] caseHome = obj.next();
                    objRxDetailsVO.setRxTitle(RMDCommonUtility.convertObjectToString(caseHome[0]));
                    objRxDetailsVO.setMdscPerformance(RMDCommonUtility.convertObjectToString(caseHome[1]));
                    rxDetailsList.add(objRxDetailsVO);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MDSC_ACCURATE_RX_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }
        return rxDetailsList;

    }
}