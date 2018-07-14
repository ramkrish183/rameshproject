/**
 * ============================================================
 * File : RxExecutionDAOIntf.java
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

import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.DispatchCaseVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 26, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RxExecutionEoaDAOIntf {

    /**
     * @Author:
     * @param strRxDelvId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    RxExecTaskDetailsServiceVO getRxExecutionDetails(String strRxCaseId, String strLanguage, String customerId,boolean isMobileRequest)
            throws RMDDAOException;

    /**
     * @Author:
     * @param rxExecTaskDetailsServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveRxExecutionDetails(RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param rxExecTaskDetailsServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    String getEOAUserId(String strUserName, String strLanguage) throws RMDDAOException;

    /**
     * @param dispatchCaseVO
     * @return
     */
    String dispatchCase(DispatchCaseVO dispatchCaseVO);
    List<RxExecTaskDetailsServiceVO> getRepeaterRxsList() throws RMDDAOException;
   
	/**
	 * @Author:
	 * @param :DispatchCaseVO dispatchCaseVO
	 * @throws RMDDAOException
	 * @Description:
	 */
	String updateRxCustFdbkDetails(DispatchCaseVO dispatchCaseVO);
	public List<String> validateURL(String caseId,String fileName,String recommId)throws RMDDAOException;
	
	public void takeOwnershipForEoAUser(AcceptCaseEoaVO acceptCaseVO);
	List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String strRxCaseId)throws RMDDAOException;
}
