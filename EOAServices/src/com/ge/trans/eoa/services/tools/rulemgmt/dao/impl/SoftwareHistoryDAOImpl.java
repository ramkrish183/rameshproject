package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SoftwareHistoryRequestVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.SoftwareHistoryDAOIntf;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.SoftwareHistoryVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class SoftwareHistoryDAOImpl  extends RMDCommonDAO implements SoftwareHistoryDAOIntf{

	@Override
	public List<SoftwareHistoryVO> getSoftwareHistory(SoftwareHistoryRequestVO historyRequestVO) {
		
		LOG.info("getSoftwareHistory DAO service called");
		SoftwareHistoryVO mine = null;
		Session session = null;
		List<SoftwareHistoryVO> historyInfoResponceTypes = new ArrayList<SoftwareHistoryVO>();
		// TODO Auto-generated method stub
		try{
			String strAssetId = historyRequestVO.getAssetNumber();
			String strCustomerId =historyRequestVO.getCustomerId();
			session = getHibernateSession();
            StringBuilder vehicleQuery = new StringBuilder();            
            vehicleQuery.append("select ROAD_NUMBER,CUSTOMER_ID,SDIS_VERSION,DEVICE_NAME,LAST_UPDATED_DATE,ACTIVE,FAULT_SOURCE_CONFIG,LAST_UPDATED_BY from gets_tools.gets_tool_CCA_DATASYNC_HDR where road_number = :assetNumber and customer_id = :customerId");
            Query vehQuery = session.createSQLQuery(vehicleQuery.toString());
            vehQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
            
            vehQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,strAssetId);
            vehQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,strCustomerId);
            List<Map<String,Object>> resultList = null;
			resultList = (List<Map<String,Object>>)vehQuery.list();
			LOG.info("Number records:"+resultList.size());
			LOG.info("Number resultList records:"+resultList);
			
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					SoftwareHistoryVO historyVO= new SoftwareHistoryVO();
					String dataSyncRd = RMDCommonUtility.convertObjectToString(reportsVO.get("ROAD_NUMBER"));
					String dataSyncHdr = RMDCommonUtility.convertObjectToString(reportsVO.get("CUSTOMER_ID"));
					String sdisVersion = RMDCommonUtility.convertObjectToString(reportsVO.get("SDIS_VERSION"));
					String deviceReporting = RMDCommonUtility.convertObjectToString(reportsVO.get("DEVICE_NAME"));
					String dataSoftwarewasReported = RMDCommonUtility.convertObjectToString(reportsVO.get("LAST_UPDATED_DATE"));
					String active = RMDCommonUtility.convertObjectToString(reportsVO.get("ACTIVE"));
					String configurationFile = RMDCommonUtility.convertObjectToString(reportsVO.get("FAULT_SOURCE_CONFIG"));
					String updatedBy = RMDCommonUtility.convertObjectToString(reportsVO.get("LAST_UPDATED_BY"));
					historyVO.setDataSyncHdr(dataSyncHdr);
					historyVO.setDataSyncRd(dataSyncRd);
					historyVO.setDeviceReporting(deviceReporting);
					historyVO.setSdisVersion(sdisVersion);
					historyVO.setUpdatedBy(updatedBy);
					historyVO.setActive(active);
					historyVO.setConfigurationFile(configurationFile);
					historyVO.setDataSoftwarewasReported(dataSoftwarewasReported);
					historyInfoResponceTypes.add(historyVO);
				}
			}
		}catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return historyInfoResponceTypes;
	}
	
	  protected void releaseSession(final Session session) {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }

}
