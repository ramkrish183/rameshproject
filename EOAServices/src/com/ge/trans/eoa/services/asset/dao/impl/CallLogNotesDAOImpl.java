package com.ge.trans.eoa.services.asset.dao.impl;

import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.asset.dao.intf.CallLogNotesDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class CallLogNotesDAOImpl extends RMDCommonDAO implements
CallLogNotesDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
	public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDDAOException{
        
        Session objSession = null;
        List<CallLogNotesVO> callLogList = new ArrayList<CallLogNotesVO>();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        try {
            LOG.debug("Begin getCallLogNotes method of CallLogNotesDAOImpl");
            objSession = getHibernateSession();
            
            StringBuffer callLogQry = new StringBuffer();
            
            callLogQry.append("SELECT callLog.CALL_LOG_ID,callLog.AGENT_SSO,callLog.BUSNSS_AREA,callLog.CALL_TYPE,LOCATION,callLog.ISSUE_TYPE,TO_CHAR(callLog.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),callLog.NOTES_DESC,concat(concat(callLog.caller_lastname,','), callLog.caller_firstname),concat(concat(callLog.agent_lastname,','), callLog.agent_firstname),OMD_CUST_ID,TO_CHAR(callLog.CALL_ENDED_ON,'MM/DD/YYYY HH24:MI:SS'),callLog.CALL_DURATION_SECONDS,TO_CHAR(callLog.CALL_STARTED_ON,'MM/DD/YYYY HH24:MI:SS')  ");
            callLogQry.append(" FROM GETS_RMD.GETS_SFDC_CALL_LOGS callLog, GETS_RMD.GETS_SFDC_CALL_LOGS_MTM_VEH callLogVeh, GETS_RMD_CUST_RNH_RN_V custRnh ");
            callLogQry.append(" WHERE callLog.OBJID = callLogVeh.MTM2SFDC_CALL_LOGS AND callLogVeh.MTM2VEHICLE=custRnh.VEHICLE_OBJID ");
            callLogQry.append(" AND  custRnh.ORG_ID = :customerID AND custRnh.VEHICLE_NO = :assetNum AND custRnh.VEHICLE_HDR = :vehicleHeader"
                    + " AND TO_DATE(callLog.CREATION_DATE ,'DD/MM/YYYY HH24:MI:SS' ) BETWEEN TO_DATE(:fromDate,'DD/MM/YYYY HH24:MI:SS')  AND TO_DATE(:toDate,'DD/MM/YYYY HH24:MI:SS') ORDER BY callLog.CREATION_DATE DESC");
            
            Query callLogHQry = objSession.createSQLQuery(callLogQry.toString());
            callLogHQry.setParameter(RMDCommonConstants.FROM_DATE, callLogNotesVO.getFromDate());
            callLogHQry.setParameter(RMDCommonConstants.TO_DATE, callLogNotesVO.getToDate());
            callLogHQry.setParameter(RMDCommonConstants.CUSTOMERID,callLogNotesVO.getCustomerId());
            callLogHQry.setParameter(RMDCommonConstants.ASSET_NUM, callLogNotesVO.getAssetNumber());
            callLogHQry.setParameter(RMDCommonConstants.VEHICLE_HEADER, callLogNotesVO.getAssetGroupName());
            
            List<Object[]> callLogResult = callLogHQry.list();
            for (Object[] callLogObj : callLogResult) {
                CallLogNotesVO callLogVO = new CallLogNotesVO();
                callLogVO.setCallLogID(RMDCommonUtility
                        .convertObjectToString(callLogObj[0]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setAgentSSO(RMDCommonUtility
                        .convertObjectToString(callLogObj[1]).replaceAll("\\p{Cntrl}", ""));
                
                callLogVO.setBusniessArea(RMDCommonUtility
                        .convertObjectToString(callLogObj[2]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setCallType(RMDCommonUtility
                        .convertObjectToString(callLogObj[3]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setLocation(RMDCommonUtility
                        .convertObjectToString(callLogObj[4]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setIssueType(RMDCommonUtility
                        .convertObjectToString(callLogObj[5]).replaceAll("\\p{Cntrl}", ""));                
             /*   if (null != RMDCommonUtility
                        .convertObjectToString(callLogObj[6]).replaceAll("\\p{Cntrl}", "")) {*/
                  /*  Date creationTime = (Date) formatter
                            .parse(RMDCommonUtility
                                    .convertObjectToString(callLogObj[6]));*/
                    callLogVO.setCreationDate(RMDCommonUtility
                            .convertObjectToString(callLogObj[6]).replaceAll("\\p{Cntrl}", ""));
                //}
            
                Clob notes = (Clob)callLogObj[7];   
                callLogVO.setNotes(notes.getSubString(1, (int)notes.length()).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setCallerName(RMDCommonUtility
                        .convertObjectToString(callLogObj[8]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setAgentName(RMDCommonUtility
                        .convertObjectToString(callLogObj[9]).replaceAll("\\p{Cntrl}", ""));
                callLogVO.setCustomerId(RMDCommonUtility
                        .convertObjectToString(callLogObj[10]));
                callLogVO.setCallDurationSeconds(RMDCommonUtility
                        .convertObjectToString(callLogObj[12]));
                callLogVO.setCallEndedOn(RMDCommonUtility
                        .convertObjectToString(callLogObj[11]));
                callLogVO.setCallStartedOn(RMDCommonUtility
                        .convertObjectToString(callLogObj[13]));
                callLogList.add(callLogVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception occurred:",e);
            
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        LOG.debug("Ends getCallLogNotes method of CallLogNotesDAOImpl");
        return callLogList;
    }


    
}
