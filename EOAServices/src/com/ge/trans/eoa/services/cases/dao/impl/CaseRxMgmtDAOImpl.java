/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseRxMgmtDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.impl;
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
package com.ge.trans.eoa.services.cases.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.dao.intf.CaseRxMgmtDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 9, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CaseRxMgmtDAOImpl extends BaseCaseDAO implements CaseRxMgmtDAOIntf {

    private static final long serialVersionUID = 1759054420908452748L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CaseRxMgmtDAOImpl.class);

    /**
     * @param
     * @return List<RecomDelvInfoServiceVO>
     * @throws RMDDAOException
     * @Description To fetch the case RX details from gets_sd_case_recom table
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDDAOException {
        // TODO Auto-generated method stub
        RecomDelvInfoServiceVO recomVO = null;
        Session session = null;
        Query hibernateQuery = null;
        List<Object[]> arrQueryList = null;
        List<RecomDelvInfoServiceVO> recomList = new ArrayList<RecomDelvInfoServiceVO>();
        try {
            session = getHibernateSession();
            String recomInfoQuery = "SELECT CASE_RECOM2RECOM,RECOM.TITLE,RECOM.URGENCY,recom.EST_REPAIR_TIME "
                    + "FROM GETS_SD_CASE_RECOM caserecom,TABLE_CASE tc,GETS_SD_RECOM recom "
                    + "WHERE caserecom.CASE_RECOM2CASE = tc.OBJID AND caserecom.CASE_RECOM2RECOM = recom.objid AND tc.id_number=:caseId";

            hibernateQuery = session.createSQLQuery(recomInfoQuery);
            hibernateQuery.setFetchSize(10);
            hibernateQuery.setParameter(RMDCommonConstants.CASEID, caseId);
            arrQueryList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrQueryList)) {
                final Iterator<Object[]> iter = arrQueryList.iterator();
                while (iter.hasNext()) {
                    final Object[] recomRow = iter.next();
                    recomVO = new RecomDelvInfoServiceVO();
                    recomVO.setStrRxObjid(RMDCommonUtility.convertObjectToString(recomRow[0]));
                    recomVO.setStrRxTitle(RMDCommonUtility.convertObjectToString(recomRow[1]));
                    recomVO.setStrUrgRepair(RMDCommonUtility.convertObjectToString(recomRow[2]));
                    recomVO.setStrEstmRepTime(RMDCommonUtility.convertObjectToString(recomRow[3]));
                    recomList.add(recomVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return recomList;
    }

    @Override
    public void addRXTocase(RecomDelvInfoServiceVO caseRXVO) throws RMDDAOException {
        // TODO Auto-generated method stub
        Session session = null;
        Query hibernateQuery = null;
        List<Object[]> arrQuery = null;
        List<Object> arrQueryList = null;
        String urgency = RMDCommonConstants.EMPTY_STRING;
        String estRepTime = RMDCommonConstants.EMPTY_STRING;
        String urgencyFromRecom = RMDCommonConstants.EMPTY_STRING;
        String estRepTimeFromRecom = RMDCommonConstants.EMPTY_STRING;
        String urgencyFromCaseRecom = RMDCommonConstants.EMPTY_STRING;
        String estRepTimeFromCaseRecom = RMDCommonConstants.EMPTY_STRING;
        String version = RMDCommonConstants.EMPTY_STRING;
        String urgencyAndRepTimeQuery = RMDCommonConstants.EMPTY_STRING;
        String insertAddRXQuery = RMDCommonConstants.EMPTY_STRING;
        String updateAddRXQuery = RMDCommonConstants.EMPTY_STRING;
        boolean isCaseRecomExist = false;
        boolean isUrgEstTimeOverride = false;
        int caseRecomObjid = 0;
        long caseObjid = 0;
        try {
            session = getHibernateSession();
            // Query to get the case objid for inserting or updating to Case
            // Recom table
            String caseObjidQuery = "SELECT OBJID FROM TABLE_CASE WHERE ID_NUMBER = :caseId";
            final Query queryCustomerName = session.createSQLQuery(caseObjidQuery.toString());
            queryCustomerName.setParameter(RMDCommonConstants.CASEID, caseRXVO.getStrCaseID());
            caseObjid = Long.parseLong(queryCustomerName.uniqueResult().toString());

            if (null != caseRXVO.getStrUrgRepair() && !caseRXVO.getStrUrgRepair().isEmpty()
                    && null != caseRXVO.getStrEstmRepTime() && !caseRXVO.getStrEstmRepTime().isEmpty()) {
                isUrgEstTimeOverride = true;
            }
            /*
             * Query to retrieve the recom details from GETS_SD_CASE_RECOM table
             * With this query it is possible to conclude whether the
             * recommendation is already existing in the case Recom table for
             * that particular case
             */
            urgencyAndRepTimeQuery = "SELECT caseRecom.OBJID,caseRecom.URGENCY,caseRecom.EST_REPAIR_TIME,caseRecom.VERSION "
                    + "FROM GETS_SD_CASE_RECOM caseRecom "
                    + "WHERE caserecom.CASE_RECOM2CASE = :caseId AND CASE_RECOM2RECOM =:recom_objid ";
            hibernateQuery = session.createSQLQuery(urgencyAndRepTimeQuery);
            hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID, caseRXVO.getStrRxObjid());
            hibernateQuery.setParameter(RMDCommonConstants.CASEID, caseObjid);
            hibernateQuery.setFetchSize(10);
            arrQuery = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrQuery)) {
                // Setting isCaseRecomExist to true is the record already exist
                isCaseRecomExist = true;
                final Iterator<Object[]> iter = arrQuery.iterator();
                if (iter.hasNext()) {
                    final Object[] urgencyAndRepTimeDetailsRow = iter.next();
                    caseRecomObjid = RMDCommonUtility.convertObjectToInt(urgencyAndRepTimeDetailsRow[0]);
                    urgencyFromCaseRecom = RMDCommonUtility.convertObjectToString(urgencyAndRepTimeDetailsRow[1]);
                    estRepTimeFromCaseRecom = RMDCommonUtility.convertObjectToString(urgencyAndRepTimeDetailsRow[2]);
                }
            }
            /*
             * Retrieving the recom details from the recommendation table
             * GETS_SD_RECOM contains the default urgency, est rep time and
             * version for the recommendation
             */
            urgencyAndRepTimeQuery = "SELECT URGENCY,EST_REPAIR_TIME,VERSION FROM GETS_SD_RECOM WHERE OBJID =:recom_objid";
            hibernateQuery = session.createSQLQuery(urgencyAndRepTimeQuery);
            hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID, caseRXVO.getStrRxObjid());
            hibernateQuery.setFetchSize(10);
            arrQuery = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrQuery)) {
                final Iterator<Object[]> iter = arrQuery.iterator();
                if (iter.hasNext()) {
                    final Object[] urgencyAndRepTimeDetailsRow = iter.next();
                    urgencyFromRecom = RMDCommonUtility.convertObjectToString(urgencyAndRepTimeDetailsRow[0]);
                    estRepTimeFromRecom = RMDCommonUtility.convertObjectToString(urgencyAndRepTimeDetailsRow[1]);
                    version = RMDCommonUtility.convertObjectToString(urgencyAndRepTimeDetailsRow[2]);
                }
            }

            // Deciding whether to override the urgency or use the urgency from
            // the recom table

            if (isUrgEstTimeOverride) {
                urgency = caseRXVO.getStrUrgRepair();
                estRepTime = caseRXVO.getStrEstmRepTime();
            } else {
                urgency = urgencyFromRecom;
                estRepTime = estRepTimeFromRecom;
            }

            if (isCaseRecomExist) {

                if (!urgency.equalsIgnoreCase(urgencyFromCaseRecom)
                        || !estRepTime.equalsIgnoreCase(estRepTimeFromCaseRecom)) {
                    updateAddRXQuery = "UPDATE GETS_SD_CASE_RECOM SET URGENCY = :urgency, EST_REPAIR_TIME = :strEstTime, "
                            + "LAST_UPDATED_DATE =sysdate, LAST_UPDATED_BY=:userName WHERE OBJID = :caseRecomObjid";
                    hibernateQuery = session.createSQLQuery(updateAddRXQuery);
                    hibernateQuery.setParameter(RMDCommonConstants.EST_REPAIR_TIME, estRepTime);
                    hibernateQuery.setParameter(RMDCommonConstants.URGENCY, urgency);
                    hibernateQuery.setParameter(RMDCommonConstants.CASE_RECOM_OBJID, caseRecomObjid);
                    hibernateQuery.setParameter(RMDCommonConstants.USERNAME, caseRXVO.getStrUserName());
                    hibernateQuery.executeUpdate();
                } else if (!isUrgEstTimeOverride) {
                    // Throwing exception if the record already exist in case
                    // and if the function is not called by deliver rx
                    String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.RX_EXIST_ERROR_CODE);
                    throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                            new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), null,
                            RMDCommonConstants.MINOR_ERROR);
                }
            } else {

                // Inserting in the the GETS_SD_CASE_RECOM table
                insertAddRXQuery = "INSERT INTO GETS_SD_CASE_RECOM (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,"
                        + " CASE_RECOM2CASE,CASE_RECOM2RECOM,REISSUE_FLAG,EST_REPAIR_TIME,URGENCY,VERSION,OB_MSG_ID) "
                        + " VALUES (GETS_SD_CASE_RECOM_SEQ.nextval,sysdate,:userName,sysdate,:userName,:caseSeqId,:recom_objid,null,"
                        + ":strEstTime,:urgency,:version,null)";
                hibernateQuery = session.createSQLQuery(insertAddRXQuery);
                hibernateQuery.setParameter(RMDCommonConstants.USERNAME, caseRXVO.getStrUserName());
                hibernateQuery.setParameter(RMDCommonConstants.CASESEQID, caseObjid);
                hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID, Integer.parseInt(caseRXVO.getStrRxObjid()));
                hibernateQuery.setParameter(RMDCommonConstants.EST_REPAIR_TIME, estRepTime);
                hibernateQuery.setParameter(RMDCommonConstants.URGENCY, urgency);
                hibernateQuery.setParameter(RMDCommonConstants.VERSION, RMDCommonUtility.convertObjectToInt(version));
                hibernateQuery.executeUpdate();

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(), new String[] {},
                        e.getErrorDetail().getErrorMessage(), e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

}
