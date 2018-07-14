/**
 * ============================================================
 * File : FaultServiceStgyBOImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.fault.bo.impl
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
package com.ge.trans.eoa.services.tools.fault.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultServiceStrategyVO;
import com.ge.trans.eoa.services.tools.fault.bo.intf.FaultServiceStgyBOIntf;
import com.ge.trans.eoa.services.tools.fault.dao.intf.FaultServiceStgyDAOIntf;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceStgyServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
public class FaultServiceStgyBOImpl implements FaultServiceStgyBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FaultServiceStgyBOImpl.class);
    private FaultServiceStgyDAOIntf objFaultServiceStgyDAOIntf = null;

    /**
     * @param objFaultServiceStgyDAOIntf
     */
    public FaultServiceStgyBOImpl(final FaultServiceStgyDAOIntf objFaultServiceStgyDAOIntf) {
        this.objFaultServiceStgyDAOIntf = objFaultServiceStgyDAOIntf;
    }

    /*
     * (non-Javadoc) This method is used for calling findFaultCode method in
     * FaultServiceStgyDAOImpl.
     */
    @Override
    public List<FaultServiceStgyServiceVO> findFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDBOException {
        List<FaultServiceStgyServiceVO> faultVO = null;
        String strFamily;
        String strFaultCode;
        try {
            if (objFaultServiceStgyServiceVO != null) {
                if (!RMDCommonUtility.isNullOrEmpty(objFaultServiceStgyServiceVO.getModelName())) {
                    faultVO = objFaultServiceStgyDAOIntf
                            .getFaultCodeforModels(objFaultServiceStgyServiceVO.getModelName());
                } else {
                    strFamily = objFaultServiceStgyServiceVO.getFamily();
                    strFaultCode = objFaultServiceStgyServiceVO.getStrFaultCode();
					faultVO = objFaultServiceStgyDAOIntf
							.findFaultCode(strFamily,strFaultCode,objFaultServiceStgyServiceVO.isExternalRuleAUthor());
                }

            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return faultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.FaultServiceStgyBOIntf
     * #findFault(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FaultServiceStgyServiceVO) This method is used for calling findFault
     * method in FaultServiceStgyDAOImpl.
     */
    @Override
    public FaultServiceStgyServiceVO findFault(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDBOException {
        FaultServiceStgyServiceVO faultVO = null;
        try {
            faultVO = objFaultServiceStgyDAOIntf.findFault(objFaultServiceStgyServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return faultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.fault.bo.intf.FaultServiceStgyBOIntf
     * # getLoadFaultClass(com.ge.trans.rmd.services.tools.fault.service.
     * valueobjects .FaultServiceStgyServiceVO)
     */// This method is used for calling getLoadFaultClass method in
      // FaultServiceStgyDAOImpl.
    @Override
    public List getLoadFaultClasses(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO)
            throws RMDBOException {
        List arlFaultClass = null;
        try {
            arlFaultClass = objFaultServiceStgyDAOIntf.getLoadFaultClasses(objFaultServiceStgyServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFaultClass;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.FaultServiceStgyBOIntf
     * #saveUpdate(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FaultServiceStgyServiceVO)
     */// This method is used for calling saveUpdate method in
      // FaultServiceStgyDAOImpl.
    @Override
    public String saveUpdate(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, final String strUserId)
            throws RMDBOException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        try {
            strResult = objFaultServiceStgyDAOIntf.saveUpdate(objFaultServiceStgyServiceVO, strUserId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return strResult;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.fault.bo.intf.FaultServiceStgyBOInf
     * #getFaultCode(java.lang.String)
     */
    /*
     * This Method is used for call the check FaultCode method in
     * FaultServiceStgyDAOImpl
     */
    @Override
    public String checkFaultCode(final FaultServiceStgyServiceVO objFaultServiceStgyServiceVO, final String strUserId)
            throws RMDBOException {
        String strResult = null;
        try {
            strResult = objFaultServiceStgyDAOIntf.checkFaultCode(objFaultServiceStgyServiceVO, strUserId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return strResult;
    }

    /**
     * @Author:
     * @param :String
     *            caseId,String language
     * @return:CaseInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for fetching the Fault Strategy
     *               Details.It accepts caseId as an Input Parameter and returns
     *               FaultServiceStrategyVO.
     */
    @Override
    public FaultServiceStrategyVO getFaultStrategyDetails(String fsObjId) throws RMDBOException {

        FaultServiceStrategyVO objFaultServiceStrategyVO = null;
        try {
            objFaultServiceStrategyVO = objFaultServiceStgyDAOIntf.getFaultStrategyDetails(fsObjId);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return objFaultServiceStrategyVO;
    }

    /**
     * @Author:
     * @param :
     * @return:List<ElementVO>
     * @throws:RMDBOException
     * @Description: This method is used to get values from lookup to populate
     *               the Fault Classification drop downlist.
     */

    @Override
    public List<FaultServiceStrategyVO> getRuleDesc(String faultCode) throws RMDBOException {
        List<FaultServiceStrategyVO> arlElement = null;
        try {
            arlElement = objFaultServiceStgyDAOIntf.getRuleDesc(faultCode);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return arlElement;
    }

    /**
     * @Author:
     * @param :String
     *            caseId,String language
     * @return:CaseInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for updating the Fault Strategy
     *               Details.
     */
    @Override
    public void updateFaultServiceStrategy(FaultServiceStrategyVO objFaultServiceStrategyVO) throws RMDBOException {
        try {
            objFaultServiceStgyDAOIntf.updateFaultServiceStrategy(objFaultServiceStrategyVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
    }

    /**
     * @param
     * @return List<FaultCodeVO>
     * @throws RMDBOException
     * @Description This method is used to fetch Fault Origin values to populate
     *              Fault Origin Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultOrigin() throws RMDBOException {
        List<FaultCodeVO> arlListFaultOrigins = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultOrigins = objFaultServiceStgyDAOIntf.getFaultOrigin();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlListFaultOrigins;
    }

    /**
     * @param faultCode,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDBOException
     * @Description This method is used to fetch Fault Code SubId's to populate
     *              Fault SubId Drop down.
     */
    @Override
    public List<FaultCodeVO> getFaultCodeSubId(String faultCode, String faultOrigin) throws RMDBOException {
        List<FaultCodeVO> arlListFaultSubIds = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultSubIds = objFaultServiceStgyDAOIntf.getFaultCodeSubId(faultCode, faultOrigin);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlListFaultSubIds;
    }

    /**
     * @param faultCode
     *            ,faultOrigin
     * @return List<FaultCodeVO>
     * @throws RMDBOException
     * @Description This method is used to get Fault Code based upon Search
     *              Criteria.
     */
    @Override
    public List<FaultCodeVO> getViewFSSFaultCode(String faultCode, String faultOrigin) throws RMDBOException {
        List<FaultCodeVO> arlListFaultCodeVO = new ArrayList<FaultCodeVO>();
        try {
            arlListFaultCodeVO = objFaultServiceStgyDAOIntf.getViewFSSFaultCode(faultCode, faultOrigin);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlListFaultCodeVO;
    }

    /**
     * @param FaultCodeVO
     *            objFaultCodeVO
     * @return String
     * @throws RMDBOException
     * @Description This method is used to get Fault Code ObjId
     */
    @Override
    public String getFaultStrategyObjId(FaultCodeVO objFaultCodeVO) throws RMDBOException {
        String faultStartObjId = null;
        try {
            faultStartObjId = objFaultServiceStgyDAOIntf.getFaultStrategyObjId(objFaultCodeVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return faultStartObjId;
    }
}
