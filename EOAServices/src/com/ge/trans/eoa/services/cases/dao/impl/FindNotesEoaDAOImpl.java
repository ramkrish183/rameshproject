/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.impl
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

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FindNotesEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesSearchResultVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to perform the database related
 *              manipulations.
 * @History :
 * 
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class FindNotesEoaDAOImpl extends RMDCommonDAO implements FindNotesEoaDAOIntf {

    private static final long serialVersionUID = -8416934926911993522L;

    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(FindNotesEoaDAOImpl.class);

    @Autowired
    private PopupListAdminDAOIntf objpopupListAdminDAO;
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.FindNotesDAOIntf#getNotes(com
     * .ge.trans.rmd.services.cases.service.valueobjects.FindNotesServiceVO)
     * This method is used to fetch the search results from database based on
     * search criteria provided.
     */
    @Override
	public void getNotes(final FindNotesServiceVO findNotesServiceVO)
            throws RMDDAOException {
        LOG.debug("Begin getNotes method of FindNotesDAOImpl");
        ArrayList arlSearchNotes;
        Session finNotesSession = null;
        ArrayList lstSearchResVO = null;
        ArrayList lstGenericNotes = null;
        FindNotesSearchResultVO objResultVO = null;
        StringBuffer strNoteDesc = new StringBuffer();
        StringBuffer strGetCustObjId = new StringBuffer();
        StringBuffer strGetGenNote = new StringBuffer();
        String hideNotesRoles = null;
        String creationDate = null;
        FindNotesSearchResultVO findNotesSearchResultVO = null;
        ArrayList arlGetCustObjId = null;
        ArrayList arlGetGenNotes = null;
        
        try {

            finNotesSession = getHibernateSession(EMPTY_STRING);
            if (RMDServiceConstants.LOCO_NOTES
                    .equalsIgnoreCase(findNotesServiceVO
                            .getFindNotesSearchCriteriaVO().getNoteType())) {
                strNoteDesc
                        .append("SELECT TBO.ORG_ID,TSP.X_VEH_HDR,TSP.SERIAL_NO,TO_CHAR(DDQ.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS') CREATIONTIME,NOTES NOTES,DDQ.LAST_UPDATED_BY FROM ");
                strNoteDesc
                        .append("GETS_RMD_DD_QUEUE DDQ,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH, ");
                strNoteDesc
                        .append("GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO WHERE ");
                strNoteDesc.append("DDQ.DD_QUEUE2VEHICLE = VEH.OBJID ");
                strNoteDesc.append("AND TSP.OBJID= VEH.VEHICLE2SITE_PART ");
                strNoteDesc.append("AND VEH.VEHICLE2VEH_HDR= VEHHDR.OBJID ");
                strNoteDesc.append("AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                strNoteDesc
                        .append("AND VEHHDR.VEH_HDR2BUSORG= TBO.OBJID AND DDQ.ACTIVE=1 ");
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetFrom()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetFrom())) {
                    strNoteDesc.append("AND TSP.SERIAL_NO=:assetNumber");
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetGroupName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetGroupName())) {
                    strNoteDesc.append(" AND TSP.X_VEH_HDR=:groupName");
                }

                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getCustomerID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getCustomerID())) {
                    strNoteDesc.append(" AND TBO.ORG_ID=:customerID");
                }

                Query getNotesDesc = finNotesSession.createSQLQuery(strNoteDesc
                        .toString());

                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetFrom()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetFrom())) {
                    getNotesDesc.setParameter(RMDCommonConstants.ASSET_NUMBER,
                            findNotesServiceVO.getFindNotesSearchCriteriaVO()
                                    .getAssetFrom());
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetGroupName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetGroupName())) {
                    getNotesDesc.setParameter(RMDCommonConstants.GROUP_NAME,
                            findNotesServiceVO.getFindNotesSearchCriteriaVO()
                                    .getAssetGroupName());
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getCustomerID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getCustomerID())) {
                    getNotesDesc.setParameter(RMDCommonConstants.CUSTOMERID,
                            findNotesServiceVO.getFindNotesSearchCriteriaVO()
                                    .getCustomerID());
                }

                getNotesDesc.setFetchSize(300);
                arlSearchNotes = (ArrayList) getNotesDesc.list();

                if (null != arlSearchNotes && !arlSearchNotes.isEmpty()) {
                    
                    lstSearchResVO = new ArrayList(arlSearchNotes.size());
                    
                    for (final Iterator<Object> notesIter = arlSearchNotes
                            .iterator(); notesIter.hasNext();) {
                        final Object[] arlSearchNotesRec = (Object[]) notesIter
                                .next();
                        objResultVO = new FindNotesSearchResultVO();
                        objResultVO.setCustomerId(RMDCommonUtility
                                .convertObjectToString(arlSearchNotesRec[0]));
                        objResultVO.setAssetGroupName(RMDCommonUtility
                                .convertObjectToString(arlSearchNotesRec[1]));
                        objResultVO.setAssetNumber(RMDCommonUtility
                                .convertObjectToString(arlSearchNotesRec[2]));
                        try{
                            creationDate = RMDCommonUtility
                                                .convertObjectToString(arlSearchNotesRec[3]);
                            objResultVO.setDate(creationDate);
                        }catch(Exception e){
                            LOG.error("Exception while parsing date: ", e);
                        }
                        objResultVO
                                .setNotes(ESAPI.encoder().encodeForXML(RMDCommonUtility
                                        .convertObjectToString(arlSearchNotesRec[4])));
                        objResultVO.setCreator(RMDCommonUtility
                                .convertObjectToString(arlSearchNotesRec[5]));
                        lstSearchResVO.add(objResultVO);
                    }
                }

                findNotesServiceVO.setNotesSearchList(lstSearchResVO);
            }

            if (RMDServiceConstants.GENERIC_NOTE
                    .equalsIgnoreCase(findNotesServiceVO
                            .getFindNotesSearchCriteriaVO().getNoteType())) {
                String custObjId = RMDCommonConstants.EMPTY_STRING;
                strGetCustObjId
                        .append("SELECT TBO.OBJID FROM  TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ");
                strGetCustObjId
                        .append("WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID  AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                strGetCustObjId
                        .append("AND TSP.SERIAL_NO = :assetNumber AND TSP.X_VEH_HDR = :groupName AND TBO.ORG_ID  = :customerID ");
                Query getCustObjIdQry = finNotesSession
                        .createSQLQuery(strGetCustObjId.toString());
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetFrom()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetFrom())) {
                    getCustObjIdQry.setParameter(
                            RMDCommonConstants.ASSET_NUMBER, findNotesServiceVO
                                    .getFindNotesSearchCriteriaVO()
                                    .getAssetFrom());
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetGroupName()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetGroupName())) {
                    getCustObjIdQry.setParameter(RMDCommonConstants.GROUP_NAME,
                            findNotesServiceVO.getFindNotesSearchCriteriaVO()
                                    .getAssetGroupName());
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getCustomerID()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getCustomerID())) {
                    getCustObjIdQry.setParameter(RMDCommonConstants.CUSTOMERID,
                            findNotesServiceVO.getFindNotesSearchCriteriaVO()
                                    .getCustomerID());
                }

                getCustObjIdQry.setFetchSize(10);
                 arlGetCustObjId = (ArrayList) getCustObjIdQry.list();

                if (null != arlGetCustObjId && !arlGetCustObjId.isEmpty()) {
                    custObjId = arlGetCustObjId.get(0).toString();
                }
                if(RMDCommonConstants.YES.equalsIgnoreCase(findNotesServiceVO
                        .getFindNotesSearchCriteriaVO().getIsNonGPOCUser())){
                    hideNotesRoles = getLookUpValue(RMDCommonConstants.HIDE_ASSETOVERVIEW_NOTES_CREATED_BY_ROLES);
                }
                strGetGenNote
                        .append("SELECT OBJID,ORG_ID, VEHICLE_NO,ID_NUMBER,TO_CHAR(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') CREATION_TIME,");
                strGetGenNote
                        .append("MODEL_NAME, X_CONTROLLER_CFG,LOGIN_NAME,NOTE_TYPE,DESCRIPTION,STICKY ");
                strGetGenNote
                        .append("FROM (SELECT A.OBJID,ORG_ID,VEHICLE_NO,NULL ID_NUMBER,A.CREATION_DATE CREATION_TIME,MODEL_NAME,X_CONTROLLER_CFG,A.CREATED_BY LOGIN_NAME,");
                strGetGenNote
                        .append("DECODE(NVL(A.STICKY,'N'),'Y','STICKY NOTE','GENERIC NOTE') NOTE_TYPE ,GETS_SD_SKB_FIND_NOTES_PKG.GETS_SD_SKB_CONVERT_VARCHAR_FN('C',A.OBJID) DESCRIPTION,NVL(A.STICKY,'N') STICKY ");
                strGetGenNote
                        .append(" FROM GETS_SD_GENERIC_NOTES_LOG A,GETS_RMD_CUST_RNH_RN_V RNHV,TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_MODEL MODEL,GETS_RMD_FLEET FLT,GETS_RMD_CTL_CFG CTLCFG ");
                strGetGenNote
                        .append("WHERE TSP.OBJID  = SITE_PART_OBJID AND VEH.OBJID    = RNHV.VEHICLE_OBJID AND MODEL.OBJID = VEHICLE2MODEL AND FLT.OBJID = VEHICLE2FLEET ");
                /* Added by Murali */
                if(RMDCommonConstants.YES.equalsIgnoreCase(findNotesServiceVO
                        .getFindNotesSearchCriteriaVO().getIsNonGPOCUser())){
                    strGetGenNote
                            .append("AND A.CREATED_BY NOT IN(SELECT CASE WHEN EOA_TABLE.WEB_LOGIN IS NULL THEN OMD_TABLE.USER_ID ELSE EOA_TABLE.LOGIN_NAME END CREATED_BY ");
                    strGetGenNote
                            .append("FROM GET_USR.GET_USR_USERS OMD_TABLE LEFT OUTER JOIN TABLE_USER EOA_TABLE ON OMD_TABLE.USER_ID = EOA_TABLE.WEB_LOGIN,GET_USR_USER_ROLES ROLEID ,GET_USR.GET_USR_ROLES ROLE ");
                    strGetGenNote
                            .append("WHERE  OMD_TABLE.GET_USR_USERS_SEQ_ID = ROLEID.LINK_USERS AND ROLEID.LINK_ROLES = ROLE.GET_USR_ROLES_SEQ_ID AND ROLE.ROLE_NAME IN :hideNotesRoles) ");
                }
                /* End */ 
                strGetGenNote
                        .append("AND VEHICLE_NO NOT LIKE '%BAD' AND (RNHV.VEHICLE_OBJID  = A.GENERIC2VEHICLE OR MODEL.OBJID = A.GENERIC2MODEL OR FLT.OBJID = A.GENERIC2FLEET OR CTLCFG.OBJID = A.GENERIC2CTL_CFG ) ");
                strGetGenNote
                        .append("AND CTLCFG.CONTROLLER_CFG = TSP.X_CONTROLLER_CFG  AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
                strGetGenNote
                        .append("AND BUS_ORG_OBJID = :custObjId AND VEHICLE_NO = :assetNumber) ");
                strGetGenNote
                        .append("WHERE ROWNUM < 201 ORDER BY TO_DATE(CREATION_TIME,'MM/DD/YYYY HH24:MI:SS') DESC ");
                Query getGenNotesQry = finNotesSession
                        .createSQLQuery(strGetGenNote.toString());
                if (null != custObjId
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(custObjId)) {
                    getGenNotesQry.setParameter(RMDCommonConstants.CUSTOBJID,
                            custObjId);
                }
                if (null != findNotesServiceVO.getFindNotesSearchCriteriaVO()
                        .getAssetFrom()
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetFrom())) {
                    getGenNotesQry.setParameter(
                            RMDCommonConstants.ASSET_NUMBER, findNotesServiceVO
                                    .getFindNotesSearchCriteriaVO()
                                    .getAssetFrom());
                }
                if(RMDCommonConstants.YES.equalsIgnoreCase(findNotesServiceVO
                        .getFindNotesSearchCriteriaVO().getIsNonGPOCUser())){
                    getGenNotesQry.setParameter(RMDCommonConstants.HIDE_NOTES_ROLES, hideNotesRoles);
                }
                getGenNotesQry.setFetchSize(50);
                arlGetGenNotes = (ArrayList) getGenNotesQry.list();

                if (null != arlGetGenNotes && !arlGetGenNotes.isEmpty()) {
                    
                    lstGenericNotes = new ArrayList(arlGetGenNotes.size());
                    
                    for (final Iterator<Object> notesIter = arlGetGenNotes
                            .iterator(); notesIter.hasNext();) {
                        final Object[] objGetGenNotes = (Object[]) notesIter
                                .next();
                        findNotesSearchResultVO = new FindNotesSearchResultVO();
                        findNotesSearchResultVO.setNoteSeqId(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[0]));
                        findNotesSearchResultVO.setCustomerId(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[1]));
                        findNotesSearchResultVO.setAssetNumber(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[2]));
                    /*  findNotesSearchResultVO
                                .setLongAssetNumber(Long.valueOf(RMDCommonUtility
                                        .convertObjectToString(objGetGenNotes[2])));*/
                        findNotesSearchResultVO
                                .setAssetGroupName(findNotesServiceVO
                                        .getFindNotesSearchCriteriaVO()
                                        .getAssetGroupName());
                        findNotesSearchResultVO.setCaseId(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[3]));
                        creationDate = RMDCommonUtility
                                        .convertObjectToString(objGetGenNotes[4]);
                        findNotesSearchResultVO.setDate(creationDate);
                        findNotesSearchResultVO.setModel(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[5]));
                        findNotesSearchResultVO.setCreator(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[7]));
                        findNotesSearchResultVO.setType(RMDCommonUtility
                                .convertObjectToString(objGetGenNotes[8]));
                        findNotesSearchResultVO
                                .setNotes(ESAPI.encoder().encodeForXML(RMDCommonUtility
                                        .convertObjectToString(objGetGenNotes[9])));

                        lstGenericNotes.add(findNotesSearchResultVO);
                    }
                }

                findNotesServiceVO.setNotesSearchList(lstGenericNotes);
            }
        } catch (RMDDAOConnectionException ex) {
            LOG.error(
                    "Error ocurred in getInitLoad method of FindNotesDAOImpl",
                    ex);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            findNotesServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(
                    "Error ocurred in getInitLoad method of FindNotesDAOImpl",
                    e);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            findNotesServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(finNotesSession);
            
            arlSearchNotes = null;
            lstSearchResVO = null;
            lstGenericNotes = null;
            objResultVO = null;
            strNoteDesc = null;
            strGetCustObjId = null;
            strGetGenNote = null;
            hideNotesRoles = null;
            creationDate = null;
            findNotesSearchResultVO = null;
            arlGetCustObjId = null;
            arlGetGenNotes = null;

        }
        LOG.debug("End getNotes method of FindNotesDAOImpl");
    }
    
    public String getLookUpValue(String listName) {

        List<GetSysLookupVO> arlSysLookUp = new ArrayList<GetSysLookupVO>();
        String lookupValue = RMDCommonConstants.EMPTY_STRING;
        try {

            arlSysLookUp = objpopupListAdminDAO.getPopupListValues(listName);
            if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {

                for (Iterator iterator = arlSysLookUp.iterator(); iterator
                        .hasNext();) {
                    GetSysLookupVO objSysLookupVO = (GetSysLookupVO) iterator
                            .next();
                    lookupValue = objSysLookupVO.getLookValue();

                }

            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return lookupValue;
    }

    
}
