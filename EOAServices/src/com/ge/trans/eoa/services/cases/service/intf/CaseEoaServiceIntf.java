package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddNotesEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CaseTypeEoaVO;
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
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileDataDetailsServiceVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCasesDetailsVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;

public interface CaseEoaServiceIntf {

    /**
     * @Author:
     * @param objVehicleFaultServiceVO
     * @throws RMDServiceException
     * @Description: Method to get data for data screens
     */
    FaultDataDetailsServiceVO getFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDServiceException;
    /**
     * @Author:
     * @param objDataScreenServiceVO
     * @throws RMDServiceException
     * @Description: Method to get data for data screens
     */
    FaultDataDetailsServiceVO getFaultDetails(DataScreenServiceVO objDataScreenServiceVO) throws RMDServiceException;

    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDServiceException
     */
    String getEoaCurrentOwnership(String caseId) throws RMDServiceException;
    /**
     * @param acceptCaseVO
     * @throws RMDServiceException
     */
    void takeOwnership(AcceptCaseEoaVO acceptCaseVO) throws RMDServiceException;

    /**
     * @Author:
     * @param recomDelvServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    String deliverRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getToolOutputDetails(String strCaseId) throws RMDServiceException;

    /**
     * @param caseId
     * @param language
     * @return date close date
     * @throws RMDServiceException
     */
    boolean isCaseClosed(String caseId) throws RMDServiceException;

    /**
     * @return
     * @throws RMDServiceException
     */
    String generateCaseIdNumberNextValue() throws RMDServiceException;
    /**
     * This method is used for real time update for case closure
     * 
     * @param void
     * @throws RMDServiceException
     */
    void closeCase(String caseId, String userId) throws RMDServiceException;

    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDServiceException
     */
    String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException;
    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDServiceException
     */
    String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDServiceException;

    /**
     * The method to get the closed repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDServiceException;

    /**
     * The method to get the case repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    List<RepairCodeEoaDetailsVO> getCaseRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType)
            throws RMDServiceException;

    /**
     * The method to get the repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    List<RepairCodeEoaDetailsVO> getRepairCodes(RepairCodeEoaDetailsVO repairCodeInputType) throws RMDServiceException;

    /**
     * @Author:
     * @param:caseId,userName
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method reOpens case by invoking caseeoaboimpl.reOpenCase() method.
     */
    String reOpenCase(String caseID, String userId) throws RMDServiceException;

    /**
     * @param scoreRxEoaVO
     * @throws RMDServiceException
     */
    void scoreRx(ScoreRxEoaVO scoreRxEoaVO) throws RMDServiceException;

    /**
     * @param customer
     * @return
     * @throws RMDServiceException
     */
    public String getCustRxCaseIdPrefix(String caseId) throws RMDServiceException;
    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDServiceException
     */
    void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDServiceException;

