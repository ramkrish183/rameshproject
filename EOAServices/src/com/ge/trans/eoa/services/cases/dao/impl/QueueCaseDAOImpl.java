package com.ge.trans.eoa.services.cases.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.cases.dao.intf.QueueCaseDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class QueueCaseDAOImpl extends RMDCommonDAO implements QueueCaseDAOIntf {

    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOGGER = RMDLoggerHelper
            .getLogger(QueueCaseDAOImpl.class);

    

    /**
     * @Author:
     * @param:userId
     * @return:List<QueueCaseVO>
     * @throws:RMDWebException
     * @Description: This method is used for fetching list of Queues from the
     *               DataBase.
     */

    @Override
	@SuppressWarnings("unchecked")
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDDAOException{
        Session objHibernateSession = null;
        StringBuilder queueListQry = new StringBuilder();
        List lstQueueNames = null;
        QueueCaseVO queueCaseVO = null;
        List<QueueCaseVO> arlQueueNames = new ArrayList<QueueCaseVO>();
        try {
			/**queueListQry
					.append("SELECT objid,"
							+ "title FROM table_queue "
							+ "WHERE objid in "
							+ "(SELECT queue2user FROM MTM_QUEUE4_USER23 WHERE "
							+ "user_assigned2queue = (SELECT objid FROM table_user WHERE lower(web_login)= "
							+ "lower(:userID))) ORDER BY title");*/
			queueListQry
				.append("SELECT OBJID,TITLE FROM table_queue WHERE S_TITLE IN(SELECT DISTINCT pr.PRIVILEGE_NAME FROM GET_USR_ROLE_PRIVILEGES ur,GET_USR.get_usr_privilege pr ");
			queueListQry
				.append("WHERE ur.LINK_PRIVILEGES=pr.GET_USR_PRIVILEGE_SEQ_ID AND pr.RESOURCE_TYPE  IN ('eoaQueuesPrivilege') AND ur.LINK_ROLES  = :roleId) ORDER BY TITLE");
            objHibernateSession = getHibernateSession();
            Query query = objHibernateSession.createSQLQuery(queueListQry
                    .toString());
            query.setParameter(RMDCommonConstants.ROLE_ID, roleId);
            lstQueueNames = query.list();
			if (lstQueueNames != null && !lstQueueNames.isEmpty()) {
				 arlQueueNames = new ArrayList<QueueCaseVO>(lstQueueNames.size());
                for (final Iterator<Object> queueItr = lstQueueNames
                        .iterator(); queueItr.hasNext();) {
                    final Object[] object = (Object[]) queueItr.next();
                    queueCaseVO = new QueueCaseVO();
                    queueCaseVO.setQueueObjId(RMDCommonUtility
                            .convertObjectToString(object[0]));
                    queueCaseVO.setQueueTitle(RMDCommonUtility
                            .convertObjectToString(object[1]));
                    arlQueueNames.add(queueCaseVO);
					queueCaseVO=null;
                }
            }
            
        }
        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
			queueListQry =null;
			lstQueueNames=null;
            releaseSession(objHibernateSession);
        }
        return arlQueueNames;
    }
    
    /**
     * @Author:
     * @param:String queueobjid,String customerId
     * @return:List<QueueCaseVO>
     * @throws:RMDWebException
     * @Description: This method is used for fetching list of Cases Associated
     *               to particular Queue.
     */

    @Override
	@SuppressWarnings("unchecked")
    public List<QueueCaseVO> getQueueCases(String queueobjid, String customerId)
            throws RMDDAOException {
        Session objHibernateSession = null;
        final StringBuffer queueListQry = new StringBuffer();
        List lstQueueCases = null;
        QueueCaseVO queueCaseVO = null;
        List<QueueCaseVO> arlQueueCases = new ArrayList<QueueCaseVO>();
        try {
            queueListQry
                    .append("SELECT ID_NUMBER,X_VEH_HDR_CUST,X_VEH_HDR,SITE_PART_SERIAL_NO,TITLE,PRIORITY,SEVERITY, TO_CHAR(TRUNC(Sysdate-Age),'9999000')||' '|| LPAD(TRUNC(MOD((sysdate-age)*24, 24)),2,'0')||':' || LPAD(TRUNC(MOD((sysdate-age)*1440, 60)),2,'0') AGE, TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS'),STATUS,CONDITION,USER_LOGIN_NAME,BUS_ORG_ORG_ID ");
            queueListQry
                    .append(" FROM Table_Queelm_Case Where Que_Objid= :queueObjId ");
            if (!RMDCommonUtility.isNullOrEmpty(customerId)) {
                queueListQry.append(" AND BUS_ORG_ORG_ID IN (:customerId)");
            }
            queueListQry.append(" order by AGE desc");
            objHibernateSession = getHibernateSession();
            Query query = objHibernateSession.createSQLQuery(queueListQry
                    .toString());
            if (!RMDCommonUtility.isNullOrEmpty(customerId)) {
                List<String> customerList = Arrays.asList(customerId
                        .split(RMDCommonConstants.COMMMA_SEPARATOR));
                query.setParameterList(RMDCommonConstants.CUSTOMER_ID,
                        customerList);
            }
            query.setParameter(RMDCommonConstants.QUEUE_OBJID, queueobjid);
            lstQueueCases = query.list();
            if (lstQueueCases != null && lstQueueCases.size() > 0) {
                arlQueueCases = new ArrayList<QueueCaseVO>(lstQueueCases.size());
                for (final Iterator<Object> queueItr = lstQueueCases.iterator(); queueItr
                        .hasNext();) {
                    final Object[] object = (Object[]) queueItr.next();
                    queueCaseVO = new QueueCaseVO();
                    queueCaseVO.setCaseId(RMDCommonUtility
                            .convertObjectToString(object[0]));
                    queueCaseVO.setVehHdrCust(RMDCommonUtility
                            .convertObjectToString(object[1]));
                    queueCaseVO.setVehHdr(RMDCommonUtility
                            .convertObjectToString(object[2]));
                    queueCaseVO.setSitepartSerialNo(RMDCommonUtility
                            .convertObjectToString(object[3]));
                    queueCaseVO.setTitle(RMDCommonUtility
                            .convertObjectToString(object[4]));
                    queueCaseVO.setPriority(RMDCommonUtility
                            .convertObjectToString(object[5]));
                    queueCaseVO.setSeverity(RMDCommonUtility
                            .convertObjectToString(object[6]));

                    /*if (null != RMDCommonUtility
                            .convertObjectToString(object[7])) {
                        Date age = (Date) formatter.parse(RMDCommonUtility
                                .convertObjectToString(object[7]));
                        queueCaseVO.setAge(age);
                    }*/
                    
                    if (null != RMDCommonUtility.convertObjectToString(object[7])) {
                        queueCaseVO.setAge(RMDCommonUtility.convertObjectToString(object[7]));
                    }
                    
                    if (null != RMDCommonUtility.convertObjectToString(object[8])) {
                        queueCaseVO.setCreationTime(RMDCommonUtility.convertObjectToString(object[8]));
                    }

                    /*if (null != RMDCommonUtility
                            .convertObjectToString(object[8])) {
                        Date creationTime = (Date) formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(object[8]));
                        queueCaseVO.setCreationTime(creationTime);
                    }*/
                    queueCaseVO.setStatus(RMDCommonUtility
                            .convertObjectToString(object[9]));
                    queueCaseVO.setCondition(RMDCommonUtility
                            .convertObjectToString(object[10]));
                    queueCaseVO.setUserLoginName((RMDCommonUtility
                            .convertObjectToString(object[11])));
                    queueCaseVO.setCustomerId((RMDCommonUtility
                            .convertObjectToString(object[12])));
                    arlQueueCases.add(queueCaseVO);
                }
            } lstQueueCases =null;

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        return arlQueueCases;
    }
}
