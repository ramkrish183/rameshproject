package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.bo.intf.FindCaseLiteEoaBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FindCaseLiteEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;

public class FindCaseLiteEoaBOImpl implements FindCaseLiteEoaBOIntf {

    private FindCaseLiteEoaDAOIntf objFindCaseLiteEoaDAOIntf;

    /**
     * @param FindCaseLiteDAOIntf
     */
    public FindCaseLiteEoaBOImpl(FindCaseLiteEoaDAOIntf objFindCaseLiteEoaDAOIntf) {
        this.objFindCaseLiteEoaDAOIntf = objFindCaseLiteEoaDAOIntf;
    }

    @Override
    public List getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List<SelectCaseHomeVO> delCaseList;
        try {
            delCaseList = objFindCaseLiteEoaDAOIntf.getDeliveredCases(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return delCaseList;
    }

    @Override
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        Map arlSearchRes;
        try {
            arlSearchRes = objFindCaseLiteEoaDAOIntf.getLatestCaseRules(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    /**
     * @Author:
     * @param FindCaseEoaServiceVO
     * @return
     * @throws RMDBOException
     * @Description: the method is used to get all the multi lang map
     */

    @Override
    public MultiLangValuesVO getMultiLangMap() throws RMDBOException {
        try {
            return objFindCaseLiteEoaDAOIntf.getMultiLangMap();
        } catch (RMDDAOException e) {
            throw e;
        }

    }

    @Override
    public List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List<SelectCaseHomeVO> closedCaseList;
        try {
            closedCaseList = objFindCaseLiteEoaDAOIntf.getAssetClosedCases(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return closedCaseList;
    }

    @Override
    public List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List<SelectCaseHomeVO> nonClosedCaseList;
        try {
            nonClosedCaseList = objFindCaseLiteEoaDAOIntf.getAssetNonClosedCases(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return nonClosedCaseList;
    }

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<UnitConfigVO>
     * @throws:RMDBOException
     * @Description:This method returns the unit configuration details in data
     *                   screen
     */
    @Override
    public List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException {
        List<UnitConfigVO> unitConfigList = null;
        try {
            unitConfigList = objFindCaseLiteEoaDAOIntf.getUnitConfigDetails(objFindCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return unitConfigList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDBOException {
        List<RepairCodeEoaDetailsVO> falseAlarmDetails = null;
        try {
            falseAlarmDetails = objFindCaseLiteEoaDAOIntf.getFalseAlarmDetails(rxObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return falseAlarmDetails;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the false alarm details in data screen
     */
    @Override
    public List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDBOException {
        List<RxDetailsVO> falseAlarmDetails = null;
        try {
            falseAlarmDetails = objFindCaseLiteEoaDAOIntf.getRXFalseAlarmDetails(rxObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return falseAlarmDetails;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate details in data screen
     */
    @Override
    public List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDBOException {
        List<RepairCodeEoaDetailsVO> mdscAccurateList = null;
        try {
            mdscAccurateList = objFindCaseLiteEoaDAOIntf.getMDSCAccurateDetails(rxObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return mdscAccurateList;
    }

    /**
     * @Author:
     * @param:String,String
     * @return:List<CaseInfoServiceVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate details in data screen
     */

    @Override
    public List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDBOException {
        List<CaseInfoServiceVO> casesList = null;
        try {
            casesList = objFindCaseLiteEoaDAOIntf.getCaseDetails(rxObjId, repObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return casesList;
    }

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate-rx details in data
     *                   screen
     */
    @Override
    public List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDBOException {
        List<RxDetailsVO> rxDetailsList = null;
        try {
            rxDetailsList = objFindCaseLiteEoaDAOIntf.getMDSCRxDetails(repObjId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return rxDetailsList;
    }
}
