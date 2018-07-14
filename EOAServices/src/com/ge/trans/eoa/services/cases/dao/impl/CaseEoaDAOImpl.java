package com.ge.trans.eoa.services.cases.dao.impl;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Future;

import oracle.jdbc.OracleTypes;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.sql.JoinFragment;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.AsyncResult;

import com.ge.trans.eoa.cm.framework.dao.TableActEntryDao;
import com.ge.trans.eoa.cm.framework.dao.TableCaseDao;
import com.ge.trans.eoa.cm.framework.dao.TableConditionDao;
import com.ge.trans.eoa.cm.framework.dao.TableContactDao;
import com.ge.trans.eoa.cm.framework.dao.TableGbstElmDao;
import com.ge.trans.eoa.cm.framework.dao.TableQueueDao;
import com.ge.trans.eoa.cm.framework.dao.TableScmViewDao;
import com.ge.trans.eoa.cm.framework.dao.TableSiteDao;
import com.ge.trans.eoa.cm.framework.dao.TableUserDao;
import com.ge.trans.eoa.cm.framework.dao.TableWipbinDao;
import com.ge.trans.eoa.cm.framework.domain.ActEntry;
import com.ge.trans.eoa.cm.framework.domain.CaseContent;
import com.ge.trans.eoa.cm.framework.domain.Condition;
import com.ge.trans.eoa.cm.framework.domain.Contact;
import com.ge.trans.eoa.cm.framework.domain.GbstElm;
import com.ge.trans.eoa.cm.framework.domain.Site;
import com.ge.trans.eoa.cm.framework.service.CaseServiceAPI;
import com.ge.trans.eoa.cm.framework.service.NotesServiceAPI;
import com.ge.trans.eoa.cm.vo.User;
import com.ge.trans.eoa.common.util.AliasToEntityMapResultTransformerUtil;
import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VisualizationDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CloseCaseVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CMPrivilegeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseAppendServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseConvertionVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMergeServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMgmtUsersDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CasesHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CloseOutRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CustomerFdbkVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MassApplyRxVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MaterialUsageVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ReCloseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecommDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxStatusHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ScoreRxEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SolutionBean;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputActEntryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewLogVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.caseapi.common.rx.DeliverRecomLMSClosure;
import com.ge.trans.rmd.caseapi.common.rx.DeliverRecommClose;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.rx.mq.RxMQConnection;
import com.ge.trans.rmd.rx.processor.BaseRxExecutor;
import com.ge.trans.rmd.rx.processor.DeleteRxExecutor;
import com.ge.trans.rmd.rx.processor.ModifyRxExecutor;
import com.ge.trans.rmd.rx.processor.OpenRxExecutor;
import com.ge.trans.rmd.rx.util.EoaProperties;
import com.ge.trans.rmd.rx.util.RxDeliveryConstants;
import com.ge.trans.rmd.rx.vo.RxDeliveryDocVO;
import com.ge.trans.rmd.rx.vo.RxDeliveryInputVO;
import com.ge.trans.rmd.rx.vo.RxDeliveryOutputVO;
import com.ge.trans.rmd.rx.vo.RxDelvQueueInfoVO;
import com.ge.trans.rmd.rx.vo.RxLogInfoVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHistoryHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ibm.mq.MQException;
import com.sun.jersey.core.util.Base64;

public class CaseEoaDAOImpl extends BaseDAO implements CaseEoaDAOIntf {

	@Autowired
    private VisualizationDAOIntf visualizationDAOIntf;
	@Autowired
    private CaseServiceAPI caseServiceAPI;
    @Autowired
    private PopupListAdminDAOIntf objpopupListAdminDAO;

    @Autowired
    private NotesServiceAPI objnotesServiceAPI;

    @Autowired
    private TableWipbinDao wipbinDao;
    @Autowired
    private TableGbstElmDao gbstElmDao;
    @Autowired
    private TableContactDao contactDao;
    @Autowired
    private TableConditionDao conditionDao;
    @Autowired
    private TableActEntryDao actEntryDao;
    @Autowired
    private TableScmViewDao tableScmViewDao;
    @Autowired
    private TableSiteDao tableSiteDao;
    @Autowired
    private TableQueueDao queueDao;
    @Autowired
    private TableCaseDao caseDao;
    @Autowired
    private TableUserDao userDao;
    Codec ORACLE_CODEC = new OracleCodec();

    public static final RMDLogger LOG = RMDLogger.getLogger(CaseEoaDAOImpl.class);
    
    static Logger logger = LogManager.getLogger(CaseEoaDAOImpl.class);
    
    private static final String SEPARATOR = ",";
    
    private static final String MIN_FAULT_OBJID = "minFaultObjId";
    private static final String MAX_FAULT_OBJID = "maxFaultObjId";
    private static final String CLOSURE_ADDITIONAL_CODE_REQUIRED = "Closure_Additional_Code_Required";
    private static final String MISS_4F_REPAIR_CODE = "MISS_4F_REPAIR_CODE";
    private static final String MISS_4B_REPAIR_CODE = "MISS_4B_REPAIR_CODE";
    private static final String REP_CODE_WHERE_QRY=" where LIST_NAME = :listName";

    private static final String REP_CODE_LIST = "repCodeList";

    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDDAOException
     */
    @Override
    public String getEoaCurrentOwnership(String caseId) throws RMDDAOException {

        Session objSession = null;
        List<String> defaultOwners = null;
        String ownerName = null;
        try {
            LOG.debug("Begin fetchEoaCurrentOwnership method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();

            caseQry.append("SELECT LOGIN_NAME FROM TABLE_USER USR,TABLE_CASE WHERE CASE_OWNER2USER = USR.OBJID and ID_NUMBER =:caseId ");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                    .setParameter(RMDCommonConstants.CASEID, caseId);
            ownerName = (String) caseHqry.uniqueResult();
            defaultOwners = Arrays
                    .asList(RMDCommonConstants.getDefaultCaseOwners());
            if (null != ownerName
                    && defaultOwners.contains(ownerName.toUpperCase())) {
                ownerName = null;
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("Exception occurred in getEoaCurrentOwnership():", ex);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getEoaCurrentOwnership():", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getEoaCurrentOwnership method of CaseEoaDAOImpl");
        return ownerName;

    }

    @Override
    public void takeOwnership(AcceptCaseEoaVO acceptCaseVO)
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

    @Override
    public void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDDAOException {
        LOG.debug("EOACaseAPI :CaseDAOImpl :yankCase() :::: STARTS");
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
                caseServiceAPI.yankCase(user, 0L, caseId);

            }
        } catch (Exception e) {
            LOG.error("Exception occurred in yankCase():", e);
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

    @Override
    public void addRXTocase(RecomDelvInfoServiceVO caseRXVO)
            throws RMDDAOException {
        Session session = null;
        Query hibernateQuery = null;
        List<Object[]> arrQuery = null;
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
            final Query queryCustomerName = session
                    .createSQLQuery(caseObjidQuery);
            queryCustomerName.setParameter(RMDCommonConstants.CASEID,
                    caseRXVO.getStrCaseID());
            caseObjid = Long.parseLong(queryCustomerName.uniqueResult()
                    .toString());

            if (null != caseRXVO.getStrUrgRepair()
                    && !caseRXVO.getStrUrgRepair().isEmpty()
                    && null != caseRXVO.getStrEstmRepTime()
                    && !caseRXVO.getStrEstmRepTime().isEmpty()) {
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
            hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID,
                    caseRXVO.getStrRxObjid());
            hibernateQuery.setParameter(RMDCommonConstants.CASEID, caseObjid);
            hibernateQuery.setFetchSize(10);
            arrQuery = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrQuery)) {
                // Setting isCaseRecomExist to true is the record already exist
                isCaseRecomExist = true;
                final Iterator<Object[]> iter = arrQuery.iterator();
                if (iter.hasNext()) {
                    final Object[] urgencyAndRepTimeDetailsRow =  iter
                            .next();
                    caseRecomObjid = RMDCommonUtility
                            .convertObjectToInt(urgencyAndRepTimeDetailsRow[0]);
                    urgencyFromCaseRecom = RMDCommonUtility
                            .convertObjectToString(urgencyAndRepTimeDetailsRow[1]);
                    estRepTimeFromCaseRecom = RMDCommonUtility
                            .convertObjectToString(urgencyAndRepTimeDetailsRow[2]);
                }
            }
            /*
             * Retrieving the recom details from the recommendation table
             * GETS_SD_RECOM contains the default urgency, est rep time and
             * version for the recommendation
             */
            urgencyAndRepTimeQuery = "SELECT URGENCY,EST_REPAIR_TIME,VERSION FROM GETS_SD_RECOM WHERE OBJID =:recom_objid";
            hibernateQuery = session.createSQLQuery(urgencyAndRepTimeQuery);
            hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID,
                    caseRXVO.getStrRxObjid());
            hibernateQuery.setFetchSize(10);
            arrQuery = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrQuery)) {
                final Iterator<Object[]> iter = arrQuery.iterator();
                if (iter.hasNext()) {
                    final Object[] urgencyAndRepTimeDetailsRow =  iter
                            .next();
                    urgencyFromRecom = RMDCommonUtility
                            .convertObjectToString(urgencyAndRepTimeDetailsRow[0]);
                    estRepTimeFromRecom = RMDCommonUtility
                            .convertObjectToString(urgencyAndRepTimeDetailsRow[1]);
                    version = RMDCommonUtility
                            .convertObjectToString(urgencyAndRepTimeDetailsRow[2]);
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
                        || !estRepTime
                                .equalsIgnoreCase(estRepTimeFromCaseRecom)) {
                    updateAddRXQuery = "UPDATE GETS_SD_CASE_RECOM SET URGENCY = :urgency, EST_REPAIR_TIME = :strEstTime, "
                            + "LAST_UPDATED_DATE =sysdate, LAST_UPDATED_BY=:userName WHERE OBJID = :caseRecomObjid";
                    hibernateQuery = session.createSQLQuery(updateAddRXQuery);
                    hibernateQuery.setParameter(
                            RMDCommonConstants.EST_REPAIR_TIME, estRepTime);
                    hibernateQuery.setParameter(RMDCommonConstants.URGENCY,
                            urgency);
                    hibernateQuery
                            .setParameter(RMDCommonConstants.CASE_RECOM_OBJID,
                                    caseRecomObjid);
                    hibernateQuery.setParameter(RMDCommonConstants.USERNAME,
                            caseRXVO.getStrUserName());
                    hibernateQuery.executeUpdate();
                } else if (!isUrgEstTimeOverride) {
                    // Throwing exception if the record already exist in case
                    // and if the function is not called by deliver rx
                    String errorCode = RMDCommonUtility
                            .getErrorCode(RMDServiceConstants.RX_EXIST_ERROR_CODE);
                    throw new RMDDAOException(errorCode, new String[] {},
                            RMDCommonUtility.getMessage(errorCode,
                                    new String[] {},
                                    RMDCommonConstants.ENGLISH_LANGUAGE), null,
                            RMDCommonConstants.MINOR_ERROR);
                }
            } else {

                // Inserting in the the GETS_SD_CASE_RECOM table
                insertAddRXQuery = "INSERT INTO GETS_SD_CASE_RECOM (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,"
                        + " CASE_RECOM2CASE,CASE_RECOM2RECOM,REISSUE_FLAG,EST_REPAIR_TIME,URGENCY,VERSION,OB_MSG_ID) "
                        + " VALUES (GETS_SD_CASE_RECOM_SEQ.nextval,sysdate,:userName,sysdate,:userName,:caseSeqId,:recom_objid,null,"
                        + ":strEstTime,:urgency,:version,null)";
                hibernateQuery = session.createSQLQuery(insertAddRXQuery);
                hibernateQuery.setParameter(RMDCommonConstants.USERNAME,
                        caseRXVO.getStrUserName());
                hibernateQuery.setParameter(RMDCommonConstants.CASESEQID,
                        caseObjid);
                hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID,
                        Integer.parseInt(caseRXVO.getStrRxObjid()));
                hibernateQuery.setParameter(RMDCommonConstants.EST_REPAIR_TIME,
                        estRepTime);
                hibernateQuery
                        .setParameter(RMDCommonConstants.URGENCY, urgency);
                hibernateQuery.setParameter(RMDCommonConstants.VERSION,
                        RMDCommonUtility.convertObjectToInt(version));
                hibernateQuery.executeUpdate();

            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error("RMDDAOConnectionException occurred in addRXTocase():", ex);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error("RMDDAOException occurred in addRXTocase():", e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }
        } catch (Exception e) {
            LOG.error("Exception occurred in addRXTocase():", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
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
            final Query queryCustomerName = session
                    .createSQLQuery(strCustomerNameQuery);
            queryCustomerName.setParameter(RMDCommonConstants.CASEID, caseId);            
            return queryCustomerName.uniqueResult().toString()
                    .trim();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in getCustomerNameForCase():", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * This method is used for retrieving Tool output details
     * 
     * @param strCaseId
     *            ,strLanguage,strUserLanguage
     * @return list of ToolOutputServiceVO
     * @throws RMDDAOException
     */
    @Override
	public ArrayList<ToolOutputEoaServiceVO> getToolOutputDetails(
            String strCaseId) throws RMDDAOException {
        ArrayList<Object> arlToolOutput = new ArrayList<Object>();
        ArrayList<ToolOutputEoaServiceVO> arlToolOutList = new ArrayList<ToolOutputEoaServiceVO>();
        Session session = null;
        try {
            session = getHibernateSession();            
            final StringBuilder query = new StringBuilder();
            query.append("SELECT INNER.TOOLID, INNER.RECOMTITLE, REPCODE,REPDESC, MSG, AR_PROBABILITY, ARLISTDATE,FAULTCODE,OBJIDS, DECODE(SIGN(NVL(MTX.DELV_COUNT,-2)-SYSPARM.VALUE),-1, null,MTX.FALSE_ALARM_PCT) FALSE_ALARM,DECODE(SIGN(NVL(MTX.DELV_COUNT,-2)-SYSPARM.VALUE),-1, null, MTX.TOOL_COVG_PCT) TOOL_COVER,");
            query.append(" decode(sign(nvl(mtx.delv_count,-2)-sysparm.value),-1, null, mtx.mdsc_perf_pct) MDSC_PERF, INNER.URGENCY,INNER.EST_REPAIR FROM (SELECT RECOM.OBJID RECOMOBJID,NVL(GTAR.TOOL_ID,'-') TOOLID,NVL(RECOM.TITLE,'-') RECOMTITLE, NVL(GSRC.REPAIR_CODE,'-') REPCODE,NVL(GSRC.REPAIR_DESC,'-')  REPDESC, NVL(GTAR.AR_MESSAGE,'-') MSG,NVL(TO_CHAR(GTAR.AR_PROBABILITY),'-') AS AR_PROBABILITY ,");
            query.append(" nvl(to_char(gtar.LAST_UPDATED_DATE,'MM/DD/YY HH24:MI:SS'),'-') arListDate,nvl(gtar.FC_FAULT,'-') faultCode, nvl(recom.objid,gsrc.objid) Objids, RECOM.URGENCY URGENCY, RECOM.EST_REPAIR_TIME EST_REPAIR from GETS_TOOL_AR_LIST gtar,GETS_SD_REPAIR_CODES gsrc,TABLE_CASE tc ,gets_sd_recom recom where gsrc.OBJID (+)= gtar.AR_LIST2REPAIR_CODES");
            query.append(" and recom.objid (+) = gtar.AR_LIST2RECOM AND GTAR.AR_LIST2CASE  = TC.OBJID AND AR_PROBABILITY != .2and tc.ID_NUMBER = :caseId) inner,gets_tool_rx_metrics mtx, gets_rmd_sysparm sysparm where mtx.rx_metrics2recom (+)= inner.recomObjid AND SYSPARM.TITLE = 'rx_metrics_display_threshold' AND mtx.tool_id (+) = inner.ToolId order by inner.ToolId desc,AR_PROBABILITY desc");
            if (null != session) {
                session.flush();
                session.clear();
                Query hibernateQuery = session.createSQLQuery(query.toString());
                hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                        strCaseId);
                hibernateQuery.setFetchSize(10);
                arlToolOutput = (ArrayList<Object>) hibernateQuery.list();
                if (null != arlToolOutput) {
                    int size = arlToolOutput.size();
                    ToolOutputEoaServiceVO objToolOutputServiceVO = null;
                    for (int i = 0; i < size; i++) {
                        objToolOutputServiceVO = new ToolOutputEoaServiceVO();
                        Object[] objDataArray = (Object[]) arlToolOutput.get(i);
                        objToolOutputServiceVO.setStrRecomId(RMDCommonUtility
                                .convertObjectToString(objDataArray[8]));
                        objToolOutputServiceVO
                                .setStrRecomTitle(RMDCommonUtility
                                        .convertObjectToString(objDataArray[1]));
                        objToolOutputServiceVO
                                .setStrFalseAlarmPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[9]));
                        objToolOutputServiceVO
                                .setStrToolCovgPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[10]));
                        objToolOutputServiceVO
                                .setStrMdscPerfPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[11]));
                        objToolOutputServiceVO.setStrUrgency(RMDCommonUtility
                                .convertObjectToString(objDataArray[12]));
                        objToolOutputServiceVO
                                .setStrEstRepairTime(RMDCommonUtility
                                        .convertObjectToString(objDataArray[13]));
                        arlToolOutList.add(objToolOutputServiceVO);
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TOOL_OUT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in DataScreenDAOImpl getToolOutputDetails()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TOOL_OUT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);

        }
        return arlToolOutList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf#isCaseClosed(
     * java.lang.String)
     */
    @Override
    public boolean isCaseClosed(String caseId) throws RMDDAOException {

        Session objSession = null;
        boolean isCaseClosed = false;
        int caseClosureCount = 0;
        try {
            LOG.debug("Begin getCloseCaseStatus method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();

            caseQry.append("select COUNT(1) from TABLE_CASE CASE1, TABLE_GBST_ELM GBST_ELM where CASE1.CASESTS2GBST_ELM = GBST_ELM.OBJID ");
            caseQry.append("AND UPPER(GBST_ELM.TITLE) = 'CLOSED' AND CASE1.ID_NUMBER = :caseId ");

            Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                    .setParameter(RMDCommonConstants.CASEID, caseId);
            caseClosureCount = ((BigDecimal) caseHqry.uniqueResult())
                    .intValue();

            // If case status is 'CASE CLOSED' in EOA, case is closed.
            if (caseClosureCount > 0) {
                isCaseClosed = true;
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getCloseCaseStatus method of CaseEoaDAOImpl");

        return isCaseClosed;
    }

    /**
     * This method is used for real time update for case closure
     * 
     * @param String
     * @throws RMDServiceException
     */
    @Override
	public void closeCase(CloseCaseVO closeCaseVO) {
        Session objSession = null;
        Query query = null;
        Connection connection = null;
        String summary = null; 
        String caseType = null;
        User user = null;
        int objIdStitleStatus = 0;
        int objIdStitleResolution = 0;
        String caseId = null;
        try {
            objSession = getHibernateSession();
            caseId = closeCaseVO.getStrCaseID();
            CaseContent casecontent =  caseServiceAPI.getCase(caseId);
            user = getUser(closeCaseVO.getStrUserName());
            caseType = getCaseType(caseId);

             query = objSession.createSQLQuery("SELECT ELM.OBJID from TABLE_GBST_ELM ELM,TABLE_GBST_LST LST where ELM.GBST_ELM2GBST_LST = LST.OBJID and LST.TITLE=:lstTitle and ELM.S_TITLE=:elmTitile");
             
             query.setString("lstTitle", "Closed");
             query.setString("elmTitile", "CLOSED");
             
            if (null != query.uniqueResult()) {
                objIdStitleStatus = Integer.parseInt(query.uniqueResult().toString());
            }
            query = objSession.createSQLQuery("SELECT OBJID FROM TABLE_GBST_ELM WHERE S_TITLE=:sTitle");
            query.setString("sTitle", "INSTRUCTION GIVEN");
            if (null != query.uniqueResult()) {
                objIdStitleResolution = Integer.parseInt(query.uniqueResult().toString());
            }
            summary = RMDServiceConstants.SUMMARY;
            if (null != caseType  && (RMDCommonConstants.PINPOINT_PROBLEM.equalsIgnoreCase(caseType)
                            || RMDCommonConstants.RMD_Equipment.equalsIgnoreCase(caseType) 
                            || RMDCommonConstants.QNX_EQUIPMENT.equalsIgnoreCase(caseType))) {
                ScoreRxEoaVO scoreRxEoaVO = new ScoreRxEoaVO();
                scoreRxEoaVO.setCaseId(caseId);
                connection = getConnection(objSession);
                manuallyResetFaults(connection, scoreRxEoaVO);
            }
            caseServiceAPI.closeCase(casecontent, user, objIdStitleStatus,
                    objIdStitleStatus, objIdStitleResolution, summary);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.CLOSE_CASE_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            closeConnection(connection);
            releaseSession(objSession);
        }
        LOG.debug("Ends real time close case method of CaseEoaDAOImpl");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ge.trans.eoa.services.cases.dao.intf.CaseEoaDAOIntf#
     * generateCaseIdNumberNextValue()
     */
    @Override
    public String generateCaseIdNumberNextValue() throws RMDDAOException {
        String caseId;

        try {
            caseId = caseServiceAPI.generateCaseIdNumberNextValue();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } 
        LOG.debug("Ends getCloseCaseStatus method of CaseEoaDAOImpl");

        return caseId;
    }


    public void reOpenCase(CloseCaseVO closeCaseVO) {
        Session objSession = null;
        StringBuilder qrystring = null;
        Query query = null;
        String summary = null;
        User user = null;
        int objIdStitleStatus = 0;
        int objIdStitleResolution = 0;
        try {
            // isvalideoacase will check if the case id is present in eoa and if
            // not will throw an exception
            if (isValidEOACase(closeCaseVO.getStrCaseID())) {
                objSession = getHibernateSession();
                CaseContent casecontent =  caseServiceAPI
                        .getCase(closeCaseVO.getStrCaseID());

                user = getUser(closeCaseVO.getStrUserName());

                qrystring = new StringBuilder();
                qrystring
                        .append("SELECT ELM.OBJID from TABLE_GBST_ELM ELM,TABLE_GBST_LST LST where ELM.GBST_ELM2GBST_LST = LST.OBJID and LST.TITLE='")
                        .append("Closed' and ELM.S_TITLE='").append("CLOSED'");
                query = objSession.createSQLQuery(qrystring.toString());
                if (null != query.uniqueResult()) {
                    objIdStitleStatus = Integer.parseInt((String) query
                            .uniqueResult().toString());
                }

                qrystring = new StringBuilder();
                qrystring.append(
                        "SELECT OBJID FROM TABLE_GBST_ELM WHERE S_TITLE='")
                        .append("INSTRUCTION GIVEN'");
                query = objSession.createSQLQuery(qrystring.toString());
                if (null != query.uniqueResult()) {
                    objIdStitleResolution = Integer.parseInt((String) query
                            .uniqueResult().toString());
                }
                summary = RMDServiceConstants.SUMMARY;
                caseServiceAPI.closeCase(casecontent, user, objIdStitleStatus,
                        objIdStitleStatus, objIdStitleResolution, summary);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.CLOSE_CASE_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends real time close case method of CaseEoaDAOImpl");
    }

    /**
     * @param scoreRxEoaVO
     */
    public void addRxToFinalRecom(ScoreRxEoaVO scoreRxEoaVO, Session session) {
        String query = null;
        Query hibernateQuery = null;
        String finalRecom = null;
        String caseObjid = null;
        Object result;
        try {
            caseObjid = getCaseObjid(session, scoreRxEoaVO.getCaseId());
            scoreRxEoaVO.setCaseObjid(caseObjid);
            scoreRxEoaVO.setEoaUserName(getEoaUserName(session,
                    scoreRxEoaVO.getStrUserName()));
            scoreRxEoaVO.setRecomObjid(getCurrentRecomObjid(session,
                    scoreRxEoaVO.getRxCaseId()));
            query = "SELECT FINAL_RECOM2RECOM FROM GETS_SD_FINAL_RECOM recom WHERE RECOM.FINAL_RECOM2CASE = :caseId";
            hibernateQuery = session.createSQLQuery(query);
            hibernateQuery.setParameter(RMDCommonConstants.CASEID, caseObjid);
            result = hibernateQuery.uniqueResult();

            if (null != result)
                finalRecom = result.toString();
            /*
             * Check if latest recommendation being scored (latest
             * recommendation???) is alreadyadded to the gets_sd_final_recom
             * table.
             */
            if (null != finalRecom && !finalRecom.isEmpty()) {
                if (!finalRecom.equals(getCurrentRecomObjid(session,
                        scoreRxEoaVO.getCaseId()))) {
                    query = "UPDATE GETS_SD_FINAL_RECOM SET FINAL_RECOM2RECOM=:recom_objid WHERE FINAL_RECOM2CASE = :caseId";
                    hibernateQuery = session.createSQLQuery(query);
                    hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                            caseObjid);
                    hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID,
                            scoreRxEoaVO.getRecomObjid());
                    hibernateQuery.executeUpdate();
                }
            } else {
                /*
                 * If no record exists in gets_sd_final_recom table, insert
                 * record corresponding to the Rx being scored in the
                 * gets_sd_final_recom table.
                 */
                query = "INSERT INTO GETS_SD_FINAL_RECOM(OBJID,FINAL_RECOM2CASE,FINAL_RECOM2RECOM,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY) "
                        + "VALUES(GETS_SD_FINAL_RECOM_SEQ.nextval,:caseId,:recom_objid,sysdate,:userName,sysdate,:userName)";
                hibernateQuery = session.createSQLQuery(query);
                hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                        caseObjid);
                hibernateQuery.setParameter(RMDCommonConstants.RECOM_OBJID,
                        scoreRxEoaVO.getRecomObjid());
                hibernateQuery.setParameter(RMDCommonConstants.USERNAME,
                        scoreRxEoaVO.getEoaUserName());
                hibernateQuery.executeUpdate();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {

            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
    }

    /**
     * @param scoreRxEoaVO
     * @Description function for score rx RTU
     */
    @Override
    public void scoreRx(ScoreRxEoaVO scoreRxEoaVO) {
        Session session = null;
        String caseType = null;
        Connection connection = null;
        CaseRepairCodeEoaVO caseRepairCodeEoaVO = null;
        Transaction transaction = null;
        String eoaUserId=null;
        try {
            session = getHibernateSession();
            transaction = session.beginTransaction();
            eoaUserId=getEoaUserName(session,
                    scoreRxEoaVO.getStrUserName());
            if (null == eoaUserId || eoaUserId.isEmpty()) {
            	scoreRxEoaVO.setStrUserName(RMDCommonConstants.EXT_RX_SCORE);
            	scoreRxEoaVO.setEoaUserName(RMDCommonConstants.EXT_RX_SCORE);
            }
            addRxToFinalRecom(scoreRxEoaVO, session);
            caseRepairCodeEoaVO = populateCaseRepairCodeEoaVO(scoreRxEoaVO);
            addCloseOutRepairCodes(caseRepairCodeEoaVO, session);
            transaction.commit();
            scoreRxEoaVO.setCustFdbkObjid(getCustFdbkObjid(session,
                    scoreRxEoaVO.getRxCaseId()));
            caseType = getCaseType(session, scoreRxEoaVO.getCaseId());
            connection = getConnection(session);
            connection.setAutoCommit(false);
            if (RMDCommonConstants.YES.equalsIgnoreCase(scoreRxEoaVO
                    .getGoodFdbk())) {
                scoreRxEoaVO.setAccuRecom(RMDCommonConstants.Y_LETTER_UPPER);
                scoreRxEoaVO.setGoodFdbk(RMDCommonConstants.Y_LETTER_UPPER);
            } else if (RMDCommonConstants.NO.equalsIgnoreCase(scoreRxEoaVO
                    .getGoodFdbk())) {
                scoreRxEoaVO.setAccuRecom(RMDCommonConstants.N_LETTER_UPPER);
                scoreRxEoaVO.setGoodFdbk(RMDCommonConstants.N_LETTER_UPPER);
            }
            updateCustomerFdbk(connection, scoreRxEoaVO);
            if (caseType.equalsIgnoreCase(RMDCommonConstants.PINPOINT_PROBLEM)
                    || caseType
                            .equalsIgnoreCase(RMDCommonConstants.RMD_Equipment)
                    || caseType
                            .equalsIgnoreCase(RMDCommonConstants.QNX_EQUIPMENT))
                manuallyResetFaults(connection, scoreRxEoaVO);

            if (!RMDCommonUtility.isNullOrEmpty(scoreRxEoaVO.getRxNote())) {
                AddNotesEoaServiceVO addnotesVO = new AddNotesEoaServiceVO();
                addnotesVO.setApplyLevel(RMDCommonConstants.STRING_CASE);
                addnotesVO.setSticky(RMDCommonConstants.STRING_FALSE);
                addnotesVO.setCaseId(scoreRxEoaVO.getCaseId());
                addnotesVO.setUserId(scoreRxEoaVO.getEoaUserName());
                addnotesVO.setNoteDescription(ESAPI.encoder().encodeForXML(EsapiUtil.escapeSpecialChars(
                        scoreRxEoaVO.getRxNote())));
                addNotesToCase(addnotesVO);
            }
            /* Calling Case Close if this call is for Case Close */
            if (!scoreRxEoaVO.isScoreRx()) {
                CloseCaseVO closeCaseVO = new CloseCaseVO();
                closeCaseVO.setStrCaseID(caseRepairCodeEoaVO.getCaseId());
                closeCaseVO.setStrUserName(caseRepairCodeEoaVO.getUserId());
                closeCase(closeCaseVO);
            }
            if (eServicesClosureUpdate(connection, scoreRxEoaVO))
                connection.commit();
            else {
                connection.rollback();
                transaction.rollback();
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode);
            }
        } catch (Exception e) {
            if(null != transaction)
                transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            closeConnection(connection);
            releaseSession(session);
        }
    }

    /**
     * method to copy value from ScoreRxEoaVO to CaseRepairCodeEoaVO
     * 
     * @return CaseRepairCodeEoaVO
     */
    private CaseRepairCodeEoaVO populateCaseRepairCodeEoaVO(
            ScoreRxEoaVO scoreRxEoaVO) {
        CaseRepairCodeEoaVO caseRepairCodeEoaVO = new CaseRepairCodeEoaVO();
        List<String> repaidcodeList = new ArrayList<String>();
        try {
            caseRepairCodeEoaVO.setCaseId(scoreRxEoaVO.getCaseId());
            caseRepairCodeEoaVO.setUserId(scoreRxEoaVO.getStrUserName());
            for (Long repairCode : scoreRxEoaVO.getRepairCodes()) {
                repaidcodeList.add(RMDCommonUtility
                        .convertObjectToString(repairCode));
            }
            caseRepairCodeEoaVO.setRepairCodeIdList(repaidcodeList);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return caseRepairCodeEoaVO;
    }

    /**
     * Returns a boolean value based on the cusrtomer feedback for the case.
     * 
     * @return a boolean value true if there is customer feedback for the case
     *         else false.
     */
    /**
     * @param scoreRxEoaVO
     * @return
     * @throws Exception
     */
    public boolean doRxExecutionValidaion(ScoreRxEoaVO scoreRxEoaVO)
            throws Exception {
        Session session = null;
        boolean isRxExecuted = false;
        CallableStatement seqStmt = null;
        Connection con = null;
        String errorMessage = null;
        try {
            session = getHibernateSession();
            con = getConnection(session);
            seqStmt = con
                    .prepareCall("call gets_sd_casetitle_pkg.case_scoring_pr(?,?,?,?,?)");
            seqStmt.setString(1, scoreRxEoaVO.getCaseId());
            seqStmt.registerOutParameter(2, java.sql.Types.INTEGER);
            seqStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            seqStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            seqStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            seqStmt.execute();
            int recCount = seqStmt.getInt(2);
            String retVal = seqStmt.getString(3);
            if (retVal.equals(RMDCommonConstants.MINUS_ONE)) {
                isRxExecuted = false;
                errorMessage = RMDCommonConstants.RX_NOT_EXECUTED_MSG;
            }
            if (recCount > 0) {
                isRxExecuted = true;
            } else {
                isRxExecuted = false;
                errorMessage = RMDCommonConstants.RX_NOT_EXECUTED_MSG;
            }
            seqStmt.close();
            if (!isRxExecuted) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, errorMessage);
            }

            return isRxExecuted;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            closeConnection(con);
            releaseSession(session);
        }
    }

    /**
     * @param scoreRxEoaVO
     * @Description: This function adds repaircode for a case
     */
    public void addRxCaseRepairCodes(ScoreRxEoaVO scoreRxEoaVO) {
        Session session = null;
        String query = null;
        Query hibernateQuery = null;
        Iterator<Long> it = null;
        try {
            session = getHibernateSession();
            if (null != scoreRxEoaVO.getRepairCodes()
                    && !scoreRxEoaVO.getRepairCodes().isEmpty()) {
                it = scoreRxEoaVO.getRepairCodes().iterator();
                while (it.hasNext()) {
                    query = "INSERT INTO GETS_SD_FINAL_REPCODE(OBJID,FINAL_REPCODE2CASE,FINAL_REPCODE2REPCODE,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY) "
                            + "VALUES(GETS_SD_FINAL_REPCODE_SEQ.nextval,:caseId,:repairCode,sysdate,:userName,sysdate,:userName)";
                    hibernateQuery = session.createSQLQuery(query);
                    hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                            scoreRxEoaVO.getCaseObjid());
                    hibernateQuery.setParameter(
                            RMDCommonConstants.REPAIR_CODES, it.next());
                    hibernateQuery.setParameter(RMDCommonConstants.USERNAME,
                            scoreRxEoaVO.getEoaUserName());
                    hibernateQuery.executeUpdate();
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * @Author :
     * @return :String
     * @param :ScoreRxEoaVO
     * @throws :RMDDAOException
     * @Description:This method does eservice validation based on caseobjid and
     *                   rxcaseid.
     */
    @Override
	public String doEServiceValidation(ScoreRxEoaVO scoreRxEoaVO)
            throws RMDDAOException {
        Session session = null;
        String query = null;
        Query hibernateQuery = null;
        Iterator<GetSysLookupVO> lookupIt = null;
        List<Object[]> queryResultList = null;
        String isSuccess = null;
        String serviceReqId = null;
        String serviceReqIdStatus = null;
        String errorMessage = null;
        String b2bClose = null;
        List<GetSysLookupVO> lookupList = null;
        String queue = null;
        try {
            session = getHibernateSession();
            query = "SELECT B2B_CLOSE,LMS_CLOSE,SERVICE_REQ_ID,SERVICE_REQ_ID_STATUS,RX_CASE_ID FROM GETS_SD_CUST_FDBK FDBK "
                    + "WHERE RX_CASE_ID =:rx_Case_ID ";
            hibernateQuery = session.createSQLQuery(query);
            hibernateQuery.setParameter(RMDCommonConstants.RX_CASE_ID,
                    scoreRxEoaVO.getRxCaseId());
            hibernateQuery.setFetchSize(10);
            queryResultList = (ArrayList<Object[]>) hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(queryResultList)) {
                final Iterator<Object[]> iter = queryResultList.iterator();
                if (iter.hasNext()) {
                    final Object[] fdbkRow =  iter.next();
                    serviceReqId = RMDCommonUtility
                            .convertObjectToString(fdbkRow[2]);
                    serviceReqIdStatus = RMDCommonUtility
                            .convertObjectToString(fdbkRow[3]);
                    b2bClose = RMDCommonUtility
                            .convertObjectToString(fdbkRow[0]);
                    /*
                     * If service request id is null for the Rx, return
                     */
                    if (null == serviceReqId || serviceReqId.isEmpty()) {
                        isSuccess = RMDCommonConstants.STRING_TRUE;
                    }
                    /*
                     * If service request id is not null for the Rx, check if
                     * service request status is OPEN.
                     */
                    else if (null != serviceReqIdStatus
                            && serviceReqIdStatus
                                    .equalsIgnoreCase(RMDCommonConstants.OPEN)) {
                        errorMessage = RMDCommonConstants.SERVICE_REQ_OPEN_MESSAGE;
                        isSuccess = errorMessage;
                    } else {
                        /*
                         * if service request status is not OPEN, proceed for
                         * B2B Check.
                         */
                        lookupList = objpopupListAdminDAO
                                .getPopupListValues(RMDCommonConstants.URG_REPAIR);
                        if (null != lookupList) {
                            lookupIt = lookupList.iterator();
                            while (lookupIt.hasNext()) {
                                if (lookupIt.next().getLookValue()
                                        .equalsIgnoreCase(b2bClose)) {
                                    scoreRxEoaVO.setB2BClosed(true);
                                    break;
                                }
                            }
                        }
                        if (scoreRxEoaVO.isB2BClosed()) {
                            isSuccess = RMDCommonConstants.STRING_TRUE;
                        } else {
                            query = "SELECT CUSTLKP.OUTPUT_QUEUE_NAME FROM TABLE_CASE TC,TABLE_SITE TS, "
                                    + "TABLE_BUS_ORG TBO,GETS_SD_CUST_LKP CUSTLKP WHERE TS.OBJID = TC.CASE_REPORTER2SITE AND "
                                    + "CUSTLKP.CUST_LKP2BUS_ORG = TBO.OBJID AND CUSTLKP.OUTPUT_QUEUE_NAME IS NOT NULL AND "
                                    + "TBO.OBJID = TS.PRIMARY2BUS_ORG AND TC.OBJID = :caseId";
                            hibernateQuery = session.createSQLQuery(query);
                            hibernateQuery.setParameter(
                                    RMDCommonConstants.CASEID,
                                    scoreRxEoaVO.getCaseObjid());
                            hibernateQuery.setFetchSize(10);
                            Object queueObj = hibernateQuery.uniqueResult();
                            if (null != queueObj) {
                                queue = queueObj.toString();
                            }
                            if (null == queue || queue.isEmpty()) {
                                isSuccess = RMDCommonConstants.STRING_TRUE;
                            } else {
                                /*
                                 * If B2B Close is not �Y� and for the customer
                                 * an output queue name is defined, then not
                                 * allowed to score case as not all feedback
                                 * received from customers
                                 */
                                isSuccess = RMDCommonConstants.B2B_VALIDATION_MESSAGE;
                            }
                        }
                    }
                } else {
                    isSuccess = RMDCommonConstants.STRING_TRUE;
                }
            } else {
                isSuccess = RMDCommonConstants.STRING_TRUE;
            }
        } catch (RMDDAOConnectionException ex) {
        	LOG.debug(ex.getMessage(), ex);
            isSuccess = RMDServiceConstants.DAO_CONNECTION_EXCEPTION;
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
                isSuccess = RMDServiceConstants.DAO_EXCEPTION_DO_ESERVICEVALIDATION;            
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            isSuccess = RMDServiceConstants.DAO_EXCEPTION_DO_ESERVICEVALIDATION;
        } finally {
            releaseSession(session);
        }
        return isSuccess;
    }

    /**
     * @param scoreRxEoaVO
     * @return
     */
    public boolean doRepairCodeValidation(ScoreRxEoaVO scoreRxEoaVO) {
        Session session = null;
        String query = null;
        Query hibernateQuery = null;
        boolean isSuccess = false;
        String errorMessage = null;
        int noOfRepairCodes = 0;
        try {
            session = getHibernateSession();
            query = "SELECT count(1) FROM   GETS_SD_REPAIR_CODES RC, TABLE_CASE TC, GETS_SD_FINAL_REPCODE FRP "
                    + "WHERE  tc.ID_NUMBER = :caseId and    tc.objid = frp.FINAL_REPCODE2CASE AND    FRP.FINAL_REPCODE2REPCODE = RC.OBJID ";
            hibernateQuery = session.createSQLQuery(query);
            hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                    scoreRxEoaVO.getCaseId());
            hibernateQuery.setFetchSize(10);
            noOfRepairCodes = Integer.parseInt(hibernateQuery.uniqueResult()
                    .toString());
            /*
             * Check for repair codes already associated with the Rx Case Id
             */
            if (noOfRepairCodes > 0) {
                /*
                 * First checks if the repair codes already added for the case
                 * are IN the list of repair codes that enforce further repair
                 * codes to be added
                 */
                query = "SELECT count(1) FROM GETS_SD_FINAL_REPCODE finrep,GETS_SD_REPAIR_CODES rep,TABLE_CASE tc "
                        + "WHERE tc.ID_NUMBER = :caseId  AND  tc.OBJID = finrep.FINAL_REPCODE2CASE  AND FINAL_REPCODE2REPCODE = rep.OBJID "
                        + "AND rep.REPAIR_CODE IN (SELECT LOOK_VALUE  FROM GETS_RMD_LOOKUP "
                        + "WHERE LIST_NAME = 'Closure_Additional_Code_Required' AND LOOK_STATE != 'InActive')";
                hibernateQuery = session.createSQLQuery(query);
                hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                        scoreRxEoaVO.getCaseId());
                hibernateQuery.setFetchSize(10);
                noOfRepairCodes = Integer.parseInt(hibernateQuery
                        .uniqueResult().toString());

                if (noOfRepairCodes > 0) {
                    /*
                     * If yes, execute query to find if repair codes already
                     * added for the case are NOT IN the list of repair codes
                     * that enforce further repair codes.
                     */
                    query = "SELECT count(1) FROM GETS_SD_FINAL_REPCODE finrep,GETS_SD_REPAIR_CODES rep,TABLE_CASE tc "
                            + "WHERE tc.ID_NUMBER = :caseId  AND  tc.OBJID = finrep.FINAL_REPCODE2CASE  AND FINAL_REPCODE2REPCODE = rep.OBJID "
                            + "AND rep.REPAIR_CODE NOT IN (SELECT LOOK_VALUE  FROM GETS_RMD_LOOKUP "
                            + "WHERE LIST_NAME = 'Closure_Additional_Code_Required' AND LOOK_STATE != 'InActive')";
                    hibernateQuery = session.createSQLQuery(query);
                    hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                            scoreRxEoaVO.getCaseId());
                    hibernateQuery.setFetchSize(10);
                    noOfRepairCodes = Integer.parseInt(hibernateQuery
                            .uniqueResult().toString());
                    if (noOfRepairCodes > 0) {
                        isSuccess = true;
                    } else {
                        /*
                         * If there are no such repair codes added to the case,
                         * returnMessage
                         */
                        isSuccess = false;
                        errorMessage = RMDCommonConstants.ADD_REPAIR_CODE_MESSAGE;
                    }
                } else {
                    isSuccess = true;
                }
            } else {
                /*
                 * If no repair codes associated with the case, return false
                 */
                errorMessage = RMDCommonConstants.NO_REPAIR_CODE_MESSAGE;
                isSuccess = false;
            }
            if (!isSuccess) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, errorMessage);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return isSuccess;
    }

    /**
     * @param connection
     * @param scoreRxEoaVO
     */
    public void updateCustomerFdbk(Connection connection,
            ScoreRxEoaVO scoreRxEoaVO) {
        PreparedStatement stmt = null;
        try {
            stmt = connection
                    .prepareStatement("UPDATE GETS_SD_CUST_FDBK SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY = ?,CASE_SUCCESS = ?,MISS_CODE = ?,"
                            + "ACCU_RECOMM= ?,GOOD_FDBK = ? WHERE OBJID = ?");

            stmt.setString(1, scoreRxEoaVO.getEoaUserName());
            stmt.setString(2, scoreRxEoaVO.getCaseSuccess());
            stmt.setString(3, scoreRxEoaVO.getMissCode());
            stmt.setString(4, scoreRxEoaVO.getAccuRecom());
            stmt.setString(5, scoreRxEoaVO.getGoodFdbk());
            stmt.setInt(6, scoreRxEoaVO.getCustFdbkObjid());
            stmt.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                	LOG.debug(e.getMessage(), e);
                    
                }
            }
        }

    }

    /**
     * @param connection
     * @param scoreRxEoaVO
     */
    /**
     * @param connection
     * @param scoreRxEoaVO
     * @return
     */
    public boolean eServicesClosureUpdate(Connection connection,
            ScoreRxEoaVO scoreRxEoaVO) {
        DeliverRecomLMSClosure closure = new DeliverRecomLMSClosure();
        boolean isSuccess = false;
        try {

            isSuccess = closure.deliverLMSClosure(connection,
                    scoreRxEoaVO.getCaseId(),
                    Integer.parseInt(scoreRxEoaVO.getCaseObjid()),
                    scoreRxEoaVO.getRxCaseId(), scoreRxEoaVO.getRecomObjid(),
                    scoreRxEoaVO.getEoaUserName());
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return isSuccess;
    }

    /**
     * @param connection
     * @param scoreRxEoaVO
     */
    public void manuallyResetFaults(Connection connection,
            ScoreRxEoaVO scoreRxEoaVO) {
        String query = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            query = "SELECT DISTINCT gtft.OBJID"
                    + " FROM GETS_TOOL_CASE_MTM_FAULT mtm_flt,"
                    + " GETS_RMD.GETS_TOOL_AR_LIST gtar,"
                    + " TABLE_CASE tc,"
                    + " GETS_RMD.GETS_TOOL_RPRLDWN gtrl,"
                    + " GETS_RMD.GETS_TOOL_DPD_RULEDEF gtdr,"
                    + " GETS_RMD.GETS_TOOL_DPD_FINRUL gtdf,"
                    + " GETS_RMD.GETS_TOOL_FLTDOWN gtfd,"
                    + " GETS_RMD.GETS_TOOL_FAULT gtft"
                    + " WHERE tc.ID_NUMBER = ?"
                    + " AND gtar.AR_LIST2CASE = tc.OBJID"
                    + " AND gtrl.RPRLDWN2AR_LIST = gtar.OBJID"
                    + " AND gtdr.OBJID = gtrl.RPRLDWN2RULE_DEFN"
                    + " AND gtdf.OBJID = gtdr.RULEDEF2FINRUL"
                    + " AND gtfd.FLTDOWN2RULE_DEFN = gtdr.OBJID"
                    + " AND gtft.OBJID = gtfd.FLTDOWN2FAULT"
                    + " AND tc.OBJID = mtm_flt.MTM2CASE"
                    + " AND mtm_flt.MTM2FAULT = gtfd.FLTDOWN2FAULT "
                    + " AND gtfd.FLTDOWN2FAULT >= (SELECT MIN(MTM2FAULT) FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE = tc.OBJID)"
                    + " AND gtfd.FLTDOWN2FAULT <= (SELECT MAX(MTM2FAULT) FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE = tc.OBJID)"
                    + " AND gtft.FAULT_RESET_TIME IS NULL";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, scoreRxEoaVO.getCaseId());
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resetFiredFaults(connection, scoreRxEoaVO);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            	LOG.debug(e.getMessage(), e);
                
            }
        }
    }

    /**
     * @param connection
     * @param scoreRxEoaVO
     * @throws Exception
     */
    public void resetFiredFaults(Connection connection,
            ScoreRxEoaVO scoreRxEoaVO) throws Exception {
        long timeInMM = System.currentTimeMillis();
        float timeinFloat = timeInMM / 0x36ee80L;
        String date = null;
        try {

            getsrmd.util.EOA_Date.convertToGMT();
            date = getsrmd.util.EOA_Date.dbdatetime;
            CallableStatement callablestatement = connection
                    .prepareCall("CALL gets_sd_casetitle_pkg.manual_rst_fired_flts_pr(?,?,?,?)");
            callablestatement.setString(1, scoreRxEoaVO.getCaseId());
            callablestatement.setFloat(2, timeinFloat);
            callablestatement.setString(3, date);
            callablestatement.registerOutParameter(4, 12);
            callablestatement.execute();
            callablestatement.close();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
    }

    @Override
	public String getCustRxCaseIdPrefix(String customer) throws RMDDAOException {

        Session objSession = null;
        String custRxPrefix = null;
        try {
            LOG.debug("Begin getCustRxCaseIdPrefix method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();

            caseQry.append("SELECT RX_PREFIX FROM GETS_SD_CUST_LKP WHERE CUSTOMER_NAME =:customerName ");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                    .setParameter(RMDCommonConstants.CUSTOMER_NAME, customer);
            custRxPrefix = (String) caseHqry.uniqueResult();

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getCustRxCaseIdPrefix method of CaseEoaDAOImpl");
        return custRxPrefix;
    }

    /**
     * @param RepairCodeEoaDetailsVO
     * @return
     * @throws RMDDAOException
     * @Description To get all the close out repair
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException {

        Session objSession = null;
        List<RepairCodeEoaDetailsVO> repairCodeEoaDetailsList = new ArrayList<RepairCodeEoaDetailsVO>();
        try {
            LOG.debug("Begin getCloseOutRepairCodes method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append("  SELECT GTASK.TASK_ID,GTASK.TASK_DESC,GREPAIR.REPAIR_CODE,GREPAIR.REPAIR_DESC,GREPAIR.OBJID  FROM GETS_SD_RECOM GRECOM,GETS_SD_RECOM_TASK GTASK,GETS_SD_REPAIR_CODES GREPAIR ");
            caseQry.append(" WHERE GRECOM.OBJID = GTASK.RECOM_TASK2RECOM  AND GTASK.RECOM_TASK2REP_CODE = GREPAIR.OBJID (+)  AND GRECOM.OBJID=:strRecomId  ORDER BY TO_NUMBER(TASK_ID) ASC");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                    .setParameter(RMDCommonConstants.RECOM_ID,
                            repairCodeInputType.getRecomId());
            List<Object[]> repairCodeResult = caseHqry.list();
            for (Object[] currentRepairCode : repairCodeResult) {
                RepairCodeEoaDetailsVO repairCode = new RepairCodeEoaDetailsVO();
                repairCode.setTaskId(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[0]));
                repairCode.setTaskDesc(RMDCommonUtility
                        .convertObjectToString(ESAPI
                                .encoder()
                                .encodeForXML(RMDCommonUtility
                                        .convertObjectToString(currentRepairCode[1]))));
                repairCode.setRepairCode(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[2]));
                repairCode.setRepairCodeDesc(RMDCommonUtility
                        .convertObjectToString(ESAPI
                                .encoder()
                                .encodeForXML(RMDCommonUtility
                                        .convertObjectToString(currentRepairCode[3]))));
                repairCode.setRepairCodeId(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[4]));
                repairCodeEoaDetailsList.add(repairCode);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getCloseOutRepairCodes method of CaseEoaDAOImpl");
        return repairCodeEoaDetailsList;

    }

    /**
     * @param RepairCodeEoaDetailsVO
     * @return
     * @throws RMDDAOException
     * @Description To get all the case repair codes
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getCaseRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException {

        Session objSession = null;
        List<RepairCodeEoaDetailsVO> repairCodeEoaDetailsList = new ArrayList<RepairCodeEoaDetailsVO>();
        try {
            LOG.debug("Begin getCaseRepairCodes method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append("  SELECT FRP.FINAL_REPCODE2REPCODE, RC.REPAIR_CODE, RC.REPAIR_DESC FROM   GETS_SD_REPAIR_CODES RC, TABLE_CASE TC, GETS_SD_FINAL_REPCODE FRP ");
            caseQry.append(" WHERE TC.ID_NUMBER =:caseId  AND TC.OBJID = FRP.FINAL_REPCODE2CASE AND    FRP.FINAL_REPCODE2REPCODE = RC.OBJID ");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                    .setParameter(RMDCommonConstants.CASE_ID,
                            repairCodeInputType.getCaseId());
            List<Object[]> repairCodeResult = caseHqry.list();
            for (Object[] currentRepairCode : repairCodeResult) {
                RepairCodeEoaDetailsVO repairCode = new RepairCodeEoaDetailsVO();
                repairCode.setRepairCode(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[1]));
                repairCode.setRepairCodeDesc(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[2]));
                repairCode.setRepairCodeId(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[0]));
                repairCodeEoaDetailsList.add(repairCode);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getCaseRepairCodes method of CaseEoaDAOImpl");
        return repairCodeEoaDetailsList;

    }

    /**
     * @param RepairCodeEoaDetailsVO
     * @return
     * @throws RMDDAOException
     * @Description To get all the repair codes
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException {
        Session objSession = null;
        List<RepairCodeEoaDetailsVO> repairCodeEoaDetailsList = null;
        try {
            objSession = getHibernateSession();
            repairCodeEoaDetailsList = getRepairCodes(repairCodeInputType, objSession);
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("getRepairCodes", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("getRepairCodes", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getRepairCodes method of CaseEoaDAOImpl");
        return repairCodeEoaDetailsList;
    }

    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDDAOException
     */
    @Override
	public void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO)
            throws RMDDAOException {
        Session objSession = null;
        Transaction transaction = null;
        try {
            objSession = getHibernateSession();
            transaction = objSession.beginTransaction();
            addCaseRepairCodes(caseRepairCodeEoaVO, objSession);
            transaction.commit();
        } catch (RMDDAOConnectionException ex) {
            if(null != transaction){
                transaction.rollback();
            }
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            if(null != transaction){
                transaction.rollback();
            }
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ADD_CASE_REPAIR_CODE_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends addCaseRepairCodes method of CaseEoaDAOImpl");
    }

    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDDAOException
     */
    public void addCloseOutRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO,
            Session objSession) throws RMDDAOException {
        StringBuilder qrystring = null;
        Query query = null;
        String userName = null;
        String caseObjid = null;
        List<String> repairCodeList = null;
        List<String> existingRepCodeList = null;
        String codeQuery = null;
        boolean repairCodePresent = false;
        try {
            caseObjid = getCaseObjid(objSession,
                    caseRepairCodeEoaVO.getCaseId());
            userName = getEoaUserName(objSession,
                    caseRepairCodeEoaVO.getUserId());
            codeQuery = "SELECT FINAL_REPCODE2REPCODE FROM GETS_SD_FINAL_REPCODE WHERE FINAL_REPCODE2CASE=:caseSeqId";
            query = objSession.createSQLQuery(codeQuery);
            query.setParameter(RMDCommonConstants.CASESEQID,
                    Long.parseLong(caseObjid));
            existingRepCodeList = query.list();
            repairCodeList = caseRepairCodeEoaVO.getRepairCodeIdList();
            if (RMDCommonUtility.isCollectionNotEmpty(repairCodeList)) {
                for (String repairCode : repairCodeList) {
                    if (RMDCommonUtility
                            .isCollectionNotEmpty(existingRepCodeList)) {
                        repairCodePresent = existingRepCodeList
                                .contains(new BigDecimal(repairCode));
                    }
                    if (!repairCodePresent) {
                        qrystring = new StringBuilder();
                        qrystring
                                .append("INSERT INTO GETS_SD_FINAL_REPCODE (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
                                        + " CREATION_DATE,CREATED_BY,FINAL_REPCODE2CASE,FINAL_REPCODE2REPCODE)"
                                        + " VALUES (GETS_SD_FINAL_REPCODE_SEQ.NEXTVAL,sysdate,:userName,sysdate,:userName,:caseSeqId,:repairCode)");
                        query = objSession.createSQLQuery(qrystring.toString());
                        query.setParameter(RMDCommonConstants.USERNAME,
                                userName);
                        query.setParameter(RMDCommonConstants.CASESEQID,
                                Long.parseLong(caseObjid));
                        query.setParameter(RMDCommonConstants.REPAIR_CODES,
                                repairCode);
                        query.executeUpdate();
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.ADD_CASE_REPAIR_CODE_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        LOG.debug("Ends addCaseRepairCodes method of CaseEoaDAOImpl");
    }

    /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDDAOException
     */
    @Override
	public void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO)
            throws RMDDAOException {
        Session objSession = null;
        StringBuilder qrystring = null;
        Query query = null;
        String caseObjid = null;
        List<String> repairCodeList = null;
        try {
            objSession = getHibernateSession();
            caseObjid = getCaseObjid(objSession,
                    caseRepairCodeEoaVO.getCaseId());
            repairCodeList = caseRepairCodeEoaVO.getRepairCodeIdList();
            if (RMDCommonUtility.isCollectionNotEmpty(repairCodeList)) {
                for (String repairCode : repairCodeList) {
                    qrystring = new StringBuilder();
                    qrystring.append("DELETE FROM GETS_SD_FINAL_REPCODE"
                            + " WHERE FINAL_REPCODE2CASE =:caseSeqId"
                            + " AND FINAL_REPCODE2REPCODE =:repairCode");
                    query = objSession.createSQLQuery(qrystring.toString());
                    query.setParameter(RMDCommonConstants.CASESEQID,
                            Long.parseLong(caseObjid));
                    query.setParameter(RMDCommonConstants.REPAIR_CODES,
                            Long.parseLong(repairCode));
                    query.executeUpdate();
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.REMOVE_CASE_REPAIR_CODE_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends removeCaseRepairCodes method of CaseEoaDAOImpl");
    }

    /**
     * @Author:
     * @param:scoreRxEoaVO
     * @return:String
     * @throws SQLException
     * @throws:RMDDAOException
     * @Description: This method is used for saving the manual feedback
     */
    @Override
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO)
            throws RMDDAOException, SQLException {
        Session session = null;
        Transaction transaction = null;
        Connection connection = null;
        final StringBuilder query = new StringBuilder();
        DeliverRecommClose deliverRecommClose = null;
        String result = null;
        String vehicleObjId = null;
        String returnMessage = null;
        String caseId = scoreRxEoaVO.getCaseId();
        String username = scoreRxEoaVO.getEoaUserName();
        String notes = EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(scoreRxEoaVO.getRxnotes()));
        String applylevel = RMDCommonConstants.CASE;
        String sticky = RMDCommonConstants.STRING_FALSE;
        String reIssue = scoreRxEoaVO.getIsReissue();
        String userId = scoreRxEoaVO.getStrUserName();
        CloseCaseVO objSaveSolutionFeedback = null;
        AddNotesEoaServiceVO objAddNotes = null;
        CaseRepairCodeEoaVO caseRepairCodeEoaVO = null;
        try {
            session = getHibernateSession();
            transaction = session.beginTransaction();
            connection = getConnection(session);
            connection.setAutoCommit(false);
            objSaveSolutionFeedback = new CloseCaseVO();
            objSaveSolutionFeedback.setStrCaseID(caseId);
            objSaveSolutionFeedback.setStrUserName(userId);
            objAddNotes = new AddNotesEoaServiceVO();
            objAddNotes.setApplyLevel(applylevel);
            objAddNotes.setSticky(sticky);
            objAddNotes.setCaseId(caseId);
            objAddNotes.setNoteDescription(notes);
            objAddNotes.setUserId(username);
            // Step 1 Update Customer Feedback Table
            scoreRxEoaVO.setCustFdbkObjid(getCustFdbkObjid(session, scoreRxEoaVO.getRxCaseId()));
            caseRepairCodeEoaVO = populateCaseRepairCodeEoaVO(scoreRxEoaVO);
            addCloseOutRepairCodes(caseRepairCodeEoaVO, session);
            updateManualCustomerFdbk(connection, scoreRxEoaVO);

            // Step 2.Call to DeliverRecommClose�s DeliverClose function
            String caseObjid = getCaseObjid(session, scoreRxEoaVO.getCaseId());
            String recomObjId = getCurrentRecomObjid(session, scoreRxEoaVO.getRxCaseId());

            scoreRxEoaVO.setCaseObjid(caseObjid);
            scoreRxEoaVO.setRecomObjid(recomObjId);

            query.append("SELECT RMDV.OBJID FROM GETS_RMD_VEHICLE RMDV, ");
            query.append("TABLE_SITE_PART TSP, TABLE_CASE TC ");
            query.append("WHERE TC.OBJID =:caseObjId ");
            query.append("AND TC.CASE_PROD2SITE_PART = TSP.OBJID ");
            query.append("AND VEHICLE2SITE_PART =TSP.OBJID");
            Query hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID, RMDCommonUtility.convertObjectToInt(caseObjid));
            Object vechileId = hibernateQuery.uniqueResult();
            if (null != vechileId) {
                vehicleObjId = vechileId.toString();
            }
            deliverRecommClose = new DeliverRecommClose();
            result = deliverRecommClose.DeliverClose(
                    scoreRxEoaVO.getEoaUserName(),
                    RMDCommonUtility.convertObjectToInt(vehicleObjId),
                    RMDCommonUtility.convertObjectToInt(caseObjid),
                    scoreRxEoaVO.getRxCaseId(), scoreRxEoaVO.getCaseId(),
                    RMDCommonConstants.ACTION_TYPE);
            if (null == result || result.isEmpty()) {
                connection.rollback();
                transaction.rollback();
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SAVE_MANUAL_FEEDBACK);
                throw new RMDDAOException(errorCode);
            } else {
                if (RMDCommonConstants.SAVE.equalsIgnoreCase(scoreRxEoaVO
                        .getCaseSuccess())) {
                    scoreRxEoaVO.setMissCode(null);
                } else if (RMDCommonConstants.MISS
                        .equalsIgnoreCase(scoreRxEoaVO.getCaseSuccess())) {
                    scoreRxEoaVO
                            .setMissCode(RMDCommonConstants.MISS_CODE_VALUE);
                }
                scoreRxEoaVO.setAccuRecom(RMDCommonConstants.Y_LETTER_UPPER);
                scoreRxEoaVO.setGoodFdbk(RMDCommonConstants.Y_LETTER_UPPER);
                updateCustomerFdbk(connection, scoreRxEoaVO);
                if (!RMDCommonUtility.isNullOrEmpty(objAddNotes
                        .getNoteDescription())) {
                    addNotesToCase(objAddNotes);
                }
                if (reIssue.equalsIgnoreCase(RMDCommonConstants.STR_FALSE)) {
                    closeCase(objSaveSolutionFeedback);
                }
                transaction.commit();
                connection.commit();
                returnMessage = RMDServiceConstants.SUCCESS;
            }
        } catch (RMDDAOConnectionException ex) {
            if(null != transaction){
                transaction.rollback();
            }
            if(null != connection){
                connection.rollback();
            }
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (RMDDAOException e) {
            if(null != transaction){
                transaction.rollback();
            }
            if(null != connection){
                connection.rollback();
            }
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SAVE_MANUAL_FEEDBACK);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            if(null != transaction){
                transaction.rollback();
            }
            if(null != connection){
                connection.rollback();
            }
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SAVE_MANUAL_FEEDBACK);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            closeConnection(connection);
            releaseSession(session);
        }
        return returnMessage;
    }

    /**
     * @param connection
     * @param scoreRxEoaVO
     */
    public void updateManualCustomerFdbk(Connection connection,
            ScoreRxEoaVO scoreRxEoaVO) {

        PreparedStatement stmt = null;
        try {
            stmt = connection
                    .prepareStatement("UPDATE GETS_SD_CUST_FDBK SET LAST_UPDATED_DATE = SYSDATE, LAST_UPDATED_BY = ?, RX_CLOSE_DATE = SYSDATE,"
                            + " RX_FDBK = ?, FDBK_DATE = SYSDATE WHERE OBJID = ?");

            stmt.setString(1, scoreRxEoaVO.getEoaUserName());
            stmt.setString(2,
            		EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(scoreRxEoaVO.getRxFeedback())));
            stmt.setInt(3, scoreRxEoaVO.getCustFdbkObjid());

            stmt.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.SCORE_RX_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                	LOG.debug(e.getMessage(), e);
                    
                }
            }
        }

    }

    @Override
    public List<SelectCaseHomeVO> getUserCases(
            FindCaseServiceVO findCaseServiceVO) throws RMDDAOException {
        List<Object[]> myCasesList = new ArrayList<Object[]>();
        List<SelectCaseHomeVO> myCases = null;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        SelectCaseHomeVO objSelectCase = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {

            session = getHibernateSession();
            strQuery.append("SELECT ID_NUMBER,BUS_ORG_ORG_ID,X_VEH_HDR,SITE_PART_SERIAL_NO,TITLE,PRIORITY, AGE,TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS'),USER_LOGIN_NAME, CONDITION,STATUS,SEVERITY,OWNER, WORKAROUND, CONDITION_CODE,X_VEH_HDR_CUST FROM SA.TABLE_WIPELM_CASE");
            strQuery.append(" WHERE USER_LOGIN_NAME=:userId AND WIP_OBJID!=0");

            if (null != findCaseServiceVO.getStrCustomerId()
                    && !(findCaseServiceVO.getStrCustomerId().isEmpty())) {
                strQuery.append(" AND BUS_ORG_ORG_ID IN (:customerId)");
            }
            strQuery.append(" ORDER BY CONDITION");

            Query query = session.createSQLQuery(strQuery.toString());

            if (null != findCaseServiceVO.getStrUserId()
                    && !(findCaseServiceVO.getStrUserId().isEmpty())) {
                query.setParameter(RMDServiceConstants.USER_ID, String
                        .valueOf(getEoaUserName(session,
                                findCaseServiceVO.getStrUserId())));
            }
            if (null != findCaseServiceVO.getStrCustomerId()
                    && !(findCaseServiceVO.getStrCustomerId().isEmpty())) {
                List<String> customerList = Arrays.asList(findCaseServiceVO
                        .getStrCustomerId().split(
                                RMDCommonConstants.COMMMA_SEPARATOR));
                query.setParameterList(RMDServiceConstants.CUSTOMER_ID,
                        customerList);
            }

            query.setFetchSize(100);
            myCasesList = (ArrayList<Object[]>) query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                myCases = new ArrayList<SelectCaseHomeVO>();

                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {

                    objSelectCase = new SelectCaseHomeVO();
                    final Object[] caseHome =  obj.next();

                    objSelectCase.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[0]));
                    objSelectCase.setStrTitle(AppSecUtil.htmlEscaping(RMDCommonUtility
                            .convertObjectToString(caseHome[4])));
                    String priority = RMDCommonUtility
                            .convertObjectToString(caseHome[5]);

                    objSelectCase.setStrPriority(RMDCommonUtility
                            .convertObjectToString(priority));

                    if (null != caseHome[8]) {
                        objSelectCase.setStrOwner(RMDCommonUtility
                                .convertObjectToString(caseHome[8]));
                    } else {
                        objSelectCase
                                .setStrOwner(RMDCommonConstants.EMPTY_STRING);
                    }

                    String ownerName = null;
                    if (null != caseHome[8]) {
                        ownerName = RMDCommonUtility
                                .convertObjectToString(caseHome[8]);
                    }
                    if (null != caseHome[8]) {
                        ownerName = ownerName
                                + RMDCommonConstants.BLANK_SPACE
                                + RMDCommonUtility
                                        .convertObjectToString(caseHome[8]);
                    }
                    if (ownerName != null) {
                        objSelectCase.setStrOwnerName(ownerName);
                    } else {
                        objSelectCase
                                .setStrOwnerName(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != caseHome[3]) {
                        objSelectCase.setStrAssetNumber(RMDCommonUtility
                                .convertObjectToString(caseHome[3]));
                    } else {
                        objSelectCase
                                .setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != caseHome[1]) {
                        objSelectCase.setStrcustomerName(RMDCommonUtility
                                .convertObjectToString(caseHome[1]));
                    }
                    if (null != caseHome[2]) {
                        objSelectCase.setStrGrpName(RMDCommonUtility
                                .convertObjectToString(caseHome[2]));
                    } else {
                        objSelectCase
                                .setStrGrpName(RMDCommonConstants.EMPTY_STRING);
                    }

                    if (null != caseHome[10]) {
                        objSelectCase.setStrStatus(RMDCommonUtility
                                .convertObjectToString(caseHome[10]));
                    } else {
                        objSelectCase
                                .setStrStatus(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (null != caseHome[9]) {
                        objSelectCase.setCondition(RMDCommonUtility
                                .convertObjectToString(caseHome[9]));
                    } else {
                        objSelectCase
                                .setStrStatus(RMDCommonConstants.EMPTY_STRING);
                    }

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[7])) {
                        Date creationTime =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[7]));
                        objSelectCase.setDtCreationDate(creationTime);
                    }
                    myCases.add(objSelectCase);
                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            findCaseServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in CaseDAOImpl getUserCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            findCaseServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        }

        finally {
            releaseSession(session);
        }
        return myCases;
    }

    private String getEoaUserObjid(String userId) {

        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        String eoaUserObjid = null;
        List eoaUserObjidList = null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT EOA_USER_ID AS OBJID FROM GET_USR.GET_USR_USERS WHERE USER_ID = :userId ");
            Query query = session.createSQLQuery(strQuery.toString());
            query.setParameter("userId", userId);
            eoaUserObjidList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(eoaUserObjidList)) {
                final Iterator<Object> iter = eoaUserObjidList.iterator();
                if (iter.hasNext()) {
                    final Object recordTypeRow =  iter.next();
                    eoaUserObjid = RMDCommonUtility
                            .convertObjectToString(recordTypeRow);
                }
            }

        } catch (Exception e) {
        	LOG.debug(e.getMessage(), e);

        } finally {
            releaseSession(session);
        }
        return eoaUserObjid;
    }

    @Override
    public CMPrivilegeVO hasCMPrivilege(String userId) throws RMDDAOException {
        Session objSession = null;
        String strCMPrivilegeQuery = null;
        Query hibernateQuery = null;
        List<Object> arrLoginName = null;
        CMPrivilegeVO cmPrivilegeVO = new CMPrivilegeVO();
        try {
            objSession = getHibernateSession();
            strCMPrivilegeQuery = "SELECT USR.LOGIN_NAME FROM TABLE_USER USR WHERE USR.WEB_LOGIN=:userId";
            hibernateQuery = objSession.createSQLQuery(strCMPrivilegeQuery);
            hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
            arrLoginName = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arrLoginName)) {
                final Iterator<Object> iter = arrLoginName.iterator();
                if (iter.hasNext()) {
                    final Object loginNameRow = (Object) iter.next();
                    cmPrivilegeVO.setCmAliasName(RMDCommonUtility
                            .convertObjectToString(loginNameRow));

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.FETCH_CM_PRIVILEGE_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getCMPrivilege()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.FETCH_CM_PRIVILEGE_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return cmPrivilegeVO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#save(com.ge.trans
     * .rmd.services.cases.service.valueobjects.CaseInfoServiceVO)
     */
    /* This method is used to update case title for a case in database */
    @Override
	public String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName)
            throws RMDDAOException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        Session session = null;
        GetCmCaseHVO caseHVO = null;

        try {

            session = getHibernateSession(strUserName);
            Criteria criteria = session.createCriteria(GetCmCaseHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.CASEID,
                    caseInfoServiceVO.getStrCaseId()));
            caseHVO = (GetCmCaseHVO) criteria.list().get(0);
            caseHVO.setTitle(caseInfoServiceVO.getStrTitle());
            session.saveOrUpdate(caseHVO);
            session.flush();
            session.clear();
            // For History Table
            GetCmCaseHistoryHVO objCaseHistoryHVO = new GetCmCaseHistoryHVO();
            objCaseHistoryHVO.setGetCmCase(caseHVO);
            GetSysLookupHVO lookSysHVO = new GetSysLookupHVO();
            lookSysHVO = getLookupHVO(RMDServiceConstants.CASE_LOOKUP_ACTIVITY,
                    RMDServiceConstants.CASE_LOOKUP_MODIFY_VALUE,
                    caseInfoServiceVO.getStrLanguage());
            objCaseHistoryHVO.setGetSysLookup(lookSysHVO);
            objCaseHistoryHVO.setHistoryLine(RMDCommonUtility.getMessage(
                    RMDServiceConstants.CASE_TITLE_SET_TO, new Object[] {},
                    caseInfoServiceVO.getStrLanguage())
                    + " \"" + caseInfoServiceVO.getStrTitle() + "\"");
            insertActivityTable(objCaseHistoryHVO, strUserName);
            session.flush();
            strResult = RMDServiceConstants.CASE_SAVE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            caseInfoServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            caseInfoServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return strResult;
    }

    @Override
	public GetSysLookupHVO getLookupHVO(final String strListName,
            final String strLookupValue, final String strLanguage) {
        GetSysLookupHVO lookupHVO = null;
        Session objSession = null;
        List critList = null;
        try {
            objSession = getHibernateSession();
            Criteria criteria = objSession
                    .createCriteria(GetSysLookupHVO.class).createAlias(
                            RMDCommonConstants.GETSYS_LOOKUP_MULTILANG,
                            RMDCommonConstants.MULTILANG,
                            JoinFragment.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq(RMDServiceConstants.LIST_NAME,
                    strListName));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_VALUE,
                    strLookupValue));
            criteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE,
                    RMDServiceConstants.ACTIVE));
            criteria.add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG,
                    strLanguage));
            criteria.setFetchSize(1);
            critList = (List) criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(critList)) {
                lookupHVO = (GetSysLookupHVO) critList.get(0);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return lookupHVO;
    }

    @Override
	public void insertActivityTable(GetCmCaseHistoryHVO objCaseHistoryHVO,
            String strUserName) {
        Session session = null;
        try {
            session = getHibernateSession(strUserName);
            session.save(objCaseHistoryHVO);
            session.flush();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseHistoryHVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseHistoryHVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    @Override
	public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO objCaseTypeVO)
            throws RMDDAOException {

        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        List objcaseTypeLst;
        CaseTypeEoaVO objCaseType = null;
        List<CaseTypeEoaVO> caseTypeLst = new ArrayList<CaseTypeEoaVO>();

        try {
            session = getHibernateSession();
            strQuery.append("select look_value from gets_rmd_lookup where list_name = :list_name and Look_State in (:Look_State1,:Look_State2) order by look_value");
            Query query = session.createSQLQuery(strQuery.toString());
            query.setParameter("list_name", "PreviousCaseTypes");
            query.setParameter("Look_State1", "Active");
            query.setParameter("Look_State2", "Default");
            objcaseTypeLst = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(objcaseTypeLst)) {
                final Iterator<Object> iter = objcaseTypeLst.iterator();
                Object caseObj = null;
                while (iter.hasNext()) {
                    objCaseType = new CaseTypeEoaVO();
                    caseObj =  iter.next();
                    objCaseType.setCaseTypeTitle(RMDCommonUtility
                            .convertObjectToString(caseObj));
                    caseTypeLst.add(objCaseType);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseTypeVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in FindCaseDAOImpl getDeliveredCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseTypeVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }

        return caseTypeLst;

    }

    // Added by Vamshi For Dispatch Case Functionality

    /**
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDDAOException
     * @Description:This method is used for fetching a list of Dynamic work
     *                   QueueNames from Data Base.
     */

    @Override
    public List<QueueDetailsVO> getQueueNames(String caseId)
            throws RMDDAOException {
        
        Session objSession = null;
        QueueDetailsVO objQueueDetails = null;
        List<QueueDetailsVO> queueDetailsVoList = null;
        try {

            objSession = getHibernateSession();
            StringBuilder rxOpenQry = new StringBuilder();

            rxOpenQry
                    .append("SELECT rx_close_date,service_req_id_status FROM gets_sd.gets_sd_cust_fdbk ");
            rxOpenQry
                    .append("WHERE objid = (SELECT recom_delv2cust_fdbk FROM gets_sd.gets_sd_recom_delv WHERE objid = (SELECT MAX (objid) FROM gets_sd.gets_sd_recom_delv WHERE recom_delv2case = (SELECT objid FROM table_case WHERE id_number = TRIM (:caseId))))");
            Query rxOpenHqry = objSession.createSQLQuery(rxOpenQry.toString());
            rxOpenHqry.setParameter(RMDCommonConstants.CASE_ID, caseId);
            List<Object[]> rxOpenList = rxOpenHqry.list();

            String rXOpen = RMDCommonConstants.LETTER_N;
            String serviceStatus = RMDCommonConstants.NOT_OPEN;
            String infoCase = "";
            String caseType = getCaseType(caseId);
            if (RMDCommonConstants.CASE_TYPE_INFORMATIONAL.equals(caseType)) {
                infoCase = ",'" + RMDCommonConstants.CASE_TYPE_INFORMATIONAL
                        + "'";
            }
            String rxCloseDate = null;
            String serviceReqStatus = null;
            if (rxOpenList.iterator().hasNext()) {
                for (Object[] currentList : rxOpenList) {
                    rxCloseDate = RMDCommonUtility
                            .convertObjectToString(currentList[0]);
                    serviceReqStatus = RMDCommonUtility
                            .convertObjectToString(currentList[1]);
                }

                if (rxCloseDate == null || "".equals(rxCloseDate)) {
                    rXOpen = RMDCommonConstants.LETTER_Y;
                }
                if (RMDCommonConstants.OPEN.equalsIgnoreCase(serviceReqStatus)) {
                    serviceStatus = RMDCommonConstants.OPEN;
                }
            }

            String strM2Qquery = "";
            if ((RMDCommonConstants.OPEN.equalsIgnoreCase(serviceStatus))
                    || (RMDCommonConstants.LETTER_Y.equals(rXOpen) && !(RMDCommonConstants.OPEN
                            .equalsIgnoreCase(serviceStatus)))) {
                strM2Qquery = " union SELECT tq.title, tq.objid "
                        + " FROM table_queue tq, gets_sd_recom recom, gets_sd_recom_delv delv "
                        + " WHERE tq.objid = recom_move2queue "
                        + " AND recom.objid = recom_delv2recom "
                        + " AND delv.objid = " + " (SELECT MAX (objid) "
                        + " FROM gets_sd.gets_sd_recom_delv "
                        + " WHERE recom_delv2case = (SELECT objid "
                        + " FROM table_case "
                        + " WHERE id_number = TRIM (:caseId) )) ";
            }

            StringBuilder queueDetailsQry = new StringBuilder();
            queueDetailsQry.append(" select  objid,title from ");

            queueDetailsQry
                    .append(" (SELECT  title, objid FROM table_queue tq where objid in( ");
            queueDetailsQry
                    .append(" Select QUEUE2TABLE_QUEUE from GETS_SD.GETS_SD_CASE_TO_QUEUE  ");
            queueDetailsQry
                    .append(" where RX_OPEN = :rXOpen and  SERVICE_REQ_ID_STATUS = :serviceStatus and CASE_TYPE in ('All'");
            queueDetailsQry.append(infoCase + ") ) ");
            // Added by Mohamed - US131761- In Dispatch Case, IT Queue should
            // always be an available option
            queueDetailsQry.append(" OR  title in ('"
                    + RMDCommonConstants.DISPATCH_IT_QUEUE + "') ");
            if ((RMDCommonConstants.OPEN.equalsIgnoreCase(serviceStatus))
                    || (RMDCommonConstants.LETTER_Y.equals(rXOpen) && !(RMDCommonConstants.OPEN
                            .equalsIgnoreCase(serviceStatus)))) {
                queueDetailsQry.append(strM2Qquery);
            }
            queueDetailsQry.append(" order by title )");

            Query queueDetailsHqry = objSession.createSQLQuery(queueDetailsQry
                    .toString());
            queueDetailsHqry.setParameter("rXOpen", rXOpen);
            queueDetailsHqry.setParameter("serviceStatus", serviceStatus);
            if ((RMDCommonConstants.OPEN.equalsIgnoreCase(serviceStatus))
                    || (RMDCommonConstants.LETTER_Y.equals(rXOpen) && !(RMDCommonConstants.OPEN
                            .equalsIgnoreCase(serviceStatus)))) {
                queueDetailsHqry.setParameter("caseId", caseId);
            }

            List<Object[]> queueNamesList = queueDetailsHqry.list();
            queueDetailsVoList = new ArrayList<QueueDetailsVO>();
            for (Object[] currentQueue : queueNamesList) {
                objQueueDetails = new QueueDetailsVO();
                objQueueDetails.setQueueId(RMDCommonUtility
                        .convertObjectToString(currentQueue[0]));
                objQueueDetails.setQueueName(RMDCommonUtility
                        .convertObjectToString(currentQueue[1]));
                queueDetailsVoList.add(objQueueDetails);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in getQueueNames method of CaseEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUE_NAMES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return queueDetailsVoList;
    }

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDDAOException
     * @Description:This method is used for a dispatching case to dynamic work
     *                   queues selected by the user.
     */

    @Override
    public String dispatchCaseToWorkQueue(final long queueId,
            final String caseId, final String userId) throws RMDDAOException {

        String result = RMDCommonConstants.FAILURE;
        Session objSession = null;

        long userObjId = 0L;
        try {

            objSession = getHibernateSession();
            StringBuilder dispatchCaseQry = new StringBuilder();
            dispatchCaseQry
                    .append(" SELECT  OBJID  FROM SA.TABLE_USER WHERE LOGIN_NAME=:userId");
            Query dispatchCaseHqry = objSession.createSQLQuery(dispatchCaseQry
                    .toString());
            dispatchCaseHqry.setParameter(
                    RMDCommonConstants.GET_DISPATCH_USERID, userId);
            dispatchCaseHqry.setFetchSize(1);
            List<BigDecimal> userIdList = dispatchCaseHqry.list();
            if (null != userIdList && !userIdList.isEmpty()) {
                userObjId = userIdList.get(0).longValue();
            }

            caseServiceAPI.dispatchCase(userObjId, queueId, caseId);
            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in dispatchCaseToWorkQueue method of CaseEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DiSPTACH_TO_QUEUE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding Case notes for a given case.
     */
    @Override
    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session objSession = null;

        try {
            if (RMDCommonConstants.STRING_UNIT.equalsIgnoreCase(addnotesVO
                    .getApplyLevel())) {
                if (RMDCommonConstants.STRING_TRUE.equalsIgnoreCase(addnotesVO
                        .getSticky())) {

                    StickyNotesDetailsVO unitStickyDetails = fetchStickyUnitNotes(addnotesVO
                            .getCaseId());
                    if (null != unitStickyDetails.getObjId()) {
                        removeStickyNotes(unitStickyDetails.getObjId(), null,
                                unitStickyDetails.getApplyLevel());
                    }

                }
                addUnitNotes(addnotesVO);
                result = RMDCommonConstants.SUCCESS;
            } else if (RMDCommonConstants.STRING_CASE
                    .equalsIgnoreCase(addnotesVO.getApplyLevel())) {
                if (RMDCommonConstants.STRING_TRUE.equalsIgnoreCase(addnotesVO
                        .getSticky())) {
                    StickyNotesDetailsVO caseStickyDetails = fetchStickyCaseNotes(addnotesVO
                            .getCaseId());

                    if (null != caseStickyDetails.getObjId()) {
                        removeStickyNotes(null, caseStickyDetails.getObjId(),
                                caseStickyDetails.getApplyLevel());
                    }

                }
                addCaseNotes(addnotesVO);
                result = RMDCommonConstants.SUCCESS;
            }

        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in addNotesToCase  method of CaseEoaDAOImpl :",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_NOTES_TO_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return result;
    }

    /**
     * @Author :
     * @return :void
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding Unit notes for a given case.
     */

    @Override
    public void addUnitNotes(final AddNotesEoaServiceVO addNotesEoaServiceVO)
            throws RMDDAOException {
        Session objSession = null;
        String isSticky = null;
        try {
            if (null != addNotesEoaServiceVO) {
            String strNoteDescription = ESAPI.encoder().decodeForHTML(
                    addNotesEoaServiceVO.getNoteDescription());
            objSession = getHibernateSession();
            StringBuilder notesQry = new StringBuilder();
            /* Query for updating the unit notes details */          
                notesQry.append("INSERT INTO GETS_SD_GENERIC_NOTES_LOG(OBJID,LAST_UPDATE_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,DESCRIPTION,STICKY,GENERIC2VEHICLE)");
                notesQry.append("VALUES (GETS_SD.GETS_SD_GENERIC_NOTES_LOG_SEQ.NEXTVAL,SYSDATE,:userId,SYSDATE,:userId,:notes,:isSticky,");
                notesQry.append("(SELECT VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V A, TABLE_CASE B WHERE ID_NUMBER=:caseId AND SITE_PART_OBJID = B.CASE_PROD2SITE_PART))");
                Query notesHqry = objSession
                        .createSQLQuery(notesQry.toString());

                notesHqry.setParameter(RMDCommonConstants.USERID,
                        addNotesEoaServiceVO.getUserId());
                notesHqry.setParameter(RMDCommonConstants.GET_NOTES,
                        strNoteDescription);

                if (RMDCommonConstants.STRING_TRUE	
                        .equalsIgnoreCase(addNotesEoaServiceVO.getSticky())) {
                    isSticky = RMDCommonConstants.LETTER_Y;
                } else {
                    isSticky = RMDCommonConstants.LETTER_N;
                }
                notesHqry.setParameter(RMDCommonConstants.IS_STICKY, isSticky);

                notesHqry.setParameter(RMDCommonConstants.CASE_ID,
                        addNotesEoaServiceVO.getCaseId());

                notesHqry.executeUpdate();
            }

        } catch (Exception e) {
            LOG.error(
                    "Exception occurred add Unit Notes  method of CaseEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_NOTES_TO_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }

    }

    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId,caseStickyObjId, applyLevel
     * @throws :RMDDAOException
     * @Description:This method is used for removing a unit Level as well as
     *                   case Level Sticky Notes for a given case.
     */
    @Override
    public String removeStickyNotes(final String unitStickyObjId,
            String caseStickyObjId, final String applyLevel)
            throws RMDDAOException {
        Session objSession = null;
        String result = RMDCommonConstants.FAILURE;
        try {
            objSession = getHibernateSession();
            StringBuilder removeCaseStickyQry = new StringBuilder();
            StringBuilder removeUnitStickyQry = new StringBuilder();

            if (RMDCommonConstants.LETTER_U.equalsIgnoreCase(applyLevel)
                    || RMDCommonConstants.CAPS_ALL.equalsIgnoreCase(applyLevel)) {
                removeUnitStickyQry
                        .append("UPDATE GETS_SD_GENERIC_NOTES_LOG SET STICKY=NULL WHERE OBJID=:objId");
                Query removeStickyHqry = objSession
                        .createSQLQuery(removeUnitStickyQry.toString());
                removeStickyHqry.setParameter(RMDCommonConstants.GET_OBJID,
                        unitStickyObjId);
                removeStickyHqry.executeUpdate();
            }
            if (RMDCommonConstants.LETTER_C.equalsIgnoreCase(applyLevel)
                    || RMDCommonConstants.CAPS_ALL.equalsIgnoreCase(applyLevel)) {
                removeCaseStickyQry
                        .append("UPDATE TABLE_NOTES_LOG SET X_STICKY = NULL WHERE OBJID =:objId");
                Query removeStickyHqry = objSession
                        .createSQLQuery(removeCaseStickyQry.toString());
                removeStickyHqry.setParameter(RMDCommonConstants.GET_OBJID,
                        caseStickyObjId);
                removeStickyHqry.executeUpdate();
            }
            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred at remove Sticky notes method of CaseEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_STICKY_NOTE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return result;

    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDDAOException
     * @Description:This method is used fetching unit Sticky notes details for a
     *                   given case.
     */
    @Override
    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId)
            throws RMDDAOException {
        Session objSession = null;
        StickyNotesDetailsVO objStickyNotesDetails = new StickyNotesDetailsVO();
        StringBuilder unitNotesQry = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            objSession = getHibernateSession();
            unitNotesQry
                    .append("SELECT 'Unit',GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_CONVERT_VARCHAR_FN('C',A.OBJID),TO_CHAR(A.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),A.CREATED_BY CREATED_BY, A.OBJID");
            unitNotesQry
                    .append(" FROM GETS_SD_GENERIC_NOTES_LOG A,TABLE_CASE B,GETS_RMD_VEHICLE C");
            unitNotesQry.append(" WHERE A.GENERIC2VEHICLE = C.OBJID");
            unitNotesQry
                    .append(" AND B.CASE_PROD2SITE_PART = C.VEHICLE2SITE_PART");
            unitNotesQry.append(" AND B.ID_NUMBER=:caseId AND A.STICKY = 'Y'");
            unitNotesQry
                    .append(" AND A.OBJID = (SELECT MAX(OBJID) FROM GETS_SD_GENERIC_NOTES_LOG L WHERE L.GENERIC2VEHICLE = A.GENERIC2VEHICLE");
            unitNotesQry.append(" AND L.STICKY = 'Y')");

            Query unitNotesHqry = objSession.createSQLQuery(unitNotesQry
                    .toString());
            unitNotesHqry.setParameter(RMDCommonConstants.CASE_ID, caseId);
            unitNotesHqry.setFetchSize(1);
            List<Object[]> notesDetailsList = unitNotesHqry.list();

            for (Object[] currentNote : notesDetailsList) {
                objStickyNotesDetails.setApplyLevel(RMDCommonUtility
                        .convertObjectToString(currentNote[0]));
                objStickyNotesDetails.setAdditionalInfo(ESAPI.encoder().encodeForXML(RMDCommonUtility
                        .convertObjectToString(currentNote[1])));
                if (null != RMDCommonUtility
                        .convertObjectToString(currentNote[2])) {
                    Date closeDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(currentNote[2]));
                    objStickyNotesDetails.setEntryTime(closeDate);
                }
                objStickyNotesDetails.setCreatedBy(RMDCommonUtility
                        .convertObjectToString(currentNote[3]));
                objStickyNotesDetails.setObjId(RMDCommonUtility
                        .convertObjectToString(currentNote[4]));
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }

        return objStickyNotesDetails;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDDAOException
     * @Description:This method is used fetching case Sticky notes details for a
     *                   given case.
     */

    @Override
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId)
            throws RMDDAOException {
        Session objSession = null;
        StickyNotesDetailsVO caseNotesInfo = new StickyNotesDetailsVO();
        StringBuilder caseNotesQry = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {

            objSession = getHibernateSession();

            caseNotesQry
                    .append("SELECT 'Case',ADDNL_INFO,TO_CHAR(ENTRY_TIME,'MM/DD/YYYY HH24:MI:SS'),CREATED_BY,OBJID FROM ");
            caseNotesQry
                    .append("(SELECT A.ADDNL_INFO, A.ENTRY_TIME, D.LOGIN_NAME CREATED_BY, C.OBJID  ");
            caseNotesQry
                    .append("FROM TABLE_ACT_ENTRY A,TABLE_CASE B,TABLE_NOTES_LOG C,TABLE_USER D  ");
            caseNotesQry.append("WHERE A.ACT_ENTRY2NOTES_LOG = C.OBJID  ");
            caseNotesQry.append("AND A.ACT_ENTRY2CASE = B.OBJID  ");
            caseNotesQry.append("AND X_STICKY = 'Y'  ");
            caseNotesQry.append("AND D.OBJID= A.ACT_ENTRY2USER  ");
            caseNotesQry.append("AND ACT_CODE = 1700  ");
            caseNotesQry.append("AND B.ID_NUMBER =:caseId  ");
            caseNotesQry.append("AND ROWNUM < 2  ");
            caseNotesQry.append("ORDER BY A.ENTRY_TIME DESC)  ");

            Query caseNotesHqry = objSession.createSQLQuery(caseNotesQry
                    .toString());
            caseNotesHqry.setParameter(RMDCommonConstants.CASE_ID, caseId);
            caseNotesHqry.setFetchSize(1);
            List<Object[]> notesDetailsList = caseNotesHqry.list();
            for (Object[] currentNote : notesDetailsList) {
                caseNotesInfo.setApplyLevel(RMDCommonUtility
                        .convertObjectToString(currentNote[0]));
                caseNotesInfo.setAdditionalInfo(ESAPI.encoder().encodeForXML(RMDCommonUtility
                        .convertObjectToString(currentNote[1])));
                if (null != RMDCommonUtility
                        .convertObjectToString(currentNote[2])) {
                    Date closeDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(currentNote[2]));
                    caseNotesInfo.setEntryTime(closeDate);
                }
                caseNotesInfo.setCreatedBy(RMDCommonUtility
                        .convertObjectToString(currentNote[3]));
                caseNotesInfo.setObjId(RMDCommonUtility
                        .convertObjectToString(currentNote[4]));
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_CASE_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return caseNotesInfo;

    }

    /**
     * @Author :
     * @return :void
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used to adding case notes to the given case.
     */
    @Override
    public void addCaseNotes(final AddNotesEoaServiceVO addnotesVO)
            throws RMDDAOException {
        Session objSession = null;
        String userId = null;
        String isSticky = null;
        try {

            objSession = getHibernateSession();
            StringBuilder addCaseNotesQry = new StringBuilder();

            addCaseNotesQry
                    .append(" SELECT  OBJID  FROM SA.TABLE_USER WHERE LOGIN_NAME=:userId");
            Query addCaseNotesHqry = objSession.createSQLQuery(addCaseNotesQry
                    .toString());
            addCaseNotesHqry.setParameter(
                    RMDCommonConstants.GET_DISPATCH_USERID,
                    addnotesVO.getUserId());

            addCaseNotesHqry.setFetchSize(1);

            List<BigDecimal> userIdList = addCaseNotesHqry.list();
            if (null != userIdList && !userIdList.isEmpty()) {
                userId = userIdList.get(0).toString();
            }

            if (RMDCommonConstants.STRING_TRUE.equalsIgnoreCase(addnotesVO
                    .getSticky())) {
                isSticky = RMDCommonConstants.LETTER_Y;
            } else {
                isSticky = RMDCommonConstants.LETTER_N;

            }
            String strNoteDescription = ESAPI.encoder().decodeForHTML(
                    addnotesVO.getNoteDescription());
            objnotesServiceAPI.addCaseNote(addnotesVO.getCaseId(),
                    strNoteDescription, userId, addnotesVO.getUserId(),
                    isSticky);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_CASE_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

    }

    /**
     * @Author:
     * @param:
     * @return:List<CaseMgmtUsersDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method fetches the list of users by interacting with
     *                   the database.
     */
    @Override
    public List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails()
            throws RMDDAOException {
        List<CaseMgmtUsersDetailsVO> objCaseMgmtUsersDetailsVOList = null;
        CaseMgmtUsersDetailsVO objCaseMgmtUsersDetailsVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        strQuery.append("SELECT FIRST_NAME,");
        strQuery.append("LAST_NAME,");
        strQuery.append("LOGIN_NAME,");
        strQuery.append("WEB_LOGIN ");
        strQuery.append(" FROM TABLE_USER EOA_USER,");
        strQuery.append("GET_USR.GET_USR_USERS USERS ");
        strQuery.append(" WHERE EOA_USER.WEB_LOGIN=USERS.USER_ID ");
        strQuery.append(" AND USERS.GET_USR_USERS_SEQ_ID IN");
        strQuery.append("(SELECT USER_ROLES.LINK_USERS ");
        strQuery.append(" FROM GET_USR.GET_USR_USER_ROLES USER_ROLES,");
        strQuery.append("GET_USR.GET_USR_ROLE_PRIVILEGES ROLE_PRIVILEGES,");
        strQuery.append("GET_USR.GET_USR_PRIVILEGE ");
        strQuery.append(" WHERE USER_ROLES.LINK_ROLES=ROLE_PRIVILEGES.LINK_ROLES ");
        strQuery.append(" AND ROLE_PRIVILEGES.LINK_PRIVILEGES=GET_USR_PRIVILEGE_SEQ_ID ");
        strQuery.append(" AND PRIVILEGE_NAME=:privilegeName)");
        strQuery.append(" ORDER BY FIRST_NAME");
        try {

            session = getHibernateSession();
            query = session.createSQLQuery(strQuery.toString());
            query.setParameter(
                    RMDCommonConstants.PRIVILEGE_NAME,
                    getLookUpValue(RMDCommonConstants.CASE_MANAGEMENT_PRIVILEGE));
            query.setFetchSize(50);
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                objCaseMgmtUsersDetailsVOList = new ArrayList<CaseMgmtUsersDetailsVO>();
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {
                    objCaseMgmtUsersDetailsVO = new CaseMgmtUsersDetailsVO();
                    final Object[] caseHome =  obj.next();

                    objCaseMgmtUsersDetailsVO.setUserId(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    objCaseMgmtUsersDetailsVO.setFirstName(RMDCommonUtility
                            .convertObjectToString(caseHome[0]));
                    objCaseMgmtUsersDetailsVO.setLastName(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));
                    objCaseMgmtUsersDetailsVO.setSso(RMDCommonUtility
                            .convertObjectToString(caseHome[3]));

                    objCaseMgmtUsersDetailsVOList
                            .add(objCaseMgmtUsersDetailsVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_NAMES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseDAOImpl getCaseMgmtUsersDetails()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_NAMES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        }

        finally {

            releaseSession(session);
        }
        return objCaseMgmtUsersDetailsVOList;
    }

    /**
     * @Author:
     * @param:userId,caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method assigns owner for particular caseId invoking
     *                   database
     */
    @Override
    public String assignCaseToUser(String userId, String caseId)
            throws RMDDAOException {
        String result = RMDServiceConstants.FAILURE;
        Session session = null;
        long longUserId = 0L;
        try {

            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT  OBJID  FROM SA.TABLE_USER WHERE LOGIN_NAME=:userID");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.USER_ID, userId);
            caseHqry.setFetchSize(1);
            List<BigDecimal> userIdList = caseHqry.list();
            if (null != userIdList && !userIdList.isEmpty()) {
                longUserId = userIdList.get(0).longValue();
            }
            if (null != caseId && 0L != longUserId) {
                caseServiceAPI.assignCase(longUserId, caseId);
                result = RMDServiceConstants.SUCCESS;
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ASSIGN_CASE_TO_USER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {

            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param:caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method return the owner for particular caseId invoking
     *                   database
     */
    @Override
    public SelectCaseHomeVO getCaseCurrentOwnerDetails(String caseId)
            throws RMDDAOException {
        String result = null;
        List<Object[]> resultList = null;
        Session session = null;
        SelectCaseHomeVO objQueueDetailsVO = new SelectCaseHomeVO();
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append("SELECT LOGIN_NAME,CASE_CURRQ2QUEUE,TABLE_CONDITION.TITLE FROM TABLE_CASE,TABLE_USER,TABLE_CONDITION WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_USER.OBJID= TABLE_CASE.CASE_OWNER2USER AND TABLE_CASE.ID_NUMBER=:caseId");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASEID, caseId);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    final Object[] caseDetails =  obj.next();
                    objQueueDetailsVO.setStrOwner(RMDCommonUtility
                            .convertObjectToString(caseDetails[0]));
                    objQueueDetailsVO.setStrQueue(RMDCommonUtility
                            .convertObjectToString(caseDetails[1]));
                    objQueueDetailsVO.setCondition(RMDCommonUtility
                            .convertObjectToString(caseDetails[2]));

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_OWNER_FOR_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {

            releaseSession(session);
        }
        return objQueueDetailsVO;
    }

    /**
     * @Author:
     * @param:caseId
     * @return:List<CaseHistoryVO>
     * @throws:RMDDAOException
     * @Description: This method return the set of activities for particular
     *               caseId by invoking database
     */
    @Override
    public List<HistoyVO> getCaseHistory(String caseId)
            throws RMDDAOException {
        List<HistoyVO> caseHistoryVOList = null;
        HistoyVO objCaseHistoryVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT A.TITLE, TO_CHAR(A.ENTRY_TIME,'MM/DD/YYYY HH24:MI:SS') CREATION_TIME,A.LOGIN_NAME,A.ADDNL_INFO,A.ACTION_TYPE,A.OBJID,GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_CONVERT_VARCHAR_FN('H',A.OBJID)DESCRIPTION FROM TABLE_CASE_ACT_HISTORY  A WHERE ACT_ENTRY2CASE =:caseId ORDER BY ENTRY_TIME ASC");
            query = session.createSQLQuery(strQuery.toString());
            query.setParameter(RMDServiceConstants.CASEID, caseId);
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                caseHistoryVOList = new ArrayList<HistoyVO>(myCasesList.size());
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {
                    objCaseHistoryVO = new HistoyVO();
                    final Object[] caseHome =  obj.next();
                    objCaseHistoryVO.setActivity(RMDCommonUtility
                            .convertObjectToString(caseHome[0]));
                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[1])) {

                        objCaseHistoryVO.setCreateDate(RMDCommonUtility
                                .convertObjectToString(caseHome[1]));
                    }
                    objCaseHistoryVO.setUser(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[3])) {
                        objCaseHistoryVO
                                .setAddInfo(ESAPI
                                        .encoder()
                                        .encodeForXML(
                                                makeHttpFtpHyperlinks(RMDCommonUtility
                                                        .convertObjectToString(caseHome[3]))));
                    }
                    objCaseHistoryVO.setActivityType(RMDCommonUtility
                            .convertObjectToString(caseHome[4]));
                    objCaseHistoryVO.setObjId(RMDCommonUtility
                            .convertObjectToString(caseHome[5]));
					objCaseHistoryVO
							.setDescription(ESAPI
									.encoder()
									.encodeForXML(EsapiUtil.escapeSpecialChars(
											RMDCommonUtility
													.convertObjectToString(caseHome[6]))));
                    caseHistoryVOList.add(objCaseHistoryVO);

                }
            }
            myCasesList = null;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ACTIVITY_LOG);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseDAOImpl getCaseHistory()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ACTIVITY_LOG);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        }

        finally {

            releaseSession(session);
        }

        return caseHistoryVOList;
    }
    
    @Override
    public List<ViewCaseVO> getViewCases(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<ViewCaseVO> selectCaseHomeVOList = new ArrayList<ViewCaseVO>();
        ViewCaseVO objSelectCaseHomeVO;
        Session session = null;
        StringBuilder strQuery = new StringBuilder();
        Query query = null;
        try {
            session = getHibernateSession();
            
            if (null != objFindCaseServiceVO.getAppendFlag()
                    && objFindCaseServiceVO.getAppendFlag().equalsIgnoreCase(RMDCommonConstants.LETTER_Y)){
                strQuery=appendCaseListQry();
            }else{
            
                strQuery.append("SELECT TCOBJID,ID_NUMBER,CONDITION,PRIORITY,CASE_TITLE,CASEREASON,TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS'),CASE_TYPE,APPENDED,");
                strQuery.append("QUEUE,TO_CHAR(CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS'),OWNER_LOGIN_NAME FROM(");                                        
                
                strQuery.append("SELECT TABLE_CASE.OBJID TCOBJID,");
                strQuery.append("TABLE_CASE.ID_NUMBER ID_NUMBER,");
                strQuery.append("TABLE_CONDITION.TITLE CONDITION,");
                strQuery.append("TABLE_GSE_PRIORITY.TITLE PRIORITY,");
                strQuery.append("TABLE_CASE.TITLE CASE_TITLE,");
                strQuery.append("DECODE(GETS_GET_CASE_REASON_DWQ_FN(TABLE_CASE.OBJID) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review' ,'6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed','Recommendation Deferred','Recommendation Deferred','Recommendation Deleted','Recommendation Deleted''Recommendation Rejected','Recommendation Rejected') CASEREASON,");
                strQuery.append("TABLE_CASE.CREATION_TIME CREATION_TIME,");
                strQuery.append("TABLE_GSE_TYPE.TITLE CASE_TYPE,TABLE_CASE.IS_SUPERCASE IS_SUPERCASE,");
                strQuery.append("DECODE(TABLE_GSE_SEVERITY.TITLE,'Appended', 'YES') APPENDED,");
                strQuery.append("TABLE_SITE.OBJID OBJID,");
                strQuery.append("TABLE_SITE.NAME NAME,");
                strQuery.append("TABLE_SITE.SITE_ID SITE_ID,");
                strQuery.append("TABLE_CASE.CASE_PROD2SITE_PART CASE_PROD2SITE_PART,");
                strQuery.append("TABLE_SITE_PART.X_VEH_HDR X_VEH_HDR,");
                strQuery.append("TABLE_SITE_PART.SERIAL_NO SERIAL_NO,");
                strQuery.append("TABLE_SITE_PART.S_SERIAL_NO S_SERIAL_NO,");
                strQuery.append("TABLE_QUEUE.TITLE QUEUE,");
                strQuery.append("TABLE_BUS_ORG.ORG_ID ORG_ID,");// this line changed
                                                                // for Defect Fix
                strQuery.append("TABLE_CLOSE_CASE.CLOSE_DATE CLOSE_DATE,");
                strQuery.append("TABLE_USER.LOGIN_NAME OWNER_LOGIN_NAME");
                strQuery.append(" FROM TABLE_GBST_ELM TABLE_GSE_TYPE,TABLE_GBST_ELM TABLE_GSE_SEVERITY,TABLE_GBST_ELM TABLE_GSE_PRIORITY,TABLE_CASE,");
                strQuery.append("GETS_RMD_VEHICLE,GETS_RMD_VEH_HDR,TABLE_BUS_ORG,");
                strQuery.append("TABLE_CONDITION,TABLE_SITE,TABLE_SITE_PART,TABLE_CLOSE_CASE,TABLE_QUEUE,TABLE_USER");
               
                strQuery.append(" WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_GSE_TYPE.OBJID = TABLE_CASE.CALLTYPE2GBST_ELM ");
                strQuery.append(" AND TABLE_SITE_PART.OBJID= GETS_RMD_VEHICLE.VEHICLE2SITE_PART AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID");// This
                                                                                                                                                                                                                        // Line
                strQuery.append(" AND TABLE_GSE_SEVERITY.OBJID = TABLE_CASE.RESPSVRTY2GBST_ELM AND TABLE_GSE_PRIORITY.OBJID = TABLE_CASE.RESPPRTY2GBST_ELM ");
                strQuery.append(" AND TABLE_SITE.OBJID = TABLE_CASE.CASE_REPORTER2SITE AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER ");
                strQuery.append(" AND TABLE_CASE.OBJID = TABLE_CLOSE_CASE.LAST_CLOSE2CASE(+) AND TABLE_SITE_PART.OBJID (+) = TABLE_CASE.CASE_PROD2SITE_PART ");
                strQuery.append(" AND TABLE_QUEUE.OBJID (+) = TABLE_CASE.CASE_CURRQ2QUEUE ");
    
                if (null != objFindCaseServiceVO.getStrAssetNumber()
                        && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {
                    strQuery.append(" AND TABLE_SITE_PART.SERIAL_NO=:roadNumber ");
                }
                if (null != objFindCaseServiceVO.getStrCustomerId()
                        && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                    strQuery.append(" AND TABLE_BUS_ORG.ORG_ID=:customer ");
                }
                if (null != objFindCaseServiceVO.getStrAssetGrpName()
                        && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                    strQuery.append(" AND TABLE_SITE_PART.X_VEH_HDR=:assetGroupName ");
                }
                if (null != objFindCaseServiceVO.getStrCaseType()
                        && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {
                    strQuery.append(" AND TABLE_GSE_TYPE.TITLE IN (:caseType)");
                }
                if (null != objFindCaseServiceVO.getCaseID()
                        && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                    strQuery.append(" AND TABLE_CASE.ID_NUMBER LIKE :caseId ");
                }
                if (null != objFindCaseServiceVO.getAppendFlag()
                        && !objFindCaseServiceVO.getAppendFlag().isEmpty()) {
                    strQuery.append(" AND ((TABLE_CONDITION.TITLE IN ('Open','Open-Dispatch')) OR (TABLE_CONDITION.TITLE = 'Closed') AND  TABLE_CLOSE_CASE.CLOSE_DATE >= (SYSDATE-:noOfDays)) ");
                } else if (null != objFindCaseServiceVO.getNoOfDays()
                        && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                    strQuery.append(" AND ((TABLE_CONDITION.TITLE IN ('Open','Open-Dispatch')) OR (TABLE_CONDITION.TITLE = 'Closed') AND TABLE_CONDITION.WIPBIN_TIME >= (SYSDATE-:noOfDays)) ");
                }
                strQuery.append("ORDER BY CONDITION DESC) ORDER BY DECODE(CONDITION,'Closed', close_date) DESC,DECODE(substr(CONDITION,1,4),'Open', creation_time) DESC");
            }
            query = session.createSQLQuery(strQuery.toString());
            if (null != objFindCaseServiceVO.getStrAssetNumber()
                    && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {
                query.setParameter(RMDServiceConstants.ROADNUMBER,
                        objFindCaseServiceVO.getStrAssetNumber());
            }
            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_CUSTOMER,
                        objFindCaseServiceVO.getStrCustomerId());
            }
            if (null != objFindCaseServiceVO.getStrAssetGrpName()
                    && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_ASSET_GROUP_NAME,
                        objFindCaseServiceVO.getStrAssetGrpName());
            }
            if (null != objFindCaseServiceVO.getNoOfDays()
                    && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_NO_OF_DAYS,
                        objFindCaseServiceVO.getNoOfDays());
            }
            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                query.setParameter(
                        RMDCommonConstants.CASEID,
                        RMDServiceConstants.PERCENTAGE
                                + objFindCaseServiceVO.getCaseID()
                                + RMDServiceConstants.PERCENTAGE);
            }
            if (null != objFindCaseServiceVO.getStrCaseType()
                    && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {
                query.setParameterList(RMDCommonConstants.PARAM_CASE_TYPE,
                        Arrays.asList(objFindCaseServiceVO.getStrCaseType().split(RMDCommonConstants.COMMMA_SEPARATOR)));
            }
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                selectCaseHomeVOList = new ArrayList<ViewCaseVO>(myCasesList.size());
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {
                    objSelectCaseHomeVO = new ViewCaseVO();
                    final Object[] caseHome =  obj.next();

                    objSelectCaseHomeVO.setStrTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(caseHome[4])));
                    objSelectCaseHomeVO.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));
                    objSelectCaseHomeVO.setCaseType(RMDCommonUtility
                            .convertObjectToString(caseHome[7]));
                    objSelectCaseHomeVO.setDtCreationDate(RMDCommonUtility
                            .convertObjectToString(caseHome[6]));
                    objSelectCaseHomeVO.setCaseObjid(RMDCommonUtility
                            .convertObjectToLong(caseHome[0]));
                    objSelectCaseHomeVO.setStrPriority(RMDCommonUtility
                            .convertObjectToString(caseHome[3]));
                    objSelectCaseHomeVO.setStrOwner(RMDCommonUtility
                            .convertObjectToString(caseHome[11]));
                    objSelectCaseHomeVO.setStrReason(RMDCommonUtility
                            .convertObjectToString(caseHome[5]));
                    objSelectCaseHomeVO.setCondition(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    objSelectCaseHomeVO.setStrQueue(RMDCommonUtility
                            .convertObjectToString(caseHome[9]));
                    objSelectCaseHomeVO.setCloseDate(RMDCommonUtility
                            .convertObjectToString(caseHome[10]));
                    objSelectCaseHomeVO.setIsAppend(RMDCommonUtility
                            .convertObjectToString(caseHome[8]));
                    selectCaseHomeVOList.add(objSelectCaseHomeVO);
                }
            }
            myCasesList = null;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getViewCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        finally {
            releaseSession(session);
        }
        return selectCaseHomeVOList;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDDAOException
     * @Description:This method return the cases for that asset by invoking
     *                   database
     */
    @Override
    public List<SelectCaseHomeVO> getAssetCases(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<SelectCaseHomeVO> selectCaseHomeVOList = new ArrayList<SelectCaseHomeVO>();
        SelectCaseHomeVO objSelectCaseHomeVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            strQuery.append("SELECT TCOBJID,ID_NUMBER,CONDITION,PRIORITY,CASE_TITLE,CASEREASON,TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS'),CASE_TYPE,IS_SUPERCASE,APPENDED,OBJID,");
            strQuery.append("NAME,SITE_ID,CASE_PROD2SITE_PART,X_VEH_HDR,SERIAL_NO,S_SERIAL_NO,QUEUE,ORG_ID,TO_CHAR(CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS'),OWNER_LOGIN_NAME FROM(");// ORG_ID
                                                                                                                                                                                // changed
                                                                                                                                                                                // for
                                                                                                                                                                                // Fix
            strQuery.append("SELECT TABLE_CASE.OBJID TCOBJID,");
            strQuery.append("TABLE_CASE.ID_NUMBER ID_NUMBER,");
            strQuery.append("TABLE_CONDITION.TITLE CONDITION,");
            strQuery.append("TABLE_GSE_PRIORITY.TITLE PRIORITY,");
            strQuery.append("TABLE_CASE.TITLE CASE_TITLE,");
            strQuery.append("DECODE(GETS_GET_CASE_REASON_DWQ_FN(TABLE_CASE.OBJID) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review' ,'6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed','Recommendation Deferred','Recommendation Deferred','Recommendation Deleted','Recommendation Deleted''Recommendation Rejected','Recommendation Rejected') CASEREASON,");
            strQuery.append("TABLE_CASE.CREATION_TIME CREATION_TIME,");
            strQuery.append("TABLE_GSE_TYPE.TITLE CASE_TYPE,TABLE_CASE.IS_SUPERCASE IS_SUPERCASE,");
            strQuery.append("DECODE(TABLE_GSE_SEVERITY.TITLE,'Appended', 'YES') APPENDED,");
            strQuery.append("TABLE_SITE.OBJID OBJID,");
            strQuery.append("TABLE_SITE.NAME NAME,");
            strQuery.append("TABLE_SITE.SITE_ID SITE_ID,");
            strQuery.append("TABLE_CASE.CASE_PROD2SITE_PART CASE_PROD2SITE_PART,");
            strQuery.append("TABLE_SITE_PART.X_VEH_HDR X_VEH_HDR,");
            strQuery.append("TABLE_SITE_PART.SERIAL_NO SERIAL_NO,");
            strQuery.append("TABLE_SITE_PART.S_SERIAL_NO S_SERIAL_NO,");
            strQuery.append("TABLE_QUEUE.TITLE QUEUE,");
            strQuery.append("TABLE_BUS_ORG.ORG_ID ORG_ID,");// this line changed
                                                            // for Defect Fix
            strQuery.append("TABLE_CLOSE_CASE.CLOSE_DATE CLOSE_DATE,");
            strQuery.append("TABLE_USER.LOGIN_NAME OWNER_LOGIN_NAME");
            strQuery.append(" FROM TABLE_GBST_ELM TABLE_GSE_TYPE,TABLE_GBST_ELM TABLE_GSE_SEVERITY,TABLE_GBST_ELM TABLE_GSE_PRIORITY,TABLE_CASE,");
            strQuery.append("GETS_RMD_VEHICLE,GETS_RMD_VEH_HDR,TABLE_BUS_ORG,");
            strQuery.append("TABLE_CONDITION,TABLE_SITE,TABLE_SITE_PART,TABLE_CLOSE_CASE,TABLE_QUEUE,TABLE_USER");
            strQuery.append(" WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_GSE_TYPE.OBJID = TABLE_CASE.CALLTYPE2GBST_ELM ");
            strQuery.append(" AND TABLE_SITE_PART.OBJID= GETS_RMD_VEHICLE.VEHICLE2SITE_PART AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID");// This
                                                                                                                                                                                                                    // Line
            strQuery.append(" AND TABLE_GSE_SEVERITY.OBJID = TABLE_CASE.RESPSVRTY2GBST_ELM AND TABLE_GSE_PRIORITY.OBJID = TABLE_CASE.RESPPRTY2GBST_ELM ");
            strQuery.append(" AND TABLE_SITE.OBJID = TABLE_CASE.CASE_REPORTER2SITE AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER ");
            strQuery.append(" AND TABLE_CASE.OBJID = TABLE_CLOSE_CASE.LAST_CLOSE2CASE(+) AND TABLE_SITE_PART.OBJID (+) = TABLE_CASE.CASE_PROD2SITE_PART ");
            strQuery.append(" AND TABLE_QUEUE.OBJID (+) = TABLE_CASE.CASE_CURRQ2QUEUE ");

            if (null != objFindCaseServiceVO.getStrAssetNumber()
                    && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {

                strQuery.append(" AND TABLE_SITE_PART.SERIAL_NO=:roadNumber ");

            }

            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                strQuery.append(" AND TABLE_BUS_ORG.ORG_ID=:customer ");

            }

            if (null != objFindCaseServiceVO.getStrAssetGrpName()
                    && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                strQuery.append(" AND TABLE_SITE_PART.X_VEH_HDR=:assetGroupName ");

            }

            if (null != objFindCaseServiceVO.getStrCaseType()
                    && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {

                strQuery.append(" AND TABLE_GSE_TYPE.TITLE=:caseType");

            }

            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                strQuery.append(" AND TABLE_CASE.ID_NUMBER LIKE :caseId ");
            }

            if (null != objFindCaseServiceVO.getAppendFlag()
                    && !objFindCaseServiceVO.getAppendFlag().isEmpty()) {
                strQuery.append(" AND ((TABLE_CONDITION.TITLE like 'Open%') OR (TABLE_CONDITION.TITLE like 'Close%') AND  TABLE_CLOSE_CASE.CLOSE_DATE >= (SYSDATE-:noOfDays)) ");

            } else if (null != objFindCaseServiceVO.getNoOfDays()
                    && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                strQuery.append(" AND ((TABLE_CONDITION.TITLE like 'Open%') OR (TABLE_CONDITION.TITLE like 'Close%') AND TABLE_CONDITION.WIPBIN_TIME >= (SYSDATE-:noOfDays)) ");

            }

            strQuery.append("ORDER BY CONDITION DESC) ORDER BY DECODE(CONDITION,'Closed', close_date) DESC,DECODE(substr(CONDITION,1,4),'Open', creation_time) DESC");

            query = session.createSQLQuery(strQuery.toString());
            if (null != objFindCaseServiceVO.getStrAssetNumber()
                    && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {
                query.setParameter(RMDServiceConstants.ROADNUMBER,
                        objFindCaseServiceVO.getStrAssetNumber());
            }
            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {

                query.setParameter(RMDServiceConstants.STR_CUSTOMER,
                        objFindCaseServiceVO.getStrCustomerId());
            }

            if (null != objFindCaseServiceVO.getStrAssetGrpName()
                    && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_ASSET_GROUP_NAME,
                        objFindCaseServiceVO.getStrAssetGrpName());
            }
            if (null != objFindCaseServiceVO.getNoOfDays()
                    && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_NO_OF_DAYS,
                        objFindCaseServiceVO.getNoOfDays());

            }
            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {

                query.setParameter(
                        RMDCommonConstants.CASEID,
                        RMDServiceConstants.PERCENTAGE
                                + objFindCaseServiceVO.getCaseID()
                                + RMDServiceConstants.PERCENTAGE);

            }
            if (null != objFindCaseServiceVO.getStrCaseType()
                    && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {

                query.setParameter(RMDCommonConstants.PARAM_CASE_TYPE,
                        objFindCaseServiceVO.getStrCaseType());

            }

            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {

                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {

                    objSelectCaseHomeVO = new SelectCaseHomeVO();
                    final Object[] caseHome =  obj.next();

                    objSelectCaseHomeVO.setStrTitle(RMDCommonUtility
                            .convertObjectToString(caseHome[4]));
                    objSelectCaseHomeVO.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));
                    objSelectCaseHomeVO.setStrCaseType(RMDCommonUtility
                            .convertObjectToString(caseHome[7]));

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[6])) {
                        Date creationDate =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[6]));
                        objSelectCaseHomeVO.setDtCreationDate(creationDate);
                    }

                    objSelectCaseHomeVO.setCaseObjid(RMDCommonUtility
                            .convertObjectToLong(caseHome[0]));
                    objSelectCaseHomeVO.setStrPriority(RMDCommonUtility
                            .convertObjectToString(caseHome[3]));
                    objSelectCaseHomeVO.setStrOwner(RMDCommonUtility
                            .convertObjectToString(caseHome[20]));
                    objSelectCaseHomeVO.setStrReason(RMDCommonUtility
                            .convertObjectToString(caseHome[5]));
                    objSelectCaseHomeVO.setCondition(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    objSelectCaseHomeVO.setStrQueue(RMDCommonUtility
                            .convertObjectToString(caseHome[17]));

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[19])) {
                        Date closeDate =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[19]));
                        objSelectCaseHomeVO.setCloseDate(closeDate);
                    }
                    objSelectCaseHomeVO.setIsAppend(RMDCommonUtility
                            .convertObjectToString(caseHome[9]));
                    objSelectCaseHomeVO.setStrAssetNumber(RMDCommonUtility
                            .convertObjectToString(caseHome[15]));
                    objSelectCaseHomeVO.setStrAssetHeader(RMDCommonUtility
                            .convertObjectToString(caseHome[14]));
                    objSelectCaseHomeVO.setStrcustomerName(RMDCommonUtility
                            .convertObjectToString(caseHome[18]));
                    selectCaseHomeVOList.add(objSelectCaseHomeVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getAssetCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);

        }

        return selectCaseHomeVOList;

    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDDAOException
     * @Description:This method is for updating case details based upon user
     *                   choice.
     */

    @Override
    public String updateCaseDetails(final FindCaseServiceVO FindCaseServiceVO)
            throws RMDDAOException {
        Session objSession = null;
        String result = RMDCommonConstants.FAILURE_MSG;
        long caseType = 0L;

        try {

            objSession = getHibernateSession();
            StringBuilder caseUpdateQry = new StringBuilder();
            caseUpdateQry.append("UPDATE  TABLE_CASE  SET");
            if (null != FindCaseServiceVO.getCaseTypes()
                    && !FindCaseServiceVO.getCaseTypes().isEmpty()) {
                caseType = Long.parseLong(FindCaseServiceVO.getCaseTypes());
            }
            if (caseType != 0L && null != FindCaseServiceVO.getStrCaseTitle()
                    && !FindCaseServiceVO.getStrCaseTitle().isEmpty()) {
                caseUpdateQry
                        .append(" TITLE=:caseTitle, CALLTYPE2GBST_ELM=:caseType");
            } else if (caseType != 0L) {
                caseUpdateQry.append(" CALLTYPE2GBST_ELM=:caseType");

            } else if (null != FindCaseServiceVO.getStrCaseTitle()
                    && !FindCaseServiceVO.getStrCaseTitle().isEmpty()) {
                caseUpdateQry.append(" TITLE=:caseTitle");
            }
            caseUpdateQry.append(" WHERE ID_NUMBER=:caseId");
            Query caseHqry = objSession
                    .createSQLQuery(caseUpdateQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASE_ID,
                    FindCaseServiceVO.getCaseID());
            if (caseType != 0L) {
                caseHqry.setParameter(RMDCommonConstants.PARAM_CASE_TYPE,
                        caseType);
            }
            if (null != FindCaseServiceVO.getStrCaseTitle()
                    && !FindCaseServiceVO.getStrCaseTitle().isEmpty()) {
                caseHqry.setParameter(RMDCommonConstants.CASE_TITLE, EsapiUtil.escapeSpecialChars(FindCaseServiceVO.getStrCaseTitle()));
            }
            caseHqry.executeUpdate();
            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_CASE_DETIALS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return result;
    }

    @Override
	public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO objCaseTypeVO)
            throws RMDDAOException {

        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        List objcaseTypeLst;
        CaseTypeEoaVO objCaseType = null;
        List<CaseTypeEoaVO> caseTypeLst = new ArrayList<CaseTypeEoaVO>();

        try {
            session = getHibernateSession();
            strQuery.append("SELECT OBJID, TITLE, RANK FROM TABLE_GBST_ELM WHERE GBST_ELM2GBST_LST = 268435460 AND STATE != 1 ORDER BY RANK");
            Query query = session.createSQLQuery(strQuery.toString());
            objcaseTypeLst = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(objcaseTypeLst)) {
                final Iterator<Object[]> iter = objcaseTypeLst.iterator();
                Object[] caseObj = null;
                while (iter.hasNext()) {
                    objCaseType = new CaseTypeEoaVO();
                    caseObj =  iter.next();
                    objCaseType.setCaseTypeTitle(RMDCommonUtility
                            .convertObjectToString(caseObj[1]));
                    objCaseType.setCaseTypeId(RMDCommonUtility
                            .convertObjtoBigDecimal(caseObj[0]).longValue());
                    caseTypeLst.add(objCaseType);
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseTypeVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in FindCaseDAOImpl getDeliveredCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objCaseTypeVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }

        return caseTypeLst;

    }

    public String makeHttpFtpHyperlinks(String s) {
        int i = 0;
        int j = 0;
        int k = 0;
        String s1 = "";
        String s2 = s;
        String s3 = s.toLowerCase();
        while (i != -1 && k != -1) {
            int l = s3.indexOf("http://", j);
            int i1 = s3.indexOf("ftp://", j);
            int j1 = s3.indexOf("https://", j);
            i = -1;
            if (l != -1)
                i = l;
            if (i1 != -1 && (i1 < i || i == -1))
                i = i1;
            if (j1 != -1 && (j1 < i || i == -1))
                i = j1;
            if (i != -1) {
                int k1 = s2.indexOf(RMDCommonConstants.BLANK_SPACE, i);
                k = s2.indexOf(RMDCommonConstants.CARRIAGE_RETURN, i);
                if (k != -1 && (k1 > k || k1 == -1)) {
                    s1 = s1 + s2.substring(j, i)
                            + "<a style=\"word-wrap:break-word\" HREF="
                            + s2.substring(i, k) + " target=_blank>"
                            + s2.substring(i, k) + "</a>";
                    j = k;
                } else {
                    k = s2.indexOf(RMDCommonConstants.BLANK_SPACE, i);
                    if (k != -1)
                        s1 = s1 + s2.substring(j, i)
                                + "<a style=\"word-wrap:break-word\" HREF="
                                + s2.substring(i, k) + " target=_blank>"
                                + s2.substring(i, k) + "</a>";
                    else
                        s1 = s1 + s2.substring(j, i)
                                + "<a style=\"word-wrap:break-word\" HREF="
                                + s2.substring(i) + " target=_blank>"
                                + s2.substring(i) + "</a>";
                    j = k;
                }
            } else {
                s1 = s1 + s2.substring(j);
            }
        }
        return s1;
    }

    /**************** *************************ADD RX ******************************/

    /**
     * @Author:
     * @return:List<SolutionBean>
     * @param String
     *            caseObjid, String language.
     * @throws RMDBOException
     * @Description:This method is used for fetching the selected
     *                   solutions/Recommendations for a given Case.
     */
    @Override
    public List<SolutionBean> getSolutionsForCase(String caseObjid,
            String language) throws RMDDAOException {
        Session session = null;
        StringBuilder selectedSolQuery = new StringBuilder();
        List<SolutionBean> caseDetailsVOsList = new ArrayList<SolutionBean>();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            selectedSolQuery
                    .append("SELECT TO_CHAR(SDCASE.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),SDRECOM.OBJID, SDRECOM.TITLE, SDCASE.URGENCY, SDCASE.EST_REPAIR_TIME,SDCASE.VERSION,");
            selectedSolQuery
                    .append("SDCASE.OB_MSG_ID, SDCASE.REISSUE_FLAG, TO_CHAR(SDDELV.DELV_DATE,'MM/DD/YYYY HH24:MI:SS'),");
            selectedSolQuery
                    .append("SDRECOM.RECOM_TYPE FROM GETS_SD_RECOM SDRECOM,GETS_SD_RECOM_DELV SDDELV,");
            selectedSolQuery
                    .append("GETS_SD_CASE_RECOM SDCASE WHERE SDCASE.CASE_RECOM2RECOM_DELV  = SDDELV.OBJID  ");
            selectedSolQuery
                    .append("(+) AND SDCASE.CASE_RECOM2RECOM = SDRECOM.OBJID AND SDCASE.CASE_RECOM2CASE =:caseObjId ORDER BY 1 ASC");
            Query selectedSolHQry = session.createSQLQuery(selectedSolQuery
                    .toString());
            selectedSolHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    caseObjid);
            selectedSolHQry.setFetchSize(10);
            List<Object[]> selectedSolutions = selectedSolHQry.list();
            for (Object[] objCurrentSol : selectedSolutions) {
                SolutionBean objCaseDetailsVO = new SolutionBean();
                if (null != RMDCommonUtility
                        .convertObjectToString(objCurrentSol[0])) {
                    Date creationDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(objCurrentSol[0]));
                    objCaseDetailsVO.setCreationDate(creationDate);
                }
                objCaseDetailsVO.setRxObjId(RMDCommonUtility
                        .convertObjectToLong(objCurrentSol[1]));
                objCaseDetailsVO.setTitle(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[2]));
                objCaseDetailsVO.setUrgency(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[3]));
                objCaseDetailsVO.setRepairTime(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[4]));
                objCaseDetailsVO.setRevisionNo(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[5]));
                objCaseDetailsVO.setMessageObjId(RMDCommonUtility
                        .convertObjectToLong(objCurrentSol[6]));
                objCaseDetailsVO.setReIssue(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[7]));
                if (null != RMDCommonUtility
                        .convertObjectToString(objCurrentSol[8])) {
                    Date deliveryDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(objCurrentSol[8]));
                    objCaseDetailsVO.setDeliveryDate(deliveryDate);
                }
                objCaseDetailsVO.setRxType(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[9]));
                caseDetailsVOsList.add(objCaseDetailsVO);
            }

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getSolutionsForCase",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            language), e, RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return caseDetailsVOsList;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method in CaseResource.java is used to add a
     *               recommendation to a given Case.
     * 
     */
    @Override
    public String addRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        StringBuilder addRxQuery = new StringBuilder();
        try {
            session = getHibernateSession();
            addRxQuery
                    .append("INSERT INTO GETS_SD_CASE_RECOM(OBJID,LAST_UPDATED_BY,CREATED_BY,LAST_UPDATED_DATE,");
            if (!RMDCommonUtility.isNullOrEmpty(objRecomDelvInfoServiceVO
                    .getRxDetailsVO().getStrVersion())) {
                addRxQuery
                        .append("CREATION_DATE,CASE_RECOM2CASE,CASE_RECOM2RECOM,EST_REPAIR_TIME,URGENCY,VERSION) VALUES");
                addRxQuery
                        .append("(GETS_SD_CASE_RECOM_SEQ.NEXTVAL,:user,:user,SYSDATE,SYSDATE,:caseObjId,:rxObjid,:estRepairTime,:urgency,:version)");
            } else {
                addRxQuery
                        .append("CREATION_DATE,CASE_RECOM2CASE,CASE_RECOM2RECOM,EST_REPAIR_TIME,URGENCY) VALUES");
                addRxQuery
                        .append("(GETS_SD_CASE_RECOM_SEQ.NEXTVAL,:user,:user,SYSDATE,SYSDATE,:caseObjId,:rxObjid,:estRepairTime,:urgency)");
            }
            Query addRxHqry = session.createSQLQuery(addRxQuery.toString());
            addRxHqry.setParameter("user",
            		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,objRecomDelvInfoServiceVO.getStrUserName()));
            addRxHqry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
            		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,objRecomDelvInfoServiceVO.getCaseObjId()));
            addRxHqry.setParameter("rxObjid",
            		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,objRecomDelvInfoServiceVO.getStrRxObjId()));
            addRxHqry.setParameter("estRepairTime",
            		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,objRecomDelvInfoServiceVO.getRxDetailsVO().getEstRepTime()));
            addRxHqry.setParameter("urgency",
            		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,objRecomDelvInfoServiceVO.getRxDetailsVO().getUrgency()));
            if (!RMDCommonUtility.isNullOrEmpty(objRecomDelvInfoServiceVO
                    .getRxDetailsVO().getStrVersion())) {
                addRxHqry
                        .setParameter("version",
                                objRecomDelvInfoServiceVO.getRxDetailsVO()
                                        .getVersion());
            }
            addRxHqry.executeUpdate();
            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl addRxToCase()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method in CaseResource.java is used to delete a
     *               recommendation to a given Case.
     * 
     */
    @Override
    public String deleteRxToCase(
            RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)
            throws RMDDAOException {

        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        StringBuilder deleteRxQuery = new StringBuilder();
        try {
            session = getHibernateSession();
            deleteRxQuery
                    .append("DELETE FROM GETS_SD_CASE_RECOM WHERE CASE_RECOM2RECOM=:rxObjid  AND CASE_RECOM2CASE=:caseObjId");
            Query deleteRxHqry = session.createSQLQuery(deleteRxQuery
                    .toString());
            deleteRxHqry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    objRecomDelvInfoServiceVO.getCaseObjId());
            deleteRxHqry.setParameter(RMDCommonConstants.RX_OBJID,
                    objRecomDelvInfoServiceVO.getRxObjId());
            deleteRxHqry.executeUpdate();
            result = RMDCommonConstants.SUCCESS;

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl addRxToCase()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseInfoServiceVO
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the case Information.It
     *               accepts caseId as an Input Parameter and returns caseBean
     *               List.
     * 
     */
    @Override
    public CaseInfoServiceVO getCaseInfo(String caseId, String language)
            throws RMDDAOException {
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder caseInfoQuery = new StringBuilder();
        Future<String> futureServices = null;
        Future<String> futureRecPendingAlert = null;
        Future<String> futurependingFaults = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        CaseInfoServiceVO objServiceVO = new CaseInfoServiceVO();
        try {
            session = getHibernateSession();
            caseInfoQuery
                    .append("SELECT CASE_OBJID, ID_NUMBER, ORG_ID, NAME, VEH_HDR,SERIAL_NO,");
            caseInfoQuery
                    .append("CASE_TITLE, PRIORITY, TO_CHAR(CREATION_TIME,'mm/dd/yyyy hh24:mi:ss') CREATION_TIME,");
            caseInfoQuery
                    .append("CASE_TYPE, VEH_HDR_OBJID,MODEL_NAME, FLEET_NUMBER, GPS_LATITUDE,");
            caseInfoQuery
                    .append("GPS_LONGITUDE,GPS_HEADING,BAD_ACTOR,VEHICLE_OBJID,TO_CHAR(TOOL_RUN_NEXT,'mm/dd/yyyy hh24:mi:ss')");
            caseInfoQuery
                    .append("TOOL_RUN_NEXT,TO_CHAR(APPENDED_DT_TM,'mm/dd/yyyy hh24:mi:ss') APPENDED_DT_TM, CTRLCFG, MSA,");
            caseInfoQuery
                    .append("LMS_ID ,VEH_HDR_CUST FROM GETS_SD_CASE_HDR_V WHERE ID_NUMBER =:caseId");
            hibernateQuery = session.createSQLQuery(caseInfoQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_ID, caseId);
            hibernateQuery.setFetchSize(1);
            List<Object[]> caseInfoList = hibernateQuery.list();
            for (Object[] currentCaseInfoObj : caseInfoList) {
                objServiceVO.setCaseObjId(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[0]));
                objServiceVO.setCaseNumber(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[1]));
                objServiceVO.setCustomerName(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[3]));
                objServiceVO.setOnBoardRNH(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[4]));
                objServiceVO.setRoadNumber(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[5]));
                objServiceVO.setCaseTitle(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[6]));
                objServiceVO.setCasePriority(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[7]));
                if (null != RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[8])) {
                    Date creationDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(currentCaseInfoObj[8]));
                    objServiceVO.setDtCreateDate(creationDate);
                }
                objServiceVO.setCreationDate(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[8]));
                objServiceVO.setCaseType(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[9]));
                objServiceVO.setModel(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[11]));
                objServiceVO.setFleet(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[12]));
                objServiceVO.setGpsLatitude(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[13]));
                objServiceVO.setGpslongitude(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[14]));
                objServiceVO.setGpsHeading(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[15]));
                objServiceVO.setBadActor(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[16]));
                objServiceVO.setVehicleObjId(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[17]));
                if (null != RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[18])) {
                    Date nextScheduledRun =  formatter
                            .parse(RMDCommonUtility
                                    .convertObjectToString(currentCaseInfoObj[18]));
                    objServiceVO.setNextScheduledRun(nextScheduledRun);
                }
                if (null != RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[19])) {
                    Date appendedDate =  formatter.parse(RMDCommonUtility
                            .convertObjectToString(currentCaseInfoObj[19]));
                    objServiceVO.setAppendedDate(appendedDate);
                }
                objServiceVO.setControllerConfig(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[20]));
                objServiceVO.setCustomerRNH(RMDCommonUtility
                        .convertObjectToString(currentCaseInfoObj[23]));
            }
            if (!RMDCommonUtility.isNullOrEmpty(objServiceVO.getVehicleObjId())
                    && !RMDCommonUtility.isNullOrEmpty(objServiceVO
                            .getCaseObjId())) {

                futureServices = getServices(objServiceVO.getVehicleObjId(),
                        session);
                futureRecPendingAlert = getRecPending(
                        objServiceVO.getVehicleObjId(),
                        objServiceVO.getCaseObjId(), session);
                futurependingFaults = getPendingFaults(
                        objServiceVO.getVehicleObjId(), session);

                if (!RMDCommonUtility.checkNull(futureServices)) {
                    objServiceVO.setServices(futureServices.get());
                }

                if (!RMDCommonUtility.checkNull(futureRecPendingAlert)) {
                    objServiceVO
                            .setRecPendingAlert(futureRecPendingAlert.get());
                }

                if (!RMDCommonUtility.checkNull(futurependingFaults)) {
                    objServiceVO.setPendingFaults(futurependingFaults.get());
                }
            }

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getCaseInfo()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            language), e, RMDCommonConstants.MAJOR_ERROR);

        } finally {

            releaseSession(session);

        }
        return objServiceVO;
    }

    /**
     * @Author:
     * @param :String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the Services it takes
     *               vehicleObjId Input Parameter and returns services.
     * 
     */
    @Override
    public Future<String> getServices(String vehicleObjId, Session session)
            throws RMDDAOException {              
		String services;
        Connection connection = null;
        CallableStatement callableStmt = null;
        String servicesAddedString = null;
        StringBuilder servicesQuery = new StringBuilder();
        StringBuilder servicesAddedStr = new StringBuilder();
        try {
            connection = getConnection(session);
            callableStmt = connection.prepareCall("CALL gets_rmd_rnservice_pkg.mq_service_type_pr(?, ?)");
            callableStmt.setInt(1, Integer.parseInt(vehicleObjId));
            callableStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStmt.execute();
            services = callableStmt.getString(2);
            callableStmt.close();
            connection.close();
            
            servicesQuery.append("SELECT DISTINCT application_name ");
            servicesQuery.append("FROM   gets_mcs.mcs_asset_application ass_app, ");
            servicesQuery.append("       gets_mcs.mcs_application app, ");
            servicesQuery.append("       gets_rmd_vehcfg vehcfg, ");
            servicesQuery.append("       gets_rmd_master_bom mbom, ");
            servicesQuery.append("       gets_rmd_vehicle veh ");
            servicesQuery.append("WHERE  vehcfg2master_bom = mbom.objid ");
            servicesQuery.append("       AND veh.objid = :vehicleObjId ");
            servicesQuery.append("       AND veh.objid = vehcfg.veh_cfg2vehicle ");
            servicesQuery.append("       AND mbom.bom_status = 'Y' ");
            servicesQuery.append("       AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg ");
            servicesQuery.append("       AND ( ( mbom.config_item = 'CMU' ");
            servicesQuery.append("               AND vehcfg.current_version = '2' ) ");
            servicesQuery.append("              OR ( mbom.config_item = 'HPEAP' ");
            servicesQuery.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery.append("              OR ( mbom.config_item = 'LCV' ");
            servicesQuery.append("                   AND vehcfg.current_version = '1' ) ");
            servicesQuery.append("              OR ( mbom.config_item = 'LIG' ");
            servicesQuery.append("                   AND vehcfg.current_version = '1' ) ) ");
            servicesQuery.append("       AND ass_app.asset_objid = veh.vehicle2asset_objid ");
            servicesQuery.append("       AND app.objid = ass_app.application_objid ");
            
            Query servicesHQuery = session.createSQLQuery(servicesQuery.toString());
            servicesHQuery.setParameter(RMDCommonConstants.VEHICLE_OBJ_ID ,vehicleObjId);
            List<String> servicesAddedList = servicesHQuery.list();
            if (null != servicesAddedList && !servicesAddedList.isEmpty()) {
            for(String servicesToAdd : servicesAddedList){
              servicesAddedStr.append(servicesToAdd);
              servicesAddedStr.append(SEPARATOR);
             } 
            servicesAddedString = servicesAddedStr.toString();
            servicesAddedString = servicesAddedString.substring(0, servicesAddedString.length() - SEPARATOR.length());
            }
              
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getServices()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SERVICES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } if (servicesAddedString != null && !servicesAddedString.isEmpty()) {
        	if (services != null && !services.isEmpty()){
				return new AsyncResult<String>(services + "," + servicesAddedString);
			} else {
				return new AsyncResult<String> (servicesAddedString);
			}
        } else {
        	return new AsyncResult<String>(services);
        }
      }

    /**
     * @Author:
     * @param :String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pending faults it takes
     *               vehicleObjId and caseObjId as Input Parameters and returns
     *               pendingFaults.
     * 
     */
    @Override
    public Future<String> getPendingFaults(String vehicleObjId, Session session)
            throws RMDDAOException {

        String pendingFaults = null;
        StringBuilder pendingFaultsQry = new StringBuilder();
        try {
            pendingFaultsQry
                    .append("SELECT COUNT(OBJID) PENDING_FAULTS FROM GETS_TOOL_FAULT WHERE OBJID >");
            pendingFaultsQry
                    .append("(SELECT MAX(RUN_PROCESSEDMAX_OBJID) FROM GETS_TOOL_RUN GTR WHERE GTR.RUN_CPT IS NOT NULL AND GTR.RUN2VEHICLE=:vehicleObjid)");
            pendingFaultsQry
                    .append("AND FAULT2VEHICLE=:vehicleObjid AND RECORD_TYPE != 'HC'");
            Query pendingFaultsHqry = session.createSQLQuery(pendingFaultsQry
                    .toString());
            pendingFaultsHqry.setParameter(RMDCommonConstants.VEHICLE_OBJID,
                    vehicleObjId);
            pendingFaultsHqry.setFetchSize(1);
            List<BigDecimal> pendingFaultsList = pendingFaultsHqry.list();
            if (null != pendingFaultsList && !pendingFaultsList.isEmpty()) {
                pendingFaults = pendingFaultsList.get(0).toString();
            }

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getPendingFaults()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PENDING_FAULTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        }
        return new AsyncResult<String>(pendingFaults);
    }

    /**
     * @Author:
     * @param :String vehicleObjId, String caseObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pending records it takes
     *               vehicleObjId and caseObjId as Input Parameters and returns
     *               pendingFaults.
     * 
     */
    @Override
    public Future<String> getRecPending(String vehicleObjId, String caseObjId,
            Session session) throws RMDDAOException {

        String recPending = null;
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        try {
            objConnection = getConnection(session);
            callableStmt = objConnection
                    .prepareCall("BEGIN gets_sd_rec_pend_pkg.pending_alert_pr(?,?,?,?); END;");
            callableStmt.setString(1, vehicleObjId);
            callableStmt.setString(2, caseObjId);
            callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            callableStmt.execute();
            recPending = callableStmt.getString(3);
            callableStmt.close();
            objConnection.close();
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getRecPending()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_REC_PENDING);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        }
        return new AsyncResult<String>(recPending);
    }

    public String getLookUpValue(String listName) {
        String lookupValue = RMDCommonConstants.EMPTY_STRING;
        try {
        	List<GetSysLookupVO> arlSysLookUp = objpopupListAdminDAO.getPopupListValues(listName);
            if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
                for (GetSysLookupVO getSysLookupVO : arlSysLookUp) {
                    lookupValue = getSysLookupVO.getLookValue();
                }
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return lookupValue;
    }

    /**
     * @Author:
     * @param:caseId,userName
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method reopens case by invoking
     *               caseeoadaoimpl.reOpenCase() method.
     */
    @Override
    public String reOpenCase(String caseID, String userName)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        try {
            CaseContent caseContent = caseServiceAPI.getCase(caseID);
            User user = getUser(userName);
            if (null != caseContent && null != user) {
                caseServiceAPI.reOpenCase(caseContent, user);
                result = RMDCommonConstants.SUCCESS;
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.REOPEN_CASE_RTU_ERROR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return result;
    }

    /*Start of Changes for OMD Slowness Issue*/
    
    /**
     * @Author :
     * @return :List<RxStatusHistoryVO>
     * @param :serviceReqId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching RxStatus History based on
     *                   servicerReq id.
     */
    @Override
	public List<RxStatusHistoryVO> getRxstatusHistory(String serviceReqId)
            throws RMDDAOException {
        List<RxStatusHistoryVO> rxStatusHistoryVOList = new ArrayList<RxStatusHistoryVO>();
        Session session = null;
        Connection connection = null;
        CallableStatement callstmt = null;
        ResultSet resultSet = null;
        RxStatusHistoryVO objRxStatusHistoryDetails;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
        try {
            if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                session = getHibernateSession();
                connection = getConnection(session);
                callstmt = connection
                        .prepareCall("{? = call GETS_SD_LMS_Fdbk_PKG.getStatusDetails(?,?,?)}");
                callstmt.registerOutParameter(1, OracleTypes.CURSOR);
                callstmt.setInt(2, Integer.parseInt(serviceReqId));
                callstmt.registerOutParameter(3, java.sql.Types.INTEGER);
                callstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
                callstmt.execute();
                resultSet = (ResultSet) callstmt.getObject(1);
                int errCode = callstmt.getInt(3);
                String errMsg = callstmt.getString(4);
                if (errCode != 0) {
                    throw new Exception(errMsg);
                }
                while (resultSet.next()) {
                    objRxStatusHistoryDetails = new RxStatusHistoryVO();
                    if (null != resultSet.getString(1)) {
                        Date statusDate = formatter.parse(resultSet
                                .getString(1));
                        objRxStatusHistoryDetails.setRxStatusDate(statusDate);
                    }
                    objRxStatusHistoryDetails.setStatus(resultSet.getString(2));
                    objRxStatusHistoryDetails.setComments(resultSet
                            .getString(3));
                    rxStatusHistoryVOList.add(objRxStatusHistoryDetails);
                }
                
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_STATUS_HISTORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_STATUS_HISTORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            try {
                if(null != resultSet)
                    resultSet.close();
            } catch (SQLException e) {
                LOG.debug("Exception occured while clsoing the ResultSet in getRxstatusHistory() ", e);
            }
            try {
                if(null != callstmt)
                    callstmt.close();
            } catch (SQLException e) {
                LOG.debug("Exception occured while clsoing the CallableStatement in getRxstatusHistory() ", e);
            }
            try {
                if(null != connection)
                    connection.close();
            } catch (SQLException e) {
                LOG.debug("Exception occured while clsoing the Connection in getRxstatusHistory() ", e);
            }
            releaseSession(session);
        }
        return rxStatusHistoryVOList;
    }

    /*End of Changes for OMD Slowness Issue*/
    
    /**
     * @Author :
     * @return :List<RxHistoryVO>
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching Rx History based on caseObj
     *                   id.
     */
    @Override
	public List<RxHistoryVO> getRxHistory(String caseObjId)
            throws RMDDAOException {
        List<RxHistoryVO> rxHistoryVOList = null;
        List<Object[]> resultList = null;
        Session session = null;
        RxHistoryVO objRxHistoryDetails;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append("SELECT OBJID , RX_CASE_ID , CASE_SUCCESS,MISS_CODE, ACCU_RECOMM,RF_CODE,TRUNC((RX_CLOSE_DATE - RX_OPEN_DATE),2) AS DAYS_TO_REPAIR,TRUNC((RF_OCCUR_DATE - RX_OPEN_DATE),2) AS DAYS_TO_FAIL,FRAC_UTIL,");
            caseQry.append("TO_CHAR(RX_CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS') AS RX_CLOSE_DATE ,RX_CLOSE_SUBCODE,CUST_FDBK2CASE ,SERVICE_REQ_ID,SERVICE_REQ_ID_STATUS, GOOD_FDBK ,CREATION_DATE,RX_FDBK  FROM GETS_SD_CUST_FDBK WHERE CUST_FDBK2CASE=:caseObjId ORDER BY OBJID ASC");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASE_OBJ_ID, caseObjId);
            caseHqry.setFetchSize(10);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                rxHistoryVOList = new ArrayList<RxHistoryVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj.hasNext();) {
                    objRxHistoryDetails = new RxHistoryVO();
                    final Object[] rxHistoryDetails =  obj.next();
                    objRxHistoryDetails.setCustFdbkObjId(RMDCommonUtility.convertObjectToString(rxHistoryDetails[0]));
                    objRxHistoryDetails.setRxCaseId(RMDCommonUtility.convertObjectToString(rxHistoryDetails[1]));
                    objRxHistoryDetails.setRxFeedback(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(rxHistoryDetails[16])));
                    objRxHistoryDetails.setRxSuccess(RMDCommonUtility.convertObjectToString(rxHistoryDetails[2]));
                    objRxHistoryDetails.setMissCode(RMDCommonUtility.convertObjectToString(rxHistoryDetails[3]));
                    objRxHistoryDetails.setGoodFeedback(RMDCommonUtility.convertObjectToString(rxHistoryDetails[14]));
                    objRxHistoryDetails.setServiceRequestId(RMDCommonUtility.convertObjectToString(rxHistoryDetails[12]));
                    if (null != RMDCommonUtility.convertObjectToString(rxHistoryDetails[9])) {
                        Date closeDate = (Date) formatter.parse(RMDCommonUtility.convertObjectToString(rxHistoryDetails[9]));
                        objRxHistoryDetails.setRxCloseDate(closeDate);
                    }
                    rxHistoryVOList.add(objRxHistoryDetails);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_HISTORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_HISTORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return rxHistoryVOList;
    }

    /**
     * @Author :
     * @return :List<CustomerFdbkVO>
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching the ServiceReqId &
     *                   CustFdbkObjId based on caseObj id.
     */
    @Override
	public List<CustomerFdbkVO> getServiceReqId(String caseObjId)
            throws RMDDAOException {
        Session session = null;
        List<CustomerFdbkVO> serviceRqIdList = null;
        List<Object[]> resultList = null;
        CustomerFdbkVO objCustomerFdbkVODetails;
        try {
            session = getHibernateSession();
            String query = "SELECT OBJID,SERVICE_REQ_ID FROM GETS_SD_CUST_FDBK WHERE CUST_FDBK2CASE = :caseObjId ORDER BY OBJID DESC";
            Query hibernateQuery = session.createSQLQuery(query);
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    caseObjId);
            hibernateQuery.setFetchSize(10);
            resultList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                serviceRqIdList = new ArrayList<CustomerFdbkVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    objCustomerFdbkVODetails = new CustomerFdbkVO();
                    final Object[] custFdbkDetails = (Object[]) obj.next();
                    objCustomerFdbkVODetails.setCustFdbkObjId(RMDCommonUtility
                            .convertObjectToString(custFdbkDetails[0]));
                    objCustomerFdbkVODetails.setServiceReqId(RMDCommonUtility
                            .convertObjectToString(custFdbkDetails[1]));
                    serviceRqIdList.add(objCustomerFdbkVODetails);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SERVICE_RQ_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SERVICE_RQ_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return serviceRqIdList;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching the Closure Feedback based
     *                   on rxCaseId id.
     */
    @Override
	public String getClosureFdbk(String rxCaseId) throws RMDDAOException {
        String objClosureFdbk = null;
        Session session = null;
        List<RxHistoryVO> rxNotesList = null;
        RxHistoryVO objRxNotesDetails = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT OBJID,GOOD_FDBK FROM GETS_SD_CUST_FDBK WHERE RX_CASE_ID= :rxCaseId ORDER BY OBJID DESC");
            Query hibernateQuery = session.createSQLQuery(caseQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.RXCASEID1, rxCaseId);
            List<Object[]> resultList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                rxNotesList = new ArrayList<RxHistoryVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    objRxNotesDetails = new RxHistoryVO();
                    final Object[] rxHistoryDetails =  obj.next();
                    objRxNotesDetails.setGoodFeedback(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(RMDCommonUtility
                            .convertObjectToString(rxHistoryDetails[1]))));
                    rxNotesList.add(objRxNotesDetails);
                }
                objRxNotesDetails = rxNotesList.get(0);
                objClosureFdbk = objRxNotesDetails.getGoodFeedback();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_GOOD_FDBK);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_GOOD_FDBK);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return objClosureFdbk;
    }

    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :custFdbkObjId , serviceReqId
     * @throws :RMDDAOException
     * @Description:This method is used fetching the CloseOut Repair Codes based
     *                   on custFdbkObj id & serviceReqId.
     */
    @Override
	public List<CloseOutRepairCodeVO> getCloseOutRepairCode(
            String custFdbkObjId, String serviceReqId) throws RMDDAOException {
        Session session = null;
        List<CloseOutRepairCodeVO> closeOutRepairCodeVOList = null;
        List<Object[]> resultList = null;
        CloseOutRepairCodeVO objCloseOutRepairCodeDetails;
        try {
            if (!RMDCommonUtility.isNullOrEmpty(custFdbkObjId)) {
                session = getHibernateSession();
                StringBuilder caseQry = new StringBuilder();
                caseQry.append(" SELECT t.TASK_ID,t.TASK_DESC,RC.OBJID,t.REPAIR_CODE,t.REPAIR_DESC ");
                if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                    caseQry.append(" ,SRTASK.PERFORMED_FLAG, t.TASK_OBJID ");
                }
                caseQry.append(" FROM (SELECT gtask.task_id AS TASK_ID, ");
                        caseQry.append(" gtask.task_desc AS TASK_DESC, ");
                        caseQry.append(" grepair.repair_code AS REPAIR_CODE, ");
                        caseQry.append(" grepair.repair_desc AS REPAIR_DESC, ");
                        caseQry.append(" gtask.objid AS TASK_OBJID ");
                        caseQry.append(" FROM gets_sd_fdbkpndg_v gfeedback, ");
                        caseQry.append(" gets_sd_recom_task gtask, ");
                        caseQry.append(" gets_sd_repair_codes grepair ");
                        caseQry.append(" WHERE gfeedback.recom_objid   = gtask.recom_Task2recom ");
                        caseQry.append(" AND gtask.recom_task2rep_code = grepair.objid (+) ");
                        caseQry.append(" AND gfeedback.cust_fdbk_objid = :custFdbkObjId ) t ");
                caseQry.append(" LEFT OUTER JOIN (SELECT OBJID ,REPAIR_CODE FROM GETS_SD_REPAIR_CODES) RC ");
                caseQry.append(" on  t.REPAIR_CODE = RC.REPAIR_CODE ");
                if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                    caseQry.append(" LEFT OUTER JOIN (SELECT PERFORMED_FLAG ,RX_TASK_ID FROM  ");
                    caseQry.append(" GETS_LMS_RMD_SR_TASKS_V@ESERVICES.WORLD    WHERE  SERVICE_REQUEST_ID = :serviceReqId) SRTASK ");
                    caseQry.append(" on  t.TASK_ID = SRTASK.RX_TASK_ID ");
                }
                caseQry.append(" ORDER BY TO_NUMBER(REGEXP_SUBSTR(t.TASK_ID, '[^.]+', 1, 1)) NULLS FIRST,");
                caseQry.append(" TO_NUMBER(REGEXP_SUBSTR(t.TASK_ID, '[^.]+', 1, 2)) NULLS FIRST,");
                caseQry.append(" TO_NUMBER(REGEXP_SUBSTR(t.TASK_ID, '[^.]+', 1, 3)) NULLS FIRST ");
                Query caseHqry = session.createSQLQuery(caseQry.toString());
                caseHqry.setParameter(RMDCommonConstants.GET_CUST_FDBK_OBJID,
                        custFdbkObjId);
                if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                    caseHqry.setParameter(RMDCommonConstants.SERVICE_REQ_ID,
                            serviceReqId);
                }
                caseHqry.setFetchSize(10);
                resultList = caseHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                    closeOutRepairCodeVOList = new ArrayList<CloseOutRepairCodeVO>();
                    for (final Iterator<Object[]> obj = resultList.iterator(); obj
                            .hasNext();) {
                        objCloseOutRepairCodeDetails = new CloseOutRepairCodeVO();
                        final Object[] closeOutRepairCodeDetails =  obj
                                .next();
                        objCloseOutRepairCodeDetails
                                .setId(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[0]));
                        objCloseOutRepairCodeDetails
                                .setTask(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[1]));
                        if (RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                            objCloseOutRepairCodeDetails.setFeedback("");
                        } else {
                            objCloseOutRepairCodeDetails
                                    .setFeedback(RMDCommonUtility
                                            .convertObjectToString(closeOutRepairCodeDetails[5]));
                        }
                        objCloseOutRepairCodeDetails
                                .setRepairCode(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[3]));
                        objCloseOutRepairCodeDetails
                                .setDescription(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[4]));
                        objCloseOutRepairCodeDetails
                                .setRepairCodeId(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[2]));
                        if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)) {
                        	objCloseOutRepairCodeDetails.setRepairCodes(getTaskRepairCodes(RMDCommonUtility
                                .convertObjectToString(closeOutRepairCodeDetails[6])));
                        }
                        closeOutRepairCodeVOList
                                .add(objCloseOutRepairCodeDetails);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CLOSEOUT_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CLOSEOUT_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }finally {
            releaseSession(session);
        }
        return closeOutRepairCodeVOList;
    }
    
    private List<CaseRepairCodeVO> getTaskRepairCodes(String rxTaskObjId) {
		List<CaseRepairCodeVO> repairCodes = null;
		Session session = null;
		Query getRepairCodeQuery = null;
		String strGetRepairCodeQuery = "select TRC.RECOM_TASK_ID AS RECOM_TASK_ID, RC.REPAIR_CODE AS REPAIR_CODE, RC.REPAIR_DESC AS REPAIR_DESC, RC.OBJID AS REPAIRCODEOBJID from GETS_SD.GETS_SD_RECOM_TASK_REPAIR_CODE TRC JOIN GETS_SD.GETS_SD_REPAIR_CODES RC ON TRC.REPAIR_CODE_ID = RC.OBJID WHERE TRC.RECOM_TASK_ID = :recomTaskObjid";
		try{
			session = getHibernateSession();
			getRepairCodeQuery = session.createSQLQuery(strGetRepairCodeQuery);
			getRepairCodeQuery.setParameter(RMDServiceConstants.RECOM_TASK_OBJID, rxTaskObjId);
			getRepairCodeQuery.setResultTransformer(AliasToEntityMapResultTransformerUtil.getInstance());
			List<Map<String,Object>> repairCodeResult = getRepairCodeQuery.list();
			if(RMDCommonUtility.isCollectionNotEmpty(repairCodeResult)) {
				repairCodes = new ArrayList<CaseRepairCodeVO>();
				for(Map<String, Object> repairCode : repairCodeResult) {
					CaseRepairCodeVO taskRepairCode = new CaseRepairCodeVO(RMDCommonUtility.convertObjectToString(repairCode.get("REPAIR_CODE")),
							RMDCommonUtility.convertObjectToString(repairCode.get("REPAIR_DESC")), 
							RMDCommonUtility.convertObjectToString(repairCode.get("REPAIRCODEOBJID")));
					repairCodes.add(taskRepairCode);
				}
			}
		} catch (Exception e) {
			LOG.error("SQL Exception in getRepairCodes of CaseEoaDAOImpl", e);
		} finally {
			releaseSession(session);
		}
		return repairCodes;
	}

    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :caseId
     * @throws :RMDDAOException
     * @Description:This method fetches the Attached Details based on case id.
     */
    @Override
	public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId)
            throws RMDDAOException {
        Session session = null;
        List<CloseOutRepairCodeVO> attachedDetailsList = null;
        List<Object[]> resultList = null;
        CloseOutRepairCodeVO objattachedDetailsList;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT RC.OBJID, RC.REPAIR_CODE, RC.REPAIR_DESC ");
            caseQry.append(" FROM   GETS_SD_REPAIR_CODES RC, TABLE_CASE TC, GETS_SD_FINAL_REPCODE FRP ");
            caseQry.append(" WHERE  TC.ID_NUMBER = :caseId ");
            caseQry.append(" AND    TC.OBJID = FRP.FINAL_REPCODE2CASE ");
            caseQry.append(" AND    FRP.FINAL_REPCODE2REPCODE = RC.OBJID ");
            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setFetchSize(10);
            caseHqry.setParameter(RMDCommonConstants.CASEID, caseId);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                attachedDetailsList = new ArrayList<CloseOutRepairCodeVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    objattachedDetailsList = new CloseOutRepairCodeVO();
                    final Object[] closeOutRepairCodeDetails = (Object[]) obj
                            .next();
                    objattachedDetailsList
                            .setId(RMDCommonUtility
                                    .convertObjectToString(closeOutRepairCodeDetails[0]));
                    objattachedDetailsList
                            .setRepairCode(RMDCommonUtility
                                    .convertObjectToString(closeOutRepairCodeDetails[1]));
                    objattachedDetailsList
                            .setDescription(RMDCommonUtility
                                    .convertObjectToString(closeOutRepairCodeDetails[2]));
                    attachedDetailsList.add(objattachedDetailsList);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ATTACHED_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return attachedDetailsList;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching the Rx Notes based on
     *                   caseObj id.
     */
    @Override
	public String getRxNote(String caseObjId) throws RMDDAOException {
        List<RxHistoryVO> rxNotesList = null;
        Session session = null;
        RxHistoryVO objRxNotesDetails = null;
        String rxNote = null;
        List<CaseHistoryVO> caseHistoryVOList = null;
        CaseHistoryVO objCaseHistoryVO;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT TITLE, TO_CHAR(ENTRY_TIME,'MM/DD/YYYY HH24:MI:SS'),LOGIN_NAME,ADDNL_INFO,ACTION_TYPE ");
            caseQry.append(" FROM TABLE_CASE_ACT_HISTORY ");
            caseQry.append(" WHERE ACT_ENTRY2CASE = :caseObjId ");
            caseQry.append(" AND TITLE='Notes' ORDER BY ENTRY_TIME DESC ");
            Query hibernateQuery = session.createSQLQuery(caseQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    caseObjId);
            List<Object[]> resultList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                caseHistoryVOList = new ArrayList<CaseHistoryVO>();
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    objCaseHistoryVO = new CaseHistoryVO();
                    final Object[] caseHome =  obj.next();
                    objCaseHistoryVO.setActivity(RMDCommonUtility
                            .convertObjectToString(caseHome[0]));
                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[1])) {
                        Date creationDate = formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[1]));
                        objCaseHistoryVO.setCreateDate(creationDate);
                    }
                    objCaseHistoryVO.setUser(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[3])) {
                        objCaseHistoryVO
                                .setAddInfo(ESAPI
                                        .encoder()
                                        .encodeForXML(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(
                                                makeHttpFtpHyperlinks(RMDCommonUtility
                                                        .convertObjectToString(caseHome[3]))))));
                    }
                    objCaseHistoryVO.setActivityType(RMDCommonUtility
                            .convertObjectToString(caseHome[4]));
                    caseHistoryVOList.add(objCaseHistoryVO);
                }
                objCaseHistoryVO = caseHistoryVOList.get(0);
                rxNote = objCaseHistoryVO.getAddInfo();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_NOTE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_NOTE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return rxNote;
    }

    /**
     * @Author :
     * @return :String
     * @param :RecomDelvInfoServiceVO
     * @throws :RMDDAOException
     * @Description: To deliver an RX for a case. This function is using
     *               EOAAPPLGCYAPI for delivering recommendation
     */

    @Override
    public String deliverRX(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        Connection connection = null;
        RxMQConnection rxMQConnection = null;
        String queueName = null;
        try {
            session = getHibernateSession();
            try {
            	connection = getConnection(session);
                connection.setAutoCommit(false);
                LOG.info("Deliver Rx Params || userName : "+recomDelvInfoServiceVO.getStrUserName()+" || customerName : "
                +recomDelvInfoServiceVO.getCustomerName()+" || caseId : "+recomDelvInfoServiceVO.getStrCaseID() + " || rxObjId : "+recomDelvInfoServiceVO.getStrRxObjId());
                OpenRxExecutor openRxExecutor = new OpenRxExecutor(connection, EoaProperties.getEoaPropertiesInstance(), loadRxLogInfoVO(recomDelvInfoServiceVO, session));
                RxDeliveryInputVO rxDeliveryInputVO = loadRxDeliveryInputVO(recomDelvInfoServiceVO, openRxExecutor, RxDeliveryConstants.ACTION_TYPE_OPEN);
                RxDelvQueueInfoVO rxDelvQueueInfoVO = rxDeliveryInputVO.getRxDelvQueueInfo();
                rxMQConnection = rxDelvQueueInfoVO.getRxMQConn();
                queueName = rxDelvQueueInfoVO.getRxDelvQueueName();
                RxDeliveryOutputVO rxDeliveryOutputVO = openRxExecutor.executeDelivery(rxDeliveryInputVO);
            	if (rxDeliveryOutputVO.isRxDelvSuccess()) {
                    if (null != rxMQConnection) {
                    	rxMQConnection.commitRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                    }
                    connection.commit();
                    result = RMDCommonConstants.SUCCESS;
                } else {
                    if (null != rxMQConnection) {
                    	rxMQConnection.backoutRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                    }
                    connection.rollback();
                }
                
            } catch (Exception e) {
            	LOG.error(e, e);
                if(null != connection){
                    connection.rollback();
                }
                closeMQConnection(rxMQConnection, queueName);
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELIVER_RX_API);
                throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
            }
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELIVER_RX);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.debug(e.getMessage(), e);
            }
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param :RecomDelvInfoServiceVO
     * @throws :RMDDAOException
     * @Description :To deliver an RX for a case. This function is using
     *              EOAAPPLGCYAPI for delivering recommendation
     */
    @Override
    public String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        Connection connection = null;
        RxMQConnection rxMQConnection = null;
        String queueName = null;
        try {
            session = getHibernateSession();
            try {
                connection = getConnection(session);
                connection.setAutoCommit(false);
                LOG.info("Deliver Rx Params || userName : "+recomDelvInfoServiceVO.getStrUserName()+" || customerName : "
                        + recomDelvInfoServiceVO.getCustomerName()+" || caseId : "+recomDelvInfoServiceVO.getStrCaseID() 
                        + " || rxObjId : "+recomDelvInfoServiceVO.getStrRxObjId());
               
                ModifyRxExecutor modifyRxExecutor = new ModifyRxExecutor(connection, EoaProperties.getEoaPropertiesInstance(), loadRxLogInfoVO(recomDelvInfoServiceVO, session));
                RxDeliveryInputVO rxDeliveryInputVO = loadRxDeliveryInputVO(recomDelvInfoServiceVO, modifyRxExecutor, RxDeliveryConstants.ACTION_TYPE_MODIFY);
                RxDelvQueueInfoVO rxDelvQueueInfoVO = rxDeliveryInputVO.getRxDelvQueueInfo();
                rxMQConnection = rxDelvQueueInfoVO.getRxMQConn();
                queueName = rxDelvQueueInfoVO.getRxDelvQueueName();
                RxDeliveryOutputVO rxDeliveryOutputVO = modifyRxExecutor.executeDelivery(rxDeliveryInputVO);
            	if (rxDeliveryOutputVO.isRxDelvSuccess()) {
                    connection.commit();
                    if (null != rxMQConnection) {
                    	rxMQConnection.commitRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                    }
                    result = RMDCommonConstants.SUCCESS;
                } else {
                    if (null != rxMQConnection) {
                    	rxMQConnection.backoutRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                    }
                    connection.rollback();
                }
                
            } catch (Exception e) {
            	LOG.debug(e.getMessage(), e);
                connection.rollback();
                closeMQConnection(rxMQConnection, queueName);
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELIVER_RX_API);
                throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), null, RMDCommonConstants.MINOR_ERROR);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MODIFY_RX);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.debug(e.getMessage(), e);
            }
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param RecomDelvInfoServiceVO
     * @throws RMDDAOException
     * @Description To deliver an RX for a case. This function is using
     *              EOAAPPLGCYAPI for delivering recommendation
     */
    @Override
    public String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO)throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session session = null;
        Connection connection = null;
        RxMQConnection rxMQConnection = null;
        String queueName = null;
        try {
            session = getHibernateSession();
            try {
                connection = getConnection(session);
                connection.setAutoCommit(false);
                RxLogInfoVO rxLogInfoVO = loadRxLogInfoVO(recomDelvInfoServiceVO, session);
                DeleteRxExecutor deleteRxExecutor = new DeleteRxExecutor(connection, EoaProperties.getEoaPropertiesInstance(), rxLogInfoVO);
                RxDeliveryInputVO rxDeliveryInputVO = loadRxDeliveryInputVO(recomDelvInfoServiceVO, deleteRxExecutor, RxDeliveryConstants.ACTION_TYPE_DELETE);
                RxDelvQueueInfoVO rxDelvQueueInfoVO = rxDeliveryInputVO.getRxDelvQueueInfo();
                rxMQConnection = rxDelvQueueInfoVO.getRxMQConn();
                queueName = rxDelvQueueInfoVO.getRxDelvQueueName();
                RxDeliveryOutputVO closeDeliveryOutputVO =  deleteRxExecutor.executeDelivery(rxDeliveryInputVO);
               
                OpenRxExecutor openRxExecutor = new OpenRxExecutor(connection, EoaProperties.getEoaPropertiesInstance(), rxLogInfoVO);
                rxDeliveryInputVO.setActionType(RxDeliveryConstants.ACTION_TYPE_OPEN);
                RxDeliveryOutputVO openDeliveryOutputVO =  openRxExecutor.executeDelivery(rxDeliveryInputVO);
                
                if (closeDeliveryOutputVO.isRxDelvSuccess() && openDeliveryOutputVO.isRxDelvSuccess()) {
                    connection.commit();
                    connection.setAutoCommit(true);
                    if (null != rxMQConnection) {
                    	rxMQConnection.commitRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                        updateCustFdbk(String.valueOf(recomDelvInfoServiceVO.getFdbkObjId()),
                                        recomDelvInfoServiceVO.getStrUserName(), session);
                        CaseRepairCodeEoaVO objCaseRepairCodeEoaVO = new CaseRepairCodeEoaVO();
                        objCaseRepairCodeEoaVO.setCaseId(recomDelvInfoServiceVO.getStrCaseID());
                        objCaseRepairCodeEoaVO.setUserId(recomDelvInfoServiceVO.getUserId());
                        objCaseRepairCodeEoaVO.setAddAndClose(false);
                        objCaseRepairCodeEoaVO.setCaseObjId(Long.valueOf(recomDelvInfoServiceVO.getCaseObjId()));
                        objCaseRepairCodeEoaVO.setUserName(recomDelvInfoServiceVO.getStrUserName());
                        RepairCodeEoaDetailsVO objRepairCodeEoaDetailsVO = new RepairCodeEoaDetailsVO();
                        objRepairCodeEoaDetailsVO.setSearchValue(RMDCommonConstants.REPLACE_REPAIR_CODE);
                        List<RepairCodeEoaDetailsVO> arlRepairCodeList = getRepairCodes(objRepairCodeEoaDetailsVO, session);
                        List<String> repairCode = new ArrayList<String>();
                        if (null !=arlRepairCodeList && !arlRepairCodeList.isEmpty()) {
                            repairCode.add(arlRepairCodeList.get(0).getRepairCodeId());
                            objCaseRepairCodeEoaVO.setRepairCodeIdList(repairCode);
                            addCaseRepairCodes(objCaseRepairCodeEoaVO, session);
                        }
                    }
                    result = RMDCommonConstants.SUCCESS;
                } else {
                    if (null != rxMQConnection) {
                    	rxMQConnection.backoutRequest();
                    	closeMQConnection(rxMQConnection, queueName);
                    }
                    connection.rollback();
                }
                
                
            } catch (Exception e) {
            	LOG.debug(e.getMessage(), e);
                connection.rollback();
                closeMQConnection(rxMQConnection, queueName);
                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELIVER_RX_API);
                throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), null, RMDCommonConstants.MINOR_ERROR);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REPLACE_RX);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MINOR_ERROR);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.debug(e.getMessage(), e);
            }
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pendingFdbkServiceStatus
     *               it takes caseId as Input Parameters and returns
     *               pendingFdbkServiceStatus as a String.
     */
    @Override
    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(String caseId)
            throws RMDDAOException {
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder pendingFdbkServiceQry = new StringBuilder();
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = null;
        List<RecomDelvInfoServiceVO> arlDelvInfoEoaServiceVOs = new ArrayList<RecomDelvInfoServiceVO>();
        try {
            session = getHibernateSession();
            pendingFdbkServiceQry
                    .append("SELECT SERVICE_REQ_ID_STATUS,RECOM_OBJID FROM GETS_SD_FDBKPNDG_V WHERE ID_NUMBER=:caseId AND RX_CLOSE_DATE IS NULL");
            hibernateQuery = session.createSQLQuery(pendingFdbkServiceQry
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_ID, caseId);
            hibernateQuery.setFetchSize(10);
            List<Object[]> pendingFdbkList = hibernateQuery.list();
            for (Object[] currentObject : pendingFdbkList) {
                objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
                objDelvInfoEoaServiceVO.setPendingFeedBack(RMDCommonUtility
                        .convertObjectToString(currentObject[0]));
                objDelvInfoEoaServiceVO.setRxObjId(RMDCommonUtility
                        .convertObjectToLong(currentObject[1]));
                arlDelvInfoEoaServiceVOs.add(objDelvInfoEoaServiceVO);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PENDING_FDBK_SERVICE_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlDelvInfoEoaServiceVOs;
    }

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching ServiceReqId it takes
     *               fdbkObjid as Input Parameters and returns ServiceReqId as a
     *               String.
     */
    @Override
    public String getServiceReqIdStatus(String fdbkObjid)
            throws RMDDAOException {
        String serviceReqIdStatus = null;
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder serviceReqIdStatusQry = new StringBuilder();
        try {
            session = getHibernateSession();
            serviceReqIdStatusQry
                    .append("SELECT SERVICE_REQ_ID_STATUS FROM GETS_SD_FDBKPNDG_V WHERE CUST_FDBK_OBJID=:fdbkObjid AND RX_CLOSE_DATE IS NULL");
            hibernateQuery = session.createSQLQuery(serviceReqIdStatusQry
                    .toString());
            hibernateQuery.setParameter(RMDServiceConstants.FDBK_OBJID,
                    fdbkObjid);
            hibernateQuery.setFetchSize(1);
            List<Object> serviceReqList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(serviceReqList)) {
                serviceReqIdStatus = RMDCommonUtility
                        .convertObjectToString(serviceReqList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SERVICE_REQID_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return serviceReqIdStatus;
    }

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching DeliveryDate it takes
     *               caseObjid,rxObjid as Input Parameters and returns
     *               DeliveryDate as a String.
     */
    @Override
    public String getDelvDateForRx(String caseObjid, String rxObjid)
            throws RMDDAOException {
        String deliveryDate = null;
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder serviceReqIdStatusQry = new StringBuilder();
        try {
            session = getHibernateSession();
            serviceReqIdStatusQry
                    .append("SELECT TO_CHAR(DELV_DATE,'MM/DD/YY HH24:MI:SS') DELV_DATE FROM GETS_SD_RECOM_DELV ");
            serviceReqIdStatusQry
                    .append(" WHERE RECOM_DELV2CASE=:caseObjId and RECOM_DELV2RECOM=:rxObjId");
            hibernateQuery = session.createSQLQuery(serviceReqIdStatusQry
                    .toString());
            hibernateQuery.setParameter(RMDServiceConstants.CASE_OBJID,
                    caseObjid);
            hibernateQuery.setParameter(RMDServiceConstants.RX_OBJID, rxObjid);
            hibernateQuery.setFetchSize(1);
            List<Object> deliveryrDateList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(deliveryrDateList)) {
                deliveryDate = RMDCommonUtility
                        .convertObjectToString(deliveryrDateList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_DELV_DATE_FOR_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return deliveryDate;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching requestId it takes caseId
     *               as Input Parameter and returns getT2Req as a String
     */
    @Override
    public String getT2Req(String caseId) throws RMDDAOException {
        String t2Request = null;
        Session session = null;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            query.append("SELECT GRV.T2_REQ FROM TABLE_CASE TC,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE GRV  ");
            query.append("WHERE TC.CASE_PROD2SITE_PART = TSP.OBJID  AND TSP.OBJID = GRV.VEHICLE2SITE_PART AND TC.ID_NUMBER =:caseId");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_ID, caseId);
            hibernateQuery.setFetchSize(1);
            List<Object> t2RequestList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(t2RequestList)) {
                t2Request = RMDCommonUtility
                        .convertObjectToString(t2RequestList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_T2_REQ);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return t2Request;
    }

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method getUnitShipDetails is used to fetch unit
     *               shipping details it takes caseObjid as Input Parameter and
     *               returns unitShipping Details as a String.
     */
    @Override
    public String getUnitShipDetails(String caseObjid) throws RMDDAOException {
        String unitShipping = null;
        Session session = null;
        StringBuilder unitShippingQry = new StringBuilder();
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            unitShippingQry
                    .append("SELECT DECODE(TO_CHAR(SHIP_DATE,'YYYYMMDD'),NULL,'N','Y') FROM TABLE_SITE_PART TSP,");
            unitShippingQry
                    .append("TABLE_CASE TC WHERE CASE_PROD2SITE_PART = TSP.OBJID AND TC.OBJID = :caseObjId");
            hibernateQuery = session.createSQLQuery(unitShippingQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    caseObjid);
            hibernateQuery.setFetchSize(1);
            List<Object> unitShippingDetailList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(unitShippingDetailList)) {
                unitShipping = RMDCommonUtility
                        .convertObjectToString(unitShippingDetailList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_UNIT_SHIP_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return unitShipping;
    }

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching case Score it takes caseid
     *               as Input Parameter and returns recommendation CaseId as a
     *               String.
     */

    @Override
    public List<RecomDelvInfoServiceVO> getCaseScore(String caseid)
            throws RMDDAOException {
        String rxCaseId = null;
        Session session = null;
        StringBuilder caseScoreQry = new StringBuilder();
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
        List<RecomDelvInfoServiceVO> arlDelvInfoEoaServiceVO = new ArrayList<RecomDelvInfoServiceVO>();
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            caseScoreQry
                    .append("SELECT RX_CASE_ID,RECOM_TITLE FROM GETS_SD_FDBKPNDG_V WHERE ID_NUMBER=:caseId");
            caseScoreQry
                    .append(" AND RX_CLOSE_DATE IS NOT NULL AND CASE_SUCCESS IS NULL");
            hibernateQuery = session.createSQLQuery(caseScoreQry.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_ID, caseid);
            hibernateQuery.setFetchSize(10);
            List<Object[]> caseScoreList = hibernateQuery.list();
            for (Object[] currentCaseScore : caseScoreList) {
                objDelvInfoEoaServiceVO = new RecomDelvInfoServiceVO();
                rxCaseId = RMDCommonUtility
                        .convertObjectToString(currentCaseScore[0]);
                objDelvInfoEoaServiceVO.setStrRxCaseID(rxCaseId);
                objDelvInfoEoaServiceVO.setStrRxTitle(RMDCommonUtility
                        .convertObjectToString(currentCaseScore[1]));
                arlDelvInfoEoaServiceVO.add(objDelvInfoEoaServiceVO);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_SCORE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlDelvInfoEoaServiceVO;
    }

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching readyToDeliver date it
     *               takes rxObjid as Input Parameter and returns ready to
     *               deliver status as a String.
     */

    @Override
    public String getReadyToDelv(String rxObjid) throws RMDDAOException {
        String readyDeliver = RMDCommonConstants.CUST_VERSION_NOT_EXIST;
        Session session = null;
        Query hibernateQry = null;
        StringBuilder readyToDeliverQry = new StringBuilder();
        try {
            session = getHibernateSession();
            readyToDeliverQry
                    .append("SELECT READY_TO_DELV FROM GETS_SD_UP_TCS_RECOM WHERE TCS_RECOM2RECOM =:rxObjId");
            hibernateQry = session.createSQLQuery(readyToDeliverQry.toString());
            hibernateQry.setParameter(RMDServiceConstants.RX_OBJID, rxObjid);
            hibernateQry.setFetchSize(1);
            List<Object> readyDeliverList = hibernateQry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(readyDeliverList)) {
                readyDeliver = RMDCommonUtility
                        .convertObjectToString(readyDeliverList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_READY_TO_DELV);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return readyDeliver;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for fetching pending recommendation
     *               details by passing caseId as Input Parameter.
     */
    @Override
    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid)
            throws RMDDAOException {
        RecomDelvInfoServiceVO objRecomDelvInfoServiceVO = null;
        Session session = null;
        Query hibernateQry = null;
        StringBuilder customerFdbkObjidQry = new StringBuilder();
        try {
            session = getHibernateSession();
            customerFdbkObjidQry
                    .append(" SELECT RECOM_OBJID, RECOM_TITLE, CASE_OBJID, ID_NUMBER, CUST_FDBK_OBJID, RX_CASE_ID, ");
            customerFdbkObjidQry
                    .append("RX_CLOSE_DATE  FROM GETS_SD_FDBKPNDG_V WHERE ID_NUMBER =:caseId AND RX_CLOSE_DATE IS NULL ");
            hibernateQry = session.createSQLQuery(customerFdbkObjidQry
                    .toString());
            hibernateQry.setParameter(RMDServiceConstants.CASEID, caseid);
            hibernateQry.setFetchSize(1);
            List<Object[]> readyDeliverList = hibernateQry.list();
            for (Object[] currentObject : readyDeliverList) {
                objRecomDelvInfoServiceVO = new RecomDelvInfoServiceVO();
                objRecomDelvInfoServiceVO.setStrRxObjId(RMDCommonUtility
                        .convertObjectToString(currentObject[0]));
                objRecomDelvInfoServiceVO.setStrRxTitle(RMDCommonUtility
                        .convertObjectToString(currentObject[1]));
                objRecomDelvInfoServiceVO.setCaseObjId(RMDCommonUtility
                        .convertObjectToString(currentObject[2]));
                objRecomDelvInfoServiceVO.setFdbkObjId(RMDCommonUtility
                        .convertObjectToInt(currentObject[4]));
                objRecomDelvInfoServiceVO.setRxCaseId(RMDCommonUtility
                        .convertObjectToString(currentObject[5]));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CUSTOMER_FDBK_OBJID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return objRecomDelvInfoServiceVO;
    }

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for checking Whether delivery mechanism
     *               is present for particular Customer.
     */

    @Override
    public String checkForDelvMechanism(String customerName)
            throws RMDDAOException {
        String result = RMDCommonConstants.LETTER_N;
        Session session = null;
        Query hibernateQry = null;
        StringBuilder checkForDelMechnismQry = new StringBuilder();
        try {
            session = getHibernateSession();
            checkForDelMechnismQry
                    .append("SELECT CUSTOMER_FORM FROM GETS_SD_CUST_LKP WHERE CUSTOMER_NAME=:customerName");
            hibernateQry = session.createSQLQuery(checkForDelMechnismQry
                    .toString());
            hibernateQry.setParameter(RMDServiceConstants.CUSTOMER_NAME,
                    customerName);
            hibernateQry.setFetchSize(1);
            List<Object> readyDeliverList = hibernateQry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(readyDeliverList)) {
                String customForm = RMDCommonUtility
                        .convertObjectToString(readyDeliverList.get(0));
                if (null != customForm
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(customForm)) {
                    result = customForm;
                } else {
                    result = RMDCommonConstants.LETTER_Y;
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_FOR_DELV_MECHANISM);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching Msdc Notes.it takes
     *               rxObjid,caseObjid as Input Parameter and returns msdcNotes
     *               as a String.
     */
    @Override
    public String getMsdcNotes(String caseObjid, String rxObjid,String fromScreen,String custFdbkObjId)
            throws RMDDAOException {
        String msdcNotes = null;
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder msdcNotesQry = new StringBuilder();
        try {
            session = getHibernateSession();
            msdcNotesQry
                    .append("SELECT RECOM_NOTES FROM GETS_SD_RECOM_DELV DELV WHERE DELV.OBJID = (SELECT MAX(OBJID) FROM GETS_SD_RECOM_DELV ");
            msdcNotesQry
                    .append(" WHERE RECOM_DELV2CASE =:caseObjId");
            /*Modified by Murali Ch to get the Line 0 notes */
            if(RMDCommonConstants.CLOSURE_SCREEN.equalsIgnoreCase(fromScreen))
                msdcNotesQry.append(" AND RECOM_DELV2CUST_FDBK =:custFdbkObjId )");              
            else
                msdcNotesQry.append(" AND RECOM_DELV2RECOM=:rxObjId )");
            /*End of Line 0 notes */
            hibernateQuery = session.createSQLQuery(msdcNotesQry.toString());
            hibernateQuery.setParameter(RMDServiceConstants.CASE_OBJID,
                    caseObjid);
            if(RMDCommonConstants.CLOSURE_SCREEN.equalsIgnoreCase(fromScreen))
                hibernateQuery.setParameter(RMDServiceConstants.CUST_FDBK_OBJID, custFdbkObjId);
            else
                hibernateQuery.setParameter(RMDServiceConstants.RX_OBJID, rxObjid);
            List<Object> msdcNoteList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(msdcNoteList)) {
                msdcNotes = EsapiUtil.escapeSpecialChars(RMDCommonUtility
                        .convertObjectToString(msdcNoteList.get(0)));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MSDC_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return msdcNotes;
    }

    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching closure details based on
     *                   caseObj id.
     */
    @Override
	public CustomerFdbkVO getClosureDetails(String caseObjId)
            throws RMDDAOException {
        Session objSession = null;
        String isRxPresent = null;
        CustomerFdbkVO objCustomerFdbkVO = null;
        CustomerFdbkVO objCustomerFdbk = new CustomerFdbkVO();
        List<CustomerFdbkVO> customerFdbkVOList = null;
        List<Object[]> resultList = null;
        try {
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append("SELECT DECODE (COUNT(1),0,'N','Y') FROM GETS_SD_CUST_FDBK WHERE RX_CASE_ID IS NOT NULL AND CUST_FDBK2CASE= :caseObjId");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASE_OBJ_ID, caseObjId);
            isRxPresent = (String) caseHqry.uniqueResult();
            if (isRxPresent.equals(RMDCommonConstants.LETTER_Y)) {
                try {
                    String query = "SELECT OBJID,SERVICE_REQ_ID,CASE_SUCCESS,RX_CLOSE_DATE,RX_FDBK,RX_CASE_ID FROM GETS_SD_CUST_FDBK WHERE CUST_FDBK2CASE= :caseObjId ORDER BY OBJID DESC";
                    Query hibernateQuery = objSession.createSQLQuery(query);
                    hibernateQuery.setFetchSize(10);
                    hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                            caseObjId);
                    resultList = hibernateQuery.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                        customerFdbkVOList = new ArrayList<CustomerFdbkVO>();
                        for (final Iterator<Object[]> obj = resultList
                                .iterator(); obj.hasNext();) {
                            objCustomerFdbkVO = new CustomerFdbkVO();
                            final Object[] custFdbkDetails =  obj
                                    .next();
                            objCustomerFdbkVO.setCustFdbkObjId(RMDCommonUtility
                                    .convertObjectToString(custFdbkDetails[0]));
                            objCustomerFdbkVO.setServiceReqId(RMDCommonUtility
                                    .convertObjectToString(custFdbkDetails[1]));
                            objCustomerFdbkVO.setCaseSuccess(RMDCommonUtility
                                    .convertObjectToString(custFdbkDetails[2]));
                            objCustomerFdbkVO.setRxCloseDate(RMDCommonUtility
                                    .convertObjectToString(custFdbkDetails[3]));
                            objCustomerFdbkVO.setRxFdbk(ESAPI.encoder()
                                    .encodeForXML(EsapiUtil.escapeSpecialChars(
                                            RMDCommonUtility.convertObjectToString(custFdbkDetails[4]))));
                            objCustomerFdbkVO.setRxCaseId(RMDCommonUtility
                                    .convertObjectToString(custFdbkDetails[5]));
                            customerFdbkVOList.add(objCustomerFdbkVO);
                        }
                        objCustomerFdbk = customerFdbkVOList.get(0);
                    }
                } catch (RMDDAOConnectionException ex) {
                    String errorCode = RMDCommonUtility
                            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CLOSURE);
                    throw new RMDDAOException(errorCode, new String[] {},
                            RMDCommonUtility.getMessage(errorCode,
                                    new String[] {},
                                    RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                            RMDCommonConstants.FATAL_ERROR);
                }
            }
            objCustomerFdbk.setIsRxPresent(isRxPresent);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CLOSURE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return objCustomerFdbk;
    }

    /**
     * @Author :
     * @return :String
     * @param :VehicleConfigVO
     * @throws :RMDDAOException
     * @Description:This method is used for checking the controller
     *                   Configuration for a given Recommendation.
     */
    @Override
    public String checkForContollerConfig(VehicleConfigVO objVehicleConfigVO)
            throws RMDDAOException {
        Object result;
        Session session = null;
        Query hibernateQry = null;
        StringBuilder caseQry = new StringBuilder();
        String isContollerConfig = RMDCommonConstants.RECOMM_NOT_MATCH_CONFIG;
        boolean isConfigMatch = false;
        boolean isModelMatch = false;
        try {
            session = getHibernateSession();
            List<String> vehConfigList = getVehConfigGroupForCase(objVehicleConfigVO
                    .getCaseObjId());
            List<String> rxVehConfigList = getVehConfigGroupForRecomm(objVehicleConfigVO
                    .getRxObjId());

            if (null != rxVehConfigList && !rxVehConfigList.isEmpty()) {
                for (String rxVehConfig : rxVehConfigList) {
                    if (vehConfigList.contains(rxVehConfig)) {
                        isConfigMatch = true;
                        break;
                    }
                    isConfigMatch = false;
                }
                if (isConfigMatch
                        && getmodelMatch(objVehicleConfigVO.getModel(),
                                objVehicleConfigVO.getRxObjId())) {
                    isModelMatch = true;
                }
            } else {
                if (getmodelMatch(objVehicleConfigVO.getModel(),
                        objVehicleConfigVO.getRxObjId())) {
                    isModelMatch = true;
                }
            }

            if (isModelMatch) {
                isContollerConfig = RMDCommonConstants.RECOMM_MATCH_CONFIG;
            } else {
                caseQry.append(" SELECT OBJID RECOM_OBJID FROM GETS_SD_RECOM WHERE  OBJID=:rxObjId AND OBJID NOT IN ");
                caseQry.append(" (SELECT MTM2RECOM  FROM GETS_SD_RECOM_MTM_CFGGPITM C,GETS_SD_CFG_GROUP_ITEM I ");
                caseQry.append(" WHERE MTM2CFG_GROUP_ITEM = I.OBJID  AND CFG_ITEM  <> 'All') ");

                hibernateQry = session.createSQLQuery(caseQry.toString());
                hibernateQry.setParameter(RMDServiceConstants.RX_OBJID,
                        objVehicleConfigVO.getRxObjId());
                result = hibernateQry.uniqueResult();
                if (null != result) {
                    isContollerConfig = RMDCommonConstants.RECOMM_MATCH_CONFIG;
                }
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_FOR_CONTOLLER_CONFIG);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return isContollerConfig;
    }

    /**
     * @Author:
     * @param customerName
     * @return:List<ElementVO>
     * @throws RMDBOException
     * @Description: This method is used for fetching the list of all Road
     *               Initials based upon CustomerId.
     */
    @Override
    public List<ElementVO> getRoadNumberHeaders(String customerId)
            throws RMDDAOException {
        List<ElementVO> arlElementVOs = new ArrayList<ElementVO>();
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder roadIntialQuery = new StringBuilder();
        try {
            session = getHibernateSession();
            roadIntialQuery
                    .append("SELECT DISTINCT VEHICLE_HDR,VEH_HDR_ORDER FROM GETS_RMD_CUST_RNH_RN_V WHERE ORG_ID=:customerId  ORDER BY VEH_HDR_ORDER");
            hibernateQuery = session.createSQLQuery(roadIntialQuery.toString());
            hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID,
                    customerId);
            hibernateQuery.setFetchSize(100);
            List<Object[]> roadIntialsList = hibernateQuery.list();
            for (Object[] currentRoadIntial : roadIntialsList) {
                ElementVO objElementVO = new ElementVO();
                objElementVO.setId(RMDCommonUtility
                        .convertObjectToString(currentRoadIntial[1]));
                objElementVO.setName(RMDCommonUtility
                        .convertObjectToString(currentRoadIntial[0]));
                arlElementVOs.add(objElementVO);
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlElementVOs;
    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used to checking foe maximum numbers of
     *               units on which mass apply Rx can be applied.
     */
    @Override
    public String getMaxMassApplyUnits() throws RMDDAOException {
        String maxLimit = null;
        Session session = null;
        StringBuilder query = new StringBuilder();
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            query.append("SELECT VALUE FROM GETS_RMD_SYSPARM WHERE TITLE=:listName");
            hibernateQuery = session.createSQLQuery(query.toString());
            hibernateQuery.setParameter(RMDCommonConstants.LIST_NAME, RMDCommonConstants.MAX_MASS_APPLY_RX_UNITS);
            hibernateQuery.setFetchSize(1);
            List<Object> maxMassApplyLimit = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(maxMassApplyLimit)) {
                maxLimit = RMDCommonUtility
                        .convertObjectToString(maxMassApplyLimit.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_MASS_APPPLY_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return maxLimit;
    }

    /**
     * @Author:
     * @param :MassApplyRxVO objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDDAOException
     * @Description: This method is used for creating a new case and delivering
     *               the recommendation to the given range of assets it will
     *               return a array list of viewLogVo's.
     */
    @Override
    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO)
            throws RMDDAOException {

        com.ge.trans.eoa.cm.framework.domain.User user = null;
        Session session = null;
        Transaction transaction = null;
        Query hibernateQuery = null;
        StringBuilder queueQuery = new StringBuilder();
        StringBuilder clarifyQueueQuery = new StringBuilder();
        StringBuilder siteIDQuery = new StringBuilder();
        Long siteObjId = 0l;
        Long site2PartId = 0l;
        String queueObjid = "-1";
        String clarifyQueue = "";
        String siteId = "";
        String currentQueue = null;
        List<ViewLogVO> arlViewLogVOs = new ArrayList<ViewLogVO>();
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();
            user = getUserDetails(objMassApplyRxVO.getUserName());
            queueQuery
                    .append("SELECT NVL(RECOM_MOVE2QUEUE,-1),URGENCY FROM GETS_SD_RECOM WHERE OBJID =:rxObjid");
            hibernateQuery = session.createSQLQuery(queueQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.RX_OBJID,
                    objMassApplyRxVO.getObjRxDetailsVO().getRxObjid());
            hibernateQuery.setFetchSize(1);
            List<Object[]> queueObjList = hibernateQuery.list();
            for (Object[] currentqueueObject : queueObjList) {
                queueObjid = RMDCommonUtility
                        .convertObjectToString(currentqueueObject[0]);

            }
            if ((RMDCommonConstants.MINUS_ONE.equals(queueObjid)) || (queueObjid.trim().length() <= 0)) {
                clarifyQueueQuery
                        .append("SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME ='MassApplyRxDefaultQueue' AND LOOK_STATE = 'Default'");
                hibernateQuery = session.createSQLQuery(clarifyQueueQuery
                        .toString());
                hibernateQuery.setParameter(RMDCommonConstants.QUEUE_OBJID,
                        queueObjid);
                hibernateQuery.setFetchSize(1);
                List<Object[]> queueList = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                    clarifyQueue = RMDCommonUtility
                            .convertObjectToString(queueList.get(0));
                }
            } else {
                clarifyQueueQuery
                        .append("SELECT Q.TITLE FROM TABLE_QUEUE Q WHERE Q.OBJID =:queueObjId");
                hibernateQuery = session.createSQLQuery(clarifyQueueQuery
                        .toString());
                hibernateQuery.setParameter(RMDCommonConstants.QUEUE_OBJID,
                        queueObjid);
                hibernateQuery.setFetchSize(1);
                List<Object[]> queueList = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                    clarifyQueue = RMDCommonUtility
                            .convertObjectToString(queueList.get(0));
                }
            }
            siteIDQuery
                    .append("SELECT DISTINCT TRC.SITE_ID,TRC.LOC_OBJID,TRC.CON_OBJID  FROM TABLE_ROL_CONTCT TRC WHERE ORG_ID =:customerId AND FIRST_NAME='AutoGen'");
            hibernateQuery = session.createSQLQuery(siteIDQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    objMassApplyRxVO.getCustomerId());
            hibernateQuery.setFetchSize(1);
            List<Object[]> queueList = hibernateQuery.list();
            for (Object[] currentObject : queueList) {
                siteId = RMDCommonUtility
                        .convertObjectToString(currentObject[0]);
                siteObjId = RMDCommonUtility
                        .convertObjectToLong(currentObject[1]);
            }
            if (RMDCommonConstants.SMPP.equals(objMassApplyRxVO.getCaseType())) {
                currentQueue = RMDCommonConstants.QUEUE_PINPOINT;
            } else if (RMDCommonConstants.COMMISSION.equals(objMassApplyRxVO
                    .getCaseType())) {
                currentQueue = RMDCommonConstants.QUEUE_CASES_IN_PROCEESS;
            } else {
                currentQueue = clarifyQueue;
            }
            // Creating Cases Start
            for (String assetdata : objMassApplyRxVO.getAssetList()) {
                ViewLogVO objViewLogVO = new ViewLogVO();
                String[] arr = assetdata.split(RMDCommonConstants.HYPHEN);
                String assetNumber = arr[1];
                String assetGrpName = arr[0];

                com.ge.trans.eoa.cm.framework.domain.SitePartView sitePartView = tableScmViewDao
                        .findWhereSiteObjidAndSerialEquals(siteObjId,
                                assetNumber, assetGrpName);
                if (null != sitePartView && !sitePartView.isObjidNull()) {
                    site2PartId = sitePartView.getObjid();
                }
                Date date = Calendar.getInstance().getTime();
                CaseContent caseContent = new CaseContent();
                caseContent.setCreationTime(date);
                caseContent.setPhoneNum("9999");
                caseContent.setModifyStmp(date);
                caseContent.setCaseWip2wipbin(wipbinDao
                        .findWhereWipbinOwner2userEquals(user.getObjid())
                        .get(0).getObjid());
                caseContent.setCaseOriginator2user(user.getObjid());
                caseContent.setCalltype2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(
                                objMassApplyRxVO.getCaseType().toUpperCase())
                        .get(0).getObjid());
                caseContent.setRespprty2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(objMassApplyRxVO.getPriority())
                        .get(0).getObjid());// 4-LOW
                caseContent.setRespsvrty2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(objMassApplyRxVO.getSeverity())
                        .get(0).getObjid());// MEDIUM
                caseContent.setCaseProd2sitePart(site2PartId);
                if (null != sitePartView) {
                    caseContent.setCasePrt2partInfo(sitePartView
                            .getPartnumObjid());
                }
                caseContent.setCaseOwner2user(user.getObjid());
                Contact objContact = contactDao.findWhereSFirstNameEquals(
                        RMDCommonConstants.CONTACT_AUTOGEN).get(0);
                caseContent.setCaseReporter2contact(objContact.getObjid());
                Site objSite = tableSiteDao.findWhereSiteIdEquals(siteId)
                        .get(0);
                caseContent.setCase2address(objSite.getCustPrimaddr2address());
                caseContent.setTitle(RMDCommonConstants.AUTO_CASE_FOR_SPRX);// "AutoCase for SpRX"
                caseContent.setSTitle(caseContent.getTitle().toUpperCase());
                caseContent.setCaseReporter2site(objSite.getObjid());
                caseContent.setCaseCurrq2queue(queueDao
                        .findWhereTitleEquals(currentQueue).get(0).getObjid());
                /* Get case objid from sequence */
                Long caseObjId = caseDao.getCaseIdSequenceNextValue();
                caseContent.setObjid(caseObjId);
                /* Generate caseId number */
                String caseIdNumber = generateCaseIdNumberNextValue();
                caseContent.setIdNumber(caseIdNumber);
                /* Create condition to open */
                Condition condition = new Condition();
                condition.setObjid(caseContent.getObjid());
                condition.setCondition(objMassApplyRxVO.getCaseCondition());
				if (!RMDCommonUtility.isNullOrEmpty(objMassApplyRxVO
						.getIsFromTooloutput())
						&& RMDCommonConstants.Y_LETTER_UPPER
								.equalsIgnoreCase(objMassApplyRxVO
										.getIsFromTooloutput())) {//if the Rx is delivered from tooloutput
					condition.setTitle(RMDCommonConstants.OPEN_DISPATCH);
					condition.setSTitle(RMDCommonConstants.OPEN_DISPATCH.toUpperCase());
				} else {
					condition.setTitle(objMassApplyRxVO.getCaseTitle());
					condition.setSTitle(condition.getTitle().toUpperCase());
				}
               
                condition.setSequenceNum(0);
                condition.setDispatchTime(caseContent.getCreationTime());
                condition.setQueueTime(caseContent.getCreationTime());
                condition.setWipbinTime(caseContent.getCreationTime());
                condition.setFirstRespTime(caseContent.getCreationTime());
                conditionDao.insert(condition);
                /* Insert case */
                caseContent
                        .setCasests2gbstElm(RMDCommonConstants.STATUS_OPEN_GBSTELM_ID);
                caseContent.setCaseState2condition(condition.getObjid());
                caseDao.insert(caseContent);
                /* Create Activity Entry log */

                ActEntry actEntry = new ActEntry();
                actEntry.setActCode(RMDCommonConstants.TABLE_ACT_ENTRY_CREATE_CASE);
                actEntry.setEntryTime(Calendar.getInstance().getTime());
                actEntry.setActEntry2case(caseContent.getObjid());
                actEntry.setActEntry2user(caseContent.getCaseOwner2user());
                actEntry.setAddnlInfo(RMDCommonConstants.CONTACT
                        + objContact.getFirstName()
                        + RMDCommonConstants.EMPTY_STRING
                        + objContact.getLastName()
                        + RMDCommonConstants.PRIORITY);
                if (!caseContent.isCaseWip2wipbinNull()) {
                    actEntry.setRemoved(0);
                    actEntry.setFocusType(0);
                    actEntry.setFocusLowid(0);
                }
                actEntry.setEntryName2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(RMDCommonConstants.CREATE)
                        .get(0).getObjid());
                actEntryDao.insert(actEntry);

                /* Update Note Log */
                String notes = RMDCommonConstants.MASSAPPLY_NOTES
                        + caseContent.getIdNumber()
                        + RMDCommonConstants.NOT_SYMBOL;
                caseServiceAPI.insertNotesLog(caseContent, notes, user);
                /* Insert into Act Entry for WIP to queue */
                ActEntry actEntryInProcess = new ActEntry();
                actEntryInProcess
                        .setActCode(RMDCommonConstants.TABLE_ACT_ENTRY_IN_PROCESS);
                actEntryInProcess.setEntryTime(date);
                String additionalInfo = RMDCommonConstants.FROM_WIP_DEFAULT_TO_QUEUE
                        + currentQueue;
                actEntryInProcess.setAddnlInfo(additionalInfo);
                if (!caseContent.isCaseWip2wipbinNull()) {
                    actEntryInProcess.setRemoved(0);
                    actEntryInProcess.setFocusType(0);
                    actEntryInProcess.setFocusLowid(0);
                }
                actEntryInProcess.setActEntry2case(caseContent.getObjid());
                actEntryInProcess.setActEntry2user(user.getObjid());
                List<GbstElm> gbstElmList = gbstElmDao
                        .findWhereTitleEquals(RMDCommonConstants.STR_NOTES);
                if (gbstElmList != null && !gbstElmList.isEmpty()) {
                    actEntryInProcess.setEntryName2gbstElm(gbstElmList.get(0)
                            .getObjid());
                }
                actEntryDao.insert(actEntryInProcess);
                /* Deliver Rx logic starts here */
                if (RMDCommonConstants.LETTER_Y
                        .equalsIgnoreCase(objMassApplyRxVO.getIsMassApplyRx())) {
                    try {
                        String recomNotes = RMDCommonConstants.EMPTY_STRING;
                        RecomDelvInfoServiceVO objRecomDelvInfoServiceVO = new RecomDelvInfoServiceVO();
                        objRecomDelvInfoServiceVO
                                .setRxDetailsVO(objMassApplyRxVO
                                        .getObjRxDetailsVO());
                        objRecomDelvInfoServiceVO.setCaseObjId(String
                                .valueOf(caseObjId));
                        objRecomDelvInfoServiceVO.setStrCaseID(caseIdNumber);
                        objRecomDelvInfoServiceVO
                                .setStrRxObjId(objMassApplyRxVO
                                        .getObjRxDetailsVO().getRxObjid());
                        objRecomDelvInfoServiceVO
                                .setCustomerName(objMassApplyRxVO
                                        .getCustomerName());
                        objRecomDelvInfoServiceVO
                                .setStrUserName(objMassApplyRxVO.getUserName());
                        objRecomDelvInfoServiceVO
                                .setStrEstmRepTime(objMassApplyRxVO
                                        .getEstRepairTime());
                        objRecomDelvInfoServiceVO
                                .setStrUrgRepair(objMassApplyRxVO.getUrgency());
                        
                        if (!RMDCommonUtility.isNullOrEmpty(objMassApplyRxVO
                                .getMsdcNotes())) { // change Made by Vamshi
                            objRecomDelvInfoServiceVO
                                    .setStrRecomNotes(objMassApplyRxVO
                                            .getMsdcNotes());
                        } else {
                            objRecomDelvInfoServiceVO
                                    .setStrRecomNotes(recomNotes);
                        }
                        objRecomDelvInfoServiceVO.setLstAttachment(objMassApplyRxVO.getLstAttachment());
                        deliverRX(objRecomDelvInfoServiceVO);
                    } catch (Exception e) {
                        transaction.rollback();
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDCommonConstants.DAO_EXCEPTION_MASS_APPLY_RX_DELIVER_RECOMMENDATION);
                        throw new RMDDAOException(errorCode, new String[] {},
                                RMDCommonUtility.getMessage(errorCode,
                                        new String[] {},
                                        RMDCommonConstants.ENGLISH_LANGUAGE),
                                e, RMDCommonConstants.MINOR_ERROR);
                    }
                }
                objViewLogVO.setCaseId(caseIdNumber);
                objViewLogVO.setCreationDate(date);
                objViewLogVO.setRoadNumber(assetNumber);
                objViewLogVO.setRoadNumberHeader(assetGrpName);
                objViewLogVO.setRxQueue(currentQueue);
                objViewLogVO.setCaseObjid(String.valueOf(caseObjId));
                objViewLogVO.setCustomerId(objMassApplyRxVO.getCustomerId());
                objViewLogVO.setCaseTitle(objMassApplyRxVO.getCaseTitle());
                arlViewLogVOs.add(objViewLogVO);
            }
            transaction.commit();
        } catch (Exception e) {
        	logger.error("Error in massApplyRx", e);
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_MASS_APPPLY_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlViewLogVOs;
    }

    /**
     * @Author:
     * @param :String userName
     * @return:User
     * @throws:RMDDAOException
     * @Description: This method is used for fetching user details based upon
     *               the username.This method will return a User details VO.
     */
    public com.ge.trans.eoa.cm.framework.domain.User getUserDetails(
            String userName) throws RMDDAOException {
        com.ge.trans.eoa.cm.framework.domain.User user = new com.ge.trans.eoa.cm.framework.domain.User();
        StringBuilder userQuery = new StringBuilder();
        Session session = null;
        Query hibernateQuery = null;

        try {
            session = getHibernateSession();
            userQuery
                    .append("SELECT OBJID, LOGIN_NAME, USER_DEFAULT2WIPBIN,USER_ACCESS2PRIVCLASS,WEB_LOGIN  FROM SA.TABLE_USER");
            userQuery
                    .append(" WHERE LOGIN_NAME=:userName OR WEB_LOGIN=:userName ORDER BY LOGIN_NAME");
            hibernateQuery = session.createSQLQuery(userQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.USERNAME, userName);
            hibernateQuery.setFetchSize(1);
            List<Object[]> userDetailsList = hibernateQuery.list();
            for (Object[] currentqueueObject : userDetailsList) {
                user.setObjid(RMDCommonUtility
                        .convertObjectToLong(currentqueueObject[0]));
                user.setLoginName(RMDCommonUtility
                        .convertObjectToString(currentqueueObject[1]));
                user.setUserDefault2wipbin(RMDCommonUtility
                        .convertObjectToLong(currentqueueObject[2]));
                user.setUserAccess2privclass(RMDCommonUtility
                        .convertObjectToLong(currentqueueObject[3]));
                user.setWebLogin(RMDCommonUtility
                        .convertObjectToString(currentqueueObject[3]));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCPETION_GET_USER_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return user;
    }

    /**
     * This method is used to check if any recommendation is delivered for a
     * case
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     * @Override public boolean checkRecomDelivered(CaseAppendServiceVO
     *           caseAppendServiceVO) throws RMDDAOException {
     * 
     * 
     *           Session objSession = null; boolean isRecomDelivered= false;/
     *           int recomDelvCount = 0; try {
     * 
     *           LOG.debug("Begin checkRecomDelivered method of CaseEoaDAOImpl")
     *           ; objSession = getHibernateSession();
     *           getCaseObjid(objSession,caseAppendServiceVO.getStrCaseId());
     *           StringBuilder caseQry = new StringBuilder();
     * 
     *           caseQry.append(
     *           "select count(activity) FROM gets_sd_act_log_v WHERE  CASE_OBJID = :caseObjId  and ACTIVITY = 'Recommendation Delivered'"
     *           );
     * 
     *           Query caseHqry = objSession.createSQLQuery(caseQry.toString())
     *           .setParameter(RMDCommonConstants.CASE_OBJ_ID,
     *           caseAppendServiceVO.getStrCaseId()); recomDelvCount =
     *           ((BigDecimal) caseHqry.uniqueResult()).intValue();
     * 
     *           //If recomDelvCount > 0 then a recommendation is delivered
     *           if(recomDelvCount > 0){ isRecomDelivered = true; }
     * 
     *           } catch (RMDDAOConnectionException ex) { String errorCode =
     *           RMDCommonUtility
     *           .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
     *           throw new RMDDAOException(errorCode, new String[] {},
     *           RMDCommonUtility.getMessage(errorCode, new String[] {},
     *           RMDCommonConstants.ENGLISH_LANGUAGE), ex,
     *           RMDCommonConstants.FATAL_ERROR); } catch (Exception e) {
     *           LOG.error(RMDCommonConstants.EXCEPTION_OCCURED,e); String errorCode =
     *           RMDCommonUtility
     *           .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
     *           throw new RMDDAOException(errorCode, new String[] {},
     *           RMDCommonUtility.getMessage(errorCode, new String[] {},
     *           RMDCommonConstants.ENGLISH_LANGUAGE), e,
     *           RMDCommonConstants.MAJOR_ERROR); } finally {
     *           releaseSession(objSession); }
     *           LOG.debug("Ends getCloseCaseStatus method of CaseEoaDAOImpl");
     * 
     *           return isRecomDelivered; }
     */

    /**
     * This method is used to check if the case type is matching with the lookup
     * values
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    @Override
	public boolean validateCaseType(String caseType) {

        Session objSession = null;
        List<GetSysLookupVO> lookupList = null;
        boolean isCaseTypeValidated = false;

        try {
            objSession = getHibernateSession();

            lookupList = objpopupListAdminDAO
                    .getPopupListValues(RMDCommonConstants.CASE_TYPE_APPEND);
            if (lookupList != null && lookupList.contains(caseType))
                isCaseTypeValidated = true;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return isCaseTypeValidated;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDDAOException
     * @Description:This method return the cases for that asset
     */
    @Override
    public List<SelectCaseHomeVO> getPreviousCases(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<SelectCaseHomeVO> selectCaseHomeVOList = new ArrayList<SelectCaseHomeVO>();
        SelectCaseHomeVO objSelectCaseHomeVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            // Customer,roadNumber,AssetGroupName,CaseType
            session = getHibernateSession();
            strQuery.append("SELECT TABLE_CASE.OBJID,");
            strQuery.append("TABLE_CASE.ID_NUMBER ID_NUMBER,");
            strQuery.append("TABLE_CONDITION.TITLE CONDITION,");
            strQuery.append("TABLE_GSE_PRIORITY.TITLE PRIORITY,");
            strQuery.append("TABLE_CASE.TITLE CASE_TITLE,");
            strQuery.append("DECODE(GETS_GET_CASE_REASON_DWQ_FN(TABLE_CASE.OBJID) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review' ,'6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed','Recommendation Deferred','Recommendation Deferred','Recommendation Deleted','Recommendation Deleted''Recommendation Rejected','Recommendation Rejected') CASEREASON,");
            strQuery.append("TO_CHAR(TABLE_CASE.CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CREATION_TIME,");
            strQuery.append("TABLE_GSE_TYPE.TITLE CASE_TYPE,TABLE_CASE.IS_SUPERCASE,");
            strQuery.append("DECODE(TABLE_GSE_SEVERITY.TITLE,'Appended', 'YES') APPENDED,");
            strQuery.append("TABLE_SITE.OBJID,");
            strQuery.append("TABLE_SITE.NAME,");
            strQuery.append("TABLE_SITE.SITE_ID,");
            strQuery.append("TABLE_CASE.CASE_PROD2SITE_PART,");
            strQuery.append("TABLE_SITE_PART.X_VEH_HDR,");
            strQuery.append("TABLE_SITE_PART.SERIAL_NO,");
            strQuery.append("TABLE_SITE_PART.S_SERIAL_NO,");
            strQuery.append("TABLE_QUEUE.TITLE QUEUE,");
            strQuery.append("TABLE_SITE_PART.X_VEH_HDR_CUST,");
            strQuery.append("TO_CHAR(TABLE_CLOSE_CASE.CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS'),");
            strQuery.append("TABLE_USER.LOGIN_NAME OWNER_LOGIN_NAME");
            strQuery.append(" FROM TABLE_GBST_ELM TABLE_GSE_TYPE,TABLE_GBST_ELM TABLE_GSE_SEVERITY,TABLE_GBST_ELM TABLE_GSE_PRIORITY,TABLE_CASE,");
            strQuery.append("TABLE_CONDITION,TABLE_SITE,TABLE_SITE_PART,TABLE_CLOSE_CASE,TABLE_QUEUE,TABLE_USER");
            strQuery.append(" WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_GSE_TYPE.OBJID = TABLE_CASE.CALLTYPE2GBST_ELM ");
            strQuery.append(" AND TABLE_GSE_SEVERITY.OBJID = TABLE_CASE.RESPSVRTY2GBST_ELM AND TABLE_GSE_PRIORITY.OBJID = TABLE_CASE.RESPPRTY2GBST_ELM ");
            strQuery.append(" AND TABLE_SITE.OBJID = TABLE_CASE.CASE_REPORTER2SITE AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER ");
            strQuery.append(" AND TABLE_CASE.OBJID = TABLE_CLOSE_CASE.LAST_CLOSE2CASE(+) AND TABLE_SITE_PART.OBJID (+) = TABLE_CASE.CASE_PROD2SITE_PART ");
            strQuery.append(" AND TABLE_QUEUE.OBJID (+) = TABLE_CASE.CASE_CURRQ2QUEUE ");

            if (null != objFindCaseServiceVO.getStrAssetNumber()
                    && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {
                strQuery.append(" AND TABLE_SITE_PART.SERIAL_NO=:roadNumber ");
            }
            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                strQuery.append(" AND TABLE_SITE_PART.X_VEH_HDR_CUST=:customer ");
            }

            if (null != objFindCaseServiceVO.getStrAssetGrpName()
                    && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                strQuery.append(" AND TABLE_SITE_PART.X_VEH_HDR=:assetGroupName ");
            }

            if (null != objFindCaseServiceVO.getStrCaseType()
                    && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {
                strQuery.append(" AND TABLE_GSE_TYPE.TITLE=:caseType");
            }

            if (null != objFindCaseServiceVO.getNoOfDays()
                    && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                strQuery.append(" AND ((TABLE_CONDITION.TITLE like 'Open%') OR (TABLE_CONDITION.TITLE like 'Close%') AND TABLE_CONDITION.WIPBIN_TIME >= (SYSDATE-:noOfDays)) ");
            }
            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                strQuery.append(" AND TABLE_CASE.ID_NUMBER=:caseId ");
            }
            strQuery.append("ORDER BY CONDITION DESC,DECODE(CONDITION,'Open-Dispatch', creation_time) DESC,");
            strQuery.append("DECODE(CONDITION,'Open-Forward', creation_time) DESC,DECODE(CONDITION,'Open', creation_time) DESC,");
            strQuery.append("DECODE(CONDITION,'Closed', close_date) DESC");
            query = session.createSQLQuery(strQuery.toString());
            if (null != objFindCaseServiceVO.getStrAssetNumber()
                    && !objFindCaseServiceVO.getStrAssetNumber().isEmpty()) {
                query.setParameter(RMDServiceConstants.ROADNUMBER,
                        objFindCaseServiceVO.getStrAssetNumber());
            }
            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {

                query.setParameter(RMDServiceConstants.STR_CUSTOMER,
                        objFindCaseServiceVO.getStrCustomerId());
            }

            if (null != objFindCaseServiceVO.getStrAssetGrpName()
                    && !objFindCaseServiceVO.getStrAssetGrpName().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_ASSET_GROUP_NAME,
                        objFindCaseServiceVO.getStrAssetGrpName());
            }
            if (null != objFindCaseServiceVO.getNoOfDays()
                    && !objFindCaseServiceVO.getNoOfDays().isEmpty()) {
                query.setParameter(RMDServiceConstants.STR_NO_OF_DAYS,
                        objFindCaseServiceVO.getNoOfDays());

            }
            if (null != objFindCaseServiceVO.getStrCaseType()
                    && !objFindCaseServiceVO.getStrCaseType().isEmpty()) {

                query.setParameter(RMDCommonConstants.PARAM_CASE_TYPE,
                        objFindCaseServiceVO.getStrCaseType());

            }
            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {

                query.setParameter(RMDCommonConstants.CASEID,
                        objFindCaseServiceVO.getCaseID());

            }
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {

                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {

                    objSelectCaseHomeVO = new SelectCaseHomeVO();
                    final Object[] caseHome = obj.next();

                    objSelectCaseHomeVO.setStrTitle(RMDCommonUtility
                            .convertObjectToString(caseHome[4]));
                    objSelectCaseHomeVO.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));
                    objSelectCaseHomeVO.setStrCaseType(RMDCommonUtility
                            .convertObjectToString(caseHome[7]));

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[6])) {
                        Date creationDate =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[6]));
                        objSelectCaseHomeVO.setDtCreationDate(creationDate);
                    }

                    objSelectCaseHomeVO.setCaseObjid(RMDCommonUtility
                            .convertObjectToLong(caseHome[0]));
                    objSelectCaseHomeVO.setStrPriority(RMDCommonUtility
                            .convertObjectToString(caseHome[3]));
                    objSelectCaseHomeVO.setStrOwner(RMDCommonUtility
                            .convertObjectToString(caseHome[20]));
                    objSelectCaseHomeVO.setStrReason(RMDCommonUtility
                            .convertObjectToString(caseHome[5]));
                    objSelectCaseHomeVO.setCondition(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    objSelectCaseHomeVO.setStrQueue(RMDCommonUtility
                            .convertObjectToString(caseHome[17]));

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[19])) {
                        Date closeDate =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[19]));
                        objSelectCaseHomeVO.setCloseDate(closeDate);
                    }
                    objSelectCaseHomeVO.setIsAppend(RMDCommonUtility
                            .convertObjectToString(caseHome[9]));
                    selectCaseHomeVOList.add(objSelectCaseHomeVO);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getPreviousCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);

        }

        return selectCaseHomeVOList;
    }

    /**
     * This method is used to get the delivered recommendations for a Case
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    @Override
    public List<String> getDeliveredRxs(CaseAppendServiceVO caseAppendServiceVO)
            throws RMDDAOException {

        Session objSession = null;
        List<BigDecimal> rxIdList = null;
        List<String> rxIdListStr = new ArrayList<String>();
        
        try {

            objSession = getHibernateSession();

            StringBuilder rxQry = new StringBuilder();
            rxQry.append("SELECT recom_delv2recom FROM GETS_SD_RECOM_DELV WHERE RECOM_DELV2CASE = :caseObjId");
            String caseObjId = getCaseObjid(objSession,
                    caseAppendServiceVO.getCaseId());
            LOG.debug("caseid" + caseAppendServiceVO.getCaseId());
            LOG.debug("Caseobj" + caseObjId);
            Query recomQry = objSession.createSQLQuery(rxQry.toString())
                    .setParameter(RMDCommonConstants.CASE_OBJ_ID, caseObjId);
            rxIdList = recomQry.list();

            for (BigDecimal b : rxIdList) {
                boolean closedRx = false;
                // to check for delivered and closed rx
                StringBuilder rxCloseQry = new StringBuilder();

                rxCloseQry
                        .append("SELECT COUNT(1) FROM GETS_SD_FDBKPNDG_V WHERE CASE_OBJID=:caseObjId AND RECOM_OBJID=:rxObjid");
                rxCloseQry
                        .append(" AND cust_fdbk_objid=(SELECT MAX(cust_fdbk_objid)  FROM GETS_SD_FDBKPNDG_V  WHERE case_objid= :caseObjId AND RECOM_OBJID=:rxObjid)");
                rxCloseQry.append(" and rownum<=1");
                rxCloseQry.append(" and case_success is not null");
                Query rxCloseHQry = objSession.createSQLQuery(rxCloseQry
                        .toString());
                rxCloseHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                        caseObjId);
                rxCloseHQry.setParameter(RMDCommonConstants.RX_OBJID,
                        String.valueOf(b));
                BigDecimal count = (BigDecimal) rxCloseHQry.uniqueResult();

                int rxCnt = count.intValue();
                LOG.debug("count" + count);
                if (rxCnt > 0)
                    closedRx = true;
                if (!closedRx)
                    rxIdListStr.add(String.valueOf(b));
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getDeliveredRxs method of CaseEoaDAOImpl");

        return rxIdListStr;
    }

    /**
     * This method is used to update casevictim2case column of table_case
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    @Override
    public void updateCaseVictimToCase(CaseAppendServiceVO caseAppendServiceVO)
            throws RMDDAOException {

        Session session = null;
        session = getHibernateSession();
        StringBuilder updateTableCaseQry = new StringBuilder();
        String toCaseId = caseAppendServiceVO.getToCaseId();
        String fromCaseId = caseAppendServiceVO.getCaseId();

        try {
            long toCaseObjId = Long.parseLong(getCaseObjid(session, toCaseId));
            long fromCaseObjId = Long.parseLong(getCaseObjid(session,
                    fromCaseId));
            LOG.info("casevictim2case=" + toCaseObjId + "for case"
                    + fromCaseObjId);
            updateTableCaseQry
                    .append("UPDATE TABLE_CASE SET CASE_VICTIM2CASE= :toCaseObjId WHERE OBJID =:fromCaseObjId");

            Query updateTableCaseHQry = session
                    .createSQLQuery(updateTableCaseQry.toString());
            updateTableCaseHQry.setParameter(
                    RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
            updateTableCaseHQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                    toCaseObjId);
            updateTableCaseHQry.executeUpdate();
            LOG.debug("Updated");
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred in updateCaseVictimToCase:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    /**
     * This method is used to perform all append related operations
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    @Override
    public void appendRx(CaseAppendServiceVO caseAppendServiceVO) {

        Transaction transaction = null;
        Session session = null;

        StringBuilder updateArListQry = new StringBuilder();
        StringBuilder updateRPRLQry = new StringBuilder();
        String toCaseId = caseAppendServiceVO.getToCaseId();
        String fromCaseId = caseAppendServiceVO.getCaseId();
        String rxId = caseAppendServiceVO.getRxId();
        String ruleDefId = caseAppendServiceVO.getRuleDefId();
        String toolId = caseAppendServiceVO.getToolId();
        String assetGrpName = caseAppendServiceVO.getAssetGrpName();
        String custumerId = caseAppendServiceVO.getCustomerId();
        String assetNumber = caseAppendServiceVO.getAssetNumber();
        int arListObjId = 0;
        StringBuilder updateTableCaseQry = new StringBuilder();
        StringBuilder deleteARQuery = new StringBuilder();
        StringBuilder selCaseQry = new StringBuilder();
        int rxCnt = 0;
        boolean rxExists = false;
        boolean faultExists = false;
        boolean isJDPADCBR = false;
        StringBuilder selFCQry = new StringBuilder();
		/*added for case append changes(US160835)*/
		StringBuilder maxMinFaultQuery = null;
		StringBuilder updateRPRLQuery = null;
		Query maxMinFaultHQuery = null;
		List<Object> maxMinFaultList = null;
		BigDecimal maxExstngFault = null;
		BigDecimal minExstngFault = null;
		/*End of case append changes */
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();

            long toCaseObjId = Long.parseLong(getCaseObjid(session, toCaseId));
            long fromCaseObjId = Long.parseLong(getCaseObjid(session,
                    fromCaseId));
            LOG.debug("Append From Case --> Id : " +fromCaseId+" Obj id  : "  + fromCaseObjId);
            LOG.debug("Append To Case --> Id : " +toCaseId+" Obj id  : "  + toCaseObjId);
            LOG.debug("******************************Getting Vehicle ObjId***********************************************************");
            String strVehicleId = getCustrnhDetails(custumerId, assetNumber,
                    assetGrpName);
            if (strVehicleId == null) {
                LOG.error("Append Error : Vehicle Not Found");
                throw new Exception();
            }

            LOG.info("Vehicle Obj id= " + strVehicleId);
            // Begin Transaction
            transaction.begin();

            // Update table_case
            LOG.debug("1.******************************Updating CASE_VICTIM2CASE in Table_case***********************************************************");
            LOG.debug("FromCase OBJID: " + fromCaseObjId);
            LOG.debug("CASE_VICTIM2CASE: " + toCaseObjId);
            updateTableCaseQry
                    .append("UPDATE TABLE_CASE SET CASE_VICTIM2CASE= :toCaseObjId WHERE OBJID =:fromCaseObjId");

            Query updateTableCaseHQry = session
                    .createSQLQuery(updateTableCaseQry.toString());
            updateTableCaseHQry.setParameter(
                    RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
            updateTableCaseHQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                    toCaseObjId);
            updateTableCaseHQry.executeUpdate();
            LOG.debug("Updated table_case.......");

            // Logic for updating tables for For RXS
            // ****************************************************************************************************************/

            if (toolId != null
                    && (toolId.equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                            .equalsIgnoreCase(RMDCommonConstants.CBR))) {
                isJDPADCBR = true;               
                /*** Check if Rx is already present in the append2case ****/
                LOG.debug(".******************************checking if rx is there in GETS_TOOL_AR_LIST************");
                if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                    selCaseQry
                        .append("SELECT COUNT(1)  FROM  gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list");
                    selCaseQry
                        .append(" AND rprldwn2rule_defn=:ruleDefId AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId");
                }else if(toolId.equalsIgnoreCase(RMDCommonConstants.CBR)){
                    selCaseQry
                        .append("SELECT COUNT(1) FROM gets_tool_ar_list WHERE AR_LIST2RECOM =:rxObjid AND AR_LIST2CASE= :toCaseObjId ");
                }
                Query selCaseHQry = session.createSQLQuery(selCaseQry
                        .toString());
                if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                    selCaseHQry.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
                }
                selCaseHQry.setParameter(RMDCommonConstants.RX_OBJID, rxId);
                selCaseHQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                        toCaseObjId);

                BigDecimal count = (BigDecimal) selCaseHQry.uniqueResult();
                rxCnt = count.intValue();
                if (rxCnt > 0)
                    rxExists = true;

                LOG.debug("Rx already exists in Append to Case : " + rxExists);
                LOG.debug("isJDPADCBR" + isJDPADCBR);

                    StringBuilder findFaultJDPADQry = new StringBuilder();
                    StringBuilder findFaultCBRQry = new StringBuilder();
                    if (toolId != null && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        findFaultJDPADQry.append("SELECT DISTINCT gtft.objid FROM GETS_RMD.GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC, GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR, ")
                        .append("GETS_RMD.GETS_TOOL_CASE_MTM_FAULT mtm,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF ,GETS_RMD.GETS_TOOL_FLTDOWN GTFD ,GETS_RMD.GETS_TOOL_FAULT GTFT WHERE ")
                        .append("TC.objid = :caseObjId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ")
                        .append("AND GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN  = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND mtm.mtm2fault = gtft.objid AND ")
                        .append("Fault2vehicle = :vehicleObjId AND Mtm.Mtm2case  = Tc.Objid AND GTRL.rprldwn2rule_defn = :ruleDefId");
                    } else if (toolId != null && (toolId.equals(RMDCommonConstants.CBR))) {
						findFaultCBRQry.append("SELECT MTM2FAULT FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE=:caseObjId");
                    }

                    Query findFaultHQry = null;
                    List<BigDecimal> faultListJDPAD = null;
                    List<BigDecimal> faultListCBR = null;               
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                     
                        findFaultHQry = session
                                .createSQLQuery(findFaultJDPADQry.toString());
                        findFaultHQry.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, fromCaseObjId);
                        findFaultHQry.setParameter(
                                RMDCommonConstants.RULE_DEFID, ruleDefId);                    
                        findFaultHQry
                                .setParameter(
                                        RMDCommonConstants.VEHICLE_OBJ_ID,
                                        strVehicleId);
                        faultListJDPAD = findFaultHQry.list();

                    } else if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {                      
                        findFaultHQry = session.createSQLQuery(findFaultCBRQry
                                .toString());
                        findFaultHQry.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, fromCaseObjId);
                        faultListCBR = findFaultHQry.list();
                    }

                    List<String> faultObjIds = new ArrayList<String>();

                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {

                        for (BigDecimal currentFault : faultListJDPAD) {
                            if (currentFault != null && !faultObjIds.contains(currentFault.toString())) {
                                    LOG.info("adding to fault list jdpad"
                                            + currentFault);
                                    faultObjIds.add(String
                                            .valueOf(currentFault));
                            }
                        }
                    }
                   
                    else if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {                   
                        for (BigDecimal currentFault : faultListCBR) {
                            if (currentFault != null && !faultObjIds.contains(currentFault.toString())) {                               
                                    LOG.info("Adding CBR fault to fault list "
                                            + currentFault);
                                    faultObjIds.add(String
                                            .valueOf(currentFault));

                            }
                        }

                    }
                    /** delete from RPRL_DWN table **/
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        LOG.debug("2.******************************Deleting from GETS_TOOL_RPRLDWN***********************************************************");
                        updateRPRLQry
                                .append("DELETE FROM GETS_TOOL_RPRLDWN where RPRLDWN2AR_LIST = :currarListObjId");

                        Query updateRPRLHQry = session
                                .createSQLQuery(updateRPRLQry.toString());

                        updateRPRLHQry.setParameter(
                                RMDCommonConstants.AR_CURR_OBJID,
                                caseAppendServiceVO.getToolObjId());
                        updateRPRLHQry.executeUpdate();
                        LOG.debug("Deleted record  from GETS_TOOL_RPRLDWN");
                    }
                    /** Update gets_tool_ar_list table **/

                    LOG.debug("3.******************************Updating GETS_TOOL_AR_LIST starts here***********************************************************");
				if (!rxExists){ //if rx Does not exist in toCase, update AR_LIST2CASE column in GETS_TOOL_AR_LIST table for fromCase
                    StringBuilder seqQry = new StringBuilder();
                    seqQry.append(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");

                    Query seqHQry = session.createSQLQuery(seqQry.toString());

                    arListObjId = ((BigDecimal) seqHQry.uniqueResult())
                            .intValue();
                    LOG.debug("New AR_LIST OBJID for Append to case" + arListObjId);

                    if (toCaseObjId == 0 || fromCaseObjId == 0
                            || arListObjId == 0) {
                        LOG.info("ToCase || FromCase || arListObjId is in INVALID :0");
                        throw new Exception();
                    }
                    
                    updateArListQry.append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE,OBJID= :arListObjId  WHERE ")
                    .append("AR_LIST2CASE =:fromCaseObjId AND AR_LIST2RECOM= :rxObjid AND OBJID= :currarListObjId AND TOOL_ID= :toolId");
                    
                    Query updateArListhQry = session
                            .createSQLQuery(updateArListQry.toString());
                    updateArListhQry.setParameter(
                            RMDCommonConstants.TO_CASE_OBJID, toCaseObjId);
                    updateArListhQry.setParameter(
                            RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                            arListObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);

                    updateArListhQry.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID,
                            caseAppendServiceVO.getToolObjId());                  

                        if (toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.CBR);
                        else if (toolId
                                .equalsIgnoreCase(RMDCommonConstants.RULE))
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.JDPAD);
                    
                    updateArListhQry.executeUpdate();
                    LOG.debug("Updated GETS_TOOL_AR_LIST table");
					}else{ //if rx  exist in toCase, update the records into GETS_TOOL_AR_LIST table
					    if (toolId != null
	                            && toolId
	                                    .equalsIgnoreCase(RMDCommonConstants.RULE)) {
	                        updateArListQry
	                            .append("UPDATE GETS_TOOL_AR_LIST SET LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE WHERE OBJID IN (SELECT ar.objid FROM ");
	                        updateArListQry
	                            .append("gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list  AND rprldwn2rule_defn=:ruleDefId AND ");
	                        updateArListQry
	                            .append("ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId) AND TOOL_ID= :toolId");                      
	                    }else if(toolId != null &&  toolId
	                            .equalsIgnoreCase(RMDCommonConstants.CBR)){
	                        updateArListQry
	                            .append("UPDATE GETS_TOOL_AR_LIST SET LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE");
	                        updateArListQry
	                            .append(" WHERE AR_LIST2CASE =:toCaseObjId AND AR_LIST2RECOM= :rxObjid AND TOOL_ID= :toolId");                        
	                    }
						Query updateArListhQry = session
								.createSQLQuery(updateArListQry.toString());
						updateArListhQry.setParameter(
								RMDCommonConstants.TO_CASE_OBJID, toCaseObjId);
						updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
								rxId);
						if (toolId != null
								&& (toolId
										.equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
										.equalsIgnoreCase(RMDCommonConstants.CBR))) {
	
							if (toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
								updateArListhQry.setParameter(
										RMDCommonConstants.TOOLID,
										RMDCommonConstants.CBR);
							else if (toolId
									.equalsIgnoreCase(RMDCommonConstants.RULE)){
							    updateArListhQry.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
								updateArListhQry.setParameter(
										RMDCommonConstants.TOOLID,
										RMDCommonConstants.JDPAD);
							}
						}
	
						updateArListhQry.executeUpdate();
						/** To get the max and min fault objid's of rule/cbr in append to case**/
						if (toolId != null
								&& toolId
										.equalsIgnoreCase(RMDCommonConstants.RULE)) {
							maxMinFaultQuery = new StringBuilder();
							
							maxMinFaultQuery.append("SELECT max(gtft.objid) maxObjId,min(gtft.objid) FROM GETS_RMD.GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC, GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR, ")
						    .append("GETS_RMD.GETS_TOOL_CASE_MTM_FAULT mtm,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF ,GETS_RMD.GETS_TOOL_FLTDOWN GTFD ,GETS_RMD.GETS_TOOL_FAULT GTFT WHERE ")
						    .append("TC.objid = :caseObjId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ")
						    .append("AND GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN  = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND mtm.mtm2fault = gtft.objid AND ")
						    .append("Fault2vehicle = :vehicleObjId AND Mtm.Mtm2case  = Tc.Objid AND GTRL.rprldwn2rule_defn IN (SELECT rprldwn2rule_defn  FROM gets_tool_rprldwn rprl, ")
						     .append("gets_tool_ar_list ar WHERE ar.objid = rprl.rprldwn2ar_list AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :caseObjId)");
							
							maxMinFaultHQuery = session
									.createSQLQuery(maxMinFaultQuery.toString());
							maxMinFaultHQuery.setParameter(
									RMDCommonConstants.CASE_OBJ_ID, toCaseObjId);
							maxMinFaultHQuery.setParameter(
									RMDCommonConstants.RX_OBJID, rxId);
							maxMinFaultHQuery
									.setParameter(
											RMDCommonConstants.VEHICLE_OBJ_ID,
											strVehicleId);
						
						}else if (toolId != null
								&& toolId
								.equalsIgnoreCase(RMDCommonConstants.CBR)) {
							maxMinFaultQuery = new StringBuilder();
							maxMinFaultQuery
								.append(" select  max(mtm2fault) maxObjId,min(mtm2fault) minObjId from gets_tool_case_mtm_fault where mtm2case=:caseObjId ");
							maxMinFaultHQuery = session
									.createSQLQuery(maxMinFaultQuery.toString());
							maxMinFaultHQuery.setParameter(
									RMDCommonConstants.CASE_OBJ_ID, toCaseObjId);
						}
						maxMinFaultList = maxMinFaultHQuery.list();
						if (RMDCommonUtility.isCollectionNotEmpty(maxMinFaultList)) {
			                for (final Iterator<Object> obj = maxMinFaultList.iterator(); obj
			                        .hasNext();) {
			                    final Object[] maxMinFaultDetails = (Object[]) obj.next();
			                    maxExstngFault = (BigDecimal) maxMinFaultDetails[0];
			                    minExstngFault = (BigDecimal) maxMinFaultDetails[1];
			                }
			         
			            }
						/** End of case append changes*/
						
					}
                    /** Insert records with new ar_list ids into RPRLDWN table **/
                    if (toolId != null
                            && !toolId
                                    .equalsIgnoreCase(RMDCommonConstants.Fault)
                            && !toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
					if(!rxExists){//if rx Does not exist in toCase, insert the record into GETS_TOOL_RPRLDWN table
                        LOG.debug("4.******************************Insert records with new ar_list ids into RPRLDWN  table ***********************************************************");                        
                        LOG.debug("RuleDefId of selected rule" + ruleDefId);
                        
                        StringBuilder insertGetsToolRprldwnQuery = new StringBuilder()
                        .append("INSERT INTO GETS_TOOL_RPRLDWN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,TOOL_ID, RPRLDWN2AR_LIST, RPRLDWN2RULE_DEFN) ")
                        .append("VALUES (GETS_TOOL_RPRLDWN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,'OMD_Append',:toolId,:arListObjId,:ruleDefId)");
                        
                        Query hibernateQuery = session
                                .createSQLQuery(insertGetsToolRprldwnQuery.toString());

                        hibernateQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.JDPAD);
                        hibernateQuery.setParameter(
                                RMDCommonConstants.AR_OBJID, arListObjId);
                        hibernateQuery.setParameter(
                                RMDCommonConstants.RULE_DEFID, ruleDefId);
                        hibernateQuery.executeUpdate();
                        LOG.debug("Record Inserted into GETS_TOOL_RPRLDWN");
						}else if(rxExists){//if rX exist in toCase , update the GETS_TOOL_RPRLDWN table
							updateRPRLQuery = new StringBuilder();
							updateRPRLQuery.append("UPDATE GETS_TOOL_RPRLDWN SET LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY='OMD_Append' WHERE OBJID IN");
							updateRPRLQuery.append("(SELECT rprl.objid FROM gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list AND ");
							updateRPRLQuery.append("rprldwn2rule_defn=:ruleDefId AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId)");
							Query updateRPRLHQuery = session.createSQLQuery(updateRPRLQuery
									.toString());
							updateRPRLHQuery.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
							updateRPRLHQuery.setParameter(RMDCommonConstants.RX_OBJID, rxId);
							updateRPRLHQuery.setParameter(RMDCommonConstants.TO_CASE_OBJID,
									toCaseObjId);
							updateRPRLHQuery.executeUpdate();
							LOG.debug("GETS_TOOL_RPRLDWN table updated");
						}
					}

                    // Update MTM FAULTS table
                    // Get FaultObjids for a rule_defn and then query it                 
                    LOG.debug("5.******************************Inserting into MTM FAULTS***********************************************************");
                    LOG.debug("                5A.******************************Get FaultObjids for a rule_defn and then query it *****************");                    
                   
                    // Insert each fault into mtm_faults for that case
                    if (toolId != null
                            && !toolId
                                    .equalsIgnoreCase(RMDCommonConstants.Fault)) {
                        // Start inserting
                        LOG.debug("                5b.******************************Append -Inserting faultobjid Into MTM_FAULTS *****************");
                        LOG.debug("toCaseObjId  " + toCaseObjId);
                        
                        StringBuilder insertGetsToolCaseMtmFaultQuery = new StringBuilder()
                        .append("INSERT INTO GETS_TOOL_CASE_MTM_FAULT (OBJID,LAST_UPDATED_BY, LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,MTM2CASE,MTM2FAULT,CBR_USED,JDPAD_USED) ")
                        .append("VALUES(GETS_TOOL_CASE_MTM_FAULT_SEQ.NEXTVAL,:lastUpdatedBy,sysdate,:lastUpdatedBy,sysdate,:caseId, :faultCode, :CBR,:JDPAD)");
                        
                        Query mtmInsertHQry = session
                                .createSQLQuery(insertGetsToolCaseMtmFaultQuery.toString());                     
                        Query countFaultHQryJdpad = session
                                .createSQLQuery("SELECT COUNT(1) FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE=:caseId AND MTM2FAULT=:faultCode ");

                        if (faultObjIds != null) {
                            for (String fault : faultObjIds) {

                                countFaultHQryJdpad
                                        .setParameter(
                                                RMDCommonConstants.CASE_ID,
                                                toCaseObjId);
                                countFaultHQryJdpad.setParameter(
                                        RMDCommonConstants.FAULTSCODE, fault);

                                int fltCntJdpad = ((BigDecimal) countFaultHQryJdpad
                                        .uniqueResult()).intValue();
                                                  
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.LAST_UPDATED,
                                        "Case_Repetition_1");
                                mtmInsertHQry
                                        .setParameter(
                                                RMDCommonConstants.CASE_ID,
                                                toCaseObjId);
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.FAULTSCODE, fault);
                                if (toolId != null
                                        && toolId
                                                .equalsIgnoreCase(RMDCommonConstants.RULE)
                                        && fltCntJdpad <= 0) {
                                    mtmInsertHQry.setParameter(
                                            RMDCommonConstants.JDPAD, "Y");

                                    mtmInsertHQry.setParameter(
                                            RMDCommonConstants.CBR, null);
                                    mtmInsertHQry.executeUpdate();
                                    LOG.debug("Inserted fault  for "+ toolId +" - "+ fault);
                                } else
                                // CBR
                                if (toolId != null
                                        && toolId
                                                .equalsIgnoreCase(RMDCommonConstants.CBR)
                                        && fltCntJdpad <= 0) {
                                    mtmInsertHQry.setParameter(
                                            RMDCommonConstants.JDPAD, null);
                                    mtmInsertHQry.setParameter(
                                            RMDCommonConstants.CBR, "Y");
                                    mtmInsertHQry.executeUpdate();
                                    LOG.debug("Inserted fault  for "+ toolId +" - "+ fault);

                                }
                               
                            }
                        } 
                        
                        /**
                         * Added to add Min and Max faults to gets_tool_run
                         * table
                         **/
                        if (toolId != null
                                && (toolId
                                        .equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                                        .equalsIgnoreCase(RMDCommonConstants.CBR))) {

                            BigDecimal maxFault = null;
                            BigDecimal minFault = null;
                            BigDecimal maxFaultInsert = null;
                            BigDecimal minFaultInsert = null;
                            if ((null != faultListJDPAD
                                    && !faultListJDPAD.isEmpty()) ||(null != faultListCBR && !faultListCBR.isEmpty())) {
                                if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                                    maxFault = Collections.max(faultListJDPAD);
                                    minFault = Collections.min(faultListJDPAD);
                                }else if(toolId.equalsIgnoreCase(RMDCommonConstants.CBR)){
                                    maxFault = Collections.max(faultListCBR);
                                    minFault = Collections.min(faultListCBR);
                                }
								/*Added for case append changes*/
								if(rxExists){
									
									 if((maxExstngFault != null && maxFault != null) && Long.parseLong(maxExstngFault.toString())>Long.parseLong(maxFault.toString())){
											maxFault = maxExstngFault;
										}
										if((minExstngFault != null && minFault != null) && Long.parseLong(minExstngFault.toString())<Long.parseLong(minFault.toString())){
											minFault = minExstngFault;
										}
								}
								/*End of case append changes*/
                                maxFaultInsert = maxFault;
                                minFaultInsert = minFault;
                                LOG.debug(MIN_FAULT_OBJID + minFault);
                                LOG.debug(MAX_FAULT_OBJID + maxFault);
                                LOG.info("inserting into gets_tool_run");
                                String strDiagServiceId= null;

                                strDiagServiceId = getDiagServiceIDAppend(
                                        session, toCaseObjId);
                                if(null == strDiagServiceId){
                                    strDiagServiceId =  getDiagServiceIDAppend(
                                            session, fromCaseObjId);
                                }
                                if (null != strDiagServiceId) {
                                	StringBuilder insertGetsToolRunQuery = new StringBuilder()
                                    .append("INSERT INTO GETS_TOOL_RUN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RUN_PRIORITY,RUN_START,RUN_CPT,RUN_PROCESSEDMIN_OBJID,")
                                    .append("RUN_PROCESSEDMAX_OBJID,RUN2CASE,RUN2VEHICLE,RUN_CASE_REQ_BY,QUIET_ON,DIAG_SERVICE_ID) VALUES(GETS_TOOL_RUN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,")
                                    .append("'OMD_Append',4,sysdate,sysdate,:minFaultObjId,:maxFaultObjId,:caseId,:vehicleObjId,NULL,NULL,:diagServiceId)");
                                	
                                    Query runInsertHQry = session
                                            .createSQLQuery(insertGetsToolRunQuery.toString());

                                    runInsertHQry.setParameter(
                                            RMDCommonConstants.VEHICLE_OBJ_ID,
                                            Integer.parseInt(strVehicleId));
                                    runInsertHQry.setParameter(
                                            RMDCommonConstants.CASE_ID,
                                            toCaseObjId);
                                    runInsertHQry.setParameter(MAX_FAULT_OBJID,
                                            maxFaultInsert.intValue());
                                    runInsertHQry.setParameter(MIN_FAULT_OBJID,
                                            minFaultInsert.intValue());
                                    runInsertHQry.setParameter(
                                            RMDCommonConstants.DIAG_SERVICE_ID,
                                            strDiagServiceId);
                                    runInsertHQry.executeUpdate();
                                    LOG.debug("Inserted MAX and MIN fault obj id into into GETS_TOOL_RUN table");
                                }

                            }

                        }

                    }

				if (rxExists) {
                    /** Delete from AR LIST **/
                    LOG.debug(".*******************Deleting from AR_LIST**********************");                 
                    deleteARQuery
                            .append("DELETE FROM GETS_TOOL_AR_LIST where AR_LIST2CASE = :fromCaseObjId AND AR_LIST2RECOM= :rxObjid  and tool_id=:toolId and OBJID=:currarListObjId");
                    Query deleteARHQuery = session.createSQLQuery(deleteARQuery
                            .toString());

                    deleteARHQuery.setParameter(
                            RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                    deleteARHQuery.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);
                    if (toolId != null && toolId.equalsIgnoreCase(RMDCommonConstants.RULE))
                        deleteARHQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.JDPAD);
                    if (toolId != null && toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                        deleteARHQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.CBR);
                    deleteARHQuery.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID,
                            caseAppendServiceVO.getToolObjId());
                    deleteARHQuery.executeUpdate();

                }

            } // End if jdpad cbr

            else {
                /*** Check if Faults is already present in the append2case ****/
                LOG.debug(".*******************checking if FAULT is there in GETS_TOOL_AR_LIST**********************************");

                LOG.debug("fromCaseObjId" + fromCaseObjId);
                LOG.debug("arlistobjid for from case for that fault " + rxId);
                selFCQry.append("select fc_fault from gets_tool_ar_list where objid= :rxObjid and AR_LIST2CASE= :fromCaseObjId");
                Query selFCHQry = session.createSQLQuery(selFCQry.toString());

                selFCHQry.setParameter(RMDCommonConstants.RX_OBJID, rxId);
                selFCHQry.setParameter(RMDCommonConstants.FROM_CASE_OBJID,
                        fromCaseObjId);

                String faultCode = selFCHQry.uniqueResult().toString();

                StringBuilder selCountQry = new StringBuilder();
                LOG.debug("toCaseObjId" + toCaseObjId);
                selCountQry
                        .append("select count(*) from gets_tool_ar_list where AR_LIST2CASE= :toCaseObjId and fc_fault=:Fault");
                Query selCountHQry = session.createSQLQuery(selCountQry
                        .toString());

                selCountHQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                        toCaseObjId);
                selCountHQry.setParameter(RMDCommonConstants.Fault, faultCode);
                BigDecimal count = (BigDecimal) selCountHQry.uniqueResult();
                int faultCnt = count.intValue();
                if (faultCnt > 0)
                    faultExists = true;

                /*** Check FAULT ends *******************************************/

                /** Update gets_tool_ar_list table **/
                LOG.debug("3.******************************Updating GETS_TOOL_AR_LIST for FAULT***********************************************************");

                Query seqHQry = session.createSQLQuery("SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");                
                arListObjId = ((BigDecimal) seqHQry.uniqueResult()).intValue();
                LOG.debug("New AR_LIST OBJID for Fault Move" + arListObjId);

                if (toCaseObjId == 0 || fromCaseObjId == 0 || arListObjId == 0) {
                    LOG.debug("ToCase || FromCase || arListObjId values are 0");
                    throw new Exception();
                }
                
                 updateArListQry =new StringBuilder()
                .append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE, OBJID= :arListObjId WHERE ")
                .append("AR_LIST2CASE =:fromCaseObjId and objid =:rxObjid");

                Query updateArListhQry = session.createSQLQuery(updateArListQry
                        .toString());
                updateArListhQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                        toCaseObjId);
                updateArListhQry.setParameter(
                        RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                        arListObjId);
                // this is the ar_list old obj id
                updateArListhQry
                        .setParameter(RMDCommonConstants.RX_OBJID, rxId);

                updateArListhQry.executeUpdate();
                LOG.debug("Updated GETS_TOOL_AR_LIST for Fault");

            }// End faults loop
                // Update TABLE_ACT_ENTRY table
            LOG.debug("6.******************************Inserting into TABLE_ACT_ENTRY***********************************************************");
            // Get Append ObjId
            LOG.debug("               6a.Get Append ObjId");
            StringBuilder appendQry = new StringBuilder();
            appendQry
                    .append(" SELECT g.OBJID from TABLE_GBST_ELM g, TABLE_GBST_LST lst  ");
            appendQry
                    .append(" WHERE g.TITLE='Append' AND g.GBST_ELM2GBST_LST = lst.OBJID  ");
            appendQry.append("AND lst.TITLE='Activity Name'");
            Query appendHQry = session.createSQLQuery(appendQry.toString());
            String appendId = appendHQry.uniqueResult().toString();
            if (appendId == null)
                throw new Exception();
            // Select the act_entry2user
            LOG.debug("               6b.Select the act_entry2user");
            StringBuilder actEntry2UserQry = new StringBuilder();
            actEntry2UserQry
                    .append(" SELECT OBJID  from TABLE_USER where login_name='autogen'  ");

            Query actEntry2UseHrQry = session.createSQLQuery(actEntry2UserQry
                    .toString());
            String actEntry2User = actEntry2UseHrQry.uniqueResult().toString();
            if (actEntry2User == null)
                throw new Exception();
            // Insert into act_entry
            LOG.debug("               6c.Select the act_entry");
            LOG.debug("entry2User " + actEntry2User);
            LOG.debug("caseObjId " + toCaseObjId);
            LOG.debug("ENTRY_NAME2GBST_ELM " + appendId);

            String addnlInfo = RMDCommonConstants.ADDNL_INFO + fromCaseId;
            LOG.debug("addnlInfo " + addnlInfo);
            String insertActEntry = "INSERT INTO TABLE_ACT_ENTRY (OBJID,ACT_CODE,ENTRY_TIME,ADDNL_INFO,ACT_ENTRY2CASE,ACT_ENTRY2USER,ENTRY_NAME2GBST_ELM) "
                    + "VALUES(TABLE_ACT_ENTRY_SEQ.NEXTVAL,'90600',sysdate,:addnlInfo,:caseObjId, :entry2User, :gbst)";

            Query insertActHEntry = session.createSQLQuery(insertActEntry);
            insertActHEntry.setParameter("addnlInfo", addnlInfo);
            insertActHEntry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    toCaseObjId);
            insertActHEntry.setParameter("entry2User", actEntry2User);
            insertActHEntry.setParameter("gbst", appendId);
            insertActHEntry.executeUpdate();
            LOG.debug("Insrted Into TABLE_ACT_ENTRY ");
            transaction.commit();
        } catch (RMDDAOConnectionException ex) {
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            transaction.rollback();
            LOG.error("Exception occurred in appendRx:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
    }

    public String getCustrnhDetails(String strCustomer, String strAssetNumber,
            String strAssetGroupName) {
        Session objHibernateSession = null;
        String strVehicleId = null;
        BigDecimal vehicleId = null;

        final StringBuilder custrnhQuery = new StringBuilder();
        try {
            objHibernateSession = getHibernateSession();
            custrnhQuery
                    .append("SELECT VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V WHERE VEHICLE_HDR=:assetGrpName and ORG_ID=:customerId and VEHICLE_NO=:assetNumber");
            Query queryCustRnh = objHibernateSession
                    .createSQLQuery(custrnhQuery.toString());
            queryCustRnh.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    strCustomer);
            queryCustRnh.setParameter(RMDCommonConstants.ASSET_NUMBER,
                    strAssetNumber);
            queryCustRnh.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    strAssetGroupName);
            vehicleId = (BigDecimal) queryCustRnh.uniqueResult();
            strVehicleId = String.valueOf(vehicleId);

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RMDDAOException(
                    RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
        } finally {
            releaseSession(objHibernateSession);
        }
        return strVehicleId;
    }

    /**
     * This method is used for retrieving Tool output details
     * 
     * @param strCaseId
     *            ,strLanguage,strUserLanguage
     * @return list of ToolOutputServiceVO
     * @throws RMDDAOException
     */
    @Override
	@SuppressWarnings("unchecked")
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId)
            throws RMDDAOException {
        List<Object> arlToolOutput = new ArrayList<Object>();
        ArrayList<ToolOutputEoaServiceVO> arlToolOutList = new ArrayList<ToolOutputEoaServiceVO>();
        Session session = null;
		Session dwSession=null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            try{
			dwSession=getDWHibernateSession();
			dwSession.flush();
			dwSession.clear();
            }catch(Exception e){
            	LOG.error(
	                    "Unexpected Error occured while getting Datawarehouse session"+e);
            	dwSession=null;
            }
            session.flush();
            session.clear();			
            final StringBuilder sBuildQuery = new StringBuilder();
			final StringBuilder dwBuildQuery = new StringBuilder();
			final StringBuilder assetQuery = new StringBuilder();
			dwBuildQuery
			.append("SELECT VIZ_BLOB FROM GETS_DW_EOA_SAS_ANOM WHERE VEHICLE_OBJID=:asset AND ANOM_OBJID=:anomObjid ORDER BY LAST_UPDATED_DATE DESC");
			assetQuery.append("SELECT VEHICLE_OBJID FROM TABLE_CASE tc, GETS_RMD_CUST_RNH_RN_V asset WHERE tc.CASE_PROD2SITE_PART = asset.SITE_PART_OBJID AND tc.ID_NUMBER = :caseId");
			List<Blob> blobLst = new ArrayList<Blob>();
			 String base64String=RMDCommonConstants.EMPTY_STRING;
			 Query hibernateQueryAsset = session.createSQLQuery(assetQuery
						.toString());
			 hibernateQueryAsset.setParameter(RMDCommonConstants.CASEID,
						strCaseId);
			 final String assetNumber = hibernateQueryAsset.uniqueResult().toString()
						.trim();

			 sBuildQuery
                    .append(" SELECT   toolid, recomtitle, repcode, repdesc, msg, ar_probability,");
			 sBuildQuery
                    .append(" arlistdate, faultcode, objids, false_alarm, tool_cover, mdsc_perf,");
			 sBuildQuery
                    .append(" ar_objid, NVL (b.ad_objid, 0), nvl(b.anom_title,'x'),ruledef_objid,urgency,estRepairTime,rxversion,");
			 sBuildQuery
                    .append(" (select ruledef2finrul from gets_tool_dpd_ruledef where objid=ruledef_objid) finrul");
			 sBuildQuery.append(" FROM (");
			 sBuildQuery
                    .append(" select inner.ToolId,inner.recomTitle,repCode,repDesc,Msg,AR_PROBABILITY,arListDate,");
			 sBuildQuery
                    .append(" faultCode,Objids,decode(sign(nvl(mtx.delv_count,-2)-sysparm.value),-1, '-', mtx.false_alarm_pct) FALSE_ALARM");
			 sBuildQuery
                    .append(" ,decode(sign(nvl(mtx.delv_count,-2)-sysparm.value),-1, '-', mtx.tool_covg_pct) TOOL_COVER");
			 sBuildQuery
                    .append(" ,decode(sign(nvl(mtx.delv_count,-2)-sysparm.value),-1, '-', mtx.mdsc_perf_pct) MDSC_PERF,ar_objid, (SELECT rprldwn2rule_defn FROM gets_tool_rprldwn rprl, gets_tool_ar_list ar WHERE ar.objid = rprl.rprldwn2ar_list AND ar.objid = inner.ar_objid) ruledef_objid ");
			 sBuildQuery
                    .append(" ,urgency,estRepairTime,rxversion from (select recom.objid recomObjid,nvl(gtar.TOOL_ID,'-') ToolId,nvl(recom.title,'-') recomTitle,");
			 sBuildQuery
                    .append(" nvl(gsrc.REPAIR_CODE,'-') repCode,nvl(gsrc.REPAIR_DESC,'-')  repDesc");
			 sBuildQuery
                    .append(" ,nvl(gtar.AR_MESSAGE,'-') Msg,nvl(to_char(gtar.AR_PROBABILITY),'-') as AR_PROBABILITY ,");
			 sBuildQuery
                    .append(" nvl(to_char(gtar.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS'),'-') arListDate,nvl(gtar.FC_FAULT,'-') faultCode ");
			 sBuildQuery
                    .append(" ,nvl(recom.objid,gsrc.objid) Objids, gtar.objid ar_objid,recom.urgency urgency,recom.est_repair_time estRepairTime,recom.version rxversion ");
			 sBuildQuery
                    .append(" from GETS_TOOL_AR_LIST gtar,GETS_SD_REPAIR_CODES gsrc,TABLE_CASE tc ");
			 sBuildQuery.append(" ,gets_sd_recom recom");
			 sBuildQuery
                    .append(" where gsrc.OBJID (+)= gtar.AR_LIST2REPAIR_CODES ");
			 sBuildQuery.append(" and recom.objid (+) = gtar.AR_LIST2RECOM ");
			 sBuildQuery
                    .append(" and gtar.AR_LIST2CASE  = tc.OBJID and AR_PROBABILITY != .2");
			 sBuildQuery.append(" and tc.ID_NUMBER = :caseId");
			 sBuildQuery
                    .append(" AND gtar.ar_list2recom IS NOT NULL "
                            + " UNION "
                            + "(select NVL (ar_list2recom, 0) recomobjid ,nvl(gtar.TOOL_ID,'-') ToolId, fc.fault_code || '-' || fc.sub_id || ' ' || fc.fault_desc as recomTitle, "
                            + "nvl(gsrc.REPAIR_CODE,'-') repCode,nvl(gsrc.REPAIR_DESC,'-')  repDesc ,nvl(gtar.AR_MESSAGE,'-') Msg,nvl(to_char(gtar.AR_PROBABILITY),'-') as AR_PROBABILITY, "
                            + "nvl(to_char(gtar.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS'),'-') arListDate,nvl(gtar.FC_FAULT,'-') faultCode,gtar.objid Objids, gtar.objid ar_objid,recom.urgency urgency,recom.est_repair_time estRepairTime,recom.version rxversion "
                            + "from GETS_TOOL_AR_LIST gtar,GETS_SD_REPAIR_CODES gsrc,TABLE_CASE tc, "
                            + "gets_rmd_flt_strategy fss, gets_rmd_fault_codes fc,gets_sd_recom recom where gsrc.OBJID (+)= gtar.AR_LIST2REPAIR_CODES AND recom.objid (+)     = gtar.AR_LIST2RECOM "
                            + "and gtar.AR_LIST2CASE  = tc.OBJID and AR_PROBABILITY = .2 and gtar.ar_list2flt_strategy = fss.objid (+) and fss.flt_strat2flt_code = fc.objid(+) "
                            + "and tc.ID_NUMBER =:caseId" + ")) inner,");
			 sBuildQuery
                    .append(" gets_tool_rx_metrics mtx, gets_rmd_sysparm sysparm where mtx.rx_metrics2recom (+)= inner.recomObjid ");
			 sBuildQuery
                    .append(" and sysparm.title = 'rx_metrics_display_threshold' and ");
			 sBuildQuery.append(" mtx.tool_id (+) = inner.ToolId ");
            sBuildQuery.append(" UNION ");
            sBuildQuery
                    .append(" SELECT   NVL (arlist.tool_id, '-') toolid, finrul.rule_title, ");
            sBuildQuery.append(" NVL (gsrc.repair_code, '-') repcode, ");
            sBuildQuery
                    .append(" NVL (gsrc.repair_desc, '-') repdesc, NVL (arlist.ar_message,'-') msg, ");
            sBuildQuery
                    .append(" NVL (TO_CHAR (arlist.ar_probability), '-') AS ar_probability, ");
            sBuildQuery
                    .append(" NVL (TO_CHAR (arlist.last_updated_date, 'MM/DD/YYYY HH24:MI:SS'),'-') arlistdate, ");
            sBuildQuery
                    .append(" NVL (arlist.fc_fault, '-') faultcode, gsrc.objid objids, ");
            sBuildQuery
                    .append(" '-' false_alarm, '-' tool_cover, '-' mdsc_perf,  arlist.objid ar_objid , ruledef.objid ruledef_objid,recom.urgency urgency,recom.est_repair_time estRepairTime,recom.version rxversion");
            sBuildQuery.append(" FROM table_case c, ");
            sBuildQuery.append(" gets_tool_ar_list arlist, ");
            sBuildQuery.append(" gets_sd_repair_codes gsrc, ");
            sBuildQuery.append(" gets_tool_rprldwn toolrpr, ");
            sBuildQuery.append(" gets_tool_dpd_ruledef ruledef, ");
            sBuildQuery
                    .append(" gets_tool_dpd_finrul finrul,gets_sd_recom recom ");

            sBuildQuery.append(" WHERE c.id_number = :caseId");
            sBuildQuery
                    .append(" AND arlist.ar_list2case = c.objid AND recom.objid (+)     = arlist.AR_LIST2RECOM");
            sBuildQuery
                    .append(" AND gsrc.objid(+) = arlist.ar_list2repair_codes ");
            sBuildQuery.append(" AND toolrpr.rprldwn2ar_list = arlist.objid ");
            sBuildQuery
                    .append(" AND toolrpr.rprldwn2rule_defn = ruledef.objid ");
            sBuildQuery.append(" AND finrul.objid = ruledef2finrul ");

            sBuildQuery
                    .append(" AND arlist.ar_list2repair_codes IS NOT NULL)  A, ");
            sBuildQuery
                    .append(" (SELECT distinct ad.objid ad_objid, ar.objid arobjid, ad.anomaly_title anom_title ");
            sBuildQuery.append(" FROM gets_tool_rprldwn rp, ");
            sBuildQuery.append(" gets_tool_ar_list ar, ");
            sBuildQuery.append(" gets_tool_dpd_ruledef rd, ");
            sBuildQuery.append(" gets_tool_dpd_finrul fr, ");
            sBuildQuery.append(" gets_tool_dpd_simrul sr, ");
            sBuildQuery.append(" gets_tool_dpd_simfea sf, ");
            sBuildQuery.append(" gets_tool_anom_admin ad, ");
            sBuildQuery.append(" table_case tabc ");
            sBuildQuery.append(" WHERE simfea2simrul = sr.objid ");
            sBuildQuery.append(" AND simrul2finrul = fr.objid ");
            sBuildQuery.append(" AND ruledef2finrul = fr.objid ");
            sBuildQuery.append(" AND rp.rprldwn2ar_list = ar.objid ");
            sBuildQuery.append(" AND rp.rprldwn2rule_defn = rd.objid ");
            sBuildQuery.append(" AND ar.ar_list2case = tabc.objid ");
            sBuildQuery.append(" AND tabc.ID_NUMBER = :caseId");
            sBuildQuery.append(" AND simfea2anom_admin = ad.objid) B ");
            sBuildQuery.append(" WHERE A.ar_objid = B.arobjid(+) ");
            sBuildQuery.append(" ORDER BY 1 DESC, 6 DESC ");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(sBuildQuery
                        .toString());
                hibernateQuery.setParameter(RMDCommonConstants.CASEID,
                        strCaseId);

                arlToolOutput = (ArrayList<Object>) hibernateQuery.list();
                if (null != arlToolOutput) {
                    int size = arlToolOutput.size();
                    ToolOutputEoaServiceVO objToolOutputServiceVO = null;
                    for (int i = 0; i < size; i++) {
                        objToolOutputServiceVO = new ToolOutputEoaServiceVO();
                        Object[] objDataArray = (Object[]) arlToolOutput.get(i);
                        objToolOutputServiceVO.setStrRecomId(RMDCommonUtility
                                .convertObjectToString(objDataArray[8]));
                        objToolOutputServiceVO
                                .setStrRecomTitle(RMDCommonUtility
                                        .convertObjectToString(objDataArray[1]));
                        objToolOutputServiceVO
                                .setStrFalseAlarmPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[9]));
                        objToolOutputServiceVO
                                .setStrToolCovgPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[10]));
                        objToolOutputServiceVO
                                .setStrMdscPerfPct(RMDCommonUtility
                                        .convertObjectToString(objDataArray[11]));
                        objToolOutputServiceVO.setStrUrgency(RMDCommonUtility
                                .convertObjectToString(objDataArray[16]));
                        objToolOutputServiceVO
                                .setStrEstRepairTime(RMDCommonUtility
                                        .convertObjectToString(objDataArray[17]));
                        objToolOutputServiceVO.setRxRevision(RMDCommonUtility
                                .convertObjectToString(objDataArray[18]));
                        objToolOutputServiceVO.setFinRuleId(RMDCommonUtility
                                .convertObjectToString(objDataArray[19]));
                        objToolOutputServiceVO.setStrRecomProb(RMDCommonUtility
                                .convertObjectToString(objDataArray[5]));
                        objToolOutputServiceVO.setStrRuleDefId(RMDCommonUtility
                                .convertObjectToString(objDataArray[15]));
						String anomObjid=RMDCommonUtility
								.convertObjectToString(objDataArray[13]);
						try{
							
						if(null!=objToolOutputServiceVO.getStrRecomId()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objToolOutputServiceVO.getStrRecomId())){
								RxVisualizationPlotInfoVO objRxVisualizationPlotInfoVO=visualizationDAOIntf.getVizPlotInformations(objToolOutputServiceVO.getStrRecomId());
								if(null!=objRxVisualizationPlotInfoVO){
									objToolOutputServiceVO.setPlotVizPresent(true);
								}else{
									objToolOutputServiceVO.setPlotVizPresent(false);
								}
						}
						if(null != dwSession&&null!=anomObjid&&dwSession.isConnected()){
							base64String=RMDCommonConstants.EMPTY_STRING;
							Query hibernateQuery1 = dwSession.createSQLQuery(dwBuildQuery
									.toString());	
							hibernateQuery1.setParameter(RMDCommonConstants.ANOM_OBJID, anomObjid);
							hibernateQuery1.setParameter(RMDCommonConstants.ASSET, assetNumber);
							
							blobLst = (ArrayList<Blob>) hibernateQuery1.list();
							byte[] buff = new byte[1024];
							 if(null != blobLst && !blobLst.isEmpty()){							
								Blob blob=blobLst.get(0);
								buff = blob.getBytes(1, (int)blob.length());
						        base64String =new String(Base64.encode(buff));
							}							
							objToolOutputServiceVO.setStrViz(base64String);							
						}
						
						
						}catch(GenericJDBCException e){
							LOG.error(
				                    "GenericJDBCException occurred while fetching SAS anom blob details:"+e);
						}catch(SQLException e){
							LOG.error(
				                    "SQL Exception occurred while fetching SAS anom blob details:"+e);
							throw e;
						}
						catch(Exception e){
							LOG.error(
				                    "Exception occurred while fetching SAS anom blob details:"+e);
							throw e;
						}
						 
                        if (null != objDataArray[6]) {
                            Date creationTime =  formatter
                                    .parse(RMDCommonUtility
                                            .convertObjectToString(objDataArray[6]));
                            objToolOutputServiceVO
                                    .setDtRxDelvDate(creationTime);
                        }
                        if (null != objDataArray[7]) {
                            objToolOutputServiceVO
                                    .setFaultCode(RMDCommonUtility
                                            .convertObjectToString(objDataArray[7]));
                        }
                        if (null != objDataArray[12]) {
                            objToolOutputServiceVO
                                    .setToolObjId(RMDCommonUtility
                                            .convertObjectToString(objDataArray[12]));
                        }
                        if (null != objDataArray[0]) {
                            String toolID = RMDCommonUtility
                                    .convertObjectToString(objDataArray[0]);
                            String arProb = RMDCommonUtility
                                    .convertObjectToString(objDataArray[5]);
                            objToolOutputServiceVO
                                    .setStrToolId(RMDCommonUtility
                                            .convertObjectToString(objDataArray[0]));

                            if (RMDCommonConstants.FC_AR_PROBABILITY
                                    .equals(arProb)) {
                                objToolOutputServiceVO
                                        .setFCFault(RMDCommonConstants.FALSE);
                                objToolOutputServiceVO
                                        .setStrToolIdDes(RMDCommonConstants.STR_FAULT);

                            } else {
                                if (toolID.equalsIgnoreCase(RMDCommonConstants.JDPAD))
                                    objToolOutputServiceVO
                                            .setStrToolIdDes(RMDCommonConstants.STR_RULE);

                                else
                                    objToolOutputServiceVO
                                            .setStrToolIdDes(RMDCommonUtility
                                                    .convertObjectToString(objDataArray[0]));
                            }
                        }
                        arlToolOutList.add(objToolOutputServiceVO);
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TOOL_OUT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getToolOutputDetails()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TOOL_OUT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
			releaseSession(dwSession);

        }
        return arlToolOutList;
    }

    /**
     * This method returns a count of Open FL work order and will be called while closing the Case will return the open work order count from the eservices. 
     * @param lmsLocoId
     * @return
     * @throws Exception
     */
        @Override
		public String getLmsLocoID(String caseId) throws RMDDAOException {
            LOG.debug("Start of getLmsLocoID method in CaseEoaDAOImpl");
            Session objSession = null;
            final StringBuilder qryLmsLocoID = new StringBuilder();
            String strLmsLocoId = RMDCommonConstants.EMPTY_STRING;
            BigDecimal lmsLocoId = null;
            try {
                objSession = getHibernateSession();

                qryLmsLocoID
                        .append(" SELECT LMS_LOCO_ID  FROM gets_rmd_cust_rnh_rn_v tv,table_Case tc "
                                + "WHERE case_prod2site_part =SITE_PART_OBJID and tc.id_number =:caseId ");                 
                Query hibernateQuery = objSession.createSQLQuery(qryLmsLocoID
                        .toString());
                hibernateQuery.setParameter(RMDCommonConstants.CASE_ID, caseId);
                lmsLocoId = (BigDecimal) hibernateQuery.uniqueResult();
                strLmsLocoId=String.valueOf(lmsLocoId);
            } catch (RMDDAOConnectionException ex) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                        RMDCommonConstants.FATAL_ERROR);

            } catch (Exception e) {
                LOG.error("Exception occured in getLmsLocoID()" + e);
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } finally {
                releaseSession(objSession);
            }
            return strLmsLocoId;
        }       
 

/**
 * This method returns a count of Open FL work order and will be called while closing the Case will return the open work order count from the eservices. 
 * @param lmsLocoId
 * @return
 * @throws Exception
 */
    @Override
	public int getOpenFLCount(String lmsLocoId) throws RMDDAOException {
        LOG.debug("Start of getOpenFLCount method in CaseEoaDAOImpl");
        Session objSession = null;
        final StringBuilder openFLCountQry = new StringBuilder();
        int countOpenFL  = RMDCommonConstants.INT_CONST_ZERO;
        try {
            objSession = getHibernateSession();

            openFLCountQry
                    .append(" SELECT COUNT(1) AS rowCount "
                            + "FROM gets_lms.gets_lms_service_workorder@ESERVICES.world a, "
                            + "gets_lms.gets_lms_locomotive_all@ESERVICES.world  c "
                            + "WHERE a.workorder_status_code = '300' and a.locomotive_id = c.locomotive_id "
                            + "and ((a.inshop_reason_code in ('FL','FL_CTL','FL_ENGINE','FL_FP','FL_MECH','FL_TM')) OR (a.inshop_reason_code = 'GR' and c.aar_road = 'CSX'))  "
                            + "and c.locomotive_type_code not in ('D7','GP38','GP40','MP15','S7','SD40','SWITCHER') "
                            + "and a.service_organization_id not in ('134','791','789','1429','790') "
                            + "and a.locomotive_id =:lmsLocoId");

            Query hibernateQuery = objSession.createSQLQuery(openFLCountQry
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.LMS_LOCO_ID, lmsLocoId);
            BigDecimal count = (BigDecimal) hibernateQuery.uniqueResult();
            countOpenFL = count.intValue();                     
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getOpenFLCount()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return countOpenFL;
}

    /**
     * @param caseId
     * @throws RMDDAOException
     */
    @Override
	public String getCaseTitle(String caseId) {

        Session objSession = getHibernateSession();
        final String strCaseObjidQuery = "SELECT tc.title FROM   TABLE_CASE tc "
                + "WHERE  tc.ID_NUMBER =:caseId";
        String strCaseObjid = null;
        try {
            final Query queryCaseObjid = objSession
                    .createSQLQuery(strCaseObjidQuery);
            queryCaseObjid.setParameter(RMDCommonConstants.CASEID, caseId);
            Object result = queryCaseObjid.uniqueResult();
            if (null != result) {
                strCaseObjid = result.toString();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getCaseTitle()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strCaseObjid;
    }

    @Override
    public String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) {
        Transaction transaction = null;
        Session session = null;

        StringBuilder updateRPRLQry = new StringBuilder();

        String fromCaseId = caseAppendServiceVO.getCaseId();
        String rxId = caseAppendServiceVO.getRxId();
        String[] rxArr = null;

        String ruleDefId = caseAppendServiceVO.getRuleDefId();
        String toolId = caseAppendServiceVO.getToolId();
        String assetGrpName = caseAppendServiceVO.getAssetGrpName();
        String customerId = caseAppendServiceVO.getCustomerId();
        String assetNumber = caseAppendServiceVO.getAssetNumber();

        int skipCount = caseAppendServiceVO.getSkipCount();
        String rxDelv = caseAppendServiceVO.getRxDelv();
        int arListObjId = 0;

        if (rxId != null) {
            rxArr = rxId.split(",");
        }

        session = getHibernateSession();
        transaction = session.getTransaction();

        /*************** Create Case Start ****************************************************/
        com.ge.trans.eoa.cm.framework.domain.User user = null;

        Query queueQry = null;
        Query selQry = null;

        StringBuilder queueQuery = new StringBuilder();
        StringBuilder clarifyQueueQuery = new StringBuilder();
        StringBuilder siteIDQuery = new StringBuilder();
        Long siteObjId = 0l;
        Long site2PartId = 0l;
        String queueObjid = "-1";
        String clarifyQueue = "";
        String siteId = "";
        String currentQueue = null;
        String caseTitle = null;
        String caseType = "";
        String idNumber = null;

        List<String> priSev = null;
        List<ViewLogVO> arlViewLogVOs = new ArrayList<ViewLogVO>();
        try {

            transaction.begin();
            long fromCaseObjId = Long.parseLong(getCaseObjid(session,
                    fromCaseId));
            if ((rxDelv != null && RMDCommonConstants.LETTER_Y.equalsIgnoreCase(rxDelv))
                    || (rxDelv != null && !RMDCommonConstants.LETTER_Y.equalsIgnoreCase(rxDelv) && skipCount != 1)) {
                LOG.debug("1.******************************getUserDetails***********************************************************");
                user = getUserDetails(caseAppendServiceVO.getUserId());

                LOG.debug("2.******************************Get Queue and urgency for the rx***********************************************************");
                if (rxId != null && rxId.indexOf(RMDCommonConstants.COMMMA_SEPARATOR) == -1) {
                    queueQuery
                            .append("SELECT NVL(RECOM_MOVE2QUEUE,-1),URGENCY FROM GETS_SD_RECOM WHERE OBJID =:rxObjid");
                    queueQry = session.createSQLQuery(queueQuery.toString());
                    queueQry.setParameter(RMDCommonConstants.RX_OBJID, rxId); // rxId
                    queueQry.setFetchSize(1);
                    List<Object[]> queueObjList = queueQry.list();
                    for (Object[] currentqueueObject : queueObjList) {
                        queueObjid = RMDCommonUtility
                                .convertObjectToString(currentqueueObject[0]);

                    }
                }
                LOG.debug("3.******************************If Queue is -1, Get default from GETS_RMD_LOOKUP***********************************************************");
                if ((("-1").equals(queueObjid))
                        || (queueObjid.trim().length() <= 0)) {
                    clarifyQueueQuery
                            .append("SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME ='MassApplyRxDefaultQueue' AND LOOK_STATE = 'Default'");
                    selQry = session.createSQLQuery(clarifyQueueQuery
                            .toString());
                    
                    selQry.setFetchSize(1);
                    List<Object[]> queueList = selQry.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                        clarifyQueue = RMDCommonUtility
                                .convertObjectToString(queueList.get(0));
                    }
                } else {
                    LOG.debug("3.******************************Select queue***********************************************************");
                    clarifyQueueQuery
                            .append("SELECT Q.TITLE FROM TABLE_QUEUE Q WHERE Q.OBJID =:queueObjId");
                    queueQry = session.createSQLQuery(clarifyQueueQuery
                            .toString());
                    queueQry.setParameter(RMDCommonConstants.QUEUE_OBJID,
                            queueObjid);
                    queueQry.setFetchSize(1);
                    List<Object[]> queueList = queueQry.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                        clarifyQueue = RMDCommonUtility
                                .convertObjectToString(queueList.get(0));
                    }
                }
                LOG.debug("4.******************************Get case Title and Type***********************************************************");
                caseTitle = getCaseTitle(fromCaseId);
                caseType = getCaseType(fromCaseId);
                LOG.debug("CaseType: " + caseType + "Case Title" + caseTitle);
                LOG.debug("5.******************************Get SITE INFO***********************************************************");
                siteIDQuery
                        .append("SELECT DISTINCT TRC.SITE_ID,TRC.LOC_OBJID,TRC.CON_OBJID  FROM TABLE_ROL_CONTCT TRC WHERE ORG_ID =:customerId AND S_FIRST_NAME ='AUTOGEN' ");
                queueQry = session.createSQLQuery(siteIDQuery.toString());
                queueQry.setParameter(RMDCommonConstants.CUSTOMER_ID, // CustomerId
                        customerId);
                queueQry.setFetchSize(1);
                List<Object[]> queueList = queueQry.list();
                for (Object[] currentObject : queueList) {
                    siteId = RMDCommonUtility
                            .convertObjectToString(currentObject[0]);
                    siteObjId = RMDCommonUtility
                            .convertObjectToLong(currentObject[1]);
                }

                if (RMDCommonConstants.SMPP.equals(caseType)) { // casetype
                    currentQueue = RMDCommonConstants.QUEUE_PINPOINT;
                } else if (RMDCommonConstants.COMMISSION.equals(caseType)) {
                    currentQueue = RMDCommonConstants.QUEUE_CASES_IN_PROCEESS;
                } else {
                    currentQueue = clarifyQueue;
                }
                // Creating Cases Start

                LOG.debug("6.******************************findWhereSiteObjidAndSerialEquals***********************************************************");
                com.ge.trans.eoa.cm.framework.domain.SitePartView sitePartView = tableScmViewDao
                        .findWhereSiteObjidAndSerialEquals(siteObjId,
                                assetNumber, assetGrpName);
                if (!sitePartView.isObjidNull()) {
                    site2PartId = sitePartView.getObjid();
                }
                Date date = Calendar.getInstance().getTime();
                CaseContent caseContent = new CaseContent();
                caseContent.setCreationTime(date);
                caseContent.setPhoneNum("9999");
                caseContent.setModifyStmp(date);
                caseContent.setCaseWip2wipbin(wipbinDao
                        .findWhereWipbinOwner2userEquals(user.getObjid())
                        .get(0).getObjid());
                caseContent.setCaseOriginator2user(user.getObjid());
                caseContent.setCalltype2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(caseType.toUpperCase()).get(0)
                        .getObjid());
                priSev = getCasePriorityServerity(fromCaseId);
                if (priSev != null) {

                    caseContent.setRespprty2gbstElm(Long.parseLong(priSev
                            .get(0)));
                    caseContent.setRespsvrty2gbstElm(Long.parseLong(priSev
                            .get(1)));

                }
                LOG.debug("Priorty" + caseContent.getRespprty2gbstElm());
                LOG.debug("SEV" + caseContent.getRespsvrty2gbstElm());

                caseContent.setCaseProd2sitePart(site2PartId);
                caseContent.setCasePrt2partInfo(sitePartView.getPartnumObjid());
                caseContent.setCaseOwner2user(user.getObjid());
                Contact objContact = contactDao.findWhereSFirstNameEquals(
                        RMDCommonConstants.CONTACT_AUTOGEN).get(0);
                caseContent.setCaseReporter2contact(objContact.getObjid());
                Site objSite = tableSiteDao.findWhereSiteIdEquals(siteId)
                        .get(0);
                caseContent.setCase2address(objSite.getCustPrimaddr2address());
                caseContent.setTitle(caseTitle);// "OLD CASE TITILE
                caseContent.setSTitle(caseTitle.toUpperCase());
                caseContent.setCaseReporter2site(objSite.getObjid());
                caseContent.setCaseCurrq2queue(queueDao
                        .findWhereTitleEquals(currentQueue).get(0).getObjid());
                /* Get case objid from sequence */
                LOG.debug("7.******************************findWhereSiteObjidAndSerialEquals***********************************************************");
                Long caseObjId = caseDao.getCaseIdSequenceNextValue();
                caseContent.setObjid(caseObjId);
                /* Generate caseId number */
                String caseIdNumber = generateCaseIdNumberNextValue();
                idNumber = caseIdNumber;
                caseContent.setIdNumber(caseIdNumber);
                /* Create condition to open */
                Condition condition = new Condition();
                condition.setObjid(caseContent.getObjid());

                condition.setCondition(10);
                condition.setTitle("Open-Dispatch");

                condition.setSequenceNum(0);
                condition.setDispatchTime(caseContent.getCreationTime());
                condition.setQueueTime(caseContent.getCreationTime());
                condition.setWipbinTime(caseContent.getCreationTime());
                condition.setFirstRespTime(caseContent.getCreationTime());
                LOG.debug("8.******************************Inserting into Condition***********************************************************");
                conditionDao.insert(condition);
                /* Insert case */
                caseContent
                        .setCasests2gbstElm(RMDCommonConstants.STATUS_OPEN_GBSTELM_ID);
                caseContent.setCaseState2condition(condition.getObjid());
                LOG.debug("9.******************************Inserting into table case***********************************************************");
                caseDao.insert(caseContent);
                /* Create Activity Entry log */
                ActEntry actEntry = new ActEntry();
                actEntry.setActCode(RMDCommonConstants.TABLE_ACT_ENTRY_CREATE_CASE);
                actEntry.setEntryTime(Calendar.getInstance().getTime());
                actEntry.setActEntry2case(caseContent.getObjid());
                actEntry.setActEntry2user(caseContent.getCaseOwner2user());
                actEntry.setAddnlInfo(RMDCommonConstants.CONTACT
                        + objContact.getFirstName()
                        + RMDCommonConstants.EMPTY_STRING
                        + objContact.getLastName()
                        + RMDCommonConstants.PRIORITY);
                if (!caseContent.isCaseWip2wipbinNull()) {
                    actEntry.setRemoved(0);
                    actEntry.setFocusType(0);
                    actEntry.setFocusLowid(0);
                }
                actEntry.setEntryName2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(RMDCommonConstants.CREATE)
                        .get(0).getObjid());
                actEntryDao.insert(actEntry);
                /* Update Note Log */
                String notes = RMDCommonConstants.MASSAPPLY_NOTES
                        + caseContent.getIdNumber()
                        + RMDCommonConstants.NOT_SYMBOL;
                caseServiceAPI.insertNotesLog(caseContent, notes, user);
                /* Insert into Act Entry for WIP to queue */
                ActEntry actEntryInProcess = new ActEntry();
                actEntryInProcess
                        .setActCode(RMDCommonConstants.TABLE_ACT_ENTRY_IN_PROCESS);
                actEntryInProcess.setEntryTime(date);
                String additionalInfo = RMDCommonConstants.FROM_WIP_DEFAULT_TO_QUEUE
                        + currentQueue;
                actEntryInProcess.setAddnlInfo(additionalInfo);
                if (!caseContent.isCaseWip2wipbinNull()) {
                    actEntryInProcess.setRemoved(0);
                    actEntryInProcess.setFocusType(0);
                    actEntryInProcess.setFocusLowid(0);
                }
                actEntryInProcess.setActEntry2case(caseContent.getObjid());
                actEntryInProcess.setActEntry2user(user.getObjid());

                List<GbstElm> gbstElmList = gbstElmDao
                        .findWhereTitleEquals(RMDCommonConstants.STR_NOTES);
                if (gbstElmList != null && !gbstElmList.isEmpty()) {
                    actEntryInProcess.setEntryName2gbstElm(gbstElmList.get(0)
                            .getObjid());
                }
                LOG.debug("10.******************************Inserting into table act entry***********************************************************");
                actEntryDao.insert(actEntryInProcess);

                // Create Case End

                /*************** Moving Tool Output **********************************************************/

                LOG.debug("FromCase OBJID: " + fromCaseObjId);
                LOG.debug("ToCase OBJID: " + caseObjId);
                LOG.debug("11.******************************Getting Vehicle ObjId***********************************************************");
                String strVehicleId = getCustrnhDetails(customerId,
                        assetNumber, assetGrpName);
                if (strVehicleId == null) {
                    LOG.debug("Vehicle Id is null");
                    throw new Exception();
                }

                LOG.debug("vehicleObjId=" + strVehicleId);

                List<String> faultObjIds = new ArrayList<String>();
                List<BigDecimal> faultListJDPAD = null;

                List<BigDecimal> faultListCBR = null;

                if (toolId != null && !RMDCommonConstants.Fault.equalsIgnoreCase(toolId)) {
                    // Update MTM FAULTS table
                    // Get FaultObjids for a rule_defn and then query it

                    LOG.debug("15.******************************Inserting into MTM FAULTS***********************************************************");
                    LOG.debug("                15A.******************************Get FaultObjids for a rule_defn and then query it *****************");

                    StringBuilder findFaultJDPADQry = new StringBuilder();
                    StringBuilder findFaultCBRQry = new StringBuilder();
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {

                        findFaultJDPADQry.append("SELECT DISTINCT gtft.objid FROM GETS_RMD.GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC, GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR, ")
                        .append("GETS_RMD.GETS_TOOL_CASE_MTM_FAULT mtm,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF ,GETS_RMD.GETS_TOOL_FLTDOWN GTFD ,GETS_RMD.GETS_TOOL_FAULT GTFT WHERE ")
                        .append("TC.objid = :caseObjId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ")
                        .append("AND GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN  = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND mtm.mtm2fault = gtft.objid AND ")
                        .append("Fault2vehicle = :vehicleObjId AND Mtm.Mtm2case  = Tc.Objid AND GTRL.rprldwn2rule_defn = :ruleDefId");

                    } else if (toolId != null && (toolId.equals(RMDCommonConstants.CBR))) {
                        LOG.debug("findFaultCBRQry" + findFaultCBRQry);
                        findFaultCBRQry
                                .append(" select  mtm2fault from gets_tool_case_mtm_fault where mtm2case=:caseObjId ");
                        findFaultCBRQry.append(" and cbr_used='Y' ");

                    }

                    Query findFaultHQry = null;

                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        findFaultHQry = session
                                .createSQLQuery(findFaultJDPADQry.toString());
                        findFaultHQry.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, fromCaseObjId);
                        findFaultHQry.setParameter(
                                RMDCommonConstants.RULE_DEFID, ruleDefId);
                        findFaultHQry
                                .setParameter(
                                        RMDCommonConstants.VEHICLE_OBJ_ID,
                                        strVehicleId);
                        LOG.debug("vehicleObjId" + strVehicleId);
                        LOG.debug("ruleDefId" + ruleDefId);
                        LOG.debug("caseObjId" + fromCaseObjId);
                        faultListJDPAD = findFaultHQry.list();
                    } else if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                        findFaultHQry = session.createSQLQuery(findFaultCBRQry
                                .toString());
                        findFaultHQry.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, fromCaseObjId);
                        faultListCBR = findFaultHQry.list();

                    }

                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {

                        for (BigDecimal currentFault : faultListJDPAD) {

                            if (currentFault != null && !faultObjIds.contains(currentFault.toString())) {
                                    faultObjIds.add(String
                                            .valueOf(currentFault));                             
                            }
                        }
                    } else if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                        LOG.debug("Adding for CBR");
                        for (BigDecimal currentFault : faultListCBR) {
                            if (currentFault != null) {
                                LOG.info("currentFaultObjId " + currentFault);
                                if (!faultObjIds.contains(currentFault.toString())) {
                                    LOG.info("adding to fault list "
                                            + currentFault);
                                    faultObjIds.add(String
                                            .valueOf(currentFault));
                                }
                            }
                        }

                    }
                    /** delete from RPRL_DWN table **/
                    if (toolId != null && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        LOG.debug("12.******************************Deleting from GETS_TOOL_RPRLDWN***********************************************************");
                        LOG.debug("moveTooloutput- AR_LIST2CASE " + fromCaseObjId);
                        LOG.debug("moveTooloutput -AR_LIST2RECOM: " + rxId);

                        updateRPRLQry
                                .append("DELETE FROM GETS_TOOL_RPRLDWN where RPRLDWN2AR_LIST=:currarListObjId");

                        Query updateRPRLHQry = session
                                .createSQLQuery(updateRPRLQry.toString());

                        updateRPRLHQry.setParameter(
                                RMDCommonConstants.AR_CURR_OBJID,
                                caseAppendServiceVO.getToolObjId());
                        updateRPRLHQry.executeUpdate();
                        LOG.debug("Deleted from GETS_TOOL_RPRLDWN");
                    }
                }
                /** Update gets_tool_ar_list table FOR RXs **/
                // This is to insert into arlist table for jpad and cbr
                if (toolId != null && !toolId.equals(RMDCommonConstants.Fault)) {
                    StringBuilder updateArListQry = new StringBuilder();

                    LOG.debug("13.******************************Updating GETS_TOOL_AR_LIST for Rx***********************************************************");

                    Query seqHQry = session.createSQLQuery(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");

                    arListObjId = ((BigDecimal) seqHQry.uniqueResult())
                            .intValue();

                    if (caseObjId == 0 || fromCaseObjId == 0
                            || arListObjId == 0) {
                        LOG.debug("moveToolOutPut - ToCase || FromCase || arListObjId is 0");
                        throw new Exception();
                    }
                   
                    LOG.debug("MoveTooloutput method - arListObjId " + arListObjId);
                    
                    updateArListQry.append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE,OBJID= :arListObjId  WHERE ")
                    .append("AR_LIST2CASE =:fromCaseObjId AND AR_LIST2RECOM= :rxObjid AND OBJID= :currarListObjId AND TOOL_ID= :toolId");
                    
                    
                    Query updateArListhQry = session
                            .createSQLQuery(updateArListQry.toString());
                    updateArListhQry.setParameter(
                            RMDCommonConstants.TO_CASE_OBJID, caseObjId);
                    updateArListhQry.setParameter(
                            RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                            arListObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);

                    updateArListhQry.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID,
                            caseAppendServiceVO.getToolObjId());
                    if (toolId != null
                            && (toolId
                                    .equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                                    .equalsIgnoreCase(RMDCommonConstants.CBR))) {

                        if (toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.CBR);
                        else if (toolId
                                .equalsIgnoreCase(RMDCommonConstants.RULE))
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.JDPAD);
                    }
                    updateArListhQry.executeUpdate();
                    LOG.debug("MoveTooloutput method - Updated GETS_TOOL_AR_LIST");
                }

                /** Update gets_tool_ar_list table FOR faults */
                // All faults are inserted in ar_list against the same case
                else {

                    for (int i = 0; i < rxArr.length; i++) {
                        StringBuilder updateArListQry = new StringBuilder();
                        rxId = rxArr[i];
                        LOG.debug("13.******************************Updating GETS_TOOL_AR_LIST for Faults***********************************************************");
                        LOG.debug("rxId" + rxId);
                        
                        Query seqHQry = session.createSQLQuery(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");

                        arListObjId = ((BigDecimal) seqHQry.uniqueResult())
                                .intValue();
                        LOG.debug("MoveTooloutput method - New AR_LIST OBJID " + arListObjId);

                        if (caseObjId == 0 || fromCaseObjId == 0
                                || arListObjId == 0) {
                            LOG.debug("ToCase || FromCase || arListObjId is 0");
                            throw new Exception();
                        }
                       
                        LOG.debug("AR_LIST2CASE " + fromCaseObjId);
                        LOG.debug("FromCase " + fromCaseObjId);
                        
                        updateArListQry.append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE,CREATION_DATE=SYSDATE, ")
                        .append("OBJID= :arListObjId WHERE AR_LIST2CASE =:fromCaseObjId and objid =:rxObjid");
                        
                        Query updateArListhQry = session
                                .createSQLQuery(updateArListQry.toString());
                        updateArListhQry.setParameter(
                                RMDCommonConstants.TO_CASE_OBJID, caseObjId);
                        updateArListhQry.setParameter(
                                RMDCommonConstants.FROM_CASE_OBJID,
                                fromCaseObjId);
                        updateArListhQry.setParameter(
                                RMDCommonConstants.AR_OBJID, arListObjId);
                        // this is the ar_list old obj id to update
                        updateArListhQry.setParameter(
                                RMDCommonConstants.RX_OBJID, rxId);

                        updateArListhQry.executeUpdate();

                        LOG.debug("Updated GETS_TOOL_AR_LIST for faults");
                    }

                }
                /** Insert records with new ar_list ids into RPRLDWN table **/
                if (toolId != null
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.Fault)
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                    LOG.debug("14.******************************Insert records with new ar_list ids into RPRLDWN  table ***********************************************************");                  
                    LOG.debug("movetoolouput-toolId " + toolId);
                    LOG.debug("movetoolouput-arListObjId " + arListObjId);
                    LOG.debug("movetoolouput-ruleDefId " + ruleDefId);
                    
                    StringBuilder insertGetsToolRprldwnQuery = new StringBuilder()
                    .append("INSERT INTO GETS_TOOL_RPRLDWN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,TOOL_ID, RPRLDWN2AR_LIST, RPRLDWN2RULE_DEFN) ")
                    .append("VALUES (GETS_TOOL_RPRLDWN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,'OMD_Append',:toolId,:arListObjId,:ruleDefId)");

                    Query hibernateQuery = session.createSQLQuery(insertGetsToolRprldwnQuery.toString());

                    hibernateQuery.setParameter(RMDCommonConstants.TOOLID,
                            RMDCommonConstants.JDPAD);
                    hibernateQuery.setParameter(RMDCommonConstants.AR_OBJID,
                            arListObjId);
                    hibernateQuery.setParameter(RMDCommonConstants.RULE_DEFID,
                            ruleDefId);
                    hibernateQuery.executeUpdate();
                    LOG.debug("Inserted into GETS_TOOL_RPRLDWN");

                }
                
                // Insert each fault into mtm_faults for that case
                if (toolId != null
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.Fault)) {

                    // Start inserting
                    LOG.debug("                5b.******************************Inserting faultobjid Into MTM_FAULTS *****************");
                    LOG.debug("toCaseObjId  " + caseObjId);
                    
                    StringBuilder  insertGetsToolCaseMtmFaultQuery = new StringBuilder()
                    .append("INSERT INTO GETS_TOOL_CASE_MTM_FAULT (OBJID,LAST_UPDATED_BY, LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,MTM2CASE,MTM2FAULT,CBR_USED,JDPAD_USED) ")
                    .append("VALUES(GETS_TOOL_CASE_MTM_FAULT_SEQ.NEXTVAL,:lastUpdatedBy,sysdate,:lastUpdatedBy,sysdate,:caseId, :faultCode, :CBR,:JDPAD)");
                  
                    Query mtmInsertHQry = session.createSQLQuery(insertGetsToolCaseMtmFaultQuery.toString());                  
                    Query countFaultHQryJdpad = session
                            .createSQLQuery("SELECT COUNT(1) FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE=:caseId AND MTM2FAULT=:faultCode ");

                    if (faultObjIds != null) {

                        for (String fault : faultObjIds) {

                            countFaultHQryJdpad.setParameter(
                                    RMDCommonConstants.CASE_ID, caseObjId);
                            countFaultHQryJdpad.setParameter(
                                    RMDCommonConstants.FAULTSCODE, fault);

                            int fltCntJdpad = ((BigDecimal) countFaultHQryJdpad
                                    .uniqueResult()).intValue();

                            LOG.debug("Inserting fault  " + fault);
                            LOG.debug("caseId  " + caseObjId);
                            
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.LAST_UPDATED,
                                    "OMD_Append");
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.CASE_ID, caseObjId);
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.FAULTSCODE, fault);
                            if (toolId != null
                                    && toolId
                                            .equalsIgnoreCase(RMDCommonConstants.RULE)
                                    && fltCntJdpad <= 0) {

                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.JDPAD, "Y");

                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.CBR, null);
                                mtmInsertHQry.executeUpdate();
                            } else
                            // CBR
                            if (toolId != null
                                    && toolId
                                            .equalsIgnoreCase(RMDCommonConstants.CBR)
                                    && fltCntJdpad <= 0) {
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.JDPAD, null);

                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.CBR, "Y");
                                mtmInsertHQry.executeUpdate();

                            }

                        }

                    }                  

                    /** Added to add Min and Max faults to gets_tool_run table **/
                    if (toolId != null
                            && (toolId
                                    .equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                                    .equalsIgnoreCase(RMDCommonConstants.CBR))) {

                        BigDecimal maxFault = null;
                        BigDecimal minFault = null;
                        BigDecimal maxFaultInsert = null;
                        BigDecimal minFaultInsert = null;

                        if ((null != faultListJDPAD
                                && !faultListJDPAD.isEmpty()) ||(null != faultListCBR && !faultListCBR.isEmpty())) {
                            if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                                maxFault = Collections.max(faultListJDPAD);
                                minFault = Collections.min(faultListJDPAD);
                            }else if(toolId.equalsIgnoreCase(RMDCommonConstants.CBR)){
                                maxFault = Collections.max(faultListCBR);
                                minFault = Collections.min(faultListCBR);
                            }
                            maxFaultInsert = maxFault;
                            minFaultInsert = minFault;
                            LOG.debug(MIN_FAULT_OBJID + minFault);
                            LOG.debug(MAX_FAULT_OBJID + maxFault);
                           

                            String strDiagServiceId = getDiagServiceID(session,
                                    fromCaseId, caseType);
                            
                            StringBuilder insertGetsToolRunQuery= new StringBuilder()
                            .append("INSERT INTO GETS_TOOL_RUN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RUN_PRIORITY,RUN_START,RUN_CPT,RUN_PROCESSEDMIN_OBJID,")
                            .append("RUN_PROCESSEDMAX_OBJID,RUN2CASE,RUN2VEHICLE,RUN_CASE_REQ_BY,QUIET_ON,DIAG_SERVICE_ID) VALUES(GETS_TOOL_RUN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,")
                            .append("'OMD_Append',4,sysdate,sysdate,:minFaultObjId,:maxFaultObjId,:caseId,:vehicleObjId,NULL,NULL,:diagServiceId)");
                            
                            Query runInsertHQry = session
                                    .createSQLQuery(insertGetsToolRunQuery.toString());
                            runInsertHQry.setParameter(
                                    RMDCommonConstants.VEHICLE_OBJ_ID,
                                    strVehicleId);
                            runInsertHQry.setParameter(
                                    RMDCommonConstants.CASE_ID, caseObjId);
                            runInsertHQry.setParameter(MAX_FAULT_OBJID,
                                    maxFaultInsert);
                            runInsertHQry.setParameter(MIN_FAULT_OBJID,
                                    minFaultInsert);
                            runInsertHQry.setParameter(
                                    RMDCommonConstants.DIAG_SERVICE_ID,
                                    strDiagServiceId);
                            runInsertHQry.executeUpdate();
                        }

                    }                  
                }
                transaction.commit();
            }// If tooloutput has to remain in same case
            else {
                /***
                 * transaction.begin(); //Remove index 0 from faultsarray
                 * if(toolId!=null && toolId.equalsIgnoreCase("Fault")){
                 * 
                 * LOG.debug("rxArr after removing"+rxArr);
                 * 
                 * LOG.info(
                 * "1.******************************getUserDetails***********************************************************"
                 * ); user = getUserDetails(caseAppendServiceVO.getUserId());
                 * 
                 * 
                 * 
                 * 
                 * LOG.info(
                 * "3.******************************If Queue is -1, Get default from GETS_RMD_LOOKUP***********************************************************"
                 * ); if ((queueObjid.equals("-1")) ||
                 * (queueObjid.trim().length() <= 0)) { clarifyQueueQuery
                 * .append(
                 * "SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME ='MassApplyRxDefaultQueue' AND LOOK_STATE = 'Default'"
                 * ); selQry = session.createSQLQuery(clarifyQueueQuery
                 * .toString());
                 * //queueQry.setParameter(RMDCommonConstants.QUEUE_OBJID,
                 * //queueObjid); selQry.setFetchSize(1); List<Object[]>
                 * queueList = selQry.list(); if
                 * (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                 * ClarifyQueue = RMDCommonUtility
                 * .convertObjectToString(queueList.get(0)); } } else {
                 * LOG.info(
                 * "3.******************************Select queue***********************************************************"
                 * ); clarifyQueueQuery .append(
                 * "SELECT Q.TITLE FROM TABLE_QUEUE Q WHERE Q.OBJID =:queueObjId"
                 * ); queueQry = session.createSQLQuery(clarifyQueueQuery
                 * .toString());
                 * queueQry.setParameter(RMDCommonConstants.QUEUE_OBJID,
                 * queueObjid); queueQry.setFetchSize(1); List<Object[]>
                 * queueList = queueQry.list(); if
                 * (RMDCommonUtility.isCollectionNotEmpty(queueList)) {
                 * ClarifyQueue = RMDCommonUtility
                 * .convertObjectToString(queueList.get(0)); } } LOG.info(
                 * "4.******************************Get case Title and Type***********************************************************"
                 * ); caseTitle=getCaseTitle(fromCaseId);
                 * caseType=getCaseType(fromCaseId); LOG.info("CaseType: " +
                 * caseType + "Case Title"+caseTitle); LOG.info(
                 * "5.******************************Get SITE INFO***********************************************************"
                 * ); siteIDQuery .append(
                 * "SELECT DISTINCT TRC.SITE_ID,TRC.LOC_OBJID,TRC.CON_OBJID  FROM TABLE_ROL_CONTCT TRC WHERE ORG_ID =:customerId AND FIRST_NAME='AutoGen'"
                 * ); queueQry = session.createSQLQuery(siteIDQuery.toString());
                 * queueQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                 * //CustomerId customerId); queueQry.setFetchSize(1);
                 * List<Object[]> queueList = queueQry.list(); for (Object[]
                 * currentObject : queueList) { siteId = RMDCommonUtility
                 * .convertObjectToString(currentObject[0]); siteObjId =
                 * RMDCommonUtility .convertObjectToLong(currentObject[1]); }
                 * 
                 * if (RMDCommonConstants.SMPP.equals(caseType)) { //casetype
                 * currentQueue = RMDCommonConstants.QUEUE_PINPOINT; } else if
                 * (RMDCommonConstants.COMMISSION.equals(caseType)) {
                 * currentQueue = RMDCommonConstants.QUEUE_CASES_IN_PROCEESS; }
                 * else { currentQueue = ClarifyQueue; } // Creating Cases Start
                 * 
                 * //ViewLogVO objViewLogVO = new ViewLogVO(); LOG.info(
                 * "6.******************************findWhereSiteObjidAndSerialEquals***********************************************************"
                 * ); com.ge.trans.eoa.cm.framework.domain.SitePartView
                 * sitePartView = tableScmViewDao
                 * .findWhereSiteObjidAndSerialEquals(siteObjId, assetNumber,
                 * assetGrpName); if (!sitePartView.isObjidNull()) { site2PartId
                 * = sitePartView.getObjid(); } Date date =
                 * Calendar.getInstance().getTime(); CaseContent caseContent =
                 * new CaseContent(); caseContent.setCreationTime(date);
                 * caseContent.setPhoneNum("9999");
                 * caseContent.setModifyStmp(date);
                 * caseContent.setCaseWip2wipbin(wipbinDao
                 * .findWhereWipbinOwner2userEquals(user.getObjid())
                 * .get(0).getObjid());
                 * caseContent.setCaseOriginator2user(user.getObjid());
                 * caseContent.setCalltype2gbstElm(gbstElmDao
                 * .findWhereSTitleEquals( caseType.toUpperCase())
                 * .get(0).getObjid());
                 * priSev=getCasePriorityServerity(fromCaseId); if(priSev!=null)
                 * {
                 * 
                 * caseContent.setRespprty2gbstElm(Long.parseLong(priSev.get(0))
                 * );
                 * caseContent.setRespsvrty2gbstElm(Long.parseLong(priSev.get(
                 * 1)));
                 * 
                 * } LOG.info("Priorty"+caseContent.getRespprty2gbstElm());
                 * LOG.info("SEV"+caseContent.getRespsvrty2gbstElm());
                 * 
                 * //caseContent.setRespsvrty2gbstElm(gbstElmDao //
                 * .findWhereSTitleEquals(objMassApplyRxVO.getSeverity())
                 * //shikha // .get(0).getObjid());// MEDIUM
                 * caseContent.setCaseProd2sitePart(site2PartId);
                 * caseContent.setCasePrt2partInfo
                 * (sitePartView.getPartnumObjid());
                 * caseContent.setCaseOwner2user(user.getObjid()); Contact
                 * objContact = contactDao.findWhereSFirstNameEquals(
                 * RMDCommonConstants.CONTACT_AUTOGEN).get(0);
                 * caseContent.setCaseReporter2contact(objContact.getObjid());
                 * Site objSite = tableSiteDao.findWhereSiteIdEquals(siteId)
                 * .get(0);
                 * caseContent.setCase2address(objSite.getCustPrimaddr2address
                 * ()); caseContent.setTitle(caseTitle);// "OLD CASE TITILE
                 * caseContent.setSTitle(caseTitle.toUpperCase());
                 * caseContent.setCaseReporter2site(objSite.getObjid());
                 * caseContent.setCaseCurrq2queue(queueDao
                 * .findWhereTitleEquals(currentQueue).get(0).getObjid());
                 * 
                 * LOG.info(
                 * "7.******************************findWhereSiteObjidAndSerialEquals***********************************************************"
                 * ); Long caseObjId = caseDao.getCaseIdSequenceNextValue();
                 * caseContent.setObjid(caseObjId);
                 * 
                 * String caseIdNumber = generateCaseIdNumberNextValue();
                 * idNumber=caseIdNumber; caseContent.setIdNumber(caseIdNumber);
                 * 
                 * Condition condition = new Condition();
                 * condition.setObjid(caseContent.getObjid()); //
                 * condition.setCondition(objMassApplyRxVO.getCaseCondition());
                 * //shikha condition.setTitle(caseTitle);
                 * condition.setSTitle(caseTitle.toUpperCase());
                 * condition.setSequenceNum(0);
                 * condition.setDispatchTime(caseContent.getCreationTime());
                 * condition.setQueueTime(caseContent.getCreationTime());
                 * condition.setWipbinTime(caseContent.getCreationTime());
                 * condition.setFirstRespTime(caseContent.getCreationTime());
                 * LOG.info(
                 * "8.******************************Inserting into Condition***********************************************************"
                 * ); conditionDao.insert(condition);
                 * 
                 * caseContent .setCasests2gbstElm(RMDCommonConstants.
                 * STATUS_OPEN_GBSTELM_ID);
                 * caseContent.setCaseState2condition(condition.getObjid());
                 * LOG.info(
                 * "9.******************************Inserting into table case***********************************************************"
                 * ); caseDao.insert(caseContent);
                 * 
                 * ActEntry actEntry = new ActEntry();
                 * actEntry.setActCode(RMDCommonConstants
                 * .TABLE_ACT_ENTRY_CREATE_CASE);
                 * actEntry.setEntryTime(Calendar.getInstance().getTime());
                 * actEntry.setActEntry2case(caseContent.getObjid());
                 * actEntry.setActEntry2user(caseContent.getCaseOwner2user());
                 * actEntry.setAddnlInfo(RMDCommonConstants.CONTACT +
                 * objContact.getFirstName() + RMDCommonConstants.EMPTY_STRING +
                 * objContact.getLastName() + RMDCommonConstants.PRIORITY); if
                 * (!caseContent.isCaseWip2wipbinNull()) {
                 * actEntry.setRemoved(0); actEntry.setFocusType(0);
                 * actEntry.setFocusLowid(0); }
                 * actEntry.setEntryName2gbstElm(gbstElmDao
                 * .findWhereSTitleEquals(RMDCommonConstants.CREATE)
                 * .get(0).getObjid()); actEntryDao.insert(actEntry);
                 * 
                 * String notes = RMDCommonConstants.MASSAPPLY_NOTES +
                 * caseContent.getIdNumber() + RMDCommonConstants.NOT_SYMBOL;
                 * caseServiceAPI.insertNotesLog(caseContent, notes, user);
                 * 
                 * ActEntry actEntryInProcess = new ActEntry();
                 * actEntryInProcess
                 * .setActCode(RMDCommonConstants.TABLE_ACT_ENTRY_IN_PROCESS);
                 * actEntryInProcess.setEntryTime(date); String additionalInfo =
                 * RMDCommonConstants.FROM_WIP_DEFAULT_TO_QUEUE + currentQueue;
                 * actEntryInProcess.setAddnlInfo(additionalInfo); if
                 * (!caseContent.isCaseWip2wipbinNull()) {
                 * actEntryInProcess.setRemoved(0);
                 * actEntryInProcess.setFocusType(0);
                 * actEntryInProcess.setFocusLowid(0); }
                 * actEntryInProcess.setActEntry2case(caseContent.getObjid());
                 * actEntryInProcess.setActEntry2user(user.getObjid());
                 * 
                 * List<GbstElm> gbstElmList = gbstElmDao
                 * .findWhereTitleEquals(RMDCommonConstants.STR_NOTES); if
                 * (gbstElmList != null && !gbstElmList.isEmpty()) {
                 * actEntryInProcess.setEntryName2gbstElm(gbstElmList.get(0)
                 * .getObjid()); } LOG.info(
                 * "10.******************************Inserting into table act entry***********************************************************"
                 * ); actEntryDao.insert(actEntryInProcess);
                 * 
                 * 
                 * for(int i=1;i< rxArr.length; i++){ StringBuilder
                 * updateArListQry = new StringBuilder(); rxId=rxArr[i];
                 * LOG.info(
                 * "13.******************************Updating GETS_TOOL_AR_LIST for Faults***********************************************************"
                 * ); LOG.info("rxId"+rxId); StringBuilder seqQry = new
                 * StringBuilder(); seqQry.append(
                 * "SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");
                 * 
                 * Query seqHQry = session.createSQLQuery(seqQry.toString());
                 * 
                 * arListObjId = ((BigDecimal)
                 * seqHQry.uniqueResult()).intValue();
                 * LOG.info("New AR_LIST OBJID "+ arListObjId);
                 * 
                 * 
                 * if(caseObjId==0 || fromCaseObjId==0 || arListObjId==0){
                 * LOG.info("ToCase || FromCase || arListObjId is 0"); throw new
                 * Exception(); } LOG.info("ToCase(AR_LIST2CASE) "+ caseObjId);
                 * LOG.info("arListObjId "+ arListObjId);
                 * LOG.info("AR_LIST2CASE "+ fromCaseObjId);
                 * LOG.info("FromCase "+ fromCaseObjId); LOG.info("rxObjid "+
                 * rxId); updateArListQry.append(
                 * "UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , "
                 * ); updateArListQry.append(
                 * "LAST_UPDATED_DATE=SYSDATE, OBJID= :arListObjId "); //rxobjid
                 * is from sd_recom updateArListQry.append(
                 * "WHERE AR_LIST2CASE =:fromCaseObjId ");
                 * updateArListQry.append(" AND OBJID= :rxObjid"); Query
                 * updateArListhQry =
                 * session.createSQLQuery(updateArListQry.toString());
                 * updateArListhQry
                 * .setParameter(RMDCommonConstants.TO_CASE_OBJID, caseObjId);
                 * updateArListhQry
                 * .setParameter(RMDCommonConstants.FROM_CASE_OBJID,
                 * fromCaseObjId);
                 * updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                 * arListObjId); //this is the ar_list old obj id to update
                 * updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
                 * rxId); updateArListhQry.executeUpdate();
                 * 
                 * LOG.debug("Updated GETS_TOOL_AR_LIST" ); }
                 * 
                 * } transaction.commit(); }
                 **/
            }
        }

        catch (RMDDAOConnectionException ex) {
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            transaction.rollback();
            LOG.error("Exception occurred in moveToolOutput:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }

        return idNumber;
    }

    /**
     * @param caseId
     * @throws RMDDAOException
     */
    public String getCaseType(String caseId) {

        Session objSession = getHibernateSession();
        final String strCaseObjidQuery = "SELECT GB.TITLE FROM   TABLE_CASE tc, TABLE_GBST_ELM GB  "
                + "WHERE  tc.ID_NUMBER =:caseId AND TC.CALLTYPE2GBST_ELM= GB.OBJID";
        String strCaseObjid = null;
        try {
            final Query queryCaseObjid = objSession
                    .createSQLQuery(strCaseObjidQuery);

            queryCaseObjid.setParameter("caseId", ESAPI.encoder().encodeForSQL(ORACLE_CODEC,caseId));
            Object result = queryCaseObjid.uniqueResult();
            if (null != result) {
                strCaseObjid = result.toString();
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getCaseTitle()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strCaseObjid;
    }

    /**
     * @param caseId
     * @throws RMDDAOException
     */
    public List<String> getCasePriorityServerity(String caseId) {

        Session objSession = getHibernateSession();
        final String strCaseObjidQuery = "SELECT GB1.objid AS PRIORITY, GB2.objid AS SEVERITY FROM TABLE_CASE TC, TABLE_GBST_ELM GB1, "
                + " TABLE_GBST_ELM GB2 WHERE TC.ID_NUMBER=:caseId "
                + " AND TC.respprty2gbst_elm= GB1.OBJID "
                + " AND TC.respsvrty2gbst_elm= GB2.OBJID ";

        List<String> strArr = new ArrayList();
        try {
            final Query queryCaseObjid = objSession
                    .createSQLQuery(strCaseObjidQuery);
            queryCaseObjid.setParameter(RMDCommonConstants.CASEID, caseId);
            queryCaseObjid.setFetchSize(10);
            List<String[]> result = queryCaseObjid.list();
            String priority = null;
            String severity = null;
            if (RMDCommonUtility.isCollectionNotEmpty(result)) {
                final Iterator<String[]> iter = result.iterator();
                if (iter.hasNext()) {
                    final Object[] prioritySeverityArr = (Object[]) iter.next();
                    priority = RMDCommonUtility
                            .convertObjectToString(prioritySeverityArr[0]);
                    severity = RMDCommonUtility
                            .convertObjectToString(prioritySeverityArr[1]);
                    strArr.add(0, priority);
                    strArr.add(1, severity);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getCasePriorityServerity()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strArr;
    }

    @Override
	public String saveToolOutputActEntry(ToolOutputActEntryVO objActEntryVO)
            throws RMDDAOException {
        Session session = getHibernateSession();
        com.ge.trans.eoa.cm.framework.domain.User user = null;
        String result = RMDCommonConstants.FAILURE;
        try {
            user = getUserDetails(objActEntryVO.getUserName());
            ActEntry actEntry = new ActEntry();
            List<String> manualCaseIds = objActEntryVO.getManualcaseID();
            String strManualCaseId = RMDCommonConstants.EMPTY_STRING;

            List<String> rxTitles = objActEntryVO.getCaseTitle();
            String strRxTitle = RMDCommonConstants.EMPTY_STRING;
            String addnlInfo = RMDCommonConstants.EMPTY_STRING;

            int size = 0;

            if (null != manualCaseIds) {
                Iterator<String> iter = manualCaseIds.iterator();
                size = manualCaseIds.size();
                strManualCaseId = StringUtils.join(iter, ',');

            }

            if (null != rxTitles) {
                Iterator<String> iter2 = rxTitles.iterator();
                strRxTitle = StringUtils.join(iter2, ',');
            }

            if (size == 1) {
                addnlInfo = "The manual Case ID  is : " + strManualCaseId
                        + ". The output delivered was : " + strRxTitle;
            } else if (size > 1) {

                addnlInfo = "The manual Case ID are : " + strManualCaseId
                        + ". and The outputs delivered are : " + strRxTitle
                        + " respectively";
            }
            StringBuilder rankQuery = new StringBuilder();
            Long rank = 0L;
            Long elmObjid = 0L;
            rankQuery
                    .append("select objid , rank from table_gbst_elm where s_title='CASE SPLIT'");
            Query hibernateQuery = session.createSQLQuery(rankQuery.toString());
            hibernateQuery.setFetchSize(1);
            List<Object[]> rankQueryList = hibernateQuery.list();
            for (Object[] currentqueueObject : rankQueryList) {
                rank = RMDCommonUtility
                        .convertObjectToLong(currentqueueObject[1]);
                elmObjid = RMDCommonUtility
                        .convertObjectToLong(currentqueueObject[0]);
            }

            if (addnlInfo != null && addnlInfo.length() > 255) {
                String truncAddnlInfoNew = addnlInfo.substring(1, 255);
                addnlInfo = truncAddnlInfoNew;

            }

            StringTokenizer st4 = new StringTokenizer(addnlInfo, "'");
            if (st4 != null) {
                StringBuilder buf = new StringBuilder("");
                int cnt = 0;
                while (st4.hasMoreTokens()) {
                    String temp = st4.nextToken();
                    if (cnt == 0) {
                        buf = new StringBuilder(temp);
                    } else {
                        buf.append("''");
                        buf.append(temp);
                    }
                    cnt++;
                }
                addnlInfo = buf.toString();
            }

            actEntry.setActCode(rank);
            actEntry.setEntryTime(Calendar.getInstance().getTime());
            actEntry.setActEntry2case(Long.valueOf(objActEntryVO
                    .getParentCaseObjId()));
            actEntry.setActEntry2user(user.getObjid());
            actEntry.setAddnlInfo(addnlInfo);
            actEntry.setEntryName2gbstElm(elmObjid);
            actEntryDao.insert(actEntry);
            List<String> manualCaseObjids = objActEntryVO.getArlCaseObjid();
            for (String caseId : manualCaseObjids) {

                ActEntry parentActEntry = new ActEntry();
                String addnlInfoNew = "The Parent Case ID for this Manual Case is: "
                        + objActEntryVO.getParentCaseID();

                if (addnlInfoNew.length() > 255) {
                    String truncAddnlInfo = addnlInfoNew.substring(1, 255);
                    addnlInfoNew = truncAddnlInfo;

                }

                parentActEntry.setActCode(rank);
                parentActEntry.setEntryTime(Calendar.getInstance().getTime());
                parentActEntry.setActEntry2case(Long.valueOf(caseId));
                parentActEntry.setActEntry2user(user.getObjid());
                parentActEntry.setAddnlInfo(addnlInfoNew);

                parentActEntry.setEntryName2gbstElm(gbstElmDao
                        .findWhereSTitleEquals(RMDCommonConstants.CREATE)
                        .get(0).getObjid());
                actEntryDao.insert(parentActEntry);
            }

            result = RMDCommonConstants.SUCCESS;

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {
            LOG.error("Exception occured in getCasePriorityServerity()" + e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return result;
    }

    public List<String> getRxForCase(String caseObjid) throws RMDDAOException {
        Session session = null;
        StringBuilder selectedSolQuery = new StringBuilder();
        List<String> caseDetailsVOsList = new ArrayList<String>();        
        try {
            session = getHibernateSession();
            selectedSolQuery
                    .append("SELECT TO_CHAR(SDCASE.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),SDRECOM.OBJID, SDRECOM.TITLE, SDCASE.URGENCY, SDCASE.EST_REPAIR_TIME,SDCASE.VERSION,");
            selectedSolQuery
                    .append("SDCASE.OB_MSG_ID, SDCASE.REISSUE_FLAG, TO_CHAR(SDDELV.DELV_DATE,'MM/DD/YYYY HH24:MI:SS'),");
            selectedSolQuery
                    .append("SDRECOM.RECOM_TYPE FROM GETS_SD_RECOM SDRECOM,GETS_SD_RECOM_DELV SDDELV,");
            selectedSolQuery
                    .append("GETS_SD_CASE_RECOM SDCASE WHERE SDCASE.CASE_RECOM2RECOM_DELV  = SDDELV.OBJID  ");
            selectedSolQuery
                    .append("(+) AND SDCASE.CASE_RECOM2RECOM = SDRECOM.OBJID AND SDCASE.CASE_RECOM2CASE =:caseObjId ORDER BY 1 ASC");
            
            Query selectedSolHQry = session.createSQLQuery(selectedSolQuery
                    .toString());
            selectedSolHQry.setParameter("caseObjId",
            		StringEscapeUtils.escapeSql(caseObjid));
            selectedSolHQry.setFetchSize(10);
            List<Object[]> selectedSolutions = selectedSolHQry.list();
            for (Object[] objCurrentSol : selectedSolutions) {
                caseDetailsVOsList.add(RMDCommonUtility
                        .convertObjectToString(objCurrentSol[1]));
            }
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getRxForCase",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return caseDetailsVOsList;
    }

    /**
     * @param RepairCodeEoaDetailsVO
     * @return
     * @throws RMDDAOException
     * @Description To get all the repair codes
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCodeId)
            throws RMDDAOException {

        Session objSession = null;
        List<RepairCodeEoaDetailsVO> repairCodeEoaDetailsList = new ArrayList<RepairCodeEoaDetailsVO>();
        try {
            LOG.debug("Begin getRepairCodes method of CaseEoaDAOImpl");
            objSession = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT DISTINCT OBJID, REPAIR_CODE, REPAIR_DESC FROM GETS_SD_REPAIR_CODES");

            if (repairCodeId != null) {
                caseQry.append(" WHERE repair_code IN(:repairCode)");
            }
            caseQry.append(" ORDER BY REPAIR_CODE DESC");
            Query caseHqry = objSession.createSQLQuery(caseQry.toString());
            if (repairCodeId != null) {
                caseHqry.setParameter(RMDCommonConstants.REPAIR_CODES,
                        repairCodeId);
            }

            List<Object[]> repairCodeResult = caseHqry.list();
            for (Object[] currentRepairCode : repairCodeResult) {
                RepairCodeEoaDetailsVO repairCode = new RepairCodeEoaDetailsVO();
                repairCode.setRepairCodeId(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[0]));
                repairCode.setRepairCode(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[1]));
                repairCode.setRepairCodeDesc(RMDCommonUtility
                        .convertObjectToString(currentRepairCode[2]));
                repairCodeEoaDetailsList.add(repairCode);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getRepairCodes method of CaseEoaDAOImpl");
        return repairCodeEoaDetailsList;
    }

    @Override
    public String moveDeliverToolOutput(String userId, String currCaseId,
            String newCaseId, String rxId, String assetNumber,
            String assetGrpName, String customerId, String ruleDefId,
            String toolId, String caseType, String toolObjId)
            throws RMDDAOException {
        Transaction transaction = null;
        Session session = null;
        int arListObjId = 0;
        String result = RMDCommonConstants.FAILURE;
        List<BigDecimal> lstFault = null;
        String tempJDPADUsed = RMDCommonConstants.EMPTY_STRING;
        String tempCBRUsed = RMDCommonConstants.EMPTY_STRING;
        String mtmFault = null;
        String strToolId = "";
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            transaction.begin();

            long newCaseObjId = Long
                    .parseLong(getCaseObjid(session, newCaseId));
            long currCaseObjId = Long.parseLong(getCaseObjid(session,
                    currCaseId));
            LOG.debug("FromCase OBJID: and ToCase OBJID" + currCaseId + "");
            LOG.debug("ToCase OBJID: " + newCaseObjId);

            String strVehicleId = getCustrnhDetails(customerId, assetNumber,
                    assetGrpName);

            LOG.debug("VehicleObJID ::: ");
            LOG.debug("Fetching arlistobjId for current case");

            if (!RMDCommonUtility.isNullOrEmpty(toolId)
                    && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                StringBuilder selectCBRFaultQry = new StringBuilder();
                /**
                 * selectCBRFaultQry.append(
                 * "SELECT MTM2FAULT FROM TABLE_CASE TC , GETS_TOOL_CASE_MTM_FAULT MTM ,GETS_TOOL_FAULT FLT "
                 * + "WHERE ID_NUMBER = :caseId AND MTM2CASE = TC.OBJID " +
                 * "AND FLT.OBJID = MTM2FAULT AND FLT.FAULT2RULE_DEFN IS NULL");
                 */
                strToolId = RMDCommonConstants.CBR;
                selectCBRFaultQry
                        .append("SELECT MTM2FAULT FROM TABLE_CASE TC , GETS_TOOL_CASE_MTM_FAULT MTM "
                                + "WHERE ID_NUMBER = :caseId AND MTM2CASE = TC.OBJID");
                Query selectCBRFaultHQry = session
                        .createSQLQuery(selectCBRFaultQry.toString());

                selectCBRFaultHQry.setParameter(RMDCommonConstants.CASE_ID,
                        currCaseId);
                lstFault = selectCBRFaultHQry.list();
                tempJDPADUsed = "";
                tempCBRUsed = "Y";
            } else {
                tempJDPADUsed = "Y";
                tempCBRUsed = "";
                strToolId = RMDCommonConstants.JDPAD;
                StringBuilder selectJDPADFaultQry = new StringBuilder();
                selectJDPADFaultQry
                        .append("select distinct(gtft.objid) from GETS_TOOL_AR_LIST GTAR,TABLE_CASE TC, GETS_TOOL_RPRLDWN GTRL, "
                                + "GETS_TOOL_DPD_RULEDEF GTDR,GETS_TOOL_DPD_FINRUL GTDF,GETS_TOOL_CASE_MTM_FAULT mtm, "
                                + "GETS_TOOL_FLTDOWN GTFD,GETS_TOOL_FAULT GTFT "
                                + "WHERE TC.ID_NUMBER =:caseId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' "
                                + "AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND  GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN AND "
                                + "GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN = GTDR.OBJID "
                                + "AND GTFT.OBJID = GTFD.fltdown2fault and mtm.mtm2fault = gtft.objid and fault2vehicle = :vehicleObjId "
                                + "and GTRL.rprldwn2rule_defn= :ruleDefId and mtm.mtm2case = tc.objid ");

                Query selectJDPADFaultHQry = session
                        .createSQLQuery(selectJDPADFaultQry.toString());

                selectJDPADFaultHQry.setParameter(RMDCommonConstants.CASE_ID,
                        currCaseId);
                selectJDPADFaultHQry.setParameter(
                        RMDCommonConstants.VEHICLE_OBJ_ID, strVehicleId);
                selectJDPADFaultHQry.setParameter(
                        RMDCommonConstants.RULE_DEFID, ruleDefId);

                LOG.debug("JDPAD FAult query ::: "
                        + selectJDPADFaultQry.toString());
                lstFault = selectJDPADFaultHQry.list();
                LOG.debug("Hqry " + selectJDPADFaultHQry);

            }
            

            String strDiagServiceId = getDiagServiceID(session, currCaseId,
                    caseType);

            StringBuilder getfaultQuery = new StringBuilder();
            getfaultQuery
                    .append("Select Min(MTM2FAULT),Max(MTM2FAULT) from GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE = :caseObjId");

            Query getFaultHQry = session.createSQLQuery(getfaultQuery
                    .toString());
            getFaultHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    currCaseObjId);

            List<Object[]> mtm2FaultList = getFaultHQry.list();

            String strMinObjid = null;
            String strMaxObjid = null;
            if (RMDCommonUtility.isCollectionNotEmpty(mtm2FaultList)) {
                int index = 0;
                if (index < mtm2FaultList.size()) {
                    Object[] data = mtm2FaultList.get(index);

                    strMinObjid = RMDCommonUtility
                            .convertObjectToString(data[0]);
                    strMaxObjid = RMDCommonUtility
                            .convertObjectToString(data[1]);
                }
            }

            LOG.info("Size of mtm2FaultList  " + mtm2FaultList);

            StringBuilder selectRPRLDWNQry = new StringBuilder();
            selectRPRLDWNQry
                    .append("Select OBJID  from GETS_TOOL_RPRLDWN where RPRLDWN2AR_LIST=:currarListObjId");
            Query selectRPRLHQry = session.createSQLQuery(selectRPRLDWNQry
                    .toString());
            selectRPRLHQry.setParameter(RMDCommonConstants.AR_CURR_OBJID,
                    toolObjId);
            BigDecimal rprlDwnObjId = (BigDecimal) selectRPRLHQry
                    .uniqueResult();

            LOG.debug("Step 1: inserting into GETS_TOOL_AR_LIST starts");

            Query seqHQry = session.createSQLQuery(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");

            arListObjId = ((BigDecimal) seqHQry.uniqueResult()).intValue();
            LOG.debug("moveDeliverToolOutput method - New AR_LIST OBJID " + arListObjId);

            StringBuilder updateArListQry = new StringBuilder();
            updateArListQry
                    .append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , ");
            updateArListQry
                    .append("LAST_UPDATED_DATE=SYSDATE, CREATION_DATE=SYSDATE ,OBJID= :arListObjId ");
            updateArListQry.append("WHERE AR_LIST2CASE =:fromCaseObjId");
            updateArListQry.append(" AND AR_LIST2RECOM= :rxObjid");
            updateArListQry.append(" AND OBJID=:currarListObjId ");
            updateArListQry.append(" AND tool_id= :toolId");

            Query updateArListhQry = session.createSQLQuery(updateArListQry
                    .toString());
            updateArListhQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                    newCaseObjId);
            updateArListhQry.setParameter(RMDCommonConstants.FROM_CASE_OBJID,
                    currCaseObjId);
            updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                    arListObjId);
            updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID, rxId);
            updateArListhQry.setParameter(RMDCommonConstants.AR_CURR_OBJID,
                    toolObjId);
            updateArListhQry.setParameter(RMDCommonConstants.TOOLID, strToolId);
            updateArListhQry.executeUpdate();
            LOG.debug("Step 1: inserting into GETS_TOOL_AR_LIST ends");

            if (null != rprlDwnObjId) {
                LOG.debug("Step 2: inserting into GETS_TOOL_RPRLDWN starts");
                StringBuilder updateRPRLDWNQry = new StringBuilder();
                updateRPRLDWNQry
                        .append("Update GETS_TOOL_RPRLDWN set RPRLDWN2AR_LIST = :arListObjId  Where objid = :objId");

                Query updateRPRLHQry = session.createSQLQuery(updateRPRLDWNQry
                        .toString());
                updateRPRLHQry.setParameter(RMDCommonConstants.AR_OBJID,
                        arListObjId);
                updateRPRLHQry.setParameter("objId", rprlDwnObjId);
                updateRPRLHQry.executeUpdate();
                LOG.debug("Step 2: inserting into GETS_TOOL_RPRLDWN ends");
            }
            LOG.debug("Step 3: inserting into gets_tool_case_mtm_fault starts");
            if (null != lstFault && !lstFault.isEmpty()) {

                for (BigDecimal currentFault : lstFault) {
                    mtmFault = String.valueOf(currentFault);
                   
                    StringBuilder insertFaultMtmQry = new StringBuilder();
                    insertFaultMtmQry
                            .append("insert into gets_tool_case_mtm_fault(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY, "
                                    + " MTM2FAULT,MTM2CASE,CBR_USED,JDPAD_USED ) "
                                    + "VALUES(GETS_TOOL_CASE_MTM_FAULT_SEQ.NEXTVAL,sysdate,:lastUpdatedBy,sysdate,:lastUpdatedBy,:faultCode,:caseId,:CBR,:JDPAD)");

                    Query mtmInsertHQry = session
                            .createSQLQuery(insertFaultMtmQry.toString());
                    mtmInsertHQry.setParameter(RMDCommonConstants.LAST_UPDATED,
                            userId);
                    mtmInsertHQry.setParameter(RMDCommonConstants.CASE_ID,
                            newCaseObjId);
                    mtmInsertHQry.setParameter(RMDCommonConstants.FAULTSCODE,
                            mtmFault);
                    mtmInsertHQry.setParameter(RMDCommonConstants.CBR,
                            tempCBRUsed);
                    mtmInsertHQry.setParameter(RMDCommonConstants.JDPAD,
                            tempJDPADUsed);
                    mtmInsertHQry.executeUpdate();
                    LOG.info("Inserted Fault  " + mtmFault);
                    LOG.debug("Step 3: inserting into gets_tool_case_mtm_fault ends");
                }
            }

            LOG.debug("Step 4: inserting into gets_tool_run Starts");
            if (mtm2FaultList != null && !mtm2FaultList.isEmpty()
                    && !RMDCommonUtility.isNullOrEmpty(strDiagServiceId)) {
                StringBuilder insertToolRun = new StringBuilder();

                insertToolRun
                        .append("INSERT into Gets_tools.gets_tool_run (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RUN_PRIORITY,RUN_START, "
                                + "RUN_CPT,RUN_PROCESSEDMIN_OBJID, RUN_PROCESSEDMAX_OBJID, RUN2CASE, RUN2VEHICLE,DIAG_SERVICE_ID) "
                                + "VALUES(Gets_tools.gets_tool_run_seq.nextval,SYSDATE,'CaseSplit',SYSDATE,'CaseSplit', 4 , SYSDATE,SYSDATE,:minFaultObjId,:maxFaultObjId,:caseObjId,:vehicleObjId,:diagServiceId)");

                Query insertToolRunHQry = session.createSQLQuery(insertToolRun
                        .toString());
                insertToolRunHQry.setParameter(MIN_FAULT_OBJID, strMinObjid);
                insertToolRunHQry.setParameter(MAX_FAULT_OBJID, strMaxObjid);
                insertToolRunHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                        newCaseObjId);
                insertToolRunHQry.setParameter(
                        RMDCommonConstants.VEHICLE_OBJ_ID, strVehicleId);
                insertToolRunHQry.setParameter(
                        RMDCommonConstants.DIAG_SERVICE_ID, strDiagServiceId);
                insertToolRunHQry.executeUpdate();
            }
            transaction.commit();

            result = RMDCommonConstants.SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            transaction.rollback();
            LOG.error("Exception occurred in moveDeliverToolOutput:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }

        return result;
    }

    /**
     * This method is used check if the case has an active RX
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    @Override
    public boolean activeRxExistsInCase(String caseId) throws RMDDAOException {

        Session objSession = null;
        int delvCount = 0;
        boolean acitveRxExists = false;

        try {

            objSession = getHibernateSession();

            StringBuilder rxQry = new StringBuilder();
            rxQry.append("SELECT count(DISTINCT RX_CASE_ID) FROM GETS_SD_FDBKPNDG_V f, GETS_sd_recom_delv d");
            rxQry.append(" WHERE d.recom_delv2case= f.CASE_OBJID");
            rxQry.append(" AND d.recom_delv2RECOM= f.RECOM_OBJID");
            rxQry.append(" AND f.CASE_SUCCESS IS NULL");
            rxQry.append(" and f.id_number IN(:caseObjId)");
            Query recomQry = objSession.createSQLQuery(rxQry.toString());
            recomQry.setParameter(RMDCommonConstants.CASE_OBJ_ID, caseId);

            delvCount = ((BigDecimal) recomQry.uniqueResult()).intValue();
            if (delvCount > 0)
                acitveRxExists = true;

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getDeliveredRxs method of CaseEoaDAOImpl");

        return acitveRxExists;
    }

    /**
     * This method is used check if any rx is delivered within a unit
     * 
     * @param
     * @throws RMDDAOException
     */
     @Override
    public Map<String,List<String>> getEnabledUnitRxsDeliver(String customerId,
            String assetGroupName, String assetNumber, String caseId,
            String caseType,String currentUser) throws RMDDAOException {
        Session session = null;
        List<String> caselist = new ArrayList<String>();
        List<String> rxIdList = new ArrayList<String>();
        List<Object[]> resultList;
        List<String> arRxList;
        List<String> vehBomRxList;
        List<String> vehBomResult;
        Map<String,List<String>> resultMap =null;
        StringBuilder caseOwnerQuery = null;
        try {
            LOG.debug("Starts getEnabledUnitRxsDeliver method of CaseEoaDAOImpl");
            session = getHibernateSession();

            //US173891 - For conditional Rx check user story -- checking if any Rx is delivered against current case
            CaseAppendServiceVO caseAppendServiceVO = new CaseAppendServiceVO();
            caseAppendServiceVO.setCaseId(caseId);
            List<String> delivRxIds = getDeliveredRxs(caseAppendServiceVO);

			// Getting RxId's from AR_LIST which are not delivered against
			// current case and RXIDs which are not delivered against current
			// case chekcing urgency <> 'C'
			
			String caseObjId = getCaseObjid(session, caseId);

			StringBuilder arlistQry = new StringBuilder();
			arlistQry
					.append("SELECT AR_LIST2RECOM,GETS_SD_RECOM.URGENCY FROM GETS_TOOL_AR_LIST,GETS_SD_RECOM WHERE FC_FAULT IS NULL "
							+ "AND AR_LIST2CASE in (:caseObjId) "
							+ "AND GETS_TOOL_AR_LIST.AR_LIST2RECOM=GETS_SD_RECOM.OBJID ");
			if(!CollectionUtils.isEmpty(delivRxIds)){
				arlistQry
				.append(" AND AR_LIST2RECOM  NOT IN (:rxId)");
			}
			
			Query arlistHQry = session.createSQLQuery(arlistQry.toString());
			
			arlistHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID, ESAPI
					.encoder().encodeForSQL(ORACLE_CODEC, caseObjId));
			if(!CollectionUtils.isEmpty(delivRxIds)){
				arlistHQry.setParameterList(RMDCommonConstants.RXID,
					delivRxIds);
			}
			
			resultList = arlistHQry.list();
             if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                 resultMap=new LinkedHashMap<String, List<String>>();
                 arRxList=new ArrayList<String>();
                 vehBomRxList=new ArrayList<String>();
                 for(Object[] obj : resultList){
                     
                     if(!vehBomRxList.contains(RMDCommonUtility.convertObjectToString(obj[0])))
                         vehBomRxList.add(RMDCommonUtility.convertObjectToString(obj[0]));
                     
                     if(!RMDCommonConstants.LETTER_C.equalsIgnoreCase(RMDCommonUtility.convertObjectToString(obj[1]))){
                         arRxList.add(RMDCommonUtility.convertObjectToString(obj[0]));
                     }
                 }
                 
               //Fetching 30 days cases    
                 if(!CollectionUtils.isEmpty(arRxList)){
                     FindCaseServiceVO objFindCaseServiceVO = new FindCaseServiceVO();
                     objFindCaseServiceVO.setStrAssetGrpName(assetGroupName);
                     objFindCaseServiceVO.setStrAssetNumber(assetNumber);
                     objFindCaseServiceVO.setStrCustomerId(customerId);
                     objFindCaseServiceVO.setCaseTypes(caseType);
                     objFindCaseServiceVO.setAppendFlag(RMDCommonConstants.LETTER_Y);            
                     objFindCaseServiceVO.setNoOfDays("30");
                     List<SelectCaseHomeVO> lstSelectCaseHomeVO = getAssetCases(objFindCaseServiceVO);

                     if (null != lstSelectCaseHomeVO && !lstSelectCaseHomeVO.isEmpty()) {
                        
                          for(SelectCaseHomeVO selectCaseHomeVO : lstSelectCaseHomeVO)                               
                             caselist.add(selectCaseHomeVO.getStrCaseId());
                     
                         caselist.remove(caseId);
                         LOG.debug("Previous CaseID ::: " + caselist);
                         if (!caselist.isEmpty()) {                                     
                                 StringBuilder activeRxQry = new StringBuilder();
                                 activeRxQry
                                         .append("SELECT DISTINCT recom_delv2RECOM FROM GETS_SD_FDBKPNDG_V f, GETS_sd_recom_delv d");
                                 activeRxQry
                                         .append(" WHERE d.recom_delv2case= f.CASE_OBJID");
                                 activeRxQry
                                         .append(" AND d.recom_delv2RECOM= f.RECOM_OBJID");
                                 activeRxQry.append(" AND d.recom_delv2RECOM IN(:rxId) AND f.id_number IN(:caseId)");

                                 Query rxListHQry = session.createSQLQuery(activeRxQry
                                         .toString());
                                 rxListHQry.setParameterList(RMDCommonConstants.RXID,
                                         arRxList);
                                 rxListHQry.setParameterList(RMDCommonConstants.CASE_ID,
                                         caselist);                   
                                 List<BigDecimal> devRxList = rxListHQry.list();         
                             if (RMDCommonUtility.isCollectionNotEmpty(devRxList)) {

                                 for (BigDecimal obj : devRxList) {
                                     rxIdList.add(obj.toString());
                                 }
                                 
                                 vehBomRxList.removeAll(rxIdList);
                                 
                                 resultMap.put(RMDCommonConstants.DISABLED_RX_LIST,rxIdList);

                             }  
                                 
                             }
                     }
                 
                 }
                 //here you go
                 if(RMDCommonUtility.isCollectionNotEmpty(vehBomRxList)){
                     String strRxObjId = StringUtils.join(vehBomRxList.iterator(), RMDCommonConstants.COMMMA_SEPARATOR);
                     String fromScreen=RMDCommonConstants.TOOL_OUT_PUT;
                     String caseOwnerId=null;
                     String caseCondition=null;
                     Object[] obj=null;
                     if(null!=currentUser && !RMDCommonConstants.NOT_A_CM_USER.equals(currentUser) && !RMDCommonConstants.CASE_TYPE_RF_TRIGGER.equalsIgnoreCase(caseType)){
                         caseOwnerQuery=new StringBuilder();
                         caseOwnerQuery.append("SELECT LOGIN_NAME,TABLE_CONDITION.TITLE FROM TABLE_CASE,TABLE_USER,TABLE_CONDITION WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_USER.OBJID= TABLE_CASE.CASE_OWNER2USER AND TABLE_CASE.ID_NUMBER=:caseId");
                         Query caseOwnerHQuery = session.createSQLQuery(caseOwnerQuery.toString());
                         caseOwnerHQuery.setParameter(RMDCommonConstants.CASEID, caseId);
                         obj = (Object[]) caseOwnerHQuery.list().get(0);
                         caseOwnerId=RMDCommonUtility.convertObjectToString(obj[0]);
                         caseCondition=RMDCommonUtility.convertObjectToString(obj[1]);
                         if(currentUser.equals(caseOwnerId) && !RMDCommonConstants.CLOSED.equalsIgnoreCase(caseCondition)){
                             vehBomResult=validateVehBoms(customerId,assetGroupName,assetNumber,
                                     strRxObjId,fromScreen);
                             resultMap.put(RMDCommonConstants.BOM_MISMATCH_RX_LIST,vehBomResult);
                         }
                     }
                     
                 }
             }                      
        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getEnabledUnitRxsDeliver()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);

        }
        LOG.debug("Ends getEnabledUnitRxsDeliver method of CaseEoaDAOImpl");
        return resultMap;
    }

    public String getDiagServiceID(Session session, String caseId,
            String caseType) throws RMDDAOException {

        StringBuilder getrunDetailsQry = new StringBuilder();
        getrunDetailsQry
                .append("Select distinct svctoflt.Diag_Service_Id from table_case tc , gets_rmd_vehicle veh, gets_tool_case_mtm_fault mtm, gets_tool_fault fault, gets_rmd_svc_to_fault svctoflt");
        getrunDetailsQry
                .append(" Where fault.objid = mtm.mtm2fault and tc.objid  = mtm.mtm2case and fault.record_type = svctoflt.fault_record_type and tc.id_number = :caseId");
        getrunDetailsQry
                .append(" and fault.fault2vehicle = veh.objid and svctoflt.case_problem_type = :caseType and rownum<=1");

        Query getRunHQry = session.createSQLQuery(getrunDetailsQry.toString());
        getRunHQry.setParameter(RMDCommonConstants.CASE_ID, caseId);
        getRunHQry.setParameter(RMDCommonConstants.PARAM_CASE_TYPE, caseType);
        return (String) getRunHQry.uniqueResult();

    }

    public String getDiagServiceIDAppend(Session session, long caseobjId)
            throws RMDDAOException {

        StringBuilder getrunDetailsQry = new StringBuilder();
        getrunDetailsQry
                .append(" Select distinct Diag_Service_Id from gets_tool_run");
        getrunDetailsQry.append(" Where run2case = :caseId");
        getrunDetailsQry.append(" and diag_service_id is not null  and rownum<=1");

        Query getRunHQry = session.createSQLQuery(getrunDetailsQry.toString());
        getRunHQry.setParameter(RMDCommonConstants.CASE_ID, caseobjId);
        return (String) getRunHQry.uniqueResult();

    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDDAOException
     * @Description:This method is for updating case title
     */

    @Override
    public String updateCaseTitle(final FindCaseServiceVO findCaseServiceVO)
            throws RMDDAOException {
        Session objSession = null;
        String result = RMDCommonConstants.FAILURE_MSG;

        try {

            objSession = getHibernateSession();
            StringBuilder caseUpdateQry = new StringBuilder();
            caseUpdateQry.append("UPDATE  TABLE_CASE  SET");
            caseUpdateQry.append(" TITLE=:caseTitle");
            caseUpdateQry.append(" WHERE ID_NUMBER=:caseId");
            Query caseHqry = objSession
                    .createSQLQuery(caseUpdateQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASE_ID,
                    findCaseServiceVO.getCaseID());

            if (null != findCaseServiceVO.getStrCaseTitle()
                    && !findCaseServiceVO.getStrCaseTitle().isEmpty()) {
                caseHqry.setParameter(RMDCommonConstants.CASE_TITLE,
                        findCaseServiceVO.getStrCaseTitle());
                caseHqry.executeUpdate();
            }

            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_CASE_DETIALS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(objSession);
        }

        return result;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDDAOException
     * @Description:This method return the cases for that asset by invoking
     *                   database
     */
    @Override
    public List<SelectCaseHomeVO> getHeaderSearchCases(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<SelectCaseHomeVO> selectCaseHomeVOList = new ArrayList<SelectCaseHomeVO>();
        SelectCaseHomeVO objSelectCaseHomeVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            session = getHibernateSession();
            strQuery.append("SELECT TABLE_CASE.OBJID TCOBJID,");
            strQuery.append("TABLE_CASE.ID_NUMBER ID_NUMBER,");
            strQuery.append("TABLE_CASE.TITLE CASE_TITLE,");
            strQuery.append("TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CREATION_TIME,");
            strQuery.append("TABLE_CASE.IS_SUPERCASE IS_SUPERCASE,");
            strQuery.append("TABLE_SITE_PART.X_VEH_HDR X_VEH_HDR,");
            strQuery.append("TABLE_SITE_PART.SERIAL_NO SERIAL_NO,");
            strQuery.append("TABLE_SITE_PART.S_SERIAL_NO S_SERIAL_NO,");
            strQuery.append("TABLE_BUS_ORG.ORG_ID ORG_ID,");
            strQuery.append("TABLE_USER.LOGIN_NAME OWNER_LOGIN_NAME");
            strQuery.append(" FROM TABLE_CASE,TABLE_SITE_PART,TABLE_BUS_ORG,TABLE_USER,GETS_RMD_VEHICLE,GETS_RMD_VEH_HDR");
            strQuery.append(" WHERE TABLE_SITE_PART.OBJID= GETS_RMD_VEHICLE.VEHICLE2SITE_PART AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID");
            strQuery.append(" AND TABLE_SITE_PART.OBJID = TABLE_CASE.CASE_PROD2SITE_PART");
            strQuery.append(" AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER");

            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                strQuery.append(" AND TABLE_BUS_ORG.ORG_ID IN (:customer) ");

            }

            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                strQuery.append(" AND TABLE_CASE.ID_NUMBER = UPPER(:caseId) ");
            }
            query = session.createSQLQuery(strQuery.toString());

            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                List<String> customerList = Arrays.asList(objFindCaseServiceVO
                        .getStrCustomerId().split(
                                RMDCommonConstants.COMMMA_SEPARATOR));
                query.setParameterList(RMDServiceConstants.STR_CUSTOMER,
                        customerList);
            }

            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {

                query.setParameter(RMDCommonConstants.CASEID,
                        objFindCaseServiceVO.getCaseID());

            }
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {

                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {

                    objSelectCaseHomeVO = new SelectCaseHomeVO();
                    final Object[] caseHome =  obj.next();

                    objSelectCaseHomeVO.setStrTitle(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    objSelectCaseHomeVO.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));

                    if (null != RMDCommonUtility
                            .convertObjectToString(caseHome[3])) {
                        Date creationDate =  formatter
                                .parse(RMDCommonUtility
                                        .convertObjectToString(caseHome[3]));
                        objSelectCaseHomeVO.setDtCreationDate(creationDate);
                    }

                    objSelectCaseHomeVO.setCaseObjid(RMDCommonUtility
                            .convertObjectToLong(caseHome[0]));
                    objSelectCaseHomeVO.setStrOwner(RMDCommonUtility
                            .convertObjectToString(caseHome[9]));
                    objSelectCaseHomeVO.setStrAssetNumber(RMDCommonUtility
                            .convertObjectToString(caseHome[6]));
                    objSelectCaseHomeVO.setStrAssetHeader(RMDCommonUtility
                            .convertObjectToString(caseHome[5]));
                    objSelectCaseHomeVO.setStrcustomerName(RMDCommonUtility
                            .convertObjectToString(caseHome[8]));
                    selectCaseHomeVOList.add(objSelectCaseHomeVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getHeaderSearchCases()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);

        }

        return selectCaseHomeVOList;

    }

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param : caseObjId,vehicleObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get B2BRx Status, EserviceRxStatus.
     * 
     */
    @Override
    public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId)
            throws RMDDAOException {
        RecommDetailsVO objRecommDetailsVO = null;
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        Session session = null;
        try {
            session = getHibernateSession();
            objConnection = getConnection(session);
            callableStmt = objConnection
                    .prepareCall("BEGIN gets_sd_rec_pend_pkg.pending_alert_pr(?,?,?,?); END;");
            callableStmt.setString(1, vehicleObjId);
            callableStmt.setString(2, caseObjId);
            callableStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            callableStmt.execute();
            String b2bRxStatus = callableStmt.getString(3);
            String eServiceRxStatus = callableStmt.getString(4);

            if (!RMDCommonUtility.checkNull(b2bRxStatus)
                    && !RMDCommonUtility.checkNull(eServiceRxStatus)) {
                objRecommDetailsVO = new RecommDetailsVO();
                objRecommDetailsVO.setB2BRxStatus(b2bRxStatus);
                objRecommDetailsVO.seteServiceRxStatus(eServiceRxStatus);
            }
            
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getRxDetails()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GETRX_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            try {
                if(null !=  callableStmt)
                    callableStmt.close();
            } catch (SQLException e) {
                LOG.debug("Exception occured while closing the Callable Statement in getRxDetails() :"+e);
            }
            try {
                if(null !=  objConnection)
                    objConnection.close();
            } catch (SQLException e) {
                LOG.error("Exception occured while closing the Connection in getRxDetails() :"+e);
            }
            releaseSession(session);
        }

        return objRecommDetailsVO;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param : caseObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get Vehicle Configuration group
     *               Details of the given Case/Asset.
     * 
     */
    @Override
    public List<String> getVehConfigGroupForCase(String caseObjId)
            throws RMDDAOException {
        List<String> vehicleConfigList = new ArrayList<String>();
        StringBuilder strQuery = new StringBuilder();
        Session session = null;
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT CGI.OBJID FROM TABLE_CASE C,GETS_RMD_VEHICLE V,GETS_RMD_MASTER_BOM MB, ");
            strQuery.append("GETS_RMD_VEHCFG VC,GETS_RMD_LOOKUP LU,GETS_RMD_MODEL MOD,GETS_SD.GETS_SD_CFG_GROUP_ITEM CGI ");
            strQuery.append("WHERE C.OBJID               =:caseObjId ");
            strQuery.append("AND C.CASE_PROD2SITE_PART   = V.VEHICLE2SITE_PART ");
            strQuery.append("AND V.VEHICLE2CTL_CFG       = MB.MASTER_BOM2CTL_CFG ");
            strQuery.append("AND VC.VEH_CFG2VEHICLE      = V.OBJID ");
            strQuery.append("AND VC.VEHCFG2MASTER_BOM    = MB.OBJID ");
            strQuery.append("AND V.VEHICLE2MODEL         = MOD.OBJID ");
            strQuery.append("AND (VC.CURRENT_VERSION     = CGI.CURRENT_VERSION ");
            strQuery.append("OR VC.CURRENT_VERSION      IS NULL) ");
            strQuery.append("AND MB.BOM_STATUS           = 'Y' ");
            strQuery.append("AND CGI.CFG_ITEM2RMD_LOOKUP = LU.OBJID ");
            strQuery.append("AND LU.LOOK_VALUE           = MB.CONFIG_ITEM ");
            strQuery.append("AND LU.LOOK_STATE!          = 'InActive' ");
            strQuery.append("UNION SELECT CGI.OBJID FROM TABLE_CASE C,GETS_RMD_VEHICLE V,GETS_RMD_MODEL MOD, ");
            strQuery.append("TABLE_SITE_PART SP,TABLE_SITE TS,TABLE_BUS_ORG BUSORG,GETS_SD.GETS_SD_CFG_GROUP_ITEM CGI ");
            strQuery.append("WHERE C.OBJID            =:caseObjId ");
            strQuery.append("AND C.CASE_PROD2SITE_PART= SP.OBJID ");
            strQuery.append("AND C.CASE_PROD2SITE_PART=V.VEHICLE2SITE_PART ");
            strQuery.append("AND V.VEHICLE2MODEL      = MOD.OBJID ");
            strQuery.append("AND SP.SITE_PART2SITE    = TS.OBJID ");
            strQuery.append("AND TS.PRIMARY2BUS_ORG   = BUSORG.OBJID ");
            strQuery.append("AND BUSORG.S_ORG_ID      =CFG_ITEM ");
            hibernateQuery = session.createSQLQuery(strQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    caseObjId);
            hibernateQuery.setFetchSize(50);
            List<BigDecimal> configList = hibernateQuery.list();
            for (BigDecimal currentConfig : configList) {
                String vehicleConfig = currentConfig.toString();
                vehicleConfigList.add(vehicleConfig);
            }

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getVehConfigGroup()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHCONFIG_GROUP);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
        return vehicleConfigList;
    }

    /**
     * @Author :
     * @return :String
     * @param : rxObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get vehicle Configuration for the
     *               given Recommendation.
     * 
     */
    @Override
    public List<String> getVehConfigGroupForRecomm(String rxObjId)
            throws RMDDAOException {
        List<String> rxConfigList = new ArrayList<String>();
        StringBuilder strQuery = new StringBuilder();
        Session session = null;
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT CFI.OBJID FROM GETS_SD_RECOM GSR,GETS_SD.GETS_SD_CFG_GROUP_ITEM CFI,");
            strQuery.append("GETS_SD.GETS_SD_RECOM_MTM_CFGGPITM CFGGPITM WHERE GSR.OBJID IN (:rxObjid) AND ");
            strQuery.append("GSR.OBJID = CFGGPITM.MTM2RECOM AND CFGGPITM.MTM2CFG_GROUP_ITEM = CFI.OBJID AND ");
            strQuery.append("GSR.RECOM_STATUS = 'Approved' AND GSR.RECOM_TYPE = 'Standard' AND ");
            strQuery.append("CFI.CFG_ITEM <> 'All' ORDER BY GSR.OBJID, CFI.CFG_ITEM2CFG_GROUP, CFI.OBJID ");
            hibernateQuery = session.createSQLQuery(strQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.RX_OBJID, rxObjId);
            hibernateQuery.setFetchSize(1);
            List<BigDecimal> vehConfigList = hibernateQuery.list();

            for (BigDecimal currentConfig : vehConfigList) {
                String vehicleConfig = currentConfig.toString();
                rxConfigList.add(vehicleConfig);
            }

        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getVehConfigGroupForRecomm()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHCONFIG_GROUP_FOR_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return rxConfigList;
    }

    /**
     * @Author :
     * @return :boolean
     * @param : modelObjId,rxObjId
     * @throws :RMDDAOException
     * @Description: This method is used to find whether rx is realted to
     *               particular model are not.
     * 
     */

    @Override
    public boolean getmodelMatch(String model, String rxObjId)
            throws RMDDAOException {
        boolean isValidRxForModel = true;
        StringBuilder modelObjIdQuery = new StringBuilder();
        StringBuilder modelMatchQuery = new StringBuilder();
        Session session = null;
        Query hibernateQuery = null;
        String modelObjId = null;
        try {
            session = getHibernateSession();
            modelObjIdQuery
                    .append("SELECT OBJID FROM GETS_RMD_MODEL WHERE MODEL_NAME=:model");
            hibernateQuery = session.createSQLQuery(modelObjIdQuery.toString());
            hibernateQuery.setFetchSize(1);
            hibernateQuery.setParameter(RMDCommonConstants.MODEL, model);
            List<Object> modelObjIdList = hibernateQuery.list();
            if (null != modelObjIdList && !modelObjIdList.isEmpty()) {
                modelObjId = RMDCommonUtility
                        .convertObjectToString(modelObjIdList.get(0));
            }
            if (null != modelObjId && !modelObjId.isEmpty()) {
                modelMatchQuery
                        .append("SELECT OBJID FROM GETS_SD_RECOM_MTM_MODEL WHERE RECOM_MODEL2RECOM =:rxObjid and RECOM_MODEL2MODEL=:modelId");
                hibernateQuery = session.createSQLQuery(modelMatchQuery
                        .toString());
                hibernateQuery.setParameter(RMDCommonConstants.RX_OBJID,
                        rxObjId);
                hibernateQuery.setParameter(RMDCommonConstants.MODELID,
                        modelObjId);
                Object result = hibernateQuery.uniqueResult();
                if (null != result) {
                    isValidRxForModel = true;
                } else {
                    isValidRxForModel = false;
                }
            }
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEoaDAOImpl getmodelMatch()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_MATCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return isValidRxForModel;
    }

    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDDAOException
     * @Description:This method is used for Checking weather unit is Shipped or
     *                   not.
     * 
     */
    @Override
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO)
            throws RMDDAOException {
        String unitShippedDetails = null;
        Session session = null;
        StringBuilder unitShippedQuery = new StringBuilder();
        Query hibernateQuery = null;
        try {
            session = getHibernateSession();
            unitShippedQuery
                    .append("SELECT  DECODE(TO_CHAR(SHIP_DATE,'YYYYMMDD'),NULL,'N','Y') FROM TABLE_SITE_PART,TABLE_BUS_ORG,GETS_RMD_VEH_HDR,GETS_RMD_VEHICLE WHERE ");
            unitShippedQuery
                    .append("TABLE_SITE_PART.OBJID= GETS_RMD_VEHICLE.VEHICLE2SITE_PART AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID ");
            unitShippedQuery
                    .append("AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID ");
            unitShippedQuery.append("AND TABLE_BUS_ORG.ORG_ID=:customerId ");
            unitShippedQuery
                    .append("AND X_VEH_HDR=:assetGrpName AND  SERIAL_NO =:assetNumber ");
            hibernateQuery = session
                    .createSQLQuery(unitShippedQuery.toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                    objUnitShipDetailsVO.getCustomerId());
            hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                    objUnitShipDetailsVO.getAssetGrpName());
            hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER,
                    objUnitShipDetailsVO.getAssetNumber());
            hibernateQuery.setFetchSize(1);
            List<Object> unitShippingDetailList = hibernateQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(unitShippingDetailList)) {
                unitShippedDetails = RMDCommonUtility
                        .convertObjectToString(unitShippingDetailList.get(0));
            }
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_UNIT_SHIP_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return unitShippedDetails;
    }

    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDDAOException
     * @Description: This method is used to get Find Cases Details.
     */
    @Override
	public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO)
            throws RMDDAOException {
        String caseFieldName = objFindCasesVO.getSearchOption().replaceAll("'","");
        String caseFieldValue = objFindCasesVO.getFieldValue();
        String caseType = objFindCasesVO.getCaseType();
        String roadHeader = objFindCasesVO.getRoadNumberHeader();
        String roadNumber = objFindCasesVO.getRoadNumber();
        String roadNumberFilterSelected = objFindCasesVO
                .getRoadNumberFilterSelected();
        String customerName = objFindCasesVO.getCustomerName();
        String caseFieldOp = objFindCasesVO.getFilterOption();
        String startDate = objFindCasesVO.getStartdate();
        String endDate = objFindCasesVO.getEndDate();
        String filter = "";
        String filterOptions = "";
        Session session = null;
        List<FindCasesDetailsVO> objFindCasesDetailsVOList = null;
        List<Object[]> resultList = null;
        FindCasesDetailsVO objFindCasesDetailsVO;
        Query caseHqry = null;
        String count = null;
        try {
            session = getHibernateSession();
            count = getLookUpValue(RMDCommonConstants.OMD_FINDCASES_RECORD_LIMIT);
            StringBuilder caseQry = new StringBuilder();
            caseQry.append(" SELECT ID_NUMBER,CONDITION,STATUS,TITLE,CREATION_TIME,FIRST_NAME,LAST_NAME,");
            caseQry.append(" TYPE,ORG_ID,");
            caseQry.append(" X_VEH_HDR,SERIAL_NO,QUEUE_TITLE ");
            caseQry.append(" FROM (SELECT TCD.ID_NUMBER,TCD.CONDITION,TCD.STATUS,TCD.TITLE,TO_CHAR(TCD.CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CREATION_TIME,");
            caseQry.append(" TCD.FIRST_NAME,TCD.LAST_NAME,TCD.TYPE,");
            caseQry.append(" TBO.ORG_ID,");
            caseQry.append(" TCD.X_VEH_HDR,TCD.SERIAL_NO,TCD.QUEUE_TITLE ");
            caseQry.append(" FROM SA.TABLE_CASE_DISPLAY_V TCD,TABLE_BUS_ORG TBO,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE GRV,GETS_RMD_VEH_HDR GHV WHERE ");

            if ((null != caseFieldValue) && (!caseFieldValue.isEmpty())) {
                if (caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ID)) {
                    filterOptions = RMDCommonConstants.FIND_CASE_TABLE_ALIAS+RMDCommonConstants.FIND_CASE_ID_NUMBER;
                } else if (caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_Title)) {
                    filterOptions = RMDCommonConstants.FIND_CASE_TABLE_ALIAS+RMDCommonConstants.FIND_CASE_TITLE;
                } else if (caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_Condition)) {
                    filterOptions = RMDCommonConstants.FIND_CASE_TABLE_ALIAS+RMDCommonConstants.FIND_CASE_CONDITION;
                } else if (caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_Status)) {
                    filterOptions = RMDCommonConstants.FIND_CASE_TABLE_ALIAS+RMDCommonConstants.FIND_CASE_STATUS;
                }
                if (!caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ID)) {
                    filterOptions = (RMDCommonConstants.FIND_CASE_UPPER
                             +filterOptions + RMDCommonConstants.CLOSE_BRACKET);
                }
                if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_STARTS_WITH)
                        || caseFieldOp
                                .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ENDS_WITH)
                        || caseFieldOp
                                .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_CONTAINS)) {
                    filter = filterOptions + "LIKE UPPER(:caseFieldValue)";
                } else if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_IS_EQUAL_TO)) {
                    filter = filterOptions + "= UPPER(:caseFieldValue)";
                } else if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_IS_NOT_EQUAL_TO)) {
                    filter = filterOptions + "!= UPPER(:caseFieldValue)";
                }

            }
            if ((null == caseFieldValue) || (caseFieldValue.isEmpty())) {
                if ((null != endDate) && (!endDate.isEmpty())) {
                    filter = filter + " TCD.creation_time <= TO_DATE(:endDate, 'MM/DD/YYYY HH24:MI:SS') and ";
                }
                filter = filter + " TCD.creation_time >= TO_DATE(:startDate, 'MM/DD/YYYY HH24:MI:SS')";
            } else {
            	if (!caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ID)) {
            	if ((null != endDate) && (!endDate.isEmpty())) {
                    filter = filter + " and TCD.creation_time <= TO_DATE(:endDate, 'MM/DD/YYYY HH24:MI:SS') ";
                }
                filter = filter + " and TCD.creation_time >= TO_DATE(:startDate, 'MM/DD/YYYY HH24:MI:SS')";
            	}
            }

            if ((null != caseType) && (!caseType.isEmpty())) {
                filter = filter + " and TCD.type LIKE :caseType";
            }
            if ((null != roadHeader) && (!roadHeader.isEmpty())) {
                filter = filter + " and TCD.x_veh_hdr = upper(:roadHeader)";
            }
            if ((null != roadNumber) && (!roadNumber.isEmpty())) {
                if (RMDCommonConstants.RN_STARTS_WITH
                        .equalsIgnoreCase(roadNumberFilterSelected)) {
                    filter = filter + " and TCD.serial_no LIKE :roadNumber";
                } else if (RMDCommonConstants.RN_IS_EQUAL_TO
                        .equalsIgnoreCase(roadNumberFilterSelected)) {
                    filter = filter + " AND TCD.serial_no = :roadNumber";
                }

            }
            if (null != customerName
                    && !customerName.isEmpty()
                    && !customerName
                            .equalsIgnoreCase(RMDCommonConstants.SELECT)) {
                filter = filter + " and TCD.site_name LIKE :customerName";
            }
            filter = filter+" AND TCD.SITE_PART_OBJID=TSP.OBJID AND TSP.OBJID= GRV.VEHICLE2SITE_PART AND GRV.VEHICLE2VEH_HDR=GHV.OBJID AND GHV.VEH_HDR2BUSORG = TBO.OBJID";
            filter = filter + " ORDER BY TO_DATE(CREATION_TIME, 'MM/DD/YYYY HH24:MI:SS') DESC ) WHERE ROWNUM  <= :count ";
            caseQry.append(filter);
            caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setFetchSize(50);
            if ((null != caseFieldValue) && (!caseFieldValue.isEmpty())) {
                if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_STARTS_WITH)) {
                    caseHqry.setParameter(RMDCommonConstants.CASE_FILED_VALUE,
                            caseFieldValue + RMDServiceConstants.PERCENTAGE);
                } else if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ENDS_WITH)) {
                    caseHqry.setParameter(RMDCommonConstants.CASE_FILED_VALUE,
                            RMDServiceConstants.PERCENTAGE + caseFieldValue);
                } else if (caseFieldOp
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_CONTAINS)) {
                    caseHqry.setParameter(RMDCommonConstants.CASE_FILED_VALUE,
                            RMDServiceConstants.PERCENTAGE + caseFieldValue
                                    + RMDServiceConstants.PERCENTAGE);
                } else {
                    caseHqry.setParameter(RMDCommonConstants.CASE_FILED_VALUE,
                            caseFieldValue);
                }
            } 
            if ((null == caseFieldValue) || (caseFieldValue.isEmpty())) {
                if ((null != endDate) && (!endDate.isEmpty())) {
                    caseHqry.setParameter(RMDCommonConstants.FIND_CASE_ENDDATE,
                            endDate);
                }
                if ((null != startDate) && (!startDate.isEmpty())) {
                    caseHqry.setParameter(
                            RMDCommonConstants.FIND_CASE_STARTDATE, startDate);
                }
            } else {
            	if (!caseFieldName
                        .equalsIgnoreCase(RMDCommonConstants.FIND_CASE_ID)) {
            	if ((null != endDate) && (!endDate.isEmpty())) {
                    caseHqry.setParameter(RMDCommonConstants.FIND_CASE_ENDDATE,
                            endDate);
                }
                if ((null != startDate) && (!startDate.isEmpty())) {
                    caseHqry.setParameter(
                            RMDCommonConstants.FIND_CASE_STARTDATE, startDate);
                }
              }
            }
            if ((null != caseType) && (!caseType.isEmpty())) {
                caseHqry.setParameter(RMDCommonConstants.FIND_CASE_TYPE,
                        caseType + RMDServiceConstants.PERCENTAGE);
            }
            if ((null != roadHeader) && (!roadHeader.isEmpty())) {
                caseHqry.setParameter(RMDCommonConstants.ROAD_HEADER,
                        roadHeader);
            }
            if ((null != roadNumber) && (!roadNumber.isEmpty())) {
                if (RMDCommonConstants.RN_STARTS_WITH
                        .equalsIgnoreCase(roadNumberFilterSelected)) {

                    caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER,
                            roadNumber + "%");
                } else if (RMDCommonConstants.RN_IS_EQUAL_TO
                        .equalsIgnoreCase(roadNumberFilterSelected)) {
                    caseHqry.setParameter(RMDCommonConstants.ROAD_NUMBER,
                            roadNumber);
                }

            }
            if (null != customerName
                    && !customerName.isEmpty()
                    && !customerName
                            .equalsIgnoreCase(RMDCommonConstants.SELECT)) {
                caseHqry.setParameter(RMDCommonConstants.CUSTOMER_NAME,
                        customerName + RMDServiceConstants.PERCENTAGE);
            }
            caseHqry.setParameter(RMDCommonConstants.COUNT, count);
            resultList = caseHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                objFindCasesDetailsVOList = new ArrayList<FindCasesDetailsVO>(resultList.size());
                for (final Iterator<Object[]> obj = resultList.iterator(); obj
                        .hasNext();) {
                    objFindCasesDetailsVO = new FindCasesDetailsVO();
                    final Object[] findCasesDetailsVO = obj.next();
                    objFindCasesDetailsVO.setCaseID(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[0]));
                    objFindCasesDetailsVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[3])));
                    objFindCasesDetailsVO.setCondition(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[1]));
                    objFindCasesDetailsVO.setStatus(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[2]));
                    objFindCasesDetailsVO
                            .setContact(RMDCommonUtility
                                    .convertObjectToString(findCasesDetailsVO[5])
                                    + RMDCommonConstants.BLANK_SPACE
                                    + RMDCommonUtility
                                            .convertObjectToString(findCasesDetailsVO[6]));
                    if (null != RMDCommonUtility.convertObjectToString(findCasesDetailsVO[4])) {
                        objFindCasesDetailsVO.setCreationTime(RMDCommonUtility.convertObjectToString(findCasesDetailsVO[4]));
                    }
                    objFindCasesDetailsVO.setCaseType(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[7]));
                    objFindCasesDetailsVO.setCustomerId(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[8]));
                    objFindCasesDetailsVO.setCustRNH(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[9]));
                    objFindCasesDetailsVO.setRn(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[10]));
                    objFindCasesDetailsVO.setQueue(RMDCommonUtility
                            .convertObjectToString(findCasesDetailsVO[11]));

                    objFindCasesDetailsVOList.add(objFindCasesDetailsVO);
                }
            } 
            
            resultList = null;
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCPETION_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return objFindCasesDetailsVOList;
    }

    /**
     * @Author:Mohamed
     * @param :serviceReqId, lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular
     *                   case.
     * 
     */
    @Override
    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId,
            String lookUpDays) throws RMDDAOException {
        List<MaterialUsageVO> materialUsageVOList = null;
        List<Object[]> resultList = null;
        Session session = null;
        MaterialUsageVO objMaterialUsageDetails;
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT);
        try {
            if (!RMDCommonUtility.isNullOrEmpty(serviceReqId)
                    && !RMDCommonUtility.isNullOrEmpty(lookUpDays)) {
                session = getHibernateSession();
                StringBuilder caseQry = new StringBuilder();
                caseQry.append(" SELECT PART_NO, DESCRIPTION,TO_CHAR(CREATION_DATE,'MM/DD/YY HH24:MI:SS'), LOCATION, QUANTITY ");
                caseQry.append(" FROM GETS_LMS_RMD_MATERIAL_USAGE_V@ESERVICES.WORLD WHERE  SERVICE_REQUEST_ID = :serviceReqId AND CREATION_DATE >= (SYSDATE -:lookUpDays) ORDER BY CREATION_DATE DESC");
                Query materialHqry = session.createSQLQuery(caseQry.toString());
                materialHqry.setParameter(RMDCommonConstants.SERVICE_REQ_ID,
                        serviceReqId);
                materialHqry.setParameter(
                        RMDCommonConstants.MATERILA_USAGE_LOOKUP_DAYS,
                        lookUpDays);
                resultList = materialHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                    materialUsageVOList = new ArrayList<MaterialUsageVO>();
                    for (final Iterator<Object[]> obj = resultList.iterator(); obj
                            .hasNext();) {
                        objMaterialUsageDetails = new MaterialUsageVO();
                        final Object[] objMaterialUsage =  obj.next();
                        objMaterialUsageDetails.setPartNo(RMDCommonUtility
                                .convertObjectToString(objMaterialUsage[0]));
                        objMaterialUsageDetails.setDescription(RMDCommonUtility
                                .convertObjectToString(objMaterialUsage[1]));
                        if (null != RMDCommonUtility
                                .convertObjectToString(objMaterialUsage[2])) {
                            Date createDate = formatter
                                    .parse(RMDCommonUtility
                                            .convertObjectToString(objMaterialUsage[2]));
                            objMaterialUsageDetails.setCreationDate(createDate);
                        }
                        objMaterialUsageDetails.setLocation(RMDCommonUtility
                                .convertObjectToString(objMaterialUsage[3]));
                        objMaterialUsageDetails.setQuantity(RMDCommonUtility
                                .convertObjectToString(objMaterialUsage[4]));
                        materialUsageVOList.add(objMaterialUsageDetails);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MATERIAL_USAGE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MATERIAL_USAGE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return materialUsageVOList;
    }

    /**
     * This method is used to perform merge related operations
     * 
     * @param CaseMergeServiceVO
     * @throws RMDDAOException
     */
    @Override
    public void mergeRx(CaseMergeServiceVO caseMergeServiceVO)
            throws RMDDAOException {

        Transaction transaction = null;
        Session session = null;
        StringBuilder updateArListQry = new StringBuilder();
        StringBuilder updateRPRLQry = new StringBuilder();
        String caseId = caseMergeServiceVO.getCaseId();
        String toCaseId = caseMergeServiceVO.getToCaseId();
        String rxId = caseMergeServiceVO.getRxId();
        String mergeRx = caseMergeServiceVO.getMergedTo();
        String ruleDefId = caseMergeServiceVO.getRuleDefId();
        String toolId = caseMergeServiceVO.getToolId();
        String assetGrpName = caseMergeServiceVO.getAssetGrpName();
        String custumerId = caseMergeServiceVO.getCustomerId();
        String assetNumber = caseMergeServiceVO.getAssetNumber();
        String toolObjId = caseMergeServiceVO.getToolObjId();
        int arListObjId = 0;
        StringBuilder deleteARQuery = new StringBuilder();
        StringBuilder selCaseQry = new StringBuilder();
        int rxCnt = 0;
        boolean rxExists = false;
        boolean faultExists = false;
        boolean isJDPADCBR = false;
        StringBuilder selFCQry = new StringBuilder();
        /* added for case append changes(US160835) */
        StringBuilder maxMinFaultQuery = null;
        StringBuilder updateRPRLQuery = null;
        Query maxMinFaultHQuery = null;
        List<Object> maxMinFaultList = null;
        BigDecimal maxExstngFault = null;
        BigDecimal minExstngFault = null;
        /* End of case append changes */
        try {
            session = getHibernateSession();
            transaction = session.getTransaction();
            long fromCaseObjId = Long.parseLong(getCaseObjid(session, caseId));
            long toCaseObjId = Long.parseLong(getCaseObjid(session, toCaseId));
            LOG.info("rxId ::" + rxId + " mergeRx :::" + mergeRx
                    + "ruleDefId :::: " + ruleDefId + " assetGrpName ::: "
                    + assetGrpName + "toolId :::" + toolId);

            LOG.info("custumerId ::" + custumerId + " assetNumber :::"
                    + assetNumber + "toolObjId :::: " + toolObjId);
            LOG.info("Case ID From: " + caseId + "Case OBJID TO :"
                    + fromCaseObjId);
            LOG.info("Case ID TO:" + toCaseId + "To Case OBJID: " + toCaseObjId);
            LOG.info("******************************Getting Vehicle ObjId***********************************************************");
            String strVehicleId = getCustrnhDetails(custumerId, assetNumber,
                    assetGrpName);
            if (strVehicleId == null) {
                LOG.info("Vehicle Id is null");
                throw new Exception();
            }
            LOG.info("vehicleObjId=" + strVehicleId);
            // Begin Transaction
            transaction.begin();

            // Logic for updating tables for For RXS
            // ****************************************************************************************************************/

            if (toolId != null
                    && (toolId.equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                            .equalsIgnoreCase(RMDCommonConstants.CBR))) {
                isJDPADCBR = true;
                /*** Check if Rx is already present in the append2case ****/
                LOG.info(".******************************checking if rx is there in GETS_TOOL_AR_LIST************");

                if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                    selCaseQry
                        .append("SELECT COUNT(1)  FROM  gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list");
                    selCaseQry
                        .append(" AND rprldwn2rule_defn=:ruleDefId AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId");
                }else if(toolId.equalsIgnoreCase(RMDCommonConstants.CBR)){
                    selCaseQry
                        .append("SELECT COUNT(1) FROM gets_tool_ar_list WHERE AR_LIST2RECOM =:rxObjid AND AR_LIST2CASE= :toCaseObjId ");
                }
                Query selCaseHQry = session.createSQLQuery(selCaseQry
                        .toString());
                if(toolId.equalsIgnoreCase(RMDCommonConstants.RULE)){
                    selCaseHQry.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
                }
                selCaseHQry.setParameter(RMDCommonConstants.RX_OBJID, rxId);
                selCaseHQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                        toCaseObjId);

                BigDecimal count = (BigDecimal) selCaseHQry.uniqueResult();
                rxCnt = count.intValue();
                if (rxCnt > 0)
                    rxExists = true;

                LOG.debug("Merge - Rx already exists in Append to Case : "
                        + rxExists);
                LOG.debug("isJDPADCBR" + isJDPADCBR);

                StringBuilder findFaultJDPADQry = new StringBuilder();
                StringBuilder findFaultCBRQry = new StringBuilder();
                if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                    
                    findFaultJDPADQry.append("SELECT DISTINCT gtft.objid FROM GETS_RMD.GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC, GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR, ")
                    .append("GETS_RMD.GETS_TOOL_CASE_MTM_FAULT mtm,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF ,GETS_RMD.GETS_TOOL_FLTDOWN GTFD ,GETS_RMD.GETS_TOOL_FAULT GTFT WHERE ")
                    .append("TC.objid = :caseObjId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ")
                    .append("AND GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN  = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND mtm.mtm2fault = gtft.objid AND ")
                    .append("Fault2vehicle = :vehicleObjId AND Mtm.Mtm2case  = Tc.Objid AND GTRL.rprldwn2rule_defn = :ruleDefId");
                    
                } else if (toolId != null
                        && (toolId.equals(RMDCommonConstants.CBR))) {

                    findFaultCBRQry.append("SELECT MTM2FAULT FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE=:caseObjId");
                }

                Query findFaultHQry = null;
                List<BigDecimal> faultListJDPAD = null;
                List<BigDecimal> faultListCBR = null;

                if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                    findFaultHQry = session.createSQLQuery(findFaultJDPADQry
                            .toString());
                    findFaultHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                            fromCaseObjId);
                    findFaultHQry.setParameter(RMDCommonConstants.RULE_DEFID,
                            ruleDefId);

                    findFaultHQry.setParameter(
                            RMDCommonConstants.VEHICLE_OBJ_ID, strVehicleId);
                    faultListJDPAD = findFaultHQry.list();
                    LOG.info("Fault list for JDPAD ::: "
                            + faultListJDPAD.size());

                } else if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {

                    findFaultHQry = session.createSQLQuery(findFaultCBRQry
                            .toString());
                    findFaultHQry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                            fromCaseObjId);
                    faultListCBR = findFaultHQry.list();
                    LOG.info("Fault list for CBR ::: " + faultListCBR.size());
                }
                List<String> faultObjIds = new ArrayList<String>();
                if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                    // add null check
                    for (BigDecimal currentFault : faultListJDPAD) {
                        if (currentFault != null && !faultObjIds.contains(currentFault.toString())) {
                                LOG.info("adding to fault list jdpad"
                                        + currentFault);
                                faultObjIds.add(String.valueOf(currentFault));
                        }
                    }
                } else if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                    LOG.info("Adding for CBR");
                    for (BigDecimal currentFault : faultListCBR) {
                        if (currentFault != null && !faultObjIds.contains(currentFault.toString())) {
                                LOG.info("Adding CBR fault to fault list "
                                        + currentFault);
                                faultObjIds.add(String.valueOf(currentFault));
                        }
                    }
                }
                /** delete from RPRL_DWN table **/
                if (toolId != null
                        && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                    LOG.info("2.******************************Deleting from GETS_TOOL_RPRLDWN***********************************************************");

                    LOG.info("AR_LIST2RECOM: " + rxId);
                    updateRPRLQry
                            .append("DELETE FROM GETS_TOOL_RPRLDWN where RPRLDWN2AR_LIST = :currarListObjId");
                    Query updateRPRLHQry = session.createSQLQuery(updateRPRLQry
                            .toString());
                    updateRPRLHQry.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID, toolObjId);
                    updateRPRLHQry.executeUpdate();
                    LOG.info("Deleted from GETS_TOOL_RPRLDWN");
                }
                /** Update gets_tool_ar_list table **/

                LOG.debug("3.******************************Updating GETS_TOOL_AR_LIST starts here***********************************************************");
                if (!rxExists) { // if rx Does not exist in toCase, update
                                 // AR_LIST2CASE column in GETS_TOOL_AR_LIST
                                 // table for fromCase
                    Query seqHQry = session.createSQLQuery(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");
                    arListObjId = ((BigDecimal) seqHQry.uniqueResult())
                            .intValue();
                    LOG.info("New AR_LIST OBJID " + arListObjId);

                    if (fromCaseObjId == 0 || arListObjId == 0) {
                        LOG.info("fromCaseObjId || arListObjId is 0");
                        throw new Exception();
                    }
                    
                    updateArListQry.append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE,OBJID= :arListObjId  WHERE ")
                    .append("AR_LIST2CASE =:fromCaseObjId AND AR_LIST2RECOM= :rxObjid AND OBJID= :currarListObjId AND TOOL_ID= :toolId");
                    
                    Query updateArListhQry = session
                            .createSQLQuery(updateArListQry.toString());
                    updateArListhQry.setParameter(
                            RMDCommonConstants.TO_CASE_OBJID, toCaseObjId);
                    updateArListhQry.setParameter(
                            RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                            arListObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);
                    updateArListhQry.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID,
                            caseMergeServiceVO.getToolObjId());

                    if (toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                        updateArListhQry.setParameter(
                                RMDCommonConstants.TOOLID,
                                RMDCommonConstants.CBR);
                    else if (toolId.equalsIgnoreCase(RMDCommonConstants.RULE))
                        updateArListhQry.setParameter(
                                RMDCommonConstants.TOOLID,
                                RMDCommonConstants.JDPAD);

                    updateArListhQry.executeUpdate();
                    LOG.debug("Updated GETS_TOOL_AR_LIST table");
                } else { // if rx exist in toCase, update the records into
                         // GETS_TOOL_AR_LIST table                  
                    if (toolId != null
                            && toolId
                                    .equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        updateArListQry
                            .append("UPDATE GETS_TOOL_AR_LIST SET LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE WHERE OBJID IN (SELECT ar.objid FROM ");
                        updateArListQry
                            .append("gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list  AND rprldwn2rule_defn=:ruleDefId AND ");
                        updateArListQry
                            .append("ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId) AND TOOL_ID= :toolId");                      
                    }else if(toolId != null &&  toolId
                            .equalsIgnoreCase(RMDCommonConstants.CBR)){
                        updateArListQry
                            .append("UPDATE GETS_TOOL_AR_LIST SET LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE");
                        updateArListQry
                            .append(" WHERE AR_LIST2CASE =:toCaseObjId AND AR_LIST2RECOM= :rxObjid AND TOOL_ID= :toolId");                        
                    }
                    Query updateArListhQry = session
                            .createSQLQuery(updateArListQry.toString());
                    updateArListhQry.setParameter(
                            RMDCommonConstants.TO_CASE_OBJID, toCaseObjId);
                    updateArListhQry.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);
                    if (toolId != null
                            && (toolId
                                    .equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                                    .equalsIgnoreCase(RMDCommonConstants.CBR))) {

                        if (toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.CBR);
                        else if (toolId
                                .equalsIgnoreCase(RMDCommonConstants.RULE)){
                            updateArListhQry.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
                            updateArListhQry.setParameter(
                                    RMDCommonConstants.TOOLID,
                                    RMDCommonConstants.JDPAD);
                        }
                    }

                    updateArListhQry.executeUpdate();
                    /**
                     * To get the max and min fault objid's of rule/cbr in
                     * append to case
                     **/
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                        maxMinFaultQuery = new StringBuilder();
                        
                        maxMinFaultQuery.append("SELECT max(gtft.objid) maxObjId,min(gtft.objid) FROM GETS_RMD.GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC, GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR, ")
                        .append("GETS_RMD.GETS_TOOL_CASE_MTM_FAULT mtm,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF ,GETS_RMD.GETS_TOOL_FLTDOWN GTFD ,GETS_RMD.GETS_TOOL_FAULT GTFT WHERE ")
                        .append("TC.objid = :caseObjId AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ")
                        .append("AND GTDF.OBJID = GTDR.RULEDEF2FINRUL AND GTFD.FLTDOWN2RULE_DEFN  = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND mtm.mtm2fault = gtft.objid AND ")
                        .append("Fault2vehicle = :vehicleObjId AND Mtm.Mtm2case  = Tc.Objid AND GTRL.rprldwn2rule_defn IN (SELECT rprldwn2rule_defn  FROM gets_tool_rprldwn rprl, ")
                        .append("gets_tool_ar_list ar WHERE ar.objid = rprl.rprldwn2ar_list AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :caseObjId)");
                        
                        maxMinFaultHQuery = session.createSQLQuery(maxMinFaultQuery.toString());
                        maxMinFaultHQuery.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, toCaseObjId);
                        maxMinFaultHQuery.setParameter(
                                RMDCommonConstants.RX_OBJID, rxId);
                        maxMinFaultHQuery
                                .setParameter(
                                        RMDCommonConstants.VEHICLE_OBJ_ID,
                                        strVehicleId);

                    } else if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                        maxMinFaultQuery = new StringBuilder();
                        maxMinFaultQuery
                                .append(" select  max(mtm2fault) maxObjId,min(mtm2fault) minObjId from gets_tool_case_mtm_fault where mtm2case=:caseObjId ");
                        maxMinFaultHQuery = session
                                .createSQLQuery(maxMinFaultQuery.toString());
                        maxMinFaultHQuery.setParameter(
                                RMDCommonConstants.CASE_OBJ_ID, toCaseObjId);
                    }
                    maxMinFaultList = maxMinFaultHQuery.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(maxMinFaultList)) {
                        for (final Iterator<Object> obj = maxMinFaultList
                                .iterator(); obj.hasNext();) {
                            final Object[] maxMinFaultDetails = (Object[]) obj
                                    .next();
                            maxExstngFault = (BigDecimal) maxMinFaultDetails[0];
                            minExstngFault = (BigDecimal) maxMinFaultDetails[1];
                        }

                    }
                    /** End of case append changes */

                }
                /** Insert records with new ar_list ids into RPRLDWN table **/
                if (toolId != null
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.Fault)
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.CBR)) {
                    if (!rxExists) {// if rx Does not exist in toCase, insert
                                    // the record into GETS_TOOL_RPRLDWN table
                        LOG.debug("4.******************************Insert records with new ar_list ids into RPRLDWN  table ***********************************************************");
                        LOG.debug("RuleDefId of selected rule" + ruleDefId);
                        
                        StringBuilder insertGetsToolRprldwnQuery = new StringBuilder()
                        .append("INSERT INTO GETS_TOOL_RPRLDWN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,TOOL_ID, RPRLDWN2AR_LIST, RPRLDWN2RULE_DEFN) ")
                        .append("VALUES (GETS_TOOL_RPRLDWN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,'OMD_Append',:toolId,:arListObjId,:ruleDefId)");
                        
                        Query hibernateQuery = session.createSQLQuery(insertGetsToolRprldwnQuery.toString());

                        hibernateQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.JDPAD);
                        hibernateQuery.setParameter(
                                RMDCommonConstants.AR_OBJID, arListObjId);
                        hibernateQuery.setParameter(
                                RMDCommonConstants.RULE_DEFID, ruleDefId);
                        hibernateQuery.executeUpdate();
                        LOG.debug("Record Inserted into GETS_TOOL_RPRLDWN");
                    } else if (rxExists) {// if rX exist in toCase , update the
                                          // GETS_TOOL_RPRLDWN table
                        updateRPRLQuery = new StringBuilder();
                        updateRPRLQuery.append("UPDATE GETS_TOOL_RPRLDWN SET LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY='OMD_Append' WHERE OBJID IN");
                        updateRPRLQuery.append("(SELECT rprl.objid FROM gets_tool_ar_list ar,gets_tool_rprldwn rprl WHERE ar.objid = rprl.rprldwn2ar_list AND ");
                        updateRPRLQuery.append("rprldwn2rule_defn=:ruleDefId AND ar.AR_LIST2RECOM =:rxObjid AND ar.AR_LIST2CASE= :toCaseObjId)");
                        Query updateRPRLHQuery = session.createSQLQuery(updateRPRLQuery
                                .toString());
                        updateRPRLHQuery.setParameter(RMDCommonConstants.RULE_DEFID, ruleDefId); 
                        updateRPRLHQuery.setParameter(
                                RMDCommonConstants.RX_OBJID, rxId);
                        updateRPRLHQuery.setParameter(
                                RMDCommonConstants.TO_CASE_OBJID, toCaseObjId);
                        updateRPRLHQuery.executeUpdate();
                        LOG.debug("GETS_TOOL_RPRLDWN table updated");
                    }
                }

                LOG.info("5.******************************Inserting into MTM FAULTS***********************************************************");
                LOG.info("                5A.******************************Get FaultObjids for a rule_defn and then query it *****************");

                // Insert each fault into mtm_faults for that case
                if (toolId != null
                        && !toolId.equalsIgnoreCase(RMDCommonConstants.Fault)) {
                    // Start inserting
                    LOG.info("                5b.******************************Inserting faultobjid Into MTM_FAULTS *****************");
                    
                    StringBuilder  insertGetsToolCaseMtmFaultQuery = new StringBuilder()
                    .append("INSERT INTO GETS_TOOL_CASE_MTM_FAULT (OBJID,LAST_UPDATED_BY, LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,MTM2CASE,MTM2FAULT,CBR_USED,JDPAD_USED) ")
                    .append("VALUES(GETS_TOOL_CASE_MTM_FAULT_SEQ.NEXTVAL,:lastUpdatedBy,sysdate,:lastUpdatedBy,sysdate,:caseId, :faultCode, :CBR,:JDPAD)");
                    
                    
                    Query mtmInsertHQry = session.createSQLQuery(insertGetsToolCaseMtmFaultQuery.toString());
                    Query countFaultHQryJdpad = session
                            .createSQLQuery("SELECT COUNT(1) FROM GETS_TOOL_CASE_MTM_FAULT WHERE MTM2CASE=:caseId AND MTM2FAULT=:faultCode ");

                    if (faultObjIds != null) {
                        for (String fault : faultObjIds) {
                            countFaultHQryJdpad.setParameter(
                                    RMDCommonConstants.CASE_ID, toCaseObjId);
                            countFaultHQryJdpad.setParameter(
                                    RMDCommonConstants.FAULTSCODE, fault);
                            int fltCntJdpad = ((BigDecimal) countFaultHQryJdpad
                                    .uniqueResult()).intValue();
                            LOG.info("Inserting fault  " + fault);
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.LAST_UPDATED,
                                    "Case_Repetition_1");
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.CASE_ID, toCaseObjId);
                            mtmInsertHQry.setParameter(
                                    RMDCommonConstants.FAULTSCODE, fault);
                            if (toolId != null
                                    && toolId
                                            .equalsIgnoreCase(RMDCommonConstants.RULE)
                                    && fltCntJdpad <= 0) {
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.JDPAD, "Y");
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.CBR, null);
                                mtmInsertHQry.executeUpdate();
                            } else
                            // CBR
                            if (toolId != null
                                    && toolId
                                            .equalsIgnoreCase(RMDCommonConstants.CBR)
                                    && fltCntJdpad <= 0) {
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.JDPAD, null);
                                mtmInsertHQry.setParameter(
                                        RMDCommonConstants.CBR, "Y");
                                mtmInsertHQry.executeUpdate();

                            }
                        }

                    }

                }

                /**
                 * Added to add Min and Max faults to gets_tool_run table
                 **/
                if (toolId != null
                        && (toolId.equalsIgnoreCase(RMDCommonConstants.RULE) || toolId
                                .equalsIgnoreCase(RMDCommonConstants.CBR))) {

                    BigDecimal maxFault = null;
                    BigDecimal minFault = null;
                    BigDecimal maxFaultInsert = null;
                    BigDecimal minFaultInsert = null;
                    if ((null != faultListJDPAD && !faultListJDPAD.isEmpty())
                            || (null != faultListCBR && !faultListCBR.isEmpty())) {
                        if (toolId.equalsIgnoreCase(RMDCommonConstants.RULE)) {
                            maxFault = Collections.max(faultListJDPAD);
                            minFault = Collections.min(faultListJDPAD);
                        } else if (toolId
                                .equalsIgnoreCase(RMDCommonConstants.CBR)) {
                            maxFault = Collections.max(faultListCBR);
                            minFault = Collections.min(faultListCBR);
                        }
                        /* Added for case append changes */
                        if (rxExists) {

                            if ((maxExstngFault != null && maxFault != null)
                                    && Long.parseLong(maxExstngFault.toString()) > Long
                                            .parseLong(maxFault.toString())) {
                                maxFault = maxExstngFault;
                            }
                            if ((minExstngFault != null && minFault != null)
                                    && Long.parseLong(minExstngFault.toString()) < Long
                                            .parseLong(minFault.toString())) {
                                minFault = minExstngFault;
                            }
                        }
                        /* End of case append changes */
                        maxFaultInsert = maxFault;
                        minFaultInsert = minFault;
                        LOG.debug(MIN_FAULT_OBJID + minFault);
                        LOG.debug(MAX_FAULT_OBJID + maxFault);
                        LOG.info("inserting into gets_tool_run");
                        String strDiagServiceId = null;

                        strDiagServiceId = getDiagServiceIDAppend(session,
                                toCaseObjId);
                        if (null == strDiagServiceId) {
                            strDiagServiceId = getDiagServiceIDAppend(session,
                                    fromCaseObjId);
                        }
                        if (null != strDiagServiceId) {
                        	
                        	StringBuilder insertGetsToolRunQuery = new StringBuilder()
                            .append("INSERT INTO GETS_TOOL_RUN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RUN_PRIORITY,RUN_START,RUN_CPT,RUN_PROCESSEDMIN_OBJID,")
                            .append("RUN_PROCESSEDMAX_OBJID,RUN2CASE,RUN2VEHICLE,RUN_CASE_REQ_BY,QUIET_ON,DIAG_SERVICE_ID) VALUES(GETS_TOOL_RUN_SEQ.NEXTVAL,sysdate,'OMD_Append',sysdate,")
                            .append("'OMD_Append',4,sysdate,sysdate,:minFaultObjId,:maxFaultObjId,:caseId,:vehicleObjId,NULL,NULL,:diagServiceId)");
                        	
                            Query runInsertHQry = session
                                    .createSQLQuery(insertGetsToolRunQuery.toString());

                            runInsertHQry.setParameter(
                                    RMDCommonConstants.VEHICLE_OBJ_ID,
                                    Integer.parseInt(strVehicleId));
                            runInsertHQry.setParameter(
                                    RMDCommonConstants.CASE_ID, toCaseObjId);
                            runInsertHQry.setParameter(
                                    MAX_FAULT_OBJID, maxFaultInsert.intValue());
                            runInsertHQry.setParameter(
                                    MIN_FAULT_OBJID, minFaultInsert.intValue());
                            runInsertHQry.setParameter(
                                    RMDCommonConstants.DIAG_SERVICE_ID,
                                    strDiagServiceId);
                            runInsertHQry.executeUpdate();
                            LOG.info("Inserted into gets_tool_run");
                        }
                    }

                }

                if (rxExists) {
                    /** Delete from AR LIST **/
                    LOG.info(".*******************Deleting from AR_LIST**********************");

                    deleteARQuery
                            .append("DELETE FROM GETS_TOOL_AR_LIST where AR_LIST2CASE = :fromCaseObjId AND AR_LIST2RECOM= :rxObjid  and tool_id=:toolId and OBJID=:currarListObjId");
                    Query deleteARHQuery = session.createSQLQuery(deleteARQuery
                            .toString());

                    deleteARHQuery.setParameter(
                            RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                    deleteARHQuery.setParameter(RMDCommonConstants.RX_OBJID,
                            rxId);
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.RULE))
                        deleteARHQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.JDPAD);
                    if (toolId != null
                            && toolId.equalsIgnoreCase(RMDCommonConstants.CBR))
                        deleteARHQuery.setParameter(RMDCommonConstants.TOOLID,
                                RMDCommonConstants.CBR);
                    deleteARHQuery.setParameter(
                            RMDCommonConstants.AR_CURR_OBJID,
                            caseMergeServiceVO.getToolObjId());
                    deleteARHQuery.executeUpdate();

                }
            } // End if jdpad cbr

            else {

                /*** Check if Faults is already present in the append2case ****/
                LOG.info(".*******************checking if FAULT is there in GETS_TOOL_AR_LIST**********************************");

                LOG.info("fromCaseObjId" + fromCaseObjId);
                LOG.info("arlistobjid for from case for that fault " + rxId);
                

                /*** Check FAULT ends *******************************************/

                /** Update gets_tool_ar_list table **/

                LOG.info("3.******************************Updating GETS_TOOL_AR_LIST***********************************************************");

                Query seqHQry = session.createSQLQuery(" SELECT GETS_TOOL_AR_LIST_SEQ.NEXTVAL FROM DUAL");
                arListObjId = ((BigDecimal) seqHQry.uniqueResult()).intValue();
                LOG.info("New AR_LIST OBJID " + arListObjId);

                if (toCaseObjId == 0 || fromCaseObjId == 0 || arListObjId == 0) {
                    LOG.info("ToCase || FromCase || arListObjId is 0");
                    throw new Exception();
                }
                
                updateArListQry =new StringBuilder()
                .append("UPDATE GETS_TOOL_AR_LIST SET AR_LIST2CASE= :toCaseObjId , LAST_UPDATED_BY='OMD_Append',LAST_UPDATED_DATE=SYSDATE, OBJID= :arListObjId WHERE ")
                .append("AR_LIST2CASE =:fromCaseObjId and objid =:rxObjid");

                Query updateArListhQry = session.createSQLQuery(updateArListQry
                        .toString());
                updateArListhQry.setParameter(RMDCommonConstants.TO_CASE_OBJID,
                        toCaseObjId);
                updateArListhQry.setParameter(
                        RMDCommonConstants.FROM_CASE_OBJID, fromCaseObjId);
                updateArListhQry.setParameter(RMDCommonConstants.AR_OBJID,
                        arListObjId);
                // this is the ar_list old obj id
                updateArListhQry
                        .setParameter(RMDCommonConstants.RX_OBJID, rxId);

                updateArListhQry.executeUpdate();
                LOG.info("Updated GETS_TOOL_AR_LIST");

            }// End faults loop
             // Update TABLE_ACT_ENTRY table
            LOG.debug("6.******************************Inserting into TABLE_ACT_ENTRY***********************************************************");
            // Get Append ObjId
            LOG.debug("               6a.Get Append ObjId");
            StringBuilder appendQry = new StringBuilder();
            appendQry
                    .append(" SELECT g.OBJID from TABLE_GBST_ELM g, TABLE_GBST_LST lst  ");
            appendQry
                    .append(" WHERE g.TITLE='Append' AND g.GBST_ELM2GBST_LST = lst.OBJID  ");
            appendQry.append("AND lst.TITLE='Activity Name'");
            Query appendHQry = session.createSQLQuery(appendQry.toString());
            String appendId = appendHQry.uniqueResult().toString();
            if (appendId == null)
                throw new Exception();
            // Select the act_entry2user
            LOG.debug("               6b.Select the act_entry2user");
            StringBuilder actEntry2UserQry = new StringBuilder();
            actEntry2UserQry
                    .append(" SELECT OBJID  from TABLE_USER where login_name='autogen'  ");

            Query actEntry2UseHrQry = session.createSQLQuery(actEntry2UserQry
                    .toString());
            String actEntry2User = actEntry2UseHrQry.uniqueResult().toString();
            if (actEntry2User == null)
                throw new Exception();
            // Insert into act_entry
            LOG.info("               6c.Select the act_entry");
            LOG.info("entry2User " + actEntry2User);
            LOG.info("caseObjId " + toCaseObjId);
            LOG.info("ENTRY_NAME2GBST_ELM " + appendId);

            String addnlInfo = RMDCommonConstants.ADDNL_INFO + caseId;
            LOG.info("addnlInfo " + addnlInfo);
            String insertActEntry = "INSERT INTO TABLE_ACT_ENTRY (OBJID,ACT_CODE,ENTRY_TIME,ADDNL_INFO,ACT_ENTRY2CASE,ACT_ENTRY2USER,ENTRY_NAME2GBST_ELM) "
                    + "VALUES(TABLE_ACT_ENTRY_SEQ.NEXTVAL,'90600',sysdate,:addnlInfo,:caseObjId, :entry2User, :gbst)";

            Query insertActHEntry = session.createSQLQuery(insertActEntry);
            insertActHEntry.setParameter("addnlInfo", addnlInfo);
            insertActHEntry.setParameter(RMDCommonConstants.CASE_OBJ_ID,
                    toCaseObjId);
            insertActHEntry.setParameter("entry2User", actEntry2User);
            insertActHEntry.setParameter("gbst", appendId);
            insertActHEntry.executeUpdate();
            LOG.info("Insrted Into TABLE_ACT_ENTRY ");
            transaction.commit();
        } catch (RMDDAOConnectionException ex) {
            LOG.error("RMDDAOConnectionException occurred in mergeRx:", ex);
            LOG.info("RMDDAOConnectionException occurred in mergeRx:", ex);
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            transaction.rollback();

            LOG.error("Exception occurred in mergeRx:", e);
            LOG.info("Exception occurred in mergeRx:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPEND_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);
        }
    }
    /**
     * @Author :Mohamed
     * @return :List<FindCaseServiceVO>
     * @param : FindCaseServiceVO
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or not
     */
    @Override
    public List<FindCaseServiceVO> getRxDetailsForReClose(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<FindCaseServiceVO> findCaseServiceVOList = null;
        List<String> resultList = null;      
        boolean flag = false;
        Session session = null;
        FindCaseServiceVO objFindCaseService = new FindCaseServiceVO();
        
        try {
            if (!RMDCommonUtility.isNullOrEmpty(objFindCaseServiceVO.getStrCaseId())) {
                session = getHibernateSession();
                StringBuilder caseQry = new StringBuilder();
                caseQry.append(" select TO_CHAR(RX_CLOSE_DATE,'MM/DD/YY HH24:MI:SS') from gets_sd_cust_fdbk where CUST_FDBK2CASE = (Select objid from table_case where id_number = :caseId) order by objid desc ");

                Query hQry = session.createSQLQuery(caseQry.toString());
                hQry.setParameter(RMDCommonConstants.CASEID,
                        objFindCaseServiceVO.getStrCaseId());               
                resultList = hQry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                    findCaseServiceVOList = new ArrayList<FindCaseServiceVO>(); 
                    for (String rxDetails : resultList) {
                        if (null != RMDCommonUtility
                                .convertObjectToString(rxDetails)) {
                            flag = true;
                        }else{
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        objFindCaseService.setStrRxStatus(RMDServiceConstants.RX_STATUS_CLOSED);
                    }else{
                        objFindCaseService.setStrRxStatus(RMDServiceConstants.RX_STATUS_OPEN);
                    }
                    findCaseServiceVOList.add(objFindCaseService);
                }
                
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DEATAILS_FOR_RE_CLOSE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_DEATAILS_FOR_RE_CLOSE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return findCaseServiceVOList;
    }

    @Override
    public void updateCloseCaseResult(ReCloseVO objReCloseVO)
            throws RMDDAOException {
        Session session = null;
        CallableStatement seqStmt = null;
        Connection con = null;       
        try {
            session = getHibernateSession();
            con = getConnection(session);
            seqStmt = con
                    .prepareCall("call gets_sd_casetitle_pkg.case_close_noaction(?,?,?,?)");
            seqStmt.setString(1, objReCloseVO.getCaseId());
            seqStmt.setString(2, objReCloseVO.getReCloseAction());
            seqStmt.setString(3, objReCloseVO.getUserId());
            seqStmt.setString(4, objReCloseVO.getAppendCaseId());
            seqStmt.execute();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED,e);
            if (null != e.getErrorDetail()) {
                throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
                        new String[] {}, e.getErrorDetail().getErrorMessage(),
                        e, e.getErrorDetail().getErrorType());
            } else {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CLOSE_RESULT);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.FATAL_ERROR);
            }

        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED,e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CLOSE_RESULT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            if(null != seqStmt){
                try {
                    seqStmt.close();
                } catch (SQLException e) {                   
                    LOG.error(RMDCommonConstants.EXCEPTION_OCCURED,e);
                }
            }
            closeConnection(con);
            releaseSession(session);
        }
        
    }

    @Override
    public void reCloseResetFaults(ReCloseVO reCloseVO)
            throws RMDDAOException {
        Session session = null;   
        Connection con = null;
        try {
            session = getHibernateSession();
            con = getConnection(session);
            if (!RMDCommonUtility.isNullOrEmpty(reCloseVO.getCaseId())) {
                ScoreRxEoaVO scoreRxEoaVO = new ScoreRxEoaVO();
                scoreRxEoaVO.setCaseId(reCloseVO.getCaseId());
                manuallyResetFaults(con, scoreRxEoaVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RECLOSE_RESET_FAULTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RECLOSE_RESET_FAULTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
        	closeConnection(con);
            releaseSession(session);
        }
    }
    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding Case notes for a given case.
     */
    @Override
    public String addNotesToUnit(final AddNotesEoaServiceVO addnotesVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session objSession = null;
        try {
            if (RMDCommonConstants.STRING_UNIT.equalsIgnoreCase(addnotesVO.getApplyLevel()))
            {
                if (RMDCommonConstants.STRING_TRUE.equalsIgnoreCase(addnotesVO.getSticky())) {

                    StickyNotesDetailsVO unitStickyDetails= fetchStickyUnitLevelNotes(addnotesVO.getAssetNumber(),addnotesVO.getCustomerId(),addnotesVO.getAssetGrpName());
                    if (null!=unitStickyDetails.getObjId()) {
                        removeStickyNotes(unitStickyDetails.getObjId(),null,
                                unitStickyDetails.getApplyLevel());                     
                    } 

                } 
                addUnitLevelNotes(addnotesVO);
                result = RMDCommonConstants.SUCCESS;
            } 
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in addNotesToCase  method of CaseEoaDAOImpl :",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_NOTES_TO_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            
            releaseSession(objSession);
        }

        return result;
    }

    /**
     * @Author :
     * @return :void
     * @param :AddNotesEoaServiceVO
     * @throws :RMDDAOException
     * @Description:This method is used for adding Unit notes for a given case.
     */
    public void addUnitLevelNotes(final AddNotesEoaServiceVO addNotesEoaServiceVO)
            throws RMDDAOException {
        Session objSession = null;
        String isSticky = null;
        try {
            if (null != addNotesEoaServiceVO) {
            String strNoteDescription = EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(
                    addNotesEoaServiceVO.getNoteDescription()));
            objSession = getHibernateSession();
            StringBuilder notesQry = new StringBuilder();
            /* Query for updating the unit notes details */            
                notesQry.append("INSERT INTO GETS_SD_GENERIC_NOTES_LOG(OBJID,LAST_UPDATE_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,DESCRIPTION,STICKY,GENERIC2VEHICLE)");
                notesQry.append("VALUES (GETS_SD.GETS_SD_GENERIC_NOTES_LOG_SEQ.NEXTVAL,SYSDATE,:userId,SYSDATE,:userId,:notes,:isSticky,");
                notesQry.append("(SELECT VEH.OBJID FROM  TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ");
                notesQry.append("WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND ");
                notesQry.append("VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND TSP.SERIAL_NO=:vehNum AND TSP.X_VEH_HDR=:grpNam AND TBO.ORG_ID =:custID))");
                Query notesHqry = objSession
                        .createSQLQuery(notesQry.toString());
                notesHqry.setParameter(RMDCommonConstants.USERID,
                        addNotesEoaServiceVO.getUserId());
                notesHqry.setParameter(RMDCommonConstants.GET_NOTES,
                        strNoteDescription);
                if (RMDCommonConstants.STRING_TRUE
                        .equalsIgnoreCase(addNotesEoaServiceVO.getSticky())) {
                    isSticky = RMDCommonConstants.LETTER_Y;
                } else {
                    isSticky = RMDCommonConstants.LETTER_N;
                }
                notesHqry.setParameter(RMDCommonConstants.IS_STICKY, isSticky);
                notesHqry.setParameter(RMDCommonConstants.VEH_NUM, addNotesEoaServiceVO.getAssetNumber());
                notesHqry.setParameter(RMDCommonConstants.GRPNAM, addNotesEoaServiceVO.getAssetGrpName());  
                notesHqry.setParameter(RMDCommonConstants.CUSTID, addNotesEoaServiceVO.getCustomerId());    

                notesHqry.executeUpdate();
            }

        } catch (Exception e) {
            LOG.error(
                    "Exception occurred add Unit Notes  method of CaseEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_NOTES_TO_CASE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }

    }
    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDDAOException
     * @Description:This method is used fetching unit Sticky notes details for a
     *                   given case.
     */
    @Override
    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(final String assetNumber,final String customerId,final String assetGrpName)
            throws RMDDAOException {
        Session objSession = null;
        StickyNotesDetailsVO objStickyNotesDetails = new StickyNotesDetailsVO();
        StringBuilder unitNotesQry = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        List<String> vehObjId=null;
        String vehicleObjId = null;
        try {
            objSession = getHibernateSession();
            vehObjId=getVehicleObjId(assetNumber, customerId, assetGrpName);
            if (null != vehObjId) {
                vehicleObjId = RMDCommonUtility.convertObjectToString(vehObjId
                        .get(0));
            unitNotesQry
                    .append("SELECT 'Unit',GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_CONVERT_VARCHAR_FN('C',A.OBJID),TO_CHAR(A.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),A.CREATED_BY CREATED_BY, A.OBJID ");
            unitNotesQry
            .append("FROM GETS_SD_GENERIC_NOTES_LOG A WHERE GENERIC2VEHICLE=:vehicleObjid AND OBJID=(SELECT MAX(OBJID) ");
            unitNotesQry
            .append("FROM GETS_SD_GENERIC_NOTES_LOG L WHERE L.GENERIC2VEHICLE =  A.GENERIC2VEHICLE ");
            unitNotesQry
            .append("AND L.STICKY = 'Y')");

            Query unitNotesHqry = objSession.createSQLQuery(unitNotesQry
                    .toString());
            unitNotesHqry.setParameter(RMDCommonConstants.VEHICLE_OBJID, vehicleObjId);
            unitNotesHqry.setFetchSize(1);
            List<Object[]> notesDetailsList = unitNotesHqry.list();

            for (Object[] currentNote : notesDetailsList) {
                objStickyNotesDetails.setApplyLevel(RMDCommonUtility
                        .convertObjectToString(currentNote[0]));
                objStickyNotesDetails.setAdditionalInfo(ESAPI.encoder().encodeForXML(EsapiUtil.resumeSpecialChars(RMDCommonUtility
                        .convertObjectToString(currentNote[1]))));
                if (null != RMDCommonUtility
                        .convertObjectToString(currentNote[2])) {
                    Date closeDate = formatter.parse(RMDCommonUtility
                            .convertObjectToString(currentNote[2]));
                    objStickyNotesDetails.setEntryTime(closeDate);
                }
                objStickyNotesDetails.setCreatedBy(RMDCommonUtility
                        .convertObjectToString(currentNote[3]));
                objStickyNotesDetails.setObjId(RMDCommonUtility
                        .convertObjectToString(currentNote[4]));
            }
            }
                
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(RMDCommonConstants.EXCEPTION_OCCURED, e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }

        return objStickyNotesDetails;
    }

    
    /**
     * @Author :Vamshi
     * @return :String
     * @param :CaseRepairCodeEoaVO objCaseRepairCodeEoaVO
     * @throws :RMDDAOException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */
    @Override
    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO)
            throws RMDDAOException {
        String result = RMDCommonConstants.FAILURE;
        Session objSession = null;
        Query query = null;
        Transaction transaction = null;
        List<String> existingRepCodeObjIdList = null;
        StringBuilder qrystring = new StringBuilder();
        StringBuilder deleteQuery=null;
        try {
            objSession = getHibernateSession();
            /*
             * Calling getRepairCodes method for fetching Repair Code ObjId of
             * Particular RepairCode
             */
            List<RepairCodeEoaDetailsVO> arlRepairCodeEoaDetailsVOs = getRepairCodes(objCaseRepairCodeEoaVO
                    .getRepairCode());
            if (RMDCommonUtility
                    .isCollectionNotEmpty(arlRepairCodeEoaDetailsVOs)) {
                RepairCodeEoaDetailsVO objCodeEoaDetailsVO = arlRepairCodeEoaDetailsVOs
                        .get(0);
                transaction = objSession.beginTransaction();
                String userName = getEoaUserName(objSession,
                        objCaseRepairCodeEoaVO.getUserId());
                String caseObjid = getCaseObjid(objSession,
                        objCaseRepairCodeEoaVO.getCaseId());
                String codeQuery = "SELECT OBJID FROM GETS_SD_FINAL_REPCODE WHERE FINAL_REPCODE2CASE=:caseSeqId";
                query = objSession.createSQLQuery(codeQuery);
                query.setParameter(RMDCommonConstants.CASESEQID,
                        Long.parseLong(caseObjid));
                existingRepCodeObjIdList = query.list();
                if (RMDCommonUtility
                        .isCollectionNotEmpty(existingRepCodeObjIdList)) {
                    if (existingRepCodeObjIdList.size() > 1) {
                        deleteQuery=new StringBuilder();
                        deleteQuery
                                .append("DELETE FROM GETS_SD_FINAL_REPCODE WHERE OBJID IN (:finalRepCodeObjIdList)");
                        query = objSession.createSQLQuery(deleteQuery.toString());
                        query.setParameterList(
                                RMDCommonConstants.FINAL_REPCODE_OBJID_LIST,
                                EsapiUtil.encodeForSQLfromList(existingRepCodeObjIdList));
                        query.executeUpdate();
                        qrystring
                                .append("INSERT INTO GETS_SD_FINAL_REPCODE (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
                                        + " CREATION_DATE,CREATED_BY,FINAL_REPCODE2CASE,FINAL_REPCODE2REPCODE)"
                                        + " VALUES (GETS_SD_FINAL_REPCODE_SEQ.NEXTVAL,SYSDATE,:userName,SYSDATE,:userName,:caseSeqId,:repairCode)");
                        query = objSession.createSQLQuery(qrystring.toString());
                        query.setParameter(RMDCommonConstants.USERNAME,
                        		ESAPI.encoder().encodeForSQL(ORACLE_CODEC,userName));
                        query.setParameter(RMDCommonConstants.CASESEQID,
                                Long.parseLong(caseObjid));
                        query.setParameter(RMDCommonConstants.REPAIR_CODES,
                        		ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objCodeEoaDetailsVO.getRepairCodeId()));
                        query.executeUpdate();
                    } else if (existingRepCodeObjIdList.size() == 1) {
                        qrystring
                                .append("UPDATE GETS_SD_FINAL_REPCODE SET LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY=:userName,FINAL_REPCODE2REPCODE=:repairCode WHERE FINAL_REPCODE2CASE=:caseSeqId");
                        query = objSession.createSQLQuery(qrystring.toString());
                        query.setParameter(RMDCommonConstants.USERNAME,
                        		ESAPI.encoder().encodeForSQL(ORACLE_CODEC, userName));
                        query.setParameter(RMDCommonConstants.CASESEQID,
                                Long.parseLong(caseObjid));
                        query.setParameter(RMDCommonConstants.REPAIR_CODES,
                        		ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objCodeEoaDetailsVO.getRepairCodeId()));
                        query.executeUpdate();
                    } 
                } else {
                    qrystring
                            .append("INSERT INTO GETS_SD_FINAL_REPCODE (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,"
                                    + " CREATION_DATE,CREATED_BY,FINAL_REPCODE2CASE,FINAL_REPCODE2REPCODE)"
                                    + " VALUES (GETS_SD_FINAL_REPCODE_SEQ.NEXTVAL,SYSDATE,:userName,SYSDATE,:userName,:caseSeqId,:repairCode)");
                    query = objSession.createSQLQuery(qrystring.toString());
                    query.setParameter(RMDCommonConstants.USERNAME, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, userName));
                    query.setParameter(RMDCommonConstants.CASESEQID,
                            Long.parseLong(caseObjid));
                    query.setParameter(RMDCommonConstants.REPAIR_CODES,
                    		ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objCodeEoaDetailsVO.getRepairCodeId()));
                    query.executeUpdate();
                }
                CloseCaseVO closeCaseVO = new CloseCaseVO();
                closeCaseVO.setStrCaseID(objCaseRepairCodeEoaVO.getCaseId());
                closeCaseVO.setStrUserName(objCaseRepairCodeEoaVO.getUserId());
                closeCase(closeCaseVO);
                transaction.commit();
                result = RMDCommonConstants.SUCCESS;
            }
        } catch (Exception e) {
            if(null != transaction){
                transaction.rollback();
            }
            LOG.error("Exception occurred in castGPOCVote() Method:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return result;
    }

    /**
     * @Author :Vamshi
     * @return :String
     * @param :String caseObjId
     * @throws :RMDDAOException
     * @Description:This method is responsible for fetching previously Casted
     *                   vote.
     */

    @Override
    public String getPreviousVote(String caseObjId) throws RMDDAOException {
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        String previousVote = null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT RC.REPAIR_CODE, RC.REPAIR_DESC FROM GETS_SD_REPAIR_CODES RC, GETS_SD_FINAL_REPCODE FRP ");
            strQuery.append(" WHERE  FRP.FINAL_REPCODE2REPCODE = RC.OBJID AND FRP.FINAL_REPCODE2CASE=:caseObjId ORDER BY FRP.LAST_UPDATED_DATE DESC");
            Query query = session.createSQLQuery(strQuery.toString());
            query.setParameter(RMDCommonConstants.CASE_OBJ_ID, caseObjId);
            List<Object[]> repairCodeList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(repairCodeList)) {
                Object[] repairCodeDetails = repairCodeList.get(0);
                previousVote = RMDCommonUtility
                        .convertObjectToString(repairCodeDetails[0])
                        + RMDCommonConstants.PIPELINE_CHARACTER
                        + RMDCommonUtility
                                .convertObjectToString(repairCodeDetails[1]);
            } else {
                previousVote = RMDCommonConstants.NOT_YET_VOTED;
            }
        } catch (Exception e) {
            LOG.error("Exception occurred in getPreviousVote() Method:", e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return previousVote;
    }
    
    
    @Override
	@Cacheable(value = { "GetCaseType" })
    public Map<String,String> getCaseType(){
            Session session = null;
            final StringBuilder strQuery = new StringBuilder();
            Map<String,String> caseTypeMap=new HashMap<String, String>();
            List<Object[]> caseTypeList = null;
            try {
                session = getHibernateSession();            
                strQuery.append("SELECT OBJID,TITLE FROM SA.TABLE_GBST_ELM");                   
                Query query = session.createSQLQuery(strQuery.toString());
                caseTypeList=query.list();
                if(RMDCommonUtility.isCollectionNotEmpty(caseTypeList)){                
                    for (Object[] object : caseTypeList) {                  
                        caseTypeMap.put(RMDCommonUtility.convertObjectToString(object[0]),RMDCommonUtility.convertObjectToString(object[1]));
                                    
                    }
                }

            } catch (Exception e) {
                LOG.debug(e.getMessage(), e);
            }finally {
                releaseSession(session);
            }
            return caseTypeMap;
        }
    

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<CasesHeaderVO>
     * @throws:RMDDAOException
     * @Description:This method return the cases for that asset by invoking
     *                   database
     */
    @Override
    public List<CasesHeaderVO> getHeaderSearchCasesData(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        List<CasesHeaderVO> selectCaseHomeVOList = new ArrayList<CasesHeaderVO>();
        CasesHeaderVO objSelectCaseHomeVO;
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
        Query query = null;
        Object[] caseHome=null;
        try {
            session = getHibernateSession();
            strQuery.append("SELECT ");
            strQuery.append("TABLE_SITE_PART.X_VEH_HDR X_VEH_HDR,");
            strQuery.append("TABLE_SITE_PART.SERIAL_NO SERIAL_NO,");
            strQuery.append("TABLE_BUS_ORG.ORG_ID ORG_ID,");
            strQuery.append("TABLE_CASE.ID_NUMBER ID_NUMBER");
            strQuery.append(" FROM TABLE_CASE,TABLE_SITE_PART,TABLE_BUS_ORG,TABLE_USER,GETS_RMD_VEHICLE,GETS_RMD_VEH_HDR");
            strQuery.append(" WHERE TABLE_SITE_PART.OBJID= GETS_RMD_VEHICLE.VEHICLE2SITE_PART AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID");
            strQuery.append(" AND TABLE_SITE_PART.OBJID = TABLE_CASE.CASE_PROD2SITE_PART");
            strQuery.append(" AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER");

            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                strQuery.append(" AND TABLE_BUS_ORG.ORG_ID IN (:customer) ");

            }

            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {
                strQuery.append(" AND TABLE_CASE.ID_NUMBER = UPPER(:caseId) ");
            }
            query = session.createSQLQuery(strQuery.toString());

            if (null != objFindCaseServiceVO.getStrCustomerId()
                    && !objFindCaseServiceVO.getStrCustomerId().isEmpty()) {
                List<String> customerList = Arrays.asList(objFindCaseServiceVO
                        .getStrCustomerId().split(
                                RMDCommonConstants.COMMMA_SEPARATOR));
                query.setParameterList(RMDServiceConstants.STR_CUSTOMER,
                        customerList);
            }

            if (null != objFindCaseServiceVO.getCaseID()
                    && !objFindCaseServiceVO.getCaseID().isEmpty()) {

                query.setParameter(RMDCommonConstants.CASEID,
                        objFindCaseServiceVO.getCaseID());

            }
            List<Object[]> myCasesList = (ArrayList<Object[]>) query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(myCasesList)) {
                selectCaseHomeVOList=new ArrayList<CasesHeaderVO>(myCasesList.size());
                for (final Iterator<Object[]> obj = myCasesList.iterator(); obj
                        .hasNext();) {
                     objSelectCaseHomeVO = new CasesHeaderVO();
                     caseHome =  obj.next();
                    objSelectCaseHomeVO.setStrAssetNumber(RMDCommonUtility
                            .convertObjectToString(caseHome[1]));
                    objSelectCaseHomeVO.setStrCaseId(RMDCommonUtility
                            .convertObjectToString(caseHome[3]));
                    objSelectCaseHomeVO.setStrAssetHeader(RMDCommonUtility
                            .convertObjectToString(caseHome[0]));
                    objSelectCaseHomeVO.setStrcustomerName(RMDCommonUtility
                            .convertObjectToString(caseHome[2]));
                    selectCaseHomeVOList.add(objSelectCaseHomeVO);

                }
            }
            myCasesList=null;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Unexpected Error occured in CaseEOADAOImpl getHeaderSearchCasesData()",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);

        }

        return selectCaseHomeVOList;

    }

	@Override
	public String getDeliverRxURL(String caseId) throws RMDDAOException {
		  String result = RMDCommonConstants.FAILURE;
	        Session session = null;
	        String id=null;
	        try {
	        	
	            session = getHibernateSession();
	            StringBuilder caseQry = new StringBuilder();
	            if (null != caseId
                        && !caseId
                        .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING))
	            {
	            int index=caseId.indexOf(RMDCommonConstants.HYPHEN);
	        	if(index>-1)
	        	{
	        		id=caseId.substring(0,index);
	        	}
	            }
	                caseQry.append(" SELECT A.ADDNL_INFO FROM TABLE_CASE_ACT_HISTORY A ");
	                if (null != id
	                        && !id
	                                .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING) ) {
	                caseQry.append("WHERE ACT_ENTRY2CASE =(select objid from table_case where id_number=:case)");
	                }
	                else  if (null != caseId
	                        && !caseId
                            .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING) && null == id)
	                {
		                caseQry.append("WHERE ACT_ENTRY2CASE =(select objid from table_case where id_number=:caseID)");
	                }
	                caseQry.append(" AND A.TITLE='Recommendation Delivered' ");
	                if (null != caseId
	                        && !caseId
	                                .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
	                    caseQry.append(" AND ADDNL_INFO LIKE :caseId");
	                }
	                caseQry.append(" ORDER BY ENTRY_TIME ASC ");
	                Query caseHqry = session.createSQLQuery(caseQry.toString());
	                
	                if (null != id
	                        && !id
	                                .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING) ){
	                		caseHqry.setParameter(RMDCommonConstants.CASE,id);
	                	}
	                else   if(null != caseId
	                        && !caseId
                            .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING) && null == id
	                		)
	                {
                		caseHqry.setParameter(RMDCommonConstants.STR_CASE_ID,caseId);

	                }
	                if (null != caseId
	                        && !caseId
	                                .equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING)) {
                		caseHqry.setParameter(RMDCommonConstants.CASE_ID,
		                    				 RMDServiceConstants.PERCENTAGE+caseId+RMDServiceConstants.PERCENTAGE);
	                }
	                List<Object> deliverRxUrl = caseHqry.list();
	                if (RMDCommonUtility.isCollectionNotEmpty(deliverRxUrl)) {
	                        result = RMDCommonUtility
	                                .convertObjectToString(deliverRxUrl.get(0));
	            }
	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DEVICE_INFORMATION);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } 
	        catch (Exception e) {
	            LOG.error(
	                    "Unexpected Error occured in CaseEOADAOImpl getDeliverRxURL()",
	                    e);
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_CASES);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        }finally {
	            releaseSession(session);
	        }
	        return result;
	}

	@Override
	public List<CaseTrendVO> getOpenCommRxCount() throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		CaseTrendVO objCaseTrendVO = null;
		List<CaseTrendVO> caseTrendVOList = new ArrayList<CaseTrendVO>();
		Object[] configDetails = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT S_ORG_ID, count(*) AS OPEN_RX_COUNT FROM (SELECT FDBK.RX_CASE_ID ,    C.ID_NUMBER CASE_ID ,    C.TITLE CASETITLE ,    TO_CHAR(C.CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CASE_TIME ,  ");
			caseQry.append("RECOM.TITLE RX_TITLE ,    RECOM.CREATION_DATE CREATION_DT,    RECOM.LAST_UPDATED_DATE LAST_UPDATED_DT,    CASERECOM.URGENCY URGENCY ,    CASERECOM.EST_REPAIR_TIME REPAIR_TIME ,    RECOM.LOCO_IMPACT ,    TO_CHAR(DELV.DELV_DATE, 'MM/DD/YYYY HH24:MI:SS') DELV_DATE , ");    
			caseQry.append("DELV.RECOM_NOTES ,    VEH.VEHICLE2MODEL MODELOBJID ,    VEH.VEHICLE2FLEET FLEETOBJID ,    C.CALLTYPE2GBST_ELM CASETYPEOBJID ,RECOM.OBJID RECOMOBJID ,    TSP.SERIAL_NO ,    TBO.S_ORG_ID ,    TSP.X_VEH_HDR ,    ROW_NUMBER() OVER ( PARTITION BY FDBK.RX_CASE_ID ORDER BY DELV.DELV_DATE DESC) ROWNUMBER ,TO_CHAR(FDBK.RX_CLOSE_DATE, 'MM/DD/YYYY HH24:MI:SS') RX_CLOSE_DATE,    RECOM_TYPE ");
			caseQry.append("FROM TABLE_CASE C ,    GETS_SD_RECOM RECOM ,    GETS_SD_CASE_RECOM CASERECOM ,    GETS_SD_RECOM_DELV DELV ,    TABLE_SITE_PART TSP ,GETS_SD_CUST_FDBK FDBK ,    GETS_RMD_VEHICLE VEH,    GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ");
			caseQry.append("WHERE DELV.RECOM_DELV2CASE = C.OBJID AND DELV.RECOM_DELV2RECOM = RECOM.OBJID AND CASERECOM.CASE_RECOM2CASE  = C.OBJID AND CASERECOM.CASE_RECOM2RECOM = RECOM.OBJID AND CASE_PROD2SITE_PART = TSP.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
			caseQry.append("AND FDBK.CUST_FDBK2CASE = C.OBJID AND CASESTS2GBST_ELM  IN (268435544,268435947,268435478,268435523) AND FDBK.RX_CLOSE_DATE IS NULL AND FDBK.OBJID= DELV.RECOM_DELV2CUST_FDBK) A WHERE ROWNUMBER = 1 and URGENCY = 'B' GROUP BY S_ORG_ID order by OPEN_RX_COUNT desc ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setFetchSize(1000);
			resultList = caseHqry.list();
			if (resultList != null && !resultList.isEmpty()) {
				caseTrendVOList = new ArrayList<CaseTrendVO>(resultList.size());
			}
			if (resultList != null
					&& RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objCaseTrendVO = new CaseTrendVO();

					objCaseTrendVO.setCustomer(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objCaseTrendVO.setCount(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					caseTrendVOList.add(objCaseTrendVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return caseTrendVOList;
	}

	@Override
	public List<CaseConvertionVO> getCaseConversionDetails()
			throws RMDDAOException {
		List<Object[]> resultListFrom = null;
		CaseConvertionVO objCaseConvertionVO = null;
		List<CaseConvertionVO> caseConvertionVOList = null;
		Session dwSession=null;
		StringBuilder caseQryFrom =null;
		try {		    
	             dwSession=getDWHibernateSession();	                    
	             caseQryFrom = new StringBuilder();
    			caseQryFrom.append("select recomm_table.creation_time,round(recomm_table.delivery_case/case_table.new_case,2) as case_count from (select count(a.recomm_del_objid) as delivery_case, TO_CHAR(creation_time,'YYYY-MM') as creation_time ");
    			caseQryFrom.append("from gets_dw_eoa_recom_del a, gets_dw_eoa_case b where a.case_objid = b.case_objid and  TO_CHAR(a.case_creation_time,'YYYY') in(to_char(sysdate,'YYYY'), to_char(sysdate,'YYYY')- 1,to_char(sysdate,'YYYY')-2)  and a.delivered_by != 'auto_rx_deliver' and ");
    			caseQryFrom.append("b.case_type in ('EOA Problem', 'ESTP Problem') AND UPPER(b.CASE_TITLE) NOT LIKE '%ERROR IN JDPAD%' group by TO_CHAR(creation_time,'YYYY-MM')) recomm_table , (select count(CASE_OBJID) ");
    			caseQryFrom.append("as new_case,TO_CHAR(creation_time,'YYYY-MM') as creation_time  from gets_DW_EOA_CASE where TO_CHAR(creation_time,'YYYY') in(to_char(sysdate,'YYYY'), to_char(sysdate,'YYYY')- 1,to_char(sysdate,'YYYY')-2) and case_type in ('EOA Problem', 'ESTP Problem') AND UPPER(CASE_TITLE) NOT LIKE '%ERROR IN JDPAD%' group by TO_CHAR(creation_time,'YYYY-MM')  ) case_table where recomm_table.creation_time = case_table.creation_time order by 1");
    			Query caseHqryFrom = dwSession.createSQLQuery(caseQryFrom.toString());
    			caseHqryFrom.setFetchSize(1000);
    			resultListFrom = caseHqryFrom.list();
    			if (RMDCommonUtility.isCollectionNotEmpty(resultListFrom)) {
    			    caseConvertionVOList = new ArrayList<CaseConvertionVO>(resultListFrom.size());
    			    for(Object[] configDetails : resultListFrom){   
                        objCaseConvertionVO = new CaseConvertionVO();
                        objCaseConvertionVO.setConvCount(RMDCommonUtility
                                .convertObjectToString(configDetails[1]));
                        String [] date = RMDCommonUtility
                                .convertObjectToString(configDetails[0]).split("-");
                        objCaseConvertionVO.setYear(date[0]);
                        objCaseConvertionVO.setMonth(date[1]);
                        caseConvertionVOList.add(objCaseConvertionVO);
                    }
    			}
    			resultListFrom = null;	        
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
		    if(null !=dwSession){
		        dwSession.flush();
                dwSession.clear();
		    }
			releaseSession(dwSession);
		}
		return caseConvertionVOList;
	}

	@Override
	public String getCaseConversionPercentage() throws RMDDAOException {
		Session session = null;
		String percentage = null;
		String lookUpQry = null;
		int defaultDays = 0;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFrom = new StringBuilder();
			caseQryFrom.append("select avg(round(recomm_table.delivery_case/case_table.new_case*100,2)) as conversion_percentage from (select count(a.recomm_del_objid) as delivery_case, TO_CHAR(creation_time,'DD-MM-YYYY') as creation_time  ");
			caseQryFrom.append("from gets_dw_eoa_recom_del@RMD_EOA_DW.WORLD a, gets_dw_eoa_case@RMD_EOA_DW.WORLD b where a.case_objid = b.case_objid and a.case_creation_time >= TO_DATE( sysdate) -:days AND a.case_creation_time < TO_DATE(sysdate)   and a.delivered_by != 'auto_rx_deliver' and ");
			caseQryFrom.append("b.case_type in ('EOA Problem', 'ESTP Problem') group by TO_CHAR(creation_time,'DD-MM-YYYY')) recomm_table , (select count(CASE_OBJID) as new_case,TO_CHAR(creation_time,'DD-MM-YYYY') as creation_time ");
			caseQryFrom.append("from gets_DW_EOA_CASE@RMD_EOA_DW.WORLD where creation_time >= TO_DATE( sysdate) -:days AND creation_time < TO_DATE(sysdate) and case_type in ('EOA Problem', 'ESTP Problem') group by TO_CHAR(creation_time,'DD-MM-YYYY')  ) case_table where recomm_table.creation_time = case_table.creation_time order by 1");
			lookUpQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CASE_CONVERSION_PERCENTAGE_DEFAULT_DAYS'";
			Query daysHqry = session.createSQLQuery(lookUpQry);
			defaultDays = RMDCommonUtility.convertObjectToInt(daysHqry
					.uniqueResult());
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
			caseHqryFrom.setParameter(RMDServiceConstants.NO_OF_DAYS,
					defaultDays);
			caseHqryFrom.setFetchSize(1000);
			percentage = RMDCommonUtility.convertObjectToString(caseHqryFrom
					.uniqueResult());
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_CONVERSION_PERCENTAGE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_CONVERSION_PERCENTAGE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return percentage;
	}

	@Override
	public List<CaseConvertionVO> getTopNoActionRXDetails()
			throws RMDDAOException {
		List<Object[]> resultListFrom = null;
		Session session = null;
		CaseConvertionVO objCaseConvertionVO = null;
		List<CaseConvertionVO> caseConvertionVOList = new ArrayList<CaseConvertionVO>();
		Object[] configDetails = null;
		String lookUpQry = null;
		int defaultDays = 0;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFrom = new StringBuilder();
			caseQryFrom.append("SELECT TITLE, count(*) as value FROM SA.TABLE_CASE_DISPLAY_V TCD,TABLE_BUS_ORG TBO,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE GRV,GETS_RMD_VEH_HDR GHV ");
			caseQryFrom.append("WHERE UPPER(TCD.TITLE )LIKE UPPER('%No Action') AND TCD.creation_time >= TO_DATE( sysdate) -:days AND TCD.creation_time < TO_DATE(sysdate) AND TCD.SITE_PART_OBJID=TSP.OBJID ");
			caseQryFrom.append("AND TSP.OBJID = GRV.VEHICLE2SITE_PART AND GRV.VEHICLE2VEH_HDR=GHV.OBJID AND GHV.VEH_HDR2BUSORG = TBO.OBJID AND TCD.OBJID in (select AR_LIST2CASE from gets_tool_ar_list where FC_FAULT is null) AND TCD.TYPE IN('EOA Problem','ESTP PROBLEM') ");
			caseQryFrom.append("group by TITLE having count(*)= (select max(count(TCD.TITLE)) FROM SA.TABLE_CASE_DISPLAY_V TCD,TABLE_BUS_ORG TBO,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE GRV,GETS_RMD_VEH_HDR GHV ");
			caseQryFrom.append("WHERE UPPER(TCD.TITLE )LIKE UPPER('%No Action') AND TCD.creation_time >= TO_DATE( sysdate) -:days AND TCD.creation_time < TO_DATE(sysdate) AND TCD.SITE_PART_OBJID=TSP.OBJID AND TSP.OBJID = GRV.VEHICLE2SITE_PART ");
			caseQryFrom.append("AND GRV.VEHICLE2VEH_HDR=GHV.OBJID AND GHV.VEH_HDR2BUSORG = TBO.OBJID  AND TCD.OBJID in (select AR_LIST2CASE from gets_tool_ar_list where FC_FAULT is null) AND TCD.TYPE IN('EOA Problem','ESTP PROBLEM') group by TITLE) order by value desc ");
			lookUpQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'TOP_NO_ACTION_RX_REPORT_DAYS'";
			Query daysHqry = session.createSQLQuery(lookUpQry);
			defaultDays = RMDCommonUtility.convertObjectToInt(daysHqry.uniqueResult());
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
			caseHqryFrom.setParameter(RMDServiceConstants.NO_OF_DAYS,
					defaultDays);
			caseHqryFrom.setFetchSize(1000);
			resultListFrom = caseHqryFrom.list();
			if (resultListFrom != null && !resultListFrom.isEmpty()) {
				caseConvertionVOList = new ArrayList<CaseConvertionVO>(resultListFrom.size());
			}
			if (resultListFrom != null
					&& RMDCommonUtility.isCollectionNotEmpty(resultListFrom)) {
				for (final Iterator<Object[]> obj = resultListFrom.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objCaseConvertionVO = new CaseConvertionVO();
					objCaseConvertionVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objCaseConvertionVO.setRxCount(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					caseConvertionVOList.add(objCaseConvertionVO);
				}
			}
			resultListFrom = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TOP_NO_ACTION_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TOP_NO_ACTION_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return caseConvertionVOList;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDDAOException {
		Session session = null;
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO=null;
        List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO =null;
        List<Object[]> resultList = null;
        StringBuilder caseQryFrom=null;
		try {
			session = getHibernateSession();
			caseQryFrom = new StringBuilder();
			caseQryFrom.append("select NOTES_DESCRIPTION,CREATED_BY FROM GETS_RMD.COMM_NOTES WHERE FLAG = 'Y' ORDER BY LAST_UPDATED_DATE DESC");
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
            caseHqryFrom.setFetchSize(1000);
            resultList = caseHqryFrom.list();
             if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                 arlGeneralNotesEoaServiceVO = new ArrayList<GeneralNotesEoaServiceVO>(
                            resultList.size());
                    for(Object[] objCommNotes : resultList){          
                        objGeneralNotesEoaServiceVO = new GeneralNotesEoaServiceVO();
                        objGeneralNotesEoaServiceVO.setNotesdesc(RMDCommonUtility
                                .convertObjectToString(objCommNotes[0]));
                        objGeneralNotesEoaServiceVO.setEnteredby(RMDCommonUtility
                                .convertObjectToString(objCommNotes[1]));                    
                        arlGeneralNotesEoaServiceVO.add(objGeneralNotesEoaServiceVO);

                    }
                }
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_COMM_NOTES_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_COMM_NOTES_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
			resultList=null;
            caseQryFrom=null;
		}
		return arlGeneralNotesEoaServiceVO;
	}
	
	@Override
    public boolean getAddRepCodeDetails(String caseId) throws RMDDAOException {
        Session session = null;
        boolean retValue = true;
        List<Object[]> resultList = null;
        List<String> listNameList = new ArrayList<String>();
        listNameList.add(CLOSURE_ADDITIONAL_CODE_REQUIRED);
        listNameList.add(MISS_4F_REPAIR_CODE);
        listNameList.add(MISS_4B_REPAIR_CODE);

        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();

            caseQry.append(" SELECT FRP.FINAL_REPCODE2REPCODE FROM GETS_SD_FINAL_REPCODE FRP,GETS_SD_REPAIR_CODES RC, TABLE_CASE TC");
            caseQry.append(" WHERE TC.ID_NUMBER = :caseId ");
            caseQry.append(" AND TC.OBJID = FRP.FINAL_REPCODE2CASE ");
            caseQry.append(" AND FRP.FINAL_REPCODE2REPCODE = RC.OBJID ");
            caseQry.append(" AND RC.REPAIR_CODE in (select LOOK_VALUE  from gets_rmd_lookup ");
            caseQry.append(REP_CODE_WHERE_QRY);
            caseQry.append(" and LOOK_STATE != :lookState)");


            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDCommonConstants.CASEID, caseId);
            caseHqry.setParameter(RMDServiceConstants.LIST_NAME, CLOSURE_ADDITIONAL_CODE_REQUIRED);
            caseHqry.setParameter(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.IN_ACTIVE);
            resultList = caseHqry.list();


            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

                final Iterator<Object[]> obj = resultList.iterator(); 

                if(obj.hasNext() && null != obj.next()) {
                    caseQry = new StringBuilder();

                    caseQry.append(" SELECT FRP.FINAL_REPCODE2REPCODE FROM GETS_SD_FINAL_REPCODE FRP,GETS_SD_REPAIR_CODES RC, TABLE_CASE TC");
                    caseQry.append(" WHERE TC.ID_NUMBER = :caseId ");
                    caseQry.append(" AND TC.OBJID = FRP.FINAL_REPCODE2CASE ");
                    caseQry.append(" AND FRP.FINAL_REPCODE2REPCODE = RC.OBJID ");
                    caseQry.append(" AND RC.REPAIR_CODE not in (select LOOK_VALUE  from gets_rmd_lookup ");
                    caseQry.append(" where LIST_NAME in (:list_name_list)");
                    caseQry.append(" and LOOK_STATE != :lookState)");


                    caseHqry = session.createSQLQuery(caseQry.toString());

                    caseHqry.setParameter(RMDCommonConstants.CASEID, caseId);
                    caseHqry.setParameterList("list_name_list", listNameList);
                    caseHqry.setParameter(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.IN_ACTIVE);
                    resultList = caseHqry.list();
                    
                    if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

                        final Iterator<Object[]> obj1 = resultList.iterator(); 

                        if(obj1.hasNext()) {
                            if (null != obj1.next()) 
                                retValue = true;
                            
                            else retValue = false;
                        }
                    }
                    else retValue = false;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ATTACHED_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                            RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return retValue;
    }

    @Override
    public boolean getLookUpRepCodeDetails(String repairCodeList)
            throws RMDDAOException {
        Session session = null;
        boolean retValue = true;
        List<String> repList = new ArrayList<String>();

        for (String splitStr: repairCodeList.split(",")) {
            repList.add(splitStr.trim());
        }

        List<Object[]> resultList = null;
        try {
            session = getHibernateSession();
            StringBuilder caseQry = new StringBuilder();

            caseQry.append(" select LOOK_VALUE  from gets_rmd_lookup ");
            caseQry.append(" where LIST_NAME = :listName");
            caseQry.append(" and LOOK_STATE != :lookState");
            caseQry.append(" AND LOOK_VALUE in (SELECT repair_code from GETS_SD_REPAIR_CODES WHERE objid in (:repCodeList))");


            Query caseHqry = session.createSQLQuery(caseQry.toString());
            caseHqry.setParameter(RMDServiceConstants.LIST_NAME, CLOSURE_ADDITIONAL_CODE_REQUIRED);
            caseHqry.setParameter(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.IN_ACTIVE);
            caseHqry.setParameterList(REP_CODE_LIST, repList);
            resultList = caseHqry.list();

            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

                final Iterator<Object[]> obj = resultList.iterator(); 


                if(obj.hasNext() && null != obj.next()) {
                    caseQry = new StringBuilder();
                    
                    caseQry.append(" SELECT repair_code from GETS_SD_REPAIR_CODES WHERE objid in (:repCodeList)");
                    caseQry.append("and repair_code not in (select LOOK_VALUE  from gets_rmd_lookup");
                    caseQry.append(" where LIST_NAME = :listName");
                    caseQry.append(" and LOOK_STATE != :lookState)");


                    caseHqry = session.createSQLQuery(caseQry.toString());

                    caseHqry.setParameter(RMDServiceConstants.LIST_NAME, CLOSURE_ADDITIONAL_CODE_REQUIRED);
                    caseHqry.setParameter(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.IN_ACTIVE);
                    caseHqry.setParameterList(REP_CODE_LIST, repList);
                    resultList = caseHqry.list();

                    if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

                        final Iterator<Object[]> obj1 = resultList.iterator(); 

                        if(obj1.hasNext()) {
                            if (null != obj1.next()) 
                                retValue = true;
                            else retValue = false;
                        }
                    }
                    else retValue = false;
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ATTACHED_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                            RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }

        return retValue;
    }
    

	@Override
	public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(String rxCaseId)
			throws RMDDAOException {
        Session session = null;
        List<CaseScoreRepairCodeVO> closeOutRepairCodeVOList = null;
        List<Object[]> resultList = null;
        CaseScoreRepairCodeVO objCloseOutRepairCodeDetails;
        try {
            if (!RMDCommonUtility.isNullOrEmpty(rxCaseId)) {
                session = getHibernateSession();
                StringBuilder caseQry = new StringBuilder();
                caseQry.append(" SELECT REPAIR_CODE, REPAIR_DESC, OBJID FROM GETS_SD_REPAIR_CODES WHERE REPAIR_CODE in ('9209','9023','1001','9030')");
                caseQry.append(" union SELECT rxtask.REPAIR_CODE,RXTASK.REPAIR_DESC, re.OBJID  FROM GETS_SD_RX_TASK_V RXTASK,  GETS_SD_REPAIR_CODES RE WHERE RXTASK.REPAIR_CODE=RE.REPAIR_CODE  ");
                caseQry.append(" AND (RX_CASE_ID= :rx_Case_ID");
                caseQry.append(" AND RXTASK.REPAIR_CODE IS NOT NULL ");
                caseQry.append(" AND RXTASK.REPAIR_DESC IS NOT NULL) ");
                Query caseHqry = session.createSQLQuery(caseQry.toString());
                
                if (!RMDCommonUtility.isNullOrEmpty(rxCaseId)) {
                    caseHqry.setParameter(RMDCommonConstants.RX_CASE_ID,
                    		rxCaseId);
                }
                caseHqry.setFetchSize(10);
                resultList = caseHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                    closeOutRepairCodeVOList = new ArrayList<CaseScoreRepairCodeVO>();
                    for (final Iterator<Object[]> obj = resultList.iterator(); obj
                            .hasNext();) {
                        objCloseOutRepairCodeDetails = new CaseScoreRepairCodeVO();
                        final Object[] closeOutRepairCodeDetails =  obj
                                .next();
                        objCloseOutRepairCodeDetails
                                .setRepairCode(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[0]));
                        objCloseOutRepairCodeDetails
                                .setDescription(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[1]));
                        objCloseOutRepairCodeDetails
                                .setRepairCodeId(RMDCommonUtility
                                        .convertObjectToString(closeOutRepairCodeDetails[2]));
                        closeOutRepairCodeVOList
                                .add(objCloseOutRepairCodeDetails);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CLOSEOUT_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } finally {
            releaseSession(session);
        }
        return closeOutRepairCodeVOList;
    }

    @Override
    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDDAOException {
        Session session = null;
        StringBuilder vehBomQry = null;
        StringBuilder rxBomQry =null;
        List<Object[]> rxBomResult=null;
        List<Object[]> vehBomResult=null;
        Map<String,Map<String,String>> vehBomMap= null;
        Map<String,String> bomTypeMap=null;
        Map<String,String> vehDetailsMap=null;
        List<String> rxList = Arrays.asList(rxObjId.split(RMDCommonConstants.COMMMA_SEPARATOR));
        boolean vehBomQryExecuted=true;
        List<String> resulList = new ArrayList<String>();
        String rxTitle=RMDCommonConstants.EMPTY_STRING;
        
        try{
            session = getHibernateSession();
            
            for(String objId : rxList){
                rxBomQry = new StringBuilder();
                rxBomQry.append("SELECT RECOMCONFIG.config_name,RECOMCONFIG.value1,RECOMCONFIG.CONFIG_FUNCTION,RECOM.TITLE FROM GETS_SD_RECOM_CONFIG RECOMCONFIG,GETS_SD_RECOM RECOM ");
                rxBomQry.append("WHERE RECOMCONFIG.recom_config2recom = RECOM.objid AND RECOM.objid=:rxObjId");
                Query rxBomHQry = session.createSQLQuery(rxBomQry.toString());
                rxBomHQry.setParameter(RMDCommonConstants.RX_OBJ_ID, objId);
                rxBomResult=rxBomHQry.list();
                if(RMDCommonUtility.isCollectionNotEmpty(rxBomResult)){
                    if(vehBomQryExecuted){
                        vehBomQry = new StringBuilder();
                        vehBomQry.append("SELECT  C.VEH_CFG2VEHICLE,B.CONFIG_ITEM CONFIGITEM,C.CURRENT_VERSION CVER,V.VEHICLE_NO FROM GETS_RMD_PARMDEF A,GETS_RMD_MASTER_BOM B, GETS_RMD_VEHCFG C,GETS_RMD_CUST_RNH_RN_V V ");
                        vehBomQry.append("WHERE A.OBJID(+) = B.MASTER_BOM2PARM_DEF AND B.OBJID(+) = C.VEHCFG2MASTER_BOM AND C.VEH_CFG2VEHICLE=V.VEHICLE_OBJID ");
                        
                        if(RMDCommonConstants.TOOL_OUT_PUT.equalsIgnoreCase(fromScreen))
                            vehBomQry.append("AND V.ORG_ID=:customer ");
                        else
                            vehBomQry.append("AND V.CUST_NAME=:customer ");
                        
                        vehBomQry.append("AND VEHICLE_NO NOT LIKE '%BAD%' AND VEHICLE_HDR=:rnh AND VEHICLE_NO IN(:roadNumber) AND B.BOM_STATUS ='Y' AND C.CURRENT_VERSION IS NOT NULL");
                        Query vehBomHQry = session.createSQLQuery(vehBomQry.toString());
                        vehBomHQry.setParameter(RMDCommonConstants.CUSTOMER, customer);
                        vehBomHQry.setParameter(RMDCommonConstants.RNH, rnh);
                        vehBomHQry.setParameterList(RMDCommonConstants.ROAD_NUMBER, Arrays.asList(rn.split(RMDCommonConstants.COMMMA_SEPARATOR)));
                        vehBomResult=vehBomHQry.list();
                        if(RMDCommonUtility.isCollectionNotEmpty(vehBomResult)){
                            vehBomMap=new LinkedHashMap<String, Map<String,String>>();
                            vehDetailsMap = new LinkedHashMap<String, String>();
                            for(Object[] obj:vehBomResult){
                                if(null == vehBomMap.get(RMDCommonUtility.convertObjectToString(obj[0]))){
                                    bomTypeMap = new LinkedHashMap<String, String>();
                                }else{
                                    bomTypeMap=vehBomMap.get(RMDCommonUtility.convertObjectToString(obj[0]));
                                }
                                if(null == vehDetailsMap.get(RMDCommonUtility.convertObjectToString(obj[0]))){
                                    vehDetailsMap.put(RMDCommonUtility.convertObjectToString(obj[0]),RMDCommonUtility.convertObjectToString(obj[3]));
                                }
                                bomTypeMap.put(RMDCommonUtility.convertObjectToString(obj[1]), RMDCommonUtility.convertObjectToString(obj[2]));
                                vehBomMap.put(RMDCommonUtility.convertObjectToString(obj[0]), bomTypeMap);
                            }
                        }
                        vehBomQryExecuted=false;
                    }    
                    if(null != vehBomMap && !vehBomMap.isEmpty()){
                            for (String key : vehBomMap.keySet()) {
                                boolean tracker=false;
                                Map<String,String> tempMap=vehBomMap.get(key);
                                for(Object[] ob:rxBomResult){
                                    String rxBomType=RMDCommonUtility.convertObjectToString(ob[0]);
                                    String rxBomVal=RMDCommonUtility.convertObjectToString(ob[1]);
                                    String operator=RMDCommonUtility.convertObjectToString(ob[2]);
                                    rxTitle=RMDCommonUtility.convertObjectToString(ob[3]);
                                    if(tempMap.containsKey(rxBomType)){
                                        if((RMDCommonConstants.EQUAL_SYMBOL.equals(operator) && rxBomVal.equals(tempMap.get(rxBomType)))||(RMDCommonConstants.NOT_EQUAL_SYMBOL.equals(operator) && !rxBomVal.equals(tempMap.get(rxBomType)))){
                                            tracker=true;
                                            break;
                                        }else if(RMDCommonConstants.GREATER_THAN_SYMBOL.equals(operator)) {
                                            try{
                                                if(Float.valueOf(tempMap.get(rxBomType))>Float.valueOf(rxBomVal)){
                                                    tracker=true;
                                                    break;
                                                }
                                            }catch(NumberFormatException e){
                                                LOG.error("Number formate Exception in validateVehBoms for Config type : "+rxBomType+" Config val : "+rxBomVal+" Error Msg :"+e.getMessage());
                                            }
                                            
                                        }else if(RMDCommonConstants.LESS_THAN_SYMBOL.equals(operator)) {
                                            try{
                                                if(Float.valueOf(tempMap.get(rxBomType))<Float.valueOf(rxBomVal)){
                                                    tracker=true;
                                                    break;
                                                }
                                            }catch(NumberFormatException e){
                                                LOG.error("Number formate Exception in validateVehBoms for Config type : "+rxBomType+" Config val : "+rxBomVal+" Error Msg :"+e.getMessage());
                                            }
                                            
                                        }else if(RMDCommonConstants.GREATER_THAN_EQUAL_SYMBOL.equals(operator)) {
                                            try{
                                                if(Float.valueOf(tempMap.get(rxBomType))>=Float.valueOf(rxBomVal)){
                                                    tracker=true;
                                                    break;
                                                }
                                            }catch(NumberFormatException e){
                                                LOG.error("Number formate Exception in validateVehBoms for Config type : "+rxBomType+" Config val : "+rxBomVal+" Error Msg :"+e.getMessage());
                                            }
                                            
                                        }else if(RMDCommonConstants.LESS_THAN_EQUAL_SYMBOL.equals(operator)) {
                                            try{
                                                if(Float.valueOf(tempMap.get(rxBomType))<=Float.valueOf(rxBomVal)){
                                                    tracker=true;
                                                    break;
                                                }
                                            }catch(NumberFormatException e){
                                                LOG.error("Number formate Exception in validateVehBoms for Config type : "+rxBomType+" Config val : "+rxBomVal+" Error Msg :"+e.getMessage());
                                            }
                                            
                                        }else{
                                            tracker=false;
                                        }
                                    }else{
                                        tracker=false;
                                        continue;
                                    }
                                    
                                }
                                if(!tracker){
                                    if(RMDCommonConstants.TOOL_OUT_PUT.equalsIgnoreCase(fromScreen))
                                        resulList.add(rxTitle);
                                    else
                                        resulList.add(rnh+RMDCommonConstants.HYPHEN+vehDetailsMap.get(key));
                                }
                            }
                    }
                }
            }

            if(null==resulList || resulList.isEmpty() ){
                resulList.add(RMDCommonConstants.LETTER_Y);
            }
            
            
            
        }catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VALIDATE_VEH_BOMS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return resulList;
    }

	@Override
	public List<String> getUnitConversionRoles() throws RMDDAOException {
		List<String> roleNames = null;
		Session session = null;
		try{
            session = getHibernateSession();
            Query roleNameQry = session.createSQLQuery("SELECT ROLE_NAME FROM gets_tools.gets_tool_ce_param_role_link WHERE CONVERSION_FLAG = 'Y'");
            roleNames = roleNameQry.list();
	    }catch (Exception e) {
	        LOG.error(e, e);
        } finally {
            releaseSession(session);
        }
		return roleNames;
	}
	
	/**
     * @param
     * @return List<RecomDelvInfoServiceVO>
     * @throws RMDDAOException
     * @Description To fetch the case RX details from gets_sd_case_recom table
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDDAOException {
        // TODO Auto-generated method stub
        RecomDelvInfoServiceVO recomVO = null;
        Session session = null;
        Query hibernateQuery = null;
        List<Object[]> arrQueryList = null;
        List<RecomDelvInfoServiceVO> recomList = new ArrayList<RecomDelvInfoServiceVO>();
        try {
            session = getHibernateSession();
            String recomInfoQuery = "SELECT RECOM_DELV2RECOM,RECOM.TITLE,RECOM.URGENCY,recom.EST_REPAIR_TIME "
                    + "FROM GETS_SD_RECOM_DELV recomDelv,TABLE_CASE tc,GETS_SD_RECOM recom "
                    + "WHERE recomDelv.RECOM_DELV2CASE = tc.OBJID AND recomDelv.RECOM_DELV2RECOM = recom.objid AND tc.id_number=:caseId order by DELV_DATE desc";

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
    
    private void closeMQConnection(RxMQConnection rxMQConnection, String qName) throws MQException{
    	if(rxMQConnection != null){
	    	rxMQConnection.closeOutputQueue(qName);
	    	rxMQConnection.disconnect();
    	}
    }
    
    private RxDeliveryInputVO loadRxDeliveryInputVO(RecomDelvInfoServiceVO recomDelvInfoServiceVO, 
    		BaseRxExecutor baseRxExecutor, String actionType) throws Exception{
    	RxDeliveryInputVO rxDelvInputVO = new RxDeliveryInputVO(); 
    	
        List<RxDeliveryDocVO> rxDeliveryDocVOs = null;
        List<RecommDelvDocVO> recommDelvDocVOs =  recomDelvInfoServiceVO.getLstAttachment();
        if(recommDelvDocVOs != null && !recommDelvDocVOs.isEmpty()){
        	rxDeliveryDocVOs = new ArrayList<RxDeliveryDocVO>();
        	for(RecommDelvDocVO delvDocVO : recommDelvDocVOs){
        		RxDeliveryDocVO rxDeliveryDocVO = new RxDeliveryDocVO();
        		rxDeliveryDocVO.setDocData(delvDocVO.getDocData());
        		rxDeliveryDocVO.setDocPath(delvDocVO.getDocPath());
        		rxDeliveryDocVO.setDocTitle(delvDocVO.getDocTitle());
        		rxDeliveryDocVOs.add(rxDeliveryDocVO);
        	}
        }
        
        rxDelvInputVO.setCaseId(recomDelvInfoServiceVO.getStrCaseID());
        if(recomDelvInfoServiceVO.getCaseObjId() != null && !recomDelvInfoServiceVO.getCaseObjId().trim().equals("")){
        	rxDelvInputVO.setCaseObjid(Long.valueOf(recomDelvInfoServiceVO.getCaseObjId()));
        }
        rxDelvInputVO.setRxObjid(Long.valueOf(recomDelvInfoServiceVO.getStrRxObjId()));
        rxDelvInputVO.setCustomerName(recomDelvInfoServiceVO.getCustomerName());
        rxDelvInputVO.setRxDelvUser(recomDelvInfoServiceVO.getStrUserName());
        String recomNotes = AppSecUtil.decodeString(recomDelvInfoServiceVO.getStrRecomNotes());
        String strRecomNotes = AppSecUtil.stripNonValidXMLCharactersForRx(recomNotes);
        rxDelvInputVO.setRecomNotes(strRecomNotes);
        rxDelvInputVO.setLstAttachment(rxDeliveryDocVOs);
        rxDelvInputVO.setPilotMode(false);
        //rxDelvInputVO.setInitTrigFltId(1l);
        rxDelvInputVO.setRxUrgency(recomDelvInfoServiceVO.getStrUrgRepair());
        rxDelvInputVO.setEstRepairTime(recomDelvInfoServiceVO.getStrEstmRepTime());
        rxDelvInputVO.setCustFdbkObjid(Long.valueOf(recomDelvInfoServiceVO.getFdbkObjId()));
        RxDelvQueueInfoVO rxDelvQueueInfo = baseRxExecutor.getDelvQueueInfo();
        rxDelvInputVO.setRxDelvQueueInfo(rxDelvQueueInfo);
        rxDelvInputVO.setActionType(actionType);
        return rxDelvInputVO;
    }
    
    private RxLogInfoVO loadRxLogInfoVO(RecomDelvInfoServiceVO recomDelvInfoServiceVO, Session session){
    	RxLogInfoVO rxLogInfoVO = new RxLogInfoVO();
        rxLogInfoVO.setCaseId(recomDelvInfoServiceVO.getStrCaseID());
        rxLogInfoVO.setUserId(recomDelvInfoServiceVO.getStrUserName());
        final String vehInfoQry = "SELECT tsp.serial_no as vehNo, tsp.x_veh_hdr as vehHdr FROM table_case tc, table_site_part tsp "
        		+ "WHERE tc.case_prod2site_part = tsp.objid AND tc.id_number = :caseId";
        Query hibernateQuery = session.createSQLQuery(vehInfoQry);
        hibernateQuery.setParameter("caseId", recomDelvInfoServiceVO.getStrCaseID());
        List<Object[]> repairCodeResult = hibernateQuery.list();
        if(repairCodeResult != null && !repairCodeResult.isEmpty()){
        	for(Object[] objects : repairCodeResult){
        		rxLogInfoVO.setRoadNumber(RMDCommonUtility.convertObjectToString(objects[0]));
        		rxLogInfoVO.setVehHdr(RMDCommonUtility.convertObjectToString(objects[1]));
        	}
        }
        return rxLogInfoVO;
    }
    
    private void updateCustFdbk(String objId, String lastUpdatedBy, Session session) throws Exception {
        LOG.debug("inside CaseMetrics");
        Query hibernateQuery = null;
        String updateQuery = "update gets_sd_cust_fdbk set last_updated_date = sysdate, last_updated_by = :lastUpdatedBy, days_to_repair = null, "
        +" days_to_fail = null, case_success = :caseSuccess, miss_code = :missCode, "
        +" accu_recomm = :accuRecomm, cust_fdbk2site_loc = null, good_fdbk = :goodFdbk "
        +" where objid = :objId";
        LOG.debug("Query   :" + updateQuery);
        try {
        	hibernateQuery = session.createSQLQuery(updateQuery);
            hibernateQuery.setParameter("lastUpdatedBy", lastUpdatedBy);
            hibernateQuery.setParameter("caseSuccess", RMDCommonConstants.STRING_MISS);
            hibernateQuery.setParameter("missCode", RMDCommonConstants.STRING_4C);
            hibernateQuery.setParameter("accuRecomm", RMDCommonConstants.N_LETTER_UPPER);
            hibernateQuery.setParameter("goodFdbk", RMDCommonConstants.Y_LETTER_UPPER);
            hibernateQuery.setParameter("objId", objId);
            hibernateQuery.executeUpdate();
        } catch (Exception exception) {
        	LOG.error(exception, exception);
        	throw exception;
        } 
        LOG.debug("save_data - return");
    }
    
    /**
     * @param RepairCodeEoaDetailsVO
     * @return
     * @throws RMDDAOException
     * @Description To get all the repair codes
     */
    private List<RepairCodeEoaDetailsVO> getRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType, Session session) throws RMDDAOException {
        List<RepairCodeEoaDetailsVO> repairCodeEoaDetailsList = new ArrayList<RepairCodeEoaDetailsVO>();
        LOG.debug("Begin getRepairCodes method of CaseEoaDAOImpl");
        StringBuilder caseQry = new StringBuilder();
        caseQry.append(" SELECT OBJID, REPAIR_CODE, REPAIR_DESC FROM GETS_SD_REPMODEL_V WHERE 1=1");
        if (null != repairCodeInputType.getModel()) {
            caseQry.append("AND MODEL_NAME =:model");
        }
        if (null != repairCodeInputType.getSearchBy() && !"".equals(repairCodeInputType.getSearchBy().trim()) 
        		&& RMDCommonConstants.DISCRIPTION.equalsIgnoreCase(repairCodeInputType.getSearchBy())) {
            caseQry.append(" AND UPPER(REPAIR_DESC) like  UPPER(:searchValue) ");
        } else if (null != repairCodeInputType.getSearchValue() && !"".equals(repairCodeInputType.getSearchValue().trim())) {
            caseQry.append(" AND REPAIR_CODE like  :searchValue ");
        }
        caseQry.append(" ORDER BY REPAIR_CODE DESC");
        Query caseHqry = session.createSQLQuery(caseQry.toString());
        if (null != repairCodeInputType.getSearchBy() && !"".equals(repairCodeInputType.getSearchBy().trim())) {
            caseHqry.setParameter(RMDCommonConstants.SEARCH_VALUE,
                    RMDServiceConstants.PERCENTAGE + repairCodeInputType.getSearchValue() + RMDServiceConstants.PERCENTAGE);
        }
        if (null != repairCodeInputType.getSearchValue() && !"".equals(repairCodeInputType.getSearchValue().trim())) {
            caseHqry.setParameter(RMDCommonConstants.SEARCH_VALUE, 
            		RMDServiceConstants.PERCENTAGE + repairCodeInputType.getSearchValue() + RMDServiceConstants.PERCENTAGE);
        }
        if (null != repairCodeInputType.getModel()) {
            caseHqry.setParameter(RMDCommonConstants.MODEL, repairCodeInputType.getModel());
        }
        List<Object[]> repairCodeResult = caseHqry.list();
        for (Object[] currentRepairCode : repairCodeResult) {
            RepairCodeEoaDetailsVO repairCode = new RepairCodeEoaDetailsVO();
            repairCode.setRepairCodeId(RMDCommonUtility.convertObjectToString(currentRepairCode[0]));
            repairCode.setRepairCode(RMDCommonUtility.convertObjectToString(currentRepairCode[1]));
            repairCode.setRepairCodeDesc(RMDCommonUtility.convertObjectToString(currentRepairCode[2]));
            repairCodeEoaDetailsList.add(repairCode);
        }
        LOG.debug("Ends getRepairCodes method of CaseEoaDAOImpl");
        return repairCodeEoaDetailsList;
    }
    
    private void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO, Session session) throws RMDDAOException {
        boolean repairCodePresent = false;
       	Long caseObjId = caseRepairCodeEoaVO.getCaseObjId();
    	if(caseObjId == null){
    		caseObjId = Long.valueOf(getCaseObjid(session, caseRepairCodeEoaVO.getCaseId()));
    	}
        String userName = caseRepairCodeEoaVO.getUserName();
        if(userName == null){
        	userName = getEoaUserName(session, caseRepairCodeEoaVO.getUserId());
        }
        String codeQuery = "SELECT FINAL_REPCODE2REPCODE FROM GETS_SD_FINAL_REPCODE WHERE FINAL_REPCODE2CASE = :caseSeqId";
        Query query = session.createSQLQuery(codeQuery);
        query.setParameter(RMDCommonConstants.CASESEQID, caseObjId);
        List<String> existingRepCodeList = query.list();
        List<String> repairCodeList = caseRepairCodeEoaVO.getRepairCodeIdList();
        if (RMDCommonUtility.isCollectionNotEmpty(repairCodeList)) {
        	StringBuilder qrystring = new StringBuilder();
            qrystring.append("INSERT INTO GETS_SD_FINAL_REPCODE (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, CREATED_BY, FINAL_REPCODE2CASE, FINAL_REPCODE2REPCODE)");
            qrystring.append(" VALUES (GETS_SD_FINAL_REPCODE_SEQ.NEXTVAL, sysdate, :userName, sysdate, :userName, :caseSeqId, :repairCode)");
            for (String repairCode : repairCodeList) {
                if (RMDCommonUtility.isCollectionNotEmpty(existingRepCodeList)) {
                    repairCodePresent = existingRepCodeList.contains(new BigDecimal(repairCode));
                }
                if (!repairCodePresent) {
                	query = session.createSQLQuery(qrystring.toString());
                    query.setParameter(RMDCommonConstants.USERNAME, userName);
                    query.setParameter(RMDCommonConstants.CASESEQID, caseObjId);
                    query.setParameter(RMDCommonConstants.REPAIR_CODES, repairCode);
                    query.executeUpdate();
                }
            }
        }
        /* Code for Add And Close of Case Closure */
        if (caseRepairCodeEoaVO.isAddAndClose()) {
            CloseCaseVO closeCaseVO = new CloseCaseVO();
            closeCaseVO.setStrCaseID(caseRepairCodeEoaVO.getCaseId());
            closeCaseVO.setStrUserName(caseRepairCodeEoaVO.getUserId());
            closeCase(closeCaseVO);
        }
        LOG.debug("Ends addCaseRepairCodes method of CaseEoaDAOImpl");
    }
    
    private StringBuilder appendCaseListQry(){
        
        StringBuilder selectClause = new StringBuilder();
        selectClause.append("SELECT TCOBJID,ID_NUMBER,CONDITION,PRIORITY,CASE_TITLE,CASEREASON,TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS'),CASE_TYPE,APPENDED,QUEUE, ");
        selectClause.append("TO_CHAR(CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS'),OWNER_LOGIN_NAME FROM( ");
        selectClause.append("SELECT TCOBJID,ID_NUMBER,CONDITION,PRIORITY,CASE_TITLE,CASEREASON,CREATION_TIME,CASE_TYPE,APPENDED,QUEUE,CLOSE_DATE,OWNER_LOGIN_NAME FROM( ");
        StringBuilder mainClause = new StringBuilder();
        mainClause.append("SELECT TABLE_CASE.OBJID TCOBJID,TABLE_CASE.ID_NUMBER ID_NUMBER,TABLE_CONDITION.TITLE CONDITION,TABLE_GSE_PRIORITY.TITLE PRIORITY,TABLE_CASE.TITLE CASE_TITLE, ");
        mainClause.append("DECODE(GETS_GET_CASE_REASON_DWQ_FN(TABLE_CASE.OBJID) ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review' ,'6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed','Recommendation Deferred','Recommendation Deferred','Recommendation Deleted','Recommendation Deleted''Recommendation Rejected','Recommendation Rejected') CASEREASON, ");
        mainClause.append("TABLE_CASE.CREATION_TIME CREATION_TIME,TABLE_GSE_TYPE.TITLE CASE_TYPE,TABLE_CASE.IS_SUPERCASE IS_SUPERCASE,DECODE(TABLE_GSE_SEVERITY.TITLE,'Appended', 'YES') APPENDED, ");
        mainClause.append("TABLE_SITE.OBJID OBJID,TABLE_SITE.NAME NAME,TABLE_SITE.SITE_ID SITE_ID,TABLE_CASE.CASE_PROD2SITE_PART CASE_PROD2SITE_PART,TABLE_SITE_PART.X_VEH_HDR X_VEH_HDR,");
        mainClause.append("TABLE_SITE_PART.SERIAL_NO SERIAL_NO,TABLE_SITE_PART.S_SERIAL_NO S_SERIAL_NO,TABLE_QUEUE.TITLE QUEUE,TABLE_BUS_ORG.ORG_ID ORG_ID,TABLE_CLOSE_CASE.CLOSE_DATE CLOSE_DATE,");
        mainClause.append("TABLE_USER.LOGIN_NAME OWNER_LOGIN_NAME FROM TABLE_GBST_ELM TABLE_GSE_TYPE,TABLE_GBST_ELM TABLE_GSE_SEVERITY,TABLE_GBST_ELM TABLE_GSE_PRIORITY,TABLE_CASE,");
        mainClause.append("GETS_RMD_VEHICLE,GETS_RMD_VEH_HDR,TABLE_BUS_ORG,TABLE_CONDITION,TABLE_SITE,TABLE_SITE_PART,TABLE_CLOSE_CASE,TABLE_QUEUE,TABLE_USER,");
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(" WHERE TABLE_CONDITION.OBJID = TABLE_CASE.CASE_STATE2CONDITION AND TABLE_GSE_TYPE.OBJID = TABLE_CASE.CALLTYPE2GBST_ELM AND TABLE_SITE_PART.OBJID = GETS_RMD_VEHICLE.VEHICLE2SITE_PART ");
        whereClause.append("AND GETS_RMD_VEHICLE.VEHICLE2VEH_HDR=GETS_RMD_VEH_HDR.OBJID AND GETS_RMD_VEH_HDR.VEH_HDR2BUSORG = TABLE_BUS_ORG.OBJID AND TABLE_GSE_SEVERITY.OBJID = TABLE_CASE.RESPSVRTY2GBST_ELM ");
        whereClause.append("AND TABLE_GSE_PRIORITY.OBJID = TABLE_CASE.RESPPRTY2GBST_ELM AND TABLE_SITE.OBJID  = TABLE_CASE.CASE_REPORTER2SITE AND TABLE_USER.OBJID = TABLE_CASE.CASE_OWNER2USER ");
        whereClause.append("AND TABLE_CASE.OBJID = TABLE_CLOSE_CASE.LAST_CLOSE2CASE(+) AND TABLE_SITE_PART.OBJID (+) = TABLE_CASE.CASE_PROD2SITE_PART AND TABLE_QUEUE.OBJID (+) = TABLE_CASE.CASE_CURRQ2QUEUE ");
        whereClause.append("AND TABLE_SITE_PART.SERIAL_NO = :roadNumber AND TABLE_BUS_ORG.ORG_ID=:customer AND TABLE_SITE_PART.X_VEH_HDR=:assetGroupName AND TABLE_GSE_TYPE.TITLE IN(:caseType) ");
        StringBuilder mainQry = new StringBuilder();
        String condition1= "AND ((TABLE_CONDITION.TITLE   IN ('Open','Open-Dispatch') AND TABLE_CASE.CREATION_TIME > SYSDATE-365 ) OR (TABLE_CONDITION.TITLE  = 'Closed' AND (TABLE_QUEUE.S_TITLE IS NULL OR TABLE_QUEUE.S_TITLE!='840. CONDITIONAL CASES')) AND TABLE_CLOSE_CASE.CLOSE_DATE    >= (SYSDATE-:noOfDays)) AND GETS_SD_RECOM_DELV.RECOM_DELV2CASE = TABLE_CASE.OBJID";
        String condition2="AND (TABLE_CONDITION.TITLE  IN ('Open') AND GETS_TOOL_AR_LIST.AR_LIST2CASE=TABLE_CASE.OBJID)";
        String orderByClause =") ORDER BY CONDITION DESC ) ORDER BY DECODE(CONDITION,'Closed', close_date) DESC,DECODE(SUBSTR(CONDITION,1,4),'Open', creation_time) DESC";
        mainQry.append(selectClause).append(mainClause).append("GETS_SD_RECOM_DELV").append(whereClause).append(condition1).append(" UNION ").append(mainClause).append("GETS_TOOL_AR_LIST").append(whereClause).append(condition2).append(orderByClause);
        return mainQry;
        
    }
}
