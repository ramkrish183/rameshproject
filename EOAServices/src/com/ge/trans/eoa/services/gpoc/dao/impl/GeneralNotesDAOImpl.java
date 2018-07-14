/**
 * =================================================================
 * Classification: GE Confidential
 * File : GeneralNotesDAOImpl.java
 * Description : DAO Impl for General Notes
 * 
 * Package : com.ge.trans.eoa.services.gpoc.dao.impl
 * Author : General Electric
 * Last Edited By :
 * Version : 1.0
 * Created on : March 10, 2017
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.gpoc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

import org.owasp.esapi.ESAPI;
/*******************************************************************************
 * @Author : General Electric
 * @Version : 1.0
 * @DateCreated : March 10, 2017
 * @DateModified : March 10, 2017
 * @ModifiedBy : Sonal
 * @Contact :
 * @Description : DAO Impl for Popup Admin List
 * @History :
 ******************************************************************************/

@SuppressWarnings("unchecked")
public class GeneralNotesDAOImpl extends BaseDAO implements GeneralNotesDAOIntf {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -2696947735607447252L;
    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(GeneralNotesDAOImpl.class);
    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */

    @Override
    public String addGeneralNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDDAOException {

        Session session = null;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        String uniquerecord = RMDCommonConstants.EMPTY_STRING;
        String insertnotesdes = RMDCommonConstants.EMPTY_STRING;
        String userId = generalnotesVO.getEnteredby();
        String strEoaUserName = null;
        try {

            session = getHibernateSession(generalnotesVO.getEnteredby());
            query.append("select GETS_RMD.GENERAL_NOTES_SEQ.nextval from dual");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setFetchSize(1);
            List<Object> uniquerecordlimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(uniquerecordlimit)) {
                uniquerecord = RMDCommonUtility.convertObjectToString(uniquerecordlimit.get(0));
            }

            StringBuilder eoaUserQry = new StringBuilder();
            eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE UPPER(A.WEB_LOGIN) =:userId");
            
            hibernateQuery = session.createSQLQuery(eoaUserQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
            Object result = hibernateQuery.uniqueResult();
            if (null != result)
                strEoaUserName = result.toString();
            else
                strEoaUserName=userId;
            
            insertnotesdes = "INSERT INTO GETS_RMD.GENERAL_NOTES (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,NOTES_DESCRIPTION,ENTERED_BY,ENTERED_DATE,FLAG) "
                    + " VALUES (:uniquerecord,sysdate,:enteredBy,sysdate,:enteredBy,:generalnotesdesc,:gnenteredBy,sysdate,:flag)";
            hibernateQuery = session.createSQLQuery(insertnotesdes);
            hibernateQuery.setParameter(RMDCommonConstants.UNIQUE_RECORD, uniquerecord);
            hibernateQuery.setParameter(RMDCommonConstants.GENERAL_NOTES_DESC,
                    EsapiUtil.escapeSpecialChars(generalnotesVO.getNotesdesc()));
            hibernateQuery.setParameter(RMDCommonConstants.ENTERED_BY, strEoaUserName);
            hibernateQuery.setParameter(RMDCommonConstants.GN_ENTERED_BY, userId);
            hibernateQuery.setParameter(RMDCommonConstants.FLAG, generalnotesVO.getVisibilityFlag());
            hibernateQuery.executeUpdate();
        }

        catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_GENERAL_NOTES);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[]{}, null, null,
                        RMDCommonConstants.MINOR_ERROR);

            } else {
                final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_GENERAL_NOTES);
                throw new RMDDAOException(errorCode);
            }
        } finally {

            releaseSession(session);

        }
        return uniquerecord;

    }

    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */

    @Override
    public List<GeneralNotesEoaServiceVO> showAllGeneralNotes(String language) throws RMDDAOException {
        Session session = null;
        Query generalnotesHqry = null;
        List<Object[]> resultList = null;
        List<GeneralNotesEoaServiceVO> objVOlist = null;
        GeneralNotesEoaServiceVO gnVO = null;

        try {
            session = getHibernateSession();
            StringBuilder generalnotesQry = new StringBuilder();
            generalnotesQry
            .append("select OBJID,NOTES_DESCRIPTION,CREATED_BY,TO_CHAR(LAST_UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS'),FLAG FROM GETS_RMD.GENERAL_NOTES ORDER BY LAST_UPDATED_DATE DESC");
            generalnotesHqry = session.createSQLQuery(generalnotesQry.toString());
            generalnotesHqry.setFetchSize(1000);
            resultList = generalnotesHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                objVOlist = new ArrayList<GeneralNotesEoaServiceVO>(resultList.size());
                for(Object[] objCommNotes : resultList){    
                    gnVO = new GeneralNotesEoaServiceVO();
                    gnVO.setGeneralnotesSeqId(RMDCommonUtility.convertObjectToLong(objCommNotes[0]));
                    gnVO.setNotesdesc(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(objCommNotes[1])));                   
                    gnVO.setEnteredby(RMDCommonUtility.convertObjectToString(objCommNotes[2]));
                    gnVO.setLastUpdatedTime(RMDCommonUtility.convertObjectToString(objCommNotes[3]));
                    gnVO.setVisibilityFlag(RMDCommonConstants.Y_LETTER_UPPER.equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objCommNotes[4]))?RMDCommonConstants.STRING_YES:RMDCommonConstants.STRING_NO);
                    objVOlist.add(gnVO);
                }
            }
        }

        catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_GENERAL_NOTES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return objVOlist;
    }

    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */

    @Override
    public String removeGeneralNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException {
        Session session = null;
        StringBuilder removeQuery = new StringBuilder();
        Query removeHibernateQuery = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.SUCCESS;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            removeQuery.append("DELETE FROM GETS_RMD.GENERAL_NOTES WHERE OBJID = :generalnotesId");
            removeHibernateQuery = session.createSQLQuery(removeQuery.toString());
            for (GeneralNotesEoaServiceVO objVO : generalnotesVO) {
                removeHibernateQuery.setParameter(RMDCommonConstants.GENERAL_NOTES_SEQID,
                        objVO.getGeneralnotesSeqId());
                removeHibernateQuery.executeUpdate();

            }
            transaction.commit();
            status = RMDCommonConstants.SUCCESS;
            return status;
        } catch (Exception e) {
            if(null !=transaction)
                transaction.rollback();
            status = RMDServiceConstants.FAILURE;
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_GENERAL_NOTES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    // started for comm notes
    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */
    @Override
    public String addCommNotes(GeneralNotesEoaServiceVO generalnotesVO) throws RMDDAOException {

        Session session = null;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        String uniquerecord = RMDCommonConstants.EMPTY_STRING;
        String insertnotesdes = RMDCommonConstants.EMPTY_STRING;
        String userId = generalnotesVO.getEnteredby();
        String strEoaUserName = null;
        try {

            session = getHibernateSession(generalnotesVO.getEnteredby());
            query.append("SELECT GETS_RMD.COMM_NOTES_SEQ.nextval from dual");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setFetchSize(1);
            List<Object> uniquerecordlimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(uniquerecordlimit)) {
                uniquerecord = RMDCommonUtility.convertObjectToString(uniquerecordlimit.get(0));
            }

            StringBuilder eoaUserQry = new StringBuilder();
            eoaUserQry.append("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE UPPER(A.WEB_LOGIN) =:userId");
            hibernateQuery = session.createSQLQuery(eoaUserQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
            Object result = hibernateQuery.uniqueResult();
            if (null != result)
                strEoaUserName = result.toString();
            else
                strEoaUserName =userId;
            insertnotesdes = "INSERT INTO GETS_RMD.COMM_NOTES (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,NOTES_DESCRIPTION,ENTERED_BY,ENTERED_DATE,FLAG) "
                    + " VALUES (:uniquerecord,sysdate,:enteredBy,sysdate,:enteredBy,:generalnotesdesc,:gnenteredBy,sysdate,:flag)";
            hibernateQuery = session.createSQLQuery(insertnotesdes);
            hibernateQuery.setParameter(RMDCommonConstants.UNIQUE_RECORD, uniquerecord);
            hibernateQuery.setParameter(RMDCommonConstants.GENERAL_NOTES_DESC,
                    EsapiUtil.escapeSpecialChars(generalnotesVO.getNotesdesc()));
            hibernateQuery.setParameter(RMDCommonConstants.ENTERED_BY, strEoaUserName);
            hibernateQuery.setParameter(RMDCommonConstants.GN_ENTERED_BY, userId);
            hibernateQuery.setParameter(RMDCommonConstants.FLAG, generalnotesVO.getVisibilityFlag());
            hibernateQuery.executeUpdate();
        }

        catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_COMM_NOTES);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[]{}, null, null,
                        RMDCommonConstants.MINOR_ERROR);

            } else {
                final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_COMM_NOTES);
                throw new RMDDAOException(errorCode);
            }
        } finally {

            releaseSession(session);

        }
        return uniquerecord;

    }
    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */
    @Override
    public List<GeneralNotesEoaServiceVO> showAllcommnotes(String language) throws RMDDAOException {
        Session session = null;

        Query generalnotesHqry = null;
        List<Object[]> resultList = null;
        List<GeneralNotesEoaServiceVO> objVOlist = null;
        GeneralNotesEoaServiceVO gnVO = null;
        try {
            session = getHibernateSession();
            StringBuilder generalnotesQry = new StringBuilder();
            generalnotesQry
            .append("SELECT OBJID,NOTES_DESCRIPTION,CREATED_BY,TO_CHAR(LAST_UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS'),FLAG FROM GETS_RMD.COMM_NOTES ORDER BY LAST_UPDATED_DATE DESC");
            generalnotesHqry = session.createSQLQuery(generalnotesQry.toString());
            generalnotesHqry.setFetchSize(1000);
            resultList = generalnotesHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                objVOlist = new ArrayList<GeneralNotesEoaServiceVO>(resultList.size());
                for(Object[] objGenNotes : resultList){
                    gnVO = new GeneralNotesEoaServiceVO();
                    gnVO.setCommnotesSeqId(RMDCommonUtility.convertObjectToLong(objGenNotes[0]));
                    gnVO.setNotesdesc(ESAPI.encoder().encodeForXML(RMDCommonUtility.
                    		convertObjectToString(objGenNotes[1])));
                    gnVO.setEnteredby(RMDCommonUtility.convertObjectToString(objGenNotes[2]));
                    gnVO.setLastUpdatedTime(RMDCommonUtility.convertObjectToString(objGenNotes[3]));
                    gnVO.setVisibilityFlag(RMDCommonConstants.Y_LETTER_UPPER.equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objGenNotes[4]))?RMDCommonConstants.STRING_YES:RMDCommonConstants.STRING_NO);
                    objVOlist.add(gnVO);
                }
            }
        }

        catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_COMM_NOTES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return objVOlist;
    }
    /*
     * (non-Javadoc)
     * @com.ge.trans.eoa.services.gpoc.dao.intf.GeneralNotesDAOIntf#
     * (com.ge.trans
     * .eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO)
     */
    @Override
    public String removeCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException {
        Session session = null;
        StringBuilder removeQuery = new StringBuilder();
        Query removeHibernateQuery = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.SUCCESS;
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            removeQuery.append("DELETE FROM GETS_RMD.COMM_NOTES WHERE OBJID = :commnotesId");
            removeHibernateQuery = session.createSQLQuery(removeQuery.toString());
            for (GeneralNotesEoaServiceVO objVO : generalnotesVO) {
                removeHibernateQuery.setParameter(RMDCommonConstants.COMM_NOTES_SEQID, objVO.getCommnotesSeqId());
                removeHibernateQuery.executeUpdate();

            }
            transaction.commit();
            status = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            if(null!=transaction)
                transaction.rollback();
            status = RMDServiceConstants.FAILURE;
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_COMM_NOTES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return status;

    }
    
    /**
     * @param List<GeneralNotesEoaServiceVO>
     * @return String
     * @throws RMDDAOException
     * @Description This method is used to update existing general/comm notes
     *              visibility flag value
     */

    @Override
    public String updateGenOrCommNotes(List<GeneralNotesEoaServiceVO> generalnotesVO) throws RMDDAOException {
        Session session = null;
        StringBuilder updateQry = null;
        Query updateHQry = null;
        Transaction transaction = null;
        String status = RMDServiceConstants.FAILURE;
        String fromScreen =null;
        try {
            fromScreen = generalnotesVO.get(0).getFromScreen();
            if(!RMDCommonUtility.isNullOrEmpty(fromScreen)){
                session = getHibernateSession();
                transaction = session.getTransaction();
                transaction.begin();          
                updateQry = new StringBuilder();
                if(RMDCommonConstants.GENERAL_NOTES.equalsIgnoreCase(fromScreen))
                    updateQry.append("UPDATE GETS_RMD.GENERAL_NOTES SET FLAG=:flag,LAST_UPDATED_BY=:userName,LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :objId");
                else if(RMDCommonConstants.COMM_NOTES.equalsIgnoreCase(fromScreen))
                    updateQry.append("UPDATE GETS_RMD.COMM_NOTES SET FLAG=:flag,LAST_UPDATED_BY=:userName,LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :objId");
                
                updateHQry = session.createSQLQuery(updateQry.toString());
                for (GeneralNotesEoaServiceVO objVO : generalnotesVO) {
                    updateHQry.setParameter(RMDCommonConstants.FLAG, objVO.getVisibilityFlag());
                    updateHQry.setParameter(RMDCommonConstants.USER_NAME, objVO.getLastUpdatedBy());
                    updateHQry.setParameter(RMDCommonConstants.OBJ_ID, objVO.getCommnotesSeqId());
                    updateHQry.executeUpdate();
    
                }
                transaction.commit();
                status = RMDCommonConstants.SUCCESS;
            }
        } catch (Exception e) {
            if(null != transaction){
                transaction.rollback();
            }
            
            status = RMDServiceConstants.FAILURE;
            LOG.error("Unexpected Error occured in updateGenOrCommNotes() of GeneralNotesDAOImpl ", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_UPDATE_GEN_OR_COMM_NOTES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
            transaction=null;
            updateQry=null;
            updateHQry=null;
        }
        return status;

    }
    // end for comm notes

}
