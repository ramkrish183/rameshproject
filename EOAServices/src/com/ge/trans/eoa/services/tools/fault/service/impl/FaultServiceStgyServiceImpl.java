/**
 * ============================================================
 * File : FaultServiceStgyServiceImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.fault.service.impl;
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
package com.ge.trans.eoa.services.tools.fault.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.eoa.services.tools.fault.bo.intf.FaultServiceStgyBOIntf;
import com.ge.trans.eoa.services.tools.fault.service.intf.FaultServiceStgyServiceIntf;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class FaultServiceStgyServiceImpl implements FaultServiceStgyServiceIntf {

    private FaultServiceStgyBOIntf objFaultServiceStgyBOIntf = null;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FaultServiceStgyServiceImpl.class);

    /**
     *
     */
    public FaultServiceStgyServiceImpl(final FaultServiceStgyBOIntf objFaultServiceStgyBOIntf) {
        this.objFaultServiceStgyBOIntf = objFaultServiceStgyBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.FaultServiceStgyServiceIntf
     * #findFault(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for calling findFaultCode method in
      // FaultServiceStgyBOImpl.
    @Override
    public List<FaultServiceStgyServiceVO> findFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDServiceException {
        List<FaultServiceStgyServiceVO> faultVO = null;
        try {
            faultVO = objFaultServiceStgyBOIntf.findFaultCode(objFaultServiceStgyServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFaultServiceStgyServiceVO.getStrLanguage());
        }
        return faultVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.FaultServiceStgyServiceIntf
     * #findFault(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for calling findFault method in
      // FaultServiceStgyBOImpl.
    @Override
    public FaultServiceStgyServiceVO findFault(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDServiceException {
        FaultServiceStgyServiceVO faultVO = null;
        try {
            faultVO = objFaultServiceStgyBOIntf.findFault(objFaultServiceStgyServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFaultServiceStgyServiceVO.getStrLanguage());
        }
        return faultVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.FaultServiceStgyServiceIntf
     * #getLoadFaultClass()
     */// This method is used for calling getLoadFaultClass method in
      // FaultServiceStgyBOImpl.
    @Override
    public List getLoadFaultClasses(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDServiceException {
        List arlFaultClass = null;
        try {
            arlFaultClass = objFaultServiceStgyBOIntf.getLoadFaultClasses(objFaultServiceStgyServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFaultServiceStgyServiceVO.getStrLanguage());
        }
        return arlFaultClass;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.FaultServiceStgyServiceIntf
     * #saveUpdate(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for calling saveUpdate method in
      // FaultServiceStgyBOImpl.
    @Override
    public String saveUpdate(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, final String strUserId)
            throws RMDServiceException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        try {
            strResult = objFaultServiceStgyBOIntf.saveUpdate(objFaultServiceStgyServiceVO, strUserId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFaultServiceStgyServiceVO.getStrLanguage());
        }
        return strResult;
    }

    /**
     * @param strFaultCode
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     *             This Method is used for call the getFaultCode method in
     *             FaultServiceStgyBOImpl
     */
    @Override
    public String checkFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, final String strUserId)
            throws RMDServiceException {
        String strResult = null;
        try {
            strResult = objFaultServiceStgyBOIntf.checkFaultCode(objFaultServiceStgyServiceVO, strUserId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFaultServiceStgyServiceVO.getStrLanguage());
        }
        return strResult;
    }

    /**
     * @Author:
     * @param :String
     *            caseId
     * @return:CaseInfoServiceVO
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the Fault Strategy
     *               Details.It accepts caseId as an Input Parameter and returns
     *               FaultServiceStrategyVO.
     */
    @Override
    public FaultServiceStrategyVO getFaultStrategyDetails(String fsObjId) throws RMDServiceException {
        FaultServiceStrategyVO objFaultServiceStrategyVO = null;
        try {
            objFaultServiceStrategyVO = objFaultServiceStgyBOIntf.getFaultStrategyDetails(fsObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objFaultServiceStrategyVO;
    }

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get values to display the Fault Rule
     *               & Desc.
     */
    @Override
    public List<FaultServiceStrategyVO> getRuleDesc(String faultCode) throws RMDServiceException {
        List<FaultServiceStrategyVO> arlElement = null;
        try {
            arlElement = objFaultServiceStgyBOIntf.getRuleDesc(faultCode);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlElement;
    }

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDServiceException
     * @Description: This method is used to update the Fault Strategy Details.
     */
    @Override
    public void updateFaultServiceStrategy(FaultServiceStrategyVO objFaultServiceStrategyVO)
            throws RMDServiceException {
        try {
            objFaultServiceStgyBOIntf.updateFaultServiceStrategy(objFaultServiceStrategyVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * @param
     * @return List<String>
     * @throws RMDServiceException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultOrigin() throws RMDServiceException {
        List<FaultCodeVO> arlListFaultOrigins = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultOrigins = objFaultServiceStgyBOIntf.getFaultOrigin();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlListFaultOrigins;
    }

    /**
     * @param faultCode
     *            ,faultOrigin
     * @return List<String>
     * @throws RMDServiceException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultCodeSubId(String faultCode, String faultOrigin) throws RMDServiceException {
        List<FaultCodeVO> arlListFaultSubIds = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultSubIds = objFaultServiceStgyBOIntf.getFaultCodeSubId(faultCode, faultOrigin);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlListFaultSubIds;
    }

    /**
     * @param faultCode
     *            ,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDServiceException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */
    @Override
    public List<FaultCodeVO> getViewFSSFaultCode(String faultCode, String faultOrigin) throws RMDServiceException {
        List<FaultCodeVO> arlListFaultCodeVO = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultCodeVO = objFaultServiceStgyBOIntf.getViewFSSFaultCode(faultCode, faultOrigin);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlListFaultCodeVO;
    }

    /**
     * @param FaultCodeVO
     *            objFaultCodeVO
     * @return String
     * @throws RMDServiceException
     * @Description This method is used to get Fault Code ObjId
     */
    @Override
    public String getFaultStrategyObjId(FaultCodeVO objFaultCodeVO) throws RMDServiceException {
        String faultStartObjId = null;
        try {
            faultStartObjId = objFaultServiceStgyBOIntf.getFaultStrategyObjId(objFaultCodeVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return faultStartObjId;
    }
}
