/**
 * ============================================================
 * File : RxExecutionDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.impl
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
package com.ge.trans.eoa.services.tools.rx.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.cm.framework.service.CaseServiceAPI;
import com.ge.trans.eoa.cm.vo.User;
import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.RxExecutionEoaDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.DispatchCaseVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDConcurrencyException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 26, 2010
 * @Date Modified :March 27, 2012
 * @Modified By :Ust
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RxExecutionEoaDAOImpl extends BaseDAO implements RxExecutionEoaDAOIntf {

    static final long serialVersionUID = 862429531L;
    @Autowired
    private CaseServiceAPI caseServiceAPI;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RxExecutionEoaDAOImpl.class);
    Codec ORACLE_CODEC = new OracleCodec();

    /**
     *
     */
   // public static final Comparator OBJIDCOMPARATOR = new Comparator() {

        /**
         * @Author:
         * @param objFirst
         * @param objSecond
         * @return
         * @Description:
         */
/*        @Override
        public int compare(final Object objFirst, final Object objSecond) {
            final ElementVO firstObj = (ElementVO) objFirst;
            final ElementVO secondObj = (ElementVO) objSecond;
            final String firstObject = firstObj.getId();
            final String secondObject = secondObj.getId();
            if (firstObject == null) {
                return -1;
            }
            if (secondObject == null) {
                return +1;
            }
            return firstObject.compareTo(secondObject);
        }
    };*/

    /*
     * This method fetches the case data for task subtab of caseList.
     * @seecom.ge.trans.rmd.services.tools.rx.dao.intf.RxExecutionDAOIntf#
     * getRxExecutionDetails(java.lang.String)
     */
    @Override
    public RxExecTaskDetailsServiceVO getRxExecutionDetails(final String strRxDelvId, final String strLanguage,
            String customerId,boolean isMobileRequest) throws RMDDAOException {
        Session session = null;
        List<Object> rxExecList = null;
        RxExecTaskServiceVO taskServiceVO = null;
        RxExecTaskDetailsServiceVO objRxExecTastVO = null;
        String strCity = null;
        String strState = null;
        List<Object> cityStList = null;
        String strRxLang = "";
        boolean isPrefered = false;
        boolean isRxClosed = false;
        boolean isRxSaved= false;
        try {
            session = getHibernateSession();
            objRxExecTastVO = new RxExecTaskDetailsServiceVO();
            /*
             * added as part of Sprint 7 - Multi Lingual Story -Start If user
             * prefered language is english then we will fetch rx tasks
             * description from GETS_SD_RECOM_TASK table else we fetch it from
             * GETS_SD.GETS_SD_RECOM_TASK_UTF8 table
             */
            if (null != strLanguage) {
                strRxLang = strLanguage;
            }

            isRxClosed = getRxClosedFlag(strRxDelvId, session);
            /*If Rx is not Closed and request is from mobile*/
            if(!isRxClosed && isMobileRequest){
            	isRxSaved=getRxSavedFlag(strRxDelvId,session);
            	isRxClosed=isRxSaved;
				objRxExecTastVO
						.setIsRxSaved(isRxSaved ? RMDCommonConstants.Y_LETTER_UPPER
								: RMDCommonConstants.N_LETTER_UPPER);
            }
            Query dataQuery = getMultiLingualRxTasksQuery(strLanguage, isPrefered, strRxDelvId, session, strRxLang,
                    isRxClosed,isRxSaved);
            rxExecList = dataQuery.list();
            int intArlDataSize = 0;
            intArlDataSize = rxExecList.size();

            if (intArlDataSize == 0) {
                isPrefered = false;
                Query dataQueryEnglishTasks = getMultiLingualRxTasksQuery(RMDCommonConstants.ENGLISH_LANGUAGE,
                        isPrefered, strRxDelvId, session, strRxLang, isRxClosed, isRxSaved);
                rxExecList = dataQueryEnglishTasks.list();
                intArlDataSize = rxExecList.size();
            }
            
            
            List<RxExecTaskServiceVO> arlExecTasks = new ArrayList<RxExecTaskServiceVO>();
            DateFormat df = new SimpleDateFormat(RMDCommonConstants.DateConstants.yyyyMMdd);

            DateFormat dateFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
            if (intArlDataSize > 0) {
                for (int i = 0; i < rxExecList.size(); i++) {
                    Object[] objectRxList = (Object[]) rxExecList.get(i);
                    taskServiceVO = new RxExecTaskServiceVO();
                    taskServiceVO.setStrTaskId(RMDCommonUtility.convertObjectToString(objectRxList[0]));// gsrt.task_id
                    taskServiceVO.setStrTaskDeails(AppSecUtil
                            .htmlEscapingWithoutEncoding(RMDCommonUtility.convertObjectToString(objectRxList[17])));//gsrt.task_desc
                    taskServiceVO.setStrLSL(RMDCommonUtility.convertObjectToString(objectRxList[2]));//lower_spec
                    taskServiceVO.setStrUSL(RMDCommonUtility.convertObjectToString(objectRxList[3]));//upper_spec
                    taskServiceVO.setStrTarget(RMDCommonUtility.convertObjectToString(objectRxList[4]));//target
                    taskServiceVO.setStrRecomTaskId(RMDCommonUtility.convertObjectToString(objectRxList[10]));//gsrt.objid task_objid
                    taskServiceVO.setStrSetFlag(Boolean.FALSE.toString());
                    taskServiceVO.setDocTitle(RMDCommonUtility.convertObjectToString(objectRxList[23]));//task_doc.doc_title
                    taskServiceVO.setDocPath(RMDCommonUtility.convertObjectToString(objectRxList[24]));//task_doc.doc_path
                    if (isRxClosed) {

                        if (objectRxList[30] != null && RMDCommonConstants.Y_LETTER
                                .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objectRxList[30]))) {
                            taskServiceVO.setStrSetFlag(Boolean.TRUE.toString());
                        }

                        taskServiceVO.setStrTaskNotes(
                                AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(objectRxList[31])));//CLOSUREDET.TASK_COMMENTS

                        

                        if (null != objectRxList[31] && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objectRxList[33]))) {

                            taskServiceVO.setCreationDate(dateFormat.parse(objectRxList[33].toString()));//TO_CHAR(CLOSUREDET.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS')

                        }
                        if(!RMDCommonUtility.isNullOrEmpty(taskServiceVO.getStrTaskNotes()) || RMDCommonConstants.Y_LETTER
                                .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objectRxList[30]))){//COMPLETED_FLAG
                        	
	                        if (null != objectRxList[32] && !RMDCommonConstants.EMPTY_STRING
	                                .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objectRxList[34]))) {
	                            taskServiceVO.setLastUpdateDate(dateFormat.parse(objectRxList[34].toString()));// TO_CHAR(CLOSUREDET.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS')
	                         }
	                        taskServiceVO.setStrLastUpdatedBy(RMDCommonUtility.convertObjectToString(objectRxList[9]));//fdbk.last_updated_by
	                        taskServiceVO.setStrTaskExecutedUserId(RMDCommonUtility.convertObjectToString(objectRxList[32]));//OMDUSERS.USER_ID
                        }
                        
                        objRxExecTastVO.setStrRepairAction(
                                AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(objectRxList[35])));//FDBK.RX_FDBK
                        strCity = RMDCommonUtility.convertObjectToString(objectRxList[36]);//FDBK.RX_CLOSE_CITY
                        strState = RMDCommonUtility.convertObjectToString(objectRxList[37]);//FDBK.RX_CLOSE_ST
                    }

                    arlExecTasks.add(taskServiceVO);

                    if (i < rxExecList.size()) {
                        objRxExecTastVO.setStrCaseId(RMDCommonUtility.convertObjectToString(objectRxList[5]));//tc.id_number
                        objRxExecTastVO.setStrRxCaseId(RMDCommonUtility.convertObjectToString(objectRxList[6]));//fdbk.rx_case_id
                        objRxExecTastVO.setStrRxExecutionId(RMDCommonUtility.convertObjectToString(objectRxList[7]));//delv.objid
                        if (null != objectRxList[8] && objectRxList[8] != RMDCommonConstants.EMPTY_STRING) {
                            Date issueDate = df.parse(objectRxList[8].toString());
                            objRxExecTastVO.setDtRxIssueDate(issueDate);//delv.creation_date rxdevdate
                        }
                        objRxExecTastVO.setCustomerName(RMDCommonUtility.convertObjectToString(objectRxList[11]));
                        objRxExecTastVO.setStrAssetNumber(RMDCommonUtility.convertObjectToString(objectRxList[13]));//tsp.serial_no
                        objRxExecTastVO.setStrFileName(RMDCommonUtility.convertObjectToString(objectRxList[14]));//delv.file_name,
                        objRxExecTastVO.setStrFilePath(RMDCommonUtility.convertObjectToString(objectRxList[15]));//delv.file_path
                       

                        if (null != objectRxList[16] && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(RMDCommonUtility.convertObjectToString(objectRxList[16]))) {
                            objRxExecTastVO.setDtRxClosedDate(dateFormat.parse(objectRxList[16].toString()));//TO_CHAR(fdbk.rx_close_date,'MM/DD/YYYY HH24:MI:SS'),
                        }

                      
                        if (!RMDCommonUtility.checkNull(objectRxList[18])) {
                            objRxExecTastVO.setStrRxTitle(RMDCommonUtility.convertObjectToString(objectRxList[18])); //gsr.title
                        } else {
                            objRxExecTastVO.setStrRxTitle(RMDCommonUtility.convertObjectToString(objectRxList[1]));//gsr.title
                        }
                        if (!RMDCommonUtility.checkNull(objectRxList[19])) {
                            objRxExecTastVO.setLocoImpact(RMDCommonUtility.convertObjectToString(objectRxList[19]));//gsr.loco_impact
                        } else {
                            objRxExecTastVO.setLocoImpact(RMDCommonUtility.convertObjectToString(objectRxList[20]));//gsr.loco_impact
                        }

                        objRxExecTastVO.setStrRecomId(RMDCommonUtility.convertObjectToString(objectRxList[21]));//gsr.objid recom_objid
                        objRxExecTastVO.setVersion(RMDCommonUtility.convertObjectToString(objectRxList[22]));//gsr.version version
                        if (isRxClosed) {
                            objRxExecTastVO.setNotes(
                                    AppSecUtil.htmlEscaping(RMDCommonUtility.convertObjectToString(objectRxList[29])));//CLOSURE_COMMENTS
                        }
                        if (!RMDCommonUtility.checkNull(objectRxList[25])) 
                            objRxExecTastVO.setRecomNotes(RMDCommonUtility.convertObjectToString(objectRxList[25]));
                        if (!RMDCommonUtility.checkNull(objectRxList[26])) 
                            objRxExecTastVO.setRxDescription(RMDCommonUtility.convertObjectToString(objectRxList[26]));
                        if (!RMDCommonUtility.checkNull(objectRxList[27])) 
                            objRxExecTastVO.setPrerequisites(RMDCommonUtility.convertObjectToString(objectRxList[27]));
                        if (!RMDCommonUtility.checkNull(objectRxList[28])) 
                            objRxExecTastVO.setRxDeliveredBy(RMDCommonUtility.convertObjectToString(objectRxList[28]));

                        // Added for disabling submit button in RX_EX Screen
                        // implementation:end
                        objRxExecTastVO.setArlExecTasks(arlExecTasks);
                    }

                }
                // Added for disabling submit button in RX_EX Screen
                // implementation:start
                if (null != strCity && !strCity.isEmpty() && null != strState && !strState.isEmpty()) {
                    StringBuilder cityStQuery = new StringBuilder();
                    cityStQuery
                            .append("SELECT TBS.OBJID FROM TABLE_SITE TBS,TABLE_ADDRESS TBA, TABLE_BUS_ORG TBO WHERE ");
                    cityStQuery.append(
                            "TBS.CUST_PRIMADDR2ADDRESS = TBA.OBJID AND TBS.PRIMARY2BUS_ORG = TBO.OBJID AND TBA.CITY=:CITY ");
                    cityStQuery.append("AND TBA.STATE=:STATE ");

                    if (null != customerId && !customerId.isEmpty()) {
                        cityStQuery.append(" AND TBO.ORG_ID =:customerID");
                    }
                    Query cityStQry = session.createSQLQuery(cityStQuery.toString());
                    cityStQry.setString(RMDCommonConstants.CITY_NAME, strCity);
                    cityStQry.setString(RMDCommonConstants.STATE_NAME, strState);
                    if (null != customerId && !customerId.isEmpty()) {
                        cityStQry.setString(RMDCommonConstants.CUSTOMERID, customerId);
                    }
                    cityStList = cityStQry.list();
                    if (null != cityStList && !cityStList.isEmpty()) {
                        objRxExecTastVO
                                .setLocationId(RMDCommonUtility.cnvrtBigDecimalObjectToString(cityStList.get(0)));
                    }
                }
               List<RxDeliveryAttachmentVO> rxAttachemnts = getRxDeliveryAttachments(strRxDelvId,session);
               objRxExecTastVO.setRxDeliveryAttachments(rxAttachemnts);
		        // Added for disabling submit button in RX_EX Screen
                // implementation:end
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_EXECDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RxExecutionDAOImpl getRxExecutionDetails", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_EXECDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return objRxExecTastVO;
    }

    /*
     * This method inserts data in to the below tables
     * GETS_RMD.GETS_RMD_EXT_RX_CLOSURE
     * ,GETS_RMD.GETS_RMD_EXT_RX_CLOSURE_DET,GETS_RMD.GETS_RMD_EXT_RX_CLOSURE
     * (com.ge.trans.rmd.services.tools.rx.service.valueobjects
     * .RxExecTaskDetailsServiceVO)
     */
    @Override
    public int saveRxExecutionDetails(RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO) throws RMDDAOException {

        LOG.debug("RMDService :RxExecutionDAOImpl :saveRxExecutionDetails() ::::START");
        int result = RMDCommonConstants.INSERTION_FAILURE;
        Session objSession = null;
        Transaction objTransaction=null;
        StringBuilder sbSaveRx = new StringBuilder();
        String extFdbKObjId=null;
        String omdObjid = null;
        StringBuilder taskCmntsUpdateQry=new StringBuilder();
        StringBuilder sbTaskRx = new StringBuilder();
        Query saveTaskQry=null;
        
        
        try {
            objSession = getHibernateSession(rxExecTaskDetailsServiceVO.getStrUserName());
            Date saveDate = new Date();
            Timestamp systimestamp = new Timestamp(saveDate.getTime());
            int closureFlag = RMDCommonConstants.INSERTION_FAILURE;
            int taskFlag = RMDCommonConstants.INSERTION_FAILURE;
            if (objSession != null) {
            	objTransaction=objSession.getTransaction();
            	objTransaction.begin();
            	
                // Changes to fetch RxTitle START
                Query qryRxTitle = objSession.createSQLQuery("SELECT GSR.TITLE , DELV.OBJID "
                        + " FROM GETS_SD_RECOM GSR,  GETS_SD_CASE_RECOM CR, GETS_SD_CUST_FDBK FDBK, GETS_SD_RECOM_DELV DELV"
                        + " WHERE FDBK.OBJID = DELV.RECOM_DELV2CUST_FDBK"
                        + " AND DELV.OBJID = CR.CASE_RECOM2RECOM_DELV AND"
                        + " FDBK.RX_CASE_ID = :rx_Case_ID AND CR.CASE_RECOM2RECOM   = GSR.OBJID");

                String rxTitle = null; 
                String delvObjid = null;

                qryRxTitle.setString("rx_Case_ID", ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getStrRxCaseId()));
                List<Object[]> rxTitleList = qryRxTitle.list();
                int intArlDataSize = rxTitleList.size();
                if (intArlDataSize > 0) {
                    rxTitle = (rxTitleList.get(0)[0] != null) ? rxTitleList.get(0)[0].toString() : null;
                    delvObjid = (rxTitleList.get(0)[1] != null) ? rxTitleList.get(0)[1].toString() : null;

                }

                // Changes to fetch FDBK Objid
                Query qryCustFdbk = objSession
                        .createSQLQuery("SELECT OBJID FROM GETS_SD_CUST_FDBK " + " WHERE RX_CASE_ID = :rx_Case_ID ");

                qryCustFdbk.setString("rx_Case_ID", ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getStrRxCaseId()));

                String rxFdbkObjid = null;
                List<Object> rxFdbkObjidList = qryCustFdbk.list();
                if (!rxFdbkObjidList.isEmpty()) {
                    rxFdbkObjid = (rxFdbkObjidList.get(0) != null) ? rxFdbkObjidList.get(0).toString() : null;
                }
                
            	/*// Changes to fetch the OmdUser Objid
                Query qryOmdObjid = objSession.createSQLQuery(
                        "SELECT GET_USR_USERS_SEQ_ID FROM GET_USR_USERS " + " WHERE GET_USR_USERS_SEQ_ID = :userId ");

                qryOmdObjid.setString(RMDCommonConstants.USERID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getUserSeqId()));

               
                List<Object> omdObjidList = qryOmdObjid.list();
                if (!omdObjidList.isEmpty()) {
                	omdObjid = (omdObjidList.get(0) != null) ? omdObjidList.get(0).toString() : null;
                }*/
                
                omdObjid = rxExecTaskDetailsServiceVO.getUserSeqId();
                /*Query to Check if the Rx is already Saved or not*/
               
                Query qryTaskObj = objSession
                        .createSQLQuery("SELECT OBJID FROM GETS_RMD_EXT_RX_CLOSURE  WHERE LINK_CUSTFDBK = :fdbkObjId ");
                
                qryTaskObj.setParameter("fdbkObjId", rxFdbkObjid);
                List<Object> rxTaskObjIDList = qryTaskObj.list();
                if (!rxTaskObjIDList.isEmpty()) {
                	extFdbKObjId = (rxTaskObjIDList.get(0) != null) ? rxTaskObjIDList.get(0).toString() : null;
                }
                
                /*If rx is not Saved then we will insert record newly*/
               if(null==extFdbKObjId){
                	
                // Changes to fetch RxTitle END
 
                Query qryObjId = objSession.createSQLQuery("SELECT GETS_RMD_EXT_RX_CLOSURE_SEQ.NEXTVAL FROM DUAL");
                LOG.debug("RMDService :RxExecutionDAOImpl :qryObjId " + qryObjId);
                String seqObjid = qryObjId.list().get(0).toString();
                /*
                 * Insert data into the GETS_RMD.GETS_RMD_EXT_RX_CLOSURE table
                 * with Rx Title and closure comments
                 */
                sbSaveRx.append("INSERT INTO GETS_RMD_EXT_RX_CLOSURE(OBJID, RX_NAME,CLOSURE_COMMENTS,");
                sbSaveRx.append(
                        "LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,LINK_CUSTFDBK,LINK_RECOM)VALUES(?,?,?,?,?,?,?,?,?) ");
                Query saveClosureQry = objSession.createSQLQuery(sbSaveRx.toString());
                saveClosureQry.setParameter(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, seqObjid));
                // Changes to save RxTitle START
                saveClosureQry.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxTitle));
                // Changes to save RxTitle END
                saveClosureQry.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, EsapiUtil.escapeSpecialChars(rxExecTaskDetailsServiceVO.getStrRepairAction())));
                saveClosureQry.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
                saveClosureQry.setParameter(4, systimestamp);
                saveClosureQry.setParameter(5, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
                saveClosureQry.setParameter(6, systimestamp);
                saveClosureQry.setParameter(7, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxFdbkObjid));
                saveClosureQry.setParameter(8, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getStrRecomId()));
                closureFlag = saveClosureQry.executeUpdate();
                /*
                 * For each task submitted from the UI, Insert data into the
                 * GETS_RMD.GETS_RMD_EXT_RX_CLOSURE_DET table with a link into
                 * the GETS_RMD.GETS_RMD_EXT_RX_CLOSURE table through the column
                 * HEADER2DETAIL
                 */

                

               
                sbTaskRx.append("INSERT INTO GETS_RMD_EXT_RX_CLOSURE_DET(OBJID,HEADER2DETAIL,RX_STEP,LAST_UPDATED_BY,");
                sbTaskRx.append(
                        "LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,COMPLETED_FLAG,TASK_COMMENTS, LINK_OMDUSER, LINK_TASK, CLOSURE_DET2DELV_TASK )VALUES(GETS_RMD_EXT_RX_CLOSUR_DET_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,(Select objid from GETS_SD_RECOM_DELV_TASK where TASK_ID = ? and DELV_TASK2RECOM_DELV = ?)) ");
                saveTaskQry = objSession.createSQLQuery(sbTaskRx.toString());

                for (RxExecTaskServiceVO objRxExecTask : rxExecTaskDetailsServiceVO.getArlExecTasks()) {
                    saveTaskQry.setParameter(0, Integer.parseInt(seqObjid));
                    saveTaskQry.setParameter(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, AppSecUtil.decodeString(objRxExecTask.getStrTaskDeails())));
                    saveTaskQry.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
                    if(null!=objRxExecTask.getLastUpdateDate()){
                    	 saveTaskQry.setParameter(3,  new Timestamp(objRxExecTask.getLastUpdateDate().getTime()));
           		 	}else{
           		 		saveTaskQry.setParameter(3,  systimestamp);
           		 	}
                    saveTaskQry.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
                    saveTaskQry.setParameter(5, systimestamp);
                    if (objRxExecTask.getStrSetFlag().equalsIgnoreCase(RMDCommonConstants.STR_TRUE)) {
                        saveTaskQry.setParameter(6, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, RMDCommonConstants.LETTER_Y));
                    } else {
                        saveTaskQry.setParameter(6, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, RMDCommonConstants.LETTER_N));
                    }
                    saveTaskQry.setParameter(7, EsapiUtil.escapeSpecialChars(objRxExecTask.getStrTaskNotes()));
                    if (null != omdObjid) {
                        saveTaskQry.setParameter(8, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, omdObjid));
                    } else {
                        saveTaskQry.setParameter(8, RMDCommonConstants.EMPTY_STRING);
                    }
                    saveTaskQry.setParameter(9, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objRxExecTask.getStrRecomTaskId()));
                    
                    saveTaskQry.setParameter(10, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objRxExecTask.getStrTaskId()));
                    saveTaskQry.setParameter(11, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, delvObjid));

                    taskFlag = saveTaskQry.executeUpdate();
                }
                
                //US244816 : TA365920 Changes
                if(rxExecTaskDetailsServiceVO.getIsMobile() != null && rxExecTaskDetailsServiceVO.getIsMobile().equalsIgnoreCase(RMDCommonConstants.STR_YES)
                			&& updateMobileCDCStatus(objSession, rxExecTaskDetailsServiceVO, systimestamp) == 0){
            		saveMobileCDCStatus(objSession, rxExecTaskDetailsServiceVO, systimestamp);
                }
                
                if (closureFlag > 0 && taskFlag > 0) {
                	result = RMDCommonConstants.INSERTION_SUCCESS;
                }
            }
             /*Else if it is already present then we will update the existing Rx task Details */
            else{
            		LOG.info("Rx is Already Saved update of existing records begin");
            		
            		/*Updating the GETS_RMD_EXT_RX_CLOSURE table with closure Comments */
					if (!RMDCommonUtility.isNullOrEmpty(rxExecTaskDetailsServiceVO.getStrRepairAction())) {
						String updateClosureComment = " UPDATE GETS_RMD_EXT_RX_CLOSURE SET CLOSURE_COMMENTS=?,LAST_UPDATED_DATE=?,LAST_UPDATED_BY=? WHERE OBJID=? ";
						Query updateClosureQry = objSession.createSQLQuery(updateClosureComment);
						updateClosureQry.setParameter(0, EsapiUtil.escapeSpecialChars(rxExecTaskDetailsServiceVO.getStrRepairAction()));
						updateClosureQry.setParameter(1, systimestamp);
						updateClosureQry.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
						updateClosureQry.setParameter(3, extFdbKObjId);
						updateClosureQry.executeUpdate();
						LOG.info(" GETS_RMD_EXT_RX_CLOSURE Update Success");
					}
					
				/*Updating the	GETS_RMD_EXT_RX_CLOSURE_DET table with taks Comments*/
					
            	taskCmntsUpdateQry.append(" UPDATE GETS_RMD_EXT_RX_CLOSURE_DET SET LAST_UPDATED_BY=? ,LAST_UPDATED_DATE=?,COMPLETED_FLAG=?, ");
            	taskCmntsUpdateQry.append(" TASK_COMMENTS=?,LINK_OMDUSER=? WHERE LINK_TASK=? AND HEADER2DETAIL=? AND CLOSURE_DET2DELV_TASK = (Select objid from GETS_SD_RECOM_DELV_TASK where TASK_ID = ? and DELV_TASK2RECOM_DELV = ?)  ");
            	Query updateTaskQry = objSession.createSQLQuery(taskCmntsUpdateQry.toString());
            	
            	for (RxExecTaskServiceVO objRxExecTask : rxExecTaskDetailsServiceVO.getArlExecTasks()) {
            		 
					if (!RMDCommonUtility.isNullOrEmpty(objRxExecTask.getIsRxModified())
							&& RMDCommonConstants.LETTER_Y.equalsIgnoreCase(objRxExecTask.getIsRxModified())){
            		 updateTaskQry.setParameter(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
            		 if(null!=objRxExecTask.getLastUpdateDate()){
            			 updateTaskQry.setParameter(1,  new Timestamp(objRxExecTask.getLastUpdateDate().getTime()));
            		 }else{
            			 updateTaskQry.setParameter(1,  systimestamp);
            		 }
            		 if (objRxExecTask.getStrSetFlag().equalsIgnoreCase(RMDCommonConstants.STR_TRUE)) {
            			 updateTaskQry.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, RMDCommonConstants.LETTER_Y));
                     } else {
                    	 updateTaskQry.setParameter(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, RMDCommonConstants.LETTER_N));
                     }
                     updateTaskQry.setParameter(3, EsapiUtil.escapeSpecialChars(objRxExecTask.getStrTaskNotes()));
                     if (null != omdObjid) {
                    	 updateTaskQry.setParameter(4, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, omdObjid));
                     } else {
                    	 updateTaskQry.setParameter(4, RMDCommonConstants.EMPTY_STRING);
                     }
                     updateTaskQry.setParameter(5, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objRxExecTask.getStrRecomTaskId()));
                     updateTaskQry.setParameter(6, extFdbKObjId);
                     updateTaskQry.setParameter(7, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objRxExecTask.getStrTaskId()));
                     updateTaskQry.setParameter(8, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, delvObjid));
                     updateTaskQry.executeUpdate();
					}
        		 }
            	 //US244816 : TA365920 Changes
            	 if(rxExecTaskDetailsServiceVO.getIsMobile() != null && rxExecTaskDetailsServiceVO.getIsMobile().equalsIgnoreCase(RMDCommonConstants.STR_YES)
            			 	&& updateMobileCDCStatus(objSession, rxExecTaskDetailsServiceVO, systimestamp) == 0){
            		 saveMobileCDCStatus(objSession, rxExecTaskDetailsServiceVO, systimestamp);
            		 LOG.info("GETS_RMD_CDC_STATUS Update Success");
            	 }
            	 LOG.info("End of Updating Rx Details");
            	 result = RMDCommonConstants.INSERTION_SUCCESS;
              }
      		 objTransaction.commit();
            }   
        } catch (RMDDAOConnectionException ex) {
        	String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, rxExecTaskDetailsServiceVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	if(null!=objTransaction){
        		objTransaction.rollback();
        	}
            LOG.error("Unexpected Error occured in saving RxExecutionDetails", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, rxExecTaskDetailsServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
         }
        LOG.debug("RMDService :RxExecutionDAOImpl :saveRxExecutionDetails() ::::END");
        return result;
    }


    @Override
    public String getEOAUserId(String strUserName, String strLanguage) {
        Session hibernateSession = null;
        String userName = null;
        try {
            
        	if(strUserName != null){
	            hibernateSession = getHibernateSession();
	            if (hibernateSession != null) {
	                Query hibernateQuery = hibernateSession.createSQLQuery("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE A.WEB_LOGIN = :userId");
	                hibernateQuery.setParameter(RMDCommonConstants.USERID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strUserName));
	                List<String> userNameLst = hibernateQuery.list();
	                if (null != userNameLst && !userNameLst.isEmpty()) {
	                    userName = userNameLst.get(0);
	                }
	            }
        	}
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RxExecutionDAOImpl getEOAUserId", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return userName;
    }

    /*
     * added as part of Sprint 7 - Multi Lingual Story -Start If user prefered
     * language is english then we will fetch rx tasks description from
     * GETS_SD_RECOM_TASK table else we fetch it from
     * GETS_SD.GETS_SD_RECOM_TASK_UTF8 table
     */
    private Query getMultiLingualRxTasksQuery(String strLanguage, boolean isPrefered, String strRxDelvId,
            Session session, String strRxLang, boolean isRxClosed,boolean isRxSaved) {
        String strDataColumns;
        String taskDesc;
        String taskTables;

        String strRxClosedColumns;
        String strRxClosedTables;
        String rxClosedWhereCondition;
        String docLanguage = null;
        Query dataQuery = null;
        Query languageQuery=null;
        strRxClosedColumns = ", CLOSURE.CLOSURE_COMMENTS, CLOSUREDET.COMPLETED_FLAG, CLOSUREDET.TASK_COMMENTS, OMDUSERS.USER_ID, TO_CHAR(CLOSUREDET.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS') ";
        strRxClosedColumns+=", TO_CHAR(CLOSUREDET.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS'),FDBK.RX_FDBK,FDBK.RX_CLOSE_CITY, FDBK.RX_CLOSE_ST ";
        strRxClosedTables = ", GETS_RMD_EXT_RX_CLOSURE CLOSURE, GETS_RMD_EXT_RX_CLOSURE_DET CLOSUREDET, GET_USR_USERS OMDUSERS ";
        rxClosedWhereCondition = " AND CLOSURE.LINK_RECOM (+) = GSR.OBJID AND CLOSUREDET.CLOSURE_DET2DELV_TASK (+) =  DELVTASK.OBJID "
                + " AND CLOSUREDET.HEADER2DETAIL (+) =  CLOSURE.OBJID AND CLOSURE.LINK_CUSTFDBK (+) =  FDBK.OBJID "
                + " AND OMDUSERS.GET_USR_USERS_SEQ_ID (+) =  CLOSUREDET.LINK_OMDUSER ";
        
         
        if (null != strLanguage && strLanguage.equalsIgnoreCase(RMDCommonConstants.ENGLISH_LANGUAGE)) {
            // Added to get Multilingual Rx Title and LocoImpact when task
            // doesn't have the translation
            if (null != strRxLang && strRxLang.equalsIgnoreCase(RMDCommonConstants.ENGLISH_LANGUAGE)) {
                taskDesc = "DELVTASK.TASK_DESC,GSR.TITLE,GSR.LOCO_IMPACT ";
            } else {
                taskDesc = "DELVTASK.TASK_DESC,(SELECT distinct RECOMUT8.TRANS_RECOM_TITLE_TXT"
                        + " FROM GETS_SD.GETS_SD_RECOM_UT8 RECOMUT8" + " WHERE GSR.OBJID = RECOMUT8.LINK_RECOM"
                        + " AND RECOMUT8.LANGUAGE_CD =(select look_value  from gets_rmd_lookup where list_name =:language)"
                        + " OR  LANGUAGE_CD=:languageCode) TRANS_RECOM_TITLE_TXT,"
                        + " (SELECT distinct MSTRLOCOIMPACT.TRANS_LOCO_IMPACT_TXT"
                        + " FROM GETS_SD.GETS_SD_MSTR_LOCO_IMPACT MSTRLOCOIMPACT"
                        + " WHERE GSR.LOCO_IMPACT          =MSTRLOCOIMPACT.LOCO_IMPACT_TXT"
                        + " AND UPPER(MSTRLOCOIMPACT.ACTIVE_FLG)='Y' "
                        + " AND MSTRLOCOIMPACT.LANGUAGE_CD =(select look_value  from gets_rmd_lookup where list_name =:language)"
                        + "  OR  LANGUAGE_CD=:languageCode) TRANS_LOCO_IMPACT  ";
            }

        } else {
            taskDesc = " utf.trans_task_desc task_desc,UT8.trans_recom_title_txt title_1,MSTRLOCOIMPACT.trans_loco_impact_txt loco_impact";
            isPrefered = true;
        }
        if(isPrefered){
        	StringBuilder strBuilder=new StringBuilder("select objid from GETS_SD_LANG_TYPE where lang_type in (select look_value from GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME =:language)");
        	languageQuery = session.createSQLQuery(strBuilder.toString());
        	languageQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_" + strLanguage.toUpperCase());
            Object objDocLanguage = languageQuery.uniqueResult();
            if (null!=objDocLanguage) {
            	docLanguage=RMDCommonUtility.convertObjectToString(objDocLanguage);
            }
        }
        strDataColumns = " SELECT  DISTINCT DELVTASK.TASK_ID, GSR.TITLE," + " DELVTASK.LOWER_SPEC LOWER_SPEC,"
                + " DELVTASK.UPPER_SPEC UPPER_SPEC, DELVTASK.TARGET TARGET,"
                + " TC.ID_NUMBER, FDBK.RX_CASE_ID, DELV.OBJID, DELV.CREATION_DATE RXDEVDATE,"
                + " FDBK.LAST_UPDATED_BY, DELVTASK.OBJID TASK_OBJID, TBO.NAME, TSP.X_VEH_HDR, TSP.SERIAL_NO,"
                + " DELV.FILE_NAME, DELV.FILE_PATH,TO_CHAR(fdbk.RX_CLOSE_DATE, 'MM/DD/YYYY HH24:MI:SS'), "
                + taskDesc + " ,GSR.LOCO_IMPACT,GSR.OBJID RECOM_OBJID,GSR.VERSION VERSION,TASK_DOC.DOC_TITLE,TASK_DOC.DOC_PATH "
                + " ,DELV.recom_notes,GSR.description,GSR.prerequisites,concat(uu.last_name,substr(uu.first_name,0,1)) AS display_name";
        /*
         * +
         * " FROM GETS_SD_RECOM GSR, GETS_SD_RECOM_TASK GSRT, GETS_SD_CASE_RECOM CR,"
         * +
         * " TABLE_CASE TC, GETS_SD_CUST_FDBK FDBK, GETS_SD_RECOM_DELV DELV, TABLE_SITE_PART TSP,"
         * + " GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO"
         */;

        taskTables = " FROM GETS_SD_RECOM GSR, GETS_SD_CASE_RECOM CR,"
                + " TABLE_CASE TC, GETS_SD_CUST_FDBK FDBK, GETS_SD_RECOM_DELV DELV, GETS_SD_RECOM_DELV_TASK DELVTASK, TABLE_SITE_PART TSP,"
                + " GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO, GETS_SD_RECOM_DELV_TASK_DOC TASK_DOC, TABLE_USER TU, GET_USR_USERS UU";

        StringBuilder rxTask = new StringBuilder();
        rxTask.append(strDataColumns);

        if (isRxClosed) {
            rxTask.append(strRxClosedColumns);
        }

        rxTask.append(taskTables);

        if (isPrefered) {
            rxTask.append(
                    " ,GETS_SD.GETS_SD_RECOM_DELV_TASK_UTF8 UTF ");
            rxTask.append(" ,GETS_SD.GETS_SD_RECOM_UT8 UT8, GETS_SD.GETS_SD_MSTR_LOCO_IMPACT MSTRLOCOIMPACT");
        }
        
        

        if (isRxClosed) {
            rxTask.append(strRxClosedTables);
        }

        String whereCondition = " WHERE  FDBK.CUST_FDBK2CASE = TC.OBJID AND DELV.RECOM_DELV2CUST_FDBK = FDBK.OBJID AND DELV.RECOM_DELV2CASE = TC.OBJID "
        		+ " AND DELVTASK.DELV_TASK2RECOM_DELV = DELV.OBJID "
                + " AND CR.CASE_RECOM2CASE = TC.OBJID  AND CR.CASE_RECOM2RECOM_DELV = DELV.OBJID AND FDBK.RX_CASE_ID = :rx_Case_ID "
                + " AND GSR.OBJID = CR.CASE_RECOM2RECOM AND TSP.OBJID = TC.CASE_PROD2SITE_PART "
                + " AND VEHHDR.OBJID = VEH.VEHICLE2VEH_HDR AND VEH.VEHICLE2SITE_PART = TC.CASE_PROD2SITE_PART"
                + " AND TBO.OBJID = VEHHDR.VEH_HDR2BUSORG  " + " AND GSR.OBJID = DELV.RECOM_DELV2RECOM "
                + " AND TASK_DOC.DELV_DOC2DELV_TASK(+) = DELVTASK.OBJID "
                + " AND TU.LOGIN_NAME = DELV.CREATED_BY AND UU.USER_ID (+) = TU.WEB_LOGIN ";
        
        if (isPrefered) {
        	 if (null != strLanguage) {
        		 whereCondition+=" AND (TASK_DOC.LINK_LANG_TYPE=:docLanguage OR TASK_DOC.LINK_LANG_TYPE is null) ";
        	 }else{
        		 whereCondition+=" AND TASK_DOC.LINK_LANG_TYPE is null ";
        	 }
        }else{
        	whereCondition+=" AND TASK_DOC.LINK_LANG_TYPE is null ";
        }
        
        rxTask.append(whereCondition);
        if (isPrefered) {
            rxTask.append(" and UTF.DELV_UTF82DELV_TASK    = DELVTASK.OBJID");
            rxTask.append(" and utf.link_lang_type   in (select objid from GETS_SD_LANG_TYPE where lang_type in (select look_value from GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME =:language))");
            rxTask.append(" and GSR.objid              = UT8.link_recom");
            rxTask.append(" and UT8.language_cd     in (select look_value from GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME = :language )");
            rxTask.append(" and GSR.LOCO_IMPACT                  = MSTRLOCOIMPACT.LOCO_IMPACT_TXT");
            rxTask.append(" AND UPPER(MSTRLOCOIMPACT.ACTIVE_FLG) ='Y'");
            rxTask.append(" AND MSTRLOCOIMPACT.LANGUAGE_CD    in (select look_value from GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_NAME = :language )");
						
//            rxTask.append(" AND ((LANG.LANG_TYPE = LOOKUP.LOOK_VALUE");
//            rxTask.append(" AND LOOKUP.LIST_NAME =:language) OR  (LANG.LANG_TYPE =:languageCode))");
//            rxTask.append(" AND RECOMCOMMENT.OBJID=GSRT.RECOM_TASK2RECOM ");
//            rxTask.append(" AND RECOMCOMMENT.TRANSLATION_STATUS='Approved' ");
//            rxTask.append(" AND (RECOMCOMMENT.LANG_TYPE=LOOKUP.LOOK_VALUE OR RECOMCOMMENT.LANG_TYPE=:languageCode) ");
        }

        if (isRxClosed) {
            rxTask.append(rxClosedWhereCondition);
        }
        
        /*Added Condition to check rx Close date is  Null For Save Rx Requirement by Vamshi*/
        if(isRxSaved){
        	rxTask.append(" AND FDBK.RX_CLOSE_DATE IS NULL ");
        }

        rxTask.append(" ORDER BY TO_NUMBER(DELVTASK.TASK_ID)");

        dataQuery = session.createSQLQuery(rxTask.toString());
        dataQuery.setString(RMDCommonConstants.RX_CASE_ID, strRxDelvId);
        if (isPrefered) {
            if (null != strLanguage) {
                dataQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_" + strLanguage.toUpperCase());
                dataQuery.setString(RMDCommonConstants.DOC_LANGUAGE, docLanguage);
                //dataQuery.setString(RMDCommonConstants.LANGUAGECODE, strLanguage);
            } else {
                dataQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_EN");
                //dataQuery.setString(RMDCommonConstants.LANGUAGECODE, "en");
            }

        } else {
            if (strRxLang != null && !strRxLang.equalsIgnoreCase(RMDCommonConstants.ENGLISH_LANGUAGE)) {
                dataQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_" + strRxLang.toUpperCase());
                dataQuery.setString(RMDCommonConstants.LANGUAGECODE, strRxLang);
            }

        }

        return dataQuery;
    }



    /*
     * @Description: Function to check whether the rx is closed for the
     * corresponding rx case id
     */
    private boolean getRxClosedFlag(String strRxDelvId, Session session) {
        StringBuilder buferQuery = new StringBuilder();
        Object rxCloseDate = null;
        Query dataQuery = null;
        try {
            buferQuery.append(" SELECT RX_CLOSE_DATE FROM GETS_SD_CUST_FDBK WHERE  RX_CASE_ID = :rx_Case_ID ");
            dataQuery = session.createSQLQuery(buferQuery.toString());
            dataQuery.setString(RMDCommonConstants.RX_CASE_ID, strRxDelvId);
            rxCloseDate = dataQuery.uniqueResult();
            if (rxCloseDate != null) {
                return true;
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RxExecutionDAOImpl getRxClosedFlag", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.caseapi.services.dao.intf.CaseDAOIntf#dispatchCase(com
     * .ge.trans.rmd.caseapi.services.valueobjects.DispatchCaseVO)
     */
    @Override
    public String dispatchCase(final DispatchCaseVO dispatchCaseVO) {
        Session hibernateSession = null;
        String caseId = null;
        String repairAction = null;
        String userId = null;
        String rxCaseID = null;
        String strResult = RMDCommonConstants.EMPTY_STRING;
        String userFirstName = null;
        String userLastName = null;
        String cityName = null;
        String stateName = null;

        String s_Title = RMDCommonConstants.S_TITLE;
        LOG.debug("RMDService :CaseDAOImpl :dispatchCase() ::::START");
        try {
            rxCaseID = dispatchCaseVO.getRxCaseID();
            caseId = dispatchCaseVO.getStrCaseId();
            repairAction = EsapiUtil.escapeSpecialChars(dispatchCaseVO.getRepairAction());
            userId = dispatchCaseVO.getStrUserName();
            userFirstName = dispatchCaseVO.getStrFirstName();
            userLastName = dispatchCaseVO.getStrLastName();
            // step 6
            StringBuilder dispatchMSDCQry = new StringBuilder();
            dispatchMSDCQry.append("UPDATE table_case SET table_case.CASE_CURRQ2QUEUE =(SELECT OBJID "
                    + " FROM table_queue WHERE S_TITLE=:s_Title) WHERE id_number=:caseId");
            hibernateSession = getHibernateSession();

            if (hibernateSession != null) {

                Query dispatchMSDCQuery = hibernateSession.createSQLQuery(dispatchMSDCQry.toString());
                if (null != caseId && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(caseId)) {
                    dispatchMSDCQuery.setParameter(RMDCommonConstants.CASEID, caseId);
                }
                if (null != s_Title && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(s_Title)) {
                    dispatchMSDCQuery.setParameter(RMDCommonConstants.STITLE, s_Title);
                }

                dispatchMSDCQuery.executeUpdate();
                if (null != dispatchCaseVO.getLocationId()
                        && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(dispatchCaseVO.getLocationId())) {
                    StringBuilder cityStateQry = new StringBuilder();
                    cityStateQry.append("SELECT TBA.CITY,TBA.STATE FROM TABLE_SITE TBS,TABLE_ADDRESS TBA ");
                    cityStateQry.append("WHERE TBS.CUST_PRIMADDR2ADDRESS = TBA.OBJID ");
                    cityStateQry.append("AND TBS.OBJID=:locationId ");
                    Query cityStateQuery = hibernateSession.createSQLQuery(cityStateQry.toString());

                    cityStateQuery.setParameter(RMDCommonConstants.LOCATION_ID, dispatchCaseVO.getLocationId());

                    List cityState = cityStateQuery.list();
                    if (null != cityState && !cityState.isEmpty()) {
                        Object[] cityStName = (Object[]) cityState.get(0);
                        cityName = (String) cityStName[0];
                        stateName = (String) cityStName[1];
                    }
                }
                
                // Step 7 
             
                String fdbk = null;
                	   fdbk = userId + RMDCommonConstants.FULL_COLON + RMDCommonConstants.BLANK_SPACE + userFirstName
                            + RMDCommonConstants.BLANK_SPACE + userLastName + RMDCommonConstants.FULL_COLON
                            + RMDCommonConstants.BLANK_SPACE + repairAction;
                
                StringBuilder updateFdbkQry = new StringBuilder();
                updateFdbkQry.append(
                        "UPDATE GETS_SD_CUST_FDBK FDBK1  SET FDBK1.RX_FDBK =:FDBTXT, FDBK1.RX_CLOSE_DATE=SYSDATE,FDBK1.FDBK_DATE=SYSDATE, ");
                updateFdbkQry.append("FDBK1.RX_CLOSE_CITY=:CITY, FDBK1.RX_CLOSE_ST=:STATE ");
                updateFdbkQry.append(
                        "WHERE OBJID = (SELECT MAX(FDBK.OBJID) FROM GETS_SD_CUST_FDBK FDBK, TABLE_CASE TC WHERE CUST_FDBK2CASE=TC.OBJID  AND FDBK.RX_CASE_ID =:rxCaseID) ");
                //updateFdbkQry.append("(SELECT MAX(OBJID) FROM GETS_SD_CUST_FDBK FDB WHERE FDB.CUST_FDBK2CASE=TC.OBJID)  AND FDBK.RX_CASE_ID =:rxCaseID)");
                Query updateQry = hibernateSession.createSQLQuery(updateFdbkQry.toString());

                updateQry.setParameter(RMDCommonConstants.CITY_NAME, cityName, Hibernate.STRING);

                updateQry.setParameter(RMDCommonConstants.STATE_NAME, stateName, Hibernate.STRING);

                if (null != caseId && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(rxCaseID)) {
                    updateQry.setParameter(RMDCommonConstants.RXCASEID, rxCaseID);
                }
                if (null != fdbk && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(fdbk)) {
                    updateQry.setParameter(RMDCommonConstants.FDBTXT, fdbk);
                }
                updateQry.executeUpdate();

                // Step 8
                strResult = casefdkHistory(dispatchCaseVO, fdbk);
            }
        } catch (RMDConcurrencyException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DISPATCH_CASE_CONCURENCY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MINOR_ERROR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DISPATCH_CASE_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.MAJOR_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occured in dispatching case for executing rx " + e);
            if (e instanceof StaleObjectStateException) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DISPATCH_CASE_CONCURENCY_ERROR_CODE);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } else {
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DISPATCH_CASE_ERROR_CODE);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            }
        } finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("RMDService :CaseDAOImpl :dispatchCase() ::::END");
        return strResult;
    }

    /**
     * @param dispatchCaseVO
     * @param closureNotes
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("deprecation")
    public String casefdkHistory(DispatchCaseVO dispatchCaseVO, String closureNotes) throws SQLException {
        String strCaseId = null;
        Connection conn = null;
        CallableStatement callableStatement = null;
        String strResult = RMDCommonConstants.EMPTY_STRING;
        String userId = null;
        Session objSession = null;
        if (dispatchCaseVO != null) {
            strCaseId = dispatchCaseVO.getStrCaseId();
            userId = dispatchCaseVO.getEoaUserId();
        }
        try {
            objSession = getHibernateSession();
            conn = objSession.connection();
            callableStatement = conn
                    .prepareCall("{ call GETS_TOOLS_ADDTOCASEHIS_PKG.GETS_TOOLS_ADDTOCASEHIS_PR(?,?,?,?,?,?) }");
            callableStatement.setString(1, strCaseId);
            callableStatement.setString(2, closureNotes);
            callableStatement.setString(3, "RECOMMENDATION CLOSED");
            callableStatement.setString(4, userId);

            callableStatement.registerOutParameter(5, java.sql.Types.NUMERIC);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);

            callableStatement.executeUpdate();

            String numb = callableStatement.getString(6);
            callableStatement.close();
            LOG.debug("casefdkHistory() method ends here......." + numb);
            strResult = RMDCommonConstants.SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occured in updating procedure" + e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException e) {
                LOG.error("Exception occured in updating procedure" + e);
            }
            releaseSession(objSession);
        }
        return strResult;
    }
    @Override
	public List<RxExecTaskDetailsServiceVO> getRepeaterRxsList()
			throws RMDDAOException {
		LOG.debug("RxExecutionEoaDAOImpl: Entered into getRepeaterRxsList() method");
	        StringBuilder strQuery = null;
	        Session session = null;
	        Query query = null;
	        List<Object> queryList = null;
	        RxExecTaskDetailsServiceVO objRxExecTaskDetailsServiceVO;
	        List<RxExecTaskDetailsServiceVO> rxExecTaskDetailsServiceVOList = null;
	        try {
	            session = getHibernateSession();
	            strQuery = new StringBuilder();
	            strQuery.append("SELECT sp.X_VEH_HDR");
	            strQuery.append("|| sp.serial_no as LocoID,     sp.X_FLEET Fleet,     sp.x_controller_cfg Model,     cf.rx_case_id rx_id,     ty.title Case_Type ,     cf.rx_open_date,");
	            strQuery.append("cf.rx_close_date,     r.title,   r.urgency,     to_char(sysdate,'mm/dd/yyyy') as rundate , rd.recom_notes ");
	            strQuery.append("FROM gets_sd_recom_delv rd,");
	            strQuery.append("   gets_sd_cust_fdbk cf,    gets_sd_recom r,     sa.table_site_part sp,");
	            strQuery.append("  sa.table_case c ,     sa.table_gbst_elm ty ");
			    strQuery.append("WHERE rd.recom_delv2cust_fdbk = cf.objid ");
				strQuery.append("AND c.calltype2gbst_elm       = ty.objid ");
			    strQuery.append("AND rd.recom_delv2recom       = r.objid ");
				strQuery.append("AND  rd.RECOM_DELV2CASE = c.objid ");
				strQuery.append("  AND cf.cust_fdbk2case         = c.objid ");
				strQuery.append(" AND c.case_prod2site_part     = sp.objid ");
				strQuery.append("AND   cf.rx_open_date >= TO_DATE( sysdate) - 1 AND cf.rx_open_date < TO_DATE(sysdate) ");
				strQuery.append("AND r.urgency                        NOT IN ('O','B') ");
				strQuery.append(" AND (cf.rx_case_ID LIKE '%-0%'   OR cf.rx_case_ID LIKE '%-1%'   ) ");
				strQuery.append(" and cf.rx_case_ID not LIKE '%-01%' ");
				strQuery.append(" GROUP BY sp.X_VEH_HDR     ||sp.serial_no,     sp.X_FLEET,     sp.x_controller_cfg,     cf.rx_case_id,     ty.title,     cf.rx_open_date,"); 
				strQuery.append("  cf.rx_close_date,     r.title,   r.urgency,     to_char(sysdate,'mm/dd/yyyy') , rd.recom_notes");
	            query = session.createSQLQuery(strQuery.toString());
	            LOG.debug("Query submitted: " + query);
	            queryList = query.list();
	            if (queryList != null && !queryList.isEmpty()) {
	            	rxExecTaskDetailsServiceVOList = new ArrayList<RxExecTaskDetailsServiceVO>(queryList.size());
	                for (final Iterator<Object> iter = queryList.iterator(); iter
	                        .hasNext();) {
	                	objRxExecTaskDetailsServiceVO = new RxExecTaskDetailsServiceVO();
	                    final Object[] objRec = (Object[]) iter.next();
	                    objRxExecTaskDetailsServiceVO.setStrAssetNumber(RMDCommonUtility
	                            .convertObjectToString(objRec[0]));
	                    objRxExecTaskDetailsServiceVO.setStrRxCaseId(RMDCommonUtility
	                            .convertObjectToString(objRec[3]));
	                    objRxExecTaskDetailsServiceVO.setStrRxTitle(RMDCommonUtility
	                            .convertObjectToString(objRec[7]));
	                    objRxExecTaskDetailsServiceVO.setNotes(ESAPI.encoder().encodeForXML(RMDCommonUtility
	                            .convertObjectToString(objRec[10])));
	                    rxExecTaskDetailsServiceVOList.add(objRxExecTaskDetailsServiceVO);
	                }
	            } 
	        } catch (RMDDAOException e) {
	            LOG.error("Exception occurred:", e);
	            throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
	                    new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
	                            .getErrorDetail().getErrorType());
	        } catch (Exception e) {
	        	LOG.error(
	                    "RxExecutionEoaDAOImpl : Exception getRepeaterRxsList() ",
	                    e);
	            throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
	                    e.getMessage());
	        } finally {
	            releaseSession(session);
	            queryList = null;
	            objRxExecTaskDetailsServiceVO = null;
	            query = null;
	            strQuery.setLength(0);
	            strQuery = null;
	        }
	        LOG.debug("RxExecutionEoaDAOImpl: Exiting from getRepeaterRxsList() method");
	        return rxExecTaskDetailsServiceVOList;
	}
    
    
   
    
    /**
	 * @Author : Vamshi
	 * @return : int 
	 * @param  : DispatchCaseVO dispatchCaseVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for saving Rx Feedback.
	 * 
	 */
    @Override
	public String updateRxCustFdbkDetails(DispatchCaseVO dispatchCaseVO) {
		Session hibernateSession = null;
		String caseId = null;
		String repairAction = RMDCommonConstants.EMPTY_STRING;
		String rxCaseID = null;
		String strResult=RMDCommonConstants.EMPTY_STRING;
		try {
			rxCaseID = dispatchCaseVO.getRxCaseID();
			caseId = dispatchCaseVO.getStrCaseId();
			repairAction = EsapiUtil.escapeSpecialChars(dispatchCaseVO.getRepairAction());
			hibernateSession = getHibernateSession();
			StringBuilder updateFdbkQry = new StringBuilder();
			updateFdbkQry
					.append(" UPDATE GETS_SD_CUST_FDBK FDBK1  SET FDBK1.RX_FDBK =:FDBTXT, FDBK1.LAST_UPDATED_DATE=SYSDATE,FDBK1.FDBK_DATE=SYSDATE,LAST_UPDATED_BY=:user ");
			updateFdbkQry
					.append(" WHERE OBJID = (SELECT FDBK.OBJID  FROM GETS_SD_CUST_FDBK FDBK,TABLE_CASE TC WHERE CUST_FDBK2CASE=TC.OBJID  AND FDBK.OBJID = ");
			updateFdbkQry
					.append(" (SELECT MAX(OBJID) FROM GETS_SD_CUST_FDBK FDB WHERE FDB.CUST_FDBK2CASE=TC.OBJID)  AND FDBK.RX_CASE_ID =:rxCaseID)");
			Query updateQry = hibernateSession.createSQLQuery(updateFdbkQry
					.toString());
			updateQry.setParameter(RMDCommonConstants.USER,
					dispatchCaseVO.getEoaUserId(), Hibernate.STRING);
			if (null != caseId
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(rxCaseID)) {
				updateQry.setParameter(RMDCommonConstants.RXCASEID, rxCaseID);
			}
			updateQry.setParameter(RMDCommonConstants.FDBTXT, repairAction);
			updateQry.executeUpdate();
			strResult = RMDCommonConstants.SUCCESS;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error("Unexpected Error occured in updateCustomerFdbk() method of RxExecutionEoaDAOImpl",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.ERROR_IN_SAVE_RX_TASKS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return strResult;
	}
	
	   /**
		 * @Author : Vamshi
		 * @return : boolean 
		 * @param  : String strRxCaseId, Session session
		 * @throws :RMDDAOException
		 * @Description: This method is Responsible for verifying if Rx is already saved or not.
		 * 
		 */
	
	private boolean getRxSavedFlag(String strRxCaseId, Session session) {
		StringBuilder buferQuery = new StringBuilder();
		List<Object> exRxClosureObjId = null;
		Query dataQuery = null;
		try {
			buferQuery
					.append(" SELECT OBJID FROM GETS_RMD_EXT_RX_CLOSURE  WHERE LINK_CUSTFDBK = (SELECT OBJID FROM GETS_SD_CUST_FDBK WHERE RX_CASE_ID = :rx_Case_ID) ");
			dataQuery = session.createSQLQuery(buferQuery.toString());
			dataQuery.setString(RMDCommonConstants.RX_CASE_ID, strRxCaseId);
			exRxClosureObjId = dataQuery.list();
			if (null != exRxClosureObjId && !exRxClosureObjId.isEmpty()) {
				return true;
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in RxExecutionDAOImpl getRxSavedFlag",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		return false;
	}
	@Override
	public List<String> validateURL(String caseId, String fileName,String recommId)
			throws RMDDAOException {
		List<String> caseResultList = null;
		String response = null;
		Session session = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT DISTINCT TASK_DOC.doc_title ");
			caseQry.append("FROM   gets_sd_recom GSR, ");
			caseQry.append("       gets_sd_recom_delv_task DELVTASK, ");
			caseQry.append("       gets_sd_case_recom CR, ");
			caseQry.append("       table_case TC, ");
			caseQry.append("       gets_sd_cust_fdbk FDBK, ");
			caseQry.append("       gets_sd_recom_delv DELV, ");
			caseQry.append("       table_site_part TSP, ");
			caseQry.append("       gets_rmd_vehicle VEH, ");
			caseQry.append("       gets_rmd_veh_hdr VEHHDR, ");
			caseQry.append("       table_bus_org TBO, ");
			caseQry.append("       gets_sd_recom_delv_task_doc TASK_DOC ");
			caseQry.append("WHERE  TASK_DOC.doc_title IS NOT NULL ");
			caseQry.append("       AND FDBK.cust_fdbk2case = TC.objid ");
			caseQry.append("       AND FDBK.objid = DELV.recom_delv2cust_fdbk ");
			caseQry.append("       AND DELV.recom_delv2case = TC.objid ");
			caseQry.append("       AND DELV.objid = CR.case_recom2recom_delv ");
			caseQry.append("       AND DELVTASK.delv_task2recom_delv = DELV.objid ");
			caseQry.append("       AND FDBK.rx_case_id = :caseId ");
			caseQry.append("       AND TASK_DOC.delv_doc2delv_task = :recommId ");
			caseQry.append("       AND TC.case_prod2site_part = TSP.objid ");
			caseQry.append("       AND TSP.objid = VEH.vehicle2site_part ");
			caseQry.append("       AND VEH.vehicle2veh_hdr = VEHHDR.objid ");
			caseQry.append("       AND VEHHDR.veh_hdr2busorg = TBO.objid ");
			caseQry.append("       AND GSR.objid = DELV.recom_delv2recom ");
			caseQry.append("       AND TASK_DOC.delv_doc2delv_task(+) = DELVTASK.objid");
			
			
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDServiceConstants.CASEID,caseId);
			caseHqry.setParameter(RMDServiceConstants.RECOMM_ID,recommId);
			caseHqry.setFetchSize(1000);
			caseResultList = caseHqry.list();
			
		
		} catch (RMDDAOConnectionException ex) {
			response = RMDCommonConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
			
		} catch (Exception e) {
			response = RMDCommonConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		
		} finally {
			releaseSession(session);
		}
		return caseResultList;
	}
	
	private int saveMobileCDCStatus(Session objSession, RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO, Timestamp systimestamp){
		StringBuilder saveCDSStatusBuilder = new StringBuilder();
    	Query cDCStatusSeqQuery = objSession.createSQLQuery("SELECT GETS_RMD.GETS_RMD_CDC_STATUS_SEQ.NEXTVAL FROM DUAL");
        String cDCStatusSeq = cDCStatusSeqQuery.list().get(0).toString();
        saveCDSStatusBuilder.append("INSERT INTO GETS_RMD.GETS_RMD_CDC_STATUS(OBJID, RX_CASE_ID, SOURCE_DATA, ");
        saveCDSStatusBuilder.append("LAST_UPDATED_BY, LAST_UPDATED_DATE, CREATED_BY, CREATED_DATE, STATUS_FLAG) VALUES(?,?,?,?,?,?,?,?)");
        Query saveMobileCDSStatusQry = objSession.createSQLQuery(saveCDSStatusBuilder.toString());
        saveMobileCDSStatusQry.setParameter(0, cDCStatusSeq);
        saveMobileCDSStatusQry.setParameter(1, rxExecTaskDetailsServiceVO.getStrRxCaseId());
        saveMobileCDSStatusQry.setParameter(2, RMDCommonConstants.MOBILE);
        saveMobileCDSStatusQry.setParameter(3, rxExecTaskDetailsServiceVO.getEoaUserId());
        saveMobileCDSStatusQry.setParameter(4, systimestamp);
        saveMobileCDSStatusQry.setParameter(5, rxExecTaskDetailsServiceVO.getEoaUserId());
        saveMobileCDSStatusQry.setParameter(6, systimestamp);
        saveMobileCDSStatusQry.setParameter(7, RMDCommonConstants.STATUS_FLAG);
        return saveMobileCDSStatusQry.executeUpdate();
	}
	
	private int updateMobileCDCStatus(Session objSession, RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO, Timestamp systimestamp){
		String updateCDCStatusQryStr = " UPDATE GETS_RMD.GETS_RMD_CDC_STATUS SET LAST_UPDATED_BY=?, LAST_UPDATED_DATE=?, SOURCE_DATA=? WHERE RX_CASE_ID=? ";
		Query updateCDCStatusQry = objSession.createSQLQuery(updateCDCStatusQryStr);
		updateCDCStatusQry.setParameter(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getEoaUserId()));
		updateCDCStatusQry.setParameter(1, systimestamp);
		updateCDCStatusQry.setParameter(2, RMDCommonConstants.MOBILE);
		updateCDCStatusQry.setParameter(3, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, rxExecTaskDetailsServiceVO.getStrRxCaseId()));
		updateCDCStatusQry.executeUpdate();    
        return updateCDCStatusQry.executeUpdate();
	}
		
	
	@Override
    public void takeOwnershipForEoAUser(AcceptCaseEoaVO acceptCaseVO)
            throws RMDDAOException {
        LOG.debug("EOACaseAPI :CaseDAOImpl :AcceptCase() :::: STARTS");
        User user = null;
        Session hibernateSession = null;
        String userName = null;
        String caseId = acceptCaseVO.getStrCaseId();
        Long userId = null;
        String strUserId = acceptCaseVO.getStrUserName();
        Long defaultWipBin = null;
        String defaultWip = null;
        try {
            // isvalideoacase will check if the case id is present in eoa and if
            // not will throw an exception
            if (isValidEOACase(caseId)) {
                hibernateSession = getHibernateSession();
                userId = Long.parseLong(getUserSequenceID(hibernateSession,
                        acceptCaseVO.getStrUserName()));
                userName = getEoaUserName(hibernateSession, strUserId);
                String defaultWipQuery = "select a.OBJID from SA.TABLE_WIPBIN a,TABLE_USER b where a.OBJID=B.USER_DEFAULT2WIPBIN and  b.login_name=:userId";
                if (hibernateSession != null) {
                    Query hibernateQuery = hibernateSession
                            .createSQLQuery(defaultWipQuery);
                    hibernateQuery.setParameter(RMDCommonConstants.USERID,
                            userName);
                    defaultWip = hibernateQuery.uniqueResult().toString();
                    if (null != defaultWip && !defaultWip.isEmpty()) {
                        defaultWipBin = Long.parseLong(defaultWip);
                    }
                }
                user = new User();
                user.setUserId(userId);
                user.setLoginName(userName);
                user.setUserDefaultWipbin(defaultWipBin);
                caseServiceAPI.acceptCase(user, caseId);
            }
        } catch (Exception e) {
            LOG.error("Exception occurred in takeOwnership(): ", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TAKE_OWNERSHIP_RTU);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }
	private List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String strRxCaseId, Session session) {
		StringBuilder buferQuery = new StringBuilder();
		List<Object> queryList = null;
		Query dataQuery = null;
		List<RxDeliveryAttachmentVO> attachmentMap = new ArrayList<RxDeliveryAttachmentVO>();
		try {
			
			buferQuery.append("SELECT doc_title, doc_path FROM gets_sd_recom_delv_doc,gets_sd_recom_delv rd, gets_sd_case_recom cr, gets_sd_recom r, table_case c,gets_sd_cust_fdbk fdbk ");
			buferQuery.append("WHERE recom_doc2recom_delv = rd.objid AND r.objid = rd.recom_delv2recom AND case_recom2case = rd.recom_delv2case AND case_recom2recom = rd.recom_delv2recom ");
			buferQuery.append("AND c.objid = rd.recom_delv2case AND cust_fdbk2case = c.objid AND rd.recom_delv2cust_fdbk = fdbk.objid  AND fdbk.RX_CASE_ID = :rx_Case_ID");
			
			dataQuery = session.createSQLQuery(buferQuery.toString());
			dataQuery.setString(RMDCommonConstants.RX_CASE_ID, strRxCaseId);
			
			queryList = dataQuery.list();

			if (queryList != null && !queryList.isEmpty()) {
				for (final Iterator<Object> iter = queryList.iterator(); iter
						.hasNext();) {
					final Object[] objRec = (Object[]) iter.next(); 
					RxDeliveryAttachmentVO rxObject = new RxDeliveryAttachmentVO();
					rxObject.setStrDocumentTitle(objRec[0].toString());
					rxObject.setStrDocumentPath(objRec[1].toString());
					attachmentMap.add(rxObject);
				}
			} 
			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in RxExecutionDAOImpl getRxDeliveryAttachments",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_CLOSE_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		return attachmentMap;
	}
	
	public List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String caseObjid)throws RMDDAOException{
        Session hibernateSession = null;
        List<RxDeliveryAttachmentVO> arlAttachmentVO=new ArrayList<RxDeliveryAttachmentVO>();
        List<Object[]> resultList=null;
        String strRxCaseId=null;
        try {           
            hibernateSession = getHibernateSession();
            String query = "SELECT OBJID,RX_CASE_ID FROM GETS_SD_CUST_FDBK WHERE CUST_FDBK2CASE= :caseObjId ORDER BY OBJID DESC";
            Query hibernateQuery = hibernateSession.createSQLQuery(query);
            hibernateQuery.setFetchSize(10);
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
            		caseObjid);
            resultList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {                
            	Object[] obj=resultList.get(0);
            	strRxCaseId=RMDCommonUtility.convertObjectToString(obj[1]);                
            }
            if(null!=strRxCaseId&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strRxCaseId)){
            arlAttachmentVO=getRxDeliveryAttachments(strRxCaseId, hibernateSession);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_ATTACHMENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RxExecutionDAOImpl getRxDeliveryAttachments", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_ATTACHMENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return arlAttachmentVO;
    }
	
}
