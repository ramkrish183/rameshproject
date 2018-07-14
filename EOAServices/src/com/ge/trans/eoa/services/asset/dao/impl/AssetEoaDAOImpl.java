package com.ge.trans.eoa.services.asset.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.scheduling.annotation.AsyncResult;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.asset.dao.intf.AssetEoaDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetHeaderServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetLocationDetailVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetsLocationFromShopVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultCodeDetailsServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindLocationEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindLocationResultEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FleetVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastDownloadStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LastFaultStatusResponseEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.LifeStatisticsEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RegionVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RoleAssetsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.rmd.asset.valueobjects.AssetInstalledProductVO;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorBean;
import com.ge.trans.rmd.asset.valueobjects.AssetLocatorVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserProductRoleHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;





public class AssetEoaDAOImpl  extends RMDCommonDAO implements AssetEoaDAOIntf {
    private static final long serialVersionUID = -3629386552882957794L;
    private static final String SEPARATOR = ",";

    @Override
	public List<AssetServiceVO> getAssets(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException {
        LOG.debug("Begin getAssets method of AssetDAOImpl");
        List<AssetServiceVO> arlAssetDetails = new ArrayList<AssetServiceVO>();
        Session objSession = null;
        List<Object[]> arlAssetNo = null;
        AssetServiceVO localAssetServicevo = objAssetServiceVO;

        try {
            objSession = getHibernateSession();

            StringBuilder assetQry = new StringBuilder();
            assetQry.append("SELECT  GRV.OBJID,TBO.ORG_ID, GRVH.VEH_HDR, GRVH.VEH_HDR_NO, TSP.SERIAL_NO, GRCC.CONTROLLER_CFG, GRCC.CONTROLLER_CFG_DESC,TSP.INSTALL_DATE,TSP.X_NX_MACHINE_ID, GRM.MODEL_NAME,GRF.FLEET_NUMBER, EGA_CFG.COMM_ID,TSP.PART_STATUS,TBO.NAME,GRM.OBJID MODELID,GRF.OBJID FLEETID,LMS_LOCO_ID,GRV.BAD_ACTOR   ");
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
                    assetQry.append(" AND TBO.ORG_ID IN ( :customerId )");
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
                 arlAssetDetails = new ArrayList<AssetServiceVO>(arlAssetNo.size());
                    DateFormat formatter = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.yyyyMMdd);
                    Collections.sort(arlAssetNo, new Comparator<Object[]>() {
                        @Override
						public int compare(Object[] obj1, Object[] obj2) {
                            int result = 0;
                            try {
                                int assetNumber1 = RMDCommonUtility
                                        .convertObjectToInt(obj1[4]);
                                int assetNumber2 = RMDCommonUtility
                                        .convertObjectToInt(obj2[4]);
                                if (assetNumber1 == assetNumber2) {
                                    result = 0;
                                }
                                result = (assetNumber1 < assetNumber2) ? -1 : 1;
                            } catch (Exception e) {
                            	 LOG.error(e);
                                String assetNumber1 = RMDCommonUtility
                                        .convertObjectToString(obj1[4]);
                                String assetNumber2 = RMDCommonUtility
                                        .convertObjectToString(obj2[4]);

                                result = assetNumber1
                                        .compareToIgnoreCase(assetNumber2);
                            }
                            return result;
                        }
                    });

                    for (Object[] obj : arlAssetNo) {
                        localAssetServicevo = new AssetServiceVO();
                        localAssetServicevo.setSourceObjid(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        localAssetServicevo.setCustomerID(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        localAssetServicevo.setAssetGroupName(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        localAssetServicevo
                                .setAssetGroupNumber(RMDCommonUtility
                                        .cnvrtBigDecimalObjectToLong(obj[3]));

                        localAssetServicevo.setStrAssetNumber(RMDCommonUtility
                                .convertObjectToString(obj[4]));
                        localAssetServicevo
                                .setControllerConfig(RMDCommonUtility
                                        .convertObjectToString(obj[5]));
                        if (null != obj[7]
                                && !RMDCommonConstants.EMPTY_STRING
                                        .equals(obj[7])) {
                            Date createDate = formatter.parse(RMDCommonUtility
                                    .convertObjectToString(obj[7]));
                            localAssetServicevo.setInstallationDate(createDate);
                        }
                        localAssetServicevo.setCmuId(RMDCommonUtility
                                .convertObjectToString(obj[8]));
                        localAssetServicevo.setModel(RMDCommonUtility
                                .convertObjectToString(obj[9]));
                        localAssetServicevo.setFleet(RMDCommonUtility
                                .convertObjectToString(obj[10]));
                        localAssetServicevo.setEgaCommId(RMDCommonUtility
                                .convertObjectToString(obj[11]));
                        localAssetServicevo.setPartStatus(RMDCommonUtility
                                .convertObjectToString(obj[12]));
                        localAssetServicevo.setCustomerName(RMDCommonUtility
                                .convertObjectToString(obj[13]));
                        localAssetServicevo.setStrModelId(RMDCommonUtility
                                .convertObjectToString(obj[14]));
                        localAssetServicevo.setStrFleetId(RMDCommonUtility
                                .convertObjectToString(obj[15]));
                        localAssetServicevo.setLmsLocoId(RMDCommonUtility
                                .convertObjectToString(obj[16]));
                        if (null != obj[17] && !RMDCommonConstants.EMPTY_STRING.equals(obj[17])) 
                        {
                            localAssetServicevo.setBadActor(RMDCommonUtility.convertObjectToString(obj[17]));
                        }
                        arlAssetDetails.add(localAssetServicevo);
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
        LOG.debug("Ends getAssets method of AssetDAOImpl");
        return arlAssetDetails;

    }
    
    @Override
	@SuppressWarnings("unchecked")
    public List<AssetInstalledProductVO> getInstalledProduct(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException {
        LOG.debug("Begin getInstalledProduct method of AssetDAOImpl");
        List<AssetInstalledProductVO> arlAssetProdDetails = new ArrayList<AssetInstalledProductVO>();
        Session objSession = null;
        List<Object[]> arlAssetNo = null;
        AssetServiceVO localAssetServicevo = objAssetServiceVO;
        AssetInstalledProductVO installedProduct = null;
        Date dtWarrantyEndDate;
        try {
            objSession = getHibernateSession();
            StringBuilder assetQry = new StringBuilder();
            assetQry.append(" SELECT OBJID, PART_NUMBER, DESCRIPTION, S_DESCRIPTION, SERIAL_NO, S_SERIAL_NO, TO_CHAR(WARRANTY_DATE,'MM/DD/YYYY HH24:MI:SS') WARRANTY_DATE FROM SA.TABLE_SCM_VIEW ");
            assetQry.append(" WHERE SERIAL_NO = :assetNumber AND RNH = :assetGrpName");
            if (null != objSession) {
                Query assetHqry = objSession
                        .createSQLQuery(assetQry.toString());
                if (null != localAssetServicevo.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(localAssetServicevo
                                        .getStrAssetNumber())) {
                    assetHqry.setParameter(RMDCommonConstants.ASSET_NUMBER,
                            localAssetServicevo.getStrAssetNumber());
                }
                if (null != localAssetServicevo.getAssetGroupName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(localAssetServicevo.getAssetGroupName())) {
                    assetHqry.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                            localAssetServicevo.getAssetGroupName());
                }
                assetHqry.setFetchSize(1);
                arlAssetNo = (ArrayList) assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
                    DateFormat formatter = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                    for (Object[] obj : arlAssetNo) {
                        installedProduct = new AssetInstalledProductVO();
                        installedProduct.setPartNumber(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        installedProduct.setDescription(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        String convertObjectToString = RMDCommonUtility
                                .convertObjectToString(obj[6]);
                        if (null != convertObjectToString) {
                            dtWarrantyEndDate = (Date) formatter
                                    .parse(convertObjectToString);
                            installedProduct
                                    .setWarrantyEndDate(dtWarrantyEndDate);
                        }
                        arlAssetProdDetails.add(installedProduct);
                    }
                }
            }
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
        LOG.debug("Ends getInstalledProduct method of AssetDAOImpl");
        return arlAssetProdDetails;

    }
    
    @Override
	public Map<String, LinkedHashMap<String, AssetLocatorVO>> getAssetLocationForCustomers(final AssetLocatorBean assetLocatorBean) throws RMDDAOException {
        LOG.debug("EOAService :AssetDAOImpl :getAssetDetailsForOverview() ::::START");
        String customerId = assetLocatorBean.getCustomerId();
        String roadInitial = assetLocatorBean.getAssetGrpName(); 
        String roadNo = assetLocatorBean.getAssetNumber();
        boolean isLastFault = assetLocatorBean.isLastFault();
        List<String> products = assetLocatorBean.getProducts();
        Session hibernateSession = null;
        AssetLocatorVO assetLocatorVO = null;
        List<Object[]> assetLocatorList = null;
        Date dtLastProcessDate = null;
        Date lstFaultdt_flt = null;
        Date dtLastFaultResetTime =null;
        Date dtLastHealthChkRequest = null;
        Date dtLastFaultDateCell = null;
        Date dtLastKeepAliveMsgRevd = null;
        Date dtAsstTrackerMsg = null;
        Date dtlstShopDwnld = null;
        boolean isReqFromFleet = "FLEETS".equals(assetLocatorBean.getScreenName());
        
        StringBuilder assetLocatorQry = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        
        Map<String, LinkedHashMap<String, AssetLocatorVO>> locationDataMap = new LinkedHashMap<String, LinkedHashMap<String, AssetLocatorVO>>();
        // LAST_FAULT_DATE_FLT,LAST_PROCESSED_DATE added as part of OMD Performance.
        assetLocatorQry.append(" SELECT STBL.ORG_ID,   STBL.X_VEH_HDR,   STBL.SERIAL_NO,   STBL.MODEL_NAME,  TO_CHAR(SNAP.DATE_TIME,'MM/DD/YYYY HH24:MI:SS') DATE_TIME,   ");
        assetLocatorQry.append(" STBL.MODEL_OBJID,   STBL.FLEET_OBJID,   SNAP.GPS_LAT,   SNAP.GPS_LON,   TO_CHAR(SNAP.LAST_KEEP_ALIVE_DATE,'MM/DD/YYYY HH24:MI:SS') LAST_KEEP_ALIVE_DATE,   ");
        assetLocatorQry.append(" TO_CHAR(SNAP.LASTPPATS_DATE,'MM/DD/YYYY HH24:MI:SS') LASTPPATS_DATE,   TO_CHAR(SNAP.LAST_FAULT_DATE_FLT,'MM/DD/YYYY HH24:MI:SS') LAST_FAULT_DATE_FLT,   ");
        assetLocatorQry.append(" TO_CHAR((   CASE     WHEN GREATEST(NVL(SNAP.LAST_FAULT_DATE_FLT, '01-JAN-0001'), NVL(SNAP.LAST_FAULT_DATE_STP, '01-JAN-0001'), NVL(SNAP.LAST_FAULT_DATE_CEL, '01-JAN-0001') ) <> '01-JAN-0001'     THEN GREATEST(NVL(SNAP.LAST_FAULT_DATE_FLT, '01-JAN-0001'), ");
        assetLocatorQry.append(" NVL(SNAP.LAST_FAULT_DATE_STP, '01-JAN-0001'), NVL(SNAP.LAST_FAULT_DATE_CEL, '01-JAN-0001') )     ELSE NULL   END) ,'MM/DD/YYYY HH24:MI:SS') LAST_FAULT_DATE,   TO_CHAR(CAST(FROM_TZ(CAST(TO_TIMESTAMP(TO_CHAR((CASE WHEN GREATEST(NVL(SNAP.FAULT_RESET_DATE_CEL, '01-JAN-0001'), ");
        assetLocatorQry.append(" NVL(SNAP.FAULT_RESET_DATE_FLT, '01-JAN-0001'), NVL(SNAP.FAULT_RESET_DATE_STP, '01-JAN-0001') ) <> '01-JAN-0001' THEN GREATEST(NVL(SNAP.FAULT_RESET_DATE_CEL, '01-JAN-0001'), NVL(SNAP.FAULT_RESET_DATE_FLT, '01-JAN-0001'), ");
        assetLocatorQry.append(" NVL(SNAP.FAULT_RESET_DATE_STP, '01-JAN-0001') ) ELSE NULL END) ,'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS') AS TIMESTAMP), 'GMT') at time zone 'US/Eastern' AS TIMESTAMP),'MM/DD/YYYY HH24:MI:SS') as FAULT_RESET_DATE,   ");
        assetLocatorQry.append(" TO_CHAR(SNAP.LAST_PROCESSED_DATE,'MM/DD/YYYY HH24:MI:SS') LAST_PROCESSED_DATE, ");
        assetLocatorQry.append(" TO_CHAR(SNAP.LAST_FAULT_DATE_STP,'MM/DD/YYYY HH24:MI:SS') LAST_FAULT_DATE_STP,   STBL.VEH_OBJID, TO_CHAR(SNAP.LAST_FAULT_DATE_HC,'MM/DD/YYYY HH24:MI:SS') LAST_FAULT_DATE_HC, TO_CHAR(SNAP.LAST_FAULT_DATE_CEL,'MM/DD/YYYY HH24:MI:SS') LAST_FAULT_DATE_CEL, ");
        assetLocatorQry.append(" SNAP.ATS_LAT, SNAP.ATS_LON, TO_CHAR(SNAP.ATS_LAST_COMM_DATE,'MM/DD/YYYY HH24:MI:SS'), TO_CHAR(SNAP.LAST_GPSLATLON_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS'), ");
        assetLocatorQry.append(" CASE WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE IS NULL AND SNAP.ATS_LAST_COMM_DATE IS NULL THEN 'NONE' ");
        assetLocatorQry.append(" WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE IS NOT NULL AND SNAP.ATS_LAST_COMM_DATE IS NULL THEN 'FLT' ");
        assetLocatorQry.append(" WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE IS NULL AND SNAP.ATS_LAST_COMM_DATE IS NOT NULL THEN 'ATS' ");  
        assetLocatorQry.append(" WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE > SNAP.ATS_LAST_COMM_DATE THEN 'FLT' ");
        assetLocatorQry.append(" WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE < SNAP.ATS_LAST_COMM_DATE THEN 'ATS' ");
        assetLocatorQry.append(" WHEN SNAP.LAST_GPSLATLON_UPDATED_DATE = SNAP.ATS_LAST_COMM_DATE THEN 'FLT' END ");
        assetLocatorQry.append(" FROM (  SELECT VEHDETAILS.ORG_ID,VEHDETAILS.X_VEH_HDR,VEHDETAILS.SERIAL_NO,VEHDETAILS.MODEL_NAME,VEHDETAILS.MODEL_OBJID,VEHDETAILS.FLEET_OBJID,VEHDETAILS.VEH_OBJID ");
        assetLocatorQry.append(" FROM (SELECT TBO.ORG_ID, TSP.X_VEH_HDR, TSP.SERIAL_NO, MDL.MODEL_NAME, MDL.OBJID MODEL_OBJID, FLT.OBJID FLEET_OBJID, VEH.OBJID VEH_OBJID ");        
        assetLocatorQry.append(" FROM TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO, GETS_RMD_MODEL MDL, GETS_RMD_FLEET FLT  ");
        assetLocatorQry.append(" WHERE TSP.OBJID  = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID  AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND VEH.VEHICLE2MODEL = MDL.OBJID AND VEH.VEHICLE2FLEET = FLT.OBJID   AND INSTR(TSP.SERIAL_NO, 'BAD', 1, 1) = 0 ");
        if(RMDCommonUtility.isCollectionNotEmpty(products) && !products.contains(RMDCommonConstants.DEFAULT_STRING)){
            assetLocatorQry.append(" AND VEH.OBJID IN (select ASSET_ID from GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST WHERE PRODUCT_CD IN(:productName)) ");
        }
   
            
        if (customerId != null && !customerId.equalsIgnoreCase(RMDCommonConstants.ALL_CUSTOMER) && !customerId.trim().equals("")) {
            assetLocatorQry.append(" AND TBO.ORG_ID = :customerId");
        }
        if (roadInitial != null && !roadInitial.trim().equals("")) {
            assetLocatorQry.append(" AND TSP.X_VEH_HDR = :assetGrpName");
        }
        if (roadNo != null && !roadNo.trim().equals("")) {
            assetLocatorQry.append(" AND TSP.SERIAL_NO = :assetNumber");
        }
        
        assetLocatorQry.append(" ) VEHDETAILS ");
        assetLocatorQry.append(" ) STBL LEFT OUTER JOIN GETS_RMD.GETS_RMD_ASSET_SNAPSHOT SNAP ON (SNAP.vehicle_objid = STBL.VEH_OBJID)");
        
        StringBuilder toolRunQuery = new StringBuilder();
        toolRunQuery.append("SELECT RUN2VEHICLE, MAX(TO_CHAR(RUN_CPT, 'MM/DD/YYYY HH24:MI:SS')) PROCESS_DATE  FROM GETS_TOOL_RUN ");
        toolRunQuery.append(" WHERE CREATION_DATE > SYSDATE - 7 AND DIAG_SERVICE_ID IN ('DHMS', 'EOA', 'ESTP', 'MNS', 'OHV', 'OIL', 'QNX Equipment') ");
        toolRunQuery.append(" AND RUN_CPT IS NOT NULL GROUP BY RUN2VEHICLE ");
        try {
            hibernateSession = getHibernateSession();
            if (null != hibernateSession) {
                Query hibernateQuery = hibernateSession.createSQLQuery(assetLocatorQry.toString());
                if (customerId != null && !customerId.equalsIgnoreCase(RMDCommonConstants.ALL_CUSTOMER) && !customerId.trim().equals("")) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
                }
                if (roadInitial != null && !roadInitial.trim().equals("")) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, roadInitial);
                }
                if (roadNo != null && !roadNo.trim().equals("")) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, roadNo);
                }
                if(RMDCommonUtility.isCollectionNotEmpty(products) && !products.contains(RMDCommonConstants.DEFAULT_STRING)){
                    hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_NAME, products);
                }
                hibernateQuery.setFetchSize(1000);
                assetLocatorList = hibernateQuery.list();
                if (null != assetLocatorList && !assetLocatorList.isEmpty()) {
                	Map<String, String> toolMap  = new HashMap<String, String>();
                	if(isReqFromFleet){
	                	Query toolRunHibQuery = hibernateSession.createSQLQuery(toolRunQuery.toString());
	                	List<Object[]> toolRunList = toolRunHibQuery.list();
	                	if(toolRunList != null && !toolRunList.isEmpty()){
	                		for(Object[] resultObject : toolRunList){
	                			toolMap.put(String.valueOf(resultObject[0]), String.valueOf(resultObject[1]));
	                		}
	                	}
                	}
                    for (int indx = 0; indx < assetLocatorList.size(); indx++) {
                        assetLocatorVO = new AssetLocatorVO();
                        Object assetData[] =  assetLocatorList.get(indx);
                        
                        //ORG_ID
                        assetLocatorVO.setCustomerId(RMDCommonUtility.convertObjectToString(assetData[0]));
                        //X_VEH_HDR
                        assetLocatorVO.setAssetGrpName(RMDCommonUtility.convertObjectToString(assetData[1]));
                        //SERIAL_NO
                        assetLocatorVO.setAssetNumber(RMDCommonUtility.convertObjectToString(assetData[2]));
                        //MODEL_NAME
                        assetLocatorVO.setModelName(RMDCommonUtility.convertObjectToString(assetData[3]));                      
                        //DATE_TIME
                        
                        //Have changed the parsing format, since the date_time column's datatype has changed from varchar to date
                        String convertObjectToStringMaxOccur = RMDCommonUtility.convertObjectToString(assetData[4]);
                        if (null != convertObjectToStringMaxOccur) {
                            assetLocatorVO.setMaxOccurTime(RMDCommonUtility
                                        .stringToGMTDate(convertObjectToStringMaxOccur, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
                        }

                        //MODEL_OBJID
                        assetLocatorVO.setModelObjId(RMDCommonUtility.convertObjectToString(assetData[5]));
                        //FLEET_OBJID
                        assetLocatorVO.setFleetObjId(RMDCommonUtility.convertObjectToString(assetData[6]));
                        //GPS_LAT
                        assetLocatorVO.setLatitude(RMDCommonUtility.convertObjectToString(assetData[7]));
                        //GPS_LON
                        assetLocatorVO.setLongitude(RMDCommonUtility.convertObjectToString(assetData[8]));
                        String snapShotLatest =  RMDCommonUtility.convertObjectToString(assetData[23]);
                        assetLocatorVO.setLatlonSource("FLT");
                        if (snapShotLatest != null && snapShotLatest.trim().equalsIgnoreCase("ATS")) {
                            assetLocatorVO.setLatitude(RMDCommonUtility.convertObjectToString(assetData[19]));
                            assetLocatorVO.setLongitude(RMDCommonUtility.convertObjectToString(assetData[20]));
                            assetLocatorVO.setLatlongDate((Date) formatter.parse(RMDCommonUtility.convertObjectToString(assetData[21])));
                            assetLocatorVO.setLatlonSource("ATS");
                        } else if (snapShotLatest != null && snapShotLatest.trim().equalsIgnoreCase("FLT")) {
                            assetLocatorVO.setLatlongDate((Date) formatter.parse(RMDCommonUtility.convertObjectToString(assetData[22])));
                        }
                        //LAST_KEEP_ALIVE_DATE
                        if (null != RMDCommonUtility.convertObjectToString(assetData[9])) {
                            dtLastKeepAliveMsgRevd = formatter.parse(RMDCommonUtility.convertObjectToString(assetData[9]));
                            assetLocatorVO.setDtLastKeepAliveMsgRevd(dtLastKeepAliveMsgRevd);
                        }
                        //LASTPPATS_DATE
                        String convertObjectToString = RMDCommonUtility.convertObjectToString(assetData[10]);
                        if (null != convertObjectToString) {
                            dtAsstTrackerMsg = formatter.parse(convertObjectToString);
                            assetLocatorVO.setLstPPATSMsgHeader(dtAsstTrackerMsg);
                        }
                        
                        //LAST_FAULT_DATE_FLT
//                      If the request is from Fleet screen(isLastFault = true), then the last_fault_date_flt need to be assigned else last_fault_date need to be assigned.
                        lstFaultdt_flt=null;
                        if (isLastFault) {

                            String convertObjectToString2 = RMDCommonUtility.convertObjectToString(assetData[11]);
                            if (null != convertObjectToString2) {
                                lstFaultdt_flt =  formatter.parse(convertObjectToString2);
                            }
                        } else {
                            //LAST_FAULT_DATE
                            String convertObjectToString2 = RMDCommonUtility.convertObjectToString(assetData[12]);
                            if (null != convertObjectToString2) {
                                lstFaultdt_flt =  formatter.parse(convertObjectToString2);
                            }
                        }
                        
                        assetLocatorVO.setLstFaultdt(lstFaultdt_flt);
                        
                        //FAULT_RESET_DATE
                        String convertObjectToString2 = RMDCommonUtility.convertObjectToString(assetData[13]);
                        if (null != convertObjectToString2) {
                            dtLastFaultResetTime =  formatter.parse(convertObjectToString2);
                            assetLocatorVO.setDtLastFaultResetTime(dtLastFaultResetTime);
                        }
                        
                        
                        
                        // LAST_PROCESSED_DATE
                        String convertObjectToString3 = null;
                    	if(isReqFromFleet){
                    		String vehicleObjId = RMDCommonUtility.convertObjectToString(assetData[16]);
                    		if(vehicleObjId != null){
                    			convertObjectToString3 = toolMap.get(vehicleObjId);
                    		}
                    	}else{
	                        convertObjectToString3 = RMDCommonUtility.convertObjectToString(assetData[14]);
	                    }
                    	if (null != convertObjectToString3) {
                            dtLastProcessDate = formatter.parse(convertObjectToString3);
                            assetLocatorVO.setLstPrcddt(dtLastProcessDate);
                        }
                        //LAST_FAULT_DATE_STP
                        String convertObjectToString4 = RMDCommonUtility.convertObjectToString(assetData[15]);
                        if (null != convertObjectToString4) {
                            dtlstShopDwnld =  formatter.parse(convertObjectToString4);
                            assetLocatorVO.setLstESTPDownloadHeader(dtlstShopDwnld);
                        }
                        
                        //LAST_FAULT_DATE_HC
                        String convertObjectToStringHealthCheck = RMDCommonUtility.convertObjectToString(assetData[17]);
                        if (null != convertObjectToStringHealthCheck) {
                            dtLastHealthChkRequest = formatter.parse(convertObjectToStringHealthCheck);
                            assetLocatorVO.setDtLastHealthChkRequest(dtLastHealthChkRequest);
                        }
                        
                        //LAST_FAULT_DATE_CEL
                        String convertObjectToStringFaultDateCell = RMDCommonUtility.convertObjectToString(assetData[18]);
                        if (null != convertObjectToStringFaultDateCell) {
                            dtLastFaultDateCell =  formatter.parse(convertObjectToStringFaultDateCell);
                            assetLocatorVO.setLstFalultDateCell(dtLastFaultDateCell);
                        }
                        
                        
                        assetLocatorVO.setDtLastFaultReserved(lstFaultdt_flt);
                        
                        
                        //Added as part of merging getLastFaultStatus code with getAssetLocation
                        assetLocatorVO.setLstEOAFaultHeader( lstFaultdt_flt);
                        
                        
                        if (null != assetLocatorVO.getCustomerId()) {
                        	LinkedHashMap<String, AssetLocatorVO> exAssetLocatorVOMap = null;
                            if (locationDataMap.containsKey(assetLocatorVO.getCustomerId())) { 
                                exAssetLocatorVOMap = locationDataMap.get(assetLocatorVO.getCustomerId());
                            } else { 
                                exAssetLocatorVOMap = new LinkedHashMap<String, AssetLocatorVO>();
                            }
                            String key = assetLocatorVO.getAssetNumber()
                                    + RMDCommonConstants.HYPHEN
                                    + assetLocatorVO.getCustomerId()
                                    + RMDCommonConstants.HYPHEN
                                    + assetLocatorVO.getAssetGrpName();
                            exAssetLocatorVOMap.put(key, assetLocatorVO);
                            locationDataMap.put(assetLocatorVO.getCustomerId(), exAssetLocatorVOMap);
                        }

                    }
                }
            }
        }
         catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("EOAService :AssetDAOImpl :getAssetLocationForCustomers() ::::END");
        return locationDataMap;     
    }
    
        
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.rmd.services.asset.dao.intf.AssetDAOIntf#getAssetsForRole
     * (com.ge.trans.rmd.services.asset.service.valueobjects.RoleAssetsVO) This
     * method is used to get the CustomerId and List of Assets for a particular
     * role
     */
    @Override
	public LinkedHashMap<String, List<String>> getAssetNoForProducts(List<String> prdNameList,String customerId) {
        
        Session hibernateSession = null;
        StringBuilder roleAssetQry = new StringBuilder();
        List<Object[]> arlAssets = null;
        LinkedHashMap<String, List<String>> custAssetMap = new LinkedHashMap<String, List<String>>();
        try {
            roleAssetQry
                    .append(" select TBO.org_id,TSP.Serial_No from GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST, ");
            roleAssetQry
                    .append(" TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
            roleAssetQry
                    .append(" where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
            roleAssetQry
                    .append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            roleAssetQry
                    .append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and ELGASST.PRODUCT_CD IN(:productNameLst)");
            // Passing customer id where clause for getting product assets
            if (null != customerId
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(customerId)) {
                roleAssetQry.append(" AND TBO.ORG_ID=:customerId");
            }

            hibernateSession = getHibernateSession();
            if (hibernateSession != null) {
                Query hibernateQuery = hibernateSession
                        .createSQLQuery(roleAssetQry.toString());
                // Passing customer id for getting product assets
                if (null != customerId
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(customerId)) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                            customerId);
                }
                
                /* Added for Product Asset Configuration */
                    hibernateQuery.setParameterList(
                            RMDCommonConstants.PRODUCT_CONF_NAME_LST, prdNameList);
                /* Added for Product Asset Configuration */
                arlAssets = hibernateQuery.list();
                List<String> strCustLst = new ArrayList<String>();
                
                if (RMDCommonUtility.isCollectionNotEmpty(arlAssets)) {
                    for (Object[] obj : arlAssets) {

                        if (strCustLst.contains(RMDCommonUtility
                                .convertObjectToString(obj[0]))) {
                            List<String> tmpAsstLst = custAssetMap
                                    .get(RMDCommonUtility
                                            .convertObjectToString(obj[0]));
                            tmpAsstLst.add(RMDCommonUtility
                                    .convertObjectToString(obj[1]));
                            custAssetMap.put(RMDCommonUtility
                                    .convertObjectToString(obj[0]), tmpAsstLst);
                        } else {
                            List<String> tmpAsstLst = new ArrayList<String>();
                            strCustLst.add(RMDCommonUtility
                                    .convertObjectToString(obj[0]));
                            tmpAsstLst.add(RMDCommonUtility
                                    .convertObjectToString(obj[1]));
                            custAssetMap.put(RMDCommonUtility
                                    .convertObjectToString(obj[0]), tmpAsstLst);
                        }

                    }

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }

        return custAssetMap;

    }
    
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : AssetLocatorVO
     * @throws RMDDAOException
     * @Description: Connects to DB and returns the Last Download status and
     *               other fields for an asset for diesel doctor role
     */
    @Override
	public LastDownloadStatusResponseEoaVO getLastDowloadStatus(
            final LastDownloadStatusEoaVO lstDownloadStatus) {

        LOG.debug("EOAService :AssetDAOImpl :getLastDowloadStatus() ::::START");
        Session hibernateSession = null;
        LastDownloadStatusResponseEoaVO lstDownRespVO = new LastDownloadStatusResponseEoaVO();
        Connection con = null;
        ResultSet assetStatusRs = null;
        String sessionId = null;
        CallableStatement callableStatement = null;
        StringBuilder strQuery = new StringBuilder();
        
        try {
            hibernateSession = getHibernateSession();
            con =hibernateSession.connection();

            callableStatement = con
                    .prepareCall("{call gets_rmd_kas_mb_rpt_mobile_pkg.gets_rmd_kas_mb_rpt_mobile_prc(?,?,?,?,?,?)}");

            callableStatement.setString(1, lstDownloadStatus.getCustomerId());
            callableStatement.setString(2, lstDownloadStatus.getNoDays());
            callableStatement.setString(3, lstDownloadStatus.getAssetNumber());
            callableStatement.registerOutParameter(4, Types.FLOAT);
            callableStatement.registerOutParameter(5, Types.FLOAT);
            callableStatement.registerOutParameter(6, Types.VARCHAR);

            callableStatement.execute();

            sessionId = callableStatement.getString(4);

        } catch (RMDDAOConnectionException e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_DOWNLOAD_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            lstDownloadStatus.getStrLanguage()), e,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	 LOG.error(e);
        } finally {

            try {
                if (assetStatusRs != null) {
                    assetStatusRs.close();
                }

                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            	 LOG.error(e);
                LOG.error("Unexpected Error occured in AssetEoaDAOImpl in getLastDowloadStatus() while closing the resultset."+e.getMessage());
            }

        }

        if (sessionId != null && sessionId.length() > 0) {
            try {


                strQuery.append("SELECT V.VEHICLE_HDR CUSTOMERHEADER,");
                strQuery.append("V.VEHICLE_NO ROADNUMBER,");
                strQuery.append("TO_CHAR(DOWNLOAD_DAY, 'DD-MON-YY') LAST_DOWNLOAD_TIME,");
                strQuery.append("LKUP.LOOK_VALUE STATE,");
                strQuery.append("DECODE(KAS.VEHICLE_GROUP,");
                strQuery.append("'L',");
                strQuery.append(" (NVL2(TO_CHAR(KAS.VAX_LAST_DOWNLOAD), 'Complete', 'Missing')),");
                strQuery.append("  'N/A') VAX,");
                strQuery.append("TO_CHAR(KAS.VAX_LAST_DOWNLOAD, 'DD-MON-YYYY HH24:MI:SS') VAX_DATE,");
                strQuery.append("DECODE(KAS.VEHICLE_GROUP,");
                strQuery.append("  'L',");
                strQuery.append("  (NVL2(TO_CHAR(KAS.EGU_LAST_DOWNLOAD), 'Complete', 'Missing')),");
                strQuery.append("  'N/A') EGU,");
                strQuery.append("TO_CHAR(KAS.EGU_LAST_DOWNLOAD, 'DD-MON-YYYY HH24:MI:SS') EGU_DATE,");
                strQuery.append("DECODE(KAS.VEHICLE_GROUP,");
                strQuery.append("  'E',");
                strQuery.append("  (NVL2(TO_CHAR(KAS.INC_LAST_DOWNLOAD), 'Complete', 'Missing')),");
                strQuery.append("  'N/A') INC,");
                strQuery.append("TO_CHAR(KAS.INC_LAST_DOWNLOAD, 'DD-MON-YYYY HH24:MI:SS') INC_DATE,");
                strQuery.append("DECODE(KAS.VEHICLE_GROUP,");
                strQuery.append("  'E',");
                strQuery.append("  (NVL2(TO_CHAR(KAS.SNP_LAST_DOWNLOAD), 'Complete', 'Missing')),");
                strQuery.append("  'N/A') SNP,");
                strQuery.append("TO_CHAR(KAS.SNP_LAST_DOWNLOAD, 'DD-MON-YYYY HH24:MI:SS') SNP_DATE");
                strQuery.append("  FROM GETS_RMD_KAS_MB_RPT    KAS,");
                strQuery.append(" GETS_RMD_LOOKUP        LKUP,");
                strQuery.append(" GETS_RMD_CUST_RNH_RN_V V");
                strQuery.append(" WHERE KAS.STATUS2LOOKUP = LKUP.OBJID");
                strQuery.append("   AND V.VEHICLE_OBJID = KAS.KASMBRPT2VEHICLE");
                strQuery.append("  AND KAS.SESSION_ID = :sessionId");
                strQuery.append("  ORDER BY GREATEST(NVL(VAX_DATE, TO_DATE('01-01-1900', 'DD-MM-YYYY')),");
                strQuery.append("  NVL(EGU_DATE, TO_DATE('01-01-1900', 'DD-MM-YYYY')),");
                strQuery.append("    NVL(INC_DATE, TO_DATE('01-01-1900', 'DD-MM-YYYY')),");
                strQuery.append("   NVL(SNP_DATE, TO_DATE('01-01-1900', 'DD-MM-YYYY'))) DESC");

                Query query = hibernateSession.createSQLQuery(strQuery
                        .toString());
                query.setParameter(RMDCommonConstants.SESSION_ID, sessionId);
                List<Object> lsDownloadStatus = query.list();
                /*
                 * After fetching the list putting the values in VO It will take
                 * only the first row
                 */
                if (RMDCommonUtility.isCollectionNotEmpty(lsDownloadStatus)) {
                    DateFormat dFormat = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE_FORMAT);
                    Object lstDownloadStatusData[] = (Object[]) lsDownloadStatus
                            .get(0);

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[0]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[0]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setCustHeader(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[0]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[1]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[1]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setAssetNumber(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[1]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[2]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[2]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setLstDownloadHeader(RMDCommonConstants.ASSET_LAST_DOWNLOAD);
                        lstDownRespVO
                                .setLstDownloadTm(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[2]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[3]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[3]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setStateHeader(RMDCommonConstants.ASSET_LAST_DOWNLOAD_STATE);
                        lstDownRespVO
                                .setState(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[3]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[4]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[4]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setVaxHeader(RMDCommonConstants.ASSET_CAB_CAX);
                        lstDownRespVO
                                .setVax(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[4]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[5]) != null) {
                        lstDownRespVO
                                .setVaxDt(dFormat.parse(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[5])));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[6]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[6]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setEguHeader(RMDCommonConstants.ASSET_EGU);
                        lstDownRespVO
                                .setEgu(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[6]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[7]) != null) {
                        lstDownRespVO
                                .setEguDt(dFormat.parse(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[7])));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[8]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[8]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setIncHeader(RMDCommonConstants.ASSET_SDIS_INCIDENT);
                        lstDownRespVO
                                .setInc(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[8]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[9]) != null) {
                        lstDownRespVO
                                .setIncDt(dFormat.parse(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[9])));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[10]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lstDownloadStatusData[10])
                                    .equalsIgnoreCase(RMDCommonConstants.NA)) {
                        lstDownRespVO
                                .setSnpHeader(RMDCommonConstants.ASSET_SDIS_SNAPSHOT);
                        lstDownRespVO
                                .setSnp(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[10]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lstDownloadStatusData[11]) != null) {
                        lstDownRespVO
                                .setSnpDt(dFormat.parse(RMDCommonUtility
                                        .convertObjectToString(lstDownloadStatusData[11])));
                    }
                } else {
                    DateFormat dFormat = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.LAST_DOWNLOAD_DATE);
                    Calendar currentDate = Calendar.getInstance();
                    String sysDate = dFormat.format(currentDate.getTime());
                    lstDownRespVO
                            .setLstDownloadHeader(RMDCommonConstants.ASSET_LAST_DOWNLOAD);
                    lstDownRespVO.setLstDownloadTm(sysDate);
                    lstDownRespVO
                            .setStateHeader(RMDCommonConstants.ASSET_LAST_DOWNLOAD_STATE);
                    lstDownRespVO.setState(RMDCommonConstants.NO_DOWNLOADS);
                }
            } catch (RMDDAOConnectionException ex) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.ERROR_LAST_DOWNLOAD_STATUS);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                lstDownloadStatus.getStrLanguage()), ex,
                        RMDCommonConstants.FATAL_ERROR);
            } catch (Exception e) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.ERROR_LAST_DOWNLOAD_STATUS);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                lstDownloadStatus.getStrLanguage()), e,
                        RMDCommonConstants.MAJOR_ERROR);
            }finally {
                releaseSession(hibernateSession);
            }
        }

        return lstDownRespVO;
    }
    
    /*
     * This method is used for fetching the Last Fault data for any role.
     */
    @Override
	public LastFaultStatusResponseEoaVO getLastFaultStatus(final LastFaultStatusEoaVO lstFaultStatus) 
    {
        
        LastFaultStatusResponseEoaVO lastFaultRespVO = new LastFaultStatusResponseEoaVO();
        Session hibernateSession = null;
        StringBuilder strQueryLstFault = new StringBuilder();
        final StringBuilder vehicleObjIdQry = new StringBuilder();
        String vehicleObjId = null;

        try {
            hibernateSession = getHibernateSession();
            vehicleObjIdQry.append("SELECT VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V ");
            vehicleObjIdQry.append("WHERE ORG_ID = :customerId AND VEHICLE_HDR_CUST = :assetGrpName AND VEHICLE_NO = :assetNumber ");
        
            final Query objIdQuery = hibernateSession.createSQLQuery(vehicleObjIdQry.toString());
        
            objIdQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,lstFaultStatus.getCustomerId());
            objIdQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,lstFaultStatus.getAssetNumber());
            objIdQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,lstFaultStatus.getAssetGrpName());
        
            objIdQuery.setFetchSize(10);
            List<Object> lsObjId = objIdQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lsObjId)) {
                vehicleObjId = RMDCommonUtility.convertObjectToString(lsObjId.get(0));
            }
            if(vehicleObjId != null)
            {
                strQueryLstFault.append("SELECT EOALASTFAULT.EOALASTFAULT, LASTESTP.LASTESTP, LASTPPATS.LASTPPATS ");
                strQueryLstFault.append("from  (select to_char(max(creation_date),'DD-MON-YYYY HH24:MI:SS') EOALASTFAULT, fault2vehicle ");
                strQueryLstFault.append("from gets_tool_fault where record_type = 'FLT' and fault2vehicle = :vehicleObjId group by fault2vehicle) EOALASTFAULT, ");
                strQueryLstFault.append("(select to_char(max(creation_date),'DD-MON-YYYY HH24:MI:SS') LASTESTP, fault2vehicle ");
                strQueryLstFault.append("from gets_tool_fault where record_type = 'STP' and fault2vehicle= :vehicleObjId group by fault2vehicle) LASTESTP, ");
                strQueryLstFault.append("(select to_char(max(hist.last_updated_date), 'DD-MON-YYYY HH24:MI:SS') LASTPPATS, curr.status_curr2vehicle ");
                strQueryLstFault.append("from gets_tool_pp_status_hist hist, gets_tool_pp_status_curr curr where ");
                strQueryLstFault.append("curr.status_curr2status_hist = hist.objid and curr.status_curr2vehicle = :vehicleObjId group by curr.status_curr2vehicle) LASTPPATS ");
                
                Query queryLstFault = hibernateSession.createSQLQuery (strQueryLstFault.toString());
                queryLstFault.setParameter(RMDCommonConstants.VEHICLE_OBJID_LFS, vehicleObjId);
                queryLstFault.setFetchSize(10);
                List<Object> lsLastFaultStatus = queryLstFault.list();

                lastFaultRespVO.setAssetNumber(lstFaultStatus.getAssetNumber());
                lastFaultRespVO.setLstEOAFaultHeader(RMDCommonConstants.ASSET_LAST_EOA_FAULT);
                lastFaultRespVO.setLstESTPDownloadHeader(RMDCommonConstants.ASSET_LAST_ESTP_DOWNLOAD);
                lastFaultRespVO.setLstPPATSMsgHeader(RMDCommonConstants.ASSET_LAST_PP_ATS_MSG);

                if (RMDCommonUtility.isCollectionNotEmpty(lsLastFaultStatus)){
                    Object lsLastFaultStatusData[] = (Object[]) lsLastFaultStatus.get(0);
                    
                    if (RMDCommonUtility
                            .convertObjectToString(lsLastFaultStatusData[0]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lsLastFaultStatusData[0]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lastFaultRespVO.setLstEOAFault(RMDCommonUtility.convertObjectToString(lsLastFaultStatusData[0]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lsLastFaultStatusData[1]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lsLastFaultStatusData[1]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lastFaultRespVO.setLstESTPDownload(RMDCommonUtility.convertObjectToString(lsLastFaultStatusData[1]));
                    }

                    if (RMDCommonUtility
                            .convertObjectToString(lsLastFaultStatusData[2]) != null
                            && !RMDCommonUtility.convertObjectToString(
                                    lsLastFaultStatusData[2]).equalsIgnoreCase(
                                    RMDCommonConstants.NA)) {
                        lastFaultRespVO.setLstPPATSMsg(RMDCommonUtility.convertObjectToString(lsLastFaultStatusData[2]));
                    }
                }   
            }
        } 
        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_FAULT_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            lastFaultRespVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_FAULT_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            lastFaultRespVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        
        finally {
            releaseSession(hibernateSession);
            
        }

        return lastFaultRespVO;
    }

    /**
     * This method is used to get Fleet details of rule for the Parameter passed
     * 
     * @param strCustomer
     *            , strModel, strConfig, strUnitNumber, strLanguage
     * @return List of Fleet
     * @throws RMDDAOException
     */
    @Override
	@SuppressWarnings("rawtypes")
    public List getFleetList(String strCustomer, String strModel,
            String[] strConfig, String strUnitNumber, String strLanguage)
            throws RMDDAOException {
        List arlFleets = null;
        Session objHibernateSession = null;

        ElementVO objElementVO = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;
        LOG.debug("Start getFleetList() RuleDAOImpl");
        try {
            arlFleets = new ArrayList();
            objHibernateSession = getHibernateSession();

            String strQry = new String();
            strQry = buildFleetQuery(strCustomer, strModel, strConfig,
                    strUnitNumber, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer)
                    && !(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER,
                        tmpStrCustomer);
            }

            // Setting the Model values to the Query Parameters, if Model value
            // is not equal to ALL
            if ((null != strModel)
                    && !(RMDServiceConstants.ALL.equals(strModel))) {

                String[] tmpStrModel = strModel
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRMODEL,
                        tmpStrModel);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber)
                    && !(RMDServiceConstants.ALL
                            .equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER,
                        tmpStrUnitNumber);
            }

            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig)
                    && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGPARAM_PARAM + i,
                                configParam);
                    }
                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGVALUE1_PARAM + i,
                                configValue1);
                    }
                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGVALUE2_PARAM + i,
                                configValue2);
                    }

                    i = i + 3;
                }

            }

            List<Object[]> fleetList = qryQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(fleetList)) {
                for (Object objFleet[] : fleetList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility
                            .convertObjectToString(objFleet[0]));
                    objElementVO.setName(RMDCommonUtility
                            .convertObjectToString(objFleet[1]));
                    if ((null != strCustomer)
                            && !(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {
                    objElementVO.setCustomerSeqId(RMDCommonUtility
                            .convertObjectToString(objFleet[2]));
                    }
                    arlFleets.add(objElementVO);
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("Size of Fleet Array List : " + arlFleets.size());
        LOG.debug("End getFleetList() RuleDAOImpl");
        return arlFleets;

    }
    
    /**
     * 
     * This method will return the dynamic SQL built based on inputs, which will
     * get the Fleet. In the SQL the table will be joined, only if corresponding
     * input is provided. i.e It will join model table only if models are
     * provided in the input parameters.
     * 
     * @param String
     *            strModel, String strFleet, String[] strConfig, String
     *            strUnitNumber, String strLanguage
     * @return String
     * 
     */
    private String buildFleetQuery(String strCustomer, String strModel,
            String[] strConfig, String strUnitNumber, String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();

        strQuery = strQuery.append(RMDServiceConstants.FLEET_SELECT);
                
        if ((null != strCustomer)
                && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {
        	strQuery=strQuery.append(",CUSTOMER.OBJID AS CUSTOMER_OBJID");
        }
        strQuery=strQuery.append(RMDServiceConstants.FROM)
        .append(RMDServiceConstants.FLEET_TABLE_NAME);
        if (((null != strCustomer) && (!(RMDServiceConstants.ALL_CUSTOMER
                .equals(strCustomer))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL
                        .equals(strConfig[0]))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL
                        .equals(strUnitNumber))))
                || ((null != strModel) && (!(RMDServiceConstants.ALL
                        .equals(strModel))))) {
            strTable = strTable.append(RMDServiceConstants.COMMA).append(
                    RMDServiceConstants.ASSET_TABLE_NAME);
            strTableCondition = strTableCondition.append(
                    RMDServiceConstants.WHERE).append(
                    RMDServiceConstants.ASSET_TO_FLEET_JOIN);

            if ((null != strUnitNumber)
                    && !(RMDServiceConstants.ALL
                            .equals(strUnitNumber))) {

                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);
            }

            if ((null != strCustomer)
                    && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(
                        RMDServiceConstants.CUSTOMER_TABLE_NAME);
                strTableCondition = strTableCondition.append(
                        RMDServiceConstants.AND).append(
                        RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }

            if ((null != strModel)
                    && !(RMDServiceConstants.ALL.equals(strModel))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(
                        RMDServiceConstants.MODEL_TABLE_NAME);
                strTableCondition = strTableCondition.append(
                        RMDServiceConstants.AND).append(
                        RMDServiceConstants.ASSET_TO_MODEL_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.MODEL_NUMBERS_IN);
            }

            if ((null != strConfig)
                    && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                strTable = strTable.append(RMDServiceConstants.COMMA)
                        .append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA)
                        .append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN)
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuffer buffer = new StringBuffer();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }
            strQuery = strQuery.append(strTable).append(strTableCondition)
					.append(strWherClause).append(RMDServiceConstants.ORDER_BY_FLEET_NUMBER_ASC);
        }

        return strQuery.toString();
    }
    
    @Override
    public List <ElementVO>  getModels( String strLanguage ) throws RMDDAOException {

        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List <ElementVO> modelList = new ArrayList<ElementVO>();


        try {
            hibernateSession = getHibernateSession();
            
            strQuery.append(" Select objid , model_name , model_desc , family  from gets_rmd_model order by model_name asc ");

            final Query objModelQuery = hibernateSession.createSQLQuery(strQuery.toString());
        
            objModelQuery.setFetchSize(10);
            List<Object[]> modelResult = objModelQuery.list();
            
            ElementVO  elementVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(modelResult)) {
                //iterate.
                for( int i=0; i<modelResult.size(); i++){
                    Object[] objModel =  modelResult.get(i);
                    elementVO = new ElementVO();
                    elementVO.setId((objModel[0]).toString());
                    
                    if ( objModel[1] != null){
                        elementVO.setName((String)objModel[1]);
                    }else{
                        elementVO.setName(RMDCommonConstants.EMPTY_STRING);
                    }
                    if ( objModel[2] != null){
                        elementVO.setDescription((String)objModel[2]);
                    }else{
                        elementVO.setDescription(RMDCommonConstants.EMPTY_STRING);
                    }
                    if ( objModel[3] != null){
                        elementVO.setFamily((String)objModel[3]);
                    }else{
                        elementVO.setFamily(RMDCommonConstants.EMPTY_STRING);
                    }
                    modelList.add(elementVO);
                }

            }
            
            
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_FAULT_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ERROR_LAST_FAULT_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage ), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        
        finally {
            releaseSession(hibernateSession);
          
        }

        return modelList;
    }

    @Override
    public List<ElementVO> getModelList(String strCustomer, String strFleet,
            String[] strConfig, String strUnitNumber, String strFamily,
            String strLanguage) throws RMDDAOException {
        List<ElementVO> arlModels = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;
        LOG.debug("Start model list");
        try {
            arlModels = new ArrayList<ElementVO>();
            objHibernateSession = getHibernateSession();

            String strQry = new String();
            strQry = buildModelQuery(strCustomer, strFleet, strConfig,
                    strUnitNumber,strFamily, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer)
                    && !(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER,
                        tmpStrCustomer);
            }

            // Setting the Fleet values to the Query Parameters, if Fleet value
            // is not equal to ALL
            if ((null != strFleet)
                    && !(RMDServiceConstants.ALL.equals(strFleet))) {

                String[] tmpStrFleet = strFleet
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRFLEET,
                        tmpStrFleet);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber)
                    && !(RMDServiceConstants.ALL
                            .equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber
                        .split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER,
                        tmpStrUnitNumber);
            }
            if(!RMDCommonUtility.isNullOrEmpty(strFamily)){
                if(strFamily.equalsIgnoreCase(RMDServiceConstants.CCA)){
                    String[] tmpFamily=RMDServiceConstants.ACCCA_DCCCA.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    qryQuery.setParameterList(RMDServiceConstants.STRFAMILY, tmpFamily);
                }
                else{
                    qryQuery.setParameter(RMDServiceConstants.STRFAMILY, strFamily);
                }
            }
            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig)
                    && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGPARAM_PARAM + i,
                                configParam);
                    }
                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGVALUE1_PARAM + i,
                                configValue1);
                    }
                    if (strQry.toString().contains(
                            RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(
                                RMDCommonConstants.CONFIGVALUE2_PARAM + i,
                                configValue2);
                    }

                    i = i + 3;
                }

            }

            qryQuery.setFetchSize(100);
            List<Object[]> modelList = qryQuery.list();

            // Setting model seq. id and model Name
            if (RMDCommonUtility.isCollectionNotEmpty(modelList)) {
                for (Object[] modelInfo : modelList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility
                            .convertObjectToString(modelInfo[0]));
                    objElementVO.setName(RMDCommonUtility
                            .convertObjectToString(modelInfo[1]));
                    arlModels.add(objElementVO);
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End Model list");
        return arlModels;

    }
    
    private String buildModelQuery(String strCustomer, String strFleet,
            String[] strConfig, String strUnitNumber,String strFamily, String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();
        boolean hasOtherParams=false;
        strQuery = strQuery.append(RMDServiceConstants.MODEL_SELECT)
                .append(RMDServiceConstants.FROM)
                .append(RMDServiceConstants.MODEL_TABLE_NAME);

        if (((null != strCustomer) && (!(RMDServiceConstants.ALL_CUSTOMER
                .equals(strCustomer))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL
                        .equals(strConfig[0]))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL
                        .equals(strUnitNumber))))
                || ((null != strFleet) && (!(RMDServiceConstants.ALL
                        .equals(strFleet)))))

        {
            hasOtherParams=true;
            strTable = strTable.append(RMDServiceConstants.COMMA).append(
                    RMDServiceConstants.ASSET_TABLE_NAME);
            strTableCondition = strTableCondition.append(
                    RMDServiceConstants.WHERE).append(
                    RMDServiceConstants.ASSET_TO_MODEL_JOIN);

            if ((null != strUnitNumber)
                    && !(RMDServiceConstants.ALL
                            .equals(strUnitNumber))) {
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);
            }

            if ((null != strFleet)
                    && !(RMDServiceConstants.ALL.equals(strFleet))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(
                        RMDServiceConstants.FLEET_TABLE_NAME);
                strTableCondition = strTableCondition.append(
                        RMDServiceConstants.AND).append(
                        RMDServiceConstants.ASSET_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.FLEET_NUMBERS_IN);
            }

            if ((null != strCustomer)
                    && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {
                if ((null == strFleet)
                        || (RMDServiceConstants.ALL.equals(strFleet))) {
                    strTable = strTable.append(RMDServiceConstants.COMMA)
                            .append(RMDServiceConstants.FLEET_TABLE_NAME);
                }
                strTable = strTable.append(RMDServiceConstants.COMMA).append(
                        RMDServiceConstants.CUSTOMER_TABLE_NAME);
                strTableCondition = strTableCondition
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN)
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }
            if ((null != strConfig)
                    && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                strTable = strTable.append(RMDServiceConstants.COMMA)
                        .append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA)
                        .append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN)
                        .append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuffer buffer = new StringBuffer();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }
            strQuery = strQuery.append(strTable).append(strTableCondition)
                    .append(strWherClause);
        }
        if(hasOtherParams && !RMDCommonUtility.isNullOrEmpty(strFamily)){
            if(strQuery.toString().contains(RMDServiceConstants.WHERE)){
                strQuery = strQuery.append(RMDServiceConstants.AND).append(
                        RMDServiceConstants.FAMILY_IN);
            }
        }
        else if(!RMDCommonUtility.isNullOrEmpty(strFamily)){
            strQuery = strQuery.append(RMDServiceConstants.WHERE).append(
                    RMDServiceConstants.FAMILY_IN);
        }
        
        strQuery=strQuery.append(RMDServiceConstants.ORDER_BY_MODEL_NAME_ASC);
        
        return strQuery.toString();
    }
    
    public void configCriteraNative(String[] strConfig, StringBuffer strQry) {

        String configParam = null;
        String configFunction = null;
        String configValue1 = null;
        String configValue2 = null;

        

        int i = 0;
        for (i = 0; i < strConfig.length; i++) {
            configParam = strConfig[i];
            configFunction = strConfig[i + 1];
            configValue1 = strConfig[i + 2];
            configValue2 = strConfig[i + 3];
            if (i > 0) {
                strQry.append(" AND EXISTS (SELECT 'X' FROM GETS_RMD_MASTER_BOM WHERE");
                strQry.append(" CONFIG_ITEM=:configParam" + i);
            } else {
                strQry.append(" AND (CONFIG_ITEM=:configParam" + i);
            }

            if (configFunction.equals(RMDCommonConstants.EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION =:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >:configValue1" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >=:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <:configValue1" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <=:configValue1" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <:configValue1" + i
                        + " AND CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >=:configValue1" + i
                        + " AND CURRENT_VERSION<:configValue2" + i + ")");
            }
            if (configFunction
                    .equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >:configValue1" + i
                        + " AND CURRENT_VERSION<=:configValue2" + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >=configValue1" + i
                        + " AND CURRENT_VERSION<=:configValue2" + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <:configValue1" + i
                        + " OR CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <=:configValue1" + i
                        + " OR CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction
                    .equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <:configValue1" + i
                        + " OR CURRENT_VERSION>=:configValue2" + i + ")");
            }
            if (configFunction
                    .equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <=:configValue1" + i
                        + " OR CURRENT_VERSION>=:configValue2" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.NOT_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION !=:configValue1" + i + ")");
            }
            i = i + 3;
        }

    }
    
    
    @Override
	public  List<ElementVO> getModelList(String strfamily, String strLanguage)
            throws RMDDAOException {
        List<ElementVO> arlModels = null;
        Session objHibernateSession = null;
        
        ElementVO objElementVO = null;
        LOG.debug("Enter into getModel in RuleDAOImpl method");
        try {
            arlModels = new ArrayList <ElementVO> ();
            objHibernateSession = getHibernateSession();
       
            StringBuilder strQuery = new StringBuilder();
            strQuery = strQuery.append(RMDServiceConstants.MODEL_SELECT)
            .append(RMDServiceConstants.FROM)
            .append(RMDServiceConstants.MODEL_TABLE_NAME)
            .append(RMDServiceConstants.WHERE)
            .append(RMDServiceConstants.MOLDEL_FAMILY_NAME_EQUALS);
            
            Query qryQuery = objHibernateSession.createSQLQuery(strQuery.toString());

            if (null != strfamily) {

                qryQuery.setParameter(RMDCommonConstants.FAMILY,strfamily);
            }
            
            List<Object[]> modelList = qryQuery.list();
            
            // Setting model seq. id and model Name
            if (RMDCommonUtility.isCollectionNotEmpty(modelList)) {
                for (Object[] modelInfo : modelList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility
                            .convertObjectToString(modelInfo[0]));
                    objElementVO.setName(RMDCommonUtility
                            .convertObjectToString(modelInfo[1]));
                    arlModels.add(objElementVO);
                }
            }
            
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getModel in RuleDAOImpl method" + arlModels.size());
        return arlModels;
    }

    /**
     * This method is used for fetch the location details based on the search
     * criteria from database
     */
    @Override
	public List findLocation(FindLocationEoaServiceVO objFindLocationServiceVO)
            throws RMDDAOException {
        List<FindLocationResultEoaServiceVO> arlLocationResult = null;
        List arlResults = null;
        Session objHibernateSession = null;
        FindLocationResultEoaServiceVO objFindLocationResultServiceVO = null;
        try {
            arlResults = new ArrayList();
            arlLocationResult = new ArrayList<FindLocationResultEoaServiceVO>();
            objHibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT TBS.OBJID,TBS.NAME,TBA.ADDRESS,TBA.CITY,TBA.STATE,TBA.ZIPCODE ");
            query.append("FROM TABLE_SITE TBS, TABLE_BUS_ORG TBO, TABLE_ADDRESS TBA ");
            query.append("WHERE TBS.PRIMARY2BUS_ORG = TBO.OBJID AND TBS.CUST_PRIMADDR2ADDRESS = TBA.OBJID ");
            if (null != objFindLocationServiceVO.getCustomerId()
                    && !objFindLocationServiceVO.getCustomerId().isEmpty()) {
                query.append("AND TBO.ORG_ID =:customerId");
            }
            Query locationQry = objHibernateSession.createSQLQuery(query
                    .toString());
            if (null != objFindLocationServiceVO.getCustomerId()
                    && !objFindLocationServiceVO.getCustomerId().isEmpty()) {
                locationQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        objFindLocationServiceVO.getCustomerId());
            }
            arlResults = locationQry.list();
            if (null != arlResults && !arlResults.isEmpty()) {
                for (int i = 0; i < arlResults.size(); i++) {
                    Object[] locObj = (Object[]) arlResults.get(i);
                    objFindLocationResultServiceVO = new FindLocationResultEoaServiceVO();
                    objFindLocationResultServiceVO.setStrLocationId(RMDCommonUtility
                                    .convertObjectToString(locObj[0]));
                    objFindLocationResultServiceVO.setStrLocationName(RMDCommonUtility
                                    .convertObjectToString(locObj[1]));
                    objFindLocationResultServiceVO.setStrSiteAddress(RMDCommonUtility
                            .convertObjectToString(locObj[2]));
                    objFindLocationResultServiceVO.setStrCity(RMDCommonUtility
                                    .convertObjectToString(locObj[3]));
                    objFindLocationResultServiceVO.setStrState(RMDCommonUtility
                                    .convertObjectToString(locObj[4]));
                    objFindLocationResultServiceVO.setStrZipCode(RMDCommonUtility
                                    .convertObjectToString(locObj[5]));
                    arlLocationResult.add(objFindLocationResultServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objFindLocationServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);

            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objFindLocationServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(objHibernateSession);
        }
        return arlLocationResult;
    }
    
    /**
     * @Author : iGatte Patni
     * @param :
     * @return : List<String>
     * @throws RMDDAOException
     * @Description: Connects to DB and returns the available services for an asset
     */
    @Override
	public List<String> getAssetServices(AssetServiceVO objAssetServiceVO) throws RMDDAOException{
        Session hibernateSession = null;
        List<String> listServices = null;
        String objId = null;
        try{
            hibernateSession = getHibernateSession();
            listServices = new ArrayList<String>();
            StringBuilder vehicleObj = new StringBuilder();
            StringBuilder serviceQuery = new StringBuilder();
            StringBuilder servicesQuery1 = new StringBuilder();

            // Get vehicle obj id
            vehicleObj.append("SELECT VEH.OBJID FROM TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH," +
                    "GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO WHERE ");
            vehicleObj.append("TSP.OBJID = VEH.VEHICLE2SITE_PART  AND TSP.SERIAL_NO NOT LIKE '%BAD%' " +
                    "AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            vehicleObj.append("AND TSP.SERIAL_NO =:assetNumber AND TSP.X_VEH_HDR =:assetGrpName AND " +
                    "TBO.ORG_ID =:customerId");
            Query vehicleQry = hibernateSession.createSQLQuery(vehicleObj.toString());

            if (null != objAssetServiceVO.getAssetGroupName()
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objAssetServiceVO.getAssetGroupName())) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, objAssetServiceVO.getAssetGroupName());
            }
            if (null != objAssetServiceVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objAssetServiceVO.getStrAssetNumber())) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_NUMBER, objAssetServiceVO.getStrAssetNumber());
            }
            if (null != objAssetServiceVO.getCustomerID()
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objAssetServiceVO.getCustomerID())) {
                vehicleQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        objAssetServiceVO.getCustomerID());
            }

            vehicleQry.setFetchSize(10);
            List<Object> objIdLst = vehicleQry.list();

            if (null != objIdLst && !objIdLst.isEmpty()) {
                objId = objIdLst.get(0).toString();
            }
            //Query to get the services for an asset
            serviceQuery.append("SELECT DECODE(gsup.service_eoa,1,'EOA','NULL') || ',' || ");
            serviceQuery.append("DECODE(gsup.service_pinpoint,1,'PINPOINT','NULL')  ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_estp,1,'ESTP','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_pdp,1,'PDP','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_ered,1,'ERED','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_cell,1,'CELL','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_smb,1,'SMB','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_oil,1,'OIL','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_ldss,1,'LDSS','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.service_lococam,1,'LOCOCAM','NULL') ");
            serviceQuery.append("|| ',' || DECODE(gsup.SERVICE_TO,1,'TO','NULL') ");
            serviceQuery.append("FROM gets_omi_mdsc_sup_def gsup ");
            serviceQuery.append("WHERE  gsup.mdsc_sup_def2vehicle =:vehicleObjid AND ");
            serviceQuery.append("gsup.active_config = 'Y' AND gsup.objid = (SELECT MAX(gsup1.objid) ");
            serviceQuery.append("FROM   gets_omi_mdsc_sup_def gsup1 WHERE  gsup1.mdsc_sup_def2vehicle =:vehicleObjid AND ");
            serviceQuery.append("gsup1.active_config = 'Y') ");

            Query serviceQry = hibernateSession.createSQLQuery(serviceQuery.toString());

            if (null != objId
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objId)) {
                serviceQry.setParameter(RMDCommonConstants.VEHICLE_OBJID, objId);
            }

            serviceQry.setFetchSize(10);
            List<Object> serviceObjLst = serviceQry.list();
            if(null != serviceObjLst && !serviceObjLst.isEmpty()){
                Object serviceObject = serviceObjLst.get(0);
                listServices = RMDCommonUtility.cnvrtStringToList(serviceObject);
            }
            
            servicesQuery1.append("SELECT DISTINCT application_name ");
            servicesQuery1.append("FROM   gets_mcs.mcs_asset_application ass_app, ");
            servicesQuery1.append("       gets_mcs.mcs_application app, ");
            servicesQuery1.append("       gets_rmd_vehcfg vehcfg, ");
            servicesQuery1.append("       gets_rmd_master_bom mbom, ");
            servicesQuery1.append("       gets_rmd_vehicle veh ");
            servicesQuery1.append("WHERE  vehcfg2master_bom = mbom.objid ");
            servicesQuery1.append("       AND veh.objid = :vehicleObjId ");
            servicesQuery1.append("       AND veh.objid = vehcfg.veh_cfg2vehicle ");
            servicesQuery1.append("       AND mbom.bom_status = 'Y' ");
            servicesQuery1.append("       AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg ");
            servicesQuery1.append("       AND ( ( mbom.config_item = 'CMU' ");
            servicesQuery1.append("               AND vehcfg.current_version = '2' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'HPEAP' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'LCV' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'LIG' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ) ");
            servicesQuery1.append("       AND ass_app.asset_objid = veh.vehicle2asset_objid ");
            servicesQuery1.append("       AND app.objid = ass_app.application_objid ");
            
            Query servicesHQuery = hibernateSession.createSQLQuery(servicesQuery1.toString());
            servicesHQuery.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID ,objId);
            List<String> servicesAddedList = servicesHQuery.list();
            if (null != servicesAddedList && !servicesAddedList.isEmpty()) {
            	listServices.addAll(servicesAddedList);
            }
            

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_SERVICES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_SERVICES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return listServices;
      }
    /*
     * (non-Javadoc)
     * 
     * @see com.ge.trans.rmd.services.cases.dao.intf.AddNotesDAOIntf
     * #saveNotes(com
     * .ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to add new notes into database
     */
    @Override
	public String saveNotes(AddNotesEoaServiceVO notesServiceVO)
    throws RMDDAOException {
        Session objSession = null;
        String strCustomer = RMDCommonConstants.EMPTY_STRING;
        try {
            
           
            String strFromPage = notesServiceVO.getStrFromPage();
            String strAssetFrom = notesServiceVO.getFromAsset();
            String strNotes = EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(notesServiceVO.getNotes()));
            String strUserName = notesServiceVO.getStrUserName();
            String strAsstGrp = notesServiceVO.getAssetGrpName();

            StringBuilder updateQry = new StringBuilder();
            StringBuilder vehicleObj = new StringBuilder();
            StringBuilder insertQry = new StringBuilder();

            objSession = getHibernateSession(strUserName);
            String eoaUserId = getEoaUserName(objSession,notesServiceVO.getStrUserName());
            if(null == eoaUserId || eoaUserId.isEmpty()){
                eoaUserId = notesServiceVO.getStrUserName();
            }
            notesServiceVO.setEoaUserId(eoaUserId);
            // Get vehicle obj id
            vehicleObj
            .append("SELECT VEH.OBJID FROM TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO WHERE ");
            vehicleObj
            .append("TSP.OBJID = VEH.VEHICLE2SITE_PART  AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            vehicleObj
            .append("AND TSP.SERIAL_NO =:assetNumber AND TSP.X_VEH_HDR =:assetGrpName AND TBO.ORG_ID =:customerId");

            Query vehicleQry = objSession.createSQLQuery(vehicleObj.toString());

            if (null != strAsstGrp
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(strAsstGrp)) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
            }
            if (null != strAssetFrom
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(strAssetFrom)) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
            }
            if (null != notesServiceVO.getCustomer()
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(notesServiceVO.getCustomer())) {
                vehicleQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        notesServiceVO.getCustomer());
            }

            List objIdLst = vehicleQry.list();
            String objId = null;

            if (null != objIdLst && !objIdLst.isEmpty()) {
                objId = objIdLst.get(0).toString();
            }

            // Insert notes for fleet view basic

            if (objSession != null) {
                if (RMDServiceConstants.NOTESLOCO.equalsIgnoreCase(strFromPage)) {
                    updateQry
                    .append("UPDATE GETS_RMD_DD_QUEUE SET ACTIVE = 0, DIAGNOSTICS='Inactive',LAST_UPDATED_BY =:eoaUserId,LAST_UPDATED_DATE = SYSDATE ");
                    updateQry
                    .append(" WHERE ROAD_NUMBER_HEADER =:assetGrpName AND ROAD_NUMBER =:assetNumber  AND ACTIVE =1");
                    Query updQry = objSession.createSQLQuery(updateQry
                            .toString());
                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(notesServiceVO
                                    .getEoaUserId())) {
                        updQry.setParameter(RMDCommonConstants.EOA_USERID,
                                notesServiceVO.getEoaUserId());
                    }
                    if (null != strAsstGrp
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strAsstGrp)) {
                        updQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
                    }
                    if (null != strAssetFrom
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strAssetFrom)) {
                        updQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
                    }
                    updQry.executeUpdate();

                    insertQry
                    .append("INSERT INTO GETS_RMD_DD_QUEUE (OBJID, CREATION_DATE,CREATED_BY,LAST_UPDATED_DATE, LAST_UPDATED_BY, ");
                    insertQry
                    .append("ROAD_NUMBER_HEADER, ROAD_NUMBER, CREATION_TIME, DIAGNOSTICS,NOTES, ACTIVE, CASE_ID,DD_QUEUE2VEHICLE) VALUES ");
                    insertQry
                    .append("(GETS_RMD_DD_QUEUE_SEQ.NEXTVAL, SYSDATE,:eoaUserId,SYSDATE,:eoaUserId, :assetGrpName,:assetNumber, SYSDATE,");
                    insertQry
                    .append("'No Request', :notes, 1, NULL,:vehicleObjid)");

                    Query intQry = objSession.createSQLQuery(insertQry
                            .toString());

                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(notesServiceVO
                                    .getEoaUserId())) {
                        intQry.setParameter(RMDCommonConstants.EOAUSER_ID,
                                notesServiceVO.getEoaUserId());
                    }

                    if (null != strAsstGrp
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strAsstGrp)) {
                        intQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
                    }

                    if (null != strAssetFrom
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strAssetFrom)) {
                        intQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
                    }

                    if (null != strNotes
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strNotes)) {
                        intQry.setParameter(RMDCommonConstants.NOTES, strNotes);
                    }

                    if (null != objId
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objId)) {
                        intQry.setParameter(RMDCommonConstants.VEHICLE_OBJID, objId);
                    }

                    intQry.executeUpdate();
                }

                // Insert notes when from page is 'Menu' -Asset overview
                if (RMDServiceConstants.NOTESMENU.equalsIgnoreCase(strFromPage)) {
                	StringBuilder insertWhenFromPageMenu = new StringBuilder();
                    insertWhenFromPageMenu
                    .append("INSERT INTO GETS_SD_GENERIC_NOTES_LOG(OBJID, LAST_UPDATE_DATE,");
                    insertWhenFromPageMenu
                    .append("LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,DESCRIPTION,STICKY,GENERIC2VEHICLE) VALUES");
                    insertWhenFromPageMenu
                    .append("(GETS_SD.GETS_SD_GENERIC_NOTES_LOG_SEQ.NEXTVAL, SYSDATE,:eoaUserId,  SYSDATE,:eoaUserId,:notes,:sticky,:vehicleObjid)");

                    Query intWhenFromPageMenuQry = objSession
                    .createSQLQuery(insertWhenFromPageMenu.toString());

                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(notesServiceVO
                                    .getEoaUserId())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.EOA_USERID,
                                notesServiceVO.getEoaUserId());
                    }

                    if (null != strNotes
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(strNotes)) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.NOTES, strNotes);
                    }
                    if ((RMDCommonConstants.NO).equalsIgnoreCase(notesServiceVO.getSticky())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.STICKY, RMDCommonConstants.LETTER_N);
                    } else if ((RMDCommonConstants.YES).equalsIgnoreCase(notesServiceVO
                            .getSticky())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.STICKY, RMDCommonConstants.LETTER_Y);
                    }
                    if (null != objId
                            && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objId)) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.VEHICLE_OBJID,
                                objId);
                    }

                    intWhenFromPageMenuQry.executeUpdate();
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            notesServiceVO.getStrLanguage()), ex,
                            RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
        	LOG.error(e);
            throw new RMDDAOException(e.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            notesServiceVO.setStrStatus(RMDCommonConstants.FAIL);
            String errorCode = RMDCommonUtility
            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            notesServiceVO.getStrLanguage()), e,
                            RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strCustomer;
    }

    /* (non-Javadoc)
     * @see com.ge.trans.eoa.services.asset.dao.intf.AssetEoaDAOIntf#getMapLastRefreshTime(java.lang.String)
     */
    @Override
    public List<GetSysLookupVO> getMapLastRefreshTime(String lookupString)
            throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            final String queryString = "select to_char(LAST_RUN_DATE, 'MM/dd/yyyy HH24:mi:ss') from GETS_RMD_JOB_SCHDL_STATUS WHERE JOB_NM = 'ASSET_SNAPSHOT_TABLE' ";
            Query query = session.createSQLQuery(queryString);
            // Get the list based on the search criteria(if any)
            query.setFetchSize(10);
            List<Object> lookupList = query.list();
            List<GetSysLookupVO> searchResults = null;

            if (lookupList != null && !lookupList.isEmpty()) {
                GetSysLookupVO objSysLookupVO = null;
                searchResults = new ArrayList<GetSysLookupVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter
                        .hasNext();) {
                    objSysLookupVO = new GetSysLookupVO();
                    
                    DateFormat formatter = new SimpleDateFormat(
                            RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                    Date lastUpdatedDate = (Date) formatter
                            .parse(RMDCommonUtility
                                    .convertObjectToString(iter.next()));
                    objSysLookupVO.setLastUpdatedDate(lastUpdatedDate);
                    searchResults.add(objSysLookupVO);
                }
            }

            return searchResults;
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LEGENDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LEGENDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        
    }
    
    @Override
	public List<FaultCodeDetailsServiceVO> getFaultCode(
            String controllerCfg,String faultCode,String language) throws RMDDAOException{
        
            


            Session hibernateSession = null;
            List<Object[]> arlFaultCodeDetails = null;
            FaultCodeDetailsServiceVO objFaultCodeDetailsServiceVO = null;
            List<FaultCodeDetailsServiceVO> faultCodeDetailsLst = new ArrayList<FaultCodeDetailsServiceVO>();
            try {
                StringBuilder strQry = new StringBuilder();
                hibernateSession = getHibernateSession();
                strQry.append("SELECT DISTINCT FAULT_CODE,FAULT_DESC FROM GETS_RMD_FAULT_CODES FAULTCODE,GETS_RMD_CTL_CFG CFG where FAULTCODE.CONTROLLER_SOURCE_ID=CFG.VEHICLE_NO_MP_SOURCE AND CFG.CONTROLLER_CFG=:controllerCfg");
                if(null!=faultCode&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(faultCode)){
                    strQry.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
                }
                strQry.append(" ORDER BY FAULT_CODE,FAULT_DESC ASC");
                Query qry = hibernateSession.createSQLQuery(strQry.toString());
                qry.setParameter(RMDServiceConstants.CONTROLLER_CONFIG,controllerCfg);
                if(null!=faultCode&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(faultCode)){
                qry.setParameter(RMDServiceConstants.FAULT_CODE,"%" + faultCode + "%");
                }
                qry.setFetchSize(500);
                arlFaultCodeDetails = qry.list();
                if (null != arlFaultCodeDetails && arlFaultCodeDetails.size() > 0) {
                    for (Object[] obj : arlFaultCodeDetails) {
                        objFaultCodeDetailsServiceVO = new FaultCodeDetailsServiceVO();
                        String strFaultCode=RMDCommonUtility
                        .convertObjectToString(obj[0]);
                        String strFaultDesc=RMDCommonUtility
                        .convertObjectToString(obj[1]);
                        String faultDetail=strFaultCode+RMDCommonConstants.HYPHEN_WITHSPACE+strFaultDesc;
                        objFaultCodeDetailsServiceVO.setFaultCode(strFaultCode);
                        objFaultCodeDetailsServiceVO.setFaultDesc(strFaultDesc);        
                        objFaultCodeDetailsServiceVO.setFaultDetail(faultDetail);
                        faultCodeDetailsLst.add(objFaultCodeDetailsServiceVO);                      

                    }

                }

            } catch (RMDDAOConnectionException ex) {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                        RMDCommonConstants.FATAL_ERROR);
            } catch (Exception e) {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } finally {
                releaseSession(hibernateSession);
            }

            return faultCodeDetailsLst;
        
            
        
            
        
        
    }
    
    /**
     * @param : AssetEoaServiceVO
     * @return : List<LifeStatisticsEoaVO>
     * @throws RMDDAOException
     * @Description: Connects to the DB and returns list of life statistics data
     */
    @Override
    public List<LifeStatisticsEoaVO> getLifeStatisticsData(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException {
        List<LifeStatisticsEoaVO> resultAssetList = new ArrayList<LifeStatisticsEoaVO>();
        Session hibernateSession = null;
        Query assetQuery = null;
        List<String> assetObjidlist = null;
        StringBuilder strQry = new StringBuilder();
        List<Object[]> queryResultList = null;
        LifeStatisticsEoaVO newLifeStatisticsEoaVO = null;
        String controllerConfigQry = null;
        try {
            hibernateSession = getHibernateSession();
            assetObjidlist = (List<String>) getVehicleObjId(
                    objAssetServiceVO.getStrAssetNumber(),
                    objAssetServiceVO.getCustomerID(),
                    objAssetServiceVO.getAssetGroupName());
            if (null != assetObjidlist && !assetObjidlist.isEmpty()) {
                controllerConfigQry = "SELECT CONTROLLER_SOURCE_ID FROM GETS_RMD_CONTROLLER WHERE CONTROLLER_NAME =:controller_cfg ";
                Query contrlConfgQry = hibernateSession
                        .createSQLQuery(controllerConfigQry.toString());
                contrlConfgQry.setParameter(RMDCommonConstants.CONTROLLER_CFG,
                        objAssetServiceVO.getControllerConfig());
                String controllerConfig = RMDCommonUtility
                        .convertObjectToString(contrlConfgQry.uniqueResult());
                strQry.append(
                        "SELECT  ATTR.ATTRIBUTE_CD,ATTR.ATTRIBUTE_DESC_TXT,ATTR.SORT_ORDER, DTL.STATISTIC_ATTRIBUTE_VAL, HRD.STATISTIC_GATHERING_DATE, ATTR.ATTRIBUTE_UOM  ")
                        .append("FROM GETS_TOOLS.GETS_TOOL_STATISTIC_HDR HRD, GETS_TOOLS.GETS_TOOL_STATISTIC_DTL DTL, GETS_TOOLS.GETS_TOOL_STATISTIC_ATTRIBUTE ATTR ")
                        .append("WHERE ATTR.GETS_TOOL_STATISTIC_ATTR_ID = DTL.GETS_TOOL_STATISTIC_ATTR_ID ")
                        .append("AND HRD.GETS_TOOL_STATISTIC_HDR_ID = DTL.GETS_TOOL_STATISTIC_HDR_ID AND ATTR.CONTROLLER_SOURCE_ID =:controller_cfg AND HRD.STAT2VEHICLE=:roadNumber ");
                if(objAssetServiceVO.isHideL3Fault()){
                strQry.append("AND ATTR.ACCESS_LEVEL < 3 ");
                }
                if (null != objAssetServiceVO.getFromDate()
                        && !objAssetServiceVO.getFromDate().isEmpty()) {
                    strQry.append("AND HRD.STATISTIC_GATHERING_DATE BETWEEN TO_DATE(:fromDate,'MM/DD/YYYY HH24:MI:SS')");
                }
                if (null != objAssetServiceVO.getToDate()
                        && !objAssetServiceVO.getToDate().isEmpty()) {
                    strQry.append(" AND TO_DATE(:toDate,'MM/DD/YYYY HH24:MI:SS') ");
                }
                strQry.append(" ORDER BY ATTR.SORT_ORDER ASC");
                assetQuery = hibernateSession.createSQLQuery(strQry.toString());
                if (null != controllerConfig && !controllerConfig.isEmpty()) {
                    assetQuery.setParameter(RMDCommonConstants.CONTROLLER_CFG,
                            controllerConfig);
                }
                if (null != assetObjidlist && !assetObjidlist.isEmpty()) {
                    assetQuery.setParameter(RMDCommonConstants.ROAD_NUMBER,
                            assetObjidlist.get(0));
                }
                if (null != objAssetServiceVO.getFromDate()
                        && !objAssetServiceVO.getFromDate().isEmpty()) {
                    assetQuery.setParameter(RMDCommonConstants.FROM_DATE,
                            objAssetServiceVO.getFromDate());
                }
                if (null != objAssetServiceVO.getToDate()
                        && !objAssetServiceVO.getToDate().isEmpty()) {
                    assetQuery.setParameter(RMDCommonConstants.TO_DATE,
                            objAssetServiceVO.getToDate());
                }
                assetQuery.setFetchSize(500);
                queryResultList = (ArrayList) assetQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(queryResultList)) {

                    for (Object[] currentAsset : queryResultList) {
                        newLifeStatisticsEoaVO = new LifeStatisticsEoaVO();
                        newLifeStatisticsEoaVO
                                .setAttributeName(RMDCommonUtility
                                        .convertObjectToString(currentAsset[0]));
                        newLifeStatisticsEoaVO
                        .setAttributeToolTip(RMDCommonUtility
                                .convertObjectToString(currentAsset[1]));
                        newLifeStatisticsEoaVO.setSortOrder(RMDCommonUtility
                                .convertObjectToString(currentAsset[2]));
                        if (null != currentAsset[3]) {
                            newLifeStatisticsEoaVO
                                    .setAttributeValue(RMDCommonUtility
                                            .convertObjectToString(currentAsset[3]));
                        } else {
                            newLifeStatisticsEoaVO
                                    .setAttributeValue(RMDCommonConstants.EMPTY_STRING);
                        }
                        newLifeStatisticsEoaVO
                                .setStatisticGatheringDate((Date) currentAsset[4]);
                        newLifeStatisticsEoaVO.setUnit(RMDCommonUtility
                                .convertObjectToString(currentAsset[5]));
                        resultAssetList.add(newLifeStatisticsEoaVO);
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ASSET_LIFE_STAT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return resultAssetList;
    }
    
    @Override
	public List<AssetServiceVO> getAssetGroups(
            final AssetServiceVO objAssetServiceVO) {
        LOG.debug("Begin getAssetGroups method of AssetDAOImpl");
        List<AssetServiceVO> AssetDetailsLst = new ArrayList<AssetServiceVO>();
        Session hibernateSession = null;
        List arlAssetGroups = null;
        AssetServiceVO localobjAssetServiceVO = objAssetServiceVO;
        try {
            arlAssetGroups = new ArrayList<Object>();
            StringBuilder assetLocatorQry = new StringBuilder();
            assetLocatorQry
                    .append("SELECT VEH_HDR_CUST FROM GETS_RMD_VEH_HDR WHERE VEH_HDR2BUSORG=(SELECT OBJID FROM TABLE_BUS_ORG WHERE ORG_ID=:customerId)");
            hibernateSession = getHibernateSession();
            if (hibernateSession != null) {
                Query hibernateQuery = hibernateSession
                        .createSQLQuery(assetLocatorQry.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        localobjAssetServiceVO.getCustomerID());
                arlAssetGroups = hibernateQuery.list();
                if (arlAssetGroups != null && !arlAssetGroups.isEmpty()) {
                    for (final Iterator<Object> lookValueIter = arlAssetGroups
                            .iterator(); lookValueIter.hasNext();) {
                        final String assetGroupRec = (String) lookValueIter
                                .next();
                        localobjAssetServiceVO = new AssetServiceVO();
                        localobjAssetServiceVO.setAssetGroupName(assetGroupRec);
                        AssetDetailsLst.add(localobjAssetServiceVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objAssetServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("Ends getAssetGroups method of AssetDAOImpl");
        return AssetDetailsLst;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.rmd.services.asset.dao.intf.AssetDAOIntf#getAssetsForRole
     * (com.ge.trans.rmd.services.asset.service.valueobjects.RoleAssetsVO) This
     * method is used get List of Assets for a particular role and the customer
     * id
     */
    @Override
	public RoleAssetsVO getProductsForRole(final RoleAssetsVO objRoleAssetsVO) {
        LOG.debug("Begin getAssetsForRole method of AssetDAOImpl");
        RoleAssetsVO objRoleAsst = null;
        Session hibernateSession = null;
        List<GetUsrUserProductRoleHVO> arlProductRoles = null;
        try {
            hibernateSession = getHibernateSession();
            if (hibernateSession != null) {
                Criteria criteria = hibernateSession
                        .createCriteria(GetUsrUserProductRoleHVO.class)
                        .setFetchMode("getUsrRoles", FetchMode.JOIN)
                        .setFetchMode("getUsrProduct", FetchMode.JOIN)
                        .createAlias("getUsrRoles", "roles")
                        ;               
                // Passing role name to criteria for getting product assets
                // configured for a role
                if (null != objRoleAssetsVO.getRoleName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objRoleAssetsVO
                                        .getRoleName())) {
                    criteria.add(Restrictions.eq(
                            "roles.roleName",
                            objRoleAssetsVO.getRoleName()));
                }

                arlProductRoles = (ArrayList<GetUsrUserProductRoleHVO>) criteria.list();

                
                LinkedHashMap<String, String> prdIdMap = new LinkedHashMap<String, String>();
                if (RMDCommonUtility.isCollectionNotEmpty(arlProductRoles)) {
                    objRoleAsst = new RoleAssetsVO();
                    for (GetUsrUserProductRoleHVO productRole : arlProductRoles) {
                        prdIdMap.put(productRole.getGetUsrProduct().getGetUsrProdSeqId().toString(),productRole.getGetUsrProduct().getProductCode());
                    }
                    objRoleAsst.setPrdIdMap(prdIdMap);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRoleAssetsVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRoleAssetsVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("Ends getAssetsForRole method of AssetDAOImpl");
        return objRoleAsst;
    }
    
    @Override
	public List<FleetVO> getAllFleets() {
        LOG.debug("Begin getAllFleets method of AssetDAOImpl");
        FleetVO objfleet = null;
        Session hibernateSession = null;
        List<FleetVO> arlFleetsVo = null;
        List<Object[]> objFleetsVo = null;
        StringBuilder fleetQuery = new StringBuilder();
        try {
            hibernateSession = getHibernateSession();
            
            fleetQuery.append("SELECT DISTINCT FLEET.OBJID, V.FLEET_NUMBER_V,  V.ORG_ID ");
            fleetQuery.append("FROM GETS_MCS.MCS_ASSET A, GETS_MCS.MCS_LOOKUP_VALUE ST, GETS_MCS.MCS_APPLICATION SERVICE, GETS_RMD.GETS_RMD_FLEET FLEET, ");
            fleetQuery.append("GETS_MCS.MCS_ASSET_APPLICATION APP, GETS_RMD_CUST_RNH_RN_V V WHERE V.FLEET_NUMBER_V = FLEET.FLEET_NUMBER ");
            fleetQuery.append("AND V.BUS_ORG_OBJID = FLEET.FLEET2BUS_ORG AND STATUS = ST.OBJID ");
            fleetQuery.append("AND APP.APPLICATION_OBJID = SERVICE.OBJID AND APP.ASSET_OBJID = A.OBJID AND V.VEHICLE2ASSET_OBJID = A.OBJID AND APPLICATION_NAME = 'ATS' ");
            fleetQuery.append("ORDER BY V.ORG_ID, V.FLEET_NUMBER_V ");

            
            if (null != hibernateSession) {
                Query fleetHqry = hibernateSession
                        .createSQLQuery(fleetQuery.toString());
                
                fleetHqry.setFetchSize(2000);
                objFleetsVo = (ArrayList) fleetHqry.list();

                if (RMDCommonUtility.isCollectionNotEmpty(objFleetsVo)) {
                    arlFleetsVo = new ArrayList<FleetVO>();
                    for (Object[] obj : objFleetsVo) {
                        objfleet = new FleetVO();
                        
                        //FLEET.OBJID
                        if( RMDCommonUtility.convertObjectToString(obj[0]) != null && !RMDCommonUtility.convertObjectToString(obj[0]).trim().equals("")){                           
                            objfleet.setFleetId(RMDCommonUtility.convertObjectToString(obj[0]));                            
                        }
                        //FLEET.FLEET_NUMBER
                        if( RMDCommonUtility.convertObjectToString(obj[1]) != null && !RMDCommonUtility.convertObjectToString(obj[1]).trim().equals("")){                           
                            objfleet.setFleetNo(RMDCommonUtility.convertObjectToString(obj[1]));                            
                        }
                        //CUSTOMER.ORG_ID
                        if( RMDCommonUtility.convertObjectToString(obj[2]) != null && !RMDCommonUtility.convertObjectToString(obj[2]).trim().equals("")){                           
                            objfleet.setCustomerId(RMDCommonUtility.convertObjectToString(obj[2]));                         
                        }
                        arlFleetsVo.add(objfleet);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_FLEETS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objfleet.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_FLEETS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objfleet.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("Ends getAllFleets method of AssetDAOImpl");
        return arlFleetsVo;
    }
    
    @Override
	public List<RegionVO> getAllRegions() {
        LOG.debug("Begin getAllRegions method of AssetDAOImpl");
        RegionVO objRegion = null;
        Session hibernateSession = null;
        List<RegionVO> arlRegionsVo = null;
        List<Object[]> objRegionsVo = null;
        StringBuilder regionQuery = new StringBuilder();
        try {
            hibernateSession = getHibernateSession();
            regionQuery.append(" SELECT MIN(REGIONTBL.OBJID), REGIONTBL.REGION, TBO.ORG_ID FROM GETS_RMD.GETS_RMD_REGION REGIONTBL, ");
            regionQuery.append(" TABLE_BUS_ORG TBO  WHERE REGIONTBL.ACTIVE = 'Y' AND REGIONTBL.ATS_REGION2BUS_ORG  =  TBO.OBJID " );
            regionQuery.append(" GROUP BY REGIONTBL.REGION, TBO.ORG_ID ORDER BY TBO.ORG_ID, REGIONTBL.REGION ");  
        
            if (null != hibernateSession) {
                Query regionHqry = hibernateSession
                        .createSQLQuery(regionQuery.toString());
                
                regionHqry.setFetchSize(2000);
                objRegionsVo = (ArrayList) regionHqry.list();

                if (RMDCommonUtility.isCollectionNotEmpty(objRegionsVo)) {
                    arlRegionsVo = new ArrayList<RegionVO>();
                    for (Object[] obj : objRegionsVo) {
                        objRegion = new RegionVO();
                        
                        //REGIONTBL.OBJID
                        if( RMDCommonUtility.convertObjectToString(obj[0]) != null && !RMDCommonUtility.convertObjectToString(obj[0]).trim().equals("")){                           
                            objRegion.setRegionId(RMDCommonUtility.convertObjectToString(obj[0]));                          
                        }
                        //REGIONTBL.REGION
                        if( RMDCommonUtility.convertObjectToString(obj[1]) != null && !RMDCommonUtility.convertObjectToString(obj[1]).trim().equals("")){                           
                            objRegion.setRegionName(RMDCommonUtility.convertObjectToString(obj[1]));                            
                        }
                        //TBO.ORG_ID
                        if( RMDCommonUtility.convertObjectToString(obj[2]) != null && !RMDCommonUtility.convertObjectToString(obj[2]).trim().equals("")){                           
                            objRegion.setCustomerId(RMDCommonUtility.convertObjectToString(obj[2]));                            
                        }
                        arlRegionsVo.add(objRegion);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_REGIONS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRegion.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_REGIONS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRegion.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("Ends getAllRegions method of AssetDAOImpl");
        return arlRegionsVo;
    }
    
    
    @Override
	public List<RegionVO> getAllSubDivisions(String region) throws RMDDAOException {
        LOG.debug("Begin getAllRegions method of AssetDAOImpl");
        RegionVO objRegion = null;
        Session hibernateSession = null;
        List<RegionVO> arlRegionsVo = null;
        List<Object[]> objRegionsVo = null;
        StringBuilder regionQuery = new StringBuilder();
        try {
            hibernateSession = getHibernateSession();
            regionQuery.append("SELECT R.REGION,S.SUB_REGION,S.OBJID FROM ");
            regionQuery.append("GETS_RMD_REGION R LEFT OUTER JOIN GETS_RMD.GETS_RMD_PP_SUB_REGION S ");
            regionQuery.append("ON S.SUB_REGION2REGION=R.OBJID WHERE R.REGION IS NOT NULL AND R.ACTIVE='Y' AND R.OBJID=:region");  
        
            if (null != hibernateSession) {
                Query regionHqry = hibernateSession
                        .createSQLQuery(regionQuery.toString());
                regionHqry.setParameter(RMDCommonConstants.REGION,
                        region);
                regionHqry.setFetchSize(2000);
                objRegionsVo = (ArrayList) regionHqry.list();

                if (RMDCommonUtility.isCollectionNotEmpty(objRegionsVo)) {
                    arlRegionsVo = new ArrayList<RegionVO>();
                    for (Object[] obj : objRegionsVo) {
                        objRegion = new RegionVO();
                        
                        //R.REGION
                        if( RMDCommonUtility.convertObjectToString(obj[0]) != null && !RMDCommonUtility.convertObjectToString(obj[0]).trim().equals("")){                           
                            objRegion.setRegionName(RMDCommonUtility.convertObjectToString(obj[0]));                            
                        }
                        //S.SUB_REGION
                        if( RMDCommonUtility.convertObjectToString(obj[1]) != null && !RMDCommonUtility.convertObjectToString(obj[1]).trim().equals("")){                           
                            objRegion.setSubDivisionName(RMDCommonUtility.convertObjectToString(obj[1]));                           
                        }
                        //S.OBJID
                        if( RMDCommonUtility.convertObjectToString(obj[2]) != null && !RMDCommonUtility.convertObjectToString(obj[2]).trim().equals("")){                           
                            objRegion.setSubDivisionId(RMDCommonUtility.convertObjectToString(obj[2]));                         
                        }
                        arlRegionsVo.add(objRegion);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_REGIONS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRegion.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_REGIONS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objRegion.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("Ends getAllRegions method of AssetDAOImpl");
        return arlRegionsVo;
    }

    /**
     * @Author:
     * @param:String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used check whether PP Asset History Button
     *               Has to Disable or Enable.
     */
    @Override
    public String displayPPAssetHistoryBtn(String vehicleObjId)
            throws RMDDAOException {
        String result = RMDCommonConstants.STRING_NULL;
        Session session = null;
        Query hibernateQuery = null;
        Query hibernateQueryForATSCustomers = null;
        StringBuilder strQery = new StringBuilder();
        int atsEnabledCount = 0;
        try {
              session = getHibernateSession();
              
               
              strQery.append("SELECT 1  ");
              strQery.append("FROM   gets_mcs.mcs_asset ma, ");
              strQery.append("       gets_mcs.mcs_lookup_value mlv, ");
              strQery.append("       gets_mcs.mcs_application mapp, ");
              strQery.append("       gets_mcs.mcs_asset_application app, ");
              strQery.append("       gets_rmd_cust_rnh_rn_v grcrrv ");
              strQery.append("WHERE  ma.status = mlv.objid ");
              strQery.append("       AND app.application_objid = mapp.objid ");
              strQery.append("       AND app.asset_objid = ma.objid ");
              strQery.append("       AND grcrrv.vehicle2asset_objid = ma.objid ");
              strQery.append("       AND mapp.application_name = 'ATS' ");
              strQery.append("       AND grcrrv.vehicle_objid = :vehicleObjId ");
              
              hibernateQuery = session.createSQLQuery(strQery.toString());
              hibernateQuery.setParameter(RMDServiceConstants.VEHICLE_OBJID,vehicleObjId);
              List<Object> resultSet = hibernateQuery.list();
              
              if (RMDCommonUtility.isCollectionNotEmpty(resultSet)) {
                     atsEnabledCount = RMDCommonUtility.convertObjectToInt(resultSet.get(0));
              }
              
              if (atsEnabledCount > 0) {
                     
                  StringBuilder  varname1 = new StringBuilder();
                       varname1.append("SELECT 'ATS' ");
                       varname1.append("FROM   table_site_part tsp ");
                       varname1.append("       JOIN gets_rmd_vehicle veh ");
                       varname1.append("         ON tsp.objid = veh.vehicle2site_part ");
                       varname1.append("       JOIN gets_rmd_vehcfg cfg ");
                       varname1.append("         ON veh.objid = cfg.veh_cfg2vehicle ");
                       varname1.append("       JOIN gets_rmd_master_bom bom ");
                       varname1.append("         ON cfg.vehcfg2master_bom = bom.objid ");
                       varname1.append("WHERE  ( ( bom.config_item = 'CMU' ");
                       varname1.append("           AND cfg.current_version = '2' ) ");
                       varname1.append("          OR ( bom.config_item = 'HPEAP' ");
                       varname1.append("               AND cfg.current_version = '1' ) ) ");
                       varname1.append("       AND veh.objid = :vehicleObjId ");


                     
                     hibernateQueryForATSCustomers = session.createSQLQuery(varname1.toString());
                     hibernateQueryForATSCustomers.setParameter(RMDServiceConstants.VEHICLE_OBJID,vehicleObjId);
                     List<Object> resultSetATSCustomer = hibernateQueryForATSCustomers.list();
                     if (null != resultSetATSCustomer && RMDCommonUtility.isCollectionNotEmpty(resultSetATSCustomer)) {
                            result =  RMDCommonUtility.convertObjectToString(resultSetATSCustomer.get(0));
                     }   
              } 
              
        } catch (Exception e) {
              String errorCode = RMDCommonUtility
                            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DISPLAY_PPASSETHST_BTN);
              throw new RMDDAOException(errorCode, new String[] {},
                            RMDCommonUtility.getMessage(errorCode, new String[] {},
                                          RMDCommonConstants.ENGLISH_LANGUAGE), e,
                            RMDCommonConstants.MINOR_ERROR);
        } finally {
              releaseSession(session);
        }
        return result;
    }
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getCustNameRNH() throws RMDDAOException {
        LOG.debug("Begin method of getCustNameRNH");
        List<AssetServiceVO> lstAssetDetails = new ArrayList<AssetServiceVO>();
        Session objSession = null;
        List<Object[]> objAsset = null;
        AssetServiceVO localAssetServicevo = null;
        try {
            objSession = getHibernateSession();
            StringBuilder assetQry = new StringBuilder();
            //assetQry.append("SELECT DISTINCT VEHICLE_HDR,CUST_NAME FROM GETS_RMD_CUST_RNH_RN_V ORDER BY LOWER(CUST_NAME) ");
            /*Added by Murali to specify RNH drop down order as in EOA */
            assetQry.append("SELECT a.veh_hdr,b.name FROM gets_rmd_veh_hdr a,table_bus_org b WHERE a.veh_hdr2busorg = b.objid ");
            assetQry.append("AND b.status = 0 ORDER BY LOWER(b.name),DECODE(b.org_id||a.veh_hdr_cust,'BNSFBNSF','1','CSXCSX','1','CNCN','1','CNIC','2','FMXFXE','1',");
            assetQry.append("'KCSKCS','1','MHFMHFX','1','NSNS','1','QTRQTR','1','TFMTFM','1','CPCP',1,'CPCEFX',2,'UPUP',1,'UPSP',2,'UPCNW',3,'MHFGEST',5,4),a.veh_hdr");
            /*End of RNH drop down order*/
            if (null != objSession) {
                Query assetHqry = objSession
                        .createSQLQuery(assetQry.toString());
                assetHqry.setFetchSize(100);
                objAsset = (ArrayList) assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(objAsset)) {
                    for (Object[] obj : objAsset) {
                        localAssetServicevo = new AssetServiceVO();
                        localAssetServicevo.setAssetGroupName(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        localAssetServicevo.setCustomerName(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        lstAssetDetails.add(localAssetServicevo);
                    }
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUTSNAME_RNH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends method of getCustNameRNH");
        return lstAssetDetails;
    }
    
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Customer Name and Road
     *               Number Headers.
     */
    @Override
    public List<AssetServiceVO> getRoadNumbers(AssetServiceVO objAssetServiceVO)
            throws RMDDAOException {
        LOG.debug("Begin method of getRoadNumbers");
        List<AssetServiceVO> lstAssetDetails = new ArrayList<AssetServiceVO>();
        Session objSession = null;
        List<String> queryResultList = null;
        AssetServiceVO localAssetServicevo = null;
        try {
            objSession = getHibernateSession();
            StringBuilder assetQry = new StringBuilder();
            assetQry.append("SELECT VEHICLE_NO FROM GETS_RMD_CUST_RNH_RN_V WHERE VEHICLE_HDR=:assetGrpName AND CUST_NAME=:customer");
            if (null != objSession) {
                Query assetHqry = objSession
                        .createSQLQuery(assetQry.toString());
                assetHqry.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                        objAssetServiceVO.getAssetGroupName());
                assetHqry.setParameter(RMDCommonConstants.CUSTOMER,
                        objAssetServiceVO.getCustomerName());
                queryResultList = assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(queryResultList)) {
                    for (String currentObj : queryResultList) {
                        localAssetServicevo = new AssetServiceVO();
                        localAssetServicevo.setStrAssetNumber(currentObj);
                        lstAssetDetails.add(localAssetServicevo);
                    }
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROAD_NUMBERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends method of getRoadNumbers");
        return lstAssetDetails;
    }
    
    /**
     * @Author:Mohamed
     * @param:customerId, assetNumber, assetGrpName
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the vehicle object id of the asset
     */
    @Override
    public String getVehicleObjectId(String customerId, String assetNumber,
            String assetGrpName) throws RMDDAOException {
        Session hibernateSession = null;
        String objId = null;
        try{
                hibernateSession = getHibernateSession();
                StringBuilder vehicleObj = new StringBuilder();
    
                // Get vehicle obj id
                vehicleObj.append("SELECT VEH.OBJID FROM TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH," +
                        "GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO WHERE ");
                vehicleObj.append("TSP.OBJID = VEH.VEHICLE2SITE_PART  AND TSP.SERIAL_NO NOT LIKE '%BAD%' " +
                        "AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
                vehicleObj.append("AND TSP.SERIAL_NO =:assetNumber AND TSP.X_VEH_HDR =:assetGrpName AND " +
                        "TBO.ORG_ID =:customerId");
                Query vehicleQry = hibernateSession.createSQLQuery(vehicleObj.toString());
    
                if (null != customerId
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(customerId)) {
                    vehicleQry.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
                }
                if (null != assetNumber
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(assetNumber)) {
                    vehicleQry.setParameter(RMDCommonConstants.ASSET_NUMBER, assetNumber);
                }
                if (null != assetGrpName
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(assetGrpName)) {
                    vehicleQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                            assetGrpName);
                }
    
                List<Object> objIdLst = vehicleQry.list();
    
                if (null != objIdLst && !objIdLst.isEmpty()) {
                    objId = objIdLst.get(0).toString();
                }
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_OBJECT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_OBJECT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return objId;
    }

    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last EOA fault header of the asset
     */
    @Override
    public Future<Date> getLstEOAFaultHeader(String vehicleObjectId)
            throws RMDDAOException {
        Session hibernateSession = null;
        List<Object[]> lastEOAFaultList = null;
        Date lastEOAFaultHeader = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try{
            hibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT CREATION_DATE,FLT_RST_LOAD_DATE FROM " +
                    "(SELECT TO_CHAR(sysdate,'mm/dd/yyyy hh24:mi:ss') FLT_RST_LOAD_DATE FROM dual) RST_DATE,");
            query.append("(SELECT TO_CHAR(CREATION_DATE,'mm/dd/yyyy hh24:mi:ss') CREATION_DATE " +
                    "FROM gets_tool_fault WHERE objid = (SELECT MAX(objid) FROM gets_tools.gets_tool_fault ");
            query.append("WHERE record_type = 'FLT' AND fault2vehicle = :vehicleObjid " +
                    ")) CREATION_DATE ");
            Query lstEOAQuery = hibernateSession.createSQLQuery(query.toString());
            if (null != vehicleObjectId
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(vehicleObjectId)) {
                lstEOAQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
            }
            lastEOAFaultList = lstEOAQuery.list();      
            if (null != lastEOAFaultList && !lastEOAFaultList.isEmpty()) {
                Object lstEOAData[] = (Object[]) lastEOAFaultList.get(0);
                String convertObjectToString = RMDCommonUtility
                        .convertObjectToString(lstEOAData[0]);
                if(null != convertObjectToString){
                    lastEOAFaultHeader = (Date) formatter.parse(convertObjectToString);
                }
            }
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_EOA_FAULT_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_EOA_FAULT_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return new AsyncResult<Date>(lastEOAFaultHeader);
    }

    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last PP/ATS Msg header of the asset
     */
    @Override
    public Future<Date> getLstPPATSMsgHeader(String vehicleObjectId)
            throws RMDDAOException {
        Session hibernateSession = null;
        List<Object[]> lastPPASTMsgList = null;
        Date lastPPASTMsgHeader = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try{
            hibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT TO_CHAR(hist.last_updated_date, 'mm/dd/yyyy hh24:mi:ss') " +
                    "FROM gets_tool_pp_status_hist hist, gets_tool_pp_status_curr curr ");
            query.append("WHERE curr.status_curr2status_hist = hist.objid " +
                    "AND curr.status_curr2vehicle      = :vehicleObjid");
            Query lstPPATSQuery = hibernateSession.createSQLQuery(query.toString());
            if (null != vehicleObjectId
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(vehicleObjectId)) {
                lstPPATSQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
            }
            lastPPASTMsgList = lstPPATSQuery.list();            
            if (null != lastPPASTMsgList && !lastPPASTMsgList.isEmpty()) {
                String convertObjectToString = RMDCommonUtility
                        .convertObjectToString(lastPPASTMsgList.get(0));
                if(null != convertObjectToString){
                    lastPPASTMsgHeader = (Date) formatter.parse(convertObjectToString);
                }
            }
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_PPATS_MSG_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_PPATS_MSG_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return new AsyncResult<Date>(lastPPASTMsgHeader);
    }

    
    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last  fault cell date of the asset
     */
    @Override
    public Future<Date> getLstFaultDateCell(String vehicleObjectId)
            throws RMDDAOException {
        Session hibernateSession = null;
        List<Object[]> lastFaultCellList = null;
        Date lastFaultDateCell = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try{
            hibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT /*+ NO_PARALLEL USE_NL(fltcodes)*/ TO_CHAR(MAX(OFFBOARD_LOAD_DATE),'mm/dd/yyyy hh24:mi:ss') " +
                    "FROM gets_tool_fault flt,gets_rmd_fault_codes fltcodes ");
            query.append("WHERE record_type = 'CEL' AND (sysdate - offboard_load_date) < 3 AND fault2vehicle  = :vehicleObjid AND fltcodes.fault_origin !='ECU' " +
                    "AND flt.fault2fault_code       = fltcodes.objid");
            Query lstFaultCellQuery = hibernateSession.createSQLQuery(query.toString());
            if (null != vehicleObjectId
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(vehicleObjectId)) {
                lstFaultCellQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
            }
            lastFaultCellList = lstFaultCellQuery.list();           
            if (null != lastFaultCellList && !lastFaultCellList.isEmpty()) {
                String convertObjectToString = RMDCommonUtility
                        .convertObjectToString(lastFaultCellList.get(0));
                if(null != convertObjectToString){
                    lastFaultDateCell = (Date) formatter.parse(convertObjectToString);
                }
            }
            if (lastFaultDateCell == null) {
                query = new StringBuilder();
                query.append("SELECT to_char(LAST_FAULT_DATE_CEL, 'mm/dd/yyyy hh24:mi:ss') FROM gets_rmd_Asset_snapshot WHERE vehicle_objid = :vehicleObjid ");
                lstFaultCellQuery = hibernateSession.createSQLQuery(query.toString());
                if (null != vehicleObjectId
                        && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(vehicleObjectId)) {
                    lstFaultCellQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
                }
                lastFaultCellList = lstFaultCellQuery.list();           
                if (null != lastFaultCellList && !lastFaultCellList.isEmpty()) {
                    String convertObjectToString = RMDCommonUtility
                            .convertObjectToString(lastFaultCellList.get(0));
                    if(null != convertObjectToString){
                        lastFaultDateCell = (Date) formatter.parse(convertObjectToString);
                    }
                }
            }
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DATE_CELL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DATE_CELL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return new AsyncResult<Date>(lastFaultDateCell);
    }

    /**
     * @Author:Mohamed
     * @param:vehicleObjectId
     * @return:Date
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the last ESTP download header of the asset
     */
    @Override
    public Future<Date> getLstESTPDownloadHeader(String vehicleObjectId)
            throws RMDDAOException {
        Session hibernateSession = null;
        List<Object[]> lastESTPList = null;
        Date lastESTPtHeader = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try{
            hibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append("SELECT TO_CHAR(MAX(OFFBOARD_LOAD_DATE),'mm/dd/yyyy hh24:mi:ss'),record_type " +
                    "FROM gets_tool_fault ");
            query.append("WHERE record_type IN ('STP') AND fault2vehicle  = :vehicleObjid " +
                    "GROUP BY record_type");
            Query lstESTPQuery = hibernateSession.createSQLQuery(query.toString());
            if (null != vehicleObjectId
                    && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(vehicleObjectId)) {
                lstESTPQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
            }
            lastESTPList = lstESTPQuery.list();         
            if (null != lastESTPList && !lastESTPList.isEmpty()) {
                Object lstESTPData[] = (Object[]) lastESTPList.get(0);
                String convertObjectToString = RMDCommonUtility
                        .convertObjectToString(lstESTPData[0]);
                if(null != convertObjectToString){
                    lastESTPtHeader = (Date) formatter.parse(convertObjectToString);
                }
            }
        }catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ESTP_DOWNLOAD_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ESTP_DOWNLOAD_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return new AsyncResult<Date>(lastESTPtHeader);
    }

    /**
     * @Author:Mohamed
     * @param:vehicleObjectId List<AssetLocatorVO>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the asset number ,
     *               latitude and longitude of the asset
     */
    @Override
    public Future<AssetLocatorVO> getLatAndLongitude(String customerId,
            String assetGrpName, String assetNumber) throws RMDDAOException {
        Session hibernateSession = null;
        AssetLocatorVO assetLocatorVO = null;
        try {
            hibernateSession = getHibernateSession();
            StringBuilder query = new StringBuilder();
            query.append(" SELECT GPS_LAT,GPS_LON FROM  GETS_RMD.GETS_RMD_ASSET_SNAPSHOT WHERE ");
            query.append(" ORG_ID=:customerId AND X_VEH_HDR=:assetGrpName AND SERIAL_NO=:assetNumber ");
            Query assetQuery = hibernateSession
                    .createSQLQuery(query.toString());
            assetQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
            assetQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    assetGrpName);
            assetQuery
                    .setParameter(RMDCommonConstants.ASSET_NUMBER, assetNumber);
            assetQuery.setFetchSize(1);
            List<Object[]> assetLocatorVOList = assetQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(assetLocatorVOList)) {
                Object[] obj = assetLocatorVOList.get(0);
                assetLocatorVO = new AssetLocatorVO();
                assetLocatorVO.setLatitude(RMDCommonUtility
                        .convertObjectToString(obj[0]));
                assetLocatorVO.setLongitude(RMDCommonUtility
                        .convertObjectToString(obj[1]));
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ESTP_DOWNLOAD_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ESTP_DOWNLOAD_HEADER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return new AsyncResult<AssetLocatorVO>(assetLocatorVO);
    }
    
    /**
     * @Author:
     * @param:
     * @return:List<AssetServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the road numbers based on  Customer Name and Road
     *               Number Headers and filter options
     */
    @Override
    public List<AssetServiceVO> getRoadNumbersWithFilter(String customer,
            String rnhId, String rnSearchString, String rnFilter){
        List<AssetServiceVO> lstAssetDetails = new ArrayList<AssetServiceVO>();
        Session objSession = null;
        List<String> queryResultList = null;
        AssetServiceVO localAssetServicevo = null;
        try {
            objSession = getHibernateSession();
            StringBuilder assetQry = new StringBuilder();
            assetQry.append("SELECT VEHICLE_NO FROM GETS_RMD_CUST_RNH_RN_V WHERE VEHICLE_HDR=:assetGrpName AND CUST_NAME=:customer");
            if (RMDCommonConstants.RN_STARTS_WITH
                    .equalsIgnoreCase(rnFilter)) {
                assetQry.append(" AND VEHICLE_NO LIKE :rnSearchString");
            } else if (RMDCommonConstants.RN_IS_EQUAL_TO
                    .equalsIgnoreCase(rnFilter)) {
                assetQry.append(" AND VEHICLE_NO = :rnSearchString");
            }
            if (null != objSession) {
                Query assetHqry = objSession
                        .createSQLQuery(assetQry.toString());
                assetHqry.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                        rnhId);
                assetHqry.setParameter(RMDCommonConstants.CUSTOMER,
                        customer);
                if(RMDCommonConstants.RN_STARTS_WITH.equalsIgnoreCase(rnFilter)){
                     
                    assetHqry.setParameter(RMDCommonConstants.RN_SEARCH_STRING,rnSearchString+"%");
                }else if(RMDCommonConstants.RN_IS_EQUAL_TO
                        .equalsIgnoreCase(rnFilter)){
                    assetHqry.setParameter(RMDCommonConstants.RN_SEARCH_STRING,rnSearchString);
                }
                
                queryResultList = assetHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(queryResultList)) {
                    for (String currentObj : queryResultList) {
                        localAssetServicevo = new AssetServiceVO();
                        localAssetServicevo.setStrAssetNumber(currentObj);
                        lstAssetDetails.add(localAssetServicevo);
                    }
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROAD_NUMBERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends method of getRoadNumbers");
        return lstAssetDetails;
    }

    
    /**
     * @Author:
     * @param:
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Customer Id based on
     *               assetNumber and assetGrpName.
     * 
     */

    @Override
    public String getCustomerId(String assetNumber, String assetGrpName)
            throws RMDDAOException {
        String customerId = null;
        Session objHibernateSession = null;
        StringBuilder customerIdQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            objHibernateSession = getHibernateSession();
            customerIdQuery
                    .append("SELECT TBO.ORG_ID FROM GETS_RMD_VEH_HDR GRVH,TABLE_BUS_ORG TBO,GETS_RMD_VEHICLE GRV,TABLE_SITE_PART TSP ");
            customerIdQuery
                    .append("WHERE GRVH.VEH_HDR2BUSORG = TBO.OBJID AND GRVH.OBJID=GRV.VEHICLE2VEH_HDR AND GRV.VEHICLE2SITE_PART = TSP.OBJID ");
            customerIdQuery
                    .append("AND TSP.S_SERIAL_NO NOT LIKE '%BAD%' AND TSP.S_SERIAL_NO=:assetNumber ");
            customerIdQuery.append("AND GRVH.VEH_HDR =:assetGrpName");
            hibernateQuery = objHibernateSession.createSQLQuery(customerIdQuery
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,
                    assetNumber);
            hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    assetGrpName);
            customerId = (String) hibernateQuery.uniqueResult();
            
            if (null == customerId) {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.INVALID_ASSET);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), null,
                        RMDCommonConstants.MINOR_ERROR);
            }
            
        } 
    
        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CUSTOMER_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } 
        catch (RMDDAOException e) {
        	 LOG.error(e);
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.INVALID_ASSET);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), null, RMDCommonConstants.MINOR_ERROR);
        } 
        catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CUSTOMER_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }finally {
            releaseSession(objHibernateSession);
        }
        return customerId;
    }

    @Override
    public List<AssetHeaderServiceVO> getAssetsData(
            AssetServiceVO objAssetServiceVO) throws RMDDAOException {
        LOG.debug("Begin getAssetsData method of AssetEoaDAOImpl");
        List<AssetHeaderServiceVO> arlAssetDetails = new ArrayList<AssetHeaderServiceVO>();
        Session objSession = null;
        List<Object[]> arlAssetNo = null;
        AssetServiceVO localAssetServicevo = objAssetServiceVO;
        AssetHeaderServiceVO localAssetService=null;

        try {
            objSession = getHibernateSession();

            StringBuilder assetQry = new StringBuilder();
            assetQry.append("SELECT TBO.ORG_ID,GRVH.VEH_HDR,TSP.SERIAL_NO, GRV.OBJID ");
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
                    assetQry.append(" AND TBO.ORG_ID IN ( :customerId )");
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
                 arlAssetDetails = new ArrayList<AssetHeaderServiceVO>(arlAssetNo.size());
                    Collections.sort(arlAssetNo, new Comparator<Object[]>() {
                        @Override
						public int compare(Object[] obj1, Object[] obj2) {
                            int result = 0;
                            try {
                                int assetNumber1 = RMDCommonUtility
                                        .convertObjectToInt(obj1[2]);
                                int assetNumber2 = RMDCommonUtility
                                        .convertObjectToInt(obj2[2]);
                                if (assetNumber1 == assetNumber2) {
                                    result = 0;
                                }
                                result = (assetNumber1 < assetNumber2) ? -1 : 1;
                            } catch (Exception e) {
                            	 LOG.error(e);
                                String assetNumber1 = RMDCommonUtility
                                        .convertObjectToString(obj1[2]);
                                String assetNumber2 = RMDCommonUtility
                                        .convertObjectToString(obj2[2]);

                                result = assetNumber1
                                        .compareToIgnoreCase(assetNumber2);
                            }
                            return result;
                        }
                    });

                    for (Object[] obj : arlAssetNo) {
                        localAssetService = new AssetHeaderServiceVO();
                        localAssetService.setCustomerID(RMDCommonUtility
                                .convertObjectToString(obj[0]));
                        localAssetService.setAssetGroupName(RMDCommonUtility
                                .convertObjectToString(obj[1]));
                        localAssetService.setStrAssetNumber(RMDCommonUtility
                                .convertObjectToString(obj[2]));
                        localAssetService.setVehicleObjId(RMDCommonUtility
                                .convertObjectToString(obj[3]));
                        arlAssetDetails.add(localAssetService);
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
        LOG.debug("Ends getAssetsData method of AssetEoaDAOImpl");
        return arlAssetDetails;

    }
    @Override
	public List<AssetNumberVO> getModelByFilter(
			AssetHeaderServiceVO assetHeaderServiceVO) throws RMDDAOException {
		LOG.debug("Begin getModelByFilter method of AssetEoaDAOImpl");
		List<AssetHeaderServiceVO> arlAssetDetails = new ArrayList<AssetHeaderServiceVO>();
		Session objSession = null;
		//List<String> arlAssetNo = null;
		List<Object[]> arlAssetNo = null;
		AssetHeaderServiceVO localAssetServicevo = assetHeaderServiceVO;
		AssetNumberVO localAssetNumberVO=null;
		List<AssetNumberVO> lstAssetNumberVO=new ArrayList<AssetNumberVO>();
		String strModelArray[] = null;
		Query assetHqry=null;
		try {
			objSession = getHibernateSession();

			StringBuilder assetQry = new StringBuilder();
			assetQry.append("SELECT  DISTINCT MODEL.MODEL_NAME,MODEL.MODEL_GENERAL FROM GETS_RMD_MODEL MODEL,GETS_RMD_VEHICLE ASSET,GETS_RMD_FLEET FLEET,TABLE_BUS_ORG CUSTOMER,TABLE_SITE_PART TSP ");
			assetQry.append("WHERE ASSET.VEHICLE2MODEL = MODEL.OBJID AND ASSET.VEHICLE2FLEET = FLEET.OBJID AND CUSTOMER.OBJID = FLEET.FLEET2BUS_ORG AND TSP.OBJID = ASSET.VEHICLE2SITE_PART ");
			if (null != localAssetServicevo.getCustomerID()
					&& !RMDCommonConstants.EMPTY_STRING
							.equals(localAssetServicevo.getCustomerID())) {
				assetQry.append(" AND ORG_ID =:customerId");
			}
			if (null != localAssetServicevo.getFleet()
					&& !RMDCommonConstants.EMPTY_STRING
							.equals(localAssetServicevo.getFleet())) {
				assetQry.append(" AND FLEET_NUMBER =:fleet");
			}
			if (null != localAssetServicevo.getAssetGroupName()
					&& !RMDCommonConstants.EMPTY_STRING
							.equals(localAssetServicevo.getAssetGroupName())) {
				assetQry.append(" AND TSP.X_VEH_HDR =:groupName");
			}
			if (null != localAssetServicevo.getAssetList()
					&& localAssetServicevo.getAssetList().length > 0) {
				strModelArray=localAssetServicevo.getAssetList();
				assetQry.append(" AND TSP.S_SERIAL_NO IN (:strModelArray) ");
			}
			if (null != objSession) {
				assetHqry = objSession
						.createSQLQuery(assetQry.toString());

				if (null != localAssetServicevo.getAssetGroupName()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(localAssetServicevo.getAssetGroupName())) {
					assetHqry.setParameter(RMDCommonConstants.GROUP_NAME,
							localAssetServicevo.getAssetGroupName());
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
				if (null != localAssetServicevo.getFleet()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(localAssetServicevo.getFleet())) {
					assetHqry.setParameter(RMDCommonConstants.FLEET,
							localAssetServicevo.getFleet());
				}
			}
			
				if (null != objSession && null !=strModelArray && strModelArray.length > 0) {
					List<String> listParameter = new ArrayList<String>();
					for (String strModelItr : strModelArray) {
						listParameter.add(strModelItr);
					}

					assetHqry.setParameterList(RMDCommonConstants.MODEL_S,
							listParameter);
				}
				assetHqry.setFetchSize(1000); 
				//arlAssetNo = (ArrayList) assetHqry.list();
				arlAssetNo = assetHqry.list();
				
				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
				 arlAssetDetails = new ArrayList<AssetHeaderServiceVO>(arlAssetNo.size());
					for (Object[] modelObj  : arlAssetNo) {
						localAssetNumberVO = new AssetNumberVO();
						localAssetNumberVO.setModel(RMDCommonUtility.convertObjectToString(modelObj[0]));
						localAssetNumberVO.setModelGeneral(RMDCommonUtility.convertObjectToString(modelObj[1]));
						lstAssetNumberVO.add(localAssetNumberVO);
						localAssetNumberVO = null;
					}
				}
			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			arlAssetNo=null;
			releaseSession(objSession);
		}
		LOG.debug("Ends getModelByFilter method of AssetEoaDAOImpl");
		return lstAssetNumberVO;
	}
	
	
	
	/**
	 * @Author:
	 * @param :String vehicleObjId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the Services it takes
	 *               vehicleObjId Input Parameter and returns services.
	 * 
	 */

	public Future<String> getServices(String vehicleObjId)
			throws RMDDAOException {
		StringBuilder serviceTypes = new StringBuilder();
		Session hibernateSession = null;
		Query hibernateQuery = null;
		StringBuilder servicesQuery = new StringBuilder();
		String[] arrServices = null;
		StringBuilder servicesQuery1 = new StringBuilder();
		StringBuilder servicesAddedStr = new StringBuilder();
		String servicesAddedString = null;
		try {
			hibernateSession = getHibernateSession();
			servicesQuery
					.append(" SELECT DECODE(gsup.service_eoa,1,'EOA','NULL')  ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_pinpoint,1,'PINPOINT','NULL')  ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_estp,1,'ESTP','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_pdp,1,'PDP','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_ered,1,'ERED','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_cell,1,'CELL','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_smb,1,'SMB','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_oil,1,'OIL','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_ldss,1,'LDSS','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.service_lococam,1,'LOCOCAM','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.SERVICE_TO,1,'TO','NULL') ");
			servicesQuery
					.append(" || ',' || DECODE(gsup.SERVICE_DHMS,1,'DHMS','') ");
			servicesQuery
					.append(" FROM   gets_omi_mdsc_sup_def gsup  WHERE  gsup.mdsc_sup_def2vehicle=:vehicleObjid ");
			servicesQuery
					.append(" AND gsup.active_config = 'Y' AND  gsup.objid =  ");
			servicesQuery
					.append(" (SELECT MAX(gsup1.objid)   FROM   gets_omi_mdsc_sup_def gsup1  WHERE  gsup1.mdsc_sup_def2vehicle=:vehicleObjid ");
			servicesQuery.append("   AND  gsup1.active_config = 'Y') ");
			hibernateQuery = hibernateSession.createSQLQuery(servicesQuery
					.toString());
			hibernateQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID,
					vehicleObjId);
			Object objServiceObject = hibernateQuery.uniqueResult();
			String services = RMDCommonUtility
					.convertObjectToString(objServiceObject);
			if (null != services) {
				arrServices = services
						.split(RMDCommonConstants.COMMMA_SEPARATOR);
				for (String service : arrServices) {
					if (!RMDCommonConstants.STRING_NULL
							.equalsIgnoreCase(service)) {
						if (serviceTypes.length() != 0) {
							serviceTypes
									.append(RMDCommonConstants.COMMMA_SEPARATOR
											+ service);
						} else {
							serviceTypes.append(service);
						}
					}
				}
				arrServices = null;
			}
			
			servicesQuery1.append("SELECT DISTINCT application_name ");
            servicesQuery1.append("FROM   gets_mcs.mcs_asset_application ass_app, ");
            servicesQuery1.append("       gets_mcs.mcs_application app, ");
            servicesQuery1.append("       gets_rmd_vehcfg vehcfg, ");
            servicesQuery1.append("       gets_rmd_master_bom mbom, ");
            servicesQuery1.append("       gets_rmd_vehicle veh ");
            servicesQuery1.append("WHERE  vehcfg2master_bom = mbom.objid ");
            servicesQuery1.append("       AND veh.objid = :vehicleObjId ");
            servicesQuery1.append("       AND veh.objid = vehcfg.veh_cfg2vehicle ");
            servicesQuery1.append("       AND mbom.bom_status = 'Y' ");
            servicesQuery1.append("       AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg ");
            servicesQuery1.append("       AND ( ( mbom.config_item = 'CMU' ");
            servicesQuery1.append("               AND vehcfg.current_version = '2' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'HPEAP' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'LCV' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery1.append("              OR ( mbom.config_item = 'LIG' ");
            servicesQuery1.append("                   AND vehcfg.current_version = '1' ) ) ");
            servicesQuery1.append("       AND ass_app.asset_objid = veh.vehicle2asset_objid ");
            servicesQuery1.append("       AND app.objid = ass_app.application_objid ");
            
            Query servicesHQuery = hibernateSession.createSQLQuery(servicesQuery1.toString());
            servicesHQuery.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID ,vehicleObjId);
            List<String> servicesAddedList = servicesHQuery.list();
            if (null != servicesAddedList && !servicesAddedList.isEmpty()) {
            for(String servicesToAdd : servicesAddedList){
              servicesAddedStr.append(servicesToAdd);
              servicesAddedStr.append(SEPARATOR);
             } 
            servicesAddedString = servicesAddedStr.toString();
            servicesAddedString = servicesAddedString.substring(0, servicesAddedString.length() - SEPARATOR.length());
            }
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SERVICE_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		if (servicesAddedString != null && !servicesAddedString.isEmpty()) { 
			if (serviceTypes.toString() != null && !serviceTypes.toString().isEmpty()){
				return new AsyncResult<String>(serviceTypes.toString() + "," + servicesAddedString);
			} else {
				return new AsyncResult<String> (servicesAddedString);
			}
		
		} else {
			return new AsyncResult<String>(serviceTypes.toString());
		}
	}

	/**
	 * @Author:
	 * @param :String vehicleObjId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the Next Scheduled run it
	 *               takes vehicleObjId Input Parameter and returns services.
	 * 
	 */

	public Future<String> getAssetNextScheduledRun(String vehicleObjId)
			throws RMDDAOException {
		Session hibernateSession = null;
		Query hibernateQuery = null;
		StringBuilder nextScheduleRunQuery = new StringBuilder();
		String nextScheduledRun = null;
		try {
			hibernateSession = getHibernateSession();
			nextScheduleRunQuery
					.append(" SELECT TO_CHAR(TOOL_RUN_NEXT, 'MM/DD/YYYY HH24:MI:SS') TOOL_RUN_NEXT FROM GETS_RMD_VEHICLE  ");
			nextScheduleRunQuery.append(" WHERE OBJID=:vehicleObjid  ");
			hibernateQuery = hibernateSession
					.createSQLQuery(nextScheduleRunQuery.toString());
			hibernateQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID,
					vehicleObjId);
			Object objServiceObject = hibernateQuery.uniqueResult();
			nextScheduledRun = RMDCommonUtility
					.convertObjectToString(objServiceObject);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEXT_SCHEDULED_RUN);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return new AsyncResult<String>(nextScheduledRun);
	}
	
	
	 @Override
		public List<AssetLocationDetailVO> getAssetDetailList(AssetsLocationFromShopVO objAssetsLocationFromShopVO) throws RMDDAOException {
		 	Session hibernateSession = null;
			Query hibernateQuery = null;
			List<AssetLocationDetailVO>  assetLocationDetailList = new ArrayList<AssetLocationDetailVO>();
			List<Object[]>  assetDetailResult = null;
			AssetLocationDetailVO assetLocationDetailVO = null;
			List<Object> resultSet = null;
			StringBuilder varname1 = null;
			StringBuilder varname = null;
			int shopAvailableCount = 0;
			int cnt = 0;
			try {

				hibernateSession = getHibernateSession();
				
			    	varname = new StringBuilder();
				    varname.append("SELECT 1 ");
				    varname.append("FROM   gets_rmd_eoa_proximity_def ");
				    varname.append("WHERE  eserviceorgid = :eServiceOrgId");
				    hibernateQuery = hibernateSession.createSQLQuery(varname.toString());
		            hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
		            resultSet = hibernateQuery.list();
		              
		              if (RMDCommonUtility.isCollectionNotEmpty(resultSet)) {
		            	  shopAvailableCount = RMDCommonUtility.convertObjectToInt(resultSet.get(0));
		              }
		              
		            if (shopAvailableCount > 0) {
				
		            varname1 = new StringBuilder();
					varname1.append("SELECT * ");
					varname1.append("FROM   (SELECT x_veh_hdr, ");
					varname1.append("               serial_no, ");
					varname1.append("               lat, ");
					varname1.append("               lon, ");
					varname1.append("               lat_lon_time, ");
					varname1.append("               gps_system, ");
					varname1.append("               CASE ");
					varname1.append("                 WHEN Nvl(lat, 0) <> 0 THEN Round(sdo_geom.Sdo_distance(Sdo_geometry(2001, 8307, Sdo_point_type(lon, lat, NULL), NULL, NULL) ");
					varname1.append("                                                  , ");
					varname1.append("                                                                                     Sdo_geometry(2001, 8307, ");
					varname1.append("                                                                                     Sdo_point_type((SELECT gps_lon ");
					varname1.append("                                                                                           FROM   gets_rmd_eoa_proximity_def ");
					varname1.append("                                                                                           WHERE  eserviceorgid = :eServiceOrgId), ");
					varname1.append("                                                                                     (SELECT gps_lat ");
					varname1.append("                                                                                      FROM   gets_rmd_eoa_proximity_def ");
					varname1.append("                                                                                      WHERE  eserviceorgid = :eServiceOrgId), NULL) ");
					varname1.append("                                                                                     , NULL, NULL), 0.005, ");
					varname1.append("                                                                               'unit=MILE'), 2) ");
					varname1.append("                 ELSE NULL ");
					varname1.append("               END distance_to_shop, ");
					varname1.append("               CASE ");
					varname1.append("                 WHEN Nvl(lat, 0) <> 0 THEN Decode(Mod(Round(( Round(Mod(Atan2(Cos (( SELECT gps_lat FROM gets_rmd_eoa_proximity_def WHERE ");
					varname1.append("                                                               eserviceorgid = ");
					varname1.append("                                                               :eServiceOrgId) * Asin( 1)* 2 /180 )*Sin ");
					varname1.append("                                                                                            ((( ");
					varname1.append("                                                                                                   SELECT ");
					varname1.append("                                                                                                             gps_lon ");
					varname1.append("                                                                                                             FROM gets_rmd_eoa_proximity_def ");
					varname1.append("                                                               WHERE ");
					varname1.append("                                                               eserviceorgid = :eServiceOrgId)* Asin (1)*2 /180 ");
					varname1.append("                                                                                            )-( ");
					varname1.append("                                                                                                   lon * ");
					varname1.append("                                                                                                       Asin ( ");
					varname1.append("                                                                                                             1)* 2 /180) ), ( Cos( lat* Asin ");
					varname1.append("                                                               (1 ) * ");
					varname1.append("                                                               2 /180 ) ");
					varname1.append("                                                               * Sin(( SELECT gps_lat FROM ");
					varname1.append("                                                                                                             gets_rmd_eoa_proximity_def ");
					varname1.append("                                                               WHERE ");
					varname1.append("                                                               eserviceorgid ");
					varname1.append("                                                               = :eServiceOrgId)* Asin (1)*2/ 180 ) ) ");
					varname1.append("                                                                                            - ( Sin ");
					varname1.append("                                                                                                   ( ");
					varname1.append("                                                                                                       lat * ");
					varname1.append("                                                                                                             Asin(1) *2/180) * Cos( ( SELECT ");
					varname1.append("                                                               gps_lat FROM ");
					varname1.append("                                                               gets_rmd_eoa_proximity_def ");
					varname1.append("                                                                                            WHERE ");
					varname1.append("                                                                                                       eserviceorgid ");
					varname1.append("                                                                                                             = :eServiceOrgId) * Asin (1)* 2 / 180) * ");
					varname1.append("                                                               Cos(( ( ");
					varname1.append("                                                               SELECT ");
					varname1.append("                                                               gps_lon FROM ");
					varname1.append("                                                                                                   gets_rmd_eoa_proximity_def ");
					varname1.append("                                                                                                       WHERE ");
					varname1.append("                                                                                                             eserviceorgid = :eServiceOrgId ) * Asin(1) ");
					varname1.append("                                                               * 2 / ");
					varname1.append("                                                               180)-( ");
					varname1.append("                                                               lon* Asin (1) *2/ 180) )) ), 2* ");
					varname1.append("                                                                                            Asin ( ");
					varname1.append("                                                                                                   1) * ");
					varname1.append("                                                                                                       2) * ");
					varname1.append("                                                                                                             180 /( Asin(1)* 2), 2) ");
					varname1.append("                                                               + 180 ) / 45, 0), 8), 0, 'N', ");
					varname1.append("                                                                                     1, 'NE', ");
					varname1.append("                                                                                     2, 'E', ");
					varname1.append("                                                                                     3, 'SE', ");
					varname1.append("                                                                                     4, 'S', ");
					varname1.append("                                                                                     5, 'SW', ");
					varname1.append("                                                                                     6, 'W', ");
					varname1.append("                                                                                     7, 'NW') ");
					varname1.append("                 ELSE NULL ");
					varname1.append("               END Direction_From_Shop ");
					varname1.append("        FROM   (SELECT org_id, ");
					varname1.append("                       x_veh_hdr, ");
					varname1.append("                       serial_no, ");
					varname1.append("                       vehicle_objid, ");
					varname1.append("                       CASE ");
					varname1.append("                         WHEN gps_system = 'ATS' THEN ats_lat ");
					varname1.append("                         WHEN gps_system = 'FLT' THEN gps_lat ");
					varname1.append("                       END LAT, ");
					varname1.append("                       CASE ");
					varname1.append("                         WHEN gps_system = 'ATS' THEN ats_lon ");
					varname1.append("                         WHEN gps_system = 'FLT' THEN gps_lon ");
					varname1.append("                       END LON, ");
					varname1.append("                       CASE ");
					varname1.append("                         WHEN gps_system = 'ATS' THEN ats_last_comm_date ");
					varname1.append("                         WHEN gps_system = 'FLT' THEN last_gpslatlon_updated_date ");
					varname1.append("                       END LAT_LON_TIME, ");
					varname1.append("                       gps_system ");
					varname1.append("                FROM   (SELECT org_id, ");
					varname1.append("                               x_veh_hdr, ");
					varname1.append("                               serial_no, ");
					varname1.append("                               gps_lat, ");
					varname1.append("                               gps_lon, ");
					varname1.append("                               last_gpslatlon_updated_date, ");
					varname1.append("                               ats_lat, ");
					varname1.append("                               ats_lon, ");
					varname1.append("                               ats_last_comm_date, ");
					varname1.append("                               vehicle_objid, ");
					varname1.append("                               CASE ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date IS NULL ");
					varname1.append("                                      AND ats_last_comm_date IS NULL THEN 'NONE' ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date IS NOT NULL ");
					varname1.append("                                      AND ats_last_comm_date IS NULL THEN 'FLT' ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date IS NULL ");
					varname1.append("                                      AND ats_last_comm_date IS NOT NULL THEN 'ATS' ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date > ats_last_comm_date THEN 'FLT' ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date < ats_last_comm_date THEN 'ATS' ");
					varname1.append("                                 WHEN last_gpslatlon_updated_date = ats_last_comm_date THEN 'FLT' ");
					varname1.append("                               END GPS_SYSTEM ");
					varname1.append("                        FROM   gets_rmd_asset_snapshot ");
					varname1.append("                        WHERE  vehicle_objid IN (" );
						for (AssetVO objAsset : objAssetsLocationFromShopVO.getAssets()) {
					varname1.append("                    SELECT vehicle_objid  FROM   gets_rmd_asset_snapshot ");
					varname1.append("                                                 WHERE  org_id = ");
					varname1.append("'"+objAsset.getRnh()+"'");
					varname1.append("                                                        AND serial_no = ");
					varname1.append("'"+objAsset.getRoadNumber()+"'");
							if (++cnt < objAssetsLocationFromShopVO.getAssets().size()) {
								varname1.append(" UNION ALL ");
							}
						}

					varname1.append("      ) )  WHERE  gps_system != 'NONE'))");

				hibernateQuery = hibernateSession.createSQLQuery(varname1.toString());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());
				hibernateQuery.setParameter(RMDCommonConstants.ESERVICE_ORGID,objAssetsLocationFromShopVO.getEServicesOrgId());

				assetDetailResult = hibernateQuery.list();
				if (RMDCommonUtility.isCollectionNotEmpty(assetDetailResult)) {
					
					for (Object[] objValues : assetDetailResult) {
					assetLocationDetailVO = new AssetLocationDetailVO();
					assetLocationDetailVO.setRnh(objValues[0].toString());
					assetLocationDetailVO.setRoadNumber(objValues[1].toString());
					assetLocationDetailVO.setLatitude(objValues[2].toString());
					assetLocationDetailVO.setLongitude(objValues[3].toString());
					assetLocationDetailVO.setLatlongTime(objValues[4].toString());
					assetLocationDetailVO.setGpsSystem(objValues[5].toString());
					assetLocationDetailVO.setDistanceToShop(objValues[6].toString());
					assetLocationDetailVO.setDirectionFromShop(objValues[7].toString());
					assetLocationDetailList.add(assetLocationDetailVO);  
				}
					
	          } 
			 } else {
				 final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SHOPNOTAVAILABLE);
	                throw new RMDDAOException(errorCode, new String[] {},
	                        RMDCommonUtility.getMessage(errorCode, new String[] {},
	                        		RMDCommonConstants.ENGLISH_LANGUAGE), null,
	                        RMDCommonConstants.MINOR_ERROR);
			 }
			}catch (RMDDAOException e) {
	        	 LOG.error(e);
	             final String errorCode = RMDCommonUtility
	                     .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SHOPNOTAVAILABLE);
	             throw new RMDDAOException(errorCode, new String[] {},
	                     RMDCommonUtility.getMessage(errorCode, new String[] {},
	                    		 RMDCommonConstants.ENGLISH_LANGUAGE), null, RMDCommonConstants.MINOR_ERROR);
	         }catch (Exception e) {
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_DETAILS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.MINOR_ERROR);
			} finally {
				releaseSession(hibernateSession);
			}
			return assetLocationDetailList;
	 }
	 
	 
	 public Future<String> getLastToolRun(String vehicleObjId)
				throws RMDDAOException {
			Session hibernateSession = null;
			Query hibernateQuery = null;
			StringBuilder lastToolRunQuery = new StringBuilder();
			String lastToolRun = null;
			try {
				hibernateSession = getHibernateSession();
				lastToolRunQuery
						.append(" SELECT TO_CHAR(RUN_CPT, 'MM/DD/YYYY HH24:MI:SS') FROM GETS_TOOL_RUN WHERE OBJID IN  (SELECT MAX(OBJID)  FROM GETS_TOOL_RUN")
						.append(" WHERE RUN2VEHICLE    = :vehicleObjId  AND CREATION_DATE    > SYSDATE - 7  AND DIAG_SERVICE_ID IN ('DHMS', 'EOA', 'ESTP', 'MNS', 'OHV', 'OIL', 'QNX Equipment')")
						.append(" AND RUN_CPT         IS NOT NULL)");
				hibernateQuery = hibernateSession
						.createSQLQuery(lastToolRunQuery.toString());
				hibernateQuery.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID,
						vehicleObjId);
				Object objServiceObject = hibernateQuery.uniqueResult();
				lastToolRun = RMDCommonUtility
						.convertObjectToString(objServiceObject);
			} catch (Exception e) {
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEXT_SCHEDULED_RUN);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.MINOR_ERROR);
			} finally {
				releaseSession(hibernateSession);
			}
			return new AsyncResult<String>(lastToolRun);
		}
	 
	 
	 /**
	     * @Author:Mohamed
	     * @param:vehicleObjectId
	     * @return:Date
	     * @throws:RMDDAOException
	     * @Description: This method is used for fetching the last EOA fault header of the asset
	     */
	    
	    public Future<String> getLstFault(String vehicleObjectId)
	            throws RMDDAOException {
	    	Session hibernateSession = null;
			String lastFault = null;
	       
	        try{
	            hibernateSession = getHibernateSession();
	            StringBuilder query = new StringBuilder();	           
	            query.append("SELECT TO_CHAR(CREATION_DATE,'mm/dd/yyyy hh24:mi:ss') CREATION_DATE " +
	                    "FROM gets_tool_fault WHERE objid = (SELECT MAX(objid) FROM gets_tools.gets_tool_fault ");
	            query.append("WHERE record_type in ('FLT','CEL','HC','STP') AND fault2vehicle = :vehicleObjid) ");
	            Query lstEOAQuery = hibernateSession.createSQLQuery(query.toString());
	            if (null != vehicleObjectId
	                    && !RMDCommonConstants.EMPTY_STRING
	                    .equalsIgnoreCase(vehicleObjectId)) {
	                lstEOAQuery.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjectId);
	            }
	            Object objServiceObject = lstEOAQuery.uniqueResult();
				lastFault = RMDCommonUtility
						.convertObjectToString(objServiceObject);
	        }catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_EOA_FAULT_HEADER);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LAST_EOA_FAULT_HEADER);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        }finally {
	            releaseSession(hibernateSession);
	        }
	        return new AsyncResult<String>(lastFault);
	    }
		 /**
	     * @Author:Arun
	     * @param:vehicleObjectId
	     * @return:Date
	     * @throws:RMDDAOException
	     * @Description: This method is used for fetching the last EOA fault header of the asset
	     */
	    
	    public String getLocoId(AssetServiceVO objAssetServiceVO)
	            throws RMDDAOException {
	    	Session hibernateSession = null;
			String locoId = null;
	       
	        try{
	            hibernateSession = getHibernateSession();
	            StringBuilder query = new StringBuilder();	           
	            query.append("SELECT LMS_LOCO_ID FROM GETS_RMD_CUST_RNH_RN_V ");
	            if(null!=objAssetServiceVO.getAssetGroupName()  && !RMDCommonConstants.EMPTY_STRING.equals(objAssetServiceVO.getAssetGroupName())){
	            query.append("WHERE VEHICLE_HDR =:assetGrpName ");
	            }
	            if(null!=objAssetServiceVO.getCustomerID()  && !RMDCommonConstants.EMPTY_STRING.equals(objAssetServiceVO.getCustomerID())){
		            query.append("AND ORG_ID =:customerId ");
		        }
	            if(null!=objAssetServiceVO.getAssetNumberLike()  && !RMDCommonConstants.EMPTY_STRING.equals(objAssetServiceVO.getAssetNumberLike())){
		            query.append("AND VEHICLE_NO =:assetNumber");
		        }
	            
	            Query lstEOAQuery = hibernateSession.createSQLQuery(query.toString());
	            if (null != objAssetServiceVO.getAssetGroupName()
	                    && !RMDCommonConstants.EMPTY_STRING
	                    .equalsIgnoreCase(objAssetServiceVO.getAssetGroupName())) {
	                lstEOAQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, objAssetServiceVO.getAssetGroupName());
	            }
	            if (null != objAssetServiceVO.getCustomerID()
	                    && !RMDCommonConstants.EMPTY_STRING
	                    .equalsIgnoreCase(objAssetServiceVO.getCustomerID())) {
	                lstEOAQuery.setParameter(RMDCommonConstants.CUSTOMER_ID, objAssetServiceVO.getCustomerID());
	            }
	            if (null != objAssetServiceVO.getAssetNumberLike()
	                    && !RMDCommonConstants.EMPTY_STRING
	                    .equalsIgnoreCase(objAssetServiceVO.getAssetNumberLike())) {
	                lstEOAQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, objAssetServiceVO.getAssetNumberLike());
	            }
	            Object objServiceObject = lstEOAQuery.uniqueResult();
	            locoId = RMDCommonUtility
						.convertObjectToString(objServiceObject);
	        }catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCO_ID);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCO_ID);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        }finally {
	            releaseSession(hibernateSession);
	        }
	        return locoId;
	    }
}

