package com.ge.trans.eoa.services.rxchange.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/**
 * @author 212338353
 *
 */
public interface RxChangeServiceIntf {

      /**
     * To populate Rx change request for GPOC/non GPOC user
     * @param rxChangeSearchVO
     * @return
     * @throws RMDWebException
     */
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO) throws RMDServiceException;
    public String saveRxChangeDetails(RxChangeVO rxChangeVO) throws RMDServiceException;
    public boolean getUserCases(String userId, String caseId) throws RMDServiceException;
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst) throws RMDServiceException;
    public Map<String, String> getOmdUsers(String fName, String sName, String sso) throws RMDServiceException;
    
    /**
     * To populate Model based on RxTitle
     * @param rxChangeSearchVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO) throws RMDServiceException; 
    /**
     * To save/update RxChange Admin changes
     * @param rxChangeAdminVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO) throws RMDServiceException;
       /**
        * To populate Rxchange Admin information Saved in Draft version
        * @param rxChangeSearchVO
        * @return
        * @throws RMDWebException
        * @throws Exception
        */
    public RxChangeAdminVO getRxChangeAdminData(RxChangeSearchVO rxChangeSearchVO) throws RMDServiceException;
    List<UserServiceVO> getRxChangeAdminUsers() throws RMDServiceException;
    /**
     * @param rxChngObjid
     * @return
     * @throws RMDServiceException
     */
    List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid) throws RMDServiceException;
    /**
     * @param escalationData
     * @throws RMDServiceException 
     */
    public void sendRxChangeEscalation(String escalationData) throws RMDServiceException;
}
