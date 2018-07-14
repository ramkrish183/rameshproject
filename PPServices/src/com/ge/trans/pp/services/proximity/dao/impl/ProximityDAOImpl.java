package com.ge.trans.pp.services.proximity.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.pp.services.asset.service.valueobjects.PPCountryStateVO;
import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.proximity.dao.intf.ProximityDAOIntf;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPCityVO;
import com.ge.trans.pp.services.proximity.service.valueobjects.PPProximityResponseVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class ProximityDAOImpl extends RMDCommonDAO implements ProximityDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 821188297674880946L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ProximityDAOImpl.class);
    Codec ORACLE_CODEC = new OracleCodec();

    /**
     * @Author:
     * @param countryName
     *            not mandatory
     * @return
     * @throws RMDDAOException
     * @Description:This method is used for fetching the country and state list
     */
    @Override
    public List<PPCountryStateVO> getAllPPCountryStates(String countryName) throws RMDDAOException {
        Session session = null;
        PPCountryStateVO objCountryStateVO = null;
        List<PPCountryStateVO> searchResults = null;
        final StringBuilder queryString = new StringBuilder();
        List<String> countryList = new ArrayList<String>();
        List<String> stateList = new ArrayList<String>();
        Map<String, List<String>> countryStateMap = new HashMap<String, List<String>>();
        try {
            session = getHibernateSession();
            queryString.append("SELECT DISTINCT STATE_PROVINCE, COUNTRY FROM GETS_RMD_PP_CITY_DEF ");
            if (countryName != null && !RMDCommonUtility.checkNull(countryName)) {
                queryString.append("WHERE COUNTRY=:country ");
            }
            queryString.append(" ORDER BY COUNTRY, STATE_PROVINCE ASC");
            Query query = session.createSQLQuery(queryString.toString());
            if (countryName != null && !RMDCommonUtility.checkNull(countryName)) {

                query.setParameter(RMDCommonConstants.COUNTRYCODE, countryName);
            }

            List<Object> countryStateList = query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(countryStateList)) {

                searchResults = new ArrayList<PPCountryStateVO>();

                for (final Iterator<Object> iter = countryStateList.iterator(); iter.hasNext();) {
                    // Creating a map with key as country and value as list of
                    // states
                    objCountryStateVO = new PPCountryStateVO();
                    final Object[] objCountryState = (Object[]) iter.next();

                    if (countryList.contains(RMDCommonUtility.convertObjectToString(objCountryState[1]))) {
                        /* Adding if country is already there in the map */
                        if (countryStateMap.containsKey(RMDCommonUtility.convertObjectToString(objCountryState[1]))) {

                            stateList = countryStateMap.get(RMDCommonUtility.convertObjectToString(objCountryState[1]));
                            stateList.add(RMDCommonUtility.convertObjectToString(objCountryState[0]));
                            countryStateMap.put(RMDCommonUtility.convertObjectToString(objCountryState[1]), stateList);
                        }

                    } else {/* Adding if new country comes */
                        countryList.add(RMDCommonUtility.convertObjectToString(objCountryState[1]));
                        stateList = new ArrayList<String>();
                        stateList.add(RMDCommonUtility.convertObjectToString(objCountryState[0]));
                        countryStateMap.put(RMDCommonUtility.convertObjectToString(objCountryState[1]), stateList);

                    }

                }

                // iterate through HashMap values iterator to add the values in
                // list
                for (Map.Entry<String, List<String>> entry : countryStateMap.entrySet()) {
                    objCountryStateVO = new PPCountryStateVO();
                    objCountryStateVO.setCountryName(entry.getKey());
                    objCountryStateVO.setStateList(entry.getValue());
                    searchResults.add(objCountryStateVO);

                }

            }

            return searchResults;
        } catch (JDBCException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.proximity.dao.intf.ProximityDAOIntf#
     * getCitiesForGivenState(java.lang.String, java.lang.String) This method is
     * used to get the cities for a given state and country
     */
    @Override
    public List<PPCityVO> getCitiesForGivenState(String countryCode, String stateCode) throws RMDDAOException {
        Session session = null;
        List<PPCityVO> cityList = null;
        final StringBuilder queryString = new StringBuilder();
        try {
            session = getHibernateSession();
            queryString.append(" SELECT GPS_LAT, GPS_LON, CITY FROM GETS_RMD_PP_CITY_DEF ");
            if (countryCode != null && !countryCode.trim().equals(RMDCommonConstants.EMPTY_STRING) && stateCode != null
                    && !stateCode.trim().equals(RMDCommonConstants.EMPTY_STRING)) {
                queryString.append(" WHERE STATE_PROVINCE = :state AND COUNTRY = :country ");
            }
            queryString.append(" ORDER BY CITY ");
            Query query = session.createSQLQuery(queryString.toString());

            if (countryCode != null && !countryCode.trim().equals(RMDCommonConstants.EMPTY_STRING) && stateCode != null
                    && !stateCode.trim().equals(RMDCommonConstants.EMPTY_STRING)) {
                query.setParameter(RMDCommonConstants.COUNTRYCODE, countryCode);
                query.setParameter(RMDCommonConstants.STATECODE, stateCode);
            }
            query.setFetchSize(1000);
            List<Object> resultList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                cityList = new ArrayList<PPCityVO>();
                PPCityVO cityVO = null;
                for (final Iterator<Object> iter = resultList.iterator(); iter.hasNext();) {
                    final Object[] cityRow = (Object[]) iter.next();
                    cityVO = new PPCityVO();
                    cityVO.setLatitude(RMDCommonUtility.convertObjectToLong(cityRow[0]));
                    cityVO.setLongitude(RMDCommonUtility.convertObjectToLong(cityRow[1]));
                    cityVO.setCityName(RMDCommonUtility.convertObjectToString(cityRow[2]));
                    cityList.add(cityVO);
                }
            }
            return cityList;
        } catch (JDBCException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.proximity.dao.intf.ProximityDAOIntf#
     * getProximityDetails(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, long, long, long) This method is used to get the
     * proximity details
     */
    @Override
    public List<PPProximityResponseVO> getProximityDetails(String customerId, String city, String stateCode,
            String countryCode, long gpsLat, long gpsLong, float mileRange, List<String> products)
            throws RMDDAOException {
        Session session = null;
        PPProximityResponseVO proximityResponseVO = null;
        List<PPProximityResponseVO> proximityList = null;
        List<Object> objIdleReportProd = null;
        Query hibernateQuery = null;
        String productCdQuery = null;
        String productCd = "";
        Map<String, String> productObjidMap = new HashMap<String, String>();
        final StringBuilder queryString = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            if (gpsLat == -1 && gpsLong == -1) {
                StringBuilder cityQueryString = new StringBuilder();
                cityQueryString.append("SELECT GPS_LAT, GPS_LON FROM GETS_RMD_PP_CITY_DEF ");
                if (city != null && !city.equals(RMDCommonConstants.EMPTY_STRING) && stateCode != null
                        && !stateCode.equals(RMDCommonConstants.EMPTY_STRING) && countryCode != null
                        && !countryCode.equals(RMDCommonConstants.EMPTY_STRING)) {
                    cityQueryString.append(
                            " WHERE UPPER(CITY) = :city AND UPPER(STATE_PROVINCE) = :state AND UPPER(COUNTRY) = :country ");
                }
                Query query = session.createSQLQuery(cityQueryString.toString());
                if (city != null && !city.equals(RMDCommonConstants.EMPTY_STRING) && stateCode != null
                        && !stateCode.equals(RMDCommonConstants.EMPTY_STRING) && countryCode != null
                        && !countryCode.equals(RMDCommonConstants.EMPTY_STRING)) {
                    query.setParameter(RMDCommonConstants.CITY, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, city.toUpperCase()));
                    query.setParameter(RMDCommonConstants.STATECODE, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, stateCode.toUpperCase()));
                    query.setParameter(RMDCommonConstants.COUNTRYCODE, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, countryCode.toUpperCase()));
                }
                List<Object> cityResultsList = query.list();
                if (RMDCommonUtility.isCollectionNotEmpty(cityResultsList)) {
                    final Object[] cityRec = (Object[]) cityResultsList.iterator().next();
                    gpsLat = RMDCommonUtility.convertObjectToLong(cityRec[0]);
                    gpsLong = RMDCommonUtility.convertObjectToLong(cityRec[1]);
                }
            }

            if (products != null && products.size() > 0 && !products.contains(RMDCommonConstants.DEFAULT_STRING)) {

                productCdQuery = "SELECT GET_RMD_OMD_PRODUCT_SEQ_ID FROM GETS_RMD.GETS_RMD_OMD_PRODUCT WHERE PRODUCT_CD IN(:productNameLst)";
                hibernateQuery = session.createSQLQuery(productCdQuery);
                hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST, EsapiUtil.encodeForSQLfromList(products));
                objIdleReportProd = hibernateQuery.list();

                if (RMDCommonUtility.isCollectionNotEmpty(objIdleReportProd)) {
                    for (int i = 0; i < objIdleReportProd.size(); i++) {
                        productCd += RMDCommonConstants.SINGLE_QTE
                                + RMDCommonUtility.convertObjectToString(objIdleReportProd.get(i))
                                + RMDCommonConstants.SINGLE_QTE;
                        if (i < (objIdleReportProd.size() - 1)) {
                            productCd += ",";
                        }
                    }
                }

                StringBuilder prodQueryString = new StringBuilder();
                prodQueryString.append(" SELECT TSP.OBJID FROM GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST, ");
                prodQueryString.append(" TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, ");
                prodQueryString.append(" TABLE_BUS_ORG TBO WHERE ELGASST.ASSET_ID = VEH.OBJID ");
                prodQueryString
                        .append(" AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID ");
                prodQueryString.append(" AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                prodQueryString.append(" AND ELGASST.GET_RMD_OMD_PRODUCT_SEQ_ID IN (" +  productCd + " ) ");
                Query query = session.createSQLQuery(prodQueryString.toString());
                List<Object> productResList = query.list();
                if (RMDCommonUtility.isCollectionNotEmpty(productResList)) {
                    for (final Iterator<Object> iter = productResList.iterator(); iter.hasNext();) {
                        final Object prodRec = iter.next();
                        productObjidMap.put(RMDCommonUtility.convertObjectToString(prodRec),
                                RMDCommonUtility.convertObjectToString(prodRec));
                    }
                }
            }
            queryString.append("SELECT * FROM (");
            queryString.append(" SELECT GVIEW.VEHICLE_HDR, GVIEW.VEHICLE_NO, ");
            queryString.append(" NVL(FLEET.CWC_LOCO_CLASS, GRM.MODEL_NAME) MODEL, ");
            queryString.append(" TSTATUS.GPS_LAT, TSTATUS.GPS_LON, TSTATUS.LOCATION_DESC, ");
            queryString.append(" TSTATUS.STATE_PROVINCE, TMILEPOST.MILEPOST_ID, TMILEPOST.REGION, ");
            queryString.append(" TMILEPOST.SUB_REGION, TO_CHAR(TSTATUS.LAST_COMM_DATE ");
            queryString.append(" ,'MM/DD/YYYY HH24:MI:SS'), GVIEW.SITE_PART_OBJID ,GVIEW.ORG_ID FROM  ");
            queryString.append(" GETS_TOOL_PP_STATUS_CURR TCURRENT, GETS_TOOL_PP_STATUS_HIST TSTATUS, ");
            queryString.append(" GETS_RMD_CUST_RNH_RN_V GVIEW, GETS_RMD_PP_MILEPOST_DEF TMILEPOST, ");
            queryString.append(" GETS_RMD_VEHICLE GRV, GETS_RMD_MODEL GRM, GETS_RMD_FLEET FLEET,gets_rmd_vehcfg cfg, gets_rmd_master_bom bom ");
            queryString.append(" WHERE TCURRENT.STATUS_CURR2STATUS_HIST = TSTATUS.OBJID ");
            queryString.append(" AND TCURRENT.STATUS_CURR2VEHICLE       = GVIEW.VEHICLE_OBJID ");
            queryString.append(" AND TSTATUS.STATUS_HIST2MILEPOST       = TMILEPOST.OBJID (+) ");
            queryString.append(" AND GVIEW.VEHICLE_OBJID                = GRV.OBJID ");
            queryString.append(" AND GRV.VEHICLE2MODEL                  = GRM.OBJID ");
            queryString.append(" AND FLEET.OBJID                        = GRV.VEHICLE2FLEET ");
            queryString.append(" AND GVIEW.vehicle_objid                = cfg.veh_cfg2vehicle ");
            queryString.append(" AND cfg.vehcfg2master_bom              = bom.objid ");
            //queryString.append(" AND GVIEW.PART_STATUS                  = 'Installed/Good' ");
            // queryString.append(" AND GVIEW.EVOLUTION_UNIT_FLAG IN ('N','B')
            // ");
            queryString.append(" AND bom.config_item                    = 'CMU' ");
            queryString.append(" AND cfg.current_version                = '1' ");
            queryString.append(" AND GVIEW.SERVICE_PINPOINT             = 1 ");
            queryString.append(" AND GVIEW.ORG_ID                       = :orgId ");
            queryString.append(" AND TSTATUS.GPS_LAT                   >= " + (gpsLat - (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LAT                   <= " + (gpsLat + (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LON                   >= " + (gpsLong - (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LON                   <= " + (gpsLong + (mileRange * 52000)));
            queryString.append(" UNION ");
            queryString.append(" SELECT GVIEW.VEHICLE_HDR, GVIEW.VEHICLE_NO, ");
            queryString.append(" NVL(FLEET.CWC_LOCO_CLASS, GRM.MODEL_NAME) MODEL, ");
            queryString.append(" TSTATUS.GPS_LAT, TSTATUS.GPS_LON, TSTATUS.LOCATION_DESC, ");
            queryString.append(" TSTATUS.STATE_PROVINCE, TMILEPOST.MILEPOST_ID, TMILEPOST.REGION, ");
            queryString.append(" TMILEPOST.SUB_REGION, TO_CHAR(TSTATUS.LAST_COMM_DATE ");
            queryString.append(" ,'MM/DD/YYYY HH24:MI:SS'), GVIEW.SITE_PART_OBJID,GVIEW.ORG_ID  FROM  ");
            queryString.append(" GETS_TOOL_PP_STATUS_CURR TCURRENT, GETS_TOOL_PP_STATUS_HIST TSTATUS, ");
            queryString.append(" GETS_RMD_CUST_RNH_RN_V GVIEW, GETS_RMD_PP_MILEPOST_DEF TMILEPOST, ");
            queryString.append(" GETS_RMD_VEHICLE GRV, GETS_RMD_MODEL GRM, GETS_RMD_FLEET FLEET, ");
            queryString
                    .append(" gets_mcs.mcs_asset a, gets_mcs.MCS_LOOKUP_VALUE st, gets_mcs.mcs_application service, ");
            queryString.append(" gets_mcs.mcs_asset_application app ");
            queryString.append(" WHERE TCURRENT.STATUS_CURR2STATUS_HIST = TSTATUS.OBJID ");
            queryString.append(" AND TCURRENT.STATUS_CURR2VEHICLE       = GVIEW.VEHICLE_OBJID ");
            queryString.append(" AND TSTATUS.STATUS_HIST2MILEPOST       = TMILEPOST.OBJID (+) ");
            queryString.append(" AND GVIEW.VEHICLE_OBJID                = GRV.OBJID ");
            queryString.append(" AND GRV.VEHICLE2MODEL                  = GRM.OBJID ");
            queryString.append(" AND FLEET.OBJID                        = GRV.VEHICLE2FLEET ");
            queryString.append(" AND STATUS = st.objid  ");
            queryString.append(" and app.APPLICATION_OBJID = service.objid ");
            queryString.append(" and app.ASSET_OBJID = a.objid ");
            queryString.append(" and gview.vehicle2asset_objid = a.objid ");
            queryString.append(" and APPLICATION_NAME = 'ATS' ");
            queryString.append(" AND GVIEW.ORG_ID                       = :orgId ");
            queryString.append(" AND TSTATUS.GPS_LAT                   >= " + (gpsLat - (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LAT                   <= " + (gpsLat + (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LON                   >= " + (gpsLong - (mileRange * 52000)));
            queryString.append(" AND TSTATUS.GPS_LON                   <= " + (gpsLong + (mileRange * 52000)));
            queryString.append(" ) ORDER BY VEHICLE_HDR, VEHICLE_NO ");
            Query query = session.createSQLQuery(queryString.toString());
            if (customerId != null && !RMDCommonUtility.checkNull(customerId)) {
                query.setParameter(RMDCommonConstants.ORGID, customerId);
            }
            List<Object> proxResultsList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(proxResultsList)) {
                proximityList = new ArrayList<PPProximityResponseVO>();
                for (final Iterator<Object> iter = proxResultsList.iterator(); iter.hasNext();) {
                    proximityResponseVO = new PPProximityResponseVO();
                    final Object[] proxRec = (Object[]) iter.next();
                    proximityResponseVO.setRoadInitial(RMDCommonUtility.convertObjectToString(proxRec[0]));
                    proximityResponseVO.setRoadNo(RMDCommonUtility.convertObjectToString(proxRec[1]));
                    proximityResponseVO.setModel(RMDCommonUtility.convertObjectToString(proxRec[2]));
                    proximityResponseVO.setLatitude(RMDCommonUtility.convertObjectToLong(proxRec[3]));
                    proximityResponseVO.setLongitude(RMDCommonUtility.convertObjectToLong(proxRec[4]));
                    double distance = Math.ceil(calculateDistance(gpsLat, proximityResponseVO.getLatitude(),
                            (gpsLat - proximityResponseVO.getLatitude()),
                            (gpsLong - proximityResponseVO.getLongitude())));
                    if (distance > mileRange) {
                        continue;
                    }
                    if (products != null && products.size() > 0 && !products.contains(RMDCommonConstants.DEFAULT_STRING)
                            && productObjidMap.get(RMDCommonUtility.convertObjectToString(proxRec[11])) == null) {
                        continue;
                    }
                    proximityResponseVO.setDistance(String.valueOf(distance));
                    proximityResponseVO.setDirection(getCityDirection(gpsLat, gpsLong,
                            proximityResponseVO.getLatitude(), proximityResponseVO.getLongitude()));
                    proximityResponseVO.setLocation(RMDCommonUtility.convertObjectToString(proxRec[5]));
                    proximityResponseVO.setStateCode(RMDCommonUtility.convertObjectToString(proxRec[6]));
                    proximityResponseVO.setMilePost(RMDCommonUtility.convertObjectToString(proxRec[7]));
                    proximityResponseVO.setRegion(RMDCommonUtility.convertObjectToString(proxRec[8]));
                    proximityResponseVO.setSubRegion(RMDCommonUtility.convertObjectToString(proxRec[9]));
                    Date lastMsgTime = RMDCommonUtility.stringToGMTDate(
                            RMDCommonUtility.convertObjectToString(proxRec[10]),
                            RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
                    proximityResponseVO.setLastMsgTime(lastMsgTime);
                    proximityResponseVO.setCustomerId(RMDCommonUtility.convertObjectToString(proxRec[12]));
                    proximityList.add(proximityResponseVO);
                }
                Collections.sort(proximityList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        PPProximityResponseVO r1 = (PPProximityResponseVO) o1;
                        PPProximityResponseVO r2 = (PPProximityResponseVO) o2;
                        Double s1 = Double.parseDouble(r2.getDistance());
                        Double s2 = Double.parseDouble(r1.getDistance());
                        return s2.compareTo(s1);
                    }
                });
            }
            return proximityList;
        } catch (JDBCException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @param lat
     * @param lat2
     * @param latDiff
     * @param lonDiff
     * @return This method is used to calculate the distance for given lat and
     *         long
     */
    private double calculateDistance(double lat, double lat2, double latDiff, double lonDiff) {
        double d = 0.0;
        double a1;
        double a2;
        double a3;
        double a4;
        double a;
        double af;
        double cf;

        lat = lat / 3600000;
        lat2 = lat2 / 3600000;
        latDiff = latDiff / 3600000;
        lonDiff = lonDiff / 3600000;
        lat = Math.toRadians(lat);
        lat2 = Math.toRadians(lat2);
        latDiff = Math.toRadians(latDiff);
        lonDiff = Math.toRadians(lonDiff);
        a1 = Math.pow(Math.sin(latDiff / 2), 2);
        a2 = Math.cos(lat);
        a3 = Math.cos(lat2);
        a4 = Math.pow(Math.sin(lonDiff / 2), 2);
        a = a1 + a2 * a3 * a4;
        af = 1 - a;
        if (af < 0) {
            af = -(af);
        }
        if (a < 0) {
            a = -(a);
        }
        cf = Math.atan2(Math.sqrt(a), Math.sqrt(af));
        d = 2 * cf * 3963;
        return d;
    }

    /**
     * @param cityLat
     * @param citylon
     * @param gpsLat
     * @param gpsLon
     * @return This method is used to get the city direction(E,W,N,S)
     */
    private String getCityDirection(double cityLat, double citylon, double gpsLat, double gpsLon) {
        String retString = RMDCommonConstants.EMPTY_STRING;
        double latDifference = gpsLat - cityLat;
        double lonDifference = gpsLon - citylon;
        if (checkAngle(latDifference, lonDifference) > 1.0) {
            retString = getLatitudeDirection(gpsLat, cityLat);
        }
        if (checkAngle(lonDifference, latDifference) > 1.0) {
            retString = retString + getLongitudeDirection(gpsLon, citylon);
        }
        return retString;
    }

    /**
     * @param l1
     * @param l2
     * @return Method is used for getting angle
     */
    private double checkAngle(double l1, double l2) {
        return Math.abs(Math.toDegrees(Math.atan(l1 / l2)));
    }

    /**
     * @param gpsLat
     * @param cityLat
     * @return Method is used for getting Latitude Direction
     */

    private String getLatitudeDirection(double gpsLat, double cityLat) {
        if (cityLat < gpsLat) {
            return RMDCommonConstants.LESSER_LATITUDE_DIRECTION;
        } else {
            return RMDCommonConstants.GREATER_LATITUDE_DIRECTION;
        }
    }

    /**
     * @param gpsLat
     * @param cityLat
     * @return Method is used for getting Longitude Direction
     */
    private String getLongitudeDirection(double gpsLon, double cityLon) {
        if (cityLon < gpsLon) {
            return RMDCommonConstants.LESSER_LONGITUDE_DIRECTION;
        } else {
            return RMDCommonConstants.GREATER_LONGITUDE_DIRECTION;
        }
    }

}