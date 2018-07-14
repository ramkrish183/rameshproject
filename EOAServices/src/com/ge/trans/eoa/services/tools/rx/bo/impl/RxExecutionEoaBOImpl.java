/**
 * ============================================================
 * File : RxExecutionBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.bo.impl;
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
package com.ge.trans.eoa.services.tools.rx.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.rx.bo.intf.RxExecutionEoaBOIntf;
import com.ge.trans.eoa.services.tools.rx.dao.intf.RxExecutionEoaDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.DispatchCaseVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

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
public class RxExecutionEoaBOImpl implements RxExecutionEoaBOIntf {

    private RMDLogger LOG = RMDLoggerHelper.getLogger(this.getClass());
    private RxExecutionEoaDAOIntf objRxExecutionDAOIntf;

    /**
     * @param objRxExecutionDAOIntf
     * @param caseAPIDAO
     */
    public RxExecutionEoaBOImpl(RxExecutionEoaDAOIntf objRxExecutionDAOIntf) {
        super();
        this.objRxExecutionDAOIntf = objRxExecutionDAOIntf;

    }

    /**
     * @return the objRxExecutionDAOIntf
     */
    public RxExecutionEoaDAOIntf getObjRxExecutionDAOIntf() {
        return objRxExecutionDAOIntf;
    }

    /**
     * @param objRxExecutionDAOIntf
     *            the objRxExecutionDAOIntf to set
     */
    public void setObjRxExecutionDAOIntf(RxExecutionEoaDAOIntf objRxExecutionDAOIntf) {
        this.objRxExecutionDAOIntf = objRxExecutionDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.bo.intf.RxExecutionBOIntf#
     * getRxExecutionDetails(java.lang.String)
     */
    @Override
    public RxExecTaskDetailsServiceVO getRxExecutionDetails(String strRxCaseId, String strLanguage, String customerId,boolean isMobileRequest)
            throws RMDBOException {
        RxExecTaskDetailsServiceVO resultVO;
        try {
            resultVO = objRxExecutionDAOIntf.getRxExecutionDetails(strRxCaseId, strLanguage, customerId,isMobileRequest);
        } catch (RMDDAOException e) {
            throw e;
        }
        return resultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rx.bo.intf.RxExecutionBOIntf
     * #saveRxExecutionDetails(com.ge.trans.rmd.services.tools.rx.service.
     * valueobjects.RxExecTaskDetailsServiceVO)
     */
    @Override
    public int saveRxExecutionDetails(RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO) throws RMDBOException {
        int result = RMDCommonConstants.INSERTION_FAILURE;
        try {
            String eoaUserId = objRxExecutionDAOIntf.getEOAUserId(rxExecTaskDetailsServiceVO.getStrUserName(),
                    rxExecTaskDetailsServiceVO.getStrLanguage());
            if (null == eoaUserId || eoaUserId.isEmpty()) {
            	rxExecTaskDetailsServiceVO.setEoaUserId(RMDCommonConstants.SCORE_YES.
            			equals(rxExecTaskDetailsServiceVO.getEndUserScoring())?RMDCommonConstants.EXT_RX_SCORE:RMDCommonConstants.EXT_RX_CLOSE);
            } else {
            	rxExecTaskDetailsServiceVO.setEoaUserId(eoaUserId);
            }
            result = objRxExecutionDAOIntf.saveRxExecutionDetails(rxExecTaskDetailsServiceVO);
                       
            if (result == RMDCommonConstants.INSERTION_SUCCESS) {
            	DispatchCaseVO objDispatchCaseVO = new DispatchCaseVO();
                objDispatchCaseVO.setStrCaseId(rxExecTaskDetailsServiceVO.getStrCaseId());
                objDispatchCaseVO.setStrQueueName(RMDServiceConstants.WORK_QUEUE);
                objDispatchCaseVO.setStrUserName(rxExecTaskDetailsServiceVO.getStrUserName());
                objDispatchCaseVO.setStrLanguage(rxExecTaskDetailsServiceVO.getStrLanguage());
                objDispatchCaseVO.setRepairAction(rxExecTaskDetailsServiceVO.getStrRepairAction());
                objDispatchCaseVO.setRxCaseID(rxExecTaskDetailsServiceVO.getStrRxCaseId());
                objDispatchCaseVO.setEoaUserId(rxExecTaskDetailsServiceVO.getEoaUserId());
                objDispatchCaseVO.setStrFirstName(rxExecTaskDetailsServiceVO.getStrFirstName());
                objDispatchCaseVO.setStrLastName(rxExecTaskDetailsServiceVO.getStrLastName());
                // Added for Add location for RX_EX Screen implementation:start
                objDispatchCaseVO.setLocationId(rxExecTaskDetailsServiceVO.getLocationId());
                // Added for Add location for RX_EX Screen implementation:end
                
                /*Changed for Save Rx Requirement Changes by Vamshi*/
                if(RMDCommonConstants.YES.equals(rxExecTaskDetailsServiceVO.getStrRxCloseFlag())){
                	/*if(eoauser){
                    	acceptCaseVO=new AcceptCaseEoaVO();
                    	acceptCaseVO.setStrCaseId(rxExecTaskDetailsServiceVO.getStrCaseId());
                    	acceptCaseVO.setStrUserName(rxExecTaskDetailsServiceVO.getStrUserName());
                    	objRxExecutionDAOIntf.takeOwnershipForEoAUser(acceptCaseVO);
                    	}*/
                	objRxExecutionDAOIntf.dispatchCase(objDispatchCaseVO);
                	
                }else{
                	objRxExecutionDAOIntf.updateRxCustFdbkDetails(objDispatchCaseVO);
                }
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }
    @Override
	public List<RxExecTaskDetailsServiceVO> getRepeaterRxsList()
			throws RMDBOException {
		List<RxExecTaskDetailsServiceVO> objRxExecTaskDetailsServiceVOList = null;
	        try {
	        	objRxExecTaskDetailsServiceVOList = objRxExecutionDAOIntf.getRepeaterRxsList();
	        } catch (RMDDAOException e) {
	            LOG.error("Unexpected Error occured in RxExecutionEoaBOImpl getRepeaterRxsList()", e);
	            throw new RMDBOException(e.getErrorDetail(), e);
	        }
	        return objRxExecTaskDetailsServiceVOList;
	}

	@Override
	public List<String> validateURL(String caseId, String fileName,String recommId)
			throws RMDBOException {
		List<String> objRxExecTaskServiceVO = null;
        try {
        	objRxExecTaskServiceVO = objRxExecutionDAOIntf.validateURL(caseId,fileName,recommId);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in RxExecutionEoaBOImpl getRepeaterRxsList()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return objRxExecTaskServiceVO;
	}

	public List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String caseObjid)
			throws RMDBOException {
		List<RxDeliveryAttachmentVO>  arlRxDeliveryAttachmentVO =null;
        try {
        	arlRxDeliveryAttachmentVO = objRxExecutionDAOIntf.getRxDeliveryAttachments(caseObjid);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in RxExecutionEoaDAOImpl getRxDeliveryAttachments()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return arlRxDeliveryAttachmentVO;
	}
}
