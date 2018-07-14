package com.ge.trans.eoa.services.asset.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class UnitRenumberDAOImpl extends RMDCommonDAO implements  UnitRenumberDAOIntf{

	private static final long serialVersionUID = 7257888416791166772L;
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf#updateUnitNumber(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumber(String oldValue, String newValue, String vehicleHeader, String customerId) {
		Session session = null;
        try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
            //Check if road number exists
            queryString.append("SELECT serial_no FROM SA.table_site_part WHERE  x_veh_hdr=:vehicleHeader AND serial_no =:oldSerialNo");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
            query.setParameter(RMDCommonConstants.UNIT_OLD_SERIAL_NUMBER, oldValue);
            List<?> result = query.list();
            if(result.isEmpty())
            	return false;
            
            StringBuilder updateQueryBuilder = new StringBuilder();
            //Update table_site_part in OLTP
            updateQueryBuilder.append("UPDATE SA.table_site_part SET serial_no = :serialNo, s_serial_no = :sserialNo ");
            updateQueryBuilder.append("WHERE objid IN (SELECT objid FROM SA.table_site_part WHERE x_veh_hdr=:vehicleHeader AND serial_no=:oldSerialNo)");
            Query updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
            updateQuery.setParameter(RMDCommonConstants.UNIT_SERIAL_NUMBER, newValue);
            updateQuery.setParameter(RMDCommonConstants.UNIT_S_SERIAL_NUMBER, newValue);
            updateQuery.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
            updateQuery.setParameter(RMDCommonConstants.UNIT_OLD_SERIAL_NUMBER, oldValue);
            
            int numberOfUpdates = updateQuery.executeUpdate();
            if(numberOfUpdates == 0)
            	return false;
            LOG.info("Update successful:"+numberOfUpdates);
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf#updateUnitNumberDW(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateUnitNumberDW(String oldValue, String newValue, String vehicleHeader, String customerId) {
		Session session = null;
        try {
        	LOG.info("Inside DAO DW");
            session = getDWHibernateSession();
            
            StringBuilder queryString = new StringBuilder();
            //Check if road number exists
            queryString.append("SELECT serial_no FROM EOA_DW.table_site_part WHERE  x_veh_hdr=:vehicleHeader AND serial_no =:oldSerialNo");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
            query.setParameter(RMDCommonConstants.UNIT_OLD_SERIAL_NUMBER, oldValue);
            List<?> result = query.list();
            int numberOfUpdates = 0;
            StringBuilder updateQueryBuilder = new StringBuilder();
            Query updateQuery;
            if(!result.isEmpty()) {
	            //Update table_site_part in DWH
	            updateQueryBuilder.append("UPDATE EOA_DW.table_site_part SET serial_no = :serialNo, s_serial_no = :sserialNo ");
	            updateQueryBuilder.append("WHERE objid IN (SELECT objid FROM EOA_DW.table_site_part WHERE x_veh_hdr=:vehicleHeader AND serial_no=:oldSerialNo)");
	            updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
	            updateQuery.setParameter(RMDCommonConstants.UNIT_SERIAL_NUMBER, newValue);
	            updateQuery.setParameter(RMDCommonConstants.UNIT_S_SERIAL_NUMBER, newValue);
	            updateQuery.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
	            updateQuery.setParameter(RMDCommonConstants.UNIT_OLD_SERIAL_NUMBER, oldValue);
            
	            numberOfUpdates = updateQuery.executeUpdate();
	            if(numberOfUpdates == 0)
	            	return false;
	            LOG.info("table_site_part update successful");
	        }
            
            queryString = new StringBuilder();
            //Check if road number exists
            queryString.append("SELECT vehicle_no FROM EOA_DW.gets_dw_eoa_site_part WHERE  veh_header=:vehicleHeader AND  vehicle_no =:oldVehicleNo");
            query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
            query.setParameter(RMDCommonConstants.UNIT_OLD_VEHICLE_NUMBER, oldValue);
            result = query.list();
            if(!result.isEmpty()){            
            	updateQueryBuilder = new StringBuilder();
            	//Update gets_dw_eoa_site_part in DWH
            	updateQueryBuilder.append("UPDATE EOA_DW.gets_dw_eoa_site_part SET vehicle_no = :vehicleNo ");
            	updateQueryBuilder.append("WHERE  site_part_objid IN (SELECT  site_part_objid FROM EOA_DW.gets_dw_eoa_site_part WHERE veh_header=:vehicleHeader AND vehicle_no=:oldVehicleNo)");
            	updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
            	updateQuery.setParameter(RMDCommonConstants.UNIT_VEHICLE_NUMBER, newValue);
            	updateQuery.setParameter(RMDCommonConstants.UNIT_VEHICLE_HEADER, vehicleHeader);
            	updateQuery.setParameter(RMDCommonConstants.UNIT_OLD_VEHICLE_NUMBER, oldValue);

            	numberOfUpdates = updateQuery.executeUpdate();
            	if(numberOfUpdates == 0)
            		return false;
            	LOG.info("gets_dw_eoa_site_part update successful");
            }
            updateQueryBuilder = new StringBuilder();
            //Update gets_dw_eoa_vehicle in DWH
            updateQueryBuilder.append("UPDATE EOA_DW.gets_dw_eoa_vehicle SET road_nbr=:roadNo ");
            updateQueryBuilder.append("WHERE vehicle_key IN (SELECT vehicle_key FROM EOA_DW.gets_dw_eoa_vehicle V JOIN EOA_DW.GETS_DW_EOA_CUSTOMER C ON C.customer_key = V.customer_key WHERE C.customer_id=:customerId AND road_nbr=:oldRoadNo)");
            updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
            updateQuery.setParameter(RMDCommonConstants.UNIT_ROAD_NUMBER, newValue);
            updateQuery.setParameter(RMDCommonConstants.UNIT_CUSTOMER_ID, customerId);
            updateQuery.setParameter(RMDCommonConstants.UNIT_OLD_ROAD_NUMBER, oldValue);
            numberOfUpdates = updateQuery.executeUpdate();
            if(numberOfUpdates == 0)
            	return false;
            LOG.info("gets_dw_eoa_vehicle update successful");
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf#updateUnitHeader(com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO)
	 */
	@Override
	public boolean updateUnitHeader(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		Session session = null;
        try {
            session = getHibernateSession();
            StringBuilder updateQueryBuilder = new StringBuilder();
        	updateQueryBuilder.append("update SA.table_site_part set x_veh_hdr= :newVehicleHeader, x_veh_hdr_cust= :newVehicleHeaderCust, x_veh_hdr_no= (select VEH_HDR_NO from gets_rmd_veh_hdr where veh_hdr = :newVehicleHeader1) ");
        	updateQueryBuilder.append("where objid IN (Select objid from SA.table_site_part where serial_no IN (:vehicleNumbers) and x_veh_hdr = :currentVehicleHeader)");
        	Query updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER_CUST, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER1, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_CURRENT_VEHICLE_HEADER, objUnitRoadHeaderUpdateVO.getCurrentRoadHeader());
        	updateQuery.setParameterList(RMDCommonConstants.UNIT_VEHICLE_NUMBERS, objUnitRoadHeaderUpdateVO.getVehicleNumbers());
        	int numberOfUpdates = updateQuery.executeUpdate();
            
        	StringBuilder updateQueryBuilder2 = new StringBuilder();
        	updateQueryBuilder2.append("update gets_rmd_vehicle set vehicle2veh_hdr=(select OBJID from gets_rmd_veh_hdr where veh_hdr= :newVehicleHeader) where vehicle2site_part IN (select objid from table_site_part where x_veh_hdr= :newVehicleHeader1 and serial_no IN (:vehicleNumbers))");
        	Query updateQuery2 = session.createSQLQuery(updateQueryBuilder2.toString());
        	updateQuery2.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery2.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER1, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery2.setParameterList(RMDCommonConstants.UNIT_VEHICLE_NUMBERS, objUnitRoadHeaderUpdateVO.getVehicleNumbers());
        	int numberOfUpdates2 = updateQuery2.executeUpdate();
        	if(numberOfUpdates == 0 && numberOfUpdates2 == 0)
            	return false;
            LOG.info("update successful");
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.asset.dao.intf.UnitRenumberDAOIntf#updateUnitHeaderDW(com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO)
	 */
	@Override
	public boolean updateUnitHeaderDW(
			UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO) {
		Session session = null;
        try {
            session = getDWHibernateSession();
            StringBuilder updateQueryBuilder = new StringBuilder();
        	//Update gets_dw_eoa_site_part in DWH
        	updateQueryBuilder.append("UPDATE EOA_DW.gets_dw_eoa_site_part set veh_header= :newVehicleHeader ");
        	updateQueryBuilder.append("WHERE site_part_objid IN (SELECT site_part_objid FROM EOA_DW.gets_dw_eoa_site_part where vehicle_no IN (:vehicleNumbers) AND veh_header = :currentVehicleHeader)");
        	Query updateQuery = session.createSQLQuery(updateQueryBuilder.toString());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_NEW_VEHICLE_HEADER, objUnitRoadHeaderUpdateVO.getNewRoadHeader());
        	updateQuery.setParameter(RMDCommonConstants.UNIT_CURRENT_VEHICLE_HEADER, objUnitRoadHeaderUpdateVO.getCurrentRoadHeader());
        	updateQuery.setParameterList(RMDCommonConstants.UNIT_VEHICLE_NUMBERS, objUnitRoadHeaderUpdateVO.getVehicleNumbers());
        	int numberOfUpdates = updateQuery.executeUpdate();
            if(numberOfUpdates == 0)
            	return false;
            LOG.info("update successful");
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return true;
	}
	
	@Override
	public List<AssetServiceVO> getAssetsForUnitRenum(
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
            assetQry.append("AND GRF.OBJID = GRV.VEHICLE2FLEET AND GRM.OBJID = GRV.VEHICLE2MODEL AND GRV.OBJID = EGA_CFG2VEHICLE(+) ");

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
        LOG.debug("Ends getAssets method of UnitRenumberDAOImpl");
        return arlAssetDetails;

    }
}
