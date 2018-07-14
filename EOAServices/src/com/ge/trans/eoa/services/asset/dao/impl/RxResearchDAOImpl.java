package com.ge.trans.eoa.services.asset.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.asset.dao.intf.RxResearchDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class RxResearchDAOImpl extends BaseDAO implements RxResearchDAOIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3099515826583244174L;

	@Override
	public List<RxResearchEOAVO> getSearchSolution(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDDAOException {
		
		List<Object[]> resultList = null;
		Session session = null;
		RxResearchEOAVO rxResearchEOAVO = null;
		List<RxResearchEOAVO> rxResearchEOAVOList = new ArrayList<RxResearchEOAVO>();
		try {
			session = getHibernateSession();
			StringBuilder caseQryTitle = new StringBuilder();
			if(RMDCommonConstants.TITLE_INIT.equals(objRxResearchEOAVO.getSelectBy())){
				caseQryTitle.append(" SELECT RECOM.OBJID,RECOM.TITLE FROM GETS_SD.GETS_SD_RECOM RECOM WHERE RECOM.RECOM_TYPE =:rxType ");
				if(RMDCommonConstants.EQUALS.equals(objRxResearchEOAVO.getCondition())){
					caseQryTitle.append("AND upper(RECOM.TITLE) = upper(:searchVal) ");
				}
				else
				{
					caseQryTitle.append("AND upper(RECOM.TITLE) LIKE upper(:searchVal) ");
				}
				caseQryTitle.append("AND RECOM.RECOM_STATUS='Approved' ORDER BY RECOM.TITLE");
				Query caseHqryTitle = session.createSQLQuery(caseQryTitle.toString());
				caseHqryTitle.setParameter(RMDCommonConstants.RX_TYPE, objRxResearchEOAVO.getRxType());
				if(RMDCommonConstants.CONTAINS.equals(objRxResearchEOAVO.getCondition())){
					caseHqryTitle.setParameter(RMDCommonConstants.SEARCH_VAL, RMDServiceConstants.PERCENTAGE
							+ AppSecUtil.escapeLikeCharacters(objRxResearchEOAVO.getSearchVal())
							+ RMDServiceConstants.PERCENTAGE);
					}
					else if(RMDCommonConstants.EQUALS.equals(objRxResearchEOAVO.getCondition())){
						caseHqryTitle.setParameter(RMDCommonConstants.SEARCH_VAL, objRxResearchEOAVO.getSearchVal());
					}
					else if(RMDCommonConstants.STARTS_WITH.equals(objRxResearchEOAVO.getCondition())){
						caseHqryTitle.setParameter(RMDCommonConstants.SEARCH_VAL,AppSecUtil.escapeLikeCharacters(objRxResearchEOAVO.getSearchVal())
								+ RMDServiceConstants.PERCENTAGE);
					}
					else if(RMDCommonConstants.ENDS_WITH.equals(objRxResearchEOAVO.getCondition())){
						caseHqryTitle.setParameter(RMDCommonConstants.SEARCH_VAL, RMDServiceConstants.PERCENTAGE
								+ AppSecUtil.escapeLikeCharacters(objRxResearchEOAVO.getSearchVal()));
					}
				caseHqryTitle.setFetchSize(1000);
				resultList = caseHqryTitle.list();
			}
			if (resultList != null && !resultList.isEmpty()) {
				rxResearchEOAVOList = new ArrayList<RxResearchEOAVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (Object[] configDetails : resultList) {
					rxResearchEOAVO = new RxResearchEOAVO();

					rxResearchEOAVO.setRecomObjid(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
						rxResearchEOAVO.setRxTitle(RMDCommonUtility
								.convertObjectToString(configDetails[1]));
					rxResearchEOAVOList.add(rxResearchEOAVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_SOLUTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_SOLUTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return rxResearchEOAVOList;
	}

	@Override
	public List<RxResearchEOAVO> getGraphicalData(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		RxResearchEOAVO rxResearchEOAVO = null;
		List<RxResearchEOAVO> rxResearchEOAVOList = new ArrayList<RxResearchEOAVO>();
		try {
			session = getDWHibernateSession();
			StringBuilder caseQry = new StringBuilder();
				caseQry.append("SELECT c.REPAIR_CODE,count(c.REPAIR_CODE) as cnt,c.REPAIR_DESC FROM GETS_DW_EOA_CUST_FDBK a,gets_dw_eoa_case b,GETS_DW_EOA_REP_CODES c, ");
				caseQry.append("EOA_DW.GETS_DW_EOA_RECOMMENDATION recom,EOA_DW.GETS_DW_EOA_VEHICLE vehicle,EOA_DW.GETS_DW_EOA_MODEL model ");
				caseQry.append("WHERE b.case_id     = a.case_id AND a.case_id = c.CASE_ID AND a.RECOM_OBJID = recom.RECOMMENDATION_KEY AND a.CUST_FDBK2VEHICLE = vehicle.VEHICLE_KEY AND vehicle.MODEL_KEY = model.MODEL_KEY ");
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getSearchVal())){
					caseQry.append("AND a.RECOM_TITLE = :searchVal ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getStartDate())){
					caseQry.append("and rx_close_date between TO_DATE(:fromDate,'DD-MM-YYYY HH24:MI:SS') and TO_DATE(:toDate,'DD-MM-YYYY HH24:MI:SS') ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCaseType())){
					caseQry.append("and b.CASE_TYPE = :CASE_TYPE ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCustomerId())){
					caseQry.append("and a.CUSTOMER_ID =:customerId ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getFleet())){
					caseQry.append("and a.FLEET_NO = :fleet ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getModel())){
					caseQry.append("and model.MODEL_NAME =:model ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRnh())){
					caseQry.append("and a.VEHICLE_HDR=:rnh ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getIsGoodFdbk())){
					caseQry.append("and a.good_fdbk=:isGoodFdbk ");
				}
				caseQry.append("and recom.TYPE_CD ='S' group by c.REPAIR_CODE,c.REPAIR_DESC order by 2 desc");
				Query caseHqry = session.createSQLQuery(caseQry.toString());
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getSearchVal())){
					caseHqry.setParameter(RMDCommonConstants.SEARCH_VAL, objRxResearchEOAVO.getSearchVal());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getStartDate())){
					caseHqry.setParameter(RMDCommonConstants.FROM_DATE, objRxResearchEOAVO.getStartDate());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getEndDate())){
					caseHqry.setParameter(RMDCommonConstants.TO_DATE, objRxResearchEOAVO.getEndDate());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCaseType())){
					caseHqry.setParameter(RMDCommonConstants.CASE_TYPE, objRxResearchEOAVO.getCaseType());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCustomerId())){
					caseHqry.setParameter(RMDCommonConstants.CUSTOMER_ID, objRxResearchEOAVO.getCustomerId());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getFleet())){
					caseHqry.setParameter(RMDCommonConstants.FLEET, objRxResearchEOAVO.getFleet());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getModel())){
					caseHqry.setParameter(RMDCommonConstants.MODEL, objRxResearchEOAVO.getModel());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRnh())){
					caseHqry.setParameter(RMDCommonConstants.RNH, objRxResearchEOAVO.getRnh());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getIsGoodFdbk())){
					caseHqry.setParameter(RMDCommonConstants.ISGOODFDBK, objRxResearchEOAVO.getIsGoodFdbk());
				}
				caseHqry.setFetchSize(1000);
				resultList = caseHqry.list();
			if (resultList != null && !resultList.isEmpty()) {
				rxResearchEOAVOList = new ArrayList<RxResearchEOAVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (Object[] configDetails : resultList) {
					rxResearchEOAVO = new RxResearchEOAVO();

					rxResearchEOAVO.setRepairCode(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					rxResearchEOAVO.setRxCount(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					rxResearchEOAVO.setRepairDesc(ESAPI.encoder()
                            .encodeForXML(RMDCommonUtility
							.convertObjectToString(configDetails[2])));
					rxResearchEOAVOList.add(rxResearchEOAVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return rxResearchEOAVOList;
	}

	@Override
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		RxRepairDetailsVO rxRepairDetailsVO = null;
		List<RxRepairDetailsVO> rxRepairDetailsVOList = new ArrayList<RxRepairDetailsVO>();
		try {
			session = getDWHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT a.RX_CASE_ID,a.CUSTOMER_ID,a.VEHICLE_NO,a.CASE_SUCCESS,c.REPAIR_CODE,a.miss_code,a.good_fdbk,a.RECOM_TITLE,");
			caseQry.append("a.RECOM_URGENCY,b.CASE_TYPE,a.RX_OPEN_DATE,a.RX_CLOSE_DATE,a.RX_FEEDBACK,recom.TYPE_CD,a.FLEET_NO,model.MODEL_NAME,a.VEHICLE_HDR,c.REPAIR_DESC ");
			caseQry.append("FROM GETS_DW_EOA_CUST_FDBK a,gets_dw_eoa_case b,GETS_DW_EOA_REP_CODES c,EOA_DW.GETS_DW_EOA_RECOMMENDATION recom,");
			caseQry.append("EOA_DW.GETS_DW_EOA_VEHICLE vehicle,EOA_DW.GETS_DW_EOA_MODEL model WHERE b.case_id     = a.case_id  AND a.case_id = c.CASE_ID ");
			caseQry.append("AND a.RECOM_OBJID = recom.RECOMMENDATION_KEY AND a.CUST_FDBK2VEHICLE = vehicle.VEHICLE_KEY AND vehicle.MODEL_KEY = model.MODEL_KEY ");
			if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getInitLoad()) && !RMDCommonConstants.Y_LETTER_UPPER.equals(objRxResearchEOAVO.getInitLoad()))
			{
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRepairCode())){
					caseQry.append("AND c.REPAIR_CODE = :repairCode ");
				}
			}
			
			if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getSearchVal())){
					caseQry.append("AND a.RECOM_TITLE = :searchVal ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getStartDate())){
					caseQry.append("and rx_close_date between TO_DATE(:fromDate,'DD-MM-YYYY HH24:MI:SS') and TO_DATE(:toDate,'DD-MM-YYYY HH24:MI:SS') ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCaseType())){
					caseQry.append("and b.CASE_TYPE = :CASE_TYPE ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCustomerId())){
					caseQry.append("and a.CUSTOMER_ID =:customerId ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getFleet())){
					caseQry.append("and a.FLEET_NO = :fleet ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getModel())){
					caseQry.append("and model.MODEL_NAME =:model ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRnh())){
					caseQry.append("and a.VEHICLE_HDR=:rnh ");
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getIsGoodFdbk())){
					caseQry.append("and a.good_fdbk=:isGoodFdbk ");
				}
				Query caseHqry = session.createSQLQuery(caseQry.toString());
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRepairCode())){
					caseHqry.setParameter(RMDCommonConstants.REPAIRCODE, objRxResearchEOAVO.getRepairCode());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getSearchVal())){
					caseHqry.setParameter(RMDCommonConstants.SEARCH_VAL, objRxResearchEOAVO.getSearchVal());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getStartDate())){
					caseHqry.setParameter(RMDCommonConstants.FROM_DATE, objRxResearchEOAVO.getStartDate());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getEndDate())){
					caseHqry.setParameter(RMDCommonConstants.TO_DATE, objRxResearchEOAVO.getEndDate());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCaseType())){
					caseHqry.setParameter(RMDCommonConstants.CASE_TYPE, objRxResearchEOAVO.getCaseType());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getCustomerId())){
					caseHqry.setParameter(RMDCommonConstants.CUSTOMER_ID, objRxResearchEOAVO.getCustomerId());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getFleet())){
					caseHqry.setParameter(RMDCommonConstants.FLEET, objRxResearchEOAVO.getFleet());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getModel())){
					caseHqry.setParameter(RMDCommonConstants.MODEL, objRxResearchEOAVO.getModel());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getRnh())){
					caseHqry.setParameter(RMDCommonConstants.RNH, objRxResearchEOAVO.getRnh());
				}
				if(!RMDCommonUtility.isNullOrEmpty(objRxResearchEOAVO.getIsGoodFdbk())){
					caseHqry.setParameter(RMDCommonConstants.ISGOODFDBK, objRxResearchEOAVO.getIsGoodFdbk());
				}
				caseHqry.setFetchSize(1000);
				resultList = caseHqry.list();
			if (resultList != null && !resultList.isEmpty()) {
				rxRepairDetailsVOList = new ArrayList<RxRepairDetailsVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (Object[] configDetails : resultList) {
					rxRepairDetailsVO = new RxRepairDetailsVO();

					rxRepairDetailsVO.setRxCaseID(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					rxRepairDetailsVO.setCustomerId(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					rxRepairDetailsVO.setVehicleNo(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					rxRepairDetailsVO.setCaseSuccess(RMDCommonUtility
							.convertObjectToString(configDetails[3]));
					rxRepairDetailsVO.setRepairCode(RMDCommonUtility
							.convertObjectToString(configDetails[4]));
					rxRepairDetailsVO.setMissCode(RMDCommonUtility
							.convertObjectToString(configDetails[5]));
					rxRepairDetailsVO.setGoodFdbk(ESAPI.encoder()
                            .encodeForXML(RMDCommonUtility
							.convertObjectToString(configDetails[6])));
					rxRepairDetailsVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(configDetails[7]));
					rxRepairDetailsVO.setRxUrgency(RMDCommonUtility
							.convertObjectToString(configDetails[8]));
					rxRepairDetailsVO.setCaseType(RMDCommonUtility
							.convertObjectToString(configDetails[9]));
					rxRepairDetailsVO.setRxOpenDate(RMDCommonUtility
							.convertObjectToString(configDetails[10]));
					rxRepairDetailsVO.setRxCloseDate(RMDCommonUtility
							.convertObjectToString(configDetails[11]));
					rxRepairDetailsVO.setRxFeedback(ESAPI.encoder()
                            .encodeForXML(RMDCommonUtility
							.convertObjectToString(configDetails[12])));
					rxRepairDetailsVO.setRepairCodeDesc(ESAPI.encoder()
                            .encodeForXML(RMDCommonUtility
							.convertObjectToString(configDetails[17])));
					rxRepairDetailsVOList.add(rxRepairDetailsVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return rxRepairDetailsVOList;
	}

}
