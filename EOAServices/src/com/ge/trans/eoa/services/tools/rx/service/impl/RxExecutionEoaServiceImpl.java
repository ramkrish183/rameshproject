/**
 * ============================================================
 * File : RxExecutionServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.impl
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
package com.ge.trans.eoa.services.tools.rx.service.impl;

import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.tools.rx.bo.intf.RxExecutionEoaBOIntf;
import com.ge.trans.eoa.services.tools.rx.service.intf.RxExecutionEoaServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxDeliveryAttachmentVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxExecTaskDetailsServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
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
public class RxExecutionEoaServiceImpl implements RxExecutionEoaServiceIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RxExecutionEoaServiceImpl.class);
    private RxExecutionEoaBOIntf objRxExecutionEoaBOIntf;

    public RxExecutionEoaServiceImpl(final RxExecutionEoaBOIntf objRxExecutionBOIntf) {
        this.objRxExecutionEoaBOIntf = objRxExecutionBOIntf;
    }

    /**
     * @return the objRxExecutionBOIntf
     */
    public RxExecutionEoaBOIntf getObjRxExecutionBOIntf() {
        return objRxExecutionEoaBOIntf;
    }

    /**
     * @param objRxExecutionBOIntf
     *            the objRxExecutionBOIntf to set
     */
    public void setObjRxExecutionBOIntf(final RxExecutionEoaBOIntf objRxExecutionBOIntf) {
        this.objRxExecutionEoaBOIntf = objRxExecutionBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.service.intf.RxExecutionServiceIntf#
     * getRxExecutionDetails(java.lang.String)
     */
    @Override
    public RxExecTaskDetailsServiceVO getRxExecutionDetails(final String strRxCaseId, final String strLanguage,
            String customerId,boolean isMobileRequest) throws RMDServiceException {
        RxExecTaskDetailsServiceVO resutlVO = null;
        try {
            resutlVO = objRxExecutionEoaBOIntf.getRxExecutionDetails(strRxCaseId, strLanguage, customerId,isMobileRequest);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return resutlVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.service.intf.RxExecutionServiceIntf
     * #saveRxExecutionDetails(com.ge.trans.rmd.services.tools.rx.service.
     * valueobjects.RxExecTaskDetailsServiceVO)
     */
    @Override
    public int saveRxExecutionDetails(final RxExecTaskDetailsServiceVO rxExecTaskDetailsServiceVO)
            throws RMDServiceException {
        int result = RMDCommonConstants.INSERTION_FAILURE;
        try {
            result = objRxExecutionEoaBOIntf.saveRxExecutionDetails(rxExecTaskDetailsServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, rxExecTaskDetailsServiceVO.getStrLanguage());
        }
        return result;
    }
    @Override
	public List<RxExecTaskDetailsServiceVO> getRepeaterRxsList()
			throws RMDServiceException {
		List<RxExecTaskDetailsServiceVO>  objRxExecTaskDetailsServiceVOList =null;
        try {
        	objRxExecTaskDetailsServiceVOList = objRxExecutionEoaBOIntf.getRepeaterRxsList();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objRxExecTaskDetailsServiceVOList;
	}

	@Override
	public List<String> validateURL(String caseId, String fileName,String recommId)
			throws RMDServiceException {
		List<String> objRxExecTaskServiceVO =null;
        try {
        	objRxExecTaskServiceVO = objRxExecutionEoaBOIntf.validateURL(caseId,fileName,recommId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objRxExecTaskServiceVO;
	}
	
	public List<RxDeliveryAttachmentVO> getRxDeliveryAttachments(String caseObjid)
			throws RMDServiceException {
		List<RxDeliveryAttachmentVO>  arlRxDeliveryAttachmentVO =null;
        try {
        	arlRxDeliveryAttachmentVO = objRxExecutionEoaBOIntf.getRxDeliveryAttachments(caseObjid);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRxDeliveryAttachmentVO;
	}

}
