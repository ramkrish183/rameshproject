/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerServiceIntf.java
 * Description : 
 * Package : com.ge.trans.rmd.services.kep.knowledgeseeker.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.BaseVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.KSRequestVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.TrackingVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: November 2, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as Service intf for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
public interface KnowledgeSeekerServiceIntf {

    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory) throws RMDServiceException;

    /*
     * public List<TrackingVO> getTrackingIDs(String strTrackingID) throws
     * RMDServiceException;
     */

    public List<TrackingVO> getCreators(String strCreatedBy) throws RMDServiceException;

    public List<TrackingVO> getRunNames(String strRunName) throws RMDServiceException;

    public List<TrackingVO> getKnowledgeSeekerTrackings(TrackingVO objTrackingVO) throws RMDServiceException;

    public List getTrackingDetails(String strTrackingID) throws RMDServiceException;

    public Integer createKnowledgeSeekerRequest(KSRequestVO objKSRequestVO) throws RMDServiceException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of BaseVO
     * @throws RMDServiceException
     */
    public List<ElementVO> getLookUPDetails(String listName, String strLanguage) throws RMDServiceException;

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    public List getSymptom(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDServiceException;

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    public List getRootCause(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDServiceException;

    /**
     * This method is used for fetching the rx from DB
     * 
     * @param
     * @return list
     * @throws KEPServiceException
     */
    public List getRx(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDServiceException;

}
