/**
 * ============================================================
 * File : RecommBOImpl.java
 * Description : BO Implementation for Recommendation screen
 *
 * Package : com.ge.trans.rmd.services.cases.bo.impl
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
package com.ge.trans.eoa.services.tools.rx.bo.impl;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf;
import com.ge.trans.eoa.services.tools.rx.bo.intf.RecommEoaBOIntf;
import com.ge.trans.eoa.services.tools.rx.dao.intf.AddEditRxDAOIntf;
import com.ge.trans.eoa.services.tools.rx.dao.intf.RecommDAOIntf;
import com.ge.trans.eoa.services.tools.rx.dao.intf.SelectRxEoaDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.ActionableRxTypeVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultEoaLiteServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RecomDelvStatusVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call RecommDAOImpl methods
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RecommEoaBOImpl implements RecommEoaBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RecommEoaBOImpl.class);

    private SelectRxEoaDAOIntf selectRxDAOIntf;
    private RecommDAOIntf recommDAOIntf;
    private AddEditRxDAOIntf addEditRxDAOIntf;
    private RuleCommonDAOIntf ruleCommonDAOIntf;

    public RecommEoaBOImpl(RecommDAOIntf recommDAOIntf, SelectRxEoaDAOIntf selectRxDAOIntf,
            AddEditRxDAOIntf addEditRxDAOIntf, RuleCommonDAOIntf ruleCommonDAOIntf) {
        super();
        this.recommDAOIntf = recommDAOIntf;
        this.selectRxDAOIntf = selectRxDAOIntf;
        this.addEditRxDAOIntf = addEditRxDAOIntf;
        this.ruleCommonDAOIntf = ruleCommonDAOIntf;
    }

    /**
     * @return the recommDAOIntf
     */
    public RecommDAOIntf getRecommDAOIntf() {
        return recommDAOIntf;
    }

    /**
     * @param recommDAOIntf
     *            the recommDAOIntf to set
     */
    public void setRecommDAOIntf(RecommDAOIntf recommDAOIntf) {
        this.recommDAOIntf = recommDAOIntf;
    }

    /**
     * @return the selectRxDAOIntf
     */
    public SelectRxEoaDAOIntf getSelectRxDAOIntf() {
        return selectRxDAOIntf;
    }

    /**
     * @param selectRxDAOIntf
     *            the selectRxDAOIntf to set
     */
    public void setSelectRxDAOIntf(SelectRxEoaDAOIntf selectRxDAOIntf) {
        this.selectRxDAOIntf = selectRxDAOIntf;
    }

    /**
     * @return the addEditRxDAOIntf
     */
    public AddEditRxDAOIntf getAddEditRxDAOIntf() {
        return addEditRxDAOIntf;
    }

    /**
     * @param addEditRxDAOIntf
     *            the addEditRxDAOIntf to set
     */
    public void setAddEditRxDAOIntf(AddEditRxDAOIntf addEditRxDAOIntf) {
        this.addEditRxDAOIntf = addEditRxDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.eoa.services.tools.rx.bo.intf.RecommEoaBOIntf#searchRecomm(
     * com.ge.trans.eoa.services.cases.service.valueobjects.
     * RxSearchCriteriaEoaServiceVO)
     */
    @Override
    public List searchRecomm(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = selectRxDAOIntf.searchRecomm(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /**
     * This method is used for fetching RxTitles of Fleet screen as part of OMD
     * Performance
     * 
     * @param strLanguage,solutionStatus
     * @return List<RxSearchResultEoaLiteServiceVO>
     * @throws RMDBOException
     */
    @Override
    public List<RxSearchResultEoaLiteServiceVO> getRxTitlesLite(String strLanguage, String solutionStatus)
            throws RMDBOException {
        List<RxSearchResultEoaLiteServiceVO> rxDetailsList;
        try {
            rxDetailsList = selectRxDAOIntf.getRxTitlesLite(strLanguage, solutionStatus);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /**
     * @Description:This method is used for fetching Solution details
     * @param RxSearchCriteriaEoaServiceVO
     * @return RxDetailsVO
     * @throws RMDBOException
     */
    @Override
    public RxDetailsVO getRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDBOException {
        RxDetailsVO rxDetailsVO = null;
        try {
            rxDetailsVO = selectRxDAOIntf.getRecommDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsVO;
    }

    /**
     * @Description:This method is used for fetching Solution task details
     * @param RxSearchCriteriaEoaServiceVO
     * @return List<RxTaskDetailsVO>
     * @throws RMDBOException
     */
    @Override
    public List<RxTaskDetailsVO> getRxTaskDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDBOException {
        List<RxTaskDetailsVO> rxTaskDetailsList = null;
        try {
            rxTaskDetailsList = selectRxDAOIntf.getRxTaskDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxTaskDetailsList;
    }

    @Override
    public List<RxDetailsVO> getListRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDBOException {
        List<RxDetailsVO> rxDetailsVO = null;
        try {
            rxDetailsVO = selectRxDAOIntf.getListRecommDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#searchRx(
     * RxSearchCriteriaServiceVO) This Method is used to call the searchRx
     * method in RecommDAOImpl
     */
    @Override
    public List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO) throws RMDBOException {
        List rxUrgencyList = new ArrayList();
        try {
            rxUrgencyList = selectRxDAOIntf.getWorstUrgency(rxUrgencyParamVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return rxUrgencyList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#getActionableRxTypes
     * (String strCustomerId, String strLanguage) This Method is used to call
     * the getActionableRxTypes method in selectRxDAOImpl
     */
    @Override
    public List<ActionableRxTypeVO> getActionableRxTypes(final ActionableRxTypeVO objActionableRxTypeVO)
            throws RMDBOException {
        List<ActionableRxTypeVO> actionableRxTypes = null;
        try {
            actionableRxTypes = selectRxDAOIntf.getActionableRxTypes(objActionableRxTypeVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return actionableRxTypes;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#getActionableRxTypes
     * (String strCustomerId, String strLanguage) This Method is used to call
     * the getActionableRxTypes method in selectRxDAOImpl
     */
    @Override
    public List<ActionableRxTypeVO> getNonActionableRxTitles(String strCustomerId, String strLanguage)
            throws RMDBOException {
        List<ActionableRxTypeVO> actionableRxTypes = null;
        try {
            actionableRxTypes = selectRxDAOIntf.getNonActionableRxTitles(strCustomerId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return actionableRxTypes;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.tools.rx.bo.intf.RecommEoaBOIntf#
     * getRxDeliveryStatus(java.lang.String)
     */
    @Override
    public void checkRxDeliveryStatus(String caseId, String language) throws RMDBOException {

        try {
            selectRxDAOIntf.checkRxDeliveryStatus(caseId, language);

        } catch (RMDDAOException e) {
            throw e;
        }

    }

    @Override
    public String getRxScoreStatus(String rxCaseId, String language) throws RMDBOException {
        // TODO Auto-generated method stub

        String caseSuccess = null;
        try {
            caseSuccess = selectRxDAOIntf.getRxScoreStatus(rxCaseId, language);

        } catch (RMDDAOException e) {
            throw e;
        }

        return caseSuccess;
    }

    @Override
    public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language) throws RMDBOException {

        RecomDelvStatusVO recomDelvVO = null;
        try {
            recomDelvVO = selectRxDAOIntf.getRxDeliveryStatus(caseId, language);

        } catch (RMDDAOException e) {
            throw e;
        }

        return recomDelvVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.bo.intf.RecommBOIntf#getRxLookupValues
     * (java.lang.String, java.lang.String)
     */
    @Override
    public List getRxLookupValues(String listName, String strLanguage) throws RMDBOException {
        List arlRxLookupValues = new ArrayList();
        try {
            arlRxLookupValues = selectRxDAOIntf.getRxLookupValues(listName, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlRxLookupValues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#
     * generateSolutionDetailsPDF(AddEditRxServiceVO addeditRxServiceVO) This
     * Method is used to call the generateSolutionDetailsPDF method in
     * RecommDAOImpl
     */
    @Override
    public String generateSolutionDetailsPDF(final AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException {
        try {
            return addEditRxDAOIntf.generateSolutionDetailsPDF(addeditRxServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#getRxConfigurations
     * (String strLanguage,String strUserLanguage ) This Method is used to call
     * the getSolutionTypes method in RecommDAOImpl
     */
    @Override
    public List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDBOException {
        List<ElementVO> rxConfigurations;
        try {
            rxConfigurations = recommDAOIntf.getRxConfigurations(strLanguage, strUserLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxConfigurations;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#getRxRepairCodes( )
     * This Method is used to call the getRxRepairCodes method in RecommDAOImpl
     */
    @Override
    public List getRxRepairCodes(String strObjID) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getRxRepairCodes(strObjID);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#getRxTaskdesc(
     * ) This Method is used to call the getRxTaskdesc method in RecommDAOImpl
     */
    @Override
    public List getRxTaskdesc(String strRepairCode) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getRxTaskdesc(strRepairCode);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.bo.intf.RecommBOIntf#
     * getSubsystemValues (java.lang.String, java.lang.String)
     */
    @Override
    public List<ElementVO> getSubsystemValues(String listName, String strLanguage) throws RMDBOException {
        List<ElementVO> arlRxLookupValues = new ArrayList<ElementVO>();
        try {
            arlRxLookupValues = recommDAOIntf.getSubsystemValues(listName, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlRxLookupValues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#
     * getSolutionLastUpdatedBy (language) This Method is used to call the
     * getSolutionLastUpdatedBy method in SelectRxDAOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String language) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getSolutionLastUpdatedBy(language);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /**
     * @Author:
     * @param strTitle
     *            ,strLanguage
     * @throws RMDDAOException
     * @Description:This Method is used to call the getSolutionTitles method in
     *                   RecommDAOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getSolutionTitles(strTitle, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /**
     * @Author:
     * @param strTitle
     *            ,strLanguage
     * @throws RMDDAOException
     * @Description:This Method is used to call the getSolutionNotes method in
     *                   RecommDAOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionNotes(String strNotes, String strLanguage) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getSolutionNotes(strNotes, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.bo.intf.RecommBOIntf#
     * recommStatusUpdate (
     * com.ge.trans.rmd.services.tools.rx.service.valueobjects.
     * AddEditRxServiceVO )
     * @ Thsi methos is used to update the status of an recommendation
     */
    @Override
    public String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException {
        String strStatus = RMDCommonConstants.EMPTY_STRING;
        try {
            strStatus = addEditRxDAOIntf.recommStatusUpdate(addeditRxServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return strStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#getTaskDetails(com
     * .ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the getTaskDetails method in RecommDAOImpl
     */
    @Override
    public void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException {
        try {
            addEditRxDAOIntf.getTaskDetails(addeditRxServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.RecommBOIntf#saveRecomm(com.ge
     * .trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO) This
     * Method is used to call the saveRecomm method in RecommDAOImpl
     */
    @Override
    public String saveRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException, SQLException {
        LOG.debug("RecommBOImpl.saveRecomm() Started");
        String strStatus = RMDCommonConstants.EMPTY_STRING;
        try {

            if (null != addeditRxServiceVO.getModelList() && (addeditRxServiceVO.getModelList().isEmpty()
                    || addeditRxServiceVO.getModelList().contains("All"))) {
                List<ElementVO> generalModels = ruleCommonDAOIntf.getModel(RMDCommonConstants.ENGLISH_LANGUAGE);

                if (null != generalModels && !generalModels.isEmpty()) {
                    List<String> lstModel = new ArrayList<String>();
                    for (ElementVO modelValue : generalModels) {
                        lstModel.add(modelValue.getId() + RMDCommonConstants.PIPELINE_CHARACTER + modelValue.getName());
                    }
                    addeditRxServiceVO.setModelList(lstModel);
                }
            }

            if (RMDServiceConstants.EDITRECOMM.equals(addeditRxServiceVO.getStrFromPage())) {
                strStatus = addEditRxDAOIntf.saveEditRecomm(addeditRxServiceVO);
            } else if (RMDServiceConstants.NEWRECOMM.equals(addeditRxServiceVO.getStrFromPage())) {

                // strStatus =
                // addEditRxDAOIntf.checkRxTitle(addeditRxServiceVO);
                if (!RMDServiceConstants.ADVISORY_TITLE_EXIST.equals(strStatus)) {
                    strStatus = addEditRxDAOIntf.saveNewRecomm(addeditRxServiceVO);
                }
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        LOG.debug("RecommBOImpl.saveRecomm() Ended");
        return strStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#fetchRecommDetails
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the fetchRecommDetails method in
     * RecommDAOImpl
     */
    @Override
    public void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException {
        try {
            addEditRxDAOIntf.fetchRecommDetails(addeditRxServiceVO);
            if (addeditRxServiceVO.isBlnLockRx()) {
                int updatedRows = addEditRxDAOIntf.lockRecommendation(addeditRxServiceVO);
                if (updatedRows == 0) {
                    addeditRxServiceVO.setBlnRecommLockedByUser(true);
                    addeditRxServiceVO.setStrLockedBy(addEditRxDAOIntf.getLockedBy(addeditRxServiceVO));
                } else {
                    addeditRxServiceVO.setStrLockedBy(addeditRxServiceVO.getStrUserName());
                }
            }
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDBOException
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */

    @Override
    public List<ElementVO> getSubsystem() throws RMDBOException {
        List<ElementVO> arlElement = null;
        try {

            arlElement = selectRxDAOIntf.getSubsystem();

        } catch (RMDDAOException e) {
            throw e;
        }

        return arlElement;

    }

    /**
     * This method used to fetch the active rule details for Rx from DAO Layer
     * 
     * @param String
     * @return List<ElementVO>
     * @throws RMDBOException
     */
    @Override
    public List<ElementVO> getRxActiveRules(String strRecomId) throws RMDBOException {
        List<ElementVO> rxRulesList = null;
        try {
            rxRulesList = addEditRxDAOIntf.fetchActiveRuleswithRX(strRecomId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxRulesList;
    }

    /**
     * This method used to fetch the active rule details for Rx from DAO Layer
     * 
     * @param String
     * @return List<ElementVO>
     */
    @Override
    public OutputStream downloadAttachment(AddEditRxServiceVO addeditRxServiceVO, String filepath, String fileName)
            throws RMDBOException {
        OutputStream os = null;
        try {
            os = addEditRxDAOIntf.downloadAttachment(addeditRxServiceVO, filepath, fileName);
        } catch (RMDDAOException e) {
            throw e;
        }
        return os;
    }
    
    @Override
    public String previewRxPdf(final AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException {
        try {
            return addEditRxDAOIntf.previewRxPdf(addeditRxServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }
    
    @Override
    public List<RxSearchResultServiceVO> getSolutionCreatedBy(String language) throws RMDBOException {
        List rxDetailsList;
        try {
            rxDetailsList = recommDAOIntf.getSolutionCreatedBy(language);
        } catch (RMDDAOException e) {
            throw e;
        }
        return rxDetailsList;
    }
}