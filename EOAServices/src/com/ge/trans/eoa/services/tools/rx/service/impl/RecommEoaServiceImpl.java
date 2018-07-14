/**
 * ============================================================
 * File : RecommServiceImpl.java
 * Description : Service Impl for Recommendation screen
 *
 * Package : com.ge.trans.rmd.services.cases.service.impl
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
package com.ge.trans.eoa.services.tools.rx.service.impl;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.tools.rx.bo.intf.RecommEoaBOIntf;
import com.ge.trans.eoa.services.tools.rx.service.intf.RecommEoaServiceIntf;
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
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call RecommBOImpl methods
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RecommEoaServiceImpl implements Serializable, RecommEoaServiceIntf {

    private static final long serialVersionUID = 1455L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RecommEoaServiceImpl.class);
    private RecommEoaBOIntf recommBOIF;

    public RecommEoaServiceImpl(final RecommEoaBOIntf recommBOIF) {
        this.recommBOIF = recommBOIF;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * searchRecomm
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the searchRecomm method in RecommBOImpl
     */
    @Override
    public List searchRecomm(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDServiceException {
        List rxDetailsList = null;
        try {
            rxDetailsList = recommBOIF.searchRecomm(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxSearchCriteriaServiceVO.getStrLanguage());
        }
        return rxDetailsList;
    }

    /**
     * This method is used for fetching RxTitles of Fleet screen as part of OMD
     * Performance
     * 
     * @param strLanguage,solutionStatus
     * @return List<RxSearchResultEoaLiteServiceVO>
     * @throws RMDServiceException
     */
    @Override
    public List<RxSearchResultEoaLiteServiceVO> getRxTitlesLite(String strLanguage, String solutionStatus)
            throws RMDServiceException {
        List<RxSearchResultEoaLiteServiceVO> rxDetailsList = null;
        try {
            rxDetailsList = recommBOIF.getRxTitlesLite(strLanguage, solutionStatus);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return rxDetailsList;
    }

    /**
     * @Description:This method is used for fetching Solution details
     * @param RxSearchCriteriaEoaServiceVO
     * @return RxDetailsVO
     * @throws RMDServiceException
     */
    @Override
    public RxDetailsVO getRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException {
        RxDetailsVO rxDetailsVO = null;
        try {
            rxDetailsVO = recommBOIF.getRecommDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxSearchCriteriaServiceVO.getStrLanguage());
        }
        return rxDetailsVO;
    }

    /**
     * @Description:This method is used for fetching Solution task details
     * @param RxSearchCriteriaEoaServiceVO
     * @return List<RxTaskDetailsVO>
     * @throws RMDServiceException
     */
    @Override
    public List<RxTaskDetailsVO> getRxTaskDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException {
        List<RxTaskDetailsVO> rxTaskDetailsList = null;
        try {
            rxTaskDetailsList = recommBOIF.getRxTaskDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxSearchCriteriaServiceVO.getStrLanguage());
        }
        return rxTaskDetailsList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.service.intf.RecommServiceIntf#
     * getWorstUrgency
     * (com.ge.trans.rmd.services.tools.rx.service.valueobjects.RxUrgencyParamVO
     * ) This Method is used to call the worstUrgency method in RecommBOImpl
     */
    @Override
    public List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO) throws RMDServiceException {
        List rsUrgencyList = null;
        try {
            rsUrgencyList = recommBOIF.getWorstUrgency(rxUrgencyParamVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxUrgencyParamVO.getStrLanguage());
        }
        return rsUrgencyList;
    }

    @Override
    public List<RxDetailsVO> getListRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException {
        List<RxDetailsVO> rxDetailsVO = null;
        try {
            rxDetailsVO = recommBOIF.getListRecommDetails(rxSearchCriteriaServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxSearchCriteriaServiceVO.getStrLanguage());
        }
        return rxDetailsVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getActionableRxTypes (strCustomerId,strLanguage) This Method is used to
     * call the getActionableRxTypes method in RecommBOImpl
     */
    @Override
    public List<ActionableRxTypeVO> getActionableRxTypes(final ActionableRxTypeVO objActionableRxTypeVO)
            throws RMDServiceException {
        List<ActionableRxTypeVO> actionableRxTypes = null;
        try {
            actionableRxTypes = recommBOIF.getActionableRxTypes(objActionableRxTypeVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objActionableRxTypeVO.getStrLanguage());
        }
        return actionableRxTypes;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getActionableRxTypes (strCustomerId,strLanguage) This Method is used to
     * call the getActionableRxTypes method in RecommBOImpl
     */
    @Override
    public List<ActionableRxTypeVO> getNonActionableRxTitles(final String strCustomerId, final String strLanguage)
            throws RMDServiceException {
        List<ActionableRxTypeVO> actionableRxTypes = null;
        try {
            actionableRxTypes = recommBOIF.getNonActionableRxTitles(strCustomerId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return actionableRxTypes;
    }

    @Override
    public void checkRxDeliveryStatus(String caseId, String language) throws RMDServiceException {

        try {

            recommBOIF.checkRxDeliveryStatus(caseId, language);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }

    }

    @Override
    public String getRxScoreStatus(String rxCaseId, String language) throws RMDServiceException {

        String caseSuccess = null;
        try {

            caseSuccess = recommBOIF.getRxScoreStatus(rxCaseId, language);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return caseSuccess;

    }

    @Override
    public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language) throws RMDServiceException {

        RecomDelvStatusVO recoDelVO = null;
        try {

            recoDelVO = recommBOIF.getRxDeliveryStatus(caseId, language);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }

        return recoDelVO;

    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rx.service.intf.RecommServiceIntf#
     * getRxLookupValues(java.lang.String, java.lang.String)
     */
    @Override
    public List getRxLookupValues(String listName, String strLanguage) throws RMDServiceException {
        List arlRxLookupValues = new ArrayList();
        try {
            arlRxLookupValues = recommBOIF.getRxLookupValues(listName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlRxLookupValues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * generateSolutionDetailsPDF (strlanguage,strUserLanguage) This Method is
     * used to call the generateSolutionDetailsPDF method in RecommBOImpl
     */
    @Override
    public String generateSolutionDetailsPDF(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        String filePath = null;
        try {
            filePath = recommBOIF.generateSolutionDetailsPDF(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
        return filePath;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getSolutionConfigurations (strlanguage,strUserLanguage) This Method is
     * used to call the getSolutionConfigurations method in RecommBOImpl
     */
    @Override
    public List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDServiceException {
        List<ElementVO> rxConfigurations = null;
        try {
            rxConfigurations = recommBOIF.getRxConfigurations(strLanguage, strUserLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strUserLanguage);
        }
        return rxConfigurations;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRxTaskdesc () This Method is used to call the getRxTaskdesc method in
     * RecommBOImpl
     */
    @Override
    public List getRxTaskdesc(String strRepairCode) throws RMDServiceException {
        List rxTaskdesc = null;
        try {
            rxTaskdesc = recommBOIF.getRxTaskdesc(strRepairCode);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxTaskdesc;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRxRepairCodes () This Method is used to call the getRxRepairCodes
     * method in RecommBOImpl
     */
    @Override
    public List getRxRepairCodes(String strObjID) throws RMDServiceException {
        List rxRepairCodes = null;
        try {
            rxRepairCodes = recommBOIF.getRxRepairCodes(strObjID);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxRepairCodes;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rx.service.intf.RecommServiceIntf#
     * getSubsystemValues(java.lang.String, java.lang.String)
     */
    @Override
    public List<ElementVO> getSubsystemValues(String listName, String strLanguage) throws RMDServiceException {
        List<ElementVO> arlRxLookupValues = new ArrayList<ElementVO>();
        try {
            arlRxLookupValues = recommBOIF.getSubsystemValues(listName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlRxLookupValues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRecomLastUpdatedBy(strLanguage) This Method is used to call the
     * getRecomLastUpdatedBy method in RecommBOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String strLanguage) throws RMDServiceException {
        List rxlastUpdatedBy = null;
        try {
            rxlastUpdatedBy = recommBOIF.getSolutionLastUpdatedBy(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxlastUpdatedBy;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRecomTitles (strTitle,strLanguage) This Method is used to call the
     * getRecomTitles method in RecommBOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage)
            throws RMDServiceException {
        List rxlastUpdatedBy = null;
        try {
            rxlastUpdatedBy = recommBOIF.getSolutionTitles(strTitle, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxlastUpdatedBy;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRecomNotes (strNotes,strLanguage) This Method is used to call the
     * rxNotes method in RecommBOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionNotes(String strNotes, String strLanguage)
            throws RMDServiceException {
        List rxNotes = null;
        try {
            rxNotes = recommBOIF.getSolutionNotes(strNotes, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxNotes;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rx.service.intf.RecommServiceIntf#
     * recommStatusUpdate
     * (com.ge.trans.rmd.services.tools.rx.service.valueobjects
     * .AddEditRxServiceVO)
     * @Description: This method is used to updated the status of recommendation
     */
    @Override
    public String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        String strStatus = RMDCommonConstants.EMPTY_STRING;
        try {
            strStatus = recommBOIF.recommStatusUpdate(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
        return strStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getTaskDetails
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the getTaskDetails method in RecommBOImpl
     */
    @Override
    public void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        try {
            recommBOIF.getTaskDetails(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#saveRecomm
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the saveRecomm method in RecommBOImpl
     */
    @Override
    public String saveRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        String strStatus = RMDCommonConstants.EMPTY_STRING;
        try {
            strStatus = recommBOIF.saveRecomm(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
        return strStatus;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * fetchRecommDetails
     * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
     * This Method is used to call the fetchRecommDetails method in RecommBOImpl
     */
    @Override
    public void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        try {
            recommBOIF.fetchRecommDetails(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
    }

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */
    @Override
    public List<ElementVO> getSubsystem() throws RMDServiceException {
        List<ElementVO> arlElement = null;
        try {

            arlElement = recommBOIF.getSubsystem();

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return arlElement;

    }

    /**
     * This method used to invoke business layer to get active rule details for
     * Rx
     * 
     * @param String
     * @return List<ElementVO>
     * @throws RMDServiceException
     */
    @Override
    public List<ElementVO> getRxActiveRules(String strRecomId) throws RMDServiceException {
        List<ElementVO> rxRuleList = null;
        try {
            rxRuleList = recommBOIF.getRxActiveRules(strRecomId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxRuleList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * generateSolutionDetailsPDF (strlanguage,strUserLanguage) This Method is
     * used to call the generateSolutionDetailsPDF method in RecommBOImpl
     */
    @Override
    public OutputStream downloadAttachment(AddEditRxServiceVO addeditRxServiceVO, String filepath, String fileName)
            throws RMDServiceException {
        OutputStream os = null;
        try {
            os = recommBOIF.downloadAttachment(addeditRxServiceVO, filepath, fileName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
        return os;

    }
    
    @Override
    public String previewRxPdf(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException {
        String filePath = null;
        try {
            filePath = recommBOIF.previewRxPdf(addeditRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addeditRxServiceVO.getStrLanguage());
        }
        return filePath;
    }
    
    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.RecommServiceIntf#
     * getRecomLastUpdatedBy(strLanguage) This Method is used to call the
     * getRecomLastUpdatedBy method in RecommBOImpl
     */
    @Override
    public List<RxSearchResultServiceVO> getSolutionCreatedBy(String strLanguage) throws RMDServiceException {
        List rxCreatedBy = null;
        try {
            rxCreatedBy = recommBOIF.getSolutionCreatedBy(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxCreatedBy;
    }
    
    
    


}