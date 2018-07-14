package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;

@SuppressWarnings("unchecked")
public interface FindCaseLiteEoaBOIntf {
    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param FindCaseEoaServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param FindCaseEoaServiceVO
     * @return
     * @throws RMDBOException
     * @Description: the method is used to get all the multi lang map
     */
    MultiLangValuesVO getMultiLangMap() throws RMDBOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<UnitConfigVO>
     * @throws:RMDBOException
     * @Description:This method returns the unit configuration details in data
     *                   screen
     */
    List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the false alarm details in data screen
     */
    List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDBOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the false alarm details in data screen
     */
    List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDBOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate details in data screen
     */
    List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDBOException;

    /**
     * @Author:
     * @param:String,String
     * @return:List<CaseInfoServiceVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate-case details in data
     *                   screen
     */
    List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDBOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDBOException
     * @Description:This method returns the mdsc accurate-rx details in data
     *                   screen
     */
    List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDBOException;
}
