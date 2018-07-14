package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

/**
 * ============================================================
 * File : ManualRuleBOImpl.java
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
import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.ManualRunBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ManualRunDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadResultServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ManualRunServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call ManualRuleDAOImpl methods
 * @History :
 ******************************************************************************/
public class ManualRunBOImpl implements ManualRunBOIntf {

    /**
     * Common utility logger
     */
    private final RMDLogger objRMDLogger;

    private ManualRunDAOIntf objManualRuleDAOIntf;
    List arlAsset = null;

    public ManualRunBOImpl(final ManualRunDAOIntf objManualRuleDAOIntf) {
        this.objManualRuleDAOIntf = objManualRuleDAOIntf;
        objRMDLogger = RMDLoggerHelper.getLogger(this.getClass());
    }

    @Override
    public List getAssetList(final ManualRunServiceVO objManualRuleServiceVO) throws RMDBOException {
        try {
            arlAsset = objManualRuleDAOIntf.getAssetList(objManualRuleServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }

        return arlAsset;

    }

    @Override
    public int runJdpad(final ManualRunServiceVO objManualJdpadServiceVO) throws RMDBOException {
        int iSeqId = 0;
        try {
            final ManualRunServiceVO objManualRuleServiceVO = new ManualRunServiceVO();
            objManualRuleServiceVO.setStrUserName(objManualJdpadServiceVO.getStrUserName());
            iSeqId = objManualRuleDAOIntf.runJdpad(objManualJdpadServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return iSeqId;

    }

    /**
     * @param objJdpadResultServiceVO
     * @Description:This method is used to get the searchResults and calls the
     *                   method in DAO
     */

    @Override
    public List searchManualRun(final JdpadResultServiceVO objJdpadResultServiceVO) throws RMDBOException {
        List list = null;
        try {
            list = objManualRuleDAOIntf.searchManualRun(objJdpadResultServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return list;
    }

    /**
     * @param strLanguage,strTrackingId
     * @Description:This method is used to call the getTrackIdsList method in
     *                   ManualRuleDAOImpl for loading the trackId in DB
     */
    @Override
    public List getTrackIdsList(final String strTrackingId, final String strLanguage) throws RMDBOException {
        List arlTrack = null;
        try {
            arlTrack = objManualRuleDAOIntf.getTrackIdsList(strTrackingId, strLanguage);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return arlTrack;
    }

    @Override
    public FaultDataDetailsServiceVO getFaultsForManualRun(
            RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException {

        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();
            objFaultDataDetailsServiceVO = objManualRuleDAOIntf
                    .getFaultsForManualRun(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }

        return objFaultDataDetailsServiceVO;
    }

}
