package com.ge.trans.pp.services.notification.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.pp.services.notification.dao.intf.NotificationDAOIntf;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class NotificationDAOImpl extends RMDCommonDAO implements NotificationDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotificationDAOImpl.class);
    Codec ORACLE_CODEC = new OracleCodec();

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.notification.dao.intf.NotificationDAOIntf#
     * getPPCustomerRegions(java.lang.String, java.lang.String) This method is
     * used for fetching list of regions from the system
     */
    @Override
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder regionQry = new StringBuilder();
        List<String> objRegionLst = null;

        try {
            regionQry.append("SELECT DISTINCT GRPMD.REGION FROM GETS_RMD_PP_SUB_REGION GRPMD,TABLE_BUS_ORG TBO ");
            regionQry.append(" WHERE GRPMD.PP_SUB_REGION2BUS_ORG  =  TBO.OBJID AND TBO.ORG_ID = :customerId");

            hibernateSession = getHibernateSession();
            if (hibernateSession != null) {
                Query hibernateQuery = hibernateSession.createSQLQuery(regionQry.toString());

                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
                objRegionLst = hibernateQuery.list();
            }
        } catch (JDBCException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_SQL_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return objRegionLst;

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.notification.dao.intf.NotificationDAOIntf#
     * getNotificationHistory
     * (com.ge.trans.pp.services.notification.service.valueobjects
     * .PPNotificationHistoryReqVO) This method is used to fetch notification
     * history details from the system
     */
    @Override
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder vehQry = new StringBuilder();
        StringBuilder custQry = new StringBuilder();
        List<String> customerLst = null;
        List<Object[]> vehObjIdLst = null;
        String customerObjId = null;
        String vehicleObjId = null;
        String lastDays = RMDCommonConstants.ZERO_STRING;
        String allRoadNos = null;
        String status = null;
        String region = null;
        Connection con = null;
        CallableStatement callableStatement = null;
        ResultSet notificationHistoryRs = null;
        Map<String, String> productObjidMap = new HashMap<String, String>();
        List<PPNotificationHistoryVO> ppNotificationHistoryVOLst = new ArrayList<PPNotificationHistoryVO>();
        PPNotificationHistoryVO objPPNotificationHistoryVO = null;
        String strVar = null;
        String[] splitVal = null;
        String notificationType = null;
        List<Object> objIdleReportProd = null;
        Query hibernateQuery = null;
        String productCdQuery = null;
        String productCd = "";
        try {
        	Map<String, String> lookUpMap = getLookUpValues(RMDServiceConstants.FUEL_ERROR_CODES);
            hibernateSession = getHibernateSession();
            if (objPPNotHistoryReqVO.isInactiveNotification()) {
                status = RMDCommonConstants.CAPS_ALL;
            } else {
                status = RMDCommonConstants.ACTIVATE;
            }

            if (null != objPPNotHistoryReqVO.getRegion() && !objPPNotHistoryReqVO.getRegion().isEmpty()) {
                region = objPPNotHistoryReqVO.getRegion();
            } else {
                region = null;
            }

            /* If asset No is present getting the vehicleObjId */
            if (null != objPPNotHistoryReqVO.getRoadNo() && !objPPNotHistoryReqVO.getRoadNo().isEmpty()) {
                vehQry.append("SELECT DISTINCT BUS_ORG_OBJID,VEHICLE_OBJID,  MODEL_NAME_V ");
                vehQry.append(" FROM GETS_RMD_CUST_RNH_RN_V ");
                vehQry.append(" WHERE ORG_ID = :customerId ");
                vehQry.append(" AND VEHICLE_HDR = :assetGrpName AND VEHICLE_NO = :assetNumber");
                Query vehQuery = hibernateSession.createSQLQuery(vehQry.toString());
                vehQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objPPNotHistoryReqVO.getCustomerId()));
                vehQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objPPNotHistoryReqVO.getRoadInitial()));
                vehQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objPPNotHistoryReqVO.getRoadNo()));
                vehObjIdLst = vehQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(vehObjIdLst)) {
                    Object[] obj = vehObjIdLst.get(0);
                    customerObjId = RMDCommonUtility.convertObjectToString(obj[0]);
                    vehicleObjId = RMDCommonUtility.convertObjectToString(obj[1]);
                }
            }
            /* If asset Number is not there getting the customerObjId */
            else {
                custQry.append("SELECT TBO.OBJID FROM TABLE_BUS_ORG TBO WHERE TBO.ORG_ID =:customerId");
                Query custQuery = hibernateSession.createSQLQuery(custQry.toString());
                custQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objPPNotHistoryReqVO.getCustomerId()));
                customerLst = custQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(customerLst)) {
                    customerObjId = RMDCommonUtility.convertObjectToString(customerLst.get(0));
                }
                vehicleObjId = RMDCommonConstants.ZERO_STRING;
                allRoadNos = RMDCommonConstants.CAPS_ALL;
            }

            if (null != vehicleObjId && !vehicleObjId.isEmpty()) {
                if (objPPNotHistoryReqVO.getProducts() != null && objPPNotHistoryReqVO.getProducts().size() > 0
                        && !objPPNotHistoryReqVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                    StringBuilder prodQueryString = new StringBuilder();

                    productCdQuery = "SELECT GET_RMD_OMD_PRODUCT_SEQ_ID FROM GETS_RMD.GETS_RMD_OMD_PRODUCT WHERE PRODUCT_CD IN(:productNameLst)";
                    hibernateQuery = hibernateSession.createSQLQuery(productCdQuery);
                    hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                            EsapiUtil.encodeForSQLfromList(objPPNotHistoryReqVO.getProducts()));
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

                    prodQueryString.append(
                            " SELECT TSP.X_VEH_HDR,TSP.SERIAL_NO FROM GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST, ");
                    prodQueryString.append(" TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, ");
                    prodQueryString.append(" TABLE_BUS_ORG TBO WHERE ELGASST.ASSET_ID = VEH.OBJID ");
                    prodQueryString
                            .append(" AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID ");
                    prodQueryString
                            .append(" AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                    prodQueryString.append(" AND TBO.ORG_ID = '" + ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objPPNotHistoryReqVO.getCustomerId()));
                    prodQueryString.append("' AND ELGASST.GET_RMD_OMD_PRODUCT_SEQ_ID IN (" + productCd + " ) ");
                    Query query = hibernateSession.createSQLQuery(prodQueryString.toString());
                    List<Object> productResList = query.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(productResList)) {
                        for (final Iterator<Object> iter = productResList.iterator(); iter.hasNext();) {
                            final Object[] prodRec = (Object[]) iter.next();
                            String key = RMDCommonUtility.convertObjectToString(prodRec[0])
                                    + RMDCommonConstants.UNDERSCORE
                                    + RMDCommonUtility.convertObjectToString(prodRec[1]);
                            productObjidMap.put(key, key);
                        }
                    }
                }

                con = hibernateSession.connection();
                callableStatement = con.prepareCall(
                        "{ ? =call gets_sd_notify_hist_pkg.gets_notify_detail_fn(?,?,?,?,?,?,?,?,?,?,?,?)}");
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, customerObjId);
                callableStatement.setString(3, vehicleObjId);
                callableStatement.setString(4, objPPNotHistoryReqVO.getFromDate());
                callableStatement.setString(5, objPPNotHistoryReqVO.getToDate());
                callableStatement.setString(6, lastDays);
                callableStatement.setString(7, allRoadNos);
                callableStatement.setString(8, objPPNotHistoryReqVO.getNotificationTypes());
                callableStatement.setString(9, status);
                callableStatement.setString(10, region);
                callableStatement.setString(11, objPPNotHistoryReqVO.getTimeZone());
                callableStatement.registerOutParameter(12, java.sql.Types.INTEGER);
                callableStatement.registerOutParameter(13, java.sql.Types.VARCHAR);
                callableStatement.executeQuery();
                notificationHistoryRs = (ResultSet) callableStatement.getObject(1);
                final int lstSize = notificationHistoryRs.getFetchSize();

                if (lstSize > 0) {
                    while (notificationHistoryRs.next()) {
                        strVar = null;
                        splitVal = null;
                        String key = RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(1))
                                + RMDCommonConstants.UNDERSCORE
                                + RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(2));
                        if (objPPNotHistoryReqVO.getProducts() != null && !objPPNotHistoryReqVO.getProducts().isEmpty()
                                && !objPPNotHistoryReqVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)
                                && productObjidMap.get(key) == null) {
                            continue;
                        }
                        objPPNotificationHistoryVO = new PPNotificationHistoryVO();
                        objPPNotificationHistoryVO.setAssetGrpName(
                                RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(1)));
                        objPPNotificationHistoryVO.setAssetNumber(
                                RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(2)));
                        objPPNotificationHistoryVO.setDateTime(
                                RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(3)));
                        objPPNotificationHistoryVO.setComments(AppSecUtil.htmlEscaping(formatString(
                                RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(4)))));
                        notificationType = notificationHistoryRs.getString(5);
                        if (null != notificationType) {
                            notificationType = StringUtils.replace(notificationType, RMDCommonConstants.UNDERSCORE,
                                    RMDCommonConstants.BLANK_SPACE);
                        }
                        objPPNotificationHistoryVO.setNotificationType(notificationType);
                        objPPNotificationHistoryVO.setEmail(notificationHistoryRs.getString(6));

                        /*
                         * Splitting the string based on "^" delimiter and
                         * populating the VO
                         */
                        strVar = RMDCommonUtility.convertObjectToString(notificationHistoryRs.getString(7));

                        splitVal = RMDCommonUtility.splitString(strVar, RMDCommonConstants.CAPSYMBOL);
                        for (int i = 0; i < splitVal.length; i++) {
                            if (i == 0) {
                                objPPNotificationHistoryVO.setStatus(splitVal[i]);
                            }
                            if (i == 1) {
                                objPPNotificationHistoryVO.setLocation(splitVal[i]);
                            }
                            if (i == 2) {
                                objPPNotificationHistoryVO.setState(splitVal[i]);
                            }
                            if (i == 3) {
                                objPPNotificationHistoryVO.setMilepost(splitVal[i]);
                            }
                            if (i == 4) {
                                objPPNotificationHistoryVO.setCurrRegion(splitVal[i]);
                            }
                            if (i == 5) {
                                objPPNotificationHistoryVO.setSubRegion(splitVal[i]);
                            }
                            if (i == 6) {
                            	if(Long.parseLong(splitVal[i]) >60000 && splitVal[i]!=null){
                            		String desc = lookUpMap.get(RMDCommonConstants.ERC+splitVal[i]);
        							StringBuilder codeStr = new StringBuilder();
        							codeStr.append(splitVal[i]).append("~").append(desc);
        						    objPPNotificationHistoryVO.setFuelLevel(codeStr.toString());
                            	}else{
                            		objPPNotificationHistoryVO.setFuelLevel(splitVal[i]);
                            	}
                            }
                            if (i == 7) {
                                objPPNotificationHistoryVO.setFuelAdded(splitVal[i]);
                            }
                            if (i == 8) {
                                objPPNotificationHistoryVO.setGpsLatDisplay(splitVal[i]);
                            }
                            if (i == 9) {
                                objPPNotificationHistoryVO.setGpsLonDisplay(splitVal[i]);
                            }
                        }
                        ppNotificationHistoryVOLst.add(objPPNotificationHistoryVO);

                    }
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
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objPPNotHistoryReqVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objPPNotHistoryReqVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {

            try {
                if (null != notificationHistoryRs) {
                    notificationHistoryRs.close();
                }
                try {
                    if (null != callableStatement) {
                        callableStatement.close();
                    }
                    if (null != con) {
                        con.close();
                    }
                } catch (SQLException e) {
                    throw e;
                }
            } catch (SQLException e) {
                LOG.error(
                        "In NotificationDAOImpl : Unexpected Error occured in NotificationDAOImpl getNotificationHistory() while closing the resultset. "
                                + e.getMessage());
            }
            releaseSession(hibernateSession);
        }

        return ppNotificationHistoryVOLst;

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.notification.dao.intf.NotificationDAOIntf#
     * getRoadInitialsForCustomer(java.lang.String) This method is used for
     * getting road initials for the customer
     */
    @Override
    public List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDDAOException {
        Session session = null;
        PPRoadInitialVO roadInitialVO = null;
        List<PPRoadInitialVO> roadInitialList = null;
        final StringBuilder queryString = new StringBuilder();
        try {
            session = getHibernateSession();
            queryString.append(
                    "SELECT VH.VEH_HDR, VH.VEH_HDR_NO FROM GETS_RMD_VEH_HDR VH,TABLE_BUS_ORG TBO  WHERE VH.VEH_HDR2BUSORG = TBO.OBJID ");
            if (customerId != null && !RMDCommonUtility.checkNull(customerId)) {
                queryString.append("AND TBO.ORG_ID=:orgId ");
            }
            queryString.append(" ORDER BY VH.VEH_HDR");
            Query query = session.createSQLQuery(queryString.toString());
            if (customerId != null && !RMDCommonUtility.checkNull(customerId)) {

                query.setParameter(RMDCommonConstants.ORGID, customerId);
            }
            List<Object> searchResultsList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(searchResultsList)) {
                roadInitialList = new ArrayList<PPRoadInitialVO>();
                for (final Iterator<Object> iter = searchResultsList.iterator(); iter.hasNext();) {
                    roadInitialVO = new PPRoadInitialVO();
                    final Object[] roadInitialRec = (Object[]) iter.next();
                    roadInitialVO.setRoadInitialName(RMDCommonUtility.convertObjectToString(roadInitialRec[0]));
                    roadInitialVO.setRoadInitialNo(RMDCommonUtility.convertObjectToString(roadInitialRec[1]));
                    roadInitialList.add(roadInitialVO);
                }
            }
            return roadInitialList;
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

    private String formatString(String s_str) {
        int i_start_pos = 0;
        int i_end_pos = 0;
        String s_lower = s_str.toLowerCase();
        i_end_pos = s_lower.indexOf(RMDCommonConstants.NOTIFICATION_LOCATION);
        if (s_lower.indexOf(RMDCommonConstants.NOTIFICATION_COMMENTS_COLON) != -1){
            i_start_pos = s_lower.indexOf(RMDCommonConstants.NOTIFICATION_COMMENTS_COLON) + 11;
        }else if (s_lower.indexOf(RMDCommonConstants.NOTIFICATION_COMMENTS) != -1){
            i_start_pos = s_lower.indexOf(RMDCommonConstants.NOTIFICATION_COMMENTS) + 9;
        }
        if ((i_start_pos != -1) && (i_end_pos != -1)) {
            s_str = s_str.substring(i_start_pos, i_end_pos);
        } else if ((i_start_pos != -1) && (i_end_pos == -1)) {
            s_str = s_str.substring(i_start_pos);
            i_end_pos = s_str.indexOf("/n");
            if (i_end_pos == -1)
                s_str = s_str.substring(0);
            else
                s_str = s_str.substring(0, i_end_pos);
        }
        return s_str;
    }
    
}
