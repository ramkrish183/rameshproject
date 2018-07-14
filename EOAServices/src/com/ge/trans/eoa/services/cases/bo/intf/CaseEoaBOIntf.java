package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseScoreRepairCodeVO;
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
import com.ge.trans.eoa.services.cases.service.valueobjects.HistoyVO;
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
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileDataDetailsServiceVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDNullObjectException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface CaseEoaBOIntf {

    /**
     * @Author:
     * @param objVehicleFaultServiceVO
     * @throws RMDBOException
     * @throws RMDNullObjectException
     * @Description: Method to get header and data for data screens
     */
    FaultDataDetailsServiceVO getFaultDetails(
            VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException;
    
    FaultMobileDataDetailsServiceVO getMobileFaultDetails(
            VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException;
    
    FaultDataDetailsServiceVO getDPEABFaultDetails(
            VehicleFaultEoaServiceVO objVehicleFaultServiceVO)
            throws RMDBOException, RMDNullObjectException;
    
    /**
     * This method is used for real time check for owners in OMD
     * 
     * @param String
     * @throws RMDBOException
     */
    String getEoaCurrentOwnership(String caseId) throws RMDBOException;
    /**
     * @param acceptCaseVO
     * @throws RMDBOException
     */
    void takeOwnership(AcceptCaseEoaVO acceptCaseVO) throws RMDBOException;
    void yankCase(AcceptCaseEoaVO acceptCaseVO) throws RMDBOException;
/**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String deliverRX(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException;


    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getToolOutputDetails(String strCaseId)
            throws RMDBOException;
    /**
     * 
     * @param caseId
     * @return
     * @throws RMDBOException
     */
    boolean isCaseClosed(String caseId ) throws RMDBOException;
    
    /**
     * 
     * @param 
     * @return String
     * @throws RMDBOException
     */
    String generateCaseIdNumberNextValue() throws RMDBOException;
    /**
     * This method is used for real time update for case closure
     * 
     * @param String
     * @throws RMDServiceException
     */
    void closeCase(CloseCaseVO closeCaseVO) throws RMDBOException;
    
    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDBOException
     */
    String modifyRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException;
    /**
     * @param recomDelvInfoServiceVO
     * @throws RMDBOException
     */
    String replaceRx(RecomDelvInfoServiceVO recomDelvInfoServiceVO) throws RMDBOException;

    /**
     * @param RepairCodeEoaDetailsVO
     * @throws RMDBOException
     */
    List<RepairCodeEoaDetailsVO> getCloseOutRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDBOException;

    /**
     * @param RepairCodeEoaDetailsVO
     * @throws RMDBOException
     */
    List<RepairCodeEoaDetailsVO> getRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDBOException;

    /**
     * @param RepairCodeEoaDetailsVO
     * @throws RMDBOException
     */
    List<RepairCodeEoaDetailsVO> getCaseRepairCodes(
            RepairCodeEoaDetailsVO repairCodeInputType) throws RMDBOException;
    /**
     * @Author: 
     * @param:caseId,userName
     * @return:String
     * @throws:RMDBOException
     * @Description: This method reopens case by invoking caseeoadaoimpl.reOpenCase() method.
     */
    String reOpenCase(String caseID, String userId) throws RMDBOException; 

    /**
     * @param scoreRxEoaVO
     */
    void scoreRx(ScoreRxEoaVO scoreRxEoaVO) throws RMDBOException;
    
    /**
     * @param customer
     * @return
     * @throws RMDBOException
     */
    public String getCustRxCaseIdPrefix(String caseId) throws RMDBOException;
    
    /**
     * This method is used for add case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDBOException
     */
    void addCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDBOException;
    
    /**
     * This method is used for remove case repair codes
     * 
     * @param caseRepairCodeEoaVO
     * @throws RMDBOException
     */
    void removeCaseRepairCodes(CaseRepairCodeEoaVO caseRepairCodeEoaVO) throws RMDBOException;
    
    
     /**
     * This method is used to save feedback given by the user
     * 
     * @param scoreRxEoaVO
     * @throws RMDBOException
     */
    public String saveSolutionFeedback(ScoreRxEoaVO scoreRxEoaVO) throws RMDBOException;
    /**
     * This method is used to retrive cases related to a user
     * 
     * @param FindCaseServiceVO
     * @throws RMDBOException
     */
    public List<SelectCaseHomeVO> getUserCases(
            FindCaseServiceVO findCaseServiceVO) throws RMDBOException;
    
    CMPrivilegeVO hasCMPrivilege(String userId) throws RMDBOException;
    
    String save(CaseInfoServiceVO caseInfoServiceVO, String strUserName)
            throws RMDBOException, RMDNullObjectException;

    public List<CaseTypeEoaVO> getCaseType(CaseTypeEoaVO caseTypeVO) throws RMDBOException;

    /**
     * @param caseId 
     * @Author :
     * @return :List<QueueDetailsVO>
     * @param :
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for fetching a list of Dynamic work
     *                   QueueNames from Data Base.
     */
    
    public List<QueueDetailsVO> getQueueNames(String caseId) throws RMDBOException;

    /**
     * @Author :
     * @return :String
     * @param :queueId,caseId,userId
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for a dispatching case to dynamic work
     *                   queues selected by the user.
     */
    public String dispatchCaseToWorkQueue(final long queueId,
            final String caseId, final String userId) throws RMDBOException;;

    /**
     * @Author :
     * @return :String
     * @param :AddNotesEoaServiceVO
     * @throws :RMDBOEXCEPTION
     * @Description:This method is used for adding Case notes for a given case.
     */

    public String addNotesToCase(final AddNotesEoaServiceVO addnotesVO)
        throws RMDBOException;

    /**
     * @Author  :
     * @return  :StickyNotesDetailsVO
     * @param   :caseId
     * @throws  :RMDBOEXCEPTION
     * @Description:This method is used fetching unit Sticky notes details for a given case.
     */

    public StickyNotesDetailsVO fetchStickyUnitNotes(final String caseId)
            throws RMDBOException;

    /**
     * @Author  :
     * @return  :StickyNotesDetailsVO
     * @param   :caseId
     * @throws  :RMDBOEXCEPTION
     * @Description:This method is used fetching case Sticky notes details for a given case.
     */
    public StickyNotesDetailsVO fetchStickyCaseNotes(final String caseId)
            throws RMDBOException;

    /**
     * @Author: 
     * @param:
     * @return:List<CaseMgmtUsersDetailsVO> 
     * @throws:RMDBOException
     * @Description: This method return the owner for particular caseId by invoking caseeoadaoimpl.getCaseMgmtUsersDetails() method.
     */
    List<CaseMgmtUsersDetailsVO> getCaseMgmtUsersDetails()
            throws RMDBOException;
    /**
     * @Author: 
     * @param:userId,caseId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method assigns owner for particular caseId by invoking caseeoadaoimpl.assignCaseToUser() method.
     */
    String assignCaseToUser(final String userId, final String caseId) throws RMDBOException;

    /**
     * @Author: 
     * @param:caseId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method return the owner for particular caseId by invoking caseeoadaoimpl.getOwnerForCase() method.
     */
    SelectCaseHomeVO getCaseCurrentOwnerDetails(final String caseId) throws RMDBOException;

    /**
     * @Author: 
     * @param:caseId
     * @return:List<CaseHistoryVO> 
     * @throws:RMDBOException
     * @Description: This method return the set of activities for particular caseId by invoking caseeoadaoimpl.getCaseHistory() method.
     */
    
    List<HistoyVO> getCaseHistory(final String caseId) throws RMDBOException;

    List<ViewCaseVO> getViewCases(
            final FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;
    /**
     * @Author: 
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO> 
     * @throws:RMDBOException
     * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
     */
    List<SelectCaseHomeVO> getAssetCases(
            final FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;

    /**
     * @Author  :
     * @return  :String
     * @param   :unitStickyObjId,caseStickyObjId ,applyLevel
     * @throws  :RMDBOEXCEPTION
     * @Description:This method is used  for removing a unit Level as well as case Level Sticky Notes for  a given case.
     */

    public String removeStickyNotes(final String unitStickyObjId,
            final String caseStickyObjId, final String applyLevel)
            throws RMDBOException;

    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDBOEXCEPTION
     * @Description:This method is for updating case details based upon user choice.
     */

    public String updateCaseDetails(
            final FindCaseServiceVO FindCaseServiceVO)
            throws RMDBOException;
    /**
     * @Author:
     * @return:String
     * @param FindCaseServiceVO
     * @throws RMDBOEXCEPTION
     * @Description:This method is for updating case details based upon user choice.
     */
    
    public List<CaseTypeEoaVO> getCaseTypes(CaseTypeEoaVO caseTypeVO) throws RMDBOException;

    /**
     * @Author:
     * @return:List<SolutionBean>
     * @param String caseObjid,String language
     * @throws RMDBOException
     * @Description:This method is used for fetching the selected
     *                   solutions/Recommendations for a Case.
     */
    public List<SolutionBean> getSolutionsForCase (String caseObjid,String language)throws RMDBOException;
    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method in CaseResource.java is used to add a
     *               recommendation to a given Case.
     * 
     */
    public String addRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)throws RMDBOException;
    /**
     * @Author:
     * @param :RecomDelvInfoServiceVO objRecomDelvInfoServiceVO
     * @return:String
     * @throws:RMDBOException
     * @Description: This method in CaseResource.java is used to delete a
     *               recommendation to a given Case.
     * 
     */
    public String deleteRxToCase(RecomDelvInfoServiceVO objRecomDelvInfoServiceVO)throws RMDBOException;
    /**
     * @Author:
     * @param :String caseId,String language
     * @return:CaseInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for fetching the case Information.It
     *               accepts caseId as an Input Parameter and returns caseBean
     *               List.
     * 
     */
    
    public CaseInfoServiceVO getCaseInfo(String caseId,String language)throws RMDBOException;
    /**
     * @Author  :
     * @return  :List<RxHistoryVO>
     * @param   :caseObjId
     * @throws  :RMDBOException
     * @Description:This method fetches the Rx History based on caseObj id
     *               by invoking caseeoadaoimpl.getRxHistory() method.
     */
    public List<RxHistoryVO> getRxHistory(String caseObjId)
            throws RMDBOException;
    /**
     * @Author  :
     * @return  :List<CustomerFdbkVO>
     * @param   :caseObjId
     * @throws  :RMDBOException
     * @Description:This method is used fetching the ServiceReqId & CustFdbkObjId  based on caseObj id
     *               by invoking caseeoadaoimpl.getServiceReqId() method.
     */
    public List<CustomerFdbkVO> getServiceReqId(String caseObjId)
            throws RMDBOException;
    /**
     * @Author  :
     * @return  :List<RxStatusHistoryVO>
     * @param   :serviceReqId
     * @throws  :RMDBOException
     * @Description:This method fetches the RxStatus History based on servicerReq id
     *               by invoking caseeoadaoimpl.getRxstatusHistory() method.
     */
    public List<RxStatusHistoryVO> getRxstatusHistory(String serviceReqId)
            throws RMDBOException;
    /**
     * @Author  :
     * @return  :String
     * @param   :caseObjId
     * @throws  :RMDBOException
     * @Description:This method is used fetching the Good Feedback based on rxCaseId id
     *               by invoking caseeoadaoimpl.getClosureFdbk() method.
     */
    public String getClosureFdbk(String rxCaseId) throws RMDBOException;
    /**
     * @Author  :
     * @return  :List<CloseOutRepairCodeVO>
     * @param   :custFdbkObjId , serviceReqId
     * @throws  :RMDBOException
     * @Description:This method is used fetching the CloseOut Repair Codes based on custFdbkObj id & serviceReqId
     *               by invoking caseeoadaoimpl.getCloseOutRepairCode() method.
     */
    public List<CloseOutRepairCodeVO> getCloseOutRepairCode(
            String custFdbkObjId, String serviceReqId) throws RMDBOException;
    /**
     * @Author  :
     * @return  :List<CloseOutRepairCodeVO>
     * @param   :caseId
     * @throws  :RMDBOException
     * @Description:This method fetches the Attached Details based on case id
     *               by invoking caseeoadaoimpl.getAttachedDetails() method.
     */
    public List<CloseOutRepairCodeVO> getAttachedDetails(String caseId)
            throws RMDBOException;
    /**
     * @Author  :
     * @return  :String
     * @param   :caseObjId
     * @throws  :RMDBOException
     * @Description:This method is used fetching the Rx Notes based on caseObj id
     *               by invoking caseeoadaoimpl.getRxNote() method.
     */
    public String getRxNote(String caseObjId) throws RMDBOException;

    /**
     * @Author:
     * @param:String caseObjid,String rxObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching DeliveryDate by invoking
     *               caseEoaDAOIntf.getDelvDateForRx() method.
     */

    public String getDelvDateForRx(String caseObjid, String rxObjid)
            throws RMDBOException;

    /**
     * @Author:
     * @param:String caseId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching requestId by invoking
     *               caseEoaDAOIntf.getT2Req() method.
     */

    public String getT2Req(String caseId) throws RMDBOException;

    /**
     * @Author:
     * @param:String caseObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching unit shipping details by
     *               invoking caseEoaDAOIntf.getUnitShipDetails() method.
     */

    public String getUnitShipDetails(String caseObjid) throws RMDBOException;

    /**
     * @Author:
     * @param:String caseid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching case Score by invoking by
     *               invoking caseEoaDAOIntf..getCaseScore() method.
     */

    public List<RecomDelvInfoServiceVO> getCaseScore(String caseid) throws RMDBOException;

    /**
     * @Author:
     * @param:String rxObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching readyToDeliver date by
     *               invoking caseEoaDAOIntf.getReadyToDelv() method.
     */

    public String getReadyToDelv(String rxObjid) throws RMDBOException;
    
    /**
     * @Author:
     * @param:String caseId,String rxObjid
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDBOException
     * @Description:This method is used for fetching pending recommendation
     *                   details by invoking
     *                   caseEoaDAOIntf.getPendingRcommendation() method.
     */
    public RecomDelvInfoServiceVO getPendingRcommendation(String caseid)
            throws RMDBOException;

    /**
     * @Author:
     * @param:String customerName
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for checking Whether delivery mechanism
     *               is present for particular Customer are not by invoking
     *               caseEoaDAOIntf.checkForDelvMechanism() method.
     */

    public String checkForDelvMechanism(String customerName)
            throws RMDBOException;
    /**
     * @Author:
     * @param:String caseObjid,String rxObjid,String fromScreen,String custFdbkObjId
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching Msdc Notes by invoking
     *               caseEoaDAOIntf.getMsdcNotes() method.
     */
    public String getMsdcNotes(String caseObjid, String rxObjid,String fromScreen,String custFdbkObjId)
            throws RMDBOException;
            
                /**
     * @Author:
     * @param:String caseId
     * @return:RecomDelvInfoServiceVO
     * @throws:RMDBOException
     * @Description: This method is used for fetching pendingFdbkServiceStatus
     *               by invoking caseEoaDAOIntf.pendingFdbkServiceStatus()
     *               method.
     */
    public List<RecomDelvInfoServiceVO> pendingFdbkServiceStatus(
            String caseId) throws RMDBOException;

    
    
    /**
     * @Author:
     * @param:String fdbkObjid
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used for fetching ServiceReqId by invoking
     *               caseEoaDAOIntf.getServiceReqIdStatus() method.
     */

    public String getServiceReqIdStatus(String fdbkObjid) throws RMDBOException;
    
    /**
     * @Author :
     * @return :CustomerFdbkVO
     * @param :caseObjId
     * @throws :RMDBOException
     * @Description:This method is used for fetching closure details based on
     *                   caseObj id.
     */
    public CustomerFdbkVO getClosureDetails(String caseObjId) throws RMDBOException;
    /**
    * @Author :
    * @return: String
    * @param coreRxEoaVO
    * @throws :RMDBOException
    * @Description:This method does eservice validation invoking
    *                   doEserviceValidation()method in CaseEoaDAOImpl.java.
    */
    public String doEserviceValidation(ScoreRxEoaVO objScoreRxEoaVO)
    throws RMDBOException;
    public String checkForContollerConfig(VehicleConfigVO obVehicleConfigVO) throws RMDBOException;
    
    
    
    /**
     * @Author:
     * @param customerId
     * @return:List<ElementVO>
     * @throws RMDBOException
     * @Description: This method is used for fetching the list of all Road
     *               Initials based upon CustomerId.
     */
    
    public List<ElementVO> getRoadNumberHeaders(final String customerId)throws RMDBOException;
    
    /**
     * @Author:
     * @param :
     * @return:String
     * @throws:RMDBOException
     * @Description: This method is used to checking foe maximum numbers of
     *               units on which mass apply rx can be applied.
     */
    public String getMaxMassApplyUnits() throws RMDBOException;
    
    /**
     * @Author:
     * @param :MassApplyRxVO objMassApplyRxVO
     * @return:List<ViewLogVO>
     * @throws:RMDBOException
     * @Description:This method is used for creating a new Case and delivering
     *                   Recommendations for the assets selected by user.
     * 
     */

    public List<ViewLogVO> massApplyRx(MassApplyRxVO objMassApplyRxVO)
            throws RMDBOException;  
    /**
     * This method is used to enable/disable append button
     * 
     * @param caseAppendServiceVO
     * @throws RMDBOException
     */
     List<String> getEnabledRxsAppendClose(CaseAppendServiceVO caseAppendServiceVO) throws RMDBOException;
    /**
     * This method is used to fetch previous cases for an asset.
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     * @throws RMDBOException 
     */
     List<SelectCaseHomeVO> getPreviousCases(FindCaseServiceVO objFindCaseServiceVO)
            throws  RMDBOException;
     
    
    /**
     * This method is used to perform append related operations
     * 
     * @param caseAppendServiceVO
     * @throws RMDDAOException
     * @throws RMDBOException 
     */
    void appendRx(CaseAppendServiceVO caseAppendServiceVO) throws RMDBOException;
    
    /**
     * This method is used to perform merge related operations
     * 
     * @param caseMergeServiceVO
     * @throws RMDDAOException
     * @throws RMDBOException 
     */
    void mergeRx(CaseMergeServiceVO caseMergeServiceVO) throws RMDBOException;
    
    /**
     * This method returns a count of Open FL work order and will be called while closing the Case will return the open work order count from the eservices. 
     * @param lmsLocoId
     * @return
     * @throws RMDBOException
     */
    public int getOpenFLCount(String lmsLocoId) throws RMDBOException;
    
    public String getLmsLocoID(String caseId) throws RMDBOException;
    

    /**
     * @Author:
     * @param strCaseId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public List<ToolOutputEoaServiceVO> getToolOutput(String strCaseId)
            throws RMDBOException;
    public String getCaseTitle(String strCaseId)
            throws RMDBOException;
    
    /**
     * @Author:
     * @param caseAppendServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String moveToolOutput(CaseAppendServiceVO caseAppendServiceVO) 
            throws RMDBOException;
    
    String saveToolOutputActEntry(ToolOutputActEntryVO objOutputActEntryVO) 
    throws RMDBOException;
    
    public List<RepairCodeEoaDetailsVO> getRepairCodes(String repairCode)
            throws RMDBOException;
    
    public String moveDeliverToolOutput(String userId, String currCaseId,
            String newCaseId, String RxId, String assetNumber,
            String assetGroupName, String customerId, String ruleDefId,
            String ToolId, String caseType,String toolObjId) throws RMDBOException;
    /**
     * @Author:
     * @param caseId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    boolean activeRxExistsInCase(String caseId) throws RMDBOException;
    
    public Map<String, List<String>> getEnabledUnitRxsDeliver(String customerId,
            String assetGroupName, String assetNumber, String caseId,String caseType,String currentUser)throws RMDBOException;
    String updateCaseTitle(FindCaseServiceVO findCaseServiceVO)throws RMDBOException;   
    
    /**
     * @Author: 
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO> 
     * @throws:RMDBOException
     * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
     */
    List<SelectCaseHomeVO> getHeaderSearchCases(
            final FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;

    /**
     * @Author :
     * @return :RxDetailsVO
     * @param : caseObjId,vehicleObjId
     * @throws :RMDBOException
     * @Description: This method is used to get Rx Details of the Case.
     * 
     */
    public RecommDetailsVO getRxDetails(String caseObjId, String vehicleObjId)
            throws RMDBOException;


    /**
     * @Author:Vamsee
     * @param :UnitShipDetailsVO
     * @return :String
     * @throws RMDBOException
     * @Description:This method is used for Checking weather unit is Shipped or not.
     * 
     */
    public String checkForUnitShipDetails(UnitShipDetailsVO objUnitShipDetailsVO) throws RMDBOException;
    /**
     * @Author:
     * @param:FindCasesDetailsVO
     * @return:List<FindCasesDetailsVO>
     * @throws:RMDBOException
     * @Description: This method is used to get Find Cases Details.
     */
    public List<FindCasesDetailsVO> getFindCases(FindCasesVO objFindCasesVO) throws RMDBOException;
    
    /**
     * @Author:Mohamed
     * @param :serviceReqId, lookUpDays
     * @return :List<MaterialUsageVO>
     * @throws RMDServiceException
     * @Description:This method is used to fetch the list of part for particular case.
     * 
     */
    
    public List<MaterialUsageVO> getMaterialUsage(String serviceReqId, String lookUpDays) throws RMDBOException;
    /**
     * @Author :Mohamed
     * @return :List<FindCaseServiceVO>
     * @param : FindCaseServiceVO
     * @throws :RMDWebException
     * @Description: This method is used to check whether the rx is closed or not
     */
    public List<FindCaseServiceVO> getRxDetailsForReClose(
            FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;
    public void updateCloseCaseResult(ReCloseVO objReCloseVO) throws RMDBOException;
    public void reCloseResetFaults(ReCloseVO reCloseVO) throws RMDBOException;
    public String addNotesToUnit(AddNotesEoaServiceVO addnotesVO)throws RMDBOException;
    public StickyNotesDetailsVO fetchStickyUnitLevelNotes(String assetNumber,
            String customerId, String assetGrpName)throws RMDBOException;
    
    
    /**
     * @Author :Vamshi
     * @return :String
     * @param :CaseRepairCodeEoaVO objCaseRepairCodeEoaVO
     * @throws :RMDBOException
     * @Description:This method is responsible for Casting the GPOC Users Vote.
     */

    public String castGPOCVote(CaseRepairCodeEoaVO objCaseRepairCodeEoaVO)
            throws RMDBOException;

    /**
     * @Author :Vamshi
     * @return :String
     * @param :String caseObjId
     * @throws :RMDBOException
     * @Description:This method is responsible for fetching previously Casted
     *                   vote.
     */

    public String getPreviousVote(String caseObjId) throws RMDBOException;
    
    
    /**
     * @Author: 
     * @param:FindCaseServiceVO
     * @return:List<SelectCaseHomeVO> 
     * @throws:RMDBOException
     * @Description:This method return the details for that asset by invoking caseeoadaoimpl.getAssetCases() method.
     */
    List<CasesHeaderVO> getHeaderSearchCasesData(
            final FindCaseServiceVO objFindCaseServiceVO) throws RMDBOException;
	public String getDeliverRxURL(String caseId)throws RMDBOException;
	public List<CaseTrendVO> getOpenCommRxCount() throws RMDBOException;
	public List<CaseConvertionVO> getCaseConversionDetails() throws RMDBOException;
	public  String getCaseConversionPercentage() throws RMDBOException;
	public List<CaseConvertionVO> getTopNoActionRXDetails() throws RMDBOException;
	public  List<GeneralNotesEoaServiceVO> getCommNotesDetails() throws RMDBOException;
    
	public boolean getAddRepCodeDetails(String caseId) throws RMDBOException;

    public boolean getLookUpRepCodeDetails(String repairCodeList)
                    throws RMDBOException;
    
    public List<CaseScoreRepairCodeVO> getCaseScoreRepairCodes(
            String rxCaseId) throws RMDBOException;

    public List<String> validateVehBoms(String customer, String rnh, String rn,
            String rxObjId,String fromScreen) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RecomDelvInfoServiceVO> getCaseRXDelvDetails(String caseId) throws RMDBOException;

}

