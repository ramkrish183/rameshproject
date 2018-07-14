/**
 * ============================================================
 * Classification: GE Confidential
 * File : BaseCaseDAO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.impl;
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

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHistoryHVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
abstract public class BaseCaseDAO extends BaseDAO {

    private static final long serialVersionUID = 1L;

    public BaseCaseDAO() {
        super();
    }

    /**
     * @Author:
     * @param objCaseHistoryHVO
     * @param strUserName
     * @Description:
     */
    @Override
    public void insertActivityTable(GetCmCaseHistoryHVO objCaseHistoryHVO, String strUserName) {
        Session session = null;
        try {
            session = getHibernateSession(strUserName);
            session.save(objCaseHistoryHVO);
            session.flush();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseHistoryHVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseHistoryHVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @Author:
     * @return String
     * @param caseId
     * @Description: To fetch the customer name for a case
     */
    @Override
    public String getCustomerNameForCase(String caseId) {
        Session session = null;
        try {
            session = getHibernateSession();
            final String strCustomerNameQuery = "SELECT asset.CUST_NAME FROM TABLE_CASE tc, GETS_RMD_CUST_RNH_RN_V asset "
                    + "WHERE tc.CASE_PROD2SITE_PART = asset.SITE_PART_OBJID AND tc.ID_NUMBER = :caseId ";
            final Query queryCustomerName = session.createSQLQuery(strCustomerNameQuery.toString());
            queryCustomerName.setParameter(RMDCommonConstants.CASEID, caseId);
            final String custName = queryCustomerName.uniqueResult().toString().trim();
            return custName;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }
}
