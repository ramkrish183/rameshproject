package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

/**
 * ============================================================
 * File : ManualRuleServiceImpl.java
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
import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.ManualRunBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.ManualRunServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadResultServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ManualRunServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call the methods in BO
 * @History :
 ******************************************************************************/
public class ManualRunServiceImpl implements ManualRunServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ManualRunServiceImpl.class);
    private ManualRunBOIntf objManualRuleBOIntf;

    /**
     * @param objManualRuleBOIntf
     */

    public ManualRunServiceImpl(final ManualRunBOIntf objManualRuleBOIntf) {

        this.objManualRuleBOIntf = objManualRuleBOIntf;

    }

    /**
     * @param objManualRuleServiceVO
     * @Description:This method is used to get the list of assets
     */

    @Override
    public List getAssetList(final ManualRunServiceVO objManualRunServiceVO) throws RMDServiceException {
        List arlAsset = null;
        try {

            arlAsset = objManualRuleBOIntf.getAssetList(objManualRunServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return arlAsset;
    }

    /**
     * @param objManualJdpadServiceVO
     * @Description:This method is used for running the jdpad
     */

    @Override
    public int runJdpad(final ManualRunServiceVO objManualJdpadServiceVO) throws RMDServiceException {
        int iSeqId = 0;
        try {
            iSeqId = objManualRuleBOIntf.runJdpad(objManualJdpadServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return iSeqId;

    }

    /**
     * @param objJdpadResultServiceVO
     * @throws RMDServiceException
     * @Description:This method is used to get the search results of Jdpad and
     *                   calls the method in BO
     */

    @Override
    public List searchManualRun(final JdpadResultServiceVO objJdpadResultServiceVO) throws RMDServiceException {
        List list = null;
        try {
            list = objManualRuleBOIntf.searchManualRun(objJdpadResultServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return list;
    }

    /**
     * @param objJdpadResultServiceVO
     * @throws RMDServiceException
     * @Description:This method is used to get the list of TrackIds and calls
     *                   the method in BO
     */

    @Override
    public List getTrackIdsList(final String strTrackingId, final String strLanguage) throws RMDServiceException {
        List lstTrackId = null;
        try {

            lstTrackId = objManualRuleBOIntf.getTrackIdsList(strTrackingId, strLanguage);

        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return lstTrackId;

    }

    @Override
    public FaultDataDetailsServiceVO getFaultsForManualRun(
            final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = objManualRuleBOIntf
                    .getFaultsForManualRun(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objFaultDataDetailsServiceVO;
    }

}
