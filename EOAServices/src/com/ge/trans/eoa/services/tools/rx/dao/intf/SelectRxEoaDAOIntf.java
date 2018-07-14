/**
 * ============================================================
 * File : SelectRxDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.intf
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
package com.ge.trans.eoa.services.tools.rx.dao.intf;

import java.util.List;
import java.util.Map;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxSearchCriteriaEoaServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.ActionableRxTypeVO;
import com.ge.trans.rmd.cases.valueobjects.RxUrgencyParamVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RecomDelvStatusVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.common.valueobjects.RxTaskDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 19, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface SelectRxEoaDAOIntf {

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    List searchRecomm(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDDAOException;

    /**
     * This method is used for fetching RxTitles of Fleet screen as part of OMD
     * Performance
     * 
     * @param strLanguage
     * @param soltuionStatus
     * @return
     * @throws RMDDAOException
     */
    List getRxTitlesLite(String strLanguage, String soltuionStatus) throws RMDDAOException;

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    RxDetailsVO getRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO) throws RMDDAOException;

    /**
     * @param RxSearchCriteriaEoaServiceVO
     * @return List<RxTaskDetailsVO>
     * @throws RMDDAOException
     */
    public List<RxTaskDetailsVO> getRxTaskDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDDAOException;

    public List getWorstUrgency(RxUrgencyParamVO rxUrgencyParamVO) throws RMDDAOException;

    /**
     * @Author:
     * @param RxSearchCriteriaEoaServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    public List<RxDetailsVO> getListRecommDetails(RxSearchCriteriaEoaServiceVO rxSearchCriteriaServiceVO)
            throws RMDDAOException;

    /**
     * @param objActionableRxTypeVO
     * @return
     * @throws RMDDAOException
     */
    List<ActionableRxTypeVO> getActionableRxTypes(ActionableRxTypeVO objActionableRxTypeVO) throws RMDDAOException;

    /**
     * @param strCustomerId
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List<ActionableRxTypeVO> getNonActionableRxTitles(String strCustomerId, String strLanguage) throws RMDDAOException;

    /**
     * @param caseId
     * @throws RMDDAOException
     */
    public void checkRxDeliveryStatus(String caseId, String language) throws RMDDAOException;

    /**
     * @param rxCaseId
     * @param language
     * @throws RMDDAOException
     */
    public String getRxScoreStatus(String rxCaseId, String language) throws RMDDAOException;

    /**
     * @param caseId
     * @param language
     * @return
     * @throws RMDDAOException
     */
    public RecomDelvStatusVO getRxDeliveryStatus(String caseId, String language) throws RMDDAOException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List getRxLookupValues(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDDAOException
     * @Description: This method is used to get values from lookup to populate
     *               the subsystem drop downlist.
     */

    public List<ElementVO> getSubsystem() throws RMDDAOException;

    /**
     * @Author:
     * @param :RxSearchCriteriaEoaServiceVO
     *            objCriteriaEoaServiceVO
     * @return:Map<String,String>
     * @throws:RMDDAOException
     * @Description: This method is used for listing the models.
     */

    public Map<String, String> getModelsForRx(RxSearchCriteriaEoaServiceVO objCriteriaEoaServiceVO)
            throws RMDDAOException;

}
