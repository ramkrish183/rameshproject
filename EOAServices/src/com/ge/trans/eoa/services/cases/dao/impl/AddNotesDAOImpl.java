/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesDAOImpl.java
 * Description : DAO implementation for Add Notes
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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.dao.intf.AddNotesDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstAssetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstGroupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmGenericNotesLogHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to perform the database related
 *              manipulations.
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class AddNotesDAOImpl extends BaseCaseDAO implements AddNotesDAOIntf {

    private static final long serialVersionUID = 3401887445765736788L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(AddNotesDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.AddNotesDAOIntf
     * #initalLoad(
     * com.ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO)
     * This method is used to fetch the initial drop down values from database
     */

    @Override
    public void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDDAOException {
        List arlAssetHeader;
        List lstAssetHeader;
        GetAsstGroupHVO objGetAsstGroup = null;
        ElementVO objElementVO = null;
        int iAssetHeaderList = RMDCommonConstants.INT_CONST;
        Session objHibernateSession = null;
        try {
            objHibernateSession = getHibernateSession();
            lstAssetHeader = new ArrayList();
            Criteria criteria = objHibernateSession.createCriteria(GetAsstGroupHVO.class);
            criteria.add(Restrictions.eq(RMDCommonConstants.STRLANGUAGE, addnotesServiceVO.getStrLanguage()));
            arlAssetHeader = criteria.list();
            iAssetHeaderList = arlAssetHeader.size();
            objElementVO = new ElementVO();
            for (int index = 0; index < iAssetHeaderList; index++) {
                objGetAsstGroup = (GetAsstGroupHVO) arlAssetHeader.get(index);
                objElementVO = new ElementVO();
                objElementVO.setId(objGetAsstGroup.getGetAsstGroupSeqId().toString());
                objElementVO.setName(objGetAsstGroup.getName());
                lstAssetHeader.add(objElementVO);
            }
            addnotesServiceVO.setLstAssetHeader(lstAssetHeader);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_INITIAL_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addnotesServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_INITIAL_LOAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addnotesServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            objGetAsstGroup = null;
            arlAssetHeader = null;
            lstAssetHeader = null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.AddNotesDAOIntf
     * #saveNotes(com
     * .ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to add new notes into database
     */
    @Override
    public String saveNotes(AddNotesServiceVO notesServiceVO) throws RMDDAOException {
        Session objSession = null;
        String strCustomer = RMDCommonConstants.EMPTY_STRING;
        try {
            String currentTime = RMDCommonUtility.formatDateToString(RMDCommonConstants.DateConstants.MMddyyyyHHmm,
                    new java.util.Date());
            String strFromPage = notesServiceVO.getStrFromPage();
            String strAssetFrom = notesServiceVO.getFromAsset();
            String strNotes = AppSecUtil.decodeString(notesServiceVO.getNotes());
            String strUserName = notesServiceVO.getStrUserName();
            String strAsstGrp = notesServiceVO.getAssetGrpName();

            StringBuilder updateQry = new StringBuilder();
            StringBuilder vehicleObj = new StringBuilder();
            StringBuilder insertQry = new StringBuilder();

            objSession = getHibernateSession(strUserName);
            // Get vehicle obj id
            vehicleObj.append(
                    "SELECT VEH.OBJID FROM TABLE_SITE_PART TSP,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO WHERE ");
            vehicleObj.append(
                    "TSP.OBJID = VEH.VEHICLE2SITE_PART  AND TSP.SERIAL_NO NOT LIKE '%BAD%' AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            vehicleObj.append(
                    "AND TSP.SERIAL_NO =:assetNumber AND TSP.X_VEH_HDR =:assetGrpName AND TBO.ORG_ID =:customerId");

            Query vehicleQry = objSession.createSQLQuery(vehicleObj.toString());

            if (null != strAsstGrp && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAsstGrp)) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
            }
            if (null != strAssetFrom && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAssetFrom)) {
                vehicleQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
            }
            if (null != notesServiceVO.getCustomer()
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(notesServiceVO.getCustomer())) {
                vehicleQry.setParameter(RMDCommonConstants.CUSTOMER_ID, notesServiceVO.getCustomer());
            }

            List objIdLst = vehicleQry.list();
            String objId = null;

            if (null != objIdLst && !objIdLst.isEmpty()) {
                objId = objIdLst.get(0).toString();
            }

            // Insert notes for fleet view basic

            if (objSession != null) {
                if (RMDServiceConstants.NOTESLOCO.equalsIgnoreCase(strFromPage)) {
                    updateQry.append(
                            "UPDATE GETS_RMD_DD_QUEUE SET ACTIVE = 0, DIAGNOSTICS='Inactive',LAST_UPDATED_BY =:eoaUserId,LAST_UPDATED_DATE = SYSDATE ");
                    updateQry.append(
                            " WHERE ROAD_NUMBER_HEADER =:assetGrpName AND ROAD_NUMBER =:assetNumber  AND ACTIVE =1");
                    Query updQry = objSession.createSQLQuery(updateQry.toString());
                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(notesServiceVO.getEoaUserId())) {
                        updQry.setParameter(RMDCommonConstants.EOA_USERID, notesServiceVO.getEoaUserId());
                    }
                    if (null != strAsstGrp && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAsstGrp)) {
                        updQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
                    }
                    if (null != strAssetFrom && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAssetFrom)) {
                        updQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
                    }
                    updQry.executeUpdate();

                    insertQry.append(
                            "INSERT INTO GETS_RMD_DD_QUEUE (OBJID, CREATION_DATE,CREATED_BY,LAST_UPDATED_DATE, LAST_UPDATED_BY, ");
                    insertQry.append(
                            "ROAD_NUMBER_HEADER, ROAD_NUMBER, CREATION_TIME, DIAGNOSTICS,NOTES, ACTIVE, CASE_ID,DD_QUEUE2VEHICLE) VALUES ");
                    insertQry.append(
                            "(GETS_RMD_DD_QUEUE_SEQ.NEXTVAL, SYSDATE,:eoaUserId,SYSDATE,:eoaUserId, :assetGrpName,:assetNumber, SYSDATE,");
                    insertQry.append("'No Request', :notes, 1, NULL,:vehicleObjid)");

                    Query intQry = objSession.createSQLQuery(insertQry.toString());

                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(notesServiceVO.getEoaUserId())) {
                        intQry.setParameter(RMDCommonConstants.EOAUSER_ID, notesServiceVO.getEoaUserId());
                    }

                    if (null != strAsstGrp && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAsstGrp)) {
                        intQry.setParameter(RMDCommonConstants.ASSET_GRP_NAME, strAsstGrp);
                    }

                    if (null != strAssetFrom && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strAssetFrom)) {
                        intQry.setParameter(RMDCommonConstants.ASSET_NUMBER, strAssetFrom);
                    }

                    if (null != strNotes && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strNotes)) {
                        intQry.setParameter(RMDCommonConstants.NOTES, strNotes);
                    }

                    if (null != objId && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objId)) {
                        intQry.setParameter(RMDCommonConstants.VEHICLE_OBJID, objId);
                    }

                    intQry.executeUpdate();
                }

                // Insert notes when from page is 'Menu' -Asset overview
                if (RMDServiceConstants.NOTESMENU.equalsIgnoreCase(strFromPage)) {
                    StringBuilder insertWhenFromPageMenu = new StringBuilder();
                    insertWhenFromPageMenu.append("INSERT INTO GETS_SD_GENERIC_NOTES_LOG(OBJID, LAST_UPDATE_DATE,");
                    insertWhenFromPageMenu.append(
                            "LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,DESCRIPTION,STICKY,GENERIC2VEHICLE) VALUES");
                    insertWhenFromPageMenu.append(
                            "(GETS_SD.GETS_SD_GENERIC_NOTES_LOG_SEQ.NEXTVAL, SYSDATE,:eoaUserId,  SYSDATE,:eoaUserId,:notes,:sticky,:vehicleObjid)");

                    Query intWhenFromPageMenuQry = objSession.createSQLQuery(insertWhenFromPageMenu.toString());

                    if (null != notesServiceVO.getEoaUserId()
                            && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(notesServiceVO.getEoaUserId())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.EOA_USERID,
                                notesServiceVO.getEoaUserId());
                    }

                    if (null != strNotes && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strNotes)) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.NOTES, strNotes);
                    }
                    if ((RMDCommonConstants.NO).equalsIgnoreCase(notesServiceVO.getSticky())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.STICKY, RMDCommonConstants.LETTER_N);
                    } else if ((RMDCommonConstants.YES).equalsIgnoreCase(notesServiceVO.getSticky())) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.STICKY, RMDCommonConstants.LETTER_Y);
                    }
                    if (null != objId && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objId)) {
                        intWhenFromPageMenuQry.setParameter(RMDCommonConstants.VEHICLE_OBJID, objId);
                    }

                    intWhenFromPageMenuQry.executeUpdate();
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (RMDDAOException e) {
            throw new RMDDAOException(e.getErrorDetail().getErrorCode(), e);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            notesServiceVO.setStrStatus(RMDCommonConstants.FAIL);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return strCustomer;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.AddNotesDAOIntf#
     * overwriteNotes
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO)
     * This method is used to overwrite the existing notes into database.
     */
    @Override
    public String overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDDAOException {
        List arlAssetDetails;
        List arlStkyExist;
        List arlStkyAssetExist;
        List arlCaseDetails;
        int iAssetDetails;
        int iStkyExist;
        int iStkyAssetExist;
        String strCustomer = RMDCommonConstants.EMPTY_STRING;
        GetCmGenericNotesLogHVO objNotesVO;
        GetAsstAssetHVO objAssetVO;
        Session objOverwrieNotesSession = null;
        try {
            String strFromPage = notesServiceVO.getStrFromPage();
            String strSticky = notesServiceVO.getSticky();
            String strAssetFrom = notesServiceVO.getFromAsset();
            String strAssetTo = notesServiceVO.getToAsset();
            String strNotes = notesServiceVO.getNotes();
            String strCaseId = notesServiceVO.getCaseId();
            String strUserId = notesServiceVO.getStrUserName();
            objOverwrieNotesSession = getHibernateSession(strUserId);
            /* Get details of asset */
            Criteria critAsset = objOverwrieNotesSession.createCriteria(GetAsstAssetHVO.class);
            critAsset.add(Restrictions.between(RMDServiceConstants.ASSETNUMBER, strAssetFrom, strAssetTo));
            critAsset.add(Restrictions.eq("language", notesServiceVO.getStrLanguage()));
            arlAssetDetails = critAsset.list();
            iAssetDetails = arlAssetDetails.size();
            if (iAssetDetails != 0) {
                objAssetVO = (GetAsstAssetHVO) arlAssetDetails.get(0);
                strCustomer = objAssetVO.getGetCmCustomer().getName();

                /* overwrie Notes from menu starts */
                if (RMDServiceConstants.NOTESMENU.equalsIgnoreCase(strFromPage)
                        && RMDCommonConstants.YES.equalsIgnoreCase(strSticky)) {
                    /* overwriting already existing asset level sticky note */
                    Criteria critStickyExist = objOverwrieNotesSession.createCriteria(GetCmGenericNotesLogHVO.class);
                    critStickyExist.add(Restrictions.and(Restrictions.eq(RMDServiceConstants.LINK_ASSET, objAssetVO),
                            Restrictions.eq(RMDServiceConstants.STICKY, RMDCommonConstants.LETTER_Y)));
                    arlStkyExist = critStickyExist.list();
                    iStkyExist = arlStkyExist.size();
                    if (iStkyExist > 0) {
                        objNotesVO = (GetCmGenericNotesLogHVO) arlStkyExist.get(0);
                        objNotesVO.setDescription(strNotes);
                        objNotesVO.setSticky(RMDCommonConstants.LETTER_Y);
                        objNotesVO.setGetCmCase(null);
                        objOverwrieNotesSession.update(objNotesVO);
                        objOverwrieNotesSession.flush();
                        notesServiceVO.setBStickyExist(RMDCommonConstants.FALSE);
                        notesServiceVO.setStrSave(RMDCommonConstants.YES);
                        notesServiceVO.setStrStatus(RMDCommonConstants.SUCCESS);
                    }
                    /* overwriting already existing sticky note ends */
                }
                /* overwrie Notes from menu ends */
                /* overwrie Notes from case starts */
                if (RMDServiceConstants.NOTESCASE.equalsIgnoreCase(strFromPage)) {
                    /* Get details of case */
                    Criteria critCase = objOverwrieNotesSession.createCriteria(GetCmCaseHVO.class);
                    critCase.add(Restrictions.eq(RMDServiceConstants.CASEID, strCaseId));
                    arlCaseDetails = critCase.list();
                    GetCmCaseHVO caseVO = (GetCmCaseHVO) arlCaseDetails.get(0);
                    /* Case level sticky note overwrite starts */
                    if (RMDCommonConstants.YES.equalsIgnoreCase(strSticky)) {
                        /*
                         * overwriting already existing asset level sticky note
                         */
                        Criteria critStickyExistAsset = objOverwrieNotesSession
                                .createCriteria(GetCmGenericNotesLogHVO.class);
                        critStickyExistAsset.add(Restrictions.and(
                                Restrictions.and(Restrictions.eq(RMDServiceConstants.LINK_ASSET, objAssetVO),
                                        Restrictions.isNull(RMDServiceConstants.GETCMCASE)),
                                Restrictions.eq(RMDServiceConstants.STICKY, RMDCommonConstants.LETTER_Y)));
                        arlStkyAssetExist = critStickyExistAsset.list();
                        iStkyAssetExist = arlStkyAssetExist.size();
                        if (iStkyAssetExist > 0) {
                            objNotesVO = (GetCmGenericNotesLogHVO) arlStkyAssetExist.get(0);
                            objNotesVO.setDescription(strNotes);
                            objNotesVO.setSticky(RMDCommonConstants.LETTER_Y);
                            objNotesVO.setGetAsstAssetByLinkAsset(objNotesVO.getGetAsstAssetByLinkAsset());
                            objNotesVO.setGetCmCase(caseVO);
                            objOverwrieNotesSession.update(objNotesVO);
                            objOverwrieNotesSession.flush();
                            notesServiceVO.setBStickyExist(RMDCommonConstants.FALSE);
                            notesServiceVO.setStrSave(RMDCommonConstants.YES);
                            notesServiceVO.setStrStatus(RMDCommonConstants.SUCCESS);
                        } else {
                            // over write the existing case level sticky notes
                            Criteria critStickyExist = objOverwrieNotesSession
                                    .createCriteria(GetCmGenericNotesLogHVO.class);
                            critStickyExist.add(Restrictions.and(
                                    Restrictions.and(Restrictions.eq(RMDServiceConstants.GETCMCASE, caseVO),
                                            Restrictions.eq(RMDServiceConstants.LINK_ASSET, objAssetVO)),
                                    Restrictions.eq(RMDServiceConstants.STICKY, RMDCommonConstants.LETTER_Y)));
                            arlStkyExist = critStickyExist.list();
                            iStkyExist = arlStkyExist.size();
                            if (iStkyExist > 0) {
                                objNotesVO = (GetCmGenericNotesLogHVO) arlStkyExist.get(0);
                                objNotesVO.setDescription(strNotes);
                                objNotesVO.setSticky(RMDCommonConstants.LETTER_Y);
                                objOverwrieNotesSession.update(objNotesVO);
                                objOverwrieNotesSession.flush();
                                notesServiceVO.setBStickyExist(RMDCommonConstants.FALSE);
                                notesServiceVO.setStrSave(RMDCommonConstants.YES);
                                notesServiceVO.setStrStatus(RMDCommonConstants.SUCCESS);
                            }
                        }
                        /* overwriting already existing sticky note ends */
                        /* Check for sticky notes already exist ends */
                    }
                }
            }
            /* overwrite Notes from case ends */
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OVERWRITE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            notesServiceVO.setStrStatus(RMDCommonConstants.FAIL);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OVERWRITE_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objOverwrieNotesSession);
            objNotesVO = null;
            arlAssetDetails = null;
            arlCaseDetails = null;
            arlStkyExist = null;
        }
        return strCustomer;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.AddNotesDAOIntf#
     * deleteFleetRemark
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO)
     * This method is used to delete the existing fleet summary from database.
     */
    @Override
    public boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDDAOException {
        Session objHibernateSession = null;
        GetSysLookupHVO lookupHVO;
        ArrayList arlResult = null;
        boolean isDeleted = false;
        try {
            lookupHVO = getParentLookupHVO(RMDServiceConstants.NOTE_TYPE, RMDServiceConstants.LOCO_NOTES,
                    notesServiceVO.getStrLanguage());
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetCmGenericNotesLogHVO.class);
            criteria.setFetchMode(RMDCommonConstants.GETASST_ASSET_BYLINK_ASSET, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETASST_ASSET_BYLINK_ASSET, RMDCommonConstants.ASSET);
            criteria.add(Restrictions.eq(RMDCommonConstants.ASSET_ASSETNUMBER, notesServiceVO.getFromAsset()));
            criteria.add(Restrictions.eq(RMDCommonConstants.NOTETYPE, lookupHVO.getGetSysLookupSeqId()));
            arlResult = (ArrayList) criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlResult)) {
                objHibernateSession.delete(arlResult.get(0));
                objHibernateSession.flush();
                isDeleted = true;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_DEL_FLEET_REMARKS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_DEL_FLEET_REMARKS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, notesServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            arlResult = null;
        }
        return isDeleted;
    }

}
