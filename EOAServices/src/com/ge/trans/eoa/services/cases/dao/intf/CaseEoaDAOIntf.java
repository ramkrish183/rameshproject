package com.ge.trans.eoa.services.cases.dao.intf;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;

import org.hibernate.Session;

import com.ge.trans.eoa.cm.vo.User;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CloseCaseVO;
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
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
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
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleConfigVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewCaseVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ViewLogVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface CaseEoaDAOIntf {

    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDDAOException
     */
    String getEoaCurrentOwnership(String caseId) throws RMDDAOException;

    /**
     * @param acceptCaseVO
     * @throws RMDDAOException
     */
    void takeOwnership(AcceptCaseEoaVO acceptCaseVO) throws RMDDAOException;
    void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String deliverRX(RecomDelvInfoServiceVO recomDelvInfoServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    void addRXTocase(RecomDelvInfoServiceVO caseRXVO) throws RMDDAOException;


    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    ArrayList getToolOutputDetails(String strCaseId)
            throws RMDDAOException;
    
    List<ViewCaseVO> getViewCases(FindCaseServiceVO objFindCaseServiceVO) 
            throws RMDDAOException;
    
    /**
     * 
     * @param caseId
     * @param language
     * @throws RMDDAOException
     */
    boolean  isCaseClosed(String caseId ) throws RMDDAOException ;
    
    /**
     * 
     * @param
     * @param language
     * @throws RMDDAOException
     */
    public String generateCaseIdNumberNextValue() throws RMDDAOException;
    
    /**
     * This method is used for real time update for case closure
     * 
     * @param String
     * @throws RMDServiceException
     */
    void closeCase(CloseCaseVO closeCaseVO) throws RMDDAOException;

    
    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDDAOException
     */

    String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO)
            throws RMDDAOException;

    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDDAOException
     */
    String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO)
            throws RMDDAOException;

    /**
     * This is a program for getting all the close out repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDDAOException
     */
    List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException;

    /**
     * This is a program for getting all the case repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDDAOException
     */
    List<RepairCodeEoaDetailsVO> getRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException;

    /**
     * This is a program for getting all the repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDDAOException
     */
    List<RepairCodeEoaDetailsVO> getCaseRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDDAOException;

    /**
     * @param scoreRxEoaVO
     * @throws RMDDAOException
     */
    void scoreRx(ScoreRxEoaVO scoreRxEoaVO) throws RMDDAOException;
    
    /**
     * @param username
     * @return
     */
    User getUser(String username)throws RMDDAOException;
    
    /**
     * @param username
     * @return
     */
    String getCustRxCaseIdPrefix(String customer)throws RMDDAOException;

    /**
     * @param caseId
     * @return
     */
    String getCustomerNameForCase(String caseId);
    
     /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDDAOException
     */
    void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDDAOException;
    
     /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDDAOException
     */
    void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDDAOException;
    
     /**
     * This method is used to save feedback given by the user
     * 
     * @param scoreRxEoaVO
     * @throws RMDDAOException
     */
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO) throws RMDDAOException, SQLException ;
    /**
     * This method is used to retrive cases related to a user
     * 
     * @param FindCaseServiceVO
     * @throws RMDDAOException
     */
    public List<SelectCaseHomeVO> getUserCases(
            FindCaseServiceVO findCaseServiceVO) throws RMDDAOException;
    
    CMPrivilegeVO hasCMPrivilege(String userId) throws RMDDAOException;
    
    String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName)
            throws RMDDAOException;

    public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO caseTypeVO)  throws RMDDAOException;

    /**
     * @param caseId 
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDDAOException
     * @Description:This method is used for fetching a list of Dynamic work
     *                   QueueNames from Data Base.
     */
    
    public List<QueueDetailsVO> getQueueNames(String caseId) throws RMDDAOException;


    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDDAOException
     * @Description:This method is used for a dispatching case to dynamic work
     *                   queues selected by the user.
     */

    public String dispatchCaseToWorkQueue(final long queueId, final String caseId,
            final String userId) throws  RMDDAOException;
    
    /**
     * @Author  :
     * @return  :String
     * @param   :AddNotesEoaServiceVO
     * @throws  :RMDDAOException
     * @Description:This method is used  for  adding Case notes for  a given case.
     */
    
    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO)throws RMDDAOException;

    /**
     * @Author  :
     * @return  :void
     * @param   :AddNotesEoaServiceVO
     * @throws  :RMDDAOException
     * @Description:This method is used  for  adding Unit notes for  a given case.
     */

    
    public void addUnitNotes(final AddNotesEoaServiceVO addnotesVO)throws RMDDAOException;
    /**
     * @Author  :
     * @return  :String
     * @param   :unitStickyObjId,caseStickyObjId, applyLevel
     * @throws  :RMDDAOException
     * @Description:This method is used  for removing a unit Level as well as case Level Sticky Notes for  a given case.
     */
    public String removeStickyNotes(final String unitStickyObjId,final String caseStickyObjId,final String applyLevel)throws RMDDAOException;
    
    /**
     * @Author  :
     * @return  :StickyNotesDetailsVO
     * @param   :caseId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching unit Sticky notes details for a given case.
     */
    
    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId)throws RMDDAOException;

    /**
     * @Author  :
     * @return  :StickyNotesDetailsVO
     * @param   :caseId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching case Sticky notes details for a given case.
     */
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId)throws RMDDAOException;

    
    /**
     * @Author  :
     * @return  :void
     * @param   :AddNotesEoaServiceVO
     * @throws  :RMDDAOException
     * @Description:This method is used to adding case notes to the given case.
     */
    public void addCaseNotes(final  AddNotesEoaServiceVO addnotesVO)throws RMDDAOException;
    /**
     * @Author: 
     * @param:
     * @return:List<CaseMgmtUsersDetailsVO>
     * @throws:RMDDAOException
     * @Description: This method return the owner for particular caseId by invoking caseeoadaoimpl.getCaseMgmtUsersDetails() method.
     */
    List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails() throws RMDDAOException ;

    /**
     * @Author: 
     * @param:userId,caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method assigns owner for particular caseId by invoking caseeoadaoimpl.assignCaseToUser() method.
     */
    String assignCaseToUser(final String userId,final  String caseId)throws RMDDAOException ;

    /**
     * @Author: 
     * @param:caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method return the owner for particular caseId by invoking caseeoadaoimpl.getOwnerForCase() method.
     */
    SelectCaseHomeVO getCaseCurrentOwnerDetails(final String caseId)throws RMDDAOException;
    /**
     * @Author: 
     * @param:caseId
     * @return:List<CaseHistoryVO> 
     * @throws:RMDDAOException
     * @Description: This method return the set of activities for particular caseId by invoking caseeoadaoimpl.getCaseHistory() method.
     */
    List<HistoyVO> getCaseHistory(final String caseId) throws RMDDAOException;
    /**
     * @Author: 
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO> 
     * @throws:RMDDAOException
     * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
     */
    List<SelectCaseHomeVO> getAssetCases(final FindCaseServiceVO objFindCaseServiceVO)throws RMDDAOException; 
    
    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDDAOException
     * @Description:This method is for updating case details based upon user choice.
     */
    
    public String updateCaseDetails(final FindCaseServiceVO FindCaseServiceVO)throws   RMDDAOException;
    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDDAOException
     * @Description:This method is for updating case details based upon user choice.
     */
    public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO caseTypeVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @return:List<CaseDetailsVO>
     * @param String caseObjid,String language
     * @throws RMDDAOException
     * @Description:This method is used for fetching the selected
     *                   solutions/Recommendations for a Case.
     */
    public List<SolutionBean> getSolutionsForCase(String caseObjid,
            String language) throws RMDDAOException;

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method in CaseResource.java is used to add a
     *               recommendation to a given Case.
     * 
     */
    public String addRxToCase(
            RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method in CaseResource.java is used to delete a
     *               recommendation to a given Case.
     * 
     */
    public String deleteRxToCase(
            RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @param :String caseId
     * @return:CaseInfoServiceVO
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the case Information.It
     *               accepts caseId as an Input Parameter and returns caseBean
     *               List.
     * 
     */

    public CaseInfoServiceVO getCaseInfo(String caseId, String language)
            throws RMDDAOException;

    /**
     * @Author:
     * @param :String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the Services it takes
     *               vehicleObjId Input Parameter and returns services.
     * 
     */
    public Future<String> getServices(String vehicleObjId, Session session)
            throws RMDDAOException;

    /**
     * @Author:
     * @param :String vehicleObjId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pending faults it takes
     *               vehicleObjId and caseObjId as Input Parameters and returns
     *               pendingFaults.
     * 
     */
    public Future<String> getPendingFaults(String vehicleObjId, Session session)
            throws RMDDAOException;

    /**
     * @Author:
     * @param :String vehicleObjId, String caseObjId
     * @return:String 
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pending records it takes
     *               vehicleObjId and caseObjId as Input Parameters and returns pendingFaults.
     * 
     */

    public Future<String> getRecPending(String vehicleObjId, String caseObjId,
            Session session) throws RMDDAOException;

    /**
     * @Author:
     * @param:caseId,userName
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method reopens case by invoking
     *               caseeoadaoimpl.reOpenCase() method.
     */
    String reOpenCase(String caseID, String userId) throws RMDDAOException; 
/**
     * @Author  :
     * @return  :List<RxHistoryVO>
     * @param   :caseObjId
     * @throws  :RMDDAOException
     * @Description:This method fetches the Rx History based on caseObj id
     *               by invoking caseeoadaoimpl.getRxHistory() method.
     */
    public List<RxHistoryVO> getRxHistory(String caseObjId)
            throws RMDDAOException;
    /**
     * @Author  :
     * @return  :List<CustomerFdbkVO>
     * @param   :caseObjId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching the ServiceReqId & CustFdbkObjId  based on caseObj id
     *               by invoking caseeoadaoimpl.getServiceReqId() method.
     */
    public List<CustomerFdbkVO> getServiceReqId(String caseObjId)
            throws RMDDAOException;
    /**
     * @Author  :
     * @return  :List<RxStatusHistoryVO>
     * @param   :serviceReqId
     * @throws  :RMDDAOException
     * @Description:This method fetches the RxStatus History based on servicerReq id
     *               by invoking caseeoadaoimpl.getRxstatusHistory() method.
     */
    public List<RxStatusHistoryVO> getRxstatusHistory(String serviceReqId)
            throws RMDDAOException;
    /**
     * @Author  :
     * @return  :String
     * @param   :caseObjId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching the Good Feedback based on caseObj id
     *               by invoking caseeoadaoimpl.getClosureFdbk() method.
     */
    public String getClosureFdbk(String rxCaseId) throws RMDDAOException;
    /**
     * @Author  :
     * @return  :List<CloseOutRepairCodeVO>
     * @param   :custFdbkObjId , serviceReqId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching the CloseOut Repair Codes based on custFdbkObj id & serviceReqId
     *               by invoking caseeoadaoimpl.getCloseOutRepairCode() method.
     */
    public List<CloseOutRepairCodeVO> getCloseOutRepairCode(
            String custFdbkObjId, String serviceReqId) throws RMDDAOException;
    /**
     * @Author  :
     * @return  :List<CloseOutRepairCodeVO>
     * @param   :caseId
     * @throws  :RMDDAOException
     * @Description:This method fetches the Attached Details based on case id
     *               by invoking caseeoadaoimpl.getAttachedDetails() method.
     */
    public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId)
            throws RMDDAOException;
    /**
     * @Author  :
     * @return  :String
     * @param   :caseObjId
     * @throws  :RMDDAOException
     * @Description:This method is used fetching the Rx Notes based on caseObj id
     *               by invoking caseeoadaoimpl.getRxNote() method.
     */
    public String getRxNote(String caseObjId) throws RMDDAOException;


    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching pendingFdbkServiceStatus
     *               it takes caseId as Input Parameters and returns
     *               pendingFdbkServiceStatus as a String.
     */

    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(
            String caseId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching ServiceReqId it takes
     *               fdbkObjid as Input Parameters and returns ServiceReqId as a
     *               String.
     */

    public String getServiceReqIdStatus(String fdbkObjid)
            throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching DeliveryDate it takes
     *               caseObjid,rxObjid as Input Parameters and returns
     *               DeliveryDate as a String.
     */

    public String getDelvDateForRx(String caseObjid, String rxObjid)
            throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching requestId it takes caseId
     *               as Input Parameter and returns getT2Req as a String
     */

    public String getT2Req(String caseId) throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method getUnitShipDetails is used to fetch unit
     *               shipping details it takes caseObjid as Input Parameter and
     *               returns DeliveryDate as a String.
     */

    public String getUnitShipDetails(String caseObjid) throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching case Score it takes caseid
     *               as Input Parameter and returns DeliveryDate as a String.
     */

    public List<RecomDelvInfoServiceVO> getCaseScore(String caseid)
            throws RMDDAOException;

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used for fetching readyToDeliver date it
     *               takes rxObjid as Input Parameter and returns DeliveryDate
     *               as a String.
     */

    public String getReadyToDelv(String rxObjid) throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseId,String rxObjid
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method is used for fetching pending recommendation
     *               details by passing caseId as Input Parameter.
     */

    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid)
            throws RMDDAOException;

    /**
     * @Author:
     * @param:String customerName
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDDAOException
     * @Description: This method is used for checking Whether delivery mechanism
     *               is present for particular Customer.
     */

    public String checkForDelvMechanism(String customerName)
            throws RMDDAOException;

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching Msdc Notes.it takes
     *               rxObjid,caseObjid as Input Parameter and returns msdcNotes
     *               as a String.
     */
    public String getMsdcNotes(String caseObjid, String rxObjid,String fromScreen,String custFdbkObjId)
            throws RMDDAOException;
    
    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDDAOException
     * @Description:This method is used for fetching closure details based on
     *                   caseObj id.
     */
    public CustomerFdbkVO getClosureDetails(String caseObjId) throws RMDDAOException;
    /**
    * @Author :
    * @return: String
    * @param coreRxEoaVO
    * @throws :RMDDAOException
    * @Description:This method does eservice validation based on caseobjid and
    *                   rxcaseid.
    */
    public String doEServiceValidation(ScoreRxEoaVO objScoreRxEoaVO)
    throws RMDDAOException;

    
    /**
     * @Author:
     * @param VehicleConfigVO
     * @return:String
     * @throws RMDDAOException
     * @Description: This method is used for checking Controller Configuration.
     */
    
    
    public String checkForContollerConfig(VehicleConfigVO objVehicleConfigVO)throws RMDDAOException;


    /**
     * @Author:
     * @param customerId
     * @return:List<ElementVO>
     * @throws RMDDAOException
     * @Description: This method is used for fetching the list of all Road
     *               Initials based upon CustomerId.
     */
    
    public List<ElementVO> getRoadNumberHeaders(final String customerId)throws RMDDAOException;

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDDAOException
     * @Description: This method is used to checking foe maximum numbers of
     *               units on which mass apply Rx can be applied.
     */
    public String getMaxMassApplyUnits() throws RMDDAOException;
    
    /**
     * @Author:
     * @param :MassApplyRxVO objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDDAOException
     * @Description:This method is used for creating a new Case and delivering
     *                   Recommendations for the assets selected by user.
     * 
     */

    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO)
            throws RMDDAOException; 
    
    /**
     * This method is used to check if Case type is RF triggered
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */ 
    boolean validateCaseType(String caseType) throws RMDDAOException;
    /** 
     * This method is used to check if recommendation is delivered
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     
    boolean checkRecomDelivered(CaseAppendServiceVO caseAppendServiceVO) throws RMDDAOException;*/
    /**
     * This method is used to get a list of delivered Rxs
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */

    List<String> getDeliveredRxs(CaseAppendServiceVO caseAppendServiceVO);
    /**
     * This method is used to fetch previous cases if recommendation is delivered
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    List<SelectCaseHomeVO> getPreviousCases(FindCaseServiceVO objFindCaseServiceVO)
            throws RMDDAOException;
    /**
     * This method is used to update casevictim2case column
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    void updateCaseVictimToCase(CaseAppendServiceVO caseAppendServiceVO) throws RMDDAOException;
    /**
     * This method is used to update gets_tool_ar_list 
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
    
    void updateARList(CaseAppendServiceVO caseAppendServiceVO); */
    /**
     * This method is used to for case repetition
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     */
    void appendRx(CaseAppendServiceVO caseAppendServiceVO) throws RMDDAOException;
    /**
     * @Author:
     * @param :lmslocoId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method is used for fetching FL count
     *  
     */ 
    public int getOpenFLCount(String lmsLocoId) throws RMDDAOException;

    /**
     * @Author:
     * @param :caseId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method is used for fetching lmslocoID
     * 
     */

    public String getLmsLocoID(String caseId) throws RMDDAOException;

    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId)
        throws RMDDAOException;

    public String getCaseTitle(String strCaseId);
    
    /**
     * @Author:Shikha
     * @param caseAppendServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */

    String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) throws RMDDAOException;  
    
    String saveToolOutputActEntry(ToolOutputActEntryVO objOutputActEntryVO) throws RMDDAOException;
    
