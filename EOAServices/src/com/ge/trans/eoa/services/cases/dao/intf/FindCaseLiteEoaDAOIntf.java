package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseInfoServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitConfigVO;
import com.ge.trans.rmd.common.valueobjects.RxDetailsVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.MultiLangValuesVO;

@SuppressWarnings("unchecked")
public interface FindCaseLiteEoaDAOIntf {

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getDeliveredCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Map getLatestCaseRules(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:This method is used to get all the multi lang map
     */
    MultiLangValuesVO getMultiLangMap();

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getAssetNonClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getAssetClosedCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<UnitConfigVO>
     * @throws:RMDDAOException
     * @Description:This method returns the unit configuration details in data
     *                   screen
     */
    List<UnitConfigVO> getUnitConfigDetails(FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the false alarm details in data screen
     */
    List<RepairCodeEoaDetailsVO> getFalseAlarmDetails(String rxObjId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the false alarm details in data screen
     */
    List<RxDetailsVO> getRXFalseAlarmDetails(String rxObjId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RepairCodeEoaDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accuarate details in data
     *                   screen
     */
    List<RepairCodeEoaDetailsVO> getMDSCAccurateDetails(String rxObjId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String,String
     * @return:List<CaseInfoServiceVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accuarate-case details in data
     *                   screen
     */
    List<CaseInfoServiceVO> getCaseDetails(String rxObjId, String repObjId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String
     * @return:List<RxDetailsVO>
     * @throws:RMDDAOException
     * @Description:This method returns the mdsc accuarate-rx details in data
     *                   screen
     */
    List<RxDetailsVO> getMDSCRxDetails(String repObjId) throws RMDDAOException;
}
