/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerServiceImpl.java
 * Description : 
 * Package : com.ge.trans.rmd.services.kep.knowledgeseeker.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.service.impl;

import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.kep.knowledgeseeker.bo.intf.KnowledgeSeekerBOIntf;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.intf.KnowledgeSeekerServiceIntf;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.BaseVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.KSRequestVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.TrackingVO;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: November 2, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Service for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
public class KnowledgeSeekerServiceImpl implements KnowledgeSeekerServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KnowledgeSeekerServiceImpl.class);
    private KnowledgeSeekerBOIntf knowledgeSeekerBOIntf;

    public KnowledgeSeekerServiceImpl(final KnowledgeSeekerBOIntf knowledgeSeekerBOIntf) {
        this.knowledgeSeekerBOIntf = knowledgeSeekerBOIntf;
    }

    /*
     * @Override public List<TrackingVO> getTrackingIDs(String strTrackingID)
     * throws RMDServiceException { List<TrackingVO> arlTrackingID = null; try {
     * arlTrackingID = knowledgeSeekerBOIntf.getTrackingIDs(strTrackingID); }
     * catch (RMDDAOException ex) { throw new
     * RMDServiceException(ex.getErrorDetail(), ex); } catch (RMDBOException ex)
     * { throw new RMDServiceException(ex.getErrorDetail(), ex); } catch
     * (Exception ex) { RMDServiceErrorHandler.handleGeneralException(ex,
     * RMDCommonConstants.ENGLISH_LANGUAGE); } return arlTrackingID; }
     */

    @Override
    public List<TrackingVO> getCreators(final String strCreatedBy) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<TrackingVO> arlCreatedBY = null;
        try {
            arlCreatedBY = knowledgeSeekerBOIntf.getCreators(strCreatedBy);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlCreatedBY;
    }

    @Override
    public List<TrackingVO> getRunNames(final String strRunName) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<TrackingVO> arlRunName = null;
        try {
            arlRunName = knowledgeSeekerBOIntf.getRunNames(strRunName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRunName;
    }

    @Override
    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory)
            throws RMDServiceException {
        List<BaseVO> arlData = null;
        try {
            arlData = knowledgeSeekerBOIntf.getPatternDetails(strPatternSeqId, strPatternCategory);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlData;
    }

    @Override
    public List<TrackingVO> getKnowledgeSeekerTrackings(final TrackingVO objTrackingVO) throws RMDServiceException {
        List<TrackingVO> arlsearchTrackingDetails = null;
        try {
            arlsearchTrackingDetails = knowledgeSeekerBOIntf.getKnowledgeSeekerTrackings(objTrackingVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlsearchTrackingDetails;
    }

    @Override
    public List getTrackingDetails(final String strTrackingID) throws RMDServiceException {
        List lstTrackingSummary = null;
        try {
            lstTrackingSummary = knowledgeSeekerBOIntf.getTrackingDetails(strTrackingID);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return lstTrackingSummary;
    }

    @Override
    public Integer createKnowledgeSeekerRequest(KSRequestVO objKSRequestVO) throws RMDServiceException {
        Integer trackingId = 0;
        try {
            trackingId = knowledgeSeekerBOIntf.createKnowledgeSeekerRequest(objKSRequestVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return trackingId;
    }

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of ElementVO
     * @throws KEPServiceException
     */
    @Override
    public List<ElementVO> getLookUPDetails(final String strListName, final String strLanguage)
            throws RMDServiceException {
        // TODO Auto-generated method stub
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = knowledgeSeekerBOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlStatus;
    }

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    @Override
    public List getSymptom(final String strCustomer, String strModel, String strFleet, final String strFromDate,
            final String strToDate) throws RMDServiceException {
        List<ElementVO> arlSymptom = null;
        try {
            arlSymptom = knowledgeSeekerBOIntf.getSymptom(strCustomer, strModel, strFleet, strFromDate, strToDate);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlSymptom;

    }

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    @Override
    public List getRootCause(final String strCustomer, String strModel, String strFleet, String strFromDate,
            String strToDate) throws RMDServiceException {

        List<ElementVO> arlRootCause = null;

        try {
            arlRootCause = knowledgeSeekerBOIntf.getRootCause(strCustomer, strModel, strFleet, strFromDate, strToDate);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRootCause;

    }

    /**
     * This method is used for fetching the rx from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    @Override
    public List getRx(final String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDServiceException {

        List<ElementVO> arlRx = null;

        try {
            arlRx = knowledgeSeekerBOIntf.getRx(strModel, strCustomer, strFleet, strFromDate, strToDate);// knowledgeSeekerBOIntf.getRx(strCustomer,strModel,strFromDate,
                                                                                                         // strToDate);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRx;

    }

}
