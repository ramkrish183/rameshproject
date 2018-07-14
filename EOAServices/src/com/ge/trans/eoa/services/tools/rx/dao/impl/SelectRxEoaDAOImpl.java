package com.ge.trans.eoa.services.tools.rx.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.SelectRxEoaDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.ActionableRxTypeVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxConfigVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultEoaLiteServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.cases.valueobjects.RxWorstUrgencyVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RecomDelvStatusVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCustomerRxGroupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCustomerRxTitleHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@SuppressWarnings("unchecked")
public class SelectRxEoaDAOImpl extends BaseDAO implements SelectRxEoaDAOIntf {

	private static final long serialVersionUID = -1488197388000549636L;

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(SelectRxEoaDAOImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.cases.dao.intf.RecommDAOIntf#searchRecomm(com
	 * .ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
	 * This method is used to fetch the advisory search list from database in
	 * select advisory screen based on search criteria provided
	 */
	@Override
	public List<RxSearchResultServiceVO> searchRecomm(
			RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		List<RxSearchResultServiceVO> recommList = null;
		RxSearchResultServiceVO objSolutionListVO = null;
		Query displayNameQuery = null;
		String strRxModel = RMDCommonConstants.EMPTY_STRING;
		String recomStatus = RMDCommonConstants.EMPTY_STRING;
		String strTempModel = RMDCommonConstants.EMPTY_STRING;
		String strRxId = RMDCommonConstants.EMPTY_STRING;
		List<String> arlRxId = new ArrayList<String>();
		String strRecomType = null;
		String strRecomTitle = null;
		Map<String, String> modelsMap = null;
		Map<String, List<String>> vehBomMap = null;
		try {
			LOG.debug("**** searchRecomm SelectRxDAOImpl BEGIN "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));
			hibernateSession = getHibernateSession();
			
			displayNameQuery = getSolutionsQuery(rxSearchCriteriaServiceVO,
					hibernateSession);
			displayNameQuery.setFetchSize(500);
			List<Object> selectList = (List<Object>) displayNameQuery.list();
			LOG.debug("searchRecomm SelectRxDAOImpl EXECUTED QUERY "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));
			// Added by Vamshi for MassApply Rx Requirement
			if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
					.getIsMassApplyRx())
					&& (null != rxSearchCriteriaServiceVO.getStrRxModel())
					&& rxSearchCriteriaServiceVO.getStrRxModel().length > 0) {
				modelsMap = getModelsForRx(rxSearchCriteriaServiceVO);
			}
			
			
			/*if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
					.getAddRxApply())
					&& (null != rxSearchCriteriaServiceVO.getStrRxModel())
					&& rxSearchCriteriaServiceVO.getStrRxModel().length > 0) {
				modelsMap = getModelsForRx(rxSearchCriteriaServiceVO);
			}*/

			if (selectList != null && !selectList.isEmpty()) {
				recommList = new ArrayList<RxSearchResultServiceVO>();
				objSolutionListVO = new RxSearchResultServiceVO();
				
				if((RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
		                .getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
		                        .getAddRxApply())) && !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getCustomer())){
	                vehBomMap=getVehBomDetails(rxSearchCriteriaServiceVO);
	            }
				
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] solutionRec = (Object[]) lookValueIter.next();
					if((RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
	                        .getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
	                                .getAddRxApply())) && !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getCustomer())){
					String rxBomType = RMDCommonUtility.convertObjectToString(solutionRec[20]);
					String rxBomVal = RMDCommonUtility.convertObjectToString(solutionRec[21]);
					String operator =RMDCommonUtility.convertObjectToString(solutionRec[22]);
					if(null != vehBomMap){
					    if(!RMDCommonUtility.isNullOrEmpty(rxBomType)){
					        if(vehBomMap.containsKey(rxBomType)){
					            if(!RMDCommonUtility.isNullOrEmpty(operator)){
					                    boolean matchFound=false;
					                    if((RMDCommonConstants.EQUAL_SYMBOL.equals(operator) && vehBomMap.get(rxBomType).contains(rxBomVal)) || (RMDCommonConstants.NOT_EQUAL_SYMBOL.equals(operator) && !vehBomMap.get(rxBomType).contains(rxBomVal))){
                                            matchFound=true;
                                        }else if(RMDCommonConstants.GREATER_THAN_SYMBOL.equals(operator)){
					                        List<String> values = vehBomMap.get(rxBomType);
					                        for(String val : values){
					                            try{
    					                            if(Float.valueOf(val)>Float.valueOf(rxBomVal)){
    					                                matchFound=true;
    					                                break;
    					                            }	
					                            }catch(NumberFormatException e){
					                                LOG.error("Number formate Exception "+e);
					                            }
                                            }
					                    }else if(RMDCommonConstants.LESS_THAN_SYMBOL.equals(operator)){
                                            List<String> values = vehBomMap.get(rxBomType);
                                            for(String val : values){
                                                try{
                                                    if(Float.valueOf(val)<Float.valueOf(rxBomVal)){
                                                        matchFound=true;
                                                        break;
                                                    }   
                                                }catch(NumberFormatException e){
                                                    LOG.error("Number formate Exception "+e);
                                                }                                                                                                 
                                            }                                        
                                        }else if(RMDCommonConstants.GREATER_THAN_EQUAL_SYMBOL.equals(operator)){
                                            List<String> values = vehBomMap.get(rxBomType);
                                            for(String val : values){
                                                try{
                                                    if(Float.valueOf(val)>=Float.valueOf(rxBomVal)){
                                                        matchFound=true;
                                                        break;
                                                    }   
                                                }catch(NumberFormatException e){
                                                    LOG.error("Number formate Exception "+e);
                                                }                                                                                                 
                                            }                                        
                                        } else if(RMDCommonConstants.LESS_THAN_EQUAL_SYMBOL.equals(operator)){
                                            List<String> values = vehBomMap.get(rxBomType);
                                            for(String val : values){
                                                try{
                                                    if(Float.valueOf(val)<=Float.valueOf(rxBomVal)){
                                                        matchFound=true;
                                                        break;
                                                    }   
                                                }catch(NumberFormatException e){
                                                    LOG.error("Number formate Exception "+e);
                                                }                                                                                                 
                                            }                                        
                                        }else
                                            continue;
					                    
					                    if(!matchFound)
					                        continue;
					            }
					        }else{
					            continue;
					        }
					    }
					}
					}
					strRxId = RMDCommonUtility
							.convertObjectToString(solutionRec[0]);
					strRecomTitle = RMDCommonUtility
							.convertObjectToString(solutionRec[1]);
					if (!arlRxId.isEmpty()) {
						if (!arlRxId.contains(strRxId)) {
							// dont add rx id
							arlRxId.add(strRxId);
							objSolutionListVO = new RxSearchResultServiceVO();
							strRxModel = RMDCommonConstants.EMPTY_STRING;
							// Multinlingual Title
							if (null != AppSecUtil
									.decodeString(RMDCommonUtility
											.convertObjectToString(solutionRec[18]))
									&& !AppSecUtil
											.decodeString(
													RMDCommonUtility
															.convertObjectToString(solutionRec[18]))
											.equals("")) {
								objSolutionListVO
										.setStrTitle(AppSecUtil
												.stripNonValidXMLCharactersForKM(AppSecUtil
														.decodeString(RMDCommonUtility
																.convertObjectToString(solutionRec[18]))));
							} else {
								objSolutionListVO
										.setStrTitle(AppSecUtil
												.stripNonValidXMLCharactersForKM(AppSecUtil
														.decodeString(RMDCommonUtility
																.convertObjectToString(solutionRec[1]))));
							}
							objSolutionListVO.setStrRxId(RMDCommonUtility
									.convertObjectToString(solutionRec[0]));
							recomStatus = RMDCommonUtility
									.convertObjectToString(solutionRec[2]);
							if (RMDServiceConstants.APPROVED
									.equals(recomStatus)) {
								objSolutionListVO
										.setStrStatusValue(RMDServiceConstants.APPROVED);
							} else if (RMDServiceConstants.INPROCESS
									.equals(recomStatus)) {
								objSolutionListVO
										.setStrStatusValue(RMDServiceConstants.DRAFT);
							} else {
								objSolutionListVO
										.setStrStatusValue(recomStatus);
							}
							strRecomType = RMDCommonUtility
									.convertObjectToString(solutionRec[3]);
							if (!RMDCommonUtility.isNullOrEmpty(strRecomType)) {
								if (!strRecomType
										.equalsIgnoreCase(RMDServiceConstants.NONE)) {
									objSolutionListVO.setStrType(strRecomType);
								}
							}
							objSolutionListVO
									.setStrUrgRepairValue(RMDCommonUtility
											.convertObjectToString(solutionRec[4]));
							objSolutionListVO
									.setStrEstRepairTimeValue(RMDCommonUtility
											.convertObjectToString(solutionRec[5]));
							objSolutionListVO.setStrVersion(RMDCommonUtility
									.convertObjectToString(solutionRec[6]));
							// Added RX Multilingual Locoimpact
							if (null != RMDCommonUtility
									.convertObjectToString(solutionRec[19])
									&& !RMDCommonUtility.convertObjectToString(
											solutionRec[19]).equals("")) {
								objSolutionListVO
										.setStrLocoImpact(RMDCommonUtility
												.convertObjectToString(solutionRec[19]));
							} else {
								objSolutionListVO
										.setStrLocoImpact(RMDCommonUtility
												.convertObjectToString(solutionRec[7]));
							}

							objSolutionListVO.setLastEdited(RMDCommonUtility
									.convertObjectToString(solutionRec[8]));
							objSolutionListVO.setStrAuthor(RMDCommonUtility
									.convertObjectToString(solutionRec[9]));
							objSolutionListVO.setStrRank(RMDCommonUtility
									.convertObjectToString(solutionRec[12]));
							objSolutionListVO.setStrPrecision(RMDCommonUtility
									.convertObjectToString(solutionRec[13]));
							objSolutionListVO.setStrDeliveries(RMDCommonUtility
									.convertObjectToString(solutionRec[14]));
							objSolutionListVO.setStrAccuracy(RMDCommonUtility
									.convertObjectToString(solutionRec[15]));
							objSolutionListVO
									.setStrReIssuePercent(RMDCommonUtility
											.convertObjectToString(solutionRec[16]));
							objSolutionListVO.setStrNTFPercent(RMDCommonUtility
									.convertObjectToString(solutionRec[17]));
							recommList.add(objSolutionListVO);
						}
					} else {
						if (null != AppSecUtil.decodeString(RMDCommonUtility
								.convertObjectToString(solutionRec[18]))
								&& !AppSecUtil
										.decodeString(
												RMDCommonUtility
														.convertObjectToString(solutionRec[18]))
										.equals("")) {
							objSolutionListVO
									.setStrTitle(AppSecUtil
											.stripNonValidXMLCharactersForKM(AppSecUtil
													.decodeString(RMDCommonUtility
															.convertObjectToString(solutionRec[18]))));
						} else {
							objSolutionListVO
									.setStrTitle(AppSecUtil
											.stripNonValidXMLCharactersForKM(AppSecUtil
													.decodeString(RMDCommonUtility
															.convertObjectToString(solutionRec[1]))));
						}
						objSolutionListVO.setStrRxId(RMDCommonUtility
								.convertObjectToString(solutionRec[0]));

						recomStatus = RMDCommonUtility
								.convertObjectToString(solutionRec[2]);
						if (RMDServiceConstants.APPROVED.equals(recomStatus)) {
							objSolutionListVO
									.setStrStatusValue(RMDServiceConstants.APPROVED);
						} else if (RMDServiceConstants.INPROCESS
								.equals(recomStatus)) {
							objSolutionListVO
									.setStrStatusValue(RMDServiceConstants.DRAFT);
						} else {
							objSolutionListVO.setStrStatusValue(recomStatus);
						}
						strRecomType = RMDCommonUtility
								.convertObjectToString(solutionRec[3]);
						if (!RMDCommonUtility.isNullOrEmpty(strRecomType)) {
							if (!strRecomType
									.equalsIgnoreCase(RMDServiceConstants.NONE)) {
								objSolutionListVO.setStrType(strRecomType);
							}
						}
						objSolutionListVO.setStrUrgRepairValue(RMDCommonUtility
								.convertObjectToString(solutionRec[4]));
						objSolutionListVO
								.setStrEstRepairTimeValue(RMDCommonUtility
										.convertObjectToString(solutionRec[5]));
						objSolutionListVO.setStrVersion(RMDCommonUtility
								.convertObjectToString(solutionRec[6]));
						// Added RX Multilingual Locoimpact
						if (null != RMDCommonUtility
								.convertObjectToString(solutionRec[19])
								&& !RMDCommonUtility.convertObjectToString(
										solutionRec[19]).equals("")) {
							objSolutionListVO.setStrLocoImpact(RMDCommonUtility
									.convertObjectToString(solutionRec[19]));
						} else {
							objSolutionListVO.setStrLocoImpact(RMDCommonUtility
									.convertObjectToString(solutionRec[7]));
						}
						objSolutionListVO.setLastEdited(RMDCommonUtility
								.convertObjectToString(solutionRec[8]));
						objSolutionListVO.setStrAuthor(RMDCommonUtility
								.convertObjectToString(solutionRec[9]));
						objSolutionListVO.setStrRank(RMDCommonUtility
								.convertObjectToString(solutionRec[12]));
						objSolutionListVO.setStrPrecision(RMDCommonUtility
								.convertObjectToString(solutionRec[13]));
						objSolutionListVO.setStrDeliveries(RMDCommonUtility
								.convertObjectToString(solutionRec[14]));
						objSolutionListVO.setStrAccuracy(RMDCommonUtility
								.convertObjectToString(solutionRec[15]));
						objSolutionListVO.setStrReIssuePercent(RMDCommonUtility
								.convertObjectToString(solutionRec[16]));
						objSolutionListVO.setStrNTFPercent(RMDCommonUtility
								.convertObjectToString(solutionRec[17]));
						recommList.add(objSolutionListVO);
						arlRxId.add(strRxId);
					}
					// Logic For Showing All Model Names For An Rx In
					// MassApplyRx Screen
					if (RMDCommonConstants.LETTER_Y
							.equals(rxSearchCriteriaServiceVO
									.getIsMassApplyRx())
							&& (null != rxSearchCriteriaServiceVO
									.getStrRxModel())
							&& rxSearchCriteriaServiceVO.getStrRxModel().length > 0) {
						if (modelsMap.containsKey(strRxId)) {
							objSolutionListVO.setStrModel(modelsMap
									.get(strRxId));
						} else {
							objSolutionListVO
									.setStrModel(RMDCommonConstants.BLANK_SPACE);
						}

					} else {
						// Logic to build the comma separated values
						strTempModel = RMDCommonUtility
								.convertObjectToString(solutionRec[10]);
						strRxModel += RMDCommonConstants.BLANK_SPACE
								+ strTempModel
								+ RMDCommonConstants.COMMMA_SEPARATOR;
						if (!RMDCommonUtility.isNullOrEmpty(strRxModel)) {
							objSolutionListVO.setStrModel(strRxModel.substring(
									0, strRxModel.length() - 1));
						}
					}
				}
			}
			LOG.debug("****searchRecomm SelectRxDAOImpl ITERATION DONE "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
			objSolutionListVO = null;
			arlRxId = null;
		}
		LOG.debug("searchRecomm SelectRxDAOImpl END "
				+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
						.format(new Date()));
		return recommList;
	}

	/**
	 * This method is used for fetching RxTitles of Fleet screen as part of OMD
	 * Performance
	 * 
	 * @param strLanguage
	 *            ,solutionStatus
	 * @return List<RxSearchResultEoaLiteServiceVO>
	 * @throws RMDDAOException
	 */
	@Override
	public List<RxSearchResultEoaLiteServiceVO> getRxTitlesLite(
			String strLanguage, String solutionStatus) throws RMDDAOException {
		Session hibernateSession = null;
		Session langHibernateSession = null;
		List<RxSearchResultEoaLiteServiceVO> recommList = null;
		RxSearchResultEoaLiteServiceVO objSolutionListVO = null;
		Query displayNameQuery = null;
		Query langDisplayNameQuery = null;
		Boolean selectEngVal = false;
		List<Object> selectList = new ArrayList<Object>();
		try {
			LOG.debug("**** getRxTitlesLite SelectRxDAOImpl BEGIN "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));
			if (!strLanguage.equalsIgnoreCase("en")) {
				langHibernateSession = getHibernateSession();
				langDisplayNameQuery = langHibernateSession
						.createSQLQuery(
								"SELECT RECOM.OBJID , recom_ut8.trans_recom_title_txt FROM GETS_SD.GETS_SD_RECOM RECOM , gets_sd_recom_ut8 recom_ut8 WHERE RECOM.RECOM_STATUS  = :solution_Status AND recom_ut8.link_recom IN RECOM.objid AND recom_ut8.language_cd = (select look_value from GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME =:lang)")
						.setParameter("solution_Status", solutionStatus)
						.setParameter("lang",
								"LANG_" + strLanguage.toUpperCase());
				langDisplayNameQuery.setFetchSize(500);
				selectList = (List<Object>) langDisplayNameQuery.list();
				if (selectList.isEmpty()) {
					selectEngVal = true;
				}
			}
			if (strLanguage.equalsIgnoreCase("en") || selectEngVal == true) {
				hibernateSession = getHibernateSession();
				displayNameQuery = hibernateSession
						.createSQLQuery(
								"SELECT RECOM.OBJID, RECOM.TITLE FROM GETS_SD.GETS_SD_RECOM RECOM WHERE RECOM.RECOM_STATUS = :solution_Status ")
						.setParameter("solution_Status", solutionStatus);

				displayNameQuery.setFetchSize(500);

				selectList = (List<Object>) displayNameQuery.list();
			}
			LOG.debug("getRxTitlesLite SelectRxDAOImpl EXECUTED QUERY "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));

			if (selectList != null && !selectList.isEmpty()) {
				recommList = new ArrayList<RxSearchResultEoaLiteServiceVO>();

				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					objSolutionListVO = new RxSearchResultEoaLiteServiceVO();
					Object[] SolutionRec = (Object[]) lookValueIter.next();
					objSolutionListVO.setSolutionId(RMDCommonUtility
							.convertObjectToString(SolutionRec[0]));
					objSolutionListVO.setSolutionTitle(AppSecUtil
							.htmlEscaping(RMDCommonUtility
									.convertObjectToString(SolutionRec[1])));
					recommList.add(objSolutionListVO);
				}
			}
			LOG.debug("****getRxTitlesLite SelectRxDAOImpl ITERATION DONE "
					+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
							.format(new Date()));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
			releaseSession(langHibernateSession);
		}
		LOG.debug("getRxTitlesLite SelectRxDAOImpl END "
				+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS")
						.format(new Date()));
		return recommList;
	}
	/*private  List<RxConfigVO> getConfigDetails(){
		 List<RxConfigVO> list =getRxConfigDetails();
		List<VehicleCfgVO> list2=getVehicleBOMConfigs();		
		System.out.println("list2"+list2.size());
		 List<RxConfigVO> finalList =new ArrayList<RxConfigVO>();
		 
		for (RxConfigVO rxConfigVO : list) {
			if(listOfRx(list2, rxConfigVO)){
				finalList.add(rxConfigVO);
			}
		}
		return finalList;
	}
	*/
	private boolean listOfRx(List<VehicleCfgVO> list,RxConfigVO rxConfigVO  ){
		for (VehicleCfgVO vehicleCfgVO : list) {
			if((rxConfigVO.getConfigName().equalsIgnoreCase(vehicleCfgVO.getConfigItem()) && rxConfigVO.getConfigValue1( ).equalsIgnoreCase(vehicleCfgVO.getCurrentVersion())) || (rxConfigVO.getConfigName() == null || rxConfigVO.getConfigValue1()== null)){
				return true;
				
			}
		}
		return false; 		
		
	}
	
	
	
	
	public List<VehicleCfgVO> getVehicleBOMConfigs() throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		VehicleCfgVO objVehicleCfgVO = null;
		List<VehicleCfgVO> vehicleCfgList = new ArrayList<VehicleCfgVO>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT B.CONFIG_ITEM ,C.CURRENT_VERSION FROM GETS_RMD_PARMDEF A,");
			caseQry.append(" GETS_RMD_MASTER_BOM B, GETS_RMD_VEHCFG C WHERE A.OBJID(+) = B.MASTER_BOM2PARM_DEF AND ");
			caseQry.append(" B.OBJID(+) = C.VEHCFG2MASTER_BOM AND C.VEH_CFG2VEHICLE = 7839");//(SELECT A.VEHICLE_OBJID  FROM GETS_RMD.GETS_RMD_CUST_RNH_RN_V A WHERE (A.CUST_NAME=:customerName) ");
			//caseQry.append(" AND (A.VEHICLE_HDR = :vehicleHeader)");
			//caseQry.append(" AND (A.VEHICLE_NO = :roadNumber)) AND B.BOM_STATUS='Y' ORDER BY  B.CONFIG_ITEM");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
		/*	caseHqry.setParameter(RMDCommonConstants.CUSTOMER_NAME, customer);
			caseHqry.setParameter(RMDCommonConstants.VEHICLE_HEADER, rnh);
			caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNumber);*/
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] vehicleCfgDetails =  obj.next();
					objVehicleCfgVO = new VehicleCfgVO();
					objVehicleCfgVO.setConfigItem(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(vehicleCfgDetails[0])));
					objVehicleCfgVO.setCurrentVersion(RMDCommonUtility
							.convertObjectToString(vehicleCfgDetails[1]));
					vehicleCfgList.add(objVehicleCfgVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return vehicleCfgList;
	}
	
	
	private List<RxConfigVO> getRxConfigDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO){		
		
		List<AddEditRxServiceVO> addeditRxServiceVO;
		StringBuilder searchRecommQry=new StringBuilder(); 
		ArrayList<RxConfigVO> arlRxConfigVO = null;
		RxConfigVO objRxConfigVO = null;
		RxConfigVO rxConfigVO=new RxConfigVO();
		
		Session session = null;
		
		Query hibernateQuery = null;
		Boolean blnKM = false;
		
			session = getHibernateSession();
			rxSearchCriteriaServiceVO.setIsModelQuery(RMDCommonConstants.LETTER_Y);
			hibernateQuery = getSolutionsQuery(rxSearchCriteriaServiceVO, session);
			List recomList=new ArrayList();
			List<Object[]> arlList = hibernateQuery.list();
			for (Object[] currentObject : arlList) {
				String recomId = RMDCommonUtility.convertObjectToString(currentObject[0]);
				recomList.add(recomId);
			}
			System.out.println("recomListrecomList :::"+recomList);
		 try{
		//String strRXId = addeditRxServiceVO.getStrRxId();
		Query displayRxNameQuery;
		Session hibernateSession= getHibernateSession();
		searchRecommQry.append("SELECT RECFG.CONFIG_FUNCTION, ");
		searchRecommQry.append("RECFG.CONFIG_NAME, RECFG.VALUE1 ");
		searchRecommQry.append("FROM GETS_SD_RECOM RECOM,GETS_SD.GETS_SD_RECOM_CONFIG RECFG ");
		searchRecommQry.append("WHERE RECOM.OBJID=RECFG.RECOM_CONFIG2RECOM(+) AND RECOM.OBJID  IN (:recomId)");
		displayRxNameQuery = hibernateSession.createSQLQuery(searchRecommQry.toString());
		displayRxNameQuery.setParameterList(RMDServiceConstants.RECOM_ID,recomList);
		
		System.out.println("BEFORE QUERY .....");
		List<Object[]> arlRecomDetails = displayRxNameQuery.list();
		System.out.println("arlRecomDetails :"+arlRecomDetails.size());
		arlRxConfigVO = new ArrayList<RxConfigVO>();
		
		if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
			for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
				Object recomDetails[] = (Object[]) arlRecomDetails
						.get(idx);
				objRxConfigVO = new RxConfigVO();
				objRxConfigVO.setConfigFunction(RMDCommonUtility
						.convertObjectToString(recomDetails[0]));
				objRxConfigVO.setConfigName(RMDCommonUtility
						.convertObjectToString(recomDetails[1]));
				objRxConfigVO.setConfigValue1(RMDCommonUtility
						.convertObjectToString(recomDetails[2]));
				
				arlRxConfigVO.add(objRxConfigVO);
			}
			
	}
		 }catch(Exception exp){
			 exp.printStackTrace();
		 }
		return arlRxConfigVO;
	}
	
	/**
	 * @param rxSearchCriteriaServiceVO
	 * @param searchRecommQry
	 * @param displayNameQuery
	 * @param hibernateSession
	 * @return
	 */
	private Query getSolutionsQuery(
			RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO,
			Session hibernateSession) {
		StringBuffer searchRecommQry = new StringBuffer();
		StringBuffer whereRecommQry = new StringBuffer();
		Query displayNameQuery;
		int defaultDaystoSearch = 0;
		String strCondition = RMDCommonConstants.EMPTY_STRING;
		String strCondValue = RMDCommonConstants.EMPTY_STRING;
		String strModelArray[] = null;
		String strModel = RMDCommonConstants.EMPTY_STRING;
		String strRxStatus = RMDCommonConstants.EMPTY_STRING;
		String strType = RMDCommonConstants.EMPTY_STRING;
		String strUrgencyArray[] = null;
		String strEstTime = RMDCommonConstants.EMPTY_STRING;
		String strLocoImpArray[] = null;
		String strSubsystemArray[] = null;
		String strLastUpdated = RMDCommonConstants.EMPTY_STRING;
		String strCreatedBy = RMDCommonConstants.EMPTY_STRING;
		String strNote = RMDCommonConstants.EMPTY_STRING;
		String strExpCtrl = RMDCommonConstants.EMPTY_STRING;
		String lastUpdatedFrom=RMDCommonConstants.EMPTY_STRING;
		String lastUpdatedTo=RMDCommonConstants.EMPTY_STRING;
		String createdFrom=RMDCommonConstants.EMPTY_STRING;
		String createdTo=RMDCommonConstants.EMPTY_STRING;
		Boolean blnDefaultLoad = false;
		Boolean blnKM = false;
		String strSelectBy = RMDCommonConstants.EMPTY_STRING;
		String rxObjid = RMDCommonConstants.EMPTY_STRING;
	/*	System.out.println("BEFORE getListDetails()getListDetails()");
		//getConfigDetails();
		System.out.println("AFTER getListDetails()getListDetails()");*/
		if (null != rxSearchCriteriaServiceVO.getStrRxObjid()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrRxObjid())) {
			
			rxObjid = rxSearchCriteriaServiceVO.getStrRxObjid();
			
		}
		
		if (null != rxSearchCriteriaServiceVO.getStrCondition()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrCondition())) {
			strCondition = rxSearchCriteriaServiceVO.getStrCondition();
		}
		if (null != rxSearchCriteriaServiceVO.getStrCondValue()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrCondValue())) {
			strCondValue = AppSecUtil.decodeString(rxSearchCriteriaServiceVO
					.getStrCondValue().toUpperCase());
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxModel()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrRxModel())) {
			strModelArray = rxSearchCriteriaServiceVO.getStrRxModel();
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxStatus()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrRxStatus())) {

			if (RMDServiceConstants.APPROVED.equals(rxSearchCriteriaServiceVO
					.getStrRxStatus())) {
				strRxStatus = RMDServiceConstants.APPROVED;
			} else if (RMDServiceConstants.DRAFT
					.equals(rxSearchCriteriaServiceVO.getStrRxStatus())) {
				strRxStatus = RMDServiceConstants.INPROCESS;
			} else {
				strRxStatus = rxSearchCriteriaServiceVO.getStrRxStatus();
			}
		}
		if (null != rxSearchCriteriaServiceVO.getStrSelRxType()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrSelRxType())) {
			strType = rxSearchCriteriaServiceVO.getStrSelRxType();
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxUrgRepairArray()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO
								.getStrRxUrgRepairArray())) {
			strUrgencyArray = rxSearchCriteriaServiceVO
					.getStrRxUrgRepairArray();
		}
		if (null != rxSearchCriteriaServiceVO.getStrSelEstmTimeRepair()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO
								.getStrSelEstmTimeRepair())) {
			strEstTime = rxSearchCriteriaServiceVO.getStrSelEstmTimeRepair();
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxLocoImpact()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrRxLocoImpact())) {
			strLocoImpArray = rxSearchCriteriaServiceVO.getStrRxLocoImpact();
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxSubSystemArray()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO
								.getStrRxSubSystemArray())) {
			strSubsystemArray = rxSearchCriteriaServiceVO
					.getStrRxSubSystemArray();
		}
		if (null != rxSearchCriteriaServiceVO.getStrLastUpdatedBy()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrLastUpdatedBy())) {
			strLastUpdated = rxSearchCriteriaServiceVO.getStrLastUpdatedBy();
		}
		if (null != rxSearchCriteriaServiceVO.getStrCreatedBy()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrCreatedBy())) {
			strCreatedBy = rxSearchCriteriaServiceVO.getStrCreatedBy();
		}
		if (null != rxSearchCriteriaServiceVO.getStrLastUpdatedFromDate()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrLastUpdatedFromDate())) {
			lastUpdatedFrom = rxSearchCriteriaServiceVO.getStrLastUpdatedFromDate();
		}
		if (null != rxSearchCriteriaServiceVO.getStrLastUpdatedToDate()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrLastUpdatedToDate())) {
			lastUpdatedTo = rxSearchCriteriaServiceVO.getStrLastUpdatedToDate();
		}
		if (null != rxSearchCriteriaServiceVO.getStrCreatedByFromDate()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrCreatedByFromDate())) {
			createdFrom = rxSearchCriteriaServiceVO.getStrCreatedByFromDate();
		}
		if (null != rxSearchCriteriaServiceVO.getStrCreatedByToDate()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrCreatedByToDate())) {
			createdTo = rxSearchCriteriaServiceVO.getStrCreatedByToDate();
		}
		
		if (null != rxSearchCriteriaServiceVO.getStrRxNotes()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrRxNotes())) {
			strNote = rxSearchCriteriaServiceVO.getStrRxNotes();
		}
		if (null != rxSearchCriteriaServiceVO.getStrRxExportControl()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO
								.getStrRxExportControl())) {
			strExpCtrl = rxSearchCriteriaServiceVO.getStrRxExportControl();
		}
		if (null != rxSearchCriteriaServiceVO.getStrSelectBy()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrSelectBy())) {
			strSelectBy = rxSearchCriteriaServiceVO.getStrSelectBy();
		}
		blnDefaultLoad = rxSearchCriteriaServiceVO.isBlnDefaultLoad();
		String language = rxSearchCriteriaServiceVO.getStrUserLanguage();
		blnKM = rxSearchCriteriaServiceVO.isBlnKM();

		if (!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO
				.getIsModelQuery())
				&& RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getIsModelQuery())) {
			searchRecommQry
					.append("SELECT distinct T2.OBJID RECOM_ID, A.MODEL_NAME FROM GETS_RMD_MODEL A,GETS_SD_RECOM_MTM_MODEL B,(");
			
		}		

		/*if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getAddRxApply())) {
			searchRecommQry
					.append("SELECT RECOM.*, BOM.* from (");
		}*/
		searchRecommQry	
				.append("SELECT DISTINCT RECOM.OBJID,RECOM.TITLE,RECOM.RECOM_STATUS, "
						+ " RECOM.RECOM_TYPE,RECOM.URGENCY,RECOM.EST_REPAIR_TIME,RECOM.VERSION, "
						+ " RECOM.LOCO_IMPACT,TO_CHAR(RECOM.LAST_UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS') LASTUPDATED_DATE ,RECOM.LAST_UPDATED_BY ,MODEL.MODEL_NAME ,RECOM.LAST_UPDATED_DATE ");

		searchRecommQry
				.append(" ,METRICS.RX_RANK,  METRICS.RX_PRECISION,  METRICS.DELV_CNT_NOREISSUE, "
						+ " MET.ACCURACYPERCENT, MET.REISSUEPERCENT, MET.NTFPERCENT ");
		
		

		// Added for Multilingual title and locoimpact
		searchRecommQry
				.append(",(SELECT DISTINCT trans_recom_title_txt  FROM	 gets_sd.GETS_SD_RECOM_UT8 "
						+ "WHERE (LANGUAGE_CD=(select look_value  from gets_rmd_lookup where list_name =:language) "
						+ " OR  LANGUAGE_CD=:languageCode) AND LINK_RECOM   = RECOM.OBJID ) tansTitle,");
		searchRecommQry
				.append(" (SELECT DISTINCT TRANS_LOCO_IMPACT_TXT FROM gets_sd.GETS_SD_MSTR_LOCO_IMPACT "
						+ "WHERE LOCO_IMPACT_TXt = RECOM.loco_impact AND UPPER(ACTIVE_FLG)='Y'"
						+ " AND (LANGUAGE_CD=(select look_value  from gets_rmd_lookup "
						+ "where list_name =:language)  OR  LANGUAGE_CD=:languageCode) ) transLocoImpact ");
		if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getAddRxApply())) {
			searchRecommQry
					.append(" ,RECFG.config_name, RECFG.VALUE1, RECFG.CONFIG_FUNCTION");

		}
		
		searchRecommQry
				.append(" FROM GETS_SD.GETS_SD_RECOM RECOM, GETS_SD.GETS_SD_RECOM_MTM_MODEL REC_MOD,GETS_RMD_MODEL MODEL , GETS_TOOL_RX_METRICS METRICS ,");
		
		if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getAddRxApply())) {
			searchRecommQry
					.append(" GETS_SD_RECOM_CONFIG RECFG,");
		}

		searchRecommQry
				.append(RMDServiceConstants.QueryConstants.FETCH_METRIC_DETAILS);		
			

		if (null != strSelectBy
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strSelectBy)
				&& RMDCommonConstants.REPAIR_CODE.equalsIgnoreCase(strSelectBy)) {
			searchRecommQry
					.append(" ,GETS_SD_RECOM_TASK  TASK, GETS_sd_REPAIR_CODES CODES ");
		} else if (null != strSelectBy
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strSelectBy)
				&& RMDCommonConstants.SUB_SYSTEMS.equalsIgnoreCase(strSelectBy)) {
			searchRecommQry
					.append(" ,GETS_SD.GETS_SD_RECOM_SUBSYS SDRS,GETS_SD.GETS_SD_DASHER_CODES SDDC ");
		}
		
				
		

		whereRecommQry
				.append(" WHERE REC_MOD.RECOM_MODEL2RECOM = RECOM.OBJID AND MODEL.OBJID = REC_MOD.RECOM_MODEL2MODEL AND RECOM.OBJID=METRICS.RX_METRICS2RECOM(+) ");
		whereRecommQry.append("  AND MET.RXTITLE(+) =RECOM.TITLE");
		
		if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getAddRxApply())) {			
			
			whereRecommQry
					.append(" AND RECOM.OBJID=RECFG.RECOM_CONFIG2RECOM(+)");			
		}
		
		
		if (null != strSelectBy
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strSelectBy)
				&& RMDCommonConstants.REPAIR_CODE.equalsIgnoreCase(strSelectBy)) {
			whereRecommQry
					.append(" AND CODES.OBJID=TASK.RECOM_TASK2REP_CODE AND TASK.RECOM_TASK2RECOM=RECOM.OBJID ");
		}
		if (null != strSelectBy
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strSelectBy)
				&& RMDCommonConstants.SUB_SYSTEMS.equalsIgnoreCase(strSelectBy)) {
			whereRecommQry
					.append(" AND SDRS.RECOM_SUBSYS2RECOM = RECOM.OBJID AND SDRS.SUBSYSTEM = SDDC.SUBSYSTEM ");
		}
				
		
		
		// Rx Id
		 
		if (null != rxObjid
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(rxObjid)) {
			whereRecommQry.append(" AND RECOM.OBJID=:rxObjid ");
		}

		// Rx Type
		if (null != strType
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strType)
				&& !RMDCommonConstants.SELECT.equalsIgnoreCase(strType)) {
			whereRecommQry.append(" AND RECOM.RECOM_TYPE=:strType ");
		}
		// Rx Title repair code or sub system
		if (null != strCondValue
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strCondValue)) {
			
			
			
			if (strCondition.equals(RMDCommonConstants.EQUALS)) {
				if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.TITLE
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry
							.append(" AND UPPER(RECOM.TITLE) = :strCondValue ");
				} else if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.REPAIR_CODE
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry
							.append("AND UPPER(CODES.REPAIR_CODE) = :strCondValue ");
				}
				if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.SUB_SYSTEMS
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry
							.append(" AND (UPPER(SDRS.SUBSYSTEM) = :strCondValue OR  UPPER(SDDC.SUBSYSTEM_DESC) = :strCondValue) ");
				}
			} else {
				if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.TITLE
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry.append(
							" AND UPPER(RECOM.TITLE) LIKE :strCondValue ")
							.append(RMDCommonConstants.ESCAPE_LIKE);
				} else if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.REPAIR_CODE
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry
							.append("AND UPPER(CODES.REPAIR_CODE) LIKE :strCondValue ");
				}
				if (null != strSelectBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strSelectBy)
						&& RMDCommonConstants.SUB_SYSTEMS
								.equalsIgnoreCase(strSelectBy)) {
					whereRecommQry
							.append(" AND (UPPER(SDRS.SUBSYSTEM) LIKE :strCondValue OR  UPPER(SDDC.SUBSYSTEM_DESC) LIKE :strCondValue) ");
				}
				
				
            
			}

		}	

		// Model
		if (null != strModelArray && strModelArray.length > 0) {

			whereRecommQry.append(" AND MODEL.MODEL_NAME IN (:strModelArray) ");
		} else if (null != rxSearchCriteriaServiceVO.getStrFamily()
				&& !RMDCommonConstants.EMPTY_STRING
						.equals(rxSearchCriteriaServiceVO.getStrFamily())) {
			whereRecommQry.append(" AND FAMILY in (:family)");
		}

		
		// Status
		if (null != strRxStatus
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strRxStatus)
				&& !RMDCommonConstants.ALL.equalsIgnoreCase(strRxStatus)
				&& !RMDCommonConstants.SELECT.equalsIgnoreCase(strRxStatus)) {
			whereRecommQry.append(" AND RECOM.RECOM_STATUS=:strRxStatus ");
		}

		// Emergency Time to repair
		if (null != strEstTime
				&& !RMDCommonConstants.ALL_URG_ESTTIME.equals(strEstTime)) {
			whereRecommQry.append(" AND RECOM.EST_REPAIR_TIME=:strEstTime");
		}
		// Urgency of Repair
		if (null != strUrgencyArray) {
			whereRecommQry.append(" AND RECOM.urgency IN (:strUrgency) ");
		}
		// Locomotive Impact
		if (null != strLocoImpArray) {
			whereRecommQry
					.append(" AND RECOM.LOCO_IMPACT IN (:strLocoImpArray) ");
		}
		// Subsystem
		if (null != strSubsystemArray) {
			searchRecommQry.append(" ,GETS_SD.GETS_SD_RECOM_SUBSYS SDRS "
					+ " ,GETS_SD.GETS_SD_DASHER_CODES SDDC ");
			whereRecommQry
					.append(" AND SDRS.RECOM_SUBSYS2RECOM = RECOM.OBJID AND SDRS.SUBSYSTEM IN (SELECT OBJID FROM GETS_RMD_LOOKUP WHERE LIST_NAME='SUBSYSTEM' AND LOOK_VALUE IN (:strSubsystemArray)) ");
					
		}

		// Last Updated User
		if (null != strLastUpdated
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strLastUpdated)) {
			whereRecommQry
					.append(" AND RECOM.LAST_UPDATED_BY=:strLastUpdated ");
		}
		//Created By
		if (null != strCreatedBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strCreatedBy)) {
					whereRecommQry
							.append(" AND RECOM.CREATED_BY=:strCreatedBy ");
		}
		// For LastUpdate From Date
        if (null!=lastUpdatedFrom
                && !RMDCommonConstants.EMPTY_STRING.equals(lastUpdatedFrom)
                && lastUpdatedTo != null
                && !RMDCommonConstants.EMPTY_STRING.equals(lastUpdatedTo)) {

        	whereRecommQry.append(" AND RECOM.LAST_UPDATED_DATE between TO_DATE("
                    + " :lastUpdatedFromDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') AND  TO_DATE("
                    +" :lastUpdatedToDate "
                    + ",'MM/DD/YYYY hh24:mi:ss')");
            
        }

        // For Creation From Date
        if (null!=createdFrom
                && !RMDCommonConstants.EMPTY_STRING.equals(createdFrom)
                && createdTo != null
                && !RMDCommonConstants.EMPTY_STRING.equals(createdTo)) {                    
        	whereRecommQry.append(" AND RECOM.CREATION_DATE BETWEEN TO_DATE("
                    +" :createdFromDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') AND  TO_DATE("
                    +" :createdToDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') ");
        }


		// Notes
		if (null != strNote
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strNote)) {
			searchRecommQry.append(" ,GETS_SD.GETS_SD_RECOM_HIST HIST ");
			whereRecommQry.append(" AND HIST.RECOM_HIST2RECOM = RECOM.OBJID "
					+ " AND HIST.REVISION_NOTES=:strNote ");
		}
		// Export Control
		if (null != strExpCtrl
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strExpCtrl)) {
			whereRecommQry.append(" AND RECOM.EXPORT_CONTROL=:strExpCtrl ");
		}

		if (blnDefaultLoad) {
			defaultDaystoSearch = RMDCommonConstants.Numeric_30_DAYS;
			whereRecommQry
					.append(" AND RECOM.LAST_UPDATED_DATE BETWEEN sysdate-"
							+ defaultDaystoSearch + " AND sysdate");
		}

		// Added models in the where clause to bring model values also
		if (blnKM) {
			searchRecommQry.append(whereRecommQry).append(
					" ORDER BY RECOM.LAST_UPDATED_DATE DESC");
		} else {
			searchRecommQry.append(whereRecommQry).append(
					"  ORDER BY UPPER(RECOM.TITLE),MODEL.MODEL_NAME");
		}
		
		/*if (RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getIsMassApplyRx()) || RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
				.getAddRxApply())) {
			searchRecommQry
			.append(") RECOM,  (select current_version,config_item from GETS_RMD_VEHCFG V,"
					+ "  GETS_RMD_MASTER_BOM MBOM where V.VEHCFG2MASTER_BOM = MBOM.OBJID) bom "
					+ "WHERE RECOM.CONFIG_NAME= BOM.CONFIG_ITEM(+) "
					+ "  AND RECOM.VALUE1  = BOM.CURRENT_VERSION(+) ");

		}
			*/
		
		// Added for Fetching Models for MassApply Rx Requirement

		if (!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO
				.getIsModelQuery())
				&& RMDCommonConstants.LETTER_Y.equals(rxSearchCriteriaServiceVO
						.getIsModelQuery())) {
			searchRecommQry
					.append(") T2 WHERE B.RECOM_MODEL2MODEL = A.OBJID AND   B.RECOM_MODEL2RECOM = T2.OBJID ORDER BY A.MODEL_NAME");
			
			
			}		
		
		displayNameQuery = hibernateSession.createSQLQuery(searchRecommQry
				.toString());	
		
		
		// Rx Id 
		if (null != rxObjid
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(rxObjid)) {
			displayNameQuery.setParameter(RMDCommonConstants.RX_OBJID, rxObjid);
		}	
		// Rx Title
		if (null != strCondValue
				&& !RMDCommonConstants.EMPTY_STRING.equals(strCondValue)) {
			if (strCondition.equals(RMDCommonConstants.STARTS_WITH_S)) {
				displayNameQuery.setParameter(RMDCommonConstants.TITLE_S,
						strCondValue + RMDServiceConstants.PERCENTAGE);
			} else if (strCondition.equals(RMDCommonConstants.ENDS_WITH_S)) {
				displayNameQuery.setParameter(RMDCommonConstants.TITLE_S,
						RMDServiceConstants.PERCENTAGE + strCondValue);
			}

			else if (strCondition.equals(RMDCommonConstants.CONTAINS)) {
				displayNameQuery.setParameter(
						RMDCommonConstants.TITLE_S,
						RMDServiceConstants.PERCENTAGE
								+ AppSecUtil.escapeLikeCharacters(strCondValue)
								+ RMDServiceConstants.PERCENTAGE);
			} else {
				displayNameQuery.setParameter(RMDCommonConstants.TITLE_S,
						strCondValue);
			}
		}

		// Model
		if (null != strModelArray) {
			List<String> listParameter = new ArrayList<String>();
			for (String strModelItr : strModelArray) {
				listParameter.add(strModelItr);
			}

			displayNameQuery.setParameterList(RMDCommonConstants.MODEL_S,
					listParameter);
		} else if (null != rxSearchCriteriaServiceVO.getStrFamily()) {
			List<String> listParameter = new ArrayList<String>();

			if (RMDServiceConstants.CCA.equals(rxSearchCriteriaServiceVO
					.getStrFamily())) {
				listParameter.add(RMDServiceConstants.FAMILY_ACCCA);
				listParameter.add(RMDServiceConstants.FAMILY_DCCCA);
				displayNameQuery.setParameterList(RMDCommonConstants.FAMILY,
						listParameter);
			} else {
				listParameter.add(rxSearchCriteriaServiceVO.getStrFamily());
				displayNameQuery.setParameterList(RMDCommonConstants.FAMILY,
						listParameter);

			}
		}

		// Status
		if (null != strRxStatus
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strRxStatus)
				&& !RMDCommonConstants.ALL.equalsIgnoreCase(strRxStatus)
				&& !RMDCommonConstants.SELECT.equalsIgnoreCase(strRxStatus)) {
			displayNameQuery.setParameter(RMDCommonConstants.STATUS_S,
					strRxStatus);
		}

		// Type
		if (null != strType
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strType)) {
			displayNameQuery.setParameter(RMDCommonConstants.TYPE_S, strType);
		}

		// Loco Impact
		if (null != strLocoImpArray) {
			List listParameter = new ArrayList();
			for (String strLocoImp : strLocoImpArray) {
				listParameter.add(strLocoImp);
			}
			displayNameQuery.setParameterList(RMDCommonConstants.LOCO_IMP_S,
					listParameter);
		}

		// Subsystem
		if (null != strSubsystemArray) {
			List listParameter = new ArrayList();
			for (String strSubsystem : strSubsystemArray) {
				listParameter.add(strSubsystem);
			}
			displayNameQuery.setParameterList(RMDCommonConstants.SUBSYSTEM_S,
					listParameter);
		}

		// Last updated by
		if (null != strLastUpdated
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strLastUpdated)) {
			displayNameQuery.setParameter(RMDCommonConstants.LAST_UPDATED_S,
					strLastUpdated);
		}
		
		//Created By
		if (null != strCreatedBy
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(strCreatedBy)) {
			displayNameQuery.setParameter(RMDCommonConstants.STRCREATED_BY,
					strCreatedBy);
		}
		// For LastUpdate From Date
        if (null!=lastUpdatedFrom
                && !RMDCommonConstants.EMPTY_STRING.equals(lastUpdatedFrom)
                && lastUpdatedTo != null
                && !RMDCommonConstants.EMPTY_STRING.equals(lastUpdatedTo)) {

        	displayNameQuery.setParameter(RMDServiceConstants.LastUpdatedFromDate,
        			lastUpdatedFrom);
        	displayNameQuery.setParameter(RMDServiceConstants.LastUpdateToDate,
        			lastUpdatedTo);
            
        }

        // For Creation From Date
        if (null!=createdFrom
                && !RMDCommonConstants.EMPTY_STRING.equals(createdFrom)
                && createdTo != null
                && !RMDCommonConstants.EMPTY_STRING.equals(createdTo)) {                    
        	displayNameQuery.setParameter(RMDServiceConstants.createdFromDate,
        			createdFrom);
        	displayNameQuery.setParameter(RMDServiceConstants.createdToDate,
            		createdTo);
        }

		// Note
		if (null != strNote
				&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strNote)) {
			displayNameQuery.setParameter(RMDCommonConstants.NOTE_S, strNote);
		}

		// Export control
		if (null != strExpCtrl
				&& !RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(strExpCtrl)) {
			displayNameQuery.setParameter(RMDCommonConstants.EXP_CTRL_S,
					strExpCtrl);
		}

		// Urgency of Repair
		if (null != strUrgencyArray) {
			List listParameter = new ArrayList();
			for (String strUrgency : strUrgencyArray) {
				listParameter.add(strUrgency);
			}
			displayNameQuery.setParameterList(RMDCommonConstants.EST_URGENCY,
					listParameter);
		}

		// Estimated Time to repair
		if (null != strEstTime
				&& !RMDCommonConstants.ALL_URG_ESTTIME.equals(strEstTime)) {
			displayNameQuery.setParameter(RMDCommonConstants.EST_REPAIR_TIME,
					strEstTime);
		}

		// Multilingual RX Title and locoImpact
		if (null != language) {
			displayNameQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_"
					+ language.toUpperCase());
			displayNameQuery.setString(RMDCommonConstants.LANGUAGECODE,
					language);
		} else {
			displayNameQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_EN");
			displayNameQuery.setString(RMDCommonConstants.LANGUAGECODE, "en");
		}
		
		
		return displayNameQuery;
	}

	/* Added for Sprint 9 Recom Search */
	/**
	 * @param rxSearchCriteriaServiceVO
	 * @return RxDetailsVO
	 * @Description Function to get the rx details
	 */
	@Override
	public RxDetailsVO getRecommDetails(
			RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
			throws RMDDAOException {
		Session session = null;
		String rxDetailsQuery = null;
		Query query = null;
		List solutionDetailsList = null;
		RxDetailsVO rxDetailsVO = null;
		String errorCode = null;
		try {
			session = getHibernateSession();
			rxDetailsQuery = "SELECT recom.OBJID,recom.TITLE,recom.URGENCY,recom.EST_REPAIR_TIME,recom.LOCO_IMPACT,recom.VERSION,recom.RECOM_TYPE,recom.RECOM_STATUS "
					+ "FROM GETS_SD_RECOM recom WHERE OBJID =:recom_objid ";
			query = session.createSQLQuery(rxDetailsQuery);
			query.setParameter(RMDCommonConstants.RECOM_OBJID,
					rxSearchCriteriaServiceVO.getStrRxObjid());
			solutionDetailsList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(solutionDetailsList)) {
				rxDetailsVO = new RxDetailsVO();
				Object recomDetails[] = (Object[]) solutionDetailsList.get(0);
				rxDetailsVO.setRxObjid(RMDCommonUtility
						.convertObjectToString(recomDetails[0]));
				rxDetailsVO.setRxTitle(RMDCommonUtility
						.convertObjectToString(recomDetails[1]));
				rxDetailsVO.setUrgency(RMDCommonUtility
						.convertObjectToString(recomDetails[2]));
				rxDetailsVO.setEstRepTime(RMDCommonUtility
						.convertObjectToString(recomDetails[3]));
				rxDetailsVO.setLocoImpact(RMDCommonUtility
						.convertObjectToString(recomDetails[4]));
				rxDetailsVO.setVersion(RMDCommonUtility
						.convertObjectToInt(recomDetails[5]));
				rxDetailsVO.setRxType(RMDCommonUtility
						.convertObjectToString(recomDetails[6]));
				rxDetailsVO.setRxstatus(RMDCommonUtility
						.convertObjectToString(recomDetails[7]));
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.RX_NOT_FOUND_ERROR);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()),
						null, RMDCommonConstants.MINOR_ERROR);
			}
		} catch (RMDDAOConnectionException ex) {
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException e) {
			if (null != e.getErrorDetail()) {
				throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
						new String[] {}, e.getErrorDetail().getErrorMessage(),
						e, e.getErrorDetail().getErrorType());
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()), e,
						RMDCommonConstants.FATAL_ERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return rxDetailsVO;
	}
	
	

	/**
	 * @Description:This method is used for fetching Solution task details
	 * @param RxSearchCriteriaEoaServiceVO
	 * @return List<RxTaskDetailsVO>
	 * @throws RMDBOException
	 */
	@Override
	public List<RxTaskDetailsVO> getRxTaskDetails(
			RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
			throws RMDDAOException {
		// TODO Auto-generated method stub
		Session session = null;
		String rxTaskDetailsQuery = null;
		Query query = null;
		List taskDetailsList = null;
		String errorCode = null;
		List<RxTaskDetailsVO> rxTaskDetailsList = null;
		RxTaskDetailsVO rxTaskDetailsVO = null;
		try {
			session = getHibernateSession();
			rxTaskDetailsQuery = "SELECT gsrt.OBJID, gsrt.TASK_ID, gsrt.TASK_DESC, gsrt.LOWER_SPEC lower_spec, "
					+ "gsrt.UPPER_SPEC upper_spec,gsrt.TARGET target "
					+ "FROM GETS_SD_RECOM gsr, GETS_SD_RECOM_TASK gsrt WHERE GSR.OBJID   = GSRT.RECOM_TASK2RECOM and GSR.OBJID =:recom_objid  order by TO_NUMBER(gsrt.TASK_ID)  asc  ";
			query = session.createSQLQuery(rxTaskDetailsQuery);
			query.setParameter(RMDCommonConstants.RECOM_OBJID,
					rxSearchCriteriaServiceVO.getStrRxObjid());
			taskDetailsList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(taskDetailsList)) {
				rxTaskDetailsList = new ArrayList<RxTaskDetailsVO>();
				for (int i = 0; i < taskDetailsList.size(); i++) {
					Object taskDetails[] = (Object[]) taskDetailsList.get(i);
					rxTaskDetailsVO = new RxTaskDetailsVO();

					rxTaskDetailsVO.setTaskId(RMDCommonUtility
							.convertObjectToString(taskDetails[0]));
					rxTaskDetailsVO.setTaskSequence(RMDCommonUtility
							.convertObjectToString(taskDetails[1]));
					rxTaskDetailsVO.setTaskDescription(RMDCommonUtility
							.convertObjectToString(taskDetails[2]));
					rxTaskDetailsVO.setLsl(RMDCommonUtility
							.convertObjectToString(taskDetails[3]));
					rxTaskDetailsVO.setUsl(RMDCommonUtility
							.convertObjectToString(taskDetails[4]));
					rxTaskDetailsVO.setTarget(RMDCommonUtility
							.convertObjectToString(taskDetails[5]));
					rxTaskDetailsList.add(rxTaskDetailsVO);
				}
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.RX_NOT_FOUND_ERROR);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()),
						null, RMDCommonConstants.MINOR_ERROR);
			}
		} catch (RMDDAOConnectionException ex) {
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_TASK_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException e) {
			if (null != e.getErrorDetail()) {
				throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
						new String[] {}, e.getErrorDetail().getErrorMessage(),
						e, e.getErrorDetail().getErrorType());
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_TASK_DETAILS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()), e,
						RMDCommonConstants.FATAL_ERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_TASK_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return rxTaskDetailsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.tools.rx.dao.intf.SelectRxDAOIntf#getWorstUrgency
	 * (
	 * com.ge.trans.rmd.services.tools.rx.service.valueobjects.RxUrgencyParamVO)
	 * This method is used to fetch the urgency for all asset from the database
	 */
	@Override
	public List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO)
			throws RMDDAOException {

		List urgencyList = null;
		RxWorstUrgencyVO rxWorstUrgencyVO = null;
		List<RxWorstUrgencyVO> rxWorstUrgencyVOLst = new ArrayList<RxWorstUrgencyVO>();
		Session hibernateSession = null;
		StringBuffer query = new StringBuffer();

		/*
		 * Query is modified in order to fetch worst urgency other than
		 * red(R),yellow(Y) and white(W)
		 */
		query.append("SELECT TBO.ORG_ID,");
		query.append(" TSP.X_VEH_HDR,");
		query.append("TSP.SERIAL_NO,");
		query.append("  DECODE(MIN(DECODE(CASERECOM.URGENCY,'R','1','Y','2','B','3','W','4','G','5','C','6','O','7')),'1','R','2','Y','3','B','4','W','5','G','6','C','7','O') URGENCY");
		query.append(" FROM TABLE_CASE C,");
		query.append("  GETS_SD_CASE_RECOM CASERECOM,");
		query.append("  GETS_SD_RECOM_DELV DELV,");
		query.append(" TABLE_SITE_PART TSP,");
		query.append(" GETS_RMD_VEHICLE VEH,");
		query.append("  GETS_RMD_VEH_HDR VEHHDR,");
		query.append(" TABLE_BUS_ORG TBO,");
		query.append(" GETS_SD_CUST_FDBK FDBK");
		query.append(" WHERE C.CASE_PROD2SITE_PART         = TSP.OBJID");
		query.append(" AND CASERECOM.CASE_RECOM2CASE       = C.OBJID");
		query.append(" AND CASERECOM.CASE_RECOM2RECOM_DELV = DELV.OBJID");
		query.append(" AND TSP.OBJID                       = VEH.VEHICLE2SITE_PART");
		query.append(" AND VEH.VEHICLE2VEH_HDR             = VEHHDR.OBJID");
		query.append(" AND VEHHDR.VEH_HDR2BUSORG           = TBO.OBJID");
		query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%'");
		query.append(" AND CASESTS2GBST_ELM IN (268435544,268435947,268435478,268435523) ");

		if (null != rxUrgencyParamVO.getCustomerId()
				&& !RMDCommonConstants.EMPTY_STRING.equals(rxUrgencyParamVO
						.getCustomerId())) {
			query.append(" AND TBO.ORG_ID            =:customerId");
		}
		if (null != rxUrgencyParamVO.getAssetNumber()
				&& !RMDCommonConstants.EMPTY_STRING.equals(rxUrgencyParamVO
						.getAssetNumber())) {
			query.append(" AND TSP.SERIAL_NO=:assetNumber");
		}
		if (null != rxUrgencyParamVO.getAssetGrpName()
				&& !RMDCommonConstants.EMPTY_STRING.equals(rxUrgencyParamVO
						.getAssetGrpName())) {
			query.append(" AND TSP.X_VEH_HDR=:assetGrpName");
		}

		query.append(" AND CASERECOM.URGENCY IN ('R','Y','W','B','G','C','O')");
		query.append(" AND FDBK.CUST_FDBK2CASE = C.OBJID");
		query.append(" AND DELV.RECOM_DELV2CUST_FDBK = FDBK.OBJID");
		query.append(" AND FDBK.RX_CLOSE_DATE IS NULL");
		query.append(" GROUP BY TBO.ORG_ID,");
		query.append(" TSP.X_VEH_HDR,");
		query.append("  TSP.SERIAL_NO");
		query.append(" ORDER BY TBO.ORG_ID,");
		query.append("  TSP.X_VEH_HDR,");
		query.append("  TSP.SERIAL_NO");

		try {
			hibernateSession = getHibernateSession();
			if (null != hibernateSession) {
				Query hibernateQuery = hibernateSession.createSQLQuery(query
						.toString());
				if (null != rxUrgencyParamVO.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(rxUrgencyParamVO
										.getCustomerId())) {
					hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
							rxUrgencyParamVO.getCustomerId());
				}

				if (null != rxUrgencyParamVO.getAssetNumber()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(rxUrgencyParamVO
										.getAssetNumber())) {
					hibernateQuery.setParameter(
							RMDCommonConstants.ASSET_NUMBER,
							rxUrgencyParamVO.getAssetNumber());
				}

				if (null != rxUrgencyParamVO.getAssetGrpName()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(rxUrgencyParamVO
										.getAssetGrpName())) {
					hibernateQuery.setParameter(
							RMDCommonConstants.ASSET_GRP_NAME,
							rxUrgencyParamVO.getAssetGrpName());
				}

				urgencyList = hibernateQuery.list();

				if (RMDCommonUtility.isCollectionNotEmpty(urgencyList)) {
					for (int idx = 0; idx < urgencyList.size(); idx++) {
						rxWorstUrgencyVO = new RxWorstUrgencyVO();
						Object urgencyData[] = (Object[]) urgencyList.get(idx);
						rxWorstUrgencyVO.setWorstUrgency(RMDCommonUtility
								.convertObjectToString(urgencyData[3]));
						rxWorstUrgencyVO.setAssetNumber(RMDCommonUtility
								.convertObjectToString(urgencyData[2]));
						rxWorstUrgencyVO.setCustomerId(RMDCommonUtility
								.convertObjectToString(urgencyData[0]));
						rxWorstUrgencyVO.setAssetGrpName(RMDCommonUtility
								.convertObjectToString(urgencyData[1]));
						rxWorstUrgencyVOLst.add(rxWorstUrgencyVO);
					}

				}

			}
			return rxWorstUrgencyVOLst;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_WORST_URGENCY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxUrgencyParamVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_WORST_URGENCY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxUrgencyParamVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

	}

	/**
	 * @param rxSearchCriteriaServiceVO
	 * @return RxDetailsVO
	 * @Description Function to get the rx details
	 */
	@Override
	public List<RxDetailsVO> getListRecommDetails(
			RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
			throws RMDDAOException {
		Session session = null;
		String rxDetailsQuery = null;
		Query query = null;
		List solutionDetailsList = null;
		List<RxDetailsVO> arlRxDetailsVO = new ArrayList<RxDetailsVO>();
		RxDetailsVO rxDetailsVO = null;
		String errorCode = null;
		try {
			session = getHibernateSession();
			rxDetailsQuery = "SELECT recom.OBJID,recom.TITLE,recom.URGENCY,recom.EST_REPAIR_TIME,recom.LOCO_IMPACT,recom.VERSION,recom.RECOM_TYPE,recom.RECOM_STATUS "
					+ "FROM GETS_SD_RECOM recom WHERE OBJID in (:recom_objid)";
			query = session.createSQLQuery(rxDetailsQuery);
			query.setParameterList(RMDCommonConstants.RECOM_OBJID,
					rxSearchCriteriaServiceVO.getArlRxObjid());
			query.setFetchSize(20);
			solutionDetailsList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(solutionDetailsList)) {
				for (int i = 0; i < solutionDetailsList.size(); i++) {
					rxDetailsVO = new RxDetailsVO();
					Object recomDetails[] = (Object[]) solutionDetailsList
							.get(i);
					rxDetailsVO.setRxObjid(RMDCommonUtility
							.convertObjectToString(recomDetails[0]));
					rxDetailsVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(recomDetails[1]));
					rxDetailsVO.setUrgency(RMDCommonUtility
							.convertObjectToString(recomDetails[2]));
					rxDetailsVO.setEstRepTime(RMDCommonUtility
							.convertObjectToString(recomDetails[3]));
					rxDetailsVO.setLocoImpact(RMDCommonUtility
							.convertObjectToString(recomDetails[4]));
					rxDetailsVO.setVersion(RMDCommonUtility
							.convertObjectToInt(recomDetails[5]));
					rxDetailsVO.setRxType(RMDCommonUtility
							.convertObjectToString(recomDetails[6]));
					rxDetailsVO.setRxstatus(RMDCommonUtility
							.convertObjectToString(recomDetails[7]));
					arlRxDetailsVO.add(rxDetailsVO);
				}
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.RX_NOT_FOUND_ERROR);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()),
						null, RMDCommonConstants.MINOR_ERROR);
			}
		} catch (RMDDAOConnectionException ex) {
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException e) {
			if (null != e.getErrorDetail()) {
				throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
						new String[] {}, e.getErrorDetail().getErrorMessage(),
						e, e.getErrorDetail().getErrorType());
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								rxSearchCriteriaServiceVO.getStrLanguage()), e,
						RMDCommonConstants.FATAL_ERROR);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							rxSearchCriteriaServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return arlRxDetailsVO;
	}

	/*
	 * Added for Sprint 14 Actionable Rx story (non-Javadoc)
	 * 
	 * @see com.ge.trans.rmd.services.tools.rx.dao.intf.SelectRxDAOIntf#
	 * getActionableRxTypes (strCustomerId,strLanguage) This method is used to
	 * fetch actionable rx titles based on the customer from the database
	 */
	@Override
	@Cacheable(value = RMDCommonConstants.CACHE_ACTIONABLE_RX_TYPE, key = "#objActionableRxTypeVO.customerId+#objActionableRxTypeVO.rxGroupId")
	public List<ActionableRxTypeVO> getActionableRxTypes(
			final ActionableRxTypeVO objActionableRxTypeVO)
			throws RMDDAOException {
		LOG.debug("in side getActionable rx types: Start");
		List<ActionableRxTypeVO> actionableRxTypes = new ArrayList<ActionableRxTypeVO>();
		Session hibernateSession = null;
		StringBuffer rxTypeQuery = new StringBuffer();
		ActionableRxTypeVO eachObjActionableRxTypeVO = null;
		final String strCustomerId = objActionableRxTypeVO.getCustomerId();
		final String strLanguage = objActionableRxTypeVO.getStrLanguage();
		final Long rxGroupId = RMDCommonUtility
				.convertObjectToLong(objActionableRxTypeVO.getRxGroupId());

		try {
			hibernateSession = getHibernateSession();
			/*
			 * If only customer id is present then this method fetches all the
			 * rx types based on the customer along with the type id
			 */
			if (null != strCustomerId && !strCustomerId.isEmpty()
					&& rxGroupId == 0) {
				rxTypeQuery = new StringBuffer();

				rxTypeQuery.append("SELECT CRXG");
				rxTypeQuery
						.append(" FROM GetCustomerRxGroupHVO AS CRXG, GetTableBusOrgHVO AS ORG,");
				rxTypeQuery.append("GetRxGroupHVO AS RXG WHERE");
				rxTypeQuery.append(" CRXG.rxGroupId = RXG.rxGroupId");
				rxTypeQuery.append(" AND CRXG.customerId = ORG.objid");
				rxTypeQuery.append(" AND RXG.activeFlg='Y'");
				rxTypeQuery.append(" AND ORG.orgId=:CUSTID");
				rxTypeQuery.append(" ORDER BY  RXG.rxTypeCd");
			}
			/*
			 * if rx group id is present then this method fetches rx titles and
			 * rx title id based on the rx type id if customer is present then
			 * customer id also taken in to consideration while fetching the rx
			 * titles
			 */
			if (rxGroupId != 0) {
				rxTypeQuery = new StringBuffer();

				rxTypeQuery.append("SELECT CRXG");
				rxTypeQuery
						.append(" FROM GetCustomerRxGroupHVO AS CRXG, GetTableBusOrgHVO AS ORG,");
				rxTypeQuery
						.append("GetRxGroupHVO AS RXG, GetSdRecomHVO AS RECOM, GetCustomerRxTitleHVO AS CRXT WHERE");
				rxTypeQuery.append(" CRXT.custRxGroupId = CRXG.custRxGroupId");
				rxTypeQuery.append(" AND CRXT.rxTitleId = RECOM.objid");
				rxTypeQuery.append(" AND CRXG.rxGroupId = RXG.rxGroupId");
				rxTypeQuery.append(" AND CRXG.customerId = ORG.objid");
				if (null != strCustomerId && !strCustomerId.isEmpty()) {
					rxTypeQuery.append(" AND ORG.orgId=:CUSTID ");
				}
				rxTypeQuery.append(" AND RXG.rxGroupId=:rxGrpId ");
				rxTypeQuery.append(" AND RXG.activeFlg='Y'");
				rxTypeQuery.append(" AND RECOM.recomStatus=:recomStatus");
				rxTypeQuery
						.append(" AND rownum < 2 ORDER BY ORG.name, RXG.rxTypeCd, RECOM.title");
			}

			Query rxTypeQry = hibernateSession.createQuery(rxTypeQuery
					.toString());

			if (null != strCustomerId && !strCustomerId.isEmpty()) {
				rxTypeQry
						.setParameter(RMDCommonConstants.CUS_ID, strCustomerId);
			}

			if (rxGroupId != 0) {
				rxTypeQry.setParameter(RMDCommonConstants.RECOM_GROUPID,
						rxGroupId);
				rxTypeQry.setParameter(RMDCommonConstants.RECOM_STATUS,
						RMDServiceConstants.APPROVED);
			}

			List<GetCustomerRxGroupHVO> rxTypes = rxTypeQry.list();
			if (null != rxTypes && !rxTypes.isEmpty()) {
				GetCustomerRxTitleHVO eachcustomerRxTitle = null;
				for (GetCustomerRxGroupHVO rxType : rxTypes) {

					if (null != strCustomerId && !strCustomerId.isEmpty()
							&& rxGroupId == 0) {
						eachObjActionableRxTypeVO = new ActionableRxTypeVO();
						eachObjActionableRxTypeVO.setRxTypeCd(rxType
								.getRxGroupId().getRxTypeCd());
						eachObjActionableRxTypeVO.setRxGroupId(RMDCommonUtility
								.convertObjectToString(rxType.getRxGroupId()
										.getRxGroupId()));
						Set<GetCustomerRxTitleHVO> customerRxTitleSet = rxType
								.getCustomerRxTitles();
						int count = 0;
						if (null != customerRxTitleSet
								&& false == customerRxTitleSet.isEmpty()) {
							Iterator<GetCustomerRxTitleHVO> itr = customerRxTitleSet
									.iterator();

							while (itr.hasNext() && count < 1) {
								boolean flag = true;
								eachcustomerRxTitle = itr.next();
								if (null != strCustomerId
										&& !strCustomerId.isEmpty()
										&& !strCustomerId.equals(rxType
												.getCustomerId().getOrgId())) {
									flag = false;
								}

								if (flag) {
									eachObjActionableRxTypeVO
											.setCustomerId(rxType
													.getCustomerId().getOrgId());
									count++;
								}
							}
						}

						actionableRxTypes.add(eachObjActionableRxTypeVO);
					} else if (rxGroupId != 0) {

						Set<GetCustomerRxTitleHVO> customerRxTitleSet = rxType
								.getCustomerRxTitles();

						if (null != customerRxTitleSet
								&& false == customerRxTitleSet.isEmpty()) {
							Iterator<GetCustomerRxTitleHVO> itr = customerRxTitleSet
									.iterator();

							while (itr.hasNext()) {
								boolean flag = true;
								eachcustomerRxTitle = itr.next();
								if (null != strCustomerId
										&& !strCustomerId.isEmpty()
										&& !strCustomerId.equals(rxType
												.getCustomerId().getOrgId())) {
									flag = false;
								}
								if (flag) {
									eachObjActionableRxTypeVO = new ActionableRxTypeVO();
									eachObjActionableRxTypeVO
											.setRxTypeCd(rxType.getRxGroupId()
													.getRxTypeCd());
									eachObjActionableRxTypeVO
											.setRxGroupId(RMDCommonUtility
													.convertObjectToString(rxType
															.getRxGroupId()
															.getRxGroupId()));
									eachObjActionableRxTypeVO
											.setCustomerId(rxType
													.getCustomerId().getOrgId());
									eachObjActionableRxTypeVO
											.setCustomerName(rxType
													.getCustomerId().getName());
									eachObjActionableRxTypeVO
											.setRxTitle(eachcustomerRxTitle
													.getRxTitleId().getTitle());
									eachObjActionableRxTypeVO
											.setRxTitleId(RMDCommonUtility
													.convertObjectToString(eachcustomerRxTitle
															.getRxTitleId()
															.getObjid()));
									actionableRxTypes
											.add(eachObjActionableRxTypeVO);
								}
							}
						}

					}

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("in side getActionable rx types: End");
		return actionableRxTypes;
	}

	/*
	 * Added for Sprint 14 Actionable Rx story (non-Javadoc)
	 * 
	 * @see com.ge.trans.rmd.services.tools.rx.dao.intf.SelectRxDAOIntf#
	 * getActionableRxTypes (strCustomerId,strLanguage) This method is used to
	 * fetch non actionable rx titles based on the customer from the database.
	 * If customer is null, then it will fetch all titles
	 */
	@Override
	@Cacheable(value = RMDCommonConstants.CACHE_NON_ACTIONABLE_RX_TITLES, key = "#strCustomerId+#strLanguage")
	public List<ActionableRxTypeVO> getNonActionableRxTitles(
			final String strCustomerId, final String strLanguage)
			throws RMDDAOException {
		LOG.debug("in side getNonActionable rx types: Start");
		List<ActionableRxTypeVO> actionableRxTypes = new ArrayList<ActionableRxTypeVO>();
		Session hibernateSession = null;
		StringBuffer rxTypeQuery = new StringBuffer();
		ActionableRxTypeVO objActionableRxTypeVO = null;
		try {
			hibernateSession = getHibernateSession();
			/*
			 * this method fetches the non actionable rx titles based on the
			 * customer id. If customer id is null then it fetches all non
			 * actionable titles
			 */

			rxTypeQuery
					.append("SELECT RECOM.title, RECOM.objid FROM GetSdRecomHVO AS RECOM WHERE RECOM.objid NOT IN");
			rxTypeQuery
					.append(" (SELECT CRXT.rxTitleId FROM GetCustomerRxTitleHVO AS CRXT, GetRxGroupHVO AS RXG, ");
			rxTypeQuery.append(" GetCustomerRxGroupHVO AS CRG ");

			if (null != strCustomerId && !strCustomerId.isEmpty()) {
				rxTypeQuery.append(", GetTableBusOrgHVO AS CUSTOMER");
			}

			rxTypeQuery.append(" WHERE CRXT.custRxGroupId = CRG.custRxGroupId");
			rxTypeQuery.append(" AND CRG.rxGroupId = RXG.rxGroupId");
			rxTypeQuery.append(" AND RXG.activeFlg='Y'");

			if (null != strCustomerId && !strCustomerId.isEmpty()) {
				rxTypeQuery.append(" AND CRG.customerId = CUSTOMER.objid");
				rxTypeQuery.append(" AND CUSTOMER.orgId=:customerId");
			}

			rxTypeQuery
					.append(") AND RECOM.recomStatus=:recomStatus ORDER BY RECOM.title");

			Query rxTypeQry = hibernateSession.createQuery(rxTypeQuery
					.toString());

			if (null != strCustomerId && !strCustomerId.isEmpty()) {
				rxTypeQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
						strCustomerId);
			}
			rxTypeQry.setParameter(RMDCommonConstants.RECOM_STATUS,
					RMDServiceConstants.APPROVED);

			List<Object> rxTypes = rxTypeQry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(rxTypes)) {
				for (Object rxType : rxTypes) {

					objActionableRxTypeVO = new ActionableRxTypeVO();
					Object rxTypeObj[] = (Object[]) rxType;
					objActionableRxTypeVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(rxTypeObj[0]));
					objActionableRxTypeVO.setRxTitleId(RMDCommonUtility
							.convertObjectToString(rxTypeObj[1]));

					actionableRxTypes.add(objActionableRxTypeVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("in side getNonActionable rx types: End");
		return actionableRxTypes;
	}

	@Override
	public void checkRxDeliveryStatus(String caseId, String strLanguage)
			throws RMDDAOException {
		// TODO Auto-generated method stub

		Session hibernateSession = null;
		StringBuffer rxStatusQryBuffer = new StringBuffer();
		List<Object> rxStatusLst = null;
		boolean rxExists = false;

		Date delvDate = null;

		try {
			if (isValidEOACase(caseId)) {
				hibernateSession = getHibernateSession();
				rxStatusQryBuffer
						.append("SELECT DELV_DATE FROM GETS_SD_RECOM_DELV, TABLE_CASE CASE1, GETS_SD_CUST_FDBK CUST_FDBK  ");
				rxStatusQryBuffer
						.append("  WHERE RECOM_DELV2CASE = CASE1.OBJID  ");
				rxStatusQryBuffer
						.append("  AND CUST_FDBK.CUST_FDBK2CASE = CASE1.OBJID  ");
				rxStatusQryBuffer
						.append("  AND RX_CLOSE_DATE IS NULL AND CASE1.ID_NUMBER = :caseId  ");

				Query rxStatusQry = hibernateSession
						.createSQLQuery(rxStatusQryBuffer.toString());
				rxStatusQry.setParameter("caseId", caseId);

				// rxStatusLst = rxStatusQry.list();

				/*
				 * if (RMDCommonUtility.isCollectionNotEmpty(rxStatusLst)) { //
				 * Collection not epmty means already a recommendation is
				 * delivered for the given case. rxExists = true; throw new
				 * RMDDAOException
				 * (RMDCommonUtility.getErrorCode(RMDServiceConstants
				 * .DAO_EXCEPTION_RX_EXISTS)); }
				 */

				delvDate = (Date) rxStatusQry.uniqueResult();
				if (null != delvDate) {

					rxExists = true;
					throw new RMDDAOException();
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {

			String errorCode = null;
			if (rxExists) {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_EXISTS);
			} else {
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_EXISTS);
			}

			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("in side checkRxDeliveryStatus: End");

	}

	/**
	 * 
	 */
	@Override
	public String getRxScoreStatus(String rxCaseId, String language)
			throws RMDDAOException {
		// TODO Auto-generated method stub

		Session hibernateSession = null;
		StringBuffer rxStatusQryBuffer = new StringBuffer();
		List<Object> rxScoreStatusList = null;
		boolean rxScoreStatus = false;
		String caseSuccess = null;

		try {
			hibernateSession = getHibernateSession();
			rxStatusQryBuffer
					.append("SELECT CASE_SUCCESS FROM GETS_SD_CUST_FDBK CUST_FDBK   WHERE CUST_FDBK.RX_CASE_ID = :rxCaseId");

			Query rxStatusQry = hibernateSession
					.createSQLQuery(rxStatusQryBuffer.toString());
			rxStatusQry.setParameter("rxCaseId", rxCaseId);

			caseSuccess = (String) rxStatusQry.uniqueResult();

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							language), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);

			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							language), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("in side getRxScoreStatus: End");

		return caseSuccess;

	}

	/**
	 * 
	 */
	@Override
	public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language)
			throws RMDDAOException {
		// TODO Auto-generated method stub

		LOG.debug("in side getRxDeliveryStatus: Start");
		Session hibernateSession = null;
		StringBuffer rxStatusQryBuffer = new StringBuffer();

		RecomDelvStatusVO recomDelvVO = null;
		Object[] result = null;
		List resultList = null;

		try {
			if (isValidEOACase(caseId)) {
				recomDelvVO = new RecomDelvStatusVO();
				hibernateSession = getHibernateSession();
				rxStatusQryBuffer
						.append(" SELECT delv_date, recom_delv2recom, cust_fdbk.service_req_id_status  FROM gets_sd_recom_delv delv, ");
				rxStatusQryBuffer
						.append(" table_case case1  , gets_sd_cust_fdbk  cust_fdbk,GETS_SD_CASE_RECOM casrec   WHERE recom_delv2case(+)   = case1.objid ");
				rxStatusQryBuffer
						.append(" AND cust_fdbk.cust_fdbk2case(+) = case1.objid   AND rx_close_date   IS NULL  AND  RECOM_DELV2CUST_FDBK =  CUST_FDBK.OBJID  ");
				rxStatusQryBuffer
						.append(" AND casrec.CASE_RECOM2RECOM_DELV = delv.OBJID AND case1.id_number =  :caseId ");

				Query rxDelvQry = hibernateSession
						.createSQLQuery(rxStatusQryBuffer.toString());
				rxDelvQry.setParameter("caseId", caseId);
				// rxDelvQry.setLong("caseId",
				// RMDCommonUtility.convertObjectToLong(caseId));

				resultList = rxDelvQry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)
						&& resultList.size() > 1) {
					String errorCode = RMDCommonUtility
							.getErrorCode(RMDCommonConstants.REPLACE_MODIFY_RX_210);
					throw new RMDDAOException(errorCode);
				} else if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

					result = (Object[]) resultList.get(0);

					if (result[0] != null) {
						recomDelvVO.setDelvDate((Date) result[0]);
					}
					if (result[1] != null) {
						recomDelvVO.setRecomDelvTorecom(RMDCommonUtility
								.convertObjectToLong(result[1]));
					}
					if (result[2] != null) {
						recomDelvVO.setServiceReqIdStatus((String) result[2]);
					} else {
						recomDelvVO
								.setServiceReqIdStatus(RMDCommonConstants.EMPTY_STRING);
					}
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							language), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException e) {
			if (null != e.getErrorDetail()) {
				throw new RMDDAOException(e.getErrorDetail());
			} else {
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.FATAL_ERROR);
			}
		} catch (Exception e) {

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIONABLE_RX_TYPE);

			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							language), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("in side getRxDeliveryStatus: End");

		return recomDelvVO;

	}

	@Override
	public List getRxLookupValues(final String strListName,
			final String strLanguage) throws RMDDAOException {
		ArrayList<ElementVO> lstLookupValues = null;
		try {
			lstLookupValues = (ArrayList) getLookupListValues(strListName,
					strLanguage);
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in RecommDAOImpl getRxLookupValues()",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
		}
		return lstLookupValues;
	}

	/**
	 * @Author:
	 * @param :
	 * @return:List<ElementVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used to get values from lookup to populate
	 *               the subsystem drop downlist.
	 */
	@Override
	public List<ElementVO> getSubsystem() throws RMDDAOException {

		Session session = null;
		final StringBuffer strQuery = new StringBuffer();
		List<Object[]> arlList;
		ElementVO objElementVO = null;
		List<ElementVO> lstSubsystems = new ArrayList<ElementVO>();

		try {
			session = getHibernateSession();
			strQuery.append("SELECT DISTINCT SUBSYSTEM,SUBSYSTEM_DESC FROM GETS_SD_DASHER_CODES ORDER BY SUBSYSTEM");
			Query query = session.createSQLQuery(strQuery.toString());
			arlList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(arlList)) {
				for (int i = 0; i < arlList.size(); i++) {
					objElementVO = new ElementVO();
					Object subsystems[] = (Object[]) arlList.get(i);
					objElementVO.setId(RMDCommonUtility	
							.convertObjectToString(subsystems[0]));
					objElementVO.setName(RMDCommonUtility
							.convertObjectToString(subsystems[1]));
					lstSubsystems.add(objElementVO);
				}

			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBSYSTEM);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDServiceConstants.LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in FindCaseDAOImpl getDeliveredCases()",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBSYSTEM);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDServiceConstants.LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);

		} finally {
			releaseSession(session);
		}

		return lstSubsystems;

	}

	/**
	 * @Author:
	 * @param :RxSearchCriteriaEoaServiceVO
	 * @return:Map<String,String>
	 * @throws:RMDDAOException
	 * @Description: This method is used to get models for the recommendations
	 *               which are listed.
	 */
	@Override
	public Map<String, String> getModelsForRx(
			RxSearchCriteriaEoaServiceVO objSearchCriteriaEoaServiceVO)
			throws RMDDAOException {
		Session session = null;
		Map<String, String> modelsMap = new HashMap<String, String>();
		Query hibernateQuery = null;
		Boolean blnKM = false;
		try {
			session = getHibernateSession();
			objSearchCriteriaEoaServiceVO
					.setIsModelQuery(RMDCommonConstants.LETTER_Y);
			hibernateQuery = getSolutionsQuery(objSearchCriteriaEoaServiceVO,
					session);
			blnKM = objSearchCriteriaEoaServiceVO.isBlnKM();
			List<Object[]> arlList = hibernateQuery.list();
			for (Object[] currentObject : arlList) {
				String recomId = RMDCommonUtility
						.convertObjectToString(currentObject[0]);
				String model = RMDCommonUtility
						.convertObjectToString(currentObject[1]);
				if (modelsMap.containsKey(recomId)) {
					if (blnKM) {
						modelsMap.put(recomId, modelsMap.get(recomId)
								+ RMDCommonConstants.COMMMA_SEPARATOR
								+ RMDCommonConstants.BLANK_SPACE + model);
					} else {
						modelsMap.put(recomId, modelsMap.get(recomId)
								+ RMDCommonConstants.COMMMA_SEPARATOR + model);
					}
				} else {
					modelsMap.put(recomId, model);
				}
			}
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in FindCaseDAOImpl getModelsForRx()",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODELS_FOR_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDServiceConstants.LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);

		} finally {
			releaseSession(session);
		}

		return modelsMap;
	}
	
	public Map<String, List<String>> getVehBomDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO){
	    Session session = null;
        StringBuilder vehBomQuery = new StringBuilder();
        StringBuilder vehObjIdQuery = new StringBuilder();
        Map<String, List<String>> vehBomMp= null;
        List<Object[]> resultList=null; 
        List<String> typeValue;
        try{
            session = getHibernateSession();
            if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getCustomer())){
                vehBomQuery.append("SELECT DISTINCT B.CONFIG_ITEM CONFIGITEM,C.CURRENT_VERSION CVER FROM GETS_RMD_PARMDEF A,GETS_RMD_MASTER_BOM B, GETS_RMD_VEHCFG C ");
                vehBomQuery.append("WHERE A.OBJID(+) = B.MASTER_BOM2PARM_DEF AND B.OBJID(+) = C.VEHCFG2MASTER_BOM AND C.VEH_CFG2VEHICLE IN("); 
                vehObjIdQuery.append("SELECT VEHICLE_OBJID FROM gets_rmd_cust_rnh_rn_v WHERE CUST_NAME=:customer AND VEHICLE_NO NOT LIKE '%BAD%' ");
                if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getRnh()) || !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFleet())){
                    if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getRnh())){
                        vehObjIdQuery.append("AND VEHICLE_HDR=:rnh ");
                    }else if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFleet())){
                        vehObjIdQuery.append("AND FLEET_NUMBER_V=:fleet ");
                    }
                    if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getAssetList())){
                        vehObjIdQuery.append("AND VEHICLE_NO IN (:asset) ");
                    }else if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFromRN()) && !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getToRN())){
                            vehObjIdQuery.append("AND LPAD(VEHICLE_NO, 30,'0') BETWEEN LPAD(:fromRN, 30,'0') AND LPAD(:toRN, 30,'0') ");
                    }
                    
                }
                vehBomQuery.append(vehObjIdQuery+""+" )  AND B.BOM_STATUS='Y' AND C.CURRENT_VERSION IS NOT NULL");
            }
            
            final Query vehBomHQuery = session.createSQLQuery(vehBomQuery
                    .toString());
            if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getCustomer())){
                vehBomHQuery.setParameter(RMDCommonConstants.CUSTOMER, rxSearchCriteriaServiceVO.getCustomer());
                if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getRnh()) || !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFleet())){
                    if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getRnh())){
                        vehBomHQuery.setParameter(RMDCommonConstants.RNH, rxSearchCriteriaServiceVO.getRnh());
                    }else if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFleet())){
                        vehBomHQuery.setParameter(RMDCommonConstants.FLEET, rxSearchCriteriaServiceVO.getFleet());
                    }
                    if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getAssetList())){
                        List<String> rnList= Arrays.asList(rxSearchCriteriaServiceVO.getAssetList().split(RMDCommonConstants.COMMMA_SEPARATOR));
                        vehBomHQuery.setParameterList(RMDCommonConstants.ASSET, rnList);
                    }else if(!RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getFromRN()) && !RMDCommonUtility.isNullOrEmpty(rxSearchCriteriaServiceVO.getToRN())){
                            vehBomHQuery.setParameter(RMDCommonConstants.FROM_RN, rxSearchCriteriaServiceVO.getFromRN());
                            vehBomHQuery.setParameter(RMDCommonConstants.TO_RN, rxSearchCriteriaServiceVO.getToRN());
                    }
                }
            }
            resultList=vehBomHQuery.list();
            if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
                vehBomMp=new HashMap<String, List<String>>();
                for(Object[] obj: resultList){
                    String type = RMDCommonUtility.convertObjectToString(obj[0]);
                    if(vehBomMp.containsKey(type)){
                        vehBomMp.get(type).add(RMDCommonUtility.convertObjectToString(obj[1]));
                    }else{
                        typeValue = new ArrayList<String>();
                        typeValue.add(RMDCommonUtility.convertObjectToString(obj[1]));
                        vehBomMp.put(type, typeValue);
                    }
                }
                
            }
            
        }catch(Exception e){
            System.out.println("Exception occured in getVehBoms() "+e);
        }finally{
            releaseSession(session);
        }
	    
        return vehBomMp;
	    
	}
}
