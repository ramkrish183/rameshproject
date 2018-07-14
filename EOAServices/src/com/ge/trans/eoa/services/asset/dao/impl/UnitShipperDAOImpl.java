/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipperDAOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.dao.impl;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.UnitShipperDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.UnitShippersVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseConvertionVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ShipUnitVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class UnitShipperDAOImpl extends BaseDAO implements UnitShipperDAOIntf {

    private static final long serialVersionUID = 1L;

    public static final RMDLogger LOG = RMDLogger
            .getLogger(UnitShipperDAOImpl.class);

    @Autowired
    private PopupListAdminDAOIntf objpopupListAdminDAO;

    
    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDDAOException
     * @Description: This Method is used to Fetch the list of Units to be
     *               shipped.
     * 
     */
    @Override
    public List<UnitShippersVO> getUnitsToBeShipped(
            UnitShipDetailsVO objDetailsVO) throws RMDDAOException {
        Session session = null;
        StringBuilder unitShippedQuery = new StringBuilder();
        Query hibernateQuery = null;
        UnitShippersVO objUnitShippersVO = null;
        List<UnitShippersVO> arlUnitShipperDetails = new ArrayList<UnitShippersVO>();
        try {
            session = getHibernateSession();

            unitShippedQuery
                    .append("SELECT TSP.OBJID,TSP.X_VEH_HDR,TSP.S_SERIAL_NO,TO_CHAR(TSP.X_TEST_TRACK_DATE,'MM/DD/YYYY HH24:MI:SS') X_TEST_TRACK_DATE ");
            unitShippedQuery
                    .append("FROM TABLE_SITE_PART TSP, GETS_RMD_CUST_RNH_RN_V GRV WHERE TSP.SHIP_DATE IS NULL AND  ");
            unitShippedQuery
                    .append("TSP.SERIAL_NO NOT LIKE '%BAD%' AND TSP.X_FLEET != 'UPTEST'AND TSP.OBJID != -2  AND GRV.SITE_PART_OBJID = TSP.OBJID ");

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getCustomerList())) {
                unitShippedQuery.append("AND GRV.CUST_NAME IN (:customer) ");
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetGrpName())) {
                unitShippedQuery.append("AND TSP.X_VEH_HDR=:assetGrpName ");
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetNumber())) {
                unitShippedQuery.append("AND TSP.S_SERIAL_NO=:assetNumber ");
            }
            

            unitShippedQuery
                    .append("ORDER BY  TSP.X_VEH_HDR,LPAD(TSP.S_SERIAL_NO, 30,'0')");

            hibernateQuery = session
                    .createSQLQuery(unitShippedQuery.toString());

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getCustomerList())) {
                List<String> customerList = Arrays.asList(objDetailsVO
                        .getCustomerList().split(
                                RMDCommonConstants.COMMMA_SEPARATOR));
                hibernateQuery.setParameterList(RMDCommonConstants.CUSTOMER,
                        customerList);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetGrpName())) {
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                        objDetailsVO.getAssetGrpName());
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetNumber())) {
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,
                        objDetailsVO.getAssetNumber());
            }

            List<Object[]> arlUnitsTobeShipped = hibernateQuery.list();
            if (arlUnitsTobeShipped != null && arlUnitsTobeShipped.size() > 0) {
                arlUnitShipperDetails = new ArrayList<UnitShippersVO>(arlUnitsTobeShipped.size());
                for (Object[] currentUnit : arlUnitsTobeShipped) {
                     objUnitShippersVO = new UnitShippersVO();
                     objUnitShippersVO.setObjId(RMDCommonUtility
                            .convertObjectToString(currentUnit[0]));
                    objUnitShippersVO.setRnh(RMDCommonUtility
                            .convertObjectToString(currentUnit[1]));
                    objUnitShippersVO.setRoadNumber(RMDCommonUtility
                            .convertObjectToString(currentUnit[2]));
                    objUnitShippersVO.setTestTrackDate(RMDCommonUtility.convertObjectToString(currentUnit[3]));
                    arlUnitShipperDetails.add(objUnitShippersVO);
                }
                arlUnitsTobeShipped=null;   
            }

        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_UNIT_TO_BE_SHIPPED);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlUnitShipperDetails;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of Units which are already
     *               Shipped.
     * 
     */
    @Override
    public List<UnitShippersVO> getLastShippedUnits(
            UnitShipDetailsVO objDetailsVO) throws RMDDAOException {
        Session session = null;
        StringBuilder unitShippedQuery = new StringBuilder();
        Query hibernateQuery = null;
        List<UnitShippersVO> arlLastShippedUnits = new ArrayList<UnitShippersVO>();
        UnitShippersVO objUnitShippersVO = null;
        try {

            session = getHibernateSession();
            unitShippedQuery
                    .append("SELECT TSP.OBJID,TSP.X_VEH_HDR,TSP.S_SERIAL_NO,TO_CHAR(TSP.X_TEST_TRACK_DATE,'MM/DD/YYYY HH24:MI:SS') X_TEST_TRACK_DATE, ");
            unitShippedQuery
                    .append("TO_CHAR(TSP.SHIP_DATE,'MM/DD/YYYY HH24:MI:SS') SHIP_DATE FROM TABLE_SITE_PART TSP, GETS_RMD_CUST_RNH_RN_V GRV WHERE ");
            unitShippedQuery
                    .append("TSP.SERIAL_NO NOT LIKE '%BAD%' AND TSP.X_FLEET != 'UPTEST'AND TSP.OBJID != -2  AND GRV.SITE_PART_OBJID = TSP.OBJID ");

            if (!RMDCommonUtility
                    .isNullOrEmpty(objDetailsVO.getIsDefaultLoad())) {
                unitShippedQuery.append("AND TSP.SHIP_DATE >= SYSDATE-:noDays ");
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getCustomerList())) {
                unitShippedQuery.append("AND GRV.CUST_NAME IN (:customer) ");
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetGrpName())) {
                unitShippedQuery.append("AND TSP.X_VEH_HDR=:assetGrpName ");
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetNumber())) {
                unitShippedQuery.append("AND TSP.S_SERIAL_NO=:assetNumber ");
            }

            unitShippedQuery
                    .append("ORDER BY TSP.X_VEH_HDR,LPAD(TSP.S_SERIAL_NO, 30,'0')");

            hibernateQuery = session
                    .createSQLQuery(unitShippedQuery.toString());

            if (!RMDCommonUtility
                    .isNullOrEmpty(objDetailsVO.getIsDefaultLoad())) {
                hibernateQuery
                        .setParameter(
                                RMDCommonConstants.NO_OF_DAYS,
                                getLookUpValue(RMDServiceConstants.DEFAULT_NO_OF_DAYS_UNIT_SHIPPERS));
            }
            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getCustomerList())) {
                List<String> customerList = Arrays.asList(objDetailsVO
                        .getCustomerList().split(
                                RMDCommonConstants.COMMMA_SEPARATOR));
                hibernateQuery.setParameterList(RMDCommonConstants.CUSTOMER,
                        customerList);
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetGrpName())) {
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                        objDetailsVO.getAssetGrpName());
            }

            if (!RMDCommonUtility.isNullOrEmpty(objDetailsVO.getAssetNumber())) {
                hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,
                        objDetailsVO.getAssetNumber());
            }
            List<Object[]> arlUnitsTobeShipped = hibernateQuery.list();
            
            if(arlUnitsTobeShipped!=null&&arlUnitsTobeShipped.size()>0){
                arlLastShippedUnits = new ArrayList<UnitShippersVO>(arlUnitsTobeShipped.size());
                
                for (Object[] currentUnit : arlUnitsTobeShipped) {
                     objUnitShippersVO = new UnitShippersVO();

                    objUnitShippersVO.setObjId(RMDCommonUtility
                            .convertObjectToString(currentUnit[0]));
                    objUnitShippersVO.setRnh(RMDCommonUtility
                            .convertObjectToString(currentUnit[1]));
                    objUnitShippersVO.setRoadNumber(RMDCommonUtility
                            .convertObjectToString(currentUnit[2]));
                    
                    objUnitShippersVO.setTestTrackDate(RMDCommonUtility
                                .convertObjectToString(currentUnit[3]));
                    
                        objUnitShippersVO.setShipDate(RMDCommonUtility
                                .convertObjectToString(currentUnit[4]));
                    
                    arlLastShippedUnits.add(objUnitShippersVO);

                }
            }

            arlUnitsTobeShipped=null;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_SHIPPED_UNITS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlLastShippedUnits;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */
    @Override
    public String updateUnitsToBeShipped(List<UnitShippersVO> arlShipperUnits)
            throws RMDDAOException {
        Session session = null;
        StringBuilder shipperUpdateQry = new StringBuilder();
        Query hibernateQuery = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.FAILURE;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            shipperUpdateQry
                    .append("UPDATE TABLE_SITE_PART  SET SHIP_DATE = TO_DATE(:shipDate,'MM/DD/YY HH24:MI:SS')");
            shipperUpdateQry
                    .append(",X_TEST_TRACK_DATE = TO_DATE(:testTrackDate,'MM/DD/YY HH24:MI:SS') WHERE OBJID =:strObjId");
            hibernateQuery = session
                    .createSQLQuery(shipperUpdateQry.toString());
            for (UnitShippersVO objUnitShippersVO : arlShipperUnits) {
                hibernateQuery.setParameter(RMDServiceConstants.SHIP_DATE,
                        objUnitShippersVO.getShipDate());
                hibernateQuery.setParameter(
                        RMDServiceConstants.TEST_TRACK_DATE,
                        objUnitShippersVO.getTestTrackDate());
                hibernateQuery.setParameter(RMDServiceConstants.OBJID,
                        objUnitShippersVO.getObjId());
                hibernateQuery.executeUpdate();
            }
            transaction.commit();
            status = RMDCommonConstants.SUCCESS;
            
        } catch (Exception e) {
            transaction.rollback();

            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_UNITS_TO_BE_SHIPPED);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        }finally {
            releaseSession(session);
        }
        return status;
    }

    /**
     * @Author :
     * @return :String
     * @param :String listName
     * @throws :RMDDAOException
     * @Description: This Method is Used to get Lookup values.
     * 
     */

    public String getLookUpValue(String listName) throws RMDDAOException {

        List<GetSysLookupVO> arlSysLookUp = new ArrayList<GetSysLookupVO>();
        String lookupValue = RMDCommonConstants.EMPTY_STRING;
        try {

            arlSysLookUp = objpopupListAdminDAO.getPopupListValues(listName);
            if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {

                for (Iterator iterator = arlSysLookUp.iterator(); iterator
                        .hasNext();) {
                    GetSysLookupVO objSysLookupVO = (GetSysLookupVO) iterator
                            .next();
                    lookupValue = objSysLookupVO.getLookValue();
                }
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return lookupValue;
    }

    /**
         * @Author :Mohameds
         * @return :String
         * @param :List<UnitShippersVO> arlShipperUnits
         * @throws :RMDWebException
         * @Description: This Method Fetches the list of ASSET NUMBER which yrt to be shipped.
         * 
         */
    @Override
	public List<AssetNumberVO> getAssetNumbersForShipUnits(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException {
        LOG.debug("Begin getAssetNumbersForShipUnits method of UnitShiiperDAOImpl");
        //Map<Long, AssetServiceVO> arlAssetDetails = new HashMap<Long, AssetServiceVO>();
        List<AssetNumberVO> arlAssetDetails = new ArrayList<AssetNumberVO>();
        Session objSession = null;
        List<Object[]> arlAssetNo = null;
        AssetServiceVO localAssetServicevo = objAssetServiceVO;
        AssetNumberVO assetNumberVO=null;

        try {
            objSession = getHibernateSession();

            StringBuffer assetQry = new StringBuffer();
            assetQry.append("SELECT TBO.ORG_ID,TSP.SERIAL_NO ");
            assetQry.append("FROM GETS_RMD_VEH_HDR GRVH,TABLE_BUS_ORG TBO,GETS_RMD_VEHICLE GRV,TABLE_SITE_PART TSP,GETS_RMD_CTL_CFG GRCC,GETS_RMD_MODEL GRM,GETS_RMD_FLEET GRF,GETS_OMI.GETS_OMI_EGA_CFG EGA_CFG ");
            assetQry.append("WHERE GRVH.VEH_HDR2BUSORG = TBO.OBJID AND GRVH.OBJID= GRV.VEHICLE2VEH_HDR AND GRV.VEHICLE2SITE_PART = TSP.OBJID AND GRCC.OBJID= GRV.VEHICLE2CTL_CFG ");
            assetQry.append("AND GRF.OBJID = GRV.VEHICLE2FLEET AND GRM.OBJID = GRV.VEHICLE2MODEL AND GRV.OBJID = EGA_CFG2VEHICLE(+)  AND TSP.S_SERIAL_NO NOT LIKE '%BAD%' ");

            if (null != localAssetServicevo.getAssetGroupName()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equals(localAssetServicevo.getAssetGroupName())) {
                assetQry.append(" AND GRVH.VEH_HDR =:groupName");
            }
            if (null != localAssetServicevo.getCustomerID()
                    && !RMDCommonConstants.EMPTY_STRING
                            .equals(localAssetServicevo.getCustomerID())) {
                    assetQry.append(" AND TBO.ORG_ID IN ( :customerId )  AND TSP.SHIP_DATE is null");
            }
            
            if (null != localAssetServicevo.getAssetNumberLike()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getAssetNumberLike())) {
                
                assetQry.append(" AND TSP.S_SERIAL_NO LIKE :assetNumberLike");
            }
            if (null != localAssetServicevo.getStrAssetNumber()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrAssetNumber())) {
                
                assetQry.append(" AND TSP.S_SERIAL_NO = :assetNumber");
            }
            if (null != localAssetServicevo.getAssetFrom()
                    && !RMDCommonUtility.checkNull(localAssetServicevo
                            .getAssetFrom()) && null != localAssetServicevo.getAssetTo()
                                    && !RMDCommonUtility.checkNull(localAssetServicevo
                                            .getAssetTo())) {
                assetQry.append(" AND LPAD(TSP.S_SERIAL_NO, 9,'0') BETWEEN LPAD(:assetFrom, 9,'0') AND LPAD(:assetTo, 9,'0')  ");
            }
            if (null != localAssetServicevo.getProducts()
                    && !RMDCommonUtility.checkNull(localAssetServicevo
                            .getProducts())) {
                if (!localAssetServicevo.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)){
                    assetQry.append(" AND TSP.OBJID in ("+getProductQuery(localAssetServicevo.getCustomerID())+" ) ");                  
                }
            }
            
            if (null != localAssetServicevo.getStrModelId()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrModelId())) {
                
                assetQry.append(" AND GRM.OBJID in (:model)");
            }
            
            if (null != localAssetServicevo.getStrFleetId()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrFleetId())) {
                
                assetQry.append(" AND GRF.OBJID in (:fleet)");
            }
            if (null != objSession) {
                Query assetHqry = objSession
                        .createSQLQuery(assetQry.toString());

                if (null != localAssetServicevo.getAssetGroupName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(localAssetServicevo.getAssetGroupName())) {
                    assetHqry.setParameter(RMDCommonConstants.GROUP_NAME,
                            localAssetServicevo.getAssetGroupName());
                }
                
                if (null != localAssetServicevo.getAssetNumberLike()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getAssetNumberLike())) {
                    
                    assetHqry.setParameter(
                            RMDCommonConstants.ASSET_NUMBER_LIKE,
                            RMDServiceConstants.PERCENTAGE
                                    + localAssetServicevo.getAssetNumberLike()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (null != localAssetServicevo.getStrAssetNumber()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrAssetNumber())) {
                    
                    assetHqry.setParameter(RMDCommonConstants.ASSET_NUMBER,
                            localAssetServicevo.getStrAssetNumber());
                }
                if (null != localAssetServicevo.getCustomerID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(localAssetServicevo.getCustomerID())) {
                    List<String> customerList = Arrays
                            .asList(localAssetServicevo.getCustomerID().split(
                                    RMDCommonConstants.COMMMA_SEPARATOR));
                    assetHqry.setParameterList(RMDServiceConstants.CUSTOMER_ID,
                            customerList);
                }

                if (null != localAssetServicevo.getAssetFrom()
                        && !RMDCommonUtility.checkNull(localAssetServicevo
                                .getAssetFrom())) {
                    assetHqry.setParameter(RMDCommonConstants.ASSET_FROM,
                            localAssetServicevo.getAssetFrom());
                }

                if (null != localAssetServicevo.getAssetFrom()
                        && !RMDCommonUtility.checkNull(localAssetServicevo
                                .getAssetTo())) {
                    assetHqry.setParameter(RMDCommonConstants.ASSET_TO,
                            localAssetServicevo.getAssetTo());
                }
                if (null != localAssetServicevo.getProducts()
                        && !RMDCommonUtility.checkNull(localAssetServicevo
                                .getProducts())) {
                    if (!localAssetServicevo.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)){
                        if (null != localAssetServicevo.getProducts()
                                && !RMDCommonUtility.checkNull(localAssetServicevo.getProducts())) {
                            assetHqry.setParameterList(
                                    RMDCommonConstants.PRODUCT_CONF_NAME_LST, localAssetServicevo.getProducts());
                            if (null != localAssetServicevo.getCustomerID()
                                    && !RMDCommonConstants.EMPTY_STRING
                                            .equals(localAssetServicevo
                                                    .getCustomerID())) {
                                List<String> customerList = Arrays
                                        .asList(localAssetServicevo.getCustomerID().split(
                                                RMDCommonConstants.COMMMA_SEPARATOR));
                                assetHqry.setParameterList(RMDServiceConstants.CUSTOMER_ID,
                                        customerList);
                            }
                        }
                    }
                }
                
                if (null != localAssetServicevo.getStrModelId()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrModelId())) {
                    List<String> modelIds = Arrays.asList(localAssetServicevo.getStrModelId().split(","));
                    assetHqry.setParameterList(RMDCommonConstants.MODEL,
                            modelIds);
                    
                }
                
                if (null != localAssetServicevo.getStrFleetId()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getStrFleetId())) {
                    List<String> fleetIds = Arrays.asList(localAssetServicevo.getStrFleetId().split(","));
                    assetHqry.setParameterList(RMDCommonConstants.FLEET,
                            fleetIds);
                }
                assetHqry.setFetchSize(1000); 
                arlAssetNo = (ArrayList) assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
                    arlAssetDetails = new ArrayList<AssetNumberVO>(arlAssetNo.size());
                    DateFormat formatter = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.yyyyMMdd);
                    Collections.sort(arlAssetNo, new Comparator<Object[]>() {
                        @Override
						public int compare(Object[] obj1, Object[] obj2) {
                            int result = 0;
                            try {
                                int assetNumber1 = RMDCommonUtility
                                        .convertObjectToInt(obj1[1]);
                                int assetNumber2 = RMDCommonUtility
                                        .convertObjectToInt(obj2[1]);
                                if (assetNumber1 == assetNumber2) {
                                    result = 0;
                                }
                                result = (assetNumber1 < assetNumber2) ? -1 : 1;
                            } catch (Exception e) {
                            	 LOG.error(e);
                                String assetNumber1 = RMDCommonUtility
                                        .convertObjectToString(obj1[1]);
                                String assetNumber2 = RMDCommonUtility
                                        .convertObjectToString(obj2[1]);

                                result = assetNumber1
                                        .compareToIgnoreCase(assetNumber2);
                            }
                            return result;
                        }
                    });

                    for (Object[] obj : arlAssetNo) {
                        assetNumberVO = new AssetNumberVO();
                        assetNumberVO.setCustomerID(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        assetNumberVO.setStrAssetNumber(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        
                        arlAssetDetails.add(assetNumberVO);
                    }
                }
            }
            arlAssetNo=null;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getAssetNumbersForShipUnits method of UniShipperDAOImpl");
        return arlAssetDetails;

    }

	@Override
	public List<ShipUnitVO> getShipUnitDetails() throws RMDDAOException {
		List<Object[]> resultListFrom = null;
		Session session = null;
		ShipUnitVO objShipUnitVO = null;
		List<ShipUnitVO> shipUnitVOList = new ArrayList<ShipUnitVO>();
		Object[] configDetails = null;
		int defaultDays = 0;
		String defaultDaysQry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFrom = new StringBuilder();
			caseQryFrom.append("SELECT X_VEH_HDR,SERIAL_NO,SHIP_DATE FROM TABLE_SITE_PART TSP, GETS_RMD_CUST_RNH_RN_V GRV WHERE ");
			caseQryFrom.append("SERIAL_NO NOT LIKE '%BAD%' AND X_FLEET != 'UPTEST' AND OBJID != -2  AND GRV.SITE_PART_OBJID = TSP.OBJID ");
			caseQryFrom.append("AND SHIP_DATE >= TO_DATE(sysdate) - :days AND SHIP_DATE < TO_DATE(sysdate) ");
			defaultDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'SHIP_UNIT_REPORT_DEFAULT_DAYS'";
			Query daysHqry = session.createSQLQuery(defaultDaysQry);
			defaultDays =RMDCommonUtility.convertObjectToInt(daysHqry.uniqueResult());
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
			caseHqryFrom.setParameter(RMDServiceConstants.NO_OF_DAYS,
					defaultDays);
			caseHqryFrom.setFetchSize(1000);
			resultListFrom = caseHqryFrom.list();
			if (resultListFrom != null && !resultListFrom.isEmpty()) {
				shipUnitVOList = new ArrayList<ShipUnitVO>(resultListFrom.size());
			}
			if (resultListFrom != null
					&& RMDCommonUtility.isCollectionNotEmpty(resultListFrom)) {
				for (final Iterator<Object[]> obj = resultListFrom.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objShipUnitVO = new ShipUnitVO();
					objShipUnitVO.setRoadNumberHeader(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objShipUnitVO.setRoadNumber(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objShipUnitVO.setShipDate(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					shipUnitVOList.add(objShipUnitVO);
				}
			}
			resultListFrom = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SHIP_UNIT_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SHIP_UNIT_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return shipUnitVOList;
	}

	@Override
	public List<ShipUnitVO> getInfancyFailureDetails() throws RMDDAOException {
		List<Object[]> resultListFrom = null;
		Session session = null;
		ShipUnitVO objShipUnitVO = null;
		List<ShipUnitVO> shipUnitVOList = new ArrayList<ShipUnitVO>();
		Object[] configDetails = null;
		int defaultDays = 0;
		String defaultDaysQry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFrom = new StringBuilder();
			caseQryFrom.append("SELECT CUSTV.VEHICLE_HDR,CUSTV.VEHICLE_NO,INSHOP_REASON_CODE,DECODE(WORKORDER_STATUS_CODE,100, 'Incoming',300,'In Shop') as Status,WORKORDER_COMMENTS,SHIP_DATE ");
			caseQryFrom.append("FROM TABLE_SITE_PART TSP,gets_rmd_cust_rnh_rn_v custv,gets_lms.gets_lms_service_workorder@eservices service WHERE service.LOCOMOTIVE_ID = CUSTV.LMS_LOCO_ID ");
			caseQryFrom.append("AND TSP.objid = CUSTV.SITE_PART_OBJID AND SHIP_DATE>= SYSDATE- :days AND WORKORDER_STATUS_CODE  IN ('100','300') AND INSHOP_REASON_CODE IN ('FL','GR')");
			defaultDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'INFANCY_FAILURE_UNIT_DEFAULT_DAYS'";
			Query daysHqry = session.createSQLQuery(defaultDaysQry);
			defaultDays = RMDCommonUtility.convertObjectToInt(daysHqry.uniqueResult());
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
			caseHqryFrom.setParameter(RMDServiceConstants.NO_OF_DAYS,
					defaultDays);
			caseHqryFrom.setFetchSize(1000);
			resultListFrom = caseHqryFrom.list();
			if (resultListFrom != null && !resultListFrom.isEmpty()) {
				shipUnitVOList = new ArrayList<ShipUnitVO>(resultListFrom.size());
			}
			if (resultListFrom != null
					&& RMDCommonUtility.isCollectionNotEmpty(resultListFrom)) {
				for (final Iterator<Object[]> obj = resultListFrom.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objShipUnitVO = new ShipUnitVO();
					objShipUnitVO.setRoadNumberHeader(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objShipUnitVO.setRoadNumber(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objShipUnitVO.setCode(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					objShipUnitVO.setComment(RMDCommonUtility
							.convertObjectToString(configDetails[3]));
					objShipUnitVO.setShipDate(RMDCommonUtility
							.convertObjectToString(configDetails[4]));
					shipUnitVOList.add(objShipUnitVO);
				}
			}
			resultListFrom = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INFANCY_FAILURE_UNIT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INFANCY_FAILURE_UNIT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return shipUnitVOList;
	}
}

