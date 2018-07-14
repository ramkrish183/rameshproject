package com.ge.trans.pp.services.geofencereport.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.geofencereport.dao.intf.GeofenceMasterProximityCacheDAOIntf;
import com.ge.trans.pp.services.geofencereport.dao.intf.GeofenceReportDAOIntf;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.AssetListResponseVo;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceMasterProximityVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportListReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportReqVO;
import com.ge.trans.pp.services.geofencereport.service.valueobjects.GeofenceReportVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class GeofenceReportDAOImpl extends RMDCommonDAO implements GeofenceReportDAOIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(GeofenceReportDAOImpl.class);

    private GeofenceMasterProximityCacheDAOIntf objGeofenceMPCacheDAOIntf;

    public GeofenceReportDAOImpl(final GeofenceMasterProximityCacheDAOIntf objGeofenceMPCacheDAOIntf) {
        this.objGeofenceMPCacheDAOIntf = objGeofenceMPCacheDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.geofencereport.dao.intf.GeofenceReportDAOIntf#
     * getGeofenceReport
     * (com.ge.trans.pp.services.geofencereport.service.valueobjects
     * .GeofenceReportReqVO) This method is used to fetch GeofenceReport details
     * from the system
     */
    @Override
    public List<GeofenceReportVO> getGeofenceReport(GeofenceReportListReqVO objGeofenceRoadRangeReqVO)
            throws RMDDAOException {

        Session hibernateSession = null;
        String histQuery;
        List<Object[]> historyDataList = null;
        List<Object[]> filterHistoryDataList = null;
        Date firstCMUTime;
        Date creationTime;
        long fuelUsed = 0;
        String customerId;
        String vehicleHeader;
        String roadNumber;
        String proximityDesc;
        String fuelLevel;
        String totalTime;
        String fleetNumber;
        List<String> geofences;

        Date secCMUTime;
        long days;
        GeofenceReportVO finalGeofenceReportVO = null;
        List<GeofenceReportVO> geofenceReportVOLst = new ArrayList<GeofenceReportVO>();
        List<GeofenceReportReqVO> geofenceReportReqVOList = null;

        try {
        	Map<String, String> lookUpMap = getLookUpValues(RMDServiceConstants.FUEL_ERROR_CODES);
            Map<String, GeofenceMasterProximityVO> geofenceMasterDataMap = objGeofenceMPCacheDAOIntf
                    .getGeofenceMasterProximities();

            hibernateSession = getHibernateSession();
            geofenceReportReqVOList = objGeofenceRoadRangeReqVO.getGeofenceReportReqVOList();

            DateFormat format = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
            if (geofenceReportReqVOList != null && !geofenceReportReqVOList.isEmpty()) {

                AssetListResponseVo assetListResponseVo = getVehicleObjIdList(objGeofenceRoadRangeReqVO);
                GeofenceReportReqVO objGeofenceReportHistoryReqVO = geofenceReportReqVOList.get(0);

                geofences = objGeofenceRoadRangeReqVO.getGeofences();

                histQuery = getProximityHistoryQuery(assetListResponseVo.getQuery());
                objGeofenceReportHistoryReqVO.setGeofences(geofences);
                historyDataList = getProximityHistoryData(hibernateSession, histQuery, objGeofenceReportHistoryReqVO,
                        assetListResponseVo);
                filterHistoryDataList = getProximityFilteredHistoryData(hibernateSession, histQuery,
                        objGeofenceReportHistoryReqVO, assetListResponseVo);

                if (RMDCommonUtility.isCollectionNotEmpty(filterHistoryDataList)
                        && RMDCommonUtility.isCollectionNotEmpty(historyDataList)) {
                    Object[] objhistoryData = null;
                    Object[] objGpsChkhistoryData = null;
                    Object[] firstRow = null;
                    Object[] secondRow = null;
                    String proxDescKey = null;
                    GeofenceMasterProximityVO masterProximity = null;
                    int firstIndex = -1;
                    int secondIndex = -1;

                    boolean isGPSCheckPassed = false;
                    float historyGPSLat = 0;
                    float historyGPSLong = -1;
                    int count = -1;
                    List<Object[]> GPSChechData = new ArrayList<Object[]>();
                    for (int i = 0; i < filterHistoryDataList.size() && filterHistoryDataList.size() > 1; i++) {
                        if (i == 0) {
                            // Initial block
                            firstRow = filterHistoryDataList.get(i);
                            secondRow = filterHistoryDataList.get(i + 1);
                            i = i + 1;
                        } else if (!isGPSCheckPassed) {
                            // GPSCheck Faild block
                            secondRow = filterHistoryDataList.get(i);
                        } else {
                            // GPSCheckPassed block
                            firstRow = filterHistoryDataList.get(i - 1);
                            secondRow = filterHistoryDataList.get(i);
                        }

                        // two consecutive proxDesc should be equal
                        if (!(null != firstRow[2] && null != firstRow[3] && firstRow[2].equals(secondRow[2])
                                && firstRow[3].equals(secondRow[3]))
                                || (firstRow[8] != null && secondRow[8] != null && !firstRow[8].equals(secondRow[8]))) {
                            firstRow = secondRow;
                            // assigning gpscheck as false to avoid the previous
                            // pass scenario.
                            isGPSCheckPassed = false;
                            continue;
                        }

                        count = -1;
                        firstIndex = -1;
                        secondIndex = -1;
                        // prxdescKey = custId_proxDesc
                        proxDescKey = objGeofenceReportHistoryReqVO.getCustomerId() + RMDCommonConstants.UNDERSCORE
                                + firstRow[8];
                        // collecting GPSChechData
                        for (int j = 0; j < historyDataList.size(); j++) {
                            objhistoryData = historyDataList.get(j);
                            count++;
                            if (firstIndex != -1) {
                                GPSChechData.add(objhistoryData);
                            }

                            if (null != objhistoryData[0] && objhistoryData[0].equals(firstRow[0])
                                    && null != objhistoryData[1] && objhistoryData[1].equals(firstRow[1])
                                    && null != objhistoryData[8] && objhistoryData[8].equals(firstRow[8])
                                    && null != objhistoryData[2] && objhistoryData[2].equals(firstRow[2])
                                    && null != objhistoryData[3] && objhistoryData[3].equals(firstRow[3])
                                    && firstIndex == -1) {
                                firstIndex = count;
                                // firstRecordFound = true;
                                GPSChechData.add(objhistoryData);
                                continue;
                            }
                            if (null != objhistoryData[0] && objhistoryData[0].equals(secondRow[0])
                                    && null != objhistoryData[1] && objhistoryData[1].equals(secondRow[1])
                                    && null != objhistoryData[8] && objhistoryData[8].equals(secondRow[8])
                                    && null != objhistoryData[2] && objhistoryData[2].equals(firstRow[2])
                                    && null != objhistoryData[3] && objhistoryData[3].equals(firstRow[3])
                                    && firstIndex != -1 && secondIndex == -1) {
                                secondIndex = count;
                                break;
                            }

                        }
                        // after collect the GPSChechData, remove the row data
                        // from historyDataList

                        /*
                         * for (int l = 0; l < count; l++) { // Remove all
                         * records till // secondRecordfound index(excluding
                         * SecondRecord). historyDataList.remove(0); }
                         */

                        // GPSChechData validation and prepare the report data.
                        for (int k = 0; k < GPSChechData.size(); k++) {
                            masterProximity = geofenceMasterDataMap.get(proxDescKey);
                            objGpsChkhistoryData = GPSChechData.get(k);
                            historyGPSLat = objGpsChkhistoryData[4] != null
                                    ? Float.parseFloat(objGpsChkhistoryData[4].toString()) : -1;
                            historyGPSLong = objGpsChkhistoryData[5] != null
                                    ? Float.parseFloat(objGpsChkhistoryData[5].toString()) : -1;

                            if (masterProximity == null || historyGPSLat > masterProximity.getUpLeftLatitude()
                                    || historyGPSLat < masterProximity.getLowRightLatitude()
                                    || historyGPSLong < masterProximity.getUpLeftLongitude()
                                    || historyGPSLong > masterProximity.getLowRightLongitude()) {
                                GPSChechData.clear();
                                firstRow = secondRow;
                                isGPSCheckPassed = false;
                                break;
                            } else {
                                isGPSCheckPassed = true;
                            }
                        }

                        if (isGPSCheckPassed) {

                            finalGeofenceReportVO = new GeofenceReportVO();
                            firstCMUTime = format.parse(RMDCommonUtility.convertObjectToString(firstRow[1]));
                            creationTime = format.parse(RMDCommonUtility.convertObjectToString(firstRow[0]));
                            
                            fuelLevel = RMDCommonUtility.convertObjectToString(firstRow[6]);
                            try{
	                            if (secondRow[6] != null && fuelLevel != null && Long.parseLong(fuelLevel) < 60000) {
	                                fuelUsed = Long.parseLong(secondRow[6].toString()) - Long.parseLong(fuelLevel);
	                            }
	                            
	                            if (null != fuelLevel &&  !fuelLevel.isEmpty() && Long.parseLong(fuelLevel) > 60000) {
	                            	String desc = lookUpMap.get(RMDCommonConstants.ERC+fuelLevel);
	    							StringBuilder codeStr = new StringBuilder();
	    							codeStr.append(fuelLevel).append("~").append(desc);
	    							fuelLevel = codeStr.toString();
	                            }
                          	}catch( NumberFormatException  ex){
                          		fuelLevel = RMDCommonConstants.ZERO_STRING;
                          		fuelUsed = 0;
                          	}
                            
                            
                            customerId = RMDCommonUtility.convertObjectToString(firstRow[10]);
                            vehicleHeader = RMDCommonUtility.convertObjectToString(firstRow[2]);
                            roadNumber = RMDCommonUtility.convertObjectToString(firstRow[3]);
                            proximityDesc = RMDCommonUtility.convertObjectToString(firstRow[8]);
                            fleetNumber = RMDCommonUtility.convertObjectToString(firstRow[11]);

                            days = RMDCommonUtility.dayDiff(RMDCommonUtility.convertObjectToString(firstRow[0]),
                                    RMDCommonUtility.convertObjectToString(firstRow[1]),
                                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                            if (days > 180 || days < -180) {
                                firstCMUTime = creationTime;
                            }
                            secCMUTime = format.parse(RMDCommonUtility.convertObjectToString(secondRow[1]));
                            totalTime = RMDCommonUtility.calculateAge(firstCMUTime, secCMUTime);

                            finalGeofenceReportVO.setCustomerId(customerId);
                            finalGeofenceReportVO.setFleet(fleetNumber);
                            finalGeofenceReportVO.setRoadInitial(vehicleHeader);
                            finalGeofenceReportVO.setRoadNumber(roadNumber);
                            finalGeofenceReportVO.setGeofenceName(AppSecUtil.htmlEscaping(proximityDesc));
                            finalGeofenceReportVO.setEntryTime(firstCMUTime);
                            finalGeofenceReportVO.setTotalTime(totalTime);
                            finalGeofenceReportVO
                                    .setEntryFuelLevel(fuelLevel != null ? fuelLevel : RMDCommonConstants.ZERO_STRING);
                            finalGeofenceReportVO.setTotalFuel(Long.toString(fuelUsed));

                            geofenceReportVOLst.add(finalGeofenceReportVO);
                            GPSChechData.clear();
                            i = i + 1;
                            // if GPS check Passed, Remove the SecondRecord
                            // also.
                            historyDataList.remove(0);
                        }
                    }
                }
            }

        } catch (JDBCException ex) {
            LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return geofenceReportVOLst;

    }

    private AssetListResponseVo getVehicleObjIdList(GeofenceReportListReqVO geofenceReportListReqVO) {
        List<GeofenceReportReqVO> geofenceReportReqVOList = geofenceReportListReqVO.getGeofenceReportReqVOList();
        // the map which have the customer id and corresponding where conditions
        Map<String, String> queryMapForCustomers = new HashMap<String, String>();
        // parameter Map will have all the named parameters and the values to
        // set
        Map<String, String> parameterMap = new HashMap<String, String>();
        StringBuilder vehicleObjidQuery = new StringBuilder();
        AssetListResponseVo assetListResponseVo = new AssetListResponseVo();
        int i = 0;
        vehicleObjidQuery.append(" SELECT VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V WHERE ");
        String productQuery = null;
        // checking if the user didnt select a road range
        if (null != geofenceReportReqVOList && !geofenceReportReqVOList.isEmpty()
                && null != geofenceReportReqVOList.get(0).getFromRoadNo()
                && !geofenceReportReqVOList.get(0).getFromRoadNo().isEmpty()) {
            // looping through the different road range vo to create the query
            for (GeofenceReportReqVO objGeofenceReportHistoryReqVO : geofenceReportReqVOList) {
                if (!queryMapForCustomers.containsKey(objGeofenceReportHistoryReqVO.getCustomerId())) {
                    StringBuilder customerRangeQuery = new StringBuilder();
                    customerRangeQuery.append(" ( ORG_ID =:" + RMDCommonConstants.CUSTOMER + i + " ");
                    parameterMap.put(RMDCommonConstants.CUSTOMER + i, objGeofenceReportHistoryReqVO.getCustomerId());
                    if (!RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getRoadInitial())) {
                        customerRangeQuery
                                .append(" AND ((VEHICLE_HDR =:" + RMDCommonConstants.VEHICLE_HEADER + i + " ");
                        parameterMap.put(RMDCommonConstants.VEHICLE_HEADER + i,
                                objGeofenceReportHistoryReqVO.getRoadInitial());

                    }

                    if (!RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getFromRoadNo())
                            && !RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getToRoadNo())) {
                        customerRangeQuery.append("AND LPAD(VEHICLE_NO, 10,'0') BETWEEN LPAD(:"
                                + RMDCommonConstants.FROM_ROAD_NUMBER + i + ", 10,'0') AND LPAD(:"
                                + RMDCommonConstants.TO_ROAD_NUMBER + i + ", 10,'0') ");
                        parameterMap.put(RMDCommonConstants.FROM_ROAD_NUMBER + i,
                                objGeofenceReportHistoryReqVO.getFromRoadNo());
                        parameterMap.put(RMDCommonConstants.TO_ROAD_NUMBER + i,
                                objGeofenceReportHistoryReqVO.getToRoadNo());
                    }
                    if (null != objGeofenceReportHistoryReqVO.getFleets() && !objGeofenceReportHistoryReqVO.getFleets().isEmpty()) {
                        customerRangeQuery.append(" AND FLEET_NUMBER_V  IN (:" + RMDCommonConstants.FLEET + i + ")) ");
                        String fleetInput = "";
                        for (int j = 0; j < objGeofenceReportHistoryReqVO.getFleets().size(); j++) {
                            fleetInput += objGeofenceReportHistoryReqVO.getFleets().get(j);
                            if (j < objGeofenceReportHistoryReqVO.getFleets().size() - 1) {
                                fleetInput += ",";
                            }
                        }
                        parameterMap.put(RMDCommonConstants.FLEET + i, fleetInput);
                    } else {
                        customerRangeQuery.append(") ");
                    }
                    queryMapForCustomers.put(objGeofenceReportHistoryReqVO.getCustomerId(),
                            customerRangeQuery.toString());

                } else {
                    String tempCustomerRangeQuery = queryMapForCustomers
                            .get(objGeofenceReportHistoryReqVO.getCustomerId());

                    if (!RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getRoadInitial())) {
                        tempCustomerRangeQuery += " OR (VEHICLE_HDR =:" + RMDCommonConstants.VEHICLE_HEADER + i + " ";
                        parameterMap.put(RMDCommonConstants.VEHICLE_HEADER + i,
                                objGeofenceReportHistoryReqVO.getRoadInitial());

                    }

                    if (!RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getFromRoadNo())
                            && !RMDCommonUtility.isNullOrEmpty(objGeofenceReportHistoryReqVO.getToRoadNo())) {
                        tempCustomerRangeQuery += "AND LPAD(VEHICLE_NO, 10,'0') BETWEEN LPAD(:"
                                + RMDCommonConstants.FROM_ROAD_NUMBER + i + ", 10,'0') AND LPAD(:"
                                + RMDCommonConstants.TO_ROAD_NUMBER + i + ", 10,'0') ";
                        parameterMap.put(RMDCommonConstants.FROM_ROAD_NUMBER + i,
                                objGeofenceReportHistoryReqVO.getFromRoadNo());
                        parameterMap.put(RMDCommonConstants.TO_ROAD_NUMBER + i,
                                objGeofenceReportHistoryReqVO.getToRoadNo());
                    }
                    if (null != objGeofenceReportHistoryReqVO.getFleets()
                            && objGeofenceReportHistoryReqVO.getFleets().size() > 0) {
                        tempCustomerRangeQuery += " AND FLEET_NUMBER_V  IN (:" + RMDCommonConstants.FLEET + i + ")) ";
                        String fleetInput = "";
                        for (int j = 0; j < objGeofenceReportHistoryReqVO.getFleets().size(); j++) {
                            fleetInput += objGeofenceReportHistoryReqVO.getFleets().get(j);
                            if (j < objGeofenceReportHistoryReqVO.getFleets().size() - 1) {
                                fleetInput += ",";
                            }
                        }
                        parameterMap.put(RMDCommonConstants.FLEET + i, fleetInput);
                    } else {
                        tempCustomerRangeQuery += ") ";
                    }
                    queryMapForCustomers.put(objGeofenceReportHistoryReqVO.getCustomerId(), tempCustomerRangeQuery);
                }
                i++;
            }
            int count = queryMapForCustomers.size();
            for (Map.Entry<String, String> entry : queryMapForCustomers.entrySet()) {
                vehicleObjidQuery.append(entry.getValue() + "))");
                count--;
                if (count != 0) {
                    vehicleObjidQuery.append(" OR ");
                }

            }

        } else {
            vehicleObjidQuery.append(" ORG_ID =:" + RMDCommonConstants.CUSTOMER + i + " ");
            parameterMap.put(RMDCommonConstants.CUSTOMER + i, geofenceReportReqVOList.get(0).getCustomerId());
            if (null != geofenceReportReqVOList.get(0).getFleets() && !geofenceReportReqVOList.get(0).getFleets().isEmpty()) {
                vehicleObjidQuery.append(" AND FLEET_NUMBER_V  IN (:" + RMDCommonConstants.FLEET + i + ") ");
                String fleetInput = "";
                for (int j = 0; j < geofenceReportReqVOList.get(0).getFleets().size(); j++) {
                    fleetInput += geofenceReportReqVOList.get(0).getFleets().get(j);
                    if (j < geofenceReportReqVOList.get(0).getFleets().size() - 1) {
                        fleetInput += ",";
                    }
                }
                parameterMap.put(RMDCommonConstants.FLEET + i, fleetInput);
            }
        }
        
        if(!RMDCommonUtility.isNullOrEmpty(geofenceReportListReqVO.getRoleId()) && !RMDCommonConstants.DEFAULT_STRING.equalsIgnoreCase(geofenceReportListReqVO.getRoleId())){
           productQuery=getProductQueryForRole(geofenceReportListReqVO.getRoleId());
           vehicleObjidQuery.append("AND VEHICLE_OBJID IN("+productQuery+")");
            
        }
        
        assetListResponseVo.setQuery(vehicleObjidQuery.toString());
        assetListResponseVo.setParameterMap(parameterMap);
        return assetListResponseVo;
    }

    private String getProximityHistoryQuery(String vehicleObjidListQuery) {
        StringBuilder histQuery = new StringBuilder();

        histQuery.append(
                " SELECT /*+ DRIVING_SITE(Hist) */ TO_CHAR(Hist.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS') CREATION_DATE, TO_CHAR(Hist.CMU_TIME, 'MM/DD/YYYY HH24:MI:SS') CMU_TIME, ");
        histQuery.append(" Hist.VEHICLE_HDR, Hist.VEHICLE_NO, Hist.GPS_LAT_DISPLAY,  Hist.GPS_LON_DISPLAY, ");
        histQuery.append(" Hist.FUEL_LEVEL, Hist.PROXIMITY_EVENT, ");
        histQuery.append(" CASE WHEN instr(Hist.PROXIMITY_DESC,'_') > 0 ");
        histQuery.append(" THEN SUBSTR(Hist.PROXIMITY_DESC,0,(instr(Hist.PROXIMITY_DESC,'_')-1)) ");
        histQuery.append(
                " ELSE Hist.PROXIMITY_DESC END ProxDesc, Hist.PROXIMITY_DESC ProxDescOriginal, Hist.Customer_ID ");
        histQuery.append(" ,Hist.FLEET_NO  ");

        histQuery.append(" FROM PP_DW.GETS_DW_PP_STATUS_HIST@RMD_EOA_DW.WORLD Hist ");
        histQuery.append(" WHERE GPS_LAT_DISPLAY is not null AND GPS_LON_DISPLAY is not null ");
        histQuery.append(" AND Hist.CREATION_DATE > to_date(:fromDate,'MM/DD/YYYY HH24:MI:SS') ");
        histQuery.append(" AND Hist.CREATION_DATE <=to_date(:toDate,'MM/DD/YYYY HH24:MI:SS') ");
        if (null != vehicleObjidListQuery && !vehicleObjidListQuery.isEmpty()) {
            histQuery.append(" AND Hist.STATUS_HIST2VEHICLE IN  (");
            histQuery.append(vehicleObjidListQuery);
            histQuery.append(") ");
        }
        return histQuery.toString();
    }

    private List<Object[]> getProximityHistoryData(Session hibernateSession, String histQuery,
            GeofenceReportReqVO objGeofenceReportHistoryReqVO, AssetListResponseVo assetListResponseVo) {
        StringBuilder histDataQuery = new StringBuilder();
        Query hibernateQuery = null;
        List<Object[]> historyDataList = null;
        histDataQuery.append(histQuery);
        histDataQuery.append(" ORDER BY 4,1 ASC ");
        hibernateQuery = hibernateSession.createSQLQuery(histDataQuery.toString());

        hibernateQuery.setFetchSize(1000);
        List<String> geofencesList = objGeofenceReportHistoryReqVO.getGeofences();

        hibernateQuery.setParameter(RMDCommonConstants.FROM_DATE, objGeofenceReportHistoryReqVO.getFromDate());
        hibernateQuery.setParameter(RMDCommonConstants.TO_DATE, objGeofenceReportHistoryReqVO.getToDate());
        if (null != assetListResponseVo.getParameterMap()) {
            for (Map.Entry<String, String> entry : assetListResponseVo.getParameterMap().entrySet()) {
                if (entry.getKey().contains(RMDCommonConstants.FLEET)) {
                    List<String> fleetList = Arrays.asList(entry.getValue().split(RMDCommonConstants.COMMMA_SEPARATOR));
                    hibernateQuery.setParameterList(entry.getKey(), fleetList);
                } else {
                    hibernateQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        historyDataList = hibernateQuery.list();
        return historyDataList;

    }

    private List<Object[]> getProximityFilteredHistoryData(Session hibernateSession, String histQuery,
            GeofenceReportReqVO objGeofenceReportHistoryReqVO, AssetListResponseVo assetListResponseVo) {
        StringBuilder filterDataQuery = new StringBuilder();
        Query hibernateQuery = null;
        List<String> geofencesList = objGeofenceReportHistoryReqVO.getGeofences();
        filterDataQuery.append(histQuery);
        filterDataQuery.append(" AND Hist.PROXIMITY_DESC is not null AND Hist.PROXIMITY_EVENT = 'A' ");
        filterDataQuery.append("AND ( CASE WHEN instr(Hist.PROXIMITY_DESC,'_') > 0  THEN "
                + "SUBSTR(Hist.PROXIMITY_DESC,0,(instr(Hist.PROXIMITY_DESC,'_')-1))  "
                + "ELSE Hist.PROXIMITY_DESC  END ) IN ");
        if (null != geofencesList && !geofencesList.isEmpty()) {
            filterDataQuery.append("(:geofences");
        } else {
            filterDataQuery.append(
                    "(SELECT mster.GEOFENCE_NAME FROM GETS_RMD.GETS_RMD_OMD_ATS_PROXIMITIES mster, TABLE_BUS_ORG busorg WHERE mster.LINK_CUSTOMER=busorg.OBJID ");
            if (!RMDCommonConstants.ALL_CUSTOMER.equals(objGeofenceReportHistoryReqVO.getCustomerId())) {
                filterDataQuery.append("AND busorg.ORG_ID=:custId ");
            }

        }
        filterDataQuery.append(") ORDER BY 4,9,1 ASC ");
        hibernateQuery = hibernateSession.createSQLQuery(filterDataQuery.toString());

        hibernateQuery.setFetchSize(1000);
        hibernateQuery.setParameter(RMDCommonConstants.FROM_DATE, objGeofenceReportHistoryReqVO.getFromDate());
        hibernateQuery.setParameter(RMDCommonConstants.TO_DATE, objGeofenceReportHistoryReqVO.getToDate());
        if (null != assetListResponseVo.getParameterMap()) {
            for (Map.Entry<String, String> entry : assetListResponseVo.getParameterMap().entrySet()) {
                if (entry.getKey().contains(RMDCommonConstants.FLEET)) {
                    List<String> fleetList = Arrays.asList(entry.getValue().split(RMDCommonConstants.COMMMA_SEPARATOR));
                    hibernateQuery.setParameterList(entry.getKey(), fleetList);
                } else {
                    hibernateQuery.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        if (null != geofencesList && !geofencesList.isEmpty()) {
            hibernateQuery.setParameterList(RMDCommonConstants.GEOFENCE_NAMES, geofencesList);
        } else {
            if (!RMDCommonConstants.ALL_CUSTOMER.equals(objGeofenceReportHistoryReqVO.getCustomerId())) {
                hibernateQuery.setParameter(RMDCommonConstants.CUST_ID, objGeofenceReportHistoryReqVO.getCustomerId());

            }
        }
        return hibernateQuery.list();

    }
    
}
