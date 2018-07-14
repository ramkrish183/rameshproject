/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerDAOIntf.java
 * Description : 
 * Package : com.ge.trans.rmd.services.kep.knowledgeseeker.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.dao.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;
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
 * @Description : This Class act as dao intf for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
public interface KnowledgeSeekerDAOIntf {

    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory) throws RMDDAOException;

    /*
     * public List<TrackingVO> getTrackingIDs(String strTrackingID) throws
     * RMDDAOException;
     */

    public List<TrackingVO> getCreators(String strCreatedBy) throws RMDDAOException;

    public List<TrackingVO> getRunNames(String strRunName) throws RMDDAOException;

    public List<TrackingVO> getKnowledgeSeekerTrackings(TrackingVO objTrackingVO) throws RMDDAOException;

    public List getTrackingDetails(String strTrackingID) throws RMDDAOException;

    public Integer createKnowledgeSeekerRequest(KSRequestVO ksRequestVO) throws RMDDAOException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strlanguage
     * @return list of BaseVO
     * @throws RMDDAOException
     */

    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * This method is used for loading the Symptoms
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */

    public List getSymptom(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDDAOException;

    /**
     * This method is used for loading the RootCause
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    public List getRootCause(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDDAOException;

    /**
     * This method is used for loading the Rx
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    public List getRx(String strModel, String strCustomer, String strFleet, String strFromDate, String strToDate,
            String rxId) throws RMDDAOException;

}
