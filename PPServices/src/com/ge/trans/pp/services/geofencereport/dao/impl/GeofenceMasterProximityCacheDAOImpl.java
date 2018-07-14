package com.ge.trans.pp.services.geofencereport.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.geofencereport.dao.intf.GeofenceMasterProximityCacheDAOIntf;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceMasterProximityVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class GeofenceMasterProximityCacheDAOImpl extends RMDCommonDAO implements GeofenceMasterProximityCacheDAOIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(GeofenceMasterProximityCacheDAOImpl.class);

    // @Cacheable(value = RMDCommonConstants.CACHE_GEOFENCE_MASTER_PROXIMITY)
    @Override
    public Map<String, GeofenceMasterProximityVO> getGeofenceMasterProximities() throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder materDataQuery = new StringBuilder();
        String customerObjId = null;
        Query hibernateQuery = null;
        List<Object[]> masterDataList = null;
        GeofenceMasterProximityVO geofenceMasterProximityVO = null;

        Map<String, GeofenceMasterProximityVO> GefenceMasterDataMap = new HashMap<String, GeofenceMasterProximityVO>();
        try {
            hibernateSession = getHibernateSession();
            materDataQuery.append(
                    " SELECT mster.GEOFENCE_NAME, mster.PROXIMITY_EVENT, mster.GPS_UPPER_LEFT_LAT_VAL, mster.GPS_UPPER_LEFT_LON_VAL, ");
            materDataQuery.append(" mster.GPS_LOWER_RIGHT_LAT_VAL, mster.GPS_LOWER_RIGHT_LON_VAL, busorg.ORG_ID ");
            materDataQuery.append(
                    " FROM GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES mster, TABLE_BUS_ORG busorg WHERE mster.LINK_CUSTOMER=busorg.OBJID ");

            hibernateQuery = hibernateSession.createSQLQuery(materDataQuery.toString());
            hibernateQuery.setFetchSize(1000);
            masterDataList = hibernateQuery.list();

            if (RMDCommonUtility.isCollectionNotEmpty(masterDataList)) {
                Object[] objGeofenceReport = null;
                for (int i = 0; i < masterDataList.size(); i++) {
                    objGeofenceReport = masterDataList.get(i);
                    geofenceMasterProximityVO = new GeofenceMasterProximityVO();
                    customerObjId = RMDCommonUtility.convertObjectToString(objGeofenceReport[6]);

                    geofenceMasterProximityVO.setCustomerId(customerObjId);
                    geofenceMasterProximityVO.setProxDesc(RMDCommonUtility.convertObjectToString(objGeofenceReport[0]));
                    geofenceMasterProximityVO
                            .setProxEvent(RMDCommonUtility.convertObjectToString(objGeofenceReport[1]));
                    geofenceMasterProximityVO
                            .setUpLeftLatitude(RMDCommonUtility.convertObjectToFloat(objGeofenceReport[2]));
                    geofenceMasterProximityVO
                            .setUpLeftLongitude(RMDCommonUtility.convertObjectToFloat(objGeofenceReport[3]));
                    geofenceMasterProximityVO
                            .setLowRightLatitude(RMDCommonUtility.convertObjectToFloat(objGeofenceReport[4]));
                    geofenceMasterProximityVO
                            .setLowRightLongitude(RMDCommonUtility.convertObjectToFloat(objGeofenceReport[5]));

                    GefenceMasterDataMap.put(customerObjId + "_" + objGeofenceReport[0], geofenceMasterProximityVO);

                }
            }
        } catch (JDBCException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return GefenceMasterDataMap;
    }
}
