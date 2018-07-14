package com.ge.trans.eoa.services.rxchange.dao.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
/**
 * 
 * @author 212338353
 *
 */
public interface RxChangeDAOIntf {
    /**
     *  To populate Rx change request for GPOC/non GPOC user
     * @param rxChangeSearchVO
     * @return
     * @throws RMDDAOException
     */
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO) throws RMDDAOException;
    public String saveRxChangeInfo(RxChangeVO rxChangeVO) throws RMDDAOException;
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst) throws RMDDAOException;
    public boolean getUserCases(String userId, String caseId) throws RMDDAOException;
    Map<String, String> getOmdUsers(String fName, String sName, String sso) throws RMDDAOException;
    /**
     * To populate Model based on RxTitle
     * @param rxChangeSearchVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO) throws RMDDAOException; 
    /**
     * To save/update RxChange Admin changes
     * @param rxChangeAdminVO
     * @return
     * @throws RMDWebException
     * @throws Exception
     */
    public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO) throws RMDDAOException;
	   /**
	    * To populate Rxchange Admin information Saved in Draft version
	    * @param rxChangeSearchVO
	    * @return
	    * @throws RMDWebException
	    * @throws Exception
	    */
    public RxChangeAdminVO getRxChangeAdminData(RxChangeSearchVO rxChangeSearchVO) throws RMDDAOException;
    public List<UserServiceVO> getRxChangeAdminUsers();

    /**
     * @param rxChangeVO
     * @throws RMDDAOException
     */
    public void saveAuditTrailInfo(RxChangeVO rxChangeVO) throws RMDDAOException;
    /**
     * @param rxChngObjid
     * @return
     * @throws RMDDAOException
     */
    List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid) throws RMDDAOException;
    /**
     * @param escalationData
     */
    public void sendRxChangeEscalation(String escalationData) throws RMDDAOException;
    /**
     * @param scId
     * @return
     * @throws RMDDAOException
     */
    String getRxChangeReqId(String scId) throws RMDDAOException;
 }



