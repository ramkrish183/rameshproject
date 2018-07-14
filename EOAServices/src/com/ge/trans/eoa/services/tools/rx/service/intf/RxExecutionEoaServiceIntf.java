/**
 * ============================================================
 * File : RxExecutionServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.intf
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

import java.util.List;

import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 28, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RxExecutionEoaServiceIntf {

    /**
     * @Author:
     * @param strRxDelvId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    RxExecTaskDetailsServiceVO getRxExecutionDetails(String strRxCaseId, String strLanguage, String customerId,boolean isMobileRequest)
            throws RMDServiceException;

    /**
     * @Author:
     * @param strRxDelvId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveRxExecutionDetails(RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO) throws RMDServiceException;
    List<RxExecTaskDetailsServiceVO> getRepeaterRxsList()throws RMDServiceException;
    public List<String> validateURL(String caseId,String fileName,String recommId)throws RMDServiceException;
    List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String caseObjid)throws RMDServiceException;

}
