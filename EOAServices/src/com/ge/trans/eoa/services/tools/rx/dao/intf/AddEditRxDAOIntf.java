/**
 * ============================================================
 * File : AddEditRxDAOIntf.java
 * Description : DAO Interface for Recommendation Screen
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

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;

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
public interface AddEditRxDAOIntf {

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String saveNewRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException, SQLException;

    /**
     * @Author:
     * @param addeditRxServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String saveEditRecomm(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException, SQLException;

    /**
     * @param addeditRxServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String checkRxTitle(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @throws RMDDAOException
     */
    void deActivateRx(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    int lockRecommendation(AddEditRxServiceVO objAddeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    String getLockedBy(AddEditRxServiceVO objAddeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    void releseLockOnRecommendation(AddEditRxServiceVO objAddeditRxServiceVO) throws RMDDAOException;

    /**
     * @param addeditRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    String generateSolutionDetailsPDF(final AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;

    /**
     * This method used to fetch active rules associated with Rx from database
     * 
     * @param String
     * @return List<ElementVO>
     * @throws RMDDAOException
     */
    public List<ElementVO> fetchActiveRuleswithRX(String recomId) throws RMDDAOException;

    public OutputStream downloadAttachment(AddEditRxServiceVO addeditRxServiceVO, String filepath, String fileName)
            throws RMDDAOException;
    
    String previewRxPdf(final AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException;
}