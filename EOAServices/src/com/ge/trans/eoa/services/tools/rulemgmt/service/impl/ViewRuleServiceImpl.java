/**
 * ============================================================
 * File : ViewRuleServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.ViewRuleBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.ViewRuleServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.IDListServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class ViewRuleServiceImpl implements ViewRuleServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ViewRuleServiceImpl.class);
    private ViewRuleBOIntf objViewRuleBOIntf;

    /**
     * @param objViewRuleBOIntf
     */
    public ViewRuleServiceImpl(final ViewRuleBOIntf objViewRuleBOIntf) {
        this.objViewRuleBOIntf = objViewRuleBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.ViewRuleServiceIntf
     * #getRuleDetails(java.lang.String)
     */
    @Override
	public FinalRuleServiceVO getRuleDetails(final String strRuleId,
			final String strCloneRule,final  String strLanguage,String explode_ror,String defaultUom) throws RMDServiceException {
		FinalRuleServiceVO objFinalRuleServiceVO = null;
		try {
			objFinalRuleServiceVO = objViewRuleBOIntf.getRuleDetails(strRuleId,
					strCloneRule, strLanguage,explode_ror,defaultUom);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
		}
		return objFinalRuleServiceVO;
	}

    /**
     * @return the objViewRuleBOIntf
     */
    public ViewRuleBOIntf getObjViewRuleBOIntf() {
        return objViewRuleBOIntf;
    }

    /**
     * @param objViewRuleBOIntf
     *            the objViewRuleBOIntf to set
     */
    public void setObjViewRuleBOIntf(final ViewRuleBOIntf objViewRuleBOIntf) {
        this.objViewRuleBOIntf = objViewRuleBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.ViewRuleServiceIntf
     * #lockEditRule()
     */
    @Override
    public String lockEditRule(final boolean isLockRule, final String strRuleId, final String strUserId,
            final String strLanguage) throws RMDServiceException {
        String strDetail = null;
        try {
            strDetail = objViewRuleBOIntf.lockEditRule(isLockRule, strRuleId, strUserId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return strDetail;
    }

    @Override
    public List getFinalRuleList(final String strRuleId, final String strFamily, final String strRuleType,
            final String language, String strCustomer) throws RMDServiceException {
        List arlFinRule = null;
        try {
            arlFinRule = objViewRuleBOIntf.getFinalRuleList(strRuleId, strFamily, strRuleType, language, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }

        return arlFinRule;
    }

    @Override
    public List<DpdFinrulSearchVO> getFiredRuleList(final String strTraceID, final String strLanguage)
            throws RMDServiceException {
        List firedRuleList = null;
        try {
            final IDListServiceVO listServiceVO = new IDListServiceVO();
            listServiceVO.setStrLanguage(strLanguage);
            listServiceVO.setStrTraceID(strTraceID);
            firedRuleList = objViewRuleBOIntf.getFiredRuleList(listServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return firedRuleList;
    }
}
