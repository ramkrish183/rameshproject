/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerBOImpl.java
 * Description : 
 * Package : com.ge.trans.rmd.services.kep.knowledgeseeker.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.kep.knowledgeseeker.bo.intf.KnowledgeSeekerBOIntf;
import com.ge.trans.eoa.services.kep.knowledgeseeker.dao.intf.KnowledgeSeekerDAOIntf;
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
 * @Description : This Class act as impl BO for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
public class KnowledgeSeekerBOImpl implements KnowledgeSeekerBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(KnowledgeSeekerBOImpl.class);
    private KnowledgeSeekerDAOIntf knowledgeSeekerDAOIntf;

    public KnowledgeSeekerBOImpl(final KnowledgeSeekerDAOIntf knowledgeSeekerDAOIntf) {
        this.knowledgeSeekerDAOIntf = knowledgeSeekerDAOIntf;
    }

    @Override
    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory) throws RMDBOException {
        List<BaseVO> arlData = null;
        try {
            arlData = knowledgeSeekerDAOIntf.getPatternDetails(strPatternSeqId, strPatternCategory);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arlData;
    }

    /*
     * public List<TrackingVO> getTrackingIDs(String strTrackingID) throws
     * RMDBOException { List<TrackingVO> arlTrackingID = null; try {
     * arlTrackingID = knowledgeSeekerDAOIntf .getTrackingIDs(strTrackingID); }
     * catch (RMDDAOException e) { throw e; } return arlTrackingID; }
     */
    @Override
    public List<TrackingVO> getCreators(String strCreatedBy) throws RMDBOException {
        List<TrackingVO> arlcreatedBY = null;
        try {
            arlcreatedBY = knowledgeSeekerDAOIntf.getCreators(strCreatedBy);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return arlcreatedBY;
    }

    @Override
    public List<TrackingVO> getRunNames(final String strRunName) throws RMDBOException {
        List<TrackingVO> arlrunname = null;
        try {
            arlrunname = knowledgeSeekerDAOIntf.getRunNames(strRunName);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlrunname;
    }

    @Override
    public List<TrackingVO> getKnowledgeSeekerTrackings(final TrackingVO objTrackingVO) throws RMDBOException {
        List<TrackingVO> arlTrackingdetails = null;
        try {
            arlTrackingdetails = knowledgeSeekerDAOIntf.getKnowledgeSeekerTrackings(objTrackingVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlTrackingdetails;
    }

    @Override
    public List getTrackingDetails(final String strTrackingID) throws RMDBOException {
        List lstTrackingSummary = null;
        try {
            lstTrackingSummary = knowledgeSeekerDAOIntf.getTrackingDetails(strTrackingID);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstTrackingSummary;
    }

    @Override
    public Integer createKnowledgeSeekerRequest(KSRequestVO ksRequestVO) throws RMDBOException {
        try {
            return knowledgeSeekerDAOIntf.createKnowledgeSeekerRequest(ksRequestVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<ElementVO> getLookUPDetails(final String strListName, String strLanguage) throws RMDBOException {
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = knowledgeSeekerDAOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlStatus;
    }

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    @Override
    public List getSymptom(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException {
        List<ElementVO> arlSymptom = null;
        try {
            arlSymptom = knowledgeSeekerDAOIntf.getSymptom(strCustomer, strModel, strFleet, strFromDate, strToDate);
        } catch (RMDDAOException e) {
            throw e;
        }

        return arlSymptom;

    }

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    @Override
    public List getRootCause(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException {
        List<ElementVO> arlRootCause = null;
        try {
            arlRootCause = knowledgeSeekerDAOIntf.getRootCause(strCustomer, strModel, strFleet, strFromDate, strToDate);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRootCause;

    }

    /**
     * This method is used for fetching the rx from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    @Override
    public List getRx(String strModel, String strCustomer, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException {
        List<ElementVO> arlRx = null;
        try {
            arlRx = knowledgeSeekerDAOIntf.getRx(strModel, strCustomer, strFleet, strFromDate, strToDate, null);
        } catch (RMDDAOException e) {
            throw e;
        }

        return arlRx;

    }

}
