/**
 * ============================================================
 * File : RecommBOIntf.java
 * Description : BO Interface for Recommendation screen
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
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
package com.ge.trans.eoa.services.tools.rx.bo.intf;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.ActionableRxTypeVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultEoaLiteServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RecomDelvStatusVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Interface for Recommendation screen
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RecommEoaBOIntf {

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @return List
     * @throws RMDBOException
     */
    List searchRecomm(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDBOException;

    /**
     * This method is used for fetching RxTitles of Fleet screen as part of OMD
     * Performance
     * 
     * @param strLanguage
     * @param soltuionStatus
     * @return
     * @throws RMDBOException
     */
    List<RxSearchResultEoaLiteServiceVO> getRxTitlesLite(String strLanguage, String soltuionStatus)
            throws RMDBOException;

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @return void
     * @throws RMDBOException
     */
    RxDetailsVO getRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDBOException;

    /**
     * @param RxSearchCriteriaEoaServiceVO
     * @return List<RxTaskDetailsVO>
     * @throws RMDBOException
     */
    public List<RxTaskDetailsVO> getRxTaskDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDBOException;

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @return void
     * @throws RMDBOException
     */
    public List<RxDetailsVO> getListRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDBOException;

    /**
     * @param rxUrgencyParamVO
     * @return
     * @throws RMDBOException
     */
    List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO) throws RMDBOException;

    /**
     * @param objActionableRxTypeVO
     * @return
     * @throws RMDBOException
     */
    List<ActionableRxTypeVO> getActionableRxTypes(final ActionableRxTypeVO objActionableRxTypeVO) throws RMDBOException;

    /**
     * @param strCustomerId
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    List<ActionableRxTypeVO> getNonActionableRxTitles(String strCustomerId, String strLanguage) throws RMDBOException;

    /**
     * @param caseId
     * @throws RMDBOException
     */
    public void checkRxDeliveryStatus(String caseId, String language) throws RMDBOException;

    /**
     * @param rxCaseId
     * @param language
     * @throws RMDBOException
     */
    public String getRxScoreStatus(String rxCaseId, String language) throws RMDBOException;

    /**
     * @param caseId
     * @param language
     * @return
     * @throws RMDBOException
     */
    public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language) throws RMDBOException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    List getRxLookupValues(String strListName, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage
     * @return List
     * @throws RMDBOException
     */
    List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLastUpdatedBy
     * @return List
     * @throws RMDBOException
     */
    List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strLastUpdatedBy
     * @return List
     * @throws RMDBOException
     */
    List<RxSearchResultServiceVO> getSolutionNotes(String strNotes, String strLanguage) throws RMDBOException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    List<ElementVO> getSubsystemValues(String strListName, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param getRxRepairCodes
     * @return List
     * @throws RMDBOException
     */
    List getRxRepairCodes(String strObjID) throws RMDBOException;

    /**
     * @Author:
     * @param getRxTaskdesc
     * @return List
     * @throws RMDBOException
     */
    List getRxTaskdesc(String strRepairCode) throws RMDBOException;

    /**
     * @param
     * @param
     * @return
     * @throws RMDBOException
     */
    List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDBOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDBOException
     */
    String generateSolutionDetailsPDF(final AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDBOException
     */
    String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @return String
     * @throws RMDBOException
     */
    String saveRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException, SQLException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDBOException
     */
    void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDBOException
     */
    void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDBOException
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */

    public List<ElementVO> getSubsystem() throws RMDBOException;

    /**
     * This method used to fetch the active rule details for Rx from DAO Layer
     * 
     * @param String
     * @return List<ElementVO>
     * @throws RMDBOException
     */
    public List<ElementVO> getRxActiveRules(String strRecomId) throws RMDBOException;

    public OutputStream downloadAttachment(AddEditRxServiceVO addeditRxServiceVO, String filepath, String fileName)
            throws RMDBOException;
    
    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDBOException
     */
    String previewRxPdf(final AddEditRxServiceVO addeditRxServiceVO) throws RMDBOException;
    /**
     * @Author:
     * @param strLanguage
     * @return List
     * @throws RMDBOException
     */
    List<RxSearchResultServiceVO> getSolutionCreatedBy(String strLanguage) throws RMDBOException;
}