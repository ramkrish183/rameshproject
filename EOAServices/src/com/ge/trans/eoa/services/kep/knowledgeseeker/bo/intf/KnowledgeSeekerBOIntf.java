/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerBOIntf.java
 * Description : 
 * Package : com.ge.trans.rmd.services.kep.knowledgeseeker.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.bo.intf;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: November 2, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as intf BO for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
import java.util.List;

import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.BaseVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.KSRequestVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.TrackingVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface KnowledgeSeekerBOIntf {

    // public List getAnalysisTable()throws KEPBOException;

    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory) throws RMDBOException;

    /*
     * public List<TrackingVO> getTrackingIDs(String strTrackingID) throws
     * RMDBOException;
     */

    public List<TrackingVO> getCreators(String strCreatedBy) throws RMDBOException;

    public List<TrackingVO> getRunNames(String strRunName) throws RMDBOException;

    public List<TrackingVO> getKnowledgeSeekerTrackings(TrackingVO objTrackingVO) throws RMDBOException;

    public List getTrackingDetails(String strTrackingID) throws RMDBOException;

    public Integer createKnowledgeSeekerRequest(KSRequestVO ksRequestVO) throws RMDBOException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strlanguage
     * @return list of BaseVO
     * @throws KEPBOException
     */

    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDBOException;

    /**
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */

    public List getSymptom(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException;

    /**
     * This method is used for fetching the root cause from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    public List getRootCause(String strCustomer, String strModel, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException;

    /**
     * This method is used for fetching the rx from DB
     * 
     * @param
     * @return list
     * @throws RMDBOException
     */
    public List getRx(String strModel, String strCustomer, String strFleet, String strFromDate, String strToDate)
            throws RMDBOException;

}
