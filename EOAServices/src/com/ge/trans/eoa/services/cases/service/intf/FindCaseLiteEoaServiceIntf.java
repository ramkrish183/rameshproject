package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;

public interface FindCaseLiteEoaServiceIntf {
    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the list of cases and rules for
     *               the given search criteria
     */
    Map getLatestCaseRules(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the multi lang map
     */
    MultiLangValuesVO getMultiLangMap() throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<UnitConfigVO>
     * @param :
     *            FindCaseServiceVO
     * @throws :RMDServiceException
     * @Description: This method is used to get unit configuration details in
     *               data screen
     */
    List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<RepairCodeEoaDetailsVO>
     * @param :
     *            String
     * @throws :RMDServiceException
     * @Description: This method is used to get false alarm details in data
     *               screen
     */
    List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<RxDetailsVO>
     * @param :
     *            String
     * @throws :RMDServiceException
     * @Description: This method is used to get false alarm details in data
     *               screen
     */
    List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<RepairCodeEoaDetailsVO>
     * @param :
     *            String
     * @throws :RMDServiceException
     * @Description: This method is used to get mdsc accurate details in data
     *               screen
     */
    List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<CaseInfoServiceVO>
     * @param :
     *            String,String
     * @throws :RMDServiceException
     * @Description: This method is used to get mdsc accurate-case details in
     *               data screen
     */
    List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<RxDetailsVO>
     * @param :
     *            String
     * @throws :RMDServiceException
     * @Description: This method is used to get mdsc accurate-rx details in data
     *               screen
     */
    List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDServiceException;
}
