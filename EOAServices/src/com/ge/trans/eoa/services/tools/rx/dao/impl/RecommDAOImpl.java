/**
 * ============================================================
 * File : RecommDAOImpl.java
 * Description : DAO Implementation for Recommendation screen
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
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseCreateServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransHistVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransTaskDetailVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.RecommDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.AssetHomeHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.tools.xjc.Language;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to perform database related manipulations
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RecommDAOImpl extends BaseDAO implements RecommDAOIntf {

    /**
     *
     */
    private static final long serialVersionUID = -757052913816732274L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RecommDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.NotesDAOIntf
     * #fetchRxStatusList
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This method is used to fetch the advisory status drop down values from
     * database
     */
    @Override
    public void fetchRxStatusList(final AddEditRxServiceVO addeditRxVO) throws RMDDAOException {
        ArrayList<ElementVO> lstRxStatusList = null;
        Session hibernateSession = null;
        try {
            hibernateSession = getHibernateSession();
            if (addeditRxVO.isBlnDelvFlag()) {
                lstRxStatusList = (ArrayList) getLookupListValues(RMDServiceConstants.RX_DELIVERY_STATUS,
                        addeditRxVO.getStrUserLanguage());
            } else {
                lstRxStatusList = (ArrayList) getLookupListValues(RMDServiceConstants.RECOMM_STATUS_LIST_NAME,
                        addeditRxVO.getStrUserLanguage());
            }
            addeditRxVO.setRxStatusList(lstRxStatusList);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXSTATUSLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditRxVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl fetchRxStatusList()", e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXSTATUSLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditRxVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
            lstRxStatusList = null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.NotesDAOIntf
     * #fetchRxStatusList
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This method is used to fetch the advisory type drop down values from
     * database
     */
    @Override
    public void fetchRxTypeList(final AddEditRxServiceVO addeditServiceRxVO) throws RMDDAOException {
        ArrayList<ElementVO> lstRxTypeList = null;
        try {
            lstRxTypeList = (ArrayList) getLookupListValues(RMDServiceConstants.RX_TYPE_LIST_NAME,
                    addeditServiceRxVO.getStrUserLanguage());
            addeditServiceRxVO.setRxTypeList(lstRxTypeList);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTYPELIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditServiceRxVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl fetchRxTypeList()", e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTYPELIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditServiceRxVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.NotesDAOIntf#
     * fetchAssetImpactList
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This method is used to fetch the asset impact drop down values from
     * database
     */
    @Override
    public void fetchAssetImpactList(final AddEditRxServiceVO addeditRxVO) throws RMDDAOException {
        ArrayList<ElementVO> lstAssetImpactList = null;
        try {
            lstAssetImpactList = (ArrayList) getLookupListValues(RMDServiceConstants.ASSET_IMPACT_LIST_NAME,
                    addeditRxVO.getStrUserLanguage());
            addeditRxVO.setAssetImpactList(lstAssetImpactList);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSETIMPLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditRxVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl fetchAssetImpactList()", e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSETIMPLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, addeditRxVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            lstAssetImpactList = null;
        }
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.dao.intf.CaseRxMgmtDAOIntf#
     * fetchEstmTimeRepair() Description : This method is used to fetch the
     * Estimated Time to Repair drop down values
     */
    @Override
    public List fetchEstmTimeRepair(final String strLanguage) throws RMDDAOException {
        List estmTimeRepairList = null;
        try {
            estmTimeRepairList = getLookupListValues(RMDServiceConstants.ESTIMATEDTIMETOREPAIR, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCHESTTIMEREPAIR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl fetchEstmTimeRepair()", e);
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCHESTTIMEREPAIR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return estmTimeRepairList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.CaseRxMgmtDAOIntf#fetchUrgRepair
     * () Description : This method is used to fetch the Urgency of Repair drop
     * down values
     */
    @Override
    public List fetchUrgRepair(final String strLanguage) throws RMDDAOException {
        List urgRepairList = null;
        try {
            urgRepairList = getLookupListValues(RMDServiceConstants.URGENCY_OF_REPAIR, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCHURGREPAIR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl fetchUrgRepair()", e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCHURGREPAIR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return urgRepairList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.dao.intf.RecommDAOIntf#
     * getValidAssetNumber (
     * com.ge.trans.rmd.services.cases.service.valueobjects.CaseCreateServiceVO)
     * Description : This method is used to fetch the Asset Numbers
     */
    @Override
    public List<String> getValidAssetNumber(final CaseCreateServiceVO objCaseCreateServiceVO) throws RMDDAOException {
        Session hibernateSession = null;
        List<String> lstRoadNumber = new ArrayList<String>();
        List lstResult;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iAssetListSize = RMDCommonConstants.INT_CONST;
        int iAsstGrpNum;
        int iAssetNum;
        Query hibQuery = null;
        try {
            hibernateSession = getHibernateSession();
            hibernateSession.flush();
            hibernateSession.clear();
            String strAssetHeader = RMDCommonUtility.getMessage(RMDServiceConstants.QTR_CUSTOMER, null,
                    objCaseCreateServiceVO.getStrLanguage());
            // select ASSET_NUMBER from get_asst_asset where LANGUAGE='en' and
            // asset_number between '0001' AND '0006'
            strQuery = " from AssetHomeHVO assetHome where assetHome.strLanguage =:strLang";
            if (strAssetHeader != null && !RMDCommonConstants.EMPTY_STRING.equals(strAssetHeader)) {
                strQuery = strQuery + " and assetHome.assetGroupHomeHVO.name =:assetHeader ";
            }

            strQuery = strQuery
                    + " and assetHome.roadNumber between :strFromAsset and :strToAsset order by assetHome.longAssetNumber ASC";
            hibQuery = hibernateSession.createQuery(strQuery);
            if (strAssetHeader != null && !RMDCommonConstants.EMPTY_STRING.equals(strAssetHeader)) {
                hibQuery.setString(RMDServiceConstants.ASSETHEADER, strAssetHeader);
            }
            if (StringUtils.isNotBlank(objCaseCreateServiceVO.getStrLanguage())) {
                hibQuery.setString(RMDCommonConstants.STRLANG, objCaseCreateServiceVO.getStrLanguage());
            }
            if (StringUtils.isNotBlank(objCaseCreateServiceVO.getStrFromAsset())) {
                hibQuery.setInteger(RMDCommonConstants.STRFROMASST,
                        RMDCommonUtility.getIntValue(objCaseCreateServiceVO.getStrFromAsset()));
            }
            if (StringUtils.isNotBlank(objCaseCreateServiceVO.getStrToAsset())) {
                hibQuery.setInteger(RMDCommonConstants.STRTOASSET,
                        RMDCommonUtility.getIntValue(objCaseCreateServiceVO.getStrToAsset()));
            } else {
                hibQuery.setInteger(RMDCommonConstants.STRTOASSET,
                        RMDCommonUtility.getIntValue(objCaseCreateServiceVO.getStrFromAsset()));
            }
            lstResult = hibQuery.list();
            iAssetListSize = lstResult.size();
            if (iAssetListSize == 0) {
                throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_ALL_ROAD);
            }
            AssetHomeHVO assetHomeHVO = null;
            for (int index = 0; index < iAssetListSize; index++) {
                assetHomeHVO = (AssetHomeHVO) lstResult.get(index);
                iAsstGrpNum = RMDCommonUtility.getIntValue(
                        assetHomeHVO.getAssetGroupHomeHVO().getGroupNumber() + RMDCommonConstants.EMPTY_STRING);
                iAssetNum = RMDCommonUtility.getIntValue(assetHomeHVO.getRoadNumber());
                // Here validate the asset number
                if (RMDCommonUtility.validateRoadNumber(iAssetNum, iAsstGrpNum)) {
                    lstRoadNumber.add(assetHomeHVO.getRoadNumber());
                }
            }
        } catch (RMDDAOException e) {
            throw new RMDDAOException(e.getErrorDetail().getErrorCode(), e);
        } catch (Exception e) {
            LOG.error("Unexceptected Error in RecommDAOImpl method getValidAssetNumber()", e);
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
        } finally {
            releaseSession(hibernateSession);
            lstResult = null;
        }
        return lstRoadNumber;
    }

    @Override
    public List getRxLookupValues(final String strListName, final String strLanguage) throws RMDDAOException {
        ArrayList<ElementVO> lstLookupValues = null;
        try {
            lstLookupValues = (ArrayList) getLookupListValues(strListName, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl getRxLookupValues()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
        }
        return lstLookupValues;
    }

    /**
     * The method is used to get all the lastUpdatedBy values from DataBase
     * using the Input given Input parameters
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String language) {
        Session hibernateSession = null;
        List<String> arlLastUpdte = null;
        List<RxSearchResultServiceVO> arlRxSearchResultServiceVO = new ArrayList<RxSearchResultServiceVO>();
        RxSearchResultServiceVO objRxSearchResultServiceVO = null;
        Query displayNameQuery = null;
        try {
            hibernateSession = getHibernateSession();
            // Query will get the distinct of lastUpdatedBy values from table
            // get_km_recom.

            StringBuilder lastUpdatedBy = new StringBuilder();

            lastUpdatedBy.append(
                    "SELECT DISTINCT LAST_UPDATED_BY " + " FROM GETS_SD.GETS_SD_RECOM " + " ORDER BY LAST_UPDATED_BY");

            displayNameQuery = hibernateSession.createSQLQuery(lastUpdatedBy.toString());

            List<Object> selectList = displayNameQuery.list();

            if (selectList != null && !selectList.isEmpty()) {
                for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter.hasNext();) {
                    String SolutionRec = (String) lookValueIter.next();
                    objRxSearchResultServiceVO = new RxSearchResultServiceVO();
                    objRxSearchResultServiceVO.setLastUpdatedBy(RMDCommonUtility.convertObjectToString(SolutionRec));

                    arlRxSearchResultServiceVO.add(objRxSearchResultServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlRxSearchResultServiceVO;
    }

    /**
     * The method is used to get all the title values from DataBase using the
     * Input given title and language.
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage) {
        Session hibernateSession = null;
        List<RxSearchResultServiceVO> arlRxSearchResultServiceVO = new ArrayList<RxSearchResultServiceVO>();
        RxSearchResultServiceVO objRxSearchResultServiceVO = null;
        StringBuilder searchRecommQry = new StringBuilder();
        Query displayNameQuery = null;
        try {
            hibernateSession = getHibernateSession();
            // Query will get the distinct of RX Titles values from table
            if (null != strTitle && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strTitle)) {
                searchRecommQry.append(
                        "SELECT DISTINCT TITLE FROM GETS_SD.GETS_SD_RECOM WHERE LOWER(TITLE) LIKE LOWER( :strTitle)");
                displayNameQuery = hibernateSession.createSQLQuery(searchRecommQry.toString());
                displayNameQuery.setParameter("strTitle",
                        RMDServiceConstants.PERCENTAGE + strTitle + RMDServiceConstants.PERCENTAGE);
                List<Object> selectList = displayNameQuery.list();

                if (selectList != null && !selectList.isEmpty()) {
                    for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter.hasNext();) {
                        String SolutionRec = (String) lookValueIter.next();
                        objRxSearchResultServiceVO = new RxSearchResultServiceVO();
                        objRxSearchResultServiceVO.setStrTitle(RMDCommonUtility.convertObjectToString(SolutionRec));

                        arlRxSearchResultServiceVO.add(objRxSearchResultServiceVO);
                    }
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlRxSearchResultServiceVO;
    }

    /**
     * The method is used to get all the title values from DataBase using the
     * Input given title and language.
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionNotes(String strSolutionNotes, String strLanguage) {
        Session hibernateSession = null;
        List<RxSearchResultServiceVO> arlRxSearchResultServiceVO = new ArrayList<RxSearchResultServiceVO>();
        RxSearchResultServiceVO objRxSearchResultServiceVO = null;
        StringBuilder searchRecommQry = new StringBuilder();
        Query displayNameQuery;
        try {
            hibernateSession = getHibernateSession();
            // Query will get the distinct of Notes values from table
            // get_km_recom_hist.

            if (null != strSolutionNotes && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strSolutionNotes)) {
                searchRecommQry.append(
                        "SELECT DISTINCT REVISION_NOTES FROM GETS_SD.GETS_SD_RECOM_HIST WHERE lower(REVISION_NOTES) LIKE LOWER( :strNotes) ");
                displayNameQuery = hibernateSession.createSQLQuery(searchRecommQry.toString());
                displayNameQuery.setParameter("strNotes",
                        RMDServiceConstants.PERCENTAGE + strSolutionNotes + RMDServiceConstants.PERCENTAGE);
                List<Object> selectList = displayNameQuery.list();

                if (selectList != null && !selectList.isEmpty()) {
                    for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter.hasNext();) {
                        String SolutionRec = (String) lookValueIter.next();
                        objRxSearchResultServiceVO = new RxSearchResultServiceVO();
                        objRxSearchResultServiceVO.setNotes(RMDCommonUtility.convertObjectToString(SolutionRec));

                        arlRxSearchResultServiceVO.add(objRxSearchResultServiceVO);
                    }
                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_NOTES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlRxSearchResultServiceVO;
    }

    @Override
    public List<ElementVO> getSubsystemValues(String strListName, String strLanguage) throws RMDDAOException {
        ArrayList<ElementVO> lstLookupValues = null;
        try {
            lstLookupValues = (ArrayList) getLookupList(strListName, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RecommDAOImpl getSubsystemValues()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return lstLookupValues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getRxRepairCodes ()
     */
    /* This Method is used for fetch the model list from database */

    @Override
    @SuppressWarnings("rawtypes")
    public List getRxRepairCodes(final String strObjID) throws RMDDAOException {
        ArrayList arlRxRepairCodes = null;
        Session session = null;

        ElementVO objElementVO = null;
        LOG.debug("Enter into getRxRepairCodes in RecommDAOImpl method");
        Query queryRepairCodes = null;
        StringBuilder builderRepairCodesQuery = new StringBuilder();
        String strModelObjid[] = null;
        try {

            session = getHibernateSession();
            if (!RMDCommonUtility.isNullOrEmpty(strObjID)) {
                strModelObjid = strObjID.split(RMDCommonConstants.COMMMA_SEPARATOR);
                builderRepairCodesQuery.append(
                        "SELECT DISTINCT GRC.OBJID REPAIRCODEID, GRC.REPAIR_CODE REPAIRCODE, GRC.REPAIR_DESC REPAIRCODEDESC FROM GETS_SD_REPAIR_CODES GRC, GETS_RMD_MODEL GRM, GETS_SD_REP_MTM_MOD GMTM ");
                builderRepairCodesQuery.append(
                        "	WHERE GRC.OBJID = GMTM.MTMMOD2REPAIR AND GMTM.MTMMOD2MODEL = GRM.OBJID AND GRC.REPAIR_STATUS = 'ACTIVE' AND GRM.OBJID IN ( :ModelObjid) ");
                builderRepairCodesQuery.append("	ORDER BY REPAIR_CODE ASC");
                queryRepairCodes = session.createSQLQuery(builderRepairCodesQuery.toString());
                queryRepairCodes.setParameterList(RMDServiceConstants.MODEL_OBJID, strModelObjid);
                List<Object> lookupList = queryRepairCodes.list();

                if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                    arlRxRepairCodes = new ArrayList();
                    for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {

                        final Object[] lookupRecord = (Object[]) iter.next();
                        objElementVO = new ElementVO();
                        objElementVO.setId(RMDCommonUtility.convertObjectToString(lookupRecord[0]));
                        objElementVO.setName(RMDCommonUtility
                                .convertObjectToString(lookupRecord[1] + RMDCommonConstants.PIPELINE_CHARACTER

                                        + lookupRecord[2]));
                        arlRxRepairCodes.add(objElementVO);

                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.EMPTY_STRING), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.EMPTY_STRING), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End of getRxRepairCodes in RecommDAOImpl  method" + arlRxRepairCodes.size());

        return arlRxRepairCodes;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getRxTaskdesc ()
     */
    /*
     * This Method is used for getting Task Description for Repair Code from
     * database
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getRxTaskdesc(final String strRepairCode) throws RMDDAOException {
        ArrayList arlRepairCode = null;
        Session session = null;

        ElementVO objElementVO = null;
        LOG.debug("Enter into getRxTaskdesc in RecommDAOImpl method");
        Codec ORACLE_CODEC = new OracleCodec();
        try {
            session = getHibernateSession();

            String queryString = "SELECT DISTINCT TASK_DESC FROM   GETS_SD_RECOM_TASK WHERE  RECOM_TASK2REP_CODE='"
                    + ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strRepairCode) + "'";
            Query query = session.createSQLQuery(queryString);
            List<String> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlRepairCode = new ArrayList();

                for (int i = 0; i < lookupList.size(); i++) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(lookupList.get(i));

                    objElementVO.setName(lookupList.get(i));
                    arlRepairCode.add(objElementVO);

                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.EMPTY_STRING), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.EMPTY_STRING), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End of getRxTaskdesc in RecommDAOImpl  method" + arlRepairCode.size());

        return arlRepairCode;
    }

    /*
     * This Method is used for fetch the Solution Configurations list from
     * database
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDDAOException {
        List<ElementVO> arlRxConfigurations = null;
        Session session = null;
        ElementVO objElementVO = null;
        Query nativeQuery = null;
        String queryString = null;
        List lookupList = null;
        try {
            LOG.debug("Start getRxConfigurations method in RecomDAOImpl");
            session = getHibernateSession();
            queryString = "SELECT  DISTINCT CFG_ITEM FROM GETS_SD_CFG_GROUP_ITEM";
            nativeQuery = session.createSQLQuery(queryString);
            lookupList = nativeQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                arlRxConfigurations = new ArrayList();
                for (int icount = 0; icount < lookupList.size(); icount++) {
                    Object strConfig = lookupList.get(icount);
                    objElementVO = new ElementVO();
                    objElementVO.setId(strConfig.toString());
                    objElementVO.setName(strConfig.toString());
                    arlRxConfigurations.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONFIGURATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONFIGURATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End getRxConfigurations method in RecomDAOImpl");

        return arlRxConfigurations;
    }

    public List getRxLocoImpact() throws RMDDAOException {
        ArrayList arlRepairCode = null;

        return arlRepairCode;
    }

    public List getSolutionTypes() throws RMDDAOException {
        ArrayList arlRepairCode = null;

        return arlRepairCode;
    }
    
    
    
    /**
     * The method is used to get all the createdBy values from DataBase
     * using the Input given Input parameters
     * 
     * @param uriParam
     * @return List of SolutionResponseType
     * @throws RMDServiceException
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionCreatedBy(String language) {
        Session hibernateSession = null;
        List<String> arlCreatedBy = null;
        List<RxSearchResultServiceVO> arlRxSearchResultServiceVO = new ArrayList<RxSearchResultServiceVO>();
        RxSearchResultServiceVO objRxSearchResultServiceVO = null;
        Query displayNameQuery = null;
        try {
            hibernateSession = getHibernateSession();
            StringBuilder lastUpdatedBy = new StringBuilder();
            lastUpdatedBy.append(
                    "SELECT DISTINCT CREATED_BY " + " FROM GETS_SD.GETS_SD_RECOM " + " WHERE CREATED_BY IS NOT NULL ORDER BY CREATED_BY");

            displayNameQuery = hibernateSession.createSQLQuery(lastUpdatedBy.toString());

            List<Object> selectList = displayNameQuery.list();

            if (selectList != null && !selectList.isEmpty()) {
                for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter.hasNext();) {
                    String SolutionRec = (String) lookValueIter.next();
                    objRxSearchResultServiceVO = new RxSearchResultServiceVO();
                    objRxSearchResultServiceVO.setCreatedBy(RMDCommonUtility.convertObjectToString(SolutionRec));
                    arlRxSearchResultServiceVO.add(objRxSearchResultServiceVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlRxSearchResultServiceVO;
    }
}
