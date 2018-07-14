package com.ge.trans.pp.services.manageGeofence.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;

import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceReqVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ManageGeofenceRespVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class ManageGeofenceDAOImpl extends RMDCommonDAO implements ManageGeofenceDAOIntf {

    /*
     * @Autowired private org.springframework.cache.CacheManager cacheManager;
     */

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ManageGeofenceDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf
     * #getGeofenceProximityData
     * (com.ge.trans.pp.services.manageGeofence.service.
     * valueobjects.ManageGeofenceReqVO) This method is used to get the
     * getGeofenceProximityData
     */
    @Override
    public List<ManageGeofenceRespVO> getGeofenceProximityData(ManageGeofenceReqVO objManageGeofenceReqVO)
            throws RMDDAOException {
        Session hibernateSession = null;
        List<ManageGeofenceRespVO> geofenceProximityDataList = new ArrayList<ManageGeofenceRespVO>();
        StringBuilder materDataQuery = new StringBuilder();
        Query hibernateQuery = null;
        List<Object[]> masterDataList = null;
        ManageGeofenceRespVO objManageGeofenceRespVO = null;
        try {
            hibernateSession = getHibernateSession();
            materDataQuery.append(
                    " SELECT  mster.GETS_RMD_OMD_ATS_PROX_ID, mster.GEOFENCE_NAME, mster.PROXIMITY_EVENT, mster.GPS_UPPER_LEFT_LAT_VAL, mster.GPS_UPPER_LEFT_LON_VAL, ");
            materDataQuery.append(
                    " mster.GPS_LOWER_RIGHT_LAT_VAL, mster.GPS_LOWER_RIGHT_LON_VAL, mster.PROXIMITY_NOTES, busorg.ORG_ID ");
            materDataQuery.append(
                    " FROM GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES mster, TABLE_BUS_ORG busorg WHERE mster.LINK_CUSTOMER=busorg.OBJID ");

            if (!RMDCommonConstants.ALL_CUSTOMER.equals(objManageGeofenceReqVO.getCustomerId())) {
                materDataQuery.append(" AND busorg.ORG_ID=:custId ");
                materDataQuery.append(" ORDER BY  UPPER(mster.GEOFENCE_NAME) ");
                hibernateQuery = hibernateSession.createSQLQuery(materDataQuery.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUST_ID, objManageGeofenceReqVO.getCustomerId());

            } else {
                materDataQuery.append(" ORDER BY  busorg.ORG_ID, UPPER(mster.GEOFENCE_NAME) ");
                hibernateQuery = hibernateSession.createSQLQuery(materDataQuery.toString());
            }

            hibernateQuery.setFetchSize(1000);
            masterDataList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(masterDataList)) {
                Object[] objGeofenceReport = null;
                for (int i = 0; i < masterDataList.size(); i++) {
                    objGeofenceReport = masterDataList.get(i);
                    objManageGeofenceRespVO = new ManageGeofenceRespVO();
                    objManageGeofenceRespVO.setCustomerId(RMDCommonUtility.convertObjectToString(objGeofenceReport[8]));
                    objManageGeofenceRespVO
                            .setGeofenceSeqId(RMDCommonUtility.convertObjectToString(objGeofenceReport[0]));
                    objManageGeofenceRespVO.setGeofenceName(
                    		ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(objGeofenceReport[1])));
                    objManageGeofenceRespVO
                            .setProximityEvent(RMDCommonUtility.convertObjectToString(objGeofenceReport[2]));
                    objManageGeofenceRespVO.setUpLeftLat(RMDCommonUtility.convertObjectToString(objGeofenceReport[3]));
                    objManageGeofenceRespVO.setUpLeftLong(RMDCommonUtility.convertObjectToString(objGeofenceReport[4]));
                    objManageGeofenceRespVO
                            .setLowerRightLat(RMDCommonUtility.convertObjectToString(objGeofenceReport[5]));
                    objManageGeofenceRespVO
                            .setLowerRightLong(RMDCommonUtility.convertObjectToString(objGeofenceReport[6]));
                    objManageGeofenceRespVO.setGeofenceNotes(
                    		ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(objGeofenceReport[7])));
                    geofenceProximityDataList.add(objManageGeofenceRespVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("Exception occurred:" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred:" + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return geofenceProximityDataList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf
     * #saveUpdateGeofenceDetails
     * (com.ge.trans.pp.services.manageGeofence.service.
     * valueobjects.ManageGeofenceReqVO) This method is used to SaveOrUpdate the
     * geofenceProximityData
     */
    @Override
    public void saveUpdateGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder queryString = null;
        String upLeftLat;
        String upLeftLong;
        String lowerRightLat;
        String lowerRightLong;
        try {
            Date saveDate = new Date();
            Timestamp systimestamp = new Timestamp(saveDate.getTime());
            hibernateSession = getHibernateSession();
            upLeftLat = objManageGeofenceReqVO.getUpLeftLat();
            upLeftLong = objManageGeofenceReqVO.getUpLeftLong();
            lowerRightLat = objManageGeofenceReqVO.getLowerRightLat();
            lowerRightLong = objManageGeofenceReqVO.getLowerRightLong();

            if (null != objManageGeofenceReqVO
                    && RMDCommonConstants.GEOFENCE_EDIT.equals(objManageGeofenceReqVO.getActionType())
                    && null != objManageGeofenceReqVO.getGeofenceSeqId()
                    && !objManageGeofenceReqVO.getGeofenceSeqId().isEmpty()) {
                queryString = new StringBuilder();
                queryString.append(
                        " UPDATE GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES SET LINK_CUSTOMER=(SELECT OBJID FROM TABLE_BUS_ORG WHERE ORG_ID=:custId), ");
                queryString.append(
                        " GEOFENCE_NAME=:geofenceName,PROXIMITY_EVENT=:proxEvent, GPS_UPPER_LEFT_LAT_VAL=:upLeftLat, ");
                queryString.append(
                        " GPS_UPPER_LEFT_LON_VAL=:upLeftLong,GPS_LOWER_RIGHT_LAT_VAL=:lowRightLat,GPS_LOWER_RIGHT_LON_VAL=:lowRightLong, ");
                queryString
                        .append(" PROXIMITY_NOTES=:notes,LAST_UPDATED_BY=:updatedBy,LAST_UPDATED_DATE=:updatedDate ");
                queryString.append(" WHERE GETS_RMD_OMD_ATS_PROX_ID=:geofenceSeqId ");
                Query query = hibernateSession.createSQLQuery(queryString.toString());

                query.setParameter(RMDCommonConstants.CUST_ID, objManageGeofenceReqVO.getCustomerId());
                query.setParameter(RMDCommonConstants.GEOFENCE_NAME,
                		EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(objManageGeofenceReqVO.getGeofenceName().trim())));
                query.setParameter(RMDCommonConstants.PROXIMITY_EVENT, objManageGeofenceReqVO.getProximityEvent());
                query.setParameter(RMDCommonConstants.UPPER_LEFT_LATITUDE,
                        upLeftLat != null ? Float.parseFloat(upLeftLat) : 0);
                query.setParameter(RMDCommonConstants.UPPER_LEFT_LONGITUDE,
                        upLeftLong != null ? Float.parseFloat(upLeftLong) : 0);
                query.setParameter(RMDCommonConstants.LOWER_RIGHT_LATITUDE,
                        lowerRightLat != null ? Float.parseFloat(lowerRightLat) : 0);
                query.setParameter(RMDCommonConstants.LOWER_RIGHT_LONGITUDE,
                        lowerRightLong != null ? Float.parseFloat(lowerRightLong) : 0);
                query.setParameter(RMDCommonConstants.NOTES,EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(objManageGeofenceReqVO.getGeofenceNotes())));
                query.setParameter(RMDCommonConstants.UPDATED_BY, objManageGeofenceReqVO.getStrUserName());
                query.setParameter(RMDCommonConstants.UPDATED_DATE, systimestamp);
                query.setParameter(RMDCommonConstants.GEOFENCE_SEQ_ID, objManageGeofenceReqVO.getGeofenceSeqId());

                query.executeUpdate();
            } else {
                queryString = new StringBuilder();
                queryString.append(" INSERT INTO GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES ");
                queryString.append(
                        " (GETS_RMD_OMD_ATS_PROX_ID,LINK_CUSTOMER,GEOFENCE_NAME,PROXIMITY_EVENT,GPS_UPPER_LEFT_LAT_VAL,GPS_UPPER_LEFT_LON_VAL, ");
                queryString.append(
                        " GPS_LOWER_RIGHT_LAT_VAL, GPS_LOWER_RIGHT_LON_VAL,PROXIMITY_NOTES,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE) ");

                queryString.append(
                        " VALUES ( GETS_RMD_OMD_ATS_PROXIMITI_SEQ.NEXTVAL,(SELECT OBJID FROM TABLE_BUS_ORG WHERE ORG_ID=:custId), ");
                queryString.append(" :geofenceName, :proxEvent,:upLeftLat, :upLeftLong, ");
                queryString.append(
                        " :lowRightLat, :lowRightLong, :notes, :updatedBy,SYSTIMESTAMP, :createdBy,SYSTIMESTAMP) ");

                Query query = hibernateSession.createSQLQuery(queryString.toString());
                query.setParameter(RMDCommonConstants.CUST_ID, objManageGeofenceReqVO.getCustomerId());
                query.setParameter(RMDCommonConstants.GEOFENCE_NAME,EsapiUtil.escapeSpecialChars(objManageGeofenceReqVO.getGeofenceName()));
                query.setParameter(RMDCommonConstants.PROXIMITY_EVENT, objManageGeofenceReqVO.getProximityEvent());
                query.setParameter(RMDCommonConstants.UPPER_LEFT_LATITUDE,
                        upLeftLat != null ? Float.parseFloat(upLeftLat) : 0);
                query.setParameter(RMDCommonConstants.UPPER_LEFT_LONGITUDE,
                        upLeftLong != null ? Float.parseFloat(upLeftLong) : 0);
                query.setParameter(RMDCommonConstants.LOWER_RIGHT_LATITUDE,
                        lowerRightLat != null ? Float.parseFloat(lowerRightLat) : 0);
                query.setParameter(RMDCommonConstants.LOWER_RIGHT_LONGITUDE,
                        lowerRightLong != null ? Float.parseFloat(lowerRightLong) : 0);
                query.setParameter(RMDCommonConstants.NOTES, EsapiUtil.escapeSpecialChars(objManageGeofenceReqVO.getGeofenceNotes()));
                query.setParameter(RMDCommonConstants.UPDATED_BY, objManageGeofenceReqVO.getStrUserName());
                query.setParameter(RMDCommonConstants.CREATED_BY, objManageGeofenceReqVO.getStrUserName());
                query.executeUpdate();
            }
            /*
             * cacheManager.getCache(RMDCommonConstants.
             * GEOFENCE_PROXIMITY_CACHE) .clear();
             */
        } catch (RMDDAOConnectionException ex) {
            LOG.error("Exception occurred:" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred:" + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf
     * #validateGeofenceData (com.ge.trans.pp.services.manageGeofence.service.
     * valueobjects.ManageGeofenceReqVO) This method is used to validate the
     * User entered geofenceDetails data from DB
     */
    @Override
    public String validateGeofenceData(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder materDataQuery = new StringBuilder();
        Query hibernateQuery = null;
        List<Object[]> masterDataObjidList = null;
        String isGeofenceValid = RMDCommonConstants.STR_TRUE;
        try {
            /*
             * Map<String, GeofenceMasterProximityVO> geofenceMasterDataMap =
             * objGeofenceMPCacheDAOIntf .getGeofenceMasterProximities();
             */

            hibernateSession = getHibernateSession();

            materDataQuery.append(" SELECT  mster.GETS_RMD_OMD_ATS_PROX_ID ");
            materDataQuery.append(
                    " FROM GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES mster, TABLE_BUS_ORG busorg WHERE mster.LINK_CUSTOMER=busorg.OBJID ");
            materDataQuery.append(
                    " AND UPPER(mster.GEOFENCE_NAME) =:geofenceName AND mster.PROXIMITY_EVENT =:proxEvent AND busorg.ORG_ID =:custId");

            hibernateQuery = hibernateSession.createSQLQuery(materDataQuery.toString());
            hibernateQuery.setFetchSize(1000);

            hibernateQuery.setParameter(RMDCommonConstants.GEOFENCE_NAME,
                    ESAPI.encoder().decodeForHTML(objManageGeofenceReqVO.getGeofenceName().trim().toUpperCase()));

            hibernateQuery.setParameter(RMDCommonConstants.PROXIMITY_EVENT, objManageGeofenceReqVO.getProximityEvent());

            hibernateQuery.setParameter(RMDCommonConstants.CUST_ID, objManageGeofenceReqVO.getCustomerId());

            masterDataObjidList = hibernateQuery.list();

            if (RMDCommonUtility.isCollectionNotEmpty(masterDataObjidList)) {
                if (RMDCommonConstants.GEOFENCE_EDIT.equals(objManageGeofenceReqVO.getActionType())
                        && !RMDCommonUtility.cnvrtBigDecimalObjectToString(masterDataObjidList.get(0))
                                .equals(objManageGeofenceReqVO.getGeofenceSeqId())) {
                    isGeofenceValid = RMDCommonConstants.STR_FALSE;
                } else if (RMDCommonConstants.GEOFENCE_ADD.equals(objManageGeofenceReqVO.getActionType())
                        && masterDataObjidList.size() > 0) {
                    isGeofenceValid = RMDCommonConstants.STR_FALSE;
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("Exception occurred:" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred:" + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return isGeofenceValid;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.manageGeofence.dao.intf.ManageGeofenceDAOIntf
     * #deleteGeofenceDetails (com.ge.trans.pp.services.manageGeofence.service.
     * valueobjects.ManageGeofenceReqVO) This method is used to delete the
     * GeofenceDetails from DB
     */
    @Override
    public void deleteGeofenceDetails(ManageGeofenceReqVO objManageGeofenceReqVO) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder materDataQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            hibernateSession = getHibernateSession();
            materDataQuery.append(" DELETE FROM GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES ");
            materDataQuery.append(" WHERE GETS_RMD_OMD_ATS_PROX_ID =:geofenceSeqId ");

            hibernateQuery = hibernateSession.createSQLQuery(materDataQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.GEOFENCE_SEQ_ID, objManageGeofenceReqVO.getGeofenceSeqId());
            hibernateQuery.executeUpdate();
            /*
             * cacheManager.getCache(RMDCommonConstants.
             * GEOFENCE_PROXIMITY_CACHE) .clear();
             */

        } catch (RMDDAOConnectionException ex) {
            LOG.error("Exception occurred:" + ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred:" + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

    }
}
