/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddRxDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;

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
public interface CaseRxMgmtDAOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    void addRXTocase(RecomDelvInfoServiceVO caseRXVO) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDDAOException;

}
