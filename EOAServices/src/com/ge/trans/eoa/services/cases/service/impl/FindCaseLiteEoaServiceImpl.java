package com.ge.trans.eoa.services.cases.service.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.cases.bo.intf.FindCaseLiteEoaBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindCaseLiteEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;

public class FindCaseLiteEoaServiceImpl implements FindCaseLiteEoaServiceIntf {

    private FindCaseLiteEoaBOIntf objFindCaseLiteEoaBOIntf;

    /**
     * @param objCaseBOIntf
     */
    public FindCaseLiteEoaServiceImpl(FindCaseLiteEoaBOIntf objFindCaseLiteEoaBOIntf) {
        this.objFindCaseLiteEoaBOIntf = objFindCaseLiteEoaBOIntf;
    }

    @Override
    public List getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseLiteEoaBOIntf.getDeliveredCases(findCaseSerVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseSerVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getLatestCaseRules
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        Map arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseLiteEoaBOIntf.getLatestCaseRules(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    @Override
    public MultiLangValuesVO getMultiLangMap() throws RMDServiceException {
        try {
            return objFindCaseLiteEoaBOIntf.getMultiLangMap();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }

    }

    @Override
    public List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseLiteEoaBOIntf.getAssetClosedCases(findCaseSerVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseSerVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    @Override
    public List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseLiteEoaBOIntf.getAssetNonClosedCases(findCaseSerVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseSerVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<UnitConfigVO>
     * @throws:RMDServiceException
     * @Description:This method returns the unit configuration details in data
     *                   screen
     */
    @Override
    public List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDServiceException {
        List<UnitConfigVO> unitConfigList = null;
        try {
            unitConfigList = objFindCaseLiteEoaBOIntf.getUnitConfigDetails(objFindCaseServiceVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return unitConfigList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDServiceException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDServiceException {
        List<RepairCodeEoaDetailsVO> falseAlarmDetailsList = null;
        try {
            falseAlarmDetailsList = objFindCaseLiteEoaBOIntf.getFalseAlarmDetails(rxObjId);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return falseAlarmDetailsList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDServiceException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDServiceException {
        List<RxDetailsVO> falseAlarmDetailsList = null;
        try {
            falseAlarmDetailsList = objFindCaseLiteEoaBOIntf.getRXFalseAlarmDetails(rxObjId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return falseAlarmDetailsList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDServiceException
     * @Description:This method returns the mdsc accurate details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDServiceException {
        List<RepairCodeEoaDetailsVO> mdscAccurateList = null;
        try {
            mdscAccurateList = objFindCaseLiteEoaBOIntf.getMDSCAccurateDetails(rxObjId);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return mdscAccurateList;
    }

    /**
     * @Author:
     * @param:String,String
     * @return:List<CaseInfoServiceVO>
     * @throws:RMDServiceException
     * @Description:This method returns the mdsc accurate-case details in data
     *                   screen
     */
    @Override
    public List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDServiceException {
        List<CaseInfoServiceVO> casesList = null;
        try {
            casesList = objFindCaseLiteEoaBOIntf.getCaseDetails(rxObjId, repObjId);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return casesList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDServiceException
     * @Description:This method returns the mdsc accurate-rx details in data
     *                   screen
     */
    @Override
    public List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDServiceException {
        List<RxDetailsVO> rxDetailsList = null;
        try {
            rxDetailsList = objFindCaseLiteEoaBOIntf.getMDSCRxDetails(repObjId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }

        return rxDetailsList;
    }
}
