package com.ge.trans.eoa.services.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.intf.CachedEoaDAOIntf;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/**
 * @author Igate
 * @description Class for caching
 */
public class CachedEoaDAOImpl extends RMDCommonDAO implements CachedEoaDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CachedEoaDAOImpl() {
        super();
    }

    /*
     * @description: This method is used to fetch the locomotive impact from
     * database. This is a cached function
     */

    @Override
    @Cacheable({ RMDCommonConstants.RX_LOCO_IMPACT_CACHE })
    public Map<String, String> getAllLocoImpactMap() {
        Session objSession = null;
        String currentId = null;
        String changedId = null;
        Map<String, String> locoImpactMLMap = new HashMap<String, String>();
        try {
            objSession = getHibernateSession();
            StringBuilder Query = new StringBuilder();
            Query.append(
                    "  SELECT RECOM.OBJID,IMPACT.LOCO_IMPACT_TXT,LIST_NAME,IMPACT.TRANS_LOCO_IMPACT_TXT FROM GETS_SD_RECOM RECOM, GETS_SD.GETS_SD_MSTR_LOCO_IMPACT IMPACT,GETS_RMD_LOOKUP LOOKUP ");
            Query.append(
                    "  WHERE RECOM.LOCO_IMPACT = IMPACT.LOCO_IMPACT_TXT AND UPPER(IMPACT.ACTIVE_FLG)='Y' AND IMPACT.LANGUAGE_CD=LOOKUP.LOOK_VALUE AND LOOKUP.LIST_NAME LIKE 'LANG_%' ORDER BY RECOM.OBJID");
            Query hibernateQuery = objSession.createSQLQuery(Query.toString());
            hibernateQuery.setFetchSize(500);
            List locoImpMLResults = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(locoImpMLResults)) {
                for (int i = 0; i < locoImpMLResults.size(); i++) {
                    Object locoImpMLData[] = (Object[]) locoImpMLResults.get(i);
                    changedId = RMDCommonUtility.convertObjectToString(locoImpMLData[0]);
                    if (changedId.equals(currentId)) {
                        if (null != locoImpMLData[2]) {
                            locoImpactMLMap.put(
                                    RMDCommonUtility.convertObjectToString(locoImpMLData[0])
                                            + RMDCommonConstants.UNDERSCORE
                                            + (RMDCommonUtility.convertObjectToString(locoImpMLData[2])
                                                    .split(RMDCommonConstants.UNDERSCORE)[1]),
                                    RMDCommonUtility.convertObjectToString(locoImpMLData[3]));
                        }
                    } else {
                        locoImpactMLMap.put(
                                RMDCommonUtility.convertObjectToString(locoImpMLData[0]) + RMDCommonConstants.UNDERSCORE
                                        + RMDCommonConstants.ENGLISH_UPPER,
                                RMDCommonUtility.convertObjectToString(locoImpMLData[1]));
                        if (null != locoImpMLData[2]) {
                            locoImpactMLMap.put(
                                    RMDCommonUtility.convertObjectToString(locoImpMLData[0])
                                            + RMDCommonConstants.UNDERSCORE
                                            + (RMDCommonUtility.convertObjectToString(locoImpMLData[2])
                                                    .split(RMDCommonConstants.UNDERSCORE)[1]),
                                    RMDCommonUtility.convertObjectToString(locoImpMLData[3]));
                        }
                    }
                    currentId = RMDCommonUtility.convertObjectToString(locoImpMLData[0]);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.MULTI_LANG_CHACHE_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.MULTI_LANG_CHACHE_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return locoImpactMLMap;
    }

    /*
     * @description: This method is used to fetch the all user values from
     * database. This is a cached function
     */

    @Override
    @Cacheable({ RMDCommonConstants.RX_TITLE_CACHE })
    public Map<String, String> getAllRxTitleMap() {
        Session objSession = null;
        String currentId = null;
        String changedId = null;
        Map<String, String> titleMLMap = new HashMap<String, String>();
        try {
            objSession = getHibernateSession();
            StringBuilder Query = new StringBuilder();
            Query.append(" SELECT RECOM.OBJID,RECOM.TITLE,LOOKUP.LIST_NAME,MULTILANG.TRANS_RECOM_TITLE_TXT ");
            Query.append(" FROM GETS_SD.GETS_SD_RECOM_UT8 MULTILANG,GETS_RMD_LOOKUP LOOKUP,GETS_SD_RECOM RECOM ");
            Query.append(
                    " WHERE MULTILANG.LINK_RECOM (+)=RECOM.OBJID AND MULTILANG.LANGUAGE_CD=LOOKUP.LOOK_VALUE (+) AND (LOOKUP.LIST_NAME LIKE 'LANG_%' OR LOOKUP.LIST_NAME IS NULL) ORDER BY RECOM.OBJID");

            Query hibernateQuery = objSession.createSQLQuery(Query.toString());
            hibernateQuery.setFetchSize(500);
            List rxTitleMLResults = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(rxTitleMLResults)) {
                for (int i = 0; i < rxTitleMLResults.size(); i++) {
                    Object rxTitleMLData[] = (Object[]) rxTitleMLResults.get(i);
                    changedId = RMDCommonUtility.convertObjectToString(rxTitleMLData[0]);
                    if (changedId.equals(currentId)) {
                        if (null != rxTitleMLData[2]) {
                            titleMLMap.put(
                                    RMDCommonUtility.convertObjectToString(rxTitleMLData[0])
                                            + RMDCommonConstants.UNDERSCORE
                                            + (RMDCommonUtility.convertObjectToString(rxTitleMLData[2])
                                                    .split(RMDCommonConstants.UNDERSCORE)[1]),
                                    RMDCommonUtility.convertObjectToString(rxTitleMLData[3]));
                        }

                    } else {
                        titleMLMap.put(
                                RMDCommonUtility.convertObjectToString(rxTitleMLData[0]) + RMDCommonConstants.UNDERSCORE
                                        + RMDCommonConstants.ENGLISH_UPPER,
                                RMDCommonUtility.convertObjectToString(rxTitleMLData[1]));
                        if (null != rxTitleMLData[2]) {
                            titleMLMap.put(
                                    RMDCommonUtility.convertObjectToString(rxTitleMLData[0])
                                            + RMDCommonConstants.UNDERSCORE
                                            + (RMDCommonUtility.convertObjectToString(rxTitleMLData[2])
                                                    .split(RMDCommonConstants.UNDERSCORE)[1]),
                                    RMDCommonUtility.convertObjectToString(rxTitleMLData[3]));
                        }
                    }
                    currentId = RMDCommonUtility.convertObjectToString(rxTitleMLData[0]);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.MULTI_LANG_CHACHE_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.MULTI_LANG_CHACHE_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return titleMLMap;
    }

}
