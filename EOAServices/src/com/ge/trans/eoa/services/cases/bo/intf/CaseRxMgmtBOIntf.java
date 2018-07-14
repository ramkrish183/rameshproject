/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseRxMgmtBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 11,2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 15, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface CaseRxMgmtBOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    void addRXToCase(RecomDelvInfoServiceVO caseRXVO) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDBOException;

}
