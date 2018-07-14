/**
 * ============================================================
 * File : FaultServiceStgyDAOImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.fault.dao.impl;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.fault.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmFaultCodesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmFltStrategyHVO;
import com.ge.trans.eoa.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class FaultServiceStgyDAOImpl extends BaseDAO implements FaultServiceStgyDAOIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FaultServiceStgyDAOImpl.class);
    /**
     *
     */
    private static final long serialVersionUID = 4407518877814521043L;

    /**
     *
     */
    public FaultServiceStgyDAOImpl() {
    }

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final int FIVE = 5;

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf
     * #findFault(com.ge.trans.rmd.services.tools.fault.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for fetching fault details.
      // @Cacheable(value = "faultCodeCache")
      // @Cacheable(value = "faultCodeCache",key = "#strFamily")
	public List<FaultServiceStgyServiceVO> findFaultCode(
			String strFamily, String strFaultCode,boolean isExternalAuthor)
			throws RMDDAOException {
		final List<FaultServiceStgyServiceVO> objFaultServiceStgyServiceVOLst = new ArrayList<FaultServiceStgyServiceVO>();
		Session session = null;
		FaultServiceStgyServiceVO faultServiceStgyServiceVO = null;
		StringBuilder queryString = new StringBuilder();
		Query query = null;
		int intFaultCode = 0;
		String strModelName=null;
		int controllerSourceId = 34;
		try {
			LOG.debug("findFaultCode FaultServiceStgyDAOImpl EXECUTED QUERY "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));


			if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
				
				if (RMDServiceConstants.FAMILY_CCA.equalsIgnoreCase(strFamily)) {
					intFaultCode = ONE;
				} else if (RMDServiceConstants.QNXEQUIPMENT
						.equalsIgnoreCase(strFamily)) {
					intFaultCode = TWO;
					//controllerSourceId =  25;
				}  else if( RMDServiceConstants.DHMS.equalsIgnoreCase(strFamily) ){
					intFaultCode = FIVE;
					controllerSourceId =  34;
				}
				// if the family selected is "PinPoint" or RMD Equipment or
				// LOCOCAM
				// or SMPP or OIL
				else if (RMDServiceConstants.PINPOINT
						.equalsIgnoreCase(strFamily)
						|| RMDServiceConstants.RMDEQUIMENT
								.equalsIgnoreCase(strFamily)
						|| RMDServiceConstants.LOCOCAM
								.equalsIgnoreCase(strFamily)
						|| RMDServiceConstants.SMPP.equalsIgnoreCase(strFamily)
						|| RMDServiceConstants.RMDOIL
								.equalsIgnoreCase(strFamily)) {
					intFaultCode = THREE;
				} else {
					intFaultCode = RMDCommonConstants.NUMERIC_4;
				}
			}
			session = getHibernateSession();
			switch (intFaultCode) {
			case ONE:
				//if the family passed is CCA				
				queryString.append("SELECT * FROM ( ");
				queryString.append("SELECT DISTINCT FAULT_CODE, FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) FROM GETS_RMD.GETS_RMD_FAULT_CODES FC");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE");
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) AND");
				}
				queryString.append(" CONTROLLER_SOURCE_ID = 20 GROUP BY FAULT_CODE,FAULT_DESC UNION ");
				queryString.append("SELECT DISTINCT FAULT_CODE, FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) FROM GETS_RMD.GETS_RMD_FAULT_CODES FC");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE");
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) AND");
				}
				queryString.append(" CONTROLLER_SOURCE_ID = 21 GROUP BY FAULT_CODE,FAULT_DESC)");
				if(!RMDCommonUtility.isNullOrEmpty(strFaultCode)){					
				queryString.append("WHERE LOWER(FAULT_CODE) LIKE LOWER(:faultCode) ");
				query = session.createSQLQuery(queryString.toString());
				query.setParameter(RMDServiceConstants.FAULT_CODE,
						strFaultCode);
				}
				else{
					query = session.createSQLQuery(queryString.toString());
				}
				break;
			case TWO:
				//if the family is 'QNX Equipment'
				queryString.append("SELECT DISTINCT FAULT_CODE,FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) ");
				queryString.append(" FROM GETS_RMD.GETS_RMD_FAULT_CODES FC ");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE");
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) AND");
				}
				queryString.append(" CONTROLLER_SOURCE_ID IN (25,30)");
				if(!RMDCommonUtility.isNullOrEmpty(strFaultCode)){
					queryString.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");					
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAULT_CODE,
							strFaultCode);
					}
					else{
						queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
						query = session.createSQLQuery(queryString.toString());
					}

				break;
			case FIVE:
				//if the family is 'DHMS'
				queryString.append("SELECT DISTINCT FAULT_CODE,FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) ");
				queryString.append(" FROM GETS_RMD.GETS_RMD_FAULT_CODES FC");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE");
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) AND");
				}
				queryString.append(" CONTROLLER_SOURCE_ID IN ("+controllerSourceId+")");
				if(!RMDCommonUtility.isNullOrEmpty(strFaultCode)){					
					queryString.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAULT_CODE,
							strFaultCode);
					}
					else{
						queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
						query = session.createSQLQuery(queryString.toString());
					}

				break;
			case THREE:
				//if the family is 'PinPoint' or RMD Equipment or LOCOCAM or SMPP or OIL
				queryString.append("SELECT DISTINCT FAULT_CODE,FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC)");
				queryString
						.append(" FROM GETS_RMD.GETS_RMD_FAULT_CODES FC");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE");
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) AND");
				}				
				queryString.append(" CONTROLLER_SOURCE_ID IN (18,24)");
				if(!RMDCommonUtility.isNullOrEmpty(strFaultCode)){					
					queryString.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAULT_CODE,
							strFaultCode);
					}
					else{
						queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
						query = session.createSQLQuery(queryString.toString());
					}
				break;
			case RMDCommonConstants.NUMERIC_4:				
				//if the family passed is ACCCA or DCCCA
				queryString.append("SELECT  DISTINCT FAULT_CODE,FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) ");
				queryString.append("FROM GETS_RMD.GETS_RMD_FAULT_CODES FC, GETS_RMD.GETS_RMD_CONTROLLER RC,GETS_RMD.GETS_RMD_CTL_CFG CC,GETS_RMD.GETS_RMD_CTL_CFG_SRC CCS");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				queryString.append(" WHERE FC.CONTROLLER_SOURCE_ID = RC.CONTROLLER_SOURCE_ID AND RC.CONTROLLER_SOURCE_ID = CCS.CONTROLLER_SOURCE_ID AND CCS.CTL_CFG_SRC2CTL_CFG = CC.OBJID ");
				queryString.append(" AND CC.FAMILY =:Family ");
				if(isExternalAuthor){
					queryString.append(" AND FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2) ");
				}	
				if(!RMDCommonUtility.isNullOrEmpty(strFaultCode)){					
					queryString.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAMILY,
							strFamily);
					query.setParameter(RMDServiceConstants.FAULT_CODE,
							strFaultCode);
				}
				else{
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAMILY,
							strFamily);
				}
				break;
			default:
				queryString
						.append("SELECT  DISTINCT(FAULT_CODE),FAULT_DESC,LISTAGG(SUB_ID,',') WITHIN GROUP (ORDER BY FAULT_CODE,FAULT_DESC) FROM GETS_RMD.GETS_RMD_FAULT_CODES FC");
				if(isExternalAuthor){
					queryString.append(",GETS_RMD_FLT_STRATEGY FS");
				}
				if(isExternalAuthor||!RMDCommonUtility.isNullOrEmpty(strFaultCode)){
				queryString.append(" WHERE");
				}
				if(isExternalAuthor){
					queryString.append(" FLT_STRAT2FLT_CODE = FC.OBJID AND DIAGNOSTIC_WEIGHT IN (0,1,2)");
				}		
				if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
					queryString
							.append(" AND LOWER(FAULT_CODE) LIKE LOWER(:faultCode)");
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
					query.setParameter(RMDServiceConstants.FAULT_CODE,
							RMDServiceConstants.PERCENTAGE + strFaultCode
									+ RMDServiceConstants.PERCENTAGE);
				} else {
					queryString.append(" GROUP BY FAULT_CODE,FAULT_DESC");
					query = session.createSQLQuery(queryString.toString());
				}

			}

			LOG.debug("findFaultCode FaultServiceStgyDAOImpl QUERY EXECUTION BEGINS*** "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));
			final List<Object> lookupList = query.list();
			LOG.debug("findFaultCode FaultServiceStgyDAOImpl QUERY EXECUTION ENDS*** "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));
			LOG.debug("findFaultCode FaultServiceStgyDAOImpl ITERATION BEGINS *** "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));
			if (lookupList != null && !lookupList.isEmpty()) {

				for (final Iterator<Object> iter = lookupList.iterator(); iter
						.hasNext();) {

					final Object[] lookupRecord = (Object[]) iter.next();
					faultServiceStgyServiceVO = new FaultServiceStgyServiceVO();

					faultServiceStgyServiceVO.setStrFaultCode(lookupRecord[0]
							.toString());
					faultServiceStgyServiceVO.setStrDescription(lookupRecord[1]
							.toString());
					faultServiceStgyServiceVO.setSubIds(lookupRecord[2]
							.toString());

					objFaultServiceStgyServiceVOLst
							.add(faultServiceStgyServiceVO);

				}
			
			}
			LOG.debug("findFaultCode FaultServiceStgyDAOImpl ITERATION COMPLETED *** "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		LOG.debug("****findFaultCode FaultServiceStgyDAOImpl ends***** "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS").format(new Date()));
		return objFaultServiceStgyServiceVOLst;
	}

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf
     * #findFault(com.ge.trans.rmd.services.tools.fault.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for fetching fault details.
    @Override
    public FaultServiceStgyServiceVO findFault(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDDAOException {
        List arlFaultData = null;
        Session session = null;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            /*
             * Criteria to find out the Fault details by providing Fault Code as
             * Input
             */
            Criteria criteria = session.createCriteria(GetKmFaultCodesHVO.class)
                    .setFetchMode(RMDServiceConstants.GET_KM_FLT_STRATEGIES, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.GET_KM_FLT_STRATEGIES,
                            RMDServiceConstants.GET_KM_FLT_STRATEGIES_ALIAS)
                    .add(Restrictions.eq(RMDServiceConstants.FAULT_CODE,
                            objFaultServiceStgyServiceVO.getStrFaultCode()))
                    .add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION,
                            objFaultServiceStgyServiceVO.getStrLanguage()));
            ;
            arlFaultData = criteria.list(); // Fault code details
            Set strategySet = null;
            Iterator itr = null;
            int size = RMDCommonConstants.INT_CONST;
            List alFaultClassification = null;
            List arlRuleUsingFc = new ArrayList();
            GetKmFltStrategyHVO faultStrategyVO = null;
            GetKmFaultCodesHVO faultServiceVO = null;
            ElementVO objElementVO = null;
            int intFaultSize = arlFaultData.size();
            // Populate the Fault Code Details to the Service VO
            for (int index = 0; index < intFaultSize; index++) {
                faultServiceVO = (GetKmFaultCodesHVO) arlFaultData.get(index);
                objFaultServiceStgyServiceVO.setLngFltCodesSeqId(faultServiceVO.getGetKmFaultCodesSeqId());
                objFaultServiceStgyServiceVO.setStrFaultOrgin(faultServiceVO.getFaultOrigin());
                objFaultServiceStgyServiceVO.setStrFaultCode(faultServiceVO.getFaultCode());
                objFaultServiceStgyServiceVO.setStrDescription(faultServiceVO.getFaultDesc());
                objFaultServiceStgyServiceVO.setDtLastModifiedDate(faultServiceVO.getLastUpdatedDate());
                objFaultServiceStgyServiceVO.setStrLastModifiedBy(faultServiceVO.getLastUpdatedBy());
                // Get the Fault Service Strategy details for the Fault Code
                strategySet = faultServiceVO.getGetKmFltStrategies();
                itr = strategySet.iterator();
                size = strategySet.size();
                alFaultClassification = new ArrayList();
                for (int counter = 0; counter < size; counter++) {
                    faultStrategyVO = (GetKmFltStrategyHVO) itr.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(faultStrategyVO.getFaultClassification());
                    objElementVO.setName(faultStrategyVO.getFaultClassification());
                    objFaultServiceStgyServiceVO.setStrNotes(faultStrategyVO.getNotes());
                    objFaultServiceStgyServiceVO.setStrToolUrg(faultStrategyVO.getCriticalFlag());
                    objFaultServiceStgyServiceVO.setStrfaultClassSelected(faultStrategyVO.getFaultClassification());
                    alFaultClassification.add(objElementVO);
                }
                objFaultServiceStgyServiceVO.setAlFaultClassification(alFaultClassification);

                // Forming the query to get the Rules which has used the given
                // Fault Code
                StringBuilder strRuleQuery = new StringBuilder();
                strRuleQuery.append(
                        " SELECT GET_KM_DPD_FINRUL_SEQ_ID, RULE_DESCRIPTION FROM GET_KM.GET_KM_DPD_FINRUL WHERE GET_KM_DPD_FINRUL_SEQ_ID IN((SELECT DISTINCT FIN.GET_KM_DPD_FINRUL_SEQ_ID ");
                strRuleQuery.append(
                        " FROM GET_KM.GET_KM_DPD_SIMRUL SIMRUL, GET_KM.GET_KM_DPD_FINRUL FIN WHERE FIN.LINK_SIMRUL = SIMRUL.GET_KM_DPD_SIMRUL_SEQ_ID ");
                strRuleQuery.append(" AND SIMRUL.FAULT = :faultCode1) ");
                strRuleQuery.append(" UNION (SELECT DISTINCT FIN.GET_KM_DPD_FINRUL_SEQ_ID");
                strRuleQuery.append(
                        " FROM GET_KM.GET_KM_DPD_SIMRUL SIMRUL, GET_KM.GET_KM_DPD_CMP_LINK CMPLINK, GET_KM.GET_KM_DPD_CMPRUL CMPRUL, GET_KM.GET_KM_DPD_FINRUL FIN ");
                strRuleQuery.append(
                        " WHERE CMPLINK.LINK_ID = CMPRUL.GET_KM_DPD_CMPRUL_SEQ_ID AND FIN.LINK_CMPRUL = CMPRUL.GET_KM_DPD_CMPRUL_SEQ_ID ");
                strRuleQuery.append(" AND CMPLINK.REF_LINK_SIMRUL = SIMRUL.GET_KM_DPD_SIMRUL_SEQ_ID ");
                strRuleQuery.append(" AND SIMRUL.FAULT = :faultCode2)) ORDER BY 1 ASC ");
                Query query = session.createSQLQuery(strRuleQuery.toString());
                query.setParameter(RMDCommonConstants.FAULTCODE1, objFaultServiceStgyServiceVO.getStrFaultCode());
                query.setParameter(RMDCommonConstants.FAULTCODE2, objFaultServiceStgyServiceVO.getStrFaultCode());
                List arlRules = new ArrayList();
                arlRules = query.list(); // List of Rules
                for (Object finrulValues : arlRules) {
                    Object[] columns = (Object[]) finrulValues;
                    objElementVO = new ElementVO();
                    objElementVO.setId(columns[0].toString());
                    objElementVO.setName((String) columns[1]);
                    arlRuleUsingFc.add(objElementVO);
                }

                objFaultServiceStgyServiceVO.setAlFaultData(arlRuleUsingFc);
            }
            session.flush();
            session.clear();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlFaultData = null;
        }
        return objFaultServiceStgyServiceVO;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.dao.intf.FaultServiceStgyDAOIntf#
     * getLoadFaultClass()
     */// This method is used for loading Fault details.
    @Override
    public List getLoadFaultClasses(final FaultServiceStgyServiceVO fltServiceStgyVO) throws RMDDAOException {
        List lstClass = new ArrayList();
        Session session = null;
        try {
            session = getHibernateSession();
            lstClass = getLookupListValues(RMDServiceConstants.FLT_CLASSIFICATION,
                    fltServiceStgyVO.getStrUserLanguage());
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_CLASS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, fltServiceStgyVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_CLASS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, fltServiceStgyVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return lstClass;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf
     * #saveUpdate(com.ge.trans.rmd.services.tools.fault.service.valueobjects.
     * FaultServiceStgyServiceVO, java.lang.String)
     */// This Method is used for saving the fault details .
    @Override
    public String saveUpdate(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId)
            throws RMDDAOException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        String strCheck = RMDCommonConstants.NO;
        Session session = null;
        ArrayList arlFault;
        try {
            session = getHibernateSession(strUserId);
            arlFault = new ArrayList();

            Criteria criteriaCheck = session.createCriteria(GetKmFaultCodesHVO.class);
            /*
             * Check if the Fault Code is already available in database and if
             * available, return a message 'Fault code already exists'
             */
            if (objFaultServiceStgyServiceVO.getStrFaultCode() != null && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objFaultServiceStgyServiceVO.getStrFaultCode())) {
                criteriaCheck.add(Restrictions.eq(RMDServiceConstants.FAULT_CODE,
                        objFaultServiceStgyServiceVO.getStrFaultCode()));
            }
            criteriaCheck.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION,
                    objFaultServiceStgyServiceVO.getStrLanguage()));
            criteriaCheck.add(Restrictions.ne(RMDCommonConstants.GETKMFAULTCODE_SEQID,
                    objFaultServiceStgyServiceVO.getLngFltCodesSeqId()));
            arlFault = (ArrayList) criteriaCheck.list();
            if (null != arlFault && !arlFault.isEmpty()) {
                strCheck = RMDCommonConstants.YES;
            }
            if (RMDCommonConstants.NO.equalsIgnoreCase(strCheck)) {
                Criteria criteria = session.createCriteria(GetKmFaultCodesHVO.class);
                criteria.add(Restrictions.eq(RMDCommonConstants.GETKMFAULTCODE_SEQID,
                        objFaultServiceStgyServiceVO.getLngFltCodesSeqId()));

                /*
                 * Setting the Fault Code details in GetKmFaultCodesHVO for
                 * updating into database
                 */
                GetKmFaultCodesHVO faultHVO = (GetKmFaultCodesHVO) criteria.list().get(0);
                faultHVO.setFaultDesc(objFaultServiceStgyServiceVO.getStrDescription());
                faultHVO.setFaultCode(objFaultServiceStgyServiceVO.getStrFaultCode());
                Set strategySet = faultHVO.getGetKmFltStrategies();
                Iterator itr = strategySet.iterator();
                int size = strategySet.size();
                GetKmFltStrategyHVO faultStrategyVO = null;
                Set newFltStgySet = new HashSet();
                /*
                 * Setting the Fault Strategy details in GetKmFltStrategyHVO for
                 * updating into database
                 */
                for (int counter = 0; counter < size; counter++) {
                    faultStrategyVO = (GetKmFltStrategyHVO) itr.next();
                    faultStrategyVO.setNotes(objFaultServiceStgyServiceVO.getStrNotes());
                    faultStrategyVO.setFaultClassification(objFaultServiceStgyServiceVO.getStrfaultClassSelected());
                    faultStrategyVO.setCriticalFlag(objFaultServiceStgyServiceVO.getStrToolUrg());
                    newFltStgySet.add(faultStrategyVO);
                }
                faultHVO.setGetKmFltStrategies(newFltStgySet);
                session.saveOrUpdate(faultHVO);
                session.flush();
                session.clear();
                strResult = RMDServiceConstants.FSS_SAVE_SUCCESS;
            } else {
                strResult = strCheck;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_FSS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_FSS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return strResult;
    }

    /**
     * @param objFaultServiceStgyServiceVO
     * @param strUserId
     * @return
     * @throws RMDDAOException
     */
    public String saveNewFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId)
            throws RMDDAOException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        Session session = null;
        try {
            session = getHibernateSession(strUserId);
            // Setting in GetKmFaultCodesHVO for saving a new Fault Code into DB
            GetKmFaultCodesHVO objGCodesHVO = new GetKmFaultCodesHVO();
            objGCodesHVO.setFaultCode(objFaultServiceStgyServiceVO.getStrFaultCode());
            objGCodesHVO.setFaultDesc(objFaultServiceStgyServiceVO.getStrDescription());
            objGCodesHVO.setFaultLabel(objFaultServiceStgyServiceVO.getStrFaultCode());
            objGCodesHVO.setStrLanguage(objFaultServiceStgyServiceVO.getStrLanguage());
            session.save(objGCodesHVO);
            session.flush();

            // Setting in GetKmFltStrategyHVO for saving into DB
            GetKmFltStrategyHVO faultStrategyVO = new GetKmFltStrategyHVO();
            faultStrategyVO.setGetKmFaultCodes(objGCodesHVO);
            faultStrategyVO.setNotes(objFaultServiceStgyServiceVO.getStrNotes());
            faultStrategyVO.setFaultClassification(objFaultServiceStgyServiceVO.getStrfaultClassSelected());
            faultStrategyVO.setCriticalFlag(objFaultServiceStgyServiceVO.getStrToolUrg());
            session.save(faultStrategyVO);
            session.flush();
            session.clear();
            strResult = RMDServiceConstants.FSS_SAVE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_FSS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_FSS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return strResult;
    }

    /* This Method is used to check if a fault code exists in database */
    /**
     * @param strFaultCode
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    @Override
    public String checkFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, String strUserId)
            throws RMDDAOException {
        Session session = null;
        List arlFault = null;
        String strResult = null;
        try {
            arlFault = new ArrayList();
            session = getHibernateSession();
            Criteria criteria = session.createCriteria(GetKmFaultCodesHVO.class);
            /*
             * Check if the Fault Code is already available in database and if
             * available, return a message 'Fault code already exists'
             */
            if (objFaultServiceStgyServiceVO.getStrFaultCode() != null && !RMDCommonConstants.EMPTY_STRING
                    .equalsIgnoreCase(objFaultServiceStgyServiceVO.getStrFaultCode())) {
                criteria.add(Restrictions.eq(RMDServiceConstants.FAULT_CODE,
                        objFaultServiceStgyServiceVO.getStrFaultCode()));
            }
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION,
                    objFaultServiceStgyServiceVO.getStrLanguage()));
            arlFault = criteria.list();
            if (null != arlFault && !arlFault.isEmpty()) {
                strResult = RMDCommonConstants.YES;
            } else {
                strResult = saveNewFaultCode(objFaultServiceStgyServiceVO, strUserId);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULTCODE);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULTCODE);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, objFaultServiceStgyServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlFault = null;
        }
        return strResult;
    }

    /**
     * @Author:iGATE
     * @param strModelName
     * @return
     * @throws RMDDAOException
     * @Description: This method will be retrieved faultcodes based on Model
     *               value passed.
     */
    @Override
    @Cacheable(value = "faultCode_ModelCache", key = "#strModelName")
    public List<FaultServiceStgyServiceVO> getFaultCodeforModels(String strModelName) throws RMDDAOException {
        // TODO Auto-generated method stub
        Session session = null;
        Query query = null;
        StringBuilder queryString = new StringBuilder();
        boolean isCCAFamily = false;
        Set<String> familyAvailableSet = new HashSet<String>();
        FaultServiceStgyServiceVO faultServiceStgyServiceVO = null;
        final List<FaultServiceStgyServiceVO> objFaultServiceStgyServiceVOLst = new ArrayList<FaultServiceStgyServiceVO>();
        String[] strarray = null;
        try {
            session = getHibernateSession();
            query = session.createSQLQuery(
                    "SELECT DISTINCT FAMILY FROM GETS_RMD_MODEL WHERE MODEL_NAME IN (:modelName) and MODEL_DESC IN (:modelName)");
            query.setParameterList(RMDCommonConstants.MODEL_NAME,
                    strModelName.split(RMDCommonConstants.COMMMA_SEPARATOR));
            List<String> familyList = query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(familyList)) {
                int iCount = 0;
                for (String string : familyList) {
                    if (string.equalsIgnoreCase("ACCCA") || string.equalsIgnoreCase("DCCCA")) {
                        isCCAFamily = true;
                        familyAvailableSet.add(familyList.get(iCount++));
                    }
                }
            }
            if (isCCAFamily) {
                queryString.append("SELECT  DISTINCT FAULT_CODE,FAULT_DESC ");
                queryString.append(
                        "FROM GETS_RMD.GETS_RMD_FAULT_CODES FC, GETS_RMD.GETS_RMD_CONTROLLER RC,GETS_RMD.GETS_RMD_CTL_CFG CC,GETS_RMD.GETS_RMD_CTL_CFG_SRC CCS ");
                queryString.append(
                        " WHERE FC.CONTROLLER_SOURCE_ID = RC.CONTROLLER_SOURCE_ID AND RC.CONTROLLER_SOURCE_ID = CCS.CONTROLLER_SOURCE_ID AND CCS.CTL_CFG_SRC2CTL_CFG = CC.OBJID ");
                queryString.append(" AND CC.FAMILY in (:Family) and FC.last_Updated_date < sysdate-30");
            }
            if (RMDCommonUtility.isCollectionNotEmpty(familyList)) {
                if (RMDCommonUtility.isCollectionNotEmpty(familyAvailableSet)) {
                    strarray = familyAvailableSet.toArray(new String[0]);
                } else {
                    queryString.append(
                            "SELECT  DISTINCT(FAULT_CODE),FAULT_DESC FROM GETS_RMD.GETS_RMD_FAULT_CODES where last_Updated_date < sysdate-30");
                }
            } else {
                queryString.append(
                        "SELECT  DISTINCT(FAULT_CODE),FAULT_DESC FROM GETS_RMD.GETS_RMD_FAULT_CODES where last_Updated_date <sysdate-30");
            }
            query = session.createSQLQuery(queryString.toString());
            if (strarray != null) {
                // String strFamilyArry[]=strFamily.split(",");
                query.setParameterList(RMDServiceConstants.FAMILY, strarray);
            }

            final List<Object> lookupList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    final Object[] lookupRecord = (Object[]) iter.next();
                    faultServiceStgyServiceVO = new FaultServiceStgyServiceVO();
                    faultServiceStgyServiceVO.setStrFaultCode(lookupRecord[0].toString());
                    faultServiceStgyServiceVO.setStrDescription(lookupRecord[1].toString());
                    objFaultServiceStgyServiceVOLst.add(faultServiceStgyServiceVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_FAULT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return objFaultServiceStgyServiceVOLst;
    }

    /**
     * @Author :
     * @return :FaultServiceStrategyVO
     * @param :fsObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching FaultStrategy Details based
     *                   on fsObjId.
     */
    @Override
    public FaultServiceStrategyVO getFaultStrategyDetails(String fsObjId) throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        FaultServiceStrategyVO objFaultServiceStrategyVO = null;
        DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(
                    " SELECT A.OBJID,A.FAULT_ORIGIN,A.FAULT_DESC,A.FAULT_LABEL,B.DIAGNOSTIC_WEIGHT,B.SUBSYSTEM_WEIGHT, ");
            caseQry.append(" B.OBJID STRATOBJID,B.MODE_RESTRICTION,B.FAULT_CLASSIFICATION,B.NOTES,B.LAST_UPDATED_BY, ");
            caseQry.append(
                    " TO_CHAR(B.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS'),A.SUB_ID,A.FAULT_CODE,B.CRITICAL_FLAG,B.FAULT_LAG_TIME FROM GETS_RMD_FLT_STRATEGY B, ");
            caseQry.append(
                    " GETS_RMD_FAULT_CODES A WHERE A.OBJID = B.FLT_STRAT2FLT_CODE  AND B.OBJID =:fsObjId ORDER BY B.CREATION_DATE DESC ");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.FAULT_STRATEGY_OBJID, fsObjId);
            caseHqry.setFetchSize(10);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    objFaultServiceStrategyVO = new FaultServiceStrategyVO();
                    final Object[] faultStrategyDetails = obj.next();
                    objFaultServiceStrategyVO
                            .setFltStart2FltCode(RMDCommonUtility.convertObjectToString(faultStrategyDetails[0]));
                    objFaultServiceStrategyVO
                            .setFaultOrigin(RMDCommonUtility.convertObjectToString(faultStrategyDetails[1]));
                    objFaultServiceStrategyVO
                            .setFaultCode(RMDCommonUtility.convertObjectToString(faultStrategyDetails[13]));
                    objFaultServiceStrategyVO
                            .setFaultSubId(RMDCommonUtility.convertObjectToString(faultStrategyDetails[12]));
                    objFaultServiceStrategyVO.setFaultDesc(
                    		EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(faultStrategyDetails[2])));
                    objFaultServiceStrategyVO
                            .setFaultLabel(RMDCommonUtility.convertObjectToString(faultStrategyDetails[3]));
                    objFaultServiceStrategyVO
                            .setDiagnosticWeight(RMDCommonUtility.convertObjectToString(faultStrategyDetails[4]));
                    objFaultServiceStrategyVO
                            .setSubSysWeight(RMDCommonUtility.convertObjectToString(faultStrategyDetails[5]));
                    objFaultServiceStrategyVO
                            .setModeRestriction(RMDCommonUtility.convertObjectToString(faultStrategyDetails[7]));
                    objFaultServiceStrategyVO
                            .setFaultClassification(RMDCommonUtility.convertObjectToString(faultStrategyDetails[8]));
                    objFaultServiceStrategyVO
                            .setFaultCriticalFlag(RMDCommonUtility.convertObjectToString(faultStrategyDetails[14]));

                    if (null != RMDCommonUtility.convertObjectToString(faultStrategyDetails[11])) {
                        Date lastUpdatedDate = formatter
                                .parse(RMDCommonUtility.convertObjectToString(faultStrategyDetails[11]));
                        objFaultServiceStrategyVO.setLastUpdatedDate(lastUpdatedDate);
                    }

                    objFaultServiceStrategyVO
                            .setLastUpdatedBy(RMDCommonUtility.convertObjectToString(faultStrategyDetails[10]));
                    objFaultServiceStrategyVO
                            .setFaultLagTime(RMDCommonUtility.convertObjectToString(faultStrategyDetails[15]));
                    objFaultServiceStrategyVO.setFaultNotes(RMDCommonUtility.convertObjectToString(ESAPI.encoder()
                            .encodeForXML(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(faultStrategyDetails[9])))));
                    objFaultServiceStrategyVO
                            .setFsObjId(RMDCommonUtility.convertObjectToString(faultStrategyDetails[6]));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_STRATEGY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_STRATEGY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return objFaultServiceStrategyVO;
    }

    /**
     * @Author :
     * @return :List<FaultServiceStrategyVO>
     * @param :faultCode
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Fault Rule & Desc based on
     *                   faultCode.
     */
    @Override
    public List<FaultServiceStrategyVO> getRuleDesc(String faultCode) throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        FaultServiceStrategyVO objFaultServiceStrategyVO = null;
        List<FaultServiceStrategyVO> lstFaultServiceStrategy = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(
                    " SELECT FR.OBJID, FR.RULE_DESCRIPTION FROM GETS_TOOL_DPD_SIM_MTM_FAULT M, GETS_TOOL_DPD_SIMRUL SR, GETS_TOOL_DPD_FINRUL FR, GETS_TOOL_DPD_RULHIS RH  ");
            caseQry.append(
                    " WHERE RULHIS2FINRUL = FR.OBJID AND SIMRUL2FINRUL = FR.OBJID AND FAULT2SIMRUL = SR.OBJID AND ACTIVE = 1 ");
            caseQry.append(" AND FAULT_CODE =:faultCode UNION  ");
            caseQry.append(
                    " SELECT FR.OBJID, FR.RULE_DESCRIPTION FROM  GETS_TOOL_DPD_SIMRUL SR, GETS_TOOL_DPD_FINRUL FR, GETS_TOOL_DPD_RULHIS RH ");
            caseQry.append(" WHERE RULHIS2FINRUL = FR.OBJID AND SIMRUL2FINRUL = FR.OBJID AND ACTIVE = 1 ");
            caseQry.append(" AND FAULT =:faultCode ORDER BY 2 DESC ");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.FAULT_CODE_ID, faultCode);
            caseHqry.setFetchSize(10);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                lstFaultServiceStrategy = new ArrayList<FaultServiceStrategyVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    objFaultServiceStrategyVO = new FaultServiceStrategyVO();
                    final Object[] faultStrategyDetails = obj.next();
                    objFaultServiceStrategyVO
                            .setFaultRule(RMDCommonUtility.convertObjectToString(faultStrategyDetails[0]));
                    objFaultServiceStrategyVO
                            .setFaultRuleDesc(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(faultStrategyDetails[1])));
                    lstFaultServiceStrategy.add(objFaultServiceStrategyVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_RULE_DESC);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_RULE_DESC);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return lstFaultServiceStrategy;
    }

    /**
     * @Author :
     * @return :
     * @param :FaultServiceStrategyVO
     * @throws :RMDDAOException
     * @Description:This method is used for updateing FaultServiceStrategy based
     *                   on FaultServiceStrategyVO.
     */
    @Override
    public void updateFaultServiceStrategy(FaultServiceStrategyVO objFaultServiceStrategyVO) {
        Session session = null;
        Query hibernateQuery = null;
        String updateFaultSS = RMDCommonConstants.EMPTY_STRING;
        String updateFaultSeS = RMDCommonConstants.EMPTY_STRING;
        String faultNotes = EsapiUtil.escapeSpecialChars(objFaultServiceStrategyVO.getFaultNotes());
        String faultDesc =  EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(objFaultServiceStrategyVO.getFaultDesc()));
        try {
            session = getHibernateSession();
            updateFaultSS = "UPDATE GETS_RMD_FLT_STRATEGY SET LAST_UPDATED_DATE= sysdate,LAST_UPDATED_BY=:userName,"
                    + "DIAGNOSTIC_WEIGHT=:diagnosticWeight, SUBSYSTEM_WEIGHT =:subSysWeight, MODE_RESTRICTION=:modeRestriction, FAULT_CLASSIFICATION=:faultClassification,NOTES=:fssrxnote,"
                    + "FLT_STRAT2FLT_CODE=:fltStart2FltCode,CRITICAL_FLAG=:criticalFlag,FAULT_LAG_TIME=:lagTime WHERE OBJID =:fsObjId";
            hibernateQuery = session.createSQLQuery(updateFaultSS);
            hibernateQuery.setParameter(RMDCommonConstants.USERNAME, objFaultServiceStrategyVO.getCmAliasName());
            hibernateQuery.setParameter(RMDCommonConstants.DIAGNOSTIC_WEIGHT,
                    objFaultServiceStrategyVO.getDiagnosticWeight());
            hibernateQuery.setParameter(RMDCommonConstants.SUBSYS_WEIGHT, objFaultServiceStrategyVO.getSubSysWeight());
            hibernateQuery.setParameter(RMDCommonConstants.MODE_RESTRICTION,
                    objFaultServiceStrategyVO.getModeRestriction());
            hibernateQuery.setParameter(RMDCommonConstants.FAULT_CLASSIFICATION,
                    objFaultServiceStrategyVO.getFaultClassification());
            hibernateQuery.setParameter(RMDCommonConstants.FAULTNOTES, faultNotes);
            hibernateQuery.setParameter(RMDCommonConstants.FLTSTART2FLTCODE,
                    objFaultServiceStrategyVO.getFltStart2FltCode());
            hibernateQuery.setParameter(RMDCommonConstants.CRITICAL_FLAG,
                    objFaultServiceStrategyVO.getFaultCriticalFlag());
            hibernateQuery.setParameter(RMDCommonConstants.FAULTLAGTIME, objFaultServiceStrategyVO.getFaultLagTime());
            hibernateQuery.setParameter(RMDCommonConstants.FAULT_STRATEGY_OBJID,
                    objFaultServiceStrategyVO.getFsObjId());
            hibernateQuery.executeUpdate();

            updateFaultSeS = "UPDATE GETS_RMD_FAULT_CODES SET LAST_UPDATED_DATE= sysdate,LAST_UPDATED_BY=:userName,FAULT_DESC=:description WHERE OBJID =:fltStart2FltCode";
            hibernateQuery = session.createSQLQuery(updateFaultSeS);
            hibernateQuery.setParameter(RMDCommonConstants.USERNAME, objFaultServiceStrategyVO.getCmAliasName());
            hibernateQuery.setParameter(RMDCommonConstants.FLTDESCRIPTION, faultDesc);
            hibernateQuery.setParameter(RMDCommonConstants.FLTSTART2FLTCODE,
                    objFaultServiceStrategyVO.getFltStart2FltCode());
            hibernateQuery.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_FAULT_SERVICE_STRATEGY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_FAULT_SERVICE_STRATEGY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @param
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultOrigin() throws RMDDAOException {
        Session session = null;
        Query faultOriginHQuery = null;
        StringBuilder faultOriginQry = new StringBuilder();
        List<FaultCodeVO> arlFaultOrigin = null;
        try {
            session = getHibernateSession();
            faultOriginQry.append(
                    "SELECT DISTINCT FAULT_ORIGIN FROM GETS_RMD_FAULT_CODES WHERE FAULT_ORIGIN IS NOT NULL ORDER BY FAULT_ORIGIN");
            faultOriginHQuery = session.createSQLQuery(faultOriginQry.toString());
            List<Object> faultOriginList = faultOriginHQuery.list();
            arlFaultOrigin = new ArrayList<FaultCodeVO>();
            for (Object currentItem : faultOriginList) {
                FaultCodeVO objCodeVO = new FaultCodeVO();
                objCodeVO.setFaultOrigin(RMDCommonUtility.convertObjectToString(currentItem));
                arlFaultOrigin.add(objCodeVO);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_ORIGIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getFaultOrigin method of FaultServiceStgyDAOImpl:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_ORIGIN);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlFaultOrigin;
    }

    /**
     * @param faultCode
     *            ,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultCodeSubId(String faultCode, String faultOrigin) throws RMDDAOException {
        Session session = null;
        StringBuilder faultCodeSubIDQry = new StringBuilder();
        List<FaultCodeVO> arlSubId = new ArrayList<FaultCodeVO>();
        ;
        try {

            session = getHibernateSession();
            faultCodeSubIDQry.append(
                    "SELECT OBJID,SUB_ID FROM GETS_RMD_FAULT_CODES WHERE FAULT_CODE=:faultCode AND FAULT_ORIGIN=:faultOrigin");
            Query faultCodeSubIDHQry = session.createSQLQuery(faultCodeSubIDQry.toString());
            faultCodeSubIDHQry.setParameter(RMDCommonConstants.FAULT_CODE_ID, AppSecUtil.decodeString(faultCode));
            faultCodeSubIDHQry.setParameter(RMDCommonConstants.FAULT_ORIGIN, faultOrigin);
            List<Object[]> ObjectList = faultCodeSubIDHQry.list();

            for (Object[] currentItem : ObjectList) {
                FaultCodeVO objFaultCodeVO = new FaultCodeVO();
                objFaultCodeVO.setSubId(RMDCommonUtility.convertObjectToString(currentItem[1]));
                arlSubId.add(objFaultCodeVO);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FAULT_CODE_SUBID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getFaultCodeSubId method of FaultServiceStgyDAOImpl:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FAULT_CODE_SUBID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

        return arlSubId;
    }

    /**
     * @param faultCode
     *            ,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDDAOException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */
    @Override
    public List<FaultCodeVO> getViewFSSFaultCode(String faultCode, String faultOrigin) throws RMDDAOException {

        Session session = null;
        Query hibernateQuery = null;
        StringBuilder faultCodesQry = new StringBuilder();
        List<FaultCodeVO> arlFaultCodes = new ArrayList<FaultCodeVO>();
        try {
            session = getHibernateSession();
            faultCodesQry
                    .append("SELECT  DISTINCT FAULT_ORIGIN,FAULT_CODE,FAULT_DESC FROM GETS_RMD_FAULT_CODES WHERE 1=1 ");
            if (null != faultOrigin && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(faultOrigin)) {
                faultCodesQry.append("AND FAULT_ORIGIN =:faultOrigin ");
            }
            if (!RMDCommonUtility.isNullOrEmpty(faultCode)) {
                faultCodesQry.append("AND UPPER(FAULT_CODE) LIKE :faultCode ");
            }
            hibernateQuery = session.createSQLQuery(faultCodesQry.toString());

            if (!RMDCommonUtility.isNullOrEmpty(faultCode)) {
                hibernateQuery.setParameter(RMDCommonConstants.FAULT_CODE_ID,
                        (AppSecUtil.decodeString(faultCode).toUpperCase()) + RMDServiceConstants.PERCENTAGE);
            }
            if (null != faultOrigin && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(faultOrigin)) {
                hibernateQuery.setParameter(RMDCommonConstants.FAULT_ORIGIN, faultOrigin);
            }

            List<Object[]> faultCodesList = hibernateQuery.list();
            for (Object[] faultCodeDetails : faultCodesList) {
                FaultCodeVO objFaultCodeVO = new FaultCodeVO();
                objFaultCodeVO.setFaultOrigin(RMDCommonUtility.convertObjectToString(faultCodeDetails[0]));
                objFaultCodeVO.setFaultCode(
                        AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(faultCodeDetails[1])));
                objFaultCodeVO.setFaultDescription(
                        ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(faultCodeDetails[2])));
                arlFaultCodes.add(objFaultCodeVO);

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIEW_FSS_FAULT_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getViewFSSFaultCode method of FaultServiceStgyDAOImpl:", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VIEW_FSS_FAULT_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlFaultCodes;
    }

    /**
     * @param FaultCodeVO
     *            objFaultCodeVO
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to get Fault Code ObjId
     */
    @Override
    public String getFaultStrategyObjId(FaultCodeVO objFaultCodeVO) throws RMDDAOException {
        Session session = null;
        StringBuilder strQuery = new StringBuilder();
        String faultStrategyObjId = RMDCommonConstants.FAILURE;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT B.OBJID FROM GETS_RMD_FAULT_CODES A, GETS_RMD_FLT_STRATEGY B  ");
            strQuery.append(
                    "WHERE A.OBJID = B.FLT_STRAT2FLT_CODE AND A.FAULT_CODE=:faultCode AND A.FAULT_ORIGIN=:faultOrigin AND  SUB_ID =:subId");
            Query hibernateQuery = session.createSQLQuery(strQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.FAULT_CODE_ID,
                    AppSecUtil.decodeString(objFaultCodeVO.getFaultCode()));
            hibernateQuery.setParameter(RMDCommonConstants.FAULT_ORIGIN, objFaultCodeVO.getFaultOrigin());
            hibernateQuery.setParameter(RMDCommonConstants.FAULT_SUBID, objFaultCodeVO.getSubId());
            List<Object> objectList = hibernateQuery.list();
            if (null != objectList && !objectList.isEmpty()) {
                faultStrategyObjId = RMDCommonUtility.convertObjectToString(objectList.get(0));
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_STRATEGY_OBJID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getFaultStrategyObjId method of FaultServiceStgyDAOImpl:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_STRATEGY_OBJID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return faultStrategyObjId;
    }
}