/**
 * ============================================================
 * File : RecommServiceIntf.java
 * Description : Service Interface for Recommendation screen
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
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
package com.ge.trans.eoa.services.tools.rx.service.intf;

import java.io.OutputStream;
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
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RecommEoaServiceIntf {

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @return List
     * @throws RMDServiceException
     */
    List searchRecomm(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDServiceException;

    /**
     * @param strLanguage
     * @param strUserLanguage
     * @return
     * @throws RMDServiceException
     */
    List<RxSearchResultEoaLiteServiceVO> getRxTitlesLite(String strLanguage, String strUserLanguage)
            throws RMDServiceException;;

    /**
     * @param RxSearchCriteriaEoaServiceVO
     * @return void
     * @throws RMDServiceException
     */
    public RxDetailsVO getRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @param RxSearchCriteriaEoaServiceVO
     * @return List<RxTaskDetailsVO>
     * @throws RMDServiceException
     */
    public List<RxTaskDetailsVO> getRxTaskDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException;

    List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO) throws RMDServiceException;

    /**
     * @param RxSearchCriteriaEoaServiceVO
     * @return void
     * @throws RMDServiceException
     */
    public List<RxDetailsVO> getListRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @param objActionableRxTypeVO
     * @return
     * @throws RMDServiceException
     */
    List<ActionableRxTypeVO> getActionableRxTypes(ActionableRxTypeVO objActionableRxTypeVO) throws RMDServiceException;

    /**
     * @param strCustomerId
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<ActionableRxTypeVO> getNonActionableRxTitles(String strCustomerId, String strLanguage)
            throws RMDServiceException;

    /**
     * @param caseId
     * @throws RMDServiceException
     */
    public void checkRxDeliveryStatus(String caseId, String language) throws RMDServiceException;

    /**
     * @param rxCaseId
     * @param language
     * @throws RMDServiceException
     */
    public String getRxScoreStatus(String rxCaseId, String language) throws RMDServiceException;

    public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language) throws RMDServiceException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List getRxLookupValues(String strListName, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @return String
     * @throws RMDServiceException
     */
    String saveRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;

    /**
     * @param
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String strLanguage) throws RMDServiceException;

    /**
     * @param strTitle
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage) throws RMDServiceException;

    /**
     * @param strNotes
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<RxSearchResultServiceVO> getSolutionNotes(String strNotes, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDServiceException
     */
    void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDServiceException
     */
    void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDServiceException
     */
    String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<ElementVO> getSubsystemValues(String strListName, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return List
     * @throws RMDServiceException
     */
    List getRxRepairCodes(String strObjID) throws RMDServiceException;

    /**
     * @Author:
     * @return List
     * @throws RMDServiceException
     */
    List getRxTaskdesc(String strRepairCode) throws RMDServiceException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDServiceException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDServiceException
     */
    String generateSolutionDetailsPDF(final AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */

    public List<ElementVO> getSubsystem() throws RMDServiceException;

    /**
     * This method used to invoke business layer to get active rule details for
     * Rx
     * 
     * @param String
     * @return List<ElementVO>
     * @throws RMDServiceException
     */
    public List<ElementVO> getRxActiveRules(String strRecomId) throws RMDServiceException;

    OutputStream downloadAttachment(AddEditRxServiceVO objAddEditRxServiceVO, String filepath, String fileName)
            throws RMDServiceException;
    
    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDServiceException
     */
    String previewRxPdf(final AddEditRxServiceVO addeditRxServiceVO) throws RMDServiceException;
    /**
     * @param
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    List<RxSearchResultServiceVO> getSolutionCreatedBy(String strLanguage) throws RMDServiceException;
    
}