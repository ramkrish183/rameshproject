/**
 * ============================================================
 * File : ViewRuleBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.ViewRuleBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ViewRuleDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.IDListServiceVO;

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
public class ViewRuleBOImpl implements ViewRuleBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ViewRuleBOImpl.class);
    private ViewRuleDAOIntf objViewRuleDAOIntf;

    /**
     * @param objViewRuleDAOIntf
     */
    public ViewRuleBOImpl(final ViewRuleDAOIntf objViewRuleDAOIntf) {
        this.objViewRuleDAOIntf = objViewRuleDAOIntf;
    }

    /**
     * @return the objViewRuleDAOIntf
     */
    public ViewRuleDAOIntf getObjViewRuleDAOIntf() {
        return objViewRuleDAOIntf;
    }

    /**
     * @param objViewRuleDAOIntf
     *            the objViewRuleDAOIntf to set
     */
    public void setObjViewRuleDAOIntf(final ViewRuleDAOIntf objViewRuleDAOIntf) {
        this.objViewRuleDAOIntf = objViewRuleDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.ViewRuleBOIntf#
     * getRuleDetails(java.lang.String)
     */
    @Override
	public FinalRuleServiceVO getRuleDetails(final String strRuleId,
			final String strCloneRule,final  String strLanguage, String explode_ror,String defaultUom) throws RMDBOException {
		FinalRuleServiceVO objRinalRuleServiceVO = null;
		try {
			objRinalRuleServiceVO = objViewRuleDAOIntf.getRuleDetails(
					strRuleId, strCloneRule, strLanguage, explode_ror,defaultUom);
		} catch (RMDDAOException e) {
			throw e;
		}
		return objRinalRuleServiceVO;
	}

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.ViewRuleBOIntf#
     * lockEditRule()
     */
    @Override
    public String lockEditRule(final boolean isLockRule, final String strRuleId, String strUserId, String strLanguage)
            throws RMDBOException {
        try {
            return objViewRuleDAOIntf.lockEditRule(isLockRule, strRuleId, strUserId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.ViewRuleBOIntf#
     * getFiredRuleList()
     */
    @Override
    public List getFiredRuleList(final IDListServiceVO listServiceVO) {
        return objViewRuleDAOIntf.getFiredRuleList(listServiceVO);
    }

    @Override
    public List<DpdFinrulSearchVO> getFinalRuleList(final String strRuleId, final String strFamily,
            final String strRuleType, final String language, String strCustomer) {
        return objViewRuleDAOIntf.getFinalRuleList(strRuleId, strFamily, strRuleType, language, strCustomer);

    }
}