    /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDServiceException
     */
    void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:scoreRxEoaVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for saving the manual feedback
     */
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO) throws RMDServiceException;

    public List<SelectCaseHomeVO> getUserCases(FindCaseServiceVO FindCaseServiceVO) throws RMDServiceException;

    CMPrivilegeVO hasCMPrivilege(String userId) throws RMDServiceException;

    String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName) throws RMDServiceException;

    public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO caseTypeVO) throws RMDServiceException;

    /* Added by Vamsee Deepak For Dispatch Functionality */

    /**
     * @param caseId
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDServiceException
     * @Description:This method is used for fetching a list of Dynamic work QueueNames from Data
     *                   Base.
     */

    public List<QueueDetailsVO> getQueueNames(String caseId) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDServiceException
     * @Description:This method is used for a dispatching case to dynamic work queues selected by
     *                   the user.
     */
    public String dispatchCaseToWorkQueue(final long queueId, final String caseId, final String userId)
            throws RMDServiceException;
    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDServiceException
     * @Description:This method is used for adding Case notes for a given case.
     */

    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching unit Sticky notes details for a given case.
     */
    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId) throws RMDServiceException;

    /**
     * @Author :
     * @return :StickyNotesDetailsVO
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching case Sticky notes details for a given case.
     */
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId) throws RMDServiceException;

    /**
     * @Author:
     * @param:
     * @return:List<CaseMgmtUsersDetails>
     * @throws:RMDServiceException
     * @Description: This method return the owner for particular caseId by invoking
     *               caseeoaboimpl.getCaseMgmtUsersDetails() method.
     */
    List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails() throws RMDServiceException;

    /**
     * @Author:
     * @param:userId,caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method assigns owner for particular caseId by invoking
     *               caseeoaboimpl.assignCaseToUser() method.
     */
    String assignCaseToUser(final String userId, final String caseId) throws RMDServiceException;

    /**
     * @Author:
     * @param:caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method return the owner for particular caseId by invoking
     *               caseeoaboimpl.getOwnerForCase() method.
     */
    SelectCaseHomeVO getCaseCurrentOwnerDetails(final String caseId) throws RMDServiceException;

    /**
     * @Author:
     * @param:caseId
     * @return:List<CaseHistoryVO>
     * @throws:RMDServiceException
     * @Description: This method return the set of activities for particular caseId by invoking
     *               caseeoaboimpl.getCaseHistory() method.
     */

    List<HistoyVO> getCaseHistory(final String caseId) throws RMDServiceException;

    List<ViewCaseVO> getViewCases(final FindCaseServiceVO objFindCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDServiceException
     * @Description:This method return the details for that asset by invoking
     *                   caseeoaboimpl.getAssetCases() method.
     */
    List<SelectCaseHomeVO> getAssetCases(final FindCaseServiceVO objFindCaseServiceVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :unitStickyObjId,caseStickyObjId, applyLevel
     * @throws :RMDServiceException
     * @Description:This method is used for removing a unit Level as well as case Level Sticky Notes
     *                   for a given case.
     */

    public String removeStickyNotes(final String unitStickyObjId, final String caseStickyObjId, final String applyLevel)
            throws RMDServiceException;

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDServiceException
     * @Description:This method is for updating case details based upon user choice.
     */

    public String updateCaseDetails(final FindCaseServiceVO FindCaseServiceVO) throws RMDServiceException;

    public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO caseTypeVO) throws RMDServiceException;

    void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDServiceException;

    /**
     * @Author:
     * @return:List<CaseDetailsVO>
     * @param String
     *            caseObjid,String language
     * @throws RMDServiceException
     * @Description:This method is used for fetching the selected solutions/Recommendations for a
     *                   Case.
     */
    public List<SolutionBean> getSolutionsForCase(String caseObjid, String language) throws RMDServiceException;

    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to add a recommendation to a given
     *               Case.
     */
    public String addRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDServiceException;
    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method in CaseResource.java is used to delete a recommendation to a given
     *               Case.
     */
    public String deleteRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param :String caseId
     * @return:List <CaseInfoServiceVO>
     * @throws:RMDServiceException
     * @Description: This method is used for fetching the case Information.It accepts caseId as an
     *               Input Parameter and returns caseBean List.
     */

    public CaseInfoServiceVO getCaseInfo(String caseId, String language) throws RMDServiceException;
    /**
     * @Author :
     * @return :List<RxHistoryVO>
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method fetches the Rx History based on caseObj id by invoking
     *                   caseeoaboimpl.getRxHistory() method.
     */
    public List<RxHistoryVO> getRxHistory(String caseObjId) throws RMDServiceException;
    /**
     * @Author :
     * @return :List<CustomerFdbkVO>
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method return the ServiceReqId & CustFdbkObjId based on caseObj id by
     *                   invoking caseeoaboimpl.getServiceReqId() method.
     */
    public List<CustomerFdbkVO> getServiceReqId(String caseObjId) throws RMDServiceException;
    /**
     * @Author :
     * @return :List<RxStatusHistoryVO>
     * @param :serviceReqId
     * @throws :RMDServiceException
     * @Description:This method fetches the RxStatus History based on servicerReq id by invoking
     *                   caseeoaboimpl.getRxstatusHistory() method.
     */
    public List<RxStatusHistoryVO> getRxstatusHistory(String serviceReqId) throws RMDServiceException;
    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method return the Good Feedback based on rxCaseId id by invoking
     *                   caseeoaboimpl.getClosureFdbk() method.
     */
    public String getClosureFdbk(String rxCaseId) throws RMDServiceException;
    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :custFdbkObjId , serviceReqId
     * @throws :RMDServiceException
     * @Description:This method is used fetching the CloseOut Repair Codes based on custFdbkObj id &
     *                   serviceReqId by invoking caseeoaboimpl.getCloseOutRepairCode() method.
     */
    public List<CloseOutRepairCodeVO> getCloseOutRepairCode(String custFdbkObjId, String serviceReqId)
            throws RMDServiceException;
    /**
     * @Author :
     * @return :List<CloseOutRepairCodeVO>
     * @param :caseId
     * @throws :RMDServiceException
     * @Description:This method is used fetching the Attached Details based on case id by invoking
     *                   caseeoaboimpl.getAttachedDetails() method.
     */
    public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId) throws RMDServiceException;
    /**
     * @Author :
     * @return :String
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method return the Rx Notes based on caseObj id by invoking
     *                   caseeoaboimpl.getRxNote() method.
     */
    public String getRxNote(String caseObjId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseId
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDServiceException
     * @Description: This method is used for fetching pendingFdbkServiceStatus by invoking
     *               caseEoaBoIntf.pendingFdbkServiceStatus() method.
     */
    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(String caseId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching ServiceReqId by invoking
     *               caseEoaBoIntf.getServiceReqIdStatus() method.
     */

    public String getServiceReqIdStatus(String fdbkObjid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching DeliveryDate by invoking
     *               caseEoaBoIntf.getDelvDateForRx() method.
     */

    public String getDelvDateForRx(String caseObjid, String rxObjid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching requestId by invoking caseEoaBoIntf.getT2Req()
     *               method.
     */

    public String getT2Req(String caseId) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching unit shipping details by invoking
     *               caseEoaBoIntf.getUnitShipDetails() method.
     */

    public String getUnitShipDetails(String caseObjid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching case Score by invoking by invoking
     *               caseEoaBoIntf..getCaseScore() method.
     */

    public List<RecomDelvInfoServiceVO> getCaseScore(String caseid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching readyToDeliver date by invoking
     *               caseEoaBoIntf.getReadyToDelv() method.
     */

    public String getReadyToDelv(String rxObjid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseId,String rxObjid
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDServiceException
     * @Description:This method is used for fetching pending recommendation details by invoking
     *                   caseEoaBoIntf.getPendingRcommendation() method.
     */
    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid) throws RMDServiceException;

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for checking Whether delivery mechanism is present for
     *               particular Customer are not by invoking caseEoaBoIntf.checkForDelvMechanism()
     *               method.
     */

    public String checkForDelvMechanism(String customerName) throws RMDServiceException;

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used for fetching Msdc Notes by invoking
     *               caseEoaBoIntf.getMsdcNotes() method.
     */
    public String getMsdcNotes(String caseObjid, String rxObjid, String fromScreen,String custFdbkObjId) throws RMDServiceException;

    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDServiceException
     * @Description:This method is used for fetching closure details based on caseObj id by invoking
     *                   caseeoaboimpl.getClosureDetails() method..
     */
    public CustomerFdbkVO getClosureDetails(String caseObjId) throws RMDServiceException;
    /**
     * @Author :
     * @return: String
     * @param coreRxEoaVO
     * @throws :RMDServiceException
     * @Description:This method does eservice validation invoking doEserviceValidation()method in
     *                   CaseEoaBOImpl.java.
     */
    public String doEserviceValidation(ScoreRxEoaVO objScoreRxEoaVO) throws RMDServiceException;
    public String checkForContollerConfig(VehicleConfigVO objVehicleConfigVO) throws RMDServiceException;

    /**
     * @Author:
     * @param customerId
     * @return:List<ElementVO>
     * @throws RMDServiceException
     * @Description: This method is used for fetching the list of all Road Initials based upon
     *               CustomerId.
     */

    public List<ElementVO> getRoadNumberHeaders(final String customerId) throws RMDServiceException;

    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDServiceException
     * @Description: This method is used to checking foe maximum numbers of units on which mass
     *               apply rx can be applied.
     */
    public String getMaxMassApplyUnits() throws RMDServiceException;

    /**
     * @Author:
     * @param :MassApplyRxVO objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDServiceException
     * @Description:This method is used for creating a new Case and delivering Recommendations for
     *                   the assets selected by user.
     */

    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO) throws RMDServiceException;

    /**
     * This method is used to get the enabled Rxs for enabling/disabling append/close buttons
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDServiceException
     */
    List<String> getEnabledRxsAppendClose(CaseAppendServiceVO caseAppendServiceVO) throws RMDServiceException;

    /**
     * This method is used to perform append operations
     * 
     * @param caseAppendVO
     * @throws RMDServiceException
     */
    void appendRx(CaseAppendServiceVO caseAppendVO) throws RMDServiceException;

    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId) throws RMDServiceException;

    /**
     * This method returns a count of Open FL work order and will be called while closing the Case
     * will return the open work order count from the eservices.
     * 
     * @param lmsLocoId
     * @return
     * @throws RMDBOException
     */
    public int getOpenFLCount(String lmsLocoId) throws RMDServiceException;

    /**
     * Author :
     * 
     * @param : caseId
     * @return: String
     * @throws RMDServiceException
     *             To get LMSLocoId for a case
     */
    public String getLmsLocoID(String caseId) throws RMDServiceException;
    String getCaseTitle(String strCaseId) throws RMDServiceException;
    /**
     * Author :
     * 
     * @param : caseAppendServiceVO
     * @return: CaseAppendServiceVO
     * @throws RMDServiceException
     *             To get LMSLocoId for a case
     */
    public String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) throws RMDServiceException;

    String saveToolOutputActEntry(ToolOutputActEntryVO objOutputActEntryVO) throws RMDServiceException;

    /**
     * The method to get the repair codes
     * 
     * @param RepairCodeEoaDetailsVO
     * @throws RMDServiceException
     */
    List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCode) throws RMDServiceException;

    /**
     * Author :
     * 
     * @param :
     * @return: String
     * @throws RMDServiceException
     *             To get LMSLocoId for a case
     */
    public String moveDeliverToolOutput(String userId, String currCaseId, String newCaseId, String RxId,
            String assetNum, String assetGroupName, String customerId, String ruleDefId, String ToolId,
            String caseType, String toolObjId) throws RMDServiceException;
    /**
     * Author :
     * 
     * @param : caseId
     * @return: String
     * @throws RMDServiceException
     *             To check for active Rxs
     */
    boolean activeRxExistsInCase(String caseId) throws RMDServiceException;

    /**
     * Author :
     * 
     * @param :
     * @return: String
     * @throws RMDServiceException
     *             To get Rx's enabled for deliver
     */
    public Map<String, List<String>> getEnabledUnitRxsDeliver(String customerId, String assetGroupName, String assetNumber,
            String caseId, String caseType,String currentUser) throws RMDServiceException;
    String updateCaseTitle(FindCaseServiceVO caseDetails) throws RMDServiceException;

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDServiceException
     * @Description:This method return the details for that asset by invoking
     *                   caseeoaboimpl.getAssetCases() method.
     */
    List<SelectCaseHomeVO> getHeaderSearchCases(final FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException;

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param : caseObjId,vehicleObjId
     * @throws :RMDServiceException
     * @Description: This method is used to get Rx Details of the Case.
     */
    public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId) throws RMDServiceException;

    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDServiceException
     * @Description:This method is used for Checking weather unit is Shipped or not.
     */
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO) throws RMDServiceException;
    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDServiceException
     * @Description: This method is used to get Find Cases Details.
     */
    public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO) throws RMDServiceException;

    /**
     * @Author:Mohamed
     * @param :serviceReqId, lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular case.
     */
    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId, String lookUpDays) throws RMDServiceException;

    /**
     * This method is used to perform merge operations
     * 
     * @param caseMergeServiceVO
     * @throws RMDServiceException
     */
    void mergeRx(CaseMergeServiceVO caseMergeServiceVO) throws RMDServiceException;
    /**
     * @Author :Mohamed
     * @return :List<CaseInfoType>
     * @param : UriInfo
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or not
     */
    public List<FindCaseServiceVO> getRxDetailsForReClose(FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException;
    public void updateCloseCaseResult(ReCloseVO objReCloseVO) throws RMDServiceException;
    public void reCloseResetFaults(ReCloseVO reCloseVO) throws RMDServiceException;
    public String addNotesToUnit(AddNotesEoaServiceVO objAddNotesServiceVO) throws RMDServiceException;
    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(String assetNumber, String customerId, String assetGrpName)
            throws RMDServiceException;

    /**
     * @Author :Vamshi
     * @return :String
     * @param :CaseRepairCodeEoaVO objCaseRepairCodeEoaVO
     * @throws :RMDServiceException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */

    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO) throws RMDServiceException;

    /**
     * @Author :Vamshi
     * @return :String
     * @param :String caseObjId
     * @throws :RMDServiceException
     * @Description:This method is responsible for fetching previously Casted vote.
     */

    public String getPreviousVote(String caseObjId) throws RMDServiceException;

    /**
     * @Author:
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO>
     * @throws:RMDServiceException
     * @Description:This method return the details for that asset by invoking
     *                   caseeoaboimpl.getAssetCases() method.
     */
    List<CasesHeaderVO> getHeaderSearchCasesData(final FindCaseServiceVO objFindCaseServiceVO)
            throws RMDServiceException;
    public String getDeliverRxURL(String caseId) throws RMDServiceException;
    public List<CaseTrendVO> getOpenCommRxCount() throws RMDServiceException;
    public List<CaseConvertionVO> getCaseConversionDetails() throws RMDServiceException;
    public String getCaseConversionPercentage() throws RMDServiceException;
    public List<CaseConvertionVO> getTopNoActionRXDetails() throws RMDServiceException;
    public List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDServiceException;

    boolean getAddRepCodeDetails(String caseId) throws RMDServiceException;

    boolean getLookUpRepCodeDetails(String repairCodeList) throws RMDServiceException;
    FaultMobileDataDetailsServiceVO getMobileFaultDetails(VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDServiceException;
    public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(String rxCaseId)
            throws RMDServiceException;
    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDServiceException;
    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description: Function to add RX to the case
     */
    List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDServiceException;
}
