package com.ge.trans.eoa.services.heatmap.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.heatmap.dao.intf.HeatMapDAOIntf;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.FaultHeatMapVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.FaultVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapResponseVO;
import com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.SelectRxEoaDAOIntf;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.cases.valueobjects.RxWorstUrgencyVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class HeatMapDAOImpl extends BaseDAO implements HeatMapDAOIntf {
 
    SelectRxEoaDAOIntf objSelectRxDAOIntf;
    
	/**
     * @param objSelectRxDAOIntf the objSelectRxDAOIntf to set
     */
    public void setObjSelectRxDAOIntf(SelectRxEoaDAOIntf objSelectRxDAOIntf) {
        this.objSelectRxDAOIntf = objSelectRxDAOIntf;
    }

    private static final RMDLogger heatMapDaoLog = RMDLoggerHelper
			.getLogger(HeatMapDAOImpl.class);	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.eoa.services.heatmap.dao.intf.HeatMapDAOIntf#getHeatMapModels
	 * (com.ge.trans.eoa.services.heatmap.service.valueobjects.HeatMapSearchVO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HeatMapResponseVO getHeatMapModels(HeatMapSearchVO heatmapSearchVO)
			throws RMDDAOException {
		Session objSession = null;
		HeatMapResponseVO heatMapRespVO = null;

		List<ModelVO> modelVOList = new ArrayList<ModelVO>();
		try {
			objSession = getDWHibernateSession();
			String modelQry = "SELECT DISTINCT MODEL,MODEL_NAME FROM GETS_DW_EOA_HEATMAP_VEHICLE_MV WHERE VEH_HDR_CUST = :customer";
			Query modelHqry = objSession.createSQLQuery(modelQry).setParameter(
					RMDCommonConstants.CUSTOMER,
					heatmapSearchVO.getCustomerId());

			List<Object[]> arlResults = modelHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {
				heatMapRespVO = new HeatMapResponseVO();
				for (Object[] modelObj : arlResults) {
					ModelVO modelVO = new ModelVO();
					modelVO.setModelObjId(RMDCommonUtility
							.convertObjectToString(modelObj[0]));
					modelVO.setModelName(RMDCommonUtility
							.convertObjectToString(modelObj[1]));
					modelVOList.add(modelVO);
				}				
				heatMapRespVO.setModels(modelVOList);
			}

		} catch (RMDDAOConnectionException ex) {
			heatMapDaoLog.error(ex, ex);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			heatMapDaoLog.error(
					"Exception occurred in getHeatMapModels method:", e);

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return heatMapRespVO;

	}

	@SuppressWarnings("unchecked")
	@Override
	public HeatMapResponseVO getHeatMapAssetHeaders(
			HeatMapSearchVO heatmapSearchVO) throws RMDDAOException {
		List<AssetVO> assetVOList = new ArrayList<AssetVO>();
		Session objSession = null;
		HeatMapResponseVO heatMapRespVO = null;
		try {
			objSession = getDWHibernateSession();
			StringBuilder assetQry = new StringBuilder();
			assetQry.append("SELECT DISTINCT VEH_HDR FROM GETS_DW_EOA_HEATMAP_VEHICLE_MV WHERE VEH_HDR_CUST = :customer AND MODEL IN (:model)");

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst()))
				assetQry.append(" AND SERIAL_NO IN(:assetNumber)");

			Query assetHqry = objSession.createSQLQuery(assetQry.toString());
			assetHqry.setParameter(RMDCommonConstants.CUSTOMER,
					heatmapSearchVO.getCustomerId());

			assetHqry.setParameterList(RMDCommonConstants.MODEL,
					heatmapSearchVO.getModelLst());

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst()))
				assetHqry.setParameterList(RMDCommonConstants.ASSET_NUMBER,
						heatmapSearchVO.getAssetNumLst());

			List<String> arlResults = assetHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {
				heatMapRespVO = new HeatMapResponseVO();
				for (Object assetObj : arlResults) {
					AssetVO assetVO = new AssetVO();
					assetVO.setRnh(RMDCommonUtility
							.convertObjectToString(assetObj));
					assetVOList.add(assetVO);
				}				
				heatMapRespVO.setAssets(assetVOList);
			}

		} catch (RMDDAOConnectionException ex) {
			heatMapDaoLog.error(ex, ex);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			heatMapDaoLog.error(
					"Exception occurred in getHeatMapAssetHeaders method:", e);

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return heatMapRespVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeatMapResponseVO getHeatMapAssetNumbers(
			HeatMapSearchVO heatmapSearchVO) throws RMDDAOException {
		List<AssetVO> assetVOList = new ArrayList<AssetVO>();
		Session objSession = null;
		HeatMapResponseVO heatMapRespVO = null;
		try {
			objSession = getDWHibernateSession();
			StringBuilder assetQry = new StringBuilder();
			assetQry.append("SELECT DISTINCT SERIAL_NO FROM GETS_DW_EOA_HEATMAP_VEHICLE_MV WHERE VEH_HDR_CUST = :customer AND MODEL IN (:model)");

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst()))
				assetQry.append(" AND VEH_HDR IN(:roadHeader)");
			
			Query assetHqry = objSession.createSQLQuery(assetQry.toString());
			assetHqry.setParameter(RMDCommonConstants.CUSTOMER,
					heatmapSearchVO.getCustomerId());

			assetHqry.setParameterList(RMDCommonConstants.MODEL,
					heatmapSearchVO.getModelLst());

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetHeaderLst()))
				assetHqry.setParameterList(RMDCommonConstants.ROAD_HEADER,
						heatmapSearchVO.getAssetHeaderLst());

			List<String> arlResults = assetHqry.list();
			if (null != arlResults && !arlResults.isEmpty()) {
				heatMapRespVO = new HeatMapResponseVO();
				for (Object assetObj : arlResults) {
					AssetVO assetVO = new AssetVO();
					assetVO.setRoadNumber(RMDCommonUtility
							.convertObjectToString(assetObj));
					assetVOList.add(assetVO);
				}
			
				heatMapRespVO.setAssets(assetVOList);
			}

		} catch (RMDDAOConnectionException ex) {
			heatMapDaoLog.error(ex, ex);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			heatMapDaoLog.error(
					"Exception occurred in getHeatMapAssetNumbers method:", e);

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return heatMapRespVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeatMapResponseVO getHeatMapFaults(HeatMapSearchVO heatmapSearchVO)
			throws RMDDAOException {
		Session objSession = null;
		HeatMapResponseVO heatMapRespVO = null;
		List<FaultVO> faultVOList = new ArrayList<FaultVO>();
		try {
			objSession = getDWHibernateSession();
			StringBuilder faultQry = new StringBuilder();
			faultQry.append("SELECT DISTINCT VEH_HDR_CUST,FAULT_CODE,FAULT_DESCRIPTION,SUB_ID FROM GETS_DW_EOA_FAULT_MV WHERE VEH_HDR_CUST = :customer AND MODEL IN (:model)");

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst()))
				faultQry.append(" AND SERIAL_NO IN(:assetNumber)");

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetHeaderLst()))
				faultQry.append(" AND VEH_HDR IN(:roadHeader)");

			if (!RMDCommonUtility.isNullOrEmpty(heatmapSearchVO.getNoOfDays())) {
				faultQry.append(" AND OCCUR_DATE > SYSDATE - (:noDays) AND OCCUR_DATE < SYSDATE");
			} else
				faultQry.append(" AND OCCUR_DATE BETWEEN TO_DATE(:fromDate,'DD/MM/YYYY HH24:MI:SS')  AND TO_DATE(:toDate,'DD/MM/YYYY HH24:MI:SS')");
		
			if (!RMDCommonUtility.isNullOrEmpty(heatmapSearchVO
					.getIsNonGPOCUser())
					&& RMDCommonConstants.YES.equalsIgnoreCase(heatmapSearchVO
							.getIsNonGPOCUser())) {
				faultQry.append(" AND DIAGNOSTIC_WEIGHT!=3");
			}
			
			Query assetHqry = objSession.createSQLQuery(faultQry.toString());
			
			assetHqry.setParameter(RMDCommonConstants.CUSTOMER,
					heatmapSearchVO.getCustomerId());

			assetHqry.setParameterList(RMDCommonConstants.MODEL,
					heatmapSearchVO.getModelLst());

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst()))
				assetHqry.setParameterList(RMDCommonConstants.ASSET_NUMBER,
						heatmapSearchVO.getAssetNumLst());

			if (!RMDCommonUtility.isNullOrEmpty(heatmapSearchVO.getNoOfDays())) {
				assetHqry.setParameter(RMDCommonConstants.NO_OF_DAYS,
						heatmapSearchVO.getNoOfDays());
			} else {
				assetHqry.setParameter(RMDCommonConstants.FROM_DATE,
						heatmapSearchVO.getFromDate());

				assetHqry.setParameter(RMDCommonConstants.TO_DATE,
						heatmapSearchVO.getToDate());
			}

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetHeaderLst()))
				assetHqry.setParameterList(RMDCommonConstants.ROAD_HEADER,
						heatmapSearchVO.getAssetHeaderLst());

			List<Object[]> arlResults = assetHqry.list();
			if (null != arlResults && !arlResults.isEmpty()) {
				heatMapRespVO = new HeatMapResponseVO();
				for (Object[] objFaultVO : arlResults) {
					FaultVO faultVO = new FaultVO();
					faultVO.setCustomerId(RMDCommonUtility
							.convertObjectToString(objFaultVO[0]));
					faultVO.setStrFaultCode(RMDCommonUtility
							.convertObjectToString(objFaultVO[1]));
					faultVO.setStrFaultDescription(RMDCommonUtility
							.convertObjectToString(objFaultVO[2]));
					faultVO.setStrSubId(RMDCommonUtility
							.convertObjectToString(objFaultVO[3]));
					faultVOList.add(faultVO);

				}				
				heatMapRespVO.setFaults(faultVOList);
			}

		} catch (RMDDAOConnectionException ex) {
			heatMapDaoLog.error(ex, ex);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			heatMapDaoLog.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return heatMapRespVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeatMapResponseVO getHeatMapFilterFaults(
			HeatMapSearchVO heatmapSearchVO) throws RMDDAOException {
		List<FaultHeatMapVO> faultHeatMapVOList = new ArrayList<FaultHeatMapVO>();
		Session objSession = null;
		HeatMapResponseVO heatMapRespVO = null;
		Map<String, String> assetUrgencyMap = new HashMap<String, String>();
		
		try {
		    RxUrgencyParamVO rxUrgencyParamVO = new RxUrgencyParamVO();
		    rxUrgencyParamVO.setCustomerId(heatmapSearchVO.getCustomerId());
		    List<RxWorstUrgencyVO> rxWorstUrgencyVOLst = objSelectRxDAOIntf.getWorstUrgency(rxUrgencyParamVO);		    
		    if(RMDCommonUtility.isCollectionNotEmpty(rxWorstUrgencyVOLst)){
		        for(RxWorstUrgencyVO rxWorstUrgencyVO:rxWorstUrgencyVOLst){
		            assetUrgencyMap.put(rxWorstUrgencyVO.getAssetNumber(), rxWorstUrgencyVO.getWorstUrgency());
		        }
		    }
			objSession = getDWHibernateSession();
			StringBuilder faultQry = new StringBuilder();
			faultQry.append("SELECT DISTINCT VEH_HDR_CUST,FAULT_CODE,FAULT_DESCRIPTION,SUB_ID,CUST_NAME,MODEL,MODEL_NAME,TO_CHAR(OCCUR_DATE,'MM/DD/YYYY HH24:MI:SS') OCCUR_DATE,TO_CHAR(OFFBOARD_LOAD_DATE,'MM/DD/YYYY HH24:MI:SS'),SERIAL_NO,LATITUDEDECIMAL,LONGITUDEDECIMAL,VEH_HDR,ASSET FROM GETS_DW_EOA_FAULT_MV WHERE VEH_HDR_CUST = :customer AND MODEL IN (:model)"
					+ " AND FAULT_CODE IN (:faultCode)");

			if (!RMDCommonUtility.isNullOrEmpty(heatmapSearchVO.getNoOfDays())) {
				faultQry.append(" AND OCCUR_DATE > SYSDATE - :noDays AND OCCUR_DATE < SYSDATE");
			} else
				faultQry.append(" AND OCCUR_DATE BETWEEN TO_DATE(:fromDate,'DD/MM/YYYY HH24:MI:SS')  AND TO_DATE(:toDate,'DD/MM/YYYY HH24:MI:SS')");

			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetHeaderLst())) {
				faultQry.append(" AND VEH_HDR IN (:roadHeader)");
			}
			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst())) {
				faultQry.append(" AND SERIAL_NO IN (:assetNumber)");
			}			
			Query faultHQry = objSession.createSQLQuery(faultQry.toString());
			faultHQry.setParameter(RMDCommonConstants.CUSTOMER,
					heatmapSearchVO.getCustomerId());

			faultHQry.setParameterList(RMDCommonConstants.MODEL,
					heatmapSearchVO.getModelLst());
			if (!RMDCommonUtility.isNullOrEmpty(heatmapSearchVO.getNoOfDays())) {
				faultHQry.setParameter(RMDCommonConstants.NO_OF_DAYS,
						heatmapSearchVO.getNoOfDays());
			} else {
				faultHQry.setParameter(RMDCommonConstants.FROM_DATE,
						heatmapSearchVO.getFromDate());

				faultHQry.setParameter(RMDCommonConstants.TO_DATE,
						heatmapSearchVO.getToDate());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetHeaderLst())) {
				faultHQry.setParameterList(RMDCommonConstants.ROAD_HEADER,
						heatmapSearchVO.getAssetHeaderLst());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getAssetNumLst())) {
				faultHQry.setParameterList(RMDCommonConstants.ASSET_NUMBER,
						heatmapSearchVO.getAssetNumLst());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(heatmapSearchVO
					.getFaultCodeLst())) {
				faultHQry.setParameterList(RMDCommonConstants.FAULTSCODE,
						heatmapSearchVO.getFaultCodeLst());
			}

			List<Object[]> arlResults = faultHQry.list();
			if (null != arlResults && !arlResults.isEmpty()) {
				heatMapRespVO = new HeatMapResponseVO();
				for (Object[] objFaultVO : arlResults) {  			  
				            FaultHeatMapVO faultHeatMapVO = new FaultHeatMapVO();
	                        faultHeatMapVO.setStrCustomerId(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[0]));
	                        faultHeatMapVO.setStrFaultCode(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[1]));
	                        faultHeatMapVO.setStrFaultDescription(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[2]));
	                        faultHeatMapVO.setStrSubId(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[3]));
	                        faultHeatMapVO.setStrCustomerName(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[4]));
	                        faultHeatMapVO.setStrModelId(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[5]));
	                        faultHeatMapVO.setStrModelName(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[6]));
	                        faultHeatMapVO.setOccurDate(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[7]));
	                        faultHeatMapVO.setOffBoardLoadDate(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[8]));
                            if (assetUrgencyMap.containsKey(RMDCommonUtility
                                    .convertObjectToString(objFaultVO[9]))) {
                                faultHeatMapVO.setWorstUrgency(assetUrgencyMap
                                        .get(RMDCommonUtility
                                                .convertObjectToString(objFaultVO[9])));
                            }
	                        faultHeatMapVO.setStrSerialNo(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[9]));
	                        faultHeatMapVO.setStrGPSLatitude(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[10]));
	                        faultHeatMapVO.setStrGPSLongitude(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[11]));
	                        faultHeatMapVO.setStrAssetNumber(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[13]));
	                        faultHeatMapVO.setStrAssetHeader(RMDCommonUtility
	                                .convertObjectToString(objFaultVO[12]));
	                        faultHeatMapVOList.add(faultHeatMapVO); 
				       }			        
				    					
							
				
				heatMapRespVO.setFaultHeatMapVO(faultHeatMapVOList);
			}

		} catch (RMDDAOConnectionException ex) {
			heatMapDaoLog.error(ex, ex);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			heatMapDaoLog.error("Exception occurred:", e);

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return heatMapRespVO;
	}

}
