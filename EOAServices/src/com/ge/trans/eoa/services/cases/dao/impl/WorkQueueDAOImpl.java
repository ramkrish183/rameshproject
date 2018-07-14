/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueDAOImpl.java
 * Description :
 *
 * Package :  com.ge.trans.rmd.services.cases.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 7,2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.WorkQueueDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7,2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class WorkQueueDAOImpl extends BaseDAO implements WorkQueueDAOIntf {

    @Autowired
    private CaseEoaDAOIntf objCaseEoaDAOIntf;
    private static final long serialVersionUID = 154542L;
    public static final RMDLogger LOG = RMDLogger.getLogger(WorkQueueDAOImpl.class);

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#
     * getDynamicWorkQueueCasesSummary()
     */
    @Override
    public List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDDAOException {
        List<OpenCaseHomeVO> arlWQCases = new ArrayList<OpenCaseHomeVO>();
        Session session = null;
        String priority = null;
        OpenCaseHomeVO objOpenCaseHomeVO = null;
        Map<String, String> caseTypeMap = new HashMap<String, String>();
        try {
            caseTypeMap = objCaseEoaDAOIntf.getCaseType();
            session = getHibernateSession();
            StringBuilder strQry = new StringBuilder();
            strQry.append(" SELECT /*+ no_parallel no_merge(WORK_QUEUE) use_nl(TC SITE_PART)*/ WORK_QUEUE.ID_NUMBER, ");
            strQry.append(" WORK_QUEUE.ORG_ID,  WORK_QUEUE.SERIAL_NO,  WORK_QUEUE.CASE_TITLE,  WORK_QUEUE.PRIORITY , ");
            strQry.append(
                    " WORK_QUEUE.AGE,  TO_CHAR(TO_DATE(WORK_QUEUE.CREATION_TIME,'MM-DD-YY HH24:MI:SS'), 'MM/DD/YYYY HH24:MI:SS') CREATION_TIME, ");
            strQry.append(
                    " WORK_QUEUE.CONDITION,  WORK_QUEUE.CASE_TYPE,  WORK_QUEUE.WIPBIN_NAME,  CASEREASON,  CASEREASON_SORT, SITE_PART.X_VEH_HDR ");
            strQry.append(" FROM GETS_RMD.DYNAMIC_WORK_QUEUE_V WORK_QUEUE, TABLE_CASE TC, TABLE_SITE_PART SITE_PART ");
            strQry.append(" WHERE WORK_QUEUE.CASE_OBJID=TC.OBJID ");
            strQry.append(" AND TC.CASE_PROD2SITE_PART=SITE_PART.OBJID ");
            if (!RMDCommonUtility.isNullOrEmpty(objOpenCaseServiceVO.getStrCustomerId())) {
                strQry.append(" AND WORK_QUEUE.ORG_ID IN ( :customerId )");
            }
            strQry.append("  ORDER BY CASEREASON_SORT");
            Query qry = session.createSQLQuery(strQry.toString());
            if (!RMDCommonUtility.isNullOrEmpty(objOpenCaseServiceVO.getStrCustomerId())) {
                List<String> customerList = Arrays
                        .asList(objOpenCaseServiceVO.getStrCustomerId().split(RMDCommonConstants.COMMMA_SEPARATOR));
                qry.setParameterList(RMDServiceConstants.CUSTOMER_ID, customerList);
            }
            List<Object[]> arlList = qry.list();
            if (arlList != null && arlList.size() > 0) {
                arlWQCases = new ArrayList<OpenCaseHomeVO>(arlList.size());
            }
            for (Object[] object : arlList) {
                objOpenCaseHomeVO = new OpenCaseHomeVO();
                objOpenCaseHomeVO.setStrCaseId(RMDCommonUtility.convertObjectToString(object[0]));
                objOpenCaseHomeVO.setStrCustomerId(RMDCommonUtility.convertObjectToString(object[1]));
                objOpenCaseHomeVO.setStrGrpName(RMDCommonUtility.convertObjectToString(object[12]));
                objOpenCaseHomeVO.setStrAssetNumber(RMDCommonUtility.convertObjectToString(object[2]));
                objOpenCaseHomeVO.setAge(RMDCommonUtility.convertObjectToString(object[5]));
                objOpenCaseHomeVO.setStrTitle(
                        AppSecUtil.htmlEscapingWithoutEncoding(RMDCommonUtility.convertObjectToString(object[3])));
                if (null != object[4]) {
                    priority = RMDCommonUtility.convertObjectToString(object[4]);
                    if (priority.equals("1"))
                        objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.CONDITIONAL);
                    else if (priority.equals("2"))
                        objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.ESTP);
                    else if (priority.equals("3"))
                        objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.HIGH);
                    else if (priority.equals("4"))
                        objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.MEDIUM);
                    else if (priority.equals("5"))
                        objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.LOW);
                } else {
                    objOpenCaseHomeVO.setStrPriority(RMDCommonConstants.LOW);
                }
                objOpenCaseHomeVO.setStrReason(RMDCommonUtility.convertObjectToString(object[10]));
                objOpenCaseHomeVO.setStrCaseType(caseTypeMap.get(RMDCommonUtility.convertObjectToString(object[8])));
                objOpenCaseHomeVO.setStrOwner(RMDCommonUtility.convertObjectToString(object[9]));
                objOpenCaseHomeVO.setCondition(
                        AppSecUtil.htmlEscapingWithoutEncoding(RMDCommonUtility.convertObjectToString(object[7])));
                if (null != RMDCommonUtility.convertObjectToString(object[6])) {
                    objOpenCaseHomeVO.setDtCreationDate(RMDCommonUtility.convertObjectToString(object[6]));
                }
                arlWQCases.add(objOpenCaseHomeVO);
            }
            arlList = null;
            // Added for Sorting DWQ To make Inbound CaseTypes at Top of the
            // Screen.
            Collections.sort(arlWQCases, new Comparator<OpenCaseHomeVO>() {
                @Override
                public int compare(OpenCaseHomeVO item1, OpenCaseHomeVO item2) {
                    if (RMDCommonConstants.INBOUND_REVIEW.equalsIgnoreCase(item1.getStrCaseType()))
                        return -1;
                    if (RMDCommonConstants.INBOUND_REVIEW.equalsIgnoreCase(item2.getStrCaseType()))
                        return 1;
                    return 0;
                }
            });
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_DWQ_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objOpenCaseServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in WorkQueueDAOImpl getDynamicWorkQueueCasesSummary()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_DWQ_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objOpenCaseServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlWQCases;
    }
}