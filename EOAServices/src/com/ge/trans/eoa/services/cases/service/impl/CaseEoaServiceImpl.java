package com.ge.trans.eoa.services.cases.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CloseCaseVO;
import com.ge.trans.eoa.services.cases.bo.intf.CaseEoaBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AcceptCaseEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CMPrivilegeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseAppendServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseConvertionVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMergeServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseMgmtUsersDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseRepairCodeEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CasesHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CloseOutRepairCodeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.CustomerFdbkVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.DataScreenServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MassApplyRxVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.MaterialUsageVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ReCloseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecommDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RxStatusHistoryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ScoreRxEoaVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SolutionBean;
import com.ge.trans.eoa.services.cases.service.valueobjects.StickyNotesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputActEntryVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ToolOutputEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleFaultEoaServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewLogVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileDataDetailsServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDNullObjectException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class CaseEoaServiceImpl implements CaseEoaServiceIntf {
    private CaseEoaBOIntf objCaseEoaBOIntf;
    private CaseEoaBOIntf dpeabCaseBOInf;

    public CaseEoaServiceImpl(CaseEoaBOIntf objCaseBOIntf) {
        this.objCaseEoaBOIntf = objCaseBOIntf;
    }

    /**
     * @param objCaseBOIntf
     */
    public CaseEoaServiceImpl(CaseEoaBOIntf objCaseEoaBOIntf, CaseEoaBOIntf dpeabCaseBOInf) {
        this.objCaseEoaBOIntf = objCaseEoaBOIntf;
        this.dpeabCaseBOInf = dpeabCaseBOInf;
    }

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CaseEoaServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf#
     * getFaultDetails ( com.ge.trans.rmd.services.cases.service.valueobjects.
     * VehicleFaultServiceVO )
     *//*
       * This Method is used for call the getFaultDetails method in CaseBOImpl
       */
    @Override
    public FaultDataDetailsServiceVO getFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            if (objVehicleFaultServiceVO != null) {
                String availableDataSets = objVehicleFaultServiceVO.getDataSets();
                String[] ddDataSets = new String[3];
                if ((availableDataSets != null) && (availableDataSets.length() > 0)) {
                    if (objVehicleFaultServiceVO.getDataSets().contains(RMDCommonConstants.COMMMA_SEPARATOR)) {
                        ddDataSets = objVehicleFaultServiceVO.getDataSets().split(RMDCommonConstants.COMMMA_SEPARATOR);
                    } else {
                        ddDataSets[0] = objVehicleFaultServiceVO.getDataSets();
                    }
                }
                if (RMDCommonConstants.DPEAB.equalsIgnoreCase(ddDataSets[0])) {
                    objFaultDataDetailsServiceVO = dpeabCaseBOInf.getDPEABFaultDetails(objVehicleFaultServiceVO);
                } else {
                    objFaultDataDetailsServiceVO = objCaseEoaBOIntf.getFaultDetails(objVehicleFaultServiceVO);
                }
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDNullObjectException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVehicleFaultServiceVO.getStrLanguage());
        }
        return objFaultDataDetailsServiceVO;
    }

    
    
    @Override
    public FaultMobileDataDetailsServiceVO getMobileFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDServiceException {
    	FaultMobileDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            if (objVehicleFaultServiceVO != null) {
                	objFaultDataDetailsServiceVO = objCaseEoaBOIntf.getMobileFaultDetails(objVehicleFaultServiceVO);
             }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDNullObjectException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objVehicleFaultServiceVO.getStrLanguage());
        }
        return objFaultDataDetailsServiceVO;
    }

    
    /**
     * This method is used for real time update for case closure
     * 
     * @param String
     * @throws RMDServiceException
     */
    @Override
    public void closeCase(String caseId, String userId) throws RMDServiceException {
        try {
            CloseCaseVO closeCaseVO = new CloseCaseVO();
            closeCaseVO.setStrCaseID(caseId);
            closeCaseVO.setStrUserName(userId);
            objCaseEoaBOIntf.closeCase(closeCaseVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf#
     * getFaultDetails (
     * com.ge.trans.rmd.services.cases.service.valueobjects.DataScreenServiceVO)
     *//*
       * This Method is used for call the getFaultDetails method in CaseBOImpl
       */
    @Override
    public FaultDataDetailsServiceVO getFaultDetails(DataScreenServiceVO objDataScreenServiceVO)
            throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {

            VehicleFaultEoaServiceVO faultVO = new VehicleFaultEoaServiceVO();
            // Creating VehicleFaultServiceVO from DataScreenServiceVO
            copyDataScreenServiceVOToVehicleFaultServiceVO(faultVO, objDataScreenServiceVO);

            objFaultDataDetailsServiceVO = objCaseEoaBOIntf.getFaultDetails(faultVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objDataScreenServiceVO.getStrLanguage());
        }
        return objFaultDataDetailsServiceVO;
    }

    // Creating VehicleFaultServiceVO from DataScreenServiceVO
    public static void copyDataScreenServiceVOToVehicleFaultServiceVO(final VehicleFaultEoaServiceVO faultVO,
            final DataScreenServiceVO objDataScreenServiceVO) {
        try {
            faultVO.setStrRoadNo(objDataScreenServiceVO.getStrAssetNo());

            faultVO.setBlnHealtCheck(new Boolean(objDataScreenServiceVO.isBlnHC()));
            faultVO.setStrSortOption(objDataScreenServiceVO.getStrSortOption());
            faultVO.setCustomerId(objDataScreenServiceVO.getCustomerId());
            faultVO.setAssetGrpName(objDataScreenServiceVO.getAssetGrpName());
            faultVO.setStrLanguage(objDataScreenServiceVO.getStrLanguage());
            faultVO.setStrUserLanguage(objDataScreenServiceVO.getStrUserLanguage());
            faultVO.setStrDaysSelected(objDataScreenServiceVO.getStrNoOfDays());
            faultVO.setStrRoadNo(objDataScreenServiceVO.getStrAssetNo());
            faultVO.setDataSets(objDataScreenServiceVO.getDataSets());
            faultVO.setStrCaseFrom(objDataScreenServiceVO.getStrCaseFrom());
            faultVO.setCaseId(objDataScreenServiceVO.getStrCaseID());
            faultVO.setStrDaysSelected(objDataScreenServiceVO.getStrNoOfDays());
            faultVO.setStrJDPADRadio(objDataScreenServiceVO.getStrJDPADRadio());
            faultVO.setStrRuleDefId(objDataScreenServiceVO.getStrRuleDefId());
            faultVO.setLimitedParam(objDataScreenServiceVO.isLimitedParam());

        } catch (Exception excep) {
            LOG.error("Error occured in CMBeanUtility - copyVehicleFaultServiceVOToDataScreenServiceVO" + excep);
        }

    }

    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDServiceException
     */
    @Override
    public String getEoaCurrentOwnership(String caseId) throws RMDServiceException {
        String result = null;

        try {
            result = objCaseEoaBOIntf.getEoaCurrentOwnership(caseId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    /**
     * @Description Function to do an RTU for takeownership
     **/
    @Override
    public void takeOwnership(AcceptCaseEoaVO acceptCaseVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.takeOwnership(acceptCaseVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    @Override
    public void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.yankCase(acceptCaseVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.service.intf.CaseRxMgmtServiceIntf
     * #saveRxDelvInfo(com.ge.trans.rmd.services.tools.rx.service.valueobjects.
     * RecomDelvInfoServiceVO)
     */
    /*
     * This Method is used for call the saveRxDelvInfo method in
     * CaseRxMgmtBOImpl
     */
    @Override
    public String deliverRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.deliverRX(recomDelvInfoServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, recomDelvInfoServiceVO.getStrLanguage());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf#
     * getToolOutputDetails(java.lang.String)
     *//*
       * This Method is used for call the getToolOutputDetails method in
       * CaseBOImpl
       */
    @Override
    public List getToolOutputDetails(String strCaseId) throws RMDServiceException {
        List arlCaseHistDetails = new ArrayList();
        try {
            arlCaseHistDetails = objCaseEoaBOIntf.getToolOutputDetails(strCaseId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlCaseHistDetails;
    }

    /**
     * @Description Function to do an RTC for close case
     **/
    @Override
    public boolean isCaseClosed(String caseId) throws RMDServiceException {

        boolean isCaseClosed = false;
        try {
            isCaseClosed = objCaseEoaBOIntf.isCaseClosed(caseId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return isCaseClosed;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf#
     * generateCaseIdNumberNextValue()
     */
    @Override
    public String generateCaseIdNumberNextValue() throws RMDServiceException {

        String caseId;
        try {
            caseId = objCaseEoaBOIntf.generateCaseIdNumberNextValue();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

        return caseId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf#
     * saveRxDelvInfo(com.ge.trans.eoa.services.cases.service.valueobjects.
     * RecomDelvInfoServiceVO)
     */
    @Override
    public String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.modifyRx(recomDelvInfoServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, recomDelvInfoServiceVO.getStrLanguage());
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.eoa.services.cases.service.intf.CaseEoaServiceIntf#
     * saveRxDelvInfo(com.ge.trans.eoa.services.cases.service.valueobjects.
     * RecomDelvInfoServiceVO)
     */
    @Override
    public String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.replaceRx(recomDelvInfoServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, recomDelvInfoServiceVO.getStrLanguage());
        }
        return result;
    }

    /**
     * This is a program for getting all the close out repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getCloseOutRepairCodes(repairCodeInputType);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * This is a program for getting all the case repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getCaseRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getCaseRepairCodes(repairCodeInputType);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * This is a program for getting all the repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getRepairCodes(repairCodeInputType);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * @Author:
     * @param:caseId,userName
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method reOpens case by invoking
     *               caseeoaboimpl.reOpenCase() method.
     */
    @Override
    public String reOpenCase(String caseID, String userName) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.reOpenCase(caseID, userName);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;

    }

    /**
     * @param scoreRxEoaVO
     */
    @Override
    public void scoreRx(ScoreRxEoaVO scoreRxEoaVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.scoreRx(scoreRxEoaVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    @Override
    public String getCustRxCaseIdPrefix(String caseId) throws RMDServiceException {
        String custRxCasePrefix = null;
        try {
            custRxCasePrefix = objCaseEoaBOIntf.getCustRxCaseIdPrefix(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return custRxCasePrefix;
    }

    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDServiceException
     */
    @Override
    public void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.addCaseRepairCodes(caseRepairCodeEoaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    }

    /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDServiceException
     */
    @Override
    public void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.removeCaseRepairCodes(caseRepairCodeEoaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    }

    /**
     * @Author:
     * @param:scoreRxEoaVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for saving the manual feedback
     */
    @Override
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO) throws RMDServiceException {
        String message = null;
        try {
            message = objCaseEoaBOIntf.saveSolutionFeedback(scoreRxEoaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return message;
    }

    @Override
    public List<SelectCaseHomeVO> getUserCases(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        List<SelectCaseHomeVO> userCases = null;

        try {
            userCases = objCaseEoaBOIntf.getUserCases(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return userCases;
    }

    @Override
    public CMPrivilegeVO hasCMPrivilege(String userId) throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.hasCMPrivilege(userId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf#save(com
     * .ge.trans.rmd.services.cases.service.valueobjects.CaseInfoServiceVO)
     * Method to update the tile of a case
     */
    @Override
    public String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName) throws RMDServiceException {
        String strResult = RMDCommonConstants.EMPTY_STRING;
        try {
            strResult = objCaseEoaBOIntf.save(caseInfoServiceVO, strUserName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDNullObjectException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, caseInfoServiceVO.getStrLanguage());
        }
        return strResult;
    }

    @Override
    public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO caseTypeVO) throws RMDServiceException {
        List<CaseTypeEoaVO> caseTypeLst = null;
        try {
            caseTypeLst = objCaseEoaBOIntf.getCaseType(caseTypeVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, caseTypeVO.getStrLanguage());
        }
        return caseTypeLst;
    }

    /**
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDServiceException
     * @Description:This method calls getQueueNames() of CaseEoaBOImpl.java
     *                   file.
     */

    @Override
    public List<QueueDetailsVO> getQueueNames(String caseId) throws RMDServiceException {
        List<QueueDetailsVO> queueDetailsVoList = null;
        try {
            queueDetailsVoList = objCaseEoaBOIntf.getQueueNames(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return queueDetailsVoList;

    }

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDServiceException
     * @Description:This method calls dispatchCaseToWorkQueue() of
     *                   CaseEoaBOImpl.java file.
     */
    @Override
    public String dispatchCaseToWorkQueue(final long queueId, final String caseId, final String userId)
            throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.dispatchCaseToWorkQueue(queueId, caseId, userId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDServiceException
     * @Description:This method calls addNotesToCase() of CaseEoaBOImpl.java
     *                   file.
     */

    @Override
    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.addNotesToCase(addnotesVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method calls fetchStickyUnitNotes() of
     *                   CaseEoaBOImpl.java file.
     */

    @Override
    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId) throws RMDServiceException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaBOIntf.fetchStickyUnitNotes(caseId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return details;
    }

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method callsfetchStickyCaseNotes() of
     *                   CaseEoaBOImpl.java file.
     */

    @Override
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId) throws RMDServiceException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaBOIntf.fetchStickyCaseNotes(caseId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return details;
    }

    /**
     * @Author:
     * @param:
     * @return:List<CaseMgmtUsersDetails>
     * @throws:RMDServiceException
     * @Description: This method return the owner for particular caseId by
     *               invoking caseeoaboimpl.getCaseMgmtUsersDetails() method.
     */
    @Override
    public List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails() throws RMDServiceException {
        List<CaseMgmtUsersDetailsVO> objCaseMgmtUsersDetails = null;
        try {
            objCaseMgmtUsersDetails = objCaseEoaBOIntf.getCaseMgmtUsersDetails();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objCaseMgmtUsersDetails;
    }

    /**
     * @Author:
     * @param:userId,caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method assigns owner for particular caseId by invoking
     *               caseeoaboimpl.assignCaseToUser() method.
     */
    @Override
    public String assignCaseToUser(final String userId, final String caseId) throws RMDServiceException {
        String result = RMDServiceConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.assignCaseToUser(userId, caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;

    }

    /**
     * @Author:
     * @param:caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method return the owner for particular caseId by
     *               invoking caseeoaboimpl.getOwnerForCase() method.
     */
    @Override
    public SelectCaseHomeVO getCaseCurrentOwnerDetails(final String caseId) throws RMDServiceException {
        SelectCaseHomeVO objDetailsVO = new SelectCaseHomeVO();
        try {
            objDetailsVO = objCaseEoaBOIntf.getCaseCurrentOwnerDetails(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objDetailsVO;
    }

    /**
     * @Author:
     * @param:caseId
     * @return:List<CaseHistoryVO>
     * @throws:RMDServiceException
     * @Description: This method return the set of activities for particular
     *               caseId by invoking caseeoaboimpl.getCaseHistory() method.
     */
    @Override
	public List<HistoyVO> getCaseHistory(final String caseId)
			throws RMDServiceException {
		List<HistoyVO> caseHistoryVOList = null;
        try {
            caseHistoryVOList = objCaseEoaBOIntf.getCaseHistory(caseId);
        } catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return caseHistoryVOList;
    }

	@Override
	public List<ViewCaseVO> getViewCases(
			final FindCaseServiceVO objFindCaseServiceVO)
			throws RMDServiceException {
		List<ViewCaseVO> SelectCaseHomeVOList = null;
		try {
			SelectCaseHomeVOList = objCaseEoaBOIntf
					.getViewCases(objFindCaseServiceVO);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail(), e);
		}
		return SelectCaseHomeVOList;
	}
	
    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDServiceException
     * @Description:This method return the details for that asset by invoking
     *                   caseeoaboimpl.getAssetCases() method.
     */
    @Override
    public List<SelectCaseHomeVO> getAssetCases(final FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException {
        List<SelectCaseHomeVO> SelectCaseHomeVOList = null;
        try {
            SelectCaseHomeVOList = objCaseEoaBOIntf.getAssetCases(objFindCaseServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return SelectCaseHomeVOList;
    }

    /**
     * @Author :
     * @return :String
     * @param :caseObjId,
     *            applyLevel
     * @throws :RMDServiceException
     * @Description:This method is used for invoking removeStickyNotes of BO
     *                   Layer.
     */
    @Override
    public String removeStickyNotes(final String unitStickyObjId, final String caseStickyObjId, final String applyLevel)
            throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.removeStickyNotes(unitStickyObjId, caseStickyObjId, applyLevel);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDServiceException
     * @Description:This method is for invoking updateDetails() of BO Layer.
     */
    @Override
    public String updateCaseDetails(final FindCaseServiceVO FindCaseServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.updateCaseDetails(FindCaseServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return result;
    }

    @Override
    public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO caseTypeVO) throws RMDServiceException {
        List<CaseTypeEoaVO> caseTypeLst = null;
        try {
            caseTypeLst = objCaseEoaBOIntf.getCaseTypes(caseTypeVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, caseTypeVO.getStrLanguage());
        }
        return caseTypeLst;
    }

    /**
     * @Author:
     * @return:List<CaseDetailsVO>
     * @param String
     *            caseObjid,String language
     * @throws RMDServiceException
     * @Description:This method is used for fetching the selected
     *                   solutions/Recommendations for a Case.
     */
    @Override
    public List<SolutionBean> getSolutionsForCase(String caseObjid, String language) throws RMDServiceException {
        List<SolutionBean> caseDetailsVOsList = null;
        try {
            caseDetailsVOsList = objCaseEoaBOIntf.getSolutionsForCase(caseObjid, language);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return caseDetailsVOsList;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO
     *            objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to add a
     *               recommendation to a given Case.
     */
    @Override
    public String addRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.addRxToCase(objRecomDelvInfoServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return result;
    }

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO
     *            objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to delete a
     *               recommendation to a given Case.
     */
    @Override
    public String deleteRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.deleteRxToCase(objRecomDelvInfoServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return result;

    }

    /**
     * @Author:
     * @param :String
     *            caseId
     * @return:CaseInfoServiceVO
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the case Information.It
     *               accepts caseId as an Input Parameter and returns caseBean
     *               List.
     */
    @Override
    public CaseInfoServiceVO getCaseInfo(String caseId, String language) throws RMDServiceException {
        CaseInfoServiceVO objCaseInfoEoaServiceVO = null;
        try {
            objCaseInfoEoaServiceVO = objCaseEoaBOIntf.getCaseInfo(caseId, language);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return objCaseInfoEoaServiceVO;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<RxStatusHistoryResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the RxStatus History for case by
     *               invoking caseeoaboimpl.getRxstatusHistory() method.
     */
    @Override
    public List<RxStatusHistoryVO> getRxstatusHistory(String servicerReqId) throws RMDServiceException {
        List<RxStatusHistoryVO> eRxStatusHistoryList = new ArrayList<RxStatusHistoryVO>();
        try {
            eRxStatusHistoryList = objCaseEoaBOIntf.getRxstatusHistory(servicerReqId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return eRxStatusHistoryList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<RxHistoryVOResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the Rx History for case by invoking
     *               caseeoaboimpl.getRxHistory() method.
     */
    @Override
    public List<RxHistoryVO> getRxHistory(String caseObjId) throws RMDServiceException {
        List<RxHistoryVO> rxHistoryVOList = new ArrayList<RxHistoryVO>();
        try {
            rxHistoryVOList = objCaseEoaBOIntf.getRxHistory(caseObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return rxHistoryVOList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CustomerFeedbackResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the ServiceReqId & CustFdbkObjId for
     *               case by invoking caseeoaboimpl.getServiceReqId() method.
     */
    @Override
    public List<CustomerFdbkVO> getServiceReqId(String caseObjId) throws RMDServiceException {
        List<CustomerFdbkVO> objCustomerFdbkList = new ArrayList<CustomerFdbkVO>();
        try {
            objCustomerFdbkList = objCaseEoaBOIntf.getServiceReqId(caseObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objCustomerFdbkList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method fetches the Good Feedback for case by invoking
     *               caseeoaboimpl.getClosureFdbk() method.
     */
    @Override
    public String getClosureFdbk(String rxCaseId) throws RMDServiceException {
        String objClosureFdbk = null;
        try {
            objClosureFdbk = objCaseEoaBOIntf.getClosureFdbk(rxCaseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objClosureFdbk;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CloseOutRepairCodesResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the CloseOut Repair Codes for case by
     *               invoking caseeoaboimpl.getCloseOutRepairCode() method.
     */
    @Override
    public List<CloseOutRepairCodeVO> getCloseOutRepairCode(String custFdbkObjId, String serviceReqId)
            throws RMDServiceException {
        List<CloseOutRepairCodeVO> closeOutRepairCodeVOList = new ArrayList<CloseOutRepairCodeVO>();
        try {
            closeOutRepairCodeVOList = objCaseEoaBOIntf.getCloseOutRepairCode(custFdbkObjId, serviceReqId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return closeOutRepairCodeVOList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CloseOutRepairCodesResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the Attached Details for case by
     *               invoking caseeoaboimpl.getAttachedDetails() method.
     */
    @Override
    public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId) throws RMDServiceException {
        List<CloseOutRepairCodeVO> attachedDetailsList = new ArrayList<CloseOutRepairCodeVO>();
        try {
            attachedDetailsList = objCaseEoaBOIntf.getAttachedDetails(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return attachedDetailsList;
    }

    /**
     * @Author:
     * @param:uriParam
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method fetches the Rx Notes for case by invoking
     *               caseeoaboimpl.getRxNote() method.
     */
    @Override
    public String getRxNote(String caseObjId) throws RMDServiceException {
        String rxNote = null;
        try {
            rxNote = objCaseEoaBOIntf.getRxNote(caseObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return rxNote;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDServiceException
     * @Description: This method is used for fetching pendingFdbkServiceStatus
     *               by invoking caseEoaBoIntf.pendingFdbkServiceStatus()
     *               method.
     */
    @Override
    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(String caseId) throws RMDServiceException {
        List<RecomDelvInfoServiceVO> alrDelvInfoEoaServiceVOs = null;
        try {
            alrDelvInfoEoaServiceVOs = objCaseEoaBOIntf.pendingFdbkServiceStatus(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return alrDelvInfoEoaServiceVOs;
    }

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching ServiceReqId by invoking
     *               caseEoaBoIntf.getServiceReqIdStatus() method.
     */

    @Override
    public String getServiceReqIdStatus(String fdbkObjid) throws RMDServiceException {

        String serviceReqId = null;
        try {
            serviceReqId = objCaseEoaBOIntf.getServiceReqIdStatus(fdbkObjid);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return serviceReqId;
    }

    /**
     * @Author:
     * @param:String caseObjid,String
     *                   rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching DeliveryDate by invoking
     *               caseEoaBoIntf.getDelvDateForRx() method.
     */

    @Override
    public String getDelvDateForRx(String caseObjid, String rxObjid) throws RMDServiceException {
        String deiveryDate = null;
        try {
            deiveryDate = objCaseEoaBOIntf.getDelvDateForRx(caseObjid, rxObjid);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return deiveryDate;
    }

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching requestId by invoking
     *               caseEoaBoIntf.getT2Req() method.
     */
    @Override
    public String getT2Req(String caseId) throws RMDServiceException {
        String reqStatus = null;
        try {
            reqStatus = objCaseEoaBOIntf.getT2Req(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return reqStatus;
    }

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching unit shipping details by
     *               invoking caseEoaBoIntf.getUnitShipDetails() method.
     */
    @Override
    public String getUnitShipDetails(String caseObjid) throws RMDServiceException {
        String unitShippingDetails = null;
        try {
            unitShippingDetails = objCaseEoaBOIntf.getUnitShipDetails(caseObjid);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return unitShippingDetails;
    }

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching case Score by invoking by
     *               invoking caseEoaBoIntf..getCaseScore() method.
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseScore(String caseId) throws RMDServiceException {
        List<RecomDelvInfoServiceVO> arlCaseScore = null;
        try {
            arlCaseScore = objCaseEoaBOIntf.getCaseScore(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlCaseScore;
    }

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching readyToDeliver date by
     *               invoking caseEoaBoIntf.getReadyToDelv() method.
     */
    @Override
    public String getReadyToDelv(String rxObjid) throws RMDServiceException {
        String readyDeliver = null;
        try {
            readyDeliver = objCaseEoaBOIntf.getReadyToDelv(rxObjid);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return readyDeliver;

    }

    /**
     * @Author:
     * @param:String caseId,String
     *                   rxObjid
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDServiceException
     * @Description:This method is used for fetching pending recommendation
     *                   details by invoking
     *                   caseEoaBoIntf.getPendingRcommendation() method.
     */
    @Override
    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid) throws RMDServiceException {
        RecomDelvInfoServiceVO objDelvInfoEoaServiceVO = null;
        try {
            objDelvInfoEoaServiceVO = objCaseEoaBOIntf.getPendingRcommendation(caseid);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objDelvInfoEoaServiceVO;
    }

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for checking Whether delivery mechanism
     *               is present for particular Customer are not by invoking
     *               caseEoaBoIntf.checkForDelvMechanism() method.
     */
    @Override
    public String checkForDelvMechanism(String customerName) throws RMDServiceException {
        String result = null;
        try {
            result = objCaseEoaBOIntf.checkForDelvMechanism(customerName);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param:String caseObjid,String
     *                   rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Msdc Notes by invoking
     *               caseEoaBoIntf.getMsdcNotes() method.
     */
    @Override
    public String getMsdcNotes(String caseObjid, String rxObjid,String fromScreen,String custFdbkObjId) throws RMDServiceException {
        String msdcNotes = null;
        try {
            msdcNotes = objCaseEoaBOIntf.getMsdcNotes(caseObjid, rxObjid,fromScreen,custFdbkObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return msdcNotes;
    }

    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method is used for fetching closure details for case by
     *                   invoking caseeoaboimpl.getClosureDetails() method.
     */
    @Override
    public CustomerFdbkVO getClosureDetails(String caseObjId) throws RMDServiceException {
        CustomerFdbkVO objCustomerFdbk = new CustomerFdbkVO();
        try {
            objCustomerFdbk = objCaseEoaBOIntf.getClosureDetails(caseObjId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objCustomerFdbk;
    }

    /**
     * @Author :
     * @return: String
     * @param coreRxEoaVO
     * @throws :RMDServiceException
     * @Description:This method does eservice validation invoking
     *                   doEserviceValidation()method in CaseEoaBOImpl.java.
     */
    @Override
    public String doEserviceValidation(ScoreRxEoaVO objScoreRxEoaVO) throws RMDServiceException {
        String result = null;
        try {
            result = objCaseEoaBOIntf.doEserviceValidation(objScoreRxEoaVO);
        } catch (Exception e) {
            result = e.getMessage();
            LOG.debug(e);
        }

        return result;
    }

    @Override
    public String checkForContollerConfig(VehicleConfigVO objConfigVO) throws RMDServiceException {
        String result = null;
        try {
            result = objCaseEoaBOIntf.checkForContollerConfig(objConfigVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param customerId
     * @return:List<ElementVO>
     * @throws RMDServiceException
     * @Description: This method is used for fetching the list of all Road
     *               Initials based upon CustomerId.
     */
    @Override
    public List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDServiceException {
        List<ElementVO> arlElementVOs = null;
        try {
            arlElementVOs = objCaseEoaBOIntf.getRoadNumberHeaders(customerId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlElementVOs;
    }

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to checking foe maximum numbers of
     *               units on which mass apply rx can be applied.
     */
    @Override
    public String getMaxMassApplyUnits() throws RMDServiceException {
        String maxLimit = null;
        try {
            maxLimit = objCaseEoaBOIntf.getMaxMassApplyUnits();
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return maxLimit;
    }

    /**
     * @Author:
     * @param :MassApplyRxVO
     *            objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDServiceException
     * @Description:This method is used for creating a new Case and delivering
     *                   Recommendations for the assets selected by user.
     */
    @Override
    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO) throws RMDServiceException {
        List<ViewLogVO> arlViewLogVOs = new ArrayList<ViewLogVO>();
        try {
            arlViewLogVOs = objCaseEoaBOIntf.massApplyRx(objMassApplyRxVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlViewLogVOs;
    }

    /**
     * This method is used to get a list of Rxs for which the append and close
     * have to be enabled
     * 
     * @param caseAppendServiceVO
     * @throws RMDServiceException
     */
    @Override
    public List<String> getEnabledRxsAppendClose(CaseAppendServiceVO caseAppendServiceVO) throws RMDServiceException {
        List<String> disabledRxs = null;

        try {
            LOG.info("CASE ID:  " + caseAppendServiceVO.getCaseId());
            LOG.info("CASE TYPE: " + caseAppendServiceVO.getCaseType());
            LOG.info("USER ID: " + caseAppendServiceVO.getUserId());
            disabledRxs = objCaseEoaBOIntf.getEnabledRxsAppendClose(caseAppendServiceVO);
            LOG.info("No of delivered  " + disabledRxs);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return disabledRxs;
    }

    @Override
    public void appendRx(CaseAppendServiceVO caseAppendServiceVO) throws RMDServiceException {
        try {

            objCaseEoaBOIntf.appendRx(caseAppendServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    }

    /**
     * This method returns a count of Open FL work order and will be called
     * while closing the Case will return the open work order count from the
     * eservices.
     * 
     * @param lmsLocoId
     * @return
     * @throws RMDServiceException
     */
    @Override
    public int getOpenFLCount(String lmsLocoId) throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getOpenFLCount(lmsLocoId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    /**
     * This method returns a count of Open FL work order and will be called
     * while closing the Case will return the open work order count from the
     * eservices.
     * 
     * @param lmsLocoId
     * @return
     * @throws RMDServiceException
     */
    @Override
    public String getLmsLocoID(String caseId) throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getLmsLocoID(caseId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf#
     * getToolOutputDetails(java.lang.String)
     *//*
       * This Method is used for call the getToolOutputDetails method in
       * CaseBOImpl
       */
    @Override
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId) throws RMDServiceException {
        List<ToolOutputEoaServiceVO> arlTooloutputDetails = new ArrayList();
        try {
            arlTooloutputDetails = objCaseEoaBOIntf.getToolOutput(strCaseId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlTooloutputDetails;
    }

    @Override
    public String getCaseTitle(String strCaseId) throws RMDServiceException {
        String caseTitle = null;
        try {
            caseTitle = objCaseEoaBOIntf.getCaseTitle(strCaseId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return caseTitle;
    }

    @Override
    public String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) throws RMDServiceException {
        String idNumb = null;
        try {

            idNumb = objCaseEoaBOIntf.moveToolOutput(caseAppendServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return idNumb;
    }

    @Override
    public String saveToolOutputActEntry(ToolOutputActEntryVO objOutputActEntryVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {

            result = objCaseEoaBOIntf.saveToolOutputActEntry(objOutputActEntryVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;

    }

    /**
     * This is a program for getting all the repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCode) throws RMDServiceException {
        try {
            return objCaseEoaBOIntf.getRepairCodes(repairCode);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    @Override
    public String moveDeliverToolOutput(String userId, String currCaseId, String newCaseId, String RxId,
            String assetNumber, String assetGroupName, String customerId, String ruleDefId, String ToolId,
            String caseType, String toolObjId) throws RMDServiceException {
        String message = null;
        try {

            message = objCaseEoaBOIntf.moveDeliverToolOutput(userId, currCaseId, newCaseId, RxId, assetNumber,
                    assetGroupName, customerId, ruleDefId, ToolId, caseType, toolObjId);
        }

        catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return message;
    }

    @Override
    public boolean activeRxExistsInCase(String caseId) throws RMDServiceException {
        boolean activeRxExistsInCase = false;
        try {

            activeRxExistsInCase = objCaseEoaBOIntf.activeRxExistsInCase(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return activeRxExistsInCase;
    }

    @Override
    public Map<String, List<String>> getEnabledUnitRxsDeliver(String customerId, String assetGroupName, String assetNumber,
            String caseId, String caseType,String currentUser) throws RMDServiceException {
        Map<String, List<String>> rxs = null;
        try {

            rxs = objCaseEoaBOIntf.getEnabledUnitRxsDeliver(customerId, assetGroupName, assetNumber, caseId, caseType,currentUser);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return rxs;
    }

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDServiceException
     * @Description:This method is for invoking updateCaseTitle() of BO Layer.
     */
    @Override
    public String updateCaseTitle(final FindCaseServiceVO FindCaseServiceVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.updateCaseTitle(FindCaseServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDServiceException
     * @Description:This method return the details for that asset by invoking
     *                   caseeoaboimpl.getAssetCases() method.
     */
    @Override
    public List<SelectCaseHomeVO> getHeaderSearchCases(final FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException {
        List<SelectCaseHomeVO> SelectCaseHomeVOList = null;
        try {
            SelectCaseHomeVOList = objCaseEoaBOIntf.getHeaderSearchCases(objFindCaseServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return SelectCaseHomeVOList;
    }

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param :
     *            caseObjId,vehicleObjId
     * @throws :RMDServiceException
     * @Description: This method is used to get Rx Details of the Case by
     *               invoking caseEoaBOIntf.getRxDetails() method.
     */
    @Override
    public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId) throws RMDServiceException {

        RecommDetailsVO objRecommDetailsVO = null;
        try {
            objRecommDetailsVO = objCaseEoaBOIntf.getRxDetails(caseObjId, vehicleObjId);

        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objRecommDetailsVO;

    }

    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDServiceException
     * @Description:This method is used for Checking weather unit is Shipped or
     *                   not.
     */
    @Override
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO) throws RMDServiceException {
        String unitShippingDetails = null;
        try {
            unitShippingDetails = objCaseEoaBOIntf.checkForUnitShipDetails(objUnitShipDetailsVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return unitShippingDetails;
    }

    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Cases Details.
     */
    @Override
    public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO) throws RMDServiceException {
        List<FindCasesDetailsVO> objFindCasesDetailsVO = null;
        try {
            objFindCasesDetailsVO = objCaseEoaBOIntf.getFindCases(objFindCasesVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return objFindCasesDetailsVO;
    }

    /**
     * @Author:Mohamed
     * @param :serviceReqId,
     *            lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular
     *                   case.
     */
    @Override
    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId, String lookUpDays) throws RMDServiceException {
        List<MaterialUsageVO> objMaterialUsageVOList = new ArrayList<MaterialUsageVO>();
        try {
            objMaterialUsageVOList = objCaseEoaBOIntf.getMaterialUsage(serviceReqId, lookUpDays);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objMaterialUsageVOList;
    }

    @Override
    public void mergeRx(CaseMergeServiceVO caseMergeServiceVO) throws RMDServiceException {
        try {

            objCaseEoaBOIntf.mergeRx(caseMergeServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }

    }

    /**
     * @Author :Mohamed
     * @return :List<FindCaseServiceVO>
     * @param :
     *            FindCaseServiceVO
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or
     *               not
     */
    @Override
    public List<FindCaseServiceVO> getRxDetailsForReClose(FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException {
        List<FindCaseServiceVO> objFindCaseServiceVOVOList = new ArrayList<FindCaseServiceVO>();
        try {
            objFindCaseServiceVOVOList = objCaseEoaBOIntf.getRxDetailsForReClose(objFindCaseServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return objFindCaseServiceVOVOList;
    }

    @Override
    public void updateCloseCaseResult(ReCloseVO objReCloseVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.updateCloseCaseResult(objReCloseVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    @Override
    public void reCloseResetFaults(ReCloseVO reCloseVO) throws RMDServiceException {
        try {
            objCaseEoaBOIntf.reCloseResetFaults(reCloseVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDServiceException
     * @Description:This method calls addNotesToCase() of CaseEoaBOImpl.java
     *                   file.
     */

    @Override
    public String addNotesToUnit(final AddNotesEoaServiceVO addnotesVO) throws RMDServiceException {
        String result = RMDCommonConstants.FAILURE;
        try {
            result = objCaseEoaBOIntf.addNotesToUnit(addnotesVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }

    @Override
    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(String assetNumber, String customerId, String assetGrpName)
            throws RMDServiceException {
        StickyNotesDetailsVO details = null;
        try {
            details = objCaseEoaBOIntf.fetchStickyUnitLevelNotes(assetNumber, customerId, assetGrpName);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return details;
    }

    /**
     * @Author :Vamshi
     * @return :String
     * @param :CaseRepairCodeEoaVO
     *            objCaseRepairCodeEoaVO
     * @throws :RMDServiceException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */

    @Override
    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO) throws RMDServiceException {
        String result = null;
        try {
            result = objCaseEoaBOIntf.castGPOCVote(objCaseRepairCodeEoaVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }
    
    /**
     * @Author :Vamshi
     * @return :String
     * @param :String
     *            caseObjId
     * @throws :RMDServiceException
     * @Description:This method is responsible for fetching previously Casted
     *                   vote.
     */
    @Override
    public String getPreviousVote(String caseObjId) throws RMDServiceException {
        String previousVote = null;
        try {
            previousVote = objCaseEoaBOIntf.getPreviousVote(caseObjId);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return previousVote;
    }

    @Override
    public List<CasesHeaderVO> getHeaderSearchCasesData(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<CasesHeaderVO> selectCaseHomeVOList = null;
        try {
            selectCaseHomeVOList = objCaseEoaBOIntf
                    .getHeaderSearchCasesData(objFindCaseServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return selectCaseHomeVOList;
    }

	@Override
	public String getDeliverRxURL(String caseId) throws RMDServiceException {
		  String result = null;
	        try {
	        	result = objCaseEoaBOIntf.getDeliverRxURL(caseId);
	        } catch (RMDBOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        }
	        return result;
	}

	@Override
	public List<CaseTrendVO> getOpenCommRxCount() throws RMDServiceException {
		List<CaseTrendVO> getOpenCommRxCount = null;
		try {
			getOpenCommRxCount = objCaseEoaBOIntf.getOpenCommRxCount();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return getOpenCommRxCount;
	}

	@Override
	public List<CaseConvertionVO> getCaseConversionDetails()
			throws RMDServiceException {
		List<CaseConvertionVO> getOpenCommRxCount = null;
		try {
			getOpenCommRxCount = objCaseEoaBOIntf.getCaseConversionDetails();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return getOpenCommRxCount;
	}

	@Override
	public String getCaseConversionPercentage() throws RMDServiceException {
		String percentage = null;
		try {
			percentage = objCaseEoaBOIntf.getCaseConversionPercentage();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return percentage;
	}

	@Override
	public List<CaseConvertionVO> getTopNoActionRXDetails()
			throws RMDServiceException {
		List<CaseConvertionVO> getTopNoActionRXDetails = null;
		try {
			getTopNoActionRXDetails = objCaseEoaBOIntf.getTopNoActionRXDetails();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return getTopNoActionRXDetails;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDServiceException {
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
		try {
		    arlGeneralNotesEoaServiceVO = objCaseEoaBOIntf.getCommNotesDetails();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlGeneralNotesEoaServiceVO;
	}

    /**
     * @Author:
     * @param:uriParam
     * @return:List<CloseOutRepairCodesResponseType>
     * @throws:RMDServiceException
     * @Description: This method fetches the Attached Details for case by
     *               invoking caseeoaboimpl.getAttachedDetails() method.
     */
    @Override
    public boolean getAddRepCodeDetails(String caseId)
            throws RMDServiceException {
        boolean retValue = true;
        try {
            retValue = objCaseEoaBOIntf.getAddRepCodeDetails(caseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return retValue;
    }

    @Override
    public boolean getLookUpRepCodeDetails(String repairCodeList)
            throws RMDServiceException {
        boolean retValue = true;
        try {
            retValue = objCaseEoaBOIntf.getLookUpRepCodeDetails(repairCodeList);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return retValue;
    }
    

	@Override
	public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(String rxCaseId)
			throws RMDServiceException {
        List<CaseScoreRepairCodeVO> closeOutRepairCodeVOList = new ArrayList<CaseScoreRepairCodeVO>();
        try {
            closeOutRepairCodeVOList = objCaseEoaBOIntf.getCaseScoreRepairCodes(rxCaseId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return closeOutRepairCodeVOList;
    }

    @Override
    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDServiceException {
        List<String> result=null;
        try {
            result = objCaseEoaBOIntf.validateVehBoms(customer,rnh,rn,rxObjId,fromScreen);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return result;
    }
    
    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDServiceException
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<RecomDelvInfoServiceVO> recomList = null;
        try {
            recomList = objCaseEoaBOIntf.getCaseRXDelvDetails(caseId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return recomList;
    }
	
}
