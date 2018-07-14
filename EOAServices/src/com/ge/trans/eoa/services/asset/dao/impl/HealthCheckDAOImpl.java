/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckDAOImpl.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.asset.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.dao.impl;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.HealthCheckDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.HCRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HeaderVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.util.MessageSender;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.AssetHomeHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 9, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class HealthCheckDAOImpl extends RMDCommonDAO implements
        HealthCheckDAOIntf {

    private static final long serialVersionUID = 1L;
    @Autowired
    private PopupListAdminDAOIntf popupListAdminDAO;
    @Autowired
    @Qualifier("hcMsgSender")
    private MessageSender mqSender;
    public static final RMDLogger LOGGER = RMDLoggerHelper
    .getLogger(HealthCheckDAOImpl.class);
    /*
     * This method is used for fetching the asset number details from database
     */
    @Override
	public HealthCheckVO getRoadNumberDetails(final String strAssetNumber,
            final String strLanguage) throws RMDDAOException {
        Session hibernateSession = null;
        List lstRoadNumber;
        HealthCheckVO objHealthCheckVO = null;
        String strAssetHeader = null;
        AssetHomeHVO assetHomeHVO = null;
        int iAssetListSize = RMDCommonConstants.INT_CONST;
        try {
            hibernateSession = getHibernateSession();
            strAssetHeader = RMDCommonUtility.getMessage(
                    RMDServiceConstants.QTR_CUSTOMER, null, strLanguage);
            final Criteria criteria = hibernateSession
                    .createCriteria(AssetHomeHVO.class);
            criteria.setFetchMode(RMDCommonConstants.CUSTOMER_HOMEHVO, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.CUSTOMER_HOMEHVO, RMDCommonConstants.CUSTOMER);
            criteria.setFetchMode(RMDCommonConstants.ASSET_GROUP_HOMEHVO, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.ASSET_GROUP_HOMEHVO, RMDCommonConstants.GROUP);
            if (strAssetHeader != null
                    && !RMDCommonConstants.EMPTY_STRING.equals(strAssetHeader)) {
                criteria.add(Restrictions.eq(RMDCommonConstants.GROUP_NAME, strAssetHeader));
            }
            criteria.add(Restrictions.eq(RMDCommonConstants.STRLANGUAGE, strLanguage));
            if (strAssetNumber != null
                    && !RMDCommonConstants.EMPTY_STRING.equals(strAssetNumber)) {
                criteria.add(Restrictions.eq(RMDCommonConstants.ROAD_NUMBER, strAssetNumber));
            }
            criteria.addOrder(Order.asc(RMDCommonConstants.ROAD_NUMBER));
            lstRoadNumber = criteria.list();
            iAssetListSize = lstRoadNumber.size();
            if (iAssetListSize != 0) {
                objHealthCheckVO = new HealthCheckVO();
                assetHomeHVO = (AssetHomeHVO) lstRoadNumber.get(0);
                objHealthCheckVO.setAssetNumber(RMDCommonUtility
                        .getIntValue(assetHomeHVO.getRoadNumber()));

                objHealthCheckVO.setAssetID(assetHomeHVO.getAssetSeqId());
                objHealthCheckVO.setAssetGroupID(assetHomeHVO
                        .getAssetGroupHomeHVO().getAssetGroupSeqId());
                objHealthCheckVO.setAssetGroupNumber(assetHomeHVO
                        .getAssetGroupHomeHVO().getGroupNumber());
                objHealthCheckVO.setCustomerID(assetHomeHVO
                        .getCustomerHomeHVO().getCmCustomerSeqId());
            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_HEALTHASSETINFO);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                strLanguage), null,
                        RMDCommonConstants.MINOR_ERROR);
            }
        } catch (RMDDAOConnectionException ex) {
            final   String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
        	 LOG.error(e);
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_HEALTHASSETINFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), null, RMDCommonConstants.MINOR_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
            lstRoadNumber = null;
        }
        return objHealthCheckVO;
    }
    
    // Raj.S(212687474)
    @Override
    public List<RDRNotificationsDataVO> getRDRNotifications(String userId) throws RMDBOException{
    	LOGGER.debug("HealthCheckRequestDAOImpl: Entered into getRDRNotifications() method");
    	StringBuilder strQuery = null;
        Session session = null;
        Query query = null;
        List queryList = null;
        RDRNotificationsDataVO notificationData;
        List<RDRNotificationsDataVO> notifications = null;
        try {
        	session = getHibernateSession();
            strQuery = new StringBuilder();
            strQuery.append("SELECT ASSET_NUMBER,ASSET_GRP_NAME,CUSTOMER_ID, "
        			+ "extract(day from(FROM_DATE - to_timestamp('1970-01-01', 'yyyy-mm-dd hh24'))) * 86400000"
        			+"+to_number(to_char(FROM_DATE, 'SSSSSFF3')),"
        			+ "extract(day from(TO_DATE - to_timestamp('1970-01-01', 'yyyy-mm-dd hh24'))) * 86400000" 
        			+"+to_number(to_char(TO_DATE, 'SSSSSFF3')),"
        			+ "STATUS "
        			+ "FROM GETS_RMD.GETS_RMD_RDR_NOTIFICATIONS "
        			+ "WHERE USER_ID=:userId and IS_DELETED='N' ");
        	query = session.createSQLQuery(strQuery.toString());
            LOGGER.debug("Query submitted: " + query);
            query.setParameter("userId",userId);
            query.setFetchSize(500);
            queryList = query.list();

            if (queryList != null && queryList.size() > 0) {
                
                notifications = new ArrayList<RDRNotificationsDataVO>(queryList.size());
                
                for (final Iterator<Object> iter = queryList.iterator(); iter
                        .hasNext();) {
                    notificationData = new RDRNotificationsDataVO();
                    final Object[] objRec = (Object[]) iter.next();
                    notificationData.setAssetNumber(Integer.toString(RMDCommonUtility.convertObjectToInt(objRec[0])));
                    notificationData.setAssetGrpName(RMDCommonUtility.convertObjectToString(objRec[1]));
                    notificationData.setCustomerID(RMDCommonUtility.convertObjectToString(objRec[2]));
                    notificationData.setFromDate(RMDCommonUtility.convertObjectToString(objRec[3]));
                    notificationData.setToDate(RMDCommonUtility.convertObjectToString(objRec[4]));
                    notificationData.setStatus(RMDCommonUtility.convertObjectToString(objRec[5]));
                    notifications.add(notificationData);
                }

            }
        } catch (RMDDAOException e) {
            LOG.error("Exception occurred:", e);
            throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                    new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception insertOutBndMsgHist() ",
                    e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        } finally {
            releaseSession(session);
            queryList = null;
            notificationData = null;
            query = null;
            strQuery.setLength(0);
            strQuery = null;
        }

        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from getRDRNotifications() method");

    	return notifications;
    }
    
    @Override
    public boolean updateRDRNotification(RDRNotificationsSubmitRequestVO notifications) throws RMDBOException{
    	LOGGER.debug("HealthCheckRequestDAOImpl: Entered into updateRDRNotification() method");
    	boolean status = false;
    	StringBuilder strQuery = null;
        Session session = null;
        Query query = null;
        try {
        	session = getHibernateSession();
        	strQuery = new StringBuilder();
        	if(notifications.getAssetNumber().equals("0") && notifications.getAssetGrpName().equals("0") && notifications.getStatus().equals("-1")) {
        		strQuery.append("UPDATE GETS_RMD.GETS_RMD_RDR_NOTIFICATIONS "
            			+ "SET IS_DELETED='Y' where "
        				+ "USER_ID=:userId");
        		query = session.createSQLQuery(strQuery.toString());
        		LOGGER.debug("Query submitted: " + query);
        		query.setParameter("userId",notifications.getUserID());
        	}
        	else {
        		strQuery.append("UPDATE GETS_RMD.GETS_RMD_RDR_NOTIFICATIONS "
        			+ "SET IS_DELETED='Y' where "
        			+ "USER_ID=:userId and ASSET_NUMBER=:assetNumber and ASSET_GRP_NAME=:assetGrpName and "
        			+ "CUSTOMER_ID=:customerId and " 
        			+ "FROM_DATE=to_timestamp(to_char(to_date('1970-01-01 00','yyyy-mm-dd hh24')+" 
        			+ "(:fromDate)/1000/60/60/24 , 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS') and "
        			+ "TO_DATE=to_timestamp(to_char(to_date('1970-01-01 00','yyyy-mm-dd hh24')+" 
        			+ "(:toDate)/1000/60/60/24 , 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS')"
        			+ " and STATUS=:status"
        			);
        		query = session.createSQLQuery(strQuery.toString());
        		LOGGER.debug("Query submitted: " + query);
        		query.setParameter("userId",notifications.getUserID());
        		query.setParameter("assetNumber", notifications.getAssetNumber());
        		query.setParameter("assetGrpName",notifications.getAssetGrpName());
        		query.setParameter("customerId",notifications.getCustomerID());
        		query.setParameter("fromDate",notifications.getFromDate());
        		query.setParameter("status",notifications.getStatus());
        		query.setParameter("toDate",notifications.getToDate());
        	}
            int count = query.executeUpdate();
            if(count>0) {
            	status = true;
            }
        	
        } catch (Exception e) {
            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception insertOutBndMsgHist() ",
                    e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        } finally {
            releaseSession(session);
            query = null;
            strQuery.setLength(0);
            strQuery = null;
        }
        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from updateRDRNotification() method");
    	return status;
    }

    @Override
    public boolean insertRDRNotification(RDRNotificationsSubmitRequestVO notifications) throws RMDBOException{
    	LOGGER.debug("HealthCheckRequestDAOImpl: Entered into insertRDRNotification() method");
    	boolean status = false;
    	StringBuilder strQuery = null;
        Session session = null;
        Query query = null;
        try {
        	session = getHibernateSession();
        	strQuery = new StringBuilder();
        	strQuery.append("INSERT INTO GETS_RMD.GETS_RMD_RDR_NOTIFICATIONS "
        			+ "(OBJID,USER_ID,ASSET_NUMBER,ASSET_GRP_NAME,CUSTOMER_ID,FROM_DATE,TO_DATE,STATUS) "
        			+ "values "
        			+ "(GETS_RMD_RDR_NOTIFICATIONS_SEQ.nextval,:userId,:assetNumber,:assetGrpName,"
        			+ ":customerId,"
        			+ "to_timestamp(to_char(to_date('1970-01-01 00','yyyy-mm-dd hh24')+" 
        			+ "(:fromDate)/1000/60/60/24 , 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS'),"
        			+ "to_timestamp(to_char(to_date('1970-01-01 00','yyyy-mm-dd hh24')+" 
        			+ "(:toDate)/1000/60/60/24 , 'yyyy-mm-dd HH24:MI:SS'),'yyyy-mm-dd HH24:MI:SS'),"
        			+ ":status)");
        	query = session.createSQLQuery(strQuery.toString());
        	LOGGER.debug("Query submitted: " + query);
            query.setParameter("userId",notifications.getUserID());
            query.setParameter("assetNumber", notifications.getAssetNumber());
            query.setParameter("assetGrpName",notifications.getAssetGrpName());
            query.setParameter("customerId",notifications.getCustomerID());
            query.setParameter("fromDate",notifications.getFromDate());
            query.setParameter("toDate",notifications.getToDate());
            query.setParameter("status",notifications.getStatus());
            int count = query.executeUpdate();
            if(count>0) {
            	status = true;
            }
        	
        } catch (Exception e) {
            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception insertOutBndMsgHist() ",
                    e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        } finally {
            releaseSession(session);
            query = null;
            strQuery.setLength(0);
            strQuery = null;
        }
        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from insertRDRNotification() method");
    	return status;
    }
    
    /* (non-Javadoc)
     * @see com.ge.trans.rmd.services.asset.dao.intf.HealthCheckDAOIntf#getAssetHCMPGroups(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<ElementVO> getAssetHCMPGroups(String strCustomer,
            String strAssetNumber, String strAssetGroupName,
            String strLanguage, String requestType, String assetType,
            String deviceName) throws RMDBOException {
        LOG.debug("Start of getAssetHCMPGroups method in HealthCheckDAOImpl");
        Session objHibernateSession = null;
        final StringBuilder assetHCMGroupQry = new StringBuilder();
        List lstGocdResult = null;
        List<ElementVO> arlGocResult = new ArrayList<ElementVO>();
        ElementVO objElementVO = null;
        String listName = RMDCommonConstants.HC_CFG_DEF_DESC_KEY;
        String hcDefDescription = RMDCommonConstants.EMPTY_STRING;
        List<GetSysLookupVO> lookupList = null;
        try {
            objHibernateSession = getHibernateSession();
            if (assetType
                    .equalsIgnoreCase(RMDCommonConstants.HC_ASSET_TYPE_LEGACY)
                    && !deviceName
                            .equalsIgnoreCase(RMDCommonConstants.PLATFORM_EGA)) {
                List<String> vehicleObjId = getVehicleObjId(strAssetNumber,
                        strCustomer, strAssetGroupName);
                String vehicleObjid = null;
                if (null != vehicleObjId) {
                    vehicleObjid = RMDCommonUtility
                            .convertObjectToString(vehicleObjId.get(0));
                }
                assetHCMGroupQry
                        .append(" SELECT HDR.OBJID, B.CONTROLLER_CFG,  "
                                + " B.CUST_ID_MP_SOURCE,D.CONTROLLER_SOURCE_ID, "
                                + " D.CONTROLLER_NAME,HDR.DATATEMPLATE_NUMBER, "
                                + " D.MP_SOURCE_APPID,D.FAULT_CTL_SRC_ID, "
                                + " HDR.TEMPLATE_FAULT_SOURCE,DEVICE_NAME "
                                + " FROM GETS_RMD.GETS_RMD_VEHICLE A, "
                                + " GETS_RMD.GETS_RMD_CTL_CFG B, "
                                + " GETS_RMD.GETS_RMD_CTL_CFG_SRC C, "
                                + " GETS_RMD.GETS_RMD_CONTROLLER D, "
                                + " GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_HDR HDR "
                                + " WHERE  A.VEHICLE2CTL_CFG = B.OBJID "
                                + " AND B.OBJID = C.CTL_CFG_SRC2CTL_CFG "
                                + " AND C.CONTROLLER_SOURCE_ID = D.CONTROLLER_SOURCE_ID "
                                + " AND HDR.DATASYNC2VEHICLE = A.OBJID "
                                + " AND HDR.TEMPLATE_FAULT_SOURCE =  D.MP_SOURCE_APPID"
                                + " AND HDR.ACTIVE= 'Y' "
                                + " AND UPPER(DEVICE_NAME) = UPPER(:deviceName) "
                                + " AND HDR.DATASYNC2VEHICLE = :vehicleObjid ");
                Query queryHCPMGroups = objHibernateSession
                        .createSQLQuery(assetHCMGroupQry.toString());
                queryHCPMGroups.setParameter(RMDCommonConstants.DEVICE_NAME,
                        deviceName);
                queryHCPMGroups.setParameter(RMDCommonConstants.VEHICLE_OBJID,
                        vehicleObjid);
                queryHCPMGroups.setFetchSize(50);
                lstGocdResult = queryHCPMGroups.list();
                if (null !=lstGocdResult && lstGocdResult.size() > 0) {
                    for (final Iterator<Object> godcIter = lstGocdResult
                            .iterator(); godcIter.hasNext();) {
                        final Object[] godcRec = (Object[]) godcIter.next();
                        objElementVO = new ElementVO();
                        objElementVO.setId(RMDCommonUtility
                                .convertObjectToString(godcRec[0]));
                        objElementVO.setName(RMDCommonUtility
                                .convertObjectToString(godcRec[4])
                                + RMDCommonConstants.HYPHEN
                                + RMDCommonUtility
                                        .convertObjectToString(godcRec[5]));
                        arlGocResult.add(objElementVO);
                    }
                }
            } else {
                lookupList = popupListAdminDAO.getHCLookUpValues(listName);
                if (null != lookupList) {
                    hcDefDescription = lookupList.get(0).getLookValue();
                }
                if (null != requestType) {
                    assetHCMGroupQry
                            .append(" SELECT GOCD.OBJID, GOCD.CFG_DEF_DESC "
                                    + " FROM TABLE_SITE_PART TSP ,GETS_RMD_VEHICLE VEH ,"
                                    + "GETS_RMD_VEH_HDR VEHHDR ,"
                                    + "TABLE_BUS_ORG TBO,GETS_OMI.GETS_OMI_CFG_DEF GOCD,"
                                    + "GETS_OMI.GETS_OMI_CFG_DEF_MTM_VEH GOCDMV "
                                    + " WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART "
                                    + " AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID "
                                    + " AND TSP.SERIAL_NO NOT LIKE '%BAD%' "
                                    + " AND TBO.ORG_ID = :customerId "
                                    + " AND TSP.x_veh_hdr =  :assetGrpName "
                                    + " AND TSP.SERIAL_NO = :assetNumber "
                                    + " AND GOCD.OBJID = GOCDMV.MTM2CFG_DEF"
                                    + " AND UPPER (GOCD.STATUS) ='COMPLETE'"
                                    + " AND GOCD.CFG_TYPE IN ('SDP','EDP')"
                                    + " AND GOCDMV.MTM2VEH = VEH.OBJID"
                                    + " AND UPPER(GOCDMV.STATUS)='ACTIVE' ");
                    if (requestType.equals(RMDCommonConstants.HC__REQUEST_TYPE)) {
                        assetHCMGroupQry
                                .append(" AND GOCD.CFG_DEF_DESC LIKE :cfgDefDescription ");
                    }
                    Query queryHCPMGroups = objHibernateSession
                            .createSQLQuery(assetHCMGroupQry.toString());
                    queryHCPMGroups.setParameter(
                            RMDCommonConstants.CUSTOMER_ID, strCustomer);
                    queryHCPMGroups.setParameter(
                            RMDCommonConstants.ASSET_NUMBER, strAssetNumber);
                    queryHCPMGroups.setParameter(
                            RMDCommonConstants.ASSET_GRP_NAME,
                            strAssetGroupName);
                    if (requestType.equals(RMDCommonConstants.HC__REQUEST_TYPE)) {
                        queryHCPMGroups.setParameter(
                                RMDCommonConstants.HC_CFG_DEF_DESC,
                                hcDefDescription + "%");
                    }
                    queryHCPMGroups.setFetchSize(50);
                    lstGocdResult = queryHCPMGroups.list();
                    if (lstGocdResult != null && lstGocdResult.size() > 0) {
                        for (final Iterator<Object> godcIter = lstGocdResult
                                .iterator(); godcIter.hasNext();) {
                            final Object[] godcRec = (Object[]) godcIter.next();
                            objElementVO = new ElementVO();
                            objElementVO.setId(RMDCommonUtility
                                    .convertObjectToString(godcRec[0]));
                            objElementVO.setName(RMDCommonUtility
                                    .convertObjectToString(godcRec[1]));

                            arlGocResult.add(objElementVO);
                        }
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        }

        catch (Exception e) {
        	LOG.error(e);
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getAssetHCMPGroups method in HealthCheckDAOImpl");
        return arlGocResult;
    }
    /**
     * @param String strCustomer,
            String strAssetNumber, String strAssetGroupName, String strLanguage
     * @return HealthCheckAvailableVO
     * @throws RMDDAOException
     * @Description: returns if the healthcheck is available for the asset. 
     * 
     */
    @Override
    public HealthCheckAvailableVO IsHCAvailable(String strCustomer, String strAssetNumber,
            String strAssetGroupName,String strLanguage) throws RMDBOException {
        LOG.debug("Start of getIsHCAvailable method in HealthCheckDAOImpl");
        final StringBuilder hcAvailableQry = new StringBuilder();
        Session objHibernateSession = null;
        Query query = null;
        HealthCheckAvailableVO healthCheckAvailableVO = new HealthCheckAvailableVO();
        List<String> platform = new ArrayList<String>(2);
        List queryList = new ArrayList();
        long countEGA, countQNX;
        try {
            healthCheckAvailableVO.setStrHCMessage(RMDCommonConstants.LETTER_Y);
            objHibernateSession = getHibernateSession();
            hcAvailableQry.append(" (SELECT Count(1), "
										+ "       Cast('QNX' AS VARCHAR(3)) "
										+ "FROM   gets_rmd.gets_rmd_vehicle VEH, "
										+ "       table_site_part TSP, "
										+ "       gets_rmd_veh_hdr VEHHDR, "
										+ "       table_bus_org TBO, "
										+ "       gets_rmd.gets_rmd_ctl_cfg CC, "
										+ "       gets_rmd.gets_rmd_controller CON, "
										+ "       gets_rmd_vehcfg cfg, "
										+ "       gets_rmd_master_bom bom "
										+ "WHERE  TSP.objid = VEH.vehicle2site_part "
										+ "       AND VEH.vehicle2ctl_cfg = CC.objid "
										+ "       AND VEH.vehicle2veh_hdr = VEHHDR.objid "
										+ "       AND VEHHDR.veh_hdr2busorg = TBO.objid "
										+ "       AND CC.cust_id_mp_source = CON.controller_source_id "
										+ "       AND TSP.objid = VEH.vehicle2site_part "
										+ "       AND VEH.objid = cfg.veh_cfg2vehicle "
										+ "       AND cfg.vehcfg2master_bom = bom.objid "
										+ "       AND TSP.serial_no NOT LIKE '%BAD%' "
										+ "       AND ( ( bom.config_item = 'CMU' "
										+ "               AND cfg.current_version = '2' ) "
										+ "              OR ( bom.config_item = 'HPEAP' "
										+ "                   AND cfg.current_version = '1' ) "
										+ "              OR ( bom.config_item = 'LCV' "
										+ "                   AND cfg.current_version = '1' ) "
										+ "              OR ( bom.config_item = 'LIG' "
										+ "                   AND cfg.current_version = '1' ) ) "
										+ "       AND TBO.org_id = :customerId "
										+ "       AND TSP.x_veh_hdr = :assetGrpName "
										+ "       AND TSP.serial_no = :assetNumber )"
					                    + "UNION ALL (select count(1), cast('EGA' as varchar(3)) "
					                    + "FROM  GETS_RMD_CUST_RNH_RN_V custv, GETS_OMI.GETS_OMI_EGA_CFG egacfg "
					                    + "WHERE egacfg.EGA_CFG2VEHICLE = custv.VEHICLE_OBJID "
					                    + "AND custv.ORG_ID = :customerId "
					                    + "AND custv.VEHICLE_HDR = :assetGrpName "
					                    + "AND custv.VEHICLE_NO = :assetNumber)");
            query = objHibernateSession.createSQLQuery(hcAvailableQry
                    .toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, strCustomer);
            query.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetNumber);
            query.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    strAssetGroupName);
            queryList = (ArrayList) query.list();
            if (queryList != null && queryList.size() > 0) {
                countEGA = RMDCommonUtility
                        .convertObjectToLong(((Object[]) queryList.get(1))[0]);
                countQNX = RMDCommonUtility
                        .convertObjectToLong(((Object[]) queryList.get(0))[0]);

                if (1 == countQNX && 1 == countEGA) {
                    platform.add(RMDCommonConstants.PLATFORM_QNX);
                    platform.add(RMDCommonConstants.PLATFORM_EGA);
                    healthCheckAvailableVO
                            .setDefaultPlatform(RMDCommonConstants.PLATFORM_EGA);
                } else if (1 == countQNX) {
                    platform.add(RMDCommonConstants.PLATFORM_QNX);
                    healthCheckAvailableVO
                            .setDefaultPlatform(RMDCommonConstants.PLATFORM_QNX);
                } else if (1 == countEGA) {
                    platform.add(RMDCommonConstants.PLATFORM_EGA);
                    healthCheckAvailableVO
                    .setDefaultPlatform(RMDCommonConstants.PLATFORM_EGA);
                } else {
                    healthCheckAvailableVO
                            .setStrHCMessage(RMDCommonConstants.LETTER_N);
                }

                if (null != platform && !platform.isEmpty()) {
                    healthCheckAvailableVO.setPlatform(platform);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        }
        catch (Exception e) {
        	LOG.error(e);
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
            query=null;
            hcAvailableQry.setLength(0);
            platform = null;
        }
        LOG.debug("End of getIsHCAvailable method in HealthCheckDAOImpl");
        return healthCheckAvailableVO;
    }

    @Override
    public String getHCAssetType(String strCustomer, String strAssetNumber,
            String strAssetGroupName, String strLanguage) throws RMDBOException {
        LOG.debug("Start of getHCAssetType method in HealthCheckDAOImpl");
        Session objHibernateSession = null;
        final StringBuilder assetTypeQry = new StringBuilder();
        String strAssetType = RMDCommonConstants.HC_ASSET_TYPE_LEGACY;
        try {
            objHibernateSession = getHibernateSession();
            assetTypeQry.append(" SELECT COUNT(1)"
                    + " FROM GETS_RMD.GETS_RMD_VEHICLE VEH,TABLE_SITE_PART TSP,"
                    + " GETS_RMD_VEH_HDR VEHHDR,"
                    + " TABLE_BUS_ORG TBO,"
                    + " GETS_RMD.GETS_RMD_CTL_CFG CC,"
                    + " GETS_RMD.GETS_RMD_CONTROLLER CON"
                    + " WHERE VEH.VEHICLE2CTL_CFG = CC.OBJID"
                    + " AND TSP.OBJID = VEH.VEHICLE2SITE_PART "
                    + " AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID"
                    + " AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID" 
                    + " AND CC.CUST_ID_MP_SOURCE  = CON.CONTROLLER_SOURCE_ID" 
                    + " AND TSP.SERIAL_NO NOT LIKE '%BAD%'"
                    + " AND CON.MP_SOURCE_APPID   = 6 "
                    + " AND TBO.ORG_ID = :customerId "
                    + " AND TSP.X_VEH_HDR = :assetGrpName "
                    + " AND TSP.SERIAL_NO = :assetNumber ");
            Query queryAssetType = objHibernateSession
            .createSQLQuery(assetTypeQry.toString());
            
            queryAssetType.setParameter(RMDCommonConstants.CUSTOMER_ID,strCustomer);
            queryAssetType.setParameter(RMDCommonConstants.ASSET_NUMBER,strAssetNumber);
            queryAssetType.setParameter(RMDCommonConstants.ASSET_GRP_NAME,strAssetGroupName);
            BigDecimal count = (BigDecimal) queryAssetType.uniqueResult();
            int hcAssetTypeCount = count.intValue();
            if (!RMDCommonConstants.ZERO_STRING.equals(String.valueOf(hcAssetTypeCount))) {
                strAssetType = RMDCommonConstants.HC_ASSET_TYPE_EVO;
            } 
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        }
        catch (Exception e) {
        	LOG.error(e);
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getAssetType method in HealthCheckDAOImpl");
        return strAssetType;
    
    }
    /* (non-Javadoc)
     * @see com.ge.trans.eoa.services.asset.dao.intf.HealthCheckDAOIntf#getSendMessageAttributes(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public HealthCheckAttributeVO getSendMessageAttributes(String strInput,
            String strAssetType, String strLanguage, String typeOfUser,
            String vehicleObjid, String device) throws RMDBOException {
        LOG.debug("Start of getSendMessageAttributes method in HealthCheckDAOImpl");
        Session objHibernateSession = null;
        final StringBuilder query = new StringBuilder();
        StringBuilder sourceApp=new StringBuilder();
        StringBuilder noOfBytes=new StringBuilder();
        StringBuilder signed=new StringBuilder();
        StringBuilder destType=new StringBuilder();
        StringBuilder requestParamNum=new StringBuilder();
        StringBuilder mPRateNum=new StringBuilder();
        List lstHCAttriVO = null;
        HealthCheckAttributeVO healthCheckAttrVO = null;
        String mpGroupId = strInput;
        try {
            objHibernateSession = getHibernateSession();
            if (RMDCommonConstants.HC_ASSET_TYPE_EVO
                    .equalsIgnoreCase(strAssetType)) {
                query.append("SELECT DECODE(PARMDEF.CONTROLLER_SOURCE_ID, '20', '06','21','06',PARMDEF.CONTROLLER_SOURCE_ID) AS SOURCE_APP,"
                        + "PARMDEF.NUMBER_BYTES AS NO_OF_BYTES,"
                        + "PARMDEF.SIGNED AS SIGNED,"
                        + "PARMDEF.DEST_TYPE AS DEST_TYPE,"
                        + "PARMDEF.REQUEST_PARM_NUMBER AS REQUEST_PARM_NUMBER,"
                        + "PARMDEF.USAGE_RATE AS MP_RATE_ENUM "
                        + "FROM GETS_RMD_PARMDEF PARMDEF,"
                        + "GETS_OMI_CFG_DEF_MTM_PARM REL,"
                        + "GETS_OMI_CFG_DEF M "
                        + "WHERE REL.MTM2CFG_DEF = M.OBJID "
                        + "AND REL.MTM2PARM_DEF = PARMDEF.OBJID "
                        + "AND M.OBJID = :MPNUM ");
                Query queryEVO = objHibernateSession.createSQLQuery(query
                        .toString());
                queryEVO.setParameter("MPNUM", strInput);
                queryEVO.setFetchSize(50);
                lstHCAttriVO = queryEVO.list();
                healthCheckAttrVO = new HealthCheckAttributeVO();
                if (lstHCAttriVO != null && lstHCAttriVO.size() > 0) {
                    for (final Iterator<Object> iter = lstHCAttriVO.iterator(); iter
                            .hasNext();) {
                        final Object[] objRec = (Object[]) iter.next();
                        sourceApp.append(RMDCommonUtility
                                .convertObjectToString(objRec[0])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        noOfBytes.append(RMDCommonUtility
                                .convertObjectToString(objRec[1])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        signed.append(RMDCommonUtility
                                .convertObjectToString(objRec[2])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        destType.append(RMDCommonUtility
                                .convertObjectToString(objRec[3])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        requestParamNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[4])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        mPRateNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[5])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    sourceApp.deleteCharAt(sourceApp.length()-1);
                    noOfBytes.deleteCharAt(noOfBytes.length()-1);
                    signed.deleteCharAt(signed.length()-1);
                    destType.deleteCharAt(destType.length()-1);
                    requestParamNum.deleteCharAt(requestParamNum.length()-1);
                    mPRateNum.deleteCharAt(mPRateNum.length()-1);
                    healthCheckAttrVO.setSourceApp(sourceApp.toString());
                    healthCheckAttrVO.setNoOfBytes(noOfBytes.toString());
                    healthCheckAttrVO.setSigned(signed.toString());
                    healthCheckAttrVO.setDestType(destType.toString());
                    healthCheckAttrVO.setRequestParamNum(requestParamNum
                            .toString());
                    healthCheckAttrVO.setmPRateNum(mPRateNum.toString());
                }
            } else if (strAssetType
                    .equalsIgnoreCase(RMDCommonConstants.HC_ASSET_TYPE_LEGACY)
                    && !device
                            .equalsIgnoreCase(RMDCommonConstants.PLATFORM_EGA)) {
                Query queryLegacy;
                if (RMDCommonConstants.STR_TRUE.equalsIgnoreCase(typeOfUser)) {
                    query.append("SELECT DTL.MP_NUMBER  AS MP_NUM,"
                            + "DTL.MP_DATA_SIGNED_TYPE   AS SIGNED,"
                            + "DTL.MP_DATATYPE AS DEST_TYPE, "
                            + "DTL.MP_DATASIZE AS NUMBER_OF_BYTES, "
                            + "DTL.MP_SOURCE_APPID AS SOURCE_APP, "
                            + "DTL.MP_RATE_ENUM AS MP_RATE_ENUM "
                            + "FROM GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_HDR HDR,GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_DTL DTL, GETS_RMD.GETS_RMD_VEHICLE VEH, "
                            + "GETS_RMD.GETS_RMD_CTL_CFG GRCC, GETS_RMD.GETS_RMD_CONTROLLER GRC  "
                            + "WHERE HDR.OBJID = DTL.MP2CELL_DATA_TEMPLATE AND HDR.DATASYNC2VEHICLE = VEH.OBJID "
                            + "AND GRC.CONTROLLER_SOURCE_ID = GRCC.CUST_ID_MP_SOURCE "
                            + "AND GRCC.OBJID = VEH.VEHICLE2CTL_CFG "
                            + "AND HDR.ACTIVE = 'Y' AND DTL.ACTIVE = 'Y' "
                            + "AND HDR.OBJID = :OBJID "
                            + "AND HDR.DATASYNC2VEHICLE = :vehicleObjid");
                    queryLegacy = objHibernateSession.createSQLQuery(query
                            .toString());
                    queryLegacy.setParameter("OBJID", strInput);
                    queryLegacy.setParameter(RMDCommonConstants.VEHICLE_OBJID,
                            vehicleObjid);
                } else {
                    query.append("SELECT DTL.MP_NUMBER  AS MP_NUM, "
                            + "DTL.MP_DATA_SIGNED_TYPE   AS SIGNED, "
                            + "DTL.MP_DATATYPE AS DEST_TYPE, "
                            + "DTL.MP_DATASIZE AS NUMBER_OF_BYTES, "
                            + "DTL.MP_SOURCE_APPID AS SOURCE_APP, "
                            + "DTL.MP_RATE_ENUM AS MP_RATE_ENUM "
                            + "FROM GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_HDR HDR,GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_DTL DTL, GETS_RMD.GETS_RMD_VEHICLE VEH, "
                            + "GETS_RMD.GETS_RMD_CTL_CFG GRCC, GETS_RMD.GETS_RMD_CONTROLLER GRC  "
                            + "WHERE HDR.OBJID = DTL.MP2CELL_DATA_TEMPLATE AND HDR.DATASYNC2VEHICLE = VEH.OBJID "
                            + "AND GRC.CONTROLLER_SOURCE_ID = GRCC.CUST_ID_MP_SOURCE "
                            + "AND GRC.MP_SOURCE_APPID = HDR.TEMPLATE_FAULT_SOURCE "
                            + "AND GRCC.OBJID       = VEH.VEHICLE2CTL_CFG "
                            + "AND HDR.ACTIVE= 'Y' AND DTL.ACTIVE = 'Y' "
                            + "AND HDR.DATASYNC2VEHICLE = :vehicleObjid");
                    queryLegacy = objHibernateSession.createSQLQuery(query
                            .toString());
                    queryLegacy.setParameter(RMDCommonConstants.VEHICLE_OBJID,
                            vehicleObjid);
                }
                queryLegacy.setFetchSize(50);
                lstHCAttriVO = queryLegacy.list();
                healthCheckAttrVO = new HealthCheckAttributeVO();
                if (null != lstHCAttriVO && lstHCAttriVO.size() > 0) {
                    for (final Iterator<Object> iter = lstHCAttriVO.iterator(); iter
                            .hasNext();) {
                        final Object[] objRec = (Object[]) iter.next();
                        requestParamNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[0])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        signed.append(RMDCommonUtility
                                .convertObjectToString(objRec[1])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        destType.append(RMDCommonUtility
                                .convertObjectToString(objRec[2])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        noOfBytes.append(RMDCommonUtility
                                .convertObjectToString(objRec[3])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        sourceApp.append(Integer.toHexString(Integer
                                .parseInt(RMDCommonUtility
                                        .convertObjectToString(objRec[4])))
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        mPRateNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[5])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    sourceApp.deleteCharAt(sourceApp.length()-1);
                    noOfBytes.deleteCharAt(noOfBytes.length()-1);
                    signed.deleteCharAt(signed.length()-1);
                    destType.deleteCharAt(destType.length()-1);
                    requestParamNum.deleteCharAt(requestParamNum.length()-1);
                    mPRateNum.deleteCharAt(mPRateNum.length()-1);
                    healthCheckAttrVO.setSourceApp(sourceApp.toString());
                    healthCheckAttrVO.setNoOfBytes(noOfBytes.toString());
                    healthCheckAttrVO.setSigned(signed.toString());
                    healthCheckAttrVO.setDestType(destType.toString());
                    healthCheckAttrVO.setRequestParamNum(requestParamNum
                            .toString());
                    healthCheckAttrVO.setmPRateNum(mPRateNum.toString());
                }
            } else {
                query.append("SELECT DTL.MP_NUMBER  AS MP_NUM,"
                        + "DTL.MP_DATA_SIGNED_TYPE   AS SIGNED,"
                        + "DTL.MP_DATATYPE AS DEST_TYPE,"
                        + "DTL.MP_DATASIZE AS NUMBER_OF_BYTES,"
                        + "DTL.MP_SOURCE_APPID AS SOURCE_APP,"
                        + "DTL.MP_RATE_ENUM AS MP_RATE_ENUM "
                        + "FROM GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_HDR HDR,"
                        + "GETS_TOOLS.GETS_TOOL_CCA_DATASYNC_DTL DTL,"
                        + "GETS_RMD.GETS_RMD_VEHICLE VEH,"
                        + "GETS_RMD.GETS_RMD_CTL_CFG_SRC CCS,"
                        + "GETS_RMD.GETS_RMD_CONTROLLER CTL "
                        + "WHERE HDR.OBJID = DTL.MP2CELL_DATA_TEMPLATE AND "
                        + "HDR.DATASYNC2VEHICLE = VEH.OBJID "
                        + "AND VEH.VEHICLE2CTL_CFG = CCS.CTL_CFG_SRC2CTL_CFG "
                        + "AND CCS.CONTROLLER_SOURCE_ID = CTL.CONTROLLER_SOURCE_ID "
                        + "AND DTL.MP_SOURCE_APPID = CTL.MP_SOURCE_APPID "
                        + "AND DTL.MP_SOURCE_APPID <> 6 "
                        + "AND HDR.ACTIVE= 'Y' AND DTL.ACTIVE = 'Y' "
                        + "AND HDR.DATASYNC2VEHICLE =:OBJID ");
                Query queryLegacy = objHibernateSession.createSQLQuery(query
                        .toString());
                queryLegacy.setParameter("OBJID", strInput);
                queryLegacy.setFetchSize(50);
                lstHCAttriVO = queryLegacy.list();
                healthCheckAttrVO = new HealthCheckAttributeVO();

                if (lstHCAttriVO != null && lstHCAttriVO.size() > 0) {
                    for (final Iterator<Object> iter = lstHCAttriVO.iterator(); iter
                            .hasNext();) {
                        final Object[] objRec = (Object[]) iter.next();
                        requestParamNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[0])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        signed.append(RMDCommonUtility
                                .convertObjectToString(objRec[1])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        destType.append(RMDCommonUtility
                                .convertObjectToString(objRec[2])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        noOfBytes.append(RMDCommonUtility
                                .convertObjectToString(objRec[3])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        sourceApp.append(Integer.toHexString(Integer
                                .parseInt(RMDCommonUtility
                                        .convertObjectToString(objRec[4])))
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                        mPRateNum.append(RMDCommonUtility
                                .convertObjectToString(objRec[5])
                                + RMDCommonConstants.COMMMA_SEPARATOR);
                    }
                    sourceApp.deleteCharAt(sourceApp.length() - 1);
                    noOfBytes.deleteCharAt(noOfBytes.length() - 1);
                    signed.deleteCharAt(signed.length() - 1);
                    destType.deleteCharAt(destType.length() - 1);
                    requestParamNum.deleteCharAt(requestParamNum.length() - 1);
                    mPRateNum.deleteCharAt(mPRateNum.length() - 1);
                    healthCheckAttrVO.setSourceApp(sourceApp.toString());
                    healthCheckAttrVO.setNoOfBytes(noOfBytes.toString());
                    healthCheckAttrVO.setSigned(signed.toString());
                    healthCheckAttrVO.setDestType(destType.toString());
                    healthCheckAttrVO.setRequestParamNum(requestParamNum
                            .toString());
                    healthCheckAttrVO.setmPRateNum(mPRateNum.toString());
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e);
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("Start of getSendMessageAttributes method in HealthCheckDAOImpl");
        return healthCheckAttrVO;
    }
    

    /* (non-Javadoc)
     * @see com.ge.trans.eoa.services.asset.dao.intf.HealthCheckDAOIntf#getCustrnhDetails(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
	public Map<String, String>getCustrnhDetails(String strCustomer, String strAssetNumber,
            String strAssetGroupName,String strLanguage){
        Session objHibernateSession = null;
        List lstCustrnh = null;
        Map<String, String> mapCustrnh = new HashMap<String, String>();
        final StringBuilder custrnhQuery = new StringBuilder();
        try {
            objHibernateSession = getHibernateSession();
            custrnhQuery
                    .append("SELECT VEHICLE_HDR_NO,VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V WHERE VEHICLE_HDR=:assetGrpName and ORG_ID=:customerId and VEHICLE_NO=:assetNumber");
            Query queryCustRnh = objHibernateSession
                    .createSQLQuery(custrnhQuery.toString());
            queryCustRnh.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    strCustomer);
            queryCustRnh.setParameter(RMDCommonConstants.ASSET_NUMBER,
                    strAssetNumber);
            queryCustRnh.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    strAssetGroupName);
            lstCustrnh = queryCustRnh.list();
            if (lstCustrnh != null && lstCustrnh.size() > 0) {
                for (final Iterator<Object> iter = lstCustrnh.iterator(); iter
                        .hasNext();) {
                    final Object[] objRec = (Object[]) iter.next();
                    mapCustrnh.put(RMDCommonUtility
                            .convertObjectToString(objRec[1]), RMDCommonUtility
                            .convertObjectToString(objRec[0]));
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e);
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        return mapCustrnh;
    }
    

    /**
     * @param healthCheckSubmitEoaVO
     * @return int
     * @throws RMDDAOException
     * @Description:This method submits a healthCheck request
     */
    @Override
    public String submitHealthCheckRequest(
            HealthCheckSubmitEoaVO healthCheckSubmitEoaVO)
            throws RMDDAOException {
        int progressCount = 0, count = 0, updateCount = 0, insertCount = 0;
        Session hibernateSession = null;
        String assetIp = null;
        List<String> vehicleObjId = new ArrayList<String>();
        StringBuilder msgCodeQuery=null;
        String msgNumCode=null;
        try {
            hibernateSession = getHibernateSession();
            hibernateSession.beginTransaction();
            StringBuilder query = new StringBuilder();
            /* finds the Ip of the asset */
            query.append("SELECT egacfg.COMM_ID FROM GETS_RMD_CUST_RNH_RN_V custv,"
                    + "GETS_OMI.GETS_OMI_EGA_CFG egacfg "
                    + "WHERE egacfg.EGA_CFG2VEHICLE = custv.VEHICLE_OBJID "
                    + "AND custv.ORG_ID=:customerId "
                    + "AND custv.VEHICLE_HDR=:roadIntial "
                    + "AND custv.VEHICLE_NO=:roadNumber");
            Query queryAssetIp = hibernateSession.createSQLQuery(query
                    .toString());
            queryAssetIp.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    healthCheckSubmitEoaVO.getCustomerID());
            queryAssetIp.setParameter(RMDCommonConstants.ROAD_INTIAL,
                    healthCheckSubmitEoaVO.getRoadIntial());
            queryAssetIp.setParameter(RMDCommonConstants.ROAD_NUMBER,
                    RMDCommonUtility
                            .convertObjectToString(healthCheckSubmitEoaVO
                                    .getRoadNumber()));
            assetIp = (String) queryAssetIp.uniqueResult();
            healthCheckSubmitEoaVO.setAssetIp(assetIp);
            vehicleObjId = getVehicleObjId(healthCheckSubmitEoaVO
                    .getRoadNumber().toString(),
                    healthCheckSubmitEoaVO.getCustomerID(),
                    healthCheckSubmitEoaVO.getRoadIntial());
            query = new StringBuilder();
            if (null != vehicleObjId) {
                healthCheckSubmitEoaVO.setVehicleObjID(RMDCommonUtility
                        .convertObjectToString(vehicleObjId.get(0)));
                msgCodeQuery=new StringBuilder();
                msgCodeQuery.append("SELECT OBJID FROM GETS_OMI_MESSAGE_DEF WHERE MSG_NUM_CODE=:msgCode");
                Query msgQuery = hibernateSession.createSQLQuery(msgCodeQuery.toString());
                if (healthCheckSubmitEoaVO.getTypeOfUser().equalsIgnoreCase(
                        RMDCommonConstants.STR_FALSE)) {
                    msgQuery.setParameter(RMDCommonConstants.MSG_CODE,RMDCommonConstants.MSG_CODE_EGA_HC_CUSTOMER);
                } else {
                    msgQuery.setParameter(RMDCommonConstants.MSG_CODE,RMDCommonConstants.MSG_CODE_EGA_HC_INTERNAL);
                }               
                msgQuery.setFetchSize(1);
                List<Object> msgInfo = msgQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(msgInfo)) {
                    msgNumCode = RMDCommonUtility
                            .convertObjectToString(msgInfo.get(0));
                }
                if(null !=msgNumCode)
                {       
                /*
                 * checks if the health check request for the selected
                 * locomotive is already in progress
                 */
                query.append("SELECT count(1) "
                        + "FROM GETS_OMI_OUTBNDMSG_HIST outbnd "
                        + "WHERE outbnd.OB_MSG_HIST2RMD_VEH = :vehicleObjId "
                        + "AND outbnd.OB_MSG_HIST2MSG_DEF =:msgDef "
                        + "AND outbnd.REQUEST_PENDING ='Y' ");
                Query queryProgress = hibernateSession.createSQLQuery(query
                        .toString());
                queryProgress.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID,
                        healthCheckSubmitEoaVO.getVehicleObjID());
                queryProgress.setParameter(RMDCommonConstants.MSG_DEF,
                        msgNumCode);
                progressCount = RMDCommonUtility
                        .convertObjectToInt(queryProgress.uniqueResult());

                if (progressCount > 0) {
                    /*
                     * If a health check request is already pending, the system
                     * shall determine if the request was created more than 30
                     * minutes back
                     */
                    query = new StringBuilder();
                    query.append("SELECT count(1) FROM GETS_OMI.GETS_OMI_OUTBNDMSG_HIST OB_HIST "
                            + "WHERE NX_MACHINE_ID =:assetIp "
                            + "AND OB_MSG_HIST2RMD_VEH =:vehicleObjId "
                            + "AND REQUEST_PENDING = 'Y' "
                            + "AND OB_REQ_STATUS = 'OPEN' "
                            + "AND OB_MSG_HIST2MSG_DEF =:msgDef "
                            + "AND OB_HIST.CREATION_DATE < SYSDATE - 30/(24*60)");
                    Query hibernateQuery = hibernateSession
                            .createSQLQuery(query.toString());
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_IP,
                            healthCheckSubmitEoaVO.getAssetIp());
                    hibernateQuery.setParameter(
                            RMDCommonConstants.VEHICLE_OBJ_ID,
                            healthCheckSubmitEoaVO.getVehicleObjID());
                    hibernateQuery.setParameter(RMDCommonConstants.MSG_DEF,
                            msgNumCode);
                    count = RMDCommonUtility.convertObjectToInt(hibernateQuery
                            .uniqueResult());

                    if (count > 0) {
                        /*
                         * If the above query returns a record, the system shall
                         * update the outbound history table using the following
                         * query
                         */
                        query = new StringBuilder();
                        query.append("UPDATE GETS_OMI.GETS_OMI_OUTBNDMSG_HIST OB_HIST "
                                + "SET OB_REQ_STATUS='FAILED', "
                                + "REQUEST_PENDING = 'N' "
                                + "WHERE NX_MACHINE_ID =:assetIp "
                                + "AND OB_MSG_HIST2RMD_VEH =:vehicleObjId "
                                + "AND REQUEST_PENDING ='Y' "
                                + "AND OB_REQ_STATUS = 'OPEN' "
                                + "AND OB_MSG_HIST2MSG_DEF =:msgDef "
                                + "AND OB_HIST.CREATION_DATE < SYSDATE - 30/(24*60)");
                        hibernateQuery = hibernateSession.createSQLQuery(query
                                .toString());
                        hibernateQuery.setParameter(
                                RMDCommonConstants.ASSET_IP,
                                healthCheckSubmitEoaVO.getAssetIp());
                        hibernateQuery.setParameter(
                                RMDCommonConstants.VEHICLE_OBJ_ID,
                                healthCheckSubmitEoaVO.getVehicleObjID());
                        hibernateQuery.setParameter(
                                RMDCommonConstants.MSG_DEF,
                                msgNumCode);
                        updateCount = hibernateQuery.executeUpdate();
                        if (updateCount > 0) {
                            progressCount = 0;
                        }
                    } else {
                        /*
                         * request was created in the last 30 minutes “A health
                         * check request is already pending for the unit. Please
                         * try again later.”
                         */
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SUBMIT_HC);
                        throw new RMDDAOException(errorCode, new String[] {},
                                RMDCommonUtility.getMessage(errorCode,
                                        new String[] {},
                                        RMDCommonConstants.ENGLISH_LANGUAGE),
                                null, RMDCommonConstants.MINOR_ERROR);
                    }

                }
            }

        }
            // generates the xml to place in EGA Queue
            if (progressCount <= 0) {

                sendMessage(healthCheckSubmitEoaVO);

                if (null != RMDCommonUtility
                        .convertObjectToString(healthCheckSubmitEoaVO
                                .getRequestNum())) {

                    getConfigType(healthCheckSubmitEoaVO, hibernateSession);
                    if (null != healthCheckSubmitEoaVO.getTemplate()
                            && healthCheckSubmitEoaVO.getTemplate()
                                    .equalsIgnoreCase(
                                            RMDCommonConstants.TEMPLATE_EDP)) {
                        healthCheckSubmitEoaVO
                                .setEdpTemplate(healthCheckSubmitEoaVO
                                        .getObjId());
                        healthCheckSubmitEoaVO
                                .setSdpTemplate(RMDCommonUtility
                                        .convertObjectToLong(RMDCommonConstants.MINUS_ONE));

                    } else if (null != healthCheckSubmitEoaVO.getTemplate()
                            && healthCheckSubmitEoaVO.getTemplate()
                                    .equalsIgnoreCase(
                                            RMDCommonConstants.TEMPLATE_SDP)) {
                        healthCheckSubmitEoaVO
                                .setSdpTemplate(healthCheckSubmitEoaVO
                                        .getObjId());
                        healthCheckSubmitEoaVO
                                .setEdpTemplate(RMDCommonUtility
                                        .convertObjectToLong(RMDCommonConstants.MINUS_ONE));
                    }
                    /*
                     * insert a record into the OutBndMsgHist table if
                     * successfully placed in the queue
                     */
                    insertOutBndMsgHist(healthCheckSubmitEoaVO,
                            hibernateSession);

                }
            }
            hibernateSession.getTransaction().commit();
            return healthCheckSubmitEoaVO.getRequestNum();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            if (null != hibernateSession) {
                hibernateSession.getTransaction().rollback();
            }
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (RMDDAOException e) {
            LOG.error("Exception occurred:", e);
            if (null != hibernateSession) {
                hibernateSession.getTransaction().rollback();
            }
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }
        } catch (Exception e) {
        	LOG.error(e);
            if (null != hibernateSession) {
                hibernateSession.getTransaction().rollback();
            }
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(hibernateSession);
        }
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return boolean
     * @throws RMDDAOException
     * @Description:This method used to insert a record into
     *                   GETS_OMI_OUTBNDMSG_HIST table
     */
    @Override
	public boolean insertOutBndMsgHist(
            HealthCheckSubmitEoaVO healthCheckSubmitEoaVO, Session session)
            throws RMDDAOException {
        LOGGER.debug("HealthCheckRequestDAOImpl: Entered into insertOutBndMsgHist() method");
        boolean inserted = false;
        StringBuilder strQuery = null;
        StringBuilder msgCodeQuery=null;
        String msgNumCode=null;
        try {
            if (null != session) {
                msgCodeQuery=new StringBuilder();
                msgCodeQuery.append("SELECT OBJID FROM GETS_OMI_MESSAGE_DEF WHERE MSG_NUM_CODE=:msgCode");
                Query msgQuery = session.createSQLQuery(msgCodeQuery.toString());
                if (healthCheckSubmitEoaVO.getTypeOfUser().equalsIgnoreCase(
                        RMDCommonConstants.STR_FALSE)) {
                    msgQuery.setParameter(RMDCommonConstants.MSG_CODE,RMDCommonConstants.MSG_CODE_EGA_HC_CUSTOMER);
                } else {
                    msgQuery.setParameter(RMDCommonConstants.MSG_CODE,RMDCommonConstants.MSG_CODE_EGA_HC_INTERNAL);
                }               
                msgQuery.setFetchSize(1);
                List<Object> msgInfo = msgQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(msgInfo)) {
                    msgNumCode = RMDCommonUtility
                            .convertObjectToString(msgInfo.get(0));
                }
                if(null !=msgNumCode)
                {
                strQuery = new StringBuilder();
                strQuery.append("INSERT INTO GETS_OMI_OUTBNDMSG_HIST VALUES(GETS_OMI.GETS_OMI_OUTBNDMSG_HIST_SEQ.NEXTVAL,SYSTIMESTAMP,:userID,SYSTIMESTAMP,");
                strQuery.append(":userID,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'OPEN',:requestNum,'Y',NULL,:msgDef, ");
                strQuery.append(":vehicleObjId,NULL,-1,:SDP,:EDP, SYSTIMESTAMP,:assetIp,NULL,:samples,NULL,0,0,NULL,NULL,NULL,NULL)");
                Query query = session.createSQLQuery(strQuery.toString());
                LOGGER.debug("Query submitted: " + query);
                query.setParameter(RMDCommonConstants.USER_ID,
                        healthCheckSubmitEoaVO.getUserId());
                query.setParameter(RMDCommonConstants.REQUEST_NUMBER,
                        healthCheckSubmitEoaVO.getRequestNum());
                query.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID,
                        healthCheckSubmitEoaVO.getVehicleObjID());
                query.setParameter(RMDCommonConstants.TEMPLATE_SDP,
                        healthCheckSubmitEoaVO.getSdpTemplate());
                query.setParameter(RMDCommonConstants.TEMPLATE_EDP,
                        healthCheckSubmitEoaVO.getEdpTemplate());
                query.setParameter(RMDCommonConstants.ASSET_IP,
                        healthCheckSubmitEoaVO.getAssetIp());
                query.setParameter(RMDCommonConstants.POST_SAMPLES,
                        healthCheckSubmitEoaVO.getPostSamples());
                query.setParameter(RMDCommonConstants.MSG_DEF,
                        msgNumCode);
                int count = query.executeUpdate();
                if (count > 0) {
                    inserted = true;
                }
              }
            }
        } catch (Exception e) {
            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception insertOutBndMsgHist() ",
                    e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        }

        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from insertOutBndMsgHist() method");

        return inserted;
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return ConfigTypeVO
     * @throws RMDDAOException
     * @Description:This method used to get the configuration type for the given
     *                   mpGroupName
     */
    @Override
	public void getConfigType(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO,
            Session hibernateSession) throws RMDDAOException {
        LOGGER.debug("HealthCheckRequestDAOImpl: Entered into getConfigType() method");
        StringBuilder strQuery = null;

        List<Object> template = new ArrayList<Object>();
        
        try {
            
            if (null != hibernateSession) {
                if (null != healthCheckSubmitEoaVO.getMpGroupName()) {
                    strQuery = new StringBuilder();
                    strQuery.append("SELECT GOCD.CFG_TYPE,GOCD.OBJID ");
                    strQuery.append("FROM GETS_OMI_CFG_DEF GOCD ");
                    strQuery.append("WHERE GOCD.CFG_DEF_DESC =:mpGrpName ");
                    Query query = hibernateSession.createSQLQuery(strQuery
                            .toString());
                    query.setParameter(RMDCommonConstants.MP_GROUP_NAME,
                            healthCheckSubmitEoaVO.getMpGroupName());
                    template = query.list();
                    LOGGER.debug("Query for Search criteria submitted: "
                            + query);
                    if (null != template) {
                        for (Object currentRow : template) {
                            final Object[] value = (Object[]) currentRow;

                            healthCheckSubmitEoaVO.setTemplate(value[0]
                                    .toString());
                            healthCheckSubmitEoaVO.setObjId(RMDCommonUtility
                                    .convertObjectToLong(value[1]));
                        }
                    }

                }
            }
        } catch (Exception e) {

            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception getConfigType() ", e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        }

        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from getConfigType() method");

    }

    /**
     * @param viewReqHistoryEoaVo
     * @return List<ViewRespHistoryEoaVo>
     * @throws RMDDAOException
     * @Description:This method used to insert a record into
     *                   GETS_OMI_OUTBNDMSG_HIST table
     */
    @Override
	public List<ViewRespHistoryEoaVo> viewRequestHistory(
            ViewReqHistoryEoaVo viewReqHistoryEoaVo) throws RMDDAOException {
        LOGGER.debug("HealthCheckRequestDAOImpl: Entered into insertOutBndMsgHist() method");

        StringBuilder strQuery = null;
        Session session = null;
        Query query = null;
        List queryList = null;
        ViewRespHistoryEoaVo viewRespHistoryEoaVo;
        List<ViewRespHistoryEoaVo> viewHistoryList = null;
        String device=viewReqHistoryEoaVo.getDevice();
        int index=device.indexOf("-");
        String deviceMsgHist=device.substring(index+1,device.length());
        try {
            session = getHibernateSession();
            strQuery = new StringBuilder();
            List<String> vehicleObjId = getVehicleObjId(viewReqHistoryEoaVo
                    .getRoadNumber().toString(),
                    viewReqHistoryEoaVo.getCustomerID(),
                    viewReqHistoryEoaVo.getRoadIntial());
            strQuery.append("SELECT mh.OB_MSG_ID, md.MSG_NUM_TITLE, "
                    + "mh.CREATED_BY, TO_CHAR(mh.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'), mh.OB_REQ_STATUS, "
                    + "mh.RETRY_ATTEMPTS, TO_CHAR(mh.OB_REQ_STATUS_DATE,'MM/DD/YYYY HH24:MI:SS'), "
                    + "mh.LAST_UPDATED_DATE "
                    + "FROM GETS_OMI_OUTBNDMSG_HIST mh, GETS_OMI_MESSAGE_DEF md "
                    + "WHERE mh.OB_MSG_HIST2MSG_DEF = md.OBJID "
                    + "AND mh.OB_MSG_HIST2RMD_VEH =:vehicleObjId "
                    + "AND mh.OB_MSG_HIST2MSG_DEF   in (SELECT OBJID "
                    + "FROM GETS_OMI.GETS_OMI_MESSAGE_DEF ");
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("GE") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_TRUE))
            {
                strQuery.append( "WHERE MSG_NUM_CODE in(:messageId,:messageIdAuto))");
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) &&  deviceMsgHist.equalsIgnoreCase("GE") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
                strQuery.append( "WHERE MSG_NUM_CODE in(:messageId))");
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("Customer") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_TRUE))
            {
                strQuery.append( "WHERE MSG_NUM_CODE in(:messageIdCustomer,:messageIdAuto)) ");
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("Customer") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
                strQuery.append( "WHERE MSG_NUM_CODE in(:messageIdCustomer)) ");
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_N) && 
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
            strQuery.append( "WHERE MSG_NUM_CODE in(:messageIdCustomer))");
            }
            strQuery.append( "AND mh.CREATION_DATE BETWEEN to_date(:fromDate,'YYYY-MM-DD HH24:MI:SS') "
                    + "AND to_date(:toDate,'YYYY-MM-DD HH24:MI:SS') "
                    + "ORDER BY mh.CREATION_DATE DESC");

            query = session.createSQLQuery(strQuery.toString());
            LOGGER.debug("Query submitted: " + query);
            query.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID,
                    RMDCommonUtility.convertObjectToString(vehicleObjId.get(0)));
            query.setParameter(RMDCommonConstants.FROM_DATE,
                    viewReqHistoryEoaVo.getFromDate());
            query.setParameter(RMDCommonConstants.TO_DATE,
                    viewReqHistoryEoaVo.getToDate());
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("GE") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_TRUE))
            {
                query.setParameter(RMDCommonConstants.MESSAGE_ID,
                        viewReqHistoryEoaVo.getMessageID());
                query.setParameter(RMDCommonConstants.MESSAGE_ID_AUTO,
                        RMDCommonConstants.MSG_CODE_EGA_HC_AUTO);
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("GE") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
                query.setParameter(RMDCommonConstants.MESSAGE_ID,
                        viewReqHistoryEoaVo.getMessageID());
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("Customer") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_TRUE))
            {
                query.setParameter(RMDCommonConstants.MESSAGE_ID_CUSTOMER,
                        viewReqHistoryEoaVo.getMessageIdCustomer());
                query.setParameter(RMDCommonConstants.MESSAGE_ID_AUTO,
                        RMDCommonConstants.MSG_CODE_EGA_HC_AUTO);
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_Y) && deviceMsgHist.equalsIgnoreCase("Customer") &&
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
                query.setParameter(RMDCommonConstants.MESSAGE_ID_CUSTOMER,
                        viewReqHistoryEoaVo.getMessageIdCustomer());
            }
            if(viewReqHistoryEoaVo.getIsInternal().equalsIgnoreCase(RMDCommonConstants.LETTER_N) && 
                    viewReqHistoryEoaVo.getMessageIdAuto().equalsIgnoreCase(RMDCommonConstants.STR_FALSE))
            {
                query.setParameter(RMDCommonConstants.MESSAGE_ID_CUSTOMER,
                        viewReqHistoryEoaVo.getMessageIdCustomer());
            }
            query.setFetchSize(500);
            queryList = query.list();

            if (queryList != null && queryList.size() > 0) {
                
                viewHistoryList = new ArrayList<ViewRespHistoryEoaVo>(queryList.size());
                
                for (final Iterator<Object> iter = queryList.iterator(); iter
                        .hasNext();) {
                    viewRespHistoryEoaVo = new ViewRespHistoryEoaVo();
                    final Object[] objRec = (Object[]) iter.next();
                    viewRespHistoryEoaVo.setMessageId((RMDCommonUtility
                            .convertObjectToLong(objRec[0])));
                    viewRespHistoryEoaVo.setMessageType((RMDCommonUtility
                            .convertObjectToString(objRec[1])));
                    viewRespHistoryEoaVo.setRequestor((RMDCommonUtility
                            .convertObjectToString(objRec[2])));
                    viewRespHistoryEoaVo.setRequestDate(RMDCommonUtility
                            .convertObjectToString(objRec[3]));
                    viewRespHistoryEoaVo.setStatus((RMDCommonUtility
                            .convertObjectToString(objRec[4])));
                    viewRespHistoryEoaVo.setRetryCount((RMDCommonUtility
                            .convertObjectToLong(objRec[5])));
                    viewRespHistoryEoaVo.setStatusDate(RMDCommonUtility
                            .convertObjectToString(objRec[6]));
                    viewRespHistoryEoaVo.setTemplateVersion(null);
                    viewRespHistoryEoaVo
                            .setMessageDirection(RMDCommonConstants.OUT_VALUE);
                    viewRespHistoryEoaVo.setOffboardSequence(null);
                    viewHistoryList.add(viewRespHistoryEoaVo);
                }

            } 

        } catch (RMDDAOException e) {
            LOG.error("Exception occurred:", e);
            throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                    new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
                            .getErrorDetail().getErrorType());
        } catch (Exception e) {
            LOGGER.error(
                    "HealthCheckRequestDAOImpl : Exception insertOutBndMsgHist() ",
                    e);
            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
                    e.getMessage());
        } finally {
            releaseSession(session);
            queryList = null;
            viewRespHistoryEoaVo = null;
            query = null;
            strQuery.setLength(0);
            strQuery = null;
        }

        LOGGER.debug("HealthCheckRequestDAOImpl: Exiting from insertOutBndMsgHist() method");

        return viewHistoryList;
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return void
     * @throws RMDBOException
     * @Description:This method calls the generate Health Check XML message and
     *                   places into queue
     */
    private void sendMessage(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO)
            throws RMDBOException {
        String xmlMessage = RMDCommonConstants.EMPTY_STRING;
        String requestNum = RMDCommonUtility
                .convertObjectToString(RMDCommonUtility.generateRandomInteger(
                        1, 999999999, new Random()));
        String transcationIndicator = RMDCommonConstants.TRANCATION_INDICATOR;
        String source = RMDCommonConstants.SOURCE_INPUT;
        String actionType = RMDCommonConstants.HC_ACTION_TYPE;
        String headerDate = null;
        HeaderVO headerVO = new HeaderVO();
        HCRequestVO hcRequestVO = new HCRequestVO();
        HealthCheckDataVO healthCheckDataVO = new HealthCheckDataVO();
        try {
            String pattern = RMDCommonConstants.SDF_FORMAT_WITH_T;
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(pattern);
            headerDate = sdf.format(new Date());

            headerVO.setStrTransactionIndicator(transcationIndicator);

            headerVO.setRequestNo(requestNum);
            healthCheckSubmitEoaVO.setRequestNum(requestNum);
            headerVO.setHeaderDate(headerDate);
            headerVO.setActionType(actionType);
            headerVO.setPhysResId(healthCheckSubmitEoaVO.getAssetIp());
            headerVO.setCustId(healthCheckSubmitEoaVO.getCustomerID());
            headerVO.setSource(source);
            hcRequestVO.setObjHeaderVO(headerVO);
            headerVO.setVehicleInitial(healthCheckSubmitEoaVO.getRoadIntial());
            headerVO.setVehicleNo(RMDCommonUtility
                    .convertObjectToString(healthCheckSubmitEoaVO
                            .getRoadNumber()));

            healthCheckDataVO.setSampleRate(RMDCommonUtility
                    .convertObjectToString(healthCheckSubmitEoaVO
                            .getSampleRate()));
            // EGA expects no of samples which is post samples +1
            healthCheckDataVO.setNoOfSamples((RMDCommonUtility
                    .convertObjectToString(healthCheckSubmitEoaVO
                            .getPostSamples()+1)));
            healthCheckDataVO
                    .setMpNumbers(healthCheckSubmitEoaVO.getMpNumber());
            hcRequestVO.setObjHealthCheckDataVO(healthCheckDataVO);
            xmlMessage = generateHCXML(hcRequestVO);
            LOG.info("XML Message for Health Check : " + xmlMessage);

            mqSender.putQueueMessage(xmlMessage);
            LOG.info("Message placed in queue");

        } catch (Exception e) {
            LOG.error(
                    "Exception while passing the message to MQ "
                            + e.getMessage(), e);
            throw new RMDBOException(
                    RMDCommonConstants.PASS_TO_QUEUE_EXCEPTION, e.getMessage());
        }

    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return String
     * @throws RMDBOException
     * @Description:This method generate Health Check XML message
     */
    public String generateHCXML(HCRequestVO hcRequestVO) throws RMDBOException {
        try {

            Marshaller marshaller = JAXBContext.newInstance(HCRequestVO.class)
                    .createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter strWriter = new StringWriter();
            marshaller.marshal(hcRequestVO, strWriter);
            return strWriter.toString();

        } catch (JAXBException je) {
            LOG.error("CommonUtil : JAXBException in generateXML :", je);
            throw new RMDBOException(je.getMessage());
        } catch (Exception e) {
            LOG.error("CommonUtil : Exception in generateXML :", e);
            throw new RMDBOException(e.getMessage());
        }
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDDAOException
     * @Description:This method fetches the device information.
     */
    @Override
    public String getDeviceInfo(HealthCheckVO objHealthCheckVO) throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        List<String> vehObjId = null;
        String vehicleObjId = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            vehObjId = getVehicleObjId(objHealthCheckVO.getRoadNumber(), objHealthCheckVO.getCustomerId(), objHealthCheckVO.getAssetGrpName());
            if (null != vehObjId && !vehObjId.isEmpty()) {
                vehicleObjId = RMDCommonUtility.convertObjectToString(vehObjId.get(0));
                if(objHealthCheckVO.isFromMcs()){
                	caseQry.append(" SELECT MBOM.EXPECTED_VALUE FROM GETS_RMD_VEHCFG VCFG,GETS_RMD_MASTER_BOM MBOM ");
                }else{
                	caseQry.append(" SELECT VCFG.CURRENT_VERSION FROM GETS_RMD_VEHCFG VCFG,GETS_RMD_MASTER_BOM MBOM ");
                }
                caseQry.append("WHERE VCFG.VEHCFG2MASTER_BOM = MBOM.OBJID AND MBOM.CONFIG_ITEM ");
                if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(RMDCommonConstants.STR_NO)) {
                    caseQry.append(" IN (:externalRequestType) ");
                } else {
                    caseQry.append(" IN (:internalRequestType) ");
                }
                caseQry.append("AND MBOM.BOM_STATUS = 'Y' ");
                if (null != vehicleObjId && !vehicleObjId.equals(RMDCommonConstants.EMPTY_STRING)) {
                    caseQry.append("AND VCFG.VEH_CFG2VEHICLE IN (:vehObjId)");
                }
                Query caseHqry = session.createSQLQuery(caseQry.toString());
                if (null != vehicleObjId && !vehicleObjId.equals(RMDCommonConstants.EMPTY_STRING)) {
                    caseHqry.setParameter(RMDCommonConstants.VEH_OBJ_ID, vehicleObjId);
                }
                if(objHealthCheckVO.isFromMcs()){
                	 if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(RMDCommonConstants.STR_NO)) {
 	                    caseHqry.setParameter(RMDCommonConstants.EXTERNAL_HC_REQUEST_TYPE, "HEALTHCHECK_MCS");
 	                } else {
 	                    caseHqry.setParameter(RMDCommonConstants.INTERNAL_HC_REQUEST_TYPE, "HEALTHCHECK_MCS");
 	                }
                }else{
	                if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(RMDCommonConstants.STR_NO)) {
	                    caseHqry.setParameter(RMDCommonConstants.EXTERNAL_HC_REQUEST_TYPE, RMDCommonConstants.HEALTHCHECK_DEVICE_CUSTOMER);
	                } else {
	                    caseHqry.setParameter(RMDCommonConstants.INTERNAL_HC_REQUEST_TYPE, RMDCommonConstants.HEALTHCHECK_DEVICE_MDSC);
	                }
                }
                caseHqry.setFetchSize(1);
                List<Object> deviceInfo = caseHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(deviceInfo)) {
                    if (deviceInfo.size() == 1) {
                        result = RMDCommonUtility.convertObjectToString(deviceInfo.get(0));
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DEVICE_INFORMATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDDAOException
     * @Description:This method saves the health check details.
     */
    @Override
    public String saveHCDetails(HealthCheckVO objHealthCheckVO)
            throws RMDDAOException {
        String result = null;
        Session session = null;
        List<String> vehObjId = new ArrayList<String>();
        String vehicleObjId = null;
        String msgNumCode = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            StringBuilder Query = new StringBuilder();
            Query.append(" SELECT OBJID FROM GETS_OMI_MESSAGE_DEF WHERE MSG_NUM_CODE=:msgCode");
            Query msgCodeQuery = session.createSQLQuery(Query.toString());
            if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(
                    RMDCommonConstants.STR_FALSE)) {
                msgCodeQuery.setParameter(RMDCommonConstants.MSG_CODE,
                        RMDCommonConstants.HC_CUSTOMER_MSG_CODE);
            } else {
                msgCodeQuery.setParameter(RMDCommonConstants.MSG_CODE,
                        RMDCommonConstants.HC_INTERNAL_MSG_CODE);
            }
            msgCodeQuery.setFetchSize(1);
            List<Object> msgInfo = msgCodeQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(msgInfo)) {
                msgNumCode = RMDCommonUtility.convertObjectToString(msgInfo
                        .get(0));
            }
            if (null != msgNumCode) {
                vehObjId = getVehicleObjId(objHealthCheckVO.getRoadNumber(),
                        objHealthCheckVO.getCustomerId(),
                        objHealthCheckVO.getAssetGrpName());
                if (null != vehObjId) {
                    vehicleObjId = RMDCommonUtility
                            .convertObjectToString(vehObjId.get(0));

                    caseQry.append(" INSERT INTO GETS_OMI.GETS_OMI_OUTBNDMSG_HIST (OBJID, LAST_UPDATED_DATE,LAST_UPDATED_BY,");
                    caseQry.append("CREATION_DATE,CREATED_BY,OB_REQ_STATUS,OB_MSG_ID,REQUEST_PENDING,OB_MSG_HIST2MSG_DEF,OB_MSG_HIST2RMD_VEH,HC_SAMPLES) VALUES ");
                    caseQry.append("(GETS_OMI_OUTBNDMSG_HIST_SEQ.nextval,sysdate,:hcRequest,sysdate,:hcRequest,:reqStatus,:messageId,:pendingRequest,:msgDef,:vehObjId,:samples)");
                    Query caseHqry = session.createSQLQuery(caseQry.toString());

                    caseHqry.setParameter(RMDCommonConstants.HC_REQUEST,
                            RMDCommonConstants.OMD_HC_REQUEST);
                    caseHqry.setParameter(RMDCommonConstants.OB_REQ_STATUS,
                            RMDCommonConstants.OB_REQ_STATUS_OPEN);
                    caseHqry.setParameter(RMDCommonConstants.REQUEST_PENDING,
                            RMDCommonConstants.STR_YES);
                    caseHqry.setParameter(RMDCommonConstants.MSG_DEF,
                            msgNumCode);

                    if (null != objHealthCheckVO.getNoOfPostSamples()
                            && !objHealthCheckVO.getNoOfPostSamples()
                                    .equalsIgnoreCase(
                                            RMDCommonConstants.EMPTY_STRING)) {
                        caseHqry.setParameter(RMDCommonConstants.POST_SAMPLES,
                                objHealthCheckVO.getNoOfPostSamples());
                    }
                    if (null != objHealthCheckVO.getMessageId()
                            && !objHealthCheckVO.getMessageId()
                                    .equalsIgnoreCase(
                                            RMDCommonConstants.EMPTY_STRING)) {
                        caseHqry.setParameter(RMDCommonConstants.MESSAGE_ID,
                                objHealthCheckVO.getMessageId());
                    }

                    if (null != vehicleObjId
                            && !vehicleObjId
                                    .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                        caseHqry.setParameter(RMDCommonConstants.VEH_OBJ_ID,
                                vehicleObjId);
                    }
                    caseHqry.executeUpdate();
                    result = RMDCommonConstants.SUCCESS;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_HC_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {

            releaseSession(session);
        }
        return result;
    }
    
    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates EGA HC.
     */
    @Override
    public String validateEGAHC(HealthCheckVO objHealthCheckVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session hibernateSession = null;
        String assetIp = null;
        String healthCheckFlagStatus = null;
        try {
            hibernateSession = getHibernateSession();
            hibernateSession.beginTransaction();
            StringBuilder healthCheckFlag = new StringBuilder();
            /* To find the healthcheck flag status of the asset */
            healthCheckFlag
                    .append("SELECT EGACFG.HEALTHCHECK_FLAG,EGACFG.COMM_ID "
                            + "FROM GETS_RMD_CUST_RNH_RN_V CUSTV,"
                            + "GETS_OMI.GETS_OMI_EGA_CFG EGACFG "
                            + "WHERE EGACFG.EGA_CFG2VEHICLE = CUSTV.VEHICLE_OBJID "
                            + "AND CUSTV.ORG_ID=:customerId "
                            + "AND CUSTV.VEHICLE_HDR=:roadIntial "
                            + "AND CUSTV.VEHICLE_NO=:roadNumber");
            Query healthCheckQuery = hibernateSession
                    .createSQLQuery(healthCheckFlag.toString());
            healthCheckQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    objHealthCheckVO.getCustomerId());
            healthCheckQuery.setParameter(RMDCommonConstants.ROAD_INTIAL,
                    objHealthCheckVO.getAssetGrpName());
            healthCheckQuery.setParameter(RMDCommonConstants.ROAD_NUMBER,
                    RMDCommonUtility.convertObjectToString(objHealthCheckVO
                            .getRoadNumber()));
            List<Object[]> hcFlag = healthCheckQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(hcFlag)) {
                for (final Iterator<Object[]> obj = hcFlag.iterator(); obj
                        .hasNext();) {
                    final Object[] healthCheckDetails = (Object[]) obj.next();
                    healthCheckFlagStatus = RMDCommonUtility
                            .convertObjectToString(healthCheckDetails[0]);
                    assetIp = RMDCommonUtility
                            .convertObjectToString(healthCheckDetails[1]);
                }
            }
            if (null != assetIp
                    && !assetIp
                            .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)
                    && AppSecUtil.ipAddressValidation(assetIp)) {
                if (null != healthCheckFlagStatus
                        && healthCheckFlagStatus
                                .equalsIgnoreCase(RMDCommonConstants.LETTER_Y)) {
                    result = RMDCommonConstants.SUCCESS;
                } else {
                    if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(
                            RMDCommonConstants.STR_NO)) {
                        result = RMDCommonConstants.HEALTHCHECK_DISABLED;
                    } else {
                        result = RMDCommonConstants.EGA_HEALTHCHECK_DISABLED;
                    }
                }
            } else {
                if (objHealthCheckVO.getTypeOfUser().equalsIgnoreCase(
                        RMDCommonConstants.STR_NO)) {
                    result = RMDCommonConstants.HEALTHCHECK_CANNOT_PERFORMED;
                } else {
                    result = RMDCommonConstants.EGA_HEALTHCHECK_CANNOT_PERFORMED;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VALIDATE_EGA_HC);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return result;
    }
    
    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates NT HC.
     */
    @Override
    public String validateNTHC(HealthCheckVO objHealthCheckVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session hibernateSession = null;
        List<String> vehObjId = new ArrayList<String>();
        String vehicleObjId = null;
        try {
            hibernateSession = getHibernateSession();
            hibernateSession.beginTransaction();
            StringBuilder NTHCFlag = new StringBuilder();
            vehObjId = getVehicleObjId(objHealthCheckVO.getRoadNumber(),
                    objHealthCheckVO.getCustomerId(),
                    objHealthCheckVO.getAssetGrpName());
            if (null != vehObjId && !vehObjId.isEmpty()) {
                vehicleObjId = RMDCommonUtility.convertObjectToString(vehObjId
                        .get(0));
                NTHCFlag.append("SELECT VCFG.CURRENT_VERSION FROM GETS_RMD_VEHCFG VCFG,GETS_RMD_MASTER_BOM MBOM "
                        + " WHERE VCFG.VEHCFG2MASTER_BOM = MBOM.OBJID AND MBOM.CONFIG_ITEM ='CMU' "
                        + " AND MBOM.BOM_STATUS = 'Y' AND VCFG.VEH_CFG2VEHICLE IN (:vehObjId)");
                Query NTFlagQuery = hibernateSession.createSQLQuery(NTHCFlag
                        .toString());
                if (null != vehicleObjId
                        && !vehicleObjId
                                .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                    NTFlagQuery.setParameter(RMDCommonConstants.VEH_OBJ_ID,
                            vehicleObjId);
                }
                List<Object> ntFlag = NTFlagQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(ntFlag)) {
                        result = RMDCommonUtility
                                .convertObjectToString(ntFlag.get(0));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VALIDATE_NT_HC);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return result;
    }
    /**
     * @Author:
     * @param String,String
     * @return String
     * @throws RMDDAOException
     * @Description:This method filters records based on type of user logged in
     */
    @Override
    public List<String> filterRecords(String messageIdList,
            String filterCondition) throws RMDDAOException {
        Session hibernateSession = null;
        List<BigDecimal> queryList = new ArrayList<BigDecimal>();
        List<String> messagesList = Arrays.asList(messageIdList
                .split(RMDCommonConstants.COMMMA_SEPARATOR));
        List<String> messageIDList = null;
        try {
            hibernateSession = getHibernateSession();
            hibernateSession.beginTransaction();
            StringBuilder filterMessageIds = new StringBuilder();
            filterMessageIds
                    .append("SELECT OB_MSG_ID FROM GETS_OMI.GETS_OMI_OUTBNDMSG_HIST WHERE "
                            + "OB_MSG_ID IN(:messageIdList) AND OB_MSG_HIST2MSG_DEF IN(SELECT OBJID "
                            + "FROM GETS_OMI.GETS_OMI_MESSAGE_DEF WHERE MSG_NUM_CODE IN ");
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.INTERNAL_AUTO_REQUESTS)) {
                filterMessageIds.append("(:qnxInternal,:qnxAuto))");
            }
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.INTERNAL_REQUESTS)) {
                filterMessageIds.append("(:qnxInternal))");
            }
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.EXTERNAL_AUTO_REQUESTS)) {
                filterMessageIds.append("(:qnxExternal,:qnxAuto))");
            }
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.EXTERNAL_USERS)) {
                filterMessageIds.append("(:qnxExternal))");
            }
            Query filterMessageIdsQuery = hibernateSession
                    .createSQLQuery(filterMessageIds.toString());
            filterMessageIdsQuery.setParameterList(
                    RMDCommonConstants.MESSAGE_ID_LIST, messagesList);
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.INTERNAL_AUTO_REQUESTS)) {
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_INTERNAL,
                        RMDCommonConstants.HC_INTERNAL_MSG_CODE);
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_AUTO,
                        RMDCommonConstants.HC_CUSTOMER_AUTO_MSG_CODE);
            }
            if(null != filterCondition
                    && filterCondition
                    .equalsIgnoreCase(RMDCommonConstants.INTERNAL_REQUESTS))
            {
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_INTERNAL,
                        RMDCommonConstants.HC_INTERNAL_MSG_CODE);
            }
            if(null != filterCondition
                    && filterCondition
                    .equalsIgnoreCase(RMDCommonConstants.EXTERNAL_AUTO_REQUESTS))
            {
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_EXTERNAL,
                        RMDCommonConstants.EGA_CODE);
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_AUTO,
                        RMDCommonConstants.HC_CUSTOMER_AUTO_MSG_CODE);
            }
            if (null != filterCondition
                    && filterCondition
                            .equalsIgnoreCase(RMDCommonConstants.EXTERNAL_USERS)) {
                filterMessageIdsQuery.setParameter(
                        RMDCommonConstants.QNX_EXTERNAL,
                        RMDCommonConstants.EGA_CODE);
            }
            filterMessageIdsQuery.setFetchSize(50);
            queryList = filterMessageIdsQuery.list();
            if (queryList != null && queryList.size() > 0) {
                messageIDList = new ArrayList<String>();
                for (BigDecimal obj : queryList) {
                    messageIDList.add(obj.toString());
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FILTER_RECORDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return messageIDList;
    }
}
