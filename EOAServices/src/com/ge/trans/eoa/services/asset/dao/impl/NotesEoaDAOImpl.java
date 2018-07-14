package com.ge.trans.eoa.services.asset.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.asset.dao.intf.NotesEoaDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FindNotesEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.ControllerListVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class NotesEoaDAOImpl extends BaseDAO implements NotesEoaDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLogger
            .getLogger(NotesEoaDAOImpl.class);

    /**
     * @Author :
     * @return :List<ControllerListVO>
     * @param :
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of controllers.
     * 
     */
    @Override
    public List<ControllerListVO> getAllControllers() throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<ControllerListVO> controllerList = new ArrayList<ControllerListVO>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append(" select distinct OBJID,CONTROLLER_CFG from gets_rmd_ctl_cfg ");
            final Query objControllerQuery = hibernateSession
                    .createSQLQuery(strQuery.toString());
            objControllerQuery.setFetchSize(10);
            List<Object[]> controllerResult = objControllerQuery.list();
            ControllerListVO controllerListVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(controllerResult)) {
                // iterate.
                for (int i = 0; i < controllerResult.size(); i++) {
                    Object[] objController = (Object[]) controllerResult.get(i);
                    controllerListVO = new ControllerListVO();
                    if (objController != null) {
                        controllerListVO.setControllerId(RMDCommonUtility
                                .convertObjectToString(objController[0]));

                        controllerListVO.setControllerName(RMDCommonUtility
                                .convertObjectToString(objController[1]));
                    }
                    controllerList.add(controllerListVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_CONTROLLERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_CONTROLLERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return controllerList;
    }

    /**
     * @Author :
     * @return :List<String>
     * @param :
     * @throws :RMDDAOException
     * @Description: This Method Fetches the list of creators.
     * 
     */
    @Override
    public List<String> getNotesCreatersList() throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder strQuery = new StringBuilder();
        List<String> creatorList = new ArrayList<String>();

        try {
            hibernateSession = getHibernateSession();
            strQuery.append("select LOGIN_NAME from table_user where STATUS = 1 order by LOGIN_NAME");
            final Query objCreatorQuery = hibernateSession
                    .createSQLQuery(strQuery.toString());
            List<Object> creatorResult = objCreatorQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(creatorResult)) {
                for (Object obj : creatorResult) {
                    creatorList
                            .add(RMDCommonUtility.convertObjectToString(obj));
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_CREATORS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_CREATORS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(hibernateSession);
        }
        return creatorList;
    }

    /**
     * @Author :
     * @return :String
     * @param :NotesBean
     * @throws :RMDDAOException, SQLException
     * @Description: This method adds the notes for the selected customer.
     * 
     */
    @Override
    public String addNotesToVehicle(AddNotesEoaServiceVO objAddNotesServiceVO)
            throws RMDDAOException, SQLException {
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        Session session = null;
        int result;
        String errString = null;
        String busOrgId = null;
        String status = null;
        String fleetId = null;
        StringBuilder removeStickyQry = new StringBuilder();
        try {
            session = getHibernateSession();
            if (!RMDCommonUtility.isNullOrEmpty(objAddNotesServiceVO
                    .getCustomerId())) {
                busOrgId = getBusOrgId(session,
                        objAddNotesServiceVO.getCustomerId());
            }
            if (!RMDCommonUtility.isNullOrEmpty(objAddNotesServiceVO
                    .getFleetId())) {
                fleetId = getFleetId(session, objAddNotesServiceVO.getFleetId());
            }
            objConnection = getConnection(session);
            objConnection.setAutoCommit(false);
            if (objAddNotesServiceVO.getSticky().equalsIgnoreCase(
                    RMDCommonConstants.Y_LETTER_UPPER)) {
                for (int i = 0; i < objAddNotesServiceVO.getAssetNumbersList()
                        .size(); i++) {
                    callableStmt = objConnection
                            .prepareCall(" BEGIN GETS_SD_SKB_FIND_NOTES_PKG.INSERT_NOTES(?,?,?,?,?,?,?,?,?,?,?); END;");
                    callableStmt.setString(1, busOrgId);
                    callableStmt.setString(2, objAddNotesServiceVO.getSticky());
                    callableStmt.setString(
                            3,EsapiUtil.escapeSpecialChars(
                                            (objAddNotesServiceVO
                                                    .getNoteDescription())));
                    callableStmt
                            .setString(4, objAddNotesServiceVO.getModelId());
                    callableStmt.setString(5, objAddNotesServiceVO.getCtrlId());
                    callableStmt.setString(6, fleetId);
                    callableStmt.setString(7, objAddNotesServiceVO
                            .getAssetNumbersList().get(i));
                    callableStmt.setString(8, objAddNotesServiceVO
                            .getAssetNumbersList().get(i));
                    callableStmt.setString(9,
                            objAddNotesServiceVO.getStrUserName());
                    callableStmt.registerOutParameter(10,
                            java.sql.Types.INTEGER);
                    callableStmt.registerOutParameter(11,
                            java.sql.Types.VARCHAR);
                    callableStmt.execute();
                    result = callableStmt.getInt(10);
                    errString = callableStmt.getString(11);
                    if (result == 0) {
                        status = RMDCommonConstants.SUCCESS_MSG;
                    } else if (result == 1) {
                        status = RMDCommonConstants.INVALID_ROAD_NUMBER_ERROR;
                        break;
                    }
                    if (RMDCommonConstants.SUCCESS_MSG
                            .equalsIgnoreCase(errString)) {
                        objConnection.commit();
                    }
                }
            } else {
                callableStmt = objConnection
                        .prepareCall(" BEGIN GETS_SD_SKB_FIND_NOTES_PKG.INSERT_NOTES(?,?,?,?,?,?,?,?,?,?,?); END;");
                callableStmt.setString(1, busOrgId);
                callableStmt.setString(2, objAddNotesServiceVO.getSticky());
                callableStmt.setString(
                        3,EsapiUtil.escapeSpecialChars(
                                (objAddNotesServiceVO.getNoteDescription())));
                callableStmt.setString(4, objAddNotesServiceVO.getModelId());
                callableStmt.setString(5, objAddNotesServiceVO.getCtrlId());
                callableStmt.setString(6, fleetId);
                callableStmt.setString(7, objAddNotesServiceVO.getFromRN());
                callableStmt.setString(8, objAddNotesServiceVO.getToRN());
                callableStmt
                        .setString(9, objAddNotesServiceVO.getStrUserName());
                callableStmt.registerOutParameter(10, java.sql.Types.INTEGER);
                callableStmt.registerOutParameter(11, java.sql.Types.VARCHAR);
                callableStmt.execute();
                result = callableStmt.getInt(10);
                errString = callableStmt.getString(11);
                if (result == 0) {
                    status = RMDCommonConstants.SUCCESS_MSG;
                } else if (result == 1) {
                    status = RMDCommonConstants.INVALID_ROAD_NUMBER_ERROR;
                }
                if (RMDCommonConstants.SUCCESS_MSG.equalsIgnoreCase(errString)) {
                    objConnection.commit();
                }
            }
            if (status
                    .equalsIgnoreCase(RMDCommonConstants.INVALID_ROAD_NUMBER_ERROR))
                objConnection.rollback();
            else
                objConnection.setAutoCommit(true);
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in addNotesToVehicle  method of NotesEoaDAOImpl :",
                    e);
            objConnection.rollback();
            // String errorCode = errString;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ADD_NOTES_TO_VEHICLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            callableStmt.close();
            objConnection.close();
            releaseSession(session);
        }
        return status;
    }
    
    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :customerID,fromRN
     * @throws :RMDDAOException
     * @Description: This method is used to get the existing sticky details.
     * 
     */
    @Override
    public List<StickyNotesDetailsVO> fetchVehicleStickyCaseNotes(
            String customerId, String fromRN, String noOfUnits)
            throws RMDDAOException {
        Session objSession = null;
        List<StickyNotesDetailsVO> objStickyNotesDetails = new ArrayList<StickyNotesDetailsVO>();
        StringBuilder stickyQry = new StringBuilder();
        DateFormat formatter = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        String vehObjId = null;
        List<String> listRn = Arrays.asList(fromRN
                .split(RMDCommonConstants.COMMMA_SEPARATOR));
        StickyNotesDetailsVO objStickyNotesDetailsVO = null;
        List<String> roadNumber = new ArrayList<String>();
        try {
            objSession = getHibernateSession(EMPTY_STRING);
            if (noOfUnits.equalsIgnoreCase(RMDCommonConstants.ONE_STRING)) {
                stickyQry
                        .append("SELECT OBJID,CREATED_BY,TO_CHAR(CREATION_DATE,'MM/DD/YYYY HH24:MI:SS'),GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_CONVERT_VARCHAR_FN('C',OBJID) DESCRIPTION FROM GETS_SD_GENERIC_NOTES_LOG ");
                stickyQry
                        .append("WHERE GENERIC2VEHICLE in (SELECT VEHICLE_OBJID FROM GETS_RMD_CUST_RNH_RN_V WHERE  VEHICLE_NO in(:fromRN) ");
                stickyQry
                        .append("AND BUS_ORG_OBJID = (SELECT OBJID FROM table_bus_org WHERE ORG_ID = :customerId) and STICKY='Y') and STICKY = 'Y'");
                final Query stickyNotesQry = objSession
                        .createSQLQuery(stickyQry.toString());
                stickyNotesQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        customerId);
                stickyNotesQry.setParameterList(RMDCommonConstants.FROM_RN,
                        listRn);
                ArrayList notesDetailsList = (ArrayList) stickyNotesQry.list();
                if (null != notesDetailsList && !notesDetailsList.isEmpty()) {
                    for (final Iterator<Object> notesIter = notesDetailsList
                            .iterator(); notesIter.hasNext();) {
                        final Object[] currentNote = (Object[]) notesIter
                                .next();
                        objStickyNotesDetailsVO = new StickyNotesDetailsVO();
                        objStickyNotesDetailsVO.setObjId(RMDCommonUtility
                                .convertObjectToString(currentNote[0]));
                        objStickyNotesDetailsVO.setCreatedBy(RMDCommonUtility
                                .convertObjectToString(currentNote[1]));
                        if (null != RMDCommonUtility
                                .convertObjectToString(currentNote[2])) {
                            Date closeDate = (Date) formatter
                                    .parse(RMDCommonUtility
                                            .convertObjectToString(currentNote[2]));
                            objStickyNotesDetailsVO.setEntryTime(closeDate);
                        }
                        objStickyNotesDetailsVO
                                .setAdditionalInfo(ESAPI
                                        .encoder()
                                        .encodeForXML(
                                                RMDCommonUtility
                                                        .convertObjectToString(currentNote[3])));
                        objStickyNotesDetails.add(objStickyNotesDetailsVO);
                    }
                }
            } else {
                stickyQry
                        .append("SELECT DISTINCT gets_rmd_cust_rnh_rn_v.VEHICLE_NO FROM GETS_SD_GENERIC_NOTES_LOG gets_sd_generic_notes_log, ");
                stickyQry
                        .append("GETS_RMD_CUST_RNH_RN_V gets_rmd_cust_rnh_rn_v,TABLE_BUS_ORG table_bus_org ");
                stickyQry
                        .append("WHERE gets_sd_generic_notes_log.GENERIC2VEHICLE = gets_rmd_cust_rnh_rn_v.VEHICLE_OBJID ");
                stickyQry
                        .append("AND gets_rmd_cust_rnh_rn_v.BUS_ORG_OBJID = table_bus_org.OBJID ");
                stickyQry
                        .append("AND gets_rmd_cust_rnh_rn_v.VEHICLE_NO IN (:fromRN) ");
                stickyQry
                        .append("AND table_bus_org.ORG_ID =:customerId AND gets_sd_generic_notes_log.STICKY = 'Y' ");
                final Query stickyNotesQry = objSession
                        .createSQLQuery(stickyQry.toString());
                stickyNotesQry.setParameter(RMDCommonConstants.CUSTOMER_ID,
                        customerId);
                stickyNotesQry.setParameterList(RMDCommonConstants.FROM_RN,
                        listRn);
                List<String> notesDetailsList = stickyNotesQry.list();
                if (null != notesDetailsList && !notesDetailsList.isEmpty()) {
                    objStickyNotesDetailsVO = new StickyNotesDetailsVO();
                    for (String notes:notesDetailsList) {
                        roadNumber.add(RMDCommonUtility
                                .convertObjectToString(notes));
                    }
                    objStickyNotesDetailsVO.setStickyAssets(roadNumber);
                    objStickyNotesDetails.add(objStickyNotesDetailsVO);
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
            LOG.error("Exception occurred:", e);
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
     * @Author:
     * @param:FindNotesEoaServiceVO
     * @return:List<NotesResponseType>
     * @throws:RMDDAOException, SQLException
     * @Description: This method is used to get Find Notes Details.
     */
    @Override
    public List<FindNotesDetailsVO> getFindNotes(
            FindNotesEoaServiceVO objFindNotesEoaServiceVO)
            throws RMDDAOException, SQLException {
        Connection objConnection = null;
        CallableStatement callableStmt = null;
        ResultSet resultList = null;
        List<FindNotesDetailsVO> objFindNotesDetailsVOList = new ArrayList<FindNotesDetailsVO>();
        FindNotesDetailsVO objFindNotesDetailsVO = null;
        Session session = null;
        Long busOrgId = null;
        String fleetId = null;
        String noteType = objFindNotesEoaServiceVO.getNoteType();
        try {
            session = getHibernateSession();
            if(!RMDCommonUtility.isNullOrEmpty(objFindNotesEoaServiceVO.getCustomerId())){
            busOrgId = Long.parseLong(getBusOrgId(session,
                    objFindNotesEoaServiceVO.getCustomerId()));
            }
            if(!RMDCommonUtility.isNullOrEmpty(objFindNotesEoaServiceVO.getFleetId())){
            fleetId =getFleetId(session,
                    objFindNotesEoaServiceVO.getFleetId());
            }
            objConnection = getConnection(session);
            objConnection.setAutoCommit(false);
            callableStmt = objConnection
                    .prepareCall(" {?= call GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_FIND_NOTES_FN(?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callableStmt.setLong(2, busOrgId);
            callableStmt.setString(3, objFindNotesEoaServiceVO.getFromRN());
            callableStmt.setString(4, objFindNotesEoaServiceVO.getToRN());
            callableStmt.setString(5, objFindNotesEoaServiceVO.getModelId());
            callableStmt.setString(6, fleetId);
            callableStmt.setString(7, objFindNotesEoaServiceVO.getCtrlId());
            callableStmt.setString(8, objFindNotesEoaServiceVO.getCreatedBy());
            callableStmt.setString(9, RMDCommonConstants.ALL.equalsIgnoreCase(noteType)?RMDCommonConstants.ALL_OMD:noteType);
            callableStmt.setString(10,
                    objFindNotesEoaServiceVO.getSearchKeyWord());
            callableStmt.setString(11, objFindNotesEoaServiceVO.getStartDate());
            callableStmt.setString(12, objFindNotesEoaServiceVO.getEndDate());
            callableStmt.setString(13, null);
            callableStmt.execute();

            resultList = (ResultSet) callableStmt.getObject(1);
            
            if(resultList!=null){
            while (resultList.next()) {
                
                    objFindNotesDetailsVO = new FindNotesDetailsVO();
                    //final Object[] notesObj = (Object[]) obj.next();
                    objFindNotesDetailsVO.setNoteObjId(RMDCommonUtility
                            .convertObjectToString(resultList.getString(1)));
                    objFindNotesDetailsVO.setCustomerName(RMDCommonUtility
                            .convertObjectToString(resultList.getString(2)));
                    objFindNotesDetailsVO.setRn(RMDCommonUtility
                            .convertObjectToString(resultList.getString(3)));
                    objFindNotesDetailsVO.setCaseId(RMDCommonUtility
                            .convertObjectToString(resultList.getString(4)));
                    if (null != RMDCommonUtility
                            .convertObjectToString(resultList.getString(5))) {
                        objFindNotesDetailsVO.setDate(RMDCommonUtility
                                .convertObjectToString(resultList
                                        .getString(5)));
                    }
                    objFindNotesDetailsVO.setModelName(RMDCommonUtility
                            .convertObjectToString(resultList.getString(6)));
                    objFindNotesDetailsVO.setCtrlName(RMDCommonUtility
                            .convertObjectToString(resultList.getString(7)));
                    objFindNotesDetailsVO.setCreatedBy(RMDCommonUtility
                            .convertObjectToString(resultList.getString(8)));
                    objFindNotesDetailsVO.setNoteType(RMDCommonUtility
                            .convertObjectToString(resultList.getString(9)));
                    objFindNotesDetailsVO.setNoteDescription(ESAPI.encoder()
                            .encodeForXML(EsapiUtil.escapeSpecialChars(
                                    RMDCommonUtility
                                            .convertObjectToString(resultList
                                                    .getString(10)))));
                    objFindNotesDetailsVO.setStickyFlag(RMDCommonUtility
                            .convertObjectToString(resultList.getString(11)));
                    objFindNotesDetailsVOList.add(objFindNotesDetailsVO);
                }
                }
            
            
            objFindNotesDetailsVO = null;
            
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred in getFindNotes  method of NotesEoaDAOImpl :",
                    e);
            // String errorCode = errString;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_NOTES_TO_VEHICLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            try {
                    resultList.close();
                    resultList = null;
            } catch (Exception e) {
            	 LOG.error(e);
                resultList = null;
            }
            try {
                    callableStmt.close();
                    callableStmt = null;
            } catch (Exception e) {
            	 LOG.error(e);
                callableStmt = null;
            }
            try {
                    objConnection.close();
                    objConnection = null;
            } catch (Exception e) {
            	 LOG.error(e);
                objConnection = null;
            }
            
            releaseSession(session);
        }
        return objFindNotesDetailsVOList;
    }

    /**
     * @Author:
     * @param:unitStickyObjId,caseStickyObjId
     * @return:String
     * @throws RMDDAOException 
     * @throws: SQLException
     * @Description: This method is used to remove sticky for case and unit level notes.
     */
    @Override
    public String removeSticky(String unitStickyObjId, String caseStickyObjId)
            throws RMDDAOException, SQLException {
        Session objSession = null;
        String result = RMDCommonConstants.FAILURE;
        try {
            objSession = getHibernateSession();
            StringBuilder removeCaseStickyQry = new StringBuilder();
            StringBuilder removeUnitStickyQry = new StringBuilder();
            if (unitStickyObjId != null) {
                List<String> unitStickyObjIdList = Arrays
                        .asList(unitStickyObjId
                                .split(RMDCommonConstants.COMMMA_SEPARATOR));
                removeUnitStickyQry
                        .append("update GETS_SD_GENERIC_NOTES_LOG set STICKY='N' where objid in (:unitStickyObjId)");
                Query removeStickyHqry = objSession
                        .createSQLQuery(removeUnitStickyQry.toString());
                removeStickyHqry.setParameterList(
                        RMDCommonConstants.UNIT_STICKY_OBJID,
                        unitStickyObjIdList);
                removeStickyHqry.executeUpdate();
            }
            if (caseStickyObjId != null) {
                List<String> caseStickyObjIdList = Arrays
                        .asList(caseStickyObjId
                                .split(RMDCommonConstants.COMMMA_SEPARATOR));
                removeCaseStickyQry
                        .append("update table_notes_log set X_STICKY='N' where objid in (:caseStickyObjId)");
                Query removeStickyHqry = objSession
                        .createSQLQuery(removeCaseStickyQry.toString());
                removeStickyHqry.setParameterList(
                        RMDCommonConstants.CASE_STICKY_OBJID,
                        caseStickyObjIdList);
                removeStickyHqry.executeUpdate();
            }
            result = RMDCommonConstants.SUCCESS;
        } catch (Exception e) {
            LOG.error(
                    "Exception occurred at removeSticky  method of NotesEoaDAOImpl:",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FIND_NOTES_REMOVE_STICKY_NOTE);
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
     * @param:Session session, String custId
     * @return:String
     * @throws  
     * @throws:
     * @Description: This method is used to get customer objid.
     */
    public String getBusOrgId(Session session, String custId) {
        String busOrgId = null;
        String query = "SELECT OBJID FROM table_bus_org WHERE ORG_ID = :customerId";
        Query busOrgIdQry = session.createSQLQuery(query.toString());
        busOrgIdQry.setParameter(RMDCommonConstants.CUSTOMER_ID, custId);
        List<Object[]> objIdList = busOrgIdQry.list();
        busOrgId = String.valueOf(objIdList.get(0));
        return busOrgId;
    }
    public String getFleetId(Session session, String fleet) {
        String fleetId = null;
        String query = "SELECT OBJID FLEET_NUMBER FROM gets_rmd_fleet  where FLEET_NUMBER=:fleet";
        Query fleetIdIdQry = session.createSQLQuery(query.toString());
        fleetIdIdQry.setParameter(RMDCommonConstants.FLEET, fleet);
        List<Object[]> objIdList = fleetIdIdQry.list();
        fleetId = String.valueOf(objIdList.get(0));
        return fleetId;
    }
    

}
