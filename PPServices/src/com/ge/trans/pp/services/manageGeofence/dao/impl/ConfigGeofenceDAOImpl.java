package com.ge.trans.pp.services.manageGeofence.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.owasp.esapi.ESAPI;

import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.manageGeofence.dao.intf.ConfigGeofenceDAOIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@SuppressWarnings("serial")
public class ConfigGeofenceDAOImpl extends RMDCommonDAO implements ConfigGeofenceDAOIntf {
	private static final RMDLogger LOG = RMDLogger.getLogger(ConfigGeofenceDAOImpl.class);
    /**
     * @Author :
     * @return :List<NotifyConfigVO>
     * @param :String
     *            customer
     * @throws :RMDDAOException
     * @Description: This method is used for Fetching Notify Configuration.
     */

    @Override
    public List<NotifyConfigVO> getNotifyConfigs(String customer) throws RMDDAOException {
        Session hibernateSession = null;
        List<NotifyConfigVO> arlNotifyConfigVOs = new ArrayList<NotifyConfigVO>();
        StringBuilder notifyConfigQuery = new StringBuilder();
        Query hibernateQuery = null;
        String cfgObjId = null;
         String busOrgObjId = null;
        String modelObjId = null;
        String customerName = null;
        String  model = null;
        Map<String, String> columnValMap = null;
        try {
            hibernateSession = getHibernateSession();
            notifyConfigQuery.append(
                    " SELECT CFG.OBJID,CFG.NOTIFY_CFG2BUS_ORG,CFG.NOTIFY_CFG2MODEL,ORG.NAME, MODEL.MODEL_NAME, ");
            notifyConfigQuery.append(" CFG.NOT_MOVING_NOTIFY_FLAG,CFG.LOW_FUEL_NOTIFY_FLAG, ");
            notifyConfigQuery.append(" CFG.FUELING_NOTIFY_FLAG, CFG.DWELL_NOTIFY_FLAG, ");
            notifyConfigQuery.append(" CFG.ENG_STATE_NOTIFY_FLAG, CFG.ENG_ON_ISOLATE_NOTIFY_FLAG, ");
            notifyConfigQuery.append(" CFG.PROXIMITY_NOTIFY_FLAG, CFG.INTERCHANGE_NOTIFY_FLAG, ");
            notifyConfigQuery.append(" CFG.CONSIST_NOTIFY_FLAG, CFG.NOT_MOVING_INFORM_THRESHOLD, ");
            notifyConfigQuery.append(" CFG.LOW_FUEL_ALERT_THRESHOLD, CFG.NOT_MOVING_ALERT_THRESHOLD, ");
            notifyConfigQuery.append(" CFG.LOW_FUEL_ALARM_THRESHOLD, CFG.DWELL_THRESHOLD, ");
            notifyConfigQuery.append(" CFG.NOT_MOVING_ALARM_THRESHOLD  FROM GETS_RMD_PP_NOTIFY_CFG CFG, ");
            notifyConfigQuery
                    .append(" GETS_RMD_MODEL MODEL, TABLE_BUS_ORG ORG  WHERE CFG.NOTIFY_CFG2MODEL = MODEL.OBJID ");
            notifyConfigQuery.append(" AND CFG.NOTIFY_CFG2BUS_ORG = ORG.OBJID  ");
            if (!RMDCommonUtility.isNullOrEmpty(customer)) {
                notifyConfigQuery
                        .append(" AND UPPER(ORG.ORG_ID) =UPPER(:customer) ORDER BY UPPER(MODEL.MODEL_NAME) ASC");
            }
            hibernateQuery = hibernateSession.createSQLQuery(notifyConfigQuery.toString());
            if (!RMDCommonUtility.isNullOrEmpty(customer)) {
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER, customer);
            }
            hibernateQuery.setFetchSize(1000);
            AliasToEntityMapResultTransformer INSTANCE = new AliasToEntityMapResultTransformer();
            hibernateQuery.setResultTransformer(INSTANCE);
            List<Map<String, Object>> masterDataList = hibernateQuery.list();
            for (Map<String, Object> mapObj : masterDataList) {
                columnValMap = new TreeMap<String, String>();
                for (Map.Entry<String, Object> entry : mapObj.entrySet()) {

                    if (RMDCommonConstants.COLUMN_OBJID.equalsIgnoreCase(entry.getKey())) {
                        cfgObjId = RMDCommonUtility.convertObjectToString(entry.getValue());
                    } else if (RMDCommonConstants.COLUMN_NOTIFY_CFG2BUS_ORG.equalsIgnoreCase(entry.getKey())) {
                        busOrgObjId = RMDCommonUtility.convertObjectToString(entry.getValue());
                    } else if (RMDCommonConstants.COLUMN_NOTIFY_CFG2MODEL.equalsIgnoreCase(entry.getKey())) {
                        modelObjId = RMDCommonUtility.convertObjectToString(entry.getValue());
                    } else if (RMDCommonConstants.COLUMN_NAME.equalsIgnoreCase(entry.getKey())) {
                        customerName = RMDCommonUtility.convertObjectToString(entry.getValue());
                    } else if (RMDCommonConstants.COLUMN_MODEL_NAME.equalsIgnoreCase(entry.getKey())) {
                        model = RMDCommonUtility.convertObjectToString(entry.getValue());
                    } else {
                        if (null != entry.getValue()) {
                            columnValMap.put(entry.getKey(), RMDCommonUtility.convertObjectToString(entry.getValue()));
                        }
                    }
                }
                for (Map.Entry<String, String> flagMap : columnValMap.entrySet()) {
                    NotifyConfigVO objNotifyConfigVO = new NotifyConfigVO();
                    objNotifyConfigVO.setCfgobjId(cfgObjId);
                    objNotifyConfigVO.setOrgObjId(busOrgObjId);
                    objNotifyConfigVO.setModelObjId(modelObjId);
                    objNotifyConfigVO.setCustomer(customerName);
                    objNotifyConfigVO.setModel(model);
                    objNotifyConfigVO.setCfgParamName(flagMap.getKey());
                    objNotifyConfigVO.setCfgValue(flagMap.getValue());
                    arlNotifyConfigVOs.add(objNotifyConfigVO);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return arlNotifyConfigVOs;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description: This method is used for Adding a Notify Configuration.
     */

    @Override
    public String addNotifyConfig(AddNotifyConfigVO objAddNotifyConfigVO) throws RMDDAOException {
        String status = RMDCommonConstants.FAILURE;
        Session hibernateSession = null;
        StringBuilder updateNotifyCfgQuery = null;
        Query hibernateQuery = null;
        Transaction transaction = null;
        String cfgObjId = null;
        StringBuilder insertionQuery = null;
        try {
            hibernateSession = getHibernateSession();
            String orgObjId = getOrgObjId(objAddNotifyConfigVO.getCustomer());
            /*
             * If Model is selected as All then Adding the Config Flag/Threshold
             * Values to All Models of a particular Customer
             */
            if (null != orgObjId) {
                if (RMDCommonConstants.ALL.equalsIgnoreCase(objAddNotifyConfigVO.getModel())) {
                    List<String> modelObjIdList = getModelObjIds(objAddNotifyConfigVO.getCustomer());
                    StringBuilder checkConfigQuery = new StringBuilder();
                    /*
                     * Checking if There is an entry for particualr customer and
                     * model in GETS_RMD_PP_NOTIFY_CFG table
                     */
                    checkConfigQuery.append(
                            "SELECT OBJID FROM GETS_RMD_PP_NOTIFY_CFG WHERE NOTIFY_CFG2BUS_ORG =:orgObjId AND NOTIFY_CFG2MODEL=:modelObjId");
                    transaction = hibernateSession.getTransaction();
                    /* Begin of Transaction */
                    transaction.begin();
                    for (String modelObjId : modelObjIdList) {
                        hibernateQuery = hibernateSession.createSQLQuery(checkConfigQuery.toString());
                        hibernateQuery.setParameter(RMDCommonConstants.ORG_OBJID, orgObjId);
                        hibernateQuery.setParameter(RMDCommonConstants.MODEL_OBJID, modelObjId);
                        List<Object> arlist = hibernateQuery.list();
                        if (RMDCommonUtility.isCollectionNotEmpty(arlist)) {
                            cfgObjId = RMDCommonUtility.convertObjectToString(arlist.get(0));
                        }
                        /*
                         * IF Entry is present in GETS_RMD_PP_NOTIFY_CFG we will
                         * update the same row
                         */
                        if (null != cfgObjId) {
                            updateNotifyCfgQuery = new StringBuilder();
                            updateNotifyCfgQuery.append(
                                    "UPDATE GETS_RMD_PP_NOTIFY_CFG SET LAST_UPDATED_DATE=SYSDATE, CREATION_DATE=SYSDATE , CREATED_BY =:user, ");

                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                                updateNotifyCfgQuery
                                        .append(objAddNotifyConfigVO.getNotifyFlagName() + " =:notifyFlagValue ,");
                            }
                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                                updateNotifyCfgQuery
                                        .append(objAddNotifyConfigVO.getThresholdName() + " =:thresholdValue ,");
                            }
                            updateNotifyCfgQuery.append(" LAST_UPDATED_BY =:user WHERE OBJID=:objId");

                            hibernateQuery = hibernateSession.createSQLQuery(updateNotifyCfgQuery.toString());
                            hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID, cfgObjId);
                        }
                        /* If entry is not there we will insert a new Entry */
                        else {
                            insertionQuery = new StringBuilder();
                            insertionQuery.append(
                                    "INSERT INTO GETS_RMD_PP_NOTIFY_CFG (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, NOTIFY_CFG2BUS_ORG, NOTIFY_CFG2MODEL,");
                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                                insertionQuery.append(
                                        objAddNotifyConfigVO.getNotifyFlagName() + RMDCommonConstants.COMMMA_SEPARATOR);
                            }
                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                                insertionQuery.append(
                                        objAddNotifyConfigVO.getThresholdName() + RMDCommonConstants.COMMMA_SEPARATOR);
                            }
                            insertionQuery.append(
                                    " CREATED_BY ) VALUES(GETS_RMD_PP_NOTIFY_CFG_SEQ.NEXTVAL,SYSDATE,:user,SYSDATE,:orgObjId, :modelObjId,");
                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                                insertionQuery.append(":notifyFlagValue, ");
                            }
                            if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                                insertionQuery.append(":thresholdValue, ");
                            }
                            insertionQuery.append("SYSDATE )");
                            hibernateQuery = hibernateSession.createSQLQuery(insertionQuery.toString());
                            hibernateQuery.setParameter(RMDCommonConstants.ORG_OBJID, orgObjId);
                            hibernateQuery.setParameter(RMDCommonConstants.MODEL_OBJID, modelObjId);
                        }
                        /*
                         * This block of code is common for both the conditions
                         */
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                            hibernateQuery.setParameter(RMDCommonConstants.NOTIFY_FLAG_VALUE,
                                    objAddNotifyConfigVO.getNotifyFlagValue());
                        }
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                            hibernateQuery.setParameter(RMDCommonConstants.THRESHOLD_VALUE,
                                    objAddNotifyConfigVO.getThresholdValue());
                        }
                        hibernateQuery.setParameter(RMDCommonConstants.USER, objAddNotifyConfigVO.getUserName());
                        hibernateQuery.executeUpdate();
                    }
                    /*
                     * Commiting the transaction after Sucessful
                     * Insertion/Update
                     */
                    transaction.commit();
                    status = RMDCommonConstants.SUCCESS;
                }
                /*
                 * If Model is not All we update/Insert only for that particular
                 * Customer
                 */
                else {
                    StringBuilder checkConfigQuery = new StringBuilder();
                    /*
                     * Checking if There is an entry for particualr customer and
                     * model in GETS_RMD_PP_NOTIFY_CFG table
                     */
                    checkConfigQuery.append(
                            "SELECT OBJID FROM GETS_RMD_PP_NOTIFY_CFG WHERE NOTIFY_CFG2BUS_ORG =:orgObjId AND NOTIFY_CFG2MODEL=:modelObjId");
                    hibernateQuery = hibernateSession.createSQLQuery(checkConfigQuery.toString());
                    hibernateQuery.setParameter(RMDCommonConstants.MODEL_OBJID, objAddNotifyConfigVO.getModelObjId());
                    hibernateQuery.setParameter(RMDCommonConstants.ORG_OBJID, orgObjId);
                    List<Object> arlist = hibernateQuery.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlist)) {
                        cfgObjId = RMDCommonUtility.convertObjectToString(arlist.get(0));
                    }
                    /*
                     * IF Entry is present in GETS_RMD_PP_NOTIFY_CFG we will
                     * update the same row
                     */
                    if (null != cfgObjId) {
                        updateNotifyCfgQuery = new StringBuilder();
                        updateNotifyCfgQuery.append(
                                "UPDATE GETS_RMD_PP_NOTIFY_CFG SET LAST_UPDATED_DATE=SYSDATE, CREATION_DATE=SYSDATE , CREATED_BY =:user, ");

                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                            updateNotifyCfgQuery
                                    .append(objAddNotifyConfigVO.getNotifyFlagName() + " =:notifyFlagValue ,");
                        }
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                            updateNotifyCfgQuery
                                    .append(objAddNotifyConfigVO.getThresholdName() + " =:thresholdValue ,");
                        }
                        updateNotifyCfgQuery.append("LAST_UPDATED_BY =:user WHERE OBJID=:objId");

                        hibernateQuery = hibernateSession.createSQLQuery(updateNotifyCfgQuery.toString());
                        hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID, cfgObjId);
                    }
                    /* If entry is not there we will insert a new Entry */
                    else {
                        insertionQuery = new StringBuilder();
                        insertionQuery.append(
                                "INSERT INTO GETS_RMD_PP_NOTIFY_CFG (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, NOTIFY_CFG2BUS_ORG, NOTIFY_CFG2MODEL,");
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                            insertionQuery.append(
                                    objAddNotifyConfigVO.getNotifyFlagName() + RMDCommonConstants.COMMMA_SEPARATOR);
                        }
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                            insertionQuery.append(
                                    objAddNotifyConfigVO.getThresholdName() + RMDCommonConstants.COMMMA_SEPARATOR);
                        }
                        insertionQuery.append(
                                " CREATED_BY ) VALUES(GETS_RMD_PP_NOTIFY_CFG_SEQ.NEXTVAL,SYSDATE,:user,SYSDATE,:orgObjId, :modelObjId,");
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                            insertionQuery.append(":notifyFlagValue, ");
                        }
                        if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdName())) {
                            insertionQuery.append(":thresholdValue, ");
                        }
                        insertionQuery.append("SYSDATE )");
                        hibernateQuery = hibernateSession.createSQLQuery(insertionQuery.toString());
                        hibernateQuery.setParameter(RMDCommonConstants.ORG_OBJID, orgObjId);
                        hibernateQuery.setParameter(RMDCommonConstants.MODEL_OBJID,
                                objAddNotifyConfigVO.getModelObjId());
                    }
                    /* This block of code is common for both the conditions */
                    if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getNotifyFlagName())) {
                        hibernateQuery.setParameter(RMDCommonConstants.NOTIFY_FLAG_VALUE,
                                objAddNotifyConfigVO.getNotifyFlagValue());
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigVO.getThresholdValue())) {
                        hibernateQuery.setParameter(RMDCommonConstants.THRESHOLD_VALUE,
                                objAddNotifyConfigVO.getThresholdValue());
                    }
                    hibernateQuery.setParameter(RMDCommonConstants.USER, objAddNotifyConfigVO.getUserName());
                    hibernateQuery.executeUpdate();
                    status = RMDCommonConstants.SUCCESS;
                }

            }
        } catch (Exception e) {
            /* Rolling back the Transaction */
            if (null != transaction) {
                transaction.rollback();
            }
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description: This method is used for Updating a Notify Configuration.
     */

    @Override
    public String updateNotifyConfig(List<NotifyConfigVO> arlNotifyConfigVOs) throws RMDDAOException {
        String status = RMDCommonConstants.FAILURE;
        Session hibernateSession = null;
        StringBuilder updateNotifyCfgQuery = null;
        Query hibernateQuery = null;
        Transaction transaction = null;
        try {
            hibernateSession = getHibernateSession();
            transaction = hibernateSession.getTransaction();
            transaction.begin();
            for (NotifyConfigVO objNotifyConfigVO : arlNotifyConfigVOs) {
                updateNotifyCfgQuery = new StringBuilder();
                updateNotifyCfgQuery.append(
                        "UPDATE GETS_RMD_PP_NOTIFY_CFG SET LAST_UPDATED_DATE=SYSDATE, CREATION_DATE=SYSDATE  , LAST_UPDATED_BY =:user, CREATED_BY =:user, ");
                updateNotifyCfgQuery
                        .append(objNotifyConfigVO.getCfgParamName() + " =:cfgParamValue WHERE OBJID =:objId");
                hibernateQuery = hibernateSession.createSQLQuery(updateNotifyCfgQuery.toString());
                hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID, objNotifyConfigVO.getCfgobjId());
                hibernateQuery.setParameter(RMDCommonConstants.USER, objNotifyConfigVO.getUserName());
                hibernateQuery.setParameter(RMDCommonConstants.CFG_PARAM_VALUE, objNotifyConfigVO.getCfgValue());
                hibernateQuery.executeUpdate();
            }
            transaction.commit();
            status = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            if(null != transaction)
                transaction.rollback();
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return status;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDDAOException
     * @Description: This method is used for fetching notify flags.
     */

    @Override
    public List<String> getNotifyFlags() throws RMDDAOException {
        Session hibernateSession = null;
        List<String> arlNotifyFlags = new ArrayList<String>();
        StringBuilder notifyFlagsQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            hibernateSession = getHibernateSession();
            notifyFlagsQuery.append(
                    " SELECT LOWER(COLUMN_NAME) AS NOTIFY_FLAG  FROM ALL_TAB_COLUMNS  WHERE TABLE_NAME = UPPER('GETS_RMD_PP_NOTIFY_CFG') AND COLUMN_NAME LIKE UPPER('%flag') ORDER BY NOTIFY_FLAG ");
            hibernateQuery = hibernateSession.createSQLQuery(notifyFlagsQuery.toString());
            List<Object> arlNotifyObjects = hibernateQuery.list();
            for (Object notifyFlagObj : arlNotifyObjects) {
                arlNotifyFlags.add(RMDCommonUtility.convertObjectToString(notifyFlagObj));
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlNotifyFlags;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :AddNotifyConfigVO
     *            objAddNotifyConfigVO
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Threshold values.
     */

    @Override
    public List<String> getThreshold() throws RMDDAOException {
        Session hibernateSession = null;
        List<String> arlThresholds = new ArrayList<String>();
        StringBuilder notifyFlagsQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            hibernateSession = getHibernateSession();
            notifyFlagsQuery.append(
                    " SELECT LOWER(COLUMN_NAME) AS THRESHOLD  FROM ALL_TAB_COLUMNS  WHERE TABLE_NAME = UPPER('GETS_RMD_PP_NOTIFY_CFG') AND COLUMN_NAME LIKE UPPER('%threshold') ORDER BY THRESHOLD ");
            hibernateQuery = hibernateSession.createSQLQuery(notifyFlagsQuery.toString());
            List<Object> arlThresholdObjects = hibernateQuery.list();
            for (Object notifyFlagObj : arlThresholdObjects) {
                arlThresholds.add(RMDCommonUtility.convertObjectToString(notifyFlagObj));
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlThresholds;
    }

    /**
     * @Author :
     * @return :String
     * @param :String
     *            customerObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Model ObjId values.
     */

    public List<String> getModelObjIds(String customer) {
        List<String> modelObjIdList = new ArrayList<String>();
        Session objSession = null;
        StringBuilder modelObjIdQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            objSession = getHibernateSession();
            modelObjIdQuery.append(
                    "SELECT DISTINCT MODEL.OBJID FROM  GETS_RMD_MODEL MODEL  , GETS_RMD_VEHICLE ASSET  ,  GETS_RMD_FLEET FLEET  , ");
            modelObjIdQuery.append(
                    " TABLE_BUS_ORG CUSTOMER  WHERE  ASSET.VEHICLE2MODEL = MODEL.OBJID  AND  ASSET.VEHICLE2FLEET = FLEET.OBJID  ");
            modelObjIdQuery.append("AND  CUSTOMER.OBJID =FLEET.FLEET2BUS_ORG  AND  CUSTOMER.ORG_ID IN(:customer)");
            hibernateQuery = objSession.createSQLQuery(modelObjIdQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER, customer);
            List<Object> arlModelsObjs = hibernateQuery.list();
            for (Object modelObj : arlModelsObjs) {
                modelObjIdList.add(RMDCommonUtility.convertObjectToString(modelObj));
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return modelObjIdList;
    }

    /**
     * @Author :
     * @return :String
     * @param :String
     *            Customer
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Model ObjId values.
     */

    public String getOrgObjId(String customer) {
        String orgObjId = null;
        Session objSession = null;
        StringBuilder orgObjIdQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            objSession = getHibernateSession();
            orgObjIdQuery.append("SELECT OBJID FROM TABLE_BUS_ORG WHERE ORG_ID=:customer");
            hibernateQuery = objSession.createSQLQuery(orgObjIdQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER, customer);
            List<Object> arlOrgObjs = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlOrgObjs)) {
                orgObjId = RMDCommonUtility.convertObjectToString(arlOrgObjs.get(0));
            }

        } catch (Exception e) {
            LOG.error("Exception occurred getOrgObjId() method : " + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return orgObjId;
    }

    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :String
     *            customerID, String proxStatus
     * @throws :RMDDAOException
     * @Description: This method is used to get the proximity details for the
     *               selected customer.
     */
    @Override
    public List<ProximityDetailsVO> getProximityDetails(String customerID, String proxStatus) throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        String proxType;
        String proxParent;
        String custProxParent;
        ProximityDetailsVO objProximityDetailsVO = null;
        List<ProximityDetailsVO> proximityList = new ArrayList<ProximityDetailsVO>();
        try {
            session = getHibernateSession();
            StringBuilder proximityQuery = new StringBuilder();
            proximityQuery.append(
                    " SELECT objid, active_flag, cust_proximity_id, smpp_proximity_id, proximity_desc, proximity_type, proximity_parent,");
            proximityQuery.append(
                    "upper_left_lat, upper_left_lon, lower_right_lat, lower_right_lon, arrival_flag, departure_flag, dwell_flag,");
            proximityQuery.append(
                    " interchange_flag,consist_flag FROM gets_rmd_pp_proximity_def WHERE proximity2bus_org = (SELECT OBJID FROM table_bus_org ");
            proximityQuery.append("WHERE ORG_ID = :customerId) ");
            if (proxStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_ACTIVE)) {
                proximityQuery.append("AND active_flag = 'Y' ORDER BY cust_proximity_id");
            } else if (proxStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_INACTIVE)) {
                proximityQuery.append("AND (active_flag = 'N' OR active_flag IS null) ORDER BY cust_proximity_id");
            } else if (proxStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_BOTH)) {
                proximityQuery.append("AND (active_flag = 'N' OR active_flag = 'Y') ORDER BY cust_proximity_id");
            }
            Query proxHqry = session.createSQLQuery(proximityQuery.toString());
            proxHqry.setParameter(RMDCommonConstants.CUSTOMER_ID, customerID);
            resultList = proxHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    proxType = RMDServiceConstants.EMPTY_STRING;
                    proxParent = null;
                    final Object[] proxmityDetails = obj.next();
                    objProximityDetailsVO = new ProximityDetailsVO();
                    objProximityDetailsVO.setProxObjId(RMDCommonUtility.convertObjectToString(proxmityDetails[0]));
                    objProximityDetailsVO.setActiveFlag(RMDCommonUtility.convertObjectToString(proxmityDetails[1]));
                    objProximityDetailsVO.setCustProxId(RMDCommonUtility.convertObjectToString(proxmityDetails[2]));
                    objProximityDetailsVO.setSmppProxId((RMDCommonUtility.convertObjectToString(proxmityDetails[3])));
                    objProximityDetailsVO.setProxDesc(
                            ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(proxmityDetails[4])));
                    proxType = RMDCommonUtility.convertObjectToString(proxmityDetails[5]);
                    objProximityDetailsVO.setProxType(proxType);

                    if (proxType.equalsIgnoreCase(RMDCommonConstants.TWO)) {
                        proxParent = RMDCommonUtility.convertObjectToString(proxmityDetails[6]);
                        if (null != proxParent) {
                            custProxParent = getCustProxParent(proxParent);
                            objProximityDetailsVO.setProxParent(custProxParent);
                        } else
                            objProximityDetailsVO
                                    .setProxParent(RMDCommonUtility.convertObjectToString(proxmityDetails[4]));
                    } else {
                        objProximityDetailsVO.setProxParent(proxParent);
                    }
                    objProximityDetailsVO.setUpperLeftLat(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(proxmityDetails[7])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    objProximityDetailsVO.setUpperLeftLon(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(proxmityDetails[8])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    objProximityDetailsVO.setLowerRightLat(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(proxmityDetails[9])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    objProximityDetailsVO.setLowerRightLon(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(proxmityDetails[10])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    objProximityDetailsVO.setArrivalFlag(RMDCommonUtility.convertObjectToString(proxmityDetails[11]));
                    objProximityDetailsVO.setDepartureFlag(RMDCommonUtility.convertObjectToString(proxmityDetails[12]));
                    objProximityDetailsVO.setDwelFlag(RMDCommonUtility.convertObjectToString(proxmityDetails[13]));
                    objProximityDetailsVO
                            .setInterchangeFlag((RMDCommonUtility.convertObjectToString(proxmityDetails[14])));
                    objProximityDetailsVO.setConsistFlag(RMDCommonUtility.convertObjectToString(proxmityDetails[15]));

                    proximityList.add(objProximityDetailsVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PROXIMITY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PROXIMITY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

        return proximityList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<ProximityDetailsVO>
     * @throws :RMDDAOException
     * @Description: This method is used to update selected proximity details
     */
    @Override
    public String updateProximity(List<ProximityDetailsVO> proxDetailsList) throws RMDDAOException {

        String proxType;
        String parentTitle;
        String activeFlag;
        String proxParent;
        String arrivalFlag = null;
        String departFlag = null;
        String interChangeFlag = null;
        String consistFlag = null;
        String dwelFlag = null;
        Session session = null;
        StringBuilder proxUpdateQry = null;
        StringBuilder fixedValQry = new StringBuilder();
        Query hibernateQuery = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.SUCCESS;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();

            fixedValQry.append(
                    "UPDATE gets_rmd_pp_proximity_def SET last_updated_date = sysdate, last_updated_by = :userId, upper_left_lat = :upperLeftLat,proximity_desc=:proxDesc,");
            fixedValQry.append(
                    "upper_left_lon = :upperLeftLon, lower_right_lat = :lowerRightLat, lower_right_lon = :lowerRightLon, proximity_type = :proxType,active_flag = :activeFlag");
          
            for (ProximityDetailsVO proxVO : proxDetailsList) {
                proxUpdateQry = new StringBuilder();
                proxType = proxVO.getProxType();
                activeFlag = proxVO.getActiveFlag();
                arrivalFlag = proxVO.getArrivalFlag();
                departFlag = proxVO.getDepartureFlag();
                interChangeFlag = proxVO.getInterchangeFlag();
                dwelFlag = proxVO.getDwelFlag();
                consistFlag = proxVO.getConsistFlag();
                proxParent = proxVO.getProxParent();
                parentTitle = null;
                proxUpdateQry = proxUpdateQry.append(fixedValQry);
                if (proxType.equalsIgnoreCase("2")) {
                    parentTitle = getProxParentTitle(proxParent);
                }
                if (!RMDCommonUtility.isNullOrEmpty(arrivalFlag)) {
                    proxUpdateQry.append(", arrival_flag = :arrivalFlag");
                } else
                    proxUpdateQry.append(", arrival_flag = null");

                if (!RMDCommonUtility.isNullOrEmpty(departFlag)) {
                    proxUpdateQry.append(", departure_flag = :departureFlag");
                } else
                    proxUpdateQry.append(", departure_flag = null");
                if (!RMDCommonUtility.isNullOrEmpty(dwelFlag)) {
                    proxUpdateQry.append(", dwell_flag = :dwelFlag");
                } else
                    proxUpdateQry.append(", dwell_flag = null");
                if (!RMDCommonUtility.isNullOrEmpty(consistFlag)) {
                    proxUpdateQry.append(", consist_flag = :consistFlag");
                } else
                    proxUpdateQry.append(", consist_flag = null");
                if (!RMDCommonUtility.isNullOrEmpty(interChangeFlag)) {
                    proxUpdateQry.append(", interchange_flag = :interChangeFlag");
                } else
                    proxUpdateQry.append(", interchange_flag = null");
                if (!RMDCommonUtility.isNullOrEmpty(parentTitle)) {
                    proxUpdateQry.append(", proximity_parent = :proxParent");
                } else
                    proxUpdateQry.append(", proximity_parent = null");
                proxUpdateQry.append(" WHERE objid = :proxObjId");                
                hibernateQuery = session.createSQLQuery(proxUpdateQry.toString());
                hibernateQuery.setParameter(RMDServiceConstants.USER_ID, proxVO.getUserId());
                hibernateQuery.setParameter(RMDServiceConstants.UPPPER_LEFT_LAT,
                        String.valueOf(Double.parseDouble(proxVO.getUpperLeftLat()) * 3600000));
                hibernateQuery.setParameter(RMDServiceConstants.UPPPER_LEFT_LON,
                        String.valueOf(Double.parseDouble(proxVO.getUpperLeftLon()) * 3600000));
                hibernateQuery.setParameter(RMDServiceConstants.LOWER_RIGHT_LAT,
                        String.valueOf(Double.parseDouble(proxVO.getLowerRightLat()) * 3600000));
                hibernateQuery.setParameter(RMDServiceConstants.LOWER_RIGHT_LON,
                        String.valueOf(Double.parseDouble(proxVO.getLowerRightLon()) * 3600000));
                hibernateQuery.setParameter(RMDServiceConstants.PROX_DESC,
                        ESAPI.encoder().decodeForHTML(proxVO.getProxDesc()));
                hibernateQuery.setParameter(RMDServiceConstants.PROX_TYPE, proxType);
                if (!RMDCommonUtility.isNullOrEmpty(parentTitle)) {
                    hibernateQuery.setParameter(RMDServiceConstants.PROX_PARENT, parentTitle);
                }
                if (!RMDCommonUtility.isNullOrEmpty(arrivalFlag)) {
                    hibernateQuery.setParameter(RMDServiceConstants.ARRIVAL_FLAG, arrivalFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(departFlag)) {
                    hibernateQuery.setParameter(RMDServiceConstants.DEPARTURE_FLAG, departFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(consistFlag)) {
                    hibernateQuery.setParameter(RMDServiceConstants.CONSIST_FLAG, consistFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(dwelFlag)) {
                    hibernateQuery.setParameter(RMDServiceConstants.DWEL_FLAG, dwelFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(interChangeFlag)) {
                    hibernateQuery.setParameter(RMDServiceConstants.INTERCHANGE_FLAG, interChangeFlag);
                }
                hibernateQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG,
                        activeFlag != null ? activeFlag : RMDServiceConstants.ALPHABET_N);                
                hibernateQuery.setParameter(RMDServiceConstants.PROX_OBJ_ID, proxVO.getProxObjId());
                hibernateQuery.executeUpdate();

            }

            transaction.commit();
        } catch (ConstraintViolationException e) {
            LOG.error(RMDCommonConstants.RECORD_EXIST_WITH_CURRENT_VALUES);
            status = RMDServiceConstants.VALUE_EXIST;

        } catch (Exception e) {
            transaction.rollback();
            status = RMDServiceConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_PROXIMITY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return status;

    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            ProximityDetailsVO
     * @throws :RMDDAOException
     * @Description: This method is used to add proximity
     */
    @Override
    public String addNewProximity(ProximityDetailsVO objProxDetails) throws RMDDAOException {
        Session session = null;
        String status = RMDServiceConstants.SUCCESS;
        StringBuilder existProxQry = new StringBuilder();
        try {
            session = getHibernateSession();
            existProxQry.append(
                    "SELECT * FROM gets_rmd_pp_proximity_def WHERE proximity2bus_org = (SELECT OBJID FROM table_bus_org");
            existProxQry.append(
                    " WHERE ORG_ID = :customerId) AND (cust_proximity_id = :proxId OR proximity_desc = :proxDesc)");
            Query hQry = session.createSQLQuery(existProxQry.toString());
            hQry.setParameter(RMDCommonConstants.CUSTOMER_ID, objProxDetails.getCustomerName());
            hQry.setParameter(RMDCommonConstants.PROXIMITY_ID, objProxDetails.getCustProxId());
            hQry.setParameter(RMDCommonConstants.PROXIMITY_DESC, objProxDetails.getProxDesc());
            ArrayList existProxDetails = (ArrayList) hQry.list();
            if (null != existProxDetails && !existProxDetails.isEmpty()) {
                status = RMDServiceConstants.ID_EXIST;
            } else {
                StringBuilder insertProxQryCol = new StringBuilder();
                StringBuilder insertProxQryVal = new StringBuilder();
                StringBuilder mainQuery = new StringBuilder();
                String proxType = objProxDetails.getProxType();
                String activeFlag = objProxDetails.getActiveFlag();
                String arrivalFlag = objProxDetails.getArrivalFlag();
                String departFlag = objProxDetails.getDepartureFlag();
                String interChangeFlag = objProxDetails.getInterchangeFlag();
                String dwelFlag = objProxDetails.getDwelFlag();
                String consistFlag = objProxDetails.getConsistFlag();
                String proxParent = objProxDetails.getProxParent();
                String parentTitle = null;
                String busOrgId = getBusOrgId(objProxDetails.getCustomerName());
               
                insertProxQryCol.append(
                        "INSERT INTO gets_rmd_pp_proximity_def (objid,last_updated_date,last_updated_by,creation_date,created_by,");
                insertProxQryCol.append(
                        "upper_left_lat,upper_left_lon,lower_right_lat,lower_right_lon,proximity_desc,proximity_type,proximity2bus_org,");
                insertProxQryCol.append("active_flag,cust_proximity_id");
                insertProxQryVal.append(
                        ") VALUES (gets_rmd_pp_proximity_def_Seq.nextval,sysdate,:userId,sysdate,:userId,:upperLeftLat,:upperLeftLon,:lowerRightLat,");
                insertProxQryVal.append(":lowerRightLon,:proxDesc,:proxType,:busOrgId,:activeFlag,:proxId");
                if (proxType.equalsIgnoreCase(RMDCommonConstants.TWO)) {
                    if (!RMDCommonUtility.isNullOrEmpty(proxParent))
                        parentTitle = getProxParentTitle(proxParent);
                }
                if (!RMDCommonUtility.isNullOrEmpty(arrivalFlag)) {
                    insertProxQryCol.append(", arrival_flag");
                    insertProxQryVal.append(", :arrivalFlag");
                }
                if (!RMDCommonUtility.isNullOrEmpty(departFlag)) {
                    insertProxQryCol.append(", departure_flag");
                    insertProxQryVal.append(", :departureFlag");
                }
                if (!RMDCommonUtility.isNullOrEmpty(dwelFlag)) {
                    insertProxQryCol.append(", dwell_flag");
                    insertProxQryVal.append(", :dwelFlag");
                }
                if (!RMDCommonUtility.isNullOrEmpty(consistFlag)) {
                    insertProxQryCol.append(", consist_flag");
                    insertProxQryVal.append(", :consistFlag");
                }
                if (!RMDCommonUtility.isNullOrEmpty(interChangeFlag)) {
                    insertProxQryCol.append(", interchange_flag");
                    insertProxQryVal.append(", :interChangeFlag");
                }
                if (!RMDCommonUtility.isNullOrEmpty(parentTitle)) {
                    insertProxQryCol.append(", proximity_parent");
                    insertProxQryVal.append(", :proxParent");
                }
                mainQuery = mainQuery.append(insertProxQryCol);
                mainQuery = mainQuery.append(insertProxQryVal).append(")");
                Query hQuery = session.createSQLQuery(mainQuery.toString());
                hQuery.setParameter(RMDServiceConstants.USER_ID, objProxDetails.getUserId());                
                hQuery.setParameter(RMDServiceConstants.UPPPER_LEFT_LAT,
                        String.valueOf(Double.parseDouble(objProxDetails.getUpperLeftLat()) * 3600000));
                hQuery.setParameter(RMDServiceConstants.UPPPER_LEFT_LON,
                        String.valueOf(Double.parseDouble(objProxDetails.getUpperLeftLon()) * 3600000));
                hQuery.setParameter(RMDServiceConstants.LOWER_RIGHT_LAT,
                        String.valueOf(Double.parseDouble(objProxDetails.getLowerRightLat()) * 3600000));
                hQuery.setParameter(RMDServiceConstants.LOWER_RIGHT_LON,
                        String.valueOf(Double.parseDouble(objProxDetails.getLowerRightLon()) * 3600000));
                hQuery.setParameter(RMDServiceConstants.PROX_DESC,
                        ESAPI.encoder().decodeForHTML(objProxDetails.getProxDesc()));
                hQuery.setParameter(RMDServiceConstants.PROX_TYPE, objProxDetails.getProxType());
                if (!RMDCommonUtility.isNullOrEmpty(parentTitle)) {
                    hQuery.setParameter(RMDServiceConstants.PROX_PARENT, parentTitle);
                }
                if (!RMDCommonUtility.isNullOrEmpty(arrivalFlag)) {
                    hQuery.setParameter(RMDServiceConstants.ARRIVAL_FLAG, arrivalFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(departFlag)) {
                    hQuery.setParameter(RMDServiceConstants.DEPARTURE_FLAG, departFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(consistFlag)) {
                    hQuery.setParameter(RMDServiceConstants.CONSIST_FLAG, consistFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(dwelFlag)) {
                    hQuery.setParameter(RMDServiceConstants.DWEL_FLAG, dwelFlag);
                }
                if (!RMDCommonUtility.isNullOrEmpty(interChangeFlag)) {
                    hQuery.setParameter(RMDServiceConstants.INTERCHANGE_FLAG, interChangeFlag);
                }
                hQuery.setParameter(RMDServiceConstants.BUS_ORG_ID, busOrgId);
                hQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG,
                        activeFlag != null ? activeFlag : RMDServiceConstants.ALPHABET_N);
                hQuery.setParameter(RMDServiceConstants.PROXIMITY_ID, objProxDetails.getCustProxId());
               
                hQuery.executeUpdate();

            }
        } catch (ConstraintViolationException e) {
            LOG.error(RMDCommonConstants.RECORD_EXIST_WITH_CURRENT_VALUES);
            status = RMDServiceConstants.VALUE_EXIST;

        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_PROXIMITY);

            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
        return status;
    }

    /**
     * @Author:
     * @param :ProximityDetailsVO
     * @throws:RMDDAOException
     * @Description: This method is used to delete the proximities
     */
    @Override
    public void deleteProximity(ProximityDetailsVO objProximityDetailsVO) throws RMDDAOException {
        Session session = null;
        String delProxObjId = null;

        try {
            StringBuilder proxDeleteQuery = new StringBuilder();
            session = getHibernateSession();
            delProxObjId = objProximityDetailsVO.getProxObjId();
            if (null != delProxObjId && !delProxObjId.isEmpty()) {
                List<String> proxObjIdList = Arrays.asList(delProxObjId.split(RMDCommonConstants.COMMMA_SEPARATOR));
                proxDeleteQuery.append("DELETE FROM gets_rmd_pp_proximity_def WHERE objid in (:delProxObjId)");
                Query hQry = session.createSQLQuery(proxDeleteQuery.toString());
                hQry.setParameterList(RMDServiceConstants.DEL_PROX_OBJ_ID, proxObjIdList);
                hQry.executeUpdate();
            }

        } catch (Exception e) {
            LOG.error("Exception occurred at deleteProximity  method of ConfigGeofenceDAOImpl:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELETE_PROXIMITY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }

    }

    /**
     * @Author :
     * @return :List<ProximityDetailsVO>
     * @param :String
     *            customerID, String milePostStatus
     * @throws :RMDDAOException
     * @Description: This method is used to get the milepost details for the
     *               selected customer.
     */
    @Override
    public List<MilePostDetailsVO> getMilePostDetails(String customerID, String milePostStatus) throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        MilePostDetailsVO objMilePostDetailsVO = null;
        List<MilePostDetailsVO> milePostList = new ArrayList<MilePostDetailsVO>();
        try {
            session = getHibernateSession();
            StringBuilder milePostQuery = new StringBuilder();         
            milePostQuery.append(
                    "SELECT objid, active_flag, milepost_id, milepost_desc, state_province, region, sub_region, gps_lat, gps_lon");
            milePostQuery.append(
                    " FROM  gets_rmd_pp_milepost_def WHERE milepost2bus_org = (SELECT OBJID FROM table_bus_org WHERE ORG_ID = :customerId) ");
            if (milePostStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_ACTIVE)) {
                milePostQuery.append("AND active_flag = 'Y' ORDER BY milepost_id");
            } else if (milePostStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_INACTIVE)) {
                milePostQuery.append("AND (active_flag = 'N' OR active_flag IS null) ORDER BY milepost_id");
            } else if (milePostStatus.equalsIgnoreCase(RMDCommonConstants.STATUS_BOTH)) {
                milePostQuery.append(
                        "AND (active_flag = 'N' OR active_flag = 'Y' OR active_flag IS null) ORDER BY milepost_id");
            }            
            Query mpHquery = session.createSQLQuery(milePostQuery.toString());
            mpHquery.setParameter(RMDCommonConstants.CUSTOMER_ID, customerID);           
            resultList = mpHquery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    final Object[] milePostDetails = obj.next();
                    objMilePostDetailsVO = new MilePostDetailsVO();
                    objMilePostDetailsVO.setMilePostObjId(RMDCommonUtility.convertObjectToString(milePostDetails[0]));
                    objMilePostDetailsVO.setActiveFlag(RMDCommonUtility.convertObjectToString(milePostDetails[1]));
                    objMilePostDetailsVO.setMilePostId(RMDCommonUtility.convertObjectToString(milePostDetails[2]));
                    objMilePostDetailsVO.setMilePostDesc(
                            ESAPI.encoder().encodeForXML((RMDCommonUtility.convertObjectToString(milePostDetails[3]))));
                    objMilePostDetailsVO.setStateProvince(RMDCommonUtility.convertObjectToString(milePostDetails[4]));
                    objMilePostDetailsVO.setRegion(RMDCommonUtility.convertObjectToString(milePostDetails[5]));
                    objMilePostDetailsVO.setSubRegion(RMDCommonUtility.convertObjectToString(milePostDetails[6]));
                    objMilePostDetailsVO.setGpsLat(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(milePostDetails[7])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    objMilePostDetailsVO.setGpsLon(String.valueOf(RMDCommonUtility.round(
                            (Double.parseDouble(RMDCommonUtility.convertObjectToString(milePostDetails[8])) / 3600000),
                            RMDServiceConstants.ROUND_TO_DECIMALS)));
                    milePostList.add(objMilePostDetailsVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MILEPOST_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MILEPOST_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

        return milePostList;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            List<MilePostDetialsVO>
     * @throws :RMDDAOException
     * @Description: This method is used to update selected milepost details
     */

    @Override
    public String updateMilePost(List<MilePostDetailsVO> mpDetailsList) throws RMDDAOException {
        Session session = null;
        String activeFlag = null;
        String region = null;
        String subRegion = null;
        String state = null;
        StringBuilder milePostFixedQry = new StringBuilder();
        ;
        StringBuilder milePostUpdateQry = null;
        Query hibernateQuery = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.SUCCESS;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();

            milePostFixedQry.append(
                    "UPDATE gets_rmd_pp_milepost_def SET last_updated_date = sysdate,last_updated_by = :userId,");
            milePostFixedQry.append(
                    "milepost_desc =:milePostDesc,gps_lat =:gpsLat,gps_lon =:gpsLon,active_flag = :activeFlag ");

            for (MilePostDetailsVO milePostVO : mpDetailsList) {
                milePostUpdateQry = new StringBuilder();
                region = milePostVO.getRegion();
                subRegion = milePostVO.getSubRegion();
                state = milePostVO.getStateProvince();
                activeFlag = milePostVO.getActiveFlag();
                milePostUpdateQry.append(milePostFixedQry);
                if (!RMDCommonUtility.isNullOrEmpty(region)) {
                    milePostUpdateQry.append(",region = :region");
                } else
                    milePostUpdateQry.append(",region = null");
                if (!RMDCommonUtility.isNullOrEmpty(subRegion)) {
                    milePostUpdateQry.append(",sub_region =:subRegion");
                } else
                    milePostUpdateQry.append(",sub_region =null");
                if (!RMDCommonUtility.isNullOrEmpty(state)) {
                    milePostUpdateQry.append(",state_province = :stateProvince");
                } else
                    milePostUpdateQry.append(",state_province = null");
                milePostUpdateQry.append(" WHERE objid = :milePostObjId");
                hibernateQuery = session.createSQLQuery(milePostUpdateQry.toString());
                hibernateQuery.setParameter(RMDServiceConstants.USER_ID, milePostVO.getUserId());               
                hibernateQuery.setParameter(RMDServiceConstants.MILEPOST_DESC,
                        ESAPI.encoder().decodeForHTML(milePostVO.getMilePostDesc()));
                hibernateQuery.setParameter(RMDServiceConstants.GPS_LAT,
                        String.valueOf(Double.parseDouble(milePostVO.getGpsLat()) * 3600000));
                hibernateQuery.setParameter(RMDServiceConstants.GPS_LON,
                        String.valueOf(Double.parseDouble(milePostVO.getGpsLon()) * 3600000));
                if (!RMDCommonUtility.isNullOrEmpty(region)) {
                    hibernateQuery.setParameter(RMDServiceConstants.REGION, milePostVO.getRegion());
                }
                if (!RMDCommonUtility.isNullOrEmpty(subRegion)) {
                    hibernateQuery.setParameter(RMDServiceConstants.SUB_REGION, milePostVO.getSubRegion());
                }
                if (!RMDCommonUtility.isNullOrEmpty(state)) {
                    hibernateQuery.setParameter(RMDServiceConstants.STATE_PROVINCE, milePostVO.getStateProvince());
                }
                hibernateQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG,
                        activeFlag != null ? activeFlag : RMDServiceConstants.ALPHABET_N);

                hibernateQuery.setParameter(RMDServiceConstants.MILEPOST_OBJID, milePostVO.getMilePostObjId());
                hibernateQuery.executeUpdate();
            }
            transaction.commit();
        } catch (ConstraintViolationException e) {
            LOG.error(RMDCommonConstants.RECORD_EXIST_WITH_CURRENT_VALUES);
            status = RMDServiceConstants.VALUE_EXIST;

        } catch (Exception e) {
            transaction.rollback();
            status = RMDServiceConstants.FAILURE;
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_MILEPOST_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :
     *            MilePostDetailsVO
     * @throws :RMDDAOException
     * @Description: This method is used to add milepost
     */
    @Override
    public String addNewMilePost(MilePostDetailsVO objMilePostDetails) throws RMDDAOException {
        Session session = null;
        String status = RMDServiceConstants.SUCCESS;
        StringBuilder existMPQry = new StringBuilder();
        try {
            session = getHibernateSession();
            existMPQry.append(
                    "SELECT * FROM gets_rmd_pp_milepost_def WHERE milepost2bus_org = (SELECT OBJID FROM table_bus_org ");
            existMPQry.append(
                    " WHERE ORG_ID = :customerId) AND (milepost_id = :milePostId OR milepost_desc = :milePostDesc)");
            Query hQry = session.createSQLQuery(existMPQry.toString());
            hQry.setParameter(RMDCommonConstants.CUSTOMER_ID, objMilePostDetails.getCustomerName());
            hQry.setParameter(RMDCommonConstants.MILEPOST_ID, objMilePostDetails.getMilePostId());
            hQry.setParameter(RMDCommonConstants.MILEPOST_DESC, objMilePostDetails.getMilePostDesc());
            ArrayList existMPDetails = (ArrayList) hQry.list();
            if (null != existMPDetails && !existMPDetails.isEmpty()) {
                status = RMDServiceConstants.ID_EXIST;
            } else {
                StringBuilder insertMPColQry = new StringBuilder();
                StringBuilder insertValColQry = new StringBuilder();
                StringBuilder mainQuery = new StringBuilder();
                String activeFlag = objMilePostDetails.getActiveFlag();
                String region = objMilePostDetails.getRegion();
                String subRegion = objMilePostDetails.getSubRegion();
                String state = objMilePostDetails.getStateProvince();
                String busOrgId = getBusOrgId(objMilePostDetails.getCustomerName());
                insertMPColQry.append(
                        "INSERT INTO gets_rmd_pp_milepost_def (objid,last_updated_date,last_updated_by,creation_date,created_by,milepost_id,");
                insertMPColQry.append("milepost_desc,gps_lat,gps_lon,milepost2bus_org,active_flag,milepost_type");
                insertValColQry.append(
                        ") VALUES (gets_rmd_pp_milepost_def_Seq.nextval, sysdate,:userId, sysdate,:userId,:milePostId,:milePostDesc,:gpsLat,:gpsLon,:busOrgId,:activeFlag,null");
                if (!RMDCommonUtility.isNullOrEmpty(region)) {
                    insertMPColQry.append(",region");
                    insertValColQry.append(",:region");
                }
                if (!RMDCommonUtility.isNullOrEmpty(subRegion)) {
                    insertMPColQry.append(",sub_region");
                    insertValColQry.append(",:subRegion");
                }
                if (!RMDCommonUtility.isNullOrEmpty(state)) {
                    insertMPColQry.append(",state_province");
                    insertValColQry.append(",:stateProvince");
                }
                mainQuery = mainQuery.append(insertMPColQry);
                mainQuery = mainQuery.append(insertValColQry).append(")");
                Query hQuery = session.createSQLQuery(mainQuery.toString());
                hQuery.setParameter(RMDServiceConstants.USER_ID, objMilePostDetails.getUserId());
                hQuery.setParameter(RMDServiceConstants.MILEPOST_ID,
                        ESAPI.encoder().decodeForHTML(objMilePostDetails.getMilePostId()));
                hQuery.setParameter(RMDServiceConstants.MILEPOST_DESC,
                        ESAPI.encoder().decodeForHTML(objMilePostDetails.getMilePostDesc()));
                hQuery.setParameter(RMDServiceConstants.GPS_LAT,
                        String.valueOf(Double.parseDouble(objMilePostDetails.getGpsLat()) * 3600000));
                hQuery.setParameter(RMDServiceConstants.GPS_LON,
                        String.valueOf(Double.parseDouble(objMilePostDetails.getGpsLon()) * 3600000));
                if (!RMDCommonUtility.isNullOrEmpty(region)) {
                    hQuery.setParameter(RMDServiceConstants.REGION, objMilePostDetails.getRegion());
                }
                if (!RMDCommonUtility.isNullOrEmpty(subRegion)) {
                    hQuery.setParameter(RMDServiceConstants.SUB_REGION, objMilePostDetails.getSubRegion());
                }
                if (!RMDCommonUtility.isNullOrEmpty(state)) {
                    hQuery.setParameter(RMDServiceConstants.STATE_PROVINCE, objMilePostDetails.getStateProvince());
                }
                hQuery.setParameter(RMDServiceConstants.BUS_ORG_ID, busOrgId);
                hQuery.setParameter(RMDServiceConstants.ACTIVE_FLAG,
                        activeFlag != null ? activeFlag : RMDServiceConstants.ALPHABET_N);
                hQuery.executeUpdate();
            }
        } catch (ConstraintViolationException e) {
            LOG.error(RMDCommonConstants.RECORD_EXIST_WITH_CURRENT_VALUES);
            status = RMDServiceConstants.VALUE_EXIST;

        } catch (Exception e) {
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_MILEPOST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
        return status;
    }

    /**
     * @Author:
     * @param :MilePostDetailsVO
     * @throws:RMDDAOException
     * @Description: This method is used to delete the milepost
     */
    @Override
    public void deleteMilePost(MilePostDetailsVO objMilePostDetailsVO) throws RMDDAOException {
        Session session = null;
        String delMilePostObjId = null;

        try {
            StringBuilder mpDeleteQuery = new StringBuilder();
            session = getHibernateSession();
            delMilePostObjId = objMilePostDetailsVO.getMilePostObjId();
            if (null != delMilePostObjId && !delMilePostObjId.isEmpty()) {
                List<String> mpObjIdList = Arrays.asList(delMilePostObjId.split(RMDCommonConstants.COMMMA_SEPARATOR));
                mpDeleteQuery.append("DELETE FROM gets_rmd_pp_milepost_def WHERE objid in (:delMPObjId)");
                Query hQry = session.createSQLQuery(mpDeleteQuery.toString());
                hQry.setParameterList(RMDServiceConstants.DEL_MILEPOST_OBJ_ID, mpObjIdList);
                hQry.executeUpdate();
            }

        } catch (Exception e) {
            LOG.error("Exception occurred at deleteMilePost  method of ConfigGeofenceDAOImpl:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELETE_MILEPOST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }

    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of stateprovince.
     */
    @Override
    public List<String> getStateProvince() throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<String> stateProvinceList = new ArrayList<String>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append("SELECT DISTINCT name FROM sa.table_state_prov ORDER BY name");
            final Query objStateProvinceQuery = hibernateSession.createSQLQuery(strQuery.toString());
            List<Object> resultList = objStateProvinceQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (Object obj : resultList) {
                    stateProvinceList.add(RMDCommonUtility.convertObjectToString(obj));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATE_PROVINCE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATE_PROVINCE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return stateProvinceList;
    }

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDDAOException
     * @Description: This method is used to get proximity parent for the
     *               selected customer
     */
    @Override
    public List<String> getProximityParent(String customerId) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<String> proxParentList = new ArrayList<String>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append(
                    "SELECT CUST_PROXIMITY_ID FROM gets_rmd_pp_proximity_def WHERE PROXIMITY2BUS_ORG= (SELECT OBJID FROM ");
            strQuery.append(
                    "table_bus_org WHERE ORG_ID = :customerId) AND active_flag = 'Y' AND  proximity_type=1 order by lower(CUST_PROXIMITY_ID)");
            final Query objproxParentQry = hibernateSession.createSQLQuery(strQuery.toString());
            objproxParentQry.setParameter(RMDServiceConstants.CUSTOMER_ID, customerId);
            List<Object> resultList = objproxParentQry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (Object obj : resultList) {
                    proxParentList.add(RMDCommonUtility.convertObjectToString(obj));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PROXIMITY_PARENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PROXIMITY_PARENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return proxParentList;
    }

    private String getCustProxParent(String proxParent) {
        Session session = null;
        List<Object[]> parent = null;
        String custProxParent = null;
        String query = "SELECT cust_proximity_id FROM gets_rmd_pp_proximity_def WHERE objid = :proxParent";
        try {
            session = getHibernateSession();
            Query hQry = session.createSQLQuery(query);
            hQry.setParameter(RMDServiceConstants.PROXIMITY_PARENT, proxParent);
            parent = hQry.list();
            if (null != parent && !parent.isEmpty())
                custProxParent = String.valueOf(parent.get(0));
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            query = null;
            parent = null;
        }
        return custProxParent;
    }

    private String getProxParentTitle(String proxParent) {
        Session session = null;
        List<Object[]> parent = null;
        String proxParentTitle = null;
        String query = "SELECT objid FROM gets_rmd_pp_proximity_def WHERE proximity_type=1 AND cust_proximity_id = :proxParent";
        try {
            session = getHibernateSession();
            Query hQry = session.createSQLQuery(query);
            hQry.setParameter(RMDServiceConstants.PROXIMITY_PARENT, proxParent);
            parent = hQry.list();
            if (null != parent && !parent.isEmpty())
                proxParentTitle = String.valueOf(parent.get(0));
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            query = null;
            parent = null;
        }
        return proxParentTitle;
    }

    public String getBusOrgId(String custId) {
        Session session = null;
        List<Object[]> objIdList = null;
        String busOrgId = null;
        String query = "SELECT OBJID FROM table_bus_org WHERE ORG_ID = :customerId";
        try {
            session = getHibernateSession();
            Query busOrgIdQry = session.createSQLQuery(query);
            busOrgIdQry.setParameter(RMDServiceConstants.CUSTOMER_ID, custId);
            objIdList = busOrgIdQry.list();
            if (null != objIdList && !objIdList.isEmpty())
                busOrgId = String.valueOf(objIdList.get(0));
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BUS_ORG_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            query = null;
            objIdList = null;
        }
        return busOrgId;
    }

    /**
     * @Author:
     * @param :customerId,region,subRegion,userId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used to add New Region/sub region
     */
    @Override
    public String addRegionSubRegion(CreateRegionVO regionVO) throws RMDDAOException {
        Session session = null;
        String status = RMDServiceConstants.SUCCESS;
        StringBuilder existRegQry = new StringBuilder();
        try {
            session = getHibernateSession();
            existRegQry.append("SELECT DISTINCT region FROM gets_rmd_pp_sub_region WHERE upper(region)= :region ");
            if (!RMDCommonUtility.isNullOrEmpty(regionVO.getSubRegion())) {
                existRegQry.append("AND upper(sub_region) = :subRegion");
            }
            Query hQry = session.createSQLQuery(existRegQry.toString());
            hQry.setParameter(RMDServiceConstants.REGION, regionVO.getRegion().toUpperCase());
            if (!RMDCommonUtility.isNullOrEmpty(regionVO.getSubRegion())) {
                hQry.setParameter(RMDServiceConstants.SUB_REGION, regionVO.getSubRegion().toUpperCase());
            }
            List<Object> result = hQry.list();
            if (null != result && !result.isEmpty()) {
                status = RMDServiceConstants.COMBINATION_EXISTS;
            } else {
                String busOrgId = getBusOrgId(regionVO.getCustomerId());
                StringBuilder insertQuery = new StringBuilder();
                insertQuery.append(
                        "INSERT INTO gets_rmd_pp_sub_region VALUES (gets_rmd_pp_sub_region_Seq.nextval, sysdate,");
                insertQuery.append(" :userId,sysdate,:userId, :busOrgId, :region, :subRegion, null, null,null)");
                Query inserthQry = session.createSQLQuery(insertQuery.toString());
                inserthQry.setParameter(RMDServiceConstants.USER_ID, regionVO.getUserId());
                inserthQry.setParameter(RMDServiceConstants.USER_ID, regionVO.getUserId());
                inserthQry.setParameter(RMDServiceConstants.BUS_ORG_ID, busOrgId);
                inserthQry.setParameter(RMDServiceConstants.REGION, regionVO.getRegion());
                inserthQry.setParameter(RMDServiceConstants.SUB_REGION, regionVO.getSubRegion());
                inserthQry.executeUpdate();
            }
        } catch (Exception e) {
            status = RMDServiceConstants.FAILURE;
            LOG.error("Exception occurred:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_REGION_SUB_REGION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
        return status;

    }

    /**
     * @Author:
     * @param :customerId
     * @return:List<String>
     * @throws:RMDDAOException
     * @Description: This method is used to get region for the selected customer
     */
    @Override
    public List<String> getRegion(String customerId) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<String> regionList = new ArrayList<String>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append(
                    "SELECT DISTINCT region FROM gets_rmd_pp_sub_region WHERE pp_sub_region2bus_org = (SELECT OBJID FROM ");
            strQuery.append("table_bus_org WHERE ORG_ID = :customerId) order by lower(region)");
            final Query regionQuery = hibernateSession.createSQLQuery(strQuery.toString());
            regionQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, customerId);
            List<Object> resultList = regionQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (Object obj : resultList) {
                    regionList.add(RMDCommonUtility.convertObjectToString(obj));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_REGION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_REGION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return regionList;
    }

    /**
     * @Author:
     * @param :region
     * @return:List<String>
     * @throws:RMDDAOException
     * @Description: This method is used to get sub region for the selected
     *               region
     */
    @Override
    public List<String> getSubRegion(String region) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<String> subRegionList = new ArrayList<String>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append(
                    "SELECT DISTINCT sub_region FROM gets_rmd_pp_sub_region WHERE region = :region order by lower(sub_region)");
            final Query subRegionQuery = hibernateSession.createSQLQuery(strQuery.toString());
            subRegionQuery.setParameter(RMDServiceConstants.REGION, region);
            List<Object> resultList = subRegionQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (Object obj : resultList) {
                    subRegionList.add(RMDCommonUtility.convertObjectToString(obj));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUB_REGION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUB_REGION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return subRegionList;
    }

}