public List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCode) throws RMDDAOException;
    
public String moveDeliverToolOutput(String userId,String currCaseId, String newCaseId,
            String RxId, String customerId,
            String assetNumber, String assetGrpName, String ruleDefId, String toolId, String caseType,String toolObjId)
            throws RMDDAOException; 
/**
 * @Author:Shikha
 * @param caseId
 * @return
 * @throws RMDDAOException
 * @Description:
 */

boolean activeRxExistsInCase(String caseId) throws RMDDAOException;

public Map<String, List<String>> getEnabledUnitRxsDeliver(String customerId,
        String assetGroupName, String assetNumber, String caseId,String caseType,String currentUser)throws RMDDAOException;

String updateCaseTitle(FindCaseServiceVO findCaseServiceVO)throws RMDDAOException;

/**
 * @Author: 
 * @param:FindCaseServiceVO
 * @return:List<SelectCaseHomeVO> 
 * @throws:RMDDAOException
 * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
 */
List<SelectCaseHomeVO> getHeaderSearchCases(final FindCaseServiceVO objFindCaseServiceVO)throws RMDDAOException; 


    /**
     * @Author :
     * @return :RxDetailsVO
     * @param : caseObjId,vehicleObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get Rx Details of the Case.
     * 
     */
public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId)
        throws RMDDAOException;

    /**
     * @Author :
     * @return :List<String>
     * @param : caseObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get Vehicle Configuration Details of
     *               the given Case.
     * 
     */
    public List<String> getVehConfigGroupForCase(String caseObjId)
            throws RMDDAOException;

    /**
     * @Author :
     * @return :String
     * @param : rxObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get vehicle Configuration for the
     *               given Recommendation.
     * 
     */
    public List<String> getVehConfigGroupForRecomm(String rxObjId)
            throws RMDDAOException;

    /**
     * @Author :
     * @return :boolean
     * @param : modelObjId,rxObjId
     * @throws :RMDDAOException
     * @Description: This method is used to get Rx Details of the Case.
     * 
     */
    public boolean getmodelMatch(String model, String rxObjId)
            throws RMDDAOException;

    /**
    * @Author:Vamsee
    * @param :UnitShipDetailsVO
    * @return :String
    * @throws RMDDAOException
    * @Description:This method is used for Checking weather unit is Shipped or not.
    * 
    */
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO) throws RMDDAOException;
    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDDAOException
     * @Description: This method is used to get Find Cases Details.
     */
    public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO) throws RMDDAOException;
    
    /**
     * @Author:Mohamed
     * @param :serviceReqId, lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular case.
     * 
     */

    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId, String lookUpDays) throws RMDDAOException;
    
    /**
     * This method is used to for case repetition
     * 
     * @param caseMergeServiceVO
     * @throws RMDDAOException
     */
    
    void mergeRx(CaseMergeServiceVO caseMergeServiceVO) throws RMDDAOException;
    
    /**
     * @Author :Mohamed
     * @return :List<FindCaseServiceVO>
     * @param : FindCaseServiceVO
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or not
     */
    public List<FindCaseServiceVO> getRxDetailsForReClose(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException;

    public void updateCloseCaseResult(ReCloseVO objReCloseVO) throws RMDDAOException;

    public void reCloseResetFaults(ReCloseVO reCloseVO) throws RMDDAOException;

    public String addNotesToUnit(AddNotesEoaServiceVO addnotesVO)throws RMDDAOException;

    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(String assetNumber,
            String customerId, String assetGrpName)throws RMDDAOException;

    /**
     * @Author :Vamsee
     * @return :String
     * @param :CaseRepairCodeEoaVO objCaseRepairCodeEoaVO
     * @throws :RMDDAOException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */

    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO)
            throws RMDDAOException;

    /**
     * @Author :Vamsee
     * @return :String
     * @param :String caseObjId
     * @throws :RMDDAOException
     * @Description:This method is responsible for fetching previously Casted
     *                   vote.
     */

    public String getPreviousVote(String caseObjId) throws RMDDAOException;
    public Map<String,String> getCaseType()throws RMDDAOException;
    
    /**
     * @Author: 
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO> 
     * @throws:RMDDAOException
     * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
     */
    List<CasesHeaderVO> getHeaderSearchCasesData(final FindCaseServiceVO objFindCaseServiceVO)throws RMDDAOException;

    public String getDeliverRxURL(String caseId) throws RMDDAOException; 
    public List<CaseTrendVO> getOpenCommRxCount() throws RMDDAOException;
    public List<CaseConvertionVO> getCaseConversionDetails() throws RMDDAOException;
    public  String getCaseConversionPercentage() throws RMDDAOException;
    public List<CaseConvertionVO> getTopNoActionRXDetails() throws RMDDAOException;
    public List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDDAOException;

    boolean getAddRepCodeDetails(String caseId) throws RMDDAOException;

    boolean getLookUpRepCodeDetails(String repairCodeList)
            throws RMDDAOException;
    
    public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(
            String rxCaseId) throws RMDDAOException;

    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDDAOException;
    
    public List<String> getUnitConversionRoles() throws RMDDAOException;
    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDDAOException;
}
