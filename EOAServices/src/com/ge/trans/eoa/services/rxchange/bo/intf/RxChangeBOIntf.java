package com.ge.trans.eoa.services.rxchange.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/**
 * @author 212338353
 *
 */
public interface RxChangeBOIntf {

   /**
    * To populate Rx change request for GPOC/non GPOC user
    * @param rxChangeSearchVO
    * @return
    * @throws RMDBOException
    */
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO) throws RMDBOException;
    public boolean getUserCases(String userId, String caseId) throws RMDBOException;
    public String saveRxChangeDetails(RxChangeVO rxChangeVo) throws RMDBOException;
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst ) throws RMDBOException;
    public Map<String, String> getOmdUsers(String fName, String sName, String sso) throws RMDBOException;
    /**
     * To populate Model based on RxTitle
     * @param rxChangeSearchVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO) throws RMDBOException; 
    /**
     * To save/update RxChange Admin changes
     * @param rxChangeAdminVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO) throws RMDBOException;
	   /**
	    * To populate Rxchange Admin information Saved in Draft version
	    * @param rxChangeSearchVO
	    * @return
	    * @throws RMDWebException
	    * @throws Exception
	    */
    public RxChangeAdminVO getRxChangeAdminData(RxChangeSearchVO rxChangeSearchVO) throws RMDBOException;
    List<UserServiceVO> getRxChangeAdminUsers() throws RMDServiceException, RMDBOException;
    /**
     * @param rxChngObjid
     * @return
     * @throws RMDBOException
     */
    List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid) throws RMDBOException;
    /**
     * @param escalationData
     * @return
     */
    public void sendRxChangeEscalation(String escalationData) throws RMDBOException;   

}
