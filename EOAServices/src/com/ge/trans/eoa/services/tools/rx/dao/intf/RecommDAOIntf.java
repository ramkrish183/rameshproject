/**
 * ============================================================
 * File : RecommDAOIntf.java
 * Description : DAO Interface for Recommendation Screen
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.intf;
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

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseCreateServiceVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxSearchResultServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : DAO Interface for Recommendation Screen
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RecommDAOIntf {

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void fetchAssetImpactList(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void fetchRxStatusList(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void fetchRxTypeList(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @param strLanguage
     * @return List
     * @throws RMDDAOException
     */
    List fetchUrgRepair(String strLanguage) throws RMDDAOException;

    /**
     * @param strLanguage
     * @return List
     * @throws RMDDAOException
     */
    List fetchEstmTimeRepair(String strLanguage) throws RMDDAOException;

    /**
     * @param objCaseCreateServiceVO
     * @return List
     * @throws RMDDAOException
     */
    List<String> getValidAssetNumber(CaseCreateServiceVO objCaseCreateServiceVO) throws RMDDAOException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List getRxLookupValues(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strLanguage
     * @throws RMDDAOException
     * @Description:
     */
    List<RxSearchResultServiceVO> getSolutionLastUpdatedBy(String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strTitle
     *            ,strLanguage
     * @throws RMDDAOException
     * @Description:
     */
    List<RxSearchResultServiceVO> getSolutionTitles(String strTitle, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strNotes
     *            ,strLanguage
     * @throws RMDDAOException
     * @Description:
     */
    List<RxSearchResultServiceVO> getSolutionNotes(String strNotes, String strLanguage) throws RMDDAOException;

    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List<ElementVO> getSubsystemValues(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @throws RMDDAOException
     * @Description:
     */
    List getRxRepairCodes(String strObjID) throws RMDDAOException;

    /**
     * @Author:
     * @throws RMDDAOException
     * @Description:
     */
    List getRxTaskdesc(String strRepairCode) throws RMDDAOException;

    /**
     * @param strLanguage
     * @param strUserLanguage
     * @return
     * @throws RMDBOException
     */
    List<ElementVO> getRxConfigurations(String strLanguage, String strUserLanguage) throws RMDDAOException;
    /**
     * @Author:
     * @param strLanguage
     * @throws RMDDAOException
     * @Description:
     */
    List<RxSearchResultServiceVO> getSolutionCreatedBy(String strLanguage) throws RMDDAOException;
    

}
